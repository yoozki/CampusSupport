package cn.yoozki.campussupport.gateway.filter;

import cn.yoozki.campussupport.common.enums.ErrorCodeEnum;
import cn.yoozki.campussupport.common.enums.RedisKeyEnum;
import cn.yoozki.campussupport.common.pojo.UserTokenDTO;
import cn.yoozki.campussupport.common.util.JSONResult;
import cn.yoozki.campussupport.common.util.JwtUtils;
import cn.yoozki.campussupport.gateway.config.ExcludePathConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author yoozki
 * @date 2021/2/2
 */
@Component
public class AuthorizeGlobalFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZE_TOKEN_HEADER = "Authorization";

    @Autowired
    private ExcludePathConfig excludePathConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN_HEADER);
        String path = request.getURI().getPath();
        if (isExcludePath(path)) {
            return chain.filter(exchange);
        }
        if (StringUtils.isBlank(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        } else {
            UserTokenDTO userTokenDTO = null;
            try {
                userTokenDTO = JwtUtils.parseSubject(token);
            } catch (Exception e) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            if (userTokenDTO != null) {
                String openId = userTokenDTO.getOpenId();
                System.out.println(openId);
                if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(RedisKeyEnum.USER_TOKEN_KEY.getKeyName() + openId))) {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private boolean isExcludePath(String path) {
        for (String excludePath : excludePathConfig.getExcludePaths()) {
            if (path.startsWith(excludePath)) {
                return true;
            }
        }
        return false;
    }
}
