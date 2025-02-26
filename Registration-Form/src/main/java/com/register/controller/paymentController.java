package com.register.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.register.services.paymentService;
import java.util.Map;

@RestController
@RequestMapping("api/payment")
public class paymentController {
    @PostMapping
    public String createOrder(@RequestBody Map<String , Object> payment){
        int amount = Integer.parseInt(payment.get("amount").toString());
        System.out.println(payment);
        System.out.println(amount);

        try {
            String orderResponse = paymentService.createOrder(amount);
            if (orderResponse != null) {
                System.out.println(orderResponse);
                return orderResponse.toString();
            } else {
                return "Failed to create Razorpay order.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while creating the order.";
        }


    }

}
