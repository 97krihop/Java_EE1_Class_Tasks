package org.EE.ejb;


import org.EE.entity.Category;
import org.EE.entity.Quiz;
import org.EE.entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ResetEjb {

    @PersistenceContext
    private EntityManager em;

    public void resetDatabase() {
        deleteEntities(Quiz.class);
        deleteEntities(SubCategory.class);
        deleteEntities(Category.class);
    }

    private void deleteEntities(Class <?> entity) {

        if(entity == null || entity.getAnnotation(Entity.class) == null)
            throw new IllegalArgumentException("Invalid non-entity class");

        String name = entity.getSimpleName();

        Query query = em.createQuery("delete from " + name);
        query.executeUpdate();
    }
}
