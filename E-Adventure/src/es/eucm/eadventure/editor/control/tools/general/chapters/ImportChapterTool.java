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
package es.eucm.eadventure.editor.control.tools.general.chapters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;

import es.eucm.eadventure.common.auxiliar.AllElementsWithAssets;
import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.PlayAnimationEffect;
import es.eucm.eadventure.common.data.chapter.effects.PlaySoundEffect;
import es.eucm.eadventure.common.data.chapter.effects.ShowTextEffect;
import es.eucm.eadventure.common.data.chapter.effects.SpeakCharEffect;
import es.eucm.eadventure.common.data.chapter.effects.SpeakPlayerEffect;
import es.eucm.eadventure.common.data.chapter.elements.Description;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.common.loader.InputStreamCreator;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.common.loader.parsers.ChapterHandler;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EditorImageLoader;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.control.writer.AnimationWriter;


public class ImportChapterTool extends Tool {

    private Controller controller;
    
    private ChapterListDataControl chaptersController;
    
    private int index;
    
    private HashMap<String,String> resourcesAdded;
    
    private int numberFilesChanged;
    
    
    private static final InputStreamCreator isCreator = new InputStreamCreator(){

        public InputStream buildInputStream( String filePath ) {
            try {
                return new FileInputStream( filePath );
            }
            catch( FileNotFoundException e ) {
                e.printStackTrace();
                return null;
            }
        }

        public URL buildURL( String path ) {
            try {
                return new File (path).toURI( ).toURL( );
            }
            catch( MalformedURLException e ) {
                e.printStackTrace();
                return null;
            }
        }

        public String[] listNames( String filePath ) {
            return null;
        }
        
    }; 
// end of isCreator definition
    
    public ImportChapterTool(ChapterListDataControl cldc){
        controller = Controller.getInstance();
        chaptersController = cldc;
        resourcesAdded = new HashMap<String,String>();
        numberFilesChanged = 0;
        
    }
    
    @Override
    public boolean canRedo( ) {
        return false;
    }

    @Override
    public boolean canUndo( ) {
        return true;
    }

    @Override
    public boolean combine( Tool other ) {
        return false;
    }

    @Override
    public boolean doTool( ) {
        
        //select the XML for the chapter
        java.io.File selectedChaperXML = controller.selectXMLChapterFile( );
        
        if (selectedChaperXML!=null){
            if (controller.showStrictConfirmDialog(TC.get( "ImportChapter.Notice.Title" ), TC.get( "ImportChapter.Notice.Message"))) {
            controller.showLoadingScreen( TC.get("LoadingScreen.ImportChapter.Message"));
            // parse chapter
            Chapter importedChapter = parseChapter(selectedChaperXML);
            // if importedChapter is null, something went wrong during parsing. 
            if (importedChapter !=null){
            
            //if there are not assets in the parsed XML, the xml is not chapter file
            if (AllElementsWithAssets.getAllAssets( ).size( ) > 0){
            //move all the assets used in the imported chapter to the project path
                getAllFiles(selectedChaperXML);
                // the title of the chapter is stored in the descriptor.xml file       
                importedChapter.setTitle( TC.get("ImportChapter.ChapterDefault.Title") );
        
                // ADD chapter to the current data model
                chaptersController.addChapterDataControl( importedChapter);
                index = chaptersController.getSelectedChapter( );
                  controller.reloadData( );
                  controller.hideLoadingScreen();
                  if (numberFilesChanged>0)
                      controller.showInformationDialog(TC.get( "ImportChapter.ModifyFilesMessage.Title" ), TC.get( "ImportChapter.ModifyFilesMessage.Message", Integer.toString( numberFilesChanged  )) );
                  controller.showInformationDialog(TC.get( "ImportChapter.ImportationOK.Title" ), TC.get( "ImportChapter.ImportationOK.Message"));
                  return true;
            }
            controller.hideLoadingScreen();
            controller.showInformationDialog(TC.get( "ImportChapter.BadFile.Title" ), TC.get( "ImportChapter.BadFile.Message"));
            return false;
         }// if importedChapter is null
            controller.hideLoadingScreen();
            controller.showInformationDialog(TC.get( "ImportChapter.UnknowProblems.Title" ), TC.get( "ImportChapter.UnknowProblems.Message" )); 
            return false;
            }
        }
        // the user cancel the importation process
        return false;
    }
    
    /**
     * Parse the selected chapter XML
     * 
     * @param selectedChaperXML
     * @return
     */
    private Chapter parseChapter(java.io.File selectedChaperXML){
        Chapter importedChapter = new Chapter();
        if( selectedChaperXML!=null && selectedChaperXML.exists( ) ) {
            try {
                
            InputStream chapterIS = null;
            chapterIS = new FileInputStream( selectedChaperXML );

                // Open the file and load the data
            
                if( chapterIS != null ) {
                    // set AllElementsWithAssets to store
                    AllElementsWithAssets.resetAllAssets( );
                    AllElementsWithAssets.setStorePath( true );
                    // Set the chapter handler with a new created chapter; 
                    ChapterHandler chapterParser = new ChapterHandler( isCreator,importedChapter );

                    Loader.getFactory( ).setValidating( false );
                    
                    SAXParser saxParser = Loader.getFactory( ).newSAXParser( );

                    // Parse the data and close the data
                    saxParser.parse( chapterIS, chapterParser );
                    chapterIS.close( );
                }

            }
            catch( ParserConfigurationException e ) {
                //TODO mostrar mensaje de error significativo
                e.getStackTrace( );
                controller.showInformationDialog(TC.get( "ImportChapter.ProblemsParsing.Title" ), TC.get( "ImportChapter.ProblemsParsing.Message" )); 
                importedChapter = null;
            }
            catch( SAXException e ) {
                //TODO mostrar mensaje de error significativo
                e.getStackTrace( );
                controller.showInformationDialog(TC.get( "ImportChapter.ProblemsParsing.Title" ), TC.get( "ImportChapter.ProblemsParsing.Message" ));
                importedChapter = null;
            }
            catch( IOException e ) {
                //TODO mostrar mensaje de error significativo
                e.getStackTrace( );
                controller.showInformationDialog(TC.get( "ImportChapter.ProblemsParsing.Title" ), TC.get( "ImportChapter.ProblemsParsing.Message" )); 
                importedChapter = null;
            }
        }
        return importedChapter;
    }
    
    /**
     * Copy all the elements from chapter's folder to the current project's folder
     * 
     * @param selectedFile
     *              the xmlFile selected
     */
    private void getAllFiles(java.io.File selectedFile){
        String xmlPath = selectedFile.getAbsolutePath( ).substring( 0, selectedFile.getAbsolutePath( ).indexOf( selectedFile.getName( )  ));
        String oldPath=null;
        String newPath = null;
        String newBasePath = controller.getProjectFolder( );
        // iterate all the elements with resources in the imported chapter
        Iterator itr = AllElementsWithAssets.getAllAssets( ).iterator();
        while (itr.hasNext()) {
            
            Object o = itr.next();
            //identify the kind of the object
            
            // Get all the files for Resources objects in the chapter (in the AllElementsWithAssets structure the hashmap with all
            // resources for the Resources are added)
            if (o instanceof HashMap){
                Iterator it = ((HashMap)o).entrySet( ).iterator( );
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry)it.next( );
                    newPath = manageResourcesAssets((String)entry.getValue( ), newBasePath, xmlPath);
                    // set the path in the all resource (the name could be changed)
                    entry.setValue( newPath );
                  }
            // Get audio resources in conversation lines
            } else if (o instanceof ConversationLine){
                oldPath = ((ConversationLine)o).getAudioPath( );
                // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                newPath = copyFile(newBasePath,oldPath,xmlPath);
                ((ConversationLine)o).setAudioPath( newPath );
            }
            // Get  audio or animation resources in effects
            else if (o instanceof AbstractEffect){
                if (((AbstractEffect)o).getType( ) == Effect.PLAY_SOUND){
                    oldPath = ((PlaySoundEffect)o).getPath( );
                    if (oldPath!=null &!oldPath.equals( "" )){
                        // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                        newPath = copyFile(newBasePath,oldPath,xmlPath);
                        ((PlaySoundEffect)o).setPath( newPath );
                    }
                }else if (((AbstractEffect)o).getType( ) == Effect.PLAY_ANIMATION){
                    oldPath = ((PlayAnimationEffect)o).getPath( );
                    if (oldPath!=null &!oldPath.equals( "" )){
                        // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                        newPath = copyFile(newBasePath,oldPath,xmlPath);
                        ((PlayAnimationEffect)o).setPath( newPath );
                    }
                }else if (((AbstractEffect)o).getType( ) == Effect.SPEAK_CHAR){
                    oldPath = ((SpeakCharEffect)o).getAudioPath( );
                    if (oldPath!=null &!oldPath.equals( "" )){
                        // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                        newPath = copyFile(newBasePath,oldPath,xmlPath);
                        ((SpeakCharEffect)o).setAudioPath( newPath );
                    }
                }else if (((AbstractEffect)o).getType( ) == Effect.SPEAK_PLAYER){
                    oldPath = ((SpeakPlayerEffect)o).getAudioPath( );
                    if (oldPath!=null &!oldPath.equals( "" )){
                        // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                        newPath = copyFile(newBasePath,oldPath,xmlPath);
                        ((SpeakPlayerEffect)o).setAudioPath( newPath );
                    }
                }else if (((AbstractEffect)o).getType( ) == Effect.SHOW_TEXT){
                    oldPath = ((ShowTextEffect)o).getAudioPath( );
                    if (oldPath!=null &!oldPath.equals( "" )){
                        // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                        newPath = copyFile(newBasePath,oldPath,xmlPath);
                        ((ShowTextEffect)o).setAudioPath( newPath );
                    }
                }
            } 
            // Get the path for html files in books
            else if (o instanceof BookPage){
                oldPath = ((BookPage)o).getUri( );
             // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                newPath = copyFile(newBasePath,oldPath,xmlPath);
                // look for the folder with the assets for the HTML
                // oldPath is used because the names inside the HTML won't be changed (images in html will be copied
                // with the same name to the destiny folder)
                // TODO change names inside HTML when it is necessary
                String originExtension="";
                String htmlWithoutExtension = oldPath.substring( 0 , oldPath.lastIndexOf( "." ));
                
                // if the book page is resource, check the folder with images for the html
                if (((BookPage)o).getType( ) == BookPage.TYPE_RESOURCE){
                if (new File(xmlPath + htmlWithoutExtension  + "_files").exists( ))
                    originExtension = "_files";
                else if (new File(xmlPath + htmlWithoutExtension + "_archivos").exists( ))
                    originExtension = "_archivos";
                else
                    controller.showInformationDialog(TC.get("ImportChapter.NoFolderForHTMLImages.Title"), TC.get( "ImportChapter.NoFolderForHTMLImages.Message",xmlPath + oldPath  ));
                
                if (!originExtension.equals( "" )){
                    File destinyFolder = new File(newBasePath +"/" + htmlWithoutExtension + originExtension);
                    File originFolder = new File(xmlPath + "/" + htmlWithoutExtension + originExtension);
                    if (!destinyFolder.exists( ))
                        destinyFolder.mkdir( );
                    String[] filesToCopy = originFolder.list( );
                    for (int i=0; i<filesToCopy.length;i++){
                        if (!File.copyTo( new File(originFolder, filesToCopy[i]), new File(destinyFolder, filesToCopy[i]))){
                            //TODO mensaje de error
                            System.out.println("something wrong happens");
                        }
                    }
                }
                }
                
                
                ((BookPage)o).setUri( newPath );
            }
            // Get the path of an image in image paragraphs in books
            else if (o instanceof BookParagraph){
                oldPath = ((BookParagraph)o).getContent( );
             // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                newPath = copyFile(newBasePath,oldPath,xmlPath);
                ((BookParagraph)o).setContent( newPath );
            }
            // Get the path of an image in image paragraphs in books
            else if (o instanceof Description){
                Description d = (Description)o;
                if (d.getDescriptionSoundPath( )!=null && !d.getDescriptionSoundPath( ).equals( "" )){
                    d.setDescriptionSoundPath( copyFile(newBasePath,d.getDescriptionSoundPath( ),xmlPath) );
                }
                if (d.getDetailedDescriptionSoundPath( )!=null && !d.getDetailedDescriptionSoundPath( ).equals( "" )){
                    d.setDetailedDescriptionSoundPath( copyFile(newBasePath,d.getDetailedDescriptionSoundPath( ),xmlPath) );
                }
                if (d.getNameSoundPath( )!=null && !d.getNameSoundPath( ).equals( "" )){
                    d.setNameSoundPath( copyFile(newBasePath,d.getNameSoundPath( ),xmlPath) );
                }
            }
            else if (o instanceof ExitLook){
                ExitLook e = (ExitLook)o;
                if (e.getCursorPath( )!=null && !e.getCursorPath( ).equals( "" )){
                    e.setCursorPath( copyFile(newBasePath,e.getCursorPath( ),xmlPath) );
                }
                if (e.getSoundPath( )!=null&& !e.getSoundPath( ).equals( "" )){
                    e.setSoundPath( copyFile(newBasePath,e.getSoundPath( ),xmlPath) );
                }
            }
             
        }
        
        //Set AllElementsWithAssets to don't store and clear the stored elements
        AllElementsWithAssets.setStorePath( false );
        AllElementsWithAssets.resetAllAssets( );
    }
    
    /**
     * Manage the assets for 
     * 
     * 
     * @param oldPath
     * @param newBasePath
     * @param xmlPath
     * @return
     */
    private String manageResourcesAssets(String oldPath, String newBasePath, String xmlPath){
        
        String newPath = "";
        // extract the full path without the file name and the name of the asset
           String[] oldPathSplit = oldPath.split( "/" );
           String fileName = oldPathSplit[oldPathSplit.length-1]; 
           
           // if the selected file is .eaa file, all the assets referenced in the .eaa file must be copied too
           if (oldPath.endsWith( ".eaa" )){
               
               Animation animation = Loader.loadAnimation( isCreator, xmlPath + oldPath , new EditorImageLoader() );
               
               //Iterate the list of frames copying all the resources (both images and sound files, if they exist)
               // and changing the name if it was necessary
               for( Frame f : animation.getFrames( ) ) {
                   if( f.getUri( ) != null && !f.getUri( ).equals( "" )) {
                      f.setUri( copyFile(newBasePath,f.getUri( ),xmlPath) );
                   }
                   if( f.getSoundUri( ) != null && !f.getSoundUri( ).equals("") ) {
                       f.setSoundUri( copyFile(newBasePath,f.getSoundUri( ),xmlPath) );
                   }
               }
               
               // Once all the animation's resources has been copied, write again the Animation cause the name of some of its resources
               // may have changed.
                  // first, copy the .eaa file because it would be necessary to change its name
                  newPath =  copyFile(newBasePath,oldPath,xmlPath);
                  // second, overwrite this file to ensure that all the inner resources paths are correct
                  AnimationWriter.writeAnimation( newPath, animation );
           } else 
               // if the file hasn't sufix is because it's a file with _01 patter (old eAd animations), look for the file with .png or .jpg extension
               // Repeat the process for _0X files 
               if (!fileName.contains( "." )){
                   String oldPathWithoutName = oldPath.substring( 0, oldPath.indexOf( fileName ) );
                   String tempPath = xmlPath + oldPathWithoutName + fileName;
                   String extension = "";
                   // check the extension: .jpg or .png
                   // the right side of the or is added for EmptyImage or empty Icon
                   if (new File(tempPath + "_01.jpg").exists( ) || new File(tempPath +".jpg").exists( ))
                       extension = ".jpg";
                   else if (new File(tempPath + "_01.png").exists( ) || new File(tempPath +".png").exists( ))
                       extension = ".png";
                
                   int i = 2;
                   //look for all the frames of the old eAd animation
                   String fullPath = tempPath +"_01" + extension;
                   
                   if (new File(fullPath).exists( )){
                       boolean moreFrames = true;
                       while (moreFrames){
                       
                           newPath = copyFile(newBasePath, oldPathWithoutName + fullPath.substring( fullPath.indexOf( fileName ), fullPath.length( ) ),xmlPath);
                           if (i<9)
                               fullPath = tempPath +"_0" + i + extension;
                           else 
                               fullPath = tempPath +"_" + i + extension;
                           i++;
                           moreFrames = new File(fullPath).exists( );
                       }
                   } else {
                       newPath = copyFile(newBasePath, oldPathWithoutName + fullPath.substring( fullPath.indexOf( fileName ), fullPath.length( ) ),xmlPath);
                   }
                   //in the path to be set for the resource mustn't appear the _0X suffix
                   newPath = newPath.substring( 0, newPath.lastIndexOf( "_" ) );
                   
           }
           // For the other kinds of resources
           else {
            // extract the new path taking the folder and the name of the oldPath and adding the current project folder
               newPath = copyFile(newBasePath,oldPath,xmlPath);
           }
           
           return newPath;
        
    }
    
    
    /**
     * This method creates the new path for the file to be copied changing the file name if it already exist in the target project.
     * After that, copy the file. 
     * 
     * @param newBasePath
     *          The path of the current project folder
     * @param oldPath
     *          The path of the asset to be copied (relative to the project)
     * @param xmlPath
     *          The path of the project from where the chapter is being imported (absolute)
     * @return
     *       The path to be set (sometimes the name of the assets change)
     */
    private String copyFile(String newBasePath, String oldPath, String xmlPath ){
     // the assets can be referenced more than one time in the xml file, check if the asset is already added
        if (resourcesAdded.get( oldPath ) == null){
            
        // extract the new path for the file taking the folder and the name of the oldPath and adding the current project folder
        String newPath = newBasePath + "\\" + oldPath;
        java.io.File newPathFile = new File(newPath);
        // extract the full path without the file name and the name of the asset
        String fileName = oldPath.substring( oldPath.lastIndexOf( "/" ) ); 
        String oldPathWithoutName = oldPath.substring( 0, oldPath.indexOf( fileName ) );
        String fullPathWithoutName = newBasePath + "/" + oldPathWithoutName;
        
        //If the file contains assets/special* and exists, don't copy it!!
        if ( !oldPath.contains( "assets/special" ) || !newPathFile.exists( )  ){
        // check if the file to be copied already exits in destiny folder 
        int i=0;
        boolean firstTime = true;
      //if the file is an old eAd animation, store the suffix
        String suffix = "";
        boolean isOldAnimation=false;
       // try {
        if (Character.isDigit( fileName.charAt( fileName.lastIndexOf( "_") + 1 )) &&  
                Character.isDigit( fileName.charAt( fileName.lastIndexOf( "_") + 2 )) && fileName.charAt( fileName.lastIndexOf( "_") + 3 )=='.' ){
            suffix = fileName.substring(fileName.lastIndexOf( "_" ), fileName.lastIndexOf( "." ));
            isOldAnimation = true;
        }
        
        while (newPathFile.exists( )){
            
            // if it exists, add a import suffix until the file doesn't exist
            if (fileName.contains( "impEad" )){
                String[] splitFileName = fileName.split( "impEad" );
                // remove the old _0X and add it before _impEad
                //quit the number of the impEad 
                splitFileName[1] = splitFileName[1].substring( splitFileName[1].lastIndexOf( "." ));
                fileName = splitFileName[0] + "impEad" + i + suffix + splitFileName[1]; 
            } else {
                String[] splitFileName = fileName.split( "\\." );
                // is it's an old animation, remove the old _0X and add it before _impEad
                if (isOldAnimation){
                    splitFileName[0] = splitFileName[0].substring( 0, splitFileName[0].lastIndexOf( "_" ));
                }
                fileName = splitFileName[0] + "_impEad" + i + suffix + "." + splitFileName[1];
            }
            
            if (firstTime)
                numberFilesChanged++;
            firstTime = false;
            
            newPath = fullPathWithoutName + fileName; 
            newPathFile = new File(newPath);
            i++;
       
        }
        
            if (!File.copyTo( new File(xmlPath+oldPath), new File(newPath)))
                controller.showInformationDialog(TC.get( "ImportChapter.ProblemsCopying.Title" ), TC.get( "ImportChapter.ProblemsCopying.Message",xmlPath+oldPath  ));
        }
        
        // store for the oldPath key the newPath to change future appearances of oldPath for other resources in the chapter 
        resourcesAdded.put( oldPath, oldPathWithoutName + fileName  );
       
        return oldPathWithoutName + fileName;
        }
       
        return resourcesAdded.get( oldPath );
    }
    

    @Override
    public boolean redoTool( ) {

        
        return false;
    }

    @Override
    public boolean undoTool( ) {

            //TODO notice that files can not be deleted after undo importation
            boolean done = ( chaptersController.removeChapterDataControl( index ) ) != null;
            controller.reloadData( );
            return done;

    }

}
