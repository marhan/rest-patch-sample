package de.marhan.patch.controller.v3;

import de.marhan.patch.resource.PersonResource;
import de.marhan.patch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping("/api/v3/merge")
public class JsonPatchV3Controller {

    private PersonService service;

    @Autowired
    public JsonPatchV3Controller(PersonService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonResource> list() {
        return service.getPersons();
    }
}
