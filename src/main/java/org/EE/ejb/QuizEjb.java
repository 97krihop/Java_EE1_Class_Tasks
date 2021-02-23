package org.EE.ejb;

import org.EE.entity.Quiz;
import org.EE.entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Stateless
public class QuizEjb {

    @PersistenceContext
    private EntityManager em;

    public long createQuiz(
            long subCategoryId,
            String question,
            String firstAnswer,
            String secondAnswer,
            String thirdAnswer,
            String fourthAnswer,
            int indexOfCorrectAnswer
    ) {
        SubCategory subCategory = em.find(SubCategory.class, subCategoryId);
        if(subCategory == null) throw new IllegalArgumentException("No SubCategory with that id: " + subCategoryId);

        Quiz quiz = new Quiz();
        quiz.setSubCategory(subCategory);
        quiz.setQuestion(question);
        quiz.setAnswer_1(firstAnswer);
        quiz.setAnswer_2(secondAnswer);
        quiz.setAnswer_3(thirdAnswer);
        quiz.setAnswer_4(fourthAnswer);
        quiz.setCorrect(indexOfCorrectAnswer);

        em.persist(quiz);
        return quiz.getId();
    }

    public List <Quiz> getQuizzes() {
        TypedQuery <Quiz> query = em.createQuery("select q from Quiz q", Quiz.class);
        return query.getResultList();
    }

    public Quiz getQuiz(long id) {
        return em.find(Quiz.class, id);
    }

    public List <Quiz> getRandomQuizzes(int n, long categoryId) {
        TypedQuery <Quiz> sizeQuery = em.createQuery(
                "select q from Quiz q where q.subCategory.parent.id=?1", Quiz.class);
        sizeQuery.setParameter(1, categoryId);
        List <Quiz> list = sizeQuery.getResultList();
        long size = list.size();
        if(n > size)
            throw new IllegalArgumentException("Cannot choose " + n + " unique quizzes out of the " + size + " existing");


        List <Quiz> res = new ArrayList <Quiz>();
        Random rand = new Random();
        for(int i = 0 ; i < n ; i++){
            int randomIndex = rand.nextInt(list.size());
            res.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
        return res;
    }


}
