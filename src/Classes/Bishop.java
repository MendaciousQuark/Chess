public class Bishop extends Piece
{
  Bishop(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn)
  {
    findDiagonalMoves(board, turn);
  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "B":"b";
  }
}
