package de.marhan.patch.controller.v1;

import de.marhan.patch.controller.common.RestMediaType;
import de.marhan.patch.controller.resource.PersonResource;
import de.marhan.patch.controller.resource.ResourceBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;


@RestController
public class JsonMergePatchV1Controller {


    @RequestMapping(value = "/v1/persons/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> get(@PathVariable Long id) {
        return new ResponseEntity<>(new ResourceBuilder().build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/persons/{id}", method = RequestMethod.PATCH, consumes = RestMediaType.APPLICATION_MERGE_PATCH_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResource> patch(@PathVariable Long id, @RequestBody PersonResource updateResource) {
        PersonResource resource = new ResourceBuilder().build();

        if (updateResource.getName() != null) {
            resource.setName(updateResource.getName());
        }

        if (!CollectionUtils.isEmpty(updateResource.getAddresses())) {
            resource.setAddresses(updateResource.getAddresses());
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
