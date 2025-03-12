package knitdiary.knitdiary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import knitdiary.knitdiary.domain.PatternRepository;
import knitdiary.knitdiary.domain.ProjectRepository;

@Controller
public class KnitDiaryController {

    // Inject repositories
    @Autowired
    private ProjectRepository pRepository;

    @Autowired
    private PatternRepository paRepository;

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
}
