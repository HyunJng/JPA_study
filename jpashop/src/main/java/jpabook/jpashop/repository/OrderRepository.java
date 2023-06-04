package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    /**
     * JPA Criteria 이용(권장X) -> Querydsl로 처리하기 (설명X)
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" +
                            orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    public List<Order> findAllWithItem() {
        /**
         * select * from order o join order_item oi on o.order_id = oi.order_id;
         * - Order은 2개인데 각각 OrderItems도 2개여서 Order이 4개가 되버림
         *
         * - distinct를 작성하면 DB 쿼리에 날려주긴 함
         * 그러나 DB의 한 줄이 완벽하게 똑같아야 구분이 되기 떄문에 DB 쿼리를 보내 가져온 결과는 동일
         * 하지만 JPA에서 Order의 id값이 같으면 중복 제거하고 하나만 담게되는 것
         *
         * - 단점 : 1 : N을 페치조인하면 페이징이 불가능해진다.
         * - DB에 SQL입장에는 order을 위주로 페이징 되는게 아니라 fetch되서 뻥튀기된 것 기준이 되고
         * JPA에 오면 Distinct된 것을 보여주기 때문에
         *
         * - 단점 : 컬랙션 패치 조인은 1개만 사용할 수 있다.
         * - 컬렉션 둘 이상에 패치 조인을 사용해서는 안된다. 데이터가 부정확하게 조회될 수 있다.
         * */
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i", Order.class)
//                .setFirstResult(1) // 불가능
//                .setMaxResults(100)
                .getResultList();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d", Order.class
                ).setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


       /* return em.createQuery("select o from Order o join o.member m" )
//                이 부분은 들어올 지 아닐지 확신이 없어서 동적 처리를 해주어야 한다.
//                "where o.status = :status" +
//                "and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000) // 결과 제한하는 것.
                .getResultList();
    }*/

}
