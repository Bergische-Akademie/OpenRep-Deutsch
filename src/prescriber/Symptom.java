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

/** Contains all the information about one symptom
 *
 * @author vladimir
 */
public class Symptom implements Comparable {
    /** unique id of a symptom */
    public int id;
    /** name of a symptom */
    public String SymName;
    /** id of the parent of this symptom - used in the tree view*/
    public int parent_id = -1;
    /** fraction name of a symptom*/
    public String fraction_name;
    /** number of additions */
    public short additions = 0;
    /** id of the repertory to which the symptom belongs to */
    public short RepertoryID = -1;
    /** repertory shortcut */
    public String RepertorySC = null;
    /** this is used only in the ReversedMM to identify the remedy grade*/
    public byte grade = 1;

    /** This is used to genereate the JTree - the string values here returns the symptom fragment
     * 
     * @return
     */
    @Override
    public String toString() {
        if (this.additions != 0)
        return this.fraction_name + Utils.SYMPTOM_COUNT_START+this.additions+Utils.SYMPTOM_COUNT_END;
        else
        return this.fraction_name;
    }
        
    public Symptom DeepCopy () {
        Symptom s = new Symptom();
        s.SymName = this.SymName;
        s.fraction_name = this.fraction_name;
        s.grade = this.grade;
        s.id = this.id;
        s.RepertoryID = this.RepertoryID;
        s.parent_id = this.parent_id;
        s.additions = this.additions;
        return s;
    }
    
    /** Sorts the list by the value[0]
     * 
     * @param arg0
     * @return
     */
    public int compareTo(Object arg0) {
        Symptom di = (Symptom)arg0;
        return this.SymName.compareTo(di.SymName);
    }    
    
    /** Converts the Symptom to SearchSymptom
     * 
     * @return
     */
    public SearchSymptom GetSearchSymptom () {
        SearchSymptom ss = new SearchSymptom();
        ss.SymName = this.SymName;
        ss.additions = this.additions;
        ss.fraction_name = this.fraction_name;
        ss.id = this.id;
        ss.grade = this.grade;
        ss.parent_id = this.parent_id;
        return ss;
    }
}
