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

import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;


/**
 * The main class that bundles command line parsing, interaction with the user,
 * exception handling and JAR signing and verification work.
 */
public class Main {
    /**
     * The main method to run from another program.
     * Parses the arguments, and performs the actual work 
     * on JAR signing and verification. 
     * If something goes wrong an exception is thrown.
     * 
     * @param args -
     *            command line with options.
     */
    public static void run(String[] args, OutputStream out) throws Exception {
        // set up logging
        Logger logger = Logger.getLogger(JSParameters.loggerName);
        logger.setUseParentHandlers(false);
        Handler handler = new StreamHandler(out, new JSLogFormatter());
        logger.addHandler(handler);
        
        // parse command line arguments
        JSParameters param = ArgParser.parseArgs(args, null);
        // print help if incorrect or no arguments
        if (param == null) {
            JSHelper.printHelp();
            return;
        }
        // do the actual work, now we have only implemented signjar
        if (param.isVerify()) {
            JSVerifier.verifyJar(param); // it is not used
        } else {
            JSSigner.signJar(param);
        }
    }

    
    /**
     * The main method to run from command line.
     * 
     * @param args -
     *            command line with options.
     */
    public static void main(String[] args) {
        try {
            run(args, System.out);
        } catch (Exception e) {
            System.out.print("JarSigner error: "
                    + e
                    + ((e.getCause() != null) ? ", caused by " + e.getCause()
                            : ""));
            e.printStackTrace();
        }
    }

}

