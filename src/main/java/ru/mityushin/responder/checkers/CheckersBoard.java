package ru.mityushin.responder.checkers;

import java.util.Arrays;

public class CheckersBoard {
    private static CheckersBoard checkersBoard;
    private String[][] board;

    private final String whiteFiled = "&#11036;";
    private final String BlackFiled = "&#11035;";
    private final String RedBall = "&#128308;";
    private final String BlueBall = "&#128309;";
    private final String BlueQuin = "&#128311;";
    private final String RedQuin = "&#9830;";

    //формат x_y:ch где ch может быть b, bq, r, rq, v
    private final String[][] defaultBoard = {
            {"1&#8419;","&#128309;","&#11036;","&#128309;","&#11036;","&#128309;","&#11036;","&#128309;","&#11036;"},
            {"2&#8419;","&#11036;","&#128309;","&#11036;","&#128309;","&#11036;","&#128309;","&#11036;","&#128309;"},
            {"3&#8419;","&#128309;","&#11036;","&#128309;","&#11036;","&#128309;","&#11036;","&#128309;","&#11036;"},
            {"4&#8419;","&#11036;","&#11035;","&#11036;","&#11035;","&#11036;","&#11035;","&#11036;","&#11035;"},
            {"5&#8419;","&#11035;","&#11036;","&#11035;","&#11036;","&#11035;","&#11036;","&#11035;","&#11036;"},
            {"6&#8419;","&#11036;","&#128308;","&#11036;","&#128308;","&#11036;","&#128308;","&#11036;","&#128308;"},
            {"7&#8419;","&#128308;","&#11036;","&#128308;","&#11036;","&#128308;","&#11036;","&#128308;","&#11036;"},
            {"8&#8419;","&#11036;","&#128308;","&#11036;","&#128308;","&#11036;","&#128308;","&#11036;","&#128308;"},
    };

    private CheckersBoard(){}

    public static CheckersBoard getCheckersBoard(){
        if (checkersBoard == null) {
            checkersBoard = new CheckersBoard();
            checkersBoard.resetBoard();
        }
        return checkersBoard;
    }
    public String[][] getBoard() {
        return board;
    }
    public void resetBoard() {
        board = new String[defaultBoard.length][];
        for (int i = 0; i < this.defaultBoard.length; i++) {
            //board[i] = new String[defaultBoard[i].length];
            board[i] = Arrays.copyOf(defaultBoard[i], defaultBoard.length);
        }
        //this.board = Arrays.copyOf(this.defaultBoard, this.defaultBoard.length);
        System.out.println();
    }

    public void removeСheckers(String command){
        String[] removePositions = command.split(";");
        for (int i = 0; i < removePositions.length; i++) {
            String position = removePositions[i];
            int x = Character.getNumericValue(position.charAt(0));
            int y = Character.getNumericValue(position.charAt(1));
            if (board[y][x].equals(RedBall)
                    || board[y][x].equals(BlueBall)
                    || board[y][x].equals(BlueQuin)
                    || board[y][x].equals(RedQuin))
            {
                board[y][x] = BlackFiled;
            }
        }


    }
    public void moveСhecker(String command){
        int XFrom = Character.getNumericValue(command.charAt(0));
        int YFrom = Character.getNumericValue(command.charAt(1));
        int XTo = Character.getNumericValue(command.charAt(4));
        int YTo = Character.getNumericValue(command.charAt(5));
        String from = board[YFrom][XFrom];
        String to = board[YTo][XTo];
        if ((from.equals(RedBall) || from.equals(BlueBall)) && to.equals(BlackFiled)){
            if (YTo == 1 && from.equals(RedBall)){
                board[YFrom][XFrom] = BlackFiled;
                board[YTo][XTo] = RedQuin;
            } else if (YTo == 8 && from.equals(BlueBall)){
                board[YFrom][XFrom] = BlackFiled;
                board[YTo][XTo] = BlueQuin;
            } else {
                board[YFrom][XFrom] = BlackFiled;
                board[YTo][XTo] = from;
            }
        }
    }



}
