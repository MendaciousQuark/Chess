public class Knight extends Piece
{

  Knight(int posX, int posY, boolean colour, int value)
  {
    super(posX, posY, colour, value);
  }

  @Override
  protected void findMoves()
  {

  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "N":"n";
  }

}
