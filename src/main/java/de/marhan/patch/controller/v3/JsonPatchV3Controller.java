package de.marhan.patch.controller.v3;

import de.marhan.patch.controller.common.JsonPatcher;
import de.marhan.patch.controller.common.RestMediaType;
import de.marhan.patch.controller.resource.PersonResource;
import de.marhan.patch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class JsonPatchV3Controller {

    private PersonService service;

    private JsonPatcher jsonPatcher;

    @Autowired
    public JsonPatchV3Controller(PersonService service, JsonPatcher jsonPatcher) {
        this.service = service;
        this.jsonPatcher = jsonPatcher;
    }

    @RequestMapping(
            value = "/v3/persons/{id}",
            method = RequestMethod.PATCH,
            consumes = RestMediaType.APPLICATION_PATCH_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updatePartial(@PathVariable Integer id, @RequestBody String resource) {
        PersonResource target = new PersonResource();
        target.setId(id);

        Optional<PersonResource> patched = jsonPatcher.patch(resource, target);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
