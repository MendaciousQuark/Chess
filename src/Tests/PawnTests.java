import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnTests
{
  private Board board;

  @BeforeEach
  public void setup()
  {
    board = new Board();
  }

  @Test
  public void testNonCapture() {
    int turn = 1;
    // Set up the board with a specific initial position
    board.setBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

    // Get the specific pawn from the board (row 1, column 3)
    Pawn pawn = (Pawn) board.getSquare(1, 3).piece;

    // Find possible moves for the pawn
    pawn.findMoves(board, turn);

    // Set the start and end positions for the first move
    int[] start = pawn.setStart(1, 3);
    int[] end =  pawn.setEnd(2, 3);
    // Create the expected move object for the first move
    Move move1 = new Move(board, start, end, pawn, pawn.colour, turn, false, false, false);

    // Set the start and end positions for the second move
    start =  pawn.setStart(1, 3);
    end =  pawn.setEnd(3, 3);
    // Create the expected move object for the second move
    Move move2 = new Move(board, start, end, pawn, pawn.colour, turn, false, false, false);

    // Assertions for the first pawn's moves
    Assertions.assertEquals(2, pawn.moves.size());
    Assertions.assertTrue(pawn.moves.contains(move1));
    Assertions.assertTrue(pawn.moves.contains(move2));

    // Get another pawn from the board (row 6, column 0)
    pawn = (Pawn) board.getSquare(6, 0).piece;

    // Find possible moves for the second pawn
    pawn.findMoves(board, turn);

    // Set the start and end positions for the third move
    start =  pawn.setStart(6, 0);
    end =  pawn.setEnd(5, 0);
    // Create the expected move object for the third move
    Move move3 = new Move(board, start, end, pawn, pawn.colour, turn, false, false, false);

    // Set the start and end positions for the fourth move
    start =  pawn.setStart(6, 0);
    end =  pawn.setEnd(4, 0);
    // Create the expected move object for the fourth move
    Move move4 = new Move(board, start, end, pawn, pawn.colour, turn, false, false, false);

    // Assertions for the second pawn's moves
    Assertions.assertEquals(2, pawn.moves.size());
    Assertions.assertTrue(pawn.moves.contains(move3));
    Assertions.assertTrue(pawn.moves.contains(move4));

    // Set up a new board with a specific position
    board.setBoard("rnbqkbnr/1pp2ppp/3p4/p3p3/1PPP4/8/P3PPPP/RNBQKBNR");

    // Get another pawn from the board (row 4, column 3)
    pawn = (Pawn) board.getSquare(4, 3).piece;

    // Find possible moves for the third pawn
    pawn.findMoves(board, turn);

    // Set the start and end positions for the fifth move
    start =  pawn.setStart(4, 3);
    end =  pawn.setEnd(3, 3);
    // Create the expected move object for the fifth move
    Move move5 = new Move(board, start, end, pawn, pawn.colour, turn, false, false, false);

    // Set the start and end positions for the sixth move
    start =  pawn.setStart(4, 3);
    end =  pawn.setEnd(2, 3);
    // Create the expected move object for the sixth move
    Move move6 = new Move(board, start, end, pawn, pawn.colour, turn, false, false, false);

    // Assertions for the third pawn's moves
    Assertions.assertEquals(2, pawn.moves.size());
    Assertions.assertTrue(pawn.moves.contains(move5));
    Assertions.assertFalse(pawn.moves.contains(move6));

    // Get another pawn from the board (row 3, column 0)
    pawn = (Pawn) board.getSquare(3, 0).piece;

    // Find possible moves for the fourth pawn
    pawn.findMoves(board, turn);

    // Set the start and end positions for the seventh move
    start =  pawn.setStart(3, 0);
    end =  pawn.setEnd(4, 0);
    // Create the expected move object for the seventh move
    Move move7 = new Move(board, start, end, pawn, pawn.colour, turn, false, false, false);

    // Set the start and end positions for the eighth move
    start =  pawn.setStart(3, 0);
    end =  pawn.setEnd(5, 0);
    // Create the expected move object for the eighth move
    Move move8 = new Move(board, start, end, pawn, pawn.colour, turn, false, false, false);

    // Assertions for the fourth pawn's moves
    Assertions.assertEquals(2, pawn.moves.size());
    Assertions.assertTrue(pawn.moves.contains(move7));
    Assertions.assertFalse(pawn.moves.contains(move8));
  }

  @Test
  public void testCapture() {
    // Set the current turn to 5
    int turn = 5;
    // Set up the board with a specific initial position
    board.setBoard("rnbqkbnr/1p3ppp/3p4/p1p1p3/1PPP4/8/P3PPPP/RNBQKBNR");

    // Get the specific pawn from the board (row 4, column 3)
    Pawn pawn = (Pawn) board.getSquare(4, 3).piece;
    // Find possible moves for the pawn
    pawn.findMoves(board, turn);

    // Set the start and end positions for the first move
    int[] start =  pawn.setStart(4, 3);
    int[] end =  pawn.setEnd(3, 4);
    // Create the expected move object for the first move
    Move move1 = new Move(board, start, end, pawn, pawn.colour, turn, true, false, false);

    // Set the start and end positions for the second move
    start =  pawn.setStart(4, 3);
    end =  pawn.setEnd(3, 2);
    // Create the expected move object for the second move
    Move move2 = new Move(board, start, end, pawn, pawn.colour, turn, true, false, false);

    // Set the start and end positions for the third move
    start =  pawn.setStart(4, 3);
    end =  pawn.setEnd(3, 0);
    // Create the expected move object for the third move
    Move move3 = new Move(board, start, end, pawn, pawn.colour, turn, true, false, false);

    // Assertions for the first pawn's moves
    Assertions.assertEquals(3, pawn.moves.size());

    Assertions.assertTrue(pawn.moves.contains(move1));
    Assertions.assertTrue(pawn.moves.contains(move2));
    Assertions.assertFalse(pawn.moves.contains(move3));

    // Get another pawn from the board (row 3, column 0)
    pawn = (Pawn) board.getSquare(3, 0).piece;
    // Find possible moves for the second pawn
    pawn.findMoves(board, turn);

    // Set the start and end positions for the fourth move
    start =  pawn.setStart(3, 0);
    end =  pawn.setEnd(4, 1);
    // Create the expected move object for the fourth move
    Move move4 = new Move(board, start, end, pawn, pawn.colour, turn, true, false, false);

    // Set the start and end positions for the sixth move
    start =  pawn.setStart(3, 0);
    end =  pawn.setEnd(3, 2);
    // Create the expected move object for the sixth move
    Move move5 = new Move(board, start, end, pawn, pawn.colour, turn, true, false, false);

    // Assertions for the second pawn's moves
    Assertions.assertEquals(2, pawn.moves.size());
    Assertions.assertTrue(pawn.moves.contains(move4));
    Assertions.assertFalse(pawn.moves.contains(move5));
  }

  @Test
  public void testEnPassant() {
    // Set up the turn number to simulate a specific game state
    int turn = 10;

    // Set up the initial board position with a scenario where En passant move is possible
    board.setBoard("rnbqkbnr/1p4pp/3p4/p1p1ppP1/1PPP4/8/P3PP1P/RNBQKBNR");

    // Obtain the pawn that can perform En passant and the pawn that can be captured by En passant
    Pawn pawn = (Pawn) board.getSquare(3, 6).piece;
    Pawn enPassantPawn = (Pawn) board.getSquare(3, 5).piece;

    // Set the En passant flag on the enPassantPawn to indicate that it can be captured by En passant
    enPassantPawn.setEnPassant(true);

    // Modify the board to update the position of the enPassantPawn after setting the En passant flag
    board.getSquare(3, 5).piece = enPassantPawn;

    // Calculate available moves for the pawn
    pawn.findMoves(board, turn);

    // Define the expected En passant moves for the pawn in this scenario
    int[] start =  pawn.setStart(3, 6);
    int[] end =  pawn.setEnd(2, 5);
    Move move1 = new Move(board, start, end, pawn, pawn.colour, turn, true, false, false);

    start =  pawn.setStart(3, 6);
    end =  pawn.setEnd(2, 7);
    Move move2 = new Move(board, start, end, pawn, pawn.colour, turn, true, false, false);

    // Check that the pawn's available moves list contains the expected En passant moves
    Assertions.assertEquals(2, pawn.moves.size());
    Assertions.assertTrue(pawn.moves.contains(move1));
    Assertions.assertFalse(pawn.moves.contains(move2));

    // Set up a different board position to test another scenario for En passant move
    board.setBoard("rnbqkbnr/1p4pp/3p4/p1p2pP1/1PPPpP2/8/P3P2P/RNBQKBNR");

    // Obtain the new pawn that can perform En passant and the new pawn that can be captured by En passant
    pawn = (Pawn) board.getSquare(4, 4).piece;
    enPassantPawn = (Pawn) board.getSquare(4, 5).piece;

    // Set the En passant flag on the new enPassantPawn
    enPassantPawn.setEnPassant(true);

    // Modify the board to update the position of the new enPassantPawn after setting the En passant flag
    board.getSquare(4, 5).piece = enPassantPawn;

    // Calculate available moves for the pawn in this scenario
    pawn.findMoves(board, turn);

    // Define the expected En passant move for the pawn in this scenario
    start =  pawn.setStart(4, 4);
    end =  pawn.setEnd(5, 5);
    Move move3 = new Move(board, start, end, pawn, pawn.colour, turn, true, false, false);

    start =  pawn.setStart(4, 4);
    end =  pawn.setEnd(5, 3);
    Move move4 = new Move(board, start, end, pawn, pawn.colour, turn, true, false, false);

    // Check that the pawn's available moves list contains the expected En passant move
    Assertions.assertEquals(2, pawn.moves.size());
    Assertions.assertTrue(pawn.moves.contains(move3));
    Assertions.assertFalse(pawn.moves.contains(move4));
  }


  @Test
  public void testPinned()
  {
    int turn = 0;
    board.setBoard("rnb1k1nr/pppppppp/8/8/4qP1b/6P1/PPPPP2P/RNBQKBNR");
    board.findMoves(turn);
    board.getSquare(5, 6).attackingPieces.add(board.getSquare(4, 7).piece);

    Pawn pawn = (Pawn) board.getSquare(5, 6).piece;
    pawn.findMoves(board, turn);

    Assertions.assertEquals(1, pawn.moves.size());
  }


}
