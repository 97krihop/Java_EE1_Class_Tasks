package org.spring.service;

import org.spring.entity.Category;
import org.spring.entity.Quiz;
import org.spring.entity.SubCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@Transactional
public class ResetEjb {

    @PersistenceContext
    private EntityManager em;

    public void resetDatabase() {
        deleteEntities(Quiz.class);
        deleteEntities(SubCategory.class);
        deleteEntities(Category.class);
    }

    private void deleteEntities(Class<?> entity) {

        if (entity == null || entity.getAnnotation(Entity.class) == null)
            throw new IllegalArgumentException("Invalid non-entity class");

        String name = entity.getSimpleName();

        Query query = em.createQuery("delete from " + name);
        query.executeUpdate();
    }
}
