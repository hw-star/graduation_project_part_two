package cn.simbrain.filter;

import cn.simbrain.util.Jwt;
import com.google.gson.JsonObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/2/25
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if(antPathMatcher.match("/sysuser/login", path) ||
                antPathMatcher.match("/user/login", path) ||
                antPathMatcher.match("/activity/activitylist/**", path) ||
                antPathMatcher.match("/druid", path) ||
                antPathMatcher.match("/excel/getexcel/**", path) ||
                antPathMatcher.match("/sysuser/findpwd", path) ||
                antPathMatcher.match("/user/adduser", path) ||
                antPathMatcher.match("/user/findpwd", path) ||
                antPathMatcher.match("/policy/policylist/**", path) ||
                antPathMatcher.match("/notice/noticelist/**", path)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse response = exchange.getResponse();
        List<String> token = request.getHeaders().get("X-Token");
        if(null != token) {
            boolean isCheck = Jwt.checkToken(token.get(0));
            if(isCheck) {
                return chain.filter(exchange);
            }
        }
        return out(response);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {
        JsonObject message = new JsonObject();
        message.addProperty("code", 60005);
        message.addProperty("msg", "接口请求超时");
        message.addProperty("data", "");
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
