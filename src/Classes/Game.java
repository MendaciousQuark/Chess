public class Game {

  private int turn;
  private Board board;
  private Player human;
  private Bot bot;

  public Game() {
    turn = 0;
    board = new Board();
    human = new Human(true, true);
    bot = new Bot(true, false, board.evaluate());
  }

  private void play(Game game)
  {
  }


  public int getTurn()
  {
    return turn;
  }

  public Board getBoard()
  {
    return board;
  }

  public Player getHuman()
  {
    return human;
  }

  public Bot getBot()
  {
    return bot;
  }


  public static void main(String[] args) {
    Game game = new Game();
  }
}
