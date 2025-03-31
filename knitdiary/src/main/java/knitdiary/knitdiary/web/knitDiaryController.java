package knitdiary.knitdiary.web;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import knitdiary.knitdiary.KnitdiaryApplication;
import knitdiary.knitdiary.domain.AppUser;
import knitdiary.knitdiary.domain.AppUserRepository;
import knitdiary.knitdiary.domain.CategoryRepository;
import knitdiary.knitdiary.domain.PatternRepository;
import knitdiary.knitdiary.domain.Project;
import knitdiary.knitdiary.domain.ProjectRepository;
import knitdiary.knitdiary.domain.YarnRepository;

@Controller
public class KnitDiaryController {

    private final UserDetailServiceImpl userDetailServiceImpl;

    private final KnitdiaryApplication knitdiaryApplication;

    private final CommandLineRunner knitDemo;

    private final SecurityFilterChain configure;

    // Inject repositories
    // Test comment
    @Autowired
    private ProjectRepository pRepository;

    @Autowired
    private PatternRepository paRepository;

    @Autowired
    private CategoryRepository cRepository;

    @Autowired
    private YarnRepository yRepository;

    @Autowired
    private AppUserRepository auRepository;

    KnitDiaryController(SecurityFilterChain configure, CommandLineRunner knitDemo,
            KnitdiaryApplication knitdiaryApplication, UserDetailServiceImpl userDetailServiceImpl) {
        this.configure = configure;
        this.knitDemo = knitDemo;
        this.knitdiaryApplication = knitdiaryApplication;
        this.userDetailServiceImpl = userDetailServiceImpl;
    }

    // Homepage with logged in user's own projects and crud functionalities
    @GetMapping("/home")
    public String homePage(Model model) {
        // Find out the current user and user's username
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Get current user's projects and save it to model, to give it to thymeleaf
        model.addAttribute("usersProjects", pRepository.findByAppUserUsername(username));
        return "homepage";
    }

    // Get all projects
    @GetMapping("/projectList")
    public String projectList(Model model) {
        model.addAttribute("projects", pRepository.findAll());
        return "projectlist";
    }

    // Get image
    @GetMapping(value = "/images/{imageId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long imageId) {

        byte[] image = pRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    // Add a new project
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

    // Edit functionality for users own projects. Checks if the user is the owner of
    // the project
    // to be edited
    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable("id") Long projectId, Model model) {
        // Get the current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        AppUser currUser = auRepository.findByUsername(username);

        // Get the project by projectID sent in pathvariable
        Project editedProject = pRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // If projects user is the same that the current user
        if (editedProject.getAppUser().equals(currUser)) {
            model.addAttribute("editedProject", editedProject);
            model.addAttribute("patterns", paRepository.findAll());
            model.addAttribute("categories", cRepository.findAll());
            model.addAttribute("yarns", yRepository.findAll());

            return "editProject";
        }
        return "redirect:/home";

    }

    // Save edited project
    @PostMapping("/saveEditedProject")
    public String saveEditedProject(@Valid @ModelAttribute("editedProject") Project project,
            BindingResult bindingResult, Model model, @RequestPart("file") MultipartFile file) {

        // Get the current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        AppUser currUser = auRepository.findByUsername(username);
        // Save the project for current user
        project.setAppUser(currUser);

        // save image for project
        try {
            project.setImageData(file.getBytes());
        } catch (Exception e) {
            System.out.println("Error saving the image");
        }
        // Check validation
        if (bindingResult.hasErrors()) {

            model.addAttribute("project", project);
            model.addAttribute("patterns", paRepository.findAll());
            model.addAttribute("categories", cRepository.findAll());
            model.addAttribute("yarns", yRepository.findAll());

            return "editProject";
        }

        pRepository.save(project);
        return "redirect:home"; // Redirect to projectlist;
    }

    // Save the new project
    @PostMapping(value = "/saveProject")
    public String saveProject(@Valid @ModelAttribute("project") Project project, BindingResult bindingResult,
            Model model, @RequestPart("file") MultipartFile file) {

        // Get the current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        AppUser currUser = auRepository.findByUsername(username);
        // Save the project for current user
        project.setAppUser(currUser);

        // save image for project
        try {
            project.setImageData(file.getBytes());
        } catch (Exception e) {
            System.out.println("Error saving the image");
        }

        // Check validation
        if (bindingResult.hasErrors()) {

            model.addAttribute("project", project);
            model.addAttribute("patterns", paRepository.findAll());
            model.addAttribute("categories", cRepository.findAll());
            model.addAttribute("yarns", yRepository.findAll());

            return "addProject";
        }

        pRepository.save(project); // Save the new project to the database

        return "redirect:home"; // Redirect to projectlist
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") Long projectId, Model model) {

        // Get the current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        AppUser currUser = auRepository.findByUsername(username);

        // Get the project by projectID sent in pathvariable
        Optional<Project> optionalProject = pRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            // If projects user is the same that the current user
            if (project.getAppUser().equals(currUser)) {
                pRepository.deleteById(projectId);
                return "redirect:/home";
            }
        } else {
            return "redirect:/home";
        }
        return "redirect:/home";
    }

    // ENDPOINTS FOR ADMIN ONLY

    // Delete project from projectlist. Admin can delete which ever project
    @GetMapping("admin/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String aDeleteProject(@PathVariable("id") Long projectId, Model model) {

        pRepository.deleteById(projectId);
        return "redirect:/projectList";
    }

    // Admin edit project
    @GetMapping("admin/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String aEditProject(@PathVariable("id") Long projectId, Model model) {

        // Get the project by projectID sent in pathvariable
        Project project = pRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("project", project);
        model.addAttribute("patterns", paRepository.findAll());
        model.addAttribute("categories", cRepository.findAll());
        model.addAttribute("yarns", yRepository.findAll());
        return "admineditproject";

    }

    // Save the new project
    @PostMapping("/admin/saveProject")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String aSaveProject(@Valid @ModelAttribute("project") Project project, BindingResult bindingResult,
            Model model,
            @RequestPart("file") MultipartFile file) {

        // Get the original project
        Project existingProject = pRepository.findById(project.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + project.getProjectId()));

        // Set the original owner ot the project to the modified project
        project.setAppUser(existingProject.getAppUser());

        if (bindingResult.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("patterns", paRepository.findAll());
            model.addAttribute("categories", cRepository.findAll());
            model.addAttribute("yarns", yRepository.findAll());

            if (project.getAppUser() != null) {
                return "adminEditProject";
            } else {
                return "addProject";
            }

        }

        // Save the image for the project
        try {
            project.setImageData(file.getBytes()); // Image's datatype is multipartfile, convert it to byte array to
                                                   // save it in the db
            pRepository.save(project);
        } catch (IOException e) {
            System.out.println("Error in saving the image");
            e.printStackTrace();
        }
        // Save the edited project
        pRepository.save(project);

        return "redirect:../projectList"; // Redirect to projectlist
    }
}
