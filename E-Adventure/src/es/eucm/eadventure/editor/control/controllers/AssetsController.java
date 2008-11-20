package es.eucm.eadventure.editor.control.controllers;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.media.MediaLocator;
import javax.swing.ImageIcon;

import java.io.FileInputStream;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.FileFilter;
import es.eucm.eadventure.common.auxiliar.categoryfilters.AnimationFileFilter;
import es.eucm.eadventure.common.auxiliar.categoryfilters.AudioFileFilter;
import es.eucm.eadventure.common.auxiliar.categoryfilters.FormattedTextFileFilter;
import es.eucm.eadventure.common.auxiliar.categoryfilters.ImageFileFilter;
import es.eucm.eadventure.common.auxiliar.categoryfilters.VideoFileFilter;
import es.eucm.eadventure.common.auxiliar.filefilters.JPGFileFilter;
import es.eucm.eadventure.common.auxiliar.filefilters.JPGSlidesFileFilter;
import es.eucm.eadventure.common.auxiliar.filefilters.MP3FileFilter;
import es.eucm.eadventure.common.auxiliar.filefilters.PNGAnimationFileFilter;
import es.eucm.eadventure.common.auxiliar.filefilters.PNGFileFilter;
import es.eucm.eadventure.common.auxiliar.filefilters.XMLFileFilter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.common.loader.incidences.Incidence;
import es.eucm.eadventure.common.loader.InputStreamCreator;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.gui.assetchooser.AnimationChooser;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;
import es.eucm.eadventure.editor.gui.assetchooser.AudioChooser;
import es.eucm.eadventure.editor.gui.assetchooser.BackgroundChooser;
import es.eucm.eadventure.editor.gui.assetchooser.CursorChooser;
import es.eucm.eadventure.editor.gui.assetchooser.FormatedTextChooser;
import es.eucm.eadventure.editor.gui.assetchooser.IconChooser;
import es.eucm.eadventure.editor.gui.assetchooser.ImageChooser;
import es.eucm.eadventure.editor.gui.assetchooser.VideoChooser;

/**
 * This class is responsible for managing the multimedia data stored in the adventure ZIP files. It is also responsible
 * for the assessment and adaptation files of the adventures.
 * 
 * @author Bruno Torijano Bueno
 */
public class AssetsController {

	/**
	 * Assessment files category.
	 */
	public static final int CATEGORY_ASSESSMENT = 0;

	/**
	 * Assessment files category.
	 */
	public static final int CATEGORY_ADAPTATION = 1;

	/**
	 * Background category.
	 */
	public static final int CATEGORY_BACKGROUND = 2;

	/**
	 * Animation category.
	 */
	public static final int CATEGORY_ANIMATION = 3;

	/**
	 * Image category.
	 */
	public static final int CATEGORY_IMAGE = 4;

	/**
	 * Icon category.
	 */
	public static final int CATEGORY_ICON = 5;

	/**
	 * Audio category.
	 */
	public static final int CATEGORY_AUDIO = 6;

	/**
	 * Video category.
	 */
	public static final int CATEGORY_VIDEO = 7;
	
	/**
	 * Cursor category.
	 */
	public static final int CATEGORY_CURSOR = 8;

	/**
	 * Cursor category.
	 */
	public static final int CATEGORY_STYLED_TEXT = 9;

	

	/**
	 * Void filter.
	 */
	public static final int FILTER_NONE = 0;

	/**
	 * JPG files filter.
	 */
	public static final int FILTER_JPG = 1;

	/**
	 * PNG files filter.
	 */
	public static final int FILTER_PNG = 2;

	/**
	 * MP3 files filter.
	 */
	public static final int FILTER_MP3 = 3;

	//TODO: VIDEO FILTERS

	/**
	 * Asset path for the empty image. For use only in assets.
	 */
	public static final String ASSET_EMPTY_IMAGE = "assets/special/EmptyImage.png";

	/**
	 * Asset path for the empty icon. For use only in assets.
	 */
	public static final String ASSET_EMPTY_ICON = "assets/special/EmptyIcon.png";

	/**
	 * Asset path for the empty animation. For use only in assets.
	 */
	public static final String ASSET_EMPTY_ANIMATION = "assets/special/EmptyAnimation";
	
	/**
	 * Asset path for the default book image. For use only in assets.
	 */
	public static final String ASSET_DEFAULT_BOOK_IMAGE = "assets/special/DefaultBook.jpg";
	
	/**
	 * Number of categories.
	 */
	private static final int CATEGORIES_COUNT = 10;

	/**
	 * Path for the assessment files.
	 */
	private static final String CATEGORY_ASSESSMENT_FOLDER = "assessment";

	/**
	 * Path for the adaptation files.
	 */
	private static final String CATEGORY_ADAPTATION_FOLDER = "adaptation";

	/**
	 * Path for the background assets.
	 */
	private static final String CATEGORY_BACKGROUND_FOLDER = "assets/background";

	/**
	 * Path for the animation assets.
	 */
	private static final String CATEGORY_ANIMATION_FOLDER = "assets/animation";

	/**
	 * Path for the image assets.
	 */
	private static final String CATEGORY_IMAGE_FOLDER = "assets/image";

	/**
	 * Path for the icon assets.
	 */
	private static final String CATEGORY_ICON_FOLDER = "assets/icon";

	/**
	 * Path for the audio assets.
	 */
	private static final String CATEGORY_AUDIO_PATH = "assets/audio";

	/**
	 * Path for the video assets.
	 */
	private static final String CATEGORY_VIDEO_PATH = "assets/video";
	
	/**
	 * Path for the video assets.
	 */
	private static final String CATEGORY_CURSOR_PATH = "gui/cursors";

	/**
	 * Path for the video assets.
	 */
	private static final String CATEGORY_STYLED_TEXT_PATH = "assets/styledtext";

	/**
	 * Static class. Private constructor.
	 */
	private AssetsController( ) {}

	//private static VideoCache videoCache = new AssetsController.VideoCache( );
	
	private static HashMap<String, File> tempFiles = new HashMap<String, File>(); 

	public static void resetCache( ) {
		//Reset video cache
		/*videoCache.clean( );
		videoCache.reset( );
		String[] videoAssets = getAssetsList( CATEGORY_VIDEO );
		for( String videoAsset : videoAssets ) {

			// Add the file
			videoCache.cacheVideo( videoAsset );
		}*/
		
		//Reset tempFiles
		tempFiles = new HashMap<String, File>();
	}

	/*public static void cleanVideoCache( ) {
		videoCache.clean( );
	}*/

	public static String[] categoryFolders(){
		String[] folders = new String[CATEGORIES_COUNT];
		for (int i=0; i<CATEGORIES_COUNT; i++){
			folders[i] = AssetsController.getCategoryFolder( i );
		}
		return folders;
	}
	
	/**
	 * Creates the initial structure of asset folders
	 */
	public static void createFolderStructure( ) {
		File projectDir = new File (Controller.getInstance( ).getProjectFolder( ));
		String[] folders = categoryFolders();
		for (int i=0; i<folders.length; i++){
			File category = new File (projectDir, folders[i]);
			category.create( );
		}
		
		// Copy eadventure.dtd, descriptor.dtd, assessment.dtd, adaptation.dtd
		File descriptorDTD = new File("descriptor.dtd");
		if (descriptorDTD.exists( )){
			if (!descriptorDTD.copyTo( new File(projectDir, "descriptor.dtd") )){
				Controller.getInstance( ).showErrorDialog( TextConstants.getText("Error.DTD.NotCopied.Title"), 
						TextConstants.getText("Error.DTD.NotCopied.Message", "descriptor.dtd") );
			}
		}else{
			Controller.getInstance( ).showErrorDialog( TextConstants.getText("Error.DTD.NotFound.Title"), 
					TextConstants.getText("Error.DTD.NotFound.Message", "descriptor.dtd") );
			
		}

		// eadventure.dtd
		File eadventureDTD = new File("eadventure.dtd");
		if (eadventureDTD.exists( )){
			if (!eadventureDTD.copyTo( new File(projectDir, "eadventure.dtd") )){
				Controller.getInstance( ).showErrorDialog( TextConstants.getText("Error.DTD.NotCopied.Title"), 
						TextConstants.getText("Error.DTD.NotCopied.Message", "eadventure.dtd") );
			}
		}else{
			Controller.getInstance( ).showErrorDialog( TextConstants.getText("Error.DTD.NotFound.Title"), 
					TextConstants.getText("Error.DTD.NotFound.Message", "eadventure.dtd") );
			
		}

		// assessment.dtd
		File assessmentDTD = new File("assessment.dtd");
		if (assessmentDTD.exists( )){
			if (!assessmentDTD.copyTo( new File(projectDir, "assessment.dtd") )){
				Controller.getInstance( ).showErrorDialog( TextConstants.getText("Error.DTD.NotCopied.Title"), 
						TextConstants.getText("Error.DTD.NotCopied.Message", "assessment.dtd") );
			}
		}else{
			Controller.getInstance( ).showErrorDialog( TextConstants.getText("Error.DTD.NotFound.Title"), 
					TextConstants.getText("Error.DTD.NotFound.Message", "assessment.dtd") );
			
		}
		
		// adaptation.dtd
		File adaptationDTD = new File("adaptation.dtd");
		if (adaptationDTD.exists( )){
			if (!adaptationDTD.copyTo( new File(projectDir, "adaptation.dtd") )){
				Controller.getInstance( ).showErrorDialog( TextConstants.getText("Error.DTD.NotCopied.Title"), 
						TextConstants.getText("Error.DTD.NotCopied.Message", "adaptation.dtd") );
			}
		}else{
			Controller.getInstance( ).showErrorDialog( TextConstants.getText("Error.DTD.NotFound.Title"), 
					TextConstants.getText("Error.DTD.NotFound.Message", "adaptation.dtd") );
			
		}

	}
	/**
	 * Returns the name of the files in the given category. This method must be used only to display the data, as it
	 * doesn't contain complete paths.
	 * 
	 * @param assetsCategory
	 *            Category of the assets
	 * @return List of assets of the given category
	 */
	public static String[] getAssetFilenames( int assetsCategory ) {
		return getAssetFilenames( assetsCategory, FILTER_NONE );
	}

	/**
	 * Returns the name of the files in the given category. This method must be used only to display the data, as it
	 * doesn't contain complete paths.
	 * 
	 * @param assetsCategory
	 *            Category of the assets
	 * @param filter
	 *            Specific filter for the files
	 * @return List of assets of the given category
	 */

	public static String[] getAssetFilenames( int assetsCategory, int filter ) {
		String[] assetsList = new String[] {};

		// Take the category folder, from the ZIP file name
		File categoryFolder = new File( Controller.getInstance( ).getProjectFolder( ), getCategoryFolder( assetsCategory ) );

		// Take the file list and create the array
		//java.io.File[] fileList = categoryFolder.listFiles( getAssetsFileFilter( assetsCategory, filter ), categoryFolder.getArchiveDetector( ) );
		File[] fileList = categoryFolder.listFiles( getAssetsFileFilter( assetsCategory, filter ) );

		// If the array is not empty
		if( fileList != null ) {
			// Copy the relative paths to the string array
			
			// If is styled text, remove referenced files (folder) when present
			if (assetsCategory != CATEGORY_STYLED_TEXT){
				assetsList = new String[fileList.length];
				for( int i = 0; i < fileList.length; i++ )
					assetsList[i] = fileList[i].getName( );
			} else {
				//Remove those files which are directories
				ArrayList<java.io.File> filteredFileList = new ArrayList<java.io.File>();
				for (int i=0; i<fileList.length; i++){
					if (!fileList[i].isDirectory( ))
						filteredFileList.add( fileList[i] );
				}
				
				assetsList = new String[filteredFileList.size( )];
				for (int i=0; i<filteredFileList.size( ); i++)
					assetsList[i] = filteredFileList.get( i ).getName( );
			}
		}

		return assetsList;
	}

	/**
	 * Returns the files in the given category, using a relative path to the ZIP file.
	 * 
	 * @param assetsCategory
	 *            Category of the assets
	 * @return List of assets of the given category, using paths relative to the opened ZIP file
	 */
	public static String[] getAssetsList( int assetsCategory ) {
		return getAssetsList( assetsCategory, FILTER_NONE );
	}

	/**
	 * Returns the files for the given category and filter, using a relative path to the ZIP file.
	 * 
	 * @param assetsCategory
	 *            Category of the assets
	 * @param filter
	 *            Specific filter for the files
	 * @return List of assets for the given category and filter, using paths relative to the opened ZIP file
	 */
	public static String[] getAssetsList( int assetsCategory, int filter ) {
		String[] assetsList = new String[] {};

		// Take the category folder, from the ZIP file name
		File categoryFolder = new File( Controller.getInstance( ).getProjectFolder( ), getCategoryFolder( assetsCategory ) );

		// Take the file list and create the array
		File[] fileList = categoryFolder.listFiles( getAssetsFileFilter( assetsCategory, filter ) );

		// If the array is not empty
		if( fileList != null ) {
			// If is styled text, remove referenced files (folder) when present
			if (assetsCategory != CATEGORY_STYLED_TEXT){
				// Copy the relative paths to the string array
				assetsList = new String[fileList.length];
				for( int i = 0; i < fileList.length; i++ )
					//assetsList[i] = fileList[i].getEnclEntryName( );
					assetsList[i] = getCategoryFolder(assetsCategory)+"/"+fileList[i].getName( );
			} else {
				//Remove those files which are directories
				ArrayList<java.io.File> filteredFileList = new ArrayList<java.io.File>();
				for (int i=0; i<fileList.length; i++){
					if (!fileList[i].isDirectory( ))
						filteredFileList.add( fileList[i] );
				}
				
				assetsList = new String[filteredFileList.size( )];
				for (int i=0; i<filteredFileList.size( ); i++)
					//assetsList[i] = filteredFileList.get( i ).getEnclEntryName( );
					assetsList[i] = getCategoryFolder(assetsCategory)+"/"+filteredFileList.get( i ).getName( );

			}
		}

		return assetsList;
	}

	/**
	 * Returns an input stream corresponding to the given file path (relative to the ZIP).
	 * 
	 * @param assetPath
	 *            Path of the file, relative to the ZIP file
	 * @return Input stream for the given file
	 */
	public static InputStream getInputStream( String assetPath ) {
		InputStream inputStream = null;

		try {
		
			// Load the input stream from the file (if it exists)
			if( new File( assetPath ).exists( ) )
				inputStream = new FileInputStream( assetPath );
			else if( new File( Controller.getInstance( ).getProjectFolder( ), assetPath ).exists( ) )
				inputStream = new FileInputStream( Controller.getInstance( ).getProjectFolder( ) + "/" + assetPath );
			
		} catch( FileNotFoundException e ) {
			e.printStackTrace( );
		}

		return inputStream;
	}

	/*public static MediaLocator getVideo( String assetPath ) {
		return videoCache.fetchVideo( assetPath );
	}*/
	
	public static MediaLocator getVideo( String assetPath ) {

		String absolutePath = null;

		String[] assetsList = AssetsController.getAssetsList( CATEGORY_VIDEO );
		int position = -1;
		//If it is in the zip
		for( int i = 0; i < assetsList.length; i++ ) {
			if( assetsList[i].equals( assetPath ) ) {
				position = i;
			}
		}
		if( position != -1 ) {
			absolutePath = new File(Controller.getInstance( ).getProjectFolder( ), assetPath).getAbsolutePath( ); 
		}

		File destinyFile = new File( absolutePath );
		try {
			return new MediaLocator( destinyFile.toURI( ).toURL( ) );
		} catch( MalformedURLException e ) {
			e.printStackTrace( );
			return null;
		}

	}

    public static URL getResourceAsURLFromZip( String path ){
        try {
            return es.eucm.eadventure.common.auxiliar.zipurl.ZipURL.createAssetURL( Controller.getInstance( ).getProjectFolder( ), path );
        } catch( MalformedURLException e ) {
            return null;
        }
    }

	/**
	 * Returns an image corresponding to the given file path (relative to the ZIP).
	 * 
	 * @param imagePath
	 *            Path to the image, relative to the ZIP file
	 * @return Image for the given file
	 */
	public static Image getImage( String imagePath ) {
		Image image = null;

		try {
			// Pick the input stream of the
			InputStream inputStream = getInputStream( imagePath );

			// Convert the input stream to an image, and close the stream
			if( inputStream != null ) {
				image = ImageIO.read( inputStream );
				inputStream.close( );
			}
		} catch( IOException e ) {
			e.printStackTrace( );
		}

		return image;
	}

	/**
	 * Returns an array of images, corresponding to the given animation path (relative to the ZIP).
	 * 
	 * @param animationPath
	 *            Animation path relative to the ZIP, including suffix ("_01.png" or "_01.jpg")
	 * @return Array of images, each one containing a frame of the animation
	 */
	public static Image[] getAnimation( String animationPath ) {
		// Create a list of images
		List<Image> framesList = new ArrayList<Image>( );

		// Prepare the root of the animation path and the extension
		String extension = getExtension( animationPath );
		animationPath = removeSuffix( animationPath );

		// While the last image has not been read
		boolean end = false;
		for( int i = 1; i < 100 && !end; i++ ) {

			// Load the current image file
			Image frame = AssetsController.getImage( animationPath + String.format( "_%02d.", i ) + extension );

			// If it exists, add it to the list
			if( frame != null )
				framesList.add( frame );

			// If it doesn't exist, exit the bucle
			else
				end = true;
		}

		return framesList.toArray( new Image[] {} );
	}

	/**
	 * Shows a dialog to the user for selecting files which will be added into the given category.
	 * 
	 * @param assetsCategory
	 *            Destiny asset category
	 * @return True if some asset was added, false otherwise
	 */
	public static boolean addAssets( int assetsCategory ) {
		boolean assetsAdded = false;

		// Ask the user for the files to include
		String[] selectedFiles = Controller.getInstance( ).showMultipleSelectionLoadDialog( getAssetsFileFilter( assetsCategory, FILTER_NONE ) );

		// If the set is not empty
		if( selectedFiles != null ) {
			//try {

				assetsAdded = true;

				// For each asset
				for( String assetPath : selectedFiles ) {
					assetsAdded &= addSingleAsset( assetsCategory, assetPath );
				}

				// Umount the ZIP files
				//File.umount( );
			//} catch( ArchiveException e ) {
			//	e.printStackTrace( );
			//}
		}

		return assetsAdded;
	}

	public static boolean addSingleAsset( int assetsCategory, String assetPath ) {
		boolean assetsAdded = false;
		// Take the category folder, from the ZIP file name
		File categoryFolder = new File( Controller.getInstance( ).getProjectFolder( ), getCategoryFolder( assetsCategory ) );

		// Check if the file is correct, and is going to be added
		if( checkAsset( assetPath, assetsCategory ) ) {

			// If it is an animation asset, add all the images of the animation
			if( assetsCategory == CATEGORY_ANIMATION ) {

				// Prepare the root of the animation path and the extension
				String extension = getExtension( assetPath );
				assetPath = removeSuffix( assetPath );

				// While the last image has not been read
				boolean end = false;
				for( int i = 1; i < 100 && !end; i++ ) {
					// Open source file
					File sourceFile = new File( assetPath + String.format( "_%02d.", i ) + extension );

					// If the file exists, create the destiny file and do the copy
					if( sourceFile.exists( ) ) {
						File destinyFile = new File( categoryFolder, sourceFile.getName( ) );
						
						//If is directory, copy all contents
						if (sourceFile.isDirectory( ))
							assetsAdded = sourceFile.copyAllTo( destinyFile );
						else
							assetsAdded = sourceFile.copyTo( destinyFile );
					}

					// If it doesn't exist, stop loading data
					else
						end = true;
				}
			}

			// If it is not an animation asset, just add the file
			else {
				// Open source file, and create destiny file in the ZIP
				File sourceFile = new File( assetPath );
				File destinyFile = new File( categoryFolder, sourceFile.getName( ) );

				// Copy the data
				//If is directory, copy all contents
				if (sourceFile.isDirectory( ))
					assetsAdded = sourceFile.copyAllTo( destinyFile );
				else
					assetsAdded = sourceFile.copyTo( destinyFile );


				//If it is a video, cache it 
				/*if( assetsCategory == CATEGORY_VIDEO ) {
					if( !videoCache.isVideoCachedZip( getCategoryFolder( assetsCategory ) + "/" + sourceFile.getName( ) ) )
						videoCache.cacheVideo( getCategoryFolder( assetsCategory ) + "/" + sourceFile.getName( ), sourceFile.getAbsolutePath( ) );
				}*/
			}
		}
		return assetsAdded;

	}
	

	/**
	 * Deletes the given asset from the ZIP, asking for confirmation to the user.
	 * 
	 * @param assetPath
	 *            Path to the asset file to delete, relative to the ZIP file.
	 * @return True if the file was deleted, false otherwise
	 */
	public static boolean deleteAsset( String assetPath ) {
		// Delete the asset and store if it has been deleted
		boolean assetDeleted = deleteAsset( assetPath, true );

		// If the asset was deleted, delete the references in the adventure
		Controller controller = Controller.getInstance( );
		if( assetDeleted ) {
			// Delete the references to the asset
			if( assetPath.startsWith( CATEGORY_ANIMATION_FOLDER ) )
				controller.deleteAssetReferences( removeSuffix( assetPath ) );
			else
				controller.deleteAssetReferences( assetPath );

			// Reload the panel
			controller.reloadPanel( );
		}

		return assetDeleted;
	}

	/**
	 * Deletes the given asset from the ZIP.
	 * 
	 * @param assetPath
	 *            Path to the asset file to delete, relative to the ZIP file
	 * @param askForConfirmation
	 *            If true, asks the user for confirmation to delete
	 * @return True if the asset was deleted, false otherwise
	 */
	private static boolean deleteAsset( String assetPath, boolean askForConfirmation ) {
		boolean assetDeleted = false;

		// Count the references, if it is an animation, remove the suffix to do the search
		String references = null;
		if( assetPath.startsWith( CATEGORY_ANIMATION_FOLDER ) )
			references = String.valueOf( Controller.getInstance( ).countAssetReferences( removeSuffix( assetPath ) ) );
		else
			references = String.valueOf( Controller.getInstance( ).countAssetReferences( assetPath ) );

		// If the asset must be deleted (when the user is not asked, or is asked and answers "Yes")
		if( !askForConfirmation || Controller.getInstance( ).showStrictConfirmDialog( TextConstants.getText( "Assets.DeleteAsset" ), TextConstants.getText( "Assets.DeleteAssetWarning", new String[] { getFilename( assetPath ), references } ) ) ) {

			// If it is an animation, delete all the files
			if( assetPath.startsWith( CATEGORY_ANIMATION_FOLDER ) ) {
				// Set "assetDeleted" to true, to perform an AND operation with each result
				assetDeleted = true;

				// Prepare the root of the animation path and the suffix
				String extension = getExtension( assetPath );
				assetPath = removeSuffix( assetPath );

				// While the last image has not been read
				boolean end = false;
				for( int i = 1; i < 100 && !end; i++ ) {
					// Open the file to be deleted
					File animationFrameFile = new File( Controller.getInstance( ).getProjectFolder( ), assetPath + String.format( "_%02d.", i ) + extension );

					// If the file exists, delete it
					if( animationFrameFile.exists( ) )
						assetDeleted &= animationFrameFile.delete( );

					// If it doesn't exist, stop deleting data
					else
						end = true;
				}
			}

			// If it is not an animation, just delete the file
			else
				assetDeleted = new File( Controller.getInstance( ).getProjectFolder( ), assetPath ).delete( );
		}

		return assetDeleted;
	}

	/**
	 * Copies all the asset files from one ZIP to another.
	 * 
	 * @param sourceFile
	 *            Source ZIP file
	 * @param destinyFile
	 *            Destiny ZIP file
	 */
	public static void copyAssets( String sourceFile, String destinyFile ) {
		//try {
			// Copy the files for each category
			for( int i = 0; i < CATEGORIES_COUNT; i++ ) {
				// Take the source and destiny folders
				File sourceCategoryPath = new File( sourceFile, getCategoryFolder( i ) );
				File destinyCategoryPath = new File( destinyFile, getCategoryFolder( i ) );

				// If the source category folder exists
				if( sourceCategoryPath.exists( ) ) {
					// For each file in the source category
					//for( File sourceAssetFile : sourceCategoryPath.listFiles( sourceCategoryPath.getArchiveDetector( ) ) ) {
					for( File sourceAssetFile : sourceCategoryPath.listFiles( ) ) {
						// Create the destiny file and copy it
						File destinyAssetFile = new File( destinyCategoryPath, sourceAssetFile.getName( ) );
						
						//If it is a directory, copy its contents (for HTML resources)
						if (sourceAssetFile.isDirectory( ))
							sourceAssetFile.copyAllTo( destinyAssetFile );
						else
							sourceAssetFile.copyTo( destinyAssetFile );
					}
				}
			}

			// Close the open ZIP files
			//File.umount( );
		//} catch( ArchiveException e ) {
		//	e.printStackTrace( );
		//}
	}

	/**
	 * Adds the special assets to the current adventure.
	 */
	public static void addSpecialAssets( ) {
		// Get the zip file
		String zipFile = Controller.getInstance( ).getProjectFolder( );

		// Add the defaultBook
		File sourceFile = new File( "img/assets/DefaultBook.jpg" );
		if( sourceFile.exists( ) )
			sourceFile.copyTo( new File( zipFile, ASSET_DEFAULT_BOOK_IMAGE ) );
		// If the source file doesn't exist, show an error message
		else
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.SpecialAssetNotFound", "img/assets/DefaultBook.jpg" ) );
		
		// Add the empty image
		sourceFile = new File( "img/assets/EmptyImage.png" );
		if( sourceFile.exists( ) )
			sourceFile.copyTo( new File( zipFile, ASSET_EMPTY_IMAGE ) );

		// If the source file doesn't exist, show an error message
		else
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.SpecialAssetNotFound", "img/assets/EmptyImage.png" ) );

		// Add the empty icon
		sourceFile = new File( "img/assets/EmptyIcon.png" );
		if( sourceFile.exists( ) )
			sourceFile.copyTo( new File( zipFile, ASSET_EMPTY_ICON ) );

		// If the source file doesn't exist, show an error message
		else
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.SpecialAssetNotFound", "img/assets/EmptyIcon.png" ) );

		// Add the empty animation
		sourceFile = new File( "img/assets/EmptyAnimation_01.png" );
		if( sourceFile.exists( ) )
			sourceFile.copyTo( new File( zipFile, ASSET_EMPTY_ANIMATION + "_01.png" ) );

		// If the source file doesn't exist, show an error message
		else
			Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.SpecialAssetNotFound", "img/assets/EmptyAnimation_01.png" ) );
	}

	/**
	 * Returns whether the given asset is a special one or not.
	 * 
	 * @param assetPath
	 *            Asset path
	 * @return True if the asset is special, false otherwise
	 */
	public static boolean isAssetSpecial( String assetPath ) {
		// The three empty assets are considered as special
		return assetPath.equals( ASSET_EMPTY_IMAGE ) || assetPath.equals( ASSET_EMPTY_ICON ) || assetPath.equals( ASSET_EMPTY_ANIMATION );
	}

	/**
	 * Returns the filename of the given asset.
	 * 
	 * @param assetPath
	 *            Path to the asset
	 * @return Name of the file representing the asset
	 */
	public static String getFilename( String assetPath ) {
		int lastSlashIndex = Math.max( assetPath.lastIndexOf( '/' ) + 1, assetPath.lastIndexOf( '\\' ) + 1 );
		return assetPath.substring( lastSlashIndex, assetPath.length( ) );
	}

	/**
	 * Removes the suffix "_01.png" or "_01.jpg" from the given asset path.
	 * 
	 * @param assetPath
	 *            Source asset path
	 * @return Asset path without the suffix
	 */
	public static String removeSuffix( String assetPath ) {
		// Remove the suffix in the PNG animations
		if( assetPath.toLowerCase( ).endsWith( "_01.png" ) )
			assetPath = assetPath.substring( 0, assetPath.length( ) - 7 );

		// Remove the suffix in the JPG slides
		else if( assetPath.toLowerCase( ).endsWith( "_01.jpg" ) ) {
			assetPath = assetPath.substring( 0, assetPath.length( ) - 7 );
		}

		return assetPath;
	}

	/**
	 * Checks the given asset to see if it fits the category. If the asset has some problem, a message is displayed to
	 * the user. If the asset already exists in the ZIP file, the user is prompted to overwrite it.
	 * 
	 * @param assetPath
	 *            Absolute path to the asset
	 * @param assetCategory
	 *            Category for the asset
	 * @return True if the asset can be added to the set, false otherwise
	 */
	private static boolean checkAsset( String assetPath, int assetCategory ) {
		boolean assetValid = true;

		// Take the instance of the controller, and the filename of the asset
		Controller controller = Controller.getInstance( );
		String assetFilename = getFilename( assetPath );

		// For images, only background and icon are checked
		if( assetCategory == CATEGORY_BACKGROUND || assetCategory == CATEGORY_ICON ) {
			// Take the data from the file
			Image image = new ImageIcon( assetPath ).getImage( );
			int width = image.getWidth( null );
			int height = image.getHeight( null );

			// Prepare the string array for the error message
			String[] fileInformation = new String[] { assetFilename, String.valueOf( width ), String.valueOf( height ) };

			// The background files must have a size of at least 800x400
			if( assetCategory == CATEGORY_BACKGROUND && ( width < 800 || height < 400 ) ) {
				controller.showErrorDialog( TextConstants.getText( "BackgroundAssets.Title" ), TextConstants.getText( "BackgroundAssets.ErrorBackgroundSize", fileInformation ) );
				assetValid = false;
			}

			// The icon files must have a size of 80x48
			else if( assetCategory == CATEGORY_ICON && ( width != 80 || height != 48 ) ) {
				controller.showErrorDialog( TextConstants.getText( "IconAssets.Title" ), TextConstants.getText( "IconAssets.ErrorIconSize", fileInformation ) );
				assetValid = false;
			}
		}

		// Check if the asset is being overwritten, if so prompt the user for action
		File assetFile = new File( controller.getProjectFolder( ), getCategoryFolder( assetCategory ) + "/" + assetFilename );
		if( assetValid && assetFile.exists( ) && !controller.showStrictConfirmDialog( TextConstants.getText( "Assets.AddAsset" ), TextConstants.getText( "Assets.WarningAssetFound", assetFilename ) ) ) {
			// If the user accepts to overwrite the asset, delete it first
			deleteAsset( assetPath, false );
			assetValid = false;
		}

		return assetValid;
	}

	public static void checkAssetFilesConsistency ( List<Incidence> incidences ){
		List<String> assetPaths = new ArrayList<String>();
		List<Integer> assetTypes = new ArrayList<Integer>();
		Controller controller = Controller.getInstance( );
		controller.getAssetReferences( assetPaths, assetTypes );
		
		
		for (int i=0; i<assetPaths.size( ); i++){
			boolean assetValid = true;
			String assetPath = assetPaths.get( i );
			int assetCategory = assetTypes.get( i ).intValue( );
			String message = "";
			boolean notPresent = true;
			
			// Take the instance of the controller, and the filename of the asset
			File file = new File(controller.getProjectFolder( ), assetPath);
			if (assetCategory == CATEGORY_ANIMATION){
				file = new File (controller.getProjectFolder( ), assetPath+"_01.png");
				if (!file.exists( )){
					file = new File (controller.getProjectFolder( ), assetPath+"_01.jpg");
				}
			}
			assetValid = file.exists( ) && file.isFile( ) && file.length( )>0;
			if (!assetValid){
				message = TextConstants.getText( "Error.AssetNotFound"+assetCategory, assetPath );
			}
			
			// For images, only background and icon are checked
			if( assetValid && (assetCategory == CATEGORY_BACKGROUND || assetCategory == CATEGORY_ICON) ) {
				// Take the data from the file
				Image image = getImage( assetPath );
				int width = image.getWidth( null );
				int height = image.getHeight( null );

				// Prepare the string array for the error message
				String[] fileInformation = new String[] { assetPath, String.valueOf( width ), String.valueOf( height ) };

				// The background files must have a size of at least 800x400
				if( assetCategory == CATEGORY_BACKGROUND && ( width < 800 || height < 400 ) ) {
					message =  TextConstants.getText( "BackgroundAssets.ErrorBackgroundSize", fileInformation ) ;
					assetValid = false; notPresent = false;
				}

				// The icon files must have a size of 80x48
				else if( assetCategory == CATEGORY_ICON && ( width != 80 || height != 48 ) ) {
					message = TextConstants.getText( "IconAssets.ErrorIconSize", fileInformation ) ;
					assetValid = false; notPresent = false;
				}
			}
			
			if (!assetValid){
				incidences.add( Incidence.createAssetIncidence( notPresent, assetCategory, message, assetPath ) );
			}
		}
	}
	
	/**
	 * Returns the extension of the given asset.
	 * 
	 * @param assetPath
	 *            Path to the asset
	 * @return Extension of the file
	 */
	private static String getExtension( String assetPath ) {
		return assetPath.substring( assetPath.lastIndexOf( '.' ) + 1, assetPath.length( ) );
	}

	/**
	 * Returns the folder associated with the given category.
	 * 
	 * @param assetsCategory
	 *            Category for the folder
	 * @return Folder for the given category, null if the category was not recognized
	 */
	public static String getCategoryFolder( int assetsCategory ) {
		String folder = null;

		switch( assetsCategory ) {
			case CATEGORY_ASSESSMENT:
				folder = CATEGORY_ASSESSMENT_FOLDER;
				break;
			case CATEGORY_ADAPTATION:
				folder = CATEGORY_ADAPTATION_FOLDER;
				break;
			case CATEGORY_BACKGROUND:
				folder = CATEGORY_BACKGROUND_FOLDER;
				break;
			case CATEGORY_ANIMATION:
				folder = CATEGORY_ANIMATION_FOLDER;
				break;
			case CATEGORY_IMAGE:
				folder = CATEGORY_IMAGE_FOLDER;
				break;
			case CATEGORY_ICON:
				folder = CATEGORY_ICON_FOLDER;
				break;
			case CATEGORY_AUDIO:
				folder = CATEGORY_AUDIO_PATH;
				break;
			case CATEGORY_VIDEO:
				folder = CATEGORY_VIDEO_PATH;
				break;
			case CATEGORY_CURSOR:
				folder = CATEGORY_CURSOR_PATH;
				break;
			case CATEGORY_STYLED_TEXT:
				folder = CATEGORY_STYLED_TEXT_PATH;
				break;

		}

		return folder;
	}

	/**
	 * Returns the file filter associated with the given category and filter.
	 * 
	 * @param assetsCategory
	 *            Category of the asset
	 * @param filter
	 *            Specific filter for the category
	 * @return File filter for the category and filter
	 */
	public static FileFilter getAssetsFileFilter( int assetsCategory, int filter ) {
		FileFilter fileFilter = null;

		switch( assetsCategory ) {
			case CATEGORY_ASSESSMENT:
			case CATEGORY_ADAPTATION:
				fileFilter = new XMLFileFilter( );
				break;
			case CATEGORY_BACKGROUND:
				if( filter == FILTER_NONE )
					fileFilter = new ImageFileFilter( );
				if( filter == FILTER_JPG )
					fileFilter = new JPGFileFilter( );
				if( filter == FILTER_PNG )
					fileFilter = new PNGFileFilter( );
				break;
			case CATEGORY_ANIMATION:
				if( filter == FILTER_NONE )
					fileFilter = new AnimationFileFilter( );
				if( filter == FILTER_JPG )
					fileFilter = new JPGSlidesFileFilter( );
				if( filter == FILTER_PNG )
					fileFilter = new PNGAnimationFileFilter( );
				break;
			case CATEGORY_IMAGE:
			case CATEGORY_CURSOR:
			case CATEGORY_ICON:
				fileFilter = new ImageFileFilter( );
				break;
			case CATEGORY_AUDIO:
				if( filter == FILTER_NONE )
					fileFilter = new AudioFileFilter( );
				if( filter == FILTER_MP3 )
					fileFilter = new MP3FileFilter( );
				break;
			case CATEGORY_VIDEO:
				fileFilter = new VideoFileFilter( );
				break;
			case CATEGORY_STYLED_TEXT:
				fileFilter = new FormattedTextFileFilter( );
				break;
		}

		return fileFilter;
	}

	public static AssetChooser getAssetChooser( int category, int filter ) {
		AssetChooser assetChooser = null;
		switch( category ) {
			case CATEGORY_ANIMATION:
				assetChooser = new AnimationChooser( filter );
				break;
			case CATEGORY_ICON:
				assetChooser = new IconChooser( filter );
				break;
			case CATEGORY_IMAGE:
				assetChooser = new ImageChooser( filter );
				break;
			case CATEGORY_BACKGROUND:
				assetChooser = new BackgroundChooser( filter );
				break;
			case CATEGORY_AUDIO:
				assetChooser = new AudioChooser( filter );
				break;
			case CATEGORY_VIDEO:
				assetChooser = new VideoChooser( );
				break;
			case CATEGORY_CURSOR:
				assetChooser = new CursorChooser( );
				break;
			case CATEGORY_STYLED_TEXT:
				assetChooser = new FormatedTextChooser( );
				break;

		}
		return assetChooser;
	}

	/*private static class VideoCache {

		private static Random random;

		private static int MAX_RANDOM = 100000;

		private static final String TEMP_FILE_NAME = "$temp_EAD_";

		private ArrayList<String> assetPaths;

		private ArrayList<String> cacheFiles;

		public VideoCache( ) {
			assetPaths = new ArrayList<String>( );
			cacheFiles = new ArrayList<String>( );
			random = new Random( );
		}

		public void clean( ) {
			for( int i = 0; i < cacheFiles.size( ); i++ ) {
				if( isTempFile( cacheFiles.get( i ) ) ) {
					File file = new File( cacheFiles.get( i ) );
					file.delete( );
				}
			}

		}

		private boolean isTempFile( String absolutePath ) {
			//return absolutePath.contains( TEMP_FILE_NAME );
			return false;
		}

		public void cacheVideo( String videoAsset ) {
			String zipFile = Controller.getInstance( ).getProjectFolder( );
			String absolutePath = generateTempFileAbsolutePath( getExtension( videoAsset ) );

			File sourceFile = new File( zipFile, videoAsset );
			if( sourceFile.exists( ) )
				sourceFile.copyTo( new File( absolutePath ) );

			cacheVideo( videoAsset, absolutePath );

		}

		public void reset( ) {
			assetPaths = new ArrayList<String>( );
			cacheFiles = new ArrayList<String>( );

		}

		private int findVideo( ArrayList<String> list, String path ) {
			int position = -1;
			for( int i = 0; i < list.size( ); i++ ) {
				if( list.get( i ).equals( path ) ) {
					position = i;
					break;
				}
			}
			return position;
		}

		public boolean isVideoCachedZip( String assetPath ) {
			return findVideo( this.assetPaths, assetPath ) != -1;
		}

		public boolean isVideoCachedFile( String absolutePath ) {
			return findVideo( this.cacheFiles, absolutePath ) != -1;
		}

		public void cacheVideo( String assetPath, String absolutePath ) {
			int absPathPosition = findVideo( this.assetPaths, assetPath );
			if( absPathPosition == -1 ) {
				assetPaths.add( assetPath );
				cacheFiles.add( absolutePath );
			} else {
				assetPaths.remove( absPathPosition );
				cacheFiles.remove( absPathPosition );
				assetPaths.add( absPathPosition, assetPath );
				cacheFiles.add( absPathPosition, absolutePath );
			}
		}

		public MediaLocator fetchVideo( String assetPath ) {
			String absolutePath = null;

			String[] assetsList = AssetsController.getAssetsList( CATEGORY_VIDEO );
			int position = -1;
			//If it is in the zip
			for( int i = 0; i < assetsList.length; i++ ) {
				if( assetsList[i].equals( assetPath ) ) {
					position = i;
				}
			}
			if( position != -1 ) {
				int posInCache = findVideo( assetPaths, assetPath );
				//It is already in cache
				if( posInCache != -1 ) {
					absolutePath = cacheFiles.get( posInCache );
				}
				//It it is not in cache, put it in
				else {
					String zipFile = Controller.getInstance( ).getProjectFolder( );

					// Add the file
					absolutePath = generateTempFileAbsolutePath( getExtension( assetPath ) );
					File sourceFile = new File( zipFile, assetPath );
					if( sourceFile.exists( ) )
						sourceFile.copyTo( new File( absolutePath ) );

				}
			}

			File destinyFile = new File( absolutePath );
			try {
				return new MediaLocator( destinyFile.toURI( ).toURL( ) );
			} catch( MalformedURLException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace( );
				return null;
			}
		}

		public static String generateTempFileAbsolutePath( String extension ) {
			String tempDirectory = null;
			if( System.getenv( "TEMP" ) != null && !System.getenv( "TEMP" ).equals( "" ) ) {
				tempDirectory = System.getenv( "TEMP" );
			} else if( System.getenv( "HOME" ) != null && !System.getenv( "HOME" ).equals( "" ) ) {
				tempDirectory = System.getenv( "HOME" );
			} else if( System.getenv( "ROOT" ) != null && !System.getenv( "ROOT" ).equals( "" ) ) {
				tempDirectory = System.getenv( "ROOT" );
			} else {
				tempDirectory = "";
			}

			String fileName = TEMP_FILE_NAME + random.nextInt( MAX_RANDOM ) + "." + extension;
			File file = new File( tempDirectory + "\\" + fileName );
			while( file.exists( ) ) {
				fileName = TEMP_FILE_NAME + random.nextInt( MAX_RANDOM ) + "." + extension;
				file = new File( tempDirectory + "\\" + fileName );
			}
			return tempDirectory + "\\" + fileName;

		}

	}*/

	public static class TempFileGenerator {
		private static Random random=new Random();

		private static int MAX_RANDOM = 100000;

		private static final String TEMP_FILE_NAME = "$temp_EAD_";
		
		public TempFileGenerator(){
			random = new Random();
		}

		public static String generateTempFileAbsolutePath( String extension ) {
			String tempDirectory = null;
			if( System.getenv( "TEMP" ) != null && !System.getenv( "TEMP" ).equals( "" ) ) {
				tempDirectory = System.getenv( "TEMP" );
			} else if( System.getenv( "HOME" ) != null && !System.getenv( "HOME" ).equals( "" ) ) {
				tempDirectory = System.getenv( "HOME" );
			} else if( System.getenv( "ROOT" ) != null && !System.getenv( "ROOT" ).equals( "" ) ) {
				tempDirectory = System.getenv( "ROOT" );
			} else {
				tempDirectory = "";
			}

			String fileName = TEMP_FILE_NAME + random.nextInt( MAX_RANDOM ) + "." + extension;
			File file = new File( tempDirectory + "\\" + fileName );
			while( file.exists( ) ) {
				fileName = TEMP_FILE_NAME + random.nextInt( MAX_RANDOM ) + "." + extension;
				file = new File( tempDirectory + "\\" + fileName );
			}
			return tempDirectory + "\\" + fileName;

		}

	}
	
    /**
     * Extracts the resource and get it copied to a file in the local system. 
     * Required when an asset cannot be loaded directly from zip
     * @param assetPath
     * @return
     *      The absolute path of the destiny file where the asset was copied
     */
    public static String extractResource (String assetPath){
        String toReturn =null;
        try{
        	//Check if the file has already been extracted
        	if (!tempFiles.containsKey( assetPath )){
        	
	            //String filePath = VideoCache.generateTempFileAbsolutePath (getExtension(assetPath));
        		String filePath = TempFileGenerator.generateTempFileAbsolutePath (getExtension(assetPath));
	            File sourceFile = new File(Controller.getInstance( ).getProjectFolder( ), assetPath);
	            File destinyFile = new File(filePath);
	            if (sourceFile.copyTo( destinyFile )){
	            	tempFiles.put( assetPath, destinyFile );
	                toReturn = filePath;
	            }
	            else
	                toReturn = null;
        	}else
        		toReturn = tempFiles.get( assetPath ).getAbsolutePath( );
        } catch (Exception e){
            toReturn = null;
        }
            
        return toReturn;
    }

    public static class InputStreamCreatorEditor implements InputStreamCreator{
    
    	private String absolutePath;
    	
    	public InputStreamCreatorEditor (){
    		absolutePath = null;
    	}
    	
    	public InputStreamCreatorEditor (String absolutePath){
    		this.absolutePath = absolutePath;
    	}
    	
		@Override
		public InputStream buildInputStream(String filePath) {
			if (absolutePath == null)
				return getInputStream(filePath);
			else
				try {
					return new FileInputStream(absolutePath);
				} catch (FileNotFoundException e) {
					return null;
				}
		}
	
		@Override
		public String[] listNames(String filePath) {
			if (absolutePath == null){
				File dir = new File(Controller.getInstance().getProjectFolder(), filePath);
				return dir.list();
			}
			else {
				File dir = new File(absolutePath);
				return dir.list();
			}
		}
	
    }

    public static InputStreamCreator getInputStreamCreator (){
    	return new InputStreamCreatorEditor();
    }
    
    public static InputStreamCreator getInputStreamCreator (String absolutePath){
    	return new InputStreamCreatorEditor(absolutePath);
    }


}
