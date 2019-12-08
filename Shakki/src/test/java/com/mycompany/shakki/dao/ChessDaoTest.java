/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.dao;

import com.mycompany.shakki.domain.Chess;
import java.sql.SQLException;
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
public class ChessDaoTest {
    private ChessDao chessDao;
    private PiecesDao piecesDao;
    private Chess chess;
    public ChessDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        chessDao = new ChessDao("jdbc:h2:./testChessDatabase");
        piecesDao = new PiecesDao("jdbc:h2:./testChessDatabase");
        chessDao.dropTables();
        chess = new Chess();
        chessDao.addGame(chess);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void rightGameIsSaved() throws SQLException {
        Chess same = chessDao.getChess(chess.getId());
        assertEquals(chess.getId(), same.getId());
    }
    
    @Test
    public void emptyGameIsReturnedIfThereAreNoMatchingId() throws SQLException {
        chessDao.removeGame(chess);
        assertTrue(chessDao.getChess(chess.getId()).getBoard().boardIsEmpty());
    }
    
    @Test
    public void ifThereIsExistingGameUpdateIt() throws SQLException {
        chess.turn(6, 6, 6, 5);
        chess.isWhitesTurn();
        chessDao.addGame(chess);
        chess = chessDao.getChess(chess.getId());
        assertFalse(chess.isWhitesTurn());
    }
}
