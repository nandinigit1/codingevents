package org.launchcode.codingevents.controllers;

import jakarta.validation.Valid;
import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.models.EventCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//type=Not Found, status=404
//org.springframework.web.servlet.resource.NoResourceFoundException: No static resource eventCategories.
@Controller
@RequestMapping("eventCategories/")
public class EventCategoryController {

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

@GetMapping
    public String displayAllEvents(Model model){
    model.addAttribute("title", "All Categories");
//    Add an attribute for the categories that uses all of the values in your EventCategoryRepository variable.
model.addAttribute("categories", eventCategoryRepository.findAll());
    return "eventCategories/index";
    }

    @GetMapping("create")
    public String renderCreateEventCategoryForm(Model model){
    model.addAttribute("title", "Create Category");
    model.addAttribute(new EventCategory());
//    It’s also allowable to pass in the Event object without a label:
//model.addAttribute(new EventCategory());
//In this case, Spring will implicitly create the label "eventCategory", which is the lowercase version of the class name.
    return "eventCategories/create";
    }

    //405 error:Method not allowed if path is not correct
    @PostMapping("create")
    //An Errors/BindingResult argument is expected to be declared immediately after the model attribute, the @RequestBody or the @RequestPart arguments to which they apply: public java.lang.String
    public String processCreateEventCategoryForm(@ModelAttribute @Valid EventCategory eventCategory, Errors errors, Model model){
    if(errors.hasErrors()){
        model.addAttribute("title", "Create Category");
        //return "redirect:eventCategories/index";
        model.addAttribute("errorMsg", "Bad Data");
        return "eventCategories/create";
    }
        model.addAttribute(new EventCategory());

//to add data into our database
eventCategoryRepository.save(eventCategory);
        return "redirect:";
    }
}
