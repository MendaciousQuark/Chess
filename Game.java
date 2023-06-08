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

  public static void main(String[] args) {
    Game game = new Game();
  }
}
