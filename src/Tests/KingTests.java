import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KingTests
{
  private Board board;
  int turn;
  boolean check;

  @BeforeEach
  public void setup()
  {
    board = new Board();
    turn = 1;
    check = false;
  }

  @Test
  public void testStraightNonCapture()
  {
    board.setBoard("rnbqkbnr/pppppppp/8/8/3PK3/5P2/PP1PP1PP/RNBQ1BNR");

    King king = (King) board.getSquare(4, 4).piece;
    king.findMoves(board, turn, check);

    // Set the start and end positions for the first move
    int[] start = king.setStart(4, 4);
    int[] end =  king.setEnd(3, 4);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    // Set the start and end positions for the second move
    start = king.setStart(4, 4);
    end =  king.setEnd(5, 4);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    // Set the start and end positions for the third move
    start = king.setStart(4, 4);
    end =  king.setEnd(4, 5);
    // Create the expected move object for the third move
    Move trueMove3 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    // Set the start and end positions for the first illegal move
    start = king.setStart(4, 4);
    end =  king.setEnd(4, 6);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    Assertions.assertEquals(6, king.moves.size());
    Assertions.assertTrue(king.moves.contains(trueMove1));
    Assertions.assertTrue(king.moves.contains(trueMove2));
    Assertions.assertTrue(king.moves.contains(trueMove3));
    Assertions.assertFalse(king.moves.contains(falseMove1));

  }

  @Test
  public void testDiagonalNonCapture()
  {
    board.setBoard("rnbqkbnr/pppppppp/8/8/3PK3/5P2/PP1PP1PP/RNBQ1BNR");

    King king = (King) board.getSquare(4, 4).piece;
    king.findMoves(board, turn, check);
  }

  @Test
  public void testCapture(){}

  @Test
  public void testCastle()
  {}

  @Test
  public void testInCheck()
  {

  }

  @Test
  public void testMoveIntoCheck()
  {

  }

}
