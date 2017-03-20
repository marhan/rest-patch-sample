package de.marhan.patch.controller.v2;

import de.marhan.patch.controller.common.JsonMergePatcher;
import de.marhan.patch.controller.common.RestMediaType;
import de.marhan.patch.resource.PersonResource;
import de.marhan.patch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class JsonMergePatchV2Controller {

    private PersonService service;

    private JsonMergePatcher jsonMergePatcher;

    @Autowired
    public JsonMergePatchV2Controller(PersonService service, JsonMergePatcher jsonMergePatcher) {
        this.service = service;
        this.jsonMergePatcher = jsonMergePatcher;
    }


    @RequestMapping(
            value = "/v2/persons/{id}",
            method = RequestMethod.PATCH,
            consumes = RestMediaType.APPLICATION_MERGE_PATCH_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updatePartial(@PathVariable Integer id, @RequestBody String resource) {
        PersonResource target = new PersonResource();
        target.setId(id);

        Optional<PersonResource> patched = jsonMergePatcher.mergePatch(resource, target);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
