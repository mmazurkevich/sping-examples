package com.spring.sample.groovy

import spock.lang.Specification

//http://www.baeldung.com/groovy-spock
class SpockTest extends Specification {

    def "two plus two should equal four"() {
        given:
        int left = 2
        int right = 2

        when:
        int result = left + right

        then:
        result == 4
    }

    def "Should get an index out of bounds when removing a non-existent item"() {
        given:
        def list = [1, 2, 3, 4]

        when:
        list.remove(20)

        then:
        thrown(IndexOutOfBoundsException)
        list.size() == 4
    }

    //Like Parameterized  in JUnit5
    def "numbers to the power of two"(int a, int b, int c) {
        expect:
        Math.pow(a, b) == c

        where:
        a | b | c
        1 | 2 | 2
        2 | 2 | 4
        3 | 2 | 9
    }

    def "Should return true value for mock"() {
        given:
        def paymentGateway = Mock(PaymentGateway)  //Spock has it's own Mock framework
        paymentGateway.makePayment(20) >> true

        when:
        def result = paymentGateway.makePayment(20)

        then:
        result
    }

    def "Should be able to remove from list"() {
        given:
        def list = [1, 2, 3, 4]

        when:
        list.remove(0)

        then:
        list == [2, 3, 4]
    }
}
