package com.register.services;
import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class paymentService {

    private String secretKey = "rzp_test_itf2r7oaM0kg34";
    private String publishableKey ="ArTlRlCiJhcmjcdcgfI9KKDs";

   private static RazorpayClient razorpayClient;

    {
        try {
            razorpayClient = new RazorpayClient(secretKey,publishableKey);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }
    public static String createOrder(int amount) {
        JSONObject options = new JSONObject(); // Prepare order options
        options.put("amount", amount * 100);   // Convert amount to paise (Razorpay accepts only paise)
        options.put("currency", "INR");       // Specify currency (INR)
        options.put("receipt", "order_rcptid_11"); // Custom receipt identifier for tracking orders
        options.put("payment_capture", 1);    // Automatically capture payments

        try {
            // Call Razorpay's Orders API to create an order
            return razorpayClient.orders.create(options).toString();
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions
            return null;
        }
    }


}
