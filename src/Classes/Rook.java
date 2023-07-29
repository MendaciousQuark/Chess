public class Rook extends Piece
{
  Rook(int posX, int posY, boolean colour, int value)
  {
    super(posX, posY, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn, boolean check)
  {

  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "R":"r";
  }

}
