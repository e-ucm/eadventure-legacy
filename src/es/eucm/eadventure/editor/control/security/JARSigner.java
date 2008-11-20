	package es.eucm.eadventure.editor.control.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import es.eucm.eadventure.common.auxiliar.File;

import sun.security.tools.JarSigner;
import sun.security.tools.KeyTool;

public class JARSigner {

	private static final String KEY_STORE_NAME = "eAdventure.keystore";
	
	private static final String KEY_STORE_PASSWORD = "3Adv3ntur3_K3YSTOR3";
	
	private static KeyStore keyStore;

	private static SecureRandom pwdGenerator;
	
	private static void initRandom(){
		SecureRandom sr = new SecureRandom();
		byte[] seed =sr.generateSeed( 12 );
		pwdGenerator = new SecureRandom();
		pwdGenerator.setSeed( seed );
	}
	
	private static boolean init(){
		boolean created = false;
			try {
				keyStore = KeyStore.getInstance( KeyStore.getDefaultType( ) );
				keyStore.load( new FileInputStream(KEY_STORE_NAME), KEY_STORE_PASSWORD.toCharArray( ) );
				created = true;
				initRandom();
	
			} catch( Exception e ) {
				try {
					keyStore.load( null, KEY_STORE_PASSWORD.toCharArray( ) );
					keyStore.store( new FileOutputStream(KEY_STORE_NAME), KEY_STORE_PASSWORD.toCharArray( ) );
					created = true;
					initRandom();
				} catch( Exception e1 ) {
					e1.printStackTrace();
				}
			}
		return created;	
	}

	private static String validateDistinguisedNameEntry(String entry){
		return entry.replace( ",", "\\," );
	}
	
	private static String generatePassword(){
		String password = "";
		for (int i=0; i<8; i++){
			//Generate
			switch(pwdGenerator.nextInt( 4 )){
				case 0:{
					//INT
					password+=Integer.toString( pwdGenerator.nextInt( 10 ) );
					break;
				}
				case 1:{
					//LOWER CHAR
					password+=(char)( pwdGenerator.nextInt( 'z'-'a'+1 )+'a' );
					break;
				}
				case 2:{
					//UPPER CHAR
					password+=(char)( pwdGenerator.nextInt( 'Z'-'A'+1 )+'A' );
					break;
				}
				case 3:{
					//SPECIAL CHARS: _ &
					switch(pwdGenerator.nextInt( 2 )){
						case 0: password+="_";break;
						case 1: password+="&";break;
					}
					break;
				}
			}
		}
		
		return password;
	}
	
	public static String generateAlias(String completeName){
		
		String alias="";
		int i=0;
		try{
			do{
				alias="";
			
				for (char currentChar:completeName.toCharArray( )){
					if ((currentChar>='a' && currentChar<='z') || (currentChar>='A' && currentChar<='Z') || (currentChar>='0' && currentChar<='9')){
						alias+=currentChar;
					}else
						alias+="_";
				}
				alias+="_"+i;
				i++;
			}while(keyStore.containsAlias( alias ));
		} catch (Exception e){alias = KEY_STORE_PASSWORD;}
		return alias;
	}
	
	public static boolean signJar (String completeName, String organization,String originJarPath, String signedJarPath){
		boolean created = false;
		boolean initiated =init();
		String alias = null;
		if (!initiated || completeName.length( )<6){
			//REPORT ERROR
		} else {
			try {
				//Calculate dName
				String dName = "CN="+validateDistinguisedNameEntry(completeName)+", OU="+validateDistinguisedNameEntry(organization)+ ", O="+validateDistinguisedNameEntry(organization);
				
				//Calculate password
				String password = generatePassword();
				
				//Calculate alias
				alias = generateAlias(completeName); 
				
				//Generate the key pair using KeyTool
				KeyTool.main( new String[]{"-genkeypair", "-alias", alias,"-dname",dName,"-keypass",password,"-validity","1000","-keystore", KEY_STORE_NAME,"-storepass", KEY_STORE_PASSWORD} );
				
				//Sign the jar
				JarSigner.main( new String[]{"-keystore", KEY_STORE_NAME, "-storepass", KEY_STORE_PASSWORD, "-keypass", password, "-signedjar",new File(signedJarPath).getAbsolutePath( ), new File(originJarPath).getAbsolutePath(), alias} );
				
				//Verify it has been successfully signed
				JarFile signedJar= new JarFile(signedJarPath, true);
				Manifest manifest = signedJar.getManifest( );
				Set<String> entries=manifest.getEntries( ).keySet( );
				for( String entry: entries){
					signedJar.getJarEntry( entry ).getAttributes( );
				}
				
				created = true;
			} catch( Exception e ) {
				e.printStackTrace();
				created = false;
			}
			
		}
		return created;

	}
}
