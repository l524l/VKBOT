package ru.mityushin.responder.commandsmodule;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import ru.mityushin.responder.checkers.CheckersBoard;
import ru.mityushin.responder.service.VkMessageSenderService;

public class Reset extends Command {
    public Reset(VkMessageSenderService messageSenderService, String name) {
        super(messageSenderService, name);
    }

    @Override
    public void exec(Message message) {
        CheckersBoard checkersBoard = CheckersBoard.getCheckersBoard();
        checkersBoard.resetBoard();

        String s = "";
        String[][] arr = checkersBoard.getBoard();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                s = s.concat(arr[i][j]);
            }
            s = s.concat("\n");
        }
        System.out.println(s);
        message.setText("Ваше поле:\n" + s);
        try {
            messageSenderService.send(message);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
