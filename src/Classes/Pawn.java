public class Pawn extends Piece
{

  Pawn(int posX, int posY, boolean colour, int value)
  {
    super(posX, posY, colour, value);
  }

  private boolean enPassant;

  @Override
  public void findMoves(Board board, int turn, boolean check)
  {
    moves.clear();
    if(!chained)
    {
      // order of calls important for tests
      findNonCaptureMoves(board, turn, check);
      findCaptureMoves(board, turn, check);
      findEnPassantMoves(board, turn, check);
    }

  }

  public void findNonCaptureMoves(Board board, int turn, boolean check)
  {
    int nextRow = colour ? posX - 1 : posX + 1;
    // Check if the square in front of the pawn is empty
    if (board.isValidCoordinate(nextRow, posY) && !board.getSquare(nextRow, posY).occupied)
    {
      // Add a regular move (no capture)
      int[] start = {posX, posY};
      int[] end = {nextRow, posY};
      Move move = new Move(board, start, end, this, colour, turn, false, check, false, false);
      moves.add(move);

      // Check if it's the first move for the pawn (two squares forward is possible)
      if (isFirstMove())
      {
        int doubleNextRow = colour ? posX - 2 : posX + 2;
        if (board.isValidCoordinate(doubleNextRow, posY) && !board.getSquare(doubleNextRow, posY).occupied)
        {
          // Add a double step move (no capture)
          int[] doubleStepEnd = {doubleNextRow, posY};
          Move doubleStepMove = new Move(board, start, doubleStepEnd, this, colour, turn, false, check, false, false);
          moves.add(doubleStepMove);
        }
      }
    }
  }

  public void findCaptureMoves(Board board, int turn, boolean check)
  {
    int nextRow = colour ? posX - 1 : posX + 1;

    // Check if capturing diagonally is possible
    for (int colOffset : new int[]{-1, 1})
    {
      int nextCol = posY + colOffset;
      if (board.isValidCoordinate(nextRow, nextCol))
      {
        Square targetSquare = board.getSquare(nextRow, nextCol);
        if (targetSquare.occupied && targetSquare.piece.colour != colour)
        {
          // Add a capture move
          int[] start = {posX, posY};
          int[] end = {nextRow, nextCol};
          Move captureMove = new Move(board, start, end, this, colour, turn, true, check, false, false);
          moves.add(captureMove);
        }
      }
    }
  }

  public void findEnPassantMoves(Board board, int turn, boolean check)
  {
    for(int colOffset : new int[]{-1, 1})
    {
      int nextCol = posY + colOffset;
      if(board.isValidCoordinate(posX, nextCol))
      {
        Square targetSquare = board.getSquare(posX, nextCol);
        if(targetSquare.occupied && targetSquare.piece.colour != colour &&
           targetSquare.piece instanceof Pawn targetPawn)
        {
          if(targetPawn.enPassant)
          {
            int [] start = {posX, posY};
            int ColorModifier = colour? -1:+1;
            int [] end = {posX + ColorModifier, nextCol};
            Move enPassantMove = new Move(board, start, end, this, colour, turn, true, check, false, false);
            moves.add(enPassantMove);
          }
        }
      }
    }
  }

  private boolean isFirstMove()
  {
    return (this.colour && posX == 6) || (!this.colour && posX == 1);
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
