import static org.junit.Assert.assertEquals;

import excelchaos_model.database.ProjectParticipation;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import excelchaos_model.sorter.ParticipationSortByDate;
import org.junit.Before;
import org.junit.Test;

public class ParticipationSortByDateTest {

    private ParticipationSortByDate sorter;
    private List<ProjectParticipation> participations;

    @Before
    public void setUp() {
        sorter = new ParticipationSortByDate();
        participations = new ArrayList<>();
    }

    @Test
    public void testCompareEqualDates() {
        ProjectParticipation participation1 = new ProjectParticipation();
        participation1.setParticipation_period(Date.from(LocalDate.of(2022, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        ProjectParticipation participation2 = new ProjectParticipation();
        participation2.setParticipation_period(Date.from(LocalDate.of(2022, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        participations.add(participation1);
        participations.add(participation2);

        Collections.sort(participations, sorter);

        assertEquals(participation1, participations.get(0));
        assertEquals(participation2, participations.get(1));
    }

    @Test
    public void testCompareDifferentDates() {
        ProjectParticipation participation1 = new ProjectParticipation();
        participation1.setParticipation_period(Date.from(LocalDate.of(2021, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        ProjectParticipation participation2 = new ProjectParticipation();
        participation2.setParticipation_period(Date.from(LocalDate.of(2022, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        participations.add(participation1);
        participations.add(participation2);

        Collections.sort(participations, sorter);

        assertEquals(participation1, participations.get(0));
        assertEquals(participation2, participations.get(1));
    }
}
