/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prescriber;

import javax.swing.JOptionPane;

/**
 *
 * @author vladimir
 */
public class Configuration {

    public static final String ConfigFile_DefaultRepertory_TagStart = "<default_repertory>";
    public static final String ConfigFile_DefaultRepertory_TagEnd = "</default_repertory>";

    public static final String ConfigFile_AutomaticSave_TagStart = "<automatically_save_changes>";
    public static final String ConfigFile_AutomaticSave_TagEnd = "</automatically_save_changes>";

    public static final String ConfigFile_AutomaticRepertorization_TagStart = "<automatic_repertorization>";
    public static final String ConfigFile_AutomaticRepertorization_TagEnd = "</automatic_repertorization>";

    public static final String ConfigFile_SearchInAllRepertories_TagStart = "<search_in_all_repertories>";
    public static final String ConfigFile_SearchInAllRepertories_TagEnd = "</search_in_all_repertories>";

    public static final String ConfigFile_VersionWarning_TagStart = "<version_warning>";
    public static final String ConfigFile_VersionWarning_TagEnd = "</version_warning>";

    public static final String ConfigFile_ProSearch_TagStart = "<professional_search>";
    public static final String ConfigFile_ProSearch_TagEnd = "</professional_search>";

    public static final String ConfigFile_OpenPMS_TagStart = "<pms_startup>";
    public static final String ConfigFile_OpenPMS_TagEnd = "</pms_startup>";

    public static int Key_PrescriberView_SymptomTree = 0;
    public static int Key_PrescriberView_RemSymptomTree = 1;
    public static int Key_PrescriberView_RemSymptomTable = 2;
    public static int Key_AddRemedyDialog_RemedyList = 3;
    public static int Key_EditRemedyAdditionsDialog_RemedyList = 4;
    public static int Key_ListDialog_CurrentPane = 5;
    public static int Key_PMSDialog_PatientTable = 6;
    public static int Key_PatientDiagnosisDialog_Diagnosis_1 = 7;
    public static int Key_PatientDiagnosisDialog_Diagnosis_2 = 8;
    public static int Key_PatientDiagnosisDialog_Diagnosis_3 = 9;
    public static int Key_PatientDiagnosisDialog_Prescription_1 = 10;
    public static int Key_PatientDiagnosisDialog_Prescription_2 = 11;
    public static int Key_PatientDiagnosisDialog_Prescription_3 = 12;
    public static int Key_PatientDialog_AdditionalInformationEdit = 13;
    public static int Key_ReversedMMDialog_RemedyTree = 14;
    public static int Key_ReversedMMDialog_SymptomTree = 15;

    private String default_repertory_name;

    private boolean automatic_saving = false;

    private String version_warning;

    private boolean automatic_repertorization = true;

    private boolean search_in_all_repertories = false;

    private boolean open_pms_startup = false;

    private String file_name;

    private int[] values = new int[20];

    private boolean professional_search = false;

    public Configuration(String config_file_name) {
        this.file_name = config_file_name;
        LoadConfigFile(file_name);
    }

    public void SetValue (int key, int value) {
        values[key] = value;
        SaveConfigFile();
    }

    public int GetValue (int key) {
        return values[key];
    }

    public void SetPMSStartup (boolean pms) {
        open_pms_startup = pms;
        SaveConfigFile();
    }

    public boolean GetPMSStartup () {
        return open_pms_startup;
    }

    public void SetSearchInAll (boolean rep) {
        search_in_all_repertories = rep;
        SaveConfigFile();
    }

    public boolean GetSearchInall () {
        //return search_in_all_repertories;
        return false;
    }

    public void SetAutomaticRepertorization (boolean rep) {
        automatic_repertorization = rep;
        SaveConfigFile();
    }

    public boolean GetAutomaticRepertorization () {
        return automatic_repertorization;
    }

    public void SetProfessionalSearch (boolean value) {
        professional_search = value;
        SaveConfigFile();
    }

    public boolean GetProfessionalSearch () {
        return this.professional_search;
    }

    public void SetVersionWarning (String warning) {
        version_warning = warning;
        SaveConfigFile();
    }

    public String GetVersionWarning () {
        return version_warning;
    }
    
    public void SetAutomaticSaving (boolean saving) {
        automatic_saving = saving;
        SaveConfigFile();
    }

    public boolean GetAutomaticSaving () {
        return automatic_saving;
    }

    public void SetDefaultRepertoryName (String name) {
        default_repertory_name = name;
        SaveConfigFile();
    }

    public String GetDefaultRepertoryName () {
        return default_repertory_name;
    }

    public void SaveConfigFile() {
        if (SaveConfigFile(file_name, default_repertory_name, automatic_saving, automatic_repertorization, search_in_all_repertories, version_warning, open_pms_startup) == false) {
            JOptionPane.showMessageDialog(PrescriberApp.getApplication().getMainFrame(), "There was an error while saving the config file. Config values were not saved.");
        }        
    }

    /** Saves values in the parameter into a config file
     *
     * @param file_name
     * @param default_repertory
     * @param automatic_save
     * @param automatic_repertorization
     * @param search_in_all
     * @return
     */
    private boolean SaveConfigFile (String file_name, String default_repertory, boolean automatic_save, boolean automatic_repertorization, boolean search_in_all, String version_warning, boolean open_pms) {
        String data = "";
        try {
            data += ConfigFile_DefaultRepertory_TagStart+default_repertory+ConfigFile_DefaultRepertory_TagEnd+"\n";
            data += ConfigFile_AutomaticRepertorization_TagStart+String.valueOf(automatic_repertorization)+ConfigFile_AutomaticRepertorization_TagEnd+"\n";
            data += ConfigFile_AutomaticSave_TagStart+String.valueOf(automatic_save)+ConfigFile_AutomaticSave_TagEnd+"\n";
            data += ConfigFile_SearchInAllRepertories_TagStart+String.valueOf(search_in_all)+ConfigFile_SearchInAllRepertories_TagEnd+"\n";
            data += ConfigFile_VersionWarning_TagStart+version_warning+ConfigFile_VersionWarning_TagEnd+"\n";
            data += ConfigFile_OpenPMS_TagStart+String.valueOf(open_pms)+ConfigFile_OpenPMS_TagEnd+"\n";
            data += ConfigFile_ProSearch_TagStart+String.valueOf(professional_search)+ConfigFile_ProSearch_TagEnd+"\n";
            for (int x = 0; x < values.length; x++) {
                if (values[x] != -1) data += "<"+Integer.toString(x)+">"+values[x]+"</"+Integer.toString(x)+">"+"\n";
            }
            Utils.WriteFile(file_name, data, true);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /** Returns the contents of the config file
     *
     * @param file_name
     * @return
     */
    public void LoadConfigFile (String file_name) {
        try {
           for (int x = 0; x < values.length; x++) 
               values[x] = -1;
            String config_content = Utils.ReadFile(file_name, "\n");
                if (!config_content.equals("")) {
                    for (int x = 0; x < values.length; x++) {
                        try {
                            values[x] = -1;
                            values[x] = Integer.parseInt(Utils.ReadTag(config_content, "<"+Integer.toString(x)+">", "</"+Integer.toString(x)+">", 0));
                        } catch (Exception e) {
                            values[x] = -1;
                        }
                    }
                    default_repertory_name = Utils.ReadTag(config_content, ConfigFile_DefaultRepertory_TagStart, ConfigFile_DefaultRepertory_TagEnd, 0);
                    try {
                       automatic_saving = Boolean.parseBoolean(Utils.ReadTag(config_content, ConfigFile_AutomaticSave_TagStart, ConfigFile_AutomaticSave_TagEnd, 0));
                    } catch (Exception e) {
                        automatic_saving = false;
                    }
                    try {
                       professional_search = Boolean.parseBoolean(Utils.ReadTag(config_content, ConfigFile_ProSearch_TagStart, ConfigFile_ProSearch_TagEnd, 0));
                    } catch (Exception e) {
                        professional_search = false;
                    }
                    try {
                       version_warning = Utils.ReadTag(config_content, ConfigFile_VersionWarning_TagStart, ConfigFile_VersionWarning_TagEnd, 0);
                    } catch (Exception e) {
                        version_warning = null;
                    }
                    try {
                       automatic_repertorization = Boolean.parseBoolean(Utils.ReadTag(config_content, ConfigFile_AutomaticRepertorization_TagStart, ConfigFile_AutomaticRepertorization_TagEnd, 0));
                    } catch (Exception e) {
                        automatic_repertorization = false;
                    }
                    try {
                       search_in_all_repertories = Boolean.parseBoolean(Utils.ReadTag(config_content, ConfigFile_SearchInAllRepertories_TagStart, ConfigFile_SearchInAllRepertories_TagEnd, 0));
                    } catch (Exception e) {
                        search_in_all_repertories = false;
                    }
                    try {
                       open_pms_startup = Boolean.parseBoolean(Utils.ReadTag(config_content, ConfigFile_OpenPMS_TagStart, ConfigFile_OpenPMS_TagEnd, 0));
                    } catch (Exception e) {
                        open_pms_startup = false;
                    }
                }
        }
        catch (Exception e) {
        }
    }

}
