package de.marhan.patch.adapter.rest.v3;

import com.github.fge.jsonpatch.JsonPatchException;
import de.marhan.patch.adapter.rest.common.JsonPatcher;
import de.marhan.patch.adapter.rest.common.RestMediaType;
import de.marhan.patch.adapter.rest.resource.PersonResource;
import de.marhan.patch.adapter.rest.resource.ResourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class JsonPatchV3Controller {

    private JsonPatcher jsonPatcher;


    @Autowired
    public JsonPatchV3Controller(JsonPatcher jsonPatcher) {
        this.jsonPatcher = jsonPatcher;
    }


    @RequestMapping(
            value = "/v3/persons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> get(@PathVariable Long id) {
        return new ResponseEntity<>(new ResourceBuilder().build(), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/v3/persons/{id}",
            method = RequestMethod.PATCH,
            consumes = RestMediaType.APPLICATION_PATCH_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> updatePartial(@PathVariable Integer id, @RequestBody String updateResource) {
        PersonResource resource = new ResourceBuilder().build();

        try {
            Optional<PersonResource> patched = jsonPatcher.patch(updateResource, resource);
            return new ResponseEntity<>(patched.get(), HttpStatus.OK);
        } catch (RuntimeException e) {
            if (JsonPatchException.class.isAssignableFrom(e.getCause().getClass())) {
                return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
