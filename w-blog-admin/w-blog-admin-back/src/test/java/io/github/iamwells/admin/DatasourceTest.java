package io.github.iamwells.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;
import com.hankcs.hanlp.utility.CharacterHelper;
import com.hankcs.hanlp.utility.LexiconUtility;
import com.hankcs.hanlp.utility.TextUtility;
import io.github.iamwells.admin.mapper.UserMapper;
import io.github.iamwells.admin.service.MailService;
import io.github.iamwells.admin.service.UserService;
import io.github.iamwells.admin.util.HanLPUtil;
import jodd.util.CharSequenceUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.util.List;

@SpringBootTest
public class DatasourceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;


    @Test
    public void test() {
        System.out.println(new File(HanLP.Config.CoreDictionaryPath).exists());
        String text = "Tom，谢谢你，今天我很高兴，我请你吃价值999元的大餐！";
        System.out.println(HanLPUtil.segmentForIndex(text));
        System.out.println(HanLPUtil.segmentForSearch(text));
    }


    public String segmentForIndex(String text) {
        List<Term> segment = NLPTokenizer.segment(text);
        List<String> words = segment.parallelStream()
                .filter(term -> TextUtility.isAllChinese(term.word) || TextUtility.isAllLetterOrNum(term.word))
                .map(term -> term.word).toList();
        return TextUtility.join(words, " ");
    }

    public String segmentForSearch(String text) {
        List<String> words = NLPTokenizer.segment(text).parallelStream()
                .filter(term ->  CoreStopWordDictionary.shouldInclude(term) || TextUtility.isAllLetterOrNum(term.word))
                .map(term -> term.word ).toList();
        return TextUtility.join(words, " ");
    }

}
