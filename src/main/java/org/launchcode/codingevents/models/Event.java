package org.launchcode.codingevents.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
//^optionO- take out unused imports statements

//As the app is not in Production, Truncate or drop all the tables which created before running the application
@Entity
public class Event extends AbstractEntity{

//public class Event {
//    @Id
//    @GeneratedValue
//private int id;
//static counter is initialized to 1
//private static int nextId = 1; //every single object that we create has a unique id
    //we don't need to expose nextId field, that remains private
    @NotBlank(message = "name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
//    @Size(max = 500, message = "Description is too long")
//    private String description;
//    @NotBlank(message = "Email is required")
//    @Email(message = "Invalid email. Try again.")
//    private String contactEmail;
//    private EventType type;
    //No need of validations for Enums, because it is already restricted
//for top field levels we use @valid annotation

    //cascade tells hibernate to cascade every operation on an Event object down to its EventDetails sub object,
    //if we save Event object, Event Details object also should be saved and same for delete
    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private EventDetails eventDetails;
    //to populate the field we can use both constructor injection or setter injection with model binding
    @ManyToOne
    @NotNull(message = "Category is required")
    private EventCategory eventCategory;
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();



    public Event(){
//        this.id=nextId;
//        nextId++;
    }
    //public Event(String name, String description, String contactEmail, EventType type ) {
//    public Event(String name, String description, String contactEmail, EventCategory eventCategory ) {
    public Event(String name, EventCategory eventCategory ) {


        //this();
        this.name = name;
//        this.description = description;
//        this.contactEmail = contactEmail;
        this.eventCategory = eventCategory;
//        this.id=nextId;
//        nextId++;
//id was 0 - default value for int since no arg constructor was passed in renderCreateEventForm() in EventController class
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDescription() {
//        return description;
//    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

//    public String getContactEmail() {
//        return contactEmail;
//    }

//    public void setContactEmail(String contactEmail) {
//        this.contactEmail = contactEmail;
//    }

//    public EventType getType() {
//        return type;
//    }
//
//    public void setType(EventType type) {
//        this.type = type;
//    }


//    public int getId() {
//        return id;
//    }


    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return name;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Event event = (Event) o;
//        return id == event.id;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
