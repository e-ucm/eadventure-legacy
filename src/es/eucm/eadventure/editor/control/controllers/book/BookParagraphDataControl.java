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
package es.eucm.eadventure.editor.control.controllers.book;

import java.util.List;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.books.ChangeParagraphContentTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;

/**
 * Controller for the book paragraph element.
 * 
 * @author Bruno Torijano Bueno
 */
public class BookParagraphDataControl extends DataControl {

    /**
     * Contained book paragraph.
     */
    private BookParagraph bookParagraph;

    /**
     * Type of the book paragraph.
     */
    private int bookParagraphType;

    /**
     * Constructor.
     * 
     * @param bookParagraph
     *            Contained book paragraph
     */
    public BookParagraphDataControl( BookParagraph bookParagraph ) {

        this.bookParagraph = bookParagraph;

        // Store the type of the paragraph
        switch( bookParagraph.getType( ) ) {
            case BookParagraph.TITLE:
                bookParagraphType = Controller.BOOK_TITLE_PARAGRAPH;
                break;
            case BookParagraph.TEXT:
                bookParagraphType = Controller.BOOK_TEXT_PARAGRAPH;
                break;
            case BookParagraph.IMAGE:
                bookParagraphType = Controller.BOOK_IMAGE_PARAGRAPH;
                break;
            case BookParagraph.BULLET:
                bookParagraphType = Controller.BOOK_BULLET_PARAGRAPH;
                break;
        }
    }

    /**
     * Returns the type of the contained paragraph.
     * 
     * @return Type of contained book paragraph
     */
    public int getType( ) {

        return bookParagraphType;
    }

    /**
     * Returns the content of the paragraph.
     * 
     * @return Paragraph's content
     */
    public String getParagraphContent( ) {

        return bookParagraph.getContent( );
    }

    /**
     * Sets the new content for the paragraph. This method must be used only
     * with text and bullet paragraphs.
     * 
     * @param content
     *            New content for the paragtaph
     */
    public void setParagraphTextContent( String content ) {

        Controller.getInstance( ).addTool( new ChangeParagraphContentTool( bookParagraph, content ) );
    }

    /**
     * Shows a dialog to choose a new path for the given asset.
     * 
     * @param index
     *            Index of the asset
     */
    public void editImagePath( ) {

        // Get the list of assets from the ZIP file
        String selectedAsset = null;
        AssetChooser chooser = AssetsController.getAssetChooser( AssetsConstants.CATEGORY_IMAGE, AssetsController.FILTER_NONE );
        int option = chooser.showAssetChooser( controller.peekWindow( ) );
        //In case the asset was selected from the zip file
        if( option == AssetChooser.ASSET_FROM_ZIP ) {
            selectedAsset = chooser.getSelectedAsset( );
        }

        //In case the asset was not in the zip file: first add it
        else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
            boolean added = AssetsController.addSingleAsset( AssetsConstants.CATEGORY_IMAGE, chooser.getSelectedFile( ).getAbsolutePath( ) );
            if( added ) {
                selectedAsset = chooser.getSelectedFile( ).getName( );
            }
        }

        // If a file was selected
        if( selectedAsset != null ) {
            // Take the index of the selected asset
            String[] assetFilenames = AssetsController.getAssetFilenames( AssetsConstants.CATEGORY_IMAGE );
            String[] assetPaths = AssetsController.getAssetsList( AssetsConstants.CATEGORY_IMAGE );
            int assetIndex = -1;
            for( int i = 0; i < assetFilenames.length; i++ )
                if( assetFilenames[i].equals( selectedAsset ) )
                    assetIndex = i;

            Controller.getInstance( ).addTool( new ChangeParagraphContentTool( bookParagraph, assetPaths[assetIndex] ) );

        }
    }

    /**
     * Sets the new path for the paragraph. This method must be used only with
     * image paragraphs.
     */
    public void setImageParagraphContent( ) {

        // Get the list of assets from the ZIP file
        String[] assetFilenames = AssetsController.getAssetFilenames( AssetsConstants.CATEGORY_IMAGE );
        String[] assetPaths = AssetsController.getAssetsList( AssetsConstants.CATEGORY_IMAGE );

        // If the list of assets is empty, show an error message
        if( assetFilenames.length == 0 )
            controller.showErrorDialog( TC.get( "Resources.EditAsset" ), TC.get( "Resources.ErrorNoAssets" ) );

        // If not empty, select one of them
        else {
            // Let the user choose between the assets
            String selectedAsset = controller.showInputDialog( TC.get( "Resources.EditAsset" ), TC.get( "Resources.EditAssetMessage" ), assetFilenames );

            // If a file was selected
            if( selectedAsset != null ) {
                // Take the index of the selected asset
                int assetIndex = -1;
                for( int i = 0; i < assetFilenames.length; i++ )
                    if( assetFilenames[i].equals( selectedAsset ) )
                        assetIndex = i;

                Controller.getInstance( ).addTool( new ChangeParagraphContentTool( bookParagraph, assetPaths[assetIndex] ) );
            }
        }
    }

    /**
     * Deletes the content of the image paragraph. This method must be used only
     * with image paragraphs.
     */
    public void deleteImageParagraphContent( ) {

        Controller.getInstance( ).addTool( new ChangeParagraphContentTool( bookParagraph, "" ) );
    }

    @Override
    public Object getContent( ) {

        return bookParagraph;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] {};
    }

    @Override
    public boolean canAddElement( int type ) {

        return false;
    }

    @Override
    public boolean canBeDeleted( ) {

        return true;
    }

    @Override
    public boolean canBeMoved( ) {

        return true;
    }

    @Override
    public boolean canBeRenamed( ) {

        return false;
    }

    @Override
    public boolean addElement( int type, String id ) {

        return false;
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        return false;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        return false;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        return false;
    }

    @Override
    public String renameElement( String name ) {

        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Do nothing
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        // If it is an image paragraph and it's not linked to an asset, is invalid
        if( bookParagraph.getType( ) == BookParagraph.IMAGE && bookParagraph.getContent( ).length( ) == 0 ) {
            valid = false;

            // Store the incidence
            if( incidences != null )
                incidences.add( currentPath + " >> " + TC.get( "Operation.AdventureConsistencyErrorBookParagraph" ) );
        }

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        // Return 1 if it is an image paragraph and the asset matches
        return bookParagraph.getType( ) == BookParagraph.IMAGE && bookParagraph.getContent( ).equals( assetPath ) ? 1 : 0;
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // If it is an image paragraph and contains the asset, delete it
        if( bookParagraph.getType( ) == BookParagraph.IMAGE && bookParagraph.getContent( ).equals( assetPath ) ) {
            bookParagraph.setContent( "" );
        }
    }

    @Override
    public int countIdentifierReferences( String id ) {

        return 0;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        // Do nothing
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        // Do nothing
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Only if book paragraph is image
        if( bookParagraph.getType( ) == BookParagraph.IMAGE ) {
            String imagePath = bookParagraph.getContent( );
            // Search in assetPaths
            boolean add = true;
            for( String asset : assetPaths ) {
                if( asset.equals( imagePath ) ) {
                    add = false;
                    break;
                }
            }
            if( add ) {
                int last = assetPaths.size( );
                assetPaths.add( last, imagePath );
                assetTypes.add( last, AssetsConstants.CATEGORY_IMAGE );
            }
        }
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

        check( this.getParagraphContent( ), TC.get( "Search.ParagraphContent" ) );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return null;
    }

}
