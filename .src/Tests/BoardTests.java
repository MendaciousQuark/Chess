import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTests
{
  private Board board;
  @BeforeEach
  public void setUp()
  {
    board = new Board();
  }

  @Test
  public void testBoardInitialization() {
    Square[][] boardArray = board.getBoard();
    Assertions.assertNotNull(boardArray);
    Assertions.assertEquals(8, boardArray.length);
    Assertions.assertEquals(8, boardArray[0].length);
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
}
