package knitdiary.knitdiary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import knitdiary.knitdiary.domain.Project;
import knitdiary.knitdiary.domain.ProjectRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
public class ProjectTests {

    @Autowired
    private ProjectRepository pRepository;

    // Test get projects
    @Test
    public void getProjectsReturnsAtleastOneProject() {
        assertThat(pRepository.count()).isGreaterThan(0);
    }

    // Test update project
    @Test
    public void updateProject() {
        Project project = new Project("Old Name", null, null, null, null, null, null, null, null);
        pRepository.save(project);

        project.setName("New Name");
        pRepository.save(project);

        Optional<Project> updatedProject = pRepository.findById(project.getProjectId());
        assertThat(updatedProject).isPresent();
        assertThat(updatedProject.get().getName()).isEqualTo("New Name");
    }

    // Test save project
    @Test
    public void saveProject() {
        Project project = new Project("Test project", null, null, null, null, null, null, null, null);
        pRepository.save(project);
        assertThat(project.getProjectId()).isNotNull();
    }

    // Test that we get correct data. Test cases userid and category name
    @Test
    public void getCorrectData() {
        Optional<Project> project = pRepository.findById((long) 1);
        assertThat(project).isPresent();
        assertThat(project.get().getAppUser().getUserId()).isEqualTo(1);
        assertThat(project.get().getCategory().getName()).isEqualTo("Sweater");
    }

    // Test delete functionality
    @Test
    public void deleteProject() {

        Project project = new Project("Test project", null, null, null, null, null, null, null, null);
        pRepository.save(project);

        Long projectId = project.getProjectId();
        assertThat(pRepository.findById(projectId).isPresent());

        pRepository.deleteById(projectId);
        assertThat(pRepository.findById(projectId)).isNotPresent();
    }

}
