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

package es.eucm.eadventure.editor.control.tools.general.chapters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
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
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.PlayAnimationEffect;
import es.eucm.eadventure.common.data.chapter.effects.PlaySoundEffect;
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
    
    private HashSet<String> resourcesAdded;
    
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
        resourcesAdded = new HashSet<String>();
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
        //TODO warning de que el capítulo a importar puede que no sea vañido
        controller.showLoadingScreen( "IMPORTANDO!!!" );
        // parse chapter
        Chapter importedChapter = parseChapter(selectedChaperXML);
        //if there are not assets in the parsed XML, the xml is not chapter file
        if (AllElementsWithAssets.getAllAssets( ).size( ) > 0){
        //move all the assets used in the imported chapter to the project path
        getAllFiles(selectedChaperXML);
        // the title of the chapter is stored in the descriptor.xml file
       
        // TODO INTERNACIONALIZAR!!!!!!!!!!!!!!!!!
        importedChapter.setTitle( " title " );
        
        // ADD chapter to the current data model TODO añadir título!!!!!
        chaptersController.addChapterDataControl( importedChapter );
        index = chaptersController.getSelectedChapter( );
        controller.reloadData( );
        controller.hideLoadingScreen();
        if (numberFilesChanged>0)
            controller.showInformationDialog("Archivos modificados", "Se han modificado " + numberFilesChanged +  " ficheros");
        controller.showInformationDialog("OK", "el capítulo se ha importado correctamente");
        return true;
        }
     // TODO informar de que el fichero xml no es de capitulo
        controller.hideLoadingScreen();
        controller.showInformationDialog("fichero no valido", "el fichero importado no es válido");
        return false;
        }
     // TODO informar de que no se ha seleccionado bien
      //  controller.hideLoadingScreen();
        //controller.showInformationDialog("fichero no valido", "el fichero importado no es válido");
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
                    AllElementsWithAssets.setStorePath( true );
                    // Set the chapter handler with a new created chapter; 
                    ChapterHandler chapterParser = new ChapterHandler( isCreator,importedChapter );

                    Loader.getFactory( ).setValidating( true );
                    
                    SAXParser saxParser = Loader.getFactory( ).newSAXParser( );

                    // Parse the data and close the data
                    saxParser.parse( chapterIS, chapterParser );
                    chapterIS.close( );
                }

            }
            catch( ParserConfigurationException e ) {
                //TODO mostrar mensaje de error significativo
            }
            catch( SAXException e ) {
                //TODO mostrar mensaje de error significativo
            }
            catch( IOException e ) {
                //TODO mostrar mensaje de error significativo
            }
        }
        return importedChapter;
    }
    
    /**
     * Copy all the elements from chapter's folder to the current project's folder
     * 
     * @param selectedFile
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
            // resources for the Resources is added)
            if (o instanceof HashMap){
                Iterator it = ((HashMap)o).entrySet( ).iterator( );
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry)it.next( );
                    oldPath = (String)entry.getValue( );
                    
                    // if the selected file is .eaa file, all the assets referenced in the .eaa file must be copied too
                    if (oldPath.endsWith( ".eaa" )){
                        
                        Animation animation = Loader.loadAnimation( isCreator, xmlPath + oldPath , new EditorImageLoader() );
                        
                        //Iterate the list of frames copying all the resources (both images and sound files, if they exist)
                        // and changing the name if it was necessary
                        for( Frame f : animation.getFrames( ) ) {
                            if( f.getUri( ) != null ) {
                               f.setUri( copyFile(newBasePath,f.getUri( ),xmlPath) );
                            }
                            if( f.getSoundUri( ) != null && f.getSoundUri( )!="" ) {
                                f.setSoundUri( copyFile(newBasePath,f.getSoundUri( ),xmlPath) );
                            }
                        }
                        
                        // Once all the animation's resources has been copied, write again the Animation cause the name of some of its resources
                        // may have changed.
                           // first, copy the .eaa file because it would be necessary to change its name
                           newPath =  copyFile(newBasePath,oldPath,xmlPath);
                           // second, overwrite this file to ensure that all the inner resources paths are correct
                           AnimationWriter.writeAnimation( newPath, animation );
                    } 
                    // For the other kinds of resources
                    else {
                     // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                        newPath = copyFile(newBasePath,oldPath,xmlPath);
                        
                    }
                    
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
                    // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                    newPath = copyFile(newBasePath,oldPath,xmlPath);
                    ((PlaySoundEffect)o).setPath( newPath );
                }else if (((AbstractEffect)o).getType( ) == Effect.PLAY_ANIMATION){
                    oldPath = ((PlayAnimationEffect)o).getPath( );
                    // extract the new path taking the folder and the name of the oldPath and adding the current project folder
                    newPath = copyFile(newBasePath,oldPath,xmlPath);
                    ((PlayAnimationEffect)o).setPath( newPath );
                }
            }
             
        }
        
        //Set AllElementsWithAssets to don't store and clear the stored elements
        AllElementsWithAssets.setStorePath( false );
        AllElementsWithAssets.resetAllAssets( );
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
        if (!resourcesAdded.contains( oldPath )){
            resourcesAdded.add( oldPath );
        
        
        // extract the new path for the file taking the folder and the name of the oldPath and adding the current project folder
        String newPath = newBasePath + "\\" + oldPath;
        java.io.File newPathFile = new File(newPath);
        // extract the full path without the file name and the name of the asset
        String[] oldPathSplit = oldPath.split( "/" );
        String fileName = oldPathSplit[oldPathSplit.length-1]; 
        String oldPathWithoutName = oldPath.substring( 0, oldPath.indexOf( fileName ) );
        String fullPathWithoutName = newBasePath + "/" + oldPathWithoutName;
        
        //If the file contains assets/special* and exists, don't copy it!!
        if ( !oldPath.contains( "assets/special" ) || !newPathFile.exists( )  ){
        
            
        // check if the file to be copied already exits in destiny folder 
        int i=0;
        boolean firstTime = true;
        while (newPathFile.exists( )){
            // if it exists, add a prefix until the file doesn't exist
            if (fileName.contains( "impEad" )){
                String[] splitFileName = fileName.split( "impEad" );
                fileName = splitFileName[0] + "impEad" +i + splitFileName[1].substring( 1 ); // quit the digit
            } else {
                String[] splitFileName = fileName.split( "\\." );
                // it is possible that some resources hasn't "." in the path (e.g., animation_01)
                String extension = splitFileName.length==1?"":"." + splitFileName[1];
                fileName = splitFileName[0] + "_impEad" + i + extension;
            }
            
            if (firstTime)
                numberFilesChanged++;
            firstTime = false;
            
            newPath = fullPathWithoutName + fileName; 
            newPathFile = new File(newPath);
            i++;
        }
        
            if (!File.copyTo( new File(xmlPath+oldPath), new File(newPath)))
                controller.showInformationDialog("no se pudo copiar fichero", "el fichero " + xmlPath+oldPath + " no se pudo copiar");
        }
       
        return oldPathWithoutName + fileName;
        }
        
        return oldPath;
    }
    

    @Override
    public boolean redoTool( ) {

        
        return false;
    }

    @Override
    public boolean undoTool( ) {
      //  if (successfullyImported){
            //TODO añadir cartelito indicando que los archivos copiados no se borraran, que  vayan a la opción de liberar asstes para hacer eso
            boolean done = ( chaptersController.removeChapterDataControl( index ) ) != null;
            controller.reloadData( );
            return done;
        //} else
          //      return true;
    }

}
