/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

import com.mycompany.shakki.domain.Chess;
import com.mycompany.shakki.domain.Pawn;
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
public class PawnTest {
    
    private Pawn pawn;
    private Chess chess;
    
    public PawnTest() {
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
        pawn = new Pawn("Pawn", true);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test 
    public void cantMoveThroughPieces() {
        chess.getBoard().getTile(1,5).setPiece(new Pawn("Pawn", true));
        assertFalse(pawn.validMove(chess.getBoard(), 1, 6, 1, 4));
    }
    
    @Test 
    public void canMoveTwoIfNotMoved() {
        pawn.setHasMoved(false);
        assertTrue(pawn.validMove(chess.getBoard(), 3, 5, 3, 3));
    }
    
    @Test 
    public void cantMoveTwoIfMoved() {
        pawn.setHasMoved(true);
        assertFalse(pawn.validMove(chess.getBoard(), 3, 5, 3, 3));
    }
    
    @Test
    public void canMoveOneAlways() {
        pawn.setHasMoved(true);
        assertTrue(pawn.validMove(chess.getBoard(), 3, 4, 3, 3));
    }
    
    @Test
    public void canEatOtherColorPieceDiagonal() {
        assertTrue(pawn.validMove(chess.getBoard(), 2, 2, 1, 1));
    }
    
    @Test
    public void cantEatSameColorPiece() {
        assertFalse(pawn.validMove(chess.getBoard(), 1, 7, 2, 6));
    }
    
    @Test 
    public void cantMoveBackward() {
        assertFalse(pawn.validMove(chess.getBoard(), 3, 3, 3, 4));
    }
}
