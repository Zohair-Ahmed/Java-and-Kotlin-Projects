package javaprojects.mobileapps.tictactoe;

import org.junit.Test;
import javaprojects.mobileapps.tictactoe.models.Game;
import static org.junit.Assert.*;

public class Tests {
    @Test
    public void test_01a() {
        Game g = new Game("Suyeon", "Yuna");

        String whoPlaysFirst = g.getCurrentPlayer();
        assertEquals("Suyeon", whoPlaysFirst);

        String status = g.getStatus();
        assertEquals("Suyeon's turn to play...", status);

        char[][] board = g.getBoard();
        char[][] expectedBoard = {
                {'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}
        };
        assertArrayEquals(expectedBoard, board);
    }

    @Test
    public void test_01b() {
        Game g = new Game("Suyeon", "Yuna");
        g.setWhoPlaysFirst('o');

        assertEquals("Yuna", g.getCurrentPlayer());
        assertEquals("Yuna's turn to play...", g.getStatus());

        char[][] expectedBoard = {
                {'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}
        };
        assertArrayEquals(expectedBoard, g.getBoard());

        g = new Game("Suyeon", "Yuna");
        g.setWhoPlaysFirst('x');
        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Suyeon's turn to play...", g.getStatus());
        assertArrayEquals(expectedBoard, g.getBoard());
    }

    @Test
    public void test_02() {
        Game g = new Game("Suyeon", "Yuna");
        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Suyeon's turn to play...", g.getStatus());
        char[][] expectedBoard = {
                {'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}
        };
        assertArrayEquals(expectedBoard, g.getBoard());
        g.move(-2, 4);
        assertEquals("Error: row -2 is invalid.", g.getStatus());
        assertEquals("Suyeon", g.getCurrentPlayer());
        assertArrayEquals(expectedBoard, g.getBoard());

        g.move(2, 4);

        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Error: col 4 is invalid.", g.getStatus());
        assertArrayEquals(expectedBoard, g.getBoard());
    }

    @Test
    public void test_03() {
        Game g = new Game("Suyeon", "Yuna");
        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Suyeon's turn to play...", g.getStatus());
        char[][] expectedBoard0 = {
                {'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}
        };
        assertArrayEquals(expectedBoard0, g.getBoard());

        g.move(2, 2);

        assertEquals("Yuna", g.getCurrentPlayer());
        assertEquals("Yuna's turn to play...", g.getStatus());
        char[][] expectedBoard1 = {
                {'_', '_', '_'},
                {'_', 'x', '_'},
                {'_', '_', '_'}
        };
        assertArrayEquals(expectedBoard1, g.getBoard());

        g.move(1, 3);

        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Suyeon's turn to play...", g.getStatus());
        char[][] expectedBoard2 = {
                {'_', '_', 'o'},
                {'_', 'x', '_'},
                {'_', '_', '_'}
        };
        assertArrayEquals(expectedBoard2, g.getBoard());

        g.move(1, 3);

        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Error: slot @ (1, 3) is already occupied.", g.getStatus());
        assertArrayEquals(expectedBoard2, g.getBoard());

        g.move(3, 3);

        assertEquals("Yuna", g.getCurrentPlayer());
        assertEquals("Yuna's turn to play...", g.getStatus());
        char[][] expectedBoard3 = {
                {'_', '_', 'o'},
                {'_', 'x', '_'},
                {'_', '_', 'x'}
        };
        assertArrayEquals(expectedBoard3, g.getBoard());

        g.move(1, 2);

        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Suyeon's turn to play...", g.getStatus());
        char[][] expectedBoard4 = {
                {'_', 'o', 'o'},
                {'_', 'x', '_'},
                {'_', '_', 'x'}
        };
        assertArrayEquals(expectedBoard4, g.getBoard());

        g.move(1, 1);

        assertNull(g.getCurrentPlayer());
        assertEquals("Game is over with Suyeon being the winner.", g.getStatus());
        char[][] expectedBoard5 = {
                {'x', 'o', 'o'},
                {'_', 'x', '_'},
                {'_', '_', 'x'}
        };
        assertArrayEquals(expectedBoard5, g.getBoard());

        g.move(4, -2);

        assertNull(g.getCurrentPlayer());
        assertEquals("Error: game is already over with a winner.", g.getStatus());
        assertArrayEquals(expectedBoard5, g.getBoard());

        g.move(1, -2);

        assertNull(g.getCurrentPlayer());
        assertEquals("Error: game is already over with a winner.", g.getStatus());
        assertArrayEquals(expectedBoard5, g.getBoard());

        g.move(1, 2);
        assertNull(g.getCurrentPlayer());
        assertEquals("Error: game is already over with a winner.", g.getStatus());
        assertArrayEquals(expectedBoard5, g.getBoard());
    }

    @Test
    public void test_04() {
        Game g = new Game("Suyeon", "Yuna");
        g.setWhoPlaysFirst('o');

        assertEquals("Yuna", g.getCurrentPlayer());
        assertEquals("Yuna's turn to play...", g.getStatus());
        char[][] expectedBoard0 = {
                {'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}
        };
        assertArrayEquals(expectedBoard0, g.getBoard());

        g.move(2, 3);

        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Suyeon's turn to play...", g.getStatus());
        char[][] expectedBoard1 = {
                {'_', '_', '_'},
                {'_', '_', 'o'},
                {'_', '_', '_'}
        };
        assertArrayEquals(expectedBoard1, g.getBoard());

        g.move(3, 1);

        assertEquals("Yuna", g.getCurrentPlayer());
        assertEquals("Yuna's turn to play...", g.getStatus());
        char[][] expectedBoard2 = {
                {'_', '_', '_'},
                {'_', '_', 'o'},
                {'x', '_', '_'}
        };
        assertArrayEquals(expectedBoard2, g.getBoard());

        g.move(2, 1);

        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Suyeon's turn to play...", g.getStatus());
        char[][] expectedBoard3 = {
                {'_', '_', '_'},
                {'o', '_', 'o'},
                {'x', '_', '_'}
        };
        assertArrayEquals(expectedBoard3, g.getBoard());

        g.move(2, 2);

        assertEquals("Yuna", g.getCurrentPlayer());
        assertEquals("Yuna's turn to play...", g.getStatus());
        char[][] expectedBoard4 = {
                {'_', '_', '_'},
                {'o', 'x', 'o'},
                {'x', '_', '_'}
        };
        assertArrayEquals(expectedBoard4, g.getBoard());

        g.move(1, 3);

        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Suyeon's turn to play...", g.getStatus());
        char[][] expectedBoard5 = {
                {'_', '_', 'o'},
                {'o', 'x', 'o'},
                {'x', '_', '_'}
        };
        assertArrayEquals(expectedBoard5, g.getBoard());

        g.move(3, 3);

        assertEquals("Yuna", g.getCurrentPlayer());
        assertEquals("Yuna's turn to play...", g.getStatus());
        char[][] expectedBoard6 = {
                {'_', '_', 'o'},
                {'o', 'x', 'o'},
                {'x', '_', 'x'}
        };
        assertArrayEquals(expectedBoard6, g.getBoard());

        g.move(1, 1);

        assertEquals("Suyeon", g.getCurrentPlayer());
        assertEquals("Suyeon's turn to play...", g.getStatus());
        char[][] expectedBoard7 = {
                {'o', '_', 'o'},
                {'o', 'x', 'o'},
                {'x', '_', 'x'}
        };
        assertArrayEquals(expectedBoard7, g.getBoard());

        g.move(1, 2);
        assertEquals("Yuna", g.getCurrentPlayer());
        assertEquals("Yuna's turn to play...", g.getStatus());
        char[][] expectedBoard8 = {
                {'o', 'x', 'o'},
                {'o', 'x', 'o'},
                {'x', '_', 'x'}
        };
        assertArrayEquals(expectedBoard8, g.getBoard());

        g.move(3, 2);

        assertNull(g.getCurrentPlayer());
        assertEquals("Game is over with a tie between Suyeon and Yuna.", g.getStatus());
        char[][] expectedBoard9 = {
                {'o', 'x', 'o'},
                {'o', 'x', 'o'},
                {'x', 'o', 'x'}
        };
        assertArrayEquals(expectedBoard9, g.getBoard());

        g.move(4, -2);

        assertNull(g.getCurrentPlayer());
        assertEquals("Error: game is already over with a tie.", g.getStatus());
        assertArrayEquals(expectedBoard9, g.getBoard());

        g.move(1, -2);

        assertNull(g.getCurrentPlayer());
        assertEquals("Error: game is already over with a tie.", g.getStatus());
        assertArrayEquals(expectedBoard9, g.getBoard());

        g.move(1, 2);
        assertNull(g.getCurrentPlayer());
        assertEquals("Error: game is already over with a tie.", g.getStatus());
        assertArrayEquals(expectedBoard9, g.getBoard());
    }
}