import excelchaos_model.ProjectParticipation;
import excelchaos_model.ProjectParticipationManager;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectParticipationManagerTest {
    @Test
    void testRemoveAll(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        assertEquals(manager.getAllProjectParticipations().size(), 0);
    }

    @Test
    void testGetByProjectIDValid(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var projectParticipation = new ProjectParticipation(1,1,0.5, calendar.getTime());
        manager.addProjectParticipation(projectParticipation);
        var recProjectParticipation = manager.getProjectParticipationByProjectID(1).get(0);
        assertEquals(projectParticipation.getParticipation_period(), recProjectParticipation.getParticipation_period());
        assertEquals(projectParticipation.getProject_id(), recProjectParticipation.getProject_id());
        assertEquals(projectParticipation.getPerson_id(), recProjectParticipation.getPerson_id());
        assertEquals(projectParticipation.getScope(), recProjectParticipation.getScope());
    }

    @Test
    void testGetByProjectIDInvalid(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var projectParticipation = new ProjectParticipation(1,1,0.5, calendar.getTime());
        manager.addProjectParticipation(projectParticipation);
        assertEquals(manager.getProjectParticipationByProjectID(2).size(), 0);
    }

    @Test
    void testGetByPersonIDValid(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var projectParticipation = new ProjectParticipation(1,1,0.5, calendar.getTime());
        manager.addProjectParticipation(projectParticipation);
        var recProjectParticipation = manager.getProjectParticipationByPersonID(1).get(0);
        assertEquals(projectParticipation.getParticipation_period(), recProjectParticipation.getParticipation_period());
        assertEquals(projectParticipation.getProject_id(), recProjectParticipation.getProject_id());
        assertEquals(projectParticipation.getPerson_id(), recProjectParticipation.getPerson_id());
        assertEquals(projectParticipation.getScope(), recProjectParticipation.getScope());
    }

    @Test
    void testGetByPersonIDInvalid(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var projectParticipation = new ProjectParticipation(1,1,0.5, calendar.getTime());
        manager.addProjectParticipation(projectParticipation);
        assertEquals(manager.getProjectParticipationByPersonID(2).size(), 0);
    }
    @Test
    void testGetByPersonAndProjectIDValid(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var projectParticipation = new ProjectParticipation(1,1,0.5, calendar.getTime());
        manager.addProjectParticipation(projectParticipation);
        var recProjectParticipation = manager.getProjectParticipationByProjectIDandPersonID(1, 1).get(0);
        assertEquals(projectParticipation.getParticipation_period(), recProjectParticipation.getParticipation_period());
        assertEquals(projectParticipation.getProject_id(), recProjectParticipation.getProject_id());
        assertEquals(projectParticipation.getPerson_id(), recProjectParticipation.getPerson_id());
        assertEquals(projectParticipation.getScope(), recProjectParticipation.getScope());
    }

    @Test
    void testGetByPersonAndProjectIDInvalid(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var projectParticipation = new ProjectParticipation(1,1,0.5, calendar.getTime());
        manager.addProjectParticipation(projectParticipation);
        assertEquals(manager.getProjectParticipationByProjectIDandPersonID(2, 2).size(), 0);
    }

    @Test
    void testRemoveValid(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var projectParticipation = new ProjectParticipation(1,1,0.5, calendar.getTime());
        manager.addProjectParticipation(projectParticipation);
        manager.removeProjectParticipation(1, 1);
        assertEquals(manager.getProjectParticipationByProjectIDandPersonID(1 ,1).size(), 0);
    }

    @Test
    void testRemoveInvalid(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var projectParticipation = new ProjectParticipation(1,1,0.5, calendar.getTime());
        manager.addProjectParticipation(projectParticipation);
        manager.removeProjectParticipation(2, 1);
        assertEquals(manager.getProjectParticipationByProjectIDandPersonID(1 ,1).size(), 1);
    }

    @Test
    void testGetAll(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var projectParticipations = new ProjectParticipation[10];

        for (int i = 0; i < 10; i++){
            var projectParticipation = new ProjectParticipation(i,i,0.5, calendar.getTime());
            manager.addProjectParticipation(projectParticipation);
            projectParticipations[i] = projectParticipation;
        }

        var recProjectParticipations = manager.getAllProjectParticipations();

        for (int i = 0; i < 10; i++){
            assertEquals(projectParticipations[i].getParticipation_period(), recProjectParticipations.get(i).getParticipation_period());
            assertEquals(projectParticipations[i].getProject_id(), recProjectParticipations.get(i).getProject_id());
            assertEquals(projectParticipations[i].getPerson_id(), recProjectParticipations.get(i).getPerson_id());
            assertEquals(projectParticipations[i].getScope(), recProjectParticipations.get(i).getScope());
        }
    }

    @Test
    void testGetRowCount(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        for (int i = 0; i < 10; i++){
            var projectParticipation = new ProjectParticipation(5,5,0.5, calendar.getTime());
            manager.addProjectParticipation(projectParticipation);
        }
        assertEquals(manager.getRowCountByProjectID(5),10);
        assertEquals(manager.getRowCountByPersonID(5),10);
    }

    @Test
    void testGetRowCountEmpty(){
        var manager = new ProjectParticipationManager();
        manager.removeAllProjectParticipations();
        assertEquals(manager.getRowCountByProjectID(5),0);
        assertEquals(manager.getRowCountByPersonID(5),0);
    }
}
