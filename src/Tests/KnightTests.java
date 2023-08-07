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
    int[] start = knight.setStart(1, 3);
    int[] end =  knight.setEnd(2, 3);
    // Create the expected move object for the first move
    Move move1 = new Move(board, start, end, knight, knight.colour, turn, false, false, false, false);

  }

  @Test
  public void testCapture()
  {
  }

  @Test
  public void testChained()
  {
  }
}
