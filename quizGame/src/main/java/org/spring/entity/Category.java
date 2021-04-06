package org.spring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private final List <SubCategory> subCategories;

    @NotNull
    @Column(unique = true)
    @Size(max = 128)
    private String name;

    public Category() {
        subCategories = new ArrayList <SubCategory>();
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List <SubCategory> getSubCategories() {
        return subCategories;
    }

    public void addSubCategories(SubCategory subCategory) {
        this.subCategories.add(subCategory);
    }
}
