/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.auxiliar.filefilters.XMLFileFilter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.security.JARSigner;
import es.eucm.eadventure.editor.control.writer.domwriters.AdaptationDOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.AssessmentDOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.ChapterDOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.DescriptorDOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.ExpectedGameIODOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.ims.IMSDOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.lom.LOMDOMWriter;
import es.eucm.eadventure.editor.control.writer.domwriters.lomes.LOMESDOMWriter;

/**
 * Static class, containing the main functions to write an adventure into an XML
 * file.
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

    //private static final String ITEM_TITLE="The eAdventure game";

    private static final String ORGANIZATION_IDENTIFIER = "eAdventure";

    private static final String ORGANIZATION_TITLE = "eAdventure course";

    private static final String ORGANIZATION_STRUCTURE = "hierarchical";

    /**
     * Private constructor.
     */
    private Writer( ) {

    }

    /**
     * Writes the daventure data into the given file.
     * 
     * @param folderName
     *            Folder where to write the data
     * @param adventureData
     *            Adventure data to write in the file
     * @param valid
     *            True if the adventure is valid (can be executed in the
     *            engine), false otherwise
     * @return True if the operation was succesfully completed, false otherwise
     */
    public static boolean writeData( String folderName, AdventureDataControl adventureData, boolean valid ) {

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

            // Delete the previous XML files in the root of the project dir
            File projectFolder = new File( folderName );

            if( projectFolder.exists( ) ) {
                //File[] xmlFiles = zipFile.listFiles( new XMLFileFilter( ), zipFile.getArchiveDetector( ) );
                File[] xmlFiles = projectFolder.listFiles( new XMLFileFilter( ) );
                for( File xmlFile : xmlFiles )
                    if( xmlFile.isFile( ) )
                        xmlFile.delete( );
            }

            // Add the special asset files
            AssetsController.addSpecialAssets( );

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
            fout = new FileOutputStream( folderName + "/descriptor.xml" );
            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );
            /** ******** END WRITING THE DESCRIPTOR ********** */

            /** ******* START WRITING THE CHAPTERS ********* */
            // Write every chapter

            int chapterIndex = 1;
            for( Chapter chapter : adventureData.getChapters( ) ) {

                doc = db.newDocument( );
                // Pick the main node of the chapter
                mainNode = ChapterDOMWriter.buildDOM( chapter, folderName, doc );
                /** ******* START WRITING THE ADAPTATION DATA ***** */
                for( AdaptationProfile profile : chapter.getAdaptationProfiles( ) ) {
                    mainNode.appendChild( Writer.writeAdaptationData( profile, true, doc ) );
                }
                /** ******* END WRITING THE ADAPTATION DATA ***** */

                /** ******* START WRITING THE ASSESSMENT DATA ***** */
                for( AssessmentProfile profile : chapter.getAssessmentProfiles( ) ) {
                    mainNode.appendChild( Writer.writeAssessmentData( profile, true, doc ) );
                }
                /** ******* END WRITING THE ASSESSMENT DATA ***** */

                indentDOM( mainNode, 0 );
                doc = db.newDocument( );
                doc.adoptNode( mainNode );
                doc.appendChild( mainNode );

                // Create the necessary elements for export the DOM into a XML file
                transformer = tFactory.newTransformer( );
                transformer.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, "eadventure.dtd" );

                // Create the output buffer, write the DOM and close it
                fout = new FileOutputStream( folderName + "/chapter" + chapterIndex++ + ".xml" );
                writeFile = new OutputStreamWriter( fout, "UTF-8" );
                transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
                writeFile.close( );
                fout.close( );
            }
            /** ******** END WRITING THE CHAPTERS ********** */

            // Update the zip files
            //File.umount( );
            dataSaved = true;

        }
        catch( IOException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
        }
        catch( ParserConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
        }
        catch( TransformerConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
        }
        catch( TransformerException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
        }

        return dataSaved;
    }

    public static Element writeAssessmentData( /* String zipFilename,*/AssessmentProfile profile, boolean valid, Document doc ) {

        /**
         * ******************* STORE ASSESSMENT DATA WHEN PRESENT
         * ******************
         */
        //boolean dataSaved=false;
        Element assNode = null;
        if( profile.getName( ) != null && !profile.getName( ).equals( "" ) /*&& new File(zipFilename, controller.getPath( )).exists()*/) {

            // Create the necessary elements for building the DOM
            /*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            TransformerFactory tFactory = TransformerFactory.newInstance( );
            DocumentBuilder db=null;
            /*Document doc = null;
            Transformer transformer = null;
            OutputStream fout = null;
            OutputStreamWriter writeFile = null;*/

            //try {
            //db = dbf.newDocumentBuilder( );
            // Pick the main node of the chapter
            assNode = AssessmentDOMWriter.buildDOM( profile, doc );
            /*indentDOM( assNode, 0 );
            
            doc = db.newDocument( );
            doc.adoptNode( assNode );
            doc.appendChild( assNode );
            */
            // Create the necessary elements for export the DOM into a XML file
            /*transformer = tFactory.newTransformer( );
            transformer.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, "assessment.dtd" );

            // 	Create the output buffer, write the DOM and close it
            fout = new FileOutputStream( zipFilename + "/"+ controller.getPath( ) );
            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );*/
            //	dataSaved=true;
            /*} catch( ParserConfigurationException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            }/* catch( TransformerConfigurationException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            } catch( UnsupportedEncodingException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            } catch( TransformerException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            } catch( FileNotFoundException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            } catch( IOException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            }*/
        }
        return assNode;
    }

    public static Element writeAdaptationData( /* String zipFilename,*/AdaptationProfile profile, boolean valid, Document doc ) {

        /**
         * ******************* STORE ASSESSMENT DATA WHEN PRESENT
         * ******************
         */

        Element adpNode = null;
        // take the name of the profile
        String name = profile.getName( );
        if( name != null && !profile.getName( ).equals( "" )/*&& new File(zipFilename, controller.getPath( )).exists()*/) {

            List<AdaptationRule> rules = profile.getRules( );
            AdaptedState initialState = profile.getInitialState( );

            // check if it is an scorm profile
            boolean scorm2004 = profile.isScorm2004( );
            boolean scorm12 = profile.isScorm12( );

            // Create the necessary elements for building the DOM
            /*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            TransformerFactory tFactory = TransformerFactory.newInstance( );
            DocumentBuilder db=null;
            Document doc = null;
            Transformer transformer = null;
            OutputStream fout = null;
            OutputStreamWriter writeFile = null;
            */
            //try {
            //db = dbf.newDocumentBuilder( );
            // Pick the main node of the chapter
            adpNode = AdaptationDOMWriter.buildDOM( rules, initialState, scorm12, scorm2004, name, doc );
            /*indentDOM( adpNode, 0 );
            
            doc = db.newDocument( );
            doc.adoptNode( adpNode );
            doc.appendChild( adpNode );

            // Create the necessary elements for export the DOM into a XML file
            /*transformer = tFactory.newTransformer( );
            transformer.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, "adaptation.dtd" );
            */

            // 	Create the output buffer, write the DOM and close it
            /*fout = new FileOutputStream( zipFilename + "/"+controller.getPath( ) );
            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );*/

            //dataSaved=true;
            /*} catch( ParserConfigurationException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            }/* catch( TransformerConfigurationException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            }catch( UnsupportedEncodingException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            } catch( TransformerException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            } /*catch( FileNotFoundException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            } catch( IOException e ) {
            	ReportDialog.GenerateErrorReport(e, true, "UNKNOWNERROR");
            }*/
        }
        return adpNode;
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

    private static boolean writeWebPage( String tempDir, String loName, boolean windowed, String mainClass ) {
        return writeWebPage ( tempDir, loName, windowed, mainClass, null);
    }
    
    private static boolean writeWebPage( String tempDir, String loName, boolean windowed, String mainClass, HashMap<String, String> additionalParams ) {


        boolean dataSaved = true;
        try {
            String jscript = "";
            
          //TODO cambiar
            boolean lams = true;
            if (mainClass.equals( "es.eucm.eadventure.engine.EAdventureAppletLAMS"  )){
                lams = true;
            }
            
            // THE NEXT CODE WAS ADDED ON 18th Dec 2009. This will be used to remove the loading message dynamically
            jscript += "\t\t<script type='text/javascript' language='JavaScript'>\n";
            jscript += "\t\t\t<!--\n";
            jscript += "\t\t\tfunction hideText(){\n";
            jscript += "\t\t\t\tmsg = document.getElementById('loadingMessage');\n";
            jscript += "\t\t\t\tmsg.style.display = 'none';\n";
            jscript += "\t\t\t}\n";
            if (lams){
            // THE NEXT CODE WAS ADDED ON Feb 2010. This will be used to communicate with LAMS
            jscript += "\t\t\tfunction getParams(){\n";
            jscript += "\t\t\t\tparent.setParams();\n";
            jscript += "\t\t\t}\n";
            // END ADDED CODE
            // THE NEXT CODE WAS ADDED ON Feb 2010. This will be used to communicate with LAMS
            jscript += "\t\t\tfunction showButton(){\n";
            jscript += "\t\t\t\ttop.contentFrame.frames[0].showButton();\n";
            jscript += "\t\t\t}\n";
            // END ADDED CODE
            }
            
            jscript += "\t\t//-->\n";
            jscript += "\t\t</script>\n";
            // END ADDED CODE
            
            if( mainClass.equals( "es.eucm.eadventure.engine.EAdventureAppletScorm" ) ) {
                jscript += "\n\t\t<script type='text/javascript' src='eadventure.js'></script>\n";
            }

            String webPage = "<html>\n" + "\t<head>\n" +
            //"\t\t<script type='text/javascript' src='commapi.js'></script>\n"+
            //"\t\t<script type='text/javascript' src='ajax-wrapper.js'></script>\n"+
            //"\t\t<script type='text/javascript' src='egame.js'></script>\n"+
            
            //"\t\t<param name=\"USER_ID\" value=\"567\"/>\n" + "\t\t<param name=\"RUN_ID\" value=\"5540\"/>\n" +
            //The game is initating.. please be patient while the digital sign is verified
 
            jscript + "\t</head>\n" + "\t<body>\n" + "\t\t<applet code=\"" + mainClass + "\" archive=\"./" + loName + ".jar\" name=\"eadventure\" id=\"eadventure\" " + ( windowed ? "width=\"200\" height=\"150\"" : "width=\"800\" height=\"600\"" ) +  " MAYSCRIPT>\n" + "\t\t<param name=\"WINDOWED\" value=\"" + ( windowed ? "yes" : "no" ) + "\"/>\n" + "\t\t<param name=\"java_arguments\" value=\"-Xms256m -Xmx512m\"/>\n"+ "\t\t<param name=\"image\" value=\"splashScreen.gif\"/>\n";
            
            // Add additional params
            if (additionalParams!=null){
                for (String param: additionalParams.keySet( )){
                    if (param!=null && additionalParams.get( param )!=null){
                        String value = additionalParams.get( param );
                        webPage+= "\t\t<param name=\"" +param+"\" value=\""+value+"\"/>\n";
                    }
                }
            }
            webPage+="\t\t</applet>\n" + "<div id=\"loadingMessage\"><p><b>"+TC.get( "Applet.LoadingMessage" )+"</b></p></div>\n" + "\t</body>\n" + "</html>\n";

            File pageFile = new File( tempDir + "/" + loName + ".html" );
            pageFile.createNewFile( );
            OutputStream is = new FileOutputStream( pageFile );
            is.write( webPage.getBytes( ) );
            is.close( );

            dataSaved = true;

        }
        catch( FileNotFoundException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWNERROR" );
            dataSaved = false;
        }
        catch( IOException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWNERROR" );
            dataSaved = false;
        }
        return dataSaved;
    }
    
    /**
     * Returns the text of a simple manifest file with the main class as
     * specified by argument
     * 
     * @param destinyFile
     * @param mainClass
     */
    public static String defaultManifestFile( String mainClass ) {

        String manifest = "Manifest-Version: 1.0\n" + "Ant-Version: Apache Ant 1.7.0\n" + "Created-By: 1.6.0_02-b06 (Sun Microsystems Inc.)\n" + "Main-Class: " + mainClass + "\n";

        return manifest;
    }

    /**
     * Exports the game as a .ead file
     * 
     * @param projectDirectory
     * @param destinyEADPath
     * @return
     */
    public static boolean export( String projectDirectory, String destinyEADPath ) {

        boolean exported = false;
        String destinyFilePathNoExt = destinyEADPath.substring( 0, destinyEADPath.lastIndexOf( "." ) );
        String destinyZIPPath = destinyFilePathNoExt + ".zip";
        File.zipDirectory( projectDirectory, destinyZIPPath );
        File destinyZIPFile = new File( destinyZIPPath );
        File destinyEADFile = new File( destinyEADPath );
        exported = destinyZIPFile.renameTo( destinyEADFile );
        return exported;
        //return true;
    }
    
    public static void addNeededLibrariesToJar(ZipOutputStream os, Controller controller ) {

        File.addJarContentsToZip("jars/tritonus_share.jar", os);
        // File.addJarContentsToZip("jars/plugin.jar", os);
        File.addJarContentsToZip("jars/mp3spi1.9.4.jar", os);
        File.addJarContentsToZip("jars/jl1.0.jar", os);
        File.addJarContentsToZip("jars/jmf.jar", os );
        //mails libraries are always added because they are needed to show report error dialog
        // TODO mirar de donde salen las referencias a este jar, supuestamente para el report dialog no hace falta
        File.addJarContentsToZip( "jars/mailapi.jar", os );
        File.addJarContentsToZip( "jars/smtp.jar", os);
        File.addJarContentsToZip( "jars/activation.jar", os );
        //XXX Uncomment these three lines to activate the game log
        //File.addJarContentsToZip( "jars/httpclient-4.1.3.jar", os );
        //File.addJarContentsToZip( "jars/httpcore-4.1.4.jar", os );
        //File.addJarContentsToZip( "jars/commons-logging-1.1.1.jar", os );

        boolean needsFreeTts = false;

        boolean needsJFFMpeg = false;
        
      
        
        for (ChapterDataControl chapter :controller.getCharapterList( ).getChapters( )) {
            for (CutsceneDataControl cutscene : chapter.getCutscenesList( ).getCutscenes( )) {
                if (cutscene.getType( ) == Controller.CUTSCENE_VIDEO)
                    needsJFFMpeg = true;
            }
            for (NPCDataControl npc: chapter.getNPCsList( ).getNPCs( )) {
                if (npc.isAlwaysSynthesizer( ))
                    needsFreeTts = true;
            }
            if (chapter.getPlayer( ).isAlwaysSynthesizer( ))
                needsFreeTts = true;
            for (ConversationDataControl conversation: chapter.getConversationsList( ).getConversations( )) {
                for (ConversationNodeView cnv : conversation.getAllNodes( )) {
                    for (int i = 0; i < cnv.getLineCount( ); i++)
                        if (cnv.getConversationLine( i ).getSynthesizerVoice( ))
                            needsFreeTts = true;
                }
            }
        }
       
        if (needsFreeTts) {
            File.addJarContentsToZip("jars/freetts.jar", os);
            File.addJarContentsToZip("jars/cmu_time_awb.jar", os);
            File.addJarContentsToZip("jars/cmulex.jar", os);
            File.addJarContentsToZip("jars/cmutimelex.jar", os);
            File.addJarContentsToZip("jars/cmudict04.jar", os);
            File.addJarContentsToZip("jars/en_us.jar", os);
            File.addJarContentsToZip("jars/cmu_us_kal.jar", os);
        }
        if (needsJFFMpeg) {
           File.addJarContentsToZip("jars/jffmpeg-1.1.0.jar", os );
        }
       
        
        File.addFileToZip( new File(ReleaseFolders.getLanguageFilePath4Engine( Controller.getInstance( ).getLanguage( ) )), "i18n/engine/en_EN.xml", os );
        
    }
    
    /**
     * Exports the game as a jar file
     * 
     * @param projectDirectory
     * @param destinyJARPath
     * @param controller 
     * @return
     */
    public static boolean exportStandalone( String projectDirectory, String destinyJARPath) {

        boolean exported = true;

        try {
            // Destiny file
            File destinyJarFile = new File( destinyJARPath );

            // Create output stream		
            FileOutputStream mergedFile = new FileOutputStream( destinyJarFile );

            // Create zipoutput stream
            ZipOutputStream os = new ZipOutputStream( mergedFile );
            
            // Create and copy the manifest into the output stream
            String manifest = Writer.defaultManifestFile( "es.eucm.eadventure.engine.EAdventureStandalone" );
            ZipEntry manifestEntry = new ZipEntry( "META-INF/MANIFEST.MF" );
            os.putNextEntry( manifestEntry );
            os.write( manifest.getBytes( ) );
            os.closeEntry( );
            os.flush( );

            // Merge projectDirectory and web/eAdventure_temp.jar into output stream
            File.mergeZipAndDirToJar( "web/eAdventure_temp.jar", projectDirectory, os );
            addNeededLibrariesToJar(os, Controller.getInstance( ));
            
           
            os.close( );
        }
        catch( FileNotFoundException e ) {
            exported = false;
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWNERROR" );
        }
        catch( IOException e ) {
            exported = false;
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWNERROR" );
        }

        return exported;

    }

    public static boolean exportAsLearningObject( String zipFilename, String loName, String authorName, String organization, boolean windowed, String gameFilename, AdventureDataControl adventureData ) {

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

            //Clean temp directory
            File tempDir = new File(Controller.createTempDirectory( ).getAbsolutePath( ));
            
            //for(File tempFile:tempDir.listFiles(tempDir.getArchiveDetector( ) )){
            for( File tempFile : tempDir.listFiles( ) ) {
                if( tempFile.isDirectory( ) )
                    tempFile.deleteAll( );
                tempFile.delete( );
            }

            // Copy the web to the zip
            dataSaved &= writeWebPage( tempDir.getAbsolutePath( ), loName, windowed, "es.eucm.eadventure.engine.EAdventureApplet " );

            // Merge project & e-Adventure jar into file eAdventure_temp.jar
            // Destiny file
            File jarUnsigned = new File( tempDir.getAbsolutePath( ) + "/eAdventure.zip" );

            // Create output stream		
            FileOutputStream mergedFile = new FileOutputStream( jarUnsigned );

            // Create zipoutput stream
            ZipOutputStream os = new ZipOutputStream( mergedFile );

            // Create and copy the manifest into the output stream
            String manifestText = Writer.defaultManifestFile( "es.eucm.eadventure.engine.EAdventureApplet" );
            ZipEntry manifestEntry = new ZipEntry( "META-INF/MANIFEST.MF" );
            ZipEntry manifestEntry2 = new ZipEntry( "META-INF/services/javax.xml.parsers.SAXParserFactory" );
            ZipEntry manifestEntry3 = new ZipEntry( "META-INF/services/javax.xml.parsers.DocumentBuilderFactory" );
            os.putNextEntry( manifestEntry );
            os.write( manifestText.getBytes( ) );
            os.putNextEntry( manifestEntry2 );
            os.putNextEntry( manifestEntry3 );
            os.closeEntry( );
            os.flush( );
            
            // Merge projectDirectory and web/eAdventure_temp.jar into output stream
            File.mergeZipAndDirToJar( "web/eAdventure_temp.jar", gameFilename, os );
            addNeededLibrariesToJar(os, Controller.getInstance( ));

            os.close( );

            dataSaved &= jarUnsigned.renameTo( new File(tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ) );

            // Integrate game and jar into a new jar File

            //File sourceMierdaFile = new File("web/ead_applet_loader_temp.jar");
            
         // Merge project & e-Adventure jar into file eAdventure_temp.jar
            // Destiny file
            
            ///////////////////////////////////
            //TODO Aquí comienza la parte de EAdAppletLoader
            //File jarUnsigned2 = new File( tempDir.getAbsolutePath( ) + "/eAdventure2.zip" );

            // Create output stream     
            //FileOutputStream mergedFile2 = new FileOutputStream( jarUnsigned2 );

            // Create zipoutput stream
            //ZipOutputStream os2 = new ZipOutputStream( mergedFile2 );

            // Create and copy the manifest into the output stream
            //String manifestText2 = Writer.defaultManifestFile( "es.eucm.eadventure.appletloader.EADAppletLoadProgress" );
            //ZipEntry manifestEntryB = new ZipEntry( "META-INF/MANIFEST.MF" );
            //os2.putNextEntry( manifestEntryB );
            //os2.write( manifestText2.getBytes( ) );
            //os2.closeEntry( );
            //os2.flush( );
            
            // Merge projectDirectory and web/eAdventure_temp.jar into output stream
            //File newDir=new File("web/cagoento/");
            //newDir.mkdir( );
            //File.mergeZipAndDirToJar( "web/ead_applet_loader_temp.jar", newDir.getAbsolutePath( ), os2 );

            //os2.close( );

            //dataSaved &= jarUnsigned2.renameTo( new File(tempDir.getAbsolutePath( ) + "/" + "ead_applet_loader_unsigned.jar" ) );

            
            
            
            
            
            
            
            dataSaved = JARSigner.signJar( authorName, organization, tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar", tempDir.getAbsolutePath( ) + "/" + loName + ".jar"/*,
                    new File(tempDir.getAbsolutePath( ) + "/" + "ead_applet_loader_unsigned.jar" ).getAbsolutePath( ), tempDir.getAbsolutePath( ) + "/ead_applet_loader_signed.jar"*/);

            new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ).delete( );

          //TODO Aquí termina parte de EAdAppletLoader            
            ///////////////////////////////////

            
            
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
            itemTitleNode.setTextContent( adventureData.getTitle( ) );
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
            resource.setAttribute( "href", loName + ".html" );

            Node metaData = doc.createElement( "metadata" );
            Node lomNode = LOMDOMWriter.buildLOMDOM( adventureData.getLomController( ), false );
            doc.adoptNode( lomNode );
            metaData.appendChild( lomNode );
            resource.appendChild( metaData );

            Element file = doc.createElement( "file" );
            file.setAttribute( "href", loName + ".html" );
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
            file5.setAttribute( "href", loName + ".jar" );
            resource.appendChild( file5 );
            
            Element file4 = doc.createElement( "file" );
            file4.setAttribute( "href", "splashScreen.gif" );
            resource.appendChild( file4 );

            resources.appendChild( resource );
            manifest.appendChild( resources );
            indentDOM( manifest, 0 );
            doc.adoptNode( manifest );
            doc.appendChild( manifest );

            // Create the necessary elements for export the DOM into a XML file
            transformer = tFactory.newTransformer( );

            // Create the output buffer, write the DOM and close it
            //fout = new FileOutputStream( zipFilename + "/imsmanifest.xml" );
            fout = new FileOutputStream( tempDir.getAbsolutePath( ) + "/imsmanifest.xml" );
            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );
            
            //copy loadingImage
            File splashScreen = new File( "web/splashScreen.gif" );
            if ( windowed ){
                splashScreen = new File( "web/splashScreen_red.gif");
            }
            splashScreen.copyTo( new File( tempDir.getAbsolutePath( ) + "/splashScreen.gif" ) );
            
            //TODO
            //new File("web/temp/ead_applet_loader_signed.jar" ).copyTo( new File( tempDir.getAbsolutePath( ) + "/eadAppletLoader.jar" ));
            
            /** ******** END WRITING THE MANIFEST ********** */

            /** COPY EVERYTHING TO THE ZIP */
            File.zipDirectory( tempDir.getAbsolutePath( ) + "/", zipFilename );
            //dataSaved&=new File("web/temp").archiveCopyAllTo( new File(zipFile) );

            // Update the zip files
            //File.umount( );

        }
        catch( IOException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( ParserConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }

        return dataSaved;
    }
    
    public static boolean exportAsLAMSLearningObject( String zipFilename, String loName, String authorName, String organization, 
            boolean windowed, String gameFilename, AdventureDataControl adventureData) {

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

            //Clean temp directory
            File tempDir = new File(Controller.createTempDirectory( ).getAbsolutePath( ));


            //for(File tempFile:tempDir.listFiles(tempDir.getArchiveDetector( ) )){
            for( File tempFile : tempDir.listFiles( ) ) {
                if( tempFile.isDirectory( ) )
                    tempFile.deleteAll( );
                tempFile.delete( );
            }

            // Copy the web to the zip
            dataSaved &= writeWebPage( tempDir.getAbsolutePath( ), loName, windowed, "es.eucm.eadventure.engine.EAdventureAppletLAMS " );

            // Merge project & e-Adventure jar into file eAdventure_temp.jar
            // Destiny file
            File jarUnsigned = new File( tempDir.getAbsolutePath( ) + "/eAdventure.zip" );

            // Create output stream     
            FileOutputStream mergedFile = new FileOutputStream( jarUnsigned );

            // Create zipoutput stream
            ZipOutputStream os = new ZipOutputStream( mergedFile );
            
            String manifestText = Writer.defaultManifestFile( "es.eucm.eadventure.engine.EAdventureAppletLAMS" );
            ZipEntry manifestEntry = new ZipEntry( "META-INF/MANIFEST.MF" );
            ZipEntry manifestEntry2 = new ZipEntry( "META-INF/services/javax.xml.parsers.SAXParserFactory" );
            ZipEntry manifestEntry3 = new ZipEntry( "META-INF/services/javax.xml.parsers.DocumentBuilderFactory" );
            os.putNextEntry( manifestEntry );
            os.write( manifestText.getBytes( ) );
            os.putNextEntry( manifestEntry2 );
            os.putNextEntry( manifestEntry3 );
            os.closeEntry( );

            // Merge projectDirectory and web/eAdventure_temp.jar into output stream
            File.mergeZipAndDirToJar( "web/eAdventure_temp.jar", gameFilename, os );
            addNeededLibrariesToJar(os, Controller.getInstance( ));

          
            os.close( );

            dataSaved &= jarUnsigned.renameTo( new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ) );

            // Integrate game and jar into a new jar File
            dataSaved = JARSigner.signJar( authorName, organization, tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar", tempDir.getAbsolutePath( ) + "/" + loName + ".jar");

            new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ).delete( );

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
            itemTitleNode.setTextContent( adventureData.getTitle( ) );
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
            resource.setAttribute( "href", loName + ".html" );

            Node metaData = doc.createElement( "metadata" );
            Node lomNode = LOMDOMWriter.buildLOMDOM( adventureData.getLomController( ), false );
            doc.adoptNode( lomNode );
            metaData.appendChild( lomNode );
            resource.appendChild( metaData );

            Element file = doc.createElement( "file" );
            file.setAttribute( "href", loName + ".html" );
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
            file5.setAttribute( "href", loName + ".jar" );
            resource.appendChild( file5 );
            
            Element file4 = doc.createElement( "file" );
            file4.setAttribute( "href", "splashScreen.gif" );
            resource.appendChild( file4 );

            resources.appendChild( resource );
            manifest.appendChild( resources );
            indentDOM( manifest, 0 );
            doc.adoptNode( manifest );
            doc.appendChild( manifest );

            // Create the necessary elements for export the DOM into a XML file
            transformer = tFactory.newTransformer( );

            // Create the output buffer, write the DOM and close it
            //fout = new FileOutputStream( zipFilename + "/imsmanifest.xml" );
            fout = new FileOutputStream( tempDir.getAbsolutePath( ) + "/imsmanifest.xml" );
            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );
            
            //copy loadingImage
            File splashScreen = new File( "web/splashScreen.gif" );
            if ( windowed ){
                splashScreen = new File( "web/splashScreen_red.gif");
            }
            splashScreen.copyTo( new File( tempDir.getAbsolutePath( ) + "/splashScreen.gif" ) );
            
            /** ******** END WRITING THE MANIFEST ********** */
            
            /** ******* START WRITING THE IOParameters ********* */
       
                
            db = dbf.newDocumentBuilder( );
            
            doc = db.newDocument( );
            
            Element param = ExpectedGameIODOMWriter.buildExpectedInputs( doc, getAdaptationRules(adventureData.getAdventureData( ).getChapters( )), getAssessmemtRules(adventureData.getAdventureData( ).getChapters( )) );

            indentDOM( param, 0 );
            doc.adoptNode( param );
            doc.appendChild( param );

            
            // Create the necessary elements for export the DOM into a XML file
            transformer = tFactory.newTransformer( );

            // Create the output buffer, write the DOM and close it
            //fout = new FileOutputStream( zipFilename + "/imsmanifest.xml" );
            fout = new FileOutputStream(tempDir.getAbsolutePath( ) + "/ead-parameters.xml" );
            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );
            /** ******* END WRITING THE IOParameters********* */

            /** COPY EVERYTHING TO THE ZIP */
            File.zipDirectory( tempDir.getAbsolutePath( ) + "/", zipFilename );
        }
        catch( IOException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( ParserConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }

        return dataSaved;
    }

    
    public static boolean exportAsGAMETELLearningObject( String zipFilename, String loName, String authorName, String organization, 
            boolean windowed, String gameFilename, String testReturnURI, String testUserID, AdventureDataControl adventureData) {

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

            //Clean temp directory
            File tempDir = new File(Controller.createTempDirectory( ).getAbsolutePath( ));


            //for(File tempFile:tempDir.listFiles(tempDir.getArchiveDetector( ) )){
            for( File tempFile : tempDir.listFiles( ) ) {
                if( tempFile.isDirectory( ) )
                    tempFile.deleteAll( );
                tempFile.delete( );
            }

            // Copy the web to the zip
            HashMap<String, String> additionalParams = new HashMap<String, String>();
            if (testReturnURI!=null && testUserID!=null && testReturnURI.length( )>0 && testUserID.length( )>0){
                additionalParams.put( "return-uri", testReturnURI );
                additionalParams.put( "user-id", testUserID );
            }
            dataSaved &= writeWebPage( tempDir.getAbsolutePath( ), loName, windowed, "es.eucm.eadventure.engine.EAdventureAppletGAMETEL", additionalParams );

            // Merge project & e-Adventure jar into file eAdventure_temp.jar
            // Destiny file
            File jarUnsigned = new File( tempDir.getAbsolutePath( ) + "/eAdventure.zip" );

            // Create output stream     
            FileOutputStream mergedFile = new FileOutputStream( jarUnsigned );

            // Create zipoutput stream
            ZipOutputStream os = new ZipOutputStream( mergedFile );
            
            String manifestText = Writer.defaultManifestFile( "es.eucm.eadventure.engine.EAdventureAppletGAMETEL" );
            ZipEntry manifestEntry = new ZipEntry( "META-INF/MANIFEST.MF" );
            ZipEntry manifestEntry2 = new ZipEntry( "META-INF/services/javax.xml.parsers.SAXParserFactory" );
            ZipEntry manifestEntry3 = new ZipEntry( "META-INF/services/javax.xml.parsers.DocumentBuilderFactory" );
            os.putNextEntry( manifestEntry );
            os.write( manifestText.getBytes( ) );
            os.putNextEntry( manifestEntry2 );
            os.putNextEntry( manifestEntry3 );
            os.closeEntry( );

            // Merge projectDirectory and web/eAdventure_temp.jar into output stream
            File.mergeZipAndDirToJar( "web/eAdventure_temp.jar", gameFilename, os );
            addNeededLibrariesToJar(os, Controller.getInstance( ));

          
            os.close( );

            dataSaved &= jarUnsigned.renameTo( new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ) );

            // Integrate game and jar into a new jar File

            dataSaved = JARSigner.signJar( authorName, organization, tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar", tempDir.getAbsolutePath( ) + "/" + loName + ".jar" );

            new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ).delete( );

            //copy loadingImage
            File splashScreen = new File( "web/splashScreen.gif" );
            if ( windowed ){
                splashScreen = new File( "web/splashScreen_red.gif");
            }
            splashScreen.copyTo( new File( tempDir.getAbsolutePath( ) + "/splashScreen.gif" ) );
            
            
            /** ******* START WRITING THE IOParameters ********* */
       
                
            db = dbf.newDocumentBuilder( );
            
            doc = db.newDocument( );
            
            Element param = ExpectedGameIODOMWriter.buildExpectedInputs( doc, getAdaptationRules(adventureData.getAdventureData( ).getChapters( )), getAssessmemtRules(adventureData.getAdventureData( ).getChapters( )) );

            indentDOM( param, 0 );
            doc.adoptNode( param );
            doc.appendChild( param );

            
            // Create the necessary elements for export the DOM into a XML file
            transformer = tFactory.newTransformer( );

            // Create the output buffer, write the DOM and close it
            //fout = new FileOutputStream( zipFilename + "/imsmanifest.xml" );
            fout = new FileOutputStream(tempDir.getAbsolutePath( ) + "/ead-parameters.xml" );
            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );
            /** ******* END WRITING THE IOParameters********* */

            /** COPY EVERYTHING TO THE ZIP */
            File.zipDirectory( tempDir.getAbsolutePath( ) + "/", zipFilename );
        }
        catch( IOException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( ParserConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }

        return dataSaved;
    }

    
    private static List<AdaptationRule> getAdaptationRules(List<Chapter> cs){
        ArrayList<AdaptationRule> adapt = new ArrayList<AdaptationRule>();
        
        Iterator it = cs.iterator( );
        while (it.hasNext( )){
            Chapter c = (Chapter) it.next( );
            Iterator it2= c.getAdaptationProfiles( ).iterator( );
            while (it2.hasNext( )){
                AdaptationProfile adp = (AdaptationProfile) it2.next( );
               adapt.addAll( adp.getRules( ) );
                
            }
        }
        
        return adapt;
    }
    
    private static List<AssessmentRule> getAssessmemtRules(List<Chapter> cs){
        ArrayList<AssessmentRule> assess = new ArrayList<AssessmentRule>();
        
        Iterator it = cs.iterator( );
        while (it.hasNext( )){
            Chapter c = (Chapter) it.next( );
            Iterator it2= c.getAssessmentProfiles( ).iterator( );
            while (it2.hasNext( )){
                AssessmentProfile adp = (AssessmentProfile) it2.next( );
                assess.addAll( adp.getRules( ) );
                
            }
        }
        
        return assess;
    }
    
    

    public static boolean exportAsSCORM( String zipFilename, String loName, String authorName, String organization, boolean windowed, String gameFilename, AdventureDataControl adventureData ) {

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

            //Clean temp directory
            File tempDir = new File(Controller.createTempDirectory( ).getAbsolutePath( ));

            for( File tempFile : tempDir.listFiles( ) ) {
                if( tempFile.isDirectory( ) )
                    tempFile.deleteAll( );
                tempFile.delete( );
            }


            // Copy the web to the zip
            dataSaved &= writeWebPage( tempDir.getAbsolutePath( ), loName, windowed, "es.eucm.eadventure.engine.EAdventureAppletScorm" );

            // Merge project & e-Adventure jar into file eAdventure_temp.jar
            // Destiny file
            File jarUnsigned = new File( tempDir.getAbsolutePath( ) + "/eAdventure.zip" );

            // Create output stream		
            FileOutputStream mergedFile = new FileOutputStream( jarUnsigned );

            // Create zipoutput stream
            ZipOutputStream os = new ZipOutputStream( mergedFile );
            
            // Create and copy the manifest into the output stream
            String manifestText = Writer.defaultManifestFile( "es.eucm.eadventure.engine.EAdventureAppletScorm" );
            ZipEntry manifestEntry = new ZipEntry( "META-INF/MANIFEST.MF" );
            ZipEntry manifestEntry2 = new ZipEntry( "META-INF/services/javax.xml.parsers.SAXParserFactory" );
            ZipEntry manifestEntry3 = new ZipEntry( "META-INF/services/javax.xml.parsers.DocumentBuilderFactory" );
            os.putNextEntry( manifestEntry );
            os.write( manifestText.getBytes( ) );
            os.putNextEntry( manifestEntry2 );
            os.putNextEntry( manifestEntry3 );
            os.closeEntry( );
            os.flush( );
            
            //PRUEBA Extraer el chapter.xml a la raiz del doc scorm para solución CEPAL-MOODLE
            //File chapterForMoodle = new File( gameFilename+"\\chapter1.xml" );
            //chapterForMoodle.copyTo( new File( "web/temp/chapter1.xml" ) );
            
            // Merge projectDirectory and web/eAdventure_temp.jar into output stream
            File.mergeZipAndDirToJar( "web/eAdventure_temp.jar", gameFilename, os );
            addNeededLibrariesToJar(os, Controller.getInstance( ));
            
            os.close( );

            dataSaved &= jarUnsigned.renameTo( new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ) );

            // Integrate game and jar into a new jar File

            dataSaved = JARSigner.signJar( authorName, organization, tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar", tempDir.getAbsolutePath( ) + "/" + loName + ".jar" );

            new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ).delete( );

            /** ******* START WRITING THE MANIFEST ********* */
            // Create the necessary elements for building the DOM
            db = dbf.newDocumentBuilder( );
            doc = db.newDocument( );

            // Pick the main node for the descriptor
            Element manifest = null;
            manifest = doc.createElement( "manifest" );
            manifest.setAttribute( "identifier", "imsaccmdv1p0_manifest" );
            manifest.setAttribute( "xmlns", "http://www.imsproject.org/xsd/imscp_rootv1p1p2" );
            manifest.setAttribute( "xmlns:imsmd", "http://www.imsglobal.org/xsd/imsmd_rootv1p2p1" );
            manifest.setAttribute( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance" );
            manifest.setAttribute( "xsi:schemaLocation", "http://www.imsproject.org/xsd/imscp_rootv1p1p2 imscp_rootv1p1p2.xsd http://www.imsglobal.org/xsd/imsmd_rootv1p2p1 imsmd_rootv1p2p1.xsd http://www.adlnet.org/xsd/adlcp_rootv1p2 adlcp_rootv1p2.xsd" );
            manifest.setAttribute( "xmlns:adlcp", "http://www.adlnet.org/xsd/adlcp_rootv1p2" );
            manifest.setAttribute( "version", "1.1" );

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
            itemEl.setAttribute( "isvisible", "true" );
            //itemEl.setAttribute( "parameters", "" );
            Node itemTitleNode = doc.createElement( "title" );
            itemTitleNode.setTextContent( adventureData.getTitle( ) );
            itemEl.appendChild( itemTitleNode );
            organizationEl.appendChild( itemEl );
            //Append the organization node to organizations
            organizations.appendChild( organizationEl );

            manifest.appendChild( organizations );
            //Create the resources node
            Node resources = doc.createElement( "resources" );
            Element resource = doc.createElement( "resource" );
            resource.setAttribute( "adlcp:scormtype", "sco" );
            resource.setAttribute( "identifier", RESOURCE_IDENTIFIER );
            resource.setAttribute( "type", "webcontent" );
            resource.setAttribute( "href", loName + ".html" );

            Node metaData = doc.createElement( "metadata" );
            Node schema = doc.createElement( "schema" );
            schema.setTextContent( "ADL SCORM" );
            metaData.appendChild( schema );
            Node schemaVersion = doc.createElement( "schemaversion" );
            schemaVersion.setTextContent( "1.2" );
            metaData.appendChild( schemaVersion );
            Node lomNode = IMSDOMWriter.buildIMSDOM( adventureData.getImsController( ) );
            doc.adoptNode( lomNode );
            metaData.appendChild( lomNode );
            resource.appendChild( metaData );

            Element file = doc.createElement( "file" );
            file.setAttribute( "href", loName + ".html" );
            resource.appendChild( file );

            Element file2 = doc.createElement( "file" );
            file2.setAttribute( "href", "eadventure.js" );
            resource.appendChild( file2 );

            Element file3 = doc.createElement( "file" );
            file3.setAttribute( "href", loName + ".jar" );
            resource.appendChild( file3 );
            
            Element file4 = doc.createElement( "file" );
            file4.setAttribute( "href", "splashScreen.gif" );
            resource.appendChild( file4 );

            resources.appendChild( resource );
            manifest.appendChild( resources );
            indentDOM( manifest, 0 );
            doc.adoptNode( manifest );
            doc.appendChild( manifest );

            // Create the necessary elements for export the DOM into a XML file
            transformer = tFactory.newTransformer( );

            // Create the output buffer, write the DOM and close it
            //fout = new FileOutputStream( zipFilename + "/imsmanifest.xml" );
            fout = new FileOutputStream( tempDir.getAbsolutePath( ) + "/imsmanifest.xml" );
            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );

            // copy mandatory xsd
            File xsd = new File( "web/adlcp_rootv1p2.xsd" );
            xsd.copyTo( new File( tempDir.getAbsolutePath( ) + "/adlcp_rootv1p2.xsd" ) );
            xsd = new File( "web/ims_xml.xsd" );
            xsd.copyTo( new File( tempDir.getAbsolutePath( ) + "/ims_xml.xsd" ) );
            xsd = new File( "web/imscp_rootv1p1p2.xsd" );
            xsd.copyTo( new File( tempDir.getAbsolutePath( ) + "/imscp_rootv1p1p2.xsd" ) );
            xsd = new File( "web/imsmd_rootv1p2p1.xsd" );
            xsd.copyTo( new File( tempDir.getAbsolutePath( ) + "/imsmd_rootv1p2p1.xsd" ) );

            //copy javascript
            File javaScript = new File( "web/eadventure.js" );
            javaScript.copyTo( new File( tempDir.getAbsolutePath( ) + "/eadventure.js" ) );
            
            //copy loadingImage
            File splashScreen = new File( "web/splashScreen.gif" );
            if ( windowed ){
                splashScreen = new File( "web/splashScreen_red.gif");
            }
            splashScreen.copyTo( new File( tempDir.getAbsolutePath( ) + "/splashScreen.gif" ) );
            
            //sourceFile = new File("web/temp/imsmanifest.xml");
            //destinyFile = new File (zipFilename, "imsmanifest.xml");
            //dataSaved &= sourceFile.copyTo( destinyFile );
            /** ******** END WRITING THE MANIFEST ********** */

            /** COPY EVERYTHING TO THE ZIP */
            File.zipDirectory( tempDir.getAbsolutePath( ) + "/", zipFilename );
            //dataSaved&=new File("web/temp").archiveCopyAllTo( new File(zipFile) );

            // Update the zip files
            //File.umount( );

        }
        catch( IOException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( ParserConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }

        return dataSaved;
    }

    public static boolean exportAsSCORM2004( String zipFilename, String loName, String authorName, String organization, boolean windowed, String gameFilename, AdventureDataControl adventureData ) {

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

            //Clean temp directory
            File tempDir = new File(Controller.createTempDirectory( ).getAbsolutePath( ));
            for( File tempFile : tempDir.listFiles( ) ) {
                if( tempFile.isDirectory( ) )
                    tempFile.deleteAll( );
                tempFile.delete( );
            }

            
            // Copy the web to the zip
            dataSaved &= writeWebPage( tempDir.getAbsolutePath( ), loName, windowed, "es.eucm.eadventure.engine.EAdventureAppletScorm" );

            // Merge project & e-Adventure jar into file eAdventure_temp.jar
            // Destiny file
            File jarUnsigned = new File( tempDir.getAbsolutePath( ) + "/eAdventure.zip" );

            // Create output stream		
            FileOutputStream mergedFile = new FileOutputStream( jarUnsigned );

            // Create zipoutput stream
            ZipOutputStream os = new ZipOutputStream( mergedFile );
            
            // Create and copy the manifest into the output stream
            String manifestText = Writer.defaultManifestFile( "es.eucm.eadventure.engine.EAdventureAppletScorm" );
            ZipEntry manifestEntry = new ZipEntry( "META-INF/MANIFEST.MF" );
            ZipEntry manifestEntry2 = new ZipEntry( "META-INF/services/javax.xml.parsers.SAXParserFactory" );
            ZipEntry manifestEntry3 = new ZipEntry( "META-INF/services/javax.xml.parsers.DocumentBuilderFactory" );
            os.putNextEntry( manifestEntry );
            os.write( manifestText.getBytes( ) );
            os.putNextEntry( manifestEntry2 );
            os.putNextEntry( manifestEntry3 );
            os.closeEntry( );
            os.flush( );

            // Merge projectDirectory and web/eAdventure_temp.jar into output stream
            File.mergeZipAndDirToJar( "web/eAdventure_temp.jar", gameFilename, os );
            addNeededLibrariesToJar(os, Controller.getInstance( ));

            
            os.close( );

            dataSaved &= jarUnsigned.renameTo( new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ) );

            // Integrate game and jar into a new jar File

            dataSaved = JARSigner.signJar( authorName, organization, tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar", tempDir.getAbsolutePath( ) + "/" + loName + ".jar" );

            new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ).delete( );

            /** ******* START WRITING THE MANIFEST ********* */
            // Create the necessary elements for building the DOM
            db = dbf.newDocumentBuilder( );
            doc = db.newDocument( );

            // Pick the main node for the descriptor
            Element manifest = null;
            manifest = doc.createElement( "manifest" );
            manifest.setAttribute( "identifier", "eAdventureGame" );
            manifest.setAttribute( "xmlns", "http://www.imsglobal.org/xsd/imscp_v1p1" );
            manifest.setAttribute( "xmlns:imsmd", "http://ltsc.ieee.org/xsd/LOM" );
            manifest.setAttribute( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance" );
            manifest.setAttribute( "xmlns:adlcp", "http://www.adlnet.org/xsd/adlcp_v1p3" );
            manifest.setAttribute( "xmlns:imsss", "http://www.imsglobal.org/xsd/imsss" );
            manifest.setAttribute( "xmlns:adlseq", "http://www.adlnet.org/xsd/adlseq_v1p3" );
            manifest.setAttribute( "xmlns:adlnav", "http://www.adlnet.org/xsd/adlnav_v1p3" );
            manifest.setAttribute( "xsi:schemaLocation", "http://www.imsglobal.org/xsd/imscp_v1p1 imscp_v1p1.xsd http://ltsc.ieee.org/xsd/LOM lom.xsd http://www.adlnet.org/xsd/adlcp_v1p3 adlcp_v1p3.xsd http://www.imsglobal.org/xsd/imsss imsss_v1p0.xsd http://www.adlnet.org/xsd/adlseq_v1p3 adlseq_v1p3.xsd http://www.adlnet.org/xsd/adlnav_v1p3 adlnav_v1p3.xsd" );
            //manifest.setAttribute( "version", "0.9" );

            // Create metadata (that is mandatory for SCORM 2004)
            Element metadata = doc.createElement( "metadata" );
            Element schema = doc.createElement( "schema" );
            schema.setTextContent( "ADL SCORM" );
            metadata.appendChild( schema );
            Element schemaversion = doc.createElement( "schemaversion" );
            schemaversion.setTextContent( "2004 3rd Edition" );
            metadata.appendChild( schemaversion );
            manifest.appendChild( metadata );

            //Create the organizations node (required)
            Element organizations = doc.createElement( "organizations" );
            organizations.setAttribute( "default", ORGANIZATION_IDENTIFIER );
            // Just one organization
            Element organizationEl = doc.createElement( "organization" );
            organizationEl.setAttribute( "identifier", ORGANIZATION_IDENTIFIER );
            organizationEl.setAttribute( "structure", ORGANIZATION_STRUCTURE );
            Node organizationTitleNode = doc.createElement( "title" );
            organizationTitleNode.setTextContent( adventureData.getTitle( ) );
            organizationEl.appendChild( organizationTitleNode );
            // The organization contains only one item, which refers to the unique resource
            Element itemEl = doc.createElement( "item" );
            itemEl.setAttribute( "identifier", ITEM_IDENTIFIER );
            itemEl.setAttribute( "identifierref", RESOURCE_IDENTIFIER );
            itemEl.setAttribute( "isvisible", "true" );
            //itemEl.setAttribute( "parameters", "" );
            Node itemTitleNode = doc.createElement( "title" );
            itemTitleNode.setTextContent( adventureData.getTitle( ) );
            itemEl.appendChild( itemTitleNode );
            organizationEl.appendChild( itemEl );
            //Append the organization node to organizations
            organizations.appendChild( organizationEl );

            manifest.appendChild( organizations );
            //Create the resources node
            Node resources = doc.createElement( "resources" );
            Element resource = doc.createElement( "resource" );
            resource.setAttribute( "identifier", RESOURCE_IDENTIFIER );
            resource.setAttribute( "adlcp:scormType", "sco" );
            resource.setAttribute( "type", "webcontent" );
            resource.setAttribute( "href", loName + ".html" );

            Node metaData = doc.createElement( "metadata" );
            Node lomNode = LOMDOMWriter.buildLOMDOM( adventureData.getLomController( ), true );
            doc.adoptNode( lomNode );
            metaData.appendChild( lomNode );
            resource.appendChild( metaData );

            Element file = doc.createElement( "file" );
            file.setAttribute( "href", loName + ".html" );
            resource.appendChild( file );

            Element file2 = doc.createElement( "file" );
            file2.setAttribute( "href", "eadventure.js" );
            resource.appendChild( file2 );

            Element file3 = doc.createElement( "file" );
            file3.setAttribute( "href", loName + ".jar" );
            resource.appendChild( file3 );
            
            Element file4 = doc.createElement( "file" );
            file4.setAttribute( "href", "splashScreen.gif" );
            resource.appendChild( file4 );
            
            resources.appendChild( resource );
            manifest.appendChild( resources );
            indentDOM( manifest, 0 );
            doc.adoptNode( manifest );
            doc.appendChild( manifest );

            // Create the necessary elements for export the DOM into a XML file
            transformer = tFactory.newTransformer( );

            // Create the output buffer, write the DOM and close it
            //fout = new FileOutputStream( zipFilename + "/imsmanifest.xml" );
            fout = new FileOutputStream( tempDir.getAbsolutePath( ) + "/imsmanifest.xml" );
            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );

            // copy mandatory xsd
            File.unzipDir( "web/Scorm2004Content.zip", tempDir.getAbsolutePath( ) + "/" );

            //copy javascript
            File javaScript = new File( "web/eadventure.js" );
            javaScript.copyTo( new File( tempDir.getAbsolutePath( ) + "/eadventure.js" ) );
            
            //copy loadingImage
            File splashScreen = new File( "web/splashScreen.gif" );
            if ( windowed ){
                splashScreen = new File( "web/splashScreen_red.gif");
            }
            splashScreen.copyTo( new File( tempDir.getAbsolutePath( ) + "/splashScreen.gif" ) );
            /** ******** END WRITING THE MANIFEST ********** */

            /** COPY EVERYTHING TO THE ZIP */
            File.zipDirectory( tempDir.getAbsolutePath( ) + "/", zipFilename );
            //dataSaved&=new File("web/temp").archiveCopyAllTo( new File(zipFile) );

            // Update the zip files
            //File.umount( );

        }
        catch( IOException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( ParserConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }

        return dataSaved;
    }

    public static boolean exportAsAGREGA( String zipFilename, String loName, String authorName, String organization, boolean windowed, String gameFilename, AdventureDataControl adventureData ) {

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

            //Clean temp directory
            File tempDir = new File(Controller.createTempDirectory( ).getAbsolutePath( ));
            for( File tempFile : tempDir.listFiles( ) ) {
                if( tempFile.isDirectory( ) )
                    tempFile.deleteAll( );
                tempFile.delete( );
            }
           

            // Copy the web to the zip
            dataSaved &= writeWebPage( tempDir.getAbsolutePath( ), loName, windowed, "es.eucm.eadventure.engine.EAdventureAppletScorm" );
            
            // Merge project & e-Adventure jar into file eAdventure_temp.jar
            // Destiny file
            File jarUnsigned = new File( tempDir.getAbsolutePath( ) + "/eAdventure.zip" );

            // Create output stream		
            FileOutputStream mergedFile = new FileOutputStream( jarUnsigned );

            // Create zipoutput stream
            ZipOutputStream os = new ZipOutputStream( mergedFile );
            
            // Create and copy the manifest into the output stream
            String manifestText = Writer.defaultManifestFile( "es.eucm.eadventure.engine.EAdventureAppletScorm" );
            ZipEntry manifestEntry = new ZipEntry( "META-INF/MANIFEST.MF" );
            ZipEntry manifestEntry2 = new ZipEntry( "META-INF/services/javax.xml.parsers.SAXParserFactory" );
            ZipEntry manifestEntry3 = new ZipEntry( "META-INF/services/javax.xml.parsers.DocumentBuilderFactory" );
            os.putNextEntry( manifestEntry );
            os.write( manifestText.getBytes( ) );
            os.putNextEntry( manifestEntry2 );
            os.putNextEntry( manifestEntry3 );
            os.closeEntry( );
            os.flush( );

            // Merge projectDirectory and web/eAdventure_temp.jar into output stream
            File.mergeZipAndDirToJar( "web/eAdventure_temp.jar", gameFilename, os );
            addNeededLibrariesToJar(os, Controller.getInstance( ));

           
            os.close( );

            dataSaved &= jarUnsigned.renameTo( new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ) );

            // Integrate game and jar into a new jar File

            dataSaved = JARSigner.signJar( authorName, organization, tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar", tempDir.getAbsolutePath( ) + "/" + loName + ".jar" );

            new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ).delete( );

            /** ******* START WRITING THE MANIFEST ********* */
            // Create the necessary elements for building the DOM
            db = dbf.newDocumentBuilder( );
            doc = db.newDocument( );

            // Pick the main node for the descriptor
            Element manifest = null;
            manifest = doc.createElement( "manifest" );
            manifest.setAttribute( "identifier", "eAdventureGame" );
            manifest.setAttribute( "xmlns", "http://www.imsglobal.org/xsd/imscp_v1p1" );
            manifest.setAttribute( "xmlns:lomes", "http://ltsc.ieee.org/xsd/LOM" );
            manifest.setAttribute( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance" );
            manifest.setAttribute( "xmlns:adlcp", "http://www.adlnet.org/xsd/adlcp_v1p3" );
            manifest.setAttribute( "xmlns:imsss", "http://www.imsglobal.org/xsd/imsss" );
            manifest.setAttribute( "xmlns:adlseq", "http://www.adlnet.org/xsd/adlseq_v1p3" );
            manifest.setAttribute( "xmlns:adlnav", "http://www.adlnet.org/xsd/adlnav_v1p3" );
            manifest.setAttribute( "xsi:schemaLocation", "http://www.imsglobal.org/xsd/imscp_v1p1 imscp_v1p1.xsd http://ltsc.ieee.org/xsd/LOM lom.xsd http://www.adlnet.org/xsd/adlcp_v1p3 adlcp_v1p3.xsd http://www.imsglobal.org/xsd/imsss imsss_v1p0.xsd http://www.adlnet.org/xsd/adlseq_v1p3 adlseq_v1p3.xsd http://www.adlnet.org/xsd/adlnav_v1p3 adlnav_v1p3.xsd" );
            //manifest.setAttribute( "version", "0.9" );

            // Create metadata (that is mandatory for SCORM 2004)
            Element metadata = doc.createElement( "metadata" );
            Element schema = doc.createElement( "schema" );
            schema.setTextContent( "ADL SCORM" );
            metadata.appendChild( schema );
            Element schemaversion = doc.createElement( "schemaversion" );
            schemaversion.setTextContent( "2004 3rd Edition" );
            metadata.appendChild( schemaversion );
            Node lomNode = LOMESDOMWriter.buildLOMESDOM( adventureData.getLOMESController( ) );
            doc.adoptNode( lomNode );
            metadata.appendChild( lomNode );
            manifest.appendChild( metadata );

            //Create the organizations node (required)
            Element organizations = doc.createElement( "organizations" );
            organizations.setAttribute( "default", ORGANIZATION_IDENTIFIER );
            // Just one organization
            Element organizationEl = doc.createElement( "organization" );
            organizationEl.setAttribute( "identifier", ORGANIZATION_IDENTIFIER );
            organizationEl.setAttribute( "structure", ORGANIZATION_STRUCTURE );
            Node organizationTitleNode = doc.createElement( "title" );
            organizationTitleNode.setTextContent( adventureData.getTitle( ) );
            organizationEl.appendChild( organizationTitleNode );
            // The organization contains only one item, which refers to the unique resource
            Element itemEl = doc.createElement( "item" );
            itemEl.setAttribute( "identifier", ITEM_IDENTIFIER );
            itemEl.setAttribute( "identifierref", RESOURCE_IDENTIFIER );
            itemEl.setAttribute( "isvisible", "true" );
            //itemEl.setAttribute( "parameters", "" );
            Node itemTitleNode = doc.createElement( "title" );
            itemTitleNode.setTextContent( adventureData.getTitle( ) );
            itemEl.appendChild( itemTitleNode );
            organizationEl.appendChild( itemEl );
            //Append the organization node to organizations
            organizations.appendChild( organizationEl );

            manifest.appendChild( organizations );
            //Create the resources node
            Node resources = doc.createElement( "resources" );
            Element resource = doc.createElement( "resource" );
            resource.setAttribute( "identifier", RESOURCE_IDENTIFIER );
            resource.setAttribute( "adlcp:scormType", "sco" );
            resource.setAttribute( "type", "webcontent" );
            resource.setAttribute( "href", loName + ".html" );

            Element file = doc.createElement( "file" );
            file.setAttribute( "href", loName + ".html" );
            resource.appendChild( file );

            Element file2 = doc.createElement( "file" );
            file2.setAttribute( "href", "eadventure.js" );
            resource.appendChild( file2 );

            Element file3 = doc.createElement( "file" );
            file3.setAttribute( "href", loName + ".jar" );
            resource.appendChild( file3 );
            
            Element file4 = doc.createElement( "file" );
            file4.setAttribute( "href", "splashScreen.gif" );
            resource.appendChild( file4 );

            resources.appendChild( resource );
            manifest.appendChild( resources );
            indentDOM( manifest, 0 );
            doc.adoptNode( manifest );
            doc.appendChild( manifest );

            // Create the necessary elements for export the DOM into a XML file
            transformer = tFactory.newTransformer( );

            // Create the output buffer, write the DOM and close it
            //fout = new FileOutputStream( zipFilename + "/imsmanifest.xml" );
            fout = new FileOutputStream( tempDir.getAbsolutePath( ) + "/imsmanifest.xml" );
            writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            fout.close( );

            // copy mandatory xsd
            File.unzipDir( "web/Scorm2004AgregaContent.zip", tempDir.getAbsolutePath( ) + "/" );

            //copy javascript
            File javaScript = new File( "web/eadventure.js" );
            javaScript.copyTo( new File( tempDir.getAbsolutePath( ) + "/eadventure.js" ) );
            
            //copy loadingImage
            File splashScreen = new File( "web/splashScreen.gif" );
            if ( windowed ){
                splashScreen = new File( "web/splashScreen_red.gif");
            }
            splashScreen.copyTo( new File( tempDir.getAbsolutePath( ) + "/splashScreen.gif" ) );
            /** ******** END WRITING THE MANIFEST ********** */

            /** COPY EVERYTHING TO THE ZIP */
            File.zipDirectory( tempDir.getAbsolutePath( ) + "/", zipFilename );
            //dataSaved&=new File("web/temp").archiveCopyAllTo( new File(zipFile) );

            // Update the zip files
            //File.umount( );

        }
        catch( IOException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( ParserConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerConfigurationException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
            dataSaved = false;
        }
        catch( TransformerException exception ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.Title" ), TC.get( "Error.WriteData" ) );
            ReportDialog.GenerateErrorReport( exception, true, TC.get( "Error.WriteData" ) );
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

    public static boolean exportAsWebCTObject( String zipFilename, String loName, String authorName, String organization, boolean windowed, String gameFilename, AdventureDataControl adventureData ) {

        File tempDir;
        try {
            tempDir = new File(Controller.createTempDirectory( ).getAbsolutePath( ));
        }
        catch( IOException e1 ) {
            e1.printStackTrace();
            return false;
        }
        for( File tempFile : tempDir.listFiles( ) ) {
            if( tempFile.isDirectory( ) )
                tempFile.deleteAll( );
            tempFile.delete( );
        }

        try {

            File jarUnsigned = new File( tempDir.getAbsolutePath( ) + "/eAdventure.zip" );
            FileOutputStream mergedFile = new FileOutputStream( jarUnsigned );
            ZipOutputStream os = new ZipOutputStream( mergedFile );
         
            String manifestText = Writer.defaultManifestFile( "es.eucm.eadventure.engine.EAdventureAppletScorm" );
            ZipEntry manifestEntry = new ZipEntry( "META-INF/MANIFEST.MF" );
            ZipEntry manifestEntry2 = new ZipEntry( "META-INF/services/javax.xml.parsers.SAXParserFactory" );
            ZipEntry manifestEntry3 = new ZipEntry( "META-INF/services/javax.xml.parsers.DocumentBuilderFactory" );
            os.putNextEntry( manifestEntry );
            os.write( manifestText.getBytes( ) );
            os.putNextEntry( manifestEntry2 );
            os.putNextEntry( manifestEntry3 );
            os.closeEntry( );
            os.flush( );

            File.mergeZipAndDirToJar( "web/eAdventure_temp.jar", gameFilename, os );
            addNeededLibrariesToJar(os, Controller.getInstance( ));

            
            os.close( );

            String fixedLoName = "learningObject";

            jarUnsigned.renameTo( new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ) );
            File.unzipDir( "web/webct_temp.zip", tempDir.getAbsolutePath( ) + "/" );
            JARSigner.signJar( authorName, organization, tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar", tempDir.getAbsolutePath( ) + "/CMD_6988980_M/my_files/" + loName + ".jar" );
            new File( tempDir.getAbsolutePath( ) + "/" + loName + "_unsigned.jar" ).delete( );
            writeWebPage( tempDir.getAbsolutePath( ), loName, windowed, "es.eucm.eadventure.engine.EAdventureApplet" );

            File webpage = new File( tempDir.getAbsolutePath( ) + "/" + loName + ".html" );
            webpage.copyTo( new File(tempDir.getAbsolutePath( ) + "/CMD_6988980_M/my_files/" + fixedLoName + ".html" ) );
            webpage.delete( );
            
            //copy loadingImage
            File splashScreen = new File( "web/splashScreen.gif" );
            if ( windowed ){
                splashScreen = new File( "web/splashScreen_red.gif");
            }
            splashScreen.copyTo( new File( tempDir.getAbsolutePath( ) + "/CMD_6988980_M/my_files/splashScreen.gif" ) );

            File.zipDirectory( tempDir.getAbsolutePath( ) + "/", zipFilename );
        }
        catch( Exception e ) {
            return false;
        }

        return true;
    }
}
