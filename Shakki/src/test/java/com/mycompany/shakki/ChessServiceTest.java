/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

import com.mycompany.shakki.dao.ChessDao;
import com.mycompany.shakki.dao.LatestGameDao;
import com.mycompany.shakki.dao.LeaderboardDao;
import com.mycompany.shakki.dao.PiecesDao;
import com.mycompany.shakki.domain.Chess;
import com.mycompany.shakki.domain.ChessService;
import com.mycompany.shakki.domain.Pawn;
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
public class ChessServiceTest {
    private ChessDao chessDao;
    private PiecesDao piecesDao;
    private LeaderboardDao leaderboardDao;
    private ChessService chessService;
    private LatestGameDao latestGameDao;
    
    public ChessServiceTest() throws SQLException {
        String url = "jdbc:h2:./testChessDatabase";
        chessDao = new ChessDao(url);
        piecesDao = new PiecesDao(url);
        leaderboardDao = new LeaderboardDao(url);
        latestGameDao = new LatestGameDao(url);
        chessService = new ChessService(chessDao, piecesDao, leaderboardDao, latestGameDao);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        chessDao.dropTables();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void newGameAddsPlayersToLeaderBoard() throws SQLException {
        chessService.newGame("dude", "bro");
        boolean p1 = false;
        boolean p2 = false;
        for(Player p : leaderboardDao.getPlayers()) {
            if(p.getName().equals("dude")) {
                p1 = true;
            }
            if(p.getName().equals("bro")) {
                p2 = true;
            }
        }
        assertTrue(p1 && p2);
    }
    
    @Test
    public void turnReturnsFalseIfIllegalMoveIsMade() throws SQLException {
        chessService.newGame("p1", "p2");
        assertFalse(chessService.turn(4, 6, 7, 7));
    }
    
    @Test
    public void turnReturnsIfTrueLegalMoveIsMade() throws SQLException {
        chessService.newGame("p1", "p2");
        assertTrue(chessService.turn(6, 6, 6, 5));
    }
    
//    @Test
//    public void returnsTrueWhenStalemate() {
//    }
    
    @Test
    public void returnsFalseWhenNoStalemate() throws SQLException {
        chessService.newGame("p1", "p2");
        assertFalse(chessService.getStalemate());
    }
    
//    @Test
//    public void returnsTrueWhenCheckmate() {
//        
//    }
    
    @Test
    public void returnsFalseWhenNoCheckmate() throws SQLException {
        chessService.newGame("p1", "p2");
        assertFalse(chessService.getCheckmate());
    }
    
    @Test
    public void endingGameRemovesItFromDatabase() throws SQLException {
        chessService.newGame("p1", "p2");
        int id = chessService.getChess().getId();
        chessService.endGameAndReturnResult();
        assertFalse(chessService.continueGame());
    }
    
    @Test
    public void endingGameWhenWhiteWinsReturnsRightString() throws SQLException {
        chessService.newGame("p1", "p2");
        chessService.turn(6, 6, 6, 5);
        assertEquals("White wins", chessService.endGameAndReturnResult());
    }
    
    @Test
    public void endingGameWhenBlackWinsReturnsRightString() throws SQLException {
        chessService.newGame("p1", "p2");
        assertEquals("Black wins", chessService.endGameAndReturnResult());
    }
    
    @Test
    public void endingGameSetsLastGameToZero() throws SQLException {
        chessService.newGame("p1", "p2");
        chessService.endGameAndReturnResult();
        assertEquals(0, chessService.getLatestGame());
    }
    
    @Test
    public void endingGameWhenWhiteWinsSetsLeaderboardRight() throws SQLException {
        chessService.newGame("p1", "p2");
        chessService.turn(6, 6, 6, 5);
        chessService.endGameAndReturnResult();
        assertEquals(1,leaderboardDao.getPlayers().get(0).getWins());
    }
    
    @Test
    public void endingGameWhenBlackWinsSetsLeaderboardRight() throws SQLException {
        chessService.newGame("p1", "p2");
        chessService.endGameAndReturnResult();
        assertEquals(1,leaderboardDao.getPlayers().get(0).getWins());
    }
    
    @Test
    public void quitingAddsGameToDatabase() throws SQLException {
        chessService.newGame("p1", "p2");
        chessService.quitAndSave();
        assertTrue(chessService.continueGame());
    }
    
    @Test
    public void quitingSetsIdToLastGame() throws SQLException {
        chessService.newGame("p1", "p2");
        int id = chessService.getChess().getId();
        chessService.quitAndSave();
        assertEquals(id, chessService.getLatestGame());
    }
    
    @Test
    public void continuingGameSetsRightGame() throws SQLException {
        chessService.newGame("p1", "p2");
        int id = chessService.getChess().getId();
        chessService.quitAndSave();
        chessService.continueGame();
        assertEquals(id, chessService.getChess().getId());
    }
    
    @Test
    public void continuingGameSetsPieceClassesRight() throws SQLException {
        chessService.newGame("p1", "p2");
        chessService.quitAndSave();
        chessService.continueGame();
        Pawn pawn = new Pawn("", true);
        assertEquals(pawn.getClass(), chessService.getBoard().getTile(1, 1).getPiece().getClass());
    }
    
    @Test
    public void gameDetectsStalemateAndReturnsDraw() throws SQLException {
        chessService.newGame("p1", "p2");
        chessService.turn(2, 6, 2, 4);
        chessService.turn(7, 1, 7, 3);
        chessService.turn(7, 6, 7, 4);
        chessService.turn(0, 1, 0, 3);
        chessService.turn(3, 7, 0, 4);
        chessService.turn(0, 0, 0, 2);
        chessService.turn(0, 4, 0, 3);
        chessService.turn(0, 2, 7, 2);
        chessService.turn(0, 3, 2, 1);
        chessService.turn(5, 1, 5, 2);
        chessService.turn(2, 1, 3, 1);
        chessService.turn(4, 0, 5, 1);
        chessService.turn(3, 1, 1, 1);
        chessService.turn(3, 0, 3, 5);
        chessService.turn(1, 1, 1, 0);
        chessService.turn(3, 5, 7, 1);
        chessService.turn(1, 0, 2, 0);
        chessService.turn(5, 1, 6, 2);
        chessService.turn(2, 0, 4, 2);
        assertEquals("It's a draw", chessService.endGameAndReturnResult());
    }
    
}
