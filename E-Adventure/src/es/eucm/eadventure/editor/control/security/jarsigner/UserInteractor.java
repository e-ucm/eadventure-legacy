/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.security.jarsigner;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class to interact with user - ask for confirmations, and necessary parameters
 * which haven't been set in the command line.
 */
class UserInteractor {
    // used to get additional data prompted
    private static InputStreamReader in = new InputStreamReader(System.in);

    // buffer for the data read
    private static char[] readData = new char[256];

    // number of symbols read
    private static int charsRead;

    // length of the "\r\n" which is added to the end of the line,
    // when ENTER is pressed.
    private static int newLineLength = 2;

    // Prints prompt and waits the user to enter the needed data,
    // the data is returned.
    static char[] getDataFromUser(String prompt) throws IOException {
        System.out.println(prompt);
        charsRead = in.read(readData);
        char[] password = new char[charsRead - newLineLength];
        System.arraycopy(readData, 0, password, 0, charsRead - newLineLength);
        return password;
    }
}

