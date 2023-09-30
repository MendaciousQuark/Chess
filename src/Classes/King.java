import java.util.ArrayList;

public class King extends Piece
{

  private boolean moved;
  private boolean inCheck;
  private boolean canKingSideCastle;
  private boolean canQueenSideCastle;
  public ArrayList<Piece> checkingPieces = new ArrayList<>();

  King(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
    if(colour)
    {
      moved = posI == 7 && posJ == 4;
    }
    else
    {
      moved = posI == 0 && posJ == 4;
    }
  }

  @Override
  protected void findMoves(Board board, int turn)
  {
    findDiagonalMoves(board, turn);
    findVerticalMoves(board, turn);
    findHorizontalMoves(board, turn);
    if(canQueenSideCastle || canKingSideCastle)
    {
      findCastleMoves(board, turn);
    }
    if(inCheck)
    {
      moves.removeIf(move -> remainsCheck(move, board));
    }
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
          if(!board.squareIsAttacked(this.colour, nextRow, nextCol))
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
        if(!board.squareIsAttacked(this.colour, nextRow, nextCol))
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
        if(!board.squareIsAttacked(this.colour, nextRow, nextCol))
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

  private void findCastleMoves(Board board, int turn)
  {
    if(queenSideCastle(board, turn))
    {
      moves.add(new Move(board, setStart(posI, posJ), setEnd(posI, posJ - 2), this, true, turn,false, false));
    }

    if(kingSideCastle(board, turn))
    {
      moves.add(new Move(board, setStart(posI, posJ), setEnd(posI, posJ + 2), this, true, turn,false, false));
    }
  }

  private boolean kingSideCastle(Board board, int turn)
  {
    Square oneRight = board.getSquare(posI, posJ + 1);
    Square twoRight = board.getSquare(posI, posJ + 2);
    if(!oneRight.occupied && !twoRight.occupied)
    {
      return !board.squareIsAttacked(this.colour, twoRight.posI, twoRight.posJ) && !board.squareIsAttacked(this.colour, oneRight.posI, oneRight.posJ);
    }
    return false;
  }

  private boolean queenSideCastle(Board board, int turn)
  {
    Square oneLeft = board.getSquare(posI, posJ  - 1);
    Square twoLeft = board.getSquare(posI, posJ  - 2);
    if(!oneLeft.occupied && !twoLeft.occupied)
    {
      return !board.squareIsAttacked(this.colour, twoLeft.posI, twoLeft.posJ) && !board.squareIsAttacked(this.colour, oneLeft.posI, oneLeft.posJ);
    }
    return false;
  }

  @Override
  protected Piece copy()
  {
    return new King(this.posI, this.posJ, this.colour, this.value);
  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "K":"k";
  }

  public boolean isMoved()
  {
    return !canKingSideCastle && !canQueenSideCastle;
  }

  public boolean isInCheck()
  {
    return inCheck;
  }

  public boolean hasKingSideCastle()
  {
    return canKingSideCastle;
  }

  public boolean hasQueenSideCastle()
  {
    return canQueenSideCastle;
  }

  public void setMoved(boolean moved)
  {
    this.moved = moved;
  }

  public void setInCheck(boolean inCheck)
  {
    this.inCheck = inCheck;
  }

  public void setCanKingSideCastle(boolean canKingSideCastle)
  {
    this.canKingSideCastle = canKingSideCastle;
  }

  public void setCanQueenSideCastle(boolean canQueenSideCastle)
  {
    this.canQueenSideCastle = canQueenSideCastle;
  }

}
