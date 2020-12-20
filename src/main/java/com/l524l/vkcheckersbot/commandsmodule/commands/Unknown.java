package com.l524l.vkcheckersbot.commandsmodule.commands;


import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.l524l.vkcheckersbot.service.VkMessageSenderService;

public class Unknown extends Command {

    public Unknown(VkMessageSenderService messageSenderService, String name) {
        super(messageSenderService, name);
    }

    @Override
    public void exec(Message message) {
        //new VKManager().sendMessage("Неизвестная команда", message.getUserId());
        message.setText("Unknown command, bro!");
        try {
            messageSenderService.send(message);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
