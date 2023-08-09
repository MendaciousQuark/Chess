public class King extends Piece
{

  private final boolean moved;
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
}
