package knitdiary.knitdiary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import knitdiary.knitdiary.domain.AppUser;
import knitdiary.knitdiary.domain.AppUserRepository;

@Controller
public class UserController {

    @Autowired
    AppUserRepository auRepository;

    // Get list of users
    @GetMapping("/userManagement")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUserManagement(Model model) {
        model.addAttribute("users", auRepository.findAll());

        return "usermanagement";
    }

    // Edit user
    @GetMapping("/editUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editUser(@PathVariable("id") Long userId, Model model) {

        // Set the user given in pathvariable to a model, to bring it to edituser view
        model.addAttribute("user", auRepository.findById(userId));

        return "edituser";
    }

    // Save edited user
    @PostMapping("/saveUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveUser(@Valid @ModelAttribute("user") AppUser user, BindingResult bindingResult, Model model) {

        //Check validation
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user); 
            return "edituser"; 
        }

        // Set edited attributes to existing user
        AppUser existingUser = auRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUsername(user.getUsername());
        existingUser.setRole(user.getRole());

        auRepository.save(existingUser);

        return "redirect:/userManagement";
    }
}
