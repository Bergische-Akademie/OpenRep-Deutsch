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

/* This class is used to log users actions for debug purposes
 */

package prescriber;

import java.io.FileWriter;

/**
 *
 * @author vladimir
 */
public class Logger {

    public static String OutputFile = "";
    
    public static String Operation_SaveRepertory = "[SAVE_REPERTORY] ";
    public static String Operation_SaveHistoryFile = "[SAVE_REPERTORY_HISTORY_FILE] ";
    public static String Operation_OpenRepertory = "[OPEN_REPERTORY] ";
    public static String Operation_ReadRemedies = "[READ_REMEDIES] ";    
    public static String Operation_ReadMainSymptoms = "[READ_MAINSYMPTOMS] ";        
    public static String Operation_ReadPatients = "[READ_PATIENTS] ";
    public static String Operation_WritePatients = "[WRITE_PATIENTS] ";
    public static String Operation_ReadDiagnoses = "[READ_DIAGNOSES] ";
    public static String Operation_WriteDiagnoses = "[WRITE_DIAGNOSES] ";    
    
    public static String Warning_Memory = "[WARNING_MEMORY] ";
    
    public static String Status_Init = "init ";
    public static String Status_Success = "success ";
    public static String Status_Failure = "failure ";
    
    /** contains the logged data */
    public static StringBuffer Log = new StringBuffer ();
    
    /** Starts the logger
     * 
     * @param Operation
     * @param comment
     */    
    public static void StartLogger () {
        AddEntryToLog("==============================================================================\n");                
    }
    
    /** Adds the entry about an initialization of an operation
     * 
     * @param Operation
     * @param comment
     */    
    public static void AddInitEntry (String Operation, String comment) {
        Log.append(Operation+Status_Init+comment);
    }

    /** Adds the entry about a success of an operation
     * 
     * @param Operation
     * @param comment
     */    
    public static void AddSuccessEntry (String Operation, String comment) {
        Log.append(Operation+Status_Success+comment);
    }
    
    /** Adds the entry about a failure of an operation
     * 
     * @param Operation
     * @param comment
     */
    public static void AddFailureEntry (String Operation, String comment) {
        Log.append(Operation+Status_Failure+comment);
    }        
    
    /** Adds an entry to the log
     * 
     * @param entry
     */
    public static void AddEntryToLog (String entry) {
        try {
            FileWriter fw = new FileWriter(OutputFile, true);
            fw.write(entry+"\n");
            fw.close();
        } 
        catch (Exception e) {            
        }
        Log.append(entry+"\n");
    }
    
    /** Retrieves the contents of the log
     * 
     * @return
     */
    public static String GetLog() {
        return Log.toString();
    }
}
