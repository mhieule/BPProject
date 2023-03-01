import excelchaos_model.database.Project;
import excelchaos_model.database.ProjectManager;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectManagerTest {
    @Test
    void testRemoveAll() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        assertEquals(manager.getAllProjects().size(), 0);
    }

    @Test
    void testGetValid() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 12, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var project = new Project(1, "test_project", calendar.getTime(), calendar.getTime(),
                calendar.getTime());
        manager.addProject(project);
        var recProject = manager.getProject(1);
        assertEquals(project.getProject_id(), recProject.getProject_id());
        assertEquals(project.getProject_name(), recProject.getProject_name());
        assertEquals(project.getStart_date(), recProject.getStart_date());
        assertEquals(project.getDuration(), recProject.getDuration());
        assertEquals(project.getApproval_date(), recProject.getApproval_date());
    }

    @Test
    void testGetInvalid() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 12, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var project = new Project(1, "test_project", calendar.getTime(), calendar.getTime(),
                calendar.getTime());
        manager.addProject(project);
        var recProject = manager.getProject(2);
        assertNull(recProject);
    }

    @Test
    void testRemoveValid() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 12, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var project = new Project(1, "test_project", calendar.getTime(), calendar.getTime(),
                calendar.getTime());
        manager.addProject(project);
        manager.removeProject(1);
        var recProject = manager.getProject(1);
        assertNull(recProject);
    }

    @Test
    void testRemoveInvalid() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 12, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var project = new Project(1, "test_project", calendar.getTime(), calendar.getTime(),
                calendar.getTime());
        manager.addProject(project);
        manager.removeProject(2);
        assertEquals(manager.getAllProjects().size(), 1);
        assertNotNull(manager.getProject(1));
    }

    @Test
    void testGetAll() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 12, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var projects = new Project[10];
        for (int i = 0; i < 10; i++) {
            var project = new Project(i, "test_project", calendar.getTime(), calendar.getTime(),
                    calendar.getTime());
            projects[i] = project;
            manager.addProject(project);
        }
        var recProjects = manager.getAllProjects();
        for (int i = 0; i < 10; i++) {
            assertEquals(projects[i].getProject_id(), recProjects.get(i).getProject_id());
            assertEquals(projects[i].getProject_name(), recProjects.get(i).getProject_name());
            assertEquals(projects[i].getStart_date(), recProjects.get(i).getStart_date());
            assertEquals(projects[i].getDuration(), recProjects.get(i).getDuration());
            assertEquals(projects[i].getApproval_date(), recProjects.get(i).getApproval_date());
        }
    }

    @Test
    void testGetRowCount() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 12, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 10; i++) {
            var project = new Project(i, "test_project", calendar.getTime(), calendar.getTime(),
                    calendar.getTime());
            manager.addProject(project);
        }
        assertEquals(manager.getRowCount(), 10);
    }

    @Test
    void testGetRowCountEmpty() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        assertEquals(manager.getRowCount(), 0);
    }

    @Test
    void testUpdateValid() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 12, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var project = new Project(1, "test_project", calendar.getTime(), calendar.getTime(),
                calendar.getTime());
        manager.addProject(project);
        var upProject = new Project(1, "update_test", calendar.getTime(), calendar.getTime(),
                calendar.getTime());
        manager.updateProject(upProject);
        var recProject = manager.getProject(1);
        assertEquals(upProject.getProject_id(), recProject.getProject_id());
        assertEquals(upProject.getProject_name(), recProject.getProject_name());
        assertEquals(upProject.getStart_date(), recProject.getStart_date());
        assertEquals(upProject.getDuration(), recProject.getDuration());
        assertEquals(upProject.getApproval_date(), recProject.getApproval_date());
    }

    @Test
    void testUpdateInvalid() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 12, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var project = new Project(1, "test_project", calendar.getTime(), calendar.getTime(),
                calendar.getTime());
        manager.addProject(project);
        var upProject = new Project(2, "update_test", calendar.getTime(), calendar.getTime(),
                calendar.getTime());
        manager.updateProject(upProject);
        var recProject = manager.getProject(1);
        assertEquals(project.getProject_id(), recProject.getProject_id());
        assertEquals(project.getProject_name(), recProject.getProject_name());
        assertEquals(project.getStart_date(), recProject.getStart_date());
        assertEquals(project.getDuration(), recProject.getDuration());
        assertEquals(project.getApproval_date(), recProject.getApproval_date());
    }

    @Test
    void testGetNextID() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 12, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var project = new Project(1, "test_project", calendar.getTime(), calendar.getTime(),
                calendar.getTime());
        manager.addProject(project);
        assertEquals(manager.getNextID(), 2);
    }

    @Test
    void testGetNextIDEmpty() {
        var manager = ProjectManager.getInstance();
        manager.removeAllProjects();
        assertEquals(manager.getNextID(), 1);
    }
}
