public class King extends Piece
{

  private final boolean moved;
  private boolean inCheck;

  King(int posX, int posY, boolean colour, int value)
  {
    super(posX, posY, colour, value);
    moved = false;
    inCheck = false;
  }

  @Override
  protected void findMoves()
  {

  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "K":"k";
  }
}
