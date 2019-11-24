/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

import com.mycompany.shakki.domain.Bishop;
import com.mycompany.shakki.domain.Chess;
import com.mycompany.shakki.domain.Piece;
import com.mycompany.shakki.domain.Queen;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Mikko
 */
public class ChessTest {
    private Chess chess;
    public ChessTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        chess = new Chess();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void gameStartsWithABoard() {
        assertFalse(chess.getBoard() == null);
    }
    
    @Test
    public void movingExistingPieceReturnsTrue() {
        assertTrue(chess.turn(6, 6, 6, 5));
    }
    
    @Test
    public void movingNonexistingPieceReturnsFalse() {
        assertFalse(chess.turn(2, 2, 0, 0));
    }
    
    @Test
    public void movingPieceSetsPieceToNewCoordinates() {
        Piece piece = chess.getBoard().getTile(0,6).getPiece();
        chess.turn(0, 6, 0, 5);
        assertEquals(piece, chess.getBoard().getTile(0,5).getPiece());
    }
    
    @Test
    public void movingPieceClearsOldCoordinates() {
        chess.turn(0, 6, 0, 5);
        assertEquals(null, chess.getBoard().getTile(0,6).getPiece());
    }
    
    @Test
    public void movingPieceToSameSpotWontClearCoordinates() {
        Piece piece = chess.getBoard().getTile(0,6).getPiece();
        chess.turn(0, 6, 0, 6);
        assertEquals(piece, chess.getBoard().getTile(0,6).getPiece());
    }
    
    @Test
    public void cantMoveWrongColorPiece() {
        assertFalse(chess.turn(1, 1, 1, 2));
    }
    
    @Test
    public void movingPieceChangesTurn() {
        chess.turn(6, 6, 6, 5);
        assertFalse(chess.isWhitesTurn());
    }
    
    @Test
    public void cantMovePieceIfItLeadsToCheck() {
        chess.getBoard().getTile(0, 3).setPiece(new Bishop("Bishop", false));
        assertFalse(chess.turn(3, 6, 3, 5));
    }
    
    @Test
    public void chessmateTrueAfterTurn() {
        chess.getBoard().getTile(3, 0).setPiece(new Queen("Queen", true));
        chess.getBoard().getTile(5, 0).setPiece(new Queen("Queen", true));
        chess.getBoard().getTile(4, 1).setPiece(new Queen("Queen", true));
        chess.turn(0, 6, 0, 5);
        assertTrue(chess.getCheckmate());
    }
}
