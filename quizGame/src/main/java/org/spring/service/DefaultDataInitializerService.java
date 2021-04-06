package org.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;


@Service
public class DefaultDataInitializerService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuizService quizService;

    @PostConstruct
    public void initialize() {

        Long category1 = attempt(() -> categoryService.createCategory("Software Engineering"));
        Long category2 = attempt(() -> categoryService.createCategory("Computer stuff"));

        Long subCategory1 = attempt(() -> categoryService.createSubCategory(category1, "Enterprise Programming"));
        Long subCategory2 = attempt(() -> categoryService.createSubCategory(category1, "Information Security"));
        Long subCategory3 = attempt(() -> categoryService.createSubCategory(category1, "Java"));
        Long subCategory4 = attempt(() -> categoryService.createSubCategory(category2, "GPU"));
        Long subCategory5 = attempt(() -> categoryService.createSubCategory(category2, "CPU"));

        createQuiz(subCategory1, "What does EJB stand for?", "Easy Java Beans", "Enterprise Javascript Beans", "Enterprise Java Beans", "Entnety Java Behavior", 2);
        createQuiz(subCategory1, "What does JPA stand for?", "Java Persistence API", "JavaScript Performance API", "Java Performance API", "Java Performance API", 0);

        createQuiz(subCategory2, "What is the shorthand for Cross Site Scripting?", "CXS", "XXS", "XXC", "CCS", 1);
        createQuiz(subCategory2, "What color is a pentester?", "Red", "Blue", "Purple", "Black", 2);

        createQuiz(subCategory3, "Who made java?", "Ken Thompson", "Anders Hejlsberg", "Dennis Ritchie", "James Gosling", 3);

        createQuiz(subCategory4, "Who have a open source GPU driver?", "AMD", "Nvidia", "Intel", "3dfx", 1);
        createQuiz(subCategory4, "Who did a Linus Torvalds say 'fuck you!' to?", "AMD", "Nvidia", "Intel", "3dfx", 1);

        createQuiz(subCategory5, "What does CPU stand for?", "Central Processing Unit", "Core Programmable User", "Calculating Professor Unit", "County Pushchair Unveiling", 0);
        createQuiz(subCategory5, "Who made 64 bit CPU for x86?", "AMD", "Qualcomm", "Intel", "Sun", 0);
        createQuiz(subCategory5, "who made a new ARM CPU that havent before?", "Qualcomm", "Microsoft", "Nvidia", "Apple", 3);

    }

    private void createQuiz(
            long subCategoryId,
            String question,
            String firstAnswer,
            String secondAnswer,
            String thirdAnswer,
            String fourthAnswer,
            int indexOfCorrectAnswer) {
        attempt(() -> quizService.createQuiz(
                subCategoryId,
                question,
                firstAnswer,
                secondAnswer,
                thirdAnswer,
                fourthAnswer,
                indexOfCorrectAnswer));
    }

    private <T> T attempt(Supplier<T> lambda) {
        try {
            return lambda.get();
        } catch (Exception e) {
            return null;
        }
    }
}
