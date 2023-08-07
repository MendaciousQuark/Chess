import java.util.ArrayList;

public abstract class Piece
{

  protected int posX, posY;
  protected boolean colour;
  protected int value;
  protected boolean chained;
  protected ArrayList<Move> moves = new ArrayList<>();

  Piece(int posX, int posY, boolean colour, int value)
  {
    this.posX = posX;
    this.posY = posY;
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

  protected void addNonCaptureMove(Board board, int turn, boolean check, int destX, int destY)
  {
    int [] start = setStart(posX, posY);
    int [] end = setEnd(destX, destY);
    moves.add(new Move(board, start, end, this,  colour, turn, true, false, false, false));

  }

  protected void addCaptureMove(Board board, int turn, boolean check, int destX, int destY)
  {
    int [] start = setStart(posX, posY);
    int [] end = setEnd(destX, destY);
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
      nextRow = posY + rowOffset;
      for(int i = 0; i < 2; i++)
      {
        int colOffset = i==0? -1 : 1;
        nextCol = posX + colOffset;
        while(board.isValidCoordinate(nextRow, nextCol))
        {
          Square targetSquare = board.getSquare(nextRow, nextCol);
          //capturing piece
          if(targetSquare.occupied && targetSquare.piece.colour != colour)
          {
            addCaptureMove(board, turn, check, nextCol, nextRow);
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
            addNonCaptureMove(board, turn, check, nextCol, nextRow);
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
      int nextRow = posY + rowOffset;
      while(board.isValidCoordinate(nextRow, posX))
      {
        Square targetSquare = board.getSquare(nextRow, posX);
        if(targetSquare.occupied && targetSquare.piece.colour != colour)
        {
          addCaptureMove(board, turn, check, posX, nextRow);
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
          addNonCaptureMove(board, turn, check, posX, nextRow);
        }
        nextRow += rowOffset;
      }
    }
  }

  protected void findHorizontalMoves(Board board, int turn, boolean check)
  {
    for(int colOffset : new int[]{-1, 1})
    {
      int nextCol = posX + colOffset;
      while(board.isValidCoordinate(posY, nextCol))
      {
        Square targetSquare = board.getSquare(posY, nextCol);
        if(targetSquare.occupied && targetSquare.piece.colour != colour)
        {
          addCaptureMove(board, turn, check, nextCol, posY);
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
          addNonCaptureMove(board, turn, check, nextCol, posY);
        }
        nextCol += colOffset;
      }
    }
  }
  
}
