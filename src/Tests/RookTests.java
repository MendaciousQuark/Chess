import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RookTests
{
  private Board board;

  @BeforeEach
  public void setup()
  {
    board = new Board();
  }

  @Test
  public void testNonCapture()
  {
    int turn = 1;
    board.setBoard("rnbqkbnr/p1p1p1pp/1p1R1p2/3P4/7P/8/PPP1PPP1/RNBQKBN1");

    Rook rook = (Rook) board.getSquare(2, 3).piece;
    rook.findMoves(board, turn);

    // Set the start and end positions for the first move
    int[] start = rook.setStart(2, 3);
    int[] end =  rook.setEnd(1, 3);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, rook, rook.colour, turn, false,  false, false);

    // Set the start and end positions for the second move
    start = rook.setStart(2, 3);
    end =  rook.setEnd(2, 2);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, rook, rook.colour, turn, false,  false, false);

    // Set the start and end positions for the third move
    start = rook.setStart(2, 3);
    end =  rook.setEnd(2, 4);
    // Create the expected move object for the third move
    Move trueMove3 = new Move(board, start, end, rook, rook.colour, turn, false,  false, false);

    // Set the start and end positions for the first illegal move
    start = rook.setStart(2, 3);
    end =  rook.setEnd(3, 3);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, rook, rook.colour, turn, false,  false, false);

    Assertions.assertEquals(6, rook.moves.size());
    Assertions.assertTrue(rook.moves.contains(trueMove1));
    Assertions.assertTrue(rook.moves.contains(trueMove2));
    Assertions.assertTrue(rook.moves.contains(trueMove3));
    Assertions.assertFalse(rook.moves.contains(falseMove1));
  }

  @Test
  public void testCapture()
  {
    int turn = 1;
    board.setBoard("rnbqkbnr/p1p1p1pp/1p1R1p2/3P4/7P/8/PPP1PPP1/RNBQKBN1");

    Rook rook = (Rook) board.getSquare(2, 3).piece;
    rook.findMoves(board, turn);

    // Set the start and end positions for the first move
    int[] start = rook.setStart(2, 3);
    int[] end =  rook.setEnd(0, 3);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, rook, rook.colour, turn, true,  false, false);

    // Set the start and end positions for the second move
    start = rook.setStart(2, 3);
    end =  rook.setEnd(2, 1);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, rook, rook.colour, turn, true,  false, false);

    // Set the start and end positions for the third move
    start = rook.setStart(2, 3);
    end =  rook.setEnd(2, 5);
    // Create the expected move object for the third move
    Move trueMove3 = new Move(board, start, end, rook, rook.colour, turn, true,  false, false);

    // Set the start and end positions for the first illegal move
    start = rook.setStart(2, 3);
    end =  rook.setEnd(2, 0);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, rook, rook.colour, turn, true,  false, false);

    Assertions.assertEquals(6, rook.moves.size());
    Assertions.assertTrue(rook.moves.contains(trueMove1));
    Assertions.assertTrue(rook.moves.contains(trueMove2));
    Assertions.assertTrue(rook.moves.contains(trueMove3));
    Assertions.assertFalse(rook.moves.contains(falseMove1));
  }

  @Test
  public void testChained()
  {

  }

}
