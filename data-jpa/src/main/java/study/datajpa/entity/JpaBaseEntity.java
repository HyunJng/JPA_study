package study.datajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@MappedSuperclass // 이걸 넣어야 속성들이 테이블에 넣어짐
public class JpaBaseEntity {
    
    @Column(updatable = false) // 수정 불가
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        updateDate = now; //
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = LocalDateTime.now();
    }
}
