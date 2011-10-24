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

/* This dialog is used to display the list of remedies in symptom and the list of cross-references to the symptom.
 * The main functionality is the JEditorPane which displays the RTF formatted thext that is created in the Utils.
 * 
 *
 * Created on August 29, 2008, 11:00 AM
 */

package prescriber;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author  vladimir
 */
public class ListDialog extends javax.swing.JDialog {
    /** window displaying symptom additions */
    public static int Window_Type_Normal = 0;
    /** window containing cross-references */
    public static int Window_Type_SymptomReferences = 1;
    /** default type of the window*/
    public int window_type = Window_Type_Normal;
    /** instancce of the main application's window*/
    public PrescriberView MainEditor;
    /** contains the current editor pane that is displaying the content  */
    private JEditorPane current_pane;

    /** contains the minimum remedy grade that will be displayed */
    private int remedy_grade = -1;
    
    /** contains the name of the current symptom */
    private SelectedSymptomItem current_symptom;
    /** contains all the displayed symptoms (in case of displaying symptom references) */
    private ArrayList<SelectedSymptomItem> all_symptoms;
    
    /** Returns the remedy grade filter
     * 
     * @return
     */
    public int GetRemedyGrade() {
        return this.remedy_grade;
    }
    
    /** Returns the sort type (selected type of the sort button)
     * 
     * @return
     */
    public boolean GetSortType() {
        return SortByNameButton.isSelected();
    }
    
    /** Creates new form ListDialog */
    public ListDialog(java.awt.Frame parent, boolean modal, SelectedSymptomItem ssi, PrescriberView editor, ArrayList<SelectedSymptomItem> all_syms, int window_type) {
        super(parent, modal);
        initComponents();
        current_symptom = ssi.DeepCopy();
        this.MainEditor = editor;
        all_symptoms = new ArrayList();
        this.window_type = window_type;
        for (int x = 0; x < all_syms.size(); x++) {
            all_symptoms.add(all_syms.get(x).DeepCopy());
        }
    }

    KeyListener kl = new KeyListener() {

        //@Override
        public void keyTyped(KeyEvent e) {
            
        }

        //@Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
                 CopyToClipboard();
            }

            if (e.isAltDown()) {
                if (e.getKeyCode() == e.VK_UP) {
                    Utils.ChangeFont(current_pane, 1);
                    MainEditor.config.SetValue(Configuration.Key_ListDialog_CurrentPane, current_pane.getFont().getSize());
                }
                else
                if (e.getKeyCode() == e.VK_DOWN) {
                    Utils.ChangeFont(current_pane, -1);
                    MainEditor.config.SetValue(Configuration.Key_ListDialog_CurrentPane, current_pane.getFont().getSize());
                }
            }

        }

        //@Override
        public void keyReleased(KeyEvent e) {
            
        }
    };

    /** Sets the data according to the parameters
     *
     * @param data
     */
    public void SetContents(String data) {
        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);
        jep.addKeyListener(kl);
        Utils.SetEditorProperties(jep, data);
        this.current_pane = jep;
        JScrollPane jsp = new JScrollPane(jep);
        jSplitPane1.setBottomComponent(jsp);
        jep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jsp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jep.setCaretPosition(0);

        int size = MainEditor.config.GetValue(Configuration.Key_ListDialog_CurrentPane);
        Font ft;
        if (size != -1) {
            ft = new Font(current_pane.getFont().getName(), current_pane.getFont().getStyle(), size);
            current_pane.setFont(ft);
        }

    }

    /** Copies the current contents to clipboard
     *  
     */
    public void CopyToClipboard() {
        try {
            ArrayList<SelectedSymptomItem> temp = new ArrayList();
            temp.add(current_symptom);         
            Utils.CopyToRtf(Utils.GetRTFSymptoms(temp, true, remedy_grade, !SortByNameButton.isSelected())[0], Utils.GetRTFSymptoms(temp, true, remedy_grade, !SortByNameButton.isSelected())[2]);
            JOptionPane.showMessageDialog(rootPane, "Die Inhalte wurden in die Zwischenablage kopiert");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Es gab einen Fehler beim Kopieren in die Zwischenablage");
        }        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        GradeButton = new javax.swing.JButton();
        SortByNameButton = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        ActiveButton = new javax.swing.JToggleButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        HeaderLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jSeparator1.setName("jSeparator1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(1);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(prescriber.PrescriberApp.class).getContext().getResourceMap(ListDialog.class);
        GradeButton.setIcon(resourceMap.getIcon("GradeButton.icon")); // NOI18N
        GradeButton.setText(resourceMap.getString("GradeButton.text")); // NOI18N
        GradeButton.setToolTipText(resourceMap.getString("GradeButton.toolTipText")); // NOI18N
        GradeButton.setFocusable(false);
        GradeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        GradeButton.setName("GradeButton"); // NOI18N
        GradeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        GradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GradeButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(GradeButton);

        SortByNameButton.setIcon(resourceMap.getIcon("SortByNameButton.icon")); // NOI18N
        SortByNameButton.setText(resourceMap.getString("SortByNameButton.text")); // NOI18N
        SortByNameButton.setToolTipText(resourceMap.getString("SortByNameButton.toolTipText")); // NOI18N
        SortByNameButton.setFocusable(false);
        SortByNameButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SortByNameButton.setName("SortByNameButton"); // NOI18N
        SortByNameButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        SortByNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SortByNameButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(SortByNameButton);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jToolBar1.add(jSeparator2);

        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setToolTipText(resourceMap.getString("jButton1.toolTipText")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jToolBar1.add(jSeparator3);

        ActiveButton.setIcon(resourceMap.getIcon("ActiveButton.icon")); // NOI18N
        ActiveButton.setText(resourceMap.getString("ActiveButton.text")); // NOI18N
        ActiveButton.setToolTipText(resourceMap.getString("ActiveButton.toolTipText")); // NOI18N
        ActiveButton.setFocusable(false);
        ActiveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ActiveButton.setName("ActiveButton"); // NOI18N
        ActiveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ActiveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(ActiveButton);

        jSplitPane1.setDividerLocation(0);
        jSplitPane1.setEnabled(false);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 150, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel2);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
        );

        HeaderLabel.setFont(resourceMap.getFont("HeaderLabel.font")); // NOI18N
        HeaderLabel.setText(resourceMap.getString("HeaderLabel.text")); // NOI18N
        HeaderLabel.setMinimumSize(new java.awt.Dimension(10, 17));
        HeaderLabel.setName("HeaderLabel"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(HeaderLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(HeaderLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void GradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GradeButtonActionPerformed
   Integer[] sel_values = new Integer[current_symptom.maximum_grade];
   for (int x = 0; x < sel_values.length; x++)
       sel_values[x] = x+1;
   try{
        this.setAlwaysOnTop(false);
        remedy_grade = (Integer)JOptionPane.showInputDialog(null, "Bitte wählen Sie die Arzneiwertigkeit.", "", javax.swing.JOptionPane.INFORMATION_MESSAGE, null, sel_values, remedy_grade);
   }
   catch (Exception e) {
       return;
   }
   finally {
       this.setAlwaysOnTop(true);
   }

   boolean display_symptom_names = false;

   if (this.window_type == Window_Type_SymptomReferences) display_symptom_names = true;

   String temps = Utils.GetRTFSymptoms(all_symptoms, display_symptom_names, remedy_grade, !SortByNameButton.isSelected())[0];

   SetContents(temps);

}//GEN-LAST:event_GradeButtonActionPerformed

private void SortByNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SortByNameButtonActionPerformed
   try {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        boolean display_symptom_names = false;

        if (this.window_type == Window_Type_SymptomReferences) display_symptom_names = true;

        String temps = Utils.GetRTFSymptoms(all_symptoms, display_symptom_names, remedy_grade, !SortByNameButton.isSelected())[0];

        SetContents(temps);
   }
   finally {
       this.setCursor(Cursor.getDefaultCursor());
   }
}//GEN-LAST:event_SortByNameButtonActionPerformed

private void ActiveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveButtonActionPerformed
   if (!ActiveButton.isSelected()) MainEditor.active_dialog = null;
   else
   {
       if (MainEditor.active_dialog != null) {
           JOptionPane.showMessageDialog(rootPane, "Es ist bereits ein Dialog auf AKTIV gesetzt. Bitte schließen Sie diesen zunächst.");
           ActiveButton.setSelected(false);
           return;
       }
        MainEditor.active_dialog = this;
   }
}//GEN-LAST:event_ActiveButtonActionPerformed

private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
   if (MainEditor.active_dialog == this) MainEditor.active_dialog = null;
}//GEN-LAST:event_formWindowClosed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
   MainEditor.SetListDialogPosition(this);
}//GEN-LAST:event_formWindowClosing

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    CopyToClipboard();
}//GEN-LAST:event_jButton1ActionPerformed

private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_C && evt.isControlDown()) {
        CopyToClipboard();
    }
}//GEN-LAST:event_formKeyPressed

private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
    
}//GEN-LAST:event_formKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton ActiveButton;
    private javax.swing.JButton GradeButton;
    public javax.swing.JLabel HeaderLabel;
    private javax.swing.JToggleButton SortByNameButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

}
