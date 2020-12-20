package com.l524l.vkcheckersbot.commandsmodule.commands;

import com.l524l.vkcheckersbot.checkers.NewCheckersBoard;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.l524l.vkcheckersbot.exceptions.BadParameterDetected;
import com.l524l.vkcheckersbot.service.VkMessageSenderService;

public class Remove extends Command {
    public Remove(VkMessageSenderService messageSenderService, String name) {
        super(messageSenderService, name);
    }

    @Override
    public void exec(Message message) {
        String param = getParam();
        NewCheckersBoard checkersBoard = NewCheckersBoard.getCheckersBoard();

        try {
            checkersBoard.removeСheckers(param);
            messageSenderService.sendPhoto(message, checkersBoard.getBoard());
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (BadParameterDetected badParameterDetected) {
            message.setText(badParameterDetected.getMessage());
            try {
                messageSenderService.send(message);
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
}
