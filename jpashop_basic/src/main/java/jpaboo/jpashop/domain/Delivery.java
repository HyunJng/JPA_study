package jpaboo.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Setter
@Getter
@Entity
public class Delivery  extends BaseEntity{

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;
}
