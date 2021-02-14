package cn.simbrain.logging;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
        startTime = System.currentTimeMillis();
        log.info("-----------------------------------------------------------------");
        log.info("请求开始时间：{}", LocalDateTime.now());
        log.info("请求Url : {}", request.getRequestURL().toString());
        log.info("请求方式 : {}", request.getMethod());
        log.info("请求ip : {}", request.getRemoteAddr());
        log.info("请求方法 : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数 : {}", Arrays.toString(joinPoint.getArgs()));
        log.info("浏览器：{}", userAgent.getBrowser().toString());
        log.info("浏览器版本：{}", userAgent.getBrowserVersion());
        log.info("操作系统: {}", userAgent.getOperatingSystem().toString());
    }

    /**
     * @description: 运行时
     * @Param ret: 请求执行结果
     * @return: void
     */
    @AfterReturning(returning = "ret", pointcut = "LogAopPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        endTime = System.currentTimeMillis();
        log.info("请求结束时间：{}", LocalDateTime.now());
        log.info("请求耗时：{}", (endTime - startTime));
        log.info("请求返回 : {}", ret);
    }

    /**
     * @description: 异常
     * @Param throwable:
     * @return: void
     */
    @AfterThrowing(value = "LogAopPointcut()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        // 异常日志记录
        log.error("-----------------------------------------------------------------");
        log.error("发生异常时间：{}", LocalDateTime.now());
        log.error("抛出异常：{}", throwable.getMessage());
        log.error("-----------------------------------------------------------------");

    }

}
