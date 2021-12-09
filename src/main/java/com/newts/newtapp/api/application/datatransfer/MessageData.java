package com.newts.newtapp.api.application.datatransfer;


import com.newts.newtapp.entities.Message;

public class MessageData {
    public final int id;
    public final String body;
    public final int author;
    public final String writtenAt;
    public final String lastUpdatedAt;

    public MessageData(Message message) {
        this.id = message.getId();
        this.body = message.getBody();
        this.author = message.getAuthor();
        this.writtenAt = message.getWrittenAt();
        this.lastUpdatedAt = message.getLastUpdatedAt();
    }
}
