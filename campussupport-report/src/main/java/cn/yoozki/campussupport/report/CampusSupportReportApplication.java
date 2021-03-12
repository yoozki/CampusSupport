package cn.yoozki.campussupport.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yoozki
 * @date 2021/3/12
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CampusSupportReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusSupportReportApplication.class, args);
    }
}
