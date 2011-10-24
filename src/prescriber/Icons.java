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

/* How does this work?
 * This class is used to store images and attribute them integer constant values, so that they can be easily accessed.
 * 1. The constructor loads images into a list of images and attributes each image an integer value
 * 2. This integer value is contained in the WHOLE_REST, WHOLE_NOTE, etc... constants
 * 3. If the image could not be loaded, the class just skips the picture and attributes the next one with the value
 *    - in this way, the pictures that are loaded contain some values, if they could ot be loaded, they will contain -1
 * 4. Retrieve the image by calling the GetImage function with a parameter of the integer index value of the image
 * 
*/
package prescriber;

import java.awt.image.BufferedImage;

public class Icons {
    private String picture_path = "";
    public static String pictures_dir = "images";
    // list of image names
    public static final String tree_group = "tree_group.png";
    public static final String tree_group_ref = "tree_group_ref.png";
    public static final String tree_leaf = "tree_leaf.png";
    public static final String tree_leaf_ref = "tree_leaf_ref.png";
    public static final String tree_positive = "add.png";
    public static final String tree_negative = "delete.png";
    
    
    // list of constants used to retrieve data externally
    public static int TREE_GROUP;
    public static int TREE_GROUP_REF;
    public static int TREE_LEAF;
    public static int TREE_LEAF_REF;
    public static int TREE_POSITIVE;
    public static int TREE_NEGATIVE;
    
    /** list of images */
    private java.util.ArrayList <java.awt.image.BufferedImage> images = new java.util.ArrayList ();
    
    private StringBuffer sb = new StringBuffer();

    /** Adds an image into the list 
     * 
     * @param ImageName
     * @return - returns the index of inserted image
     */
    private int AddImage (String ImageName) {
      try {        
        java.awt.image.BufferedImage bi = javax.imageio.ImageIO.read(new java.io.File (ImageName));          
        images.add(bi);
        return images.size()-1;
      }
      catch (Exception e) {
          return -1;
      }
    }

    /** Constructor that also loads images into the list of images
     * 
     */
    Icons (String path) {
      picture_path = path;
      TREE_GROUP = AddImage(picture_path+Utils.GetDirectorySeparator()+tree_group);
      TREE_GROUP_REF = AddImage(picture_path+Utils.GetDirectorySeparator()+tree_group_ref);
      TREE_LEAF = AddImage(picture_path+Utils.GetDirectorySeparator()+tree_leaf);      
      TREE_LEAF_REF = AddImage(picture_path+Utils.GetDirectorySeparator()+tree_leaf_ref);
      TREE_POSITIVE = AddImage(picture_path+Utils.GetDirectorySeparator()+tree_positive);
      TREE_NEGATIVE = AddImage(picture_path+Utils.GetDirectorySeparator()+tree_negative);      
    }

    /** Returns image from the ArrayList containing all images loaded in the constructor
     * 
     * @param ImageIndex - index of image (e.g. G_CLEF, F_CLEF...etc..)
     * @return
     */
    public BufferedImage GetImage (int ImageIndex) {
        try {
            return images.get(ImageIndex);
        }
        catch (Exception e) {
            return null;
        }
    }
     
    public StringBuffer GetErrorMessages() {
        return sb;
    }
    
}
