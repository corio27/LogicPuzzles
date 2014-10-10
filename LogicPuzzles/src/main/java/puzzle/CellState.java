package puzzle;
import java.io.Serializable;
public class CellState   implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	public static final CellState ENABLE = new CellState( "X" );
	     public static final CellState DISABLE = new CellState( "O" );
	     public static final CellState VOID = new CellState( " " );
	     private final String          name;

	     private  CellState(final String name) {
	         this.name = name;
	     }
	     public String  toString() {
	         return "CellState: " + this.name;
	     }
}
