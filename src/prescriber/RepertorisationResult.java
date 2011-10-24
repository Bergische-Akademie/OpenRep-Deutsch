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

/* This class contains all the data about the repertorization results generated in the Database class
 */

package prescriber;

class SortingConstants {
    public static byte SORT_BY_REMSC = 0;
    public static byte SORT_BY_VALUE = 1;
    public static byte SORT_BY_CNT = 2;
}

public class RepertorisationResult implements Comparable {
    /** repertorization value*/
    public short value;
    /** number of symptoms in which the remedy is located*/
    public short cnt;
    /** remedy shortcut*/
    public String RemSC;
    /** remedy full name*/
    public String RemName;
    /** sort by which column */
    public byte sort_by = prescriber.SortingConstants.SORT_BY_VALUE;
    
    public int compareTo(Object o) {
        RepertorisationResult rr = (RepertorisationResult)o;
        if (sort_by == SortingConstants.SORT_BY_REMSC) {
            return this.RemSC.compareTo(rr.RemSC);
        }
        else
        if (sort_by == SortingConstants.SORT_BY_CNT) {
            if (this.cnt > rr.cnt) return -1;
            else
            if (this.cnt == rr.cnt) return 0;
            else
            return 1;
        }
        else
        {
            if (this.value > rr.value) return -1;
            else
            if (this.value == rr.value) return 0;
            else
            return 1;
        }

    }
}
