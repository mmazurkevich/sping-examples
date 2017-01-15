package com.example.integration.rest;

import com.example.integration.model.Order;
import com.example.integration.rest.config.IntegrationContextConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationContextConfiguration.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class RestTemplateOrderTest {

    @Value("${local.server.port}")
    private int port;

    @Value("${element.test.value}")
    private String month;

    @Test
    public void getSun(){
        Assert.assertEquals("December",month);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Order> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/sun", Order.class);
        Order order = responseEntity.getBody();
        Assert.assertEquals(56, order.getId());
        Assert.assertEquals("Milk", order.getName());
        Assert.assertEquals(21, order.getCount());
    }
}
