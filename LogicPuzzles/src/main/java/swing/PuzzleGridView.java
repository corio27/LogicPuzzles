
package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;


public class PuzzleGridView
extends JComponent

   {

   private static final long serialVersionUID = 510l;
   private PuzzleGridModel model;
   private GridLayout gridLayout;
   private JButton jButtons[][];
   
   public PuzzleGridView(){
      gridLayout = new GridLayout(PuzzleGridModel.NUM_ROWS, PuzzleGridModel.NUM_COLS);
      setLayout(gridLayout);
      jButtons = new JButton[PuzzleGridModel.NUM_ROWS][PuzzleGridModel.NUM_COLS];
      for (int row=0; row<jButtons.length; row++) {
         for (int col=0; col<jButtons[row].length; col++){
        	 JButton jButton ;
        	 String dim;
        	 if (row==8 && col==8)
             {
            	 jButton  = new JButton("");
            	 jButton.setEnabled(false);
             }else  if (row==8) {
            	if(col<4)
            	{
            		 dim="1."+col;
            		jButton = new JButton(dim);
            		
            	}else
            	{
            	    dim="2."+(col-4);
            		jButton = new JButton(dim);
            	}
            	
            	jButton.setEnabled(false);
            	
             }else if (col==8) {
            	 if(row<4)
             	{
             		 dim="1."+row;
             		jButton = new JButton(dim);
             		
             	}else
             	{
             	    dim="3."+(row-4);
             		jButton = new JButton(dim);
             	}
            	 
            	 jButton.setEnabled(false);
             }else if (row<4 && col<4)
             {
            	 jButton  = new JButton("");
            	 jButton.setEnabled(false);
             }
             else
             {
            	 jButton  = new JButton(""); 
            	 jButton.setEnabled(true);
             }
           
      
            
            JPanel panel = new JPanel();
            
            int top = row % PuzzleGridModel.INNER_GRID_HEIGHT == 0 ? 2 : 0;
            int left = col % PuzzleGridModel.INNER_GRID_WIDTH == 0 ? 2 : 0;
            int bottom = row == PuzzleGridModel.NUM_ROWS-1 ? 2 : 0;
            int right = col == PuzzleGridModel.NUM_COLS-1 ? 2 : 0;
           
            panel.setBorder(BorderFactory.createMatteBorder( top, left, bottom, right, Color.BLACK ));
            panel.setLayout(new BorderLayout());
            
            
            jButtons[row][col] = jButton;
            
            panel.add(BorderLayout.CENTER, jButton);
            add(panel);
         }
      }
   }
   
   public PuzzleGridView(PuzzleGridModel model){
      this();
      setModel(model);
   }
  
   public void setModel(PuzzleGridModel model){
    
      this.model = model;
     
      
     
   }
  




   
   
}
