public class Knight extends Piece
{

  Knight(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn, boolean check)
  {
    if(!chained)
    {
      findHorizontalMoves(board, turn, check);
      findVerticalMoves(board, turn, check);
    }
  }

  @Override
  protected void findHorizontalMoves(Board board, int turn, boolean check)
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
          if(targetSquare.occupied && targetSquare.piece.colour != colour)
          {
            addCaptureMove(board, turn, check, nextRow, nextCol);
          }
          else if (!targetSquare.occupied)
          {
            addNonCaptureMove(board, turn, check, nextRow, nextCol);
          }
        }
      }
    }
  }

  @Override
  protected void findVerticalMoves(Board board, int turn, boolean check)
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
          if(targetSquare.occupied && targetSquare.piece.colour != colour)
          {
            addCaptureMove(board, turn, check, nextRow, nextCol);
          }
          else if (!targetSquare.occupied)
          {
            addNonCaptureMove(board, turn, check, nextRow, nextCol);
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
