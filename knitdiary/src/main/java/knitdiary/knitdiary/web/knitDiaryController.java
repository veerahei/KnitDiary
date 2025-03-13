package knitdiary.knitdiary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import knitdiary.knitdiary.domain.CategoryRepository;
import knitdiary.knitdiary.domain.PatternRepository;
import knitdiary.knitdiary.domain.Project;
import knitdiary.knitdiary.domain.ProjectRepository;
import knitdiary.knitdiary.domain.Yarn;
import knitdiary.knitdiary.domain.YarnRepository;

@Controller
public class KnitDiaryController {

    // Inject repositories
    @Autowired
    private ProjectRepository pRepository;

    @Autowired
    private PatternRepository paRepository;

    @Autowired
    private CategoryRepository cRepository;

    @Autowired
    private YarnRepository yRepository;

    // Test: return text
    @GetMapping("/test")
    @ResponseBody
    public String testPage() {
        return "Testisivu";
    }

    // Test: return html view
    @GetMapping("/testhtml")
    public String testHtml() {
        return "test";
    }

    // Get all projects
    @GetMapping("/projectList")
    public String projectList(Model model) {
        model.addAttribute("projects", pRepository.findAll());
        return "projectlist";
    }

    // Add a new project.
    @GetMapping("/addProject")
    public String addProject(Model model) {
        // Function gives an empty model of a project for thymeleaf for user to fill it
        // out
        model.addAttribute("project", new Project());
        model.addAttribute("patterns", paRepository.findAll());
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("yarns", yRepository.findAll());
        return "addproject";
    }

    // Save the new project
    @PostMapping("/saveProject")
    public String saveProject(Project project) {

        pRepository.save(project); // Save the new project to the database

        return "redirect:projectList"; // Redirect to projectlist
    }

    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable("id") Long projectId, Model model) {
        model.addAttribute("project", pRepository.findById(projectId));
        model.addAttribute("patterns", paRepository.findAll());
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("yarns", yRepository.findAll());
        return "editProject";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") Long projectId, Model model) {
        pRepository.deleteById(projectId);
        return "redirect:/projectList";
    }
}
