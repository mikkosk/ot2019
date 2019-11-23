/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.domain;

/**
 *
 * @author Mikko
 */
public class Chess {
    private Board board;
    private boolean whitesTurn;
    private boolean checkmate;
    private boolean stalemate;
    
    public Chess() {
        board = new Board();
        whitesTurn = true;
        checkmate = false;
        stalemate = false;
    }
    
    private void checkmate(Board copy, boolean whiteTurn) {
        if(checkCheck(copy, !whiteTurn) && mate(copy, whiteTurn)) {
            checkmate = true;
        }
    }
    
    private void stalemate(Board copy, boolean whiteTurn) {
        if(!checkCheck(copy, !whiteTurn) && mate(copy, whiteTurn)) {
            stalemate = true;
        }
    }
    
    private boolean mate(Board copy, boolean whiteTurn) {
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    System.out.println("i" + i + "j"+j);
                    Piece piece = copy.getTile(i, j).getPiece();
                    if(piece != null && piece.isWhite() != whiteTurn) {
                        for(int x = 0; x < 8; x++){
                            for(int y = 0; y < 8; y++) {
                                System.out.println("x + y" + x + " " + y);
                                System.out.println("ei null, oikea väri");
                                if(piece.validMove(copy, i, j, x, y)) {
                                    System.out.println("valid move");
                                    Piece eaten = copy.getTile(x, y).getPiece();
                                    copy.getTile(x, y).setPiece(piece);
                                    copy.getTile(i, j).setPiece(null);
                                    if(!checkCheck(copy, !whiteTurn)) {
                                        copy.getTile(x, y).setPiece(eaten);
                                        copy.getTile(i, j).setPiece(piece);
                                        System.out.println("ei shakkimattia");
                                        return false;
                                    }
                                    copy.getTile(x, y).setPiece(eaten);
                                    copy.getTile(i, j).setPiece(piece);
                                };
                            }
                        }
                    }
                }
            }
        return true;
    }
        
    private boolean checkCheck(Board copy, boolean whiteTurn) {
        Tile tile = findKing(whiteTurn);
        int x = tile.getX();
        int y = tile.getY();
        System.out.println(x);
        System.out.println(y);
        
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = copy.getTile(i, j).getPiece();
                if(piece != null && piece.isWhite() != whiteTurn) {
                    
                    if(piece.validMove(copy, i, j, x, y)) {
                        System.out.println("i" + i);
                        System.out.println("j" + j);
                        System.out.println("shakki omassa päässä");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean movePiece(int oldX, int oldY, int newX, int newY) {
        System.out.println("Valkoinen vuoro " + whitesTurn);
        if(oldX == newX && oldY == newY) return false;
        
        boolean moveMade = false;
        Piece piece = board.getTile(oldX, oldY).getPiece();
        if(piece != null && piece.validMove(board, oldX, oldY, newX, newY)) {
            Piece eaten = board.getTile(newX, newY).getPiece();
            board.getTile(newX, newY).setPiece(piece);
            board.getTile(oldX, oldY).setPiece(null);
            if(!checkCheck(board, whitesTurn)) {
                moveMade = true;
                piece.setHasMoved(true);
                stalemate(board, whitesTurn);
                checkmate(board, whitesTurn);
                whitesTurn = !whitesTurn;
            } else {
                board.getTile(newX, newY).setPiece(eaten);
                board.getTile(oldX, oldY).setPiece(piece);
            }           
        }
        return moveMade;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    
    public boolean getCheckmate() {
        return this.checkmate;
    }
    
    private Tile findKing(boolean white) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = board.getTile(i, j).getPiece();
                if(piece != null && piece.isWhite() == white && piece.getType().equals("King")) {
                        return board.getTile(i,j);
                }
            }
        }
        return null;
    }
}
