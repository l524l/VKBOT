package ru.mityushin.responder.checkers;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;
import ru.mityushin.responder.exceptions.BadParameterDetected;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class NewCheckersBoard {
    private static NewCheckersBoard checkersBoard;
    private File mainField;
    private File backupField;
    private File redCh;
    private File blueCh;
    private File redQCh;
    private File blueQCh;


    //формат x_y:ch где ch может быть b, bq, r, rq, v. w если поле белое;
    private final String[][] DEFAULT_BOARD_STATE= {
            {"64_64:r", "w","192_64:r", "w","320_64:r", "w","448_64:r", "w"},
            {"w","128_128:r","w","256_128:r","w","384_128:r","w","512_128:r"},
            {"64_192:r","w","192_192:r","w","320_192:r","w","448_192:r","w"},
            {"w","128_256:v","w","256_256:v","w","384_256:v","w","512_256:v"},
            {"64_320:v","w","192_320:v","w","320_320:v","w","448_320:v","w"},
            {"w","128_384:b","w","256_384:b","w","348_384:b","w","512_384:b"},
            {"64_448:b","w","192_448:b","w","320_448:b","w","448_448:b","w"},
            {"w","128_512:b","w","256_512:b","w","348_512:b","w","512_512:b"},
    };
    private String[][] currentBoardState;
    private String[][] backupBoardState;

    private NewCheckersBoard(){
        try {
            redCh = loadPatternsToTempFiles("classpath:patterns/cr.png","redCh",".png");
            blueCh = loadPatternsToTempFiles("classpath:patterns/cb.png","blueCh",".png");
            blueQCh = loadPatternsToTempFiles("classpath:patterns/qb.png","blueQCh",".png");
            redQCh = loadPatternsToTempFiles("classpath:patterns/qr.png","redQCh",".png");
            this.resetBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static NewCheckersBoard getCheckersBoard() {
        if (checkersBoard == null) {
            checkersBoard = new NewCheckersBoard();
        }
        return checkersBoard;
    }
    public void resetBoard() throws IOException {
        mainField = loadPatternsToTempFiles("classpath:patterns/demo2.png","board",".png");
        backupField = loadPatternsToTempFiles("classpath:patterns/demo2.png","backup_board",".png");
        currentBoardState = new String[DEFAULT_BOARD_STATE.length][];
        backupBoardState = new String[DEFAULT_BOARD_STATE.length][];
        for (int i = 0; i < this.DEFAULT_BOARD_STATE.length; i++) {
            currentBoardState[i] = Arrays.copyOf(DEFAULT_BOARD_STATE[i], DEFAULT_BOARD_STATE.length);
            backupBoardState[i] = Arrays.copyOf(DEFAULT_BOARD_STATE[i], DEFAULT_BOARD_STATE.length);
        }
    }
    private File loadPatternsToTempFiles(String resourcePath, String tempPrefix, String tempSuffix) throws IOException {
        UrlResource resource = new UrlResource(resourcePath);
        File file = File.createTempFile(tempPrefix,tempSuffix);
        FileOutputStream out = new FileOutputStream(file);
        IOUtils.copy(resource.getInputStream(), out);
        return file;
    }
    public File getBoard(){
        return mainField;
    }
    public void moveСhecker(String command) throws BadParameterDetected {
        if (command.matches("[1-8][1-8]to[1-8][1-8]")) {
            int XFrom = Character.getNumericValue(command.charAt(0)) - 1;
            int YFrom = Character.getNumericValue(command.charAt(1)) - 1;
            int XTo = Character.getNumericValue(command.charAt(4)) - 1;
            int YTo = Character.getNumericValue(command.charAt(5)) - 1;
            String[] from = currentBoardState[YFrom][XFrom].split(":");
            String[] to = currentBoardState[YTo][XTo].split(":");
            if ((from[1].equals("r") || from[1].equals("b") || from[1].equals("bq") || from[1].equals("rq")) && to[1].equals("v")) {
                try {
                    if (YTo == 0 && from[1].equals("b")) {
                        writeMove(from[0], to[0], "bq");
                        currentBoardState[YTo][XTo] = to[0] + ":bq";
                    } else if (YTo == 7 && from[1].equals("r")) {
                        writeMove(from[0], to[0], "rq");
                        currentBoardState[YTo][XTo] = to[0] + ":rq";
                    } else {
                        writeMove(from[0], to[0], from[1]);
                        currentBoardState[YTo][XTo] = to[0] + ":" + to[1];
                    }
                    currentBoardState[YFrom][XFrom] = from[0] + ":v";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                loadBackup();
                throw new BadParameterDetected("Невозможно перемещение по указанным координатам");
            }
        } else {
            loadBackup();
            throw new BadParameterDetected("Начальные или конечные координаты содержат ошибку");
        }
        saveBackup();
    }
    private void saveBackup(){
        for (int i = 0; i < currentBoardState.length; i++) {
            backupBoardState[i] = Arrays.copyOf(currentBoardState[i], currentBoardState.length);
        }
        BufferedImage field = null;
        try {
            field = ImageIO.read(mainField);
            ImageIO.write(field, "png", backupField);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadBackup(){
        for (int i = 0; i < backupBoardState.length; i++) {
            currentBoardState[i] = Arrays.copyOf(backupBoardState[i], backupBoardState.length);
        }
        BufferedImage field = null;
        try {
            field = ImageIO.read(backupField);
            ImageIO.write(field, "png", mainField);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeСheckers(String command) throws BadParameterDetected {
        if (command.matches("[1-8][1-8]")
                || command.matches("[1-8][1-8];[1-8][1-8]")
                || command.matches("[1-8][1-8];[1-8][1-8];[1-8][1-8]")
        ) {
            String[] removePositions = command.split(";");
            for (int i = 0; i < removePositions.length; i++) {
                String position = removePositions[i];
                int x = Character.getNumericValue(position.charAt(0)) - 1;
                int y = Character.getNumericValue(position.charAt(1)) - 1;
                String[] state = currentBoardState[y][x].split(":");

                if (state[1].equals("r")
                        || state[1].equals("b")
                        || state[1].equals("bq")
                        || state[1].equals("rq")) {
                    try {
                        writeRemove(state[0]);
                        currentBoardState[y][x] = state[0] + ":v";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    loadBackup();
                    throw new BadParameterDetected("Один или несколько параметров ошибочны!");
                }
            }
        } else {
            loadBackup();
            throw new BadParameterDetected();
        }
        saveBackup();
    }

    public void writeRemove(String XY) throws IOException {
        BufferedImage field = ImageIO.read(mainField);
        Color color = new Color(0,0,0);
        String[] to = XY.split("_");
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                int x = Integer.parseInt(to[0]);
                int y = Integer.parseInt(to[1]);
                field.setRGB(x + i,y + j, color.getRGB());
            }
        }
        ImageIO.write(field, "png", mainField);
    }

    public void writeMove(String fromXY, String toXY, String pattern) throws IOException {
        BufferedImage field = ImageIO.read(mainField);
        BufferedImage patternImg = null;
        String[] to = toXY.split("_");
        switch (pattern){
            case "r":
                patternImg = ImageIO.read(redCh);
                break;
            case "b":
                patternImg = ImageIO.read(blueCh);
                break;
            case "bq":
                patternImg = ImageIO.read(blueQCh);
                break;
            case "rq":
                patternImg = ImageIO.read(redQCh);
                break;
            default:
                break;
        }
        for (int i = 0; i < patternImg.getHeight(); i++) {
            for (int j = 0; j < patternImg.getWidth(); j++) {
                Color color = new Color(patternImg.getRGB(i,j));
                int x = Integer.parseInt(to[0]);
                int y = Integer.parseInt(to[1]);
                field.setRGB(x + i, y + j, color.getRGB());
            }
        }
        ImageIO.write(field, "png", mainField);
        writeRemove(fromXY);
    }

}
