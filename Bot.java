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

  @Override
  protected void validateMove()
  {
    super.validateMove();
  }

  @Override
  protected void makeMove(Move move)
  {
    super.makeMove(move);
  }
}
