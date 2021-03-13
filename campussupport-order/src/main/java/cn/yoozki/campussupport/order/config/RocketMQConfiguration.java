package cn.yoozki.campussupport.order.config;

import cn.yoozki.campussupport.order.message.OrderDelayChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @author yoozki
 * @date 2021/3/13
 */
@EnableBinding({OrderDelayChannel.class})
public class RocketMQConfiguration {

}
