package cn.yoozki.campussupport.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yoozki
 * @date 2021/2/8
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.yoozki.campussupport.order.mapper")
public class CampusSupportOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusSupportOrderApplication.class, args);
    }
}
