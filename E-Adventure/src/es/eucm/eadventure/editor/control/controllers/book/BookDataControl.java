/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.control.controllers.book;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.assets.AddResourcesBlockTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Controller for the book elements.
 * 
 * @author Bruno Torijano Bueno
 */
public class BookDataControl extends DataControlWithResources {

    /**
     * Contained book.
     */
    private Book book;

    /**
     * Book paragraphs list controller.
     */
    private BookParagraphsListDataControl bookParagraphsListDataControl;

    /**
     * Book pages list controller.
     */
    private BookPagesListDataControl bookPagesListDataControl;

    /**
     * Constructor.
     * 
     * @param book
     *            Contained book
     */
    public BookDataControl( Book book ) {

        this.book = book;
        this.resourcesList = book.getResources( );

        selectedResources = 0;

        // Add a new resource if the list is empty
        if( resourcesList.size( ) == 0 )
            resourcesList.add( new Resources( ) );

        // Create the subcontrollers
        resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
        for( Resources resources : resourcesList )
            resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.BOOK ) );

        if( book.getType( ) == Book.TYPE_PARAGRAPHS )
            bookParagraphsListDataControl = new BookParagraphsListDataControl( book.getParagraphs( ) );
        else if( book.getType( ) == Book.TYPE_PAGES )
            bookPagesListDataControl = new BookPagesListDataControl( book.getPageURLs( ) );
    }

    /**
     * Returns the book paragraphs list controller.
     * 
     * @return Book paragraphs list controller
     */
    public BookParagraphsListDataControl getBookParagraphsList( ) {

        return bookParagraphsListDataControl;
    }

    /**
     * Returns the book paragraphs list controller.
     * 
     * @return Book paragraphs list controller
     */
    public BookPagesListDataControl getBookPagesList( ) {

        return bookPagesListDataControl;
    }

    /**
     * Returns the id of the book.
     * 
     * @return Book's id
     */
    public String getId( ) {

        return book.getId( );
    }

    /**
     * Returns the documentation of the book.
     * 
     * @return Book's documentation
     */
    public String getDocumentation( ) {

        return book.getDocumentation( );
    }

    /**
     * Returns the path to the selected preview image.
     * 
     * @return Path to the image, null if not present
     */
    public String getPreviewImage( ) {

        return resourcesDataControlList.get( selectedResources ).getAssetPath( "background" );
    }
    
    public final static int ARROW_LEFT = 0,
                      ARROW_RIGHT = 1,
                      ARROW_NORMAL = 0,
                      ARROW_OVER = 1;
    /**
     * Get the path of image for the specified arrow.
     * @param arrowOrientation Arrow orientation. It can be <i>ARROW_LEFT</i> or <i>ARROW_RIGHT</i>
     * @param arrowState Arrow state. It can be <i>ARROW_NORMAL</i> or <i>ARROW_OVER<i>
     * @return
     */
    public String getArrowImagePath( int arrowOrientation, int arrowState ) {

        String asset = "";
        switch ( arrowOrientation ){
            case ARROW_LEFT: asset += "arrowLeft";
                break;
            case ARROW_RIGHT: asset += "arrowRight";
                break;
        }
        
        switch ( arrowState ){
            case ARROW_NORMAL: asset+= "Normal";
                break;
            case ARROW_OVER: asset += "Over";
                break;
        }
        
        return resourcesDataControlList.get( selectedResources ).getAssetPath( asset );
    }

    /**
     * Sets the new documentation of the book.
     * 
     * @param documentation
     *            Documentation of the book
     */
    public void setDocumentation( String documentation ) {

        controller.addTool( new ChangeDocumentationTool( book, documentation ) );
    }

    @Override
    public Object getContent( ) {

        return book;
    }

    @Override
    public int[] getAddableElements( ) {

        //return new int[] { Controller.RESOURCES };
        return new int[] {};
    }

    @Override
    public boolean canAddElement( int type ) {

        // It can always add new resources
        //return type == Controller.RESOURCES;
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

        return true;
    }

    @Override
    public boolean addElement( int type, String id ) {

        boolean elementAdded = false;

        if( type == Controller.RESOURCES ) {
            elementAdded = Controller.getInstance( ).addTool( new AddResourcesBlockTool( resourcesList, resourcesDataControlList, Controller.BOOK, this ) );
        }

        return elementAdded;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

        if( elementIndex > 0 ) {
            resourcesList.add( elementIndex - 1, resourcesList.remove( elementIndex ) );
            resourcesDataControlList.add( elementIndex - 1, resourcesDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

        if( elementIndex < resourcesList.size( ) - 1 ) {
            resourcesList.add( elementIndex + 1, resourcesList.remove( elementIndex ) );
            resourcesDataControlList.add( elementIndex + 1, resourcesDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public String renameElement( String name ) {

        boolean elementRenamed = false;
        String oldBookId = book.getId( );
        String references = String.valueOf( controller.countIdentifierReferences( oldBookId ) );

        // Ask for confirmation
        if( name != null || controller.showStrictConfirmDialog( TC.get( "Operation.RenameBookTitle" ), TC.get( "Operation.RenameElementWarning", new String[] { oldBookId, references } ) ) ) {

            // Show a dialog asking for the new book id
            String newBookId = name;
            if( name == null )
                newBookId = controller.showInputDialog( TC.get( "Operation.RenameBookTitle" ), TC.get( "Operation.RenameBookMessage" ), oldBookId );

            // If some value was typed and the identifiers are different
            if( newBookId != null && !newBookId.equals( oldBookId ) && controller.isElementIdValid( newBookId ) ) {
                book.setId( newBookId );
                controller.replaceIdentifierReferences( oldBookId, newBookId );
                controller.getIdentifierSummary( ).deleteBookId( oldBookId );
                controller.getIdentifierSummary( ).addBookId( newBookId );
                //controller.dataModified( );
                elementRenamed = true;
            }
        }

        if( elementRenamed )
            return oldBookId;
        else
            return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Do nothing
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        // Iterate through the resources
        for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
            String resourcesPath = currentPath + " >> " + TC.getElement( Controller.RESOURCES ) + " #" + ( i + 1 );
            valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
        }

        // Spread the call to the paragraphs
        if( book.getType( ) == Book.TYPE_PARAGRAPHS )
            valid &= bookParagraphsListDataControl.isValid( currentPath, incidences );
        else if( book.getType( ) == Book.TYPE_PAGES )
            valid &= bookPagesListDataControl.isValid( currentPath, incidences );

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            count += resourcesDataControl.countAssetReferences( assetPath );

        // Spread the call to the paragraphs/pages
        if( book.getType( ) == Book.TYPE_PARAGRAPHS )
            count += bookParagraphsListDataControl.countAssetReferences( assetPath );
        else if( book.getType( ) == Book.TYPE_PAGES )
            count += bookPagesListDataControl.countAssetReferences( assetPath );
        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

        // Spread the call to the paragraphs/pages
        if( book.getType( ) == Book.TYPE_PARAGRAPHS )
            bookParagraphsListDataControl.getAssetReferences( assetPaths, assetTypes );
        else if( book.getType( ) == Book.TYPE_PAGES )
            bookPagesListDataControl.getAssetReferences( assetPaths, assetTypes );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.deleteAssetReferences( assetPath );

        // Spread the call to the paragraphs/pages
        if( book.getType( ) == Book.TYPE_PARAGRAPHS )
            bookParagraphsListDataControl.deleteAssetReferences( assetPath );
        else if( book.getType( ) == Book.TYPE_PAGES )
            bookPagesListDataControl.deleteAssetReferences( assetPath );
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

    public int getType( ) {

        return book.getType( );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

        check( this.getDocumentation( ), TC.get( "Search.Documentation" ) );
        check( this.getId( ), "ID" );
        if( this.getBookParagraphsList( ) != null )
            this.getBookParagraphsList( ).recursiveSearch( );
        if( this.getBookPagesList( ) != null )
            this.getBookPagesList( ).recursiveSearch( );
        check( this.getPreviewImage( ), TC.get( "Search.PreviewImage" ) );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        List<Searchable> path = getPathFromChild( dataControl, resourcesDataControlList );
        if( path != null )
            return path;
        path = getPathFromChild( dataControl, bookParagraphsListDataControl );
        if( path != null )
            return path;
        return getPathFromSearchableChild( dataControl, bookPagesListDataControl );
    }

}
