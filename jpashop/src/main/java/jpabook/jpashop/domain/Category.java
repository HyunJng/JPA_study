package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    // 중간테이블이 category_item 이라는 의미
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    ) // 관계형에서는 다대다 관계는 중간테이블이 있어야 가능.
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent; // 상위 카테고리가 있을 수도 있어서

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // == 연관관계 메서드 == //
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
