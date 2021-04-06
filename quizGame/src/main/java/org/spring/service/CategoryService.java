package org.spring.service;

import org.spring.entity.Category;
import org.spring.entity.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


@Service
@Transactional
public class CategoryService {

    @Autowired
    private EntityManager em;

    public Long createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        em.persist(category);
        return category.getId();
    }

    public Long createSubCategory(long parentId, String name) {
        Category category = em.find(Category.class, parentId);
        if(category == null) throw new IllegalArgumentException("No Category with that id: " + parentId);
        SubCategory subCategory = new SubCategory();
        subCategory.setParent(category);
        subCategory.setName(name);
        em.persist(subCategory);
        return subCategory.getId();
    }

    public List <Category> getAllCategories(boolean withSub) {
        TypedQuery <Category> query = em.createQuery("select c from Category c", Category.class);
        List <Category> category = query.getResultList();
        if(withSub) category.forEach(c -> c.getSubCategories().size());
        return category;
    }

    public Category getCategory(long id, boolean withSub) {
        Category category = em.find(Category.class, id);
        if(withSub && category != null) category.getSubCategories().size();
        return category;
    }

    public SubCategory getSubCategory(long id) {
        return em.find(SubCategory.class, id);
    }
}
