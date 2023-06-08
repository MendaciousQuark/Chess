import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Tests
{
  private Board board;
  private Game game;
  @BeforeEach
  public void setUp()
  {
    game = new Game();
    board = new Board();
  }


  @Test
  public void testGameInit()
  {
    Assertions.assertEquals(0, game.getTurn());
    Assertions.assertNotNull(game.getBoard());
    Assertions.assertNotNull(game.getHuman());
    Assertions.assertNotNull(game.getBot());
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
  }

  @Test
  public void testSetAndGetBoard() {
    Square[][] newBoard = new Square[8][8];
    board.setBoard(newBoard);
    Square[][] updatedBoard = board.getBoard();
    Assertions.assertEquals(newBoard, updatedBoard);
  }
}
