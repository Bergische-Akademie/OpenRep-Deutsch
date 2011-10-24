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

/* This class is instantiated in the Repertory and is used to record changes performed in the repertory
 * 
 */

package prescriber;

import java.util.ArrayList;

/** Item that contains the changes
 * 
 * @author vladimir
 */
class ChangeItem{
    /** symptomid */
    int sym_id = -1;
    /** symptomid - reference*/
    int sym_id_ref = -1;
    /** remedyid */
    int rem_id = -1;
    /** list of sourceids */
    ArrayList<Byte> source_id = null;
    /** referenceid */
    int reference_id = -1;
    /** type of a change - can be retrieved from Changes.CHANGE_TYPE...*/
    String type = null;
    /** symptom name */
    String sym_name = null;
    /** symptom name - original */
    String sym_name_orig = null;    
    /** symptom name - reference */
    String sym_name_ref = null;
    /** symptom name - new */
    String sym_name_new = null;
    /** remedy name */
    String rem_name = null;
    /** remedy shortcut */
    String rem_sc = null;
    /** remedy sc */
    String new_rem_name = null;
    /** remedy shortcut */
    String new_rem_sc = null;
    /** source name - author*/
    ArrayList<String> source_author_value = null;
    /** source name - work*/
    ArrayList<String> source_work_value = null;
    /** remedy grade */
    int rem_grade = -1;
    /** previous remedy grade */
    int prev_rem_grade = -1;
    /** new remedy grade */
    int new_rem_grade = -1;

}
/** Main class containing the Changes performed in the repertory
 * 
 * @author vladimir
 */
public class Changes {
    // types of changes
    public static final String CHANGE_TYPE_ADD_SYMPTOM = "add_symptom";
    public static final String CHANGE_TYPE_DELETE_SYMPTOM = "delete_symptom";
    public static final String CHANGE_TYPE_RENAME_SYMPTOM = "rename_symptom";
    public static final String CHANGE_TYPE_RENAME_REMEDY = "rename_remedy";
    public static final String CHANGE_TYPE_ADD_SYMPTOM_REFERENCE = "add_symptom_reference";
    public static final String CHANGE_TYPE_ADD_REMEDY_ADDITION = "add_remedy_addition";
    public static final String CHANGE_TYPE_DELETE_REMEDY_ADDITION = "delete_remedy_addition";
    public static final String CHANGE_TYPE_CHANGE_REMEDY_GRADE = "change_remedy_grade";
    public static final String CHANGE_TYPE_CREATE_REMEDY = "create_remedy";
    public static final String CHANGE_TYPE_ADD_SOURCE = "add_source";
    public static final String CHANGE_TYPE_ADD_REMSYMPTOM_SOURCE = "add_remsymptom_source";
    public static final String CHANGE_TYPE_DELETE_REMSYMPTOM_SOURCE = "delete_remsymptom_source";    
    // tags used to write the changes to the file
    public static final String FILE_SESSION_TAG_START = "<session>";
    public static final String FILE_SESSION_TAG_END = "</session>";
    
    public static final String FILE_CHANGE_TAG_START = "<change>";
    public static final String FILE_CHANGE_TAG_END = "</change>";
    
    public static final String FILE_ID_TAG_START = "<id>";
    public static final String FILE_ID_TAG_END = "</id>";
    
    public static final String FILE_OPERATION_TAG_START = "<operation>";
    public static final String FILE_OPERATION_TAG_END = "</operation>";
    
    public static final String FILE_REMID_TAG_START = "<remedy_id>";
    public static final String FILE_REMID_TAG_END = "</remedy_id>";

    public static final String FILE_REMNAME_TAG_START = "<remedy_name>";
    public static final String FILE_REMNAME_TAG_END = "</remedy_name>";

    public static final String FILE_REMSC_TAG_START = "<remedy_sc>";
    public static final String FILE_REMSC_TAG_END = "</remedy_sc>";
    
    public static final String FILE_REMGRADE_TAG_START = "<remedy_grade>";
    public static final String FILE_REMGRADE_TAG_END = "</remedy_grade>";    
    
    public static final String FILE_ORIG_REMGRADE_TAG_START = "<original_remedy_grade>";
    public static final String FILE_ORIG_REMGRADE_TAG_END = "</original_remedy_grade>";    
    
    public static final String FILE_NEW_REMGRADE_TAG_START = "<new_remedy_grade>";
    public static final String FILE_NEW_REMGRADE_TAG_END = "</new_remedy_grade>";        
    
    public static final String FILE_SYMID_TAG_START = "<symptom_id>";
    public static final String FILE_SYMID_TAG_END = "</symptom_id>";    

    public static final String FILE_SYMREFID_TAG_START = "<symptom_reference_id>";
    public static final String FILE_SYMREFID_TAG_END = "</symptom_reference_id>";        
    
    public static final String FILE_SYMREFNAME_TAG_START = "<symptom_reference_name>";
    public static final String FILE_SYMREFNAME_TAG_END = "</symptom_reference_name>";            
    
    public static final String FILE_SOURCEID_TAG_START = "<source_id>";
    public static final String FILE_SOURCEID_TAG_END = "</source_id>";    

    public static final String FILE_SOURCE_AUTHOR_WORK_TAG_START = "<source>";
    public static final String FILE_SOURCE_AUTHOR_WORK_TAG_END = "</source>";    
    
    public static final String FILE_SOURCE_AUTHOR_TAG_START = "<source_author>";
    public static final String FILE_SOURCE_AUTHOR_TAG_END = "</source_author>";

    public static final String FILE_SOURCE_WORK_TAG_START = "<source_work>";
    public static final String FILE_SOURCE_WORK_TAG_END = "</source_work>";

    public static final String FILE_REFID_TAG_START = "<reference_id>";
    public static final String FILE_REFID_TAG_END = "</reference_id>";    

    public static final String FILE_ORIG_SYMNAME_TAG_START = "<original_symptom_name>";
    public static final String FILE_ORIG_SYMNAME_TAG_END = "</original_symptom_name>";    
    
    public static final String FILE_NEW_SYMNAME_TAG_START = "<new_symptom_name>";
    public static final String FILE_NEW_SYMNAME_TAG_END = "</new_symptom_name>";

    public static final String FILE_NEW_REMNAME_TAG_START = "<new_remedy_name>";
    public static final String FILE_NEW_REMNAME_TAG_END = "</new_remedy_name>";

    public static final String FILE_NEW_REMSC_TAG_START = "<new_remedy_sc>";
    public static final String FILE_NEW_REMSC_TAG_END = "</new_remedy_sc>";

    public static final String FILE_SYMNAME_TAG_START = "<symptom_name>";
    public static final String FILE_SYMNAME_TAG_END = "</symptom_name>";        
    /** contains the actual data of changes */
    private ArrayList<ChangeItem> changes = new ArrayList();
    
    /** Records the change - adding of the symptom
     * 
     * @param new_name name of the newly added symptom
     * @param symid symptom id of the newly added symptom
     */
    public void AddSymptom (String new_name, int symid) {
       ChangeItem ci = new ChangeItem();
       ci.type = CHANGE_TYPE_ADD_SYMPTOM;
       ci.sym_id = symid;
       ci.sym_name = new_name;
       changes.add(ci);
    }
    
    /** Records the change - renaming the remedy
     * 
     * @param prev_name
     * @param prev_sc
     * @param new_name
     * @param new_sc
     */
    public void RenameRemedy (String prev_name, String prev_sc, String new_name, String new_sc) {
        ChangeItem ci = new ChangeItem();
        ci.type = CHANGE_TYPE_RENAME_REMEDY;
        ci.rem_name = prev_name;
        ci.rem_sc = prev_sc;
        ci.new_rem_name = new_name;
        ci.new_rem_sc = new_sc;
        changes.add(ci);
    }

    /** Records the change - deleting of the symptom
     * 
     * @param name name of the deleted symptom
     * @param symid symptomid of the deleted symptom
     */
    public void DeleteSymptom (String name, int symid) {
       ChangeItem ci = new ChangeItem();
       ci.type = CHANGE_TYPE_DELETE_SYMPTOM;
       ci.sym_id = symid;
       ci.sym_name = name;
       changes.add(ci);
    }    
    
    /** Records the change - adding of the new remedy source to the Remedy Addition
     * 
     * @param symid symptomid
     * @param remid remedyid
     * @param sourceid sourceid
     * @param sym_name symptom name
     * @param rem_name remedy name
     * @param author author of the addition
     * @param work name of the book 
     */
    public void AddRemSymptomSource (int symid, int remid, byte sourceid, String sym_name, String rem_name, String author, String work) {
        ChangeItem ci = new ChangeItem();
        ci.type = CHANGE_TYPE_ADD_REMSYMPTOM_SOURCE;
        ci.sym_id = symid;
        ci.rem_id = remid;
        ci.sym_name = sym_name;
        ci.rem_name = rem_name;
        ci.source_id = new ArrayList();
        ci.source_id.add(sourceid);
        ci.source_author_value = new ArrayList();
        ci.source_author_value.add(author);
        ci.source_work_value = new ArrayList();
        ci.source_work_value.add(work);
        changes.add(ci);
    }
    
    /** Records the change - deleting of the remedy source to the Remedy Addition
     * 
     * @param symid symptomid
     * @param remid remedyid
     * @param sourceid sourceid
     * @param sym_name symptom name
     * @param rem_name remedy name
     * @param author name of the author
     * @param work name of the book
     */
    public void DeleteRemSymptomSource (int symid, int remid, byte sourceid, String sym_name, String rem_name, String author, String work) {
        ChangeItem ci = new ChangeItem();
        ci.type = CHANGE_TYPE_DELETE_REMSYMPTOM_SOURCE;
        ci.sym_id = symid;
        ci.rem_id = remid;
        ci.sym_name = sym_name;
        ci.rem_name = rem_name;
        ci.source_id = new ArrayList();
        ci.source_author_value = new ArrayList();
        ci.source_work_value = new ArrayList();
        ci.source_id.add(sourceid);
        ci.source_author_value.add(author);
        ci.source_work_value.add(work);
        changes.add(ci);
    }    
    
    /** Records the change - renaming of the symptom
     * 
     * @param prev_name original name of the symptom
     * @param new_name new name of the symptom
     * @param symid symptomid
     */
    public void RenameSymptom (String prev_name, String new_name, int symid) {
       ChangeItem ci = new ChangeItem();
       ci.type = CHANGE_TYPE_RENAME_SYMPTOM;
       ci.sym_id = symid;
       ci.sym_name_orig = prev_name;
       ci.sym_name_new = new_name;
       changes.add(ci);
    }
    
    /** Records the change - adding of the new symptom cross-reference
     * 
     * @param symid symptomid of the symtom TO WHICH is the another one referenced
     * @param refid symptomid WHICH IS referenced to the symid
     * @param sym_name_orig symptom name of the symptom TO WHICH is the another one referenced
     * @param sym_name_ref symptom name of the symptom WHICH is referenced to the symid
     */
    public void AddSymptomReference (int symid, int refid, String sym_name_orig, String sym_name_ref) {
       ChangeItem ci = new ChangeItem();
       ci.type = CHANGE_TYPE_ADD_SYMPTOM_REFERENCE;
       ci.sym_name_orig = sym_name_orig;
       ci.sym_name_ref = sym_name_ref;
       ci.sym_id = symid;
       ci.sym_id_ref = refid;
       changes.add(ci);
    }    
    
    /** Records the change - adding of the new Remedy Addition
     * 
     * @param symid symptomid
     * @param remid remedyid
     * @param grade remedy grade
     * @param source sourceid
     * @param symname symptom name
     * @param remname remedy name
     * @param remsc remedy shortcut
     * @param source_author name of the author
     * @param source_work name of the book
     */    
    public void AddRemedyAddition (int symid, short remid, byte grade, ArrayList<Byte> source, String symname, String remname, String remsc, ArrayList<String> source_author, ArrayList<String> source_work) {
       ChangeItem ci = new ChangeItem();
       ci.type = CHANGE_TYPE_ADD_REMEDY_ADDITION;
       ci.sym_id = symid;
       ci.rem_id = remid;
       ci.rem_grade = grade;
       ci.source_id = source;
       ci.sym_name = symname;
       ci.rem_name = remname;
       ci.rem_sc = remsc;
       ci.source_author_value = source_author;
       ci.source_work_value = source_work;
       changes.add(ci);
    }
    
    /** Records the change - deleting of the Remedy Addition
     * 
     * @param symid symptomid
     * @param remid remedyid
     * @param remgrade remedy grade
     * @param symname symptom name
     * @param remname remedy name
     * @param remsc remedy shortcut
     */
    public void DeleteRemedyAddition (int symid, int remid, int remgrade, String symname, String remname, String remsc) {
       ChangeItem ci = new ChangeItem();
       ci.type = CHANGE_TYPE_DELETE_REMEDY_ADDITION;
       ci.sym_id = symid;
       ci.rem_id = remid;
       ci.sym_name = symname;
       ci.rem_name = remname;
       ci.rem_sc = remsc;
       ci.rem_grade = remgrade;
       changes.add(ci);
    }    

    /** Records the change - changing of the Remedy grade
     * 
     * @param symid symptomid
     * @param remid remedyid
     * @param prev_grade previous remedy grade
     * @param grade new remedy grade
     * @param symname symtpom name
     * @param remname remedy name
     * @param remsc remedy shortcut
     * @param Author name of the author
     * @param Work name of the book
     */
    public void ChangeRemedyGrade (int symid, int remid, int prev_grade, int grade, String symname, String remname, String remsc, String Author, String Work) {
       ChangeItem ci = new ChangeItem();
       ci.type = CHANGE_TYPE_CHANGE_REMEDY_GRADE;
       ci.sym_id = symid;
       ci.rem_id = remid;
       ci.new_rem_grade = grade;
       ci.prev_rem_grade = prev_grade;
       ci.sym_name = symname;
       ci.rem_name = remname;
       ci.rem_sc = remsc;
       ci.source_author_value = new ArrayList();
       ci.source_author_value.add(Author);
       ci.source_work_value = new ArrayList();
       ci.source_work_value.add(Work);
       changes.add(ci);
    }
    
    /** Records the change - creation of the new remedy
     * 
     * @param remid remedyid
     * @param remname remedy name
     * @param remsc remedy shortcut
     */
    public void CreateRemedy (int remid, String remname, String remsc) {
       ChangeItem ci = new ChangeItem();
       ci.type = CHANGE_TYPE_CHANGE_REMEDY_GRADE;
       ci.rem_id = remid;
       ci.rem_name = remname;
       ci.rem_sc = remsc;
       changes.add(ci);
    }

    /** Saves all the changes into a .XML file. It does not rewrite the file, but appends the data to the previously 
     *  saved data
     * 
     * @param filename name of the file into which to save the changes
     * @return
     */
    public boolean SaveChangesToHistoryFile (String filename) {
        ArrayList<String> data = new ArrayList();
        data.add(FILE_SESSION_TAG_START+"\n");
        for (int x = 0; x < changes.size(); x++) {
            data.add("\t"+FILE_CHANGE_TAG_START+"\n");
            data.add("\t\t"+FILE_OPERATION_TAG_START+changes.get(x).type+FILE_OPERATION_TAG_END+"\n");
            if (changes.get(x).reference_id != -1) data.add("\t\t"+FILE_REFID_TAG_START+changes.get(x).reference_id+FILE_REFID_TAG_END+"\n");
            if (changes.get(x).rem_grade != -1) data.add("\t\t"+FILE_REMGRADE_TAG_START+changes.get(x).rem_grade+FILE_REMGRADE_TAG_END+"\n");
            if (changes.get(x).rem_id != -1) data.add("\t\t"+FILE_REMID_TAG_START+changes.get(x).rem_id+FILE_REMID_TAG_END+"\n");
            if (changes.get(x).rem_name != null) data.add("\t\t"+FILE_REMNAME_TAG_START+changes.get(x).rem_name+FILE_REMNAME_TAG_END+"\n");
            if (changes.get(x).rem_sc != null) data.add("\t\t"+FILE_REMSC_TAG_START+changes.get(x).rem_sc+FILE_REMSC_TAG_END+"\n");
            if (changes.get(x).source_author_value != null && changes.get(x).source_work_value != null && changes.get(x).source_id != null) {
                for (int y = 0; y < changes.get(x).source_author_value.size(); y++) {
                  data.add("\t\t"+FILE_SOURCE_AUTHOR_WORK_TAG_START+"\n");
                  data.add("\t\t"+FILE_SOURCE_AUTHOR_TAG_START+changes.get(x).source_author_value.get(y)+FILE_SOURCE_AUTHOR_TAG_END+"\n");
                  data.add("\t\t"+FILE_SOURCE_WORK_TAG_START+changes.get(x).source_work_value.get(y)+FILE_SOURCE_WORK_TAG_END+"\n");
                  data.add("\t\t"+FILE_SOURCEID_TAG_START+changes.get(x).source_id.get(y)+FILE_SOURCEID_TAG_END+"\n");
                  data.add("\t\t"+FILE_SOURCE_AUTHOR_WORK_TAG_END+"\n");
                }
            }
            if (changes.get(x).sym_id != -1) data.add("\t\t"+FILE_SYMID_TAG_START+changes.get(x).sym_id+FILE_SYMID_TAG_END+"\n");
            if (changes.get(x).sym_id_ref != -1) data.add("\t\t"+FILE_SYMREFID_TAG_START+changes.get(x).sym_id_ref+FILE_SYMREFID_TAG_END+"\n");
            if (changes.get(x).sym_name != null) data.add("\t\t"+FILE_SYMNAME_TAG_START+changes.get(x).sym_name+FILE_SYMNAME_TAG_END+"\n");
            if (changes.get(x).sym_name_new != null) data.add("\t\t"+FILE_NEW_SYMNAME_TAG_START+changes.get(x).sym_name_new+FILE_NEW_SYMNAME_TAG_END+"\n");
            if (changes.get(x).sym_name_orig != null) data.add("\t\t"+FILE_ORIG_SYMNAME_TAG_START+changes.get(x).sym_name_orig+FILE_ORIG_SYMNAME_TAG_END+"\n");
            if (changes.get(x).sym_name_ref != null) data.add("\t\t"+FILE_SYMREFNAME_TAG_START+changes.get(x).sym_name_ref+FILE_SYMREFNAME_TAG_END+"\n");
            if (changes.get(x).prev_rem_grade != -1) data.add("\t\t"+FILE_ORIG_REMGRADE_TAG_START+changes.get(x).prev_rem_grade+FILE_ORIG_REMGRADE_TAG_END+"\n");
            if (changes.get(x).new_rem_grade != -1) data.add("\t\t"+FILE_NEW_REMGRADE_TAG_START+changes.get(x).new_rem_grade+FILE_NEW_REMGRADE_TAG_END+"\n");
            if (changes.get(x).new_rem_name != null) data.add("\t\t"+FILE_NEW_REMNAME_TAG_START+changes.get(x).new_rem_name+FILE_NEW_REMNAME_TAG_END+"\n");
            if (changes.get(x).new_rem_sc != null) data.add("\t\t"+FILE_NEW_REMSC_TAG_START+changes.get(x).new_rem_sc+FILE_NEW_REMSC_TAG_END+"\n");
            data.add("\t"+FILE_CHANGE_TAG_END+"\n");
        }        
        data.add(FILE_SESSION_TAG_END+"\n");
        
        // read the previous data from the file
        ArrayList<String> prev_data = new ArrayList();
        try{
            prev_data = Utils.ReadFile(filename);
        }
        catch (Exception e) {
            
        }
        
        // add data to the previous data
        for (int x = 0; x < prev_data.size(); x++)
            prev_data.set(x, prev_data.get(x) + "\n");
        
        // save all the data
        try{
            for (int x = 0; x < data.size(); x++) {
                prev_data.add(data.get(x));
            }
            Utils.WriteFile(filename, prev_data, true);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }    
}