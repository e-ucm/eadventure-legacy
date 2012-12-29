package es.eucm.eadventure.tracking.prv.studentcode;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;


public class ReadCodes {

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
	
	public static List<Integer> getEncodedCodes(){
		String encodedCodes=readEncodedCodes();
		List<Integer> codes = new ArrayList<Integer>();
		if (encodedCodes!=null){
			String[] allCodes = encodedCodes.split(";");
			for (String code:allCodes){
				codes.add(Integer.parseInt(code));
			}
		}
		
		return codes;
	}
	
	public static String readEncodedCodes(){
		File input = new File ("codes");
		ObjectCrypter crypter= new ObjectCrypter();
		try {
			FileInputStream fis = new FileInputStream(input);
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
	
	public static void writeEncodedCodes(){
		String allCodes = getCodesString();
		ObjectCrypter crypter= new ObjectCrypter();
		try {
			byte[] encrypted = crypter.encrypt(allCodes);
			File output = new File ("encrypted-codes.csv");
			FileOutputStream fos = new FileOutputStream(output);
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
	
	public static String getCodesString(){
		List<Integer> codes =getCodes();
		String trimmedCodes="";
		for (int code:codes){
			trimmedCodes+=code+";";
		}
		return trimmedCodes;
	}
	
	public static List<Integer> getCodes(){
		List<Integer> codes = null;
		try{
			  // Open the file that is the first 
			  // command line parameter
				  File file = new File("codes3.csv");
			  FileInputStream fstream = new FileInputStream(file);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  codes = new ArrayList<Integer>();
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  //System.out.println (strLine);
			  	String[] lineSplit = strLine.split(";");
			  	if (lineSplit.length>=2){
			  		try {
			  			int code = Integer.parseInt(lineSplit[0]);
			  			codes.add(code);
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
