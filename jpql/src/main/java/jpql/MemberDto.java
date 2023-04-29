package jpql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class MemberDto {
    private String username;
    private Integer age;

    public MemberDto(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}
