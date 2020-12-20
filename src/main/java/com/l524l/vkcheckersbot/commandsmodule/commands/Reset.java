package com.l524l.vkcheckersbot.commandsmodule.commands;

import com.l524l.vkcheckersbot.checkers.NewCheckersBoard;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.l524l.vkcheckersbot.service.VkMessageSenderService;
import java.io.IOException;

public class Reset extends Command {
    public Reset(VkMessageSenderService messageSenderService, String name) {
        super(messageSenderService, name);
    }

    @Override
    public void exec(Message message) {
        String param = getParam();
        NewCheckersBoard checkersBoard = NewCheckersBoard.getCheckersBoard();

        try {
            checkersBoard.resetBoard();
            messageSenderService.sendPhoto(message, checkersBoard.getBoard());
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
