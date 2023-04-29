package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager(); // application에 딱 하나만 존재해야한다.

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.changeTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);
            em.persist(member2);

            em.flush();
            em.clear();

            String query =
                    "select " +
                    "case when m.age <= 10 then '학생요금' " +
                            "when m.age >= 60 then '경로요금' " +
                            "else '일반요금' " +
                            "end " +
                            "from Member m";
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.getStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
