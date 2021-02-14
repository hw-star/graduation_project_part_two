package cn.simbrain.provide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author huowei
 * @version 1.0.0
 * @description 邮件控制层，处理密码丢失问题。
 * @date 2021/2/13
 */
@Controller
public class EmailProvide {
    public static final String EMAIL_FROM_ID = "youngvolunteers@foxmail.com";

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public boolean createEmail(String userPwd,Long userId,String userEmail) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,false,"utf-8");
            mimeMessageHelper.setSubject("密码找回");
            mimeMessageHelper.setText("<h2 style='color:#409EFF'>账号："+userId+"</h2>"+"<h2 style='color:#409EFF'>密码："+userPwd+"</h2>"+"<h3 style='color:#87CEFA'>请妥善保管密码。</h3>"+"<h3><a style='color:#87CEFA;text-decoration：none !important;' href='http://www.baidu.com'>返回青年志愿者服务</a></h3>",true);
            mimeMessageHelper.setFrom(EMAIL_FROM_ID);
            mimeMessageHelper.setTo(userEmail);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            return false;
        }

    }
}
