public class Pawn extends Piece
{

  Pawn(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  private boolean enPassant;

  @Override
  public void findMoves(Board board, int turn, boolean check)
  {
    moves.clear();
    if(!chained)
    {
      // order of calls important for tests
      findNonCapture(board, turn, check);
      findCapture(board, turn, check);
      findEnPassant(board, turn, check);
    }

  }

  public void findNonCapture(Board board, int turn, boolean check)
  {
    int nextRow = colour ? posI - 1 : posI + 1;
    // Check if the square in front of the pawn is empty
    if (board.isValidCoordinate(nextRow, posJ) && !board.getSquare(nextRow, posJ).occupied)
    {
      // Add a regular move (no capture)
      int[] start = {posI, posJ};
      int[] end = {nextRow, posJ};
      Move move = new Move(board, start, end, this, colour, turn, false, check, false, false);
      moves.add(move);

      // Check if it's the first move for the pawn (two squares forward is possible)
      if (isFirstMove())
      {
        int doubleNextRow = colour ? posI - 2 : posI + 2;
        if (board.isValidCoordinate(doubleNextRow, posJ) && !board.getSquare(doubleNextRow, posJ).occupied)
        {
          // Add a double step move (no capture)
          int[] doubleStepEnd = {doubleNextRow, posJ};
          Move doubleStepMove = new Move(board, start, doubleStepEnd, this, colour, turn, false, check, false, false);
          moves.add(doubleStepMove);
        }
      }
    }
  }

  public void findCapture(Board board, int turn, boolean check)
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
          addCaptureMove(board, turn, check, nextRow, nextCol);
        }
      }
    }
  }

  public void findEnPassant(Board board, int turn, boolean check)
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
            int [] start = {posI, posJ};
            int ColorModifier = colour? -1:+1;
            int [] end = {posI + ColorModifier, nextCol};
            Move enPassantMove = new Move(board, start, end, this, colour, turn, true, check, false, false);
            moves.add(enPassantMove);
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

  public void setChained(boolean chained)
  {
    this.chained = chained;
  }
}
