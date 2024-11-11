package org.launchcode.codingevents.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class EventCategory extends AbstractEntity {

//    @Id
//    @GeneratedValue
//    private int id;

    @NotBlank
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;
    @OneToMany(mappedBy = "eventCategory")
    private final List<Event> events = new ArrayList<>();
    public EventCategory(@Size(min = 3, message = "Name must be at least 3 characters long") String name) {
        this.name = name;
    }

    public EventCategory() {
    }

//    public int getId() {
//        return id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return name;
    }
}

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        EventCategory that = (EventCategory) o;
//        return id == that.id;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//}

//EventCategory needs to have the following:

//An id field of type int.
//A name field of type String.
//A constructor.
//The appropriate getters and setters.
//EventCategory represents data that will be stored in our database, so you need to use the @Entity annotation!