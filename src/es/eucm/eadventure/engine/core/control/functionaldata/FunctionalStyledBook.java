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
package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Component;
import java.awt.Image;
import java.util.ArrayList;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalStyledBook extends FunctionalBook{

    /**
     * List of functional pages.
     */
    private ArrayList<FunctionalBookPage> functionalPages;

    public FunctionalStyledBook(Book book){
        //Create EditorPanes
        functionalPages = new ArrayList<FunctionalBookPage>();
        this.book = book;
        
        //Get the background image
        Resources resources = null;
        for( int i = 0; i < book.getResources( ).size( ) && resources == null; i++ )
            if( new FunctionalConditions(book.getResources( ).get( i ).getConditions( )).allConditionsOk( ) )
                resources = book.getResources( ).get( i );
            
        Image background = MultimediaManager.getInstance( ).loadImageFromZip( resources.getAssetPath( Book.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );
        
        //ADD the functional pages: only those valid are used 
        for (BookPage pageURL:book.getPageURLs( )){
            FunctionalBookPage newFPage= new FunctionalBookPage(pageURL, background, false);
            
            if (newFPage.isValid( )){
                functionalPages.add( newFPage );
                //System.out.println( "[LOG] FunctionalStyledBook - Constructor - Page added" );
            } else {
                //System.out.println( "[LOG] FunctionalStyledBook - Constructor - Page NOT added" );
            }
        }
        
        this.numPages = functionalPages.size( );
        
        //If no valid pages were found a blank page is added
        if (numPages == 0){
            functionalPages.add( new FunctionalBookPage(background) );
        }
        this.currentPage = 0;
        
        //System.out.println( "[LOG] FunctionalStyledBook - Constructor - TOTAL PAGES: "+numPages );
        //Show the first page
        GUI.getInstance().showComponent(functionalPages.get( currentPage ));
  
    }

    @Override
    public boolean isInLastPage( ) {
        return currentPage == numPages-1; 
    }

    @Override
    public void nextPage( ) {
        if (currentPage<numPages-1){
            currentPage++;
            GUI.getInstance().showComponent(functionalPages.get( currentPage ));
            functionalPages.get( currentPage ).updateUI( );
            //System.out.println( "NEXT PAGE" );
        }
    }

    @Override
    public void previousPage( ) {
        if (currentPage>0){
            currentPage--;
            GUI.getInstance().showComponent(functionalPages.get( currentPage ));
            functionalPages.get( currentPage ).updateUI( );
            //System.out.println( "PREVIOUS PAGE" );
        }
    }
    
    public Component getPageToDisplay(){
        return functionalPages.get( currentPage );
    }
}
