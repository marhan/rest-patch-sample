package de.marhan.patch.adapter.rest.resource;

import java.util.Arrays;

public class ResourceBuilder {

    public PersonResource build() {
        AddressResource addressResourceOne = new AddressResource();
        addressResourceOne.setStreet("Spitalerstrasse 12");
        addressResourceOne.setCity("Hamburg");

        AddressResource addressResourceTwo = new AddressResource();
        addressResourceTwo.setStreet("Boetcherstrasse 2");
        addressResourceTwo.setCity("Bremen");


        PersonResource personResource = new PersonResource();
        personResource.setId(1);
        personResource.setName("Fritz Brause");

        personResource.setAddresses(Arrays.asList(addressResourceOne, addressResourceTwo));

        return personResource;
    }
}
