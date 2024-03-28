package io.github.iamwells.admin.properties;


import lombok.Data;

@Data
public class MailProperties {
    /**
     * 邮件发送服务器主机host
     */
    private String host;
    /**
     * 邮件用户名
     */
    private String username;
    /**
     * 邮件密码或token
     */
    private String password;
    /**
     * 邮件服务器协议
     */
    private String protocol = "smtps";
    /**
     * 邮件服务器端口号
     */
    private Integer port = 465;
    /**
     * 启动时是否开启邮箱连接测试
     */
    private Boolean testConnection = false;
}
