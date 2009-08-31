/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import es.eucm.eadventure.common.auxiliar.ReleaseFolders;

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

	public static void setLanguangeFile( String language, String about, String loadingImage) {
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

	public static void loadFromXML( String configFile ) {
		instance = new ConfigData( configFile );
	}

	public static void storeToXML( ) {
		// Load the current configuration
		Properties configuration = new Properties( );
		configuration.setProperty( "ShowItemReferences", String.valueOf( instance.showItemReferences ) );
		configuration.setProperty( "ShowNPCReferences", String.valueOf( instance.showNPCReferences ) );
		configuration.setProperty( "ShowAtrezzoReferences", String.valueOf( instance.showAtrezzoReferences ) );
		configuration.setProperty( "ShowStartDialog", String.valueOf( instance.showStartDialog ) );
		configuration.setProperty( "LanguageFile", instance.languageFile );
		configuration.setProperty( "AboutFile", instance.aboutFile );
		configuration.setProperty( "LoadingImage", instance.loadingImage );
		if (instance.exportsPath!=null)
			configuration.setProperty( "ExportsDirectory", instance.exportsPath );
		if (instance.reportsPath!=null)
			configuration.setProperty( "ReportsDirectory", instance.reportsPath );
		if (instance.projectsPath!=null)
			configuration.setProperty( "ProjectsDirectory", instance.projectsPath );
		if (instance.projectsPath!=null)
			configuration.setProperty( "EffectSelectorTab", Integer.toString( instance.effectSelectorTab ) );
		
		instance.recentFiles.fillProperties( configuration );

		
		// Store the configuration into a file
		try {
			configuration.storeToXML( new FileOutputStream( instance.configFile ), "<e-Adventure> editor configuration" );
		} catch( FileNotFoundException e ) {} catch( IOException e ) {}

	}

	private ConfigData( String fileName ) {
		this.configFile = fileName;
		Properties configuration = new Properties( );
		try {
			configuration.loadFromXML( new FileInputStream( fileName ) );
			languageFile = configuration.getProperty( "LanguageFile" );
			aboutFile = configuration.getProperty( "AboutFile" );
			loadingImage = configuration.getProperty( "LoadingImage" );
			
			showItemReferences = Boolean.parseBoolean( configuration.getProperty( "ShowItemReferences" ) );
			showNPCReferences = Boolean.parseBoolean( configuration.getProperty( "ShowNPCReferences" ) );
			showStartDialog = Boolean.parseBoolean( configuration.getProperty( "ShowStartDialog" ) );
			
            exportsPath = configuration.getProperty("ExportsDirectory");
            if (exportsPath!=null)
            	ReleaseFolders.setExportsPath(exportsPath);
            reportsPath = configuration.getProperty("ReportsDirectory");
            if (reportsPath!=null)
            	ReleaseFolders.setReportsPath(reportsPath);
            projectsPath = configuration.getProperty("ProjectsDirectory");
            if (projectsPath!=null)
            	ReleaseFolders.setProjectsPath(projectsPath);
            try {
            	effectSelectorTab = Integer.parseInt(configuration.getProperty("EffectSelectorTab"));
            }catch (Exception e){
            	effectSelectorTab = 0;
            }
			
			recentFiles = new RecentFiles( configuration );
		} catch( InvalidPropertiesFormatException e ) {
			checkConsistency();
		} catch( FileNotFoundException e ) {
			checkConsistency();
		} catch( IOException e ) {
			checkConsistency();
		} catch ( Exception e ){
			checkConsistency();
		}

	}
	
	private void checkConsistency(){
		if (languageFile == null){
			languageFile = ReleaseFolders.getLanguageFilePath(ReleaseFolders.LANGUAGE_ENGLISH);
		} 
		if (aboutFile == null){
			aboutFile = ReleaseFolders.getAboutFilePath(ReleaseFolders.LANGUAGE_ENGLISH);
		}
		if (loadingImage == null){
			loadingImage = ReleaseFolders.getLoadingImagePath(ReleaseFolders.LANGUAGE_ENGLISH);
		}
		if ( exportsPath==null){
			
		}
		if (projectsPath ==null){
			
		}
		if (reportsPath ==null){
			
		}
		if (recentFiles == null){
			recentFiles = new RecentFiles( new Properties() );	
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
	public static int getEffectSelectorTab() {
		return instance.effectSelectorTab;
	}

	/**
	 * @param effectSelectorTab the effectSelectorTab to set
	 */
	public static void setEffectSelectorTab(int effectSelectorTab) {
		instance.effectSelectorTab = effectSelectorTab;
	}

}
