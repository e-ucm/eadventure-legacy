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
package es.eucm.eadventure.editor.control.config;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.auxiliar.runsettings.DebugSettings;
import es.eucm.eadventure.editor.control.Controller;

public class ConfigData {

    private boolean showStartDialog;

    private RecentFiles recentFiles;

    private static ConfigData instance;

    private String configFile;

    /**
     * Stores the file that contains the GUI strings.
     */
    private String languageFile;

    /**
     * Stores the file that contains the about document.
     */
    private String aboutFile;

    /**
     * Stores the file that contains the loading screen.
     */
    private String loadingImage;

    /**
     * Stores whether the item references must be displayed by default.
     */
    private boolean showItemReferences;

    /**
     * Stores whether the atrezzo item references must be displayed by default.
     */
    private boolean showAtrezzoReferences;

    /**
     * Stores whether the character references must be displayed by default.
     */
    private boolean showNPCReferences;

    private String exportsPath;

    private String reportsPath;

    private String projectsPath;

    private int effectSelectorTab;
    
    
    /////// Added to control preferences on support display options for debugging
    private DebugSettings debugOptions;
    //////
    
    /**
     * @since V1.5
     * Stores the last X position of the upper-left vertex of the main window
     */
    private int editorWindowX=Integer.MAX_VALUE;
    
    /**
     * @since V1.5
     * Stores the last Y position of the upper-left vertex of the main window
     */
    private int editorWindowY=Integer.MAX_VALUE;
    
    /**
     * @since V1.5
     * Stores the last width of the main window
     */
    private int editorWindowW=Integer.MAX_VALUE;
    
    /**
     * @since V1.5
     * Stores the last height of the main window
     */
    private int editorWindowH=Integer.MAX_VALUE;

    // Engine - Run
    
    /**
     * @since V1.5
     * Stores the last X position of the upper-left vertex of the game engine
     */
    private int engineWindowX=Integer.MAX_VALUE;
    
    /**
     * @since V1.5
     * Stores the last Y position of the upper-left vertex of the game engine
     */
    private int engineWindowY=Integer.MAX_VALUE;
    
    /**
     * @since V1.5
     * Stores the last width of the game engine
     */
    private int engineWindowW=Integer.MAX_VALUE;
    
    /**
     * @since V1.5
     * Stores the last height of the game engine
     */
    private int engineWindowH=Integer.MAX_VALUE;
    
    // Engine - Debug
    
    /**
     * @since V1.5
     * Stores the last X position of the upper-left vertex of the game debug
     */
    private int debugWindowX=Integer.MAX_VALUE;
    
    /**
     * @since V1.5
     * Stores the last Y position of the upper-left vertex of the game debug
     */
    private int debugWindowY=Integer.MAX_VALUE;
    
    /**
     * @since V1.5
     * Stores the last width of the game debug
     */
    private int debugWindowW=Integer.MAX_VALUE;
    
    /**
     * @since V1.5
     * Stores the last height of the game debug
     */
    private int debugWindowH=Integer.MAX_VALUE;
    
    
    public static boolean showNPCReferences( ) {

        return instance.showNPCReferences;
    }

    public static boolean showItemReferences( ) {

        return instance.showItemReferences;
    }

    public static boolean showAtrezzoReferences( ) {

        return instance.showAtrezzoReferences;
    }

    public static String getLanguangeFile( ) {

        return instance.languageFile;
    }

    public static String getAboutFile( ) {

        return instance.aboutFile;
    }

    public static String getLoadingImage( ) {

        return instance.loadingImage;
    }
    
    public static DebugSettings getUserDefinedDebugSettings(){
        return instance.debugOptions;
    }

    public static boolean showStartDialog( ) {

        return instance.showStartDialog;
    }

    public static void setShowNPCReferences( boolean b ) {

        instance.showNPCReferences = b;
    }

    public static void setShowItemReferences( boolean b ) {

        instance.showItemReferences = b;
    }

    public static void setShowAtrezzoReferences( boolean b ) {

        instance.showAtrezzoReferences = b;
    }

    public static void setLanguangeFile( String language, String about, String loadingImage ) {

        instance.languageFile = language;
        instance.aboutFile = about;
        instance.loadingImage = loadingImage;
    }

    public static void setAboutFile( String s ) {

        instance.aboutFile = s;
    }

    public static void setLoadingImage( String s ) {

        instance.loadingImage = s;
    }

    public static void setShowStartDialog( boolean b ) {

        instance.showStartDialog = b;
    }

    // Editor
    public static void setEditorWindowX ( int x ){
        instance.editorWindowX = x;
    }
    
    public static void setEditorWindowY ( int y ){
        instance.editorWindowY = y;
    }
    
    public static void setEditorWindowWidth ( int w ){
        instance.editorWindowW = w;
    }
    
    public static void setEditorWindowHeight ( int h ){
        instance.editorWindowH = h;
    }
    
    public static int getEditorWindowX (){
        return instance.editorWindowX;
    }
    
    public static int getEditorWindowY (){
        return instance.editorWindowY;
    }
    
    public static int getEditorWindowWidth (){
        return instance.editorWindowW;
    }
    
    public static int getEditorWindowHeight (){
        return instance.editorWindowH;
    }

    
    //Engine - normal run
    public static void setEngineWindowX ( int x ){
        instance.engineWindowX = x;
    }
    
    public static void setEngineWindowY ( int y ){
        instance.engineWindowY = y;
    }
    
    public static void setEngineWindowWidth ( int w ){
        instance.engineWindowW = w;
    }
    
    public static void setEngineWindowHeight ( int h ){
        instance.engineWindowH = h;
    }
    
    public static int getEngineWindowX (){
        return instance.engineWindowX;
    }
    
    public static int getEngineWindowY (){
        return instance.engineWindowY;
    }
    
    public static int getEngineWindowWidth (){
        return instance.engineWindowW;
    }
    
    public static int getEngineWindowHeight (){
        return instance.engineWindowH;
    }


    //Engine - debug run
    public static void setDebugWindowX ( int x ){
        instance.debugWindowX = x;
    }
    
    public static void setDebugWindowY ( int y ){
        instance.debugWindowY = y;
    }
    
    public static void setDebugWindowWidth ( int w ){
        instance.debugWindowW = w;
    }
    
    public static void setDebugWindowHeight ( int h ){
        instance.debugWindowH = h;
    }
    
    public static int getDebugWindowX (){
        return instance.debugWindowX;
    }
    
    public static int getDebugWindowY (){
        return instance.debugWindowY;
    }
    
    public static int getDebugWindowWidth (){
        return instance.debugWindowW;
    }
    
    public static int getDebugWindowHeight (){
        return instance.debugWindowH;
    }

    
    public static void loadFromXML( String configFile ) {

        instance = new ConfigData( configFile );
    }

    public static void storeToXML( ) {

        // Load the current configuration
        Properties configuration = new Properties( );
        
        configuration.setProperty( "EditorWindowX", String.valueOf( instance.editorWindowX ) );
        configuration.setProperty( "EditorWindowY", String.valueOf( instance.editorWindowY ) );
        configuration.setProperty( "EditorWindowWidth", String.valueOf( instance.editorWindowW ) );
        configuration.setProperty( "EditorWindowHeight", String.valueOf( instance.editorWindowH ) );
        
        configuration.setProperty( "EngineWindowX", String.valueOf( instance.engineWindowX ) );
        configuration.setProperty( "EngineWindowY", String.valueOf( instance.engineWindowY ) );
        configuration.setProperty( "EngineWindowWidth", String.valueOf( instance.engineWindowW ) );
        configuration.setProperty( "EngineWindowHeight", String.valueOf( instance.engineWindowH ) );
        
        configuration.setProperty( "DebugWindowX", String.valueOf( instance.debugWindowX ) );
        configuration.setProperty( "DebugWindowY", String.valueOf( instance.debugWindowY ) );
        configuration.setProperty( "DebugWindowWidth", String.valueOf( instance.debugWindowW ) );
        configuration.setProperty( "DebugWindowHeight", String.valueOf( instance.debugWindowH ) );
        
        configuration.setProperty( "PaintGrid", String.valueOf( instance.debugOptions.isPaintGrid( ) ) );
        configuration.setProperty( "PaintHotSpots", String.valueOf( instance.debugOptions.isPaintHotSpots( ) ) );
        configuration.setProperty( "PaintBoundingAreas", String.valueOf( instance.debugOptions.isPaintBoundingAreas( ) ) );
        
        configuration.setProperty( "ShowItemReferences", String.valueOf( instance.showItemReferences ) );
        configuration.setProperty( "ShowNPCReferences", String.valueOf( instance.showNPCReferences ) );
        configuration.setProperty( "ShowAtrezzoReferences", String.valueOf( instance.showAtrezzoReferences ) );
        configuration.setProperty( "ShowStartDialog", String.valueOf( instance.showStartDialog ) );
        configuration.setProperty( "LanguageFile", instance.languageFile );
        configuration.setProperty( "AboutFile", instance.aboutFile );
        configuration.setProperty( "LoadingImage", instance.loadingImage );
        if( instance.exportsPath != null )
            configuration.setProperty( "ExportsDirectory", instance.exportsPath );
        if( instance.reportsPath != null )
            configuration.setProperty( "ReportsDirectory", instance.reportsPath );
        if( instance.projectsPath != null )
            configuration.setProperty( "ProjectsDirectory", instance.projectsPath );
        if( instance.projectsPath != null )
            configuration.setProperty( "EffectSelectorTab", Integer.toString( instance.effectSelectorTab ) );

        instance.recentFiles.fillProperties( configuration );

        // Store the configuration into a file
        try {
            configuration.storeToXML( new FileOutputStream( instance.configFile ), "<e-Adventure> editor configuration" );
        }
        catch( FileNotFoundException e ) {
        }
        catch( IOException e ) {
        }

    }

    private ConfigData( String fileName ) {

        this.configFile = fileName;
        Properties configuration = new Properties( );
        try {
            configuration.loadFromXML( new FileInputStream( fileName ) );
            languageFile = configuration.getProperty( "LanguageFile" );
            aboutFile = configuration.getProperty( "AboutFile" );
            loadingImage = configuration.getProperty( "LoadingImage" );

            Dimension size = Toolkit.getDefaultToolkit( ).getScreenSize( );
            // Editor
            try{
                editorWindowX=Integer.parseInt( configuration.getProperty( "EditorWindowX" ) );
                if (editorWindowX<0 || editorWindowX>size.width){
                    editorWindowX=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                editorWindowX=Integer.MAX_VALUE;
            }
            try{
                editorWindowY=Integer.parseInt( configuration.getProperty( "EditorWindowY" ) );
                if (editorWindowY<0 || editorWindowY>size.height){
                    editorWindowY=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                editorWindowY=Integer.MAX_VALUE;
            }
            try{
                editorWindowW=Integer.parseInt( configuration.getProperty( "EditorWindowWidth" ) );
                if (editorWindowW<0 || editorWindowW>size.width){
                    editorWindowW=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                editorWindowW=Integer.MAX_VALUE;
            }
            try{
                editorWindowH=Integer.parseInt( configuration.getProperty( "EditorWindowHeight" ) );
                if (editorWindowH<0 || editorWindowH>size.height){
                    editorWindowH=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                editorWindowH=Integer.MAX_VALUE;
            }
            
            // Engine
            try{
                engineWindowX=Integer.parseInt( configuration.getProperty( "EngineWindowX" ) );
                if (engineWindowX<0 || engineWindowX>size.width){
                    engineWindowX=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                engineWindowX=Integer.MAX_VALUE;
            }
            try{
                engineWindowY=Integer.parseInt( configuration.getProperty( "EngineWindowY" ) );
                if (engineWindowY<0 || engineWindowY>size.height){
                    engineWindowY=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                engineWindowY=Integer.MAX_VALUE;
            }
            try{
                engineWindowW=Integer.parseInt( configuration.getProperty( "EngineWindowWidth" ) );
                if (engineWindowW<0 || engineWindowW>size.width){
                    engineWindowW=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                engineWindowW=Integer.MAX_VALUE;
            }
            try{
                engineWindowH=Integer.parseInt( configuration.getProperty( "EngineWindowHeight" ) );
                if (engineWindowH<0 || engineWindowH>size.height){
                    engineWindowH=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                engineWindowH=Integer.MAX_VALUE;
            }

            // Debug
            try{
                debugWindowX=Integer.parseInt( configuration.getProperty( "DebugWindowX" ) );
                if (debugWindowX<0 || debugWindowX>size.width){
                    debugWindowX=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                debugWindowX=Integer.MAX_VALUE;
            }
            try{
                debugWindowY=Integer.parseInt( configuration.getProperty( "DebugWindowY" ) );
                if (debugWindowY<0 || debugWindowY>size.height){
                    debugWindowY=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                debugWindowY=Integer.MAX_VALUE;
            }
            try{
                debugWindowW=Integer.parseInt( configuration.getProperty( "DebugWindowWidth" ) );
                if (debugWindowW<0 || debugWindowW>size.width){
                    debugWindowW=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                debugWindowW=Integer.MAX_VALUE;
            }
            try{
                debugWindowH=Integer.parseInt( configuration.getProperty( "DebugWindowHeight" ) );
                if (debugWindowH<0 || debugWindowH>size.height){
                    debugWindowH=Integer.MAX_VALUE;
                }
            } catch (NumberFormatException ne){
                debugWindowH=Integer.MAX_VALUE;
            }

            
            showItemReferences = Boolean.parseBoolean( configuration.getProperty( "ShowItemReferences" ) );
            showNPCReferences = Boolean.parseBoolean( configuration.getProperty( "ShowNPCReferences" ) );
            showStartDialog = Boolean.parseBoolean( configuration.getProperty( "ShowStartDialog" ) );

            debugOptions = new DebugSettings();
            if (configuration.containsKey( "PaintBoundingAreas" ))
                debugOptions.setPaintBoundingAreas( Boolean.parseBoolean( configuration.getProperty( "PaintBoundingAreas" ) ));
            if (configuration.containsKey( "PaintGrid" ))    
                debugOptions.setPaintGrid( Boolean.parseBoolean( configuration.getProperty( "PaintGrid" ) ));
            if (configuration.containsKey( "PaintHotSpots" ))    
                debugOptions.setPaintHotSpots( Boolean.parseBoolean( configuration.getProperty( "PaintHotSpots" ) ));
            
            exportsPath = configuration.getProperty( "ExportsDirectory" );
            if( exportsPath != null )
                ReleaseFolders.setExportsPath( exportsPath );
            reportsPath = configuration.getProperty( "ReportsDirectory" );
            if( reportsPath != null )
                ReleaseFolders.setReportsPath( reportsPath );
            projectsPath = configuration.getProperty( "ProjectsDirectory" );
            if( projectsPath != null )
                ReleaseFolders.setProjectsPath( projectsPath );
            try {
                effectSelectorTab = Integer.parseInt( configuration.getProperty( "EffectSelectorTab" ) );
            }
            catch( Exception e ) {
                effectSelectorTab = 0;
            }

            recentFiles = new RecentFiles( configuration );
        }
        catch( InvalidPropertiesFormatException e ) {
            checkConsistency( );
        }
        catch( FileNotFoundException e ) {
            checkConsistency( );
        }
        catch( IOException e ) {
            checkConsistency( );
        }
        catch( Exception e ) {
            checkConsistency( );
        }

    }

    private void checkConsistency( ) {

        if (debugOptions == null)
            debugOptions = new DebugSettings();
        
        if( languageFile == null ) {
            languageFile = ReleaseFolders.getLanguageFilePath( ReleaseFolders.LANGUAGE_ENGLISH );
        }
        if( aboutFile == null ) {
            aboutFile = ReleaseFolders.LANGUAGE_DIR_EDITOR + "/" + ReleaseFolders.getDefaultAboutFilePath( );
        }
        if( loadingImage == null ) {
            loadingImage = ReleaseFolders.IMAGE_LOADING_DIR + "/" + Controller.getInstance( ).getDefaultLanguage( ) + "/Editor2D-Loading.png";
        }
        if( exportsPath == null ) {

        }
        if( projectsPath == null ) {

        }
        if( reportsPath == null ) {

        }
        if( recentFiles == null ) {
            recentFiles = new RecentFiles( new Properties( ) );
        }

    }

    public static void fileLoaded( String file ) {

        instance.recentFiles.fileLoaded( file );
    }

    public static String[][] getRecentFilesInfo( int r ) {

        return instance.recentFiles.getRecentFilesInfo( r );
    }

    public static String[][] getRecentFilesInfo( int l, int r ) {

        return instance.recentFiles.getRecentFilesInfo( l, r );
    }

    /**
     * @return the effectSelectorTab
     */
    public static int getEffectSelectorTab( ) {

        return instance.effectSelectorTab;
    }

    /**
     * @param effectSelectorTab
     *            the effectSelectorTab to set
     */
    public static void setEffectSelectorTab( int effectSelectorTab ) {

        instance.effectSelectorTab = effectSelectorTab;
    }

}
