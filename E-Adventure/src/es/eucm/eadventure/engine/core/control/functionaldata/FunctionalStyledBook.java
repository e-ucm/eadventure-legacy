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
package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Component;
import java.util.ArrayList;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.gui.GUI;

public class FunctionalStyledBook extends FunctionalBook {

    /**
     * List of functional pages.
     */
    private ArrayList<FunctionalBookPage> functionalPages;

    public FunctionalStyledBook( Book b ) {
        super( b );
        //Create EditorPanes
        functionalPages = new ArrayList<FunctionalBookPage>( );

        //Get the background image
        Resources resources = null;
        for( int i = 0; i < book.getResources( ).size( ) && resources == null; i++ )
            if( new FunctionalConditions( book.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                resources = book.getResources( ).get( i );

        //Image background = MultimediaManager.getInstance( ).loadImageFromZip( resources.getAssetPath( Book.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );

        //ADD the functional pages: only those valid are used 
        for( BookPage pageURL : book.getPageURLs( ) ) {
            FunctionalBookPage newFPage = new FunctionalBookPage( pageURL, this, background, arrowLeftNormal, arrowRightNormal, previousPage, nextPage, false );
            //newFPage.addKeyListener( Game.getInstance( ) );
            
            
            if( newFPage.isValid( ) ) {
                functionalPages.add( newFPage );
            }
        }

        this.numPages = functionalPages.size( );

        //If no valid pages were found a blank page is added
        if( numPages == 0 ) {
            functionalPages.add( new FunctionalBookPage( background ) );
        }
        this.currentPage = 0;

        //Show the first page
        GUI.getInstance( ).showComponent( functionalPages.get( currentPage ) );

    }

    @Override
    public boolean isInLastPage( ) {

        return currentPage == numPages - 1;
    }
    
    @Override
    public boolean isInFirstPage( ) {

        return currentPage == 0;
    }

    @Override
    public void nextPage( ) {

        if( currentPage < numPages - 1 ) {
            // We put the normal arrow image. If we don't put this method, when we return
            // to this page, the image for the arrow is still the over image
            functionalPages.get( currentPage ).setCurrentArrowRight( arrowRightNormal );
            currentPage++;
            GUI.getInstance( ).showComponent( functionalPages.get( currentPage ) );
            functionalPages.get( currentPage ).updateUI( );
        }
    }

    @Override
    public void previousPage( ) {

        if( currentPage > 0 ) {
            // We put the normal arrow image. If we don't put this method, when we return
            // to this page, the image for the arrow is still the over image
            functionalPages.get( currentPage ).setCurrentArrowLeft( arrowLeftNormal );
            currentPage--;
            GUI.getInstance( ).showComponent( functionalPages.get( currentPage ) );
            functionalPages.get( currentPage ).updateUI( );
        }
    }

    public Component getPageToDisplay( ) {

        return functionalPages.get( currentPage );
    }
    
    @Override
    public void mouseOverPreviousPage( boolean mouseOverPreviousPage ){
        if ( mouseOverPreviousPage ){
            functionalPages.get( currentPage ).setCurrentArrowLeft( arrowLeftOver );
        }
        else
            functionalPages.get( currentPage ).setCurrentArrowLeft( arrowLeftNormal );
    }
    
    @Override
    public void mouseOverNextPage( boolean mouseOverNextPage ){
        if ( mouseOverNextPage ){
            functionalPages.get( currentPage ).setCurrentArrowRight( arrowRightOver );
        }
        else
            functionalPages.get( currentPage ).setCurrentArrowRight( arrowRightNormal );
    }
    
}
