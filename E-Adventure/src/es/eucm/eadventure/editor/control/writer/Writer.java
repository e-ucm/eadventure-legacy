package es.eucm.eadventure.editor.control.writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileOutputStream; 

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.filefilters.XMLFileFilter;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.security.JARSigner;
import es.eucm.eadventure.editor.control.writer.domwriters.AdaptationDOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.AssessmentDOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.ChapterDOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.DescriptorDOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.lom.LOMDOMWriter;

/**
 * Static class, containing the main functions to write an adventure into an XML file.
 * 
 * @author Bruno Torijano Bueno
 */
/*
 * @updated by Javier Torrente. New functionalities added - Support for .ead files. Therefore <e-Adventure> files are no
 * longer .zip but .ead
 */

public class Writer {

	/**
	 * Text Constants for LOM Exportation 
	 */
	private static final String RESOURCE_IDENTIFIER = "res_eAdventure";
	
	private static final String ITEM_IDENTIFIER = "itm_eAdventure";
	
	private static final String ITEM_TITLE="The eAdventure game";
	
	private static final String ORGANIZATION_IDENTIFIER = "eAdventure";
	
	private static final String ORGANIZATION_TITLE = "eAdventure course";
	
	/**
	 * Private constructor.
	 */
	private Writer( ) {}

	/**
	 * Writes the daventure data into the given file.
	 * 
	 * @param zipFilename
	 *            Zip file to write the data
	 * @param adventureData
	 *            Adventure data to write in the file
	 * @param valid
	 *            True if the adventure is valid (can be executed in the engine), false otherwise
	 * @return True if the operation was succesfully completed, false otherwise
	 */
	public static boolean writeDescriptor( String zipFilename, AdventureDataControl adventureData, boolean valid ) {
		boolean dataSaved = false;

		try {
			// Create the necessary elements for building the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			TransformerFactory tFactory = TransformerFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = null;
			Transformer transformer = null;
			OutputStream fout = null;
			OutputStreamWriter writeFile = null;

			// Delete the previous XML files in the root of the ZIP
			File zipFile = new File( zipFilename );

			if( zipFile.exists( ) ) {
				//File[] xmlFiles = zipFile.listFiles( new XMLFileFilter( ), zipFile.getArchiveDetector( ) );
				File[] xmlFiles = zipFile.listFiles( new XMLFileFilter( ) );
				for( File xmlFile : xmlFiles )
					if( xmlFile.isFile( ) && xmlFile.getName( ).equals( "descriptor.xml" ))
						xmlFile.delete( );
			}

			/** ******* START WRITING THE DESCRIPTOR ********* */
			// Pick the main node for the descriptor
			Node mainNode = DescriptorDOMWriter.buildDOM( adventureData, valid );
			indentDOM( mainNode, 0 );
			doc = db.newDocument( );
			doc.adoptNode( mainNode );
			doc.appendChild( mainNode );

			// Create the necessary elements for export the DOM into a XML file
			transformer = tFactory.newTransformer( );
			transformer.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, "descriptor.dtd" );

			// Create the output buffer, write the DOM and close it
			fout = new FileOutputStream( zipFilename + "/descriptor.xml" );
			writeFile = new OutputStreamWriter( fout, "UTF-8" );
			transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
			writeFile.close( );
			fout.close( );
			/** ******** END WRITING THE DESCRIPTOR ********** */

			dataSaved = true;

		} catch( IOException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
		} catch( ParserConfigurationException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
		} catch( TransformerConfigurationException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
		} catch( TransformerException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
		}

		return dataSaved;
	}
	
	/**
	 * Writes the daventure data into the given file.
	 * 
	 * @param zipFilename
	 *            Zip file to write the data
	 * @param adventureData
	 *            Adventure data to write in the file
	 * @param valid
	 *            True if the adventure is valid (can be executed in the engine), false otherwise
	 * @return True if the operation was succesfully completed, false otherwise
	 */
	public static boolean writeData( String zipFilename, AdventureDataControl adventureData, boolean valid ) {
		boolean dataSaved = false;

		try {
			// Create the necessary elements for building the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			TransformerFactory tFactory = TransformerFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = null;
			Transformer transformer = null;
			OutputStream fout = null;
			OutputStreamWriter writeFile = null;

			// Delete the previous XML files in the root of the ZIP
			File zipFile = new File( zipFilename );

			if( zipFile.exists( ) ) {
				//File[] xmlFiles = zipFile.listFiles( new XMLFileFilter( ), zipFile.getArchiveDetector( ) );
				File[] xmlFiles = zipFile.listFiles( new XMLFileFilter( ) );
				for( File xmlFile : xmlFiles )
					if( xmlFile.isFile( ) )
						xmlFile.delete( );
			}

			// Add the special asset files
			AssetsController.addSpecialAssets( );

			/** ******* START WRITING THE ADAPTATION FILES ***** */
			for (AdaptationProfileDataControl profile : adventureData.getAdaptationRulesListDataControl( ).getProfiles( )){
				Writer.writeAdaptationData( zipFilename, profile, true );
			}
			/** *******  END WRITING THE ADAPTATION FILES  ***** */
			
			/** ******* START WRITING THE ASSESSMENT FILES ***** */
			for (AssessmentProfileDataControl profile : adventureData.getAssessmentRulesListDataControl( ).getProfiles( )){
				Writer.writeAssessmentData( zipFilename, profile, true );
			}
			/** *******  END WRITING THE ASSESSMENT FILES  ***** */
			
			/** ******* START WRITING THE DESCRIPTOR ********* */
			// Pick the main node for the descriptor
			Node mainNode = DescriptorDOMWriter.buildDOM( adventureData, valid );
			indentDOM( mainNode, 0 );
			doc = db.newDocument( );
			doc.adoptNode( mainNode );
			doc.appendChild( mainNode );

			// Create the necessary elements for export the DOM into a XML file
			transformer = tFactory.newTransformer( );
			transformer.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, "descriptor.dtd" );

			// Create the output buffer, write the DOM and close it
			fout = new FileOutputStream( zipFilename + "/descriptor.xml" );
			writeFile = new OutputStreamWriter( fout, "UTF-8" );
			transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
			writeFile.close( );
			fout.close( );
			/** ******** END WRITING THE DESCRIPTOR ********** */

			/** ******* START WRITING THE CHAPTERS ********* */
			// Write every chapter
			int chapterIndex = 1;
			for( Chapter chapter : adventureData.getChapters( ) ) {

				// Pick the main node of the chapter
				mainNode = ChapterDOMWriter.buildDOM( chapter, zipFilename );
				indentDOM( mainNode, 0 );
				doc = db.newDocument( );
				doc.adoptNode( mainNode );
				doc.appendChild( mainNode );

				// Create the necessary elements for export the DOM into a XML file
				transformer = tFactory.newTransformer( );
				transformer.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, "eadventure.dtd" );

				// Create the output buffer, write the DOM and close it
				fout = new FileOutputStream( zipFilename + "/chapter" + chapterIndex++ + ".xml" );
				writeFile = new OutputStreamWriter( fout, "UTF-8" );
				transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
				writeFile.close( );
				fout.close( );
			}
			/** ******** END WRITING THE CHAPTERS ********** */

			// Update the zip files
			//File.umount( );
			dataSaved = true;

		} catch( IOException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
		} catch( ParserConfigurationException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
		} catch( TransformerConfigurationException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
		} catch( TransformerException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
		}

		return dataSaved;
	}
	
	public static boolean writeAssessmentData( String zipFilename, AssessmentProfileDataControl controller, boolean valid ) {
		/********************* STORE ASSESSMENT FILE WHEN PRESENT *******************/
		boolean dataSaved=false;
		if (controller.getPath( )!= null && !controller.getPath( ).equals( "" ) && new File(zipFilename, controller.getPath( )).exists()){
		
		// Create the necessary elements for building the DOM
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
		TransformerFactory tFactory = TransformerFactory.newInstance( );
		DocumentBuilder db=null;
		Document doc = null;
		Transformer transformer = null;
		OutputStream fout = null;
		OutputStreamWriter writeFile = null;

		try {
			db = dbf.newDocumentBuilder( );
		
			// Pick the main node of the chapter
			Node assNode = AssessmentDOMWriter.buildDOM( (AssessmentProfile)controller.getContent( ) );
			indentDOM( assNode, 0 );
			
			doc = db.newDocument( );
			doc.adoptNode( assNode );
			doc.appendChild( assNode );

			// Create the necessary elements for export the DOM into a XML file
			transformer = tFactory.newTransformer( );
			transformer.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, "assessment.dtd" );

			// 	Create the output buffer, write the DOM and close it
			fout = new FileOutputStream( zipFilename + "/"+ controller.getPath( ) );
			writeFile = new OutputStreamWriter( fout, "UTF-8" );
			transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
			writeFile.close( );
			fout.close( );
			dataSaved=true;
		} catch( ParserConfigurationException e ) {
			e.printStackTrace();
		} catch( TransformerConfigurationException e ) {
			e.printStackTrace();
		} catch( UnsupportedEncodingException e ) {
			e.printStackTrace();
		} catch( TransformerException e ) {
			e.printStackTrace();
		} catch( FileNotFoundException e ) {
			e.printStackTrace();
		} catch( IOException e ) {
			e.printStackTrace();
		}
		}
		return dataSaved;
	}
	
	public static boolean writeAdaptationData( String zipFilename, AdaptationProfileDataControl controller, boolean valid ) {
		/********************* STORE ASSESSMENT FILE WHEN PRESENT *******************/
		boolean dataSaved=false;
		if (controller.getPath( )!= null && !controller.getPath( ).equals( "" )&& new File(zipFilename, controller.getPath( )).exists()){
		
		List<AdaptationRule> rules = ((AdaptationProfile)controller.getContent( )).getRules();
		AdaptedState initialState = controller.getInitialState( );
		
		// Create the necessary elements for building the DOM
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
		TransformerFactory tFactory = TransformerFactory.newInstance( );
		DocumentBuilder db=null;
		Document doc = null;
		Transformer transformer = null;
		OutputStream fout = null;
		OutputStreamWriter writeFile = null;

		try {
			db = dbf.newDocumentBuilder( );
		
			// Pick the main node of the chapter
			Node adpNode = AdaptationDOMWriter.buildDOM( rules, initialState );
			indentDOM( adpNode, 0 );
			
			doc = db.newDocument( );
			doc.adoptNode( adpNode );
			doc.appendChild( adpNode );

			// Create the necessary elements for export the DOM into a XML file
			transformer = tFactory.newTransformer( );
			transformer.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, "adaptation.dtd" );

			// 	Create the output buffer, write the DOM and close it
			fout = new FileOutputStream( zipFilename + "/"+controller.getPath( ) );
			writeFile = new OutputStreamWriter( fout, "UTF-8" );
			transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
			writeFile.close( );
			fout.close( );
			dataSaved=true;
		} catch( ParserConfigurationException e ) {
			e.printStackTrace();
		} catch( TransformerConfigurationException e ) {
			e.printStackTrace();
		} catch( UnsupportedEncodingException e ) {
			e.printStackTrace();
		} catch( TransformerException e ) {
			e.printStackTrace();
		} catch( FileNotFoundException e ) {
			e.printStackTrace();
		} catch( IOException e ) {
			e.printStackTrace();
		}
		}
		return dataSaved;
	}

	

	/**
	 * Indent the given DOM node recursively with the given depth.
	 * 
	 * @param nodeDOM
	 *            DOM node to be indented
	 * @param depth
	 *            Depth of the current node
	 */
	private static void indentDOM( Node nodeDOM, int depth ) {
		// First of all, extract the document of the node, and the list of children
		Document document = nodeDOM.getOwnerDocument( );
		NodeList children = nodeDOM.getChildNodes( );

		// Flag for knowing if the current node is empty of element nodes
		boolean isEmptyOfElements = true;

		int i = 0;
		// For each children node
		while( i < children.getLength( ) ) {
			Node currentChild = children.item( i );

			// If the current child is an element node
			if( currentChild.getNodeType( ) == Node.ELEMENT_NODE ) {
				// Insert a indention before it, and call the recursive function with the child (and a higher depth)
				nodeDOM.insertBefore( document.createTextNode( "\n" + getTab( depth + 1 ) ), currentChild );
				indentDOM( currentChild, depth + 1 );

				// Set empty of elements to false, and increase i (the new child moves all children)
				isEmptyOfElements = false;
				i++;
			}

			// Go to next child
			i++;
		}

		// If this node has some element, add the indention for the closing tag
		if( !isEmptyOfElements )
			nodeDOM.appendChild( document.createTextNode( "\n" + getTab( depth ) ) );
	}
	
	
	private static boolean writeWebPage( String loName ){
		boolean dataSaved=true;
		try {
			String webPage = 
			"<html>\n"+
			"\t<head>\n"+
			//"\t\t<script type='text/javascript' src='commapi.js'></script>\n"+
			//"\t\t<script type='text/javascript' src='ajax-wrapper.js'></script>\n"+
			//"\t\t<script type='text/javascript' src='egame.js'></script>\n"+
			"\t</head>\n"+
			"\t<body>\n"+
			"\t\t<applet code=\"es.eucm.eadventure.engine.EAdventureApplet\" archive=\"./"+loName+".jar\" name=\"eadventure\" id=\"eadventure\" width=\"260\" height=\"100\" MAYSCRIPT>\n"+
			"\t\t<param name=\"USER_ID\" value=\"567\"/>\n"+
			"\t\t<param name=\"RUN_ID\" value=\"5540\"/>\n"+
			"\t\t</applet>\n"+
			"<p><b>The game is initating.. please be patient while the digital sign is verified</b></p>\n"+
			"\t</body>\n"+
			"</html>\n";
			
			
			File pageFile = new File("web/temp/"+loName+".html");
			pageFile.createNewFile( );
			OutputStream is = new FileOutputStream(pageFile);
			is.write( webPage.getBytes( ) );
			is.close( );
			
			dataSaved=true;
			
		} catch( FileNotFoundException e ) {
			e.printStackTrace();dataSaved=false;
		} catch( IOException e ) {
			e.printStackTrace();dataSaved=false;
		} 
		return dataSaved;
	}
	/**
	 * Returns the text of a simple manifest file with the main class as specified by argument
	 * @param destinyFile
	 * @param mainClass
	 */
	public static String defaultManifestFile (String mainClass){

		String manifest = 	"Manifest-Version: 1.0\n"+
							"Ant-Version: Apache Ant 1.7.0\n"+
							"Created-By: 1.6.0_02-b06 (Sun Microsystems Inc.)\n"+
							"Main-Class: "+mainClass+"\n";
		
		return manifest;
	}
	
	/**
	 * Exports the game as a .ead file
	 * @param projectDirectory
	 * @param destinyEADPath
	 * @return
	 */
	public static boolean export( String projectDirectory, String destinyEADPath ) {
		boolean exported = false;
		String destinyFilePathNoExt = destinyEADPath.substring( 0, destinyEADPath.lastIndexOf( "." ) );
		String destinyZIPPath = destinyFilePathNoExt+".zip"; 
		File.zipDirectory( projectDirectory, destinyZIPPath );
		File destinyZIPFile = new File(destinyZIPPath);
		File destinyEADFile = new File(destinyEADPath);
		exported = destinyZIPFile.renameTo( destinyEADFile );
		return exported;
		//return true;
	}
	
	/**
	 * Exports the game as a jar file
	 * @param projectDirectory
	 * @param destinyJARPath
	 * @return
	 */
	public static boolean exportStandalone( String projectDirectory, String destinyJARPath ) {
		boolean exported = true;

		try {
		
			// Clear web/temp dir
			/*File tempDir = new File("web/temp");
			if (tempDir.exists( )){
				tempDir.deleteAll( );
			} else {
				tempDir.create( );
			}*/
			
			// Destiny file
			File destinyJarFile = new File(destinyJARPath);
			
			// Create output stream		
			FileOutputStream mergedFile = new FileOutputStream(destinyJarFile);
			
			// Create zipoutput stream
			ZipOutputStream os = new ZipOutputStream(mergedFile);
			
			// Merge projectDirectory and web/eAdventure_temp.jar into output stream
			File.mergeZipAndDirToJar( "web/eAdventure_temp.jar", projectDirectory, os );
			
			// Create and copy the manifest into the output stream
			String manifest = Writer.defaultManifestFile( "es.eucm.eadventure.engine.EAdventureStandalone" );
			ZipEntry manifestEntry = new ZipEntry("META-INF/MANIFEST.MF");
			os.putNextEntry( manifestEntry );
			os.write( manifest.getBytes( ) );
			os.closeEntry( );
			os.flush( );
			os.close( );
		} catch( FileNotFoundException e ) {
			exported = false;
			e.printStackTrace();
		} catch( IOException e ) {
			exported = false;
			e.printStackTrace();
		}
		
		return exported;
		
		// Integrate game and jar into a new jar File
		
		// Copy jar files to temp dir
		
		/*File jarFolder = new File("web/JAR_S");
		File jarTempFolder = new File("web/temp/JAR_S");
		if (jarTempFolder.exists( )){
			jarTempFolder.deleteAll( );
		}else{
			jarTempFolder.create( );
		}
		exported &= jarFolder.copyAllTo( jarTempFolder );
		
		// NO: Compress game project to web/temp/JAR_S
		//File.zipDirectory( projectDirectory, new File(jarTempFolder,"integration.zip").getAbsolutePath( ) );
		File projectDir = new File(projectDirectory);
		projectDir.copyAllTo( jarTempFolder );
		
		// Compress jar temp folder
		File jar = new File("web/temp/eAdventure.zip");
		File.zipDirectory( jarTempFolder.getAbsolutePath( ), jar.getAbsolutePath( ) );
		exported &= jar.copyTo( destinyJarFile );
		jar.delete();
		
		// Delete temp jar dir
		jarTempFolder.deleteAll( );
		jarTempFolder.delete( );

		
		return exported;*/
		//return true;
	}

	
	public static boolean exportAsLearningObject( String zipFilename, String loName, String authorName, String organization, String gameFilename, AdventureDataControl adventureData ) {
		boolean dataSaved = true;

		try {
			// Create the necessary elements for building the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			TransformerFactory tFactory = TransformerFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = null;
			Transformer transformer = null;
			OutputStream fout = null;
			OutputStreamWriter writeFile = null;

			// Delete the previous content of the zip
			/*File zipFile = new File( zipFilename );

			if( zipFile.exists( ) ) {
				//File[] allFiles = zipFile.listFiles( zipFile.getArchiveDetector( ) );
				File[] allFiles = zipFile.listFiles(  );
				if (allFiles!=null)
					for( File file : allFiles )
						if( file.isFile( ) )
							file.delete( );
			}*/
			
			//Clean temp directory
			File tempDir = new File("web/temp");
			//for(File tempFile:tempDir.listFiles(tempDir.getArchiveDetector( ) )){
			for(File tempFile:tempDir.listFiles( )){
				if (tempFile.isDirectory( ))
					tempFile.deleteAll( );
				tempFile.delete( );
			}

			// Copy the web to the zip
			dataSaved &=writeWebPage( loName );
			
			// Merge project & e-Adventure jar into file eAdventure_temp.jar
			// Destiny file
			File jarUnsigned = new File("web/temp/eAdventure.zip");
			
			// Create output stream		
			FileOutputStream mergedFile = new FileOutputStream(jarUnsigned);
			
			// Create zipoutput stream
			ZipOutputStream os = new ZipOutputStream(mergedFile);
			
			// Merge projectDirectory and web/eAdventure_temp.jar into output stream
			File.mergeZipAndDirToJar( "web/eAdventure_temp.jar", gameFilename, os );
			
			// Create and copy the manifest into the output stream
			String manifestText = Writer.defaultManifestFile( "es.eucm.eadventure.engine.EAdventureApplet" );
			ZipEntry manifestEntry = new ZipEntry("META-INF/MANIFEST.MF");
			os.putNextEntry( manifestEntry );
			os.write( manifestText.getBytes( ) );
			os.closeEntry( );
			os.flush( );
			os.close( );
			
			dataSaved &= jarUnsigned.renameTo( new File("web/temp/"+loName+"_unsigned.jar") );
			
			// Integrate game and jar into a new jar File
			
			// Copy jar files to temp dir
			/*File jarFolder = new File("web/JAR");
			File jarTempFolder = new File("web/temp/JAR");
			if (jarTempFolder.exists( )){
				jarTempFolder.deleteAll( );
			}else{
				jarTempFolder.create( );
			}
			dataSaved &= jarFolder.copyAllTo( jarTempFolder );*/
			
			// NO: Compress game project to web/temp/JAR
			//File.zipDirectory( gameFilename, new File(jarTempFolder,"integration.zip").getAbsolutePath( ) );
			//File projectDir = new File(gameFilename);
			//projectDir.copyAllTo( jarTempFolder );
			
			// Compress jar temp folder
			
			//File.zipDirectory( jarTempFolder.getAbsolutePath( ), jarUnsigned.getAbsolutePath( ) );
			//dataSaved &= jarUnsigned.renameTo( new File("web/temp/"+loName+"_unsigned.jar") );
			
			// Delete temp jar dir
			//jarTempFolder.deleteAll( );
			//jarTempFolder.delete( );
			
			//dataSaved &= File.putFilesInJar( "web/eAdventure.jar", new String[]{gameFilename}, "web/temp/"+loName+"_unsigned.jar", new String[]{"integration.zip"} );
			
			// Copy the jar file to the zip
			//File sourceFile = new File("web/eAdventure.jar");
			//File destinyFile = new File("web/temp/"+loName+"_unsigned.jar");
			//dataSaved &= sourceFile.copyAllTo( destinyFile );
			//dataSaved &= sourceFile.copyTo( destinyFile );
			
			// Copy the game to the jar (in the zip)
			//File sourceFile2 = new File (gameFilename);
			
			//File destinyFile2 = new File(destinyFile,"integration.zip");
			//File destinyFile2 = new File("web/temp/integration.zip");
			//if(sourceFile2.copyAllTo( destinyFile2 ))
			//	dataSaved&=true;
			//else if (sourceFile2.copyTo( destinyFile2 ))
			//	dataSaved&=true;
			//else
				//dataSaved = false;

			//File.umount( destinyFile, true );
			//File.umount( sourceFile2, true );
			//Once the file has been saved, sign the jar
			
			//if (dataSaved)
				dataSaved = JARSigner.signJar( authorName, organization, "web/temp/"+loName+"_unsigned.jar", "web/temp/"+loName+".jar" );

			//if (dataSaved){
				//Delete unsigned jar
				new File("web/temp/"+loName+"_unsigned.jar").delete( );
				//destinyFile.delete();
				
				/*File destinyFileInZip = new File(zipFile, loName+".jar");
				sourceFile = new File("web/temp/"+loName+".jar");
				dataSaved &= sourceFile.copyAllTo( destinyFileInZip );*/
				
				/*File loFile = new File(zipFile);
				ZipFile signedJar = new ZipFile("web/temp/"+loName+".jar");
				ZipOutputStream fos = new ZipOutputStream(new FileOutputStream(loFile));
				ZipInputStream fis = new ZipInputStream(new FileInputStream("web/temp/"+loName+".jar"));
				int b;
				while ((b=fis.read( ))!=-1){
					fos.write( b );
				}
				fos.close( );
				fis.close( );
				signedJar.close( );*/
			//}
			
			
			/** ******* START WRITING THE MANIFEST ********* */
			// Create the necessary elements for building the DOM
			db = dbf.newDocumentBuilder( );
			doc = db.newDocument( );

			// Pick the main node for the descriptor
			Element manifest = null;
			manifest = doc.createElement( "manifest" );
			manifest.setAttribute( "identifier", "imsaccmdv1p0_manifest" );
			manifest.setAttribute( "xmlns", "http://www.imsglobal.org/xsd/imscp_v1p1" );
			manifest.setAttribute( "xmlns:imsmd", "http://www.imsglobal.org/xsd/imsmd_v1p2" );
			manifest.setAttribute( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance" );
			manifest.setAttribute( "xsi:schemaLocation", "http://www.imsglobal.org/xsd/imscp_v1p1 imscp_v1p1.xsd http://www.imsglobal.org/xsd/imsmd_v1p2 imsmd_v1p2p4.xsd" );
			manifest.setAttribute( "version", "IMS CP 1.1.3" );
			
			//Create the organizations node (required)
			Element organizations = doc.createElement( "organizations" );
			organizations.setAttribute( "default", ORGANIZATION_IDENTIFIER );
			// Just one organization
			Element organizationEl = doc.createElement( "organization" );
			organizationEl.setAttribute( "identifier", ORGANIZATION_IDENTIFIER );
			Node organizationTitleNode = doc.createElement( "title" );
			organizationTitleNode.setTextContent( ORGANIZATION_TITLE );
			organizationEl.appendChild( organizationTitleNode );
			// The organization contains only one item, which refers to the unique resource
			Element itemEl = doc.createElement( "item" );
			itemEl.setAttribute( "identifier", ITEM_IDENTIFIER );
			itemEl.setAttribute( "identifierref", RESOURCE_IDENTIFIER );
			itemEl.setAttribute( "isvisible", "1" );
			itemEl.setAttribute( "parameters", "" );
			Node itemTitleNode = doc.createElement( "title" );
			itemTitleNode.setTextContent( ITEM_TITLE );
			itemEl.appendChild( itemTitleNode );
			organizationEl.appendChild( itemEl );
			//Append the organization node to organizations
			organizations.appendChild( organizationEl );
			
			manifest.appendChild( organizations );
			
			//Create the resources node
			Node resources = doc.createElement( "resources" );
			Element resource = doc.createElement( "resource" );
			resource.setAttribute( "identifier", RESOURCE_IDENTIFIER );
			resource.setAttribute( "type", "webcontent" );
			resource.setAttribute( "href", loName+".html" );
			
			Node metaData = doc.createElement( "metadata" );
			Node lomNode = LOMDOMWriter.buildLOMDOM( adventureData.getLomController( ) );
			doc.adoptNode(lomNode);
			metaData.appendChild( lomNode );
			resource.appendChild( metaData );
			
			Element file = doc.createElement( "file" );
			file.setAttribute( "href", loName+".html" );
			resource.appendChild( file );
			
			/*Element file2 = doc.createElement( "file" );
			file2.setAttribute( "href", "ajax-wrapper.js" );
			resource.appendChild( file2 );
			
			Element file3 = doc.createElement( "file" );
			file3.setAttribute( "href", "commapi.js" );
			resource.appendChild( file3 );

			Element file4 = doc.createElement( "file" );
			file4.setAttribute( "href", "egame.js" );
			resource.appendChild( file4 );*/

			Element file5 = doc.createElement( "file" );
			file5.setAttribute( "href", loName+".jar" );
			resource.appendChild( file5 );

			resources.appendChild( resource );
			manifest.appendChild( resources );
			indentDOM( manifest, 0 );
			doc.adoptNode( manifest );
			doc.appendChild( manifest );
			
			// Create the necessary elements for export the DOM into a XML file
			transformer = tFactory.newTransformer( );

			// Create the output buffer, write the DOM and close it
			//fout = new FileOutputStream( zipFilename + "/imsmanifest.xml" );
			fout = new FileOutputStream( "web/temp/imsmanifest.xml" );
			writeFile = new OutputStreamWriter( fout, "UTF-8" );
			transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
			writeFile.close( );
			fout.close( );
			
			//sourceFile = new File("web/temp/imsmanifest.xml");
			//destinyFile = new File (zipFilename, "imsmanifest.xml");
			//dataSaved &= sourceFile.copyTo( destinyFile );
			/** ******** END WRITING THE MANIFEST ********** */
			
			/** COPY EVERYTHING TO THE ZIP*/
			File.zipDirectory("web/temp/", zipFilename);
			//dataSaved&=new File("web/temp").archiveCopyAllTo( new File(zipFile) );
			

			// Update the zip files
			//File.umount( );

		} catch( IOException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
			dataSaved = false;
		} catch( ParserConfigurationException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
			dataSaved = false;
		} catch( TransformerConfigurationException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
			dataSaved = false;
		} catch( TransformerException exception ) {
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.WriteData" ) );
			exception.printStackTrace( );
			dataSaved = false;
		}

		return dataSaved;
	}


	/**
	 * Returns a set of tabulations, equivalent to the given number.
	 * 
	 * @param tabulations
	 *            Number of tabulations
	 */
	private static String getTab( int tabulations ) {
		String tab = "";
		for( int i = 0; i < tabulations; i++ )
			tab += "\t";
		return tab;
	}
}
