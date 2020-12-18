package ru.mityushin.responder.commandsmodule;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import ru.mityushin.responder.checkers.NewCheckersBoard;
import ru.mityushin.responder.service.VkMessageSenderService;

public class Move extends Command {
    public Move(VkMessageSenderService messageSenderService, String name) {
        super(messageSenderService, name);
    }

    @Override
    public void exec(Message message) {
        String param = getParam();
        NewCheckersBoard checkersBoard = NewCheckersBoard.getCheckersBoard();
        checkersBoard.moveСhecker(param);
        try {
            messageSenderService.sendPhoto(message, checkersBoard.getBoard());
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
