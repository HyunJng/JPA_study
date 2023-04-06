package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id;
    // 공통속성
    private String name;
    private int price;
    private int stockQuantity;

    // 북
    private String author;
    private String isbn;
}
