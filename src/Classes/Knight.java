public class Knight extends Piece
{

  Knight(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn)
  {
    if(!chained)
    {
      findHorizontalMoves(board, turn);
      findVerticalMoves(board, turn);
    }
  }

  @Override
  protected void findHorizontalMoves(Board board, int turn)
  {
    int nextRow,nextCol;
    //horizontal
    for(int rowOffset : new int[]{-1, 1})
    {
      for(int colOffset : new int[]{-2, 2})
      {
        nextRow = posI + rowOffset;
        nextCol = posJ + colOffset;
        if(board.isValidCoordinate(nextRow, nextCol))
        {
          Square targetSquare = board.getSquare(nextRow, nextCol);
          if(targetSquare.isOccupiedByOpponent(this.colour))
          {
            addCaptureMove(board, turn, nextRow, nextCol);
          }
          else if (!targetSquare.occupied)
          {
            addNonCaptureMove(board, turn, nextRow, nextCol);
          }
        }
      }
    }
  }

  @Override
  protected void findVerticalMoves(Board board, int turn)
  {
    int nextRow,nextCol;
    //horizontal
    for(int rowOffset : new int[]{-2, 2})
    {
      for(int colOffset : new int[]{-1, 1})
      {
        nextRow = posI + rowOffset;
        nextCol = posJ + colOffset;
        if(board.isValidCoordinate(nextRow, nextCol))
        {
          Square targetSquare = board.getSquare(nextRow, nextCol);
          if(targetSquare.isOccupiedByOpponent(this.colour))
          {
            addCaptureMove(board, turn, nextRow, nextCol);
          }
          else if (!targetSquare.occupied)
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
    return (this.colour) ? "N":"n";
  }

}
