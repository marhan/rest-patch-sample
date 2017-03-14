package de.marhan.patch.controller.v1;

import de.marhan.patch.resource.PersonResource;
import de.marhan.patch.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;



@RestController
@Transactional
public class JsonMergePatchV1Controller {

	private PersonService service;


	@Autowired
	public JsonMergePatchV1Controller(PersonService service) {
		this.service = service;
	}


	@RequestMapping(
			value = "/v1/persons",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<List<PersonResource>> getAll() {
		PersonResource personResource = new PersonResource();
		personResource.setName("test name");
		personResource.setId(1);
		return new ResponseEntity<>(Arrays.asList(personResource), HttpStatus.OK);
	}

	/*
	 * @RequestMapping(
	 * method = RequestMethod.POST,
	 * consumes = MediaType.APPLICATION_JSON_VALUE)
	 * @ResponseStatus(HttpStatus.CREATED)
	 * public ResponseEntity<PersonResource> create(@RequestBody PersonResource
	 * resource) {
	 * PersonResource createdResource = service.createPersonResource(resource);
	 * return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
	 * }
	 */
}
