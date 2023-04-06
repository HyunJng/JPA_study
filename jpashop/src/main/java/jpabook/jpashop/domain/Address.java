package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable // 어딘가(다른 클래스)에 내장될 수 있음
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {  // protected
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode =zipcode;
    }
}
