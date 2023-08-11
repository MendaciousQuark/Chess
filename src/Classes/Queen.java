public class Queen extends Piece
{

  Queen(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn)
  {
    if(!chained)
    {
      findDiagonalMoves(board, turn);
      findVerticalMoves(board, turn);
      findHorizontalMoves(board, turn);
    }
  }


  @Override
  protected String getName()
  {
    return (this.colour) ? "Q":"q";
  }

}
