package jpabook.jpashop.repository.order.simipleQuery;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleOrderQueryDto> findOrderDdtos() {
        // JPA는 entity나 valueObject만 반환할 수 있음, DTO로 반환하려면 new 사용
        return em.createQuery(
                        "select new jpabook.jpashop.repository.order.simipleQuery.OrderSimpleOrderQueryDto(o.id, o.name, o.orderDate, o.orderStatus, o.address)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderSimpleOrderQueryDto.class)
                .getResultList();
    }
}
