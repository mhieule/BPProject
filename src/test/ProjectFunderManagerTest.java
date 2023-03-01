import excelchaos_model.database.ProjectFunder;
import excelchaos_model.database.ProjectFunderManager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectFunderManagerTest {
    @Test
    void testRemoveAll(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        assertEquals(manager.getAllProjectFunder().size(), 0);
    }

    @Test
    void testGetValid(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        var projectFunder = new ProjectFunder(1, 1, "test_1", "test_1", "test_1");
        manager.addProjectFunder(projectFunder);
        var recProjectFunder = manager.getProjectFunder(1);
        assertEquals(projectFunder.getProject_funder_id(),recProjectFunder.getProject_funder_id());
        assertEquals(projectFunder.getProject_id(), recProjectFunder.getProject_id());
        assertEquals(projectFunder.getProject_funder_name(), recProjectFunder.getProject_funder_name());
        assertEquals(projectFunder.getProject_number(), recProjectFunder.getProject_number());
        assertEquals(projectFunder.getFunding_id(), recProjectFunder.getFunding_id());
    }

    @Test
    void testGetInvalid(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        var projectFunder = new ProjectFunder(1, 1, "test_1", "test_1", "test_1");
        manager.addProjectFunder(projectFunder);
        var recProjectFunder = manager.getProjectFunder(2);
        assertNull(recProjectFunder);
    }

    @Test
    void testRemoveValid(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        var projectFunder = new ProjectFunder(1, 1, "test_1", "test_1", "test_1");
        manager.addProjectFunder(projectFunder);
        manager.removeProjectFunder(1);
        var recProjectFunder = manager.getProjectFunder(1);
        assertNull(recProjectFunder);
    }

    @Test
    void testRemoveInvalid(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        var projectFunder = new ProjectFunder(1, 1, "test_1", "test_1", "test_1");
        manager.addProjectFunder(projectFunder);
        manager.removeProjectFunder(2);
        assertEquals(manager.getAllProjectFunder().size(), 1);
        assertNotNull(manager.getProjectFunder(1));
    }

    @Test
    void testGetAll(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        var projectFunders = new ProjectFunder[10];
        for (int i = 0; i < 10; i++){
            var projectFunder = new ProjectFunder(i, i, "test_1", "test_1", "test_1");
            manager.addProjectFunder(projectFunder);
            projectFunders[i] = projectFunder;
        }
        var recProjectFunders = manager.getAllProjectFunder();
        for (int i = 0; i < 10; i++){
            assertEquals(projectFunders[i].getProject_funder_id(), recProjectFunders.get(i).getProject_funder_id());
            assertEquals(projectFunders[i].getProject_id(), recProjectFunders.get(i).getProject_id());
            assertEquals(projectFunders[i].getProject_funder_name(), recProjectFunders.get(i).getProject_funder_name());
            assertEquals(projectFunders[i].getProject_number(), recProjectFunders.get(i).getProject_number());
            assertEquals(projectFunders[i].getFunding_id(), recProjectFunders.get(i).getFunding_id());
        }
    }

    @Test
    void testGetRowCount(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        for (int i = 0; i < 10; i++){
            var projectFunder = new ProjectFunder(i, i, "test_1", "test_1", "test_1");
            manager.addProjectFunder(projectFunder);
        }
        assertEquals(manager.getRowCount(), 10);
    }

    @Test
    void testGetRowCountEmpty(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        assertEquals(manager.getRowCount(), 0);
    }

    @Test
    void testUpdateValid(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        var projectFunder = new ProjectFunder(1, 1, "test_1", "test_1", "test_1");
        manager.addProjectFunder(projectFunder);
        var upProjectFunder = new ProjectFunder(1, 1, "update_1", "update_1", "update_1");
        manager.updateProjectFunder(upProjectFunder);
        var recProjectFunder = manager.getProjectFunder(1);
        assertEquals(upProjectFunder.getProject_funder_id(),recProjectFunder.getProject_funder_id());
        assertEquals(upProjectFunder.getProject_id(), recProjectFunder.getProject_id());
        assertEquals(upProjectFunder.getProject_funder_name(), recProjectFunder.getProject_funder_name());
        assertEquals(upProjectFunder.getProject_number(), recProjectFunder.getProject_number());
        assertEquals(upProjectFunder.getFunding_id(), recProjectFunder.getFunding_id());
    }

    @Test
    void testUpdateInvalid(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        var projectFunder = new ProjectFunder(1, 1, "test_1", "test_1", "test_1");
        manager.addProjectFunder(projectFunder);
        var upProjectFunder = new ProjectFunder(1, 2, "update_1", "update_1", "update_1");
        manager.updateProjectFunder(upProjectFunder);
        var recProjectFunder = manager.getProjectFunder(1);
        assertEquals(projectFunder.getProject_funder_id(),recProjectFunder.getProject_funder_id());
        assertEquals(projectFunder.getProject_id(), recProjectFunder.getProject_id());
        assertEquals(projectFunder.getProject_funder_name(), recProjectFunder.getProject_funder_name());
        assertEquals(projectFunder.getProject_number(), recProjectFunder.getProject_number());
        assertEquals(projectFunder.getFunding_id(), recProjectFunder.getFunding_id());
    }

    @Test
    void testGetNextID(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        var projectFunder = new ProjectFunder(1, 1, "test_1", "test_1", "test_1");
        manager.addProjectFunder(projectFunder);
        assertEquals(manager.getNextID(), 2);
    }

    @Test
    void testGetNextIDEmpty(){
        var manager = new ProjectFunderManager();
        manager.removeAllProjectFunder();
        assertEquals(manager.getNextID(), 1);
    }
}
