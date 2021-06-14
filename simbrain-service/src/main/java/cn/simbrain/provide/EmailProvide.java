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
                mimeMessageHelper.setText("<html><head><base target=\"_blank\" /><style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style><style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style><style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style><style type=\"text/css\">        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}        td, input, button, select, body{font-family:Helvetica, \"Microsoft Yahei\", verdana}        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}        img{ border:0}        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}        blockquote{margin-right:0px}</style></head><body tabindex=\"0\" role=\"listitem\"><table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\"><tbody><tr><td><div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\"><tbody><tr><td width=\"210\"></td></tr></tbody></table></div><div style=\"width:680px;padding:0 10px;margin:0 auto;\"><div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\"><strong style=\"display:block;margin-bottom:15px;\">尊敬的用户：<span style=\"color:#f60;font-size: 16px;\"></span>您好！</strong><strong style=\"display:block;margin-bottom:15px;\">                        您正在进行<span style=\"color: red\">密码找回</span>，您的密码是：<span style=\"color:#f60;font-size: 24px\">" +userPwd+ "</span></strong></div><div style=\"margin-bottom:30px;\"><small style=\"display:block;margin-bottom:20px;font-size:12px;\"><p style=\"color:#747474;\">                            注意：如果你未使用该服务，请忽略该邮件，请及时登录并修改密码以保证帐户安全。</p></small></div></div><div style=\"width:700px;margin:0 auto;\"><div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\"><p>此为系统邮件，请勿回复<br>                        请保管好您的邮箱，避免账号被他人盗用</p></div></div></td></tr></tbody></table></body></html>",true);
            else
                mimeMessageHelper.setText("<html><head><base target=\"_blank\" /><style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style><style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style><style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style><style type=\"text/css\">        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}        td, input, button, select, body{font-family:Helvetica, \"Microsoft Yahei\", verdana}        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}        img{ border:0}        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}        blockquote{margin-right:0px}</style></head><body tabindex=\"0\" role=\"listitem\"><table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\"><tbody><tr><td><div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\"><tbody><tr><td width=\"210\"></td></tr></tbody></table></div><div style=\"width:680px;padding:0 10px;margin:0 auto;\"><div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\"><strong style=\"display:block;margin-bottom:15px;\">尊敬的管理员：<span style=\"color:#f60;font-size: 16px;\"></span>您好！</strong><strong style=\"display:block;margin-bottom:15px;\">                        您正在进行<span style=\"color: red\">密码找回</span>，您的密码是：<span style=\"color:#f60;font-size: 24px\">" +userPwd+ "</span></strong></div><div style=\"margin-bottom:30px;\"><small style=\"display:block;margin-bottom:20px;font-size:12px;\"><p style=\"color:#747474;\">                            注意：如果你未使用该服务，请忽略该邮件，请及时登录并修改密码以保证帐户安全。</p></small></div></div><div style=\"width:700px;margin:0 auto;\"><div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\"><p>此为系统邮件，请勿回复<br>                        请保管好您的邮箱，避免账号被他人盗用</p></div></div></td></tr></tbody></table></body></html>",true);
            mimeMessageHelper.setFrom(EMAIL_FROM_ID);
            mimeMessageHelper.setTo(userEmail);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            return false;
        }

    }
}
