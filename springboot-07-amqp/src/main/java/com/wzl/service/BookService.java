package com.wzl.service;

import com.wzl.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Created by thinkpad on 2018/10/2.
 */

@Service
public class BookService {

    @RabbitListener(queues = "wzl.news")
    public void receive(Book book) {
        System.out.println("收到消息: " + book);
    }


    @RabbitListener(queues = "wzl")
    public void receive02(Message message) {
        System.out.println("body: " + message.getBody());
        System.out.println("property" + message.getMessageProperties());
    }
}
