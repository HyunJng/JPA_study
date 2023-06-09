package jpaboo.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpaboo.jpashop.domain.Book;
import jpaboo.jpashop.domain.Order;
import jpaboo.jpashop.domain.OrderItem;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager(); // application에 딱 하나만 존재해야한다.

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            em.persist(book);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
