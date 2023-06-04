package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simipleQuery.OrderSimpleOrderQueryDto;
import jpabook.jpashop.repository.order.simipleQuery.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xxxToOne 관계인 것의 성능 최적화는 어떻게 하는가?(외래키 가진 것)
 * Order객체일 때
 * * Order -> Member
 * * Order -> Delivery
 *
 * => fetch 조인으로 최적화한다.
 */
@RestController
@AllArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        return orders;
        // 문제1. 무한 루프. Member이 Order을 조회, Order이 Member을 조회, ...,
        //      양방향 연관관계이기 때문. 양방향이면 한 쪽에 @JsonIgnore 붙여주어야함.
        // 문제2. Order의 Member이 Lazy로딩이여서 Proxy Member(ByteBuddyInterceptor)을 넣어둬서 JSON으로 변경 X
        //      Hinernate5Module 사용해서 지연로딩 무시하도록 하거나, 지연로딩을 JSON만드는ㄴ 시점에 실행하도록 할 수 있음.

        //  위의 두 문제는 해결하는 방법이 있기는 하지만
        //  결국 ENtity바꾸면 Api 스펙이 변하게 되고,
        //  API에서 반드시 필요하지 않는 것까지 모두 DB에서 가져와야하는 성능 상의 문제도 생김.
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // 1. Order -> 1번 쿼리 -> order 2개
        // 2. 2번 Loop -> Member, Delivery 한번에 2번 쿼리 -> 쿼리 4개
        //    1 + 회원 N + 배송 N
        return orderRepository.findAllByCriteria(new OrderSearch()).stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        /**
         * fetch 조인으로 최적화
         * */
        return orderRepository.findAllWithMemberDelivery()
                .stream()
                .map(SimpleOrderDto::new)
                .toList();
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleOrderQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDdtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        // DTO는 Entity를 받아도 문제가 되지 않음. 중요하지 않은 것에서 중요한 것을 참조하는 거라서.
        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
