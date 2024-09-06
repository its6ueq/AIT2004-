package piece;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.Board;
import main.GamePanel;
import main.Type;

import javax.imageio.ImageIO;

public class Piece {
    Type type;
    public BufferedImage image;
    public int x, y;
    public int col, row, preCol, preRow;
    public int color;
    public Piece hittingP;
    public boolean moved, twoStepped;

    public Piece(int color, int col, int row) {
        this.color = color;
        this.row = row;
        this.col = col;
        x = getX(col);
        y = getY(row);
        preCol = col;
        preRow = row;
    }

    public BufferedImage getImage(String path) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public int getX(int col) {
        return col * Board.SQUARE_SIZE;
    }

    public int getY(int row) {
        return row * Board.SQUARE_SIZE;
    }

    public int getCol(int x) {
        return (x + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }

    public int getRow(int y) {
        return (y + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }

    public int getIndex(){
        for (int index = 0; index < GamePanel.simPieces.size(); index++){
            if (GamePanel.simPieces.get(index) == this){
                return index;
            }
        }
        return 0;
    }

    public void updatePosition() {
        x = getX(col);
        y = getY(row);
        preCol = getCol(x);
        preRow = getRow(y);
    }

    public void resetPosition(){
        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
    }

    public boolean canMove(int targetCol, int targetRow){
        return false;
    }

    public boolean isWithinBoard(int targetCol, int targetRow) {
        return targetCol >= 0 && targetCol <= 7 && targetRow >= 0 && targetRow <= 7;
    }

    public boolean isSameSquare(int targetCol, int targetRow){
        return targetCol == preCol && targetRow == preRow;
    }

    public Piece getHittingP(int targetCol, int targetRow) {
        for (Piece piece : GamePanel.simPieces) {
            if (piece.col == targetCol && piece.row == targetRow && piece != this) {
                return piece;
            }
        }
        return null;
    }

    public boolean isValidSquare(int targetCol, int targetRow) {
        hittingP = getHittingP(targetCol, targetRow);
        if (hittingP == null){
            return true;
        }
        else {
            if (hittingP.color != this.color){ //capture if diff color
                return true;
            }
            else {
                hittingP = null;
            }
        }
        return false;
    }

    public boolean pieceIsOnStraightLine(int targetCol, int targetRow) {
        //moving left
        for (int c = preCol-1; c > targetCol; c--) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.col == c && piece.row == targetRow) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        //moving right
        for (int c = preCol+1; c < targetCol; c++) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.col == c && piece.row == targetRow) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        //moving up
        for (int r = preRow-1; r > targetRow; r--) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.col == targetCol && piece.row == r) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        //moving down
        for (int r = preRow+1; r < targetRow; r++) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.col == targetCol && piece.row == r) {
                    hittingP = piece;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow) {
        if (targetRow < preRow){
            //Up left
            for (int c = preCol-1; c > targetCol; c--) {
                int diff = Math.abs(preCol - c);
                for (Piece piece : GamePanel.simPieces){
                    if (piece.col == c && piece.row == preRow - diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }
            //Up right
            for (int c = preCol+1; c < targetCol; c++) {
                int diff = Math.abs(preCol - c);
                for (Piece piece : GamePanel.simPieces){
                    if (piece.col == c && piece.row == preRow - diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }
        }

        if (targetRow > preRow){
            for (int c = preCol-1; c > targetCol; c--) {
                int diff = Math.abs(preCol - c);
                for (Piece piece : GamePanel.simPieces){
                    if (piece.col == c && piece.row == preRow + diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }

            for (int c = preCol+1; c < targetCol; c++) {
                int diff = Math.abs(preCol - c);
                for (Piece piece : GamePanel.simPieces){
                    if (piece.col == c && piece.row == preRow + diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}