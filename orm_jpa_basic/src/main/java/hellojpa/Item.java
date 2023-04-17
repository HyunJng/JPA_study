package hellojpa;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn // 있는 것이 좋다. 기본 DTYPE을 넣어줌.
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;
}
