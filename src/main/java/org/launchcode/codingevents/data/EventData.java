package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventData {
    //contains all static properties and methods

    // need a place to put events
private static final Map<Integer, Event> events = new HashMap<>();

    //get all events
    public static Collection<Event> getAll(){
        return events.values();
    }
//google search java17 javadoc Collection
    //An interface can extend another interface

    //get a single event
public static Event getById(Integer id){
    return events.get(id);
}
    //add an event
public static void add(Event event){
    events.put(event.getId(), event);
}
    //remove an event
    public static void remove(Integer id) {
        if (events.containsKey(id)) {
            events.remove(id);
        }
    }


}
