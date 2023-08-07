public class Rook extends Piece
{
  Rook(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn, boolean check)
  {
    if(!chained)
    {
      findVerticalMoves(board, turn, check);
      findHorizontalMoves(board, turn, check);
    }
  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "R":"r";
  }

}
