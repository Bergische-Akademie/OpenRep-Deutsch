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

package prescriber;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;


import javax.swing.JOptionPane;

// !!! LIMITATIONS:
//    Remedy.RemedyId             == short
//    Symptom.additions           == short
//    Symptom.RepertoryID         == short
//    Source.id                   == short
//    SelectedSymptomItem.repertory_id == short
//    SelectedSymptomItem.symptom_grade == byte
//    SelectedSymptomItem.maximum_grade == byte
//    ReversedMMSymptom.grade == byte
//    Repertory.maximum_grade == byte
//    RepertorisationResult.value == short
//    RepertorisationResult.cnt == short
//    RemSymptomItem.remid == short
//    RemSymptomItem.rem_grade == byte
//    RemSymptomItem.sources == byte[]
    

/** Class repertory contains one repertory - e.g. contains its remedies, symptoms, remsymptoms, tree, etc.
 
 *  !!! For meaningful results, it has to be asserted, that there are the same remedy ids in the repertories
 * 
 *  On every opening of repertory - following things are done:
 *  - regeneration of symptoms, e.g. (if there is a symptom: "Abdomen  , something,  something else   " it is transformed to
 *    "Abdomen, something, something else"
 *  - if the symptoms tree does not exist, it is regenerated and saved in the directory of the repertory under the name:
 *    [descriptor_name]_tree - if such a file exists, program tries to create files [descriptor_name]_tree0 - [descriptor_name]_tree100
 *    until [descriptor_name]_tree100 - then it stops
 * 
 */

public class Repertory {
    
    /** contains the list of main symptoms of the repertory*/
    private ArrayList<String> main_symptoms = new ArrayList();
    /** directory in which the repertory is located */
    private String directory = null;
    /** name of the repertory */
    private String name = null;
    /** shortcut of repertory's name */
    private String short_cut = null;    
    /** author of the repertory*/
    private String author = null;
    /** path and name of the descriptor file name */
    private String descriptor_file_name = null;
    /** name of the remedy file - does not contain path*/
    private String remedy_file_name = null;
    /** name of the symptom file - does not contain path*/    
    private String symptom_file_name = null;
    /** hashtable of symptoms */
    private Hashtable symptom_hashtable = new Hashtable(80000);
    /** hashtable of remedies */
    private Hashtable remedy_hashtable = new Hashtable(1500);    
    /** name of the remsymptom file - does not contain path*/    
    private String remsymptom_file_name = null;
    /** name of the symptomtree file - does not contain path*/
    private String symptomtree_file_name = null;
    /** name of the symptom references file */
    private String reference_file_name = null;
    /** name of the sources file */
    private String source_file_name = null;    
    /** name of the main symptom file */
    private String mainsymptom_file_name = null;        
    
    /** maximum grade used in repertory - it is generated after reading the RemSymptom file*/
    private byte maximum_grade = 0;
    
    /** the list of all remedies - every remedy is saved under its id, so this is always true remedies.get(x).id = x 
     *  what it also means, is that if the remedy does not exist under a specific id, the remedies.get(x) can be null*/
    private ArrayList<Remedy> remedies = new ArrayList();
    /** the list of all symptoms - every symptom is saved under its id, so this is always true symptoms.get(x).id = x 
     *  what it also means, is that if the symptom does not exist under a specific id, the symptoms.get(x) can be null*/    
    private ArrayList<Symptom> symptoms = new ArrayList();
    /** list of remedy symptoms that are not loaded in memory*/
    private ArrayList<String> remsymptoms_string = new ArrayList();
    /** list of references to symptoms */
    private ArrayList<SymptomReference> references = new ArrayList();
    /** list of sources */
    private ArrayList<Source> sources = new ArrayList();     
    /** contains the history of the repertory - the list of changes */
    private Changes repertory_changes = new Changes();
    /** if the repertory is opened, this is set to true, otherwise to false */
    private boolean status_opened = false;
    /** indicates whether there were some changes to the remertory */
    private boolean repertory_changed = false;
    /** contains the number of non-null symptoms */
    private int symptom_cnt = 0;
    
    /** Returns the shortcut of the repertory
     * 
     * @return
     */
    public String GetShortCut() {
        return this.short_cut;
    }

    /** Returns the number of non-null symptoms
     * 
     * @return
     */
    public int GetSymptomCnt() {
        return this.symptom_cnt;
    }

    /** Returns the maximum grade
     * 
     * @return
     */
    public byte GetMaximumGrade() {
        return maximum_grade;
    }
    
    /** Regerates the maximum grade value
     * 
     */
    public void RegenerateMaximumGrade() {        
        for (int x = 0; x < remsymptoms_string.size(); x++) {
            if (remsymptoms_string.get(x) == null || remsymptoms_string.get(x).equals("")) continue;
            RemSymptom rs = GetRemSymptom(remsymptoms_string.get(x));
            if (rs == null) continue;
            for (int y = 0; y < rs.items.size(); y++) {
                if (rs.items.get(y).rem_grade > maximum_grade) maximum_grade = rs.items.get(y).rem_grade;
            }
        }        
    }
    
    /** Returns the repertory descriptor file name
     * 
     * @return
     */
    public String GetDescriptorFilaName() {
        return this.descriptor_file_name;
    }

    /** Preprocesses the search string for the non_professional search system
     *
     * @param search_string
     * @return
     */
    public static String PreProcessSearchString (String search_string) {
        String result = search_string.toLowerCase()+" ";
        if (result.startsWith("and ")) result = " " + result;
        if (result.startsWith("in ")) result = " " + result;
        if (result.startsWith("on ")) result = " " + result;
        if (result.startsWith("at ")) result = " " + result;
        if (result.startsWith("the ")) result = " " + result;
        if (result.startsWith("a ")) result = " " + result;
        if (result.startsWith("an ")) result = " " + result;
        if (result.startsWith("with ")) result = " " + result;
        if (result.startsWith("or ")) result = " " + result;
        if (result.startsWith("by ")) result = " " + result;
        if (result.startsWith("better from ")) result = " " + result;
        if (result.startsWith("worse from ")) result = " " + result;
        if (result.startsWith("outside ")) result = " " + result;
        if (result.startsWith("flu ")) result = " " + result;
        result = result.replaceAll(" and ", " ");
        result = result.replaceAll(" in ", " ");
        result = result.replaceAll(" on ", " ");
        result = result.replaceAll(" at ", " ");
        result = result.replaceAll(" the ", " ");
        result = result.replaceAll(" a ", " ");
        result = result.replaceAll(" an ", " ");
        result = result.replaceAll(" by ", " ");
        result = result.replaceAll(" with ", " ");
        result = result.replaceAll(" or ", ";");
        result = result.replaceAll(" worse from ", " agg. ");
        result = result.replaceAll(" better from ", " amel. ");
        result = result.replaceAll(" worse ", " agg. ");
        result = result.replaceAll(" better ", " amel. ");
        result = result.replaceAll(" outside ", " open air ");
        result = result.replaceAll(" flu ", " influenza ");
        String[] temp = result.split(";");
        ArrayList<String> s = new ArrayList();
        for (int x = 0; x < temp.length; x++) {
            String temps = Utils.ReplaceAche(temp[x]);
            if (!temps.equals(temp[x])) s.add(temps);
        }
        result = "";
        for (int x = 0; x < temp.length; x++) {
            result += temp[x] + ";";
        }
        for (int x = 0; x < s.size(); x++) {
            result += s.get(x) + ";";
        }
        return result;
    }

    /** Closes the repertory, releases it from the memory and calls the garbage collector
     * 
     */
    public void CloseRepertory() {
        remedies = null;
        symptoms = null;
        remsymptoms_string = null;
        references = null;
        sources = null;
        main_symptoms = null;
        symptom_hashtable.clear();
        remedy_hashtable.clear();
        System.gc();
        status_opened = false;
    }
    
    /** Returns the symptom based on the symptom index
     * 
     * @param symptom_index
     * @return
     */
    public Symptom GetSymptom (int symptom_index) {
        if (symptoms.size() - 1 < symptom_index) return null;
        return symptoms.get(symptom_index);
    }

    /** Opens the repertory and loads all the data into the memory
     * 
     */
    public void OpenRepertory() {
       Logger.AddInitEntry(Logger.Operation_OpenRepertory, descriptor_file_name);
       MemoryManagement.setPercentageUsageThreshold(0.8);
       MemoryManagement mws = new MemoryManagement();
       mws.addListener(new MemoryManagement.Listener() {
        public void memoryUsageLow(long usedMemory, long maxMemory) {
          double percentageUsed = ((double) usedMemory) / maxMemory;
          Logger.AddEntryToLog(Logger.Warning_Memory+" low memory. Used memory="+percentageUsed+", Maximum memory="+maxMemory);
          MemoryManagement.setPercentageUsageThreshold(0.95);
          Logger.AddEntryToLog(Logger.Warning_Memory+" increasing limit to 95%");
          }
        });    
        try{
            System.out.println("1");
            status_opened = true;
            Logger.AddInitEntry(Logger.Operation_ReadRemedies, "");
            remedies = new ArrayList();
            ReadRemedies();
            System.out.println("2");
            ReadSymptoms(true, true);
            System.out.println("3");
            remsymptoms_string = new ArrayList();
            ReadRemSymptomsToArrayList();
            System.out.println("4");
            references = new ArrayList();
            ReadReferences();
            System.out.println("5");
            sources = new ArrayList();
            ReadSources();

            Logger.AddSuccessEntry(Logger.Operation_OpenRepertory, descriptor_file_name);            
            Runtime.getRuntime().gc();
            System.out.println("6");
        }
        catch (final java.lang.OutOfMemoryError e) {
            Logger.AddFailureEntry(Logger.Operation_OpenRepertory, " there was not enough memory to load the repertory");
            JOptionPane.showMessageDialog(null, "There was not enough memory to open this repertory");
            status_opened = false;
            return;
        }
    }
    
    /** Constructor - reads the contents of the repertory descriptor file. If the load_data parameter is
     *  true - loads all data into memory, otherwise only checks whether repertory contains all files
     * 
     * @param file_name
     * @param load_data
     */
    public Repertory (String file_name, boolean load_data) {
        ReadRepertoryDescriptorFile(file_name);
        if (load_data && this.IsRepertoryOK()) {
            // maximum grade not found, regenerating
            if (maximum_grade == -1) {
                RegenerateMaximumGrade();
                // write the new value into the descriptor file
                try{
                    String file_content = Utils.ReadFile(file_name, "\n");
                    file_content += Database.REPERTORY_DESCRIPTOR_FILE_MAXGRADE_TAG_START + maximum_grade + Database.REPERTORY_DESCRIPTOR_FILE_MAXGRADE_TAG_END;
                    Utils.WriteFile(descriptor_file_name, file_content, true);
                }
                catch (Exception e) {
                   Logger.AddEntryToLog("could not save the maximum grade into the repertory descriptor file: "+file_name+". Exception ="+e.getMessage());
                }
            }            
        }
    }
    
    /** Returns the parent of the symptom
     * 
     * @param symptomname
     * @return
     */
    public int GetSymptomParent(String symptomname) {
        while (true) {
            int pos = symptomname.lastIndexOf(String.valueOf(Utils.SYMPTOM_FRAGMENT_SEPARATOR));
            if (pos == -1) return -1;
            symptomname = symptomname.substring(0, pos);
            int index = GetSymptomIndex(symptomname);
            if (index != -1) return index;
        }
    }

    /** Returns the fraction name of the symptom
     * 
     * @param symptom
     * @param parent
     * @return
     */
    public String GetSymptomFractionName (String symptom, String parent) {
        return symptom.substring(parent.length() + 2);     
    }
    
    /** Adds a new symptom to the repertory. Returns false if a symptom with this name already exitsts
     * 
     * @param SymptomName
     */
    public boolean AddSymptom (String symptom_name) {
        if (symptom_name.trim().equals("")) return false;
        symptom_name = ReturnCorrectedSymptom(symptom_name.trim());
        if (!IsSymptoWellFormed(symptom_name)) return false;
        for (int x = 0; x < symptoms.size(); x++) {
            if (symptoms.get(x) != null && symptoms.get(x).SymName.equalsIgnoreCase(symptom_name)) return false;
        }
        Symptom s = new Symptom();
        s.id = symptoms.size();
        s.SymName = symptom_name.trim();        
        s.parent_id = GetSymptomParent(s.SymName);
        if (s.parent_id == -1) s.fraction_name = symptom_name;
        else
        {
            s.fraction_name = GetSymptomFractionName(s.SymName, GetSymptom(s.parent_id).SymName);
        }
        symptoms.add(s);
        RemSymptom rs = new RemSymptom();
        rs.id = s.id;
        rs.items = new ArrayList();
        int remsym = remsymptoms_string.size()-1;
        for (int x = remsym; x <= s.id; x++) {
            remsymptoms_string.add (null);
        }
        remsymptoms_string.set(rs.id, ConvertRemSymptomToString(rs));
        repertory_changes.AddSymptom(symptom_name, s.id);
        symptom_hashtable.put(s.SymName, s.id);
        repertory_changed = true;
        return true;
    }
    
    /** Generates a list of symptoms in which the remedy is present (reversed repertory)
     * 
     * @param remedy_index
     * @return
     */
    public ArrayList<String> GetSymptomListFromRemedy (int remedy_index) {
        ArrayList<String> result = new ArrayList();
        for (int x = 0; x < remsymptoms_string.size(); x++) {
            if (remsymptoms_string.get(x) == null) continue;
            RemSymptom rs = GetRemSymptom(x);
            for (int y = 0; y < rs.items.size(); y++) {
                if (rs.items.get(y).rem_id == remedy_index) {
                    result.add(GetSymptomName(rs.id)+Utils.SYMPTOM_COUNT_START+rs.items.get(y).rem_grade+Utils.SYMPTOM_COUNT_END);
                    break;
                }
            }
        }
        Collections.sort(result);
        return result;                
    }
    
    /** Returns the remedy name
     * 
     * @param remedy_index
     * @return
     */
    public String GetRemedyName (int remedy_index) {
       try{
            return remedies.get(remedy_index).RemName;
       }
       catch (Exception e) {
           return null;
       }
    }
    
    /** Returns the name of the repertory
     * 
     * @return
     */
    public String GetName() {
        return name;
    }
    
    /** Returns the ArrayList of symptom  references for the selected symptom
     * 
     * @param symptom_index
     * @return
     */
    public int[] GetSymptomReferences (int symptom_index) {
        if (symptom_index == -1 || references.size()-1 < symptom_index || references.get(symptom_index) == null) return null;
        else
        return references.get(symptom_index).references;
    }
            
    /** Returns the symptom references
     * 
     * @return
     */
    public ArrayList<SymptomReference> GetReferences () {
        return references;
    }
    
    /** Returns the number of symptom references
     * 
     * @param symptom_index
     * @return
     */
    public int GetSymptomReferenceCnt(int symptom_index) {
        if (references.size()-1 < symptom_index || references.get(symptom_index) == null) return 0;
        else
        return references.get(symptom_index).references.length;
    }
    
    /** Splits a symptoms into its fragments according to the Utils.SYMPTOM_FRAGMENT_SEPARATOR and returns the fragments
     *  in an ArrayList - they are in original order, 1, 2, 3, 4 will be transformed to [1][2][3][4]
     * 
     * @param symptom name of the symptom
     * @return 
     */
    public static String[] GetSymptomFragments (String symptom) {
        ArrayList<String> al = new ArrayList();
        int prev_fragment = 0;
        int pos = symptom.indexOf(Utils.SYMPTOM_FRAGMENT_SEPARATOR);
        //get fragments until there is no more Utils.SYMPTOM_FRAGMENT_SEPARATOR, in which case break
        while (pos != -1) {
            if (pos == -1) {
                // trim added fragments
                al.add(symptom.substring(prev_fragment, symptom.length()).trim());
                break;
            }
            else
            {                
                // trim added fragments     whether the symptom is in this combination present           
                al.add(symptom.substring(prev_fragment, pos).trim());                
                prev_fragment = pos+1;
                pos = symptom.indexOf(Utils.SYMPTOM_FRAGMENT_SEPARATOR, pos+1);
            }
        }
        // there were no more Utils.SYMPTOM_FRAGMENT_SEPARATOR chars, so save the last one (position of the
        // previous fragment - length of the string
        al.add(symptom.substring(prev_fragment, symptom.length()).trim());
        String[] result = new String[al.size()];
        for (int x = 0; x < al.size(); x++) {
            result[x] = al.get(x);
        }
        return result;
    }

    /** Returns a symptom name based on its index
     * 
     * @param index index of a symptom
     * @return name of the symptom
     */
    public String GetSymptomName (int index) {
        return symptoms.get(index).SymName;
    }
    
    /** Returns index of a symptom based on its name - a HashTable is used for quick retrieval
     * 
     * @param symptom name of the symptom
     * @return index of the symptom, returns -1 if the symptom was not found (should not happen)
     */
    public int GetSymptomIndex (String symptom) {
        if (symptom == null) return -1;
        else
        {
            // repertory is opened => use the structures loaded in memory
            if (symptom_hashtable != null && symptom_hashtable.size() != 0) {
                try{
                    return (Integer)symptom_hashtable.get(symptom);
                }
                catch (Exception e) {
                    return -1;
                }
            }
            else
            // repertory is closed => get symptom name from the file
            {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader (directory + symptom_file_name));
                    String temp = "";
                    while (temp != null) {
                        temp = br.readLine();
                        if (temp == null) break;
                        if (temp.indexOf(String.valueOf(Utils.FILE_ID_SEPARATOR)+String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+symptom+String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)) != -1) {
                            temp = temp.substring(0, temp.indexOf(String.valueOf(Utils.FILE_ID_SEPARATOR)));
                            try {
                                return Integer.parseInt(temp);                                
                            }
                            catch (Exception e) {
                                return -1;
                            }
                        }
                    }
                    return -1;
                }
                catch (Exception e) {
                    return -1;
                }
                finally {
                    try {
                        if (br != null) br.close();
                    }
                    catch (Exception ex) {
                    }
                }
            }
        }
    }

    /** Returns index of a remedy based on its name - a HashTable is used for quick retrieval
     * 
     * @param remedy name of the remedy
     * @return index of the remedy, returns -1 if the remedy was not found (should not happen)
     */
    public int GetRemedyIndex (String remedy) {
        if (remedy == null) return -1;
        else
        {
            for (int x = 0; x < remedies.size(); x++) {
                if (remedies.get(x) != null && remedies.get(x).RemName.equals(remedy)) return remedies.get(x).id;
            }
        }
        return -1;
    }    

    /** Removes the "*" characters from a keyword
     * 
     * @param keyword
     * @return
     */
    private String CleanUpKeyWord (String keyword) {
        keyword = keyword.replace(Utils.MATCH_ALL, ' ').trim();
        keyword = keyword.replace(Utils.NEGATIVE_CHAR, ' ').trim();
        return keyword;
    }

    /** Returns false if char is == "_-=+ |\/? ....
     * 
     * @param ch
     * @return
     */
    public static boolean IsCharNonAlphabetical(char ch) {
        if (ch == Utils.SPACE || 
            ch == Utils.SYMPTOM_FRAGMENT_SEPARATOR ||
            ch == '-' ||
            ch == ':' ||
            ch == '_' ||
            ch == '+' ||
            ch == '=' ||
            ch == '\\' ||
            ch == '|' ||
            ch == '{' ||
            ch == '}' ||
            ch == '[' ||            
            ch == ']' ||
            ch == '\"' ||
            ch == '\'' ||
            ch == '/' ||
            ch == '(' ||
            ch == ')' ||
            ch == '.' ||
            ch == ';' ||
            ch == '?') return true;
        else
        return false;        
    }
    
    /** Finds all positions for a specified keyword (can contain *) in a symptom and returns a list of positions
     *
     * @param keyword
     * @return
     */
    private ArrayList<Integer> SearchInSymptom (String keyword, String clean_keyword, String symptom) {
        ArrayList<Integer> positions = new ArrayList();
        // abc type of keyword
        if (keyword.charAt(0) != Utils.MATCH_ALL && keyword.charAt(keyword.length()-1) != Utils.MATCH_ALL) {
            int pos = 0;
            boolean first_run = true;
            while (pos != -1) {
                if (first_run) {
                    pos = symptom.toLowerCase().indexOf(clean_keyword, 0);
                    first_run = false;
                }
                else
                pos = symptom.toLowerCase().indexOf(clean_keyword, pos+1);
                if (pos != -1) positions.add(pos);
                if (pos == -1) break;
            }
        }
        // abc. keyword
        if (keyword.charAt(0) != Utils.MATCH_ALL && keyword.charAt(keyword.length()-1) == Utils.MATCH_ALL) {
            int pos = 0;
            boolean first_run = true;
            while (pos != -1) {
                if (first_run) {
                    pos = symptom.toLowerCase().indexOf(clean_keyword, 0);
                    first_run = false;
                }
                else
                pos = symptom.indexOf(clean_keyword, pos+1);
                if (pos != -1) {
                    if (((pos >= 1) && (pos+clean_keyword.length() == symptom.length() ||
                            Utils.IsCharNonAlphabetical(symptom.charAt(pos+clean_keyword.length()))))) positions.add(pos);
                }
                if (pos == -1) break;
            }
        }
        // .abc keyword
        if (keyword.charAt(0) == Utils.MATCH_ALL && keyword.charAt(keyword.length()-1) != Utils.MATCH_ALL) {
            int pos = 0;
            boolean first_run = true;
            while (pos != -1) {
                if (first_run) {
                    pos = symptom.toLowerCase().indexOf(clean_keyword, 0);
                    first_run = false;
                }
                else
                pos = symptom.indexOf(clean_keyword, pos+1);
                if (pos != -1) {
                    if (pos + clean_keyword.length() <= symptom.length() && (pos == 0 || (Utils.IsCharNonAlphabetical(symptom.charAt(pos-1))))) positions.add(pos);
                }
                if (pos == -1) break;
            }
        }
        // .abc. keyword
        if (keyword.charAt(0) == Utils.MATCH_ALL && keyword.charAt(keyword.length()-1) == Utils.MATCH_ALL) {
            int pos = 0;
            boolean first_run = true;
            while (pos != -1) {
                if (first_run) {
                    pos = symptom.toLowerCase().indexOf(clean_keyword, 0);
                    first_run = false;
                }
                else
                pos = symptom.indexOf(clean_keyword, pos+1);
                if ((pos == 0 || (pos >= 1 && Utils.IsCharNonAlphabetical(symptom.charAt(pos-1)))) && (pos+clean_keyword.length() == symptom.length() || Utils.IsCharNonAlphabetical(symptom.charAt(pos+clean_keyword.length())) || symptom.charAt(pos+clean_keyword.length()) == Utils.SYMPTOM_FRAGMENT_SEPARATOR)) {
                    positions.add(pos);
                }
                if (pos == -1) break;
            }
        }
        return positions;
    }

    /** Performs a search in symptoms and returns the results
     * 
     * @param keywords - keywords have to be space delimiated
     * @return
     */
    /*public ArrayList<SearchSymptom> SearchInSymptoms (String keywords, ArrayList<Symptom> symptom_list, Database current_db, int repertory_id) {
        if (symptom_list == null) symptom_list = symptoms;
        ArrayList<SearchSymptom> results = new ArrayList();
        keywords = keywords.toLowerCase();
        String[] multi_keywords = Utils.SplitSearchString(keywords, ',', false);
        ArrayList<ArrayList<String>> single_keywords = new ArrayList();
        ArrayList<ArrayList<String>> clean_keywords = new ArrayList();
        for (int x = 0; x < multi_keywords.length; x++) {
            if (multi_keywords[x].trim().equals("")) continue;
            String[] temp = Utils.SplitSearchString(multi_keywords[x], ' ', true);
            single_keywords.add(new ArrayList());
            clean_keywords.add(new ArrayList());
            for (int y = 0; y < temp.length; y++) {
                if (temp[y].trim().equals("")) continue;
                String clean_keyword = CleanUpKeyWord(temp[y].trim());
                if (clean_keyword.equals("")) continue;
                single_keywords.get(x).add(temp[y].trim());
                clean_keywords.get(x).add(clean_keyword);
            }
            if (single_keywords.size() == 0 && single_keywords.size() != 0) single_keywords.remove(single_keywords.size()-1);
        }
        
        for (int x = 0; x < symptom_list.size(); x++) {
            if (symptom_list.get(x) == null) continue;
            ArrayList<ArrayList<Integer>> positions = new ArrayList();
            boolean not_fnd = false;            
            for (int y = 0; y < single_keywords.size(); y++) {
                positions.add(new ArrayList());
                for (int z = 0; z < single_keywords.get(y).size(); z++) {
                    ArrayList<String> synonyms = new ArrayList();
                    if (current_db != null) synonyms = current_db.GetSynonyms(single_keywords.get(y).get(z));
                    ArrayList<Integer> pos;
                    pos = SearchInSymptom(single_keywords.get(y).get(z), clean_keywords.get(y).get(z), symptom_list.get(x).SymName.toLowerCase());
                    if (pos.size() == 0) {
                        // search in synonyms only if the original was not found
                        not_fnd = true;
                        if (synonyms.size() != 0) {
                            for (int i = 0; i < synonyms.size(); i++) {
                                pos = SearchInSymptom(synonyms.get(i), CleanUpKeyWord(synonyms.get(i)), symptom_list.get(x).SymName.toLowerCase());
                                if (pos.size() != 0) {
                                    positions.set(y, pos);
                                    not_fnd = false;
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    else
                    positions.set(y, pos);
                }
                if (not_fnd) break;
            }
            if (!not_fnd) {
                if (single_keywords.size() != 1) {
                    int cnt = 0;
                    if (positions.size() == 0 || positions.get(0).size() == 0) continue;
                    else
                    cnt = positions.get(0).get(0);
                    boolean is_ok = true;
                    for (int y = 1; y < positions.size(); y++) {
                        boolean fnd = false;
                        for (int z = 0; z < positions.get(y).size(); z++) {
                            if (positions.get(y).get(z) > cnt) {
                                cnt = positions.get(y).get(z);
                                fnd = true;
                                break;
                            }              
                        }
                        if (!fnd) {
                            is_ok = false;
                            break;
                        }
                    }
                    if (is_ok) {
                        results.add(symptom_list.get(x).GetSearchSymptom());
                        results.get(results.size() - 1).RepertorySC = this.short_cut;
                        results.get(results.size() - 1).RepertoryID = (short) repertory_id;
                    }
                }
                else  {
                    results.add(symptom_list.get(x).GetSearchSymptom());
                    results.get(results.size() - 1).RepertorySC = this.short_cut;
                    results.get(results.size() - 1).RepertoryID = (short) repertory_id;                    
                }                
            }
        }
        return results;
    }*/
    public ArrayList<SearchSymptom> SearchInSymptoms (String keywords, ArrayList<Symptom> symptom_list, Database current_db, int repertory_id) {
        if (symptom_list == null) symptom_list = symptoms;
        ArrayList<SearchSymptom> results = new ArrayList();
        keywords = keywords.toLowerCase();
        String[] multi_keywords = Utils.SplitSearchString(keywords, ',', false);
        ArrayList<ArrayList<String>> single_keywords = new ArrayList();
        ArrayList<ArrayList<String>> clean_keywords = new ArrayList();        
        for (int x = 0; x < multi_keywords.length; x++) {
            if (multi_keywords[x].trim().equals("")) continue;            
            String[] temp = Utils.SplitSearchString(multi_keywords[x], ' ', true);
            ArrayList<String> al = new ArrayList();
            // if word occurances exist, sort the words according to their occurance, so that the smallest words are searched
            // as the first ones
            for (int y = 0; y < temp.length; y++)
                if (!temp[y].startsWith(String.valueOf(Utils.NEGATIVE_CHAR))) al.add(temp[y]);
            for (int y = 0; y < temp.length; y++)
                if (temp[y].startsWith(String.valueOf(Utils.NEGATIVE_CHAR))) al.add(temp[y]);
            for (int y = 0; y < al.size(); y++) 
                temp[y] = al.get(y);
            single_keywords.add(new ArrayList());
            clean_keywords.add(new ArrayList());
            for (int y = 0; y < temp.length; y++) {
                if (temp[y].trim().equals("")) continue;
                String clean_keyword = CleanUpKeyWord(temp[y].trim());
                if (clean_keyword.equals("")) continue;
                single_keywords.get(x).add(temp[y].trim());
                clean_keywords.get(x).add(clean_keyword);
            }
            if (single_keywords.size() == 0 && single_keywords.size() != 0) single_keywords.remove(single_keywords.size()-1);
        }
        
        for (int x = 0; x < symptom_list.size(); x++) {
            if (symptom_list.get(x) == null) continue;
            ArrayList<ArrayList<Integer>> positions = new ArrayList();
            boolean not_fnd = false;
            for (int y = 0; y < single_keywords.size(); y++) {
                positions.add(new ArrayList());
                for (int z = 0; z < single_keywords.get(y).size(); z++) {
                    ArrayList<String> synonyms = new ArrayList();
                    if (current_db != null) synonyms = current_db.GetSynonyms(single_keywords.get(y).get(z));
                    ArrayList<Integer> pos;
                    pos = SearchInSymptom(single_keywords.get(y).get(z), clean_keywords.get(y).get(z), symptom_list.get(x).SymName.toLowerCase());
                    if (pos.size() == 0) {
                        // search in synonyms only if the original was not found
                        not_fnd = true;
                        if (synonyms.size() != 0) {
                            for (int i = 0; i < synonyms.size(); i++) {
                                pos = SearchInSymptom(synonyms.get(i), CleanUpKeyWord(synonyms.get(i)), symptom_list.get(x).SymName.toLowerCase());
                                if (pos.size() != 0) {
                                    if (single_keywords.get(y).get(z).startsWith(String.valueOf(Utils.NEGATIVE_CHAR))) {
                                        not_fnd = true;
                                        break;
                                    }
                                    positions.set(y, pos);
                                    not_fnd = false;
                                    //break;
                                }
                            }
                        }
                        if (single_keywords.get(y).get(z).startsWith(String.valueOf(Utils.NEGATIVE_CHAR))) {
                            not_fnd = !not_fnd;                            
                        }
                        if (not_fnd && !single_keywords.get(y).get(z).startsWith(String.valueOf(Utils.NEGATIVE_CHAR))) break;
                    }
                    else
                    {
                        if (single_keywords.get(y).get(z).startsWith(String.valueOf(Utils.NEGATIVE_CHAR))) {
                            not_fnd = true;
                            break;
                        }
                        positions.set(y, pos);
                    }
                }
                if (not_fnd) break;
            }
            if (!not_fnd) {
                if (single_keywords.size() != 1) {
                    int cnt = 0;
                    if (positions.size() == 0 || positions.get(0).size() == 0) continue;
                    else
                    cnt = positions.get(0).get(0);
                    boolean is_ok = true;
                    for (int y = 1; y < positions.size(); y++) {
                        boolean fnd = false;
                        for (int z = 0; z < positions.get(y).size(); z++) {
                            if (positions.get(y).get(z) > cnt) {
                                cnt = positions.get(y).get(z);
                                fnd = true;
                                break;
                            }              
                        }
                        if (!fnd) {
                            is_ok = false;
                            break;
                        }
                    }
                    if (is_ok) {
                        results.add(symptom_list.get(x).GetSearchSymptom());
                        results.get(results.size() - 1).RepertorySC = this.short_cut;
                        results.get(results.size() - 1).RepertoryID = (short) repertory_id;
                    }
                }
                else  {
                    results.add(symptom_list.get(x).GetSearchSymptom());
                    results.get(results.size() - 1).RepertorySC = this.short_cut;
                    results.get(results.size() - 1).RepertoryID = (short) repertory_id;                    
                }                
            }
        }
        return results;
    }
    
    /** Get children of the symptom in the tree structure
     * 
     * @param idx index of the symptom
     * @return
     */
    public ArrayList<Symptom> GetSymptomChildren (int idx) {
        ArrayList<Symptom> al = new ArrayList();
        int node_idx = idx;
        // return all symptoms that are not null and that have parent specified by the idx
        for (int x = 0; x < symptoms.size(); x++) {
              if (symptoms.get(x) != null && symptoms.get(x).SymName != null && symptoms.get(x).parent_id == node_idx) {
                  al.add(symptoms.get(x));
              }
        }
        return al;
    }        
    
    /** If the tree structure is not already generated, this method generates it.
     *  Generating tree structure consists of:
     *  1. Assigning the parent_id to each symptom, while parent_id == -1 means, that it does
     *  not have a parent and that its only parent is a root node.
     *  2. Generating fraction names of symptoms and saving them to .values[1] - String that differentiates a child from its
     *     parent
     *     e.g. if symptom "Generalities, pain" was the parent of the "Generalities, pain, strong", the fraction string
     *          of the child would be "strong"
     */
    public void GenerateTreeStructure() {
        for (int x = 0; x < symptoms.size(); x++) {
            GenerateTreeStructure(x);
        }
    }    

    /** Generates a tree structure only for a single symptom
     * 
     * @param symptom
     */
    public void GenerateTreeStructure(int symptom_id) {        
        if (symptoms.get(symptom_id) == null) return;
        String temp = symptoms.get(symptom_id).SymName;
        symptoms.get(symptom_id).fraction_name = temp;
        int pos;
        int symptom_idx = -1;
        while (true) {
           pos = temp.lastIndexOf(Utils.SYMPTOM_FRAGMENT_SEPARATOR);
           if (pos == -1) break;
           temp = temp.substring(0, pos);
           symptom_idx = GetSymptomIndex(temp);
           if (symptom_idx != -1) {
               symptoms.get(symptom_id).parent_id = symptom_idx;
               symptoms.get(symptom_id).fraction_name = symptoms.get(symptom_id).SymName.substring(pos+Utils.SYMPTOM_FRAGMENT_FULL_SEPARATOR.length(), symptoms.get(symptom_id).SymName.length());
               break;
           }
        }
    }
    
    /** Reads only the RemSymptoms file but does not store it into the remsymptoms list
     * 
     */
    public void ReadRemSymptomsToArrayList() {
        try {
            ArrayList<String> remsyms = Utils.ReadFile(directory + this.remsymptom_file_name);
            for (int x = 0; x < remsyms.size(); x++) {
                ReadItem ri;
                try{
                    if (remsyms.get(x) != "\n") {
                    remsyms.set(x, remsyms.get(x).replace("\n", ""));
                    ri = ReadData(remsyms.get(x));
                    int rem_size = remsymptoms_string.size();
                    for (int y = 0; y < ri.id - (rem_size) + 1; y++) {
                        remsymptoms_string.add(null);
                    }
                    remsymptoms_string.set(ri.id, remsyms.get(x));
                    if (symptoms.get(ri.id) != null) symptoms.get(ri.id).additions = (short) ri.values.size();
                    }
                }
                catch (Exception e) {
                    Logger.AddEntryToLog("Error in remedy_symptom value: "+remsyms.get(x)+" in file: "+this.remsymptom_file_name+" in repertory "+this.name);
                }
            }
            // add null records for symptoms that are not included in the remsymptoms
            for (int x = remsymptoms_string.size(); x < symptoms.size(); x++) {
                remsymptoms_string.add(null);
            }
        } 
        catch (Exception e) {
            Logger.AddEntryToLog("Error while reading the remedy_symptom file name: "+this.remsymptom_file_name+" for repertory:"+this.name+" Exception = "+e.getMessage());
        }
    }    
    
    /** Adds a reference to a symptom 
     * 
     * @param symptom_index
     * @param reference_index
     */
    public void AddSymptomReference (int symptom_index, int reference_index) {
        int ref_size = references.size();
        for (int x = ref_size-1; x <= symptom_index; x++) {
            references.add(null);
        }
        if (references.get(symptom_index) == null) {
            SymptomReference sr = new SymptomReference();
            sr.id = symptom_index;
            ArrayList<Integer> temp_al = new ArrayList();
            sr.references = new int[1];
            sr.references[0] = reference_index;
            references.set(symptom_index, sr);
            return;
        }
        if (!Utils.ARRAY_Contains(reference_index, references.get(symptom_index).references)) {
            references.get(symptom_index).references = Utils.ARRAY_AddValueToArray(reference_index, references.get(symptom_index).references);
        }
    }
    
    /** Corrects a name of symptom, so that all symptoms have the same naming conventions (is very important for
     *  referencing symptoms from the tree back to the symptom name). If this is not done, symptoms like:
     *  "Abdomen,  something  , something else" will cause problems when clicking on them in a tree (because each click
     *  in the tree reconstructs the symptom name into convention: "a, b, c, d, e".
     *  
     *  So this routine changes the symptom: "Abdomen,  something  , something else" to "Abdomen, something, something else"
     * 
     */    
    public static String ReturnCorrectedSymptom (String symptom) {
        symptom = symptom.trim();
        if (symptom.equals("")) return null;
        String[] al = GetSymptomFragments (symptom);
        String temps = "";
        for (int x = 0; x < al.length;x++) {
            temps += al[x];
            if (x != al.length-1) temps += Utils.SYMPTOM_FRAGMENT_FULL_SEPARATOR;
        }
        return temps;
    }
    
    /** Saves the tree structure generated by the GenerateTreeStructure into a file
     * 
     * @param file_name
     */
    private void SaveTreeStructure (String file_name) {
        ArrayList<String> al = new ArrayList();
        Logger.AddEntryToLog("Saving symptom tree structure to file: "+file_name);
        for (int x = 0; x < symptoms.size(); x++) {
            if (symptoms.get(x) != null)
            al.add(symptoms.get(x).id+String.valueOf(Utils.FILE_ID_SEPARATOR)+symptoms.get(x).parent_id+String.valueOf(Utils.FILE_VALUE_SEPARATOR)+"\""+symptoms.get(x).fraction_name+"\""+"\n");
        }
        try{
            Utils.WriteFile(file_name, al, true);        
            Logger.AddEntryToLog("Symptom tree file saved: "+file_name);
        } catch (Exception e) {
           Logger.AddEntryToLog("Error while saving the symptom tree file: "+file_name+" - "+e.getMessage());
        }
    }
    
    /** Reads the tree structure from the symptomtree_file_name and updates the symptoms arraylist
     *  if the file cannot be opened, it only generates a tree strcutrure
     * 
     */
    private void ReadTreeStructure() {
        try{
            Logger.AddEntryToLog("Reading the symptom tree file: "+this.directory+this.symptomtree_file_name);
            ArrayList<String> al = Utils.ReadFile(directory + this.symptomtree_file_name);
            ReadItem ri;
            for (int x = 0; x < al.size(); x++) {
                try{
                    if(al.get(x) != "\n") {
                    al.set(x, al.get(x).replace("\n", ""));
                    ri = ReadData(al.get(x));
                    if (ri.values.size() >= 1 && ri.values.get(0).size() >= 2) symptoms.get(ri.id).fraction_name = ri.values.get(0).get(1);
                    try{
                        if (ri.values.size() >= 1 && ri.values.get(0).size() >= 1) symptoms.get(ri.id).parent_id = Integer.parseInt(ri.values.get(0).get(0));
                    }
                    catch (Exception e) {                        
                    }
                    }
                }
                catch (Exception e) {
                    Logger.AddEntryToLog("Error while reading the tree structure item: "+al.get(x)+" from file: "+this.symptomtree_file_name);
                }
            }
        } catch (Exception e) {
            Logger.AddEntryToLog("Error while reading the symptoms tree file: "+this.symptomtree_file_name);
            Logger.AddEntryToLog("Regenerating the symptom tree structure...");
            GenerateTreeStructure();
        }
    }
    
    /** Reads the symptoms from the symptom_file_name and saves them into the symptoms ArrayList
     * 
     */
    public void ReadSymptoms(boolean read_tree, boolean fill_hash) {
        try{
            if (symptoms == null) symptoms = new ArrayList();
            ArrayList<String> syms = Utils.ReadFile(directory + this.symptom_file_name);
            ReadItem ri;
            symptom_cnt = 0;
            for (int x = 0; x < syms.size(); x++) {                
                try{  
                    if(syms.get(x) != "\n") {
                    syms.set(x, syms.get(x).replace("\n", ""));
                    ri = ReadData(syms.get(x));
                    int sym_size = symptoms.size();
                    for (int y = 0; y < ri.id - (sym_size) + 1; y++) {
                        symptoms.add(null);
                    }
                    Symptom sym = new Symptom ();
                    if (ri.values.size() >= 1 && ri.values.get(0).size() >= 1) sym.SymName = ReturnCorrectedSymptom(ri.values.get(0).get(0).trim());
                    sym.id = ri.id;
                    symptoms.set(sym.id, sym);
                    symptom_cnt++;
                    if (fill_hash) symptom_hashtable.put(sym.SymName, sym.id);
                    }
                }
                catch (Exception e) {
                    Logger.AddEntryToLog("Error in symptom value: "+syms.get(x)+" in file: "+this.symptom_file_name+" in repertory "+this.name);
                }
            }
            // this is used when we e.g. open repertory only temporarily to perform a search in it and close it again
            if (!read_tree) return;
            // generate the tree structure if it does not exist
            if (this.symptomtree_file_name != null) {
                File f = new File (directory + this.symptomtree_file_name);
                if (!f.exists()) {
                    Logger.AddEntryToLog("Symptom tree file does not exist-generating: "+descriptor_file_name);
                    GenerateTreeStructure();
                    Logger.AddEntryToLog("Finished generating the symptom tree structure");
                    String new_name = directory + this.symptomtree_file_name;
                        // save the generated tree structure into a file
                        SaveTreeStructure(new_name);
                        return;
                    }
                }                    
            if (this.symptomtree_file_name == null) {
                Logger.AddEntryToLog("Symptom tree file does not exist-generating: "+descriptor_file_name);
                GenerateTreeStructure();
                String new_name = Utils.GetFileNameWithoutExt(descriptor_file_name);
                new_name += "_tree";
                String file_content = Utils.ReadFile(descriptor_file_name, "\n");
                File f = new File(new_name);
                if (!f.exists()) {
                     file_content += Database.REPERTORY_DESCRIPTOR_FILE_SYMPTOMTREE_TAG_START + Utils.ExtractFileName(new_name)+Database.REPERTORY_DESCRIPTOR_FILE_SYMPTOMTREE_TAG_END;
                     Utils.WriteFile(descriptor_file_name, file_content, true);
                     // save the generated tree structure into a file
                     SaveTreeStructure(Utils.ExtractFileName(new_name));
                 }
            } 
            else
            {
                ReadTreeStructure();
            }            
        } 
        catch (Exception e) {
            Logger.AddEntryToLog("Error while reading the symptom file name: "+this.symptom_file_name+" for repertory:"+this.name+" Exception = "+e.getMessage());
        }
    }

    /** Returns true if a symptom with this name exists
     * 
     * @param symptom_name
     * @return
     */
    public boolean SymptomExists (String symptom_name) {
        for (int x = 0; x < symptoms.size(); x++) {
            if (symptoms.get(x) == null) continue;
            if (symptoms.get(x).SymName.equalsIgnoreCase(symptom_name)) return true;
        }
        return false;
    }
    
    /** Deletes a remedy addition
     * 
     * @param symid
     * @param remid
     */
    public void DeleteRemedyAddition (int symid, int remid) {
        RemSymptom rs = GetRemSymptom(symid);
        for (int x = 0; x < rs.items.size(); x++) {
            if (rs.items.get(x).rem_id == remid) {
                repertory_changes.DeleteRemedyAddition(symid, remid, rs.items.get(x).rem_grade, GetSymptomName(symid), GetRemedyName(remid), GetRemedySC(remid));
                rs.items.remove(x);
                remsymptoms_string.set(symid, ConvertRemSymptomToString(rs));
                repertory_changed = true;
                symptoms.get(symid).additions -= 1;
                break;
            }
        }
    }
    
    /** Adds a new remedy
     * 
     * @param remname
     * @param remsc
     */
    public boolean AddRemedy (String remname, String remsc) {
        Remedy rem = new Remedy();
        rem.id = (short)remedies.size();
        rem.RemName = remname;
        rem.RemSC = remsc;
        remedies.add(rem);
        if (!IsRemedyNameWellFormed(remname)) return false;
        if (!IsRemedySCWellFormed(remsc)) return false;
        remedy_hashtable.put(remname, rem.id);
        repertory_changes.CreateRemedy(GetRemedyIndex(remname), remname, remsc);
        repertory_changed = true;
        return true;
    }

    /** Sets the new value of the maximum remedy grade
     * 
     * @param new_value
     */
    public void SetMaximumGrade (byte new_value) {
        maximum_grade = new_value;
    }
    
    /** changes a remedy grade in a remedy addition
     * 
     * @param symid
     * @param remid
     * @param grade
     */
    public void SetRemedyGrade (int symid, short remid, byte grade, byte sourceid) {
        repertory_changed = true;
        RemSymptom rs = GetRemSymptom(symid);
        for (int x = 0; x < rs.items.size(); x++) {
            if (rs.items.get(x).rem_id == remid) {
                if (sourceid == -1) {
                    repertory_changes.ChangeRemedyGrade(symid, remid, rs.items.get(x).rem_grade, grade, GetSymptomName(symid), GetRemedyName(remid), GetRemedySC(remid), "OpenRep user", "Customization");
                }
                else
                repertory_changes.ChangeRemedyGrade(symid, remid, rs.items.get(x).rem_grade, grade, GetSymptomName(symid), GetRemedyName(remid), GetRemedySC(remid), GetSource(sourceid).author, GetSource(sourceid).work);                    
                rs.items.get(x).rem_grade = grade;
                if (rs.items.get(x) == null || rs.items.get(x).sources == null) {
                    byte[] new_sources = new byte[1];                    
                    new_sources[0] = sourceid;
                    rs.items.get(x).sources = new_sources;
                }
                else
                {
                   byte[] new_sources = new byte[rs.items.get(x).sources.length + 1];
                   for (int y = 0; y < rs.items.get(x).sources.length; y++) 
                       new_sources[y] = rs.items.get(x).sources[y];
                   new_sources[new_sources.length - 1] = sourceid;
                   rs.items.get(x).sources = new_sources;
                }
                remsymptoms_string.set(symid, ConvertRemSymptomToString(rs));
                break;
            }
        }        
    }
    
    /** Returns a remedy name based on its shortcut
     * 
     * @param remedy_short_cut
     * @return
     */
    public int GetRemedyIndexFromShortCut (String remedy_short_cut) {
        for (int x = 0; x < remedies.size(); x++) {
            if (remedies.get(x) == null) continue;
            if (remedies.get(x).RemSC.equalsIgnoreCase(remedy_short_cut)) return remedies.get(x).id;
        }
        return -1;
    }
    
    /** Adds a new remedy addition
     * 
     * @param symptomid
     * @param remid
     * @param grade
     * @param sourceid
     */
    public void AddRemedyAddition (int symptomid, short remid, byte grade, byte[] sourceids, boolean add_highest_grade) {
        repertory_changed = true;
        int size = remsymptoms_string.size();
        if (remsymptoms_string.size()-1 < symptomid) {
            for (int x = size; x < symptomid+1; x++) {
                remsymptoms_string.add(null);
            }
        }
        if (remsymptoms_string.get(symptomid) != null) {
            int index = -1;
            RemSymptom rs = GetRemSymptom(symptomid);
            for (int x = 0; x < rs.items.size(); x++) {
                if (rs.items.get(x).sources == null || rs.items.get(x).sources.length == 0) {
                    byte[] tmp = new byte[1];
                    tmp[0] = 0;
                    rs.items.get(x).sources = tmp;
                }
            }
            for (int x = 0; x < rs.items.size(); x++) {
                if (rs.items.get(x).rem_id == remid) {
                    index = x;
                    if (!add_highest_grade) break;
                    else
                    {
                        if (rs.items.get(x).rem_grade < grade) {
                            rs.items.get(x).rem_grade = grade;
                            break;
                        }
                    }
                }
            }
            if (index != -1) {
                ArrayList<Byte> rem_sources = new ArrayList();
                if (rs.items.get(index).sources != null && rs.items.get(index).sources.length != 0) {
                    for (int x = 0; x < rs.items.get(index).sources.length; x++) {
                        rem_sources.add(rs.items.get(index).sources[x]);
                    }
                }
                if (sourceids != null && sourceids.length != 0) {
                    for (int x = 0; x < sourceids.length; x++) {
                        if (!rem_sources.contains(sourceids[x])) rem_sources.add(sourceids[x]);
                    }
                }
                rs.items.get(index).sources = new byte[rem_sources.size()];
                for (int x = 0; x < rs.items.get(index).sources.length; x++) 
                    rs.items.get(index).sources[x] = rem_sources.get(x);
                remsymptoms_string.set(symptomid, ConvertRemSymptomToString(rs));
                symptoms.get(symptomid).additions = (short) rs.items.size();
            }
        if (index == -1)
        {
            rs.id = symptomid;
            if (rs.items == null) rs.items = new ArrayList();
        
            RemSymptomItem rsi = new RemSymptomItem();
            rsi.rem_id = remid;
            rsi.rem_grade = grade;
                ArrayList<Byte> rem_sources = new ArrayList();
                if (sourceids != null && sourceids.length != 0) {
                    for (int x = 0; x < sourceids.length; x++) {
                        if (!rem_sources.contains(sourceids[x])) rem_sources.add(sourceids[x]);
                    }
                }
                rsi.sources = new byte[rem_sources.size()];
                for (int x = 0; x < rsi.sources.length; x++) 
                    rsi.sources[x] = rem_sources.get(x);
                rs.items.add(rsi);
                ArrayList<String> authors = new ArrayList();
                ArrayList<String> works = new ArrayList();
                    for (int x = 0; x < rsi.sources.length; x++) {
                        if (rsi.sources[x] != -1) {
                            authors.add(GetSource(rsi.sources[x]).author);
                            works.add(GetSource(rsi.sources[x]).work);                    
                        }
                    }
                ArrayList<Byte> source_id = new ArrayList();
                for (int x = 0; x < rem_sources.size(); x++)
                    source_id.add(rem_sources.get(x));
                remsymptoms_string.set(symptomid, ConvertRemSymptomToString(rs));
                symptoms.get(symptomid).additions = (short) rs.items.size();
                repertory_changes.AddRemedyAddition(symptomid, remid, grade, source_id, GetSymptomName(symptomid), GetRemedyName(remid), GetRemedySC(remid), authors, works);
            }        
        }
        else
        {
            RemSymptom rs = new RemSymptom();
            rs.id = symptomid;
            rs.items = new ArrayList();            
        
            RemSymptomItem rsi = new RemSymptomItem();
            rsi.rem_id = remid;
            rsi.rem_grade = grade;
                ArrayList<Byte> rem_sources = new ArrayList();
                if (sourceids != null && sourceids.length != 0) {
                    for (int x = 0; x < sourceids.length; x++) {
                        if (!rem_sources.contains(sourceids[x])) rem_sources.add(sourceids[x]);
                    }
                }
                rsi.sources = new byte[rem_sources.size()];
                for (int x = 0; x < rsi.sources.length; x++) 
                    rsi.sources[x] = rem_sources.get(x);
                rs.items.add(rsi);
                ArrayList<String> authors = new ArrayList();
                ArrayList<String> works = new ArrayList();
                    for (int x = 0; x < rsi.sources.length; x++) {
                        if (rsi.sources[x] != -1) {
                            authors.add(GetSource(rsi.sources[x]).author);
                            works.add(GetSource(rsi.sources[x]).work);                    
                        }
                    }
                ArrayList<Byte> source_id = new ArrayList();
                for (int x = 0; x < rem_sources.size(); x++)
                    source_id.add(rem_sources.get(x));
                remsymptoms_string.set(symptomid, ConvertRemSymptomToString(rs));
                symptoms.get(symptomid).additions = (short) rs.items.size();
                repertory_changes.AddRemedyAddition(symptomid, remid, grade, source_id, GetSymptomName(symptomid), GetRemedyName(remid), GetRemedySC(remid), authors, works);            
            }    
        }
        
    /** Returns whether the repertory was changed 
     * 
     * @return
     */
    public boolean GetRepertoryChanged () {
        return repertory_changed;
    }
    
    /** Generic routine that reads any repetory file (except descriptor)
     * 
     * @param data_line
     * @return
     */
    public ReadItem ReadData (String data_line) {
        ReadItem result = new ReadItem();
        // the first string is an id delimited by the character FILE_ID_SEPARATOR
        int pos = 0;
        int prev_pos = 0;
        int pos_text1 = 0;
        int pos_data_val = 0;
        int pos_multiple = 0;
        ArrayList<String> values_in_batch = new ArrayList();
        try {
            pos = data_line.indexOf(Utils.FILE_ID_SEPARATOR);
            result.id = Integer.parseInt(data_line.substring(prev_pos, pos));
            prev_pos = pos+1;
        }
        catch (Exception e) {
            return null;
        }
        
        boolean first_run = true;
        values_in_batch.clear();
        while (pos_text1 != -1 || pos_data_val != -1 || pos_multiple != -1) {            
            // the next string can be either delimited by " - it is a string, in which case search for start " and end "
            // or a number in which search for # or ; - which comes first
            if (first_run == false && pos_text1 != -1 || first_run)
            pos_text1 = data_line.indexOf(Utils.FILE_TEXT_VALUE_DELIM, prev_pos);
            if (first_run == false && pos_data_val != -1 || first_run)
            pos_data_val = data_line.indexOf(Utils.FILE_VALUE_SEPARATOR, prev_pos);
            if (first_run == false && pos_multiple != -1 || first_run)
            pos_multiple = data_line.indexOf(Utils.FILE_MULTIPLE_VALUE_SEPARATOR, prev_pos);
            first_run = false;
            // the value is the "
            if (pos_text1 != -1 && (pos_data_val > pos_text1 || pos_data_val == -1) && (pos_multiple > pos_text1 || pos_multiple == -1)) {
                int pos_text2 = data_line.indexOf(Utils.FILE_TEXT_VALUE_DELIM, pos_text1+1);
                // unclosed " tag - invalid values
                if (pos_text2 == -1) return null;            
                values_in_batch.add(data_line.substring(prev_pos+1, pos_text2));
                prev_pos = pos_text2+1;
                // there is the # delimiter after this string - move one space to the right
                if (pos_text2+1 < data_line.length() && data_line.charAt(pos_text2+1) == Utils.FILE_VALUE_SEPARATOR) prev_pos += 1;
                if (pos_text2+1 < data_line.length() && data_line.charAt(pos_text2+1) == Utils.FILE_MULTIPLE_VALUE_SEPARATOR) {
                    prev_pos += 1;
                    result.values.add(values_in_batch);
                    values_in_batch = new ArrayList();
                }
            }
            else
            // the value is # - return the string before this tag
            if (pos_data_val != -1 && (pos_data_val < pos_text1 || pos_text1 == -1) && (pos_data_val < pos_multiple || pos_multiple == -1)) {
                values_in_batch.add(data_line.substring(prev_pos, pos_data_val));
                prev_pos = pos_data_val+1;
            }
            else
            // it is the multiple delimiter - 
            if (pos_multiple != -1 && (pos_data_val == -1 || pos_multiple < pos_data_val) && (pos_text1 == -1 || pos_multiple < pos_text1)) {
                values_in_batch.add(data_line.substring(prev_pos, pos_multiple));
                result.values.add(values_in_batch);
                values_in_batch = new ArrayList();
                prev_pos = pos_multiple+1;
                if (prev_pos >= data_line.length()) break;                
            }
            if (prev_pos >= data_line.length() && values_in_batch.size() != 0) result.values.add(values_in_batch);
        }
        return result;
    }
       
    /** Reads the list of main remedies, if the file does not exist, regenerates
     * 
     */
    public void ReadMainSymptoms() {
        try{
            // the file does not exist => regenerate
            if (this.mainsymptom_file_name == null || (new File(directory+ this.mainsymptom_file_name).exists() == false)) {
                ReadSymptoms(false, false);
                GenerateMainSymptomFile(directory+ this.mainsymptom_file_name);
            }
            main_symptoms = Utils.ReadFile(directory+ this.mainsymptom_file_name);
            for (int x = 0; x < main_symptoms.size(); x++) {
                if (main_symptoms.get(x).trim().equals("")) {
                    main_symptoms.remove(x);
                }
            }                
            Logger.AddSuccessEntry(Logger.Operation_ReadMainSymptoms, "");
        } catch (Exception e) {
            Logger.AddFailureEntry(Logger.Operation_ReadMainSymptoms, e.getMessage());
        }
    }
    
    /** Reads the list of remedies from a file
     * 
     */
    public void ReadRemedies() {
        try{
            remedies = new ArrayList();
            ArrayList<String> rems = Utils.ReadFile(directory + this.remedy_file_name);
            ReadItem ri;
            int error_cnt = 0;
            for (int x = 0; x < rems.size(); x++) {                
                try{
                    if(rems.get(x) != "\n") {
                    rems.set(x, rems.get(x).replace("\n", ""));
                    ri = ReadData(rems.get(x));
                    int rem_size = remedies.size();
                    for (int y = 0; y < ri.id - (rem_size) + 1; y++) {
                        remedies.add(null);
                    }
                    Remedy rem = new Remedy();
                    rem.id = (short)ri.id;
                    if (ri.values.size() >= 1 && ri.values.get(0).size() >= 1) rem.RemName = ri.values.get(0).get(0);
                    if (ri.values.size() >= 1 && ri.values.get(0).size() >= 2) rem.RemSC = ri.values.get(0).get(1);
                    remedies.set(ri.id, rem);
                    ri = null;
                    }
                }
                catch (Exception e) {
                    error_cnt++;
                    Logger.AddEntryToLog("Error in remedy value: "+rems.get(x)+" in file: "+this.remedy_file_name+" in repertory "+this.name);
                }
            }
            if (error_cnt == 0) Logger.AddSuccessEntry(Logger.Operation_ReadRemedies, "");
            else
            Logger.AddFailureEntry(Logger.Operation_ReadRemedies, "error_cnt="+error_cnt);
        } 
        catch (Exception e) {
            Logger.AddFailureEntry(Logger.Operation_ReadRemedies, e.getMessage());
        }
    }

    /** Reads the list of sources from a file
     * 
     */
    public void ReadSources() {
        try{
            ArrayList<String> srcs = Utils.ReadFile(directory + this.source_file_name);
            ReadItem ri;
            for (int x = 0; x < srcs.size(); x++) {                
                try{
                    if (srcs.get(x) != "\n") {
                    //srcs.set(x, srcs.get(x).replace("\n", ""));
                    ri = ReadData(srcs.get(x));
                    int rem_size = sources.size();
                    for (int y = 0; y < ri.id - (rem_size) + 1; y++) {
                        sources.add(null);
                    }
                    Source src = new Source();
                    src.id = (short) ri.id;
                    if (ri.values.size() >= 1 && ri.values.get(0).size() >= 1) src.author = ri.values.get(0).get(0);
                    if (ri.values.size() >= 1 && ri.values.get(0).size() >= 2) src.work = ri.values.get(0).get(1);
                    if (ri.values.size() >= 1 && ri.values.get(0).size() >= 3) src.shortcut = ri.values.get(0).get(2);
                    sources.set(ri.id, src);
                    ri = null;
                    }
                }
                catch (Exception e) {
                    Logger.AddEntryToLog("Error in source value: "+srcs.get(x)+" in file: "+this.source_file_name+" in repertory "+this.name);
                }
            }
        } 
        catch (Exception e) {
            Logger.AddEntryToLog("Error while reading the source file name: "+this.source_file_name+" for repertory:"+this.name+" Exception = "+e.getMessage());
        }
    }    
    
    /** Reads the list of symptom references from a file
     * 
     */
    public void ReadReferences() {
        try{
            ArrayList<String> refs = Utils.ReadFile(directory + this.reference_file_name);
            for (int x = 0; x < refs.size(); x++) {
                ReadItem ri;
                try{
                    if(refs.get(x) != "\n") {
                    refs.set(x, refs.get(x).replace("\n", ""));
                    ri = ReadData(refs.get(x));
                    int rem_size = references.size();
                    for (int y = 0; y < ri.id - (rem_size) + 1; y++) {
                        references.add(null);
                    }
                    SymptomReference ref = new SymptomReference();
                    ref.id = ri.id;
                    int[] temp = new int[ri.values.size()];
                    for (int y = 0; y < ri.values.size(); y++) {
                        try {
                            temp[y] = Integer.parseInt(ri.values.get(y).get(0));
                        } 
                        catch (Exception e) {                            
                        }
                    }
                    ref.references = temp;
                    references.set(ref.id, ref);
                    }
                }
                catch (Exception e) {
                    Logger.AddEntryToLog("Error in reference value: "+refs.get(x)+" in file: "+this.reference_file_name+" in repertory "+this.name);
                }
            }
        } 
        catch (Exception e) {
            Logger.AddEntryToLog("Error while reading the reference file name: "+this.reference_file_name+" for repertory:"+this.name+" Exception = "+e.getMessage());
        }
    }    
    
    /** The repertory is considered to be ok, if it has:
     *  - name
     *  - author
     *  - descriptor_file
     *  - remedy_file
     *  - remsymptom_file
     *  - symptom_file
     * 
     * @return
     */
    public boolean IsRepertoryOK(){
        if (author == null  || descriptor_file_name == null  || name == null || remedy_file_name == null || 
            remsymptom_file_name == null || symptom_file_name == null) return false;
        else
        return true;
    }
    
    /** Returns the prefix of the repertory descriptor file. (e.g. for a file /home/kent.rd returns /home/kent
     * 
     * @return
     */
    public String GetRepertoryDescriptorFilePrefix () {
        return Utils.ExtractFilePathWithoutExtension(this.descriptor_file_name);
    }
    
    /** Reads the descriptor file (file containing the information about remedy file name, symptom file name,
     *  name, author, etc. This routine also extracts the directory.
     * 
     * @param file_name
     */
    private void ReadRepertoryDescriptorFile (String file_name) {
        this.descriptor_file_name = file_name;
        this.directory = Utils.GetDirectory(file_name);
        if (this.directory == null) {
            Logger.AddEntryToLog("Could not extract directory name for file name: "+file_name+" directory separator is: "+Utils.GetDirectorySeparator());
            return;
        }
        try{
            String file_content = Utils.ReadFile(file_name, "\n");
            this.name = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_NAME_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_NAME_TAG_END, 0);
            if (this.name == null) {
                Logger.AddEntryToLog("Repertory name not found in the repertory descriptor file: "+file_name);
            }
            this.remedy_file_name = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_REMEDY_NAME_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_REMEDY_NAME_TAG_END, 0);
            if (this.remedy_file_name == null) {
                Logger.AddEntryToLog("Remedy file name not found in the repertory descriptor file: "+file_name);
            }            
            this.symptom_file_name = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_SYMPTOM_NAME_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_SYMPTOM_NAME_TAG_END, 0);
            if (this.symptom_file_name == null) {
                Logger.AddEntryToLog("Symptom file name not found in the repertory descriptor file: "+file_name);
            }            
            this.symptomtree_file_name = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_SYMPTOMTREE_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_SYMPTOMTREE_TAG_END, 0);
            this.mainsymptom_file_name = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_MAINSYMPTOM_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_MAINSYMPTOM_TAG_END, 0);
            this.short_cut = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_SHORTCUT_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_SHORTCUT_TAG_END, 0);
            if (this.short_cut == null || this.short_cut.equals("")) this.short_cut = this.name;
            try{
                this.short_cut = this.short_cut.trim();
            }
            catch (Exception e) {}
            this.remsymptom_file_name = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_REMSYMPTOM_NAME_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_REMSYMPTOM_NAME_TAG_END, 0);
            if (this.remsymptom_file_name == null) {
                Logger.AddEntryToLog("Remsymptom file name not found in the repertory descriptor file: "+file_name);
            }                        
            this.author = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_AUTHOR_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_AUTHOR_TAG_END, 0);
            if (this.author == null) {
                Logger.AddEntryToLog("Author name not found in the repertory descriptor file: "+file_name);
            }
            this.reference_file_name = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_REFERENCES_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_REFERENCES_TAG_END, 0);
            if (this.reference_file_name == null) {
                Logger.AddEntryToLog("References file name not found in the repertory descriptor file: "+file_name);
            }
            this.source_file_name = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_SOURCES_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_SOURCES_TAG_END, 0);
            if (this.source_file_name == null) {
                Logger.AddEntryToLog("Sources file name not found in the repertory descriptor file: "+file_name);
            }
            String max_grade = Utils.ReadTag(file_content, Database.REPERTORY_DESCRIPTOR_FILE_MAXGRADE_TAG_START, Database.REPERTORY_DESCRIPTOR_FILE_MAXGRADE_TAG_END, 0);
            try {
                maximum_grade = (byte) Integer.parseInt(max_grade);
                if (maximum_grade <= 0) {
                    maximum_grade = -1;
                    Logger.AddEntryToLog("Maximum grade value not valid... regenerating ");
                }
            } catch (Exception e) {
                maximum_grade = -1;
                Logger.AddEntryToLog("Maximum grade value not valid... regenerating ");                
            }
        }
        catch (Exception e) {
                Logger.AddEntryToLog("Error while reading the file: "+file_name+" exception="+e.getMessage());
        }
    }    
        
    /** Returns the remedy shortcut based on the passed remedy index
     * 
     * @param remedy_index remedy id
     * @return
     */
    public String GetRemedySC (int remedy_index) {
        try {
         return remedies.get(remedy_index).RemSC;
        } catch (Exception e) {
            System.out.println("!!! "+remedy_index);
            return null;
        }
    }
    
    /** Sorts the arraylist containing symptoms in format (remgrade) remsc
     * 
     * @param al
     */
    public static ArrayList<String> SortRemSymptomArrayList(ArrayList<String> al) {
      Collections.sort(al);
      if (al.size() == 0) return al;
      ArrayList<String> resorted_al = new ArrayList();
      int x = al.size()-1;
      while (true) {          
          String symstart = al.get(x).substring(0, al.get(x).indexOf(Utils.REMSYMPTOM_GRADE_END));
          int start = x;
          for (int y = x; y >= 0; y--) {
              if (al.get(y).startsWith(symstart)) start = y;
              else
              break;
          }
          for (int y = start; y <= x; y++) {
              resorted_al.add(al.get(y));
          }
          x = start-1;
          if (x < 0) break;
      }
      return resorted_al;        
    }
    
    /** Returns the remedy shortcut for the remedysymptoms tree in the format "(grade) remsc"
     * 
     * @param symptom_name name of the symptoms for which the remedysymptoms should be returned
     * @return
     */
    public ArrayList<String> GetRemSymptoms_SC (String symptom_name) {
      int symptom_idx = GetSymptomIndex(symptom_name);
      ArrayList<String> al = new ArrayList();
      RemSymptom di = GetRemSymptom(symptom_idx);
      if (di == null || di.items == null || di.items.size() == 0) return al;
      for (int x = 0; x < di.items.size(); x++) {          
          al.add(Utils.REMSYMPTOM_GRADE_START+di.items.get(x).rem_grade+Utils.REMSYMPTOM_GRADE_END+GetRemedySC(di.items.get(x).rem_id));
      }
      SortRemSymptomArrayList(al);
      return al;
    }

    /** Adds remedy additions from one symptom to another as highest grades
     * 
     * @param orig_symptom_index
     * @param dest_symptom_index
     * @return
     */
    public void AddRemedyAdditions_highestgrade (int orig_symptom_index, int dest_symptom_index) {
        if (orig_symptom_index == -1 || dest_symptom_index == -1) return;
        RemSymptom rso = GetRemSymptom(orig_symptom_index);
        for (int x = 0; x < rso.items.size(); x++) {
            AddRemedyAddition(dest_symptom_index, rso.items.get(x).rem_id, rso.items.get(x).rem_grade, rso.items.get(x).sources, true);
        }
    }    
    
    /** Adds remedy additions from one symptom to another as grade 1
     * 
     * @param orig_symptom_index
     * @param dest_symptom_index
     * @return
     */
    public void AddRemedyAdditions_grade1 (int orig_symptom_index, int dest_symptom_index) {
        if (orig_symptom_index == -1 || dest_symptom_index == -1) return;
        RemSymptom rso = GetRemSymptom(orig_symptom_index);
        for (int x = 0; x < rso.items.size(); x++) {
            AddRemedyAddition(dest_symptom_index, (short)rso.items.get(x).rem_id, (byte)1, rso.items.get(x).sources, false);
        }
    }
    
    /** Changes the name of the symptom and recalculates the fraction names of symptom that are the children of
     *  this symptom
     * 
     * @param prev_name
     * @param new_name
     * @return true if changes were successfull or false if the new_name already exists
     */
    public boolean RenameSymptom (String prev_name, String new_name, boolean rename_children) {
        int index = GetSymptomIndex(prev_name);
        new_name = ReturnCorrectedSymptom(new_name);
        if (GetSymptomIndex(new_name) != -1) {
            int new_index = GetSymptomIndex(new_name);
            AddRemedyAdditions_highestgrade(index, GetSymptomIndex(new_name));
            for (int x = 0 ; x < references.size(); x++) {
                if (references.get(x) == null) continue;
                if (references.get(x).id == index) {
                    for (int y = 0; y < references.size(); y++) {
                        if (references.get(y).id == new_index) {
                            for (int z = 0; z < references.get(x).references.length; z++) {
                                if (!Utils.ARRAY_Contains(references.get(x).references[z], references.get(y).references)) {
                                    references.get(y).references = Utils.ARRAY_AddValueToArray(references.get(x).references[z], references.get(y).references);
                                }
                                break;
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            repertory_changed = true;
            DeleteSymptom(prev_name);
        }
        else {
            symptoms.get(index).SymName = new_name;
            symptom_hashtable.remove(prev_name);
            symptom_hashtable.put(new_name, index);
            int new_sym_id = GetSymptomIndex(new_name);
            repertory_changes.RenameSymptom(prev_name, new_name, new_sym_id);
            GetSymptom(new_sym_id).parent_id = GetSymptomParent(new_name);            
            GetSymptom(new_sym_id).fraction_name = GetSymptomFractionName(GetSymptom(new_sym_id).SymName, GetSymptom(GetSymptom(new_sym_id).parent_id).SymName);
        }
        if (rename_children) {
            for (int x = 0; x < symptoms.size(); x++) {
                if (symptoms.get(x) == null || symptoms.get(x).id == index) continue;
                if (symptoms.get(x).SymName.startsWith(prev_name)) {
                    String temp_name = new_name + symptoms.get(x).SymName.substring(prev_name.length(), symptoms.get(x).SymName.length());
                    if (GetSymptomIndex(temp_name) != -1) {
                        AddRemedyAdditions_highestgrade(index, GetSymptomIndex(temp_name));
                        int new_index = GetSymptomIndex(temp_name);
                        for (int a = 0 ; a < references.size(); a++) {
                            if (references.get(a) == null) continue;
                            if (references.get(a).id == index) {
                                for (int y = 0; y < references.size(); y++) {
                                    if (references.get(y).id == new_index) {
                                        for (int z = 0; z < references.get(x).references.length; z++) {
                                            if (!Utils.ARRAY_Contains(references.get(a).references[z], references.get(y).references)) {
                                                references.get(y).references = Utils.ARRAY_AddValueToArray(references.get(a).references[z], references.get(y).references);
                                            }
                                            break;
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        repertory_changed = true;
                        DeleteSymptom(prev_name);
                    }
                    else {
                        String temps = symptoms.get(x).SymName;
                        symptoms.get(x).SymName = temp_name;
                        symptom_hashtable.remove(temps);
                        symptom_hashtable.put(temp_name, x);                        
                        int new_sym_id = GetSymptomIndex(temp_name);
                        repertory_changes.RenameSymptom(temps, temp_name, new_sym_id);
                        GetSymptom(new_sym_id).parent_id = GetSymptomParent(temp_name);
                        GetSymptom(new_sym_id).fraction_name = GetSymptomFractionName(GetSymptom(new_sym_id).SymName, GetSymptom(GetSymptom(new_sym_id).parent_id).SymName);
                    }
                }
            }
        }    
        //GenerateTreeStructure();                
        repertory_changed = true;
        return true;
    }
    
    /** Renames the remedy specified by the remid to a new name and sc. Returns false if the remed ycannot be renamed
     * 
     * @param remid
     * @param new_name
     * @param new_sc
     * @return
     */
    public boolean RenameRemedy (int remid, String new_name, String new_sc) {
        try {
            for (int x = 0; x < remedies.size(); x++) {
                if (remedies.get(x) == null) continue;
                if (remedies.get(x).RemName.equalsIgnoreCase(new_name) && x != remid) {
                    return false;
                }
                if (remedies.get(x).RemSC.equalsIgnoreCase(new_sc) && x != remid) {
                    return false;
                }
            }
            repertory_changes.RenameRemedy(remedies.get(remid).RemName, remedies.get(remid).RemSC, new_name, new_sc);
            remedies.get(remid).RemName = new_name;
            remedies.get(remid).RemSC = new_sc;
            repertory_changed = true;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Returns a shallow copy of sources
     * 
     * @return
     */
    public ArrayList<Source> GetSources () {
        return sources;
    }
    
    /** Returns a deep copy (stringlist) of full remedy names
     * 
     * @return
     */
    public ArrayList<String> GetRemedyNames () {
        ArrayList<String> result = new ArrayList();
        for (int x = 0; x < remedies.size(); x++) {
            if (remedies.get(x) != null) result.add(remedies.get(x).RemName);
        }
        Collections.sort(result);
        return result;
    }
    
    /** Deletes a symptom from the repertory with the specified name, deletes its remsymptoms and changes the
     *  symptom tree so, that all of the symptom's children are attached to the parent of the deleted symptom
     *  and it recalculates the symptom fragments of the re-attached children
     * 
     * @param symptom
     */
    public boolean DeleteSymptom (String symptom) {
        int index = GetSymptomIndex(symptom);
        if (index == -1) return false;
        // delete this symptom from symptom references
        repertory_changes.DeleteSymptom(symptom, GetSymptomIndex(symptom));        
        symptoms.set(index, null);        
        remsymptoms_string.set(index, null);
        symptom_hashtable.remove(symptom);
        repertory_changed = true;
        return true;
    }
    
    /** Returns RemSymptom from the specified symptomid
     * 
     * @param symid
     * @return
     */
    public RemSymptom GetRemSymptom (int symid) {
        RemSymptom remsym = new RemSymptom();
        if (symid == -1 || remsymptoms_string.get(symid) == null) return null;
        else
        {
            ReadItem ri = ReadData(remsymptoms_string.get(symid));
            remsym.id = ri.id;
            for (int y = 0; y < ri.values.size(); y++) {
                 RemSymptomItem rsi = new RemSymptomItem();
                 try {
                    if (ri.values.get(y).size() >= 1) rsi.rem_id = (short) Integer.parseInt(ri.values.get(y).get(0));
                    if (ri.values.get(y).size() >= 2) rsi.rem_grade = (byte) Integer.parseInt(ri.values.get(y).get(1));
                    if (ri.values.get(y).size() > 2) {
                        byte[] temp = new byte[ri.values.get(y).size() - 2];
                        for (int z = 2; z < ri.values.get(y).size(); z++) 
                            temp[z-2] = (byte) Integer.parseInt(ri.values.get(y).get(z));
                        rsi.sources = temp;
                    }
                    else
                    {
                        byte[] temp = new byte[1];
                        temp[0] = 0;
                        rsi.sources = temp;
                    }
                    if (rsi.rem_grade > maximum_grade) maximum_grade = rsi.rem_grade;
                    remsym.items.add(rsi);
                  }
                 catch (Exception e) {
                     return null;
                 }
            }
            return remsym;
        }
    }

    /** Returns RemSymptom from a string
     * 
     * @param symid
     * @return
     */
    public RemSymptom GetRemSymptom (String symptom_string) {
        if (symptom_string == null || symptom_string.equals("")) return null;
        RemSymptom remsym = new RemSymptom();        
        ReadItem ri = ReadData(symptom_string);
        remsym.id = ri.id;
        for (int y = 0; y < ri.values.size(); y++) {
             RemSymptomItem rsi = new RemSymptomItem();
             try {
                if (ri.values.get(y).size() >= 1) rsi.rem_id = (short) Integer.parseInt(ri.values.get(y).get(0));
                if (ri.values.get(y).size() >= 2) rsi.rem_grade = (byte) Integer.parseInt(ri.values.get(y).get(1));
                if (ri.values.get(y).size() > 2) {
                    int[] temp = new int[ri.values.get(y).size() - 2];
                    for (int z = 2; z < ri.values.get(y).size(); z++) 
                        temp[z-2] = Integer.parseInt(ri.values.get(y).get(z));
                 }
                 if (rsi.rem_grade > maximum_grade) maximum_grade = rsi.rem_grade;
                 remsym.items.add(rsi);
              }
              catch (Exception e) {
                 return null;
              }
         }
         return remsym;
     }    
    
    /** Returns the number of remedy additions in a symptom
     * 
     * @param symptom
     * @return
     */
    public int GetRemedyAdditions (String symptom) {
        System.out.println("GetSI="+symptom);
        int index = GetSymptomIndex(symptom);
        System.out.println("index = "+index);
        if (symptoms.get(index) == null) return 0;
        else
        return symptoms.get(index).additions;
    }
    
    /** Returns the RemedySymptom values for the specified Symptom
     * 
     * @param symptom_name
     * @return
     */
    public ArrayList<SelectedRemSymptom> GetRemedySymptom (String symptom_name) {
        ArrayList<SelectedRemSymptom> result = new ArrayList();
        int symptom_index = GetSymptomIndex(symptom_name);
        RemSymptom rs = GetRemSymptom(symptom_index);
        if (rs == null || rs.items == null) return new ArrayList();
        for (int x = 0; x < rs.items.size(); x++) {
            SelectedRemSymptom srs = new SelectedRemSymptom();
            srs.RemGrade = (double)rs.items.get(x).rem_grade / (double)maximum_grade;
            srs.Remname = remedies.get(rs.items.get(x).rem_id).RemName;
            srs.RemSC = remedies.get(rs.items.get(x).rem_id).RemSC;
            result.add(srs);
        }
        return result;
    }    
    
    /** Returns the RemedySymptom values for the specified Symptom
     * 
     * @param symptom_name
     * @return
     */
    public ArrayList<SelectedRemSymptom> GetRemedySymptom (int symptom_id) {
        ArrayList<SelectedRemSymptom> result = new ArrayList();
        // repertory is opened => read from memory
        if (status_opened) {
            RemSymptom rs = GetRemSymptom(symptom_id);
            if (rs == null || rs.items == null) return new ArrayList();
            for (int x = 0; x < rs.items.size(); x++) {
                SelectedRemSymptom srs = new SelectedRemSymptom();
                srs.RemGrade = (double)rs.items.get(x).rem_grade / (double)maximum_grade;
                srs.Remname = remedies.get(rs.items.get(x).rem_id).RemName;
                srs.RemSC = remedies.get(rs.items.get(x).rem_id).RemSC;
                result.add(srs);
            }
        }
        else
        {
            // repertory is closed => read from a file
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(directory + remsymptom_file_name));
                String temp = "";
                while (temp != null) {
                    temp = br.readLine();
                    if (temp == null) break;
                    if (temp.startsWith(symptom_id+String.valueOf(Utils.FILE_ID_SEPARATOR))) {
                        RemSymptom rs = GetRemSymptom(temp);
                        if (rs == null || rs.items == null) return new ArrayList();
                        for (int x = 0; x < rs.items.size(); x++) {
                            SelectedRemSymptom srs = new SelectedRemSymptom();
                            srs.RemGrade = (double)rs.items.get(x).rem_grade / (double)maximum_grade;
                            srs.Remname = remedies.get(rs.items.get(x).rem_id).RemName;
                            srs.RemSC = remedies.get(rs.items.get(x).rem_id).RemSC;
                            result.add(srs);
                        }
                        break;
                    }
                }
            }
            catch (Exception e) {                
           }
            finally {
                try {
                    if (br != null) br.close();
                }
                catch (Exception ex) {                    
                }
            }
        }
        return result;
    }        
 
    /** Adds a new source
     * 
     * @param author
     * @param work
     */
    public boolean AddSource (String author, String work) {
        for (int x = 0; x < sources.size(); x++) {
            if (sources.get(x).author.equalsIgnoreCase(author) && sources.get(x).work.equalsIgnoreCase(work)) return false;
        }
        Source s = new Source();
        s.id = (short) sources.size();
        s.author = author;
        s.work = work;
        sources.add(s);
        return true;
    }
        
    /** Adds a source to the remedysymptom
     * 
     * @param symptomid
     * @param remid
     * @param sourceid
     */
    public void AddRemedySymptomSource (int symptomid, int remid, byte sourceid) {
        RemSymptom rs = GetRemSymptom(symptomid);
        for (int x = 0; x < rs.items.size(); x++) {
            if (rs.items.get(x).rem_id == remid) {
                if (rs.items.get(x).sources.length == 0) {
                    rs.items.get(x).sources = new byte[2];
                    rs.items.get(x).sources[0] = 0;
                    rs.items.get(x).sources[1] = sourceid;
                    if (sourceid == -1) {
                         repertory_changes.AddRemSymptomSource(symptomid, remid, (byte)0, GetSymptomName(symptomid), GetRemedyName(remid), "", "");                        
                    }
                    else
                    repertory_changes.AddRemSymptomSource(symptomid, remid, sourceid, GetSymptomName(symptomid), GetRemedyName(remid), GetSource(sourceid).author, GetSource(sourceid).author);                    
                }
                else
                {
                    rs.items.get(x).sources = Utils.ARRAY_AddValueToArray(sourceid, rs.items.get(x).sources);
                    if (sourceid == -1) {
                         repertory_changes.AddRemSymptomSource(symptomid, remid, (byte)0, GetSymptomName(symptomid), GetRemedyName(remid), "", "");                        
                    }
                    else
                    repertory_changes.AddRemSymptomSource(symptomid, remid, sourceid, GetSymptomName(symptomid), GetRemedyName(remid), GetSource(sourceid).author, GetSource(sourceid).author);
                }
                remsymptoms_string.set(symptomid, ConvertRemSymptomToString(rs));
                return;
            }
        }
    }
    
    /** Deletes a source from the remedysymptom
     * 
     * @param symptomid
     * @param remid
     * @param sourceid
     */
    public void DeleteRemedySymptomSource (int symptomid, int remid, byte sourceid) {
        RemSymptom rs = GetRemSymptom(symptomid);
        for (int x = 0; x < rs.items.size(); x++) {
            if (rs.items.get(x).rem_id == remid) {
                if (rs.items.get(x).sources.length == 0) return;
                    rs.items.get(x).sources = Utils.ARRAY_DeleteValueFromArray(sourceid, rs.items.get(x).sources);
                    repertory_changes.DeleteRemSymptomSource(symptomid, remid, sourceid, GetSymptomName(symptomid), GetRemedyName(remid), GetSource(sourceid).author, GetSource(sourceid).author);
                    remsymptoms_string.set(symptomid, ConvertRemSymptomToString(rs));
                    return;
            }
        }
    }    
    
    /** Saves all the remedies to a file
     * 
     * @param filename
     */
    public boolean SaveRemediesToFile (String filename) {
        ArrayList<String> result = new ArrayList();
        for (int x = 0; x < remedies.size(); x++) {
            if (remedies.get(x) == null) continue;
            String temps = remedies.get(x).id + String.valueOf(Utils.FILE_ID_SEPARATOR)+
                           String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+remedies.get(x).RemName+
                           String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+String.valueOf(Utils.FILE_VALUE_SEPARATOR)+
                           String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+remedies.get(x).RemSC+
                           String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+"\n";
            result.add(temps);
        }
        try{
            Utils.WriteFile(filename, result, true);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /** Saves all the sources to a file
     * 
     * @param filename
     */
    public boolean SaveSourcesToFile (String filename) {
        ArrayList<String> result = new ArrayList();
        for (int x = 0; x < sources.size(); x++) {
            String temps = sources.get(x).id + String.valueOf(Utils.FILE_ID_SEPARATOR)+
                           String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+sources.get(x).author+
                           String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+String.valueOf(Utils.FILE_VALUE_SEPARATOR)+
                           String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+sources.get(x).work+String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+
                           String.valueOf(Utils.FILE_VALUE_SEPARATOR)+String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+sources.get(x).shortcut+String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+"\n";
            result.add(temps);
        }
        try{
            Utils.WriteFile(filename, result, true);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }    
    
    /** Saves all the symptoms to a file
     * 
     * @param filename
     */
    public boolean SaveSymptomsToFile (String filename) {
        ArrayList<String> result = new ArrayList();
        for (int x = 0; x < symptoms.size(); x++) {
            if (symptoms.get(x) == null) continue;
            String temps = symptoms.get(x).id + String.valueOf(Utils.FILE_ID_SEPARATOR)+
                           String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+symptoms.get(x).SymName+
                           String.valueOf(Utils.FILE_TEXT_VALUE_DELIM)+"\n";
            result.add(temps);
        }
        try{
            Utils.WriteFile(filename, result, true);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /** Saves all the references to a file
     * 
     * @param filename
     */
    public boolean SaveReferencesToFile (String filename) {
        ArrayList<String> result = new ArrayList();
        for (int x = 0; x < references.size(); x++) {
            if (references.get(x) == null) continue;
            String temps = references.get(x).id + String.valueOf(Utils.FILE_ID_SEPARATOR);
            for (int y = 0; y < references.get(x).references.length; y++) {
                temps += String.valueOf(references.get(x).references[y]);
                if (y != references.get(x).references.length - 1) temps += String.valueOf(Utils.FILE_MULTIPLE_VALUE_SEPARATOR);
            }
            temps += Utils.FILE_MULTIPLE_VALUE_SEPARATOR+"\n";
            result.add(temps);
        }
        try{
            Utils.WriteFile(filename, result, true);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }    
    /** Converts the RemSymptom to a String
     * 
     * @param rs
     * @return
     */
    public String ConvertRemSymptomToString (RemSymptom rs) {
       String temps = rs.id + String.valueOf(Utils.FILE_ID_SEPARATOR);
       for (int y = 0; y < rs.items.size(); y++) {
           temps += rs.items.get(y).rem_id+String.valueOf(Utils.FILE_VALUE_SEPARATOR)+
                    rs.items.get(y).rem_grade;
           if (rs.items.get(y).sources != null) {
               if (rs.items.get(y).sources.length != 0)
               temps += String.valueOf(Utils.FILE_VALUE_SEPARATOR);
               for (int z = 0; z < rs.items.get(y).sources.length; z++) {
                   temps += rs.items.get(y).sources[z];
                   if (z != rs.items.get(y).sources.length-1) temps += String.valueOf(Utils.FILE_VALUE_SEPARATOR);
                }
            }
            temps += String.valueOf(Utils.FILE_MULTIPLE_VALUE_SEPARATOR);
        }
        return temps;        
    }
    
    /** Saves all the remedysymptoms to a file
     * 
     * @param filename
     */
    public boolean SaveRemSymptomsToFile (String filename) {
        ArrayList<String> result = new ArrayList();
        for (int x = 0; x < remsymptoms_string.size(); x++) {
            if (remsymptoms_string.get(x) == null) continue;
            String temps = remsymptoms_string.get(x);            
            result.add(temps+"\n");
        }
        try{
            Utils.WriteFile(filename, result, true);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }    
    
    /** Saves the whole repertory to files
     * 
     * @param filename
     * @return
     */
    public ArrayList<String> SaveRepertoryToFile (String filename) {
        ArrayList<String> result = new ArrayList();
        String main_file = Utils.ExtractFilePathWithoutExtension(filename);
        if (!SaveRepertoryDescriptorToFile(filename)) result.add("Error while saving the repertory descriptor file: "+filename);
        if (!SaveReferencesToFile(main_file+"_ref")) result.add("Error while saving the repertory references file: "+main_file+"_ref");
        if (!SaveRemSymptomsToFile(main_file+"_remsym")) result.add("Error while saving the repertory remedy_symptoms file: "+main_file+"_remsym");
        if (!SaveRemediesToFile(main_file+"_rem")) result.add("Error while saving the repertory remedy file: "+main_file+"_rem");
        if (!SaveSourcesToFile(main_file+"_src")) result.add("Error while saving the repertory sources file: "+main_file+"_src");
        if (!SaveSymptomsToFile(main_file+"_sym")) result.add("Error while saving the repertory symptoms file: "+main_file+"_sym");
        if (!SaveHistoryToFile(main_file+"_hist")) result.add("Error while saving the history file: "+main_file+"_hist");
        GenerateTreeStructure();
        if (result.size() == 0) {
            repertory_changed = false;
        }
        return result;
    }

    /** Returns the list of symptoms
     * 
     * @return
     */
    public ArrayList<Symptom> GetSymptoms () {
        return symptoms;
    }
    
    /** Saves the history of changes to a file
     * 
     * @param filename
     * @return
     */
    public boolean SaveHistoryToFile (String filename) {
        return repertory_changes.SaveChangesToHistoryFile(filename);
    }
    
    /** Sets the name of the author
     * 
     * @param author
     */
    public void SetAuthor (String author) {
        this.author = author;
        repertory_changed = true;        
    }
    
    /** Returns the name of the author
     * 
     * @param author
     */
    public String GetAuthor () {
        return this.author;
    }    
    
    /** Sets the name of the repertory
     * 
     * @param author
     */
    public void SetName (String name) {
        this.name = name;
        repertory_changed = true;
    }    
    
    /** Sets the shortcut of the repertory
     * 
     * @param author
     */
    public void SetShortCut (String sc) {
        this.short_cut = sc;
        repertory_changed = true;        
    }        
    
    /** Returns the name of the history file
     * 
     * @return
     */
    public String GetHistoryFileName () {
        String main_file = this.directory + Utils.ExtractFileNameWithoutExtension(descriptor_file_name);
        return main_file+"_hist";
    }
    
    /** Saves the repertory descriptor to a file
     * 
     * @param filename
     */
    public boolean SaveRepertoryDescriptorToFile (String filename) {
        if (!Utils.ExtractFileExtension(filename).equalsIgnoreCase("rd")) {
            filename += Utils.FILE_EXTENSION_SEPARATOR+"rd";
        }
        ArrayList<String> data = new ArrayList();
        String main_file = Utils.ExtractFileNameWithoutExtension(filename);
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_AUTHOR_TAG_START+this.author+Database.REPERTORY_DESCRIPTOR_FILE_AUTHOR_TAG_END+"\n");
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_COMBINATIONS_TAG_START+main_file+"_comb"+Database.REPERTORY_DESCRIPTOR_FILE_COMBINATIONS_TAG_END+"\n");
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_HISTORY_TAG_START+main_file+"_hist"+Database.REPERTORY_DESCRIPTOR_FILE_HISTORY_TAG_END+"\n");
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_NAME_TAG_START+this.name+Database.REPERTORY_DESCRIPTOR_FILE_NAME_TAG_END+"\n");
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_REFERENCES_TAG_START+main_file+"_ref"+Database.REPERTORY_DESCRIPTOR_FILE_REFERENCES_TAG_END+"\n");
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_REMEDY_NAME_TAG_START+main_file+"_rem"+Database.REPERTORY_DESCRIPTOR_FILE_REMEDY_NAME_TAG_END+"\n");
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_REMSYMPTOM_NAME_TAG_START+main_file+"_remsym"+Database.REPERTORY_DESCRIPTOR_FILE_REMSYMPTOM_NAME_TAG_END+"\n");
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_SOURCES_TAG_START+main_file+"_src"+Database.REPERTORY_DESCRIPTOR_FILE_SOURCES_TAG_END+"\n");
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_SYMPTOM_NAME_TAG_START+main_file+"_sym"+Database.REPERTORY_DESCRIPTOR_FILE_SYMPTOM_NAME_TAG_END+"\n");
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_MAXGRADE_TAG_START+GetMaximumGrade()+Database.REPERTORY_DESCRIPTOR_FILE_MAXGRADE_TAG_END+"\n");
        data.add(Database.REPERTORY_DESCRIPTOR_FILE_SHORTCUT_TAG_START + short_cut+ Database.REPERTORY_DESCRIPTOR_FILE_SHORTCUT_TAG_END+"\n");        
        try{
            Utils.WriteFile(filename, data, true);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
 
    /** Adds a superrubric (if needed), adds remedies from other rubrics to it and adds it as a reference to all other 
     *  rubrics.
     *  E.g. "Abdomen, liver" - creates "Abdomen, liver" and adds additions from all symptoms containing "Abdomen, liver"
     *  and adds the "Abdomen, liver" as a reference to all other symptoms
     * 
     */
    public void AddSuperRubric (String symptom, String searchphrase, Database db) {
        String superrubric = symptom.substring(0, symptom.indexOf(Utils.SYMPTOM_FRAGMENT_SEPARATOR)).trim();
            RemSymptom rs = new RemSymptom();
            int symptom_id = -1;
            for (int x = 0; x < symptoms.size(); x++) {
                if (symptoms.get(x) == null) continue;
                if (symptoms.get(x).SymName.equals(symptom)) {
                    symptom_id = x;
                    if (remsymptoms_string.get(x) != null && !remsymptoms_string.get(x).equals(""))
                    rs = GetRemSymptom(remsymptoms_string.get(x));
                    break;
                }
            }
            if (symptom_id == -1) {
                AddSymptom(ReturnCorrectedSymptom(symptom));
                symptom_id = symptoms.size() -1;                
            }
            //for (int x = 0; x < symptoms.size(); x++) {
                ArrayList<SearchSymptom> al = SearchInSymptoms(searchphrase, symptoms, db, -1);
                for (int x = 0; x < al.size(); x++) {
                    if (al.get(x).id != symptom_id) ; AddSymptomReference(al.get(x).id, symptom_id);
                    if (remsymptoms_string.get(al.get(x).id) == null || remsymptoms_string.get(al.get(x).id).equals("")) continue;
                    RemSymptom temprs = GetRemSymptom(remsymptoms_string.get(al.get(x).id));
                    for (int z = 0; z < temprs.items.size(); z++) {
                        boolean fnd = false; 
                        for (int i = 0; i < rs.items.size(); i++) {
                            if (temprs.items.get(z).rem_id == rs.items.get(i).rem_id) {
                                fnd = true;
                                if (temprs.items.get(z).rem_grade > rs.items.get(i).rem_grade) rs.items.get(i).rem_grade = temprs.items.get(z).rem_grade;
                            }
                        }
                        if (!fnd) {
                            RemSymptomItem rsi = new RemSymptomItem();
                            rsi.rem_id = temprs.items.get(z).rem_id;
                            rsi.rem_grade = temprs.items.get(z).rem_grade;
                            rsi.sources = new byte[1];
                            rsi.sources[0] = 0;
                            rs.items.add(rsi);
                        }
                    }
                }
                if (rs.items.size() != 0) {
                    rs.id = symptom_id;
                    remsymptoms_string.set(symptom_id, ConvertRemSymptomToString(rs));
                }
            }
          
    /** Returns a source with specified index
     * 
     * @param srcid
     * @return
     */
    public Source GetSource (int srcid) {
        for (int x = 0; x < sources.size(); x++) {
            if (sources.get(x).id == srcid) {
                return sources.get(x);
            }
        }
        return null;
    }    

    /** Returns the shallow copy of the remedy list
     * 
     * @return
     */
    public ArrayList<Remedy> GetRemedies () {
        return remedies;
    }

    /** Returns true if symptom does not contain any not-allowed characters
     *
     * @param symptom
     * @return
     */
    public static boolean IsSymptoWellFormed(String symptom) {
        if (symptom.indexOf(":") != -1) return false;
        if (symptom.indexOf("\"") != -1) return false;
        if (symptom.indexOf("#") != -1) return false;
        if (symptom.indexOf("|") != -1) return false;
        if (symptom.indexOf("\t") != -1) return false;
        if (symptom.indexOf("\n") != -1) return false;
        return true;
    }

    /** Returns true if symptom does not contain any not-allowed characters
     *
     * @param symptom
     * @return
     */
    public static boolean IsRemedyNameWellFormed(String symptom) {
        if (symptom.indexOf(":") != -1) return false;
        if (symptom.indexOf("#") != -1) return false;
        if (symptom.indexOf("|") != -1) return false;
        if (symptom.indexOf("\t") != -1) return false;
        if (symptom.indexOf("\n") != -1) return false;
        if (symptom.indexOf(";") != -1) return false;
        return true;
    }

    /** Returns true if symptom does not contain any not-allowed characters
     *
     * @param symptom
     * @return
     */
    public static boolean IsRemedySCWellFormed(String symptom) {
        if (symptom.indexOf("#") != -1) return false;
        if (symptom.indexOf(":") != -1) return false;
        if (symptom.indexOf("|") != -1) return false;
        if (symptom.indexOf("\t") != -1) return false;
        if (symptom.indexOf("\n") != -1) return false;
        if (symptom.indexOf(";") != -1) return false;
        if (symptom.indexOf(" ") != -1) return false;
        return true;
    }

    /** Returns the string (coded remsymptom) for the specified symptom_index
     * 
     * @param symptom_index
     * @return
     */
    public String GetRemSymptomString (int symptom_index) {
        return remsymptoms_string.get(symptom_index);
    }
    
    /** Returns -1 if the remedy is not located in the symptom, or valid remedy grade if the remedy is located in this symptom.
     *  !!! Warning, this method performs a quick string seach, so if the format of the string changes, this method must be changed as well !!!
     * 
     * @param remsymptom_string
     * @return
     */
    public int GetRemedyGradeInRemSymptom_String (String remsymptom_string, String remid) {
        if (remsymptom_string == null) return -1;
        int pos = remsymptom_string.indexOf(Utils.FILE_ID_SEPARATOR+remid+Utils.FILE_VALUE_SEPARATOR);
        if (pos == -1) pos = remsymptom_string.indexOf(Utils.FILE_MULTIPLE_VALUE_SEPARATOR+remid+Utils.FILE_VALUE_SEPARATOR);
        if (pos == -1) return -1;
        else
        {
            try {
                // the values 1 and 1 are the lengths of the Utils.FILE_ID_SEPARATOR and Utils.FILE_VALUE_SEPARATOR,
                // which are char, so their length is == 1
                pos += 1+remid.length()+1;
                int pos2 = remsymptom_string.indexOf(Utils.FILE_VALUE_SEPARATOR, pos);
                int pos3 = remsymptom_string.indexOf(Utils.FILE_MULTIPLE_VALUE_SEPARATOR, pos);
                if (pos3 < pos2) pos2 = pos3;
                return Integer.parseInt(remsymptom_string.substring(pos, pos2));
            }
            catch (Exception e) {
                return -1;
            }
        }
    }
  
    /** Generates a file containing the list of main symptoms (e.g. symptoms that do not have a parent)
     *  The list will be empty, if there are end symptoms that do not have a parent.
     * 
     *  Prerequisite is, that that SymptomTree is Generated. Saves a nempty file in case that there are symptoms 
     *  that are leaves and do not have a parent
     * 
     * @param file_name
     */
    public void GenerateMainSymptomFile(String file_name) {
        ArrayList<String> main_symptoms = new ArrayList();
        for (int x = 0; x < symptoms.size(); x++) {
            if (symptoms.get(x) == null) continue;
            if (symptoms.get(x).parent_id == -1) {
                boolean fnd = false;
                for (int y = 0; y < symptoms.size(); y++) {
                    if (y == x) continue;
                    if (symptoms.get(y) == null) continue;
                    if (symptoms.get(y).parent_id == symptoms.get(x).id) {
                        fnd = true;
                        break;
                    }
                }
                if (!fnd) {
                    main_symptoms.clear();
                    break;
                }
                else
                main_symptoms.add(symptoms.get(x).SymName);
            }
        }
        try{
           if (file_name == null) {
                String new_name = Utils.GetFileNameWithoutExt(descriptor_file_name);
                new_name += "_mainsym";
                String file_content = Utils.ReadFile(descriptor_file_name, "\n");
                File f = new File(new_name);
                file_content += Database.REPERTORY_DESCRIPTOR_FILE_SYMPTOMTREE_TAG_START + Utils.ExtractFileName(new_name)+Database.REPERTORY_DESCRIPTOR_FILE_SYMPTOMTREE_TAG_END;
                Utils.WriteFile(descriptor_file_name, file_content, true);            
                Utils.WriteFile(new_name, main_symptoms, '\n', true);
           }
           else
           Utils.WriteFile(file_name, main_symptoms, '\n', true);
        } catch (Exception e) {            
        }
    }
    
    /** Returns the arraylist of unique words in the repertory
     * 
     * @return
     */
    public ArrayList<String> GetUniqueWords () {        
        ArrayList<String> result = new ArrayList();
        for (int x = 0; x < symptoms.size(); x++) {
            if (symptoms.get(x) == null) continue;
            String temp = symptoms.get(x).SymName.toLowerCase();
            int prev_pos = 0;
            for (int y = 0;y < temp.length(); y++) {
                if (IsCharNonAlphabetical(temp.charAt(y))) {
                    String new_temp = temp.substring(prev_pos, y);
                    if (!result.contains(new_temp) && !new_temp.equals("")) {
                        result.add(new_temp);
                    }
                    prev_pos = y+1;                    
                }
            }
            String new_temp = temp.substring(prev_pos, temp.length());
            if (!result.contains(new_temp)) {
                 result.add(new_temp);
            }            
        }
        return result;
    }
  
    /** Returns the list of MainSymptoms
     * 
     * @return
     */
    public ArrayList<String> GetMainSymptoms () {
        return this.main_symptoms;
    }
    
    /** Returns the reversed list of symptoms for a remedy
     * 
     * @param rem_id
     * @return
     */
    public ArrayList<Symptom> GetSymptoms (int rem_id) {
        ArrayList<Symptom> result = new ArrayList();
        for (int x = 0; x < remsymptoms_string.size(); x++) {
            int pos = GetRemedyGradeInRemSymptom_String(remsymptoms_string.get(x), String.valueOf(rem_id));
            if (pos != -1) {
                Symptom rms = new Symptom();
                if (GetSymptom(x) == null) continue;
                rms = GetSymptom(x).DeepCopy();
                rms.grade = (byte) pos;
                result.add(rms);
            }
        }
        return result;
    }

    /** Removes the specified cross-reference
     * 
     * @param sym_id
     * @param ref_sym_id
     */
    public void DeleteSymptomReference (int sym_id, int ref_sym_id) {
        for (int x = 0; x < references.size(); x++) {
            if (references.get(x) == null) continue;
            if (references.get(x).id == sym_id) {
                ArrayList<Integer> temp = new ArrayList();
                for (int y = 0; y < references.get(x).references.length; y++) {
                    if (references.get(x).references[y] != ref_sym_id) temp.add(references.get(x).references[y]);
                }
                int[] temp_i = new int[temp.size()];
                for (int y = 0; y < temp.size(); y++)
                    temp_i[y] = temp.get(y);
                references.get(x).references = temp_i;
                return;
            }
        }
    }

    /** Sets a remsymptom
     * 
     * @param symptomid
     * @param rs
     */
    public void SetRemSymptom (int symptomid, RemSymptom rs) {
        remsymptoms_string.set(symptomid, ConvertRemSymptomToString(rs));
    }

} 