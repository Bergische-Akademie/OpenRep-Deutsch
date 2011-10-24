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

/** Contains all the information about one itom selected in the repertorization. 
 *
 * @author vladimir
 */
public class SelectedSymptomItem implements Comparable {
   /** symptom id */
   public int sym_id;
   /** remedies in this symptom */
   public ArrayList<SelectedRemSymptom> remsymptoms = new ArrayList();
   /** unique name of the repertory from which we have taken this symptom */
   public String repertory_name;
   /** contains the unique repertory id */
   public short repertory_id;
   /** grade of the symptom */
   public byte symptom_grade = Database.SYMPTOM_GRADE_AVERAGE;
   /** name of the symptom */
   public String sym_name;
   /** maximum grade in the repertory */
   byte maximum_grade;
   /** is the symptom member of a positive filter?*/
   boolean positive_filter = false;
   /** is the symptom member of a negative filter?*/
   boolean negative_filter = false; 
   /** number of the desktop in which is the symptom added*/
   int desktop = 0;

    public int compareTo(Object o) {
        SelectedSymptomItem temp = (SelectedSymptomItem)o;
        return (this.sym_name.compareToIgnoreCase(temp.sym_name));
    }

    /** Creates a deep copy of the object
     * 
     * @return
     */
    public SelectedSymptomItem DeepCopy () {
        SelectedSymptomItem ssi = new SelectedSymptomItem();
        ssi.desktop = this.desktop;
        ssi.maximum_grade = this.maximum_grade;
        ssi.negative_filter = this.negative_filter;
        ssi.positive_filter = this.positive_filter;
        ssi.repertory_id = this.repertory_id;
        ssi.repertory_name = this.repertory_name;
        ssi.sym_id = this.sym_id;
        ssi.sym_name = this.sym_name;
        ssi.symptom_grade = this.symptom_grade;
        ssi.remsymptoms = new ArrayList();
        for (int x = 0; x < this.remsymptoms.size(); x++) { 
            SelectedRemSymptom srs = new SelectedRemSymptom();            
            srs.RemGrade = this.remsymptoms.get(x).RemGrade;
            srs.RemSC = this.remsymptoms.get(x).RemSC;
            srs.Remname = this.remsymptoms.get(x).Remname;
            ssi.remsymptoms.add(srs);
        }
        return ssi;
    }
    
    @Override
    public String toString() {
        return this.sym_name + Utils.SYMPTOM_COUNT_START + this.remsymptoms.size()+Utils.SYMPTOM_COUNT_END;
    }
   
   
}
