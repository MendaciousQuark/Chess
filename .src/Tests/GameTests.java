import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTests
{
  private Game game;
  @BeforeEach
  public void setUp()
  {
    game = new Game();
  }

  @Test
  public void testGameInit()
  {
    Assertions.assertEquals(0, game.getTurn());
    Assertions.assertNotNull(game.getBoard());
    Assertions.assertNotNull(game.getHuman());
    Assertions.assertNotNull(game.getBot());
  }
}
