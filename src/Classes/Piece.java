import java.util.ArrayList;
import java.util.Arrays;

public abstract class Piece
{

  protected int posI, posJ;
  protected boolean colour;
  protected int value;
  protected ArrayList<Move> moves = new ArrayList<>();
  protected Piece pinningPiece;

  Piece(int posI, int posJ, boolean colour, int value)
  {
    this.posI = posI;
    this.posJ = posJ;
    this.colour = colour;
    this.value = value;
  }

  protected String getName()
  {
    return "Piece";
  }

  @Override
  public boolean equals(Object obj)
  {
    if(this == obj)
    {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    return sameAttributes((Piece) obj);
  }

  private boolean sameAttributes(Piece other)
  {
    return this.colour == other.colour &&
           this.value == other.value;
  }

  protected void addNonCaptureMove(Board board, int turn, int destI, int destJ)
  {
    int [] start = setStart(posI, posJ);
    int [] end = setEnd(destI, destJ);
    Move newMove = new Move(board, start, end, this,  colour, turn, false, false, false);
    if(!moves.contains(newMove))
    {
      moves.add(newMove);
    }

  }

  protected void addCaptureMove(Board board, int turn, int destI, int destJ)
  {
    int [] start = setStart(posI, posJ);
    int [] end = setEnd(destI, destJ);
    Move newMove = new Move(board, start, end, this,  colour, turn, true, false, false);
    if(!moves.contains(newMove))
    {
      moves.add(newMove);
    }
  }

  protected int[] setStart(int i, int j) {
    // Create a new integer array representing the start position
    int[] start = new int[2];
    // Set the row and column values for the start position
    start[0] = i;
    start[1] = j;
    // Return the start position array
    return start;
  }

  protected int[] setEnd(int i, int j) {
    // Create a new integer array representing the end position
    int[] end = new int[2];
    // Set the row and column values for the end position
    end[0] = i;
    end[1] = j;
    // Return the end position array
    return end;
  }

  protected abstract void findMoves(Board board, int turn);

  protected void findDiagonalMoves(Board board, int turn)
  {
    int nextRow, nextCol;
    for(int rowOffset : new int[]{-1, 1})
    {
      for(int colOffset : new int[] {-1, 1})
      {
        nextRow = posI + rowOffset;
        nextCol = posJ + colOffset;
        while(board.isValidCoordinate(nextRow, nextCol))
        {
          Square targetSquare = board.getSquare(nextRow, nextCol);
          //capturing piece
          if(targetSquare.isOccupiedByOpponent(this.colour))
          {
            addCaptureMove(board, turn, nextRow, nextCol);
            break;
          }
          else if(!targetSquare.occupied)
          {
            addNonCaptureMove(board, turn, nextRow, nextCol);
          }
          //non-capture move
          else
          {
            break;
          }
          nextCol += colOffset;
          nextRow += rowOffset;
        }
      }
    }
  }

  protected void findVerticalMoves(Board board, int turn)
  {
    for(int rowOffset : new int[]{-1, 1})
    {
      int nextRow = posI + rowOffset;
      while(board.isValidCoordinate(nextRow, posJ))
      {
        Square targetSquare = board.getSquare(nextRow, posJ);
        if(targetSquare.isOccupiedByOpponent(this.colour))
        {
          addCaptureMove(board, turn, nextRow, posJ);
          break;
        }
        else if(!targetSquare.occupied)
        {
          addNonCaptureMove(board, turn, nextRow, posJ);
        }
        else
        {
          break;
        }
        nextRow += rowOffset;
      }
    }
  }

  protected void findHorizontalMoves(Board board, int turn)
  {
    for(int colOffset : new int[]{-1, 1})
    {
      int nextCol = posJ + colOffset;
      while(board.isValidCoordinate(posI, nextCol))
      {
        Square targetSquare = board.getSquare(posI, nextCol);
        if(targetSquare.isOccupiedByOpponent(this.colour))
        {
          addCaptureMove(board, turn, posI, nextCol);
          break;
        }
        else if(!targetSquare.occupied)
        {
          addNonCaptureMove(board, turn, posI, nextCol);
        }
        else
        {
          break;
        }
        nextCol += colOffset;
      }
    }
  }

  protected boolean isPinned(Board board)
  {
    if(board.squareIsAttacked(this.colour, this.posI, this.posJ))
    {
      //get the square this piece is on
      Square currentSquare = board.getSquare(this.posI, this.posJ);

      // get all kings on the board
      ArrayList<Piece> kings = board.findPieces(King.class);

      //placeholder king Declaration
      King king = new King(0, 0, false, 100);
      for(Piece piece: kings)
      {
        if(piece.colour == this.colour)
        {
          // point king to the King of the same colour as this piece
          king = (King) piece;
          break;
        }
      }

      //for every piece attacking this square
      for(Piece attackingPiece: currentSquare.attackingPieces)
      {
        if(attackingPiece.colour != this.colour)
        {
          //if the piece can pin
          if(attackingPiece instanceof Queen || attackingPiece instanceof Bishop || attackingPiece instanceof Rook)
          {
            //check the direction it is attacking from
            int[] attackDirection = getAttackDirection(attackingPiece);
            if(attackDirection != null)
            {
              int rowOffset = attackDirection[0];
              int colOffset = attackDirection[1];
              int nextRow = this.posI + rowOffset;
              int nextCol = this.posJ + colOffset;
              boolean validSquare = board.isValidCoordinate(nextRow, nextCol);
              int piecesEncountered = 0;

              //continue in the direction it is attacking from while no other piece has been encountered
              // and the square is still on the board
              while(validSquare && piecesEncountered == 0)
              {
                Square targetSquare = board.getSquare(nextRow, nextCol);
                // the next square is occupied
                if(targetSquare.occupied)
                {
                  piecesEncountered += 1;
                  //if the encountered piece is this piece's king
                  if(targetSquare.piece == king)
                  {
                    //set the attacking piece as the pining piece as a piece can only ever be pinned by one piece
                    pinningPiece = attackingPiece;
                    //piece is pinned
                    return true;
                  }
                }

                nextRow += rowOffset;
                nextCol += colOffset;
                validSquare = board.isValidCoordinate(nextRow, nextCol);
              }
            }
          }
        }
      }
    }
    //otherwise, the piece is not pinned
    return false;
  }

  protected void removeMovesIfPinned(Board board)
  {
    // Check if the piece is pinned
    if (isPinned(board))
    {
      // Remove capture moves that do not free or maintain the pin
      moves.removeIf(move -> !freesPin(move, board) && !staysInPin(move, board));
    }
  }

  protected boolean freesPin(Move move, Board board)
  {
    // Check if the target coordinates of the move are valid
    if(board.isValidCoordinate(move.getEnd()[0], move.getEnd()[1]))
    {
      // Get the square at the target coordinates
      Square targetSquare = board.getSquare(move.getEnd()[0], move.getEnd()[1]);

      // Check if the target square is occupied by the same piece that was causing the pin
      return targetSquare.occupied && targetSquare.piece == pinningPiece;
    }
    else
    {
      // If the coordinates are invalid, print an error message
      System.err.println("Invalid coordinates for move");

      // Exit the program with an error code of 1
      System.exit(1);
    }

    // Return false if the move does not free the pin
    return false;
  }

  protected boolean staysInPin(Move move, Board board)
  {
    //get attack direction of the pinning piece
    int[] attackDirection = getAttackDirection(pinningPiece);
    //reverse the direction, so we can iterate towards the pinning piece
    attackDirection[0] = attackDirection[0] * -1;
    attackDirection[1] = attackDirection[1] * -1;

    //determine the next square
    int nextRow = this.posI + attackDirection[0];
    int nextCol = this.posJ + attackDirection[1];

    //until the coordinates are invalid, or we have reached the piece
    while(board.isValidCoordinate(nextRow, nextCol) && (nextRow != pinningPiece.posI || nextCol != pinningPiece.posJ))
    {
      //if the move we are looking at ends in a square we are iterating over
      if(move.getEnd()[0] == nextRow && move.getEnd()[1] == nextCol)
      {
        //the piece moves in a way that the pin is still held (the king is not in check)
        return true;
      }
      nextRow += attackDirection[0];
      nextCol += attackDirection[1];
    }
    //otherwise, the piece moves out of the pin or captures the pinning piece
    return false;
  }

  protected int[] getAttackDirection(Piece attackingPiece)
  {
    int[] diagonalDirection = getDiagonalAttackDirection(attackingPiece);
    int[] straightLineDirection = getStraightLineAttackDirection(attackingPiece);

    // Check if either diagonalDirection or straightLineDirection indicates an attack
    if (diagonalDirection[0] != 0 && diagonalDirection[1] != 0)
    {
      // Diagonal attack
      return diagonalDirection;
    }
    else if (straightLineDirection[0] != 0 || straightLineDirection[1] != 0)
    {
      // Straight-line attack (horizontal or vertical)
      return straightLineDirection;
    }
    else
    {
      // No valid attack direction
      return null;
    }
  }

  private int[] getDiagonalAttackDirection(Piece attackingPiece) {
    int rowDiff = this.posI - attackingPiece.posI;
    int colDiff = this.posJ - attackingPiece.posJ;

    int[] direction = new int[2];

    if (Math.abs(rowDiff) == Math.abs(colDiff))
    {
      if (rowDiff > 0 && colDiff > 0)
      {
        // Piece is attacking along the top-right diagonal
        direction[0] = 1;
        direction[1] = 1;
      }
      else if (rowDiff > 0 && colDiff < 0)
      {
        // Piece is attacking along the top-left diagonal
        direction[0] = 1;
        direction[1] = -1;
      }
      else if (rowDiff < 0 && colDiff > 0)
      {
        // Piece is attacking along the bottom-right diagonal
        direction[0] = -1;
        direction[1] = 1;
      }
      else if (rowDiff < 0 && colDiff < 0)
      {
        // Piece is attacking along the bottom-left diagonal
        direction[0] = -1;
        direction[1] = -1;
      }
    }

    return direction;
  }

  private int[] getStraightLineAttackDirection(Piece attackingPiece)
  {
    int rowDiff = this.posI - attackingPiece.posI;
    int colDiff = this.posJ - attackingPiece.posJ;

    int[] direction = {0, 0};

    if (rowDiff == 0)
    {
      // Piece is attacking horizontally
      direction[1] = (colDiff > 0) ? 1 : -1;
    }
    else if (colDiff == 0)
    {
      // Piece is attacking vertically
      direction[0] = (rowDiff > 0) ? 1 : -1;
    }

    return direction;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Type: ").append(getName()).append("\n");
    sb.append("Position: ").append(posI).append(", ").append(posJ).append("\n");
    sb.append("Colour: ").append(colour ? "White" : "Black").append("\n");
    sb.append("Value: ").append(value).append("\n");

    sb.append("Moves: [\n");
    for (Move move : moves) {
      sb.append("  Start: ").append(Arrays.toString(move.getStart())).append(", ");
      sb.append("End: ").append(Arrays.toString(move.getEnd())).append("\n");
    }
    sb.append("]\n");

    return sb.toString();
  }

  protected abstract Piece copy();
}
