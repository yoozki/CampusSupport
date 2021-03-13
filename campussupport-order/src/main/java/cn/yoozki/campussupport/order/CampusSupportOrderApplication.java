package cn.yoozki.campussupport.order;

import cn.yoozki.campussupport.order.message.OrderDelayChannel;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

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
