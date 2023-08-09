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
  protected void findMoves(Board board, int turn, boolean check)
  {

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
