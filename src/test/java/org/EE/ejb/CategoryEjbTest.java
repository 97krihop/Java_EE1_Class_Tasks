package org.EE.ejb;

import org.EE.entity.Category;
import org.EE.entity.SubCategory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(Arquillian.class)
public class CategoryEjbTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.EE")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private CategoryEjb ctgEjb;

    @EJB
    private ResetEjb resetEjb;

    @Before
    public void init() {
        resetEjb.resetDatabase();
    }


    @Test
    public void testNoCategory() {
        List <Category> list = ctgEjb.getAllCategories(false);
        assertEquals(0, list.size());
    }

    @Test
    public void testCreateCategory() {
        String name = "test";
        assertNotNull(ctgEjb.createCategory(name));
    }

    @Test
    public void testGetCategory() {
        String name = "test";
        long id = ctgEjb.createCategory(name);
        Category category = ctgEjb.getCategory(id, false);
        assertEquals(name, category.getName());
    }

    @Test
    public void testCreateSubCategory() {
        String name = "test";
        long id = ctgEjb.createCategory(name);
        String subName = "test2";
        long subId = ctgEjb.createSubCategory(id, subName);

        SubCategory sub = ctgEjb.getSubCategory(subId);
        assertEquals((Long) id, sub.getParent().getId());
        assertEquals(subName, sub.getName());
    }

    @Test
    public void testGetAllCategories() {
        long a = ctgEjb.createCategory("a");
        long b = ctgEjb.createCategory("b");
        long c = ctgEjb.createCategory("c");

        ctgEjb.createSubCategory(a, "1");
        ctgEjb.createSubCategory(b, "2");
        ctgEjb.createSubCategory(c, "3");

        List <Category> categories = ctgEjb.getAllCategories(false);
        assertEquals(3, categories.size());

        Category first = categories.get(0);
        try{
            first.getSubCategories().size();
            fail();
        }catch(Exception ignored){
        }
        categories = ctgEjb.getAllCategories(true);
        first = categories.get(0);
        assertEquals(1, first.getSubCategories().size());
    }

    @Test
    public void testCreateTwice() {
        String test = "test";
        ctgEjb.createCategory(test);
        try{
            ctgEjb.createCategory(test);
            fail();
        }catch(Exception ignored){
        }
    }


}
