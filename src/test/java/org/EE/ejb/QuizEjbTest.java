package org.EE.ejb;

import org.EE.entity.Quiz;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class QuizEjbTest extends EjbTestBase {
    @EJB
    private QuizEjb quizEjb;
    @EJB
    private CategoryEjb categoryEjb;

    private long createParents(String ctg, String sub) {
        long ctgId = categoryEjb.createCategory(ctg);
        return categoryEjb.createSubCategory(ctgId, sub);
    }

    private long createQuizzes(String... names) {
        long subId = createParents("foo", "bar");
        for(String n : names){
            quizEjb.createQuiz(subId, n, "yes", "no", "may", "no idea", 0);
        }
        return categoryEjb.getSubCategory(subId).getParent().getId();
    }

    @Test
    public void testNoQuiz() {
        assertEquals(0, quizEjb.getQuizzes().size());
    }

    @Test
    public void testCreateQuiz() {
        long subId = createParents("foo", "bar");
        String question = "ok?";
        long quizId =  quizEjb.createQuiz(subId, question, "yes", "no", "may", "no idea", 0);
        assertEquals(1, quizEjb.getQuizzes().size());
        assertEquals(question, quizEjb.getQuiz(quizId).getQuestion());
    }

    @Test
    public void testNotEnoughQuizzes() {
        long id = createQuizzes("1", "2", "3");
        try{
            quizEjb.getRandomQuizzes(5, id);
            fail();
        }catch(Exception e){

        }
    }

    @Test
    public void testGetRandomQuizzes() {
        long id = createQuizzes("1", "2", "3");
        Set <String> questions = new HashSet <>();

        for(int i = 0 ; i < 50 ; i++){

            List <Quiz> quizzes = quizEjb.getRandomQuizzes(2, id);
            assertEquals(2, quizzes.size());

            Quiz first = quizzes.get(0);
            Quiz second = quizzes.get(1);

            assertNotEquals(first.getQuestion(), second.getQuestion());

            questions.add(first.getQuestion());
            questions.add(second.getQuestion());
        }

        assertEquals(3, questions.size());
        assertTrue(questions.contains("1"));
        assertTrue(questions.contains("2"));
        assertTrue(questions.contains("3"));
    }
}
