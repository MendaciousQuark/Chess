import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnightTests
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
    boolean check = false;
    board.setBoard("rnbqkbnr/pppppppp/8/8/3N4/5P2/PPPPP1PP/RNBQKB1R");

    Knight knight = (Knight) board.getSquare(4, 3).piece;
    knight.findMoves(board, turn, check);

    // Set the start and end positions for the first move
    int[] start = knight.setStart(4, 3);
    int[] end =  knight.setEnd(2, 2);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, knight, knight.colour, turn, false, false, false, false);

    // Set the start and end positions for the second move
    start = knight.setStart(4, 3);
    end =  knight.setEnd(3, 1);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, knight, knight.colour, turn, false, false, false, false);

    // Set the start and end positions for the third move
    start = knight.setStart(4, 3);
    end =  knight.setEnd(5, 1);
    // Create the expected move object for the third move
    Move trueMove3 = new Move(board, start, end, knight, knight.colour, turn, false, false, false, false);

    // Set the start and end positions for the first illegal move
    start = knight.setStart(4, 3);
    end =  knight.setEnd(5, 5);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, knight, knight.colour, turn, false, false, false, false);

    // Set the start and end positions for the second illegal move
    start = knight.setStart(4, 3);
    end =  knight.setEnd(6, 4);
    // Create the expected move object for the second illegal move
    Move falseMove2 = new Move(board, start, end, knight, knight.colour, turn, false, false, false, false);

    Assertions.assertEquals(5, knight.moves.size());
    Assertions.assertTrue(knight.moves.contains(trueMove1));
    Assertions.assertTrue(knight.moves.contains(trueMove2));
    Assertions.assertTrue(knight.moves.contains(trueMove3));
    Assertions.assertFalse(knight.moves.contains(falseMove1));
    Assertions.assertFalse(knight.moves.contains(falseMove2));
  }

  @Test
  public void testCapture()
  {
    int turn = 1;
    boolean check = false;
    board.setBoard("rnb1kbn1/pp1ppppp/2p1q3/5r2/3N2P1/8/PPPPP1PP/RNBQKB1R");

    Knight knight = (Knight) board.getSquare(4, 3).piece;
    knight.findMoves(board, turn, check);

    // Set the start and end positions for the first move
    int[] start = knight.setStart(4, 3);
    int[] end =  knight.setEnd(2, 2);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, knight, knight.colour, turn, true, false, false, false);

    // Set the start and end positions for the second move
    start = knight.setStart(4, 3);
    end =  knight.setEnd(2, 4);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, knight, knight.colour, turn, true, false, false, false);

    // Set the start and end positions for the third move
    start = knight.setStart(4, 3);
    end =  knight.setEnd(3, 5);
    // Create the expected move object for the third move
    Move trueMove3 = new Move(board, start, end, knight, knight.colour, turn, true, false, false, false);

    // Set the start and end positions for the first illegal move
    start = knight.setStart(4, 3);
    end =  knight.setEnd(6, 2);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, knight, knight.colour, turn, false, false, false, false);

    // Set the start and end positions for the second illegal move
    start = knight.setStart(4, 3);
    end =  knight.setEnd(6, 4);
    // Create the expected move object for the second illegal move
    Move falseMove2 = new Move(board, start, end, knight, knight.colour, turn, false, false, false, false);

    Assertions.assertEquals(6, knight.moves.size());
    Assertions.assertTrue(knight.moves.contains(trueMove1));
    System.out.println(knight.moves.get(5));
    System.out.println(trueMove2);
    Assertions.assertTrue(knight.moves.contains(trueMove2));
    Assertions.assertTrue(knight.moves.contains(trueMove3));
    Assertions.assertFalse(knight.moves.contains(falseMove1));
    Assertions.assertFalse(knight.moves.contains(falseMove2));
  }

  @Test
  public void testChained()
  {
  }
}
