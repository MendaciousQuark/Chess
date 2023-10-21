import java.util.Scanner;

public class Game {

  private int turn;
  private Board board;
  private Player player1;
  private Player player2;

  public Game() {
    turn = 0;
    board = new Board();
    player1 = new Human(true, true);
    player2 = new Bot(true, false);
  }

  public Game(boolean singlePlayer)
  {
    turn = 0;
    board = new Board();

    if(!singlePlayer)
    {
      player1 = new Human(true, true);
      player2 =  new Human(true, false);
    }
    else
    {
      player1 = new Human(true, true);
      player2 = new Bot(true, false);
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
      System.out.println(board.evaluate());
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

      //if the player moved a pawn
      if(move.getPiece() instanceof Pawn pawn)
      {
        //determine its promotion rank
        int rank = (pawn.colour)? 0:7;
        //if the pawn is on its promotion rank
        if(move.getEnd()[0] == rank)
        {
          Square currentSquare  = board.getSquare(move.getEnd()[0], move.getEnd()[1]);
          //ask for and place the promoted piece from the player/bot
          currentSquare.piece = currentPlayer.promotePawn(pawn);
        }
      }

      // Check if the opposing king is now in check
      boolean opposingColor = !currentPlayer.colour;
      if (board.isKingInCheck(opposingColor))
      {
        //mark opposing king as in check
        board.changeCheckStatus(opposingColor, true);
        // If in check, check for checkmate
        if (board.isCheckOrStalemate(opposingColor))
        {
          // End the game (checkmate)
          String winner = (currentPlayer.colour)? "White":"Black";
          System.out.println("Checkmate! " + winner + " wins!");
          break;
        }
      }
      else
      {
        //if not in check but there are no more move
        if(board.isCheckOrStalemate(opposingColor))
        {
          System.out.println("Stalemate... no one wins!");
          break;
        }
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
    //start a new game (two player assumed for now)
    Scanner scanner = new Scanner(System.in);
    boolean isSinglePlayer;

    System.out.println("Welcome to Chess Game!");
    System.out.print("Choose a game mode (1 for Single Player, 2 for Multiplayer): ");

    int choice = scanner.nextInt();

    // Check user's choice and set the boolean variable accordingly
    if (choice == 1) {
      isSinglePlayer = true;
      System.out.println("You have selected Single Player mode.");
    } else if (choice == 2) {
      isSinglePlayer = false;
      System.out.println("You have selected Multiplayer mode.");
    } else {
      System.out.println("Invalid choice. Defaulting to Single Player mode.");
      isSinglePlayer = true;
    }
    Game game = new Game(isSinglePlayer);
    game.play();
  }
}
