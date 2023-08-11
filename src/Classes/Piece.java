import java.util.ArrayList;
import java.util.Arrays;

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
    moves.add(new Move(board, start, end, this,  colour, turn, false, false, false));

  }

  protected void addCaptureMove(Board board, int turn, int destI, int destJ)
  {
    int [] start = setStart(posI, posJ);
    int [] end = setEnd(destI, destJ);
    moves.add(new Move(board, start, end, this,  colour, turn, true, false, false));
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Type: ").append(getName()).append("\n");
    sb.append("Position: ").append(posI).append(", ").append(posJ).append("\n");
    sb.append("Colour: ").append(colour ? "White" : "Black").append("\n");
    sb.append("Value: ").append(value).append("\n");
    sb.append("Chained: ").append(chained).append("\n");

    sb.append("Moves: [\n");
    for (Move move : moves) {
      sb.append("  Start: ").append(Arrays.toString(move.getStart())).append(", ");
      sb.append("End: ").append(Arrays.toString(move.getEnd())).append("\n");
    }
    sb.append("]\n");

    return sb.toString();
  }


}
