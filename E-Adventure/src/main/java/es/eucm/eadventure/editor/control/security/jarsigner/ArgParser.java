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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.util.logging.Logger;

/**
 * The class to parse the program arguments. 
 */
class ArgParser {
    // options names to compare to //
    final static String sVerify = "-verify";

    final static String sKeyStore = "-keystore";
    
    final static String sStoreType = "-storetype";
    
    final static String sStorePass = "-storepass";
    
    final static String sKeyPass = "-keypass";
    
    final static String sSigFile = "-sigfile";
    
    final static String sSignedJAR = "-signedjar";
    
    final static String sCerts = "-certs";
    
    final static String sVerbose = "-verbose";
    
    final static String sInternalSF = "-internalsf";
    
    final static String sSectionsOnly = "-sectionsonly";
    
    final static String sProvider = "-providerclass";
    
    final static String sProviderName = "-providername";

    final static String sCertProvider = "-certproviderclass";
    
    final static String sCertProviderName = "-certprovidername";

    final static String sSigProvider = "-sigproviderclass";
    
    final static String sSigProviderName = "-sigprovidername";

    final static String sKSProvider = "-ksproviderclass";
    
    final static String sKSProviderName = "-ksprovidername";
    
    final static String sMDProvider = "-mdproviderclass";
    
    final static String sMDProviderName = "-mdprovidername";
    
    final static String sTSA = "-tsa";
    
    final static String sTSAcert = "-tsacert";
    
    final static String sProxy = "-proxy";
    
    final static String sProxyType = "-proxytype";

    final static String sSilent = "-silent";
    
    final static String sAltSigner = "-altsigner";
    
    final static String sAltSignerPath = "-altsignerpath";
    
    
    /**
     * @param args
     * @param param
     * @return new instance of JSParameters if param is null or updated param
     *         object if it is non-null. Returns null if args is null or
     *         zero-sized, an unknown option is found or an expected option
     *         value is not given or not of an expected type. If null is
     *         returned, the param object contents is not defined.
     *         
     * @throws JarSignerException
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     */
    static JSParameters parseArgs(String[] args, JSParameters param)
            throws JarSignerException, IOException, KeyStoreException,
            UnrecoverableKeyException, NoSuchAlgorithmException {
        if (args == null){
            return null;
        }
        if (args.length == 0){
            return null;
        }
        if (param == null){
            param = new JSParameters();
        } else {
            // clean the param
            param.setDefault();
        }
        
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equalsIgnoreCase(sVerify)) {
                    param.setVerify(true);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sKeyStore)) {
                    param.setStoreURI(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sStoreType)) {
                    param.setStoreType(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sStorePass)) {
                    param.setStorePass(args[++i].toCharArray());
                    continue;
                }
                if (args[i].equalsIgnoreCase(sKeyPass)) {
                    param.setKeyPass(args[++i].toCharArray());
                    continue;
                }
                if (args[i].equalsIgnoreCase(sSigFile)) {
                    param.setSigFileName(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sSignedJAR)) {
                    param.setSignedJARName(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sCerts)) {
                    param.setCerts(true);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sVerbose)) {
                    param.setVerbose(true);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sSilent)) {
                    param.setSilent(true);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sInternalSF)) {
                    param.setInternalSF(true);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sSectionsOnly)) {
                    param.setSectionsOnly(true);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sProvider)) {
                    param.setProvider(args[++i]);
                    addProvider(args[i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sProviderName)) {
                    param.setProviderName(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sCertProvider)) {
                    param.setCertProvider(args[++i]);
                    addProvider(args[i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sCertProviderName)) {
                    param.setCertProviderName(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sSigProvider)) {
                    param.setSigProvider(args[++i]);
                    addProvider(args[i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sSigProviderName)) {
                    param.setSigProviderName(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sKSProvider)) {
                    param.setKsProvider(args[++i]);
                    addProvider(args[i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sKSProviderName)) {
                    param.setKsProviderName(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sMDProvider)) {
                    param.setMdProvider(args[++i]);
                    addProvider(args[i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sMDProviderName)) {
                    param.setMdProviderName(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sTSA)) {
                    try {
                        param.setTsaURI(new URI(args[++i]));
                    } catch (URISyntaxException e) {
                        throw new JarSignerException("Argument " + args[i]
                                + " is not an URI");
                    }
                    continue;
                }
                if (args[i].equalsIgnoreCase(sTSAcert)) {
                    param.setTsaCertAlias(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sProxy)) {
                    int colonPos = args[++i].lastIndexOf(':');
                    if (colonPos == -1) {
                        param.setProxy(args[i]);
                        continue;
                    } 

                    String proxy = args[i].substring(0, colonPos);
                    int port;
                    try {
                        port = Integer.parseInt(args[i].substring(colonPos + 1,
                                args[i].length()));
                    } catch (NumberFormatException e) {
                        throw new JarSignerException(
                                "Proxy port must be an integer value.");
                    }
                    param.setProxy(proxy);
                    param.setProxyPort(port);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sAltSigner)) {
                    param.setAltSigner(args[++i]);
                    continue;
                }
                if (args[i].equalsIgnoreCase(sAltSignerPath)) {
                    param.setAltSignerPath(args[++i]);
                    continue;
                }
                
                if ((param.isVerify() && i == args.length - 1)
                        || (!param.isVerify() && i == args.length - 2)) {
                    param.setJarURIorPath(args[i]);
                    continue;
                }
                if (!param.isVerify() && i == args.length - 1){
                    param.setAlias(args[i]);
                    continue;
                }
                
                System.out.println("Illegal option: " + args[i]);
                return null;
            }
        } catch(ArrayIndexOutOfBoundsException e){
            // ignore the last option if its value is not provided
        }
        
        // set specific provider names the same as the main provider name
        // if their values are not set.
        String providerName = param.getProviderName();
        if (providerName != null){
            if (param.getCertProviderName() == null){
                param.setCertProviderName(providerName);
            }
            if (param.getSigProviderName() == null){
                param.setSigProviderName(providerName);
            }
            if (param.getKsProviderName() == null){
                param.setKsProviderName(providerName);
            }
            if (param.getMdProviderName() == null){
                param.setMdProviderName(providerName);
            }
        }
        
        // if the store password is not given, prompt for it
        if (param.getStorePass() == null) {
            param.setStorePass(UserInteractor
                    .getDataFromUser("Enter keystore password:  "));
            // check the password
            param.getKeyStore();
        }
        
        if (param.getAlias() == null && !param.isVerify()) {
            param.setAlias(new String(UserInteractor
                    .getDataFromUser("Enter alias name:  ")));
        }
        if (!param.getKeyStore().containsAlias(param.getAlias())) {
            throw new JarSignerException("The alias " + param.getAlias()
                    + " does not exist in keystore");
        }
            
        // if key password is not given, try to inplace it with store password
        if (param.getKeyPass() == null) {
            param.setKeyPass(tryStorePassAsKeyPass(param.getKeyStore(), param
                    .getAlias(), param.getStorePass()));
        }

        // TODO: if decide to implement such abilities, remove this code
        if (param.isInternalSF() || param.isSectionsOnly()
                || param.getAltSigner() != null
                || param.getAltSignerPath() != null) {
            Logger.getLogger(JSParameters.loggerName).warning(
                    "Options " + sAltSigner + ", " + sAltSignerPath + ", "
                            + sInternalSF + ", " + sSectionsOnly
                            + " are currently ignored since they eliminate "
                            + "useful optimizations. ");
        }
        
        
        return param;
    }
    
    
    // Method tries to get the key, associated with alias, using the storePass.
    // If it can be recovered using the password, storePass is returned,
    // otherwise - the password is prompted for. Another attempt to recover the
    // key with entered password. If it is ok, it is returned, otherwise
    // UnrecoverableKeyException is thrown.
    private static char[] tryStorePassAsKeyPass(KeyStore keyStore,
            String alias, char[] storePass) throws KeyStoreException,
            IOException, UnrecoverableKeyException, NoSuchAlgorithmException {
        try {
            // try to get a key with keystore password
            // if succeed set key password same as that for keystore
            keyStore.getKey(alias, storePass);

            // will not come here if exception is thrown
            return storePass;
        } catch (UnrecoverableKeyException e) {
            // if key password is not equal to store password, ask for it.
            char[] keyPass = UserInteractor
                    .getDataFromUser("Enter key password for <" + alias + ">: ");
            // if the new password is incorrect an exception will be thrown
            try {
                keyStore.getKey(alias, keyPass);
            } catch (NoSuchAlgorithmException nsae) {
                throw new NoSuchAlgorithmException(
                        "Cannot find the algorithm to recover the key. ", e);
            }
            return keyPass;
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(
                    "Cannot find the algorithm to recover the key. ", e);
        }
    }
    
    // method for adding providers to java.security.Security
    static int addProvider(String provider) throws JarSignerException {
        try {
            return Security.addProvider(Class.forName(provider).asSubclass(
                    Provider.class).newInstance());
        } catch (Exception e) {
            throw new JarSignerException("Failed to load the provider "
                    + provider, e);
        }
    }

}

