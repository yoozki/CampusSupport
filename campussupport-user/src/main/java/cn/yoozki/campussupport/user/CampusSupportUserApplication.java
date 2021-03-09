package cn.yoozki.campussupport.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yoozki
 * @date 2021/1/27
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.yoozki.campussupport.user.mapper")
public class CampusSupportUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusSupportUserApplication.class, args);
    }
}
