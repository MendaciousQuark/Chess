public class Bishop extends Piece
{
  Bishop(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn, boolean check)
  {
    findDiagonalMoves(board, turn, check);
  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "B":"b";
  }
}
