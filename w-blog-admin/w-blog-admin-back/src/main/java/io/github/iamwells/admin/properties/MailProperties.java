package io.github.iamwells.admin.properties;


import lombok.Data;

@Data
public class MailProperties {
    /**
     * 邮件发送服务器主机host，参考spring.mail.host
     */
    private String host;
    /**
     * 邮件用户名，参考spring.mail.username
     */
    private String username;
    /**
     * 邮件密码或token，参考spring.mail.password
     */
    private String password;
    /**
     * 邮件服务器协议，参考spring.mail.protocol
     */
    private String protocol = "smtps";
    /**
     * 邮件服务器端口号，参考spring.mail.port
     */
    private Integer port = 465;
    /**
     * 启动时是否开启邮箱连接测试，参考spring.mail.test-connection
     */
    private Boolean testConnection = false;
}
