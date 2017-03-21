package de.marhan.patch.service;

import de.marhan.patch.controller.resource.PersonResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PersonService {

    private Integer sequnece = 1;

    private Map<Integer, PersonResource> resources = new HashMap<>();

    public PersonResource createPersonResource(PersonResource resource) {
        Integer id = ++sequnece;
        resource.setId(id);
        resources.put(id, resource);
        return resource;
    }

    public List<PersonResource> getPersons() {
        return new ArrayList<>(resources.values());
    }
}
