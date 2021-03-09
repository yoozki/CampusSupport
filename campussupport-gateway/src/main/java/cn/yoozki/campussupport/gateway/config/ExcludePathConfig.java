package cn.yoozki.campussupport.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yoozki
 * @date 2021/2/8
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.cloud.gateway")
public class ExcludePathConfig {

    private List<String> excludePaths;
}
