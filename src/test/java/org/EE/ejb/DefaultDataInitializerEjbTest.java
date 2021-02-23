package org.EE.ejb;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class DefaultDataInitializerEjbTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.EE")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private QuizEjb quizEjb;
    @EJB
    private CategoryEjb categoryEjb;

    @Test
    public void testDefault() {
        assertTrue(quizEjb.getQuizzes().size() > 0);
        assertTrue(categoryEjb.getAllCategories(true).size() > 0);
        assertTrue(categoryEjb.getAllCategories(true).get(0).getSubCategories().size() > 0);
    }
}
