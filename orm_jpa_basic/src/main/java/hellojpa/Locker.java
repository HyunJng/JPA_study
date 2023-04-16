package hellojpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Locker {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "locker")
    private Member member;
}
