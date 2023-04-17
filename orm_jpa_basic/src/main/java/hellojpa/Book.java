package hellojpa;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("B") // DTYPE이 B가 됨.
public class Book extends Item{

    private String author;
    private String isbn;

}
