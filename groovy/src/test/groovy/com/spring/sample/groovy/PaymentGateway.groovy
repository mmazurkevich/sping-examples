package com.spring.sample.groovy

interface PaymentGateway {
    boolean makePayment(BigDecimal amount)
}