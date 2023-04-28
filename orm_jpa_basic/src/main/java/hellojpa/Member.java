package hellojpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity // JPA가 관리하는 객체라는 의미
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne // Member이 Team테이블과의 관계에서 N의 역할
    @JoinColumn(name = "TEAM_ID") // Member 테이블의 foriegn키와 매핑
    private Team team;

    @Embedded
    private Period period;

    @Embedded
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city",
                    column = @Column(name = "work_city")),
            @AttributeOverride(name = "street",
                    column = @Column(name = "work_street")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "work_zipcode"))
    })
    private Address workAddress;

    @ElementCollection
    @CollectionTable(name = "FAVORIT_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID")
    )
    @Column(name = "FOOD_NAME")
    private Set<String> favoritFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns =
//        @JoinColumn(name = "MEMBER_ID")
//    )
//    private List<Address> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();
//    @OneToOne
//    @JoinColumn(name = "LOCKER_ID")
//    private Locker locker;

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
