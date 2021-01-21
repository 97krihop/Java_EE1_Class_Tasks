import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryEntityTest extends EntityTestBase {
    @Test
    void testTooLongName() {
        Category category = new Category();
        category.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertFalse(persistData(category));
        Category category1 = new Category();
        category1.setName("a");
        assertTrue(persistData(category1));
    }

    @Test
    void testUniqueName() {
        Category category = new Category();
        category.setName("a");
        assertTrue(persistData(category));
        Category category1 = new Category();
        category1.setName("a");
        assertFalse(persistData(category1));

    }


}
