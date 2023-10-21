import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ParallelMoveProcessor implements Callable <Pair<Move, Double>>
{
  ArrayList<Piece> assignedPieces;
  Board board;
  boolean colour;

  public ParallelMoveProcessor(Board board, ArrayList<Piece> assignedPieces, boolean colour)
  {
    this.assignedPieces = assignedPieces;
    this.board = board;
    this.colour = colour;
  }

  @Override
  public Pair<Move, Double> call()
  {
    //initialise variables
    Move bestMove = null;
    double bestEvaluation = board.evaluate();

    //for each assigned piece
    for(Piece piece: assignedPieces)
    {
      //if we are dealing with the correct colour piece
      if(piece.colour == colour)
      {
        //find the legal moves for the piece
        piece.findMoves(board, board.getTurn());

        //for each legal, found move
        for(Move move : piece.moves)
        {
          //create a copy of the chess board
          Board boardCopy = board.copy();

          //make the move on the cloned board
          boardCopy.makeMove(move);

          //evaluate the result
          double moveEvaluation = boardCopy.evaluate();

          //if we are generating whiteMoves
          if(colour)
          {
            //if the board has higher evaluation than the previous highest evaluation
            if(moveEvaluation >= bestEvaluation)
            {
              //update the best move
              bestMove = move;
            }
          }
          //if we are generating black moves
          else
          {
            //if the board has lower evaluation than the previous lowest evaluation
            if(moveEvaluation <= bestEvaluation)
            {
              //update the best move
              bestMove = move;
            }
          }
        }
      }
    }

    return new Pair<>(bestMove, bestEvaluation);
  }
}
