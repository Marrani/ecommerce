package br.com.marrani.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var orderDispacher = new KafkaDispatcher<Order>()) {
            try(var emailDispacher = new KafkaDispatcher<String>()) {

                for (var i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);

                    var order = new Order(userId, orderId, amount);
                    orderDispacher.send("ECOMMERCE_NEW_ORDER", userId, order);

                    var email = "Thank you for your order! We are processing your order!";
                    emailDispacher.send("ECOMMERCE_SEND_EMAIL", userId, email);


                }
            }
        }
    }

}
