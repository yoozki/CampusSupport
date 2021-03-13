package cn.yoozki.campussupport.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * @author yoozki
 * @date 2021/3/13
 */
@Component
public interface OrderDelayChannel {

    String INPUT = "orderDelayInput";
    String OUTPUT = "orderDelayOutput";

    @Input(OrderDelayChannel.INPUT)
    SubscribableChannel input();

    @Output(OrderDelayChannel.OUTPUT)
    MessageChannel output();
}
