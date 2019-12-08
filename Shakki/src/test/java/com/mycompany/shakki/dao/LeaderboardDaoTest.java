/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.dao;

import com.mycompany.shakki.domain.Player;
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
public class LeaderboardDaoTest {
    private LeaderboardDao leaderboardDao;
    private ChessDao chessDao;
    public LeaderboardDaoTest() {
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
        chessDao.dropTables();
        leaderboardDao = new LeaderboardDao("jdbc:h2:./testChessDatabase");
    }
    
    @After
    public void tearDown() {
    }

    @Test 
    public void daoWontAddNewPlayerOnExistingPlayer() throws SQLException {
        leaderboardDao.addPlayer(new Player("P1", 0, 0));
        leaderboardDao.updatePlayerWin("P1");
        leaderboardDao.addPlayer(new Player("P1", 0, 0));
        assertEquals(1, leaderboardDao.getPlayers().get(0).getWins());
    }
    
    @Test
    public void daoReturnsListOfPlayers() throws SQLException {
        leaderboardDao.addPlayer(new Player("P1", 0, 0));
        leaderboardDao.addPlayer(new Player("P2", 0, 0));
        assertEquals(2, leaderboardDao.getPlayers().size());
    }
    
    @Test
    public void returnPlayerWithMostWinsFirst() throws SQLException {
        leaderboardDao.addPlayer(new Player("P1", 0, 0));
        leaderboardDao.addPlayer(new Player("P2", 0, 0));
        leaderboardDao.addPlayer(new Player("P3", 0, 0));
        leaderboardDao.updatePlayerWin("P2");
        assertEquals("P2", leaderboardDao.getPlayers().get(0).getName());
    }
    
    @Test
    public void canAddLosses() throws SQLException {
        leaderboardDao.addPlayer(new Player("P1", 0, 0));
        leaderboardDao.updatePlayerLose("P1");
        assertEquals(1, leaderboardDao.getPlayers().get(0).getLosses());
    }
    
    @Test
    public void canAddWins() throws SQLException {
        leaderboardDao.addPlayer(new Player("P1", 0, 0));
        leaderboardDao.updatePlayerWin("P1");
        assertEquals(1, leaderboardDao.getPlayers().get(0).getWins());
    }
}
