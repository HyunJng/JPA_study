package study.datajpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, Integer age);

    List<Member> findTop3HelloBy();

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    /** 반환타입 */
    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOptionalByUsername(String username);

    /**
     * 페이징
     */
//    @Query(value = "select m from Member m left join m.team t")
//    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable); /** totalCount쿼리도 같이 날림 **/

    /**
     * 벌크성 수정
     */
    @Modifying(clearAutomatically = true) // 이게 있어야 executeUpdate가 실행. getResultList 같은 select가 기본이기 때문
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    /**
     * @EntityGraph : 연관된 것을 한꺼번에 DB에서 fetch join하여 가져오는 것
     */

    // 이렇게 매번 JPQL 쓰는거 귀찮아
    @Query("select m from Member m left join fetch m.team") // member안에 team까지 한 방에 다 끌고옴
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) //
    List<Member> findAll();

    //JPQL도 짜고 ENtityGraph넣는 것도 됨
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findByUsername(@Param("username") String username);

    /**
     * JPA Hint
     */
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true")) // snapshot을 안만듬 => 변경 안됨
    Member findReadOnlyByUsername(String username);

    /**
     * JPA Lock
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
