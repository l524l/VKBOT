package com.l524l.vkcheckersbot.commandsmodule.commands;

import com.l524l.vkcheckersbot.checkers.NewCheckersBoard;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.l524l.vkcheckersbot.service.VkMessageSenderService;

public class Play extends Command {

    public Play(VkMessageSenderService messageSenderService, String name) {
        super(messageSenderService, name);
    }

    @Override
    public void exec(Message message) {
        NewCheckersBoard checkersBoard = NewCheckersBoard.getCheckersBoard();
        try {
            messageSenderService.sendPhoto(message, checkersBoard.getBoard());
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
