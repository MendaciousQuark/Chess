public class Queen extends Piece
{

  Queen(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn, boolean check)
  {
    if(!chained)
    {
      findDiagonalMoves(board, turn, check);
      findVerticalMoves(board, turn, check);
      findHorizontalMoves(board, turn, check);
    }
  }


  @Override
  protected String getName()
  {
    return (this.colour) ? "Q":"q";
  }

}
