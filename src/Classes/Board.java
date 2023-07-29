public class Board
{

  private Square[][] board = new Square[8][8];

  public  Board()
  {
    init();
  }
  public Board(String fen)
  {
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
    StringBuilder fen = new StringBuilder();

    int emptySquares = 0;
    for(int i = 0; i < 8; i++)
    {
      for(int j = 0; j < 8; j++)
      {
        if(board[i][j].occupied)
        {
          if(emptySquares > 0)
          {
            fen.append(emptySquares);
            emptySquares = 0;
          }
          fen.append(board[i][j].piece.getName());
        }
        else
        {
          emptySquares++;
        }
      }
      if(emptySquares > 0)
      {
        fen.append(emptySquares);
        emptySquares = 0;
      }
      fen.append("/");
    }

    fen.deleteCharAt(fen.length() - 1);
    return fen.toString();
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
    String[] fenParts = fen.split(" ");
    String boardPart = fenParts[0];

    int i = 0, j = 0;
    for (char c : boardPart.toCharArray()) {
      if (c == '/') {
        i++;
        j = 0;
      } else if (Character.isDigit(c)) {
        int emptySquares = Character.getNumericValue(c);
        for (int k = 0; k < emptySquares; k++) {
          board[i][j].occupied = false;
          board[i][j].piece = null;
          j++;
        }
      } else
      {
        boolean isUpperCase = Character.isUpperCase(c);
        char pieceCode = Character.toLowerCase(c);
        boolean isOccupied = true;
        int value;
        switch(pieceCode)
        {
          case 'k' -> {
            value = 100;
            board[i][j].piece = new King(i, j, isUpperCase, value);
          }
          case 'q' -> {
            value = 9;
            board[i][j].piece = new Queen(i, j, isUpperCase, value);
          }
          case 'b' -> {
            value = 3;
            board[i][j].piece = new Bishop(i, j, isUpperCase, value);
          }
          case 'n' -> {
            value = 3;
            board[i][j].piece = new Knight(i, j, isUpperCase, value);
          }
          case 'r' -> {
            value = 5;
            board[i][j].piece = new Rook(i, j, isUpperCase, value);
          }
          case 'p' -> {
            value = 1;
            board[i][j].piece = new Pawn(i, j, isUpperCase, value);
          }
          default -> isOccupied = false;
        }

        board[i][j].occupied = isOccupied;
        j++;
      }
    }
  }

  public String[] Display()
  {
    String[] displayBoard = new String[26];

    StringBuilder sb = new StringBuilder();
    displayBoard[0] = "  __h___g___f___e___d___c___b___a___\n";
    for(int i = 0; i < 8; i++)
    {
      if(i % 2 == 0)
      {
        for(int j = 0; j < 3; j++)
        {
          if(j == 1)
          {
            sb.append(i + 1).append(" |").append(middleLine(true, i)).append("|\n");
          }
          else
          {
            sb.append(" ").append(" |").append("****    ****    ****    ****    |\n");
          }
          displayBoard[(((i*3)+j)+1)] = sb.toString();
          sb.delete(0,sb.length());
        }
      }
      else
      {
        for(int j = 0; j < 3; j++)
        {
          if(j == 1)
          {
            sb.append(i + 1).append(" |").append(middleLine(false, i)).append("|\n");
          }
          else
          {
            sb.append(" ").append(" |").append("    ****    ****    ****    ****|\n");
          }
          displayBoard[(((i*3)+j)+1)] = sb.toString();
          sb.delete(0,sb.length());
        }
      }
    }
    displayBoard[25] = "  |_a___b___c___d___e___f___g___h__|\n";

    return displayBoard;
  }

  private String middleLine(boolean colour, int row)
  {
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < 8; i++)
    {
      if(this.board[row][i].occupied)
      {
        if(colour)
        {
          sb.append("*").append(board[row][i].piece.getName()).append(board[row][i].piece.getName()).append("*");
        }
        else
        {
          sb.append(" ").append(board[row][i].piece.getName()).append(board[row][i].piece.getName()).append(" ");
        }
      }
      else
      {
        String square = (colour) ? "****":"    ";
        sb.append(square);
      }
      colour = !colour;
    }
    return sb.toString();
  }

  public boolean isValidCoordinate(int row, int col) {
    return row >= 0 && row < 8 && col >= 0 && col < 8;
  }

  public double evaluate()
  {
    return 0.0;
  }

  public Square[][] getBoard()
  {
    return board;
  }

  public Square getSquare(int row, int col)
  {
    return board[row][col];
  }

  public void setBoard(String fen)
  {
    setUp(fen);
  }

}
