package test.database;

import excelchaos_model.database.ProjectCategory;
import excelchaos_model.database.ProjectCategoryManager;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectCategoryManagerTest {
    @Test
    void testRemoveAll(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        assertEquals(manager.getAllProjectCategories().size(), 0);
    }

    @Test
    void testGetValid(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        BigDecimal num = new BigDecimal("12.22");
        var projectCategory = new ProjectCategory(1, 1, "test_1", num);
        manager.addProjectCategory(projectCategory);
        var recProjectCategory = manager.getProject(1);
        assertEquals(recProjectCategory.getCategory_id(), projectCategory.getProject_id());
        assertEquals(recProjectCategory.getProject_id(), projectCategory.getProject_id());
        assertEquals(recProjectCategory.getCategory_name(), projectCategory.getCategory_name());
        assertEquals(recProjectCategory.getApproved_funds(), projectCategory.getApproved_funds());
    }

    @Test
    void testGetInvalid(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        BigDecimal num = new BigDecimal("12.22");
        var projectCategory = new ProjectCategory(1, 1, "test_1", num);
        manager.addProjectCategory(projectCategory);
        var recProjectCategory = manager.getProject(2);
        assertNull(recProjectCategory);
    }

    @Test
    void testRemoveValid(){
        var manager = new ProjectCategoryManager();
        BigDecimal num = new BigDecimal("12.22");
        var projectCategory = new ProjectCategory(1, 1, "test_1", num);
        manager.addProjectCategory(projectCategory);
        manager.removeProjectCategory(1);
        assertNull(manager.getProject(1));
    }

    @Test
    void testRemoveInvalid(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        BigDecimal num = new BigDecimal("12.22");
        var projectCategory = new ProjectCategory(1, 1, "test_1", num);
        manager.addProjectCategory(projectCategory);
        manager.removeProjectCategory(2);
        assertEquals(manager.getAllProjectCategories().size(), 1);
        assertNotNull(manager.getProject(1));
    }

    @Test
    void testGetAll(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        var projectCategories = new ProjectCategory[10];
        for (int i = 0 ; i < 10; i++){
            BigDecimal num = new BigDecimal("12.22");
            var projectCategory = new ProjectCategory(i, i, "test_1", num);
            manager.addProjectCategory(projectCategory);
            projectCategories[i] = projectCategory;
        }
        var recProjectCategories = manager.getAllProjectCategories();
        for (int i = 0; i < 10; i++){
            assertEquals(recProjectCategories.get(i).getCategory_id(), projectCategories[i].getProject_id());
            assertEquals(recProjectCategories.get(i).getProject_id(), projectCategories[i].getProject_id());
            assertEquals(recProjectCategories.get(i).getCategory_name(), projectCategories[i].getCategory_name());
            assertEquals(recProjectCategories.get(i).getApproved_funds(), projectCategories[i].getApproved_funds());
        }
    }

    @Test
    void testGetRowCount(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        for (int i = 0 ; i < 10; i++){
            BigDecimal num = new BigDecimal("12.22");
            var projectCategory = new ProjectCategory(i, i, "test_1", num);
            manager.addProjectCategory(projectCategory);
        }
        assertEquals(manager.getRowCount(), 10);
    }

    @Test
    void testGetRowCountEmpty(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        assertEquals(manager.getRowCount(), 0);
    }

    @Test
    void testUpdateValid(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        BigDecimal num = new BigDecimal("12.22");
        var projectCategory = new ProjectCategory(1, 1, "test_1", num);
        manager.addProjectCategory(projectCategory);
        var upProjectCategory = new ProjectCategory(1, 1, "update_1", num);
        manager.updateProjectCategory(upProjectCategory);
        var recProjectCategory = manager.getProject(1);
        assertEquals(recProjectCategory.getCategory_id(), upProjectCategory.getProject_id());
        assertEquals(recProjectCategory.getProject_id(), upProjectCategory.getProject_id());
        assertEquals(recProjectCategory.getCategory_name(), upProjectCategory.getCategory_name());
        assertEquals(recProjectCategory.getApproved_funds(), upProjectCategory.getApproved_funds());
    }

    @Test
    void testUpdateInvalid(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        BigDecimal num = new BigDecimal("12.22");
        var projectCategory = new ProjectCategory(1, 1, "test_1", num);
        manager.addProjectCategory(projectCategory);
        var upProjectCategory = new ProjectCategory(1, 2, "update_1", num);
        manager.updateProjectCategory(upProjectCategory);
        var recProjectCategory = manager.getProject(1);
        assertEquals(recProjectCategory.getCategory_id(), projectCategory.getProject_id());
        assertEquals(recProjectCategory.getProject_id(), projectCategory.getProject_id());
        assertEquals(recProjectCategory.getCategory_name(), projectCategory.getCategory_name());
        assertEquals(recProjectCategory.getApproved_funds(), projectCategory.getApproved_funds());
    }

    @Test
    void testGetNextID(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        BigDecimal num = new BigDecimal("12.22");
        var projectCategory = new ProjectCategory(1, 1, "test_1", num);
        manager.addProjectCategory(projectCategory);
        assertEquals(manager.getNextID(), 2);
    }

    @Test
    void testGetNextIDEmpty(){
        var manager = new ProjectCategoryManager();
        manager.removeAllProjectCategories();
        assertEquals(manager.getNextID(), 1);
    }
}
