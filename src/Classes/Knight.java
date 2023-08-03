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
      findHorizontalMoves(board, turn, check);
      findVerticalMoves(board, turn, check);
    }
  }

  private void findHorizontalMoves(Board board, int turn, boolean check)
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

  private void findVerticalMoves(Board board, int turn, boolean check)
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

  private void addNonCaptureMove(Board board, int turn, boolean check, int destX, int destY)
  {
    int [] start = setStart(posX, posY);
    int [] end = setEnd(destX, destY);
    moves.add(new Move(board, start, end, this,  colour, turn, true, false, false, false));

  }

  private void addCaptureMove(Board board, int turn, boolean check, int destX, int destY)
  {
    int [] start = setStart(posX, posY);
    int [] end = setEnd(destX, destY);
    moves.add(new Move(board, start, end, this,  colour, turn, true, false, false, false));
  }
  @Override
  protected String getName()
  {
    return (this.colour) ? "N":"n";
  }

}
