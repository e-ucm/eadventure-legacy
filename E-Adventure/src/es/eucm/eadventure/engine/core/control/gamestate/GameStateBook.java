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
package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.resources.Asset;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBook;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalStyledBook;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalTextBook;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * A game main loop when a "bookscene" is being displayed
 */
public class GameStateBook extends GameState {

    /**
     * Functional book to be displayed
     */
    private FunctionalBook book;

    /**
     * Background image of the book
     */
    private Image background;

    /**
     * Creates a new GameStateBook
     */
    public GameStateBook( ) {

        super( );
        if( game.getBook( ).getType( ) == Book.TYPE_PARAGRAPHS ) {
            //System.out.println( "[LOG] GameStateBook - Constructor - Paragraphs Book" );
            book = new FunctionalTextBook( game.getBook( ) );
        }
        else if( game.getBook( ).getType( ) == Book.TYPE_PAGES ) {
            //System.out.println( "[LOG] GameStateBook - Constructor - Pages Book" );
            book = new FunctionalStyledBook( game.getBook( ) );
        }

        Resources resources = createResourcesBlock( );

        background = MultimediaManager.getInstance( ).loadImageFromZip( resources.getAssetPath( Book.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    @Override
    public void mainLoop( long elapsedTime, int fps ) {
        if( book.getBook( ).getType( ) == Book.TYPE_PARAGRAPHS ) {
            Graphics2D g = GUI.getInstance( ).getGraphics( );
            g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
            g.drawImage( background, 0, 0, null );

            ( (FunctionalTextBook) book ).draw( g );

            g.setColor( Color.WHITE );
            //g.drawString(Integer.toString( fps ), 780, 14);

            GUI.getInstance( ).endDraw( );

            g.dispose( );
        }
    }

       
    @Override
    public void mouseClicked( MouseEvent e ) {

        //System.out.println( "MOUSE CLICKED" );
        // Left click changes the page
        if( e.getButton( ) == MouseEvent.BUTTON1 ) {
            if( book.isInPreviousPage( e.getX( ), e.getY( ) ) )
                book.previousPage( );

            else if( book.isInNextPage( e.getX( ), e.getY( ) ) ) {

                if( book.isInLastPage( ) ) {
                    GUI.getInstance( ).restoreFrame( );
                    // this method also change the state to run effects
                    FunctionalEffects.storeAllEffects( new Effects( ) );
                    //game.setState( Game.STATE_RUN_EFFECTS );
                }
                else
                    book.nextPage( );
            }
        }

        // Right click ends the book
        else if( e.getButton( ) == MouseEvent.BUTTON3 ) {
            GUI.getInstance( ).restoreFrame( );
            FunctionalEffects.storeAllEffects( new Effects( ) );
            //game.setState( Game.STATE_RUN_EFFECTS );
        }
    }

    /**
     * Creates the current resource block to be used
     */
    private Resources createResourcesBlock( ) {

        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < book.getBook( ).getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( book.getBook( ).getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = book.getBook( ).getResources( ).get( i );

        // If no resource block is available, create a default one 
        if( newResources == null ) {
            newResources = new Resources( );
            newResources.addAsset( new Asset( Book.RESOURCE_TYPE_BACKGROUND, ResourceHandler.DEFAULT_BACKGROUND ) );
        }
        return newResources;
    }
}
