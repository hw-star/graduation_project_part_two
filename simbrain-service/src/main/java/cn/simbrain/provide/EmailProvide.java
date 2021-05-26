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
 * @description 邮件控制层
 * @date 2021/2/13
 */
@Controller
public class EmailProvide {
    public static final String EMAIL_FROM_ID = "youngvolunteers@foxmail.com";

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    /**
     * @description: 发送邮件
     * @Param type: 类型 普通用户-管理员
     * @Param userPwd: 密码
     * @Param userId: 账号
     * @Param userEmail: 邮箱
     * @return: boolean
     */
    public boolean createEmail(boolean type,String userPwd,String userId,String userEmail) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,false,"utf-8");
            mimeMessageHelper.setSubject("密码找回");
            if (type)
                mimeMessageHelper.setText("<h2 style='color:#f40'>类型：管理员</h2>"+"<h2 style='color:#409EFF'>账号："+userId+"</h2>"+"<h2 style='color:#409EFF'>密码："+userPwd+"</h2>"+"<h3 style='color:#87CEFA'>请妥善保管密码。</h3>"+"<h3><a style='color:#87CEFA;text-decoration：none !important;' href='http://www.bishe.link/admin/'>返回青年志愿者后台</a></h3>",true);
            else
                mimeMessageHelper.setText("<h2 style='color:#f40'>类型：普通用户</h2>"+"<h2 style='color:#409EFF'>账号："+userId+"</h2>"+"<h2 style='color:#409EFF'>密码："+userPwd+"</h2>"+"<h3 style='color:#87CEFA'>请妥善保管密码。</h3>"+"<h3><a style='color:#87CEFA;text-decoration：none !important;' href='http://www.bishe.link'>返回青年志愿者服务</a></h3>",true);
            mimeMessageHelper.setFrom(EMAIL_FROM_ID);
            mimeMessageHelper.setTo(userEmail);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            return false;
        }

    }
}
