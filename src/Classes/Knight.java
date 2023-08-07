public class Knight extends Piece
{

  Knight(int posX, int posY, boolean colour, int value)
  {
    super(posX, posY, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn, boolean check)
  {
    if(!chained)
    {
      findHorizontal(board, turn, check);
      findVertical(board, turn, check);
    }
  }

  private void findHorizontal(Board board, int turn, boolean check)
  {
    int nextRow,nextCol;
    //horizontal
    for(int rowOffset : new int[]{-1, 1})
    {
      for(int colOffset : new int[]{-2, 2})
      {
        nextRow = posX + rowOffset;
        nextCol = posY + colOffset;
        if(board.isValidCoordinate(nextRow, nextCol))
        {
          Square targetSquare = board.getSquare(nextRow, nextCol);
          if(targetSquare.occupied && targetSquare.piece.colour != colour)
          {
            addCaptureMove(board, turn, check, nextCol, nextRow);
          }
          else if (!targetSquare.occupied)
          {
            addNonCaptureMove(board, turn, check, nextCol, nextRow);
          }
        }
      }
    }
  }

  private void findVertical(Board board, int turn, boolean check)
  {
    int nextRow,nextCol;
    //horizontal
    for(int rowOffset : new int[]{-2, 2})
    {
      for(int colOffset : new int[]{-1, 1})
      {
        nextRow = posX + rowOffset;
        nextCol = posY + colOffset;
        if(board.isValidCoordinate(nextRow, nextCol))
        {
          Square targetSquare = board.getSquare(nextRow, nextCol);
          if(targetSquare.occupied && targetSquare.piece.colour != colour)
          {
            addCaptureMove(board, turn, check, nextCol, nextRow);
          }
          else if (!targetSquare.occupied)
          {
            addNonCaptureMove(board, turn, check, nextCol, nextRow);
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
