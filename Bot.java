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
  protected void validateMove(Move move)
  {
    super.validateMove(move);
  }

  @Override
  protected void makeMove()
  {
    super.makeMove();
  }
}
