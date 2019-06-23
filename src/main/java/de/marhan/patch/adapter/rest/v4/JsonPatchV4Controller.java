package de.marhan.patch.adapter.rest.v4;

import de.marhan.patch.adapter.rest.common.RestMediaType;
import de.marhan.patch.adapter.rest.resource.PersonResource;
import de.marhan.patch.adapter.rest.resource.ResourceBuilder;
import de.marhan.patch.adapter.rest.v3.JsonPatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonPatchV4Controller {

    private JsonPatcher jsonPatcher;

    @Autowired
    public JsonPatchV4Controller(JsonPatcher jsonPatcher) {
        this.jsonPatcher = jsonPatcher;
    }

    @RequestMapping(
            value = "/v4/persons/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> get(@PathVariable Long id) {
        return new ResponseEntity<>(new ResourceBuilder().build(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/v4/persons/{id}",
            method = RequestMethod.PATCH,
            consumes = RestMediaType.APPLICATION_PATCH_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> updatePartial(@PathVariable Integer id, @RequestBody String updateResource) {

        // TODO: Put the new patch operation handling here!

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
