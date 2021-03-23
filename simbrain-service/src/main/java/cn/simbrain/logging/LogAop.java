package cn.simbrain.logging;

import cn.simbrain.pojo.LogFailure;
import cn.simbrain.pojo.LogSuccess;
import cn.simbrain.service.LogService.LogFailureService;
import cn.simbrain.service.LogService.LogSuccessService;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.joda.time.DateTime;
import cn.simbrain.util.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author huowei
 * @version 1.0.0
 * @description 用户及管理员操作日志
 * @date 2021/2/13
 */

@Aspect
@Component
@Slf4j
public class LogAop {

    @Autowired
    private LogFailureService logFailureService;
    @Autowired
    private LogSuccessService logSuccessService;

    private LogFailure logFailure = new LogFailure();
    private LogSuccess logSuccess = new LogSuccess();
    /**
     *
     * 操作开始时间
     */
    private Long startTime;
    /**
     *
     * 操作结束时间
     */
    private Long endTime;

    public LogAop() {
    }

    /**
     * @description: 扫描路径以及起别名：LogAopPointcut
     * @return: void
     */
    @Pointcut("execution(public * cn.simbrain.controller.*.*(..))")
    public void LogAopPointcut() {
    }

    /**
     * @description: 请求执行之前
     * @Param joinPoint:
     * @return: void
     */
    @Before("LogAopPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求头中的User-Agent
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String name = null;
        String id = null;
        if (request.getHeader("X-Token") != null) {
            Claims claims = Jwt.parseJwt(request.getHeader("X-Token"));
            name = (String) claims.get("name");
            id = claims.getSubject();
        }
        startTime = System.currentTimeMillis();
        try{
            // 请求开始时间
            logSuccess.setStartTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            // 请求Url
            logSuccess.setRequestUrl(request.getRequestURL().toString());
            // 请求方式
            logSuccess.setRequestMethod(request.getMethod());
            // 请求发起人
            logSuccess.setRequestName(name);
            // 操作账号
            logSuccess.setRequestId(id);
            // 请求ip
            logSuccess.setRequestIp(request.getRemoteAddr());
            // 请求方法
            logSuccess.setRequestSignature(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            // 请求参数
            logSuccess.setRequestParam(Arrays.toString(joinPoint.getArgs()));
            // 浏览器
            logSuccess.setRequestBrowser(userAgent.getBrowser().toString());
            // 操作系统
            logSuccess.setRequestSystem(userAgent.getOperatingSystem().toString());


            logFailure.setStartTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            logFailure.setRequestUrl(request.getRequestURL().toString());
            logFailure.setRequestMethod(request.getMethod());
            logFailure.setRequestName(name);
            logFailure.setRequestIp(request.getRemoteAddr());
            logFailure.setRequestSignature(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logFailure.setRequestParam(Arrays.toString(joinPoint.getArgs()));
            logFailure.setRequestBrowser(userAgent.getBrowser().toString());
            logFailure.setRequestSystem(userAgent.getOperatingSystem().toString());
        }catch (Exception e){

        }

    }

    /**
     * @description: 运行时
     * @Param ret: 请求执行结果
     * @return: void
     */
    @AfterReturning(returning = "ret", pointcut = "LogAopPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        endTime = System.currentTimeMillis();
        // 请求结束时间
        logSuccess.setRequestTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        // 请求耗时
        logSuccess.setFinishTime(Long.toString(endTime - startTime));
        // 请求返回
        if (ret!=null)
            logSuccess.setRequestResult(ret.toString());
        logSuccessService.insertMsg(logSuccess);
    }

    /**
     * @description: 异常
     * @Param throwable: 捕获异常
     * @return: void
     */
    @AfterThrowing(value = "LogAopPointcut()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        // 异常日志记录
        // 发生异常时间
        logFailure.setErrorTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        // 抛出异常
        logFailure.setErrorMessage(throwable.getMessage());
        logFailureService.insertMsg(logFailure);
    }
}
