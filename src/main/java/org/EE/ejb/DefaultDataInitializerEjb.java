package org.EE.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import java.util.function.Supplier;

import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;

@Singleton
@Startup
public class DefaultDataInitializerEjb {

    @EJB
    private CategoryEjb categoryEjb;

    @EJB
    private QuizEjb quizEjb;

    @PostConstruct
    @TransactionAttribute(NOT_SUPPORTED)
    public void initialize() {

        Long category1 = attempt(() -> categoryEjb.createCategory("Software Engineering"));
        Long category2 = attempt(() -> categoryEjb.createCategory("Computer stuff"));

        Long subCategory1 = attempt(() -> categoryEjb.createSubCategory(category1, "Enterprise Programming"));
        Long subCategory2 = attempt(() -> categoryEjb.createSubCategory(category1, "Information Security"));
        Long subCategory3 = attempt(() -> categoryEjb.createSubCategory(category1, "Java"));
        Long subCategory4 = attempt(() -> categoryEjb.createSubCategory(category2, "GPU"));
        Long subCategory5 = attempt(() -> categoryEjb.createSubCategory(category2, "CPU"));

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
        attempt(() -> quizEjb.createQuiz(
                subCategoryId,
                question,
                firstAnswer,
                secondAnswer,
                thirdAnswer,
                fourthAnswer,
                indexOfCorrectAnswer));
    }

    private <T> T attempt(Supplier <T> lambda) {
        try{
            return lambda.get();
        }catch(Exception e){
            return null;
        }
    }
}
