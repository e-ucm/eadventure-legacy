package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.BookPagePreviewPanel;


public class StyledBookDialog extends JDialog{
    /**
     * X position of the upper left corner of the next page button
     */
    public static final int NEXT_PAGE_X = 685;

    /**
     * Y position of the upper left corner of the next page button
     */
    public static final int NEXT_PAGE_Y = 475;

    /**
     * X position of the upper left corner of the previous page button
     */
    public static final int PREVIOUS_PAGE_X = 45;

    /**
     * Y position of the upper left corner of the previous page button
     */
    public static final int PREVIOUS_PAGE_Y = 475;

    /**
     * Width of the change page button
     */
    public static final int CHANGE_PAGE_WIDTH = 80;

    /**
     * Height of the change page button
     */
    public static final int CHANGE_PAGE_HEIGHT = 80;

	
	
	private BookPagePreviewPanel previewPanel;
	
	private BookDataControl dataControl;
	
	private List<BookPage> pages;
	
	private Image background;
	
	private int currentPage;
	
	private int numPages;
	
	public StyledBookDialog(BookDataControl book){
		super();
		if (book.getType( ) == Book.TYPE_PAGES){
			this.dataControl = book;
			currentPage = 0;
			getContentPane( ).setLayout( new BorderLayout() );
	        this.updatePreview( );
			
	        setResizable( false );
	        setSize( new Dimension(800,600) );
	        
	        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
	        setLocation( ( screenSize.width - 800 ) / 2, ( screenSize.height - 600 ) / 2);
	        setEnabled( true );
	        setVisible( true );
	        setFocusable( true );
	        requestFocus( );
	        
		}
	}
	
    /**
     * Returns whether the mouse pointer is in the "next page" button
     * @param x the horizontal position of the mouse pointer
     * @param y the vertical position of the mouse pointer
     * @return true if the mouse is in the "next page" button, false otherwise
     */
    public boolean isInNextPage( int x, int y ) {
        return ( NEXT_PAGE_X < x ) && ( x < NEXT_PAGE_X + CHANGE_PAGE_WIDTH ) && ( NEXT_PAGE_Y < y ) && ( y < NEXT_PAGE_Y + CHANGE_PAGE_HEIGHT );
    }
    
    /**
     * Returns wheter the mouse pointer is in the "previous page" button
     * @param x the horizontal position of the mouse pointer
     * @param y the vertical position of the mouse pointer
     * @return true if the mouse is in the "previous page" button, false otherwise
     */
    public boolean isInPreviousPage( int x, int y ) {
        return ( PREVIOUS_PAGE_X < x ) && ( x < PREVIOUS_PAGE_X + CHANGE_PAGE_WIDTH ) && ( PREVIOUS_PAGE_Y < y ) && ( y < PREVIOUS_PAGE_Y + CHANGE_PAGE_HEIGHT );
    }

	
    public boolean isInLastPage( ) {
        return currentPage == numPages-1; 
    }

    public void nextPage( ) {
        if (currentPage<numPages-1){
            currentPage++;
            previewPanel = new BookPagePreviewPanel(this,pages.get( currentPage ),background);
            getContentPane().removeAll( );
            getContentPane().add(previewPanel, BorderLayout.CENTER);
            previewPanel.updateUI();
        }
    }

    public void previousPage( ) {
        if (currentPage>0){
            currentPage--;
            previewPanel = new BookPagePreviewPanel(this,pages.get( currentPage ),background);
            getContentPane().removeAll( );
            getContentPane().add(previewPanel, BorderLayout.CENTER);
            previewPanel.updateUI();
        }
    }

	public void mouseClicked ( int x, int y){
        if( isInPreviousPage( x, y ) )
            previousPage( );

        else if( isInNextPage( x, y ) ) {
            nextPage( );
        }
	
	}
    
	public void updatePreview(){
		pages = dataControl.getBookPagesList( ).getBookPages( );
		numPages = pages.size( );
		this.setTitle( "Preview of the book: "+dataControl.getId( ) );

		//Get the background image
        String backgroundPath =  dataControl.getPreviewImage( );
        if (backgroundPath!=null && backgroundPath.length( )>0)
        	background = AssetsController.getImage( backgroundPath );
        else
        	background = null;

        if (currentPage<0 || currentPage>=numPages)
        	currentPage=0;
        
		if (numPages>0){			
			previewPanel = new BookPagePreviewPanel(this, pages.get( currentPage ), background);
			getContentPane( ).add( previewPanel, BorderLayout.CENTER );	
		} else {
			getContentPane( ).add( new JPanel(), BorderLayout.CENTER );
		}
	}
}
