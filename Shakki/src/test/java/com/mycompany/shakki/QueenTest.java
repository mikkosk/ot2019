/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

import com.mycompany.shakki.domain.Chess;
import com.mycompany.shakki.domain.Queen;
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
public class QueenTest {
    
    private Queen queen;
    private Chess chess;
    
    public QueenTest() {
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
        queen = new Queen("Queen", true);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test 
    public void cantMoveThroughPieces() {
        assertFalse(queen.validMove(chess.getBoard(), 1, 7, 1, 5));
    }
    
    @Test 
    public void canMoveStraight() {
        assertTrue(queen.validMove(chess.getBoard(), 2, 5, 2, 3));
    }
    
    @Test
    public void canMoveDiagonnally() {
        assertTrue(queen.validMove(chess.getBoard(), 2, 3, 4, 5));
    }
    
    @Test
    public void cantMoveInLShape() {
        assertFalse(queen.validMove(chess.getBoard(), 2, 3, 5, 5));
    }
    
    @Test
    public void canEatOtherColorPiece() {
        assertTrue(queen.validMove(chess.getBoard(), 2, 2, 1, 1));
    }
    
    @Test
    public void cantEatSameColorPiece() {
        assertFalse(queen.validMove(chess.getBoard(), 5, 5, 5, 6));
    }
}
