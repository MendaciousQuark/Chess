import java.util.Scanner;

public class Human  extends Player
{

  public Human(boolean castle, boolean colour)
  {
    super(castle, colour);
  }

  @Override
  protected Move getMove(Board board)
  {
    int[] start;
    int[] end;
    Scanner scanner = new Scanner(System.in);

    while(true)
    {
      try
      {

        // Prompt the user for the start and end locations
        System.out.print("Enter the start location (e.g., 'e4'): ");
        String startNotation = scanner.nextLine();

        System.out.print("Enter the end location (e.g., 'e5'): ");
        String endNotation = scanner.nextLine();

        // Convert chess notations to locations
        start = notationToLocation(startNotation);
        end = notationToLocation(endNotation);

        //find the targeted square
        Square targetSquare = board.getSquare(start[0], start[1]);

        //check if the square is empty
        if(!targetSquare.occupied)
        {
          throw new IllegalArgumentException("The targeted square is empty.");
        }
        //check if the targeted piece is of the same colour
        if(targetSquare.piece.colour != this.colour)
        {
          throw new IllegalArgumentException("The targeted piece is not yours. You can only move your own pieces!");
        }
        break;
      }
      catch(IllegalArgumentException e)
      {
        //output the error message of the caught exception
        System.err.println(e.getMessage());
      }
    }

    //set the relevant fields of the move and return it
    return populateMove(start, end, board);
  }

  //if on first or last row of board and

  private Move populateMove(int[] start, int[] end, Board board)
  {
    //find the start and end squares
    Square startSquare = board.getSquare(start[0], start[1]);
    Square targetSquare = board.getSquare(end[0], end[1]);

    //set the moved piece
    Piece movedPiece = startSquare.piece;

    //if the end square is occupied mark the move as a capture
    boolean isCapture = targetSquare.occupied;
    //if we are trying to move a king
    if(movedPiece instanceof King king)
    {
      boolean isCastle = false;
      //check if the king has moved
      if(!king.isMoved())
      {
        //if it hasn't check if the move conforms either to king or queen side castling
        if(startSquare.posI == targetSquare.posI
           && (Math.abs(targetSquare.posJ - startSquare.posJ) == 2))
        {
          //if it does mark the move as a castle move
          isCastle = true;
        }
      }
      //return the king move
      return  new Move(board, start, end, king, isCastle, board.getTurn(),false, false);
    }
    //if we are not moving a king
    else
    {
      //return a non-king move
      return new Move(board, start, end, movedPiece, colour, board.getTurn(), isCapture, false, false);
    }
  }
}
