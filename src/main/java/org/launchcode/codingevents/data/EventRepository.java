package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Autoboxing helps converting primitive types of data to object types
@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {


    //On the fly spring creates a class like this and that class will be autowired to the @Autowired field
//public class MyRepository implements EventRepository {}
//Spring implements this Repository interface by itself
//to see the methods search CrudRepository javadoc
}