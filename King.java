public class King extends Piece
{
  private boolean moved, inCheck;

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
}
