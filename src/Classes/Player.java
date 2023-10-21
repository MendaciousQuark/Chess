public abstract class Player
{
  protected boolean castle;
  protected boolean colour;

  Player(boolean castle, boolean colour)
  {
    this.castle = castle;
    this.colour = colour;
  }

  protected Move getMove(Board board)
  {
    return new Move();
  }

  protected static int[] notationToLocation(String notation) {
    if (notation.length() != 2) {
      throw new IllegalArgumentException("Invalid chess notation. It should be two characters.");
    }

    char fileChar = notation.charAt(0);
    char rankChar = notation.charAt(1);

    if (fileChar < 'a' || fileChar > 'h' || rankChar < '1' || rankChar > '8') {
      throw new IllegalArgumentException("Invalid chess notation. It should be in the format 'a1' to 'h8'.");
    }

    int file = fileChar - 'a'; // Convert file character to column (0 to 7)
    int rank = '8' - rankChar; // Convert rank character to row (0 to 7)

    return new int[]{rank, file};
  }

  protected abstract Piece promotePawn(Pawn pawn);
}
