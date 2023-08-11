import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BishopTests
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
    board.setBoard("rnbqkbnr/pppppppp/8/3B4/8/8/PPPPPPPP/RN1QKBNR");

    Bishop bishop = (Bishop) board.getSquare(3, 3).piece;
    bishop.findMoves(board, turn);

    // Set the start and end positions for the first move
    int[] start = bishop.setStart(3, 3);
    int[] end =  bishop.setEnd(2, 2);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, bishop, bishop.colour, turn, false, false, false);

    // Set the start and end positions for the second move
    start = bishop.setStart(3, 3);
    end =  bishop.setEnd(4, 2);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, bishop, bishop.colour, turn, false, false, false);

    // Set the start and end positions for the third move
    start = bishop.setStart(3, 3);
    end =  bishop.setEnd(4, 4);
    // Create the expected move object for the third move
    Move trueMove3 = new Move(board, start, end, bishop, bishop.colour, turn, false, false, false);

    // Set the start and end positions for the fourth move
    start = bishop.setStart(3, 3);
    end =  bishop.setEnd(2, 4);
    // Create the expected move object for the fourth move
    Move trueMove4 = new Move(board, start, end, bishop, bishop.colour, turn, false, false, false);

    // Set the start and end positions for the fifth move
    start = bishop.setStart(3, 3);
    end =  bishop.setEnd(5, 1);
    // Create the expected move object for the fifth move
    Move trueMove5 = new Move(board, start, end, bishop, bishop.colour, turn, false, false, false);

    // Set the start and end positions for the sixth move
    start = bishop.setStart(3, 3);
    end =  bishop.setEnd(5, 5);
    // Create the expected move object for the sixth move
    Move trueMove6 = new Move(board, start, end, bishop, bishop.colour, turn, false, false, false);

    // Set the start and end positions for the first illegal move
    start = bishop.setStart(3, 3);
    end =  bishop.setEnd(6, 0);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, bishop, bishop.colour, turn, false, false, false);

    // Set the start and end positions for the second illegal move
    start = bishop.setStart(3, 3);
    end =  bishop.setEnd(6, 6);
    // Create the expected move object for the second illegal move
    Move falseMove2 = new Move(board, start, end, bishop, bishop.colour, turn, false, false, false);

    Assertions.assertEquals(8, bishop.moves.size());
    Assertions.assertTrue(bishop.moves.contains(trueMove1));
    Assertions.assertTrue(bishop.moves.contains(trueMove2));
    Assertions.assertTrue(bishop.moves.contains(trueMove3));
    Assertions.assertTrue(bishop.moves.contains(trueMove4));
    Assertions.assertTrue(bishop.moves.contains(trueMove6));
    Assertions.assertFalse(bishop.moves.contains(falseMove1));
    Assertions.assertFalse(bishop.moves.contains(falseMove2));
  }

  @Test
  public void testCapture()
  {
    int turn = 1;
    board.setBoard("rnbqkbnr/pppppppp/8/3B4/8/8/PPPPPPPP/RN1QKBNR");

    Bishop bishop = (Bishop) board.getSquare(3, 3).piece;
    bishop.findMoves(board, turn);

    // Set the start and end positions for the first move
    int[] start = bishop.setStart(3, 3);
    int[] end =  bishop.setEnd(1, 1);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, bishop, bishop.colour, turn, true, false, false);

    // Set the start and end positions for the second move
    start = bishop.setStart(3, 3);
    end =  bishop.setEnd(1, 5);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, bishop, bishop.colour, turn, true, false, false);

    // Set the start and end positions for the first illegal move
    start = bishop.setStart(3, 3);
    end =  bishop.setEnd(0, 0);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, bishop, bishop.colour, turn, true, false, false);


    Assertions.assertEquals(8, bishop.moves.size());
    Assertions.assertTrue(bishop.moves.contains(trueMove1));
    Assertions.assertTrue(bishop.moves.contains(trueMove2));
    Assertions.assertFalse(bishop.moves.contains(falseMove1));
  }

  @Test
  public void testChained()
  {

  }
}
