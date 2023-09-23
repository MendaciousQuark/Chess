import java.util.Arrays;

public class Move
{

  private String fen;
  private int[] start;
  private int[] end;
  private Piece piece;
  private boolean colour;
  private int turn;
  private boolean capture;
  private boolean checkmate;
  private boolean draw;
  private boolean castle;

  public Move(Board board, int[] start, int[] end, Piece piece, boolean colour, int turn, boolean capture, boolean checkmate, boolean draw)
  {
    this.fen = board.toString();
    this.start = start;
    this.end = end;
    this.piece = piece;
    this.colour = colour;
    this.turn = turn;
    this.capture = capture;
    this.checkmate = checkmate;
    this.draw = draw;
    this.castle = false;
  }

  public Move(Board board, int[] start, int[] end, Piece piece, boolean castle, int turn, boolean checkmate, boolean draw)
  {
    this.fen = board.toString();
    this.start = start;
    this.end = end;
    this.piece = piece;
    this.colour = piece.colour;
    this.turn = turn;
    this.capture = false;
    this.checkmate = checkmate;
    this.draw = draw;
    this.castle = castle;
  }

  public Move()
  {
    this.fen = "fen";
    this.start = null;
    this.end = null;
    this.piece = null;
    this.colour = false;
    this.turn = 0;
    this.capture = false;
    this.checkmate = false;
    this.draw = false;
    this.castle = false;
  }
  @Override
  public String toString() {
    return "Move{" +
           "\n    start=" + Arrays.toString(start) +
           "\n    end=" + Arrays.toString(end) +
           "\n    piece=" + piece.getName() +
           "\n    colour=" + colour +
           "\n    turn=" + turn +
           "\n    capture=" + capture +
           "\n    checkmate=" + checkmate +
           "\n    draw=" + draw +
           "\n    fen='" + fen + '\'' +
           "\n}";
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Move otherMove = (Move) obj;
    return sameAttributes(otherMove);

  }

  private boolean sameAttributes(Move otherMove)
  {
    return Arrays.equals(start, otherMove.start) &&
           Arrays.equals(end, otherMove.end) &&
           piece.equals(otherMove.piece) &&
           colour == otherMove.colour &&
           turn == otherMove.turn &&
           capture == otherMove.capture &&
           checkmate == otherMove.checkmate &&
           draw == otherMove.draw;
  }

  public boolean sameDestination(int[] otherEnd)
  {
    return this.end[0] == otherEnd[0] && this.end[1] == otherEnd[1];
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

  public void setFen(String fen)
  {
    this.fen = fen;
  }

  public void setStart(int[] start)
  {
    this.start = start;
  }

  public void setEnd(int[] end)
  {
    this.end = end;
  }

  public void setPiece(Piece piece)
  {
    this.piece = piece;
  }

  public void setColour(boolean colour)
  {
    this.colour = colour;
  }

  public void setTurn(int turn)
  {
    this.turn = turn;
  }

  public void setCapture(boolean capture)
  {
    this.capture = capture;
  }

  public void setCheckmate(boolean checkmate)
  {
    this.checkmate = checkmate;
  }

  public void setDraw(boolean draw)
  {
    this.draw = draw;
  }

  public void setCastle(boolean castle)
  {
    this.castle = castle;
  }
}
