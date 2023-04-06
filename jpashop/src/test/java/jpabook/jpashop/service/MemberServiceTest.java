package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // @Test 있으면 기본으로 rollback을 해버림 -> insert문이 안보임
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    // @Rollback(false) //
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("Kim");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush(); // insert문이 보고 싶다면 쿼리 날리는 것.
        assertEquals(member, memberRepository.findOne(saveId));
        // 이게 true인 이유는 JPA에서 같은 Transaction안에서 같은 ENtity(PK값 동일)
    }

    @Test //(expected = IllegalStateException.class) Junit5에서는 지원X
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);

        //then
        Assertions.assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
    }
}
