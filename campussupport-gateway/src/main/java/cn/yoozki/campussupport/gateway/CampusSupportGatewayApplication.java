package cn.yoozki.campussupport.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yoozki
 * @date 2021/2/2
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CampusSupportGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusSupportGatewayApplication.class, args);
    }
}
