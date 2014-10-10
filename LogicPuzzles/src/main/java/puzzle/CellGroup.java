

package puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


public abstract class CellGroup  {

 

    private List<Cell> cells = new ArrayList<Cell>();
    
   
    protected CellGroup() {
       
    }

   
    public void addCell(Cell cell) {
        cells.add(cell);
    }

    
    public List<Cell> getCells() {
        return cells;
    }
}
