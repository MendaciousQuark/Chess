public class Bot  extends Player
{
  private double posEval;

  public Bot(boolean castle, boolean colour, double posEval)
  {
    super(castle, colour);
    this.posEval = posEval;
  }

  private void generateMove()
  {

  }

  protected Piece promotePawn(Pawn pawn)
  {
    return null;
  }
  @Override
  protected Move getMove(Board board)
  {
    Move move = super.getMove(board);
    return move;
  }
}
