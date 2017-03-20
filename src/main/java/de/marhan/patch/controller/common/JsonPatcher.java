package de.marhan.patch.controller.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

@Component
public class JsonPatcher {

    private final ObjectMapper mapper;

    @Autowired
    public JsonPatcher(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> Optional patch(String json, @NotNull @Valid T target) {
        JsonNode patchedNode = null;
        try {
            final JsonPatch patch = mapper.readValue(json, JsonPatch.class);
            patchedNode = patch.apply(mapper.convertValue(target, JsonNode.class));
        } catch (IOException | JsonPatchException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(mapper.convertValue(patchedNode, target.getClass()));
    }

}
