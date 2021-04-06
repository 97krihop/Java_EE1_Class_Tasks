package org.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.spring.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
//Important, as referring to state given by Singleton that
//could be modified by previous tests, as they share the same
//SpringBoot application context (ie SpringBoot is started only
//once for all tests).
//Also note that this class does NOT extend ServiceTestBase
@DirtiesContext(classMode = BEFORE_CLASS)
public class DefaultDataInitializerServiceTest {

    @Autowired
    private QuizService quizService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testDefault() {
        assertTrue(quizService.getQuizzes().size() > 0);
        assertTrue(categoryService.getAllCategories(true).size() > 0);
        assertTrue(categoryService.getAllCategories(true).get(0).getSubCategories().size() > 0);
    }
}
