package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)// Entity는 디폴트 생성자가 있어야함(프록시 때문). protected로 생성
@ToString(of = {"id", "username", "age"})
//@Setter// setter은 entity에 안쓰는 편이시다.
public class Member extends JpaBaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, Integer age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public Member(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
