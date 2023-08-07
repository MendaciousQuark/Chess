public class Bishop extends Piece
{
  Bishop(int posX, int posY, boolean colour, int value)
  {
    super(posX, posY, colour, value);
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
