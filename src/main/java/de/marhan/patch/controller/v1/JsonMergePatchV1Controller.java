package de.marhan.patch.controller.v1;

import de.marhan.patch.resource.PersonResource;
import de.marhan.patch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/merge")
public class JsonMergePatchV1Controller {

    private PersonService service;

    @Autowired
    public JsonMergePatchV1Controller(PersonService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonResource> list() {
        return new ArrayList<>(service.getPersons());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> create(PersonResource resource) {
        PersonResource createdResource = service.createPersonResource(resource);
        return new ResponseEntity<>(createdResource, HttpStatus.CREATED);

    }




}
