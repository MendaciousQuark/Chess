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

}
