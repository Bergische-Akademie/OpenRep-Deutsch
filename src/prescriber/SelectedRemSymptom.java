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

/** This class contains the complete information about the RemSymptom repertorization, e.g. remedyname, remedy shortcut,
 *  remedy id etc. This is done in order to be able to mix multiple repertories and evaluate them together. The
 *  identifier used to distinguish the remedies is of course the full remedy name (it is not case sensitive).
 *
 * @author vladimir
 */
public class SelectedRemSymptom implements Comparable {
    /** full remedy name*/
    public String Remname;
    /** remedy shortcut */
    public String RemSC;
    /** remedy grade - percentual value in accordance to the highest value used in repertory */
    public double RemGrade;

    public int compareTo(Object o) {
        SelectedRemSymptom srs = (SelectedRemSymptom)o;
        return this.RemSC.compareToIgnoreCase(srs.RemSC);
    }
}
