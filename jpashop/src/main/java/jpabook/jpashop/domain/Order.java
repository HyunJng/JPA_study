package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // orders이름으로 변경
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // order를 조회할 때 member을 join해서 같이 가져옴
    // EGER을 하면1: JPQL select o from Order o; -> >SQL로 그대로 번역
    // EGER을 하면2:  select * from order 100개가 있다고 하면 100개를 가져오기위해 쿼리가 100개 들어감 (n + 1 문제 하나 될 떄 n개가 같이 전송)
    @JoinColumn(name = "member_id") // FK이름이 member_id가 됨
    private Member member;

    @OneToMany(mappedBy = "order", cascade =CascadeType.ALL)
    // order에 persist() - insert될 때 orderItems에도 각각의 Item이 자동으로 insert됨
    // 이전에는 persist(itemA) persist(itemB)이렇게 각각 해주어야했다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // order저장할 때 delivery에 값만 세팅해주면 delivery도 자동 세팅된다.
    @JoinColumn(name = "delivery_id") // FK를 받으면 다 JointCoumn으로 이름 다시지정해줌
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    // === 연관관계 메서드 ===//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성메서드==//
    // 주문 생성은 복잡한 로직이 필요하기 때문에(item받아서 넣고, delivery연관되고)
    // 따로 생성 메서드 만들어서 진행하는 것이 좋다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //== 비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliverStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // == 조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
