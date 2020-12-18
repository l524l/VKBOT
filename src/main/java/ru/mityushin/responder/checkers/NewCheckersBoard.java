package ru.mityushin.responder.checkers;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class NewCheckersBoard {
    private static NewCheckersBoard checkersBoard;
    private File mainField;
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
        currentBoardState = new String[DEFAULT_BOARD_STATE.length][];
        for (int i = 0; i < this.DEFAULT_BOARD_STATE.length; i++) {
            currentBoardState[i] = Arrays.copyOf(DEFAULT_BOARD_STATE[i], DEFAULT_BOARD_STATE.length);
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

}
