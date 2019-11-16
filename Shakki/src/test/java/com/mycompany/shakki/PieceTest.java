/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

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
public class PieceTest {
    private Piece whitePawn;
    public PieceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        whitePawn = new Piece("Pawn", true);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void pieceColorIsRigthInTheBeginning() {
        assertTrue(whitePawn.isWhite());
    }
    
    @Test
    public void pieceColorIsRightAfterChange() {
        whitePawn.setWhite(false);
        assertFalse(whitePawn.isWhite());
    }
    
    @Test
    public void pieceTypeIsRightInTheBeginning() {
        assertEquals("Pawn", whitePawn.getType());
    }
    
    @Test
    public void pieceTypeIsRightAfterChange() {
        whitePawn.setType("Rook");
        assertEquals("Rook", whitePawn.getType());
    }
    
}
