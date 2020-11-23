import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

/*
	This class can be used as a starting point for creating your Chess game project. The only piece that 
	has been coded is a white pawn...a lot done, more to do!
*/

public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {

    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;

    int xAdjustment;
    int yAdjustment;
    int startX; // Column
    int startY; // Row
    int initialX;
    int initialY;

    private int landingX;
    private int landingY;
    private int xMovement;
    private int yMovement;

    private boolean validMove;
    private boolean success;

    JPanel panels;
    JLabel pieces;

    private String winner; // Remove
    Boolean progression; // Remove

    boolean movement;
    Boolean whiteMove;

    MouseEvent currentEvent;

    private String pieceName;

    public ChessProject() {

        Dimension boardSize = new Dimension(600, 600);
        whiteMove = true;

        this.setTitle("Whites Turn");
        // Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        // Add a chess board to the Layered Pane
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            chessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground(i % 2 == 0 ? Color.white : Color.gray);
            else
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
        }

        // Setting up the Initial Chess board.
        // White pieces setup
        for (int i = 8; i < 16; i++) {
            pieces = new JLabel(new ImageIcon("WhitePawn.png"));
            panels = (JPanel) chessBoard.getComponent(i);
            panels.add(pieces);
        }
        pieces = new JLabel(new ImageIcon("WhiteRook.png"));
        panels = (JPanel) chessBoard.getComponent(0);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
        panels = (JPanel) chessBoard.getComponent(1);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
        panels = (JPanel) chessBoard.getComponent(6);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteBishup.png"));
        panels = (JPanel) chessBoard.getComponent(2);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteBishup.png"));
        panels = (JPanel) chessBoard.getComponent(5);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteKing.png"));
        panels = (JPanel) chessBoard.getComponent(3);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
        panels = (JPanel) chessBoard.getComponent(4);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteRook.png"));
        panels = (JPanel) chessBoard.getComponent(7);
        panels.add(pieces);

        // Black pieces setup
        for (int i = 48; i < 56; i++) {
            pieces = new JLabel(new ImageIcon("BlackPawn.png"));
            panels = (JPanel) chessBoard.getComponent(i);
            panels.add(pieces);
        }
        pieces = new JLabel(new ImageIcon("BlackRook.png"));
        panels = (JPanel) chessBoard.getComponent(56);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackKnight.png"));
        panels = (JPanel) chessBoard.getComponent(57);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackKnight.png"));
        panels = (JPanel) chessBoard.getComponent(62);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackBishup.png"));
        panels = (JPanel) chessBoard.getComponent(58);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackBishup.png"));
        panels = (JPanel) chessBoard.getComponent(61);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackKing.png"));
        panels = (JPanel) chessBoard.getComponent(59);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackQueen.png"));
        panels = (JPanel) chessBoard.getComponent(60);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackRook.png"));
        panels = (JPanel) chessBoard.getComponent(63);
        panels.add(pieces);
    }

    /*
     * This method checks if there is a piece present on a particular square.
     */
    private Boolean piecePresent(int x, int y) {
        Component c = chessBoard.findComponentAt(x, y);
        if (c instanceof JPanel) {
            return false;
        } else {
            return true;
        }
    }

    private Boolean piecePresent() {
        return piecePresent(currentEvent.getX(), currentEvent.getY());
    }

    /*
     * This is a method to check if a piece is a Black piece.
     */
    // private Boolean checkWhiteOponent(int newX, int newY){
    // Boolean oponent;
    // Component c1 = chessBoard.findComponentAt(newX, newY);
    // JLabel awaitingPiece = (JLabel)c1;
    // String tmp1 = awaitingPiece.getIcon().toString();
    // if(((tmp1.contains("Black")))){
    // oponent = true;
    // }
    // else{
    // oponent = false;
    // }
    // return oponent;
    // }

    // /*
    // This is a method to check if a piece is a white piece.
    // */
    // private Boolean checkBlackOponent(int newX, int newY) {
    // Boolean oponent;
    // Component c1 = chessBoard.findComponentAt(newX, newY);
    // JLabel awaitingPiece = (JLabel) c1;
    // String tmp1 = awaitingPiece.getIcon().toString();
    // if (((tmp1.contains("White")))) {
    // oponent = true;
    // } else {
    // oponent = false;
    // }
    // return oponent;
    // }

    private Boolean checkWhiteOponent(int newX, int newY) {
        return checkOpponentIs("Black", newX, newY);
    }

    private Boolean checkBlackOponent(int newX, int newY) {
        return checkOpponentIs("White", newX, newY);
    }

    private boolean checkOpponentIs(String colour, int newX, int newY) {
        Boolean oponent;
        Component c1 = chessBoard.findComponentAt(newX, newY);
        JLabel awaitingPiece = (JLabel) c1;
        String tmp1 = awaitingPiece.getIcon().toString();
        if (((tmp1.contains(colour)))) {
            oponent = true;
        } else {
            oponent = false;
        }
        return oponent;
    }

    /*
     * This method is called when we press the Mouse. So we need to find out what
     * piece we have selected. We may also not have selected a piece!
     */
    public void mousePressed(MouseEvent e) {
        chessPiece = null;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel)
            return;

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX(); // Gets the adjustment value for x
        yAdjustment = parentLocation.y - e.getY(); // Gets the adjustment value for y
        chessPiece = (JLabel) c;

        String tmp = chessPiece.getIcon().toString();
        pieceName = new File(tmp).getName();
        pieceName = pieceName.substring(0, (pieceName.length() - 4));

        if (whiteMove) {
            if (!pieceName.contains("White")) {
                JOptionPane.showMessageDialog(null, "It is whites turn");
            }
        } else {
            if (!pieceName.contains("Black")) {
                JOptionPane.showMessageDialog(null, "It is blacks turn");
                return;
            }
        }

        initialX = e.getX();
        initialY = e.getY();
        startX = (e.getX() / 75); // If there is any movement for xAdjustment, it will get startX value
        startY = (e.getY() / 75); // If there is any movement for yAdjustment, it will get startY value

        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }

    public void mouseDragged(MouseEvent me) {
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    /*
     * This method is used when the Mouse is released...we need to make sure the
     * move was valid before putting the piece back on the board.
     */
    public void mouseReleased(MouseEvent e) {
        currentEvent = e;
        if (chessPiece == null) return;

        whiteMove = !whiteMove;

        chessPiece.setVisible(false);
        Boolean success = false;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        String tmp = chessPiece.getIcon().toString();
        pieceName = new File(tmp).getName();
        String pieceName = tmp.substring(0, (tmp.length() - 4));
        Boolean validMove = false;

        // Terminal output
        int landingY = e.getY() / 75;
        int landingX = e.getX() / 75;

        int xMovement = Math.abs((e.getX() / 75) - startX);
        int yMovement = Math.abs((e.getY() / 75) - startY);
        System.out.println("----------------------------------------------");
        System.out.println("The piece that is being moved is : " + pieceName);
        System.out.println("The starting coordinates are : " + "( " + startX + "," + startY + ")");
        System.out.println("The xMovement is : " + xMovement);
        System.out.println("The yMovement is : " + yMovement);
        System.out.println("The landing coordinates are : " + "( " + landingX + "," + landingY + ")");
        System.out.println("----------------------------------------------");

        Boolean possible = false;
        String title = whiteMove ? "Whites Turn" : "Blacks Turn";
        this.setTitle(title);

        /*
         * The only piece that has been enabled to move is a White Pawn...but we should
         * really have this is a separate method somewhere...how would this work.
         * 
         * So a Pawn is able to move two squares forward one its first go but only one
         * square after that. The Pawn is the only piece that cannot move backwards in
         * chess...so be careful when committing a pawn forward. A Pawn is able to take
         * any of the opponent’s pieces but they have to be one square forward and one
         * square over, i.e. in a diagonal direction from the Pawns original position.
         * If a Pawn makes it to the top of the other side, the Pawn can turn into any
         * other piece, for demonstration purposes the Pawn here turns into a Queen.
         */

        /**************
         * WHITE PAWN
         **************/

        /*
            ATTEMPT 1!!!!
        */
        if (pieceName.equals("WhitePawn")) {
            if (startY == 1) { // Row 2
                // Pawn at starting position
                if ((startX == (e.getX() / 75))
                        // If starting position is in the same row as where it was released and was
                        // moved one or two steps ahead
                        && ((((e.getY() / 75) - startY) == 1) || ((e.getY() / 75) - startY) == 2)) {
                    if ((((e.getY() / 75) - startY) == 2)) { // If it was moved two squares
                        if ((!piecePresent(e.getX(), (e.getY()))) && (!piecePresent(e.getX(), (e.getY() - 75)))) { // Ensures there was no piece either one or two squares ahead
                            validMove = true; // Move piece
                        } else {
                            validMove = false; // Dont move piece
                        }
                    } else { // Only moved one square
                        if ((!piecePresent(e.getX(), (e.getY())))) { // If there is not a piece
                            // present where mouse was released
                            validMove = true; // Move piece
                        } else {
                            validMove = false; // Dont move piece
                        }
                    }
                } else {
                    validMove = false; // If we try to move it horizantally and not one or two places, we dont move
                }
                // End of pawn movements after moving from starting position
            } else { // If not at row 2
                // Pawn is at a different position than it began at the start of the game
                int newY = e.getY() / 75; // Get a new X - Not in starting position so need new coordinates
                int newX = e.getX() / 75; // Get a new Y - Not in starting position so need new coordinates
                if ((startX - 1 >= 0) || (startX + 1 <= 7)) { // If piece is still on board
                    // Taking an opponents piece
                    if ((piecePresent(e.getX(), (e.getY()))) && ((((newX == (startX + 1) && (startX + 1 <= 7)))
                            || ((newX == (startX - 1)) && (startX - 1 >= 0))))) { // If there is a piece present AND
                                                                                  // moving it within the limitations of
                                                                                  // the board
                        if (checkWhiteOponent(e.getX(), e.getY())) { // If there is a black piece
                            validMove = true; // We can move and take piece
                            if (startY == 6) { // If startY is 6, we then move it to 7 (End of board)
                                success = true; // At the end of the board and can transform into another piece, i.e. queen
                            }
                        } else {
                            validMove = false;
                        }
                    } else {
                        // Moving without taking a piece
                        if (!piecePresent(e.getX(), (e.getY()))) { // If there is not a piece present
                            if ((startX == (e.getX() / 75)) && ((e.getY() / 75) - startY) == 1) {
                                if (startY == 6) {
                                    success = true;
                                }
                                validMove = true;
                            } else {
                                validMove = false;
                            }
                        } else {
                            validMove = false;
                        }
                    }
                } else {
                    // Tried to move piece out of the board and does not move piece
                    validMove = false;
                }
                // End of pawn movement
            }
        }

            /*
            ATTEMPT 2!!!!
            */

            // if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            // validMove = false;
            // return;
            // }
            // Boolean whitePawn = pieceName.contains("White");
            // if (whitePawn) {
            // if (landingY < startY) {
            // validMove = false;
            // return;
            // }
            // } else {
            // if (landingY > startY) {
            // validMove = false;
            // return;
            // }
            // }
            // Boolean startCondition = whitePawn ? startY == 1 : startY == 6;
            // Boolean directionCondition = whitePawn ? startY < landingY : startY >
            // landingY;
            // // On the first move, pawn can move 2 spaces.
            // // Pawn can only move forward (White = Y+, Black = Y-)
            // // Pawn can also take an opponent on his first move.
            // if (startCondition) {
            // if ((yMovement == 1 || yMovement == 2) && directionCondition && xMovement ==
            // 0) {
            // if (yMovement == 2) {
            // if ((!piecePresent(currentEvent.getX(), (currentEvent.getY())))
            // && (!piecePresent(currentEvent.getX(), (currentEvent.getY() + 75)))) {
            // validMove = true;
            // } else {
            // validMove = false;
            // }
            // } else {
            // if ((!piecePresent(currentEvent.getX(), (currentEvent.getY())))) {
            // validMove = true;
            // } else {
            // validMove = false;
            // }
            // }
            // } else if (xMovement == 1 && yMovement == 1) {
            // // Diagonal, trying to take opponent. Check if opponent is there.
            // if (piecePresent(currentEvent.getX(), currentEvent.getY()) && (xMovement ==
            // 1)
            // && (yMovement == 1)) {
            // // If opponent is King, its over!
            // if (isGameOver(currentEvent.getX(), currentEvent.getY())) {
            // String winMessage = whitePawn ? "Game Over - White Wins!!" : "Game Over -
            // Black Wins!!";
            // JOptionPane.showMessageDialog(null, winMessage);
            // System.exit(1);
            // }
            // Boolean opponentCondition = whitePawn
            // ? checkWhiteOponent(currentEvent.getX(), currentEvent.getY())
            // : checkBlackOponent(currentEvent.getX(), currentEvent.getY());
            // if (opponentCondition) {
            // validMove = true;
            // } else {
            // validMove = false;
            // }
            // } else {
            // validMove = false;
            // }
            // }
            // } else {
            // Boolean p2StartCondition = whitePawn ? (startX - 1 >= 0) || (startX + 1 <= 7)
            // : (startX <= 7) || (startX - 1 == 0);
            // if (p2StartCondition) {
            // // Enforce that movement is diagonal, 1 square AND opponent piece is present
            // if (piecePresent(currentEvent.getX(), currentEvent.getY()) && (xMovement ==
            // 1)
            // && (yMovement == 1)) {
            // if (isGameOver(currentEvent.getX(), currentEvent.getY())) {
            // String winMessage = whitePawn ? "Game Over - White Wins!!" : "Game Over -
            // Black Wins!!";
            // JOptionPane.showMessageDialog(null, winMessage);
            // System.exit(1);
            // }
            // Boolean opponentCondition = whitePawn
            // ? checkWhiteOponent(currentEvent.getX(), currentEvent.getY())
            // : checkBlackOponent(currentEvent.getX(), currentEvent.getY());
            // if (opponentCondition) {
            // validMove = true;
            // } else {
            // validMove = false;
            // }
            // } else {
            // // Normal move, no piece present, movement only 1 square in the Y direction.
            // if (!piecePresent(currentEvent.getX(), (currentEvent.getY()))) {
            // if (xMovement == 0 && yMovement == 1) {
            // Boolean successStartCondition = whitePawn == true ? startY == 6 : startY ==
            // 1;
            // if (successStartCondition) {
            // success = true;
            // }
            // validMove = true;
            // } else {
            // validMove = false;
            // }
            // } else {
            // validMove = false;
            // }
            // }
            // } else {
            // validMove = false;
            // }
            // }
        // }    
    
        /**************
         * BLACK PAWN
         **************/

        /*
        ATTEMPT 1!!!
        */
        else if (pieceName.equals("BlackPawn")) {
            if (startY == 6) { // Row 6
                if ((startX == (e.getX() / 75))
                        && ((((e.getY() / 75) - startY) == -1) || ((e.getY() / 75) - startY) == -2)) {
                    if ((((e.getY() / 75) - startY) == -2)) {
                        if ((!piecePresent(e.getX(), (e.getY()))) && (!piecePresent(e.getX(), (e.getY() + 75)))) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        if ((!piecePresent(e.getX(), (e.getY())))) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    }
                } else {
                    validMove = false;
                }
            } else {
                int newY = e.getY() / 75;
                int newX = e.getX() / 75;
                if ((startX - 1 >= 0) || (startX + 1 <= 7)) {
                    if ((piecePresent(e.getX(), (e.getY()))) && ((((newX == (startX + 1) && (startX + 1 <= 7)))
                            || ((newX == (startX - 1)) && (startX - 1 >= 0))))) {
                        if (checkBlackOponent(e.getX(), e.getY())) {
                            validMove = true;
                            if (startY == 1) {
                                success = true;
                            }
                        } else {
                            validMove = false;
                        }
                    } else {
                        if (!piecePresent(e.getX(), (e.getY()))) {
                            if ((startX == (e.getX() / 75)) && ((e.getY() / 75) - startY) == -1) {
                                if (startY == 2) {
                                    success = true;
                                }
                                validMove = true;
                            } else {
                                validMove = false;
                            }
                        } else {
                            validMove = false;
                        }
                    }
                } else {
                    validMove = false;
                }
            }
        }


        /*
        ATTEMPT 2!!!
        */
            // if ((landingX < 0 || landingX > 7) || (landingY < 0 || landingY > 7)) {
            // validMove = false;
            // return;
            // }
            // Boolean blackPawn = pieceName.contains("White");
            // if (blackPawn) {
            // if (landingY < startY) {
            // validMove = false;
            // return;
            // }
            // } else {
            // if (landingY > startY) {
            // validMove = false;
            // return;
            // }
            // }
            // Boolean startCondition = blackPawn ? startY == 1 : startY == 6;
            // Boolean directionCondition = blackPawn ? startY < landingY : startY >
            // landingY;
            // // On the first move, pawn can move 2 spaces.
            // // Pawn can only move forward (White = Y+, Black = Y-)
            // // Pawn can also take an opponent on his first move.
            // if (startCondition) {
            // if ((yMovement == 1 || yMovement == 2) && directionCondition && xMovement ==
            // 0) {
            // if (yMovement == 2) {
            // if ((!piecePresent(currentEvent.getX(), (currentEvent.getY())))
            // && (!piecePresent(currentEvent.getX(), (currentEvent.getY() + 75)))) {
            // validMove = true;
            // } else {
            // validMove = false;
            // }
            // } else {
            // if ((!piecePresent(currentEvent.getX(), (currentEvent.getY())))) {
            // validMove = true;
            // } else {
            // validMove = false;
            // }
            // }
            // } else if (xMovement == 1 && yMovement == 1) {
            // // Diagonal, trying to take opponent. Check if opponent is there.
            // if (piecePresent(currentEvent.getX(), currentEvent.getY()) && (xMovement ==
            // 1)
            // && (yMovement == 1)) {
            // // If opponent is King, its over!
            // if (isGameOver(currentEvent.getX(), currentEvent.getY())) {
            // String winMessage = blackPawn ? "Game Over - White Wins!!" : "Game Over -
            // Black Wins!!";
            // JOptionPane.showMessageDialog(null, winMessage);
            // System.exit(1);
            // }
            // Boolean opponentCondition = blackPawn
            // ? checkWhiteOponent(currentEvent.getX(), currentEvent.getY())
            // : checkBlackOponent(currentEvent.getX(), currentEvent.getY());
            // if (opponentCondition) {
            // validMove = true;
            // } else {
            // validMove = false;
            // }
            // } else {
            // validMove = false;
            // }
            // }
            // } else {
            // Boolean p2StartCondition = blackPawn ? (startX - 1 >= 0) || (startX + 1 <= 7)
            // : (startX <= 7) || (startX - 1 == 0);
            // if (p2StartCondition) {
            // // Enforce that movement is diagonal, 1 square AND opponent piece is present
            // if (piecePresent(currentEvent.getX(), currentEvent.getY()) && (xMovement ==
            // 1)
            // && (yMovement == 1)) {
            // if (isGameOver(currentEvent.getX(), currentEvent.getY())) {
            // String winMessage = blackPawn ? "Game Over - White Wins!!" : "Game Over -
            // Black Wins!!";
            // JOptionPane.showMessageDialog(null, winMessage);
            // System.exit(1);
            // }
            // Boolean opponentCondition = blackPawn
            // ? checkWhiteOponent(currentEvent.getX(), currentEvent.getY())
            // : checkBlackOponent(currentEvent.getX(), currentEvent.getY());
            // if (opponentCondition) {
            // validMove = true;
            // } else {
            // validMove = false;
            // }
            // } else {
            // // Normal move, no piece present, movement only 1 square in the Y direction.
            // if (!piecePresent(currentEvent.getX(), (currentEvent.getY()))) {
            // if (xMovement == 0 && yMovement == 1) {
            // Boolean successStartCondition = blackPawn == true ? startY == 6 : startY ==
            // 1;
            // if (successStartCondition) {
            // success = true;
            // }
            // validMove = true;
            // } else {
            // validMove = false;
            // }
            // } else {
            // validMove = false;
            // }
            // }
            // } else {
            // validMove = false;
            // }
            // }

        // }


        /**************
         * KNIGHT
         **************/

        /*
        ATTEMPT 1!!!
        */
        else if (pieceName.contains("Knight")) {

            int newY = e.getY() / 75; // Get a newY - Not in starting position so need new coordinates
            int newX = e.getX() / 75; // Get a newX - Not in starting position so need new coordinates

            // If piece is still on board
            if (((newX < 0 || newX > 7)) || ((newY < 0 || newY > 7))) { // If piece released outside the board
                validMove = false; // Dont move piece
            } else {
                // RIGHT ONE SPACE AND UP TWO SPACES OR LEFT ONE SPACE AND UP TWO SPACES OR
                // RIGHT TWO SPACES AND UP ONE SPACE
                if (((newX == startX + 1) && (newY == startY + 2)) || ((newX == startX - 1) && (newY == startY + 2))
                        || ((newX == startX + 2) && (newY == startY + 1))
                        // OR LEFT TWO SPACES AND UP ONE SPACE OR RIGHT ONE SPACE AND DOWN TWO SPACES OR
                        // LEFT ONE SPACE AND DOWN TWO SPACES
                        || ((newX == startX - 2) && (newY == startY + 1))
                        || ((newX == startX + 1) && (newY == startY - 2))
                        || ((newX == startX - 1) && (newY == startY - 2))
                        // OR RIGHT TWO SPACES AND DOWN ONE SPACE OR LEFT TWO SPACES AND DOWN ONE SPACE
                        || ((newX == startX + 2) && (newY == startY - 1))
                        || ((newX == startX - 2) && (newY == startY - 1))) {
                    validMove = true; // IF ANY OF THE ABOVE, MOVE PIECE
                    // Taking an opponents piece
                    // Checks if opponent is in the way, if so, it can move. If it is your own
                    // piece, not able to move to location
                    if (piecePresent(e.getX(), (e.getY()))) { // If piece present at where mouse was released
                        if (pieceName.contains("White")) { // And name contains 'white'
                            if (checkWhiteOponent(e.getX(), e.getY())) { // And it contains 'white'
                                validMove = true; // Can move piece
                            } else {
                                validMove = false; // Dont move piece
                            }
                        } else { // And if name contains 'black'
                            if (checkBlackOponent(e.getX(), e.getY())) { // If it is an opponent, i.e. 'white'
                                validMove = true;
                            } else {
                                validMove = false;
                            }
                        }
                    }
                } else {
                    validMove = false;
                }
            }
            completeMove();
        }


        /**************
         * BISHOP
         **************/

        /*
        ATTEMPT 1!!!
        */
        else if (pieceName.contains("Bishup")) {
            int newY = e.getY() / 75; // Get a newY - Not in starting position so need new coordinates
            int newX = e.getX() / 75; // Get a newX - Not in starting position so need new coordinates
            // Check if piece is blocking route
            boolean blocking = false;
            int distance = Math.abs(startX - newX); // Starting position minus the new position
            if (((newX < 0 || newX > 7)) || ((newY < 0 || newY > 7))) { // If piece released outside the board
                validMove = false; // Dont move piece
            } else { // If it is on board
                validMove = true; // Move piece
                // Checking if there is a piece in the way
                if (Math.abs(startX - newX) == Math.abs(startY - newY)) { // If columns to left/right is equal to rows
                                                                          // up/down, i.e piece went diagonally
                    if ((startX - newX < 0) && (startY - newY < 0)) { // If bishop went down diagonally left
                        for (int i = 0; i < distance; i++) {
                            // If there is a piece present, then it is blocked
                            if (piecePresent((initialX + (i * 75)), (initialY + (i * 75)))) {
                                blocking = true;
                            }
                        }
                    } else if ((startX - newX < 0) && (startY - newY > 0)) { // If bishop went up diaganolly left
                        for (int i = 0; i < distance; i++) {
                            // If there is a piece present, then it is blocked
                            if (piecePresent((initialX + (i * 75)), (initialY - (i * 75)))) {
                                blocking = true;
                            }
                        }
                    } else if ((startX - newX > 0) && (startY - newY > 0)) { // If bishop went up diagonally right
                        for (int i = 0; i < distance; i++) {
                            // If there is a piece present, then it is blocked
                            if (piecePresent((initialX - (i * 75)), (initialY - (i * 75)))) {
                                blocking = true;
                            }
                        }
                    } else if ((startX - newX > 0) && (startY - newY < 0)) { // If bishop went down diagonally right
                        for (int i = 0; i < distance; i++) {
                            // If there is a piece present, then it is blocked
                            if (piecePresent((initialX - (i * 75)), (initialY + (i * 75)))) {
                                blocking = true;
                            }
                        }
                    }
                    if (blocking) { // If bishop is blocked
                        validMove = false; // Dont move piece
                    } else {
                        // Checks if opponent is in the way, if so, it can move. If it is your own
                        // piece, not able to move to location
                        if (piecePresent(e.getX(), (e.getY()))) { // If piece present at where mouse was released
                            if (pieceName.contains("White")) { // And it contains 'white'
                                if (checkWhiteOponent(e.getX(), e.getY())) { // If it is an opponent, i.e. 'black'
                                    validMove = true; // Can move piece
                                } else {
                                    validMove = false; // Dont move piece
                                }
                            } else {
                                if (checkBlackOponent(e.getX(), e.getY())) { // If it is an opponent, i.e. 'white'
                                    validMove = true;
                                } else {
                                    validMove = false;
                                }
                            }
                        } else {
                            validMove = true;
                        }
                    }
                } else { // the move that is being tried is not a diagonal move...
                    validMove = false;
                }
            }
            completeMove();
        }

        /**************
         * ROOK
         **************/

        /*
        ATTEMPT 1!!!
        */
        else if (pieceName.contains("Rook")) {
            Boolean blocking = false;

            if (((landingX < 0 || landingX > 7)) || ((landingY < 0 || landingY > 7))) { // If piece released outside the board
                validMove = false; // Dont move piece
            } else {
                // MOVING LEFT/RIGHT != 0 AND MOVING UP/DOWN = 0 OR MOVING LEFT/RIGHT = 0 AND
                // MOVING UP/DOWN != 0
                // WE WENT HORIZENTALLY WE WENT VERTICALLY
                if (((Math.abs(startX - landingX) != 0) && (Math.abs(startY - landingY) == 0))
                        || ((Math.abs(startX - landingX) == 0) && (Math.abs(landingY - startY) != 0))) {
                    // PIECE MOVED HORIZENTALLY
                    if (Math.abs(startX - landingX) != 0) {
                        // int xMovement = Math.abs(startX - landingX);
                        // WENT RIGHT HORIZENTALLY
                        if (startX - landingX > 0) {
                            for (int i = 0; i < xMovement; i++) {
                                // If there is a piece present
                                if (piecePresent(initialX - (i * 75), e.getY())) {
                                    blocking = true; // Path is blocked
                                    break;
                                } else {
                                    blocking = false; // Path is not blocked
                                }
                            }
                        } else {
                            // MOVED LEFT HORIZENTALLY
                            for (int i = 0; i < xMovement; i++) {
                                if (piecePresent(initialX + (i * 75), e.getY())) {
                                    blocking = true;
                                    break;
                                } else {
                                    blocking = false;
                                }
                            }
                        }
                        // End of moving right horizentally
                    } else {
                        // int yMovement = Math.abs(startY - landingY);
                        // WENT UP VERTICALLY
                        if (startY - landingY > 0) {
                            for (int i = 0; i < yMovement; i++) {
                                if (piecePresent(e.getX(), initialY - (i * 75))) {
                                    blocking = true;
                                    break;
                                } else {
                                    blocking = false;
                                }
                            }
                        } else {
                            // WENT DOWN VERTICALLY
                            for (int i = 0; i < yMovement; i++) {
                                if (piecePresent(e.getX(), initialY + (i * 75))) {
                                    blocking = true;
                                    break;
                                } else {
                                    blocking = false;
                                }
                            }
                        }
                    }
                    if (blocking) {
                        validMove = false;
                    } else {
                        // Checks if opponent is in the way, if so, it can move. If it is your own
                        // piece, not able to move to location
                        if (piecePresent(e.getX(), (e.getY()))) {
                            if (pieceName.contains("White")) {
                                if (checkWhiteOponent(e.getX(), e.getY())) {
                                    validMove = true;
                                } else {
                                    validMove = false;
                                }
                            } else {
                                if (checkBlackOponent(e.getX(), e.getY())) {
                                    validMove = true;

                                } else {
                                    validMove = false;
                                }
                            }
                        } else {
                            validMove = true;
                        }
                    }
                } else {
                    validMove = false;
                }
            }
            completeMove();
        }

        /**************
         * KING
         **************/

        /*
        ATTEMPT 1!!!
        */
        else if (pieceName.contains("King")) {
        
            if (((landingX < 0 || landingX > 7)) || ((landingY < 0 || landingY > 7))) { // If piece released outside the
                                                                                        // board
                validMove = false;
            } else {
                // If piece moves in any direction, up or down, by one
                if ((xMovement == 1) || (yMovement == 1)) {
                    // Check if opponents or own piece is in the way
                    if (piecePresent(e.getX(), (e.getY()))) {
                        if (pieceName.contains("White")) {
                            if (checkWhiteOponent(e.getX(), e.getY())) {
                                validMove = true;
                            } else {
                                validMove = false;
                            }
                        } else {
                            if (checkBlackOponent(e.getX(), e.getY())) {
                                validMove = true;
                            } else {
                                validMove = false;
                            }
                        }
                    } else {
                        // If no piece in the way, move to free space
                        validMove = true;
                    }
                } else {
                    validMove = false;
                }
            }
            if ((checkKingAtLoc(currentEvent.getX() - 75, currentEvent.getY() + 75))
                    || (checkKingAtLoc(currentEvent.getX() - 75, currentEvent.getY()))
                    || (checkKingAtLoc(currentEvent.getX() - 75, currentEvent.getY() - 75))
                    || (checkKingAtLoc(currentEvent.getX(), currentEvent.getY() - 75))
                    || (checkKingAtLoc(currentEvent.getX() + 75, currentEvent.getY() - 75))
                    || (checkKingAtLoc(currentEvent.getX() + 75, currentEvent.getY()))
                    || (checkKingAtLoc(currentEvent.getX() + 75, currentEvent.getY() + 75))
                    || (checkKingAtLoc(currentEvent.getX(), currentEvent.getY() + 75))) {
                validMove = false;
                return;
            }
            completeMove();
        }


        /**************
         * QUEEN
         **************/

        /*
        ATTEMPT 1!!!
        */
        else if (pieceName.contains("Queen")) {
            if ((startX == landingX) || (startY == landingY)) {
                boolean blocking = false;
                // MOVED LEFT/RIGHT AND DID NOT MOVE UP/DOWM OR DID NOT MOVE LEFT/RIGHT AND
                // MOVED UP/DOWN
                // MOVED HORIZENTALLY MOVED VERTICALLY
                if (((Math.abs(startX - landingX) != 0) && (Math.abs(startY - landingY) == 0))
                        || ((Math.abs(startX - landingX) == 0) && (Math.abs(landingY - startY) != 0))) {
                    // MOVED HORIZENTALLY
                    if (Math.abs(startX - landingX) != 0) {
                        // int xMovement = Math.abs(startX - landingX);
                        // MOVED RIGHT HORIZENTALLY
                        if (startX - landingX > 0) {
                            for (int i = 0; i < xMovement; i++) {
                                if (piecePresent(initialX - (i * 75), e.getY())) {
                                    blocking = true;
                                    break;
                                } else {
                                    blocking = false;
                                }
                            }
                        } else {
                            // MOVED LEFT HORIZENTALLY
                            for (int i = 0; i < xMovement; i++) {
                                if (piecePresent(initialX + (i * 75), e.getY())) {
                                    blocking = true;
                                    break;
                                } else {
                                    blocking = false;
                                }
                            }
                        }
                    }
                    // End horizental moves
                    else {
                        // int yMovement = Math.abs(startY - landingY);
                        // MOVED UP VERTICALLY
                        if (startY - landingY > 0) {
                            for (int i = 0; i < yMovement; i++) {
                                if (piecePresent(e.getX(), initialY - (i * 75))) {
                                    blocking = true;
                                    break;
                                } else {
                                    blocking = false;
                                }
                            }
                        } else {
                            // MOVED DOWN VERTICALLY
                            for (int i = 0; i < yMovement; i++) {
                                if (piecePresent(e.getX(), initialY + (i * 75))) {
                                    blocking = true;
                                    break;
                                } else {
                                    blocking = false;
                                }
                            }
                        }
                    }
                    // End vertical moves
                    if (blocking) {
                        validMove = false;
                    } else {
                        if (piecePresent(e.getX(), (e.getY()))) {
                            if (pieceName.contains("White")) {
                                if (checkWhiteOponent(e.getX(), e.getY())) {
                                    validMove = true;
                                } else {
                                    validMove = false;
                                }
                            } else {
                                if (checkBlackOponent(e.getX(), e.getY())) {
                                    validMove = true;

                                } else {
                                    validMove = false;
                                }
                            }
                        } else {
                            validMove = true;
                        }
                    }
                } else {
                    validMove = false;
                }
            } else {
                Boolean blocking = false;
                int distance = Math.abs(startX - landingX);
                if (((landingX < 0 || landingX > 7)) || ((landingY < 0 || landingY > 7))) { // If piece released outside
                                                                                            // the board
                    validMove = false; // Dont move piece
                } else {
                    validMove = true;
                    if (Math.abs(startX - landingX) == Math.abs(startY - landingY)) { // If columns to left/right is
                                                                                      // equal to rows up/down, i.e
                                                                                      // piece went diagonally
                        if ((startX - landingX < 0) && (startY - landingY < 0)) { // If queen went down diagonally left
                            for (int i = 0; i < distance; i++) {
                                if (piecePresent((initialX + (i * 75)), (initialY + (i * 75)))) {
                                    blocking = true;
                                }
                            }
                        } else if ((startX - landingX < 0) && (startY - landingY > 0)) { // If queen went up diagonally
                                                                                         // left
                            for (int i = 0; i < distance; i++) {
                                if (piecePresent((initialX + (i * 75)), (initialY - (i * 75)))) {
                                    blocking = true;
                                }
                            }
                        } else if ((startX - landingX > 0) && (startY - landingY > 0)) { // If queen went up diagonally
                                                                                         // right
                            for (int i = 0; i < distance; i++) {
                                if (piecePresent((initialX - (i * 75)), (initialY - (i * 75)))) {
                                    blocking = true;
                                }
                            }
                        } else if ((startX - landingX > 0) && (startY - landingY < 0)) { // If queen went down
                                                                                         // diagonally right
                            for (int i = 0; i < distance; i++) {
                                if (piecePresent((initialX - (i * 75)), (initialY + (i * 75)))) {
                                    blocking = true;
                                }
                            }
                        }
                        if (blocking) {
                            validMove = false;
                        } else {
                            if (piecePresent(e.getX(), (e.getY()))) {
                                if (pieceName.contains("White")) {
                                    if (checkWhiteOponent(e.getX(), e.getY())) {
                                        validMove = true;
                                    } else {
                                        validMove = false;
                                    }
                                } else {
                                    if (checkBlackOponent(e.getX(), e.getY())) {
                                        validMove = true;
                                    } else {
                                        validMove = false;
                                    }
                                }
                            } else {
                                validMove = true;
                            }
                        }
                    } else {
                        validMove = false;
                    }
                }
            }
            completeMove();
        }

        // End of moving pieces

        if (!validMove) { // If it was not a valid
            int location = 0;
            if (startY == 0) {
                location = startX; // Place it back where it was originally
            } else {
                location = (startY * 8) + startX;
            }
            String pieceLocation = pieceName + ".png";
            pieces = new JLabel(new ImageIcon(pieceLocation));
            panels = (JPanel) chessBoard.getComponent(location);
            panels.add(pieces);
        } else {
            if (success) {
                // int location = 56 + (e.getX()/75);
                // if (c instanceof JLabel){
                // Container parent = c.getParent();
                // parent.remove(0);
                // pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
                // parent = (JPanel)chessBoard.getComponent(location);
                // parent.add(pieces);
                // }
                // else{
                // Container parent = (Container)c;
                // pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
                // parent = (JPanel)chessBoard.getComponent(location);
                // parent.add(pieces);
                // }
                if (c instanceof JLabel) {
                    Container parent = c.getParent();
                    parent.remove(0);

                    String promoteTo;
                    do {
                        promoteTo = (String) JOptionPane.showInputDialog(null, "Promote Pawn to :", "Pawn Promotion",
                                JOptionPane.QUESTION_MESSAGE, null,
                                new String[] { "Queen", "Bishup", "Knight", "Rook" }, "Queen");
                    } while (promoteTo == null);
                    String newPiece = null;
                    int location = 0; // Comment out int location = 56 to parent.add(pieces)
                    if (pieceName.contains("White")) {
                        location = 56 + (e.getX() / 75);
                        newPiece = "White" + promoteTo;
                    } else {
                        location = (e.getX() / 75);
                        newPiece = "Black" + promoteTo;
                    }

                    pieces = new JLabel(new ImageIcon(newPiece + ".png"));
                    parent = (JPanel) chessBoard.getComponent(location);
                    parent.add(pieces);
                    validate();
                    repaint();
                }
            } else {
                if (c instanceof JLabel) {
                    Container parent = c.getParent();
                    parent.remove(0);
                    parent.add(chessPiece);
                } else {
                    Container parent = (Container) c;
                    parent.add(chessPiece);
                }
                chessPiece.setVisible(true);
            }
        }
    }

    private Boolean checkKingAtLoc(int x, int y) {
        try {
            Component c1 = chessBoard.findComponentAt(x, y);
            if (c1 instanceof JPanel) {
                return false;
            }
            JLabel checkingPiece = (JLabel) c1;
            String tmp1 = checkingPiece.getIcon().toString();
            return tmp1.contains("King");
        } catch (Exception e) {
            return false;
        }
    }

    // Complete a move for all pieces except a Pawn
    private void completeMove() {
        if (piecePresent(currentEvent.getX(), currentEvent.getY())) {
            if (pieceName.contains("White")) {
                if (checkWhiteOponent(currentEvent.getX(), currentEvent.getY())) {
                    if (isGameOver(currentEvent.getX(), currentEvent.getY())) {
                        JOptionPane.showMessageDialog(null, "Game Over - White Wins!!");
                        System.exit(1);
                    }
                    validMove = true;
                } else {
                    validMove = false;
                }
            } else if (pieceName.contains("Black")) {
                if (checkBlackOponent(currentEvent.getX(), currentEvent.getY())) {
                    if (isGameOver(currentEvent.getX(), currentEvent.getY())) {
                        JOptionPane.showMessageDialog(null, "Game Over - Black Wins!!");
                        System.exit(1);
                    }
                    validMove = true;
                } else {
                    validMove = false;
                }
            }
        } else {
            validMove = true;
        }
    }

    private Boolean isGameOver(int newX, int newY) {
        Boolean kingTaken = false;
        Component c1 = chessBoard.findComponentAt(newX, newY);
        JLabel takenPiece = (JLabel) c1;
        String tmp1 = takenPiece.getIcon().toString();
        if (((tmp1.contains("King")))) {
            kingTaken = true;
        } else {
            kingTaken = false;
        }
        return kingTaken;
    }
    public void mouseClicked(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    /*
     * Main method that gets the ball moving.
     */
    public static void main(String[] args) {
        JFrame frame = new ChessProject();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
