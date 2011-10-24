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

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.text.rtf.RTFEditorKit;

/** Support utilities used by the OpenRep
 *
 * 
 */
public class Utils {    
    /** char used to determine the id in the repertory files*/
    public static final char FILE_ID_SEPARATOR = ':';  
    /** char used to determine the values in the repertory files*/    
    public static final char FILE_VALUE_SEPARATOR = '#';  
    /** char used to determine the text values in the repertory files*/    
    public static final char FILE_TEXT_VALUE_DELIM = '\"';
    /** char used to determine the multiple values the repertory files*/    
    public static final char FILE_MULTIPLE_VALUE_SEPARATOR = ';';
    /** standard extension of any file == '.' */    
    public static final String FILE_EXTENSION_SEPARATOR = ".";
    /** repertory descriptor file extension*/    
    public static final String REPERTORY_DESCRIPTOR_FILE_EXTENSION = "rd";
    /** extension of the repertorization files*/
    public static final String REPERTORIZATION_FILE_EXTENSION = "xml";    
    /** symptom fragments are in the repertory delimited by the comma = "Abdomen, pain"*/    
    public static final char SYMPTOM_FRAGMENT_SEPARATOR = ',';
    /** symptom fragments are in the repertory delimited by the comma and space= "Abdomen, pain"*/        
    public static final String SYMPTOM_FRAGMENT_FULL_SEPARATOR = ", ";
    
    /** this token will be in every path that is converted with Utils.ConvertPath to path in which is the program installed */
    public static final String INSTALL_DIR_TOKEN = "$INSTALL_DIR$";
    /** search string constant*/
    public static final char MATCH_ALL = '*';
    /** search string constant*/    
    public static final char SPACE = ' ';
    /** search string constant*/    
    public static final char SPLIT_CHAR = ';';
    /** search string constant*/
    public static final char EXACT_MATCH_CHAR = '\"';
    /** search string constant*/    
    public static final char NEGATIVE_CHAR = '-';
    /** format of displaying grades */
    public static final String REMSYMPTOM_GRADE_START = "(";
    /** format of displaying grades */    
    public static final String REMSYMPTOM_GRADE_END = ") ";
    /** format of displaying symptom counts */    
    public static final String SYMPTOM_COUNT_START = " [";
    /** format of displaying symptom counts */        
    public static final String SYMPTOM_COUNT_END = "]";
    // tags used to save data to file
    public static final String SaveFile_Tag_Header = "<header>";
    public static final String SaveFile_Tag_HeaderEnd = "</header>";
    
    public static final String SaveFile_Tag_Data = "<repertorization>";
    public static final String SaveFile_Tag_DataEnd = "</repertorization>";
    
    public static final String SaveFile_Tag_Remedies = "<remedies>";
    public static final String SaveFile_Tag_RemediesEnd = "</remedies>";
    
    public static final String SaveFile_Tag_Symptom = "<symptom>";
    public static final String SaveFile_Tag_SymptomEnd = "</symptom>";
    
    public static final String SaveFile_Tag_SymptomGrade = "<symptom_grade>";
    public static final String SaveFile_Tag_SymptomGradeEnd = "</symptom_grade>";    
    
    public static final String SaveFile_Tag_RepertoryName = "<repertory_name>";
    public static final String SaveFile_Tag_RepertoryNameEnd = "</repertory_name>";
    
    public static final String SaveFile_Tag_SymptomName = "<symptom_name>";
    public static final String SaveFile_Tag_SymptomNameEnd = "</symptom_name>";

    public static final String SaveFile_Tag_RemedyName = "<remedy_name>";
    public static final String SaveFile_Tag_RemedyNameEnd = "</remedy_name>";
    
    public static final String SaveFile_Tag_RemedyGrade = "<remedy_grade>";
    public static final String SaveFile_Tag_RemedyGradeEnd = "</remedy_grade>";
    
    public static final String SaveFile_Tag_RemedyAddition = "<remedy_addition>";
    public static final String SaveFile_Tag_RemedyAdditionEnd = "</remedy_additiom>";    
    
    public static final String SaveFile_Tag_RemedyShortCut = "<remedy_shortcut>";
    public static final String SaveFile_Tag_RemedyShortCutEnd = "</remedy_shortcut>"; 
    
    public static final String SaveFile_Tag_MaximumGrade = "<maximum_grade>";
    public static final String SaveFile_Tag_MaximumGradeEnd = "</maximum_grade>";
    
    public static final String SaveFile_Tag_PositiveFilter = "<positive_filter>";
    public static final String SaveFile_Tag_PositiveFilterEnd = "</positive_filter>";

    public static final String SaveFile_Tag_NegativeFilter = "<negative_filter>";
    public static final String SaveFile_Tag_NegativeFilterEnd = "</negative_filter>";    

    public static final String SaveFile_Tag_DesktopFilter = "<desktop>";
    public static final String SaveFile_Tag_DesktopFilterEnd = "</desktop>";        
    /** header of the save file - version of the file*/    
    public static final String SaveFile_Header = "V001";    
    
    public static String VersionURL = "http://www.btb.info/free_version.txt";

    public static void ChangeFont (JComponent comp, int wheel_rotation) {
        int size_inc = 0;
        if (wheel_rotation > 0) size_inc++;
        else
        if (wheel_rotation < 0) size_inc--;
        if (size_inc != 0) {
            Font f = new Font(comp.getFont().getName(), comp.getFont().getStyle(), comp.getFont().getSize()+size_inc);
            comp.setFont(f);
            comp.repaint();
        }
    }

    /** Copies orig_file to dest_file only in case, that the dest_file does not exit
     * 
     * @param orig_file
     * @param dest_file
     * @return
     */
    public static boolean CopyFileIfDoesNotExist (String orig_file, String dest_file) {
        File of = new File (orig_file);
        File f = new File (dest_file);
        if (!of.exists() || f.exists()) return false;
        try {
            ArrayList<String> al = ReadFile(orig_file);
            for (int x = 0; x < al.size(); x++)
                al.set(x, al.get(x)+"\n");
            WriteFile(dest_file, al, false);
        } catch (Exception e) {return false;}
        return true;
    }
    
    /** Returns true if the date is in format year/month/day, false if not
     * 
     * @param date
     * @return
     */
    public static boolean IsDateOK (String date) {
        try {
            DateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
            Date d = fmt.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /** Returns the name of the file without the extension
     * 
     * @param file_path
     * @return
     */
    public static String GetFileNameWithoutExt (String file_path) {
        int pos = file_path.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int pos2 = file_path.lastIndexOf(GetDirectorySeparator());
        if (pos2 < pos) return file_path.substring(0, pos);
        else
        return file_path;
    }

    /** Saves the repertorization into a xml file. Returns null if there were no problems while saving it,
     *  otherwise it returns a string containing the error message.
     * 
     * @param file_name
     */
    public String SaveRepertorizationToFile (ArrayList<SelectedSymptomItem> SelectedSymptoms, String file_name) {
        if (!ExtractFileExtension(file_name).equalsIgnoreCase(REPERTORIZATION_FILE_EXTENSION)) file_name += FILE_EXTENSION_SEPARATOR+REPERTORIZATION_FILE_EXTENSION;
        File f = new File(file_name);
        if (f.exists()) {
            f.delete();
            try{
                f.createNewFile();
            }
            catch (Exception e) {
                Logger.AddEntryToLog("Error while saving the repertorization to file:"+file_name+". Exception="+e.getMessage());
                return "There was an error while saving the file:"+file_name;
            }            
        }
        
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(SaveFile_Tag_Header+SaveFile_Header+SaveFile_Tag_HeaderEnd+"\n");
            
            bw.write(SaveFile_Tag_Data+"\n");
            for (int x = 0; x < SelectedSymptoms.size(); x++) {
                bw.write("\t"+SaveFile_Tag_Symptom+"\n");

                bw.write("\t"+"\t"+SaveFile_Tag_SymptomGrade+SelectedSymptoms.get(x).symptom_grade+SaveFile_Tag_SymptomGradeEnd+"\n");

                bw.write("\t"+"\t"+SaveFile_Tag_SymptomName+SelectedSymptoms.get(x).sym_name+SaveFile_Tag_SymptomNameEnd+"\n");
               
                bw.write("\t"+"\t"+SaveFile_Tag_RepertoryName+SelectedSymptoms.get(x).repertory_name+SaveFile_Tag_RepertoryNameEnd+"\n");

                bw.write("\t"+"\t"+SaveFile_Tag_MaximumGrade+SelectedSymptoms.get(x).maximum_grade+SaveFile_Tag_MaximumGradeEnd+"\n");
                
                bw.write("\t"+"\t"+SaveFile_Tag_PositiveFilter+SelectedSymptoms.get(x).positive_filter+SaveFile_Tag_PositiveFilterEnd+"\n");
                
                bw.write("\t"+"\t"+SaveFile_Tag_NegativeFilter+SelectedSymptoms.get(x).negative_filter+SaveFile_Tag_NegativeFilterEnd+"\n");

                bw.write("\t"+"\t"+SaveFile_Tag_DesktopFilter+SelectedSymptoms.get(x).desktop+SaveFile_Tag_DesktopFilterEnd+"\n");
                
                bw.write("\t"+"\t"+SaveFile_Tag_Remedies+"\n");
                
                for (int y = 0; y < SelectedSymptoms.get(x).remsymptoms.size(); y++) {   
                    bw.write("\t\t\t"+SaveFile_Tag_RemedyAddition+"\n");
                    bw.write("\t\t\t\t"+SaveFile_Tag_RemedyName+SelectedSymptoms.get(x).remsymptoms.get(y).Remname+SaveFile_Tag_RemedyNameEnd+"\n");                    
                    bw.write("\t\t\t\t"+SaveFile_Tag_RemedyGrade+SelectedSymptoms.get(x).remsymptoms.get(y).RemGrade+SaveFile_Tag_RemedyGradeEnd+"\n");
                    bw.write("\t\t\t\t"+SaveFile_Tag_RemedyShortCut+SelectedSymptoms.get(x).remsymptoms.get(y).RemSC+SaveFile_Tag_RemedyShortCutEnd+"\n");
                    bw.write("\t\t\t"+SaveFile_Tag_RemedyAdditionEnd+"\n");                    
                }
                
                bw.write("\t"+"\t"+SaveFile_Tag_Remedies+"\n");
                
                bw.write("\t"+SaveFile_Tag_SymptomEnd+"\n");
            }
            bw.write(SaveFile_Tag_DataEnd+"\n");
            bw.close();
        }
        catch (Exception e) {
            Logger.AddEntryToLog("Error while saving the repertorization to file:"+file_name+". Exception="+e.getMessage());
            return "There was an error while saving the file:"+file_name;
        }
        return null;
    }    
        
    /** Loads a data from repertorization file
     * 
     * @param file_name
     * @return
     */
    public String LoadRepertorizationFile (String file_name, ArrayList<SelectedSymptomItem> SelectedSymptoms) {        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file_name));
            String temps = "";
            StringBuffer tempx = new StringBuffer();
            while (temps != null) {
                temps = br.readLine();
                if (temps == null) break;
                tempx.append(temps+"\n");
            }
            br.close();
            String rslt = tempx.toString();
            ArrayList<String> symptom_contents = Utils.ReadTagContents(rslt, SaveFile_Tag_Symptom, SaveFile_Tag_SymptomEnd);
            // if there are no symptoms, it does not mean that it is an error
            if (symptom_contents.size() == 0) return null;
            for (int x = 0; x < symptom_contents.size(); x++) {
                SelectedSymptomItem ssi = new SelectedSymptomItem();
                ssi.repertory_id = -1;
                ArrayList<String> temp = ReadTagContents(symptom_contents.get(x), SaveFile_Tag_RepertoryName, SaveFile_Tag_RepertoryNameEnd);
                if (temp.size() != 0) ssi.repertory_name = temp.get(0);
                
                temp = ReadTagContents(symptom_contents.get(x), SaveFile_Tag_MaximumGrade, SaveFile_Tag_MaximumGradeEnd);
                try{
                    ssi.maximum_grade = (byte) Integer.parseInt(temp.get(0));
                }
                catch (Exception e) {                    
                    // maximum grade could not be parsed, so assign -1 and try to generate it at the end
                    ssi.maximum_grade = -1;
                }                
                temp = ReadTagContents(symptom_contents.get(x), SaveFile_Tag_SymptomName, SaveFile_Tag_SymptomNameEnd);
                if (temp.size() != 0) ssi.sym_name = temp.get(0);
                ssi.sym_id = -1;
                temp = ReadTagContents(symptom_contents.get(x), SaveFile_Tag_SymptomGrade, SaveFile_Tag_SymptomGradeEnd);
                try{
                    if (temp.size() != 0) ssi.symptom_grade = (byte) Integer.parseInt(temp.get(0));
                    else
                    ssi.symptom_grade = Database.SYMPTOM_GRADE_AVERAGE;
                }
                catch (Exception e) {
                    ssi.symptom_grade = Database.SYMPTOM_GRADE_AVERAGE;
                }
                try {
                    temp = ReadTagContents(symptom_contents.get(x), SaveFile_Tag_PositiveFilter, SaveFile_Tag_PositiveFilterEnd);
                    if (temp.get(0).equals("")) ssi.positive_filter = false;
                    else
                    ssi.positive_filter = Boolean.parseBoolean(temp.get(0));
                } catch (Exception e) {
                    ssi.positive_filter = false;
                }
                try {
                    temp = ReadTagContents(symptom_contents.get(x), SaveFile_Tag_DesktopFilter, SaveFile_Tag_DesktopFilterEnd);
                    ssi.desktop = Integer.parseInt(temp.get(0));
                } catch (Exception e) {
                    ssi.desktop = 1;
                }                
                try {
                    temp = ReadTagContents(symptom_contents.get(x), SaveFile_Tag_NegativeFilter, SaveFile_Tag_NegativeFilterEnd);
                    if (temp.get(0).equals("")) ssi.negative_filter = false;
                    else
                    ssi.negative_filter = Boolean.parseBoolean(temp.get(0));
                } catch (Exception e) {
                    ssi.negative_filter = false;
                }                
                ArrayList<String> rem_additions = ReadTagContents(symptom_contents.get(x), SaveFile_Tag_RemedyAddition, SaveFile_Tag_RemedyAdditionEnd);
                ArrayList<Double> grades = new ArrayList();
                for (int y = 0; y < rem_additions.size(); y++) {
                    SelectedRemSymptom sri = new SelectedRemSymptom();
                    temp = ReadTagContents(rem_additions.get(y), SaveFile_Tag_RemedyName, SaveFile_Tag_RemedyNameEnd);
                    if (temp.size() == 0) continue;
                    sri.Remname = temp.get(0);
                    temp = ReadTagContents(rem_additions.get(y), SaveFile_Tag_RemedyShortCut, SaveFile_Tag_RemedyShortCutEnd);
                    if (temp.size() == 0) continue;
                    sri.RemSC = temp.get(0);
                    temp = ReadTagContents(rem_additions.get(y), SaveFile_Tag_RemedyGrade, SaveFile_Tag_RemedyGradeEnd);
                    if (temp.size() == 0) continue;
                    try{
                        sri.RemGrade = Double.parseDouble(temp.get(0));
                        if (!grades.contains(sri.RemGrade)) grades.add(sri.RemGrade);
                    }
                    catch (Exception e) {
                        continue;
                    }
                    ssi.remsymptoms.add(sri);
                }
                // if the maximum ggrade was not found (older versions of OpenRep did not save it), try to generate it
                if (ssi.maximum_grade == -1) ssi.maximum_grade = (byte) grades.size();                
                SelectedSymptoms.add(ssi);
            }
        }
        catch (Exception e) {
            return "There was an error while reading the file. "+e.getMessage();
        }
        return null;
    }    
    
    /** Extracts a substring from a string delimited by start_delim and end_delim Strings
     * 
     * @param value
     * @param start_delim
     * @param end_delim
     * @return
     */
    private String ExtractStringFromString (String value, String start_delim, String end_delim) {
        int pos1 = value.indexOf(start_delim);
        int pos2 = value.lastIndexOf(end_delim);
        if (pos1 == -1 || pos2 == -1) return value;
        else
        return value.substring(pos1+start_delim.length(), pos2);
    }        
    
    /** Converts the URL to a file Path
     * 
     * @param url_path
     * @return
     */
    public static String ConvertURLToPath (java.net.URL url_path) {
        try{
        java.io.File f = new java.io.File(url_path.toURI());
        return f.getAbsolutePath();
        }
        catch (Exception e) {
            System.out.println("ERROR "+e.getMessage());
        }
        return null;
    }
    
    /** Extracts the file extension
     * 
     * @param file_name
     * @return
     */
    public static String ExtractFileExtension (String file_name) {
        int pos = file_name.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        if (pos != -1) return file_name.substring(pos+1);
        else
        return "";
    }

    /** Replaces words like headache => head pain
     * 
     * @param text
     * @return
     */
    public static String ReplaceAche (String text) {
        if (text.endsWith("ache")) text += " ";
        while (true) {
            int pos = text.indexOf("ache");
            if (pos == -1) break;
            int pos2 = text.lastIndexOf(" ", pos);
            String temps = "";
            if (pos2 == -1) pos2 = 0;
            else
            temps = text.substring(0, pos2);
            temps += text.substring(pos2, pos) + " pain "+text.substring(pos+5, text.length());
            text = temps;
        }
        return text;
    }

    /** Extracts a path from a file name. the extracted path is 
     * 
     * @param filename
     * @return
     */
    public static String ExtractFilePath (String filename) {
        String temps = filename;
        while (true) {
           File f = new File (temps);
           if (!f.isDirectory()) {               
               temps = temps.substring(0, temps.lastIndexOf(String.valueOf(java.io.File.separatorChar), temps.length()-1));               
           }
           else
           {
               if (temps.charAt(temps.length()-1) != java.io.File.separatorChar) return temps + java.io.File.separatorChar;
               else
               return temps;
           }                
        }
    }
    
    /** Extracts only the file path without the extension of the file
     * 
     * @param filename
     * @return
     */
    public static String ExtractFilePathWithoutExtension (String filename) {
        int pos = filename.lastIndexOf(GetDirectorySeparator());
        int pos2 = filename.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        if (pos2 > pos) return filename.substring(0, pos2);
        else
        return filename;
    }
    
    /** Extracts only the file name and not the path
     * 
     * @param file_name
     * @return
     */
    public static String ExtractFileName (String file_name) {
        int pos = file_name.lastIndexOf(GetDirectorySeparator());
        if (pos != -1) return file_name.substring(pos+1);
        else
        return file_name;               
    }
    
    /** Extracts onle the file name without the extension and without the path
     * 
     * @param file_name
     * @return
     */
    public static String ExtractFileNameWithoutExtension (String file_name) {
        int pos = file_name.lastIndexOf(GetDirectorySeparator());
        int pos2 = file_name.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        if (pos2 == -1) pos2 = file_name.length();
        if (pos != -1) return file_name.substring(pos+1, pos2);
        else
        return file_name;               
    }

    /** Returns the Directory Separator used in the current operating system Windows = \ Linux = /
     * 
     * @return
     */
    public static char GetDirectorySeparator() {
        return java.io.File.separatorChar;
    }
    
    /** Returns the directory of the file name (path)
     * 
     * @param file_name
     * @return
     */
    public static String GetDirectory (String file_name) {
        int pos = file_name.lastIndexOf(GetDirectorySeparator());
        if (pos == -1) return null;
        else
        return file_name.substring(0, pos+1);
    }
    
    /** Fills the specified string with fill_with until it reaches the max_len
     *  fill_before = before the string
     * 
     * @param val
     * @param max_len
     * @param fill_with
     * @return
     */
    public static String FillChars(String val, int max_len, char fill_with, boolean fill_before) {
        String temps = "";
        for (int x = val.length(); x < max_len; x++) {
            temps += fill_with;
        }
        if (fill_before) return temps + val;
        else
        return val + temps;
    }
    
    /** Reads the data of this tag
     * 
     * @param data
     * @param start_tag
     * @param end_tag
     * @param pos
     * @return
     */
    public static String ReadTag (String data, String start_tag, String end_tag, int pos) {
        int start_pos = data.indexOf(start_tag, pos);
        int end_pos = data.indexOf(end_tag, start_pos);
        if (start_pos != -1 && end_pos != -1) return data.substring(start_pos+start_tag.length(), end_pos);
        else
        return null;
    }    
    
    /** Reads a text file
     * 
     * @param filename file name
     * @param line_delimiter line delimiter -> '\n'
     * @return
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static String ReadFile (String filename, String line_delimiter) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader (filename);
        BufferedReader br = new BufferedReader(fr);
        
        String temps = "";
        String rslt = "";
        Boolean doCrypt = false;
        Blowfish crypt = new Blowfish("d8dD8f0vn35ßbsDnSlf4bvc535nsd56n4n23jj");

        ArrayList<String> al1 = new ArrayList();

        while (temps != null) {

            temps = br.readLine();
            if (temps == null) break;

            if(temps.indexOf("TPYRC", 0) >= 0) {

                doCrypt = true;
            } else {

               if(doCrypt) {
                   temps = crypt.decryptString(temps);
               }
               rslt += temps;
            }
        }
        
        br.close();
        fr.close();



        
        return rslt;
    }    

    /** Writes a text file
     * 
     * @param file_name file name
     * @param content content to be written
     * @param rewrite true if rewrite file, false ends if exists
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    public static void WriteFile (String file_name, ArrayList<String> content, boolean rewrite) throws IOException, Exception {
        java.io.File f = new java.io.File(file_name);
        String directory = f.getPath().substring(0, f.getPath().lastIndexOf(GetDirectorySeparator())+1);
        java.io.File dir = new java.io.File (directory);
        System.out.println("createdir="+directory);
        if (!dir.exists()) dir.mkdirs();
        if (f.exists() && rewrite) f.delete();
        System.out.println("createfile="+file_name);
        if (!f.exists()) f.createNewFile();
        System.gc();
        
        Blowfish crypt = new Blowfish("d8dD8f0vn35ßbsDnSlf4bvc535nsd56n4n23jj");
        Boolean doCrypt = false;

        if (content.size() != 0) {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file_name),"UTF-8");
            for (int x = 0; x < content.size(); x++) {
                if(doCrypt == true) {


                    if(x == 0) {
                        out.write("TPYRC\n");
                    }
                    /*if(content.get(x).indexOf("\n", 0) >= 0) {
                        char[] tempp = content.get(x).toCharArray();
                        String temppp = "";

                        for(int y = 0; y < tempp.length; y++) {
                            temppp += tempp[y];
                            if(tempp[y] == "\n") {
                                out.write(crypt.encryptString(
                            }

                        }
                    }*/
                    out.write(crypt.encryptString(content.get(x))+ "\n");
                } else {
                   out.write(content.get(x));
                }
            }
            out.close();
        }
    }    
    
    /** Write a text file
     * 
     * @param file_name file name
     * @param content content to be written
     * @param eol_character end of line character 
     * @param rewrite true if rewrite the file
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    public static void WriteFile (String file_name, ArrayList<String> content, char eol_character, boolean rewrite) throws IOException, Exception {
        java.io.File f = new java.io.File(file_name);
        String directory = f.getPath().substring(0, f.getPath().lastIndexOf(GetDirectorySeparator())+1);
        java.io.File dir = new java.io.File (directory);
        System.out.println("createdir="+directory);
        if (!dir.exists()) dir.mkdirs();
        if (f.exists() && rewrite) f.delete();
        System.out.println("createfile="+file_name);
        if (!f.exists()) f.createNewFile();
        System.gc();
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file_name),"UTF-8");
        for (int x = 0; x < content.size(); x++) {
            out.write(content.get(x)+String.valueOf(eol_character));
        }
        out.close();
    }        

    /** Writes a text file
     * 
     * @param file_name file name
     * @param content content to be written
     * @param rewrite true if rewrite the data
     * @throws java.io.IOException
     */
    public static void WriteFile (String file_name, String content, boolean rewrite) throws IOException {
        java.io.File f = new java.io.File(file_name);
        if (f.exists() && rewrite) f.delete();
        if (f.exists()) {
            Logger.AddEntryToLog("Could not rewrite the file: "+file_name);
            return;
        }
        else
        f.createNewFile();
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file_name),"UTF-8");
        out.write(content);
        out.close();
    }
    
    /** Reads the text file
     * 
     * @param filename file name
     * @return
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static ArrayList<String> ReadFile (String filename) throws FileNotFoundException, IOException {
        /*java.io.FileReader fr = new FileReader(filename);
        java.io.BufferedReader bis = new BufferedReader(fr);
        ArrayList<String> al = new ArrayList();
        String s = "";
        while ((s = bis.readLine()) != null) {al.add (s);}
        fr.close();
        bis.close();
        return al;*/
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        Reader r = new InputStreamReader(is, "UTF-8");
        java.io.BufferedReader bis = new BufferedReader(r);
        ArrayList<String> al = new ArrayList();

        String s = "";

        Blowfish crypt = new Blowfish("d8dD8f0vn35ßbsDnSlf4bvc535nsd56n4n23jj");
        Boolean doCrypt = false;

        while ((s = bis.readLine()) != null) {
            if(s.indexOf("TPYRC", 0) >= 0) {

                doCrypt = true;
            } else {

                if(doCrypt) {


                    al.add (crypt.decryptString(s));
                } else {
                    al.add (s);
                }
            }
        }



        r.close();
        is.close();
        bis.close();
        return al;
    }                

    /** Replaces a string in a string
     * 
     * @param text the original text in which to perform the replace
     * @param orig original string that will be replaced with the "replace" string
     * @param replace the string with which the "original" will be replaced
     * @return
     */
    public static String ReplaceString (String text, String orig, String replace, boolean case_sensitive) {
        String rslt = "";
        int pos = 0;
        int prevpos = 0;
        boolean first_run = true;
        while (pos != -1) {
            if (first_run) pos = -1;
            if (case_sensitive) pos = text.indexOf(orig, pos+1);
            else
            pos = text.toLowerCase().indexOf(orig.toLowerCase(), pos+1);
            first_run = false;
            if (pos != -1) {
                rslt += text.substring(prevpos, pos);
                rslt += replace;
                prevpos = pos + orig.length();
            }
        }
        rslt += text.substring(prevpos, text.length());
        return rslt;
    }
    
    /** Reads the contents of the tag and returns ArrayList containing all of the contents within these tags
     * 
     * @param text
     * @param tag
     * @param end_tag
     * @return
     */
    public static ArrayList<String> ReadTagContents (String text, String tag, String end_tag)  {
        ArrayList<String> result = new ArrayList<String>();
        int pos1 = 0;        
        boolean first_run = true;
        while (pos1 != -1) {
            if (first_run) pos1 = -1;
            first_run = false;
            pos1 = text.indexOf(tag, pos1+1);
            int pos2 = text.indexOf(end_tag, pos1);
            if (pos1 == -1 || pos2 == -1) return result;
            else
            result.add(text.substring(pos1+tag.length(), pos2));
        }
        return result;
    }
    
    /** Converts path that contains the install_dir_token to the correct absolute path
     * 
     * @param path
     * @param install_path
     * @return
     */
    public static String ConvertPath (String path, String install_path) {
        String ipath = null;
        if (install_path.charAt(install_path.length()-1) == GetDirectorySeparator()) ipath = install_path.substring(0, install_path.length()-1);
        else
        ipath = install_path;        
        return ReplaceString (ReplaceString(path, Utils.INSTALL_DIR_TOKEN, ipath, true), "/", String.valueOf(GetDirectorySeparator()), true);
    }
    
    /** Adds a new value to the int[] array
     * 
     * @param new_value
     * @return
     */
    public static int[] ARRAY_AddValueToArray (int new_value, int[] array) {
        int arr_length = 1;
        if (array != null && array.length != 0) arr_length = array.length+1;
        int[] result = new int[arr_length];        
        for (int x = 0; x < array.length; x++) {
            result[x] = array[x];
        }
        result[result.length - 1] = new_value;
        return result;
    }
    
    /** Adds a new value to the byte[] array
     * 
     * @param new_value
     * @return
     */
    public static byte[] ARRAY_AddValueToArray (byte new_value, byte[] array) {
        int arr_length = 1;
        if (array != null && array.length != 0) arr_length = array.length+1;
        byte[] result = new byte[arr_length];        
        for (int x = 0; x < array.length; x++) {
            result[x] = array[x];
        }
        result[result.length - 1] = new_value;
        return result;
    }    
    
    /** Deletes a value from the byte[] array
     * 
     * @param new_value
     * @return
     */
    public static byte[] ARRAY_DeleteValueFromArray (byte value, byte[] array) {
        if (array != null && array.length == 0) return array;
        ArrayList<Byte> al = new ArrayList();
        for (int x = 0; x < array.length; x++) {
            if (array[x] != value) al.add(array[x]);
        }
        byte[] result = new byte[al.size()];
        for (int x = 0; x < al.size(); x++) {
            result[x] = al.get(x);
        }
        return result;
    }        
    
    /** returns true if the array contains the value, or false if it does not
     * 
     * @param new_value
     * @return
     */
    public static boolean ARRAY_Contains (int value, int[] array) {
        for (int x = 0; x < array.length; x++) {
            if (array[x] == value) return true;
        }
        return false;
    }    
    
    /** Deletes a non-empty directory (e.g. removes all the files first and then deletes the directory)
     * 
     * @param path
     * @return
     */
    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
          File[] files = path.listFiles();
          for(int i=0; i<files.length; i++) {
             if(files[i].isDirectory()) {
               deleteDirectory(files[i]);
             }
             else {
               files[i].delete();
             }
          }
        }
        return( path.delete() );
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
            ch == '\n' ||
            ch == '\r' ||
            ch == '\t' ||
            ch == '!' ||
            ch == '*' ||
            ch == '`' ||
            ch == '~' ||
            ch == '^' || 
            ch == '$' ||
            ch == '&' ||
            ch == '_' ||
            ch == '|' ||
            ch == '\'' ||
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

    /** Reads the list of synonyms and returns a list of all read synonyms. Everything is converted to LOWERCASE
     * 
     * @param file_name
     * @return
     */
    public static ArrayList<Synonym> ReadSynonymFile(String file_name) {
        ArrayList<Synonym> result = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file_name));
            String temps = "";
            while (temps != null) {
                temps = br.readLine();
                if (temps == null) break;
                Synonym s = new Synonym();
                try {
                    s.trigger = temps.substring(0, temps.indexOf(FILE_ID_SEPARATOR)).trim().toLowerCase();
                    s.synonyms = temps.substring(temps.indexOf(FILE_ID_SEPARATOR)+1, temps.length()).split(String.valueOf(FILE_MULTIPLE_VALUE_SEPARATOR));
                    for (int x = 0; x < s.synonyms.length; x++) 
                        s.synonyms[x] = s.synonyms[x].trim().toLowerCase();
                }
                catch (Exception e) {
                    s = null;
                }
                if (s != null) result.add(s);
            }
            br.close();
        } catch (Exception e) {
        }
        finally {
            return result;
        }
    }

    /** Sets the JEditor pane the properties used for the RTFEditor pane and assigns it the text
     * 
     * @param editor
     * @param text
     * @return
     */
    public static void SetEditorProperties (JEditorPane editor, String text) {
        RTFEditorKit html = new RTFEditorKit();
	editor.setBackground( Color.white );
        editor.setEditorKit(html);
        editor.setText(text);
        editor.setCaretPosition(0);
    }
            
    
    
    /** Returns the symptom text
     * 
     * @param symptoms
     * @return
     */
    public static String[] GetRTFSymptoms (ArrayList<SelectedSymptomItem> symptoms, boolean display_symptom_name, int minimum_grade, boolean sort_by_name) {
        StringBuffer result = new StringBuffer();
        StringBuffer unformatted_result = new StringBuffer();
        StringBuffer plain_unformatted_result = new StringBuffer();
                result.append("{\\rtf1\\ansi{\\fonttbl{\\f0\\froman\\fprq2\\fcharset0 Nimbus Roman No9 L{\\*\\falt Times New Roman};"+
                      "}{\\f1\\froman\\fprq0\\fcharset128 ;}{\\f2\\fswiss\\fprq2\\fcharset0 Nimbus Sans L{\\*\\falt Arial};"+
                      "}{\\f3\\fnil\\fprq2\\fcharset128 AR PL UMing CN;}{\\f4\\fnil\\fprq2\\fcharset128 AlMateen;"+
                      "}{\\f5\\froman\\fprq2\\fcharset128 DejaVu Serif;}{\\f6\\froman\\fprq2\\fcharset128 Bitstream Vera Serif;"+
                      "}{\\f7\\froman\\fprq2\\fcharset128 Lucida Bright;}{\\f8\\froman\\fprq2\\fcharset128 "+
                      "Nimbus Roman No9 L{\\*\\falt Times New Roman};}{\\f9\\froman\\fprq0\\fcharset128 ;}"+
                      "{\\f10\\fnil\\fprq2\\fcharset0 DejaVu Sans;}}"+
                      "{\\colortbl;\\red0\\green0\\blue0;"+
                      "\\red180\\green180\\blue180;"+
                      "\\red0\\green100\\blue0;"+
                      "\\red130\\green0\\blue0;" +
                      "\\red0\\green0\\blue190;}");
        for (int x = 0; x < symptoms.size(); x++) {
            SelectedSymptomItem ssi = symptoms.get(x).DeepCopy();
            if (display_symptom_name)
            {
                result.append("\\pard\\f8\\li300\\fi-300{\\b "+ssi.sym_name+"}: ");
                unformatted_result.append(ssi.sym_name+"@"+"|");
                plain_unformatted_result.append(ssi.sym_name+": ");
            }
            else
            {
                result.append("\\pard\\f8 ");                
            }
            if (sort_by_name) Collections.sort(ssi.remsymptoms);
            for (int y = 0; y < ssi.remsymptoms.size(); y++) {
                if (ssi.remsymptoms.get(y).RemGrade*ssi.maximum_grade < minimum_grade && minimum_grade != -1) continue;
                //colors -> 0=black,1=gray,2=blue,3=red
                // there are only symptoms with 1 grade
                if (ssi.maximum_grade == 1) {
                    result.append(ssi.remsymptoms.get(y).RemSC);
                }
                // there are only symptoms with 2 grades
                if (ssi.maximum_grade == 2) {
                    if (ssi.remsymptoms.get(y).RemGrade == 1)
                    result.append("{\\b "+ssi.remsymptoms.get(y).RemSC+"}");
                    else
                    result.append(ssi.remsymptoms.get(y).RemSC);                    
                }
                // there are only symptoms with 3 grades
                if (ssi.maximum_grade == 3) {
                    if (ssi.remsymptoms.get(y).RemGrade == 1)
                    result.append("{\\b \\cf4 "+ssi.remsymptoms.get(y).RemSC.toUpperCase()+"}");
                    else
                    if (ssi.remsymptoms.get(y).RemGrade*ssi.maximum_grade == 2)
                    result.append("{\\b \\cf5 "+ssi.remsymptoms.get(y).RemSC+"}");
                    else
                    result.append(ssi.remsymptoms.get(y).RemSC);
                }
                // there are only symptoms with 4 grades
                if (ssi.maximum_grade == 4) {
                    if (ssi.remsymptoms.get(y).RemGrade == 1)
                    result.append("{\\b \\cf4 "+ssi.remsymptoms.get(y).RemSC.toUpperCase()+"}");
                    else
                    if (ssi.remsymptoms.get(y).RemGrade*ssi.maximum_grade == 3)
                    result.append("{\\b \\cf5 "+ssi.remsymptoms.get(y).RemSC+"}");
                    else
                    if (ssi.remsymptoms.get(y).RemGrade*ssi.maximum_grade == 2)
                    result.append("{\\b "+ssi.remsymptoms.get(y).RemSC+"}");
                    else
                    result.append(ssi.remsymptoms.get(y).RemSC);
                }                
                // there are only symptoms with 5 grades
                if (ssi.maximum_grade >= 5) {
                    if (ssi.remsymptoms.get(y).RemGrade == 1)
                    result.append("{\\b \\cf4 "+ssi.remsymptoms.get(y).RemSC.toUpperCase()+"}");
                    else
                    if (ssi.remsymptoms.get(y).RemGrade*ssi.maximum_grade == 4)
                    result.append("{\\b \\cf4 "+ssi.remsymptoms.get(y).RemSC+"}");
                    else
                    if (ssi.remsymptoms.get(y).RemGrade*ssi.maximum_grade == 3)
                    result.append("{\\b \\cf5 "+ssi.remsymptoms.get(y).RemSC+"}");
                    else
                    if (ssi.remsymptoms.get(y).RemGrade*ssi.maximum_grade == 2)                    
                    result.append("{\\b "+ssi.remsymptoms.get(y).RemSC+"}");
                    else
                    result.append(ssi.remsymptoms.get(y).RemSC);
                }
                unformatted_result.append(ssi.remsymptoms.get(y).RemSC);
                plain_unformatted_result.append(ssi.remsymptoms.get(y).RemSC);
                if (y != ssi.remsymptoms.size() - 1) {
                    result.append(" ");
                    unformatted_result.append("|");
                    plain_unformatted_result.append(", ");
                }
            }
            result.append("\\par");
            unformatted_result.append('\n');
            plain_unformatted_result.append('\n');
        } 
        String[] rslt = new String[3];
        rslt[0] = result.toString()+"}";
        rslt[1] = unformatted_result.toString();
        rslt[2] = plain_unformatted_result.toString();
        return rslt;        
    }                
    
    /** Converts the date in format yyyy/mm/dd to the age of the person
     * 
     * @param date
     * @return
     */
    public static String ConvertDateToAge (String date) {
        if (date == null) return "";
        int year, month, day;
        try {
            int pos = date.indexOf("/");
            int prev_pos = pos + 1;
            year = Integer.parseInt(date.substring(0, pos));
            pos = date.indexOf("/", pos+1);
            month = Integer.parseInt(date.substring(prev_pos, pos));
            prev_pos = pos + 1;            
            day = Integer.parseInt(date.substring(prev_pos, date.length()));
        } catch (Exception e) {
            return "";
        }
        GregorianCalendar cal = new GregorianCalendar();
        int age = cal.get(Calendar.YEAR) - year;
        if (month > (cal.get(Calendar.MONTH)+1) || (month == (cal.get(Calendar.MONTH)+1) && day > cal.get(Calendar.DAY_OF_MONTH))) age--;
        return String.valueOf(age);
    }

    /** Sorts an array of repertorization results by specified criteria
     * 
     * @param rep_res
     * @param sort_by REM_SC = 0, CNT = 1, VALUE = 2
     * @param ascending
     */
    public static ArrayList<RepertorisationResult> SortRepertorisationResults (ArrayList<RepertorisationResult> rep_res, int sort_by, boolean ascending) {
        ArrayList<RepertorisationResult> result = new ArrayList();
        // sort by remedy shortcut
        if (sort_by == 0) {
            ArrayList<String> temp = new ArrayList();
            for (int x = 0; x < rep_res.size(); x++) {
                temp.add(rep_res.get(x).RemSC);
            }
            Collections.sort(temp);
            if (ascending) {
                for (int x = 0; x < temp.size(); x++) {
                    for (int y = 0; y < rep_res.size(); y++) {
                        if (rep_res.get(y).RemSC.equals(temp.get(x))) {
                            result.add(rep_res.get(y));
                            rep_res.remove(y);
                            break;
                        }
                    }
                }
            }
            else
            {
                for (int x = temp.size() - 1; x >= 0 ; x--) {
                    for (int y = 0; y < rep_res.size(); y++) {
                        if (rep_res.get(y).RemSC.equals(temp.get(x))) {
                            result.add(rep_res.get(y));
                            rep_res.remove(y);
                            break;
                        }
                    }
                }                
            }
        }
        else
        // sort by count
        if (sort_by == 1) {
            ArrayList<Short> temp = new ArrayList();
            for (int x = 0; x < rep_res.size(); x++) {
                temp.add(rep_res.get(x).cnt);
            }
            Collections.sort(temp);
            if (ascending) {
                for (int x = 0; x < temp.size(); x++) {
                    for (int y = 0; y < rep_res.size(); y++) {
                        if (rep_res.get(y).cnt == temp.get(x)) {
                            result.add(rep_res.get(y));
                            rep_res.remove(y);
                            break;
                        }
                    }
                }
            }
            else
            {
                for (int x = temp.size() - 1; x >= 0 ; x--) {
                    for (int y = 0; y < rep_res.size(); y++) {
                        if (rep_res.get(y).cnt == temp.get(x)) {
                            result.add(rep_res.get(y));
                            rep_res.remove(y);
                            break;
                        }
                    }
                }                
            }
        }            
        else
        // sort by value
        if (sort_by == 2) {
            ArrayList<Short> temp = new ArrayList();
            for (int x = 0; x < rep_res.size(); x++) {
                temp.add(rep_res.get(x).value);
            }
            Collections.sort(temp);
            if (ascending) {
                for (int x = 0; x < temp.size(); x++) {
                    for (int y = 0; y < rep_res.size(); y++) {
                        if (rep_res.get(y).value == temp.get(x)) {
                            result.add(rep_res.get(y));
                            rep_res.remove(y);
                            break;
                        }
                    }
                }
            }
            else
            {
                for (int x = temp.size() - 1; x >= 0 ; x--) {
                    for (int y = 0; y < rep_res.size(); y++) {
                        if (rep_res.get(y).value == temp.get(x)) {
                            result.add(rep_res.get(y));
                            rep_res.remove(y);
                            break;
                        }
                    }
                }                
            }
        }
        return result;        
    }    

    /** Returns only numeric data from the string
     * 
     * @param str
     * @return
     */
    public static int GetNumericDataFromString(String str) {
        if (str == null) return -1;
        String temps = "";
        for (int x = 0; x < str.length(); x++) {
            if (str.charAt(x) == '0' || 
                str.charAt(x) == '1' || 
                str.charAt(x) == '2' || 
                str.charAt(x) == '3' || 
                str.charAt(x) == '4' || 
                str.charAt(x) == '5' || 
                str.charAt(x) == '6' || 
                str.charAt(x) == '7' || 
                str.charAt(x) == '8' || 
                str.charAt(x) == '9') temps += str.charAt(x);                
        }
        try{
            return Integer.parseInt(temps);
        } catch (Exception e) {
            return -1;
        }
    }

/** Copies the submitted rtf text to clipboard
     * 
     * @param rtftext
     */
    public static void CopyToRtf(String rtftext, String unformatted) {
        RtfTest r = new RtfTest(rtftext, unformatted);
    }

    /** A class that allows to copy rtf text into clipboard
    *  
    */
    static class RtfTest implements ClipboardOwner {

        public RtfTest(String rtftext, String unformatted) {

            StringBuffer s = new StringBuffer();
            s.append(rtftext);

            RtfSelection sel = new RtfSelection(s.toString(), unformatted);

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, this);            
            
        }

        public void lostOwnership(Clipboard cl, Transferable content) {
        }

        class RtfSelection implements Transferable {
            DataFlavor rtfFlavor;
            DataFlavor[] supportedFlavors;
            private String content;
            private String unformatted;
            public RtfSelection(String s, String unformatted_text) {
                content = s;
                unformatted = unformatted_text;
                try {
                    rtfFlavor = new DataFlavor("text/rtf; class=java.io.InputStream");
                    supportedFlavors = new DataFlavor[] {rtfFlavor, DataFlavor.stringFlavor};
                } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(rtfFlavor) || flavor.equals(DataFlavor.stringFlavor);
        }

        public java.awt.datatransfer.DataFlavor[] getTransferDataFlavors() {
            return supportedFlavors;
        }

        public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {

            if (flavor.equals(DataFlavor.stringFlavor)) {                
                return unformatted;
            }
            else if (flavor.equals(rtfFlavor)) {
                byte[] byteArray = content.getBytes();
                return new ByteArrayInputStream(byteArray);
            }
            throw new UnsupportedFlavorException(flavor);
            }
        }
    }

    /** Reads data from an URLAddress to the string
     *
     * @param URLAddress
     * @return
     */
    public static ArrayList<String> ReadURL (String URLAddress) {
	URL address;
    try {
        address = new URL(URLAddress);
    } catch (MalformedURLException ex) {
        return null;
    }
    ArrayList<String> sb = new ArrayList();
    try {
        BufferedReader in = new BufferedReader(new InputStreamReader(address.openStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null)
            sb.add(inputLine);
        in.close();
    } catch (Exception e) {
        return null;
    }
    return sb;
    }

    /** Splits the search string according to the following rule:
     *  1. If the split_char character is inside "" -> do not split
     *
     * @param search_string
     * @return
     */
    public static String[] SplitSearchString(String search_string, char split_char, boolean clear_exact_match_char) {
        ArrayList<String> result = new ArrayList();
        String temps = "";
        boolean delim_start = false;
        for (int x = 0; x < search_string.length(); x++) {
            if (search_string.charAt(x) == EXACT_MATCH_CHAR) {
                delim_start = !delim_start;
                if (!clear_exact_match_char) temps += search_string.charAt(x);
            }
            else
            if (search_string.charAt(x) == split_char && !delim_start) {
                temps = temps.trim();
                if (!temps.equals("")) result.add(temps);
                temps = "";
            }
            else
            temps += search_string.charAt(x);
        }
        temps = temps.trim();
        if (!temps.equals("")) result.add(temps);
        String[] rslt = new String[result.size()];
        for (int x = 0; x < result.size(); x++)
            rslt[x] = result.get(x);
        return rslt;
    }
        
}
