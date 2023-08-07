import java.util.ArrayList;

public abstract class Piece
{

  protected int posI, posJ;
  protected boolean colour;
  protected int value;
  protected boolean chained;
  protected ArrayList<Move> moves = new ArrayList<>();

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

  protected abstract void findMoves(Board board, int turn, boolean check);

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

  protected void addNonCaptureMove(Board board, int turn, boolean check, int destI, int destJ)
  {
    int [] start = setStart(posI, posJ);
    int [] end = setEnd(destI, destJ);
    moves.add(new Move(board, start, end, this,  colour, turn, false, false, false, false));

  }

  protected void addCaptureMove(Board board, int turn, boolean check, int destI, int destJ)
  {
    int [] start = setStart(posI, posJ);
    int [] end = setEnd(destI, destJ);
    moves.add(new Move(board, start, end, this,  colour, turn, true, false, false, false));
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

  protected void findDiagonalMoves(Board board, int turn, boolean check)
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
          if(targetSquare.occupied && targetSquare.piece.colour != colour)
          {
            addCaptureMove(board, turn, check, nextRow, nextCol);
            break;
          }
          //illegal move (capturing own piece)
          else if(targetSquare.occupied)
          {
            break;
          }
          //non-capture move
          else
          {
            addNonCaptureMove(board, turn, check, nextRow, nextCol);
          }
          nextCol += colOffset;
          nextRow += rowOffset;
        }
      }
    }
  }

  protected void findVerticalMoves(Board board, int turn, boolean check)
  {
    for(int rowOffset : new int[]{-1, 1})
    {
      int nextRow = posI + rowOffset;
      while(board.isValidCoordinate(nextRow, posJ))
      {
        Square targetSquare = board.getSquare(nextRow, posJ);
        if(targetSquare.occupied && targetSquare.piece.colour != colour)
        {
          addCaptureMove(board, turn, check, nextRow, posJ);
          break;
        }
        //illegal move (capturing own piece)
        else if(targetSquare.occupied)
        {
          break;
        }
        //non-capture move
        else
        {
          addNonCaptureMove(board, turn, check, nextRow, posI);
        }
        nextRow += rowOffset;
      }
    }
  }

  protected void findHorizontalMoves(Board board, int turn, boolean check)
  {
    for(int colOffset : new int[]{-1, 1})
    {
      int nextCol = posJ + colOffset;
      while(board.isValidCoordinate(posI, nextCol))
      {
        Square targetSquare = board.getSquare(posI, nextCol);
        if(targetSquare.occupied && targetSquare.piece.colour != colour)
        {
          addCaptureMove(board, turn, check, posI, nextCol);
          break;
        }
        //illegal move (capturing own piece)
        else if(targetSquare.occupied)
        {
          break;
        }
        //non-capture move
        else
        {
          addNonCaptureMove(board, turn, check, posI, nextCol);
        }
        nextCol += colOffset;
      }
    }
  }
  
}
