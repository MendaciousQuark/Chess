public class Game {

  private int turn;
  private Board board;
  private Player player;
  private Bot bot;

  public Game() {
    turn = 0;
    board = new Board();
    player = new Player(true, true);
    bot = new Bot(true, false, board.evaluate());
  }

  public static void main(String[] args) {
    Game game = new Game();
  }
}
