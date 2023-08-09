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

    // Set the start and end positions for the first move
    int[] start = king.setStart(4, 4);
    int[] end =  king.setEnd(3, 3);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    // Set the start and end positions for the second move
    start = king.setStart(4, 4);
    end =  king.setEnd(3, 5);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    // Set the start and end positions for the third move
    start = king.setStart(4, 4);
    end =  king.setEnd(5, 3);
    // Create the expected move object for the third move
    Move trueMove3 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    // Set the start and end positions for the first illegal move
    start = king.setStart(4, 4);
    end =  king.setEnd(6, 6);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    Assertions.assertEquals(6, king.moves.size());
    Assertions.assertTrue(king.moves.contains(trueMove1));
    Assertions.assertTrue(king.moves.contains(trueMove2));
    Assertions.assertTrue(king.moves.contains(trueMove3));
    Assertions.assertFalse(king.moves.contains(falseMove1));
  }

  @Test
  public void testCapture()
  {
    board.setBoard("r1bqkbnr/pppp1ppp/8/3np3/4KN2/8/PP1PPPPP/RNBQPB1R");

    King king = (King) board.getSquare(4, 4).piece;
    king.findMoves(board, turn, check);

    // Set the start and end positions for the first move
    int[] start = king.setStart(4, 4);
    int[] end =  king.setEnd(3, 3);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, king, king.colour, turn, true, check, false, false);

    // Set the start and end positions for the second move
    start = king.setStart(4, 4);
    end =  king.setEnd(3, 5);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, king, king.colour, turn, true, check, false, false);

    // Set the start and end positions for the first illegal move
    start = king.setStart(4, 4);
    end =  king.setEnd(4, 5);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, king, king.colour, turn, true, check, false, false);

    Assertions.assertEquals(7, king.moves.size());
    Assertions.assertTrue(king.moves.contains(trueMove1));
    Assertions.assertTrue(king.moves.contains(trueMove2));
    Assertions.assertFalse(king.moves.contains(falseMove1));

  }

  @Test
  public void testCastle()
  {
    board.setBoard("r3k2r/ppp1np1p/2np2p1/2b1pbq1/Q1P1P3/BPNB2N1/P2P1PPP/R3K2R ");

    King king = (King) board.getSquare(7, 4).piece;
    king.setMoved(false);
    king.findMoves(board, turn, check);

    // Set the start and end positions for the first move
    int[] start = king.setStart(7, 4);
    int[] end =  king.setEnd(7, 2);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, king, true, turn,false, false, check);

    // Set the start and end positions for the second move
    start = king.setStart(7, 4);
    end =  king.setEnd(7, 6);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, king, true, turn,false, false, check);

    // Set the start and end positions for the first illegal move
    start = king.setStart(7, 4);
    end =  king.setEnd(4, 5);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, king, king.colour, turn, true, check, false, false);

    Assertions.assertEquals(4, king.moves.size());
    Assertions.assertTrue(king.moves.contains(trueMove1));
    Assertions.assertTrue(king.moves.contains(trueMove2));
    Assertions.assertFalse(king.moves.contains(falseMove1));
  }

  @Test
  public void testInCheck()
  {
    board.setBoard("r3k2r/ppp1np1p/3p2p1/2b1pb1N/Q1P1P2q/BPN2P2/P2PB1PP/R2nK2R");

    King king = (King) board.getSquare(7, 4).piece;
    king.setInCheck(true);
    king.findMoves(board, turn, check);

    // Set the start and end positions for the first move
    int[] start = king.setStart(7, 4);
    int[] end =  king.setEnd(7, 3);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, king, king.colour, turn, true, check, false, false);

    // Set the start and end positions for the second move
    start = king.setStart(4, 4);
    end =  king.setEnd(7, 5);
    // Create the expected move object for the second move
    Move trueMove2 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    // Set the start and end positions for the first illegal move
    start = king.setStart(7, 4);
    end =  king.setEnd(6, 5);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, king, king.colour, turn, true, check, false, false);

    Assertions.assertEquals(2, king.moves.size());
    Assertions.assertTrue(king.moves.contains(trueMove1));
    Assertions.assertTrue(king.moves.contains(trueMove2));
    Assertions.assertFalse(king.moves.contains(falseMove1));

  }

  @Test
  public void testMoveIntoCheck()
  {
    board.setBoard("rn2kbnr/pppppppp/1b3q2/8/4K3/3P4/PPP1PPPP/RNBQ1BNR");

    King king = (King) board.getSquare(4, 4).piece;
    king.findMoves(board, turn, check);

    // Set the start and end positions for the first move
    int[] start = king.setStart(4, 4);
    int[] end =  king.setEnd(3, 3);
    // Create the expected move object for the first move
    Move trueMove1 = new Move(board, start, end, king, king.colour, turn, true, check, false, false);

    // Set the start and end positions for the first illegal move
    start = king.setStart(4, 4);
    end =  king.setEnd(4, 3);
    // Create the expected move object for the first illegal move
    Move falseMove1 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    // Set the start and end positions for the second illegal move
    start = king.setStart(4, 4);
    end =  king.setEnd(3, 5);
    // Create the expected move object for the second illegal move
    Move falseMove2 = new Move(board, start, end, king, king.colour, turn, false, check, false, false);

    Assertions.assertEquals(1, king.moves.size());
    Assertions.assertTrue(king.moves.contains(trueMove1));
    Assertions.assertFalse(king.moves.contains(falseMove1));
    Assertions.assertFalse(king.moves.contains(falseMove2));
  }

}
