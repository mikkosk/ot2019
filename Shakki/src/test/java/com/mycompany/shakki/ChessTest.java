/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

import com.mycompany.shakki.domain.Chess;
import com.mycompany.shakki.domain.Piece;
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
        assertTrue(chess.movePiece(1, 1, 1, 2));
    }
    
    @Test
    public void movingNonexistingPieceReturnsFalse() {
        assertFalse(chess.movePiece(2, 2, 0, 0));
    }
    
    @Test
    public void movingPieceSetsPieceToNewCoordinates() {
        Piece piece = chess.getBoard().getTile(0,1).getPiece();
        chess.movePiece(0, 1, 0, 2);
        assertEquals(piece, chess.getBoard().getTile(0,2).getPiece());
    }
    
    @Test
    public void movingPieceClearsOldCoordinates() {
        chess.movePiece(0, 1, 0, 2);
        assertEquals(null, chess.getBoard().getTile(0,1).getPiece());
    }
    
    @Test
    public void movingPieceToSameSpotWontClearCoordinates() {
        Piece piece = chess.getBoard().getTile(0,0).getPiece();
        chess.movePiece(0, 0, 0, 0);
        assertEquals(piece, chess.getBoard().getTile(0,0).getPiece());
    }
}
