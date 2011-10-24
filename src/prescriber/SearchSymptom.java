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

/** SearchSymptom is the descendant of the Symptom object that is used to display differently the names of 
 *  Symptoms when displaying them after the search was performed, e.g. not in the format of the Tree, but in the format
 *  of the symptom list.
 *
 * @author vladimir
 */
public class SearchSymptom extends Symptom {
    
    @Override
    public String toString() {
        if (this.additions != 0) {
            if (this.RepertorySC == null) return this.SymName + Utils.SYMPTOM_COUNT_START+this.additions+Utils.SYMPTOM_COUNT_END;
            else
            return this.SymName + Utils.SYMPTOM_COUNT_START+this.additions+Utils.SYMPTOM_COUNT_END + " (" + this.RepertorySC+")";
        }
        else
        {
            if (this.RepertorySC == null) return this.SymName;
            else
            return this.SymName + " (" + this.RepertorySC+")";
        }
    }
}
