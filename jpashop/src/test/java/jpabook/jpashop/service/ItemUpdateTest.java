package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception{
        // TX
        Book book = em.find(Book.class, 1L);

        // 변경감지 == dirty checking
        // 변경하고 DB에 sql 날린 적이 없어도 알아서 DB에 반영됨.
        // 문제는 준영속 엔티티 : 더이상 영속성 관리를 하지 않는 Entity
    }
}
