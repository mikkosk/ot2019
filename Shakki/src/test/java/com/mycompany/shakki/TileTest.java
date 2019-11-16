/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

import com.mycompany.shakki.domain.Piece;
import com.mycompany.shakki.domain.Tile;
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
public class TileTest {
    private Tile tile;
    private Piece piece;
    
    public TileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        tile = new Tile(0, 0, true);
        piece = new Piece("Pawn", true);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void AtStartTileIsEmpty() {
        assertTrue(tile.empty());
    }
    
    @Test
    public void EmptyTileReturnsNull() {
        assertEquals(null, tile.getPiece());

    }
    
    @Test
    public void TileCanBeFilled() {
        tile.setPiece(piece);
        assertFalse(tile.empty());
    }
    
    @Test
    public void TileCanBeEmptied() {
        tile.setPiece(piece);
        tile.setPiece(null);
        assertTrue(tile.empty());
    }
    
    @Test
    public void TileWithPieceReturnsCorrectPiece() {
        tile.setPiece(piece);
        assertEquals(piece, tile.getPiece());
    }
}
