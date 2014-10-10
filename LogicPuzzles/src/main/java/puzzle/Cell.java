
package puzzle;

import java.util.Set;


public class Cell  {

  
    
    private CellSqr   cellSqr;
    private Set<Cell> exCells;
    private CellState cellState = CellState.VOID;
    private int       col;
    private int       row;
        public  Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
        public int  getCol() {
            return col;
        }
        public int  getRow() {
            return row;
        }

    

    public Cell() {
        super();
    }
    
   
   
    
    /**
     * Return the set of Cell objects where contents are mutually exclusive with
     * this cell; they are in the same row or same column or same block.
     * 
     * @return a Set of Cell objects not including this cell.
     */
    public Set<Cell> getExCells() {
        return exCells;
    }
    
    
   
    

    /**
     * Returns the 3x3 block group of nine of this cell.
     * @return a cellSqr object.
     */
    public CellSqr getCellSqr() {
        return cellSqr;
    }
    public CellState  getCellState() {

    	

    	        return this.cellState;

    	

    	    }
    public void   setCellState(final CellState newState) {

    	

    	        this.cellState = newState;

    	

    	    }
   

    


}
