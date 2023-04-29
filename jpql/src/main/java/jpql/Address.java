package jpql;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
