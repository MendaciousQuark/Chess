import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class BoardTests
{
  private Board board;

  @BeforeEach
  public void setUp() {
    board = new Board();
  }

  @Test
  public void testBoardInitialization() {
    Square[][] boardArray = board.getBoard();
    Assertions.assertNotNull(boardArray);
    Assertions.assertEquals(8, boardArray.length);
    Assertions.assertEquals(8, boardArray[0].length);

    int i = 0;
    boolean expectedColour;
    for(Square[] row: boardArray)
    {
      expectedColour = i % 2 == 0;
      for(Square square: row)
      {
        Assertions.assertEquals(expectedColour, square.colour);
        expectedColour = !expectedColour;
      }
      i++;
    }
  }

  @Test
  public void testToString() {
    String fen = board.toString();
    Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR KQkq", fen);
    board.setBoard("rnbqkbnr/pppppp2/6pp/8/3NR3/4PPP1/1PPP3P/2BQKBNR KQkq");
    fen = board.toString();
    StringBuilder sb = new StringBuilder();
    Square[][] test = board.getBoard();
    Assertions.assertEquals("rnbqkbnr/pppppp2/6pp/8/3NR3/4PPP1/1PPP3P/2BQKBNR KQkq", fen);
  }

  @Test
  public void testSetAndGetBoard() {
    Board newBoard = new Board("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1");
    board.setBoard("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1");

    Assertions.assertTrue(Arrays.deepEquals(newBoard.getBoard(), board.getBoard()));
  }

  @Test
  public void testSetUp() {
    String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR KQkq";
    board.setBoard(fen);
    Square[][] boardArray = board.getBoard();
    int i = 0;
    for(Square[] row: boardArray)
    {
      for(Square square: row)
      {

        if((i > 7 && i < 15) || (i > 47 && i < 55))
        {
          Assertions.assertTrue(square.piece instanceof Pawn);
        }
        else if ( i < 7 || i > 55)
        {
          switch(i % 8)
          {
            case 0, 7 -> Assertions.assertTrue(square.piece instanceof Rook);
            case 1, 6 -> Assertions.assertTrue(square.piece instanceof Knight);
            case 2, 5 -> Assertions.assertTrue(square.piece instanceof Bishop);
            case 3 -> Assertions.assertTrue(square.piece instanceof Queen);
            case 4 -> Assertions.assertTrue(square.piece instanceof King);
          }
        }
        i++;
      }
    }
  }

  @Test
  public void testDisplay() {
    // Implement the test to verify if the display method displays the board correctly
    // Redirect the output to a string and compare it with the expected board representation
    board.setBoard("8/8/8/8/8/8/8/8 -");
    String[] displayBoard = board.display();
    String[] expectedOutput = {
        "  __a___b___c___d___e___f___g___h___\n",
        "  |****    ****    ****    ****    |\n",
        "8 |****    ****    ****    ****    |\n",
        "  |****    ****    ****    ****    |\n",
        "  |    ****    ****    ****    ****|\n",
        "7 |    ****    ****    ****    ****|\n",
        "  |    ****    ****    ****    ****|\n",
        "  |****    ****    ****    ****    |\n",
        "6 |****    ****    ****    ****    |\n",
        "  |****    ****    ****    ****    |\n",
        "  |    ****    ****    ****    ****|\n",
        "5 |    ****    ****    ****    ****|\n",
        "  |    ****    ****    ****    ****|\n",
        "  |****    ****    ****    ****    |\n",
        "4 |****    ****    ****    ****    |\n",
        "  |****    ****    ****    ****    |\n",
        "  |    ****    ****    ****    ****|\n",
        "3 |    ****    ****    ****    ****|\n",
        "  |    ****    ****    ****    ****|\n",
        "  |****    ****    ****    ****    |\n",
        "2 |****    ****    ****    ****    |\n",
        "  |****    ****    ****    ****    |\n",
        "  |    ****    ****    ****    ****|\n",
        "1 |    ****    ****    ****    ****|\n",
        "  |    ****    ****    ****    ****|\n",
        "  |_a___b___c___d___e___f___g___h__|\n"
    };

    Assertions.assertArrayEquals(expectedOutput, displayBoard);

    board.setBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR KQkq");
    expectedOutput = new String[]{
        "  __a___b___c___d___e___f___g___h___\n",
        "  |****    ****    ****    ****    |\n",
        "8 |*rr* nn *bb* qq *kk* bb *nn* rr |\n",
        "  |****    ****    ****    ****    |\n",
        "  |    ****    ****    ****    ****|\n",
        "7 | pp *pp* pp *pp* pp *pp* pp *pp*|\n",
        "  |    ****    ****    ****    ****|\n",
        "  |****    ****    ****    ****    |\n",
        "6 |****    ****    ****    ****    |\n",
        "  |****    ****    ****    ****    |\n",
        "  |    ****    ****    ****    ****|\n",
        "5 |    ****    ****    ****    ****|\n",
        "  |    ****    ****    ****    ****|\n",
        "  |****    ****    ****    ****    |\n",
        "4 |****    ****    ****    ****    |\n",
        "  |****    ****    ****    ****    |\n",
        "  |    ****    ****    ****    ****|\n",
        "3 |    ****    ****    ****    ****|\n",
        "  |    ****    ****    ****    ****|\n",
        "  |****    ****    ****    ****    |\n",
        "2 |*PP* PP *PP* PP *PP* PP *PP* PP |\n",
        "  |****    ****    ****    ****    |\n",
        "  |    ****    ****    ****    ****|\n",
        "1 | RR *NN* BB *QQ* KK *BB* NN *RR*|\n",
        "  |    ****    ****    ****    ****|\n",
        "  |_a___b___c___d___e___f___g___h__|\n"
    };

    displayBoard = board.display();
    Assertions.assertArrayEquals(expectedOutput, displayBoard);
  }

  @Test
  public void testEvaluate() {
    board.setBoard("8/3k4/3pp3/3nn3/3NN3/2P1KP2/8/8");
    System.out.println(board.evaluate());
  }

  @Test
  public void testBoardModifications() {
    // Implement tests to validate various board modification operations
    // Test adding and removing pieces from the board, moving pieces, etc.
    // Assert that the board state reflects the changes accurately
  }

  @Test
  public void testBoardBoundaries() {
    // Implement tests to verify proper handling of board boundaries
    // Ensure that attempting to access positions outside the valid board range throws an exception or returns the expected result
  }

  @Test
  public void testBoardEquality() {
    // Implement tests to compare two board objects for equality
    // Create two boards with the same or different configurations and assert if they are equal or not based on your expectations
  }
}
