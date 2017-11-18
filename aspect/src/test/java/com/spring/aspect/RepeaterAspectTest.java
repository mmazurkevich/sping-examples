package com.spring.aspect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RepeaterTestConfig.class)
public class RepeaterAspectTest {

    @Autowired
    private RepeaterService repeaterService;

    @Test
    public void testRepeaterSuccessfully() {
        assertTrue(repeaterService.repeaterSuccessfully());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRepeaterReachAttemptCount() {
        repeaterService.repeaterReachAttemptCount();
    }

    @Test(expected = NullPointerException.class)
    public void testRepeaterFailed() {
        repeaterService.repeaterFailed();
    }


}