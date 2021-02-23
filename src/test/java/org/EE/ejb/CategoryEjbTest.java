package org.EE.ejb;

import org.EE.entity.Category;
import org.EE.entity.SubCategory;
import org.junit.Test;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.*;

public class CategoryEjbTest extends EjbTestBase {

    @EJB
    private CategoryEjb ctgEjb;

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
