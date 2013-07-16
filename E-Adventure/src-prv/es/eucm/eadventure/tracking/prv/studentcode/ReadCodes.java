package es.eucm.eadventure.tracking.prv.studentcode;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;


public class ReadCodes {

    
    /* ###############################################################################
     * MODIFY THESE METHODS TO CHANGE INPUT AND OUTPUT FILES
     * @param args
     * ###############################################################################*/
    //Input
    private static File getDecodedFile(){
        //return new File("Experimento-Códigos.csv");
        return new File("codes.csv");
    }
    
    //Output
    private static OutputStream getEncodedOStream(){
        String trackingConfigFile ="encrypted-codes.csv";
        try {
            return new FileOutputStream(trackingConfigFile);
        }
        catch( FileNotFoundException e ) {
            e.printStackTrace();
            return null;
        }
    }
    
    //Output
    private static InputStream getEncodedIStream(){
        String trackingConfigFile ="encrypted-codes.csv";
        if ( trackingConfigFile == null )
            return null;
        
        InputStream source = null;
        source = ReadCodes.class.getResourceAsStream( trackingConfigFile );
        if (source==null)
            source = ReadCodes.class.getResourceAsStream( "/"+trackingConfigFile );
        if (source==null)
            source = ReadCodes.class.getResourceAsStream( "./"+trackingConfigFile );
        if (source==null)
            source = ReadCodes.class.getResourceAsStream( "/tracking/"+trackingConfigFile );
        if (source==null)
            source = ReadCodes.class.getResourceAsStream( "../.bin/"+trackingConfigFile );
        if (source==null)
            source = ReadCodes.class.getResourceAsStream( "../../.bin/"+trackingConfigFile );
        if (source==null){
            try {
                source = new FileInputStream( trackingConfigFile );
            }
            catch( FileNotFoundException e ) {
                try {
                    source = new FileInputStream( "../.bin/"+trackingConfigFile );
                }
                catch( FileNotFoundException e1 ) {
                    try {
                        source = new FileInputStream( "../../.bin/"+trackingConfigFile );
                    }
                    catch( FileNotFoundException e2 ) {
                        try {
                            source = new FileInputStream( ".bin/"+trackingConfigFile );
                        }
                        catch( FileNotFoundException e3 ) {
                            source=null;
                        }
                    }
                }
            }
        }        
        return source;        
        
    }
    
	public static void main(String args[])
	{
		System.out.println(getCodesString());
		writeEncodedCodes();
		readEncodedCodes();
		System.out.println(getEncodedCodes().size());
		/*int n=0;
		for (int code: getCodes()){
			n++;
			System.out.println(code);
		}
		System.out.println("NCODES="+n);*/
	}
	
	public static List<String> getEncodedCodes(){
		String encodedCodes=readEncodedCodes();
		List<String> codes = new ArrayList<String>();
		if (encodedCodes!=null){
			String[] allCodes = encodedCodes.split(";");
			for (String code:allCodes){
				codes.add(/*Integer.parseInt(code)*/code);
			}
		}
		
		return codes;
	}
	
	private static String readEncodedCodes(){
		//File input = getEncodedFile();//new File ("codes");
		ObjectCrypter crypter= new ObjectCrypter();
		try {
			InputStream fis = getEncodedIStream();//new FileInputStream(input);
			byte[] allBytes=new byte[0];
			byte[] bytesToRead=new byte[1024];
			int read=0;
			while ((read=fis.read(bytesToRead))>0){
				byte[] newAllBytes = new byte[allBytes.length+read];
				for (int i=0; i<allBytes.length; i++){
					newAllBytes[i]=allBytes[i];
				}
				for (int i=0; i<read; i++){
					newAllBytes[i+allBytes.length]=bytesToRead[i];
				}
				allBytes=newAllBytes;
			}
			String decrypted = (String)(crypter.decrypt(allBytes));
			return decrypted;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static void writeEncodedCodes(){
		String allCodes = getCodesString();
		ObjectCrypter crypter= new ObjectCrypter();
		try {
			byte[] encrypted = crypter.encrypt(allCodes);
			//File output = new File ("encrypted-codes-chermug.csv");
			//File output = getEncodedFile();
			//FileOutputStream fos = new FileOutputStream(output);
			OutputStream fos = getEncodedOStream();
			fos.write(encrypted);
			fos.close();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShortBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String getCodesString(){
		List<String> codes =getCodes();
		String trimmedCodes="";
		for (String code:codes){
			trimmedCodes+=code+";";
		}
		return trimmedCodes;
	}
	
	private static List<String> getCodes(){
		List<String> codes = null;
		try{
			  // Open the file that is the first 
			  // command line parameter
				//  File file = new File("Experimento-Códigos.csv");
		        File file = getDecodedFile();
			  FileInputStream fstream = new FileInputStream(file);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  codes = new ArrayList<String>();
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  //System.out.println (strLine);
			  	String[] lineSplit = strLine.split(";");
			  	if (lineSplit.length>=2){
			  		try {
			  			//int code = Integer.parseInt(lineSplit[0]);
			  			//codes.add(code);
			  			codes.add(lineSplit[0]);
			  		}catch (NumberFormatException e){	  			
			  		}
			  	} else{
			  	  try {
                      //int code = Integer.parseInt(strLine);
                      //codes.add(code);
			  	    codes.add(strLine);
                  }catch (NumberFormatException e){               
                  }
			  	}
			  }
			  //Close the input stream
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		return codes;
	}
}
