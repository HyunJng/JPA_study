package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// 데이터 변경하는 것은 Transactional이 꼭 필요함
@Transactional(readOnly = true) // readOnly하면 조회에 더 최적회해줌
@RequiredArgsConstructor // final필드 가지고 생성자 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

    /**
   * 회원 가입
   */
   @Transactional //readOnly가 아니여서 따로 지정
    public Long join(Member member) {
        //중복회원 검사
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); // DB에 값을 넣기 전에 Id가 생성되어있음
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     * */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     *  회원 수정
     * */
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
