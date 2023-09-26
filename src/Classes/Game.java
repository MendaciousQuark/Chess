public class Game {

  private int turn;
  private Board board;
  private Player player1;
  private Player player2;

  public Game() {
    turn = 0;
    board = new Board();
    player1 = new Human(true, true);
    player2 = new Bot(true, false, board.evaluate());
  }

  public Game(boolean twoPlayer)
  {
    turn = 0;
    board = new Board();
    player1 = new Human(true, true);
    player2 = new Bot(true, false, board.evaluate());
    if(twoPlayer)
    {
      player1 = new Human(true, true);
      player2 =  new Human(true, false);
    }
  }

  //On odd turns players with a true(white) colour make a turn.
  //Each turn, the player inputs/generates a move.
  //The piece being move checks whether the move is legal.
  //If the move is legal, the move is made.
  //then the game checks if the opposing king is now in check.
  //  if in check, check for checkmate
  //    if in checkmate. end the game
  //  mark the king as in check
  //add one to turns and loop round to the beginning.
  private void play()
  {
    while (true)
    {
      Player currentPlayer = (turn % 2 == 0) ? player1 : player2;
      board.setTurn(turn);
      System.out.println("\n");
      board.display();
      System.out.println("\n");

      // Prompt the player for a move input or generate a move programmatically
      Move move = currentPlayer.getMove(board);

      while(!board.isValidMove(move))
      {
        System.out.println("Move is illegal. Try Again.");
        move = currentPlayer.getMove(board);
      }

      // Make the move on the board
      board.makeMove(move);

      // Check if the opposing king is now in check
      boolean opposingColor = !currentPlayer.colour;
      if (board.isKingInCheck(opposingColor))
      {
        // If in check, check for checkmate
        if (board.isCheckmate(opposingColor))
        {
          // End the game (checkmate)
          String winner = (currentPlayer.colour)? "White":"Black";
          System.out.println("Checkmate! " + winner + " wins!");
          break;
        }
        else
        {
          // Mark the opposing king as in check
          board.changeCheckStatus(opposingColor, true);
        }
      }
      else
      {
        // Opposing king is not in check
        board.changeCheckStatus(opposingColor, false);
      }

      // Add one to turns and continue to the next turn
      turn++;
    }
  }


  public int getTurn()
  {
    return turn;
  }

  public Board getBoard()
  {
    return board;
  }


  public static void main(String[] args) {
    //index out of range when accessing h file
    Game game = new Game(true);
    game.play();
  }
}
