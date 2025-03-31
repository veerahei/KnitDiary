package knitdiary.knitdiary.web;

import java.util.List;
import java.util.Optional;

import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import knitdiary.knitdiary.domain.Project;
import knitdiary.knitdiary.domain.ProjectRepository;

@RestController
public class ProjectRestController {

    @Autowired
    ProjectRepository pRepository;

    // Get all projects
    @GetMapping("/projects")
    public List<Project> projectList() {
        return (List<Project>) pRepository.findAll();
    }

    // Get project by id
    @GetMapping("/projects/{id}")
    public Optional<Project> projectById(@PathVariable("id") Long projectId) {
        return pRepository.findById(projectId);
    }

    // Add new project
    @PostMapping("/projects")
    public Project addNewProject(@RequestBody Project project) {
        return pRepository.save(project);
    }

    // Edit project
    @PutMapping("/projects/{id}")
    public Project editProject(@RequestBody Project editedProject, @PathVariable("id") Long projectId) {
        editedProject.setProjectId(projectId);
        return pRepository.save(editedProject);
    }

    // Delete project
    @DeleteMapping("/projects/{id}")
    public void deleteCar(@PathVariable("id") Long projectId) {
        pRepository.deleteById(projectId);
    }
}
