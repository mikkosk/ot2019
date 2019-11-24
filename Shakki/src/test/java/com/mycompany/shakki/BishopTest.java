/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

import com.mycompany.shakki.domain.Bishop;
import com.mycompany.shakki.domain.Chess;
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
public class BishopTest {
    private Bishop bishop;
    private Chess chess;
    
    public BishopTest() {
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
        bishop = new Bishop("Bishop", true);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test 
    public void cantMoveThroughPieces() {
        assertFalse(bishop.validMove(chess.getBoard(), 2, 7, 4, 5));
    }
    
    @Test 
    public void canMoveDiagonally() {
        chess.getBoard().getTile(3, 6).setPiece(null);
        assertTrue(bishop.validMove(chess.getBoard(), 2, 7, 4, 5));
    }
    
    @Test
    public void cantMoveStraight() {
        chess.getBoard().getTile(2, 6).setPiece(null);
        assertFalse(bishop.validMove(chess.getBoard(), 2, 7, 2, 5));
    }
    
    @Test
    public void canEatOtherColorPiece() {
        assertTrue(bishop.validMove(chess.getBoard(), 2, 2, 1, 1));
    }
    
    @Test
    public void cantEatSameColorPiece() {
        assertFalse(bishop.validMove(chess.getBoard(), 2, 7, 3, 6));
    }
}
