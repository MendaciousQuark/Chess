public class Board
{

  private Square[][] board = new Square[8][8];

  Board()
  {
    init();
  }
  Board(String fen){
    init();
    setUp(fen);
  }
  Board(Square[][] board)
  {
    this.board = board;
  }


  @Override
  public String toString()
  {
    return "FEN";
  }

  private void init()
  {
    boolean colour;
    for(int i = 0; i < 8; i++)
    {
      colour = i % 2 == 0;
      for(int j = 0; j < 8; j++)
      {
        Piece piece = placePiece(i, j);
        if(piece != null)
        {
          this.board[i][j] = new Square(i, j, colour, piece);
        }
        else
        {
          this.board[i][j] = new Square(i, j, colour);
        }
        colour = !colour;
      }
    }
  }

  private Piece placePiece(int i, int j)
  {
    Piece piece = null;
    boolean pieceColour;

    if(i < 2)
    {
      pieceColour = false;
    }
    else if (i > 5)
    {
      pieceColour = true;
    }
    else
    {
      return null;
    }

    if(i == 1 || i==6)
    {
      piece = new Pawn(i, j, pieceColour, 1);
    }
    else
    {
      switch(j % 8)
      {
        case 0, 7 -> piece = new Rook(i, j, pieceColour, 5);
        case 1, 6 -> piece = new Knight(i, j, pieceColour, 3);
        case 2, 5 -> piece = new Bishop(i, j, pieceColour, 3);
        case 3 -> piece = new Queen(i, j, pieceColour, 9);
        case 4 -> piece = new King(i, j, pieceColour, 100);
      }
    }
    return piece;
  }

  private void setUp(String fen)
  {
    int i = 0, j = 0;
    boolean colour;
    char[] fenArray = fen.toCharArray();
    for(char element: fenArray)
    {
      if(element == ' ')
      {
        break;
      }

      switch(element)
      {
        case 1, 2, 3, 4, 5, 6, 7, 8:
          for(int k = j; k < element - j; k++)
          {
            board[i][j].occupied = false;
            board[i][j].piece = null;
            j++;
          }
          break;
        case 'K', 'k':
          colour = element == 'K';
          board[i][j].occupied = true;
          board[i][j].piece = new King(i, j, colour, 100);
          j++;
          break;
        case 'Q', 'q':
          colour = element == 'Q';
          board[i][j].occupied = true;
          board[i][j].piece = new Queen(i, j, colour, 9);
          j++;
          break;
        case 'B', 'b':
          colour = element == 'B';
          board[i][j].occupied = true;
          board[i][j].piece = new Bishop(i, j, colour, 3);
          j++;
          break;
        case 'N', 'n':
          colour = element == 'N';
          board[i][j].occupied = true;
          board[i][j].piece = new Knight(i, j, colour, 3);
          j++;
          break;
        case 'R', 'r':
          colour = element == 'R';
          board[i][j].occupied = true;
          board[i][j].piece = new Rook(i, j, colour, 5);
          j++;
          break;
        case 'P', 'p':
          colour = element == 'P';
          board[i][j].occupied = true;
          board[i][j].piece = new Pawn(i, j, colour, 1);
          j++;
          break;
      }
      if(j > 7)
      {
        i++;
        j = 0;
      }
    }
  }
  public void Display(Board board){}

  public double evaluate()
  {
    return 0.0;
  }

  public Square[][] getBoard()
  {
    return board;
  }

  public void setBoard(String fen)
  {
    setUp(fen);
  }

}
