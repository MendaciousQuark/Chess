import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueenTests
{
  private Board board;

  @BeforeEach
  public void setup()
  {
    board = new Board();
  }

  @Test
  public void testDiagonal()
  {
    int turn = 1;
    boolean check = false;
    board.setBoard("rnbqkbnr/pppppppp/8/3Q4/8/8/PPPPPPPP/RN1QKBNR");

    Queen queen = (Queen) board.getSquare(3, 3).piece;
    queen.findMoves(board, turn, check);

    // Set the start and end positions for the first move
    int[] start = queen.setStart(3, 3);
    int[] end =  queen.setEnd(2, 2);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    // Set the start and end positions for the second move
    start = queen.setStart(3, 3);
    end =  queen.setEnd(4, 2);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    // Set the start and end positions for the third move
    start = queen.setStart(3, 3);
    end =  queen.setEnd(4, 4);
    // Create the expected move object for the third move
    Move trueMove3 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    // Set the start and end positions for the fourth move
    start = queen.setStart(3, 3);
    end =  queen.setEnd(2, 4);
    // Create the expected move object for the fourth move
    Move trueMove4 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    // Set the start and end positions for the fifth move
    start = queen.setStart(3, 3);
    end =  queen.setEnd(5, 1);
    // Create the expected move object for the fifth move
    Move trueMove5 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    // Set the start and end positions for the sixth move
    start = queen.setStart(3, 3);
    end =  queen.setEnd(5, 5);
    // Create the expected move object for the sixth move
    Move trueMove6 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    // Set the start and end positions for the first illegal move
    start = queen.setStart(3, 3);
    end =  queen.setEnd(6, 0);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    // Set the start and end positions for the second illegal move
    start = queen.setStart(3, 3);
    end =  queen.setEnd(6, 6);
    // Create the expected move object for the second illegal move
    Move falseMove2 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    Assertions.assertEquals(19, queen.moves.size());
    Assertions.assertTrue(queen.moves.contains(trueMove1));
    Assertions.assertTrue(queen.moves.contains(trueMove2));
    Assertions.assertTrue(queen.moves.contains(trueMove3));
    Assertions.assertTrue(queen.moves.contains(trueMove4));
    Assertions.assertTrue(queen.moves.contains(trueMove5));
    Assertions.assertTrue(queen.moves.contains(trueMove6));
    Assertions.assertFalse(queen.moves.contains(falseMove1));
    Assertions.assertFalse(queen.moves.contains(falseMove2));
  }

  @Test
  public void testStraight()
  {
    int turn = 1;
    boolean check = false;
    board.setBoard("rnbqkbnr/p1p1p1pp/1p1Q1p2/3P4/7P/8/PPP1PPP1/RNBQKBN1");

    Queen queen = (Queen) board.getSquare(2, 3).piece;
    queen.findMoves(board, turn, check);

    // Set the start and end positions for the first move
    int[] start = queen.setStart(2, 3);
    int[] end =  queen.setEnd(1, 3);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    // Set the start and end positions for the second move
    start = queen.setStart(2, 3);
    end =  queen.setEnd(2, 2);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    // Set the start and end positions for the third move
    start = queen.setStart(2, 3);
    end =  queen.setEnd(2, 4);
    // Create the expected move object for the third move
    Move trueMove3 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    // Set the start and end positions for the first illegal move
    start = queen.setStart(2, 3);
    end =  queen.setEnd(3, 3);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, queen, queen.colour, turn, false, check, false, false);

    Assertions.assertEquals(15, queen.moves.size());
    Assertions.assertTrue(queen.moves.contains(trueMove1));
    Assertions.assertTrue(queen.moves.contains(trueMove2));
    Assertions.assertTrue(queen.moves.contains(trueMove3));
    Assertions.assertFalse(queen.moves.contains(falseMove1));
  }

  @Test
  public void testCapture()
  {
    int turn = 1;
    boolean check = false;
    board.setBoard("rnbqkbnr/p1p1p1pp/1p1Q1p2/3P4/7P/8/PPP1PPP1/RNBQKBN1");

    Queen queen = (Queen) board.getSquare(2, 3).piece;
    queen.findMoves(board, turn, check);

    // Set the start and end positions for the first move
    int[] start = queen.setStart(2, 3);
    int[] end =  queen.setEnd(0, 3);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, queen, queen.colour, turn, true, check, false, false);

    // Set the start and end positions for the second move
    start = queen.setStart(2, 3);
    end =  queen.setEnd(1, 2);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, queen, queen.colour, turn, true, check, false, false);

    // Set the start and end positions for the third move
    start = queen.setStart(2, 3);
    end =  queen.setEnd(1, 4);
    // Create the expected move object for the third move
    Move trueMove3 = new Move(board, start, end, queen, queen.colour, turn, true, check, false, false);

    // Set the start and end positions for the fourth move
    start = queen.setStart(2, 3);
    end =  queen.setEnd(2, 1);
    // Create the expected move object for the fourth move
    Move trueMove4 = new Move(board, start, end, queen, queen.colour, turn, true, check, false, false);

    // Set the start and end positions for the fifth move
    start = queen.setStart(2, 3);
    end =  queen.setEnd(2, 5);
    // Create the expected move object for the fifth move
    Move trueMove5 = new Move(board, start, end, queen, queen.colour, turn, true, check, false, false);

    // Set the start and end positions for the first illegal move
    start = queen.setStart(3, 3);
    end =  queen.setEnd(0, 1);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, queen, queen.colour, turn, true, check, false, false);

    // Set the start and end positions for the second illegal move
    start = queen.setStart(3, 3);
    end =  queen.setEnd(2, 6);
    // Create the expected move object for the second illegal move
    Move falseMove2 = new Move(board, start, end, queen, queen.colour, turn, true, check, false, false);

    Assertions.assertEquals(15, queen.moves.size());
    Assertions.assertTrue(queen.moves.contains(trueMove1));
    Assertions.assertTrue(queen.moves.contains(trueMove2));
    Assertions.assertTrue(queen.moves.contains(trueMove3));
    Assertions.assertTrue(queen.moves.contains(trueMove4));
    Assertions.assertTrue(queen.moves.contains(trueMove5));
    Assertions.assertFalse(queen.moves.contains(falseMove1));
    Assertions.assertFalse(queen.moves.contains(falseMove2));

  }

}
