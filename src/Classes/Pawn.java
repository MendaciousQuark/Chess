public class Pawn extends Piece
{

  Pawn(int posX, int posY, boolean colour, int value)
  {
    super(posX, posY, colour, value);
  }

  private boolean enPassant()
  {
    return false;
  }

  @Override
  protected void findMoves()
  {

  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "P":"p";
  }
  
}
