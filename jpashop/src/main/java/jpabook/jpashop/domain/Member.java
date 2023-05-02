package jpabook.jpashop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id") // 실제 테이블에서는 컬럼 명을 member_id로 생성하기 위함
    private Long id;

    @NotEmpty
    private String name;
    
    @Embedded // 내장타입이다
    private Address address;

    // order의 member필드에 있는 것을 반영하는 거울일 뿐이야
//    @JsonIgnore
    @OneToMany(mappedBy = "member") // Member이 One쪽이기 때문, 주인이 아니라는 의미
    private List<Order> orders = new ArrayList<>();

}
