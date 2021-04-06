package org.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.spring.Application;
import org.spring.entity.Category;
import org.spring.entity.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CategoryServiceTest extends ServiceTestBase {

    @Autowired
    private CategoryService ctgEjb;

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
