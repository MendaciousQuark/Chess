import java.util.Arrays;
import java.util.Objects;

public class Move
{

  private String fen;
  private int[] start;
  private int[] end;
  private Piece piece;
  private boolean colour;
  private int turn;
  private boolean capture;
  private boolean check;
  private boolean checkmate;
  private boolean draw;

  public Move(Board board, int[] start, int[] end, Piece piece, boolean colour, int turn, boolean capture, boolean check, boolean checkmate, boolean draw)
  {
    this.start = start;
    this.end = end;
    this.piece = piece;
    this.colour = colour;
    this.turn = turn;
    this.capture = capture;
    this.check = check;
    this.checkmate = checkmate;
    this.draw = draw;
    this.fen = board.toString();
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
}
