package puzzle;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;



import puzzle.swing.PuzzleGridSamples;
import puzzle.swing.PuzzleGridView;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;







 

public class PuzzleStart implements ActionListener {
	
	
    private JFrame mainFrame;
    private PuzzleGridView puzzleGridView;
    private Puzzle puzzle;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenu samplesMenu = new JMenu("Samples");
    private JMenuItem openMenuItem = new JMenuItem("Open...");
    private JMenuItem exitMenuItem = new JMenuItem("Exit");
    private BorderLayout borderLayout = new BorderLayout();
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT);
    private JPanel buttonPanel = new JPanel(flowLayout);
    private JButton solveButton = new JButton("Solve");
    private JButton stepButton  = new JButton("Step");
    private JButton dumpButton  = new JButton("Dump");
    private JFileChooser fileChooser;
	public static void main(String[] args) {
        new PuzzleStart().init(true);
    }
	 public PuzzleStart() {
	    
	 
	 }
	 public void init(boolean exitOnClose) {
	        mainFrame = new JFrame("Logic Puzzle");
	        for (String sampleName : PuzzleGridSamples.getInstance().getSampleNames()){
	            JMenuItem menuItem = new JMenuItem(sampleName);
	            menuItem.addActionListener(this);
	            samplesMenu.add(menuItem);
	        }
	        fileMenu.add(samplesMenu);
	        openMenuItem.addActionListener(this);
	        fileMenu.add(openMenuItem);
	        exitMenuItem.addActionListener(this);
	        fileMenu.add(exitMenuItem);
	        menuBar.add(fileMenu);
	        mainFrame.setJMenuBar(menuBar);
	        puzzleGridView = new PuzzleGridView();

	        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
	        puzzle = new Puzzle( kc );

	        mainFrame.setLayout(borderLayout);
	        mainFrame.add(BorderLayout.CENTER, puzzleGridView);

	        buttonPanel.add(solveButton);
	        solveButton.addActionListener(this);
	        buttonPanel.add(stepButton);
	        stepButton.addActionListener(this);  
	        buttonPanel.add(dumpButton);
	        buttonsActive( false );
	        dumpButton.addActionListener(this);
	        mainFrame.add(BorderLayout.SOUTH, buttonPanel);
	        mainFrame.setSize(400,400);
	        mainFrame.setLocationRelativeTo(null); // Center in screen
	        mainFrame.setDefaultCloseOperation(exitOnClose ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
	        mainFrame.setResizable( false );
	        mainFrame.setVisible(true);
	        puzzleGridView.setModel(puzzle);
	    }

	    private void buttonsActive(boolean active) {
	        solveButton.setEnabled(active);
	        stepButton.setEnabled(active);
	        dumpButton.setEnabled(active);
	    }


	    private void runFile(String path){
	        Integer[][] values = new Integer[9][];
	        Reader fileIsReader = null;
	        BufferedReader rdr = null;
	        try {
	            fileIsReader = new InputStreamReader(new FileInputStream(path));
	            rdr = new BufferedReader( fileIsReader );
	            String line = rdr.readLine();
	            for( int iRow = 0; iRow < 9;  iRow++ ){
	                values[iRow] = new Integer[9];
	                for( int iCol = 0; iCol < 9; iCol++ ){
	                    if( line != null && line.length() > iCol ){
	                        char c = line.charAt( iCol );
	                        if( '1' <= c && c <= '9' ){
	                            values[iRow][iCol] = Integer.valueOf( c - '0' );
	                        }
	                    }
	                }
	                line = rdr.readLine();
	            }
	            puzzle.setCellValues( values );
	            puzzle.validate();
	        } catch ( FileNotFoundException e ) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if( rdr != null ) {
	                try {
	                    rdr.close();
	                } catch (IOException e) {
	                    // nothing to do
	                }
	            }
	        }
	    }

	    public void actionPerformed(ActionEvent ev){
	        if (ev.getSource().equals(solveButton) ) {
	        	puzzle.solve();
	            buttonsActive(false);
	            if (!puzzle.isSolved()) {
	            	puzzle.dumpGrid();
	                System.out.println( "Sorry - can't solve this grid." );
	            }

	        } else if (ev.getSource().equals(stepButton)) {
	        	puzzle.step();
	            if (puzzle.isSolved() || puzzle.isUnsolvable()) buttonsActive(false);
	            if (puzzle.isUnsolvable()) {
	                puzzle.dumpGrid();
	                System.out.println( "Sorry - can't solve this grid." );
	            }

	        } else if (ev.getSource().equals(dumpButton)) {
	            puzzle.dumpGrid();

	        } else if (ev.getSource().equals(openMenuItem)) {
	            if( fileChooser == null ){
	                fileChooser = new JFileChooser();
	            }

	            try {
	                if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
	                    String path = fileChooser.getSelectedFile().getCanonicalPath();
	                    System.out.println(path);
	                    runFile(path);
	                    buttonsActive(true);
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }

	        } else if (ev.getSource().equals(exitMenuItem)) {
	            if (mainFrame.getDefaultCloseOperation() == WindowConstants.EXIT_ON_CLOSE) {
	                System.exit(0);
	            } else {
	                mainFrame.dispose();
	            }

	        } else if (ev.getSource() instanceof JMenuItem) {
	            JMenuItem menuItem = (JMenuItem) ev.getSource();
	            Integer[][] sample = PuzzleGridSamples.getInstance().getSample(menuItem.getText());
	            puzzle.setCellValues(sample);
	            puzzle.validate();
	            buttonsActive(true);
	        } else {
	            //
	        }
	    }
}
