package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M") // dtype이 M일때 Movie
@Getter @Setter
public class Movie extends Item{
    private String director;
    private String actor;
}
