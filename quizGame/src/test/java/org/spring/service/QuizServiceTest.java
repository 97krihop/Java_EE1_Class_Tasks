package org.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.spring.Application;
import org.spring.entity.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class QuizServiceTest extends ServiceTestBase {
    @Autowired
    private QuizService quizService;
    @Autowired
    private CategoryService categoryService;

    private long createParents(String ctg, String sub) {
        long ctgId = categoryService.createCategory(ctg);
        return categoryService.createSubCategory(ctgId, sub);
    }

    private long createQuizzes(String... names) {
        long subId = createParents("foo", "bar");
        for (String n : names) {
            quizService.createQuiz(subId, n, "yes", "no", "may", "no idea", 0);
        }
        return categoryService.getSubCategory(subId).getParent().getId();
    }

    @Test
    public void testNoQuiz() {
        assertEquals(0, quizService.getQuizzes().size());
    }

    @Test
    public void testCreateQuiz() {
        long subId = createParents("foo", "bar");
        String question = "ok?";
        long quizId = quizService.createQuiz(subId, question, "yes", "no", "may", "no idea", 0);
        assertEquals(1, quizService.getQuizzes().size());
        assertEquals(question, quizService.getQuiz(quizId).getQuestion());
    }

    @Test
    public void testNotEnoughQuizzes() {
        long id = createQuizzes("1", "2", "3");
        assertThrows(IllegalArgumentException.class, () -> quizService.getRandomQuizzes(5, id));
    }

    @Test
    public void testGetRandomQuizzes() {
        long id = createQuizzes("1", "2", "3");
        Set<String> questions = new HashSet<>();

        for (int i = 0; i < 50; i++) {

            List<Quiz> quizzes = quizService.getRandomQuizzes(2, id);
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
