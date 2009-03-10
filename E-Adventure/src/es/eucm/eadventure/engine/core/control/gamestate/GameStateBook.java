package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.resources.Asset;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBook;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalStyledBook;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalTextBook;
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
        if (game.getBook( ).getType( ) == Book.TYPE_PARAGRAPHS){
            //System.out.println( "[LOG] GameStateBook - Constructor - Paragraphs Book" );
            book = new FunctionalTextBook( game.getBook( ) );
        }else if (game.getBook( ).getType( ) == Book.TYPE_PAGES){
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
    public void mainLoop( long elapsedTime, int fps ) {
        if (book.getBook( ).getType() == Book.TYPE_PARAGRAPHS){
            Graphics2D g = GUI.getInstance( ).getGraphics( );
            g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
            g.drawImage( background, 0, 0, null );

            ((FunctionalTextBook)book).draw( g );
        
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
    
                if( book.isInLastPage( ) ){
                    GUI.getInstance( ).restoreFrame( );
                    game.setState( Game.STATE_RUN_EFFECTS );
                }else
                    book.nextPage( );
            }
        }
        
        // Right click ends the book
        else if( e.getButton() == MouseEvent.BUTTON3 ) {
            GUI.getInstance( ).restoreFrame( );
            game.setState( Game.STATE_RUN_EFFECTS );
        }
    }
    
    /**
     * Creates the current resource block to be used
     */
    private Resources createResourcesBlock( ) {
        
        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < book.getBook( ).getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions(book.getBook( ).getResources( ).get( i ).getConditions( )).allConditionsOk( ) )
                newResources = book.getBook( ).getResources( ).get( i );

        // If no resource block is available, create a default one 
        if (newResources == null){
            newResources = new Resources();
            newResources.addAsset( new Asset( Book.RESOURCE_TYPE_BACKGROUND, ResourceHandler.DEFAULT_BACKGROUND ) );
        }
        return newResources;
    }
}
