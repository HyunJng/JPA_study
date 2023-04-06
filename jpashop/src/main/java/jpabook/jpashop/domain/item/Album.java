package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("A") // dtype이 A일때 Album
public class Album extends Item{
    private String artist;
    private String etc;
}
