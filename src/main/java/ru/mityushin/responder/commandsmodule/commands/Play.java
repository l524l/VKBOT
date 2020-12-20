package ru.mityushin.responder.commandsmodule.commands;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import ru.mityushin.responder.checkers.NewCheckersBoard;
import ru.mityushin.responder.service.VkMessageSenderService;

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
