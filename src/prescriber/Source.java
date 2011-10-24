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

/** Contains all the information about one source of information
 *
 * @author vladimir
 */
public class Source {
   /** unique id of a source*/
   public short id;
   /** author of a source*/
   public String author;
   /** name of the book*/
   public String work;
   /** shortcut of the book*/
   public String shortcut;

    @Override
    public String toString() {
        return this.author + " - " + this.work;
    }
   
   
   
}
