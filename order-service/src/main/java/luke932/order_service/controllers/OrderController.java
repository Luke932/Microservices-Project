package luke932.order_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import luke932.order_service.entities.Order;
import luke932.order_service.exceptions.UserNotFoundException;
import luke932.order_service.services.OrderService;



@RestController
@RequestMapping("/order-service/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.ok(createdOrder);
        
    }
}


