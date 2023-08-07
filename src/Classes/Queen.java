public class Queen extends Piece
{

  Queen(int posX, int posY, boolean colour, int value)
  {
    super(posX, posY, colour, value);
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
