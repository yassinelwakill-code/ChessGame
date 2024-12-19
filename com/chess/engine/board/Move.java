
package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;



public abstract class Move {
    
    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinate;
    protected final boolean isFirstMove;
    
    
    public static final Move NULL_MOVE = new NullMove();

    public Move(final Board board, 
                final Piece movedPiece, 
                final int destinationCoordinate) {
        this.board = board; 
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = movedPiece.isFirstMove();
    }
    
    private Move(final Board board, 
                 final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }
    
    @Override
    public int hashCode(){
         final int prime = 31;
         int result = 1;
         result = prime * result + this.destinationCoordinate;
         result = prime * result + this.movedPiece.hashCode();
         result = prime * result + this.movedPiece.getPiecePosition();
         result = result + (isFirstMove ? 1 : 0);
         return result;
        
    }
    
    @Override
    public boolean equals(final Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;
        return  getCurrentCoordinate() == otherMove.getCurrentCoordinate() && 
                getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
               getMovedPiece().equals(otherMove.getMovedPiece());
    }
    
    public int getCurrentCoordinate() {
        return this.getMovedPiece().getPiecePosition();
    }
    
    
    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }
    
    public Piece getMovedPiece(){
        return this.movedPiece;
    }
    
    public boolean isAttack() {
        return false;
    }
    
    public boolean isCastlingMove(){
        return false;
    }
    
    public Piece getAttackedPiece() {
        return null;
    }
    
    
    
     public Board execute() {
            final Builder builder = new Builder();
            // set pieces
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            // set opponent pieces
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            
            //move the moved piece
            builder.setPiece(this.movedPiece.movePiece(this));
            
            if (builder.boardConfig.isEmpty()) {
                throw new IllegalStateException("Builder has no pieces set after move execution.");
            }
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            Board newBoard = builder.build();
            if (newBoard == null) {
                throw new IllegalStateException("new board is null after building it");
            }
            return newBoard;
            
            
        }

    
    
    public static final class MajorMove extends Move{

        public MajorMove(final Board board, 
                         final Piece movedPiece, 
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
        
        @Override
        public boolean equals (final Object other) {
            return this == other || other instanceof MajorMove && super.equals(other);
        }
        
        @Override
        public String toString() {
            return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
         
    }
    
    public static class MajorAttackMove
            extends AttackMove {

        public MajorAttackMove(final Board board,
                               final Piece pieceMoved,
                               final int destinationCoordinate,
                               final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorAttackMove && super.equals(other);

        }
        
        @Override
        public String toString () {
            return movedPiece.getPieceType() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }

        

    }

       
    public static class AttackMove extends Move {

        final Piece attackedPiece;

            public AttackMove(final Board board, 
                              final Piece movedPiece, 
                              final int destinationCoordinate, 
                              final Piece attackedPiece) {
                super(board, movedPiece, destinationCoordinate);
                this.attackedPiece = attackedPiece;
            }
            
            @Override
            public int hashCode() {
                return this.attackedPiece.hashCode() + super.hashCode();
            }
            
            @Override
            public boolean equals(final Object other) {
                if(this == other) {
                    return true;
                }
                if(!(other instanceof AttackMove)) {
                    return false;
                }
                final AttackMove otherAttackMove = (AttackMove) other;
                return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
            }

            @Override
            public boolean isAttack() {
                return true;
            }
            
            @Override
            public Piece getAttackedPiece() {
                return this.attackedPiece;
            }
            
            @Override
            public String toString() {
                return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
            }
    }
    
    public static class PawnMove extends Move{

        public PawnMove(
                final Board board, 
                final Piece movedPiece, 
                final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
        
        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnMove && super.equals(other);
            
        }
        
        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
        
    }
    
    public static class PawnAttackMove extends AttackMove{

        public PawnAttackMove(final Board board, 
                              final Piece movedPiece, 
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
        
        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }
        
        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0,1) + "x" +
                    BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
        
    }
    
    public static final class PawnEnPassantAttackMove extends PawnAttackMove{

        public PawnEnPassantAttackMove(final Board board, 
                                       final Piece movedPiece, 
                                       final int destinationCoordinate,
                                       final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
        
        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnEnPassantAttackMove && super.equals(other);
        }
        
        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                if(!piece.equals(this.getAttackedPiece())) {
                    builder.setPiece(piece);
                }
            }
            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
        
    }
    
    public static final class PawnJump extends Move{

        public PawnJump(
                final Board board, 
                final Piece movedPiece, 
                final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
        
        @Override
        public Board execute() {
            final Builder builder = new Builder();

            // Retain all current player's pieces except the moved pawn
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            // Retain all opponent's pieces
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            // Move the pawn to its destination
            builder.setPiece(this.movedPiece.movePiece(this));

            // Set the next move maker
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

            // Build the new board
            return builder.build();
        }

        
        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }
    
     static abstract class CastleMove extends Move{
         
         protected final Rook castleRook;
         protected final int castleRookStart;
         protected final int castleRookDestination;

        public CastleMove(
                final Board board,
                final Piece movedPiece,
                final int destinationCoordinate,
                final Rook castleRook,
                final int castleRookStart,
                final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookDestination = castleRookDestination;
            this.castleRookStart = castleRookStart;
        }
        
        public Rook getCastleRook(){
            return this.castleRook;
        }
        
        @Override
        public boolean isCastlingMove(){
            return true;
        }
        
        @Override
        public Board execute() {
            final Builder builder = new Builder();
            
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
                
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRook.getPieceAlliance(), this.castleRookDestination));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
            }       
        
        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castleRook.hashCode();
            result = prime * result + this.castleRookDestination;
            return result;
        }
        
        @Override
        public boolean equals (final Object other) {
           if(this == other) {
               return true;
           }  if(!(other instanceof CastleMove)) {
               return false;
           }
           
           final CastleMove otherCastleMove = (CastleMove) other;
           return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
        }
    }
     
    public static final class KingSideCastleMove extends  CastleMove{

        public KingSideCastleMove(
                final Board board,
                final Piece movedPiece,
                final int destinationCoordinate,
                final Rook castleRook,
                final int castleRookStart,
                final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }
        
        @Override
        public boolean equals (final Object other) {
            return this == other || other instanceof KingSideCastleMove && super.equals(other);
            
        }
        
        @Override
        public String toString () {
            return "0-0";
        }
        
    }
     
    public static final class QueenSideCastleMove extends CastleMove{

        public QueenSideCastleMove(
                final Board board,
                final Piece movedPiece,
                final int destinationCoordinate,
                final Rook castleRook,
                final int castleRookStart,
                final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }
        
        @Override
        public String toString () {
            return "0-0-0";
        }
        
        public boolean equals (final Object other) {
            return this == other || other instanceof QueenSideCastleMove && super.equals(other);
            
        }
        
    }
    
    public static final class NullMove extends Move{

        public NullMove() {
            super(null, 65);
        }
        @Override
        public Board execute() {
            throw new RuntimeException("cannot execute null move");
        }
        
        @Override
        public int getCurrentCoordinate() {
            return -1;
        }
    }
    
    public static class MoveFactory {
        
        private MoveFactory() {
        }
        
        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
            for(final Move move : board.getAllLegalMoves()) {
                if(move.getCurrentCoordinate() == currentCoordinate &&
                   move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;

        }
    }
    
    
}