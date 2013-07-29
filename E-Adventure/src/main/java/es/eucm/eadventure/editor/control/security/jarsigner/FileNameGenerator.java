/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.security.jarsigner;

/**
 * Class to build the base file names for .SF and .DSA files.
 * It is designed to be used only in JSParameters.
 */
class FileNameGenerator {
    private static final int fileNameLength = 8;
    private static final int maxExtLength = 3;
    
    /**
     * Generates the file name for .SF and .DSA files using
     * sigFileName or alias given on the command line. 
     * 
     * @param param
     * @return
     * @throws NullPointerException - if both parameters are null 
     */
    static String generateFileName(String sigFileName, String alias){
        if (sigFileName != null){
            return convertString(sigFileName.toUpperCase());
        }
        if (alias == null){
            throw new NullPointerException("Alias is null.");
        }
        int length = alias.length();
        if (length > fileNameLength){
            alias = alias.substring(0, fileNameLength);
            length = fileNameLength;
        } 
        
        alias = convertString(alias);
        return alias.toUpperCase();
    }
 
    /**
     *  Generates signature block file name, based on key algorithm.
     * 
     * @param sigFileName
     * @param keyAlg
     * @return
     */ 
    static String generateSigBlockName(String sigFileName, String keyAlg) {
        // make an extension
        String sigBlockFileExt;
        // max allowed extension length is 3 symbols
        if (keyAlg.length() > maxExtLength) {
            sigBlockFileExt = "."
                    + (keyAlg.substring(0, maxExtLength)).toUpperCase();
        } else {
            sigBlockFileExt = "." + keyAlg.toUpperCase();
        }

        // add a prefix if necessary
        if (keyAlg.equalsIgnoreCase("DSA") || keyAlg.equalsIgnoreCase("RSA")) {
            // no prefix
            return sigFileName + sigBlockFileExt;
        } else {
            // add prefix "SIG-"
            return "SIG-" + sigFileName + sigBlockFileExt;
        }
    }

    
    // Finds disallowed letters in input String and converts
    // them to underscores ("_"). Allowed characters are letters, digits,
    // hyphens and underscores. If no changes are made, the input string itself
    // is returned (not a copy!).
    private static String convertString(String input){
        char [] chars = input.toCharArray();
        boolean isChanged = false; 
        for (int i = 0; i < chars.length; i++){
            char current = chars[i];
            if ((current >= 'A' && current<= 'Z') || 
                    (current >= 'a' && current <= 'z') ||
                    (current >= '0' && current <= '9') ||
                    current == '-' || current == '_'){
                continue;
            }
            
            isChanged = true;
            chars[i] = '_';
        }
        if (isChanged){
            return new String(chars);
        } else {
            return input;
        }
    }
}

