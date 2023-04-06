package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor // JPA 최신 버전은 @PersistenceContext를 @Autowired로 바꿀 수 있음
public class MemberRepository {

//    @PersistenceContext // 스프링이 EntityManger을 만들어 둔 것을 주입해줌
    /* Entity매니저를 통한 모든 데이터 변경은 transaction 안에서 이루어져야함 */
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // JPQL 작성 (JPQL, 반환 타입) - 테이블이 아니라 Entity 객체에 대한 조회를 함
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
