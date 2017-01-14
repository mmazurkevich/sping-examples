package com.example.integration.controller;

import com.example.integration.model.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class OrderController {

    @RequestMapping(path = "/order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrder(@RequestBody Order order) {
        order.setName("Milk");
        return ResponseEntity.ok(order);
    }

    @RequestMapping(path = "/put", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> putName(@RequestParam("name") String name) {
        Order order = new Order(12,name,89);
        return ResponseEntity.ok(order);
    }

    @RequestMapping(path = "/sun", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getSun() {
        return ResponseEntity.ok(new Order(56, "Milk", 21));
    }
}
