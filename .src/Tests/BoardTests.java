import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    Assertions.assertEquals("FEN", fen);
    board.setBoard("rnbqkbnr/pppppp2/6pp/8/3NR3/4PPP1/1PPP3P/2BQKBNR w KQkq - 0 1");
    fen = board.toString();
    Assertions.assertEquals("rnbqkbnr/pppppp2/6pp/8/3NR3/4PPP1/1PPP3P/2BQKBNR w KQkq - 0 1", fen);
  }

  @Test
  public void testSetAndGetBoard() {
    Board newBoard = new Board("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1");
    board.setBoard("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1");
    Assertions.assertEquals(newBoard.getBoard(), board.getBoard());
  }

  @Test
  public void testSetUp() {
    String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    board.setBoard(fen);
    Square[][] boardArray = board.getBoard();
    int i = 0;
    for(Square[] row: boardArray)
    {
      for(Square square: row)
      {
        if(i < 17 && square.occupied)
        {
          Assertions.assertFalse(square.piece.colour);
        }
        else if (i > 46 && square.occupied)
        {
          Assertions.assertTrue(square.piece.colour);
        }
        if((i > 7 && i < 15) || (i > 47 && i < 55))
        {
          Assertions.assertTrue(square.piece instanceof Pawn);
        }
        else
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
    // Implement the test to verify if the Display method displays the board correctly
    // Redirect the output to a string and compare it with the expected board representation
  }

  @Test
  public void testEvaluate() {
    // Implement the test to check if the evaluate method returns the expected evaluation score
    // Create a test case with a specific board setup and assert the evaluation score based on the expected outcome
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
