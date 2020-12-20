package com.l524l.vkcheckersbot.commandsmodule.commands;

import com.l524l.vkcheckersbot.service.VkMessageSenderService;
import com.vk.api.sdk.objects.messages.Message;

public abstract class Command {
    protected final VkMessageSenderService messageSenderService;

    public final String name;

    public Command(VkMessageSenderService messageSenderService, String name){
        this.messageSenderService = messageSenderService;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    private String param;

    public abstract void exec(Message message);

    @Override
    public String toString() {
        return String.format("name: %s",this.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Command){
            if (name.equals(((Command) obj).name)){
                return true;
            }
        }
        return false;
    }
}
