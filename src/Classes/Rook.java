public class Rook extends Piece
{
  Rook(int posI, int posJ, boolean colour, int value)
  {
    super(posI, posJ, colour, value);
  }

  @Override
  protected void findMoves(Board board, int turn)
  {
    findVerticalMoves(board, turn);
    findHorizontalMoves(board, turn);
    //if the piece is pinned remove all moves that don't capture the pinning piece
    removeMovesIfPinned(board);
    if(board.findKing(this.colour).isInCheck())
    {
      moves.removeIf(move -> remainsCheck(move, board));
    }
  }

  @Override
  protected Piece copy()
  {
    return new Rook(this.posI, this.posJ, this.colour, this.value);
  }

  @Override
  protected String getName()
  {
    return (this.colour) ? "R":"r";
  }

}
