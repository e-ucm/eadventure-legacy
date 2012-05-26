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
package es.eucm.eadventure.editor.control.controllers.book;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;
import es.eucm.eadventure.editor.gui.editdialogs.BookTypesDialog;

public class BooksListDataControl extends DataControl {

    /**
     * List of books.
     */
    private List<Book> booksList;

    /**
     * Book controllers.
     */
    private List<BookDataControl> booksDataControlList;

    /**
     * Constructor.
     * 
     * @param booksList
     *            List of books
     */
    public BooksListDataControl( List<Book> booksList ) {

        this.booksList = booksList;

        // Create the subcontrollers
        booksDataControlList = new ArrayList<BookDataControl>( );
        for( Book book : booksList )
            booksDataControlList.add( new BookDataControl( book ) );
    }

    /**
     * Returns the list of book controllers.
     * 
     * @return Book controllers
     */
    public List<BookDataControl> getBooks( ) {

        return booksDataControlList;
    }

    /**
     * Returns the last book controller of the list.
     * 
     * @return Last book controller
     */
    public BookDataControl getLastBook( ) {

        return booksDataControlList.get( booksDataControlList.size( ) - 1 );
    }

    @Override
    public Object getContent( ) {

        return booksList;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] { Controller.BOOK };
    }

    @Override
    public boolean canAddElement( int type ) {

        // It can always add new scenes
        return type == Controller.BOOK;
    }

    @Override
    public boolean canBeDeleted( ) {

        return false;
    }

    @Override
    public boolean canBeMoved( ) {

        return false;
    }

    @Override
    public boolean canBeRenamed( ) {

        return false;
    }

    @Override
    public boolean addElement( int type, String bookId ) {

        boolean elementAdded = false;

        if( type == Controller.BOOK ) {

            int bookType = -1;
            BookTypesDialog bookTypesDialog = new BookTypesDialog( Book.TYPE_PARAGRAPHS );
            bookType = bookTypesDialog.getOptionSelected( );

            if( bookType != -1 ) {
                // Show a dialog asking for the book id
                if( bookId == null )
                    bookId = controller.showInputDialog( TC.get( "Operation.AddBookTitle" ), TC.get( "Operation.AddBookMessage" ), TC.get( "Operation.AddBookDefaultValue" ) );

                // If some value was typed and the identifier is valid
                if( bookId != null && controller.isElementIdValid( bookId ) ) {
                    // Add thew new book
                    Book newBook = new Book( bookId );
                    newBook.setType( bookType );

                    // Set default background
                    Resources resources = new Resources( );
                    resources.addAsset( "background", SpecialAssetPaths.ASSET_DEFAULT_BOOK_IMAGE );
                    resources.addAsset( "arrowLeftNormal", SpecialAssetPaths.ASSET_DEFAULT_ARROW_NORMAL );
                    resources.addAsset( "arrowLeftOver", SpecialAssetPaths.ASSET_DEFAULT_ARROW_OVER );
                    newBook.addResources( resources );

                    BookDataControl newDataControl = new BookDataControl( newBook );
                    booksList.add( newBook );
                    booksDataControlList.add( newDataControl );
                    controller.getIdentifierSummary( ).addBookId( bookId );
                    //controller.dataModified( );
                    elementAdded = true;
                }

            }

        }

        return elementAdded;
    }

    @Override
    public boolean duplicateElement( DataControl dataControl ) {

        if( !( dataControl instanceof BookDataControl ) )
            return false;

        try {
            Book newElement = (Book) ( ( (Book) ( dataControl.getContent( ) ) ).clone( ) );
            String id = newElement.getId( );
            int i = 1;
            do {
                id = newElement.getId( ) + i;
                i++;
            } while( !controller.isElementIdValid( id, false ) );
            newElement.setId( id );
            booksList.add( newElement );
            booksDataControlList.add( new BookDataControl( newElement ) );
            controller.getIdentifierSummary( ).addBookId( id );
            return true;
        }
        catch( CloneNotSupportedException e ) {
            ReportDialog.GenerateErrorReport( e, true, "Could not clone book" );
            return false;
        }
    }

    @Override
    public String getDefaultId( int type ) {

        return TC.get( "Operation.AddBookDefaultValue" );
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        boolean elementDeleted = false;
        String bookId = ( (BookDataControl) dataControl ).getId( );
        String references = String.valueOf( controller.countIdentifierReferences( bookId ) );

        // Ask for confirmation
        if( !askConfirmation || controller.showStrictConfirmDialog( TC.get( "Operation.DeleteElementTitle" ), TC.get( "Operation.DeleteElementWarning", new String[] { bookId, references } ) ) ) {
            if( booksList.remove( dataControl.getContent( ) ) ) {
                booksDataControlList.remove( dataControl );
                controller.deleteIdentifierReferences( bookId );
                controller.getIdentifierSummary( ).deleteBookId( bookId );
                //controller.dataModified( );
                elementDeleted = true;
            }
        }

        return elementDeleted;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = booksList.indexOf( dataControl.getContent( ) );

        if( elementIndex > 0 ) {
            booksList.add( elementIndex - 1, booksList.remove( elementIndex ) );
            booksDataControlList.add( elementIndex - 1, booksDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = booksList.indexOf( dataControl.getContent( ) );

        if( elementIndex < booksList.size( ) - 1 ) {
            booksList.add( elementIndex + 1, booksList.remove( elementIndex ) );
            booksDataControlList.add( elementIndex + 1, booksDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
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

        // Update the current path
        currentPath += " >> " + TC.getElement( Controller.BOOKS_LIST );

        // Iterate through the books
        for( BookDataControl bookDataControl : booksDataControlList ) {
            String bookPath = currentPath + " >> " + bookDataControl.getId( );
            valid &= bookDataControl.isValid( bookPath, incidences );
        }

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Iterate through the books
        for( BookDataControl bookDataControl : booksDataControlList )
            count += bookDataControl.countAssetReferences( assetPath );

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through the books
        for( BookDataControl bookDataControl : booksDataControlList )
            bookDataControl.getAssetReferences( assetPaths, assetTypes );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through the books
        for( BookDataControl bookDataControl : booksDataControlList )
            bookDataControl.deleteAssetReferences( assetPath );
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
    public boolean canBeDuplicated( ) {

        return false;
    }

    @Override
    public void recursiveSearch( ) {

        for( DataControl dc : this.booksDataControlList )
            dc.recursiveSearch( );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, booksDataControlList );
    }

}
