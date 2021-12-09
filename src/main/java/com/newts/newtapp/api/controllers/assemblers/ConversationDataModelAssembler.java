package com.newts.newtapp.api.controllers.assemblers;

import com.newts.newtapp.api.application.datatransfer.ConversationData;
import com.newts.newtapp.api.controllers.ConversationController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * A RepresentationModelAssembler for ConversationData. Implemented to reduce duplicate code.
 */
@Component
public class ConversationDataModelAssembler implements RepresentationModelAssembler<ConversationData,
        EntityModel<ConversationData>> {

    /**
     * @param data      ConversationData to be contained in EntityModel
     * @return          EntityModel containing provided ConversationData and appropriate links
     */
    @Override
    public @NonNull EntityModel<ConversationData> toModel(@NonNull ConversationData data) {
        ArrayList<Link> links = new ArrayList<>();
        try {
            // Add appropriate links from this ConversationData
            links.add(linkTo(methodOn(ConversationController.class).getData(data.id)).withSelfRel());
        } catch (Exception e) {
            // No exception will ever be thrown at this point in normal use of this class as an exception would have
            // been thrown earlier during application logic.
        }
        return EntityModel.of(data, links);
    }
}