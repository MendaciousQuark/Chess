import java.util.Arrays;
import java.util.Objects;

public class Move
{

  private final String fen;
  private final int[] start;
  private final int[] end;
  private final Piece piece;
  private final boolean colour;
  private final int turn;
  private final boolean capture;
  private final boolean check;
  private final boolean checkmate;
  private final boolean draw;
  private final boolean castle;

  public Move(Board board, int[] start, int[] end, Piece piece, boolean colour, int turn, boolean capture, boolean check, boolean checkmate, boolean draw)
  {
    this.fen = board.toString();
    this.start = start;
    this.end = end;
    this.piece = piece;
    this.colour = colour;
    this.turn = turn;
    this.capture = capture;
    this.check = check;
    this.checkmate = checkmate;
    this.draw = draw;
    this.castle = false;
  }

  public Move(Board board, int[] start, int[] end, Piece piece, boolean castle, int turn, boolean checkmate, boolean draw, boolean check)
  {
    this.fen = board.toString();
    this.start = start;
    this.end = end;
    this.piece = piece;
    this.colour = piece.colour;
    this.turn = turn;
    this.capture = false;
    this.check = check;
    this.checkmate = checkmate;
    this.draw = draw;
    this.castle = castle;
  }

  @Override
  public String toString() {
    return "Move{" +
           "\n    start=" + Arrays.toString(start) +
           "\n    end=" + Arrays.toString(end) +
           "\n    piece=" + piece +
           "\n    colour=" + colour +
           "\n    turn=" + turn +
           "\n    capture=" + capture +
           "\n    check=" + check +
           "\n    checkmate=" + checkmate +
           "\n    draw=" + draw +
           "\n    fen='" + fen + '\'' +
           "\n}";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Move otherMove = (Move) obj;
    return sameAttributes(otherMove);

    // Compare other fields that determine move equality
    // For example: capturedPiece, isEnPassant, isPromotion, etc.
  }

  private boolean sameAttributes(Move otherMove)
  {
    return Arrays.equals(start, otherMove.start) &&
           Arrays.equals(end, otherMove.end) &&
           piece.equals(otherMove.piece) &&
           colour == otherMove.colour &&
           turn == otherMove.turn &&
           capture == otherMove.capture &&
           check == otherMove.check &&
           checkmate == otherMove.checkmate &&
           draw == otherMove.draw;
  }

  public String getFen()
  {
    return fen;
  }

  public int[] getStart()
  {
    return start;
  }

  public int[] getEnd()
  {
    return end;
  }

  public Piece getPiece()
  {
    return piece;
  }

  public boolean isColour()
  {
    return colour;
  }

  public int getTurn()
  {
    return turn;
  }

  public boolean isCapture()
  {
    return capture;
  }

  public boolean isCheck()
  {
    return check;
  }

  public boolean isCheckmate()
  {
    return checkmate;
  }

  public boolean isDraw()
  {
    return draw;
  }

  public boolean isCastle()
  {
    return castle;
  }
}
