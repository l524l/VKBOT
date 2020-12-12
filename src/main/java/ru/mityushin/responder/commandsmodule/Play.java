package ru.mityushin.responder.commandsmodule;

import ru.mityushin.responder.checkers.CheckersBoard;
import ru.mityushin.responder.commandsmodule.Command;
import ru.mityushin.responder.dto.MessagesSendDto;
import ru.mityushin.responder.entity.MessageNewCallback;
import ru.mityushin.responder.service.VkMessageSenderService;

/**
 * @author Arthur Kupriyanov
 */
public class Play extends Command {

    public Play(String name) {
        super(name);
    }

    @Override
    public void exec(MessageNewCallback message) {
        CheckersBoard checkersBoard = CheckersBoard.getCheckersBoard();
        String s = "";
        String[][] arr = checkersBoard.getBoard();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                s = s.concat(arr[i][j]);
            }
            s = s.concat("\n");
        }
        System.out.println(s);
        VkMessageSenderService sender = new VkMessageSenderService();
        MessagesSendDto dto = MessagesSendDto.builder()
                .peerId(message.getPeerId())
                .message("Ваше поле:\n" + s)
                .groupId(message.getGroupId())
                .build();
        sender.send(dto);
    }
}
