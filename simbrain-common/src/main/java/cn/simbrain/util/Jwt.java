package cn.simbrain.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.Date;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @author huowei
 * @version 1.0.0
 * @description Jwt令牌
 * @date 2021/2/14
 */
@Component
@Data
// @ConfigurationProperties("simbrain.jwt.config")
public class Jwt {
    /**
     * 密钥
     */
    private static String secretKey = "SimBrain0510";
    /**
     * 令牌有效时间(默认6天)
     */
    private static long expires = 60 * 60 * 24 * 6;

    /**
     * @description: 生成Jwt令牌
     * @Param id: 主键ID
     * @Param userId: 账号
     * @Param isLogin: 是否登录
     * @Param name: 名字
     * @Param isUser: 是否普通用户
     * @return: java.lang.String
     */
    public static String createJwt(String id, String userId, Boolean isLogin, String name, boolean isUser){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(userId)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .claim("isLogin",isLogin)
                .claim("name", name)
                .claim("user", isUser);
        if (expires > 0){
            jwtBuilder.setExpiration(new Date(nowMillis + expires*1000));
        }
        return jwtBuilder.compact();
    }

    /**
     * @description: 解析Token
     * @Param jwtToken: token
     * @return: io.jsonwebtoken.Claims
     */
    public static Claims parseJwt(String jwtToken){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
    }

    /**
     * @description: 验证token是否正确以及有效
     * @Param jwtToken: token
     * @return: boolean
     */
    public static boolean checkToken(String jwtToken) {
        if(isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
