package com.mycompany.shakki;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.shakki.domain.Board;
import com.mycompany.shakki.domain.Piece;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mikko
 */
public class BoardTest {
    private Board board;
    
    public BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        board = new Board();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void noTileIsNull() {
        boolean tileIsNull = false;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board.getTile(i, j) == null) tileIsNull = true;
            }
        }
        
        assertFalse(tileIsNull);
    }
    
    @Test
    public void PawnsAreInRightPlace() {
        boolean right = true;
        for(int i = 0; i < 8; i++) {
            Piece pieceBlack = board.getTile(i, 1).getPiece();
            Piece pieceWhite = board.getTile(i, 6).getPiece();
            if(!pieceBlack.getType().equals("Pawn") || pieceBlack.isWhite()) right = false;
            if(!pieceWhite.getType().equals("Pawn") || !pieceWhite.isWhite()) right = false;
        }
        assertTrue(right);
    }
    
    @Test
    public void RooksAreInRightPlace() {
        boolean right = true;
        for(int i = 0; i < 8; i += 7) {
            Piece pieceBlack = board.getTile(i, 0).getPiece();
            Piece pieceWhite = board.getTile(i, 7).getPiece();
            if(!pieceBlack.getType().equals("Rook") || pieceBlack.isWhite()) right = false;
            if(!pieceWhite.getType().equals("Rook") || !pieceWhite.isWhite()) right = false;
        }
        assertTrue(right);
    }
    
    @Test
    public void KnigthsAreInRightPlace() {
        boolean right = true;
        for(int i = 1; i < 8; i += 5) {
            Piece pieceBlack = board.getTile(i, 0).getPiece();
            Piece pieceWhite = board.getTile(i, 7).getPiece();
            if(!pieceBlack.getType().equals("Knight") || pieceBlack.isWhite()) right = false;
            if(!pieceWhite.getType().equals("Knight") || !pieceWhite.isWhite()) right = false;
        }
        assertTrue(right);
    }
    
    @Test
    public void BishopsAreInRightPlace() {
        boolean right = true;
        for(int i = 2; i < 8; i += 3) {
            Piece pieceBlack = board.getTile(i, 0).getPiece();
            Piece pieceWhite = board.getTile(i, 7).getPiece();
            if(!pieceBlack.getType().equals("Bishop") || pieceBlack.isWhite()) right = false;
            if(!pieceWhite.getType().equals("Bishop") || !pieceWhite.isWhite()) right = false;
        }
        assertTrue(right);
    }
    
    @Test
    public void QueensAreInRightPlace() {
        boolean right = true;
        Piece pieceBlack = board.getTile(3, 0).getPiece();
        Piece pieceWhite = board.getTile(3, 7).getPiece();
        if(!pieceBlack.getType().equals("Queen") || pieceBlack.isWhite()) right = false;
        if(!pieceWhite.getType().equals("Queen") || !pieceWhite.isWhite()) right = false;
        assertTrue(right);
    }
    
    @Test
    public void KingsAreInRightPlace() {
        boolean right = true;
        Piece pieceBlack = board.getTile(4, 0).getPiece();
        Piece pieceWhite = board.getTile(4, 7).getPiece();
        if(!pieceBlack.getType().equals("King") || pieceBlack.isWhite()) right = false;
        if(!pieceWhite.getType().equals("King") || !pieceWhite.isWhite()) right = false;
        assertTrue(right);
    }
    
    @Test
    public void NoAddittionalPieces() {
        boolean right = true;
        for(int j = 2; j < 6; j++) {
            for(int i = 0; i < 8; i++) {
                if(!board.getTile(i, j).empty()) right = false;
            }
        }
        assertTrue(right);
    }
}
