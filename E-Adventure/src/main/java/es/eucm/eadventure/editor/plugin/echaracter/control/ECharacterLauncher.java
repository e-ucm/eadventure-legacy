package es.eucm.eadventure.editor.plugin.echaracter.control;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.EditorImageLoader;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.writer.AnimationWriter;
import es.eucm.echaracter.api.Callback;
import es.eucm.echaracter.api.eCharacter;


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

public class ECharacterLauncher implements es.eucm.echaracter.api.Callback{

    private static boolean isRunning = false;
    
    private String exportPath;
    private Properties p;
    private HashMap<String,String> resourcesAdded;
    private int numberFilesChanged;
    private ResourcesDataControl dataControl;
    private Resources resources;
    
    private JButton source;
    
    private static int numberOfTimesUsed;
    private static int numberOfTimesCompleted;
    private static int numberOfTimesFailed;
    private static boolean hasClickedOnForm;
    private static int numberOfRemindersDismissed;
    private static String formURL;
    
    private static boolean init = false;
    
    public static boolean isInit() {
        return init;
    }

    public static void save() {

        Properties properties = new Properties();
        properties.put( "times-used", ""+numberOfTimesUsed );
        properties.put( "times-completed", ""+numberOfTimesCompleted );
        properties.put( "times-failed", ""+numberOfTimesFailed );
        properties.put( "reminders-dismissed", ""+numberOfRemindersDismissed );
        properties.put( "clicked-form", ""+hasClickedOnForm );
        if (formURL!=null){
            properties.put( "form-url", formURL );
        }
        try {
            properties.store( new FileOutputStream(new File("echaracter.config")), "eCharacter config params" );
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static void init() {
        if (!isInit()) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream("echaracter.config"));
                
                try {
                    numberOfTimesUsed = Integer.parseInt( properties.getProperty("times-used") );
                    numberOfTimesCompleted = Integer.parseInt( properties.getProperty("times-completed") );
                    numberOfTimesFailed = Integer.parseInt( properties.getProperty("times-failed") );
                    numberOfRemindersDismissed = Integer.parseInt( properties.getProperty("reminders-dismissed") );
                    hasClickedOnForm = Boolean.parseBoolean( properties.getProperty("clicked-form") );
                    formURL = properties.getProperty( "form-url" );
                } catch (Exception e){
                    numberOfRemindersDismissed = numberOfTimesUsed = numberOfTimesCompleted = numberOfTimesFailed = 0;
                    hasClickedOnForm=false;
                }
                
                // Check consistency
                if (numberOfTimesUsed  != numberOfTimesCompleted + numberOfTimesFailed || 
                        numberOfTimesUsed<0 || numberOfTimesCompleted<0 || numberOfTimesFailed<0){
                    numberOfRemindersDismissed = numberOfTimesUsed = numberOfTimesCompleted = numberOfTimesFailed = 0;
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                numberOfRemindersDismissed = numberOfTimesUsed = numberOfTimesCompleted = numberOfTimesFailed = 0;
                hasClickedOnForm=false;
            } 
            
            save();
            init=true;
        }
    }
    
    public static boolean isAvailable(){
        return !isRunning;
    }
    
    private void setRunning( boolean running ){
        isRunning = running;
        if (source!=null)
            source.setEnabled( isAvailable() );
    }
    
    
    /*private static File testDir = new File("C:\\Users\\Javier Torrente\\eCharacter");
    private static int fileCount=-1;*/
    
    public ECharacterLauncher(ResourcesDataControl dataControl){
        if (!isInit()){
            init();
        }
        
        //fileCount=(fileCount+1)%3;
        
        this.dataControl=dataControl;
         resources= (Resources)dataControl.getContent( );
        resourcesAdded = new HashMap<String,String>();
        numberFilesChanged = 0;
        
        p = new Properties();
        exportPath=null;
        String language = Controller.getInstance( ).getLanguage( );
        if (language.equals( "es_ES" ) || language.equals( "en_EN" )){
            p.put("lang", language);    
        }
        
        exportPath = createTempDir().getAbsolutePath( );
        new File(exportPath).mkdir( );
        p.put( "defaultExportPath", exportPath);
        
        p.put( "defaultCamera", "Front|Back|Left|Right" );
        p.put( "defaultAnimations", "##all##" );
        p.put( "defaultQuality", "3" );

    }
    
    public static void main (String[]args){
        test();
    }
    
    public static void test(){
        try {
            Callback inv = new ECharacterLauncher(null);
            Properties p = new Properties();
            p.put("lang", "fr_FR");
            p.put("defaultFamily","assets"+File.separator+"Families"+File.separator+"eAdventure.xml");
            p.put("defaultModel","assets"+File.separator+"Families"+File.separator+"eAdventure"
                    +File.separator+"XML models"+File.separator+"YoungBoy.xml");
            p.put("defaultStage","idMultiStageLabel6");
            p.put("defaultCamera","idCameraLabel2");
            p.put("defaultAnimations","Coger|Andar");
            p.put("defaultQuality","6");
            eCharacter eC = new eCharacter();
            eC.eCharacter(p,inv);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }        
    }
    
    private static final int TEMP_DIR_ATTEMPTS = 10000;
    
    public static File createTempDir() {
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        String baseName = System.currentTimeMillis() + "-";

        for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {
          File tempDir = new File(baseDir, baseName + counter);
          if (tempDir.mkdir()) {
            return tempDir;
          }
        }
        throw new IllegalStateException("Failed to create directory within "
            + TEMP_DIR_ATTEMPTS + " attempts (tried "
            + baseName + "0 to " + baseName + (TEMP_DIR_ATTEMPTS - 1) + ')');
      }
    
    public void launch(JButton button){
        if (isRunning) return;
        this.source = button;
        try {
            
            setRunning(true);
            eCharacter eC = new eCharacter();
            eC.eCharacter(p,this);
            
            /*File source = new File(testDir, "eCharacter"+(fileCount+1)+".zip");
            File target = new File(exportPath, "eCharacter.zip");
            es.eucm.eadventure.common.auxiliar.File.copyTo( source, target );
            Thread.sleep( 5000 );
            exportSuccess();*/
            
            numberOfTimesUsed++;
            save();
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            setRunning(false);
        }
    }
    
    private void showEvaluationReminder(){
        if (Desktop.isDesktopSupported() && numberOfTimesUsed>0 && numberOfTimesUsed%3==0 && formURL!=null && !hasClickedOnForm && numberOfRemindersDismissed<3){
            if (Controller.getInstance( ).showStrictConfirmDialog( TC.get( "ECharacter.EvaluationReminder.Title" ), TC.get( "ECharacter.EvaluationReminder.Message" )+ " "+formURL )){
                hasClickedOnForm=true;
                try {
                    Desktop.getDesktop().browse(new URI(formURL));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            } else {
                numberOfRemindersDismissed++;
            }
        }
        save();
    }
    
    public void exportFailed( ) {
        Controller.getInstance( ).showInformationDialog( TC.get( "ECharacter.Error.Title" ), TC.get( "ECharacter.Error.Message" ) );
        setRunning(false);
        numberOfTimesFailed++;
        save();
        showEvaluationReminder();
    }

    public void exportSuccess( ) {
        //exportPath = "C:\\Users\\Javier Torrente\\eCharacter\\export\\cbx.zip";
        File target;
          //target = createTempDir();
          //target.mkdir( );
          File target2 = new File(exportPath,"/assets/animation/");
          System.out.println("TARGET 2="+target2.getAbsolutePath( ) );
          target2.mkdirs( );
          es.eucm.eadventure.common.auxiliar.File.unzipDir( new File(exportPath, "eCharacter.zip").getAbsolutePath( ), target2.getAbsolutePath( ) );
          new File(exportPath, "eCharacter.zip").delete( );
          for (File child:target2.listFiles( )){
              String relativePath=child.getName( );
              manageResourcesAssets(relativePath, Controller.getInstance( ).getProjectFolder( ), exportPath);
          }
        /*File dir=new File("C:\\Users\\Javier Torrente\\eCharacter\\export\\eCharacter\\assets\\animation");
        File dir2=new File("C:\\Users\\Javier Torrente\\eCharacter\\export\\eCharacter");
        for (File file: dir.listFiles( )){
            String relativePath=file.getName( );
            manageResourcesAssets(relativePath, Controller.getInstance( ).getProjectFolder( ), dir2.getAbsolutePath( ));
        }*/
          
          for (String importedFile : this.resourcesAdded.values( )){
              if (importedFile.toLowerCase( ).endsWith( "eaa" )){
                  //Talk to animations
                  if (importedFile.toLowerCase( ).contains( "hablar" ) || importedFile.toLowerCase( ).contains( "talk" )){
                      // Up
                      if (importedFile.toLowerCase( ).contains( "front" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_SPEAK_UP, (importedFile.contains( "assets/animation" )?"":"/assets/animation/")+importedFile );
                      } 
                      // Down
                      else if (importedFile.toLowerCase( ).contains( "back" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_SPEAK_DOWN, (importedFile.contains( "assets/animation" )?"":"/assets/animation/")+importedFile );
                      } 
                      //Left
                      else if (importedFile.toLowerCase( ).contains( "left" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_SPEAK_LEFT, (importedFile.contains( "assets/animation" )?"":"/assets/animation/")+importedFile );
                      } 
                      //Right
                      else if (importedFile.toLowerCase( ).contains( "right" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_SPEAK_RIGHT, (importedFile.contains( "assets/animation" )?"":"/assets/animation/")+importedFile );
                      }
                  } 
                  //Standing animations
                  else if (importedFile.toLowerCase( ).contains( "stand" ) || importedFile.toLowerCase( ).contains( "parado" ) || importedFile.toLowerCase( ).contains( "idle" )){
                      // Up
                      if (importedFile.toLowerCase( ).contains( "front" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_STAND_UP, (importedFile.contains( "assets/animation" )?"":"assets/animation/")+importedFile );
                      } 
                      // Down
                      else if (importedFile.toLowerCase( ).contains( "back" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_STAND_DOWN, (importedFile.contains( "assets/animation" )?"":"assets/animation/")+importedFile );
                      } 
                      //Left
                      else if (importedFile.toLowerCase( ).contains( "left" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_STAND_LEFT, (importedFile.contains( "assets/animation" )?"":"assets/animation/")+importedFile );
                      } 
                      //Right
                      else if (importedFile.toLowerCase( ).contains( "right" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_STAND_RIGHT, (importedFile.contains( "assets/animation" )?"":"assets/animation/")+importedFile );
                      } 
                      
                  // Walk animations
                  } else if (importedFile.toLowerCase( ).contains( "andar" ) || importedFile.toLowerCase( ).contains( "walk" )){
                      // Up
                      if (importedFile.toLowerCase( ).contains( "front" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_WALK_UP, (importedFile.contains( "assets/animation" )?"":"assets/animation/")+importedFile );
                      } 
                      // Down
                      else if (importedFile.toLowerCase( ).contains( "back" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_WALK_DOWN, (importedFile.contains( "assets/animation" )?"":"assets/animation/")+importedFile );
                      } 
                      //Left
                      else if (importedFile.toLowerCase( ).contains( "left" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_WALK_LEFT, (importedFile.contains( "assets/animation" )?"":"assets/animation/")+importedFile );
                          
                      } 
                      //Right
                      else if (importedFile.toLowerCase( ).contains( "right" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_WALK_RIGHT, (importedFile.contains( "assets/animation" )?"":"assets/animation/")+importedFile );
                      }
                      
                  // Grab/Use animations
                  } else if (importedFile.toLowerCase( ).contains( "attack" ) || importedFile.toLowerCase( ).contains( "coger" ) || 
                          importedFile.toLowerCase( ).contains( "usar" ) || importedFile.toLowerCase( ).contains( "grab" ) || 
                          importedFile.toLowerCase( ).contains( "use" )){
                      // Up
                      if (importedFile.toLowerCase( ).contains( "front" )){
                      } 
                      // Down
                      else if (importedFile.toLowerCase( ).contains( "back" )){
                          
                      } 
                      //Left
                      else if (importedFile.toLowerCase( ).contains( "left" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_USE_LEFT, (importedFile.contains( "assets/animation" )?"":"assets/animation/")+importedFile );                              
                      } 
                      //Right
                      else if (importedFile.toLowerCase( ).contains( "right" )){
                          resources.addAsset( NPC.RESOURCE_TYPE_USE_RIGHT, (importedFile.contains( "assets/animation" )?"":"assets/animation/")+importedFile );
                      }                          
                  }
              }
          }
          
          setRunning(false);
          numberOfTimesCompleted++;
          
          save();
          
          Controller.getInstance( ).updatePanel( );
          showEvaluationReminder();
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
           
           // if the selected file is .eaa file, all the assets referenced in the .eaa file must be copied too
           if (oldPath.endsWith( ".eaa" )){
               String filename = getAbsoluteAnimationPath(xmlPath,oldPath);
               //String filename = xmlPath + "/assets/animation/"+oldPath;
               Animation animation = Loader.loadAnimation( AssetsController.getInputStreamCreator( ),  filename, new EditorImageLoader() );
               
               //Iterate the list of frames copying all the resources (both images and sound files, if they exist)
               // and changing the name if it was necessary
               for( Frame f : animation.getFrames( ) ) {
                   if( f.getUri( ) != null && !f.getUri( ).equals( "" )) {
                      f.setUri( copyFile(newBasePath,f.getUri( ),xmlPath) );
                   }
               }
               
               // Once all the animation's resources has been copied, write again the Animation cause the name of some of its resources
               // may have changed.
                  // first, copy the .eaa file because it would be necessary to change its name
                  newPath =  copyFile(newBasePath,"assets/animation/"+oldPath,xmlPath);
                  // second, overwrite this file to ensure that all the inner resources paths are correct
                  AnimationWriter.writeAnimation( newPath, animation );
           } 
           
           return newPath;
        
    }
    
    private String getAbsoluteAnimationPath(String sourceDir, String animationFileName){
        String filename = sourceDir;
        if (!filename.contains( "assets/animation" ) && !animationFileName.contains( "assets/animation" )){
            if (filename.endsWith( "/" )){
                filename = filename+"assets/animation";
            } else {
                filename = filename+"/assets/animation";
            }
        }
        
        if (!filename.endsWith( "/" ) && !animationFileName.startsWith( "/" )){
            filename = filename+"/"+animationFileName;
        } else {
            filename = filename+animationFileName;
        }
        return filename;
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
            //String newPath = newBasePath + (oldPath.contains( "assets/animation" )?"/":"/assets/animation/")+oldPath;
            String newPath = getAbsoluteAnimationPath ( newBasePath, oldPath);
            java.io.File newPathFile = new File(newPath);
            // extract the full path without the file name and the name of the asset
            String fileName = oldPath.substring( oldPath.lastIndexOf( "/" )+1,oldPath.length( ) ); 
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
                
                //newPath = fullPathWithoutName + (fileName.contains( "assets/animation" )?"":"assets/animation/")+fileName;
                newPath = getAbsoluteAnimationPath(fullPathWithoutName, fileName);
                newPathFile = new File(newPath);
                i++;
           
            }
            
                if (!es.eucm.eadventure.common.auxiliar.File.copyTo( new File(xmlPath+"/"+oldPath), new File(newPath)))
                    Controller.getInstance( ).showInformationDialog(TC.get( "ImportChapter.ProblemsCopying.Title" ), TC.get( "ImportChapter.ProblemsCopying.Message",xmlPath+oldPath  ));
            }
            
            // store for the oldPath key the newPath to change future appearances of oldPath for other resources in the chapter 
            resourcesAdded.put( oldPath, oldPathWithoutName + fileName  );
           
            return oldPathWithoutName + fileName;
        }
       
        return resourcesAdded.get( oldPath );
    }
}
