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

import java.util.ArrayList;

/** This class is used to record patient's diagnosis information
 *
 * @author vladimir
 */
public class PatientDiagnosis implements Comparable {
   /** date of the diagnosis*/
   public String date;
   /** short description of the diagnosis*/   
   public String short_description;
   /** description of the diagnosis*/   
   public String description;
   /** contains prescriptions */   
   public String prescription;
   
   /** contains the list of appendices added to this diagnosis*/
   public ArrayList<PatientAppendix> appendices = new ArrayList();
   
   /** Returns the prescriptions
    * 
    * @return
    */
   public String GetPrescriptions() {       
       return prescription;
   }
   
   /** Returns the prescription text
    * 
    * @return
    */
   public String GetPrescriptionText() {
        return prescription;
   }   
   
   /** Sets the prescriptions
    * 
    * @return
    */
   public void SetPrescriptions(String text) {
       prescription = text;
   }   
   
   /** Compared objects based on the date
    * 
    * @param o
    * @return
    */
    public int compareTo(Object o) {
        PatientDiagnosis pd = (PatientDiagnosis)o;
        return this.date.compareTo(pd.date);
    }
   
}
