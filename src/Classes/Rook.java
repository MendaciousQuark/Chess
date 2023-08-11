public class Rook extends Piece
{
  Rook(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn)
  {
    if(!chained)
    {
      findVerticalMoves(board, turn);
      findHorizontalMoves(board, turn);
    }
  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "R":"r";
  }

}
