package com.newts.newtapp.api.controllers.assemblers;

import com.newts.newtapp.api.application.datatransfer.UserProfile;
import com.newts.newtapp.api.controllers.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * A RepresentationModelAssembler for UserProfiles. Implemented to reduce duplicate code.
 */
@Component
public class UserProfileModelAssembler implements RepresentationModelAssembler<UserProfile, EntityModel<UserProfile>> {

    /**
     * @param profile   UserProfile to be contained in EntityModel
     * @return          EntityModel containing provided UserProfile and appropriate links
     */
    @Override
    public @NonNull EntityModel<UserProfile> toModel(@NonNull UserProfile profile) {
        ArrayList<Link> links = new ArrayList<>();
        try {
            // Add appropriate links from this UserProfile
            links.add(linkTo(methodOn(UserController.class).getByUsername(profile.username)).withSelfRel());
        } catch (Exception e) {
            // No exception will ever be thrown at this point in normal use of this class as an exception would have
            // been thrown earlier during application logic.
        }
        return EntityModel.of(profile, links);
    }
}
