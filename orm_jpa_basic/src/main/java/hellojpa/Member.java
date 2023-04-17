package hellojpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
