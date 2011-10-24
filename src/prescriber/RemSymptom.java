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

/* A class used to save remedy additions in symptom
 */

package prescriber;

/**
 *
 * @author vladimir
 */

public class RemSymptom {
   /** unique id of the symptom to which this RemSymptom belongs to*/
   public int id;
   /** collection of rem_id / rem_grade pairs */
   public java.util.ArrayList<RemSymptomItem> items = new java.util.ArrayList();
}
