package puzzle;


import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import swing.PuzzleGridView;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

public class PuzzleStart implements ActionListener {
	
	
    private JFrame mainFrame;
    private PuzzleGridView puzzleGridView;
    private Puzzle puzzle;
    
   
   
    private JMenuItem exitMenuItem = new JMenuItem("Exit");
    private BorderLayout borderLayout = new BorderLayout();
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.RIGHT);
    private JPanel buttonPanel = new JPanel(flowLayout);
    private JPanel dimensionPanel = new JPanel(flowLayout);
    
    private JButton solveButton = new JButton("Solve");
    private JButton stepButton  = new JButton("Step");
    private JButton dumpButton  = new JButton("Dump");
    private JButton addDimensionButton  = new JButton("Add Dimension");
    private JButton addValuesButton  = new JButton("Add Values");
    private JTextField dimensionValues = new JTextField(20);
    private JComboBox dimensionList;
    private JComboBox dimensionTypeList;
    private JTextArea textarea = new JTextArea(15,20);
	public static void main(String[] args) {
        new PuzzleStart().init(true);
    }
	 public PuzzleStart() {
	    
	 
	 }
	 public void init(boolean exitOnClose) {
		
		    
	        mainFrame = new JFrame("Logic Puzzle");
	       
	     
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
	        addDimensionButton.addActionListener(this);
	        dimensionList= new JComboBox();
	        dimensionList.setMaximumRowCount(3);
	        dimensionList.setEditable(true);
	        dimensionList.getEditor().addActionListener(new ActionListener() {
	        
	            public void actionPerformed(ActionEvent e) {
	            	if(dimensionList.getItemCount()<3)
	                {
	            	Object newItem = dimensionList.getEditor().getItem();                  
	                DefaultComboBoxModel dcbm = (DefaultComboBoxModel) dimensionList.getModel();
	                dcbm.addElement(newItem);
	                dcbm.setSelectedItem(newItem);
	                }else
	                	dimensionList.setEditable(false);
	                	
	            }
	        });
	        String[] typeDimensionStrings = { "int", "String"};
	        dimensionTypeList= new JComboBox(typeDimensionStrings);
	        addDimensionButton.addActionListener(this);
	        
	        dimensionPanel.setPreferredSize(new Dimension(300, 600));
	        dimensionPanel.add(dimensionList);
	        dimensionPanel.add(dimensionTypeList);
	        dimensionPanel.add(addDimensionButton);
	        dimensionPanel.add(dimensionValues);
	        
			dimensionPanel.add(addValuesButton);
			String[] columnNames = {"Dimension",
                    "Type",
                    "Value"
                    };
			
			textarea.setEditable(false);
			dimensionPanel.add(textarea);
	        mainFrame.add(BorderLayout.EAST,dimensionPanel);
	        mainFrame.add(BorderLayout.SOUTH, buttonPanel);
	        
	        mainFrame.setSize(900,600);
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

	        
	        } else if (ev.getSource().equals(exitMenuItem)) {
	            if (mainFrame.getDefaultCloseOperation() == WindowConstants.EXIT_ON_CLOSE) {
	                System.exit(0);
	            } else {
	                mainFrame.dispose();
	            }

	        }  else {
	            //
	        }
	    }
}
