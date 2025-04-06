package knitdiary.knitdiary.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
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
    public ResponseEntity<Project> projectById(@PathVariable("id") Long projectId) {
        return pRepository.findById(projectId)
                .map(project -> ResponseEntity.ok(project))
                .orElse(ResponseEntity.notFound().build());

    }

    // Add new project
    @PostMapping("/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public Project addNewProject(@Valid @RequestBody Project project) {
        return pRepository.save(project);
    }

    // Edit project
    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> editProject(@RequestBody Project editedProject, @PathVariable("id") Long projectId) {
        if (!pRepository.existsById(projectId)) {
            return ResponseEntity.notFound().build();
        }

        editedProject.setProjectId(projectId);
        return ResponseEntity.ok(pRepository.save(editedProject));
    }

    // Delete project
    @DeleteMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable("id") Long projectId) {
        pRepository.deleteById(projectId);
    }
}
