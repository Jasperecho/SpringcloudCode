package com.yjj.controller;

import com.yjj.entity.CommentResult;
import com.yjj.entity.Payment;
import com.yjj.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommentResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("--------插入"+result);
        if (result > 0){
            return new CommentResult(200,"success,serverPort: "+serverPort,result);
        }else {
            return new CommentResult(444,"fail",null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommentResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("--------插入"+payment);
        if (payment != null){
            return new CommentResult(200,"success,serverPort: "+serverPort,payment);
        }else {
            return new CommentResult(444,"fail"+id,null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

}
