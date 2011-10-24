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

/* Database contains all the data saved in repertories and all the repertories are accessed throught this class.
 */

package prescriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/** Database of repertories
 * 
 * @author vladimir
 */
public class Database {
        
    /** used in symptom combinations to determine, that this symptom has to be present in this combination */
    public final static byte SYMPTOM_TYPE_PRESENT = 0;
    /** used in symptom combinations to determine, that this symptom cannot be present in this symptom */
    public final static byte SYMPTOM_TYPE_NOT_PRESENT = 1;    
    /** this symptom is required - any of the symptoms marked as TRIGGER_ONE will target the combination */
    public final static byte SYMPTOM_TYPE_PRESENT_TRIGGER_ONE = 2;
    /** this symptom is required - only all of the symptoms marked as TRIGGER_ALL will target the combination */    
    public final static byte SYMPTOM_TYPE_PRESENT_TRIGGER_ALL = 3;
    /** specified by how much should the combined symptom be boosted */
    public final static byte COMBINEDSYMPTOM_VALUE = 50;
    /** specified by how much should the combined symptom be boosted */    
    public final static byte SYMPTOM_GRADE_ESSENTIAL = 10;
    /** specified by how much should the combined symptom be boosted */    
    public final static byte SYMPTOM_GRADE_VERY_IMPORTANT = 8;
    /** specified by how much should the combined symptom be boosted */    
    public final static byte SYMPTOM_GRADE_IMPORTANT = 6;
    /** specified by how much should the combined symptom be boosted */    
    public final static byte SYMPTOM_GRADE_AVERAGE = 3;
    /** specified by how much should the combined symptom be boosted */    
    public final static byte SYMPTOM_GRADE_NON_IMPORTANT = 1;
    /** contains the name of the repertory file which contains the list of all imported repertories*/
    private String repertory_file = "";

    /** list of all synonyms */
    private ArrayList<Synonym> synonyms = new ArrayList();
    
    /** list containing all the imported repertories */
    private ArrayList<Repertory> repertories = new ArrayList();    
    
    /** index in the repertories ArrayList that points to the currently opened repertory*/
    private int current_repertory = 0;
    
    /** name of the synonyms file */
    private String synonyms_file = "synonyms";
    // tags used in the repertory file and repertory descriptor file
    public static final String REPERTORY_FILE_REPERTORY_TAG_START = "<repertory>";
    
    public static final String PATIENT_FILE_PATIENT_TAG_START = "<patient>";
    public static final String PATIENT_FILE_PATIENT_TAG_END = "</patient>";
    public static final String PATIENT_FILE_NAME_TAG_START = "<name>";
    public static final String PATIENT_FILE_NAME_TAG_END = "</name>";    
    public static final String PATIENT_FILE_SURNAME_TAG_START = "<surname>";
    public static final String PATIENT_FILE_SURNAME_TAG_END = "</surname>";    
    public static final String PATIENT_FILE_EMAIL_TAG_START = "<email>";
    public static final String PATIENT_FILE_EMAIL_TAG_END = "</email>";    
    public static final String PATIENT_FILE_TELEPHONE_TAG_START = "<telephone>";
    public static final String PATIENT_FILE_TELEPHONE_TAG_END = "</telephone>";        
    public static final String PATIENT_FILE_ADDRESS_TAG_START = "<address>";
    public static final String PATIENT_FILE_ADDRESS_TAG_END = "</address>";
    public static final String PATIENT_FILE_SEX_TAG_START = "<sex>";
    public static final String PATIENT_FILE_SEX_TAG_END = "</sex>";
    public static final String PATIENT_FILE_BIRTH_TAG_START = "<date_of_birth>";
    public static final String PATIENT_FILE_BIRTH_TAG_END = "</date_of_birth>";
    public static final String PATIENT_FILE_COMMENT_TAG_START = "<comment>";
    public static final String PATIENT_FILE_COMMENT_TAG_END = "</comment>";    
    public static final String PATIENT_FILE_ADDITIONALINFORMATION_TAG_START = "<additional_information>";
    public static final String PATIENT_FILE_ADDITIONALINFORMATION_TAG_END = "</additional_information>";
    public static final String PATIENT_FILE_ID_TAG_START = "<unique_id>";
    public static final String PATIENT_FILE_ID_TAG_END = "</unique_id>";
    
    public static final String PATIENTDATA_FILE_DATA_TAG_START = "<diagnosis>";
    public static final String PATIENTDATA_FILE_DATA_TAG_END = "</diagnosis>";
    public static final String PATIENTDATA_FILE_DATE_TAG_START = "<date>";
    public static final String PATIENTDATA_FILE_DATE_TAG_END = "</date>";
    public static final String PATIENTDATA_FILE_ID_TAG_START = "<id>";
    public static final String PATIENTDATA_FILE_ID_TAG_END = "</id>";    
    public static final String PATIENTDATA_FILE_SHORTDESCRIPTION_TAG_START = "<short_description>";
    public static final String PATIENTDATA_FILE_SHORTDESCRIPTION_TAG_END = "</short_description>";
    public static final String PATIENTDATA_FILE_DESCRIPTION_TAG_START = "<description>";
    public static final String PATIENTDATA_FILE_DESCRIPTION_TAG_END = "</description>";
    public static final String PATIENTDATA_FILE_PRESCRIPTIONS_TAG_START = "<prescriptions>";
    public static final String PATIENTDATA_FILE_PRESCRIPTIONS_TAG_END = "</prescriptions>";    
    public static final String PATIENTDATA_FILE_REMEDY_TAG_START = "<remedy>";
    public static final String PATIENTDATA_FILE_REMEDY_TAG_END = "</remedy>";    
    public static final String PATIENTDATA_FILE_DOSAGE_TAG_START = "<dosage>";
    public static final String PATIENTDATA_FILE_DOSAGE_TAG_END = "</dosage>";        
    public static final String PATIENTDATA_FILE_APPENDICES_TAG_START = "<appendices>";
    public static final String PATIENTDATA_FILE_APPENDICES_TAG_END = "</appendices>";
    public static final String PATIENTDATA_APPENDIX_TAG_START = "<appendix>";
    public static final String PATIENTDATA_APPENDIX_TAG_END = "</appendix>";    
    public static final String PATIENTDATA_APPENDIXNAME_TAG_START = "<appendix_name>";
    public static final String PATIENTDATA_APPENDIXNAME_TAG_END = "</appendix_name>";
    public static final String PATIENTDATA_APPENDIXFILE_TAG_START = "<appendix_file>";
    public static final String PATIENTDATA_APPENDIXFILE_TAG_END = "</appendix_file>";
    
    public static String REPERTORY_FILE_REPERTORY_TAG_END = "</repertory>";
    public static String REPERTORY_FILE_FILENAME_TAG_START = "<file_name>";        
    public static String REPERTORY_FILE_FILENAME_TAG_END = "</file_name>";        
    
    public static String REPERTORY_DESCRIPTOR_FILE_NAME_TAG_START = "<name>";
    public static String REPERTORY_DESCRIPTOR_FILE_NAME_TAG_END = "</name>";
    public static String REPERTORY_DESCRIPTOR_FILE_SHORTCUT_TAG_START = "<name_short_cut>";
    public static String REPERTORY_DESCRIPTOR_FILE_SHORTCUT_TAG_END = "</name_short_cut>";
    public static String REPERTORY_DESCRIPTOR_FILE_AUTHOR_TAG_START = "<author>";
    public static String REPERTORY_DESCRIPTOR_FILE_AUTHOR_TAG_END = "</author>";        
    public static String REPERTORY_DESCRIPTOR_FILE_REMEDY_NAME_TAG_START = "<remedy_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_REMEDY_NAME_TAG_END = "</remedy_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_SYMPTOM_NAME_TAG_START = "<symptom_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_SYMPTOM_NAME_TAG_END = "</symptom_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_REMSYMPTOM_NAME_TAG_START = "<remsymptom_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_REMSYMPTOM_NAME_TAG_END = "</remsymptom_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_SYMPTOMTREE_TAG_START = "<symptomtree_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_SYMPTOMTREE_TAG_END = "</symptomtree_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_MAINSYMPTOM_TAG_START = "<mainsymptoms_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_MAINSYMPTOM_TAG_END = "</mainsymptoms_file>";    
    public static String REPERTORY_DESCRIPTOR_FILE_REFERENCES_TAG_START = "<references_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_REFERENCES_TAG_END = "</references_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_COMBINATIONS_TAG_START = "<combinations>";
    public static String REPERTORY_DESCRIPTOR_FILE_COMBINATIONS_TAG_END = "</combinations>"; 
    public static String REPERTORY_DESCRIPTOR_FILE_SOURCES_TAG_START = "<sources_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_SOURCES_TAG_END = "</sources_file>";
    public static String REPERTORY_DESCRIPTOR_FILE_HISTORY_TAG_START = "<history>";
    public static String REPERTORY_DESCRIPTOR_FILE_HISTORY_TAG_END = "</history>";
    public static String REPERTORY_DESCRIPTOR_FILE_OBJECTS_TAG_START = "<objects>";
    public static String REPERTORY_DESCRIPTOR_FILE_OBJECTS_TAG_END = "</objects>";
    public static String REPERTORY_DESCRIPTOR_FILE_OBJSYM_TAG_START = "<object_symptoms>";
    public static String REPERTORY_DESCRIPTOR_FILE_OBJSYM_TAG_END = "</object_symptoms>";
    public static String REPERTORY_DESCRIPTOR_FILE_MAXGRADE_TAG_START = "<maximum_grade>";
    public static String REPERTORY_DESCRIPTOR_FILE_MAXGRADE_TAG_END = "</maximum_grade>";    
    
    /** Returns the index of currently opened repertory
     * 
     * @return
     */
    public int GetCurrentRepertoryIndex () {
        return current_repertory;
    }
    
    /** Returns the index of a repertory based on the repertory name
     * 
     * @param RepertoryName
     * @return
     */
    public int GetRepertoryIndex (String RepertoryName) {
        for (int x = 0; x < repertories.size(); x++) {
            if (repertories.get(x).GetName().equals(RepertoryName)) return x;
        }
        return -1;
    }        
        
    /** Returns the list of repertory names
     * 
     * @return
     */
    public ArrayList<String> GetRepertoryNames() {
        ArrayList<String> result = new ArrayList();
        for (int x = 0; x < repertories.size(); x++) {
            result.add(repertories.get(x).GetName());
        }
        return result;                
    }
    
    /** Adds a repertory to the database
     * 
     * @param file_name
     */
    public String AddRepertory (String file_name, String install_path) {
        Repertory rep = new Repertory(file_name, true);
        if (GetRepertoryIndex(rep.GetName()) != -1) {
            return "Repertory with this name is already imported. Please select a different repertory.";
        }
        repertories.add(rep);
        File f = new File (repertory_file);
        if (!f.exists()) try {
            f.createNewFile();
        } catch (IOException ex) {

        }
        ArrayList<String> al = new ArrayList();
        try{
            al = Utils.ReadFile(repertory_file);
            for (int x = 0; x < al.size(); x++) {
                al.set(x, al.get(x)+"\n");
            }
        }
        catch (Exception e) {
            return "Error while reading the repertory"+e.getMessage();
        }
        al.add(REPERTORY_FILE_REPERTORY_TAG_START+"\n");
        if (file_name.startsWith(install_path)) {
            file_name = Utils.INSTALL_DIR_TOKEN+"/"+file_name.substring(install_path.length());
            file_name.replace(File.separatorChar, '/');
        }
        al.add("\t"+REPERTORY_FILE_FILENAME_TAG_START+file_name+REPERTORY_FILE_FILENAME_TAG_END+"\n");
        al.add(REPERTORY_FILE_REPERTORY_TAG_END+"\n");
        try {
            Utils.WriteFile(repertory_file, al, true);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return null;
    }
    
    /** Returns the list of synonyms to the selected trigger. If there are no synonyms, returns an empty list
     * 
     * @param trigger
     * @return
     */
    public ArrayList<String> GetSynonyms (String trigger) {
        ArrayList<String> result = new ArrayList();
        for (int x = 0; x < synonyms.size(); x++) {
            if (synonyms.get(x).trigger.equals(trigger)) {
                for (int y = 0; y < synonyms.get(x).synonyms.length; y++)
                    result.add(synonyms.get(x).synonyms[y]);
                break;
            }
        }
        return result;
    }
    
    /** Returns a pointer to a repertory based on its index
     * 
     * @param RepertoryIndex
     * @return
     */
    public Repertory GetRepertory (int RepertoryIndex) {
         return repertories.get(RepertoryIndex);
    }    
    
    /** Returns a repertory based on its index
     * 
     * @param RepertoryName
     * @return
     */
    public Repertory GetRepertory (String RepertoryName) {
        int rep_idx = GetRepertoryIndex(RepertoryName);
        if (rep_idx == -1) return null;
        else
        return repertories.get(rep_idx);
    }
    
    /** Returns the currently opened repertory
     * 
     * @return
     */
    public Repertory GetCurrentRepertory() {
        if (repertories.size()-1 < current_repertory) return null;
        return repertories.get(current_repertory);
    }
    
    /** Generates the repertorization results
     * 
     * @param symptoms
     * @return
     */
    public java.util.ArrayList<RepertorisationResult> GenerateRepertorizationResults (java.util.ArrayList<SelectedSymptomItem> symptoms, int current_desktop) {
        ArrayList<String> positive_rem = null;
        ArrayList<String> negative_rem = new ArrayList();
        // fill the lists of positive and negative remedy filters
        for (int x = 0; x < symptoms.size(); x++) {
            // do not repertorize other desktops, only this one
            if (current_desktop != symptoms.get(x).desktop) continue;
            if (symptoms.get(x).positive_filter) {
                if (positive_rem == null) {
                    positive_rem = new ArrayList();
                    for (int y = 0; y < symptoms.get(x).remsymptoms.size(); y++) {
                        if (!positive_rem.contains(symptoms.get(x).remsymptoms.get(y).Remname)) positive_rem.add(symptoms.get(x).remsymptoms.get(y).Remname);
                    }
                }
                else {
                    for (int z = 0; z < positive_rem.size(); z++) {
                        boolean fnd = false;
                        for (int y = 0; y < symptoms.get(x).remsymptoms.size(); y++) {
                            if (positive_rem.get(z).equals(symptoms.get(x).remsymptoms.get(y).Remname)) {
                                fnd = true;
                                break;
                            }
                        }
                        if (!fnd) {
                            positive_rem.remove(z);
                            z--;
                        }
                    }
                }
            }
            if (symptoms.get(x).negative_filter) {
                for (int y = 0; y < symptoms.get(x).remsymptoms.size(); y++) {
                    if (!negative_rem.contains(symptoms.get(x).remsymptoms.get(y).Remname)) negative_rem.add(symptoms.get(x).remsymptoms.get(y).Remname);
                }
            }            
        }
        if (positive_rem != null && positive_rem.size() == 0) {
            java.util.ArrayList<RepertorisationResult> al = new ArrayList();
            return al;
        }
        if (positive_rem == null) positive_rem = new ArrayList();
        java.util.ArrayList<RepertorisationResult> rr = new java.util.ArrayList();
        for (int x = 0; x < symptoms.size(); x++) {
            if (current_desktop != symptoms.get(x).desktop) continue;            
            // do not use the symptoms from a different desktop           
            for (int y = 0; y < symptoms.get(x).remsymptoms.size(); y++) {
                boolean fnd = false;
                for (int z = 0; z < rr.size(); z++) {
                    if (rr.get(z).RemName.equalsIgnoreCase(symptoms.get(x).remsymptoms.get(y).Remname)) {
                        rr.get(z).value += (symptoms.get(x).symptom_grade*symptoms.get(x).remsymptoms.get(y).RemGrade);
                        rr.get(z).cnt += 1;
                        fnd = true;
                        break;
                    }
                }
                if (!fnd) {
                    if (((positive_rem.size() == 0) || (positive_rem.contains(symptoms.get(x).remsymptoms.get(y).Remname))) &&
                       ((negative_rem.size() == 0) || (negative_rem.size() != 0 && !negative_rem.contains(symptoms.get(x).remsymptoms.get(y).Remname)))) {
                        RepertorisationResult new_rr = new RepertorisationResult();
                        new_rr.RemName = symptoms.get(x).remsymptoms.get(y).Remname;
                        new_rr.value = (short) (symptoms.get(x).symptom_grade*symptoms.get(x).remsymptoms.get(y).RemGrade);
                        new_rr.cnt = 1;
                        new_rr.RemSC = symptoms.get(x).remsymptoms.get(y).RemSC;
                        rr.add(new_rr);
                    }
                }
            }
        }   
        // retrieves the list of indexes of repertories to which the symptoms belong to
        ArrayList<Integer> found_repertories = new ArrayList();
        for (int x = 0; x < symptoms.size(); x++) {
            // do not use symptoms from a different desktop
            if (symptoms.get(x).desktop != current_desktop) continue;
            if (symptoms.get(x).repertory_id != -1 && !found_repertories.contains(symptoms.get(x).repertory_id)) found_repertories.add((int)symptoms.get(x).repertory_id);
        }        
        ArrayList<RepertorisationResult> rep_res = new ArrayList ();
        for (int x = 0; x < found_repertories.size(); x++) {
            ArrayList<SelectedSymptomItem> repertory_symptoms = new ArrayList();
            for (int y = 0; y < symptoms.size(); y++) {
                // do not use symptoms from a different desktop
                if (symptoms.get(y).desktop != current_desktop) continue;
                if (symptoms.get(y).repertory_id != -1 && symptoms.get(y).repertory_id == found_repertories.get(x)) repertory_symptoms.add(symptoms.get(y));
            }
        }
        for (int x = 0; x < rep_res.size(); x++) {
            for (int y = 0; y < rr.size(); y++) {
                if (rr.get(y).RemName.equalsIgnoreCase(rep_res.get(x).RemName)) {
                    rr.get(y).value += rep_res.get(x).value;
                    break;
                }
            }
        }                
        return rr;
    }
    
    /** Reads the file containing the list of available repertories
     * 
     * @param repertory_file
     * @return
     */
    private ArrayList<Repertory> ReadRepertoryFile (String repertory_file, String install_directory) {
        ArrayList<Repertory> result = new ArrayList();
        try {
            String rep_data = Utils.ReadFile(repertory_file, "\n");
            String rep_tag = "";
            int pos = -1;
            // read the contents
            while (rep_tag != null) {
                // <repertory> </repertory> tag content
                rep_tag = Utils.ReadTag(rep_data, REPERTORY_FILE_REPERTORY_TAG_START, REPERTORY_FILE_REPERTORY_TAG_END, pos+1);
                if (rep_tag == null) break;
                pos = rep_data.indexOf(REPERTORY_FILE_REPERTORY_TAG_START, pos+1);
                // reading repertory entries
                String file_name = "";
                if (rep_tag != null) {
                    file_name = Utils.ConvertPath(Utils.ReadTag(rep_tag, REPERTORY_FILE_FILENAME_TAG_START, REPERTORY_FILE_FILENAME_TAG_END, 0), install_directory);
                    
                    Logger.AddEntryToLog("Processing the repertory descriptor file: "+file_name);
                    Repertory rep = new Repertory(file_name, false);
                    if (rep.IsRepertoryOK()) {
                        Logger.AddEntryToLog("Added repertory descriptor file for repertory: "+rep.GetName());
                        result.add(rep);
                    }
                }
            }
        }
        catch (Exception e) {
            result.clear();
            Logger.AddEntryToLog("Error while reading the repertory file: "+repertory_file);
            JOptionPane.showMessageDialog(null, "Could not open the repertory file: "+repertory_file);
        }
        return result;
    }

    /** Writes the file containing the list of available repertories
     * 
     * @param repertory_file
     * @return
     */
    private ArrayList<Repertory> WriteRepertoryFile (String repertory_file) {
        ArrayList<Repertory> result = new ArrayList();
        try {
            String temps = "";
            for (int x = 0; x < repertories.size(); x++) {
                temps += REPERTORY_FILE_REPERTORY_TAG_START+REPERTORY_FILE_FILENAME_TAG_START+repertories.get(x).GetDescriptorFilaName()+REPERTORY_FILE_FILENAME_TAG_END+REPERTORY_FILE_REPERTORY_TAG_END+"\n";
            }
            Utils.WriteFile(repertory_file, temps, true);
        }
        catch (Exception e) {
            result.clear();
            Logger.AddEntryToLog("Error while reading the repertory file: "+repertory_file);
            JOptionPane.showMessageDialog(null, "Could not open the repertory file: "+repertory_file);
        }
        return result;
    }    
    
    /** Removes a repertory from the list of repertories with the index in the list of repertories
     * 
     * @param index_in_repertory_list
     */
    public void RemoveRepertory (int index_in_repertory_list) {
        repertories.remove(index_in_repertory_list);
        WriteRepertoryFile(this.repertory_file);
    }

    /** Opens a repertory and loads it into the memory
     * 
     * @param repertory_index
     */
    public void OpenRepertory(int repertory_index) {
        if (repertory_index > repertories.size() - 1) return;
        repertories.get(repertory_index).OpenRepertory();
    }
    
    /** Returns the number of imported repertories
     * 
     * @return
     */
    public int GetRepertoryCount() {
        return repertories.size();
    }
    
    /** Closes a repertory and frees the memory
     * 
     * @param repertory_index
     */
    public void CloseRepertory(int repertory_index) {
        repertories.get(repertory_index).CloseRepertory();
        System.gc();
    }    
    
    /** Sets the current repertory index, unloads all opened repertories from the memory and calls the garbage collector
     * 
     * @param new_index
     */
    public void SetCurrentRepertory (int new_index) {
        current_repertory = new_index;
        CloseAllRepertories();
        if (new_index > repertories.size() - 1) return;
        repertories.get(new_index).OpenRepertory();
    }
    
    /** Closes all repertories and removes them from the memory
     * 
     */
    public void CloseAllRepertories() {
        for (int x = 0; x < repertories.size(); x++) {
            repertories.get(x).CloseRepertory();
        }
        System.gc();
    }
    
    /** Constructor
     * 
     * @param repertory_file
     */
    public Database(String repertory_file, String install_dir) {
        Logger.AddEntryToLog("Initializing");

        this.repertory_file = repertory_file;
        
        synonyms = Utils.ReadSynonymFile(install_dir+synonyms_file);

        Logger.AddEntryToLog("Reading the repertory file: "+repertory_file);
        // read the repertory file - file that contains the information about available repertories
        repertories = ReadRepertoryFile(repertory_file, install_dir);
        System.out.println(Logger.GetLog());
    }
}
