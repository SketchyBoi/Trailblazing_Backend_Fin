package com.nighthawk.spring_portfolio.mvc.usr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// Built using article: https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html
// or similar: https://asbnotebook.com/2020/04/11/spring-boot-thymeleaf-form-validation-example/
@Controller
@RequestMapping("/mvc/usr")
public class UsrViewController {
    // Autowired enables Control to connect HTML and POJO Object to database easily for CRUD
    @Autowired
    private UsrDetailsService repository;

    @GetMapping("/read")
    public String user(Model model) {
        List<Usr> list = repository.listAll();
        model.addAttribute("list", list);
        return "usr/read";
    }

    /*  The HTML template Forms and PersonForm attributes are bound
        @return - template for person form
        @param - Person Class
    */
    @GetMapping("/create")
    public String userAdd(Usr user) {
        return "usr/create";
    }

    /* Gathers the attributes filled out in the form, tests for and retrieves validation error
    @param - Person object with @Valid
    @param - BindingResult object
     */
    @PostMapping("/create")
    public String userSave(@Valid Usr user, BindingResult bindingResult) {
        // Validation of Decorated PersonForm attributes
        if (bindingResult.hasErrors()) {
            return "usr/create";
        }
        repository.save(user);
        repository.addRoleToUsr(user.getEmail(), "ROLE_STUDENT");
        // Redirect to next step
        return "redirect:/mvc/usr/read";
    }

    @GetMapping("/update/{id}")
    public String userUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("usr", repository.get(id));
        return "usr/update";
    }

    @PostMapping("/update")
    public String userUpdateSave(@Valid Usr user, BindingResult bindingResult) {
        // Validation of Decorated UserForm attributes
        if (bindingResult.hasErrors()) {
            return "usr/update";
        }
        repository.save(user);
        repository.addRoleToUsr(user.getEmail(), "ROLE_STUDENT");

        // Redirect to next step
        return "redirect:/mvc/usr/read";
    }

    @GetMapping("/delete/{id}")
    public String userDelete(@PathVariable("id") long id) {
        repository.delete(id);
        return "redirect:/mvc/usr/read";
    }

    @GetMapping("/search")
    public String user() {
        return "usr/search";
    }

}
