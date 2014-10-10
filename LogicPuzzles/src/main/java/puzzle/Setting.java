

package puzzle;


public class Setting {
    
    private int rowNo;
    private int colNo;
    private CellState state;
    
    
    public Setting (int row, int col, CellState state) {
        this.rowNo = row;
        this.colNo = col;
        this.state = state;
    }
    
    

    
    public int getRowNo() {
        return rowNo;
    }

   
    public int getColNo() {
        return colNo;
    }
    
    public CellState getState() {
        return state;
    }
}
