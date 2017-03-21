package de.marhan.patch.controller.v3;

import de.marhan.patch.controller.common.JsonPatcher;
import de.marhan.patch.controller.common.RestMediaType;
import de.marhan.patch.controller.resource.PersonResource;
import de.marhan.patch.controller.resource.ResourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class JsonPatchV3Controller {


    private JsonPatcher jsonPatcher;

    @Autowired
    public JsonPatchV3Controller(JsonPatcher jsonPatcher) {
        this.jsonPatcher = jsonPatcher;
    }

    @RequestMapping(value = "/v3/persons/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> get(@PathVariable Long id) {
        return new ResponseEntity<>(new ResourceBuilder().build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/v3/persons/{id}", method = RequestMethod.PATCH, consumes = RestMediaType.APPLICATION_PATCH_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> updatePartial(@PathVariable Integer id, @RequestBody String updateResource) {
        PersonResource resource = new ResourceBuilder().build();
        Optional<PersonResource> patched = jsonPatcher.patch(updateResource, resource);
        return new ResponseEntity<>(patched.get(), HttpStatus.OK);
    }
}
