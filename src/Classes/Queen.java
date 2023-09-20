public class Queen extends Piece
{

  Queen(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn)
  {
    findDiagonalMoves(board, turn);
    findVerticalMoves(board, turn);
    findHorizontalMoves(board, turn);
    //if the piece is pinned remove all moves that don't capture the pinning piece
    removeMovesIfPinned(board);
  }

  @Override
  protected Piece copy()
  {
    return new Queen(this.posI, this.posJ, this.colour, this.value);
  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "Q":"q";
  }

}
