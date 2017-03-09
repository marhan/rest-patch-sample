package de.marhan.controller;

import de.marhan.resource.PersonResource;
import de.marhan.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/merge")
public class JsonPatchController {

    private PersonService service;

    @Autowired
    public JsonPatchController(PersonService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonResource> list() {
        return service.getPersons();
    }
}
