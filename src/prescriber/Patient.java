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

/** This class contains all the information about the patient
 *
 * @author vladimir
 */
public class Patient {
    /** unique id of the patient*/
    public int id;
    /** patient's name*/
    public String name;
    /** patient's surname*/    
    public String surname;
    /** patient's address*/    
    public String address;
    /** patient's email*/    
    public String email;
    /** comment to the patient */
    public String comment;
    /** patient's telephone number*/    
    public String telephone;
    /** additional information about the patient*/    
    public String additional_information;
    /** sex of the patient TRUE = male, FALSE = female*/
    public boolean sex = true;
    /** date of birth in the yyyy/mm/dd format */
    public String date_of_birth;
    
    /** Creates a Deep Copy of patient's data into a new instantiation of the object
     * 
     * @param source_data
     */
    public void DeepCopy (Patient source_data) {
         this.name = source_data.name;
         this.surname = source_data.surname;
         this.address = source_data.address;
         this.comment = source_data.comment;
         this.email = source_data.email;
         this.telephone = source_data.telephone;
         this.additional_information = source_data.additional_information;
         this.id = source_data.id;
         this.sex = source_data.sex;
         this.date_of_birth = source_data.date_of_birth;
    }
}
