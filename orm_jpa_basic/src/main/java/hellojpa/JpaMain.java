package hellojpa;

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
            Member member = new Member();
            member.setUsername("member");
            member.setHomeAddress(new Address("homecity", "street", "zipcode"));

            member.getFavoritFoods().add("치킨");
            member.getFavoritFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "zipcode"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "zipcode"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("============= START ===============");
//            Member findMember = em.find(Member.class, member.getId());
//
//            // 치킨 -> 한식 Collection도 값 타입이라 없애고 바꿔끼는 형태로 해야함.
//            findMember.getFavoritFoods().remove("치킨");
//            findMember.getFavoritFoods().add("한식");
//
//            // old1 -> new1
//            findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "zipcode")); // equals, hashcode를 제대로 넣어야 동작
//            findMember.getAddressHistory().add(new AddressEntity("new1", "street", "zipcode"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
    // 그런데 항상 팀을 출력하는 것이 아니라면 Member만 가져오는 것이 최적화(Team까지 가져오는 쿼리는 낭비)
    private static void printMember(Member member) {
        System.out.println("member.getUsername() = " + member.getUsername());
    }
    // 멤버를 출력할 때 항상 팀도 같이 출력해야하면 쿼리 날리는 시점에 같이 Team도 가져오는 것이 좋다.
    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}
