package hellojpa;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
