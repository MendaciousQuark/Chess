public class Pawn extends Piece
{

  Pawn(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  private boolean enPassant;

  @Override
  public void findMoves(Board board, int turn)
  {
    moves.clear();
    // order of calls important for tests
    findNonCapture(board, turn);
    findCapture(board, turn);
    findEnPassant(board, turn);
    //if the piece is pinned remove all moves that don't capture the pinning piece
    removeMovesIfPinned(board);

  }

  public void findNonCapture(Board board, int turn)
  {
    int nextRow = colour ? posI - 1 : posI + 1;
    // Check if the square in front of the pawn is empty
    if (board.isValidCoordinate(nextRow, posJ) && !board.getSquare(nextRow, posJ).occupied)
    {
      // Add a regular move (no capture)
      addNonCaptureMove(board, turn, nextRow, posJ);

      // Check if it's the first move for the pawn (two squares forward is possible)
      if (isFirstMove())
      {
        int doubleNextRow = colour ? posI - 2 : posI + 2;
        if (board.isValidCoordinate(doubleNextRow, posJ) && !board.getSquare(doubleNextRow, posJ).occupied)
        {
          // Add a double step move (no capture)
          addNonCaptureMove(board, turn, doubleNextRow, posJ);
        }
      }
    }
  }

  public void findCapture(Board board, int turn)
  {
    int nextRow = colour ? posI - 1 : posI + 1;

    // Check if capturing diagonally is possible
    for (int colOffset : new int[]{-1, 1})
    {
      int nextCol = posJ + colOffset;
      if (board.isValidCoordinate(nextRow, nextCol))
      {
        Square targetSquare = board.getSquare(nextRow, nextCol);
        if (targetSquare.occupied && targetSquare.piece.colour != colour)
        {
          addCaptureMove(board, turn, nextRow, nextCol);
        }
      }
    }
  }

  public void findEnPassant(Board board, int turn)
  {
    for(int colOffset : new int[]{-1, 1})
    {
      int nextCol = posJ + colOffset;
      if(board.isValidCoordinate(posI, nextCol))
      {
        Square targetSquare = board.getSquare(posI, nextCol);
        if(targetSquare.occupied && targetSquare.piece.colour != colour &&
           targetSquare.piece instanceof Pawn targetPawn)
        {
          if(targetPawn.enPassant)
          {
            int ColorModifier = colour? -1:+1;
            addCaptureMove(board, turn, posI+ColorModifier, nextCol);
          }
        }
      }
    }
  }

  private boolean isFirstMove()
  {
    return (this.colour && posI == 6) || (!this.colour && posI == 1);
  }

  @Override
  protected Piece copy()
  {
    return new Pawn(this.posI, this.posJ, this.colour, this.value);
  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "P":"p";
  }

  public boolean isEnPassant()
  {
    return enPassant;
  }

  public void setEnPassant(boolean enPassant)
  {
    this.enPassant = enPassant;
  }

}
