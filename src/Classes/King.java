public class King extends Piece
{

  private boolean moved;
  private boolean inCheck;

  King(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
    moved = false;
    inCheck = false;
  }

  @Override
  protected void findMoves(Board board, int turn)
  {
    findDiagonalMoves(board, turn);
    findVerticalMoves(board, turn);
    findHorizontalMoves(board, turn);
  }

  @Override
  protected void findDiagonalMoves(Board board, int turn)
  {
    for(int rowOffset: new int[] {-1,1})
    {
      for(int colOffset: new int[] {-1,1})
      {
        int nextRow = posI + rowOffset;
        int nextCol = posJ + colOffset;
        if(board.isValidCoordinate(nextRow, nextCol))
        {
          Square targetSquare = board.getSquare(nextRow, nextCol);
          if(!board.isSquareAttacked(this.colour, nextRow, nextCol, turn))
          {
            if(targetSquare.isOccupiedByOpponent(this.colour))
            {
              addCaptureMove(board, turn, nextRow, nextCol);
            }
            else if(!targetSquare.occupied)
            {
              addNonCaptureMove(board, turn, nextRow, nextCol);
            }
            else
            {
              break;
            }
          }
        }
      }
    }
  }

  @Override
  protected void findVerticalMoves(Board board, int turn)
  {
    for(int rowOffset: new int[] {-1,1})
    {
      int nextRow = posI + rowOffset;
      int nextCol = posJ;
      if(board.isValidCoordinate(nextRow, nextCol))
      {
        Square targetSquare = board.getSquare(nextRow, nextCol);
        if(!board.isSquareAttacked(this.colour, nextRow, nextCol, turn))
        {
          if(targetSquare.isOccupiedByOpponent(this.colour))
          {
            addCaptureMove(board, turn, nextRow, nextCol);
          }
          else if(!targetSquare.occupied)
          {
            addNonCaptureMove(board, turn, nextRow, nextCol);
          }
        }
      }
    }
  }

  @Override
  protected void findHorizontalMoves(Board board, int turn)
  {
    for(int colOffset: new int[] {-1,1})
    {
      int nextRow = posI;
      int nextCol = posJ + colOffset;
      if(board.isValidCoordinate(nextRow, nextCol))
      {
        Square targetSquare = board.getSquare(nextRow, nextCol);
        if(!board.isSquareAttacked(this.colour, nextRow, nextCol, turn))
        {
          if(targetSquare.isOccupiedByOpponent(this.colour))
          {
            addCaptureMove(board, turn, nextRow, nextCol);
          }
          else if(!targetSquare.occupied)
          {
            addNonCaptureMove(board, turn, nextRow, nextCol);
          }
        }
      }
    }
  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "K":"k";
  }

  public boolean isMoved()
  {
    return moved;
  }

  public boolean isInCheck()
  {
    return inCheck;
  }

  public void setMoved(boolean moved)
  {
    this.moved = moved;
  }

  public void setInCheck(boolean inCheck)
  {
    this.inCheck = inCheck;
  }
}
