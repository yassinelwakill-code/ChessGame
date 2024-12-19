
import com.chess.engine.board.Board;
import com.chess.gui.Table;



/**
 *
 * @author yassi
 */
public class JChess {
    
    public static void main(String [] args) {
        
        Board board = Board.createStandardBoard();
        
        System.out.println(board);
        
        Table.get().show();
        
    }
    
}
