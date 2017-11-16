package com.spring.aspect;

import org.springframework.stereotype.Service;

@Service
public class TestExample {

    @Repeater(IllegalArgumentException.class)
    public boolean testRepeater() {
        System.out.println("Repeated element");
        getException();
        return true;
    }

    private void getException(){
        throw new IllegalArgumentException();
    }
}
