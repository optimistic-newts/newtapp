package com.newts.newtapp.api.controllers.assemblers;

import com.newts.newtapp.api.application.datatransfer.MessageData;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * A RepresentationModelAssembler for MessageData. Implemented to reduce duplicate code.
 */
@Component
public class MessageDataModelAssembler
        implements RepresentationModelAssembler<MessageData, EntityModel<MessageData>> {

    /**
     * @param data      ConversationData to be contained in EntityModel
     * @return          EntityModel containing provided ConversationData and appropriate links
     */
    @Override
    public @NonNull EntityModel<MessageData> toModel(@NonNull MessageData data) {
        return EntityModel.of(data);
    }
}