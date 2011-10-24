/*Copyright 2008 by Vladimir Polony, Stupy 24, Banska Bystrica, Slovakia

This file is part of OpenRep FREE homeopathic software.

    OpenRep FREE is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free iSoftware Foundation, either version 3 of the License, or
    (at your option) any later version.

    OpenRep FREE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with OpenRep FREE.  If not, see <http://www.gnu.org/licenses/>.*/

/* Main Frame of OpenRep
 */

package prescriber;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import javax.swing.tree.TreePath;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventObject;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import org.jdesktop.application.Application.ExitListener;

/**- 
 * The application's main frame.
 */
public class PrescriberView extends FrameView {
    public static final String version = "1.0-RC2";

    // the list of commands used in the console
    public static final String Command_Help = "help";
    public static final String Command_QuitConsole = "q";
    public static final String Command_Quit = "qq";    
    public static final String Command_ForceQuit = "qqq";
    public static final String Command_SaveRepertory = "saverep";
    public static final String Command_SaveRepertoryAs = "saverep ";
    public static final String Command_DisplayRepertories = "showrep";
    public static final String Command_ImportRepertory = "importrep";    
    public static final String Command_OpenRepertory = "openrep";
    public static final String Command_CurrentRepertory = "currentrep";
    public static final String Command_RemoveRepertory = "removerep";
    public static final String Command_CreateRepertory = "createrep";
    public static final String Command_EditRepertory = "editrep";    
    public static final String Command_GarbageCollector = "gc";        
    public static final String Command_FreeMemory = "freemem";
    // the list of action commands used in the ActionPerformed to identify actions
    public static final String DeleteSymptomFromSelection = "Delete_symptom";
    public static final String DisplaySymptomReferences = "Display_Reference";
    public static final String MainMenu_RepertorizationResults = "Repertorization Results";
    public static final String DisplayRemedyAdditions = "Display_Additions";
    public static final String DisplayTopRubric = "Display_Top_Rubric";
    public static final String DisplayHigherRubric = "Display_Higher_Rubric";
    public static final String AddSymptom = "Add_Symptom";
    public static final String MainMenu_Exit = "Exit";
    public static final String MainMenu_New = "New";
    public static final String MainMenu_Save = "Save";
    public static final String MainMenu_SaveAs = "SaveAs";
    public static final String MainMenu_Open = "Open";        
    public static final String MainMenu_Preferences = "Preferences";
    public static final String MainMenu_PMS = "PMS";
    public static final String MainMenu_MergeSymptoms = "Merge_symptoms";
    public static final String MainMenu_AddMerged = "Add_Merged_Symptoms_To_Repertorization";
    public static final String MainMenu_PositiveFilter = "Positive_Filter";
    public static final String MainMenu_NegativeFilter = "Negative_Filter";
    public static final String MainMenu_RepertoryProperties = "Repertory_properties";
    public static final String MainMenu_NewRepertory = "New_Repertory";
    public static final String MainMenu_Console = "Console";    
    public static final String MainMenu_ImportRepertory = "Import repertory";
    public static final String MainMenu_OpenRepertory = "Open repertory";    
    public static final String MainMenu_RemoveRepertory = "Remove repertory";        
    public static final String MainMenu_Repertorize = "Repertorize case";
    public static final String MainMenu_AutomaticRepertorization = "Automatic repertorization";
    public static final String MainMenu_AddNewSymptom = "Add new symptom";
    public static final String MainMenu_SaveRepertory = "Save repertory";
    public static final String MainMenu_DeleteSymptom = "Delete symptom";
    public static final String MainMenu_EditSymptom = "Edit symptom";
    public static final String MainMenu_EditRemedyAdditions = "Edit Remedy additions";    
    public static final String MainMenu_ExportRepertoryChanges = "Export repertory changes";
    public static final String MainMenu_SaveRepertoryAs = "Save repertory as";
    public static final String MainMenu_SearchInallRepertories = "Search In All Repertories";    
    public static final String MainMenu_ReversedMM = "Reversed Materia Medica";
    public static final String MainMenu_RepertoryTree = "Display repertory tree";
    public static final String MainMenu_PreviousSearchResult = "Display previous search result";
    public static final String MainMenu_NextSearchResult = "Display next search result";

    public static final String MainMenu_Desktop1 = "Desktop_1";
    public static final String MainMenu_Desktop2 = "Desktop_2";
    public static final String MainMenu_Desktop3 = "Desktop_3";
    public static final String MainMenu_Desktop4 = "Desktop_4";
    public static final String MainMenu_Desktop5 = "Desktop_5";    
    
    /** interval in milliseconds in which to save the repertory to the disk*/
    public static final int saveRepertoryInterval = 900000;
    
    /** contains the installation folder into which the program was installed*/        
    private String install_directory = null;

    /** contains the name of the directory where the PMS data is being kept */
    private String pms_directory = null;
    
    /** name of the config file */
    private String config_file = "OpenRep.cfg";

    /** contains the list of searched terms in the repertory*/
    private ArrayList<SearchHistory> search_history = new ArrayList();

    /** cotains the position of the current search_history in the list*/
    private int search_history_pos = -1;

    /** Records the symptom that was selected as the last one in the SymptomTree */
    private String selected_sym = null;

    /** name of the pms - patients file */
    public static final String patients_file = "pms_patients.xml";

    public static int DRAG_SOURCE_NONE = -1;
    public static int DRAG_SOURCE_SYMPTOMTREE = 0;
    public static int DRAG_SOURCE_REMSYMPTOMTREE = 1;

    /** contains the instance of the Configuration class*/
    public Configuration config;

    /** contains the source information about which component was the last to initiate dragging - used to determine
        what to copy when the target is a desktop button - constants = DRAG_SOURCE*/
    private int drag_source = DRAG_SOURCE_NONE;

    /** contains the sort type for the RemSymptomTable */
    private boolean RemSymptomTable_sort_ascending = false;
                
    /** contains the sort row for the RemSymptomTable sort*/
    private int RemSymptomTable_sort_by_col = 2;
        
    /** pointer to this application*/
    private PrescriberView this_prescriber_view = this;    

    /** when user drags from remsymptom tree to a desktop button, here is saved the status of the CTRL button - is used
        to determine whether to COPY or to MOVE*/
    private boolean drag_remsymptom_ctrl = false;

    /** contains the instance of the PMS dialog */
    private PMSDialog pms_dialog = null;

    /** this is enabled on the KeyPressed on the SymptomTree and RemSymptomTree and is handled
     *  and set to false in KeyReleased. This is done to replace the clipboard text that is copied
     *  by the .copy() method of the JTree*/
    private boolean process_ctrlc_key = false;

    /** active dialog */
    public ListDialog active_dialog = null;

    /** if this is true, the expansion of nodes in the SymptomTree will be loaded lazily in a thread. If it is false,
     *  it will not be done by a thread, but in a procedure (this is e.g. useful when expanding tree programatically, as
     *  if it would run in a thread, the speed issue would cause the latter expansions not to load
     * 
     */
    private boolean threaded_symptom_tree_expand = true;

    /** the name of the repertory that will be oneped always on startup*/
    private String default_repertory_name = "";
    /** popup menu for the remsymptom tree*/
    private JPopupMenu PopupMenu_RemSymptomTree = new JPopupMenu();
    /** popup menu for the remsymptom tree*/
    private JPopupMenu PopupMenu_SymptomTree = new JPopupMenu();    
    /** class that contains all the repertories */
    private Database db; 
    /** symptoms that are added to the RemSymptom tree */
    private ArrayList<SelectedSymptomItem> SelectedSymptoms = new ArrayList();
    /** symptoms that are already generated in the tree and that do not have to have their children added*/
    private ArrayList<Integer> generated_symptoms = new ArrayList();        
    /** contains the current repertorization results */
    private ArrayList<RepertorisationResult> results = new ArrayList();
    /** Path and name of the opened file. Is set to null, if none is opened */
    private String current_file_name = null;
    /** contains the icons not used in the menu etc.*/
    private Icons icons;
    /** contains the grades gathered in the repertorization */
    private ArrayList<Double> repertorization_grades = new ArrayList();
    /** contains the top five repertorization results based on the number of symptoms */
    private ArrayList<Integer> top_results_symptom_cnt = new ArrayList();
    /** contains the top five repertorization results based on the value */
    private ArrayList<Integer> top_results_symptom_value = new ArrayList();    
    /** this is the timer responsible for savinbg changes to the repertory*/
    private Timer saverepertory_timer = null;    
    /** contains the position of the ListDialog, initially set to null, after first displaying is set to a value*/
    private Point ListDialog_pos = null;
    /** contains the width of the Listdialog, initially set to -1*/
    private int List_Dialog_width = -1;
    /** contains the height of the ListDialog, initially set to -1*/
    private int ListDialog_height = -1;

    /** Sets the initial positions of the ListDialog
     * 
     * @param ld
     */
    public void SetListDialogPosition (ListDialog ld) {
        ListDialog_pos = ld.getLocation();
        ListDialog_height = ld.getHeight();
        List_Dialog_width = ld.getWidth();
    }

/** Is used to provide a model for the RemSymptomTable
 *
 * @author vladimir
 */
class MyModel extends DefaultTableModel {

    private String[][] data;

    MyModel (String[][] data) {
        this.data = data;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return data[row][column];
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) return "Rem";
        else
        if (column == 1) return "Cnt";
        else
        return "Val";
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }

    @Override
    public int getRowCount() {
        if (data == null) return 0;
        return data.length;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

    /** This mouselistener is used to listen to clicks on the RemSymptomTable header
     * 
     */
    MouseListener RemSymptomTableHeaderListener = new MouseListener() {

        public void mouseClicked(MouseEvent e) {
            int col = RemSymptomTable.columnAtPoint(e.getPoint());
            System.out.println("clicked" + col);
            if (col == RemSymptomTable_sort_by_col) RemSymptomTable_sort_ascending = !RemSymptomTable_sort_ascending;
            else
            {
                RemSymptomTable_sort_ascending = false;
                RemSymptomTable_sort_by_col = col;
            }
            
            GenerateRemSymptomTableThread gen = new GenerateRemSymptomTableThread();
            gen.run();
        }

        public void mousePressed(MouseEvent e) {
            
        }

        public void mouseReleased(MouseEvent e) {
            
        }

        public void mouseEntered(MouseEvent e) {
            
        }

        public void mouseExited(MouseEvent e) {
            
        }

    };    
    
    /** This is the filefilter that is used to display correct file extension types in the opendialogs
     * 
     */
    javax.swing.filechooser.FileFilter RepertorizationFileFilter = new javax.swing.filechooser.FileFilter() {

        public boolean accept(File pathname) {
            if (pathname.isDirectory() || prescriber.Utils.ExtractFileExtension(pathname.getPath()).equalsIgnoreCase(prescriber.Utils.REPERTORIZATION_FILE_EXTENSION)) {
                return true;
            }
            else
            return false;
        }

        @Override
        public String getDescription() {
            return "XML database file";
        }
    };    
    
    /** This is the file filter used to display correct file types in the open / save / load repertory opendialogs
     * 
     */
    javax.swing.filechooser.FileFilter RepertoryFileFilter = new javax.swing.filechooser.FileFilter() {

        public boolean accept(File pathname) {
            if (pathname.isDirectory() || prescriber.Utils.ExtractFileExtension(pathname.getPath()).equalsIgnoreCase(prescriber.Utils.REPERTORY_DESCRIPTOR_FILE_EXTENSION)) {
                return true;
            }
            else
            return false;
        }

        @Override
        public String getDescription() {
            return "Repertory descriptor file";
        }
    };    

    ActionListener timer_listener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            System.out.println("Saving repertory...");
            if (db.GetCurrentRepertory().GetRepertoryChanged()) SaveCurrentRepertory(null);
        }
    };
    
/** Class used to introduce colors into the Result table in PrescriberView
 * 
 */
public class RepertorizationTableCellRenderer extends DefaultTableCellRenderer 
{
    
    public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);
        String val;
        String val1 = null;
        if (value != null)
        val = value.toString();
        else
        val = null;
        if (column == 0) {
            val = table.getValueAt(row, 2).toString();
        }
        if (column == 1) {
            val = table.getValueAt(row, 2).toString();
            val1 = table.getValueAt(row, 1).toString();
        }        
        if (column > 0) setHorizontalAlignment(CENTER);
        else
        setHorizontalAlignment(LEFT);
        cell.setBackground(Color.white);
        cell.setForeground(Color.black);
        if (column == 1) {
            if (val1 != null && !val1.equals("")) {
                int i_val = 0;
                try{
                    i_val = Integer.parseInt(val1);
                    for (int x = 0; x < top_results_symptom_cnt.size(); x++) {
                        if (top_results_symptom_cnt.get(x).equals(i_val) && x == 0) cell.setFont(new Font (cell.getFont().getFontName(), Font.PLAIN, cell.getFont().getSize()));
                        else
                        if (top_results_symptom_cnt.get(x).equals(i_val) && x == 1) cell.setFont(new Font (cell.getFont().getFontName(), Font.PLAIN, cell.getFont().getSize()));
                        else
                        if (top_results_symptom_cnt.get(x).equals(i_val) && x == 2) cell.setFont(new Font (cell.getFont().getFontName(), Font.PLAIN, cell.getFont().getSize()));
                        else
                        if (top_results_symptom_cnt.get(x).equals(i_val) && x == 3) cell.setFont(new Font (cell.getFont().getFontName(), Font.BOLD, cell.getFont().getSize()));
                        else
                        if (top_results_symptom_cnt.get(x).equals(i_val)) cell.setFont(new Font (cell.getFont().getFontName(), Font.BOLD, cell.getFont().getSize()));
                    }
                } catch (Exception e) {}
            }
        }
        if (column == 2 || column == 0 || column == 1) {
            
            if (val != null && !val.equals("")) {
                int i_val = 0;
                try{
                    i_val = Integer.parseInt(val);
                    for (int x = 0; x < top_results_symptom_value.size(); x++) {
                        if (top_results_symptom_value.get(x) <= i_val && x == 0) cell.setBackground(new Color(255, 255, 230));
                        else
                        if (top_results_symptom_value.get(x) <= i_val && x == 1) cell.setBackground(new Color(255, 230, 255));
                        else
                        if (top_results_symptom_value.get(x) <= i_val && x == 2) cell.setBackground(new Color(240, 240, 255));
                        else
                        if (top_results_symptom_value.get(x) <= i_val && x == 3) cell.setBackground(new Color(220, 255, 220));
                        else
                        if (top_results_symptom_value.get(x) <= i_val) cell.setBackground(new Color(180, 255, 180));
                    }
                } catch (Exception e) {}
            }            
        }
        return cell;
    }
}

/** Loads the current repertorization from a file
 * 
 */
public boolean LoadRepertorization(String filename) {
      ArrayList<SelectedSymptomItem> ssi = new ArrayList();
      Utils u = new Utils();
      String rslt = u.LoadRepertorizationFile(filename, ssi);
      if (rslt == null) SelectedSymptoms = ssi;
      else
      {
           JOptionPane.showMessageDialog(mainPanel, rslt);
           return false;
      }
      SelectedSymptoms = ssi;
      UpdateRemedySymptomsTree();
      return true;
}

/** Saves the current repertorization to a file
 * 
 */
public boolean SaveCurrentRepertorization(String filename) {
    Utils u = new Utils();
    String rslt = u.SaveRepertorizationToFile(SelectedSymptoms, filename);
    if (rslt != null) {
        JOptionPane.showMessageDialog(mainPanel, rslt);
        return false;
    }
    return true;
}

/** Deletes all symptoms from repertorization
 * 
 */
public void DeleteSelectedSymptomsFromRepertorization () {
       PopupMenu_RemSymptomTree.setVisible(false);
       int[] desktops = GetDesktops();
       if (RemSymptomsTree.getSelectionCount() == 0) return;
       for (int x = 0; x < RemSymptomsTree.getSelectionPaths().length; x++) {
           DefaultMutableTreeNode node = (DefaultMutableTreeNode)RemSymptomsTree.getSelectionPaths()[x].getLastPathComponent();
           SelectedSymptomItem ssi = (SelectedSymptomItem)node.getUserObject();
            boolean must_continue = true;            
            while (must_continue) {
                must_continue = false;
                for (int z = 0; z < desktops.length; z++) {
                    for (int y = 0; y < SelectedSymptoms.size(); y++) {
                         if (SelectedSymptoms.get(y).sym_name.equals(ssi.sym_name) && SelectedSymptoms.get(y).repertory_id == ssi.repertory_id && SelectedSymptoms.get(y).desktop == desktops[z]) {
                            SelectedSymptoms.remove(y);
                            must_continue = true;
                            break;
                         }
                    }
                }
            }
        }
        UpdateRemedySymptomsTree();
        if (config.GetAutomaticRepertorization()) RepertorizeCase();
}

/** Saves the current repertorization 
 * 
 * @return true is saving was successfull
 */
public boolean SaveCurrentRepertorization () {
    if (current_file_name == null) {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(RepertorizationFileFilter);
        int result = fc.showSaveDialog(mainPanel);
        if (result == JFileChooser.CANCEL_OPTION) return true;
             current_file_name = fc.getSelectedFile().getPath();
        }
        if (current_file_name == null) return false;
        Utils u = new Utils();
        String rslt = u.SaveRepertorizationToFile(SelectedSymptoms, current_file_name);
        if (rslt != null) {
             JOptionPane.showMessageDialog(mainPanel, rslt);
             return false;
        }
        else
        statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Repertorium wurde erfolgreich gespeichert...");
        return true;
}

/** Sets the current_file_name - name of the repertorization file currently opened
 *
 * @param cur_file_name
 */
public void SetCurrentFileName (String cur_file_name) {
    this.current_file_name = cur_file_name;
}

/** Returns the shortcut of the repertory or "" if there is no opened
 * 
 */
public String GetRepertoryStatusMessageLabel () {
    try {
        return "("+db.GetCurrentRepertory().GetShortCut()+") ";
    } catch (Exception e) {
        return "(Es ist kein Repertorium geöffnet) ";
    }
}

/** Saves the current repertory to a file
 * 
 */
public boolean SaveCurrentRepertory(String file_name) {
     if (saverepertory_timer != null) saverepertory_timer.stop();
     saverepertory_timer = null;
     Logger.AddInitEntry(Logger.Operation_SaveRepertory, file_name);
     mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
     statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Bitte warten während das Repertorium gespeichert wird...");
     mainPanel.invalidate();
     statusMessageLabel.invalidate();
     ArrayList<String> al;
     if (file_name == null) al = db.GetCurrentRepertory().SaveRepertoryToFile(db.GetCurrentRepertory().GetDescriptorFilaName());
     else
     al = db.GetCurrentRepertory().SaveRepertoryToFile(file_name);
     if (al.size() == 0) Logger.AddSuccessEntry(Logger.Operation_SaveRepertory, "");
     else
     for (int x = 0; x < al.size(); x++) {
         Logger.AddFailureEntry(Logger.Status_Failure, al.get(x));
     }             
     mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
     statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Speichern des Repertoriums abgeschlossen...");
     if (al.size() == 0) {
         JOptionPane.showMessageDialog(mainPanel, "Das Repertorium wurde erfolgreich abgespeichert!");
         return true;
     }
     else
     {
         JOptionPane.showMessageDialog(mainPanel, "Es kam zu einem Fehler beim Speichern des Repertoriums. Cannot save data to specified descriptor file.");
         // start timer again, because there was an error while saving
         StartTimer();
         return false;
     }
}

    /** Merges the selected symptoms provided in the parameter and returns the merged symptoms 
     * 
     * @param symptoms
     * @return
     */
    public SelectedSymptomItem MergeSelectedSymptoms (ArrayList<SelectedSymptomItem> asymptoms, String rslt_name) {
         SelectedSymptomItem tgt = new SelectedSymptomItem();
         for (int x = 0; x < asymptoms.size(); x++) {
            SelectedSymptomItem src = asymptoms.get(x);
            if (tgt.maximum_grade < src.maximum_grade) tgt.maximum_grade = src.maximum_grade;
            tgt.repertory_id = -1;
            tgt.repertory_name = "";
            tgt.sym_id = -1;
            tgt.symptom_grade = Database.SYMPTOM_GRADE_AVERAGE;
            tgt.sym_name = rslt_name;
            for (int y = 0; y < src.remsymptoms.size(); y++) {
                boolean fnd = false;
                for (int z = 0; z < tgt.remsymptoms.size(); z++) {
                if (tgt.remsymptoms.get(z).Remname.equals(src.remsymptoms.get(y).Remname)) {
                    fnd = true;
                    if (tgt.remsymptoms.get(z).RemGrade < (src.remsymptoms.get(y).RemGrade)) tgt.remsymptoms.get(z).RemGrade = src.remsymptoms.get(y).RemGrade;
                        break;
                 }
             }
             if (!fnd) {
                SelectedRemSymptom srs = new SelectedRemSymptom();
                srs.RemGrade = src.remsymptoms.get(y).RemGrade;
                srs.RemSC = src.remsymptoms.get(y).RemSC;
                srs.Remname = src.remsymptoms.get(y).Remname;
                tgt.remsymptoms.add(srs);
                tgt.desktop = src.desktop;
             }
         }
       }
       return tgt;
    }

    /** Finds the node with the symptom id in the SymptomTree
     * 
     * @param symptom_id
     * @return
     */
    public DefaultMutableTreeNode FindSymptomTreeSymptom (DefaultMutableTreeNode node, int symptom_id) {
        Symptom s = null;
        try{
            s = (Symptom)node.getUserObject();
        }
        catch (Exception e) {}
        if (s != null && s.id == symptom_id) return node;
        if (node.getChildCount() != 0) {
            for (int x = 0; x < node.getChildCount(); x++) {            
                DefaultMutableTreeNode child = (DefaultMutableTreeNode)node.getChildAt(x);
                s = (Symptom)child.getUserObject();
                if (s.id == symptom_id) return child;
                if (child.getChildCount() != 0) {
                    DefaultMutableTreeNode temp = FindSymptomTreeSymptom(child, symptom_id);
                    if (temp != null) return temp;
                }
            }
        }
        return null;
    }

    /** Returns the int[] list of selected desktops
     * 
     * @return
     */
    public int[] GetDesktops() {
        int[] result = new int[GetSelectedDesktopsCount()];
        int pos = 0;
        if (DesktopButton_1.isSelected()) {
            result[pos++] = 1;
        }
        if (DesktopButton_2.isSelected()) {
            result[pos++] = 2;
        }
        if (DesktopButton_3.isSelected()) {
            result[pos++] = 3;
        }
        if (DesktopButton_4.isSelected()) {
            result[pos++] = 4;
        }
        if (DesktopButton_5.isSelected()) {
            result[pos++] = 5;
        }
        return result;
    }

    /** Executes a console command
     * 
     * @param command
     * @param cd
     */
    public void ExecuteCommand(String command, ConsoleDialog cd) {
        if (command.equalsIgnoreCase(Command_Help)) {
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                   "help                  = displays this help\n"+
                   "----------------------------------------------------------------\n"+
                   "q                     = quit the console\n"+
                   "qq                    = quit OpenRep (saving changes) \n"+
                   "qqq                   = force quit OpenRep (no saving changes...)\n"+
                   "gc                    = garbage collector (frees-up memory)\n"+
                   "freemem               = displays the total and available memory\n"+                   
                   "----------------------------------------------------------------\n"+
                   "saverep               = save currently opened repertory\n"+
                   "saverep <file_name>   = save currently opened repertory to a new file\n"+
                   "showrep               = displays currently imported repertories\n"+
                   "openrep <number>      = opens the repertory with the specified number\n"+
                   "removerep <number>    = removes the repertory with the specified number\n"+                   
                   "currentrep            = displays the currently opened repertory\n"+                   
                   "createrep <file_name> \"author\" \"name\" \"shortcut\" = creates an empty repertory\n"+
                   "editrep \"author\" \"name\" \"shortcut\" \"max_grade_nr\" = edits the current repertory's properties\n"+
                   "importrep <file_name> = imports the repertory");
        }
        else
        if (command.equalsIgnoreCase(Command_QuitConsole)) {
            cd.setVisible(false);
        }
        else
        if (command.equalsIgnoreCase(Command_GarbageCollector)) {
            System.gc();
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "Done />");            
        } 
        else
        if (command.equalsIgnoreCase(Command_FreeMemory)) {
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "Total memory   : "+Runtime.getRuntime().totalMemory()+"\n"+
                    "Free memory    : "+Runtime.getRuntime().freeMemory()+"\n"+
                    "Maximum memory : "+Runtime.getRuntime().maxMemory()+" />");            
        }                
        else
        if (command.equalsIgnoreCase(Command_SaveRepertory)) {
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "Please wait while saving the repertory />");
            MainMenu_Listener.actionPerformed(new ActionEvent(this, 0, MainMenu_SaveRepertory));
        }        
        else
        if (command.equalsIgnoreCase(Command_CurrentRepertory)) {
            if (db.GetCurrentRepertoryIndex() != -1) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                        db.GetCurrentRepertory().GetAuthor() + ", "+ db.GetCurrentRepertory().GetName()+" />");
                
            }
        }                    
        else
        if (command.toLowerCase().startsWith(Command_ImportRepertory)) {
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "Please wait while importing the repertory />");
            String rslt = db.AddRepertory(command.substring(Command_ImportRepertory.length()).trim(), install_directory);
            if (rslt == null)
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "Repertory was imported />");            
            else
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "There were errors while importing the repertory"+rslt+" />");                        
        }                    
        else
        if (command.toLowerCase().startsWith(Command_CreateRepertory)) {
            String filename = command.substring(Command_CreateRepertory.length()).trim();
            String sc;
            String name;
            String author;
            int pos2 = filename.lastIndexOf("\"");
            int pos1 = filename.lastIndexOf("\"", pos2-1);
            if (pos2 == -1 || pos1 == -1) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "The name, author and shortcut are not correctly entered />");
                return;
            }
            sc = filename.substring(pos1+1, pos2).trim();
            System.out.println(sc);
            filename = filename.substring(0, pos1);
            pos2 = filename.lastIndexOf("\"");
            pos1 = filename.lastIndexOf("\"", pos2-1);
            if (pos2 == -1 || pos1 == -1) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "The name, author and shortcut are not correctly entered />");
                return;
            }
            name = filename.substring(pos1+1, pos2).trim();
            System.out.println(name);
            filename = filename.substring(0, pos1);
            pos2 = filename.lastIndexOf("\"");
            pos1 = filename.lastIndexOf("\"", pos2-1);
            if (pos2 == -1 || pos1 == -1) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "The name, author and shortcut are not correctly entered />");
                return;
            }
            author = filename.substring(pos1+1, pos2).trim();
            filename = filename.substring(0, pos1);   
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "Please wait while creating the repertory />");            
            Repertory rep = new Repertory(filename, false);
            rep.SetAuthor(author);
            rep.SetName(name);
            rep.SetShortCut(sc);
            ArrayList<String> temp = rep.SaveRepertoryToFile(filename);
            if (temp.size() == 0) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                "The repertory was successfully created />");
            }
            else {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                "There were errors while created the repertory. The repertory was not created />");
            }                                    
        }
        else
        if (command.toLowerCase().startsWith(Command_EditRepertory)) {
            String filename = command.substring(Command_EditRepertory.length()).trim();
            String sc;
            String name;
            String author;
            int max_grade = -1;
            int pos2 = filename.lastIndexOf("\"");
            int pos1 = filename.lastIndexOf("\"", pos2-1);
            if (pos2 == -1 || pos1 == -1) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "The maximum_grade is not correctly entered />");
                return;
            }
            sc = filename.substring(pos1+1, pos2).trim();
            try {
                max_grade = Integer.parseInt(sc);
                if (max_grade <= 0 || max_grade > 5) throw new Exception ("");
            } catch (Exception e) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "The maximum_grade is not a valid number />");
                return;                
            }
            System.out.println(sc);
            filename = filename.substring(0, pos1);            
            pos2 = filename.lastIndexOf("\"");
            pos1 = filename.lastIndexOf("\"", pos2-1);
            if (pos2 == -1 || pos1 == -1) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "The name, author and shortcut are not correctly entered />");
                return;
            }
            sc = filename.substring(pos1+1, pos2).trim();
            System.out.println(sc);
            filename = filename.substring(0, pos1);
            pos2 = filename.lastIndexOf("\"");
            pos1 = filename.lastIndexOf("\"", pos2-1);
            if (pos2 == -1 || pos1 == -1) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "The name, author and shortcut are not correctly entered />");
                return;
            }
            name = filename.substring(pos1+1, pos2).trim();
            System.out.println(name);
            filename = filename.substring(0, pos1);
            pos2 = filename.lastIndexOf("\"");
            pos1 = filename.lastIndexOf("\"", pos2-1);
            if (pos2 == -1 || pos1 == -1) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "The name, author and shortcut are not correctly entered />");
                return;
            }
            author = filename.substring(pos1+1, pos2).trim();
            filename = filename.substring(0, pos1);   
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "Please wait while creating the repertory />");            
            if (db.GetCurrentRepertory() == null) {
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                        "There is no repertory opened, please open a repertory first />");                            
            }
            db.GetCurrentRepertory().SetAuthor(author);
            db.GetCurrentRepertory().SetName(name);
            db.GetCurrentRepertory().SetShortCut(sc);
            db.GetCurrentRepertory().SetMaximumGrade((byte)max_grade);
                cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                "The repertory properties were changed />");
        }   
        else            
        if (command.toLowerCase().startsWith(Command_DisplayRepertories)) {
            String text = cd.ConsoleEdit.getText()+"\n";
            for (int x = 0; x < db.GetRepertoryCount(); x++) {
                text += x + " : "+db.GetRepertory(x).GetAuthor() + ", " + db.GetRepertory(x).GetName()+"\n";
            }
            cd.ConsoleEdit.setText(text);
        }                            
        else
        if (command.toLowerCase().startsWith(Command_SaveRepertoryAs)) {
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "Please wait while saving the repertory />");
            SaveCurrentRepertory(command.substring(Command_SaveRepertoryAs.length()).trim());
        }                
        else
        if (command.toLowerCase().startsWith(Command_OpenRepertory)) {
            int repnr = -1;
            try {
                repnr = Integer.parseInt(command.substring(Command_OpenRepertory.length()).trim());
            } catch (Exception e) {
                cd.ConsoleEdit.setText("The entered number of repertory is not a valid repertory number />");
                return;
            }
            if (repnr < 0 || repnr > db.GetRepertoryCount()-1 || db.GetRepertory(repnr) == null) {
                cd.ConsoleEdit.setText("The repertory with this number is not imported />");
                return;
            }
            try {
                OpenRepertoryThread ort = new OpenRepertoryThread();
                ort.new_index = repnr;
                ort.start();
            } catch (Exception e) {
                cd.ConsoleEdit.setText("There was an error while opening the repertory />");                
                return;
            }
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "Repertory was opened />");
        }                        
        else
        if (command.toLowerCase().startsWith(Command_RemoveRepertory)) {
            int repnr = -1;
            try {
                repnr = Integer.parseInt(command.substring(Command_OpenRepertory.length()).trim());
            } catch (Exception e) {
                cd.ConsoleEdit.setText("The entered number of repertory is not a valid repertory number />");
                return;
            }
            if (repnr < 0 || repnr > db.GetRepertoryCount()-1 || db.GetRepertory(repnr) == null) {
                cd.ConsoleEdit.setText("The repertory with this number is not imported />");
                return;
            }
            try {
                db.RemoveRepertory (repnr);
            } catch (Exception e) {
                cd.ConsoleEdit.setText("There was an error while removing the repertory />");                
                return;
            }
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                    "Repertory was removed />");
        }                        
        else
        if (command.equals(Command_Quit)) {
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                                   "Closing OpenRep />");
            MainMenu_Listener.actionPerformed(new ActionEvent(this, 0, MainMenu_Exit));
        }   
        else
        if (command.equals(Command_ForceQuit)) {
            cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+
                                   "Closing OpenRep />");            
            System.exit(0);
        }   
        else
        cd.ConsoleEdit.setText(cd.ConsoleEdit.getText()+"\n"+"Unrecognized command />");
    }

    // Finds the path in tree as specified by the array of names. The names array is a
    // sequence of names where names[0] is the root and names[i] is a child of names[i-1].
    // Comparison is done using String.equals(). Returns null if not found.
    public TreePath findByName(JTree tree, Object[] nodes) {
        try {
            threaded_symptom_tree_expand = false;
            String temp = "";
            for (int x = 0; x < nodes.length; x++) {
                if (nodes.length-1 != x) temp += nodes[x] + Utils.SYMPTOM_FRAGMENT_FULL_SEPARATOR;
                else
                temp += nodes[x];
            }
            TreeNode root = (TreeNode)tree.getModel().getRoot();
            return find2(tree, new TreePath(root), nodes, 0, true, temp);
        } finally {
            threaded_symptom_tree_expand = true;
        }
    }
    private TreePath find2(JTree tree, TreePath parent, Object[] nodes, int depth, boolean byName, String symname) {
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        Object o = node;

        // If by name, convert node to a string
        if (byName) {
            try {
                o = o.toString().substring(0, o.toString().lastIndexOf(Utils.SYMPTOM_COUNT_START));
            } catch (Exception e) {
                o = o.toString();
            }
        }

        // If equal, go down the branch
        if (o.equals(nodes[depth])) {
            tree.expandPath(parent);
            // If at end, return match
            if (depth == nodes.length-1) {
                return parent;
            }

            // Traverse children
            if (node.getChildCount() >= 0) {
                for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                    TreeNode n = (TreeNode)e.nextElement();
                    TreePath path = parent.pathByAddingChild(n);
                    try {
                        String path_x = path.toString().substring(1, path.toString().lastIndexOf(Utils.SYMPTOM_COUNT_START));
                        if (path_x.equals(symname)) {
                            System.out.println("FND");
                            return path;
                        }
                    } catch (Exception ex) {}
                    TreePath result = find2(tree, path, nodes, depth+1, byName, symname);
                    // Found a match
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        // No match at this branch
        return null;
    }

    /** Selects the symptom of the SymptomTree specified in the parameter
     * 
     * @param s
     */
    private void SelectSymptomTreeNode (String s) {
        System.out.println("SEL = "+s);
        String[] temps = s.split(", ");

        TreePath tp = findByName(SymptomTree, temps);
        SymptomTree.setSelectionPath(tp);
        SymptomTree.scrollPathToVisible(tp);
    }

    /** Deselects all Repertorization desktops
     * 
     */
    public void DeselectAllDesktops() {
        DesktopButton_1.setSelected(false);
        DesktopButton_2.setSelected(false);
        DesktopButton_3.setSelected(false);
        DesktopButton_4.setSelected(false);
        DesktopButton_5.setSelected(false);
    }
    
    /** Returns the number of selected desktops
     * 
     * @return
     */
    public int GetSelectedDesktopsCount() {
        int cnt = 0;
        if (DesktopButton_1.isSelected()) cnt++;
        if (DesktopButton_2.isSelected()) cnt++;
        if (DesktopButton_3.isSelected()) cnt++;
        if (DesktopButton_4.isSelected()) cnt++;
        if (DesktopButton_5.isSelected()) cnt++;        
        return cnt;
    }
    
    /** Main action listener that listens to actions of main menu
     * 
     */
        ActionListener MainMenu_Listener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                boolean ctrl_held = false;
                if (e.getModifiers() == 18) ctrl_held = true;
                if (e.getActionCommand().equals(MainMenu_RepertoryTree)) {
                    UpdateSymptomTree(null, null, false, (short)-1);
                }
                if (e.getActionCommand().equals(MainMenu_PreviousSearchResult)) {
                    try {
                        search_history.get(search_history_pos).selected_symptom = selected_sym;
                    } catch (Exception ex) {}
                    search_history_pos--;
                    if (search_history_pos < 0) {
                        search_history_pos = -1;
                        UpdateSymptomTree(null, null, false, (short)-1);
                        return;
                    }                    
                    SearchEdit.setText(search_history.get(search_history_pos).search_string);
                    UpdateSymptomTree(search_history.get(search_history_pos).search_string, null, false, (short)-1);
                    SelectSymptomTreeNode(search_history.get(search_history_pos).selected_symptom);
                }
                if (e.getActionCommand().equals(MainMenu_NextSearchResult)) {
                    try {
                        search_history.get(search_history_pos).selected_symptom = selected_sym;
                    } catch (Exception ex) {}
                    search_history_pos++;
                    if (search_history_pos > search_history.size() - 1) {
                        search_history_pos = search_history.size() - 1;
                        return;
                    }                    
                    if (search_history_pos > search_history.size() - 1) return;
                    SearchEdit.setText(search_history.get(search_history_pos).search_string);
                    UpdateSymptomTree(search_history.get(search_history_pos).search_string, null, false, (short)-1);
                    SelectSymptomTreeNode(search_history.get(search_history_pos).selected_symptom);
                }
                if (e.getActionCommand().equals(MainMenu_Console)) {
                    ConsoleDialog cd = new ConsoleDialog(null, false, this_prescriber_view);
                    cd.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                    cd.setVisible(true);
                }                
                if (e.getActionCommand().equals(MainMenu_NewRepertory)) {
                    RepertoryPropertiesDialog rpd = new RepertoryPropertiesDialog(null, true, db);
                    rpd.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
		            try {
                        rpd.NameEdit.setText(db.GetCurrentRepertory().GetName());
                        rpd.ShortcutEdit.setText(db.GetCurrentRepertory().GetShortCut());
                        rpd.AuthorEdit.setText(db.GetCurrentRepertory().GetAuthor());
                    } catch (Exception ex) {}
                    rpd.setVisible(true);
                    if (!rpd.ok_pressed) return;
                    JFileChooser fc = new JFileChooser();
                    fc.setFileFilter(RepertoryFileFilter);
                    int result = fc.showSaveDialog(mainPanel);
                    if (result == JFileChooser.CANCEL_OPTION) return;
                    if (new File (fc.getSelectedFile().getPath()).exists()) {
                         int rslt = JOptionPane.showConfirmDialog(mainPanel, "Diese Datei existiert bereits. Möchten Sie sie überschreiben?");
                         if (rslt != JOptionPane.OK_OPTION) return;
                    }
                    String temps = fc.getSelectedFile().getPath();
                    if (!Utils.ExtractFileExtension(temps).trim().equals("rd")) temps += ".rd";
                    Repertory rep = new Repertory(temps, false);
                    rep.SetAuthor(rpd.AuthorEdit.getText().trim());
                    rep.SetName(rpd.NameEdit.getText().trim());
                    rep.SetShortCut(rpd.ShortcutEdit.getText().trim());
                    ArrayList<String> temp = rep.SaveRepertoryToFile(temps);
                    if (temp.size() == 0) {
                        JOptionPane.showMessageDialog(null, "Das Repertorium wurde erfolgreich erstellt.");
                        db.AddRepertory(temps, install_directory);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Es kam zu einem Problem beim erstellen des Repertoriums. Das Repertorium wurde nicht erstellt.");
                    }                        
                }                
                if (e.getActionCommand().equals(MainMenu_RepertoryProperties)) {
                    RepertoryPropertiesDialog rpd = new RepertoryPropertiesDialog(null, true, db);
                    rpd.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                    rpd.NameEdit.setText(db.GetCurrentRepertory().GetName());
                    rpd.ShortcutEdit.setText(db.GetCurrentRepertory().GetShortCut());
                    rpd.AuthorEdit.setText(db.GetCurrentRepertory().GetAuthor());
                    rpd.setVisible(true);
                    if (!rpd.ok_pressed) return;
                    db.GetCurrentRepertory().SetName(rpd.NameEdit.getText().trim());
                    db.GetCurrentRepertory().SetAuthor(rpd.AuthorEdit.getText().trim());
                    db.GetCurrentRepertory().SetShortCut(rpd.ShortcutEdit.getText().trim());
                }                
                if (e.getActionCommand().equals(MainMenu_Desktop1)) {
                    if (GetSelectedDesktopsCount() == 0) DesktopButton_1.setSelected(true);
                    if (!ctrl_held) {
                        DeselectAllDesktops();
                        DesktopButton_1.setSelected(true);                        
                    }
                    UpdateRemedySymptomsTree();
                }
                if (e.getActionCommand().equals(MainMenu_Desktop2)) {
                    if (GetSelectedDesktopsCount() == 0) DesktopButton_2.setSelected(true);
                    if (!ctrl_held) {
                        DeselectAllDesktops();
                        DesktopButton_2.setSelected(true);                        
                    }                    
                    UpdateRemedySymptomsTree();                    
                }
                if (e.getActionCommand().equals(MainMenu_Desktop3)) {
                    if (GetSelectedDesktopsCount() == 0) DesktopButton_3.setSelected(true);
                    if (!ctrl_held) {
                        DeselectAllDesktops();
                        DesktopButton_3.setSelected(true);                        
                    }                    
                    UpdateRemedySymptomsTree();                    
                }
                if (e.getActionCommand().equals(MainMenu_Desktop4)) {
                    if (GetSelectedDesktopsCount() == 0) DesktopButton_4.setSelected(true);
                    if (!ctrl_held) {
                        DeselectAllDesktops();
                        DesktopButton_4.setSelected(true);
                    }                    
                    UpdateRemedySymptomsTree();                    
                }
                if (e.getActionCommand().equals(MainMenu_Desktop5)) {
                    if (GetSelectedDesktopsCount() == 0) DesktopButton_5.setSelected(true);
                    if (!ctrl_held) {
                        DeselectAllDesktops();
                        DesktopButton_5.setSelected(true);
                    }                    
                    UpdateRemedySymptomsTree();                    
                }                
                if (e.getActionCommand().equals(MainMenu_AddMerged)) {
                    if (SymptomTree.getSelectionCount() < 2) {
                        JOptionPane.showMessageDialog(PrescriberApp.getApplication().getMainFrame(), "Bitte wählen Sie mindestens 2 Symptome, um sie zusammenzuführen und der Repertorisation hinzuzufügen.");
                        return;
                    }
                    String rslt = JOptionPane.showInputDialog(PrescriberApp.getApplication().getMainFrame(), "Name des zusammengeführten Symptoms: ", ConvertTreePathToSymptomName(SymptomTree.getSelectionPath(), true));
                    if (rslt == null) return;
                    if (rslt.equals("")) {
                        JOptionPane.showMessageDialog(PrescriberApp.getApplication().getMainFrame(), "Bitte geben Sie einen gültigen Namen ein");
                        return;
                    }
                    SelectedSymptomItem ssi = MergeSelectedSymptoms(AddSelectedItemsList(), rslt) ;
                    SelectedSymptoms.add(ssi);
                    int[] desktops = GetDesktops();
                    for (int x = 0; x < desktops.length; x++) {
                        SelectedSymptomItem ss = ssi.DeepCopy();
                        ss.desktop = desktops[x];
                        SelectedSymptoms.add(ss);
                    }
                    UpdateRemedySymptomsTree();
                }
                if (e.getActionCommand().equals(MainMenu_MergeSymptoms)) {
                    if (RemSymptomsTree.getSelectionCount() < 2) {
                        JOptionPane.showMessageDialog(PrescriberApp.getApplication().getMainFrame(), "Bitte wählen Sie mindestens 2 Symptome aus der Repertorisation, um sie zusammenzuführen.");
                        return;
                    }
                    String rslt = JOptionPane.showInputDialog(PrescriberApp.getApplication().getMainFrame(), "Name des Zusammengeführten Symptoms: ", ConvertTreePathToSymptomName(RemSymptomsTree.getSelectionPath(), true));
                    if (rslt == null) return;
                    if (rslt.equals("")) {
                        JOptionPane.showMessageDialog(PrescriberApp.getApplication().getMainFrame(), "Bitte geben Sie einen gültigen Namen ein");
                        return;
                    }
                    ArrayList<SelectedSymptomItem> ssi = new ArrayList();
                    for (int x = 0; x < RemSymptomsTree.getSelectionCount(); x++) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode)RemSymptomsTree.getSelectionPaths()[x].getLastPathComponent();
                        ssi.add((SelectedSymptomItem)node.getUserObject());
                    }
                    int[] desktops = GetDesktops();
                    SelectedSymptomItem ss = MergeSelectedSymptoms(ssi, rslt);
                    for (int x = 0; x < desktops.length; x++) {                        
                        SelectedSymptomItem temp_ss = ss.DeepCopy();
                        temp_ss.desktop = desktops[x];
                        SelectedSymptoms.add(temp_ss);
                    }
                    Collections.sort(SelectedSymptoms);                    
                    DeleteSelectedSymptomsFromRepertorization();
                    UpdateRemedySymptomsTree();
                }
                if (e.getActionCommand().equals(MainMenu_PMS)) {
                    if (pms_dialog == null) pms_dialog = new PMSDialog(null, false, install_directory+patients_file, pms_directory, this_prescriber_view);
                    pms_dialog.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                    pms_dialog.setVisible(true);
                }
                if (e.getActionCommand().equals(MainMenu_Preferences)) {
                    PreferencesDialog pd = new PreferencesDialog (PrescriberApp.getApplication().getMainFrame(), true);                    
                    pd.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                    pd.UpdateRepertoryNames(db.GetRepertoryNames());
                    pd.UpdateAutomaticRepertorization(config.GetAutomaticRepertorization());
                    pd.UpdateAutomaticSaveRepertory(config.GetAutomaticSaving());
                    pd.UpdateSearchInAllRepertories(config.GetSearchInall());
                    pd.UpdateOpenPMS(config.GetPMSStartup());
                    pd.UpdateProSearch(config.GetProfessionalSearch());
                    pd.setVisible(true);
                    if (!pd.ok_pressed) return;
                    default_repertory_name = pd.GetDefaultName();
                    config.SetDefaultRepertoryName(pd.GetDefaultName());
                    config.SetAutomaticRepertorization(pd.GetAutomaticRepertorization());
                    config.SetAutomaticSaving(pd.GetAutomaticSave());
                    config.SetPMSStartup(pd.GetOpenPMS());
                    config.SetSearchInAll(pd.GetSearchInAll());
                    config.SetProfessionalSearch(pd.GetProSearch());
                    if (config.GetAutomaticSaving() == false) saverepertory_timer = null;
                }
                if (e.getActionCommand().equals(MainMenu_ReversedMM)) {
                    ReversedMMDialog rmd = new ReversedMMDialog(PrescriberApp.getApplication().getMainFrame(), true, db.GetCurrentRepertory(), db, this_prescriber_view);
                    rmd.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                    rmd.setVisible(true);
                }                
                if (e.getActionCommand().equals(MainMenu_SaveRepertoryAs)) {
                     JFileChooser fc = new JFileChooser();
                     fc.setFileFilter(RepertoryFileFilter);
                     int result = fc.showSaveDialog(mainPanel);
                     if (result == JFileChooser.CANCEL_OPTION) return;                     
                     String temps = Utils.ExtractFileExtension(fc.getSelectedFile().getPath());
                     if (!temps.equalsIgnoreCase("rd")) temps = fc.getSelectedFile().getPath() + ".rd";
                     else
                     temps = fc.getSelectedFile().getPath();
                     SaveCurrentRepertory(temps);
                }
                if (e.getActionCommand().equals(MainMenu_ExportRepertoryChanges)) {
                     JFileChooser fc = new JFileChooser();
                     fc.setFileFilter(RepertorizationFileFilter);
                     int result = fc.showSaveDialog(mainPanel);
                     if (result == JFileChooser.CANCEL_OPTION) return;
                     Logger.AddInitEntry(Logger.Operation_SaveHistoryFile, db.GetCurrentRepertory().GetHistoryFileName());
                     if (db.GetCurrentRepertory().SaveHistoryToFile(db.GetCurrentRepertory().GetHistoryFileName()) == false) Logger.AddFailureEntry(Logger.Operation_SaveHistoryFile, "");
                     else
                     Logger.AddSuccessEntry(Logger.Operation_SaveHistoryFile, "");
                     Logger.AddInitEntry(Logger.Operation_SaveHistoryFile, fc.getSelectedFile().getPath());
                     try {
                        ArrayList<String> data = Utils.ReadFile(db.GetCurrentRepertory().GetHistoryFileName());
                        data.add(0, Database.REPERTORY_FILE_REPERTORY_TAG_START + db.GetCurrentRepertory().GetName() + Database.REPERTORY_FILE_REPERTORY_TAG_END);
                        Utils.WriteFile(fc.getSelectedFile().getPath(), data, config.GetAutomaticSaving());
                        Logger.AddSuccessEntry(Logger.Operation_SaveHistoryFile, "");
                     } 
                     catch (Exception ex) {
                         Logger.AddFailureEntry(Logger.Operation_SaveHistoryFile, ex.getMessage());
                         JOptionPane.showMessageDialog(mainPanel, "Es kam zu einem Fehler beim Speichern der Daten. "+ex.getMessage());
                     }
                        
                }
                if (e.getActionCommand().equals(MainMenu_EditRemedyAdditions)) {
                    if (SymptomTree.getSelectionCount() != 1) {
                        JOptionPane.showMessageDialog(mainPanel, "Bitte wählen Sie ein Symptom um die Arzneiwertigkeiten zu bearbeiten.");
                        return;
                    }
                    EditRemedyAdditionsDialog nsd = new EditRemedyAdditionsDialog(PrescriberApp.getApplication().getMainFrame(), true, this_prescriber_view);
                    nsd.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                    nsd.current_repertory = db.GetCurrentRepertory();
                    nsd.mainFrame = PrescriberApp.getApplication().getMainFrame();
                    nsd.SymptomName = ConvertTreePathToSymptomName(SymptomTree.getSelectionPath(), true);
                    nsd.UpdateRemedySymptomsTree();
                    nsd.SymptomNameLabel.setText(nsd.SymptomName);
                    nsd.setVisible(true);
                    if (nsd.changes && config.GetAutomaticSaving()) SaveCurrentRepertory(null);
                    DefaultMutableTreeNode root_node = (DefaultMutableTreeNode)SymptomTree.getModel().getRoot();                    
                    DefaultMutableTreeNode new_node = FindSymptomTreeSymptom(root_node, db.GetCurrentRepertory().GetSymptomIndex(nsd.SymptomName));
                    ((DefaultTreeModel)SymptomTree.getModel()).nodeChanged(new_node);
                    //UpdateSymptomTree(SearchEdit.getText(), null);
                }
                if (e.getActionCommand().equals(MainMenu_EditSymptom)) {
                    String temp_old = ConvertTreePathToSymptomName(SymptomTree.getSelectionPath(), true);                    
                    if (SymptomTree.getSelectionCount() != 1) {
                        JOptionPane.showMessageDialog(mainPanel, "Bitte wählen Sie ein Symptom aus, um den Namen zu bearbeiten");
                        return;
                    }
                    String temp = null;
                    while (true) {
                        AddNewSymptomDialog nsd = new AddNewSymptomDialog(PrescriberApp.getApplication().getMainFrame(), true);
                        nsd.SymptomName = temp_old;
                        nsd.SymptomEdit.setText(temp_old);
                        nsd.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                        nsd.setVisible(true);
                        if (nsd.SymptomName == null) return;
                        temp = nsd.SymptomName;
                        if (temp == null) return;
                        temp = Repertory.ReturnCorrectedSymptom(temp);
                        if (temp == null) {
                            JOptionPane.showMessageDialog(mainPanel, "Der Symptomname enthält keine Zeichen. Bitte wählen Sie einen anderen Namen.");
                        }
                        else
                        if (db.GetCurrentRepertory().SymptomExists(temp)) {
                            JOptionPane.showMessageDialog(mainPanel, "Ein Symptom mit diesem Namen existiert bereits. Bitte wählen Sie einen anderen Namen");
                        }
                        else
                        break;
                    }
                    mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));                    
                    statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Bitte warten während das Symptom umbenannt wird...");
                    mainPanel.invalidate();
                    statusMessageLabel.invalidate();
                    db.GetCurrentRepertory().RenameSymptom(temp_old, temp, true);
                    int index = db.GetCurrentRepertory().GetSymptomIndex(Repertory.ReturnCorrectedSymptom(temp));
                    DefaultMutableTreeNode root_node = (DefaultMutableTreeNode)SymptomTree.getModel().getRoot();
                    DefaultMutableTreeNode new_node = FindSymptomTreeSymptom(root_node, index);
                    if (new_node != null) ((DefaultTreeModel)SymptomTree.getModel()).nodeChanged(new_node);
                    //db.GetCurrentRepertory().GenerateTreeStructure(db.GetCurrentRepertory().GetSymptomIndex(temp));
                    //UpdateSymptomTree(null, null);
                    SymptomTree.repaint();
                    mainPanel.setCursor(Cursor.getDefaultCursor());
                    statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Symptom erfolgreich umbenannt...");
                    if (config.GetAutomaticSaving()) SaveCurrentRepertory(null);
                }
                if (e.getActionCommand().equals(MainMenu_DeleteSymptom)) {
                    int result = JOptionPane.showConfirmDialog(mainPanel, "Möchten Sie das gewählte Symptom wirklich löschen?");
                    if (result != JOptionPane.OK_OPTION) return;
                    mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Bitte warten Sie wähhrend das Symptom entfernt wird...");
                    mainPanel.invalidate();
                    statusMessageLabel.invalidate();
                    for (int x = 0; x < SymptomTree.getSelectionCount(); x++) {
                        String temp = ConvertTreePathToSymptomName(SymptomTree.getSelectionPaths()[x], true);                        
                        db.GetCurrentRepertory().DeleteSymptom(temp);                        
                    }
                    //db.GetCurrentRepertory().GenerateTreeStructure();
                    UpdateSymptomTree(null, null, false, (short)-1);
                    statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Das Symptom wurde erfolgreich gelöscht...");
                    mainPanel.setCursor(Cursor.getDefaultCursor());
                    if (config.GetAutomaticSaving()) SaveCurrentRepertory(null);
                }
                if (e.getActionCommand().equals(MainMenu_SaveRepertory)) {
                    SaveCurrentRepertory(null);
                }
                if (e.getActionCommand().equals(MainMenu_AddNewSymptom)) {
/*                    String temp = null;
                    while (true) {                        
                        AddNewSymptomDialog nsd = new AddNewSymptomDialog(PrescriberApp.getApplication().getMainFrame(), true);
                        nsd.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                        if (SymptomTree.getSelectionCount() != 0) {                            
                            nsd.SymptomEdit.setText(ConvertTreePathToSymptomName(SymptomTree.getSelectionPath(), true));
                        }                        
                        nsd.setVisible(true);
                        temp = nsd.SymptomName;
                        if (temp == null) return;
                        temp = Repertory.ReturnCorrectedSymptom(temp);
                        if (temp == null) {
                            JOptionPane.showMessageDialog(mainPanel, "The symptom name that you have specified does not contain any character. Please specify a different symptom name");
                        }
                        else
                        if (db.GetCurrentRepertory().SymptomExists(temp)) {
                            JOptionPane.showMessageDialog(mainPanel, "The symptom with this name already exists. Please specify a different symptom name");
                        }
                        else
                        break;
                    }
                    mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));                    
                    statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Please wait while adding the symptom to repertory...");
                    mainPanel.invalidate();
                    statusMessageLabel.invalidate();
                    db.GetCurrentRepertory().AddSymptom(temp);
                    if (db.GetCurrentRepertory().GetSymptoms().size() != 1) {
                        DefaultMutableTreeNode root_node = (DefaultMutableTreeNode)SymptomTree.getModel().getRoot();
                        DefaultMutableTreeNode parent_node = FindSymptomTreeSymptom(root_node, db.GetCurrentRepertory().GetSymptom(db.GetCurrentRepertory().GetSymptomIndex(Repertory.ReturnCorrectedSymptom(temp))).parent_id);
                        DefaultMutableTreeNode new_node = new DefaultMutableTreeNode(db.GetCurrentRepertory().GetSymptom(db.GetCurrentRepertory().GetSymptomIndex(Repertory.ReturnCorrectedSymptom(temp))));
                        if (parent_node == null) ((DefaultTreeModel)SymptomTree.getModel()).insertNodeInto(new_node, root_node, 0);
                        else
                        ((DefaultTreeModel)SymptomTree.getModel()).insertNodeInto(new_node, parent_node, 0);
                    } else
                    {
                        UpdateSymptomTree(null, null, false, (short)db.GetCurrentRepertoryIndex());
                    }
                    mainPanel.setCursor(Cursor.getDefaultCursor());
                    statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Done adding a symptom...");
                    if (config.GetAutomaticSaving()) SaveCurrentRepertory(null);*/
                    String temp = null;
                    while (true) {
                        AddNewSymptomDialog nsd = new AddNewSymptomDialog(PrescriberApp.getApplication().getMainFrame(), true);
                        nsd.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                        if (SymptomTree.getSelectionCount() != 0) {
                            nsd.SymptomEdit.setText(ConvertTreePathToSymptomName(SymptomTree.getSelectionPath(), true));
                        }
                        nsd.setVisible(true);
                        temp = nsd.SymptomName;
                        if (temp == null) return;
                        temp = Repertory.ReturnCorrectedSymptom(temp);
                        if (temp == null) {
                            JOptionPane.showMessageDialog(mainPanel, "Der gewählte Symptomname enthält keine Zeichen. Bitte geben Sie einen anderen Namen an.");
                        }
                        else
                        if (db.GetCurrentRepertory().SymptomExists(temp)) {
                            JOptionPane.showMessageDialog(mainPanel, "Ein Symptom mit dem gewünschtem Namen existiert bereits. Bitte geben Sie einen anderen Namen an.");
                        }
                        else
                        break;
                    }
                    mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    try {
                        statusMessageLabel.setText("Bitte warten Sie während das Symptom hinzugefügt wird...");
                        mainPanel.invalidate();
                        statusMessageLabel.invalidate();
                        if (!db.GetCurrentRepertory().AddSymptom(temp)) {
                            JOptionPane.showMessageDialog(null, "Der Symptomname ist nicht gut gewählt. Ein Symptom mit diesem Namen kann nicht angelegt werden.");
                            return;
                        }
                        //db.GetCurrentRepertory().GenerateTreeStructure(db.GetCurrentRepertory().GetSymptomIndex(temp));
                        /*if (SearchEdit.getText().equals("")) UpdateSymptomTree(null, null);
                        else
                        UpdateSymptomTree(SearchEdit.getText(), null);*/
                        DefaultMutableTreeNode root_node = (DefaultMutableTreeNode)SymptomTree.getModel().getRoot();
//                        if (db.GetCurrentRepertory().GetSymptom(db.GetCurrentRepertory().GetSymptomIndex(Repertory.ReturnCorrectedSymptom(temp))).parent_id == -1) {
                          UpdateSymptomTree(null, null, false, (short)-1);
                          SelectSymptomTreeNode(temp);
/*                        }
                        else {
                            DefaultMutableTreeNode parent_node = FindSymptomTreeSymptom(root_node, db.GetCurrentRepertory().GetSymptom(db.GetCurrentRepertory().GetSymptomIndex(Repertory.ReturnCorrectedSymptom(temp))).parent_id);
                            DefaultMutableTreeNode new_node = new DefaultMutableTreeNode(db.GetCurrentRepertory().GetSymptom(db.GetCurrentRepertory().GetSymptomIndex(Repertory.ReturnCorrectedSymptom(temp))));
                            if (parent_node == null) ((DefaultTreeModel)SymptomTree.getModel()).insertNodeInto(new_node, root_node, 0);
                            else
                            ((DefaultTreeModel)SymptomTree.getModel()).insertNodeInto(new_node, parent_node, 0);

                            mainPanel.setCursor(Cursor.getDefaultCursor());
                            statusMessageLabel.setText("Done adding a symptom...");
                            if (config.GetAutomaticSaving()) SaveCurrentRepertory(null);
                        }*/
                    }
                    finally {
                       mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }                
                if (e.getActionCommand().equals(MainMenu_RepertorizationResults)) {
                    RepertorizationResults rr = new RepertorizationResults();
                    GenerateRemSymptomDialogTable rst = new GenerateRemSymptomDialogTable(rr.ResultTable);
                    rr.grades = rst.grades;
                    rr.firstcol = rst.firstcol;
                    rr.firstrow = rst.firstrow;
                    rr.UpdateRepertorization(mainPanel.getWidth(), 200, PrescriberApp.getApplication().getMainFrame(), db.GetCurrentRepertory());
                }
                if (e.getActionCommand().equals(MainMenu_Repertorize)) {
                    RepertorizeCase();
                }
                if (e.getActionCommand().equals(MainMenu_Exit)) {
                      int result = JOptionPane.showConfirmDialog(mainPanel, "Möchten Sie wirklich beenden?");
                      if (result == JOptionPane.OK_OPTION) System.exit(0);
                }
                else
                if (e.getActionCommand().equals(MainMenu_New)) {
                    if (SelectedSymptoms.size() != 0) {
                        int result = JOptionPane.showConfirmDialog(mainPanel, "Durch das Erstellen einer neuen Sitzung verlieren Sie alle ungespeicherten Daten. Möchten Sie wirklich fortfahren?");
                        if (result == JOptionPane.OK_OPTION) {
                            StartNewSession();
                        }
                    }
                }
                else
                if (e.getActionCommand().equals(MainMenu_Save)) {
                    SaveCurrentRepertorization();
                }
                else
                if (e.getActionCommand().equals(MainMenu_SaveAs)) {
                     JFileChooser fc = new JFileChooser();
                     fc.setFileFilter(RepertorizationFileFilter);
                     int result = fc.showSaveDialog(mainPanel);
                     if (result == JFileChooser.CANCEL_OPTION) return;
                     current_file_name = fc.getSelectedFile().getPath();
                     if (current_file_name == null) return;
                     Utils u = new Utils();
                     String rslt = u.SaveRepertorizationToFile(SelectedSymptoms, current_file_name);
                     if (rslt != null) {
                         JOptionPane.showMessageDialog(mainPanel, rslt);
                     }
                }
                else
                if (e.getActionCommand().equals(MainMenu_Open)) {
                     JFileChooser fc = new JFileChooser();
                     fc.setFileFilter(RepertorizationFileFilter);
                     int result = fc.showOpenDialog(mainPanel);
                     if (result == JFileChooser.CANCEL_OPTION) return;
                     current_file_name = fc.getSelectedFile().getPath();
                     if (current_file_name == null) return;
                     Utils u = new Utils();                     
                     ArrayList<SelectedSymptomItem> ssi = new ArrayList();
                     statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Bitte warten Sie während die Repertorisations-Dateien geladen werden...");
                     String rslt = u.LoadRepertorizationFile(current_file_name, ssi);
                     if (rslt == null) SelectedSymptoms = ssi;
                     else
                     {
                         JOptionPane.showMessageDialog(mainPanel, rslt);
                         return;
                     }
                     UpdateRemedySymptomsTree();
                     // try to find the repertory with the same name as is saved in the SelectedSymptomItem and assign the
                     // symptom ids with correct ids (if == -1) => if loaded from a file
                     for (int x = 0; x < SelectedSymptoms.size(); x++) {
                         SelectedSymptoms.get(x).repertory_id = (short) db.GetRepertoryIndex(SelectedSymptoms.get(x).repertory_name);
                         if (SelectedSymptoms.get(x).repertory_id != -1)
                         SelectedSymptoms.get(x).sym_id = db.GetRepertory(SelectedSymptoms.get(x).repertory_id).GetSymptomIndex(SelectedSymptoms.get(x).sym_name);
                     }
                     GenerateRemSymptomTableThread grst = new GenerateRemSymptomTableThread();
                     grst.run();
                     statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Repertorisations-Dateien wurde erfolgreich geöffnet...");
                }
                else
                if (e.getActionCommand().equals(MainMenu_ImportRepertory)) {
                    JFileChooser fc = new JFileChooser();
                    fc.setDialogType(JFileChooser.OPEN_DIALOG);
                    fc.setFileFilter(RepertoryFileFilter);
                    int result = fc.showSaveDialog(mainPanel);
                    if (result == JFileChooser.CANCEL_OPTION) return;
                    String rslt = db.AddRepertory(fc.getSelectedFile().getPath(), install_directory);
                    if (rslt != null) {
                        JOptionPane.showMessageDialog(mainPanel, rslt);
                    }
                    else {
                        boolean open = false;
                        if (db.GetRepertoryCount() == 1) open = true;
                        else
                        {
                            int dlg_rslt = JOptionPane.showConfirmDialog(mainPanel, "Das Repertorium wurde erfolgreich importiert. Möchten Sie es öffnen?");
                            if (dlg_rslt == JOptionPane.YES_OPTION || dlg_rslt == JOptionPane.OK_OPTION) open = true;
                        }
                        if (open) {
                            OpenRepertoryThread ort = new OpenRepertoryThread();
                            ort.new_index = db.GetRepertoryCount()-1;
                            ort.start();
                        }
                    }
                    
                }
                else
                if (e.getActionCommand().equals(MainMenu_RemoveRepertory)) {
                    JFrame mainFrame = PrescriberApp.getApplication().getMainFrame();

                    RepertoryListDialog rld = new RepertoryListDialog(mainFrame, true);
                    rld.setLocationRelativeTo(mainFrame);
                    rld.RepertoryComboBox.removeAllItems();
                    ArrayList<String> repertory_names = db.GetRepertoryNames();
                    for (int x = 0; x < repertory_names.size(); x++) {
                        rld.RepertoryComboBox.addItem(repertory_names.get(x));
                    }                    
                    rld.setVisible(true);
                    if (!rld.ok_pressed) return;
                    if (rld.RepertoryComboBox.getSelectedIndex() != -1) {
                        db.RemoveRepertory(rld.RepertoryComboBox.getSelectedIndex());
                    }
                }
                else
                if (e.getActionCommand().equals(MainMenu_OpenRepertory)) {
                    if (db.GetCurrentRepertory() != null && db.GetCurrentRepertory().GetRepertoryChanged()) {
                         int rtrn = JOptionPane.showConfirmDialog(null, "Das Repertorium wurde geändert. Möchten Sie die Änderungen speichern?");
                         if (rtrn == JOptionPane.CANCEL_OPTION) return;
                         if (rtrn == JOptionPane.OK_OPTION || rtrn == JOptionPane.YES_OPTION) {
                            if (!SaveCurrentRepertory(null)) return;
                         }
                         saverepertory_timer = null;
                    }
                    JFrame mainFrame = PrescriberApp.getApplication().getMainFrame();

                    RepertoryListDialog rld = new RepertoryListDialog(mainFrame, true);
                    rld.setLocationRelativeTo(mainFrame);
                    rld.RepertoryComboBox.removeAllItems();
                    rld.jLabel1.setText("Open repertory");
                    ArrayList<String> repertory_names = db.GetRepertoryNames();
                    for (int x = 0; x < repertory_names.size(); x++) {
                        rld.RepertoryComboBox.addItem(repertory_names.get(x));
                    }                    
                    rld.setVisible(true);
                    if (!rld.ok_pressed) return;
                    OpenRepertoryThread ort = new OpenRepertoryThread();
                    ort.new_index = rld.RepertoryComboBox.getSelectedIndex();
                    ort.start();
                }
                // start the timer, because the repertory was changed
                if (config.GetAutomaticSaving() && db.GetCurrentRepertory().GetRepertoryChanged() && saverepertory_timer == null) StartTimer();
            }
        };        
        
        /** Resurns the SelectedSymptom from the symptom that is suitable for the ListDialog (Display Remedy additions)
         * 
         * @param xs
         * @return
         */
        public ArrayList<SelectedSymptomItem> GetSelectedSymptomsForListDialog (Symptom xs) {
               ArrayList<SelectedRemSymptom> remedies;
                        
               SelectedSymptomItem ssi = new SelectedSymptomItem();
                    
               int max_grade = 1;                    
               if (db.GetCurrentRepertoryIndex() != xs.RepertoryID) {
                   Repertory rep = db.GetRepertory(xs.RepertoryID);
                   rep.ReadRemedies();
                   remedies = rep.GetRemedySymptom(rep.GetSymptomIndex(xs.SymName));
                   max_grade = rep.GetMaximumGrade();
                   rep.CloseRepertory();
               }
               else {
                    remedies = db.GetCurrentRepertory().GetRemedySymptom(xs.SymName);
                    max_grade = db.GetCurrentRepertory().GetMaximumGrade();
               }
               ssi.maximum_grade = (byte) max_grade;
               ssi.repertory_id = xs.RepertoryID;
               ssi.sym_name = xs.SymName;
                        
               ArrayList<String> remsyms = new ArrayList();
               for (int y = 0; y < remedies.size(); y++) {
                    remsyms.add(Utils.REMSYMPTOM_GRADE_START+remedies.get(y).RemGrade+Utils.REMSYMPTOM_GRADE_END+remedies.get(y).RemSC);
               }
               remsyms = Repertory.SortRemSymptomArrayList(remsyms);

               for (int y = 0; y < remsyms.size(); y++) {
                    for (int z = 0; z < remedies.size(); z++) {
                        String temps = Utils.REMSYMPTOM_GRADE_START+remedies.get(z).RemGrade+Utils.REMSYMPTOM_GRADE_END+remedies.get(z).RemSC;
                        if (temps.equals(remsyms.get(y))) {
                            ssi.remsymptoms.add(remedies.get(z));
                            remedies.remove(z);
                            break;
                        }                                
                    }
               }
                    
               ArrayList<SelectedSymptomItem> result = new ArrayList(); 
               result.add(ssi); 
               
               return result;
        }
        
        /** Starts the timer that automatically saves repertory to the disk
         * 
         */
        public void StartTimer() {
             saverepertory_timer = new Timer(saveRepertoryInterval, timer_listener);
        }

        /** The action listener listening to the actions of the PopupMenu
         * 
         */
        ActionListener PopupMenu_RemSymptomTree_Listener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PopupMenu_RemSymptomTree.setVisible(false);
                if (e.getActionCommand().startsWith("@")) {
                    int symid = -1;
                    try {
                        symid = Integer.parseInt(e.getActionCommand().substring(1));
                    } catch (Exception ex) {}
                    if (symid == -1) return;
                    UpdateSymptomTree("\""+db.GetCurrentRepertory().GetSymptomName(symid)+"\"", null, true, (short)db.GetCurrentRepertoryIndex());
                }
                if (e.getActionCommand().equals(MainMenu_PositiveFilter)) {
                    for (int x = 0; x < RemSymptomsTree.getSelectionCount(); x++) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode)RemSymptomsTree.getSelectionPaths()[x].getLastPathComponent();
                        SelectedSymptomItem ssi = (SelectedSymptomItem)node.getUserObject();
                        int[] desktops = GetDesktops();
                        for (int y = 0; y < desktops.length; y++) {
                            for (int z = 0; z < SelectedSymptoms.size(); z++) {
                                if (SelectedSymptoms.get(z).desktop == desktops[y] && SelectedSymptoms.get(z).sym_name.equals(ssi.sym_name)) {
                                    if (SelectedSymptoms.get(z).positive_filter) SelectedSymptoms.get(z).positive_filter = false;
                                    else
                                    {
                                        SelectedSymptoms.get(z).negative_filter = false;
                                        SelectedSymptoms.get(z).positive_filter = true;
                                    }                                                            
                                }
                            }
                        }
                    }
                    UpdateRemedySymptomsTree();
                }
                if (e.getActionCommand().equals(MainMenu_NegativeFilter)) {
                    for (int x = 0; x < RemSymptomsTree.getSelectionCount(); x++) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode)RemSymptomsTree.getSelectionPaths()[x].getLastPathComponent();
                        SelectedSymptomItem ssi = (SelectedSymptomItem)node.getUserObject();
                        int[] desktops = GetDesktops();
                        for (int y = 0; y < desktops.length; y++) {
                            for (int z = 0; z < SelectedSymptoms.size(); z++) {
                                if (SelectedSymptoms.get(z).desktop == desktops[y] && SelectedSymptoms.get(z).sym_name.equals(ssi.sym_name)) {
                                    if (SelectedSymptoms.get(z).negative_filter) SelectedSymptoms.get(z).negative_filter = false;
                                    else
                                    {
                                        SelectedSymptoms.get(z).negative_filter = true;
                                        SelectedSymptoms.get(z).positive_filter = false;
                                    }                                                            
                                }
                            }
                        }
                    }
                    UpdateRemedySymptomsTree();
                }                
                if (e.getActionCommand().equals(AddSymptom)) {
                    PopupMenu_SymptomTree.setVisible(false);
                    AddSelectedItemsToRemSymptomTree(null, -1);
                }
                if (e.getActionCommand().equals(DisplayTopRubric) || e.getActionCommand().equals(DisplayHigherRubric)) {
                    try {
                        PrescriberApp.getApplication().getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        if (SymptomTree.getSelectionCount() == 0) return;                        
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode)SymptomTree.getSelectionPath().getLastPathComponent();
                        Symptom xs = (Symptom)node.getUserObject();
                        String temps = xs.SymName;
                        if (e.getActionCommand().equals(DisplayHigherRubric)) {
                            while (true) {
                                int pos = temps.lastIndexOf(Utils.SYMPTOM_FRAGMENT_FULL_SEPARATOR);
                                if (pos == -1) break;
                                temps = temps.substring(0, pos);
                                break;
                            }
                        }
                        UpdateSymptomTree("\""+temps+"\"", null, true, xs.RepertoryID);
                    } catch (Exception ex) {
                        
                    }
                    finally {
                        PrescriberApp.getApplication().getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
                if (e.getActionCommand().equals(DisplayRemedyAdditions)) {
                    try {
                        PrescriberApp.getApplication().getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        if (SymptomTree.getSelectionCount() == 0) return;
                        String symptom_name = ConvertTreePathToSymptomName(SymptomTree.getSelectionPath(), true);
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode)SymptomTree.getSelectionPath().getLastPathComponent();
                        Symptom xs = (Symptom)node.getUserObject();
                                            
                        ArrayList<SelectedSymptomItem> result = GetSelectedSymptomsForListDialog(xs);
                    
                        ListDialog ld = new ListDialog(PrescriberApp.getApplication().getMainFrame(), false, result.get(0), this_prescriber_view, result, ListDialog.Window_Type_Normal);
                        ld.setTitle("Remedies");
                        if (ListDialog_pos == null || List_Dialog_width == -1 || ListDialog_height == -1)
                        ld.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                        else
                        {
                            ld.setLocation(ListDialog_pos);
                            ld.setSize(List_Dialog_width, ListDialog_height);
                        }
                        ld.HeaderLabel.setText(symptom_name);
                        String temps = Utils.GetRTFSymptoms(result, false, -1, true)[0];
                        ld.SetContents(temps);
                        ld.setVisible(true);
                    }
                    finally {
                         PrescriberApp.getApplication().getMainFrame().setCursor(Cursor.getDefaultCursor());
                    }
                }
                if (e.getActionCommand().equals(DisplaySymptomReferences)) {
                    try {
                        PrescriberApp.getApplication().getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        PopupMenu_SymptomTree.setVisible(false);
                        if (SymptomTree.getSelectionCount() != 1) return;
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode)SymptomTree.getSelectionPath().getLastPathComponent();
                        Symptom xs = (Symptom)node.getUserObject();
                        if (xs.RepertoryID != db.GetCurrentRepertoryIndex()) return;
                    
                        int[] ref = db.GetCurrentRepertory().GetSymptomReferences(xs.id);
                        
                        if (ref == null) return;
                        
                        ArrayList<SelectedSymptomItem> result = new ArrayList();
                    
                        for (int x = 0; x < ref.length; x++) {
                            Symptom temps = db.GetCurrentRepertory().GetSymptom(ref[x]);
                            if (temps == null) continue;
                            temps.RepertoryID = (short) db.GetCurrentRepertoryIndex();
                            ArrayList<SelectedSymptomItem> temp = GetSelectedSymptomsForListDialog(temps);
                            result.add(temp.get(0));
                        }

                        ListDialog ld = new ListDialog(PrescriberApp.getApplication().getMainFrame(), false, result.get(0), this_prescriber_view, result, ListDialog.Window_Type_SymptomReferences);
                        ld.setTitle("Symptom References");
                        ld.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                        ld.HeaderLabel.setText(xs.SymName);
                        String temps = Utils.GetRTFSymptoms(result, true, -1, true)[0];
                        ld.SetContents(temps);
                        ld.setVisible(true);
                    }
                    finally {
                        PrescriberApp.getApplication().getMainFrame().setCursor(Cursor.getDefaultCursor());
                    }
                }
                if (e.getActionCommand().equals(String.valueOf(Database.SYMPTOM_GRADE_ESSENTIAL))) {
                    int[] desktops = GetDesktops();                    
                    for (int x = 0; x < RemSymptomsTree.getSelectionCount(); x++) {
                        for (int y = 0; y < SelectedSymptoms.size(); y++) {
                           for (int z = 0; z < desktops.length; z++) {
                                if (SelectedSymptoms.get(y).sym_name.equals(ConvertTreePathToSymptomName(RemSymptomsTree.getSelectionPaths()[x], true)) && desktops[z] == SelectedSymptoms.get(y).desktop) {
                                    SelectedSymptoms.get(y).symptom_grade = Database.SYMPTOM_GRADE_ESSENTIAL;
                                    break;
                                }
                           }
                        }
                    }
                    if (config.GetAutomaticRepertorization()) RepertorizeCase();
                }                
                if (e.getActionCommand().equals(String.valueOf(Database.SYMPTOM_GRADE_AVERAGE))) {
                    int[] desktops = GetDesktops();
                    for (int x = 0; x < RemSymptomsTree.getSelectionCount(); x++) {
                        for (int y = 0; y < SelectedSymptoms.size(); y++) {
                           for (int z = 0; z < desktops.length; z++) {
                                if (SelectedSymptoms.get(y).sym_name.equals(ConvertTreePathToSymptomName(RemSymptomsTree.getSelectionPaths()[x], true)) && SelectedSymptoms.get(y).desktop == desktops[z]) {
                                    SelectedSymptoms.get(y).symptom_grade = Database.SYMPTOM_GRADE_AVERAGE;
                                    break;
                                }
                           }
                        }
                    }
                    if (config.GetAutomaticRepertorization()) RepertorizeCase();
                }
                if (e.getActionCommand().equals(String.valueOf(Database.SYMPTOM_GRADE_IMPORTANT))) {
                    int[] desktops = GetDesktops();
                    for (int x = 0; x < RemSymptomsTree.getSelectionCount(); x++) {
                        for (int y = 0; y < SelectedSymptoms.size(); y++) {
                            for (int z = 0; z < desktops.length; z++) {
                                if (SelectedSymptoms.get(y).sym_name.equals(ConvertTreePathToSymptomName(RemSymptomsTree.getSelectionPaths()[x], true)) && desktops[z] == SelectedSymptoms.get(y).desktop) {
                                    SelectedSymptoms.get(y).symptom_grade = Database.SYMPTOM_GRADE_IMPORTANT;
                                    break;
                                }
                           }
                        }
                    }
                    if (config.GetAutomaticRepertorization()) RepertorizeCase();
                }
                if (e.getActionCommand().equals(String.valueOf(Database.SYMPTOM_GRADE_NON_IMPORTANT))) {
                    int[] desktops = GetDesktops();
                    for (int x = 0; x < RemSymptomsTree.getSelectionCount(); x++) {
                        for (int y = 0; y < SelectedSymptoms.size(); y++) {
                            for (int z = 0; z < desktops.length; z++) {
                                if (SelectedSymptoms.get(y).sym_name.equals(ConvertTreePathToSymptomName(RemSymptomsTree.getSelectionPaths()[x], true)) && desktops[z] == SelectedSymptoms.get(y).desktop) {
                                    SelectedSymptoms.get(y).symptom_grade = Database.SYMPTOM_GRADE_NON_IMPORTANT;
                                    break;
                                }
                           }
                        }
                    }
                    if (config.GetAutomaticRepertorization()) RepertorizeCase();
                }
                if (e.getActionCommand().equals(String.valueOf(Database.SYMPTOM_GRADE_VERY_IMPORTANT))) {
                    int[] desktops = GetDesktops();
                    for (int x = 0; x < RemSymptomsTree.getSelectionCount(); x++) {
                        for (int y = 0; y < SelectedSymptoms.size(); y++) {
                           for (int z = 0; z < desktops.length; z++) {
                                if (SelectedSymptoms.get(y).sym_name.equals(ConvertTreePathToSymptomName(RemSymptomsTree.getSelectionPaths()[x], true)) && desktops[z] == SelectedSymptoms.get(y).desktop) {
                                    SelectedSymptoms.get(y).symptom_grade = Database.SYMPTOM_GRADE_VERY_IMPORTANT;
                                    break;
                                }
                           }
                        }
                    }
                    if (config.GetAutomaticRepertorization()) RepertorizeCase();
                }                                
                if (e.getActionCommand().equals(DeleteSymptomFromSelection)) {
                    DeleteSelectedSymptomsFromRepertorization();
                }
            }
        };    
            
    /** This class is used to refresh the remsymptom dialog table
     * 
     */    
    class GenerateRemSymptomDialogTable {
        
        public ArrayList<Double> grades = new ArrayList();
        public String[] firstcol;
        public String[] firstrow;
        public GenerateRemSymptomDialogTable(JTable result_table) {
            ArrayList<RepertorisationResult> rep_res;            
            if (GetSelectedDesktopsCount() == 1) rep_res  = db.GenerateRepertorizationResults(SelectedSymptoms, GetDesktops()[0]);
            else
            rep_res = new ArrayList();
            rep_res = Utils.SortRepertorisationResults(rep_res, RemSymptomTable_sort_by_col, RemSymptomTable_sort_ascending);
            final String[] columnNames = new String[rep_res.size() + 1];
            columnNames[0] = "Symptomname";
            firstrow = new String[rep_res.size()];
            for (int x = 0; x < rep_res.size(); x++) {
                columnNames[x+1] = rep_res.get(x).RemSC;
                firstrow[x] = rep_res.get(x).RemSC;
            } 
            int pos = 0;            
            for (int x = 0; x < SelectedSymptoms.size(); x++) {
                if (SelectedSymptoms.get(x).desktop == GetDesktops()[0]) pos++;
            }
            String[][] data = new String[pos][columnNames.length];
            firstcol = new String[pos];
            pos = 0;
            for (int x = 0; x < SelectedSymptoms.size(); x++) {
                if (SelectedSymptoms.get(x).desktop != GetDesktops()[0]) continue;
                data[pos][0] = SelectedSymptoms.get(x).sym_name;
                firstcol[pos] = SelectedSymptoms.get(x).sym_name;
                for (int y = 0; y < rep_res.size(); y++) {
                    for (int z = 0; z < SelectedSymptoms.get(x).remsymptoms.size(); z++) {
                        if (SelectedSymptoms.get(x).remsymptoms.get(z).RemSC.equals(rep_res.get(y).RemSC)) {
                            data[pos][y+1] = String.valueOf(SelectedSymptoms.get(x).remsymptoms.get(z).RemGrade);
                            if (!grades.contains(SelectedSymptoms.get(x).remsymptoms.get(z).RemGrade)) {
                                grades.add(SelectedSymptoms.get(x).remsymptoms.get(z).RemGrade);
                            }
                            break;
                        }
                    }
                }
                pos++;
            }
            Collections.sort(grades);
            JTable temp_t = new JTable(data, columnNames);
                                
            result_table.setModel(temp_t.getModel());
            result_table.setBackground(Color.WHITE);
            result_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            result_table.getColumnModel().getColumn(0).setPreferredWidth(220);
        }
    }

    /** This class is used to read whether a new version of OpenRep if available online
     *
     */
    class UpgradeInfo extends Thread {

        String version_warned = null;

        public UpgradeInfo(String version_warning) {
            this.version_warned = version_warning;
        }

        @Override
        public void run() {
            ArrayList<String> ver_info = Utils.ReadURL(Utils.VersionURL);
            if (ver_info == null) return;
            try {
                String ver_warn = ver_info.get(0);
                if (ver_info.get(0).equals(version_warned)) return;
                int curr_ver = Utils.GetNumericDataFromString(ver_info.get(0));
                int this_ver = Utils.GetNumericDataFromString(version);
                if (curr_ver != this_ver) {
                    UpgradeDialog ud = new UpgradeDialog(null, true, ver_info.get(0), ver_info);
                    ud.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                    ud.setVisible(true);
                    config.SetVersionWarning(ver_warn);
                }
            } catch (Exception e) {
                
            }
        }
    }

    /** This class is used to initialize the Database class
     * 
     */
    class InitializeDatabaseThread extends Thread {
    
        public boolean isRunning = true;
        
        public boolean showSplashWindow = false;

        public InitializeDatabaseThread(boolean splash) {
            this.showSplashWindow = splash;
        }

        @Override
        public void run() {

            long time = System.currentTimeMillis();

            super.run();

            SplashScreen ss = new SplashScreen(null, false);
            if (this.showSplashWindow) {
                ss.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                ss.setVisible(true);
            }
            progressBar.setIndeterminate(true);
            statusMessageLabel.setText("Bitte warten Sie während das Repertorium geöffnet wird...");

            try{
                db = new Database (install_directory+"rep"+Utils.FILE_EXTENSION_SEPARATOR+"rdx", install_directory);
                int index = db.GetRepertoryIndex(config.GetDefaultRepertoryName());
                if (index == -1) index = 0;
                db.SetCurrentRepertory(index);
                db.OpenRepertory(db.GetCurrentRepertoryIndex());
                statusMessageLabel.setText(GetRepertoryStatusMessageLabel());
                UpdateSymptomTree(null, null, true, (short)-1);
                isRunning = false;
            }
            finally {
                statusMessageLabel.setText(GetRepertoryStatusMessageLabel());
                if (this.showSplashWindow) {
                    while (Math.abs(System.currentTimeMillis() - time) < 3000) {

                    }
                    ss.setVisible(false);
                }
                progressBar.setIndeterminate(false);
                UpgradeInfo ui = new UpgradeInfo(config.GetVersionWarning());
                ui.start();
                if (config.GetPMSStartup()) {
                    PMSDialog pms = new PMSDialog(null, false, install_directory+patients_file, pms_directory, this_prescriber_view);
                    pms.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
                    pms.setVisible(true);                    
                }
            }
        }
    }

    /** This class is used to Update the SymptomTree
     * 
     */
    class UpdateSymptomTreeThread extends Thread {

        public String key_words;
        
        public ArrayList<Integer> symptom_ids;
        
        @Override
        public void run() {
            super.run();
            UpdateSymptomTree(key_words, symptom_ids, true, (short)-1);
        }
    }
    
    /** This class is used to refresh the RemSymptomTable
     * 
     */
    class GenerateRemSymptomTableThread {

        public java.util.ArrayList<RepertorisationResult> rep_res = new ArrayList();
       
        public void run() {
            String[] columnNames = {"Rem",
                        "Cnt",
                        "Val"};
            if (GetSelectedDesktopsCount() == 1)
            rep_res  = db.GenerateRepertorizationResults(SelectedSymptoms, GetDesktops()[0]);
            top_results_symptom_cnt.clear();
            top_results_symptom_value.clear();
            rep_res = Utils.SortRepertorisationResults(rep_res, RemSymptomTable_sort_by_col, RemSymptomTable_sort_ascending);
            if (rep_res.size() == 0) {
                String[][] data = new String[rep_res.size()][3];
                JTable temp_t = new JTable(data, columnNames);
                MyModel model = new MyModel(data);
                try {
                    RemSymptomTable.setModel(model);
                } catch (Exception e) {}
                return;
            }
            int top_median = rep_res.get(0).value / 2;
            int median_increment = top_median / 5;
            for (int x = 0; x < 5; x++) {
                top_results_symptom_value.add(rep_res.get(0).value - (x+1)*median_increment);
            }
            for (int x = 0; x < rep_res.size(); x++) {
                if (top_results_symptom_cnt.size() < 5 && !top_results_symptom_cnt.contains(rep_res.get(x).cnt)) {
                    top_results_symptom_cnt.add((int)rep_res.get(x).cnt);
                }
                else
                if (top_results_symptom_cnt.size() == 5) {
                    int min_pos = 0;
                    int min_val = top_results_symptom_cnt.get(0);
                    for (int y = 0; y < top_results_symptom_cnt.size(); y++) {
                        if (top_results_symptom_cnt.get(y) < min_val) {
                            min_val = top_results_symptom_cnt.get(y);
                            min_pos = y;
                        }
                    }
                    if (min_val < rep_res.get(x).cnt && !top_results_symptom_cnt.contains(rep_res.get(x).cnt)) top_results_symptom_cnt.set(min_pos, (int)rep_res.get(x).cnt);
                }
            }
            Collections.sort(top_results_symptom_cnt);
            Collections.sort(top_results_symptom_value);
            String[][] data = new String[rep_res.size()][3];
            int max_col_1 = 0;
            int max_col_2 = 0;
            for (int y = 0; y < rep_res.size(); y++) {
                data[y][0] = rep_res.get(y).RemSC;                
                if (rep_res.get(y).cnt > max_col_1) max_col_1 = rep_res.get(y).cnt;
                if (rep_res.get(y).value > max_col_2) max_col_2 = rep_res.get(y).value;
            }
            for (int y = 0; y < rep_res.size(); y++) {
                data[y][0] = rep_res.get(y).RemSC;                
                if (rep_res.get(y).cnt <= 9 && max_col_1 >= 1000) data[y][1] = String.valueOf("00"+rep_res.get(y).cnt);
                else
                if (rep_res.get(y).cnt <= 9 && max_col_1 <= 100 && max_col_1 >= 10) data[y][1] = String.valueOf("0"+rep_res.get(y).cnt);                    
                else
                if (rep_res.get(y).cnt <= 9 && max_col_1 > 100) data[y][1] = String.valueOf("00"+rep_res.get(y).cnt);                    
                else
                if (rep_res.get(y).cnt <= 99 && max_col_1 < 100) data[y][1] = String.valueOf(rep_res.get(y).cnt);
                else
                if (rep_res.get(y).cnt <= 99 && max_col_1 >= 100) data[y][1] = String.valueOf("0"+rep_res.get(y).cnt);                    
                else
                if (max_col_1 < 10) data[y][1] = String.valueOf(rep_res.get(y).cnt);
                else
                data[y][1] = String.valueOf(rep_res.get(y).cnt);                    
                if (rep_res.get(y).value <= 9 && max_col_2 >= 1000) data[y][2] = String.valueOf("00"+rep_res.get(y).value);
                else
                if (rep_res.get(y).value <= 9 && max_col_2 <= 100 && max_col_2 >= 10) data[y][2] = String.valueOf("0"+rep_res.get(y).value);                    
                else
                if (rep_res.get(y).value <= 9 && max_col_2 > 100) data[y][2] = String.valueOf("00"+rep_res.get(y).value);
                else
                if (rep_res.get(y).value <= 99 && max_col_2 < 100) data[y][2] = String.valueOf(rep_res.get(y).value);
                else
                if (rep_res.get(y).value <= 99 && max_col_2 >= 100) data[y][2] = String.valueOf("0"+rep_res.get(y).value);
                else
                if (max_col_2 < 10) data[y][2] = String.valueOf(rep_res.get(y).value);
                else
                data[y][2] = String.valueOf(rep_res.get(y).value);
            }                        
            MyModel model = new MyModel(data);
            RemSymptomTable.setModel(model);
            RemSymptomTable.setBackground(Color.WHITE);
            RemSymptomTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            RemSymptomTable.getColumnModel().getColumn(0).setPreferredWidth(60);
            RemSymptomTable.getColumnModel().getColumn(1).setPreferredWidth(40);
            RemSymptomTable.getColumnModel().getColumn(2).setPreferredWidth(40);
            TableCellRenderer table_renderer = new RepertorizationTableCellRenderer();
            try{
               RemSymptomTable.setDefaultRenderer( Class.forName
               ( "java.lang.Integer" ), table_renderer );
            }catch (Exception ex) {}                    
            for (int i = 0; i < RemSymptomTable.getColumnCount(); i++) {
                TableColumn column = RemSymptomTable.getColumnModel().getColumn(i);
                column.setCellRenderer(table_renderer);
            }                                                      
    }
    }

    /** This class is used to display a dialog with a progress bar
     * 
     */
    class OpenRepertoryThread extends Thread {

        public int new_index = -1;
        
        @Override
        public void run() {
            super.run();
            ProgressDialogThread pdt = null;
            try {
                pdt = new ProgressDialogThread();
                pdt.text = "Bitte warten Sie während das Repertorium geöffnet wird...";
                pdt.start();
                db.SetCurrentRepertory(new_index);
                UpdateSymptomTree(null, null, false, (short)-1);
                pdt.Close();

            } finally {
                statusMessageLabel.setText(GetRepertoryStatusMessageLabel());
            }
        }        
    }
    
    
    /** This class is used to display a dialog with a progress bar
     * 
     */
    class ProgressDialogThread extends Thread {

        public String text = "";
        
        private boolean close = false;

        ProgressDialog pd;
        
        @Override
        public void run() {
            super.run();
            if (close) {
                return;
            }
            pd = new ProgressDialog(PrescriberApp.getApplication().getMainFrame(), false);
            if (close) {
                return;
            }
            pd.setLocationRelativeTo(PrescriberApp.getApplication().getMainFrame());
            if (close) {
                return;
            }
            pd.jLabel1.setText(text);
            if (close) {
                return;
            }
            pd.jProgressBar1.setIndeterminate(true);
            if (close) {
                return;
            }
            if (!close) pd.setVisible(true);
            if (close) {
                pd.setVisible(false);
                return;
            }
            while (!close) {
                pd.invalidate();
                pd.repaint();
            }
            pd.setVisible(false);
        }

        public void Close() {
            this.close = true;
            if (pd != null) pd.setVisible(false);            
        }

    }
    
    /** Expands the node of the SymptomsTree and lazily loads the structure
     * 
     * @param evt
     */
    private void ExpandSymptomTree (javax.swing.event.TreeExpansionEvent evt) {
            String temps = ConvertTreePathToSymptomName(evt.getPath(), true);
            DefaultMutableTreeNode di = (DefaultMutableTreeNode)evt.getPath().getLastPathComponent();
            statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+"Bitte warten Sie während die Arznei-Liste generiert wird...");
            boolean use_progress_bar = false;
            if (progressBar.getValue() == 0) use_progress_bar = true;
            if (use_progress_bar) {
                progressBar.setMaximum(di.getChildCount());
                progressBar.setMinimum(0);
                progressBar.setValue(0);
            }
            // add children to the children of the expanded node
            for (int x = 0; x < di.getChildCount(); x++) {
                if (use_progress_bar) {
                    progressBar.setValue(x);
                    //progressBar.paintImmediately(progressBar.getVisibleRect());
                }
                String symptom = CleanUpSymptom(temps + Utils.SYMPTOM_FRAGMENT_FULL_SEPARATOR+ di.getChildAt(x).toString());
                if (db.GetCurrentRepertory() == null) return;
                RecAddChildren(db.GetCurrentRepertory().GetSymptomIndex(symptom), (DefaultMutableTreeNode)di.getChildAt(x), false);
                SymptomTree.invalidate();
                SymptomTree.repaint();
            }
            if (use_progress_bar) {
                progressBar.setValue(0);
                statusMessageLabel.setText(GetRepertoryStatusMessageLabel());
            }
    }
    
    /** This class is used to fill the tree data after clicking the tree node
     * 
     */
    class WorkThread extends Thread {

        /** event passed by the SymptomTreeTreeWillExpand event */
        public javax.swing.event.TreeExpansionEvent evt;
        
        @Override
        public void run() {
            super.run();
            // convert the node to a symptom name
            ExpandSymptomTree(evt);
        }
        
    }

    /** Deletes all data - selected symptoms, repertorization, searchEdit and sets the current_file_name to null
     * 
     */
    public void StartNewSession() {
        SelectedSymptoms.clear();
        if (results != null) results.clear();
        generated_symptoms.clear();
        UpdateRemedySymptomsTree();
        JTable temp_table = new JTable();
        RemSymptomTable.setModel(temp_table.getModel());
        SearchEdit.setText("");
        current_file_name = null;
    }
            
    
    /** Removes from a symptom information about the number of remedy additions 
     * 
     * @param symptom
     * @return
     */
    public String CleanUpSymptom (String symptom) {
       int pos = 0;
       boolean delete_last_parenthesis = false;
       if (symptom.lastIndexOf(" (") > symptom.lastIndexOf(Utils.SYMPTOM_COUNT_END)) delete_last_parenthesis = true;
       while (pos != -1) {
           pos = symptom.lastIndexOf(Utils.SYMPTOM_COUNT_START);
           int pos1 = symptom.lastIndexOf(Utils.SYMPTOM_COUNT_END);
           if (pos != -1 && pos1 != -1) {
               symptom = symptom.substring(0, pos)+symptom.substring(pos1+1, symptom.length());
           }
       }
       if (delete_last_parenthesis) {
           pos = symptom.lastIndexOf(" (");
           int pos1 = symptom.lastIndexOf(")");
           if (pos == -1 || pos1 == -1 || (pos1 < pos)) return symptom;
           symptom = symptom.substring(0, pos);
       }
       return symptom;
    }
    
    /** Converts the TreePath (passed usually by JTree events) to a String
     * 
     * @param tp
     * @return
     */
    public static String ConvertTreePathToSymptomName (TreePath tp, boolean clean_up_symptom) {       
       DefaultMutableTreeNode node = (DefaultMutableTreeNode)tp.getLastPathComponent();
       try {
            Symptom xs = (Symptom)node.getUserObject();
            return xs.SymName;
       }
       catch (Exception e) {
           
       }
       try {
            SelectedSymptomItem xs = (SelectedSymptomItem)node.getUserObject();
            return xs.sym_name;
       }
       catch (Exception e) {      
       }       
       return null;
    }
    
    /** Recoursively adds the children to the Parent node
     * 
     * @param idx index of the symptom that is the parent symptom
     * @param parent node that is the parent node
     * @param stop if it is true, add only the first line of children to this parent (don't add children of the children)
     *             if it is false, add children to this parent and also first children of the parent's children
     */
    private void RecAddChildren (int idx, DefaultMutableTreeNode parent, boolean stop) {
        if (generated_symptoms.contains(idx)) return;
        else
        generated_symptoms.add(idx);
        if (db.GetCurrentRepertory() == null) return;
        ArrayList<Symptom> children = db.GetCurrentRepertory().GetSymptomChildren(idx);
        if (children.size() == 0) return;
        Collections.sort(children);
        for (int x = 0; x < children.size(); x++) {
            children.get(x).RepertoryID = (short) db.GetCurrentRepertoryIndex();
            DefaultMutableTreeNode tr = new DefaultMutableTreeNode(children.get(x));
            if (db.GetCurrentRepertory() == null) return;            
            if (!stop)
            RecAddChildren(db.GetCurrentRepertory().GetSymptomIndex(children.get(x).SymName), tr, true);
            parent.add(tr);
        }
    }

    /** Adds the selected symptoms to the SelectedSymptomItems list.
     * 
     * @return
     */
    public ArrayList<SelectedSymptomItem> AddSelectedItemsList () {
        ArrayList<SelectedSymptomItem> result = new ArrayList();
        TreePath[] tp = SymptomTree.getSelectionPaths();
        if (tp == null) return result;
        // add the selected symptoms to the SelectedSymptoms list
        for (int x = 0; x < tp.length; x++) {
            String temps = ConvertTreePathToSymptomName(tp[x], true);            
            boolean fnd = false;
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)tp[x].getLastPathComponent();
            Symptom xs = (Symptom)node.getUserObject();
            for (int y = 0; y < result.size(); y++) {
                if (result.get(y).repertory_id == xs.RepertoryID && result.get(y).sym_name.equals(temps)) {
                    fnd = true;
                    break;
                }
            }                    
            if (!fnd) {
                SelectedSymptomItem ss = new SelectedSymptomItem();
                ss.sym_name = temps;
                if (db.GetRepertory(xs.RepertoryID) == null) continue;
                if (db.GetCurrentRepertoryIndex() != xs.RepertoryID) {
                    Repertory rep = null;
                    try {
                        rep = db.GetRepertory(xs.RepertoryID);
                        rep.ReadRemedies();
                        ss.sym_id = rep.GetSymptomIndex(temps);
                        ss.repertory_name = db.GetRepertory(xs.RepertoryID).GetName();
                        ss.remsymptoms = db.GetRepertory(xs.RepertoryID).GetRemedySymptom(ss.sym_id);
                        if (ss.remsymptoms.size() == 0) {
                            rep.CloseRepertory();
                            continue;
                        }
                        ss.repertory_id = xs.RepertoryID; 
                        ss.maximum_grade = rep.GetMaximumGrade();
                        rep.CloseRepertory();
                    }
                    finally {
                        if (rep != null) rep.CloseRepertory();
                    }
                }
                else
                {
                    ss.sym_id = db.GetRepertory(xs.RepertoryID).GetSymptomIndex(temps);
                    ss.repertory_name = db.GetRepertory(xs.RepertoryID).GetName();
                    ss.remsymptoms = db.GetRepertory(xs.RepertoryID).GetRemedySymptom(temps);
                    if (ss.remsymptoms.size() == 0) continue;
                    ss.repertory_id = xs.RepertoryID;
                    ss.maximum_grade = db.GetRepertory(xs.RepertoryID).GetMaximumGrade();
                }
                int[] desktops = GetDesktops();
                for (int y = 0; y < desktops.length; y++) {
                    SelectedSymptomItem ssi = ss.DeepCopy();
                    ssi.desktop = desktops[y];
                    result.add(ssi);
                }                
            }
        }
        return result;
    }
    
    /** Adds the selected symptoms to the RemSymptom tree and updates the SelectedSymptoms list.
     *  Also checks whether the symptom is not already contained in the SelectedSymptoms list
     * 
     */
    public void AddSelectedItemsToRemSymptomTree(ArrayList<SelectedSymptomItem> items, int desktop) {
        // update the RemedySymptoms tree
        ArrayList<SelectedSymptomItem> temp = new ArrayList();
        if (items == null) temp = AddSelectedItemsList();
        else
        temp = items;
        int[] desktops;
        if (desktop == -1) desktops = GetDesktops();
        else
        {
            desktops = new int[1];
            desktops[0] = desktop;
        }
        for (int x = 0; x < temp.size(); x++) {
            for (int z = 0; z < desktops.length; z++) {
                boolean fnd = false;
                for (int y = 0; y < SelectedSymptoms.size(); y++) {
                        if (SelectedSymptoms.get(y).desktop == desktops[z] && SelectedSymptoms.get(y).sym_name.equalsIgnoreCase(temp.get(x).sym_name) && SelectedSymptoms.get(y).repertory_id == temp.get(x).repertory_id) {
                            fnd = true;
                            break;
                        }                                    
                    }
                 if (!fnd) {
                    SelectedSymptomItem ssi = temp.get(x).DeepCopy();
                    ssi.desktop = desktops[z];
                    SelectedSymptoms.add(ssi);
                 }
             }
        }
        UpdateRemedySymptomsTree();
    }

    /** Repertorizes the case
     * 
     */
    public void RepertorizeCase() {
         GenerateRemSymptomTableThread temp = new GenerateRemSymptomTableThread();
         results = temp.rep_res;
         temp.run();        
    }
    
    /** Updates the contents of the RemedySymptoms tree based on the SelectedSymptoms tree
     * 
     */
    public void UpdateRemedySymptomsTree () {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Results");
        Collections.sort(SelectedSymptoms);
        ArrayList<String> added_symptomnames = new ArrayList();
        int[] desktops = GetDesktops();
        //if (db.GetCurrentRepertory() == null) return;
        int selected = -1;
        for (int x = 0; x < SelectedSymptoms.size(); x++) {
            // add the symptom
            if (added_symptomnames.contains(SelectedSymptoms.get(x).sym_name)) continue;
            boolean fnd = true;
            for (int y = 0; y < desktops.length; y++) {
                boolean this_fnd = false;
                for (int z = y; z < SelectedSymptoms.size(); z++) {
                    if (SelectedSymptoms.get(z).sym_name.equals(SelectedSymptoms.get(x).sym_name) && SelectedSymptoms.get(z).desktop == desktops[y]) {
                        selected = z;
                        this_fnd = true;
                        break;
                    }
                }
                if (!this_fnd) {
                    fnd = false;
                    break;
                }
            }
            if (!fnd) continue;
            added_symptomnames.add(SelectedSymptoms.get(selected).sym_name);
            DefaultMutableTreeNode temp = new DefaultMutableTreeNode(SelectedSymptoms.get(selected)/*.sym_name+Utils.SYMPTOM_COUNT_START+SelectedSymptoms.get(x).remsymptoms.size()+Utils.SYMPTOM_COUNT_END*/);
            // get the remedy list - if there is 
            try {                   
                ArrayList<String> al = new ArrayList();
                for (int y = 0; y < SelectedSymptoms.get(x).remsymptoms.size(); y++) {
                    al.add(Utils.REMSYMPTOM_GRADE_START+(int)(SelectedSymptoms.get(selected).remsymptoms.get(y).RemGrade*SelectedSymptoms.get(selected).maximum_grade)+Utils.REMSYMPTOM_GRADE_END+SelectedSymptoms.get(selected).remsymptoms.get(y).RemSC);
                }
                ArrayList<String> resorted_al = Repertory.SortRemSymptomArrayList(al);                
             if (resorted_al.size() != 0) {
                // add remedies to the remedysymptoms tree
                for (int y = 0; y < resorted_al.size(); y++) {
                    DefaultMutableTreeNode tn = new DefaultMutableTreeNode(resorted_al.get(y));
                    temp.add(tn);
                }
                // add the node to the top node
                top.add(temp);
             }
            
            }
            catch (Exception e) {
                
            }
        }
        if (config.GetAutomaticRepertorization()) {
            RepertorizeCase();
        }        
        // update the model of the RemSymptoms tree
        JTree x = new JTree(top);
        RemSymptomsTree.setModel(x.getModel());
    }

    /** Copies the current symptoms to clipboard
     *  
     */
    public void CopySymptomsToClipboard(ArrayList<SelectedSymptomItem> temp) {
        if (temp.size() == 0) return;
        try {
            Utils.CopyToRtf(Utils.GetRTFSymptoms(temp, true, 1, true)[0], Utils.GetRTFSymptoms(temp, true, 1, true)[2]);
            JOptionPane.showMessageDialog(null, "Der Inhalt wurde in die Zwischenablage kopiert");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Beim kopieren des Inhalts in die Zwischenablage kam es zu einem Fehler");
        }        
    }

    public void CreateSymptomTreePopupMenu() {
            PopupMenu_SymptomTree.setVisible(false);
            PopupMenu_SymptomTree = new JPopupMenu();
            JMenuItem mi;
        
            mi = new JMenuItem("Zeige Arzneien (F1)");
            mi.setActionCommand(DisplayRemedyAdditions);
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            PopupMenu_SymptomTree.add(mi);            
            
            mi = new JMenuItem("Zeige Symptomreferenzen (F3)");
            mi.setActionCommand(DisplaySymptomReferences);
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            
            DefaultMutableTreeNode dtm = (DefaultMutableTreeNode)SymptomTree.getSelectionPath().getLastPathComponent();

            Symptom s = null;

            try {
                s = (Symptom)dtm.getUserObject();
                if (s.RepertoryID != db.GetCurrentRepertoryIndex() || db.GetCurrentRepertory().GetSymptomReferenceCnt(s.id) == 0) mi.setEnabled(false);
            } catch (Exception e) {}
            
            PopupMenu_SymptomTree.add(mi);
            
            mi = new JMenuItem("Symptom zur Repertorisation hinzufügen (F4)");
            mi.setActionCommand(AddSymptom);
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            PopupMenu_SymptomTree.add(mi);
        
            PopupMenu_SymptomTree.addSeparator();

            if (s != null && s.RepertoryID == db.GetCurrentRepertoryIndex()) {
                while (true) {
                    if (s.parent_id == -1) break;

                    mi = new JMenuItem("Display: \""+db.GetCurrentRepertory().GetSymptomName(s.parent_id)+"\"");
                    mi.setActionCommand("@"+s.parent_id);
                    mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
                    PopupMenu_SymptomTree.add(mi);

                    s = db.GetCurrentRepertory().GetSymptom(s.parent_id);
                }
            }

            /*mi = new JMenuItem("Display rubric (ALT+R)");
            mi.setActionCommand(DisplayTopRubric);
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            PopupMenu_SymptomTree.add(mi);*/

            /*mi = new JMenuItem("Display higher rubric (ALT+H)");
            mi.setActionCommand(DisplayHigherRubric);
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            PopupMenu_SymptomTree.add(mi);*/
    }

    /** Updates the number of symptoms in the status label by the number specified in the parameter
     * 
     */
    public void UpdateStatusLabel(int cnt) {
        SymptomCntLabel.setText(cnt+" Symptome");
    }

    /** Updates the Symptom tree based on the keywords (contains either the search phrase or null) if it's null
     *  generate the whole tree structure
     * 
     * @param key_words can be either null (generate the whole structure) or a search phrase from the SearchEdit
     */
    public void UpdateSymptomTree (String key_words, ArrayList<Integer> symptom_ids, boolean save_search_result, short repertory_index) {
        if (db.GetCurrentRepertory() == null) return;
        // displays the symptoms with ids from the symptom_ids ArrayList
        if (symptom_ids != null) {
            try{
                DefaultMutableTreeNode top = new DefaultMutableTreeNode("Results");
                int cnt = 0;
                for (int x = 0; x < symptom_ids.size(); x++) {
                    SearchSymptom s = db.GetCurrentRepertory().GetSymptoms().get(symptom_ids.get(x)).GetSearchSymptom();
                    if (s == null) continue;
                    DefaultMutableTreeNode tr = new DefaultMutableTreeNode(s);
                    top.add(tr);
                    cnt++;
                }
                UpdateStatusLabel(cnt);
                JTree x = new JTree(top);
                SymptomTree.setModel(x.getModel());
                if (save_search_result) {
                    try {
                        search_history.get(search_history_pos).selected_symptom = selected_sym;
                    } catch (Exception e) {}
                    search_history_pos++;
                    if (search_history_pos > search_history.size() - 1) {
                        SearchHistory sh = new SearchHistory();
                        sh.search_string = key_words;
                        sh.selected_symptom = null;
                        search_history.add(sh);
                    }
                    else
                    {
                        SearchHistory sh = new SearchHistory();
                        sh.search_string = key_words;
                        sh.selected_symptom = null;
                        search_history.set(search_history_pos, sh);
                        if (search_history_pos != search_history.size() - 1) {
                            while (search_history.size() - 1 != search_history_pos) {
                                search_history.remove(search_history.size() - 1);
                            }
                        }
                    }
                }
                selected_sym = null;
            } finally {
            }
            return;
        }
        // display the whole tree structure
        if (key_words == null || key_words.trim().equals("")) {
            try {
                DefaultMutableTreeNode top = new DefaultMutableTreeNode("Results");
                generated_symptoms.clear();
                {
                    RecAddChildren(-1, top, false);
                }
                UpdateStatusLabel(db.GetCurrentRepertory().GetSymptomCnt());
                JTree x = new JTree(top);
                SymptomTree.setModel(x.getModel());
                if (save_search_result) {
                    try {
                        search_history.get(search_history_pos).selected_symptom = selected_sym;
                    } catch (Exception e) {}
                    search_history_pos++;
                    if (search_history_pos > search_history.size() - 1) {
                        SearchHistory sh = new SearchHistory();
                        sh.search_string = key_words;
                        sh.selected_symptom = null;
                        search_history.add(sh);
                    }
                    else
                    {
                        SearchHistory sh = new SearchHistory();
                        sh.search_string = key_words;
                        sh.selected_symptom = null;
                        search_history.set(search_history_pos, sh);
                        if (search_history_pos != search_history.size() - 1) {
                            while (search_history.size() - 1 != search_history_pos) {
                                search_history.remove(search_history.size() - 1);
                            }
                        }
                    }
                }
                selected_sym = null;
            } finally {
            }
        }
        else
        // perform search and display the results
        {
            ProgressDialogThread pdt = new ProgressDialogThread();
            pdt.text = "Bitte wartem, es wird gesucht...";
            pdt.start();
            boolean multiple_repertories_results = false;
            if (!config.GetProfessionalSearch()) key_words = Repertory.PreProcessSearchString(key_words);
            try {
                String[] search_phrases = Utils.SplitSearchString(key_words, Utils.SPLIT_CHAR, false);
                ArrayList<SearchSymptom> search_results = new ArrayList();
                for (int x = 0; x < search_phrases.length; x++) {
                    if (search_phrases[x].trim().equals("")) continue;
                    if (!search_phrases[x].trim().startsWith(String.valueOf(Utils.NEGATIVE_CHAR))) {
                        ArrayList<SearchSymptom> temp_results = new ArrayList();
                        if ((!config.GetSearchInall() && (repertory_index == -1 || repertory_index == db.GetCurrentRepertoryIndex())) ||
                            (config.GetSearchInall() && repertory_index == db.GetCurrentRepertoryIndex())) {
                            temp_results = db.GetCurrentRepertory().SearchInSymptoms(search_phrases[x], null, db, db.GetCurrentRepertoryIndex());
                        }
                        else
                        {
                            for (int y = 0; y < db.GetRepertoryCount(); y++) {
                                if (repertory_index != -1 && y != repertory_index) continue;
                                ArrayList<SearchSymptom> x_results;
                                if (db.GetCurrentRepertoryIndex() != y) {
                                    System.out.println("Rep = "+db.GetRepertory(y).GetShortCut());
                                    db.GetRepertory(y).ReadSymptoms(false, false);
                                    x_results = db.GetRepertory(y).SearchInSymptoms(search_phrases[x], null, db, y);
                                    if (!multiple_repertories_results) multiple_repertories_results = (x_results.size() != 0);
                                    db.GetRepertory(y).CloseRepertory();
                                    System.out.println("Rep close...");
                                    System.gc();
                                }
                                else
                                {
                                    x_results = db.GetCurrentRepertory().SearchInSymptoms(search_phrases[x], null, db, y);
                                }
                                for (int z = 0; z < x_results.size(); z++) {
                                    x_results.get(z).RepertoryID = (short) y;
                                    temp_results.add(x_results.get(z));
                                }
                            }                        
                        }
                        for (int y = 0; y < temp_results.size(); y++) {
                            boolean fnd = false;
                            if (x > 0) {
                                for (int z = 0; z < search_results.size(); z++) {
                                    if (search_results.get(z).id == temp_results.get(y).id) {
                                        fnd = true;
                                        break;
                                    }
                                }
                            }
                            if (!fnd) search_results.add(temp_results.get(y));
                        }
                    }
                }
                for (int x = 0; x < search_phrases.length; x++) {
                    if (search_phrases[x].trim().equals("")) continue;
                    if (search_phrases[x].trim().startsWith(String.valueOf(Utils.NEGATIVE_CHAR))) {
                        search_phrases[x] = search_phrases[x].trim().substring(1);
                        ArrayList<SearchSymptom> temp_results = new ArrayList();
                        if (!config.GetSearchInall()) {
                            temp_results = db.GetCurrentRepertory().SearchInSymptoms(search_phrases[x], null, db, db.GetCurrentRepertoryIndex());
                        }
                        else
                        {
                            for (int y = 0; y < db.GetRepertoryCount(); y++) {
                                ArrayList<SearchSymptom> x_results;
                                if (db.GetCurrentRepertoryIndex() != y) {
                                    db.OpenRepertory(y);
                                    x_results = db.GetRepertory(y).SearchInSymptoms(search_phrases[x], null, db, y);
                                    db.GetRepertory(y).CloseRepertory();
                                }
                                else
                                {
                                    x_results = db.GetCurrentRepertory().SearchInSymptoms(search_phrases[x], null, db, y); 
                                }
                                for (int z = 0; z < x_results.size(); z++) {
                                    x_results.get(z).RepertoryID = (short) y;
                                    temp_results.add(x_results.get(z));
                                }  
                            }
                        }
                        for (int y = 0; y < temp_results.size(); y++) {
                            for (int z = 0; z < search_results.size(); z++) {
                                if (search_results.get(z).id == temp_results.get(y).id) {
                                    search_results.remove(z);
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!multiple_repertories_results) {
                    for (int x = 0; x < search_results.size(); x++)
                        search_results.get(x).RepertorySC = null;
                }
                if (search_results.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Die Suche erzielte keine Treffer. Bitte versuchen Sie andere Suchworte");
                    return;
                }
                Collections.sort(search_results);
                DefaultMutableTreeNode top = new DefaultMutableTreeNode("Results");
                int cnt = 0;
                for (int x = 0; x < search_results.size(); x++) {                    
                    DefaultMutableTreeNode tr = new DefaultMutableTreeNode(search_results.get(x));
                    top.add(tr);
                    cnt++;
                }
                UpdateStatusLabel(cnt);
                JTree x = new JTree(top);
                SymptomTree.setModel(x.getModel());
                
                if (search_results.size() != 0 && save_search_result) {
                    try {
                        search_history.get(search_history_pos).selected_symptom = selected_sym;
                    } catch (Exception e) {}
                    search_history_pos++;
                    if (search_history_pos > search_history.size() - 1) {
                        SearchHistory sh = new SearchHistory();
                        sh.search_string = key_words;
                        sh.selected_symptom = null;
                        search_history.add(sh);
                    }
                    else
                    {
                        SearchHistory sh = new SearchHistory();
                        sh.search_string = key_words;
                        sh.selected_symptom = null;
                        search_history.set(search_history_pos, sh);
                        if (search_history_pos != search_history.size() - 1) {
                            while (search_history.size() - 1 != search_history_pos) {
                                search_history.remove(search_history.size() - 1);
                            }
                        }
                    }
                }
                selected_sym = null;
            } finally {
                pdt.Close();
            }
        }
    }

    /** Renderer used to add icons to the SymptomTree
     * 
     */
private class SymptomTreeRenderer extends DefaultTreeCellRenderer {
        Icon LeafRef;
        Icon GroupRef;
        Icon Group;
        Icon Leaf;

        public SymptomTreeRenderer(Icon icon_leaf, Icon icon_group, Icon icon_leaf_ref, Icon icon_group_ref) {
            LeafRef = icon_leaf_ref;
            GroupRef = icon_group_ref;
            Group = icon_group;
            Leaf = icon_leaf;
        }

        public Component getTreeCellRendererComponent(
                            JTree tree,
                            Object value,
                            boolean sel,
                            boolean expanded,
                            boolean leaf,
                            int row,
                            boolean hasFocus) {

            super.getTreeCellRendererComponent(
                            tree, value, sel,
                            expanded, leaf, row,
                            hasFocus);
            if (leaf && HasReferences(value)) setIcon(LeafRef);
            if (!leaf && HasReferences(value)) setIcon(GroupRef);
            if (leaf && !HasReferences(value)) setIcon(Leaf);
            if (!leaf && !HasReferences(value)) setIcon(Group);

            return this;
        }

        protected boolean HasReferences(Object value) {
            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode)value;
            try{
               Symptom nodeInfo =  (Symptom)(node.getUserObject());
               if (nodeInfo.RepertoryID != db.GetCurrentRepertoryIndex() || db.GetCurrentRepertory().GetSymptomReferenceCnt(nodeInfo.id) == 0) return false;
               else
               return true;            
            }
            catch (Exception e) {
                return false;
            }
        }
}

/** Renderer used to add colors and formats to the RemedySymptom Tree
 * 
 */
class RemedySymptomTreeRenderer extends DefaultTreeCellRenderer {
        Icon Positive;
        Icon Negative;
        Icon Normal;
        JTree this_tree;

        public RemedySymptomTreeRenderer(JTree tree, Icon icon_positive, Icon icon_negative, Icon icon_normal) {
            Positive = icon_positive;
            Negative = icon_negative;
            Normal = icon_normal;
            this.this_tree = tree;
        }
    
        public Component getTreeCellRendererComponent(
                            JTree tree,
                            Object value,
                            boolean sel,
                            boolean expanded,
                            boolean leaf,
                            int row,
                            boolean hasFocus) {

            super.getTreeCellRendererComponent(
                            tree, value, sel,
                            expanded, leaf, row,
                            hasFocus);            
            int rem_grade = GetRemGrade(value.toString());
            if (leaf && rem_grade != -1) {
                if (rem_grade == 1) {
                    Font f = new Font(getFont().getName(), Font.PLAIN, this_tree.getFont().getSize());
                    setFont(f);                    
                    setForeground(java.awt.Color.GRAY);
                }
                else
                if (rem_grade == 2) {
                    Font f = new Font(getFont().getName(), Font.PLAIN, this_tree.getFont().getSize());
                    setFont(f);
                    setForeground(java.awt.Color.BLACK);
                }
                else
                if (rem_grade == 3) {
                    Font f = new Font(getFont().getName(), Font.PLAIN, this_tree.getFont().getSize());
                    setFont(f);
                    setForeground(java.awt.Color.BLUE);
                }                    
                else
                if (rem_grade == 4) {
                    Font f = new Font(getFont().getName(), Font.PLAIN, this_tree.getFont().getSize());
                    setFont(f);
                    setForeground(java.awt.Color.RED);
                }
                else
                if (rem_grade == 5) {
                    Font f = new Font(getFont().getName(), Font.BOLD, this_tree.getFont().getSize());
                    setFont(f);
                    setForeground(java.awt.Color.RED);
                }
                else
                if (rem_grade > 5) {
                    Font f = new Font(getFont().getName(), Font.BOLD, this_tree.getFont().getSize());
                    setFont(f);
                    setForeground(java.awt.Color.GREEN);
                }
                //this.setText(value.toString().substring(value.toString().indexOf(Utils.REMSYMPTOM_GRADE_END) + Utils.REMSYMPTOM_GRADE_END.length()));
            }
            else
            if (!leaf) {
                try {
                    Font f = new Font(getFont().getName(), Font.PLAIN, this_tree.getFont().getSize());
                    setFont(f);
                    String temp = value.toString();
                    if (temp.lastIndexOf(Utils.SYMPTOM_COUNT_START) != -1) {
                        temp = temp.substring(0, temp.lastIndexOf(Utils.SYMPTOM_COUNT_START));
                    }
                    int[] desktops = GetDesktops();
                    int positive_cnt = 0;
                    int negative_cnt = 0;
                    int sym_grade = 0;
                    for (int y = 0; y < desktops.length; y++) {
                        for (int x = 0; x < SelectedSymptoms.size(); x++) {                            
                            if (SelectedSymptoms.get(x).sym_name.equals(temp) && desktops[y] == SelectedSymptoms.get(x).desktop) {
                               SelectedSymptomItem ssi = SelectedSymptoms.get(x);
                               if (ssi.positive_filter) positive_cnt++;
                               if (ssi.negative_filter) negative_cnt++;
                               sym_grade += ssi.symptom_grade;
                            }
                        }                    
                    }
                    if (positive_cnt == desktops.length && Positive != null) setIcon(Positive);
                    else
                    if (negative_cnt == desktops.length && Negative != null) setIcon(Negative);
                    else
                    setIcon(Normal);                    
                    if (sel && (sym_grade / desktops.length) != Database.SYMPTOM_GRADE_AVERAGE) {
                        f = new Font(getFont().getName(), Font.ITALIC, this_tree.getFont().getSize());
                        setFont(f);
                        return this;
                    }   
                    else
                    if (sel) {
                        return this;
                    }
                    if ((sym_grade / desktops.length) == Database.SYMPTOM_GRADE_AVERAGE) {
                        if (!sel) setForeground(java.awt.Color.BLACK);
                    }
                    else
                    if ((sym_grade / desktops.length) == Database.SYMPTOM_GRADE_ESSENTIAL) setForeground(java.awt.Color.RED);
                    else
                    if ((sym_grade / desktops.length) == Database.SYMPTOM_GRADE_IMPORTANT) setForeground(new java.awt.Color(10, 150, 10));
                    else
                    if ((sym_grade / desktops.length) == Database.SYMPTOM_GRADE_NON_IMPORTANT) setForeground(java.awt.Color.GRAY);
                    else
                    if ((sym_grade / desktops.length) == Database.SYMPTOM_GRADE_VERY_IMPORTANT) setForeground(java.awt.Color.BLUE);
                    else
                    if (!sel) setForeground(java.awt.Color.BLACK);
                } catch (Exception e) {
                }
            }
            return this;
        }
        protected int GetRemGrade(String remedy) {
            try{
               return Integer.parseInt(remedy.substring(remedy.indexOf(Utils.REMSYMPTOM_GRADE_START) + Utils.REMSYMPTOM_GRADE_START.length(), remedy.indexOf(Utils.REMSYMPTOM_GRADE_END)));
            }
            catch (Exception e) {
                return -1;
            }
        }
}

/** Constructor
 * 
 * @param app
 */
    public PrescriberView(SingleFrameApplication app) {                
        super(app);
        // exit listener
        app.addExitListener(new ExitListener() {
        // exit listener to ask whether to save repertory / changes
            public boolean canExit(EventObject arg0) {
                if (db.GetCurrentRepertory() != null && db.GetCurrentRepertory().GetRepertoryChanged()) {
                    int rtrn = JOptionPane.showConfirmDialog(null, "Das Repertorium wurde geändert. Möchten Sie die Änderungen speichern?");
                    if (rtrn == JOptionPane.CANCEL_OPTION) return false;
                    if (rtrn == JOptionPane.OK_OPTION || rtrn == JOptionPane.YES_OPTION) {
                        if (!SaveCurrentRepertory(null)) return false;
                    }
                }
                if (SelectedSymptoms != null && SelectedSymptoms.size() != 0) {
                    int rtrn = JOptionPane.showConfirmDialog(null, "Das Repertorium wurde geändert. Möchten Sie die Änderungen speichern");
                    if (rtrn == JOptionPane.CANCEL_OPTION) return false;
                    if (rtrn == JOptionPane.NO_OPTION) return true;
                    if (!SaveCurrentRepertorization()) return false;
                }
                return true;
            }

            public void willExit(EventObject arg0) {
            }
        }); 
        // this is used to determine the directory in which is the OpenRep installed
        java.net.URL dir = getClass().getProtectionDomain().getCodeSource().getLocation();
        install_directory = Utils.ConvertURLToPath(dir);
        install_directory = Utils.ExtractFilePath(install_directory);
        pms_directory = install_directory + "pms" + java.io.File.separatorChar;
        // setting up of logger
        Logger.OutputFile = install_directory+"log.txt";
        Logger.StartLogger();
        File inst_dir = new File(install_directory);
        if (!inst_dir.exists()) inst_dir.mkdirs();
        File pms_dir = new File(pms_directory);
        if (!pms_dir.exists()) pms_dir.mkdirs();
        
        // read the icons for the tree view
        try {
            icons = new Icons(install_directory+Icons.pictures_dir);
        }
        catch (Exception e) {
            System.out.println("Error could not find the icon files");
        }
        File f = new File (install_directory+"rep"+Utils.FILE_EXTENSION_SEPARATOR+"rdx");
        if (!f.exists()) {
            try {
                Utils.WriteFile(install_directory+"rep"+Utils.FILE_EXTENSION_SEPARATOR+"rdx", Database.REPERTORY_FILE_REPERTORY_TAG_START+"\n"+Database.REPERTORY_FILE_FILENAME_TAG_START+install_directory+"publicum"+java.io.File.separatorChar+"publicum.rd"+Database.REPERTORY_FILE_FILENAME_TAG_END+"\n"+Database.REPERTORY_FILE_REPERTORY_TAG_END, config.GetAutomaticSaving());
            } catch (Exception e) {
                Logger.AddEntryToLog("Error while creating the default rdx file "+install_directory+"rep"+Utils.FILE_EXTENSION_SEPARATOR+"rdx"+". Exception="+e.getMessage());
            }            
        }
        initComponents();

        config = new Configuration (install_directory+config_file);

        int size = config.GetValue(Configuration.Key_PrescriberView_SymptomTree);
        Font ft;
        if (size != -1) {
            ft = new Font(SymptomTree.getFont().getName(), SymptomTree.getFont().getStyle(), size);
            SymptomTree.setFont(ft);
        }

        size = config.GetValue(Configuration.Key_PrescriberView_RemSymptomTree);
        if (size != -1) {
            ft = new Font(RemSymptomsTree.getFont().getName(), RemSymptomsTree.getFont().getStyle(), size);
            RemSymptomsTree.setFont(ft);
        }

        size = config.GetValue(Configuration.Key_PrescriberView_RemSymptomTable);
        if (size != -1) {
            ft = new Font(RemSymptomTable.getFont().getName(), RemSymptomTable.getFont().getStyle(), size);
            RemSymptomTable.setFont(ft);
            try {
                RemSymptomTable.setRowHeight(RemSymptomTable.getFontMetrics(RemSymptomTable.getFont()).getHeight());
            } catch (Exception e) {}
        }
        
        class TreeDropTarget implements DropTargetListener {

            DropTarget target;

            JTree targetTree;
            
            private boolean symtreedrop;
            private boolean remsymtreedrop;

            public TreeDropTarget(JTree tree, boolean drop_from_symtree, boolean drop_from_remsymtree) {
                targetTree = tree;
                target = new DropTarget(targetTree, this);
                this.symtreedrop = drop_from_symtree;
                this.remsymtreedrop = drop_from_remsymtree;
            }

            /*
            * Drop Event Handlers
            */
            private TreeNode getNodeForEvent(DropTargetDragEvent dtde) {
                Point p = dtde.getLocation();
                DropTargetContext dtc = dtde.getDropTargetContext();
                JTree tree = (JTree) dtc.getComponent();
                TreePath path = tree.getClosestPathForLocation(p.x, p.y);
                return (TreeNode) path.getLastPathComponent();
            }

            public void dragEnter(DropTargetDragEvent dtde) {
                /*TreeNode node = getNodeForEvent(dtde);
                if (node.isLeaf()) {
                dtde.rejectDrag();
                } else {*/
                // start by supporting move operations
                //dtde.acceptDrag(DnDConstants.ACTION_MOVE);
                dtde.acceptDrag(dtde.getDropAction());
                //}
            }

            public void dragOver(DropTargetDragEvent dtde) {
                /*    TreeNode node = getNodeForEvent(dtde);
                if (node.isLeaf()) {
                dtde.rejectDrag();
                } else {
                // start by supporting move operations
                //dtde.acceptDrag(DnDConstants.ACTION_MOVE);*/
                dtde.acceptDrag(dtde.getDropAction());
                //}
            }

            public void dragExit(DropTargetEvent dte) {
            }

            public void dropActionChanged(DropTargetDragEvent dtde) {
            }

            public void drop(DropTargetDropEvent dtde) {
                Point pt = dtde.getLocation();
                DropTargetContext dtc = dtde.getDropTargetContext();
                JTree tree = (JTree) dtc.getComponent();
                if (symtreedrop) AddSelectedItemsToRemSymptomTree(null, -1);
                if (remsymtreedrop) DeleteSelectedSymptomsFromRepertorization();
            }
        }

       class ButtonDropTarget implements DropTargetListener {

            DropTarget target;

            JToggleButton targetButton;

            Object source = null;

            public ButtonDropTarget(JToggleButton button) {
                targetButton = button;
                target = new DropTarget(targetButton, this);
            }

            public void dragEnter(DropTargetDragEvent dtde) {
                /*TreeNode node = getNodeForEvent(dtde);
                if (node.isLeaf()) {
                dtde.rejectDrag();
                } else {*/
                // start by supporting move operations
                //dtde.acceptDrag(DnDConstants.ACTION_MOVE);
                this.source = dtde.getSource();
                System.out.println(dtde.getSourceActions());
                dtde.acceptDrag(dtde.getDropAction());
                //}
            }

            public void dragOver(DropTargetDragEvent dtde) {
                /*    TreeNode node = getNodeForEvent(dtde);
                if (node.isLeaf()) {
                dtde.rejectDrag();
                } else {
                // start by supporting move operations
                //dtde.acceptDrag(DnDConstants.ACTION_MOVE);*/
                dtde.acceptDrag(dtde.getDropAction());
                //}
            }

            public void dragExit(DropTargetEvent dte) {
            }

            public void dropActionChanged(DropTargetDragEvent dtde) {
            }

            public void drop(DropTargetDropEvent dtde) {
                Point pt = dtde.getLocation();
                DropTargetContext dtc = dtde.getDropTargetContext();
                if (drag_source == DRAG_SOURCE_NONE) return;
                int desktop = -1;
                if (targetButton == DesktopButton_1) desktop = 1;
                else
                if (targetButton == DesktopButton_2) desktop = 2;
                else
                if (targetButton == DesktopButton_3) desktop = 3;
                else
                if (targetButton == DesktopButton_4) desktop = 4;
                else
                if (targetButton == DesktopButton_5) desktop = 5;
                else
                desktop = -1;
                if (drag_source == DRAG_SOURCE_SYMPTOMTREE) AddSelectedItemsToRemSymptomTree(null, desktop);
                else
                if (drag_source == DRAG_SOURCE_REMSYMPTOMTREE) {
                    for (int x = 0; x < RemSymptomsTree.getSelectionCount(); x++) {
                        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)RemSymptomsTree.getSelectionPaths()[x].getLastPathComponent();
                        SelectedSymptomItem ssi = (SelectedSymptomItem)dmtn.getUserObject();
                        if (!drag_remsymptom_ctrl) {
                            ssi.desktop = desktop;
                        }
                        else
                        {
                            SelectedSymptomItem new_ssi = ssi.DeepCopy();
                            new_ssi.desktop = desktop;
                            SelectedSymptoms.add(new_ssi);
                        }
                    }
                    UpdateRemedySymptomsTree();
                }
            }
        }

        ButtonDropTarget b1 = new ButtonDropTarget(DesktopButton_1);
        ButtonDropTarget b2 = new ButtonDropTarget(DesktopButton_2);
        ButtonDropTarget b3 = new ButtonDropTarget(DesktopButton_3);
        ButtonDropTarget b4 = new ButtonDropTarget(DesktopButton_4);
        ButtonDropTarget b5 = new ButtonDropTarget(DesktopButton_5);

        TreeDropTarget dtd = new TreeDropTarget(RemSymptomsTree, true, false);
        TreeDropTarget dtdr = new TreeDropTarget(SymptomTree, false, true);        

        RemSymptomTable.getTableHeader().addMouseListener(RemSymptomTableHeaderListener);        
        
        DesktopButton_1.setSelected(true);

        byte overwrite_repertories = 0;
        byte cnt = 0;
        // moves data from default_rep - only if the repertories do not already exist        
        File def_rep = new File (install_directory+"default_rep");
        if (def_rep.exists()) {
            File[] dirs = null;
            if (def_rep.isDirectory()) dirs = def_rep.listFiles();
            if (dirs != null) {
                for (int x = 0; x < dirs.length; x++) {
                    String last_dir_portion = dirs[x].getAbsolutePath().substring(dirs[x].getAbsolutePath().lastIndexOf(File.separatorChar)+1);
                    if (new File(install_directory+last_dir_portion).exists()) {                        
                        if (overwrite_repertories == 0) {
                            int temp = JOptionPane.showConfirmDialog(null, "I have detected a more recent copy of repertories in the installation package. Do you want to overwrite your repertories?\n Please note, that this will delete any changes that you have made to the repertories in standard folders.");
                            if (temp == JOptionPane.OK_OPTION || temp == JOptionPane.YES_OPTION) overwrite_repertories = 1;
                            else
                            overwrite_repertories = 2;
                        }
                        if (overwrite_repertories == 1) {
                            // user has agreed to replace all repertories with new versions
                            Utils.deleteDirectory(new File (install_directory+last_dir_portion));
                            dirs[x].renameTo(new File(install_directory+last_dir_portion));                            
                            cnt++;
                        }
                        else
                        {
                            Utils.deleteDirectory(dirs[x]);
                        }
                    }
                    else
                    {
                        dirs[x].renameTo(new File(install_directory+last_dir_portion));
                    }
                }
            }
        }
        if (cnt != 0) {
            JOptionPane.showMessageDialog(null, "I have succesfully replaced "+cnt+" repertories with new versions.");
        }
        try {
            InitializeDatabaseThread idt = new InitializeDatabaseThread(false);
            idt.start();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error while opening the default repertory");
        }
            ImageIcon i1;
            ImageIcon i2;
            ImageIcon i3;
            ImageIcon i4;
            ImageIcon i5;
            ImageIcon i6;
            try {
            
                i1 = new ImageIcon(icons.GetImage(Icons.TREE_LEAF));
                i2 = new ImageIcon(icons.GetImage(Icons.TREE_GROUP));
                i3 = new ImageIcon(icons.GetImage(Icons.TREE_LEAF_REF));
                i4 = new ImageIcon(icons.GetImage(Icons.TREE_GROUP_REF));
                i5 = new ImageIcon(icons.GetImage(Icons.TREE_POSITIVE));
                i6 = new ImageIcon(icons.GetImage(Icons.TREE_NEGATIVE));
                SymptomTree.setCellRenderer(new SymptomTreeRenderer(i1, i2, i3, i4));
                RemSymptomsTree.setCellRenderer(new RemedySymptomTreeRenderer(RemSymptomsTree, i5, i6, i2));
            } catch (Exception e) {
                RemSymptomsTree.setCellRenderer(new RemedySymptomTreeRenderer(RemSymptomsTree, null, null, null));
                Logger.AddEntryToLog("Error while reading the icon files "+e.getMessage());
            }
            // status bar initialization - message timeout, idle icon and busy animation, etc
            ResourceMap resourceMap = getResourceMap();
            int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
            messageTimer = new Timer(messageTimeout, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    statusMessageLabel.setText(GetRepertoryStatusMessageLabel());
                }
            });
        
            // populates the PopUpMenus
            JMenuItem mi = new JMenuItem("Symptom(e) aus Auswahl entfernen (F5)");
            mi.setActionCommand(DeleteSymptomFromSelection);
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            PopupMenu_RemSymptomTree.add(mi);
        
            PopupMenu_RemSymptomTree.addSeparator();

            mi = new JMenuItem("Ausgewählte Symptome zusammenführen (F7)");
            mi.setActionCommand(MainMenu_MergeSymptoms);
            mi.addActionListener(MainMenu_Listener);
            PopupMenu_RemSymptomTree.add(mi);
        
            PopupMenu_RemSymptomTree.addSeparator();
                
            mi = new JMenuItem("Ändere Gewichtung in ESSENZIELL (ALT+1)");
            mi.setActionCommand(String.valueOf(Database.SYMPTOM_GRADE_ESSENTIAL));
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            PopupMenu_RemSymptomTree.add(mi);
        
            mi = new JMenuItem("Ändere Gewichtung in SEHR WICHTIG (ALT+2)");
            mi.setActionCommand(String.valueOf(Database.SYMPTOM_GRADE_VERY_IMPORTANT));
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            PopupMenu_RemSymptomTree.add(mi);

            mi = new JMenuItem("Ändere Gewichtung in WICHTIG (ALT+3)");
            mi.setActionCommand(String.valueOf(Database.SYMPTOM_GRADE_IMPORTANT));
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            PopupMenu_RemSymptomTree.add(mi);
        
            mi = new JMenuItem("Ändere Gewichtung in DURCHSCHNITTLICH (ALT+4)");
            mi.setActionCommand(String.valueOf(Database.SYMPTOM_GRADE_AVERAGE));
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            PopupMenu_RemSymptomTree.add(mi);

            mi = new JMenuItem("Ändere Gewichtung in NICHT WICHTIG (ALT+5)");
            mi.setActionCommand(String.valueOf(Database.SYMPTOM_GRADE_NON_IMPORTANT));
            mi.addActionListener(PopupMenu_RemSymptomTree_Listener);
            PopupMenu_RemSymptomTree.add(mi);
                        
            messageTimer.setRepeats(false);
            // connecting action tasks to status bar via TaskMonitor
/*            TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
            taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    String propertyName = evt.getPropertyName();
                    if ("started".equals(propertyName)) {
                        if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
            });*/
        
            // sets the action commands to the actions
        
            exitMenuItem.setActionCommand(MainMenu_Exit);
            exitMenuItem.addActionListener(MainMenu_Listener);

            newMenuItem.setActionCommand(MainMenu_New);
            newMenuItem.addActionListener(MainMenu_Listener);
        
            saveMenuItem.setActionCommand(MainMenu_Save);
            saveMenuItem.addActionListener(MainMenu_Listener);
        
            saveasMenuItem.setActionCommand(MainMenu_SaveAs);
            saveasMenuItem.addActionListener(MainMenu_Listener);
        
            openMenuItem.setActionCommand(MainMenu_Open);
            openMenuItem.addActionListener(MainMenu_Listener);
        
            importRepertoryMenuItem.setActionCommand(MainMenu_ImportRepertory);
            importRepertoryMenuItem.addActionListener(MainMenu_Listener);
        
            openRepertoryMenuItem.setActionCommand(MainMenu_OpenRepertory);
            openRepertoryMenuItem.addActionListener(MainMenu_Listener);
        
            AddMergedSymptomsButton.setActionCommand(MainMenu_AddMerged);
            AddMergedSymptomsButton.addActionListener(MainMenu_Listener);
        
            PositiveFilterButton.setActionCommand(MainMenu_PositiveFilter);
            PositiveFilterButton.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
            NegativeFilterButton.setActionCommand(MainMenu_NegativeFilter);
            NegativeFilterButton.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
            removeRepertoryMenuItem.setActionCommand(MainMenu_RemoveRepertory);
            removeRepertoryMenuItem.addActionListener(MainMenu_Listener);
        
            NewSessionButton.setActionCommand(MainMenu_New);
            NewSessionButton.addActionListener(MainMenu_Listener);
            NewSessionButton.setToolTipText("Erstelle einen neuen Fall");
        
            OpenSessionButton.setActionCommand(MainMenu_Open);
            OpenSessionButton.addActionListener(MainMenu_Listener);
            OpenSessionButton.setToolTipText("Öffne einen Fall");

            SaveSessionButton.setActionCommand(MainMenu_Save);
            SaveSessionButton.addActionListener(MainMenu_Listener);
            SaveSessionButton.setToolTipText("Speichere einen Fall");
        
            OpenRepertoryButton.setActionCommand(MainMenu_OpenRepertory);
            OpenRepertoryButton.addActionListener(MainMenu_Listener);
        
            RepertorizeButton.setActionCommand(MainMenu_Repertorize);
            RepertorizeButton.addActionListener(MainMenu_Listener);

            RepertorizeMenuItem.setActionCommand(MainMenu_Repertorize);
            RepertorizeMenuItem.addActionListener(MainMenu_Listener);
        
            RepertorizationResultsButton.setActionCommand(MainMenu_RepertorizationResults);
            RepertorizationResultsButton.addActionListener(MainMenu_Listener);
        
            DisplayFullResults.setActionCommand(MainMenu_RepertorizationResults);
            DisplayFullResults.addActionListener(MainMenu_Listener);
        
            AddSymptomToRepertorizationButton.setActionCommand(AddSymptom);
            AddSymptomToRepertorizationButton.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
            DeleteSymptomFromRepertorizationButton.setActionCommand(DeleteSymptomFromSelection);
            DeleteSymptomFromRepertorizationButton.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
            AddNewSymptomButton.setActionCommand(MainMenu_AddNewSymptom);
            AddNewSymptomButton.addActionListener(MainMenu_Listener);
        
            SaveRepertoryMenuItem.setActionCommand(MainMenu_SaveRepertory);
            SaveRepertoryMenuItem.addActionListener(MainMenu_Listener);
        
            DeleteSymptomButton.setActionCommand(MainMenu_DeleteSymptom);
            DeleteSymptomButton.addActionListener(MainMenu_Listener);
        
            AddMergedSymptomsToRepertorization_MainMenu.setActionCommand(MainMenu_AddMerged);
            AddMergedSymptomsToRepertorization_MainMenu.addActionListener(MainMenu_Listener);
        
            EditSymptomButton.setActionCommand(MainMenu_EditSymptom);
            EditSymptomButton.addActionListener(MainMenu_Listener);
        
            EditRemedyAdditionsButton.setActionCommand(MainMenu_EditRemedyAdditions);
            EditRemedyAdditionsButton.addActionListener(MainMenu_Listener);
        
            ExportRepertoryChangesMenuItem.setActionCommand(MainMenu_ExportRepertoryChanges);
            ExportRepertoryChangesMenuItem.addActionListener(MainMenu_Listener);
        
        SaveRepertoryAsMenuItem.setActionCommand(MainMenu_SaveRepertoryAs);
        SaveRepertoryAsMenuItem.addActionListener(MainMenu_Listener);
        
        ReversedMMButton.setActionCommand(MainMenu_ReversedMM);
        ReversedMMButton.addActionListener(MainMenu_Listener);
        
        ReversedMateriaMedicaMeduItem.setActionCommand(MainMenu_ReversedMM);
        ReversedMateriaMedicaMeduItem.addActionListener(MainMenu_Listener);
        
        PreferencesMenuItem.setActionCommand(MainMenu_Preferences);
        PreferencesMenuItem.addActionListener(MainMenu_Listener);
        
        PMS_MenuItem.setActionCommand(MainMenu_PMS);
        PMSButton.setActionCommand(MainMenu_PMS);
        PMS_MenuItem.addActionListener(MainMenu_Listener);
        PMSButton.addActionListener(MainMenu_Listener);
        
        AddSymptomToRepertorizationMenuItem.setActionCommand(AddSymptom);
        AddSymptomToRepertorizationMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
        DeleteSymptomFromRepertorizationMenuItem.setActionCommand(DeleteSymptomFromSelection);
        DeleteSymptomFromRepertorizationMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
        AddSymptomToRepertoryMenuItem.setActionCommand(MainMenu_AddNewSymptom);
        AddSymptomToRepertoryMenuItem.addActionListener(MainMenu_Listener);
        
        DeleteSymptomFromRepertoryMenuItem.setActionCommand(MainMenu_DeleteSymptom);
        DeleteSymptomFromRepertoryMenuItem.addActionListener(MainMenu_Listener);
        
        EditSymptomNameMenuItem.setActionCommand(MainMenu_EditSymptom);
        EditSymptomNameMenuItem.addActionListener(MainMenu_Listener);
        
        EditRemedyAdditionsMenuItem.setActionCommand(MainMenu_EditRemedyAdditions);
        EditRemedyAdditionsMenuItem.addActionListener(MainMenu_Listener);
        
        DisplayRemedyAdditionsMenuItem.setActionCommand(DisplayRemedyAdditions);
        DisplayRemedyAdditionsMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
        DisplaySymptomReferencesMenuItem.setActionCommand(DisplaySymptomReferences);
        DisplaySymptomReferencesMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
        EssentialMenuItem.setActionCommand(String.valueOf(Database.SYMPTOM_GRADE_ESSENTIAL));
        EssentialMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
        VeryImportantMenuItem.setActionCommand(String.valueOf(Database.SYMPTOM_GRADE_VERY_IMPORTANT));
        VeryImportantMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
        ImportantMenuItem.setActionCommand(String.valueOf(Database.SYMPTOM_GRADE_IMPORTANT));
        ImportantMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
        AverageMenuItem.setActionCommand(String.valueOf(Database.SYMPTOM_GRADE_AVERAGE));
        AverageMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);        
        
        NotImportantMenuItem.setActionCommand(String.valueOf(Database.SYMPTOM_GRADE_NON_IMPORTANT));
        NotImportantMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);
        
        MergeSymptomsButton.setActionCommand(MainMenu_MergeSymptoms);
        MergeSymptomsButton.addActionListener(MainMenu_Listener);
        
        MergeSymptomsMenuItem.setActionCommand(MainMenu_MergeSymptoms);
        MergeSymptomsMenuItem.addActionListener(MainMenu_Listener);
        
        DesktopButton_1.setActionCommand(MainMenu_Desktop1);
        DesktopButton_2.setActionCommand(MainMenu_Desktop2);
        DesktopButton_3.setActionCommand(MainMenu_Desktop3);
        DesktopButton_4.setActionCommand(MainMenu_Desktop4);
        DesktopButton_5.setActionCommand(MainMenu_Desktop5);        
        
        DesktopButton_1.addActionListener(MainMenu_Listener);
        DesktopButton_2.addActionListener(MainMenu_Listener);
        DesktopButton_3.addActionListener(MainMenu_Listener);
        DesktopButton_4.addActionListener(MainMenu_Listener);
        DesktopButton_5.addActionListener(MainMenu_Listener);        
        
        Desktop1MenuItem.setActionCommand(MainMenu_Desktop1);
        Desktop2MenuItem.setActionCommand(MainMenu_Desktop2);
        Desktop3MenuItem.setActionCommand(MainMenu_Desktop3);
        Desktop4MenuItem.setActionCommand(MainMenu_Desktop4);
        Desktop5MenuItem.setActionCommand(MainMenu_Desktop5);        
        
        Desktop1MenuItem.addActionListener(MainMenu_Listener);
        Desktop2MenuItem.addActionListener(MainMenu_Listener);
        Desktop3MenuItem.addActionListener(MainMenu_Listener);
        Desktop4MenuItem.addActionListener(MainMenu_Listener);
        Desktop5MenuItem.addActionListener(MainMenu_Listener);
        
        RepertoryPropertiesButton.setActionCommand(MainMenu_RepertoryProperties);
        RepertoryPropertiesButton.addActionListener(MainMenu_Listener);
        
        NewRepertoryButton.setActionCommand(MainMenu_NewRepertory);
        NewRepertoryButton.addActionListener(MainMenu_Listener);
        
        ConsoleButton.setActionCommand(MainMenu_Console);
        ConsoleButton.addActionListener(MainMenu_Listener);

        PreviousSearchResultMenuItem.setActionCommand(MainMenu_PreviousSearchResult);
        PreviousSearchResultMenuItem.addActionListener(MainMenu_Listener);

        PrevSearchButton.setActionCommand(MainMenu_PreviousSearchResult);
        PrevSearchButton.addActionListener(MainMenu_Listener);

        RepertoryTreeMenuItem.setActionCommand(MainMenu_RepertoryTree);
        RepertoryTreeMenuItem.addActionListener(MainMenu_Listener);

        RepertoryTreeButton.setActionCommand(MainMenu_RepertoryTree);
        RepertoryTreeButton.addActionListener(MainMenu_Listener);

        NextSearchMenuItem.setActionCommand(MainMenu_NextSearchResult);
        NextSearchMenuItem.addActionListener(MainMenu_Listener);

        NextSearchButton.setActionCommand(MainMenu_NextSearchResult);
        NextSearchButton.addActionListener(MainMenu_Listener);

        DisplayHigherRubricMenuItem.setActionCommand(DisplayHigherRubric);
        DisplayHigherRubricMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);

        DisplayTopRubricMenuItem.setActionCommand(DisplayTopRubric);
        DisplayTopRubricMenuItem.addActionListener(PopupMenu_RemSymptomTree_Listener);
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = PrescriberApp.getApplication().getMainFrame();
            aboutBox = new PrescriberAboutBox(mainFrame, version);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        PrescriberApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        NewSessionButton = new javax.swing.JButton();
        OpenSessionButton = new javax.swing.JButton();
        SaveSessionButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        OpenRepertoryButton = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        ReversedMMButton = new javax.swing.JButton();
        PMSButton = new javax.swing.JButton();
        jSeparator20 = new javax.swing.JToolBar.Separator();
        PrevSearchButton = new javax.swing.JButton();
        RepertoryTreeButton = new javax.swing.JButton();
        NextSearchButton = new javax.swing.JButton();
        SearchEdit = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        RemSymptomTable = new javax.swing.JTable();
        jToolBar2 = new javax.swing.JToolBar();
        RepertorizeButton = new javax.swing.JButton();
        RepertorizationResultsButton = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        AddSymptomToRepertorizationButton = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        AddMergedSymptomsButton = new javax.swing.JButton();
        AddNewSymptomButton = new javax.swing.JButton();
        DeleteSymptomButton = new javax.swing.JButton();
        EditSymptomButton = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        EditRemedyAdditionsButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        SymptomTree = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        RemSymptomsTree = new javax.swing.JTree();
        jToolBar4 = new javax.swing.JToolBar();
        DeleteSymptomFromRepertorizationButton = new javax.swing.JButton();
        MergeSymptomsButton = new javax.swing.JButton();
        jSeparator18 = new javax.swing.JToolBar.Separator();
        PositiveFilterButton = new javax.swing.JButton();
        NegativeFilterButton = new javax.swing.JButton();
        jToolBar5 = new javax.swing.JToolBar();
        DesktopButton_1 = new javax.swing.JToggleButton();
        DesktopButton_2 = new javax.swing.JToggleButton();
        DesktopButton_3 = new javax.swing.JToggleButton();
        DesktopButton_4 = new javax.swing.JToggleButton();
        DesktopButton_5 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveasMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        PreferencesMenuItem = new javax.swing.JMenuItem();
        ConsoleButton = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JSeparator();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        AddSymptomToRepertorizationMenuItem = new javax.swing.JMenuItem();
        AddMergedSymptomsToRepertorization_MainMenu = new javax.swing.JMenuItem();
        DeleteSymptomFromRepertorizationMenuItem = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JSeparator();
        AddSymptomToRepertoryMenuItem = new javax.swing.JMenuItem();
        DeleteSymptomFromRepertoryMenuItem = new javax.swing.JMenuItem();
        EditSymptomNameMenuItem = new javax.swing.JMenuItem();
        EditRemedyAdditionsMenuItem = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JSeparator();
        EssentialMenuItem = new javax.swing.JMenuItem();
        VeryImportantMenuItem = new javax.swing.JMenuItem();
        ImportantMenuItem = new javax.swing.JMenuItem();
        AverageMenuItem = new javax.swing.JMenuItem();
        NotImportantMenuItem = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JSeparator();
        MergeSymptomsMenuItem = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        ReversedMateriaMedicaMeduItem = new javax.swing.JMenuItem();
        PMS_MenuItem = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JSeparator();
        DisplaySymptomReferencesMenuItem = new javax.swing.JMenuItem();
        DisplayRemedyAdditionsMenuItem = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        Desktop1MenuItem = new javax.swing.JMenuItem();
        Desktop2MenuItem = new javax.swing.JMenuItem();
        Desktop3MenuItem = new javax.swing.JMenuItem();
        Desktop4MenuItem = new javax.swing.JMenuItem();
        Desktop5MenuItem = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JSeparator();
        PreviousSearchResultMenuItem = new javax.swing.JMenuItem();
        NextSearchMenuItem = new javax.swing.JMenuItem();
        RepertoryTreeMenuItem = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JSeparator();
        DisplayTopRubricMenuItem = new javax.swing.JMenuItem();
        DisplayHigherRubricMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        importRepertoryMenuItem = new javax.swing.JMenuItem();
        removeRepertoryMenuItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        openRepertoryMenuItem = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JSeparator();
        NewRepertoryButton = new javax.swing.JMenuItem();
        SaveRepertoryMenuItem = new javax.swing.JMenuItem();
        SaveRepertoryAsMenuItem = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JSeparator();
        RepertoryPropertiesButton = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JSeparator();
        ExportRepertoryChangesMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        RepertorizeMenuItem = new javax.swing.JMenuItem();
        DisplayFullResults = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        SymptomCntLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        STree = new javax.swing.JTree();

        mainPanel.setName("mainPanel"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N
        jToolBar1.setPreferredSize(new java.awt.Dimension(143, 45));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(prescriber.PrescriberApp.class).getContext().getResourceMap(PrescriberView.class);
        NewSessionButton.setIcon(resourceMap.getIcon("NewSessionButton.icon")); // NOI18N
        NewSessionButton.setText(resourceMap.getString("NewSessionButton.text")); // NOI18N
        NewSessionButton.setFocusable(false);
        NewSessionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        NewSessionButton.setName("NewSessionButton"); // NOI18N
        NewSessionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(NewSessionButton);

        OpenSessionButton.setIcon(resourceMap.getIcon("OpenSessionButton.icon")); // NOI18N
        OpenSessionButton.setText(resourceMap.getString("OpenSessionButton.text")); // NOI18N
        OpenSessionButton.setFocusable(false);
        OpenSessionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        OpenSessionButton.setMaximumSize(new java.awt.Dimension(35, 35));
        OpenSessionButton.setMinimumSize(new java.awt.Dimension(35, 35));
        OpenSessionButton.setName("OpenSessionButton"); // NOI18N
        OpenSessionButton.setPreferredSize(new java.awt.Dimension(35, 35));
        OpenSessionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(OpenSessionButton);

        SaveSessionButton.setIcon(resourceMap.getIcon("SaveSessionButton.icon")); // NOI18N
        SaveSessionButton.setText(resourceMap.getString("SaveSessionButton.text")); // NOI18N
        SaveSessionButton.setFocusable(false);
        SaveSessionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SaveSessionButton.setName("SaveSessionButton"); // NOI18N
        SaveSessionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(SaveSessionButton);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jToolBar1.add(jSeparator3);

        OpenRepertoryButton.setIcon(resourceMap.getIcon("OpenRepertoryButton.icon")); // NOI18N
        OpenRepertoryButton.setText(resourceMap.getString("OpenRepertoryButton.text")); // NOI18N
        OpenRepertoryButton.setToolTipText(resourceMap.getString("OpenRepertoryButton.toolTipText")); // NOI18N
        OpenRepertoryButton.setFocusable(false);
        OpenRepertoryButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        OpenRepertoryButton.setName("OpenRepertoryButton"); // NOI18N
        OpenRepertoryButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(OpenRepertoryButton);

        jSeparator9.setName("jSeparator9"); // NOI18N
        jToolBar1.add(jSeparator9);

        ReversedMMButton.setIcon(resourceMap.getIcon("ReversedMMButton.icon")); // NOI18N
        ReversedMMButton.setText(resourceMap.getString("ReversedMMButton.text")); // NOI18N
        ReversedMMButton.setToolTipText(resourceMap.getString("ReversedMMButton.toolTipText")); // NOI18N
        ReversedMMButton.setFocusable(false);
        ReversedMMButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ReversedMMButton.setName("ReversedMMButton"); // NOI18N
        ReversedMMButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(ReversedMMButton);

        PMSButton.setIcon(resourceMap.getIcon("PMSButton.icon")); // NOI18N
        PMSButton.setText(resourceMap.getString("PMSButton.text")); // NOI18N
        PMSButton.setToolTipText(resourceMap.getString("PMSButton.toolTipText")); // NOI18N
        PMSButton.setFocusable(false);
        PMSButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PMSButton.setName("PMSButton"); // NOI18N
        PMSButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(PMSButton);

        jSeparator20.setName("jSeparator20"); // NOI18N
        jToolBar1.add(jSeparator20);

        PrevSearchButton.setIcon(resourceMap.getIcon("PrevSearchButton.icon")); // NOI18N
        PrevSearchButton.setText(resourceMap.getString("PrevSearchButton.text")); // NOI18N
        PrevSearchButton.setToolTipText(resourceMap.getString("PrevSearchButton.toolTipText")); // NOI18N
        PrevSearchButton.setFocusable(false);
        PrevSearchButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PrevSearchButton.setName("PrevSearchButton"); // NOI18N
        PrevSearchButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(PrevSearchButton);

        RepertoryTreeButton.setIcon(resourceMap.getIcon("RepertoryTreeButton.icon")); // NOI18N
        RepertoryTreeButton.setText(resourceMap.getString("RepertoryTreeButton.text")); // NOI18N
        RepertoryTreeButton.setToolTipText(resourceMap.getString("RepertoryTreeButton.toolTipText")); // NOI18N
        RepertoryTreeButton.setFocusable(false);
        RepertoryTreeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        RepertoryTreeButton.setName("RepertoryTreeButton"); // NOI18N
        RepertoryTreeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(RepertoryTreeButton);

        NextSearchButton.setIcon(resourceMap.getIcon("NextSearchButton.icon")); // NOI18N
        NextSearchButton.setText(resourceMap.getString("NextSearchButton.text")); // NOI18N
        NextSearchButton.setToolTipText(resourceMap.getString("NextSearchButton.toolTipText")); // NOI18N
        NextSearchButton.setFocusable(false);
        NextSearchButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        NextSearchButton.setName("NextSearchButton"); // NOI18N
        NextSearchButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(NextSearchButton);

        SearchEdit.setText(resourceMap.getString("SearchEdit.text")); // NOI18N
        SearchEdit.setName("SearchEdit"); // NOI18N
        SearchEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchEditKeyReleased(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        RemSymptomTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        RemSymptomTable.setName("RemSymptomTable"); // NOI18N
        RemSymptomTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RemSymptomTableMouseClicked(evt);
            }
        });
        RemSymptomTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RemSymptomTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(RemSymptomTable);

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(1);
        jToolBar2.setRollover(true);
        jToolBar2.setName("jToolBar2"); // NOI18N

        RepertorizeButton.setIcon(resourceMap.getIcon("RepertorizeButton.icon")); // NOI18N
        RepertorizeButton.setText(resourceMap.getString("RepertorizeButton.text")); // NOI18N
        RepertorizeButton.setToolTipText(resourceMap.getString("RepertorizeButton.toolTipText")); // NOI18N
        RepertorizeButton.setFocusable(false);
        RepertorizeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        RepertorizeButton.setName("RepertorizeButton"); // NOI18N
        RepertorizeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(RepertorizeButton);

        RepertorizationResultsButton.setIcon(resourceMap.getIcon("RepertorizationResultsButton.icon")); // NOI18N
        RepertorizationResultsButton.setText(resourceMap.getString("RepertorizationResultsButton.text")); // NOI18N
        RepertorizationResultsButton.setToolTipText(resourceMap.getString("RepertorizationResultsButton.toolTipText")); // NOI18N
        RepertorizationResultsButton.setFocusable(false);
        RepertorizationResultsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        RepertorizationResultsButton.setName("RepertorizationResultsButton"); // NOI18N
        RepertorizationResultsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(RepertorizationResultsButton);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jToolBar3.setFloatable(false);
        jToolBar3.setOrientation(1);
        jToolBar3.setRollover(true);
        jToolBar3.setName("jToolBar3"); // NOI18N

        AddSymptomToRepertorizationButton.setIcon(resourceMap.getIcon("AddSelectedSymptomsButton.icon")); // NOI18N
        AddSymptomToRepertorizationButton.setText(resourceMap.getString("AddSelectedSymptomsButton.text")); // NOI18N
        AddSymptomToRepertorizationButton.setToolTipText(resourceMap.getString("AddSelectedSymptomsButton.toolTipText")); // NOI18N
        AddSymptomToRepertorizationButton.setFocusable(false);
        AddSymptomToRepertorizationButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddSymptomToRepertorizationButton.setName("AddSelectedSymptomsButton"); // NOI18N
        AddSymptomToRepertorizationButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(AddSymptomToRepertorizationButton);

        jSeparator5.setName("jSeparator5"); // NOI18N
        jToolBar3.add(jSeparator5);

        AddMergedSymptomsButton.setIcon(resourceMap.getIcon("AddMergedSymptomsButton.icon")); // NOI18N
        AddMergedSymptomsButton.setText(resourceMap.getString("AddMergedSymptomsButton.text")); // NOI18N
        AddMergedSymptomsButton.setToolTipText(resourceMap.getString("AddMergedSymptomsButton.toolTipText")); // NOI18N
        AddMergedSymptomsButton.setFocusable(false);
        AddMergedSymptomsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddMergedSymptomsButton.setName("AddMergedSymptomsButton"); // NOI18N
        AddMergedSymptomsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(AddMergedSymptomsButton);

        AddNewSymptomButton.setIcon(resourceMap.getIcon("AddNewSymptomButton.icon")); // NOI18N
        AddNewSymptomButton.setText(resourceMap.getString("AddNewSymptomButton.text")); // NOI18N
        AddNewSymptomButton.setToolTipText(resourceMap.getString("AddNewSymptomButton.toolTipText")); // NOI18N
        AddNewSymptomButton.setFocusable(false);
        AddNewSymptomButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddNewSymptomButton.setName("AddNewSymptomButton"); // NOI18N
        AddNewSymptomButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(AddNewSymptomButton);

        DeleteSymptomButton.setIcon(resourceMap.getIcon("DeleteSymptomButton.icon")); // NOI18N
        DeleteSymptomButton.setText(resourceMap.getString("DeleteSymptomButton.text")); // NOI18N
        DeleteSymptomButton.setToolTipText(resourceMap.getString("DeleteSymptomButton.toolTipText")); // NOI18N
        DeleteSymptomButton.setFocusable(false);
        DeleteSymptomButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DeleteSymptomButton.setName("DeleteSymptomButton"); // NOI18N
        DeleteSymptomButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(DeleteSymptomButton);

        EditSymptomButton.setIcon(resourceMap.getIcon("EditSymptomButton.icon")); // NOI18N
        EditSymptomButton.setText(resourceMap.getString("EditSymptomButton.text")); // NOI18N
        EditSymptomButton.setToolTipText(resourceMap.getString("EditSymptomButton.toolTipText")); // NOI18N
        EditSymptomButton.setFocusable(false);
        EditSymptomButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EditSymptomButton.setName("EditSymptomButton"); // NOI18N
        EditSymptomButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(EditSymptomButton);

        jSeparator7.setName("jSeparator7"); // NOI18N
        jToolBar3.add(jSeparator7);

        EditRemedyAdditionsButton.setIcon(resourceMap.getIcon("EditRemedyAdditionsButton.icon")); // NOI18N
        EditRemedyAdditionsButton.setText(resourceMap.getString("EditRemedyAdditionsButton.text")); // NOI18N
        EditRemedyAdditionsButton.setToolTipText(resourceMap.getString("EditRemedyAdditionsButton.toolTipText")); // NOI18N
        EditRemedyAdditionsButton.setFocusable(false);
        EditRemedyAdditionsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EditRemedyAdditionsButton.setName("EditRemedyAdditionsButton"); // NOI18N
        EditRemedyAdditionsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        EditRemedyAdditionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditRemedyAdditionsButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(EditRemedyAdditionsButton);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        SymptomTree.setModel(null);
        SymptomTree.setDragEnabled(true);
        SymptomTree.setName("SymptomTree"); // NOI18N
        SymptomTree.setRootVisible(false);
        SymptomTree.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                SymptomTreeTreeWillExpand(evt);
            }
        });
        SymptomTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SymptomTreeMouseClicked(evt);
            }
        });
        SymptomTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                SymptomTreeValueChanged(evt);
            }
        });
        SymptomTree.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                SymptomTreeMouseDragged(evt);
            }
        });
        SymptomTree.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SymptomTreeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SymptomTreeKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(SymptomTree);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jToolBar3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .add(jToolBar3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                .add(0, 0, 0))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        RemSymptomsTree.setModel(null);
        RemSymptomsTree.setDragEnabled(true);
        RemSymptomsTree.setName("RemSymptomsTree"); // NOI18N
        RemSymptomsTree.setRootVisible(false);
        RemSymptomsTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RemSymptomsTreeMouseClicked(evt);
            }
        });
        RemSymptomsTree.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener() {
            public void treeCollapsed(javax.swing.event.TreeExpansionEvent evt) {
                RemSymptomsTreeTreeCollapsed(evt);
            }
            public void treeExpanded(javax.swing.event.TreeExpansionEvent evt) {
                RemSymptomsTreeTreeExpanded(evt);
            }
        });
        RemSymptomsTree.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                RemSymptomsTreeMouseDragged(evt);
            }
        });
        RemSymptomsTree.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RemSymptomsTreeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                RemSymptomsTreeKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(RemSymptomsTree);

        jToolBar4.setFloatable(false);
        jToolBar4.setOrientation(1);
        jToolBar4.setRollover(true);
        jToolBar4.setName("jToolBar4"); // NOI18N

        DeleteSymptomFromRepertorizationButton.setIcon(resourceMap.getIcon("DeleteSelectedSymptoms.icon")); // NOI18N
        DeleteSymptomFromRepertorizationButton.setText(resourceMap.getString("DeleteSelectedSymptoms.text")); // NOI18N
        DeleteSymptomFromRepertorizationButton.setToolTipText(resourceMap.getString("DeleteSelectedSymptoms.toolTipText")); // NOI18N
        DeleteSymptomFromRepertorizationButton.setFocusable(false);
        DeleteSymptomFromRepertorizationButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DeleteSymptomFromRepertorizationButton.setName("DeleteSelectedSymptoms"); // NOI18N
        DeleteSymptomFromRepertorizationButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(DeleteSymptomFromRepertorizationButton);

        MergeSymptomsButton.setIcon(resourceMap.getIcon("MergeSymptomsButton.icon")); // NOI18N
        MergeSymptomsButton.setText(resourceMap.getString("MergeSymptomsButton.text")); // NOI18N
        MergeSymptomsButton.setToolTipText(resourceMap.getString("MergeSymptomsButton.toolTipText")); // NOI18N
        MergeSymptomsButton.setFocusable(false);
        MergeSymptomsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        MergeSymptomsButton.setName("MergeSymptomsButton"); // NOI18N
        MergeSymptomsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(MergeSymptomsButton);

        jSeparator18.setName("jSeparator18"); // NOI18N
        jToolBar4.add(jSeparator18);

        PositiveFilterButton.setIcon(resourceMap.getIcon("PositiveFilterButton.icon")); // NOI18N
        PositiveFilterButton.setText(resourceMap.getString("PositiveFilterButton.text")); // NOI18N
        PositiveFilterButton.setToolTipText(resourceMap.getString("PositiveFilterButton.toolTipText")); // NOI18N
        PositiveFilterButton.setFocusable(false);
        PositiveFilterButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PositiveFilterButton.setName("PositiveFilterButton"); // NOI18N
        PositiveFilterButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(PositiveFilterButton);

        NegativeFilterButton.setIcon(resourceMap.getIcon("NegativeFilterButton.icon")); // NOI18N
        NegativeFilterButton.setText(resourceMap.getString("NegativeFilterButton.text")); // NOI18N
        NegativeFilterButton.setToolTipText(resourceMap.getString("NegativeFilterButton.toolTipText")); // NOI18N
        NegativeFilterButton.setFocusable(false);
        NegativeFilterButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        NegativeFilterButton.setName("NegativeFilterButton"); // NOI18N
        NegativeFilterButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(NegativeFilterButton);

        jToolBar5.setFloatable(false);
        jToolBar5.setOrientation(1);
        jToolBar5.setRollover(true);
        jToolBar5.setName("jToolBar5"); // NOI18N

        DesktopButton_1.setIcon(resourceMap.getIcon("DesktopButton_1.icon")); // NOI18N
        DesktopButton_1.setSelected(true);
        DesktopButton_1.setText(resourceMap.getString("DesktopButton_1.text")); // NOI18N
        DesktopButton_1.setToolTipText(resourceMap.getString("DesktopButton_1.toolTipText")); // NOI18N
        DesktopButton_1.setFocusable(false);
        DesktopButton_1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DesktopButton_1.setName("DesktopButton_1"); // NOI18N
        DesktopButton_1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar5.add(DesktopButton_1);

        DesktopButton_2.setIcon(resourceMap.getIcon("DesktopButton_2.icon")); // NOI18N
        DesktopButton_2.setText(resourceMap.getString("DesktopButton_2.text")); // NOI18N
        DesktopButton_2.setToolTipText(resourceMap.getString("DesktopButton_2.toolTipText")); // NOI18N
        DesktopButton_2.setFocusable(false);
        DesktopButton_2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DesktopButton_2.setName("DesktopButton_2"); // NOI18N
        DesktopButton_2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar5.add(DesktopButton_2);

        DesktopButton_3.setIcon(resourceMap.getIcon("DesktopButton_3.icon")); // NOI18N
        DesktopButton_3.setText(resourceMap.getString("DesktopButton_3.text")); // NOI18N
        DesktopButton_3.setToolTipText(resourceMap.getString("DesktopButton_3.toolTipText")); // NOI18N
        DesktopButton_3.setFocusable(false);
        DesktopButton_3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DesktopButton_3.setName("DesktopButton_3"); // NOI18N
        DesktopButton_3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar5.add(DesktopButton_3);

        DesktopButton_4.setIcon(resourceMap.getIcon("DesktopButton_4.icon")); // NOI18N
        DesktopButton_4.setText(resourceMap.getString("DesktopButton_4.text")); // NOI18N
        DesktopButton_4.setToolTipText(resourceMap.getString("DesktopButton_4.toolTipText")); // NOI18N
        DesktopButton_4.setFocusable(false);
        DesktopButton_4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DesktopButton_4.setName("DesktopButton_4"); // NOI18N
        DesktopButton_4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar5.add(DesktopButton_4);

        DesktopButton_5.setIcon(resourceMap.getIcon("DesktopButton_5.icon")); // NOI18N
        DesktopButton_5.setText(resourceMap.getString("DesktopButton_5.text")); // NOI18N
        DesktopButton_5.setToolTipText(resourceMap.getString("DesktopButton_5.toolTipText")); // NOI18N
        DesktopButton_5.setFocusable(false);
        DesktopButton_5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        DesktopButton_5.setName("DesktopButton_5"); // NOI18N
        DesktopButton_5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar5.add(DesktopButton_5);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jToolBar5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jToolBar4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jToolBar4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addContainerGap())
            .add(jScrollPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel2);

        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setToolTipText(resourceMap.getString("jButton1.toolTipText")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
                        .add(SearchEdit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jToolBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jToolBar2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, mainPanelLayout.createSequentialGroup()
                        .add(7, 7, 7)
                        .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(SearchEdit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newMenuItem.setIcon(resourceMap.getIcon("newMenuItem.icon")); // NOI18N
        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        fileMenu.add(newMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        openMenuItem.setIcon(resourceMap.getIcon("openMenuItem.icon")); // NOI18N
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setIcon(resourceMap.getIcon("saveMenuItem.icon")); // NOI18N
        saveMenuItem.setText(resourceMap.getString("saveMenuItem.text")); // NOI18N
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        fileMenu.add(saveMenuItem);

        saveasMenuItem.setText(resourceMap.getString("saveasMenuItem.text")); // NOI18N
        saveasMenuItem.setName("saveasMenuItem"); // NOI18N
        fileMenu.add(saveasMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        PreferencesMenuItem.setIcon(resourceMap.getIcon("PreferencesMenuItem.icon")); // NOI18N
        PreferencesMenuItem.setText(resourceMap.getString("PreferencesMenuItem.text")); // NOI18N
        PreferencesMenuItem.setName("PreferencesMenuItem"); // NOI18N
        fileMenu.add(PreferencesMenuItem);

        ConsoleButton.setText(resourceMap.getString("ConsoleButton.text")); // NOI18N
        ConsoleButton.setName("ConsoleButton"); // NOI18N
        fileMenu.add(ConsoleButton);

        jSeparator12.setName("jSeparator12"); // NOI18N
        fileMenu.add(jSeparator12);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setIcon(resourceMap.getIcon("exitMenuItem.icon")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setActionCommand(resourceMap.getString("exitMenuItem.actionCommand")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu4.setText(resourceMap.getString("jMenu4.text")); // NOI18N
        jMenu4.setName("jMenu4"); // NOI18N

        AddSymptomToRepertorizationMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        AddSymptomToRepertorizationMenuItem.setText(resourceMap.getString("AddSymptomToRepertorizationMenuItem.text")); // NOI18N
        AddSymptomToRepertorizationMenuItem.setName("AddSymptomToRepertorizationMenuItem"); // NOI18N
        jMenu4.add(AddSymptomToRepertorizationMenuItem);

        AddMergedSymptomsToRepertorization_MainMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        AddMergedSymptomsToRepertorization_MainMenu.setText(resourceMap.getString("AddMergedSymptomsToRepertorization_MainMenu.text")); // NOI18N
        AddMergedSymptomsToRepertorization_MainMenu.setName("AddMergedSymptomsToRepertorization_MainMenu"); // NOI18N
        jMenu4.add(AddMergedSymptomsToRepertorization_MainMenu);

        DeleteSymptomFromRepertorizationMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        DeleteSymptomFromRepertorizationMenuItem.setText(resourceMap.getString("DeleteSymptomFromRepertorizationMenuItem.text")); // NOI18N
        DeleteSymptomFromRepertorizationMenuItem.setName("DeleteSymptomFromRepertorizationMenuItem"); // NOI18N
        jMenu4.add(DeleteSymptomFromRepertorizationMenuItem);

        jSeparator14.setName("jSeparator14"); // NOI18N
        jMenu4.add(jSeparator14);

        AddSymptomToRepertoryMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        AddSymptomToRepertoryMenuItem.setText(resourceMap.getString("AddSymptomToRepertoryMenuItem.text")); // NOI18N
        AddSymptomToRepertoryMenuItem.setName("AddSymptomToRepertoryMenuItem"); // NOI18N
        jMenu4.add(AddSymptomToRepertoryMenuItem);

        DeleteSymptomFromRepertoryMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        DeleteSymptomFromRepertoryMenuItem.setText(resourceMap.getString("DeleteSymptomFromRepertoryMenuItem.text")); // NOI18N
        DeleteSymptomFromRepertoryMenuItem.setName("DeleteSymptomFromRepertoryMenuItem"); // NOI18N
        jMenu4.add(DeleteSymptomFromRepertoryMenuItem);

        EditSymptomNameMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        EditSymptomNameMenuItem.setText(resourceMap.getString("EditSymptomNameMenuItem.text")); // NOI18N
        EditSymptomNameMenuItem.setName("EditSymptomNameMenuItem"); // NOI18N
        jMenu4.add(EditSymptomNameMenuItem);

        EditRemedyAdditionsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        EditRemedyAdditionsMenuItem.setText(resourceMap.getString("EditRemedyAdditionsMenuItem.text")); // NOI18N
        EditRemedyAdditionsMenuItem.setActionCommand(resourceMap.getString("EditRemedyAdditionsMenuItem.actionCommand")); // NOI18N
        EditRemedyAdditionsMenuItem.setName("EditRemedyAdditionsMenuItem"); // NOI18N
        jMenu4.add(EditRemedyAdditionsMenuItem);

        jSeparator16.setName("jSeparator16"); // NOI18N
        jMenu4.add(jSeparator16);

        EssentialMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.ALT_MASK));
        EssentialMenuItem.setText(resourceMap.getString("EssentialMenuItem.text")); // NOI18N
        EssentialMenuItem.setName("EssentialMenuItem"); // NOI18N
        jMenu4.add(EssentialMenuItem);

        VeryImportantMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.ALT_MASK));
        VeryImportantMenuItem.setText(resourceMap.getString("VeryImportantMenuItem.text")); // NOI18N
        VeryImportantMenuItem.setName("VeryImportantMenuItem"); // NOI18N
        jMenu4.add(VeryImportantMenuItem);

        ImportantMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.ALT_MASK));
        ImportantMenuItem.setText(resourceMap.getString("ImportantMenuItem.text")); // NOI18N
        ImportantMenuItem.setName("ImportantMenuItem"); // NOI18N
        jMenu4.add(ImportantMenuItem);

        AverageMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.ALT_MASK));
        AverageMenuItem.setText(resourceMap.getString("AverageMenuItem.text")); // NOI18N
        AverageMenuItem.setName("AverageMenuItem"); // NOI18N
        jMenu4.add(AverageMenuItem);

        NotImportantMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.ALT_MASK));
        NotImportantMenuItem.setText(resourceMap.getString("NotImportantMenuItem.text")); // NOI18N
        NotImportantMenuItem.setName("NotImportantMenuItem"); // NOI18N
        jMenu4.add(NotImportantMenuItem);

        jSeparator17.setName("jSeparator17"); // NOI18N
        jMenu4.add(jSeparator17);

        MergeSymptomsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        MergeSymptomsMenuItem.setText(resourceMap.getString("MergeSymptomsMenuItem.text")); // NOI18N
        MergeSymptomsMenuItem.setName("MergeSymptomsMenuItem"); // NOI18N
        jMenu4.add(MergeSymptomsMenuItem);

        menuBar.add(jMenu4);

        jMenu3.setText(resourceMap.getString("jMenu3.text")); // NOI18N
        jMenu3.setName("jMenu3"); // NOI18N

        ReversedMateriaMedicaMeduItem.setText(resourceMap.getString("ReversedMateriaMedicaMeduItem.text")); // NOI18N
        ReversedMateriaMedicaMeduItem.setName("ReversedMateriaMedicaMeduItem"); // NOI18N
        jMenu3.add(ReversedMateriaMedicaMeduItem);

        PMS_MenuItem.setText(resourceMap.getString("PMS_MenuItem.text")); // NOI18N
        PMS_MenuItem.setName("PMS_MenuItem"); // NOI18N
        jMenu3.add(PMS_MenuItem);

        jSeparator15.setName("jSeparator15"); // NOI18N
        jMenu3.add(jSeparator15);

        DisplaySymptomReferencesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        DisplaySymptomReferencesMenuItem.setText(resourceMap.getString("DisplaySymptomReferencesMenuItem.text")); // NOI18N
        DisplaySymptomReferencesMenuItem.setName("DisplaySymptomReferencesMenuItem"); // NOI18N
        jMenu3.add(DisplaySymptomReferencesMenuItem);

        DisplayRemedyAdditionsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        DisplayRemedyAdditionsMenuItem.setText(resourceMap.getString("DisplayRemedyAdditionsMenuItem.text")); // NOI18N
        DisplayRemedyAdditionsMenuItem.setName("DisplayRemedyAdditionsMenuItem"); // NOI18N
        jMenu3.add(DisplayRemedyAdditionsMenuItem);

        jMenu5.setText(resourceMap.getString("jMenu5.text")); // NOI18N
        jMenu5.setName("jMenu5"); // NOI18N

        Desktop1MenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        Desktop1MenuItem.setText(resourceMap.getString("Desktop1MenuItem.text")); // NOI18N
        Desktop1MenuItem.setName("Desktop1MenuItem"); // NOI18N
        jMenu5.add(Desktop1MenuItem);

        Desktop2MenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        Desktop2MenuItem.setText(resourceMap.getString("Desktop2MenuItem.text")); // NOI18N
        Desktop2MenuItem.setName("Desktop2MenuItem"); // NOI18N
        jMenu5.add(Desktop2MenuItem);

        Desktop3MenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_MASK));
        Desktop3MenuItem.setText(resourceMap.getString("Desktop3MenuItem.text")); // NOI18N
        Desktop3MenuItem.setName("Desktop3MenuItem"); // NOI18N
        jMenu5.add(Desktop3MenuItem);

        Desktop4MenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_MASK));
        Desktop4MenuItem.setText(resourceMap.getString("Desktop4MenuItem.text")); // NOI18N
        Desktop4MenuItem.setName("Desktop4MenuItem"); // NOI18N
        jMenu5.add(Desktop4MenuItem);

        Desktop5MenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.CTRL_MASK));
        Desktop5MenuItem.setText(resourceMap.getString("Desktop5MenuItem.text")); // NOI18N
        Desktop5MenuItem.setName("Desktop5MenuItem"); // NOI18N
        jMenu5.add(Desktop5MenuItem);

        jMenu3.add(jMenu5);

        jSeparator13.setName("jSeparator13"); // NOI18N
        jMenu3.add(jSeparator13);

        PreviousSearchResultMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_UP, java.awt.event.InputEvent.CTRL_MASK));
        PreviousSearchResultMenuItem.setText(resourceMap.getString("PreviousSearchResultMenuItem.text")); // NOI18N
        PreviousSearchResultMenuItem.setName("PreviousSearchResultMenuItem"); // NOI18N
        jMenu3.add(PreviousSearchResultMenuItem);

        NextSearchMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_DOWN, java.awt.event.InputEvent.CTRL_MASK));
        NextSearchMenuItem.setText(resourceMap.getString("NextSearchMenuItem.text")); // NOI18N
        NextSearchMenuItem.setName("NextSearchMenuItem"); // NOI18N
        jMenu3.add(NextSearchMenuItem);

        RepertoryTreeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_UP, java.awt.event.InputEvent.CTRL_MASK));
        RepertoryTreeMenuItem.setText(resourceMap.getString("RepertoryTreeMenuItem.text")); // NOI18N
        RepertoryTreeMenuItem.setName("RepertoryTreeMenuItem"); // NOI18N
        jMenu3.add(RepertoryTreeMenuItem);

        jSeparator21.setName("jSeparator21"); // NOI18N
        jMenu3.add(jSeparator21);

        DisplayTopRubricMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        DisplayTopRubricMenuItem.setText(resourceMap.getString("DisplayTopRubricMenuItem.text")); // NOI18N
        DisplayTopRubricMenuItem.setName("DisplayTopRubricMenuItem"); // NOI18N
        jMenu3.add(DisplayTopRubricMenuItem);

        DisplayHigherRubricMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.ALT_MASK));
        DisplayHigherRubricMenuItem.setText(resourceMap.getString("DisplayHigherRubricMenuItem.text")); // NOI18N
        DisplayHigherRubricMenuItem.setName("DisplayHigherRubricMenuItem"); // NOI18N
        jMenu3.add(DisplayHigherRubricMenuItem);

        menuBar.add(jMenu3);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        importRepertoryMenuItem.setText(resourceMap.getString("importRepertoryMenuItem.text")); // NOI18N
        importRepertoryMenuItem.setName("importRepertoryMenuItem"); // NOI18N
        jMenu1.add(importRepertoryMenuItem);

        removeRepertoryMenuItem.setText(resourceMap.getString("removeRepertoryMenuItem.text")); // NOI18N
        removeRepertoryMenuItem.setName("removeRepertoryMenuItem"); // NOI18N
        jMenu1.add(removeRepertoryMenuItem);

        jSeparator6.setName("jSeparator6"); // NOI18N
        jMenu1.add(jSeparator6);

        openRepertoryMenuItem.setText(resourceMap.getString("openRepertoryMenuItem.text")); // NOI18N
        openRepertoryMenuItem.setName("openRepertoryMenuItem"); // NOI18N
        jMenu1.add(openRepertoryMenuItem);

        jSeparator8.setName("jSeparator8"); // NOI18N
        jMenu1.add(jSeparator8);

        NewRepertoryButton.setText(resourceMap.getString("NewRepertoryButton.text")); // NOI18N
        NewRepertoryButton.setName("NewRepertoryButton"); // NOI18N
        jMenu1.add(NewRepertoryButton);

        SaveRepertoryMenuItem.setText(resourceMap.getString("SaveRepertoryMenuItem.text")); // NOI18N
        SaveRepertoryMenuItem.setName("SaveRepertoryMenuItem"); // NOI18N
        jMenu1.add(SaveRepertoryMenuItem);

        SaveRepertoryAsMenuItem.setText(resourceMap.getString("SaveRepertoryAsMenuItem.text")); // NOI18N
        SaveRepertoryAsMenuItem.setName("SaveRepertoryAsMenuItem"); // NOI18N
        jMenu1.add(SaveRepertoryAsMenuItem);

        jSeparator10.setName("jSeparator10"); // NOI18N
        jMenu1.add(jSeparator10);

        RepertoryPropertiesButton.setText(resourceMap.getString("RepertoryPropertiesButton.text")); // NOI18N
        RepertoryPropertiesButton.setActionCommand("Repertoriumeinstellungen");
        RepertoryPropertiesButton.setName("RepertoryPropertiesButton"); // NOI18N
        jMenu1.add(RepertoryPropertiesButton);

        jSeparator11.setName("jSeparator11"); // NOI18N
        jMenu1.add(jSeparator11);

        ExportRepertoryChangesMenuItem.setText(resourceMap.getString("ExportRepertoryChangesMenuItem.text")); // NOI18N
        ExportRepertoryChangesMenuItem.setName("ExportRepertoryChangesMenuItem"); // NOI18N
        jMenu1.add(ExportRepertoryChangesMenuItem);

        menuBar.add(jMenu1);

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        RepertorizeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        RepertorizeMenuItem.setText(resourceMap.getString("RepertorizeMenuItem.text")); // NOI18N
        RepertorizeMenuItem.setName("RepertorizeMenuItem"); // NOI18N
        jMenu2.add(RepertorizeMenuItem);

        DisplayFullResults.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, java.awt.event.InputEvent.CTRL_MASK));
        DisplayFullResults.setText(resourceMap.getString("DisplayFullResults.text")); // NOI18N
        DisplayFullResults.setActionCommand(resourceMap.getString("DisplayFullResults.actionCommand")); // NOI18N
        DisplayFullResults.setName("DisplayFullResults"); // NOI18N
        jMenu2.add(DisplayFullResults);

        jSeparator4.setName("jSeparator4"); // NOI18N
        jMenu2.add(jSeparator4);

        menuBar.add(jMenu2);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(prescriber.PrescriberApp.class).getContext().getActionMap(PrescriberView.class, this);
        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setToolTipText(resourceMap.getString("aboutMenuItem.toolTipText")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N
        statusPanel.setPreferredSize(new java.awt.Dimension(858, 28));

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        SymptomCntLabel.setFont(resourceMap.getFont("SymptomCntLabel.font")); // NOI18N
        SymptomCntLabel.setForeground(resourceMap.getColor("SymptomCntLabel.foreground")); // NOI18N
        SymptomCntLabel.setText(resourceMap.getString("SymptomCntLabel.text")); // NOI18N
        SymptomCntLabel.setName("SymptomCntLabel"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(statusAnimationLabel)
                    .add(statusPanelLayout.createSequentialGroup()
                        .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, statusPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .add(statusMessageLabel)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 555, Short.MAX_VALUE))
                            .add(statusPanelLayout.createSequentialGroup()
                                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                                .add(64, 64, 64)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(SymptomCntLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 20, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel))
                .add(6, 6, 6))
            .add(statusPanelLayout.createSequentialGroup()
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(statusPanelLayout.createSequentialGroup()
                .add(SymptomCntLabel)
                .addContainerGap())
        );

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        STree.setModel(null);
        STree.setMinimumSize(new java.awt.Dimension(63, 57));
        STree.setName("STree"); // NOI18N
        STree.setRootVisible(false);
        STree.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                STreeTreeWillExpand(evt);
            }
        });
        STree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                STreeMouseClicked(evt);
            }
        });
        STree.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener() {
            public void treeCollapsed(javax.swing.event.TreeExpansionEvent evt) {
            }
            public void treeExpanded(javax.swing.event.TreeExpansionEvent evt) {
                STreeTreeExpanded(evt);
            }
        });
        STree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                STreeValueChanged(evt);
            }
        });
        STree.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                STreeKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(STree);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

private void STreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_STreeMouseClicked
}//GEN-LAST:event_STreeMouseClicked

private void STreeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_STreeKeyReleased

}//GEN-LAST:event_STreeKeyReleased

private void STreeTreeExpanded(javax.swing.event.TreeExpansionEvent evt) {//GEN-FIRST:event_STreeTreeExpanded

}//GEN-LAST:event_STreeTreeExpanded

private void STreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_STreeValueChanged

}//GEN-LAST:event_STreeValueChanged

private void SearchEditKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchEditKeyReleased
    if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        if (SearchEdit.getText().trim().equals("")) {
            UpdateSymptomTree(null, null, false, (short)-1);
        }
        else
        {
            UpdateSymptomTreeThread ust = new UpdateSymptomTreeThread();
            ust.key_words = SearchEdit.getText().trim();
            ust.symptom_ids = null;
            ust.start();
        }
    }
}//GEN-LAST:event_SearchEditKeyReleased

private void STreeTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {//GEN-FIRST:event_STreeTreeWillExpand

}//GEN-LAST:event_STreeTreeWillExpand

private void SymptomTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SymptomTreeMouseClicked
     if (evt.getButton() != java.awt.event.MouseEvent.BUTTON1) {         
         String temp = ConvertTreePathToSymptomName(SymptomTree.getPathForLocation(evt.getX(), evt.getY()), true);
         boolean fnd = false;
         for (int x = 0; x < SymptomTree.getSelectionCount(); x++) {
             String sym = ConvertTreePathToSymptomName(SymptomTree.getSelectionPaths()[x], true);
             if (sym.equals(temp)) {
                 fnd = true;
                 break;
             }
         }
         if (!fnd) {
             SymptomTree.clearSelection();
             SymptomTree.setSelectionPath(SymptomTree.getPathForLocation(evt.getX(), evt.getY()));
         }
         CreateSymptomTreePopupMenu();
         PopupMenu_SymptomTree.setVisible(false);
         Point p = new Point(evt.getX(), evt.getY());
         PopupMenu_SymptomTree.show(SymptomTree, evt.getX(), evt.getY());
     }
     else
     PopupMenu_SymptomTree.setVisible(false);
}//GEN-LAST:event_SymptomTreeMouseClicked

private void SymptomTreeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SymptomTreeKeyReleased
    if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) AddSelectedItemsToRemSymptomTree(null, -1);
    if (process_ctrlc_key) {
        process_ctrlc_key = false;
        ArrayList<SelectedSymptomItem> selected_symptoms = new ArrayList();
        if (SymptomTree.getSelectionCount() != 0) {
            for (int x = 0;x < SymptomTree.getSelectionCount(); x++) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)SymptomTree.getSelectionPaths()[x].getLastPathComponent();
                Symptom xs = (Symptom)node.getUserObject();
                                            
                ArrayList<SelectedSymptomItem> result = GetSelectedSymptomsForListDialog(xs);
                for (int y = 0; y < result.size(); y++)
                    selected_symptoms.add(result.get(y));
            }
        }        
        CopySymptomsToClipboard(selected_symptoms);        
    }
}//GEN-LAST:event_SymptomTreeKeyReleased

private void SymptomTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_SymptomTreeValueChanged
    TreePath[] tp = SymptomTree.getSelectionPaths();    
    String temps = ""; 
    if (tp == null) return;
    for (int x = 0; x < tp.length; x++) {                
        temps = ConvertTreePathToSymptomName(tp[x], true);
    }
    // sets the current selected_sym
    DefaultMutableTreeNode dtn = (DefaultMutableTreeNode)SymptomTree.getSelectionPath().getLastPathComponent();

    selected_sym = "Results, " + ((Symptom)dtn.getUserObject()).SymName;

    if (tp.length == 0) statusMessageLabel.setText(GetRepertoryStatusMessageLabel());
    else
    if (tp.length == 1) statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+ temps);
    else
    statusMessageLabel.setText(GetRepertoryStatusMessageLabel()+tp.length+" symptoms selected");
    // there is an ACTIVE dialog selected, so display the symptom contents
    if (active_dialog != null) {
       try{
            PrescriberApp.getApplication().getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (SymptomTree.getSelectionCount() == 0) {
                active_dialog.HeaderLabel.setText("Multiple symptoms selected...");
                active_dialog.SetContents("");
                return;
            }
            String symptom_name = ConvertTreePathToSymptomName(SymptomTree.getSelectionPath(), true);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)SymptomTree.getSelectionPath().getLastPathComponent();
            Symptom xs = (Symptom)node.getUserObject();

            ArrayList<SelectedSymptomItem> temp = GetSelectedSymptomsForListDialog(xs);
            
            active_dialog.HeaderLabel.setText(symptom_name);
            String tempx = Utils.GetRTFSymptoms(temp, false, active_dialog.GetRemedyGrade(), !active_dialog.GetSortType())[0];
            active_dialog.SetContents(tempx);
       }
       finally {
            PrescriberApp.getApplication().getMainFrame().setCursor(Cursor.getDefaultCursor());  
       }               
    }
}//GEN-LAST:event_SymptomTreeValueChanged

private void SymptomTreeTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {//GEN-FIRST:event_SymptomTreeTreeWillExpand
    if (threaded_symptom_tree_expand) {
        WorkThread wt = new WorkThread();
        wt.evt = evt;
        wt.start();
    } else
    ExpandSymptomTree(evt);
}//GEN-LAST:event_SymptomTreeTreeWillExpand

private void RemSymptomsTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemSymptomsTreeMouseClicked
     if (evt.getButton() != java.awt.event.MouseEvent.BUTTON1) {
         String temp = ConvertTreePathToSymptomName(RemSymptomsTree.getPathForLocation(evt.getX(), evt.getY()), true);
         if (temp == null) return;
         boolean fnd = false;
         for (int x = 0; x < RemSymptomsTree.getSelectionCount(); x++) {
             String sym = ConvertTreePathToSymptomName(RemSymptomsTree.getSelectionPaths()[x], true);
             if (sym.equals(temp)) {
                 fnd = true;
                 break;
             }
         }
         if (!fnd) {
             RemSymptomsTree.clearSelection();
             RemSymptomsTree.setSelectionPath(RemSymptomsTree.getPathForLocation(evt.getX(), evt.getY()));
         }
         PopupMenu_RemSymptomTree.setVisible(false);
         Point p = new Point(evt.getX(), evt.getY());        
         PopupMenu_RemSymptomTree.setLocation(RemSymptomsTree.getLocationOnScreen().x + evt.getX(), RemSymptomsTree.getLocationOnScreen().y + evt.getY());
         PopupMenu_RemSymptomTree.setFocusable(true);
         PopupMenu_RemSymptomTree.show(RemSymptomsTree, evt.getX(), evt.getY());
     }
     else
     PopupMenu_RemSymptomTree.setVisible(false);
}//GEN-LAST:event_RemSymptomsTreeMouseClicked

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (SearchEdit.getText().trim().equals("")) {
            UpdateSymptomTree(null, null, false, (short)-1);
        }
        else
        {
            UpdateSymptomTree(SearchEdit.getText().trim(), null, true, (short)-1);
        }
}//GEN-LAST:event_jButton1ActionPerformed

private void EditRemedyAdditionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditRemedyAdditionsButtonActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_EditRemedyAdditionsButtonActionPerformed

private void RemSymptomsTreeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RemSymptomsTreeKeyReleased
   // if delete is pressed, delete the selected symptoms from the repertorization
   if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
       DeleteSelectedSymptomsFromRepertorization();
       UpdateRemedySymptomsTree();
   }
   if (process_ctrlc_key) {
        process_ctrlc_key = false;
        ArrayList<SelectedSymptomItem> selected_symptoms = new ArrayList();
        if (RemSymptomsTree.getSelectionCount() != 0) {
            for (int x = 0;x < RemSymptomsTree.getSelectionCount(); x++) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)RemSymptomsTree.getSelectionPaths()[x].getLastPathComponent();
                selected_symptoms.add((SelectedSymptomItem)node.getUserObject());
            }
        }        
        CopySymptomsToClipboard(selected_symptoms);        
   }
}//GEN-LAST:event_RemSymptomsTreeKeyReleased

private void RemSymptomsTreeTreeExpanded(javax.swing.event.TreeExpansionEvent evt) {//GEN-FIRST:event_RemSymptomsTreeTreeExpanded
   
}//GEN-LAST:event_RemSymptomsTreeTreeExpanded

private void RemSymptomsTreeTreeCollapsed(javax.swing.event.TreeExpansionEvent evt) {//GEN-FIRST:event_RemSymptomsTreeTreeCollapsed
   
}//GEN-LAST:event_RemSymptomsTreeTreeCollapsed

private void RemSymptomsTreeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RemSymptomsTreeKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_C && evt.isControlDown()) {
        process_ctrlc_key = true;
        return;
    }
    if (evt.isAltDown()) {
        if (evt.getKeyCode() == evt.VK_UP) {
            Utils.ChangeFont(RemSymptomsTree, 1);
            config.SetValue(Configuration.Key_PrescriberView_RemSymptomTree, RemSymptomsTree.getFont().getSize());
        }
        else
        if (evt.getKeyCode() == evt.VK_DOWN) {
            Utils.ChangeFont(RemSymptomsTree, -1);
            config.SetValue(Configuration.Key_PrescriberView_RemSymptomTree, RemSymptomsTree.getFont().getSize());
        }
    }
}//GEN-LAST:event_RemSymptomsTreeKeyPressed

private void SymptomTreeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SymptomTreeKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_C && evt.isControlDown()) {
        process_ctrlc_key = true;
        return;
    }
    if (evt.isAltDown()) {
        if (evt.getKeyCode() == evt.VK_UP) {
            Utils.ChangeFont(SymptomTree, 1);
            config.SetValue(Configuration.Key_PrescriberView_SymptomTree, SymptomTree.getFont().getSize());
        }
        else
        if (evt.getKeyCode() == evt.VK_DOWN) {
            Utils.ChangeFont(SymptomTree, -1);
            config.SetValue(Configuration.Key_PrescriberView_SymptomTree, SymptomTree.getFont().getSize());
        }
    }
}//GEN-LAST:event_SymptomTreeKeyPressed

private void RemSymptomsTreeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemSymptomsTreeMouseDragged
    drag_source = DRAG_SOURCE_REMSYMPTOMTREE;
    drag_remsymptom_ctrl = evt.isControlDown();
}//GEN-LAST:event_RemSymptomsTreeMouseDragged

private void SymptomTreeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SymptomTreeMouseDragged
    drag_source = DRAG_SOURCE_SYMPTOMTREE;
    drag_remsymptom_ctrl = evt.isControlDown();
}//GEN-LAST:event_SymptomTreeMouseDragged

private void RemSymptomTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RemSymptomTableKeyPressed
      if (evt.isAltDown()) {
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            Utils.ChangeFont(RemSymptomTable, 1);
            RemSymptomTable.setRowHeight(RemSymptomTable.getFontMetrics(RemSymptomTable.getFont()).getHeight());
            config.SetValue(Configuration.Key_PrescriberView_RemSymptomTable, RemSymptomTable.getFont().getSize());
        }
        else
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            Utils.ChangeFont(RemSymptomTable, -1);
            RemSymptomTable.setRowHeight(RemSymptomTable.getFontMetrics(RemSymptomTable.getFont()).getHeight());
            config.SetValue(Configuration.Key_PrescriberView_RemSymptomTable, RemSymptomTable.getFont().getSize());
        }
     }
     else
     {
        System.out.println(RemSymptomTable.getSelectedRow());
     }
}//GEN-LAST:event_RemSymptomTableKeyPressed

private void RemSymptomTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemSymptomTableMouseClicked
    
}//GEN-LAST:event_RemSymptomTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddMergedSymptomsButton;
    private javax.swing.JMenuItem AddMergedSymptomsToRepertorization_MainMenu;
    private javax.swing.JButton AddNewSymptomButton;
    private javax.swing.JButton AddSymptomToRepertorizationButton;
    private javax.swing.JMenuItem AddSymptomToRepertorizationMenuItem;
    private javax.swing.JMenuItem AddSymptomToRepertoryMenuItem;
    private javax.swing.JMenuItem AverageMenuItem;
    private javax.swing.JMenuItem ConsoleButton;
    private javax.swing.JButton DeleteSymptomButton;
    private javax.swing.JButton DeleteSymptomFromRepertorizationButton;
    private javax.swing.JMenuItem DeleteSymptomFromRepertorizationMenuItem;
    private javax.swing.JMenuItem DeleteSymptomFromRepertoryMenuItem;
    private javax.swing.JMenuItem Desktop1MenuItem;
    private javax.swing.JMenuItem Desktop2MenuItem;
    private javax.swing.JMenuItem Desktop3MenuItem;
    private javax.swing.JMenuItem Desktop4MenuItem;
    private javax.swing.JMenuItem Desktop5MenuItem;
    private javax.swing.JToggleButton DesktopButton_1;
    private javax.swing.JToggleButton DesktopButton_2;
    private javax.swing.JToggleButton DesktopButton_3;
    private javax.swing.JToggleButton DesktopButton_4;
    private javax.swing.JToggleButton DesktopButton_5;
    private javax.swing.JMenuItem DisplayFullResults;
    private javax.swing.JMenuItem DisplayHigherRubricMenuItem;
    private javax.swing.JMenuItem DisplayRemedyAdditionsMenuItem;
    private javax.swing.JMenuItem DisplaySymptomReferencesMenuItem;
    private javax.swing.JMenuItem DisplayTopRubricMenuItem;
    private javax.swing.JButton EditRemedyAdditionsButton;
    private javax.swing.JMenuItem EditRemedyAdditionsMenuItem;
    private javax.swing.JButton EditSymptomButton;
    private javax.swing.JMenuItem EditSymptomNameMenuItem;
    private javax.swing.JMenuItem EssentialMenuItem;
    private javax.swing.JMenuItem ExportRepertoryChangesMenuItem;
    private javax.swing.JMenuItem ImportantMenuItem;
    private javax.swing.JButton MergeSymptomsButton;
    private javax.swing.JMenuItem MergeSymptomsMenuItem;
    private javax.swing.JButton NegativeFilterButton;
    private javax.swing.JMenuItem NewRepertoryButton;
    private javax.swing.JButton NewSessionButton;
    private javax.swing.JButton NextSearchButton;
    private javax.swing.JMenuItem NextSearchMenuItem;
    private javax.swing.JMenuItem NotImportantMenuItem;
    private javax.swing.JButton OpenRepertoryButton;
    private javax.swing.JButton OpenSessionButton;
    private javax.swing.JButton PMSButton;
    private javax.swing.JMenuItem PMS_MenuItem;
    private javax.swing.JButton PositiveFilterButton;
    private javax.swing.JMenuItem PreferencesMenuItem;
    private javax.swing.JButton PrevSearchButton;
    private javax.swing.JMenuItem PreviousSearchResultMenuItem;
    private javax.swing.JTable RemSymptomTable;
    private javax.swing.JTree RemSymptomsTree;
    private javax.swing.JButton RepertorizationResultsButton;
    private javax.swing.JButton RepertorizeButton;
    private javax.swing.JMenuItem RepertorizeMenuItem;
    private javax.swing.JMenuItem RepertoryPropertiesButton;
    private javax.swing.JButton RepertoryTreeButton;
    private javax.swing.JMenuItem RepertoryTreeMenuItem;
    private javax.swing.JButton ReversedMMButton;
    private javax.swing.JMenuItem ReversedMateriaMedicaMeduItem;
    private javax.swing.JTree STree;
    private javax.swing.JMenuItem SaveRepertoryAsMenuItem;
    private javax.swing.JMenuItem SaveRepertoryMenuItem;
    private javax.swing.JButton SaveSessionButton;
    private javax.swing.JTextField SearchEdit;
    private javax.swing.JLabel SymptomCntLabel;
    private javax.swing.JTree SymptomTree;
    private javax.swing.JMenuItem VeryImportantMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem importRepertoryMenuItem;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JToolBar.Separator jSeparator18;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem openRepertoryMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem removeRepertoryMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem saveasMenuItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;

    private JDialog aboutBox;
    }
