package de.marhan.patch.controller.v2;

import de.marhan.patch.controller.common.JsonMergePatcher;
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
public class JsonMergePatchV2Controller {

    private JsonMergePatcher jsonMergePatcher;

    @Autowired
    public JsonMergePatchV2Controller(JsonMergePatcher jsonMergePatcher) {
        this.jsonMergePatcher = jsonMergePatcher;
    }

    @RequestMapping(value = "/v2/persons/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> get(@PathVariable Long id) {
        return new ResponseEntity<>(new ResourceBuilder().build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/v2/persons/{id}", method = RequestMethod.PATCH, consumes = RestMediaType.APPLICATION_MERGE_PATCH_JSON_VALUE)
    public ResponseEntity<PersonResource> patch(@PathVariable Integer id, @RequestBody String updateResource) {
        PersonResource resource = new ResourceBuilder().build();
        Optional<PersonResource> patched = jsonMergePatcher.mergePatch(updateResource, resource);
        return new ResponseEntity<>(patched.get(), HttpStatus.OK);
    }


}
