/*Copyright 2008 by Vladimir Polony, Stupy 24, Banska Bystrica, Slovakia

This file is part of OpenRep FREE homeopathic software.

    OpenRep FREE is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    OpenRep FREE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with OpenRep FREE.  If not, see <http://www.gnu.org/licenses/>.*/

/* This class generates the Repertorization Results Dialog
 */

package prescriber;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author vladimir
 */
public class RepertorizationResults {
    /** contains the list of grades*/
    public ArrayList<Double> grades = new ArrayList();
    /** the main table*/
    public JTable ResultTable = new JTable();
    /** names of symptoms - first column of the table*/
    public String[] firstcol;
    /** main frame of the application*/
    private JFrame frame;
    /** currently opened repertory*/
    private Repertory main_rep;
    /** contains the first row of the table (remedy shortcuts) */
    public String[] firstrow;
    /** this table represents the fixed table column */
    JTable fixed;
    
/** Class used to introduce the Color system in the RepertorizationResultDialog's ResultTable
 * 
 */
class RepertorizationDialogTableCellRenderer extends DefaultTableCellRenderer 
{
    
    public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);
        String val;
        if (value != null)
        val = value.toString();
        else
        val = null;
        setHorizontalAlignment(CENTER);
        if (val == null || val.equals("")) cell.setBackground(Color.white);
        else
        {
            double d_val = 0;
            try{
                d_val = Double.parseDouble(val);
            }
            catch (Exception ex) {                
            }
            for (int x = 0; x < grades.size(); x++) {
                if (grades.get(x).equals(d_val)) {
                    if (x == 0) cell.setBackground(new Color(200, 255, 200));
                    else
                    if (x == 1) cell.setBackground(new Color(180, 180, 255));
                    else
                    if (x == 2) cell.setBackground(new Color(255, 200, 200));
                    else
                    if (x == 3) cell.setBackground(new Color(255, 150, 150));
                    else
                    cell.setBackground(new Color(255, 80, 80));
                    setText(String.valueOf(x+1));
                }
            }
        }
        return cell;
    }
}
/** Class used to implement fixed cols of the table
 * 
 */
class FixedColumnScrollPane extends JScrollPane
{
    public FixedColumnScrollPane(JTable main, int fixedColumns)
    {
        super( main );
 
        //  Use the table to create a new table sharing
        //  the DataModel and ListSelectionModel
 
        fixed = new JTable( main.getModel() );
        fixed.setSize(300, 200);
        fixed.setMaximumSize(new Dimension(300, 200));
        fixed.setSelectionModel( main.getSelectionModel() );
        fixed.getTableHeader().setReorderingAllowed( false );
        fixed.getTableHeader().setResizingAllowed( true );
        fixed.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        main.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
 
        //  Remove the fixed columns from the main table
 
        for (int i = 0; i < fixedColumns; i++)
        {
            TableColumnModel columnModel = main.getColumnModel();
            columnModel.removeColumn( columnModel.getColumn( 0 ) );
        }
 
        //  Remove the non-fixed columns from the fixed table
 
        while (fixed.getColumnCount() > fixedColumns)
        {
            TableColumnModel columnModel = fixed.getColumnModel();
            columnModel.removeColumn( columnModel.getColumn( fixedColumns ) );
        }
 
        //  Add the fixed table to the scroll pane
 
        fixed.setPreferredScrollableViewportSize(fixed.getMaximumSize());
        setRowHeaderView( fixed );
        setCorner(JScrollPane.UPPER_LEFT_CORNER, fixed.getTableHeader());
    }};

    /** This method creates the form and opens it with all the data
     * 
     * @param width
     * @param height
     * @param mainframe
     * @param rep
     */
    public void UpdateRepertorization(int width, int height, JFrame mainframe, Repertory rep) {        
        this.main_rep = rep;            
        TableCellRenderer renderer = new RepertorizationDialogTableCellRenderer();
                    try{
                        ResultTable.setDefaultRenderer( Class.forName
                        ( "java.lang.Integer" ), renderer );
                    }catch (Exception ex) {}        
                            for (int i = 0; i < ResultTable.getColumnCount(); i++) {
                    TableColumn column = ResultTable.getColumnModel().getColumn(i);
                    column.setCellRenderer(renderer);
                    }  

      JScrollPane scrollPane= new FixedColumnScrollPane(ResultTable, 1 );

      /** Mousemotionlistener used to listen to the header of the fixed table -> changing the col widths
       * 
       */
      MouseMotionListener mml_header = new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                 fixed.setSize(e.getX(), 200);
                 fixed.setMaximumSize(new Dimension(e.getX(), 200));
                 fixed.setMinimumSize(new Dimension(e.getX(), 200));
                 fixed.setPreferredScrollableViewportSize(fixed.getMaximumSize());
                 fixed.invalidate();
            }

            public void mouseMoved(MouseEvent e) {
                
            }
        };      
      
      /** MotionListener that displays the tooltip text
       * 
       */
      MouseMotionListener mml = new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                
            }

            public void mouseMoved(MouseEvent e) {
                int row = ResultTable.rowAtPoint(e.getPoint());
                int col = ResultTable.columnAtPoint(e.getPoint());
                String full_name = main_rep.GetRemedyName(main_rep.GetRemedyIndexFromShortCut(firstrow[col]));
                ResultTable.setToolTipText(firstcol[row] + " ("+full_name+")");
            }
        };
        
        ResultTable.addMouseMotionListener(mml);
        fixed.getTableHeader().addMouseMotionListener(mml_header);
      
        frame = new JFrame("Ergebnisse der Repertorisierung");
        frame.getContentPane().add( scrollPane );
        frame.setSize(width, height);
        frame.setLocationRelativeTo(mainframe);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);      
      
      //this.add(scrollPane);        
    }

}
