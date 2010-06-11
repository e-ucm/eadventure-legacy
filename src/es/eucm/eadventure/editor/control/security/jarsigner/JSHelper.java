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

import java.util.logging.Logger;

/**
 * The class prints JarSigner help message.
 */
class JSHelper {
    /**
     * Prints help message. 
     */
    static void printHelp(){
        StringBuilder buf = new StringBuilder();
        buf.append("JarSigner help.");
        buf.append("\nUsage:");
        buf.append("\tjarsigner {options} JAR-file alias");
        buf.append("\n\tjarsigner -verify {options} JAR-file");
        
        buf.append("\n\n-verify\t\t\t\t verify a signed JAR file");
        
        buf.append("\n-keystore <keystore_path>\t location of the keystore");
        
        buf.append("\n-storetype <keystore_type>\t type of the keystore");
        
        buf.append("\n-storepass <keystore_password>\t keystore password");
        
        buf.append("\n-keypass <key_password>\t\t private key password ");
        buf.append("(if differs from <keystore_password>)");
        
        buf.append("\n-signedjar <file_name>\t\t name of the signed JAR file");

        buf.append("\n-sigfile <file_name>\t\t name of .SF and .DSA files");
        
        buf.append("\n-verbose \t\t\t provide additional output");
        
        buf.append("\n-silent \t\t\t provide as few output as possible");
        
        buf.append("\n-certs \t\t\t\t display certificates ");
        buf.append("(use with -verify and -verbose)");
        
        buf.append("\n-tsa <TSA_URL>\t\t\t location of time-stamp authority");
        
        buf.append("\n-tsacert <TSA_cert_alias>\t keystore alias of the ");
        buf.append("TSA certificate");
        
        buf.append("\n-proxy <host_address>{:<port>}\t proxy server host ");
        buf.append("address and port, e.g. proxy.server.com:1234");

        buf.append("\n-proxytype <type_name>\t\t type of the proxy server (HTTP or SOCKS)");
        
        buf.append("\n-providerclass <class>\t\t class name of cryptographic ");
        buf.append("service provider");
        
        buf.append("\n-providername <name>\t\t provider name");
        
        buf.append("\n-ksproviderclass <class>\t class name of cryptographic ");
        buf.append("service provider for managing keystore");
        
        buf.append("\n-ksprovidername <name>\t\t keystore provider name");
        
        buf.append("\n-sigproviderclass <class>\t class name of cryptographic ");
        buf.append("service provider for work with signatures");
        
        buf.append("\n-sigprovidername <name>\t\t signature provider name");
        
        buf.append("\n-certproviderclass <class>\t class name of cryptographic ");
        buf.append("service provider for work with certificates");
        
        buf.append("\n-certprovidername <name>\t certificate provider name");
        
        buf.append("\n-mdproviderclass <class>\t class name of cryptographic ");
        buf.append("service provider for work with message digests");
        
        buf.append("\n-mdprovidername <name>\t\t message digest provider name\n");
        
        Logger.getLogger(JSParameters.loggerName).info(buf.toString());
    }
}

