package org.launchcode.codingevents.controllers;

import jakarta.validation.Valid;
import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.TagRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.launchcode.codingevents.models.EventTagDTO;
import org.launchcode.codingevents.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@RequestMapping("/events") we can leave slash inside the string or double quotes
@Controller
@RequestMapping("events/")
public class EventController {
//private static List<String> events = new ArrayList<>();
    // private static List<Event> events = new ArrayList<>();

    //Make sure Repository is available in Controller class
    //Autowired - SpringBoot should auto populate this field. Autowired uses a Spring feature called dependency injection
    //Dependency injection means creating objects of that class by spring and that object can be used anywhere using Autowired feature
    //Autowired feature enables that this field will be automatically wired up with an actual object
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private TagRepository tagRepository;


    //findAll, save, findById methods are part of the base class CrudRepository

    //The first of these steps is to move data handling out of our controller classes and into a model class.
    @GetMapping
//    public String displayAllEvents(Model model) {
    public String displayAllEvents(@RequestParam(required = false) Integer categoryId, Model model) {

        //create an empty list and add a few event names to it

        // List<String> events = new ArrayList<>();
//        events.add("Code With Pride");
//        events.add("Strange Loop");
//        events.add("Apple WWDC");
//        events.add("SpringOne Platform");
        //Add the list to the model by calling model.addAttribute("events", events)
        // model.addAttribute("events", events);
        //model.addAttribute("title", "Create Event");
        //model.addAttribute("events", EventData.getAll());
        if (categoryId == null) {
            // model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
        } else {
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Cateory ID: " + categoryId);
            } else {
                EventCategory category = result.get();
                model.addAttribute("title", "Events in category: " + category.getName());
                model.addAttribute("events", category.getEvents());
                //http://localhost:8080/events/?categoryId=702
            }
        }

        //static method is called using class name EventData
        // return the template name "events/index"
        return "events/index";
    }

    // Create and Render a Form - Text
    //lives at /events/create
    @GetMapping("create")
    public String renderCreateEventForm(Model model) {
        //public String renderCreateEventForm() {
        //model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());//empty Event object which has the information of data types of the fields
        //model.addAttribute("event", new Event());
        //It’s also allowable to pass in the Event object without a label:
        //In this case, Spring will implicitly create the label "event", which is the lowercase version of the class name.
        //Using this technique on our other form fields completes the task of binding the object to the form during rendering.
//        model.addAttribute("types", EventType.values());//Returns an array containing the constants of this enum type
//        model.addAttribute("types", eventCategoryRepository.findAll());//Returns an array containing the constants of this enum type
        model.addAttribute("categories", eventCategoryRepository.findAll());//Returns an array containing the constants of this enum type

        return "events/create";
    }

    //Add a Form Handler Method
    //lives at /events/create
    @PostMapping("create")
//public String createEvent(@RequestParam String eventName, @RequestParam String eventDescription){//request parameter name should be same as the input name of the form create.html
    public String createEvent(@ModelAttribute @Valid Event newEvent, Errors errors, Model model) {//make sure the form inputs match with the names of the fields in this Event class
//        When submitting the event creation information, rather than passing in each field used to instantiate a model,
//        we can instead pass in @ModelAttribute Event newEvent as a parameter of the controller method.
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            //model.addAttribute("errorMsg", "Bad Data");
            return "events/create";
        }
        //events.add(eventName);
        // events.add(new Event(eventName, eventDescription));
        //EventData.add(new Event(eventName, eventDescription));
        //Spring will create the newEvent object for us given the name and description values that are in the request
//        EventData.add(newEvent);
        eventRepository.save(newEvent);
//return "redirect:";//300 level http response that instructs the browser to go or redirect to a different page
        return "redirect:/create";//300 level http response that instructs the browser to go or redirect to a different page

    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
//        model.addAttribute("events", EventData.getAll());
        model.addAttribute("events", eventRepository.findAll());

        return "events/delete";
    }


    @PostMapping("delete")
    public String processDeleteEventForm(@RequestParam(required = false) int[] eventIds) {
        //eventIds name should be same as delete.html
        if (eventIds != null) {
            for (int id : eventIds) {
//        EventData.remove(id);
                eventRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("add-tag/detail/")
    public String displayEventDetails(@RequestParam Integer eventId, Model model) {
        Optional<Event> result = eventRepository.findById(eventId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID: " + eventId);
        } else {
            Event event = result.get();
            model.addAttribute("title", event.getName() + " Details");
            model.addAttribute("event", event);
            //http://localhost:8080/events/?categoryId=702
            model.addAttribute("tags", event.getTags());
        }
        return "events/detail";
    }

    //responds to /events/add-tag/?eventId=2
    //http://localhost:8080/events/add-tag/?eventId=2
    @GetMapping("add-tag/")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model) {
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        model.addAttribute("title", "Add Tag to: " + event.getName());
        model.addAttribute("tags", tagRepository.findAll());
        //model.addAttribute("event", event);
       // model.addAttribute("eventTag", new EventTagDTO());
        EventTagDTO eventTag = new EventTagDTO();
        eventTag.setEvent(event);//to set th:field value
        model.addAttribute("eventTag", eventTag);
        return "events/add-tag.html";
    }

    @PostMapping("add-tag/")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag, Errors errors, Model model) {
        if (!errors.hasErrors()) {
            Event event = eventTag.getEvent();
            Tag tag = eventTag.getTag();
            if (!event.getTags().contains(tag)) {
                event.addTag(tag);
                eventRepository.save(event);
            }
            return "redirect:detail/?eventId=" +event.getId();
        }
        return "redirect:add-tag";
    }
}
//Create and Render a Form
//@GetMapping("formTemplateName")
//public String renderFormMethodName(Model model) {
//
//    // Method code...
//
//    return "pathToTemplate";
//}

//Line 1: The string parameter for GetMapping must be the name of the form template you want to use.
//Line 2: Declare a Model object to hold data that needs to be passed to the template.
//The method code performs any data manipulation required before rendering the form. The model.addAttribute statements would be included here.
//The return string specifies the path to the template. Recall that Spring automatically adds MOST of the file path—up through .../templates. You need to add any path details that follow.
//For example, if our templates folder contains a subfolder called events that holds a template called create.html, then line 6 would be return "events/create";

//Add a Form Handler Method
//@PostMapping("formTemplateName")
//public String processFormMethodName(@RequestParam Type parameter1, Type parameter2, ...) {
//
//    // Method code...
//
//    return "redirect:templateName";
//}
//Line 1: The string parameter for PostMapping must be the name of the form template.
//Line 2: For each piece of data that needs to be retrieved from the form, declare a parameter of the appropriate type.
//@RequestParam matches the parameters to the submitted data.
// For this to work, the parameter names MUST match the name attributes used in each of the input elements.
//The method code performs any data manipulation required after the information gets submitted.
//Line 6: Generally, we want to send the user to a different page after they successfully submit a form. Instead of re-rendering the form, the return string redirects the user to a method that handles a different template.


//Which HTML attributes will a th:field attribute NOT influence?
//id