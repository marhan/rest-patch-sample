package de.marhan.patch.controller.resource;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

public class PersonResource {

    private Integer id;

    private String name;

    private List<AddressResource> addresses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AddressResource> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResource> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
