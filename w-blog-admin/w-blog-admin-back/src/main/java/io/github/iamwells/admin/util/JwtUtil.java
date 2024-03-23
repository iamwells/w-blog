package io.github.iamwells.admin.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.iamwells.admin.entity.User;
import io.github.iamwells.admin.properties.jwt.JwtProperties;
import io.github.iamwells.admin.properties.jwt.JwtHeader;
import io.github.iamwells.admin.properties.jwt.JwtPayload;
import io.github.iamwells.admin.properties.jwt.JwtSignature;
import lombok.SneakyThrows;
import org.apache.ibatis.javassist.NotFoundException;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;

import java.nio.charset.StandardCharsets;

public class JwtUtil {

    private static final String defaultAlgorithm = AlgorithmIdentifiers.HMAC_SHA256;
    private static final long defaultExpMinutes = 120L;
    private static final String defaultType = "JWT";
    private static final String defaultAud = "user";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static String generate(User user, JwtProperties jwtProperties) {
        JwtHeader header = jwtProperties.getHeader();
        JwtPayload payload = jwtProperties.getPayload();
        JwtSignature signature = jwtProperties.getSignature();
        return generate(user, header, payload, signature);
    }

    @SneakyThrows
    public static String generate(User user, JwtHeader header, JwtPayload payload, JwtSignature signature) {
        removeNameAndPass(user);

        JwtClaims claims = getClaims(user, payload);

        JsonWebSignature jws = getJsonWebSignature(header, signature);

        jws.setPayload(claims.toJson());

        return jws.getCompactSerialization();
    }

    public static void removeNameAndPass(User user) {
        user.setPassword(null);
        user.setName(null);
    }

    @SneakyThrows
    private static JwtClaims getClaims(User user, JwtPayload payload) {
        JwtClaims claims = new JwtClaims();

        claims.setGeneratedJwtId();

        String issuer = payload.getIssuer();
        if (issuer != null && !issuer.isEmpty()) {
            claims.setIssuer(issuer);
            claims.setIssuedAt(NumericDate.now());
        }

        String sub = payload.getSub();
        if (sub != null && !sub.isEmpty()) {
            claims.setSubject(sub);
        }

        float exp = payload.getExp();
        claims.setExpirationTimeMinutesInTheFuture(exp > 0 ? exp * 60L : defaultExpMinutes);

        String aud = getStringValueOrDefault(payload.getAud(), defaultAud);
        claims.setAudience(aud);
        claims.setClaim("user", objectMapper.writeValueAsString(user));

        return claims;
    }

    @SneakyThrows
    @SuppressWarnings("SameParameterValue")
    private static JsonWebSignature getJsonWebSignature(JwtHeader header, JwtSignature signature) {

        JsonWebSignature jws = new JsonWebSignature();

        String alg = getStringValueOrDefault(signature.getAlg(), defaultAlgorithm);

        jws.setAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, signature.getAlg()));

        jws.setAlgorithmHeaderValue(alg);


        String secret = signature.getSecret();
        HmacKey hmacKey = getHmacKey(secret);
        jws.setKey(hmacKey);


        String typ = getStringValueOrDefault(header.getTyp(), defaultType);
        jws.setHeader("typ", typ);
        return jws;
    }

    @SneakyThrows
    private static HmacKey getHmacKey(String secret) {
        if (secret == null || secret.isEmpty()) {
            throw new NotFoundException("JWT secret not found! Please set the secret through the .yml/.properties configuration file, or through the setter of the JwtSignature class object.");
        }
        String fullSecret = String.format("%-32s", secret);
        return new HmacKey(fullSecret.getBytes(StandardCharsets.UTF_8));
    }

    public static <T> T verify(String jwt, JwtSignature signature, JwtPayload payload, Class<T> returnType) throws InvalidJwtException, JsonProcessingException {
        String alg = getStringValueOrDefault(signature.getAlg(), defaultAlgorithm);
        String aud = getStringValueOrDefault(payload.getAud(), defaultAud);
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireJwtId()
                .setRequireExpirationTime()
                .setExpectedAudience(aud)
                .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, alg)
                .setVerificationKey(getHmacKey(signature.getSecret()))
                .build();
        JwtContext jwtContext = jwtConsumer.process(jwt);
        JwtClaims jwtClaims = jwtContext.getJwtClaims();
        String json = String.valueOf(jwtClaims.getClaimValue("user"));
        return objectMapper.readValue(json, returnType);
    }

    private static String getStringValueOrDefault(String originalValue, String defaultValue) {
        if (originalValue != null && !originalValue.isEmpty()) {
            return originalValue;
        } else {
            return defaultValue;
        }
    }

}
