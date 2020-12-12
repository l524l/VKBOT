package ru.mityushin.responder.commandsmodule;

import ru.mityushin.responder.checkers.CheckersBoard;
import ru.mityushin.responder.dto.MessagesSendDto;
import ru.mityushin.responder.entity.MessageNewCallback;

/**
 * @author Arthur Kupriyanov
 */
public class Play extends Command {

    public Play(String name) {
        super(name);
    }

    @Override
    public String exec(String message) {
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
        return s;
    }
}
