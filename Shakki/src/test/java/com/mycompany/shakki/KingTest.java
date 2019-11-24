/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

import com.mycompany.shakki.domain.Chess;
import com.mycompany.shakki.domain.King;
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
public class KingTest {
    
    private King king;
    private Chess chess;
    
    public KingTest() {
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
        king = new King("King", true);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test 
    public void canMoveDiagonally() {
        assertTrue(king.validMove(chess.getBoard(), 2, 2, 3, 3));
    }
    
    @Test 
    public void canMoveInStraight() {
        assertTrue(king.validMove(chess.getBoard(), 2, 2, 2, 3));
    }
    
    @Test
    public void cantMoveTwoTiles() {
        assertFalse(king.validMove(chess.getBoard(), 2, 2, 2, 4));
    }
    
    @Test
    public void canEatOtherColorPiece() {
        assertTrue(king.validMove(chess.getBoard(), 0, 1, 0, 0));
    }
    
    @Test
    public void cantEatSameColorPiece() {
        assertFalse(king.validMove(chess.getBoard(), 0, 5, 0, 6));
    }
}
