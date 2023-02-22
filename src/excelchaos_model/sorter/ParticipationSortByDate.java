package excelchaos_model.sorter;

import excelchaos_model.ProjectParticipation;

import java.util.Comparator;

public class ParticipationSortByDate implements Comparator<ProjectParticipation> {
    @Override
    public int compare(ProjectParticipation participation1, ProjectParticipation participation2) {
        return participation1.getParticipation_period().compareTo(participation2.getParticipation_period());
    }
}
