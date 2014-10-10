

package puzzle;
import swing.PuzzleGridModel;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;



public class Puzzle  implements PuzzleGridModel {

    public static Puzzle puzzle;
    
    public  Cell[][]    cells;
    private CellSqr[][] sqrs = new CellSqr[][]{ new CellSqr[4], new CellSqr[4], new CellSqr[4] };
   
    
    private KieContainer kc;
    private KieSession session;
    private PuzzleWorkingMemoryListener workingMemoryListener = new PuzzleWorkingMemoryListener();
    private Counter counter;
    private Boolean explain = false;
    private FactHandle steppingFactHandle;
    private Stepping stepping;
    private boolean unsolvable = false;
    
    /**
     * Constructor.
     */
    public Puzzle(KieContainer kc) {
        this.kc = kc;
        puzzle = this;
    }
    
   
    
    /**
     * Nice printout of the grid.
     */
    public void dumpGrid() {
        /*Formatter fmt = new Formatter(System.out);
        fmt.format("       ");
        for (int iCol = 0; iCol < 9; iCol++) {
            fmt.format("Col: %d     ", iCol);
        }
        System.out.println();
        for (int iRow = 0; iRow < 9; iRow++) {
            System.out.print("Row " + iRow + ": ");
            for (int iCol = 0; iCol < 9; iCol++) {
                if (cells[iRow][iCol].getValue() != null) {
                    fmt.format(" --- %d --- ", cells[iRow][iCol].getValue());
                } else {
                    StringBuilder sb = new StringBuilder();
                    Set<String> perms = cells[iRow][iCol].getFree();
                    for (int i = 1; i <= 9; i++) {
                        if (perms.contains(i)) {
                            sb.append(i);
                        } else {
                            sb.append(' ');
                        }
                    }
                    fmt.format(" %-10s", sb.toString());
                }
            }
            System.out.println();
        }
        fmt.close();*/
    }
    
    /**
     * Checks that everything is still according to the sudoku rules.
     */
    public void consistencyCheck() {
        /*for (int iRow = 0; iRow < 9; iRow++) {
            for (int iCol = 0; iCol < 9; iCol++) {
                Cell cell = cells[iRow][iCol];
                String value = cell.getValue();
                if (value != null) {
                    if (! cell.getFree().isEmpty()) {
                        throw new IllegalStateException("free not empty");
                    }
                    // any containing group
                    for (Cell other: cell.getExCells()) {
                        // must not occur in any of the other cells
                        if (value.equals(other.getValue())) {
                            throw new IllegalStateException("duplicate");
                        }
                        // must not occur in the permissibles of any of the other cells
                        if (other.getFree().contains(value)) {
                            throw new IllegalStateException("not eliminated");
                        }
                    }
                }
            }
        }
        
        for (int i = 0; i < rows.length; i++) {
            Set<String> aSet = new HashSet<String>();
            for (int j = 0; j < rows[i].getCells().size(); j++) {
                Cell cell = rows[i].getCells().get(j);
                String value = cell.getValue();
                if (value != null) {
                    aSet.add(value);
                } else {
                    aSet.addAll(cell.getFree());
                }
            }
            if (! aSet.equals(CellGroup.ALL_FOUR)) {
                throw new IllegalStateException("deficit in row");
            }
        }
        
        for (int i = 0; i < cols.length; i++) {
            Set<String> aSet = new HashSet<String>();
            for (int j = 0; j < cols[i].getCells().size(); j++) {
                Cell cell = cols[i].getCells().get(j);
                String value = cell.getValue();
                if (value != null) {
                    aSet.add(value);
                } else {
                    aSet.addAll(cell.getFree());
                }
            }
            if (! aSet.equals(CellGroup.ALL_FOUR)) {
                throw new IllegalStateException("deficit in column");
            }
        }

        for (int ir = 0; ir < sqrs.length; ir++) {
            for (int ic = 0; ic < sqrs[ir] .length; ic++) {
                Set<String> aSet = new HashSet<String>();
                for (int j = 0; j < sqrs[ir][ic].getCells().size(); j++) {
                    Cell cell = sqrs[ir][ic].getCells().get(j);
                    String value = cell.getValue();
                    if (value != null) {
                        aSet.add(value);
                    } else {
                        aSet.addAll(cell.getFree());
                    }
                }
                if (! aSet.equals(CellGroup.ALL_FOUR)) {
                    throw new IllegalStateException("deficit in square");
                }
            }
        }
        System.out.println("+++ check OK +++");*/
    }
    
   
    public void solve() {
        if (this.isSolved()) return;
        explain = false;
        session.setGlobal("explain", explain);
        if( steppingFactHandle != null ){
            session.delete( steppingFactHandle );
            steppingFactHandle = null;
            stepping = null;
        }
        this.session.fireAllRules();
//        dumpGrid();
    }
    
   
    public void step() {
        if (this.isSolved()) return;
        explain = true;
        session.setGlobal("explain", explain);
        this.counter.setCount(1);
        session.update(session.getFactHandle(this.counter), this.counter);
        if( steppingFactHandle == null ){
            steppingFactHandle = session.insert( stepping = new Stepping() );
        }
        this.session.fireUntilHalt();
        if( stepping.isEmergency() ){
            this.unsolvable = true;
        }
//        dumpGrid();
    }

    public boolean isSolved() {
        for (int iRow = 0; iRow < 8; iRow++) {
            for (int iCol = 0; iCol < 8; iCol++) {
                if (cells[iRow][iCol].getCellState() == CellState.VOID) return false;
            }
        }
        return true;
    }
    
    public boolean isUnsolvable(){
        return unsolvable;
    }
    
    private void create() {
     /*   for (int i = 0; i < 9; i++) {
            session.insert(Integer.valueOf(i+1));
            rows[i] = new CellRow(i);
            cols[i] = new CellCol(i);
        }
        
        cells = new Cell[9][];
        for (int iRow = 0; iRow < 9; iRow++) {
            cells[iRow] = new Cell[9];
            for (int iCol = 0; iCol < 9; iCol++) {
                Cell cell = cells[iRow][iCol] = new Cell();
                rows[iRow].addCell(cell);
                cols[iCol].addCell(cell);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sqrs[i][j] = new CellSqr(rows[i*3], rows[i*3+1], rows[i*3+2],
                                         cols[j*3], cols[j*3+1], cols[j*3+2]);
            }
        }

        for (int iRow = 0; iRow < 9; iRow++) {
            for (int iCol = 0; iCol < 9; iCol++) {
                cells[iRow][iCol].makeReferences(rows[iRow], cols[iCol], sqrs[iRow/3][iCol/3]);
                session.insert(cells[iRow][iCol]);
            }
            session.insert(rows[iRow]);
            session.insert(cols[iRow]);
            session.insert(sqrs[iRow/3][iRow%3]);
        }*/
    }

   
    public void setCellValues(String[][] cellValues) {
       /* if (session != null) {
            session.removeEventListener(workingMemoryListener);
            session.dispose();
            steppingFactHandle = null;
        }
        
        this.session = kc.newKieSession("PuzzleKS");
        session.setGlobal("explain", explain);
        session.addEventListener(workingMemoryListener);

        Setting s000 = new Setting(0, 0, null);
        FactHandle fh000 = this.session.insert(s000);
        this.create();

        int initial = 0;
        for (int iRow = 0; iRow < 9; iRow++) {
            for (int iCol = 0; iCol < 9; iCol++) {
                String value = cellValues[iRow][iCol];
                if (value != null) {
                    session.insert(new Setting(iRow, iCol, value));
                    initial++;
                }
            }
        }
        this.counter = new Counter(initial);
        this.session.insert(counter);
        this.session.delete(fh000);
        this.session.fireAllRules();*/
    }
    
  
    @Override
    public String toString() {
       /* StringBuilder sb = new StringBuilder();

        sb.append("Puzzle:").append('\n');
        for (int iRow = 0; iRow < 9;  iRow++) {
            sb.append("  ").append(rows[iRow].toString()).append('\n');
        }
        
        return sb.toString();*/
    	return "";
    }
    
    class PuzzleWorkingMemoryListener implements RuleRuntimeEventListener {

        public void objectInserted(ObjectInsertedEvent ev) {
            if (ev.getObject() instanceof Counter) {
    //            fireRestartEvent(null);
            }
        }

        public void objectDeleted(ObjectDeletedEvent ev) {
        }

        public void objectUpdated(ObjectUpdatedEvent ev) {
            if (ev.getObject() instanceof Cell) {
                Cell cell = (Cell) ev.getObject();
                if (cell.getCellState()!= CellState.VOID) {
    //                fireCellUpdatedEvent(new PuzzleGridEvent(this,
                	//                        cell.getRowNo(),
                	//        cell.getColNo(),
                	//       cell.getValue()));
                }
            }
        }
    }
    
    public void validate(){
        session.getAgenda().getAgendaGroup( "validate" ).setFocus();
        session.fireUntilHalt();
    }

	public String getCellState(int iRow, int iCol) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCellState(int iRow, int iCol) {
		// TODO Auto-generated method stub
		
	}
}
