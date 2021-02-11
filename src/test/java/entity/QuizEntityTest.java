package entity;

import org.junit.jupiter.api.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizEntityTest extends EntityTestBase {

    @Test
    public void testQuiz() {
        Quiz quiz = defaultQuiz(null, "a");
        //missing sub-category
        assertFalse(persistData(quiz));
    }

    @Test
    void testQuizWithSubcategory() {
        Category category = new Category();
        category.setName("Enterprise");

        SubCategory subCategory = new SubCategory("wow");

        category.addSubCategories(subCategory);
        subCategory.setParent(category);

        Quiz quiz = defaultQuiz(subCategory, "a");
        assertTrue(persistData(category, subCategory, quiz));
    }

    @Test
    void testQueries() {
        Category category = new Category();
        category.setName("JEE");

        SubCategory jpa = new SubCategory("JPA");
        jpa.setParent(category);
        category.addSubCategories(jpa);
        SubCategory ejb = new SubCategory("EJB");
        ejb.setParent(category);
        category.addSubCategories(ejb);
        SubCategory jsf = new SubCategory("JSF");
        jsf.setParent(category);
        category.addSubCategories(jsf);


        Quiz quiz1 = defaultQuiz(jpa, "a");
        Quiz quiz2 = defaultQuiz(jpa, "b");
        Quiz quiz3 = defaultQuiz(ejb, "c");
        Quiz quiz4 = defaultQuiz(jsf, "d");

        persistData(category, jpa, ejb, jsf, quiz1, quiz2, quiz3, quiz4);


        TypedQuery <Quiz> query1 = em.createQuery("select q from Quiz q where q.subCategory.name = 'JPA'", Quiz.class);
        List <Quiz> quizzes = query1.getResultList();
        assertEquals(2, quizzes.size());

        TypedQuery <Quiz> query2 = em.createQuery("select q from Quiz q where q.subCategory.parent.name = 'JEE'", Quiz.class);
        List <Quiz> quizzes2 = query2.getResultList();
        assertEquals(4, quizzes2.size());
    }


}
