package com.hallio.tms;

import com.hallio.dms.IObject;

import java.util.LinkedList;
import java.util.List;

public class Conversation extends IObject {
    private String[] conversation;

    public Conversation(int ticketId) {
        this.id = ticketId;
        this.conversation = new String[0];
    }

    public Conversation(){
        this.id = 0;
        this.conversation = new String[0];
    }

    @Override
    protected LinkedList<String> getAttributes() {
        // each element of the conversation array is a message
        return new LinkedList<>(
                conversation == null ? List.of() : List.of(conversation)
        );
    }

    @Override
    protected String getFilePath() {
        return "";
    }

    @Override
    protected void loadFromString(List<String> list) {
        this.id = Integer.parseInt(list.get(0));
        this.conversation = list.subList(1, list.size()).toArray(new String[0]);
    }

    public void addMessage(String message) {
        String[] newConversation = new String[conversation.length + 1];
        System.arraycopy(conversation, 0, newConversation, 0, conversation.length);
        newConversation[conversation.length] = message;
        conversation = newConversation;
    }

    public String[] getConversation() {
        return conversation;
    }
}
