package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.BookPagePreviewPanel;
import es.eucm.eadventure.engine.core.gui.GUI;

public class BookPagesPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BookDataControl dataControl;
	
	private JPanel pagesPanel;
	
	private PagesTable pagesTable;
	
	private JPanel previewPanelContainer;
	
	private JScrollPane previewPanelScroll;
	
	private BookPagePreviewPanel previewPanel;
	
	private JPanel pageNotLoadedPanel;
	
	private JPanel pageEditionPanel;
	
	private JPanel pageEditionPanelContainer;
	
	private JButton deleteButton;
	
	private JButton moveUpButton;
	
	private JButton moveDownButton;
	
	private JLabel previewLabel;

	private JSplitPane infoAndPreview;
	
	private JSplitPane splitAndTable;
	
	private JPanel createPageNotLoadedPanel (){
		JPanel panel=new JPanel();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;

		panel.add(new JLabel("Page not available"), c);
		return panel;
	}
	
	public BookPagesPanel (BookDataControl dControl){
		this.dataControl = dControl;
		
		createPagesPanel();
		
		previewPanelContainer = new JPanel();
		previewPanelContainer.setLayout( new BorderLayout() );
		previewPanelContainer.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), TextConstants.getText("BookPages.Preview")) );
		
		// Create the info panel
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "BookPages.PreviewDescription2" ) );
		previewPanelContainer.add( informationTextPane, BorderLayout.NORTH );
		this.pageNotLoadedPanel = this.createPageNotLoadedPanel( );
		previewPanelScroll = new JScrollPane(pageNotLoadedPanel);
		previewPanelContainer.add( previewPanelScroll, BorderLayout.CENTER );
		
		pageEditionPanelContainer = new JPanel();
		pageEditionPanelContainer.setLayout( new BorderLayout() );
		
		updateSelectedPage();
		
		infoAndPreview = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				pageEditionPanelContainer, previewPanelContainer);	
		infoAndPreview.setOneTouchExpandable(true);
		infoAndPreview.setResizeWeight(0.5);
		infoAndPreview.setDividerSize(10);
		infoAndPreview.setContinuousLayout(true);
		infoAndPreview.setDividerLocation(320);
		
		//JPanel leftPanel = new JPanel();
		//leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		pageEditionPanelContainer.setMinimumSize( new Dimension(100,300) );
		previewPanelContainer.setMinimumSize( new Dimension(100,150) );
		pagesPanel.setMinimumSize( new Dimension (150,0) );
		// create split pane with two panels: infoAndPreview panel and table
		splitAndTable = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				infoAndPreview, pagesPanel);
		splitAndTable.setOneTouchExpandable(true);
		splitAndTable.setDividerLocation(490);
		splitAndTable.setDividerSize(10);
		splitAndTable.setResizeWeight(0.5);
		
		setLayout( new BorderLayout( ) );
		add(splitAndTable,BorderLayout.CENTER);
	}
	
	public void updatePreview(){

		BookPage currentPage = dataControl.getBookPagesList( ).getSelectedPage( );
		if (pageNotLoadedPanel!=null)
			previewPanelContainer.remove( previewPanelScroll );
		else if (previewLabel!=null)
			previewPanelContainer.remove( previewLabel );
		
		pageNotLoadedPanel = null;
		if (currentPage!=null){
			//Get the background image
	        String backgroundPath =  dataControl.getPreviewImage( );
	        Image background;
	        if (backgroundPath!=null && backgroundPath.length( )>0)
	        	background = AssetsController.getImage( backgroundPath );
	        else
	        	background = null;
	
			previewPanel = new BookPagePreviewPanel(null, currentPage, background);	
			previewPanel.setPreferredSize(new Dimension(GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT));
			previewPanel.repaint();
			Image image = previewPanel.paintToImage();

			previewLabel = new JLabel();
			previewLabel.setIcon(new ImageIcon(getResizedImage(image)));
			
			previewPanelContainer.add(previewLabel, BorderLayout.CENTER );
			
			previewPanelContainer.updateUI( );
		}
		
	}
	
	private void updateSelectedPage(){
		int selectedPage = pagesTable.getSelectedRow( );
		dataControl.getBookPagesList( ).changeCurrentPage( selectedPage );
		if (pageEditionPanel!=null && pageEditionPanel instanceof BookPagePanel)
			((BookPagePanel)pageEditionPanel).stop();
		pageEditionPanel = new BookPagePanel(this, dataControl.getBookPagesList( ), selectedPage);

		// No valid row is selected
		if (selectedPage<0 || selectedPage>=dataControl.getBookPagesList( ).getBookPages( ).size( )){
			//Disable delete button
			deleteButton.setEnabled( false );
			//Disable moveUp and moveDown buttons
			moveUpButton.setEnabled( false );
			moveDownButton.setEnabled( false );
			if (pageNotLoadedPanel!=null)
				previewPanelContainer.remove( previewPanelScroll );
			else if (previewLabel!=null)
				previewPanelContainer.remove( previewLabel );
			
			pageNotLoadedPanel = this.createPageNotLoadedPanel( );
			previewLabel = null;
			previewPanelScroll = new JScrollPane(pageNotLoadedPanel);
			previewPanelContainer.add( previewPanelScroll, BorderLayout.CENTER );
		}
		
		//When a page has been selected
		else {
			// Enable delete button
			deleteButton.setEnabled( true );
			//Enable moveUp and moveDown buttons when there is more than one element
			moveUpButton.setEnabled( dataControl.getBookPagesList( ).getBookPages( ).size( )>1 && selectedPage>0);
			moveDownButton.setEnabled( dataControl.getBookPagesList( ).getBookPages( ).size( )>1 && selectedPage<pagesTable.getRowCount( )-1 );
			BookPage currentPage = dataControl.getBookPagesList( ).getSelectedPage( );
			if (pageNotLoadedPanel!=null)
				previewPanelContainer.remove( previewPanelScroll);
			else if (previewPanel!=null)
				previewPanelContainer.remove( previewPanelScroll );
			
			pageNotLoadedPanel = null;
			//Get the background image
	        String backgroundPath =  dataControl.getPreviewImage( );
	        Image background;
	        if (backgroundPath!=null && backgroundPath.length( )>0)
	        	background = AssetsController.getImage( backgroundPath );
	        else
	        	background = null;

			previewPanel = new BookPagePreviewPanel(null, currentPage, background);	
			previewPanel.setPreferredSize(new Dimension(GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT));
			previewPanel.repaint();
			Image image = previewPanel.paintToImage();
			previewLabel = new JLabel();
			previewLabel.setIcon(new ImageIcon(getResizedImage(image)));
			previewPanelContainer.add(previewLabel, BorderLayout.CENTER );
		}
		
		pageEditionPanelContainer.removeAll( );
		pageEditionPanelContainer.add( pageEditionPanel, BorderLayout.CENTER );
		pageEditionPanelContainer.updateUI( );
		previewPanelContainer.updateUI( );
		previewPanelScroll.updateUI();
	}

	
    private Image getResizedImage( Image image ) {
        // set up the transform
        AffineTransform transform = new AffineTransform( );
        transform.scale( 350/(double)image.getWidth( null ), 250/(double)image.getHeight( null ) );

        // create a transparent (not translucent) image
        Image newImage = new BufferedImage( 350, 250, BufferedImage.TYPE_3BYTE_BGR );

        // draw the transformed image
        Graphics2D g = (Graphics2D) newImage.getGraphics( );
        g.drawImage( image, transform, null );
        g.dispose( );

        return newImage;
    }
    
	private void createPagesPanel(){
		// Create the main panel
		pagesPanel = new JPanel();
		pagesPanel.setLayout( new BorderLayout() );
		
		// Create the info panel (NORTH)
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "BookPages.ListDescription" , Integer.toString( dataControl.getBookPagesList( ).getBookPages( ).size( ) ) ));
		pagesPanel.add( informationTextPane, BorderLayout.NORTH );
		
		// Create the table (CENTER)
		pagesTable = new PagesTable(dataControl.getBookPagesList( ));
		pagesTable.addMouseListener( new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				// By default the JTable only selects the nodes with the left click of the mouse
				// With this code, we spread a new call everytime the right mouse button is pressed
				if( e.getButton( ) == MouseEvent.BUTTON3 ) {
					// Create new event (with the left mouse button)
					MouseEvent newEvent = new MouseEvent( e.getComponent( ), e.getID( ), e.getWhen( ), MouseEvent.BUTTON1_MASK, e.getX( ), e.getY( ), e.getClickCount( ), e.isPopupTrigger( ) );

					// Take the listeners and make the calls
					for( MouseListener mouseListener : e.getComponent( ).getMouseListeners( ) )
						mouseListener.mousePressed( newEvent );
					
				}
			}
			
			public void mouseClicked(MouseEvent evt){
				if (evt.getButton( ) == MouseEvent.BUTTON3){
					JPopupMenu menu = getCompletePopupMenu();
					menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
				}
			}
		});
		pagesTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener(){
			public void valueChanged( ListSelectionEvent e ) {
				updateSelectedPage();
			}
		});

		pagesPanel.add( new JScrollPane(pagesTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER );
		
		//Create the buttons panel (SOUTH)
		JPanel buttonsPanel = new JPanel();
		JButton newButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		newButton.setContentAreaFilled( false );
		newButton.setMargin( new Insets(0,0,0,0) );
		newButton.setToolTipText( TextConstants.getText( "BookPages.AddPage" ) );
		newButton.addMouseListener( new MouseAdapter(){
			public void mouseClicked (MouseEvent evt){
				addPage();
			}
		});
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setToolTipText( TextConstants.getText( "BookPages.DeletePage" ) );
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				deletePage();
			}
		});
		moveUpButton = new JButton(new ImageIcon("img/icons/moveNodeUp.png"));
		moveUpButton.setContentAreaFilled( false );
		moveUpButton.setMargin( new Insets(0,0,0,0) );
		moveUpButton.setToolTipText( TextConstants.getText( "BookPages.MovePageUp" ) );
		moveUpButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				movePageUp();
			}
		});
		moveDownButton = new JButton(new ImageIcon("img/icons/moveNodeDown.png"));
		moveDownButton.setContentAreaFilled( false );
		moveDownButton.setMargin( new Insets(0,0,0,0) );
		moveDownButton.setToolTipText( TextConstants.getText( "BookPages.MovePageDown" ) );
		moveDownButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				movePageDown();
			}
		});

		buttonsPanel.add( newButton );
		buttonsPanel.add( deleteButton );
		buttonsPanel.add( moveUpButton );
		buttonsPanel.add( moveDownButton );
		
		pagesPanel.add( buttonsPanel, BorderLayout.SOUTH );
		pagesPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookPages.List" ) ) );
	}
	
	/**
	 * Returns a popup menu with all the operations.
	 * 
	 * @return Popup menu with all operations
	 */
	public JPopupMenu getCompletePopupMenu( ) {
		//Create the menu
		JPopupMenu completePopupMenu = new JPopupMenu();
		
		//Add page item
		JMenuItem addPageIt = new JMenuItem(TextConstants.getText( "TreeNode.AddElement"+Controller.BOOK_PAGE ));
		addPageIt.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				addPage( );
			}
		} );
		completePopupMenu.add( addPageIt );

		// Separator
		completePopupMenu.addSeparator( );

		// Create and add the delete item
		JMenuItem deleteMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.DeleteElement" ) );
		deleteMenuItem.setEnabled( deleteButton.isEnabled( ));
		deleteMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				deletePage( );
			}
		} );
		completePopupMenu.add( deleteMenuItem );

		// Separator
		completePopupMenu.addSeparator( );

		// Create and add the move up and down item
		JMenuItem moveUpMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.MoveElementUp" ) );
		JMenuItem moveDownMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.MoveElementDown" ) );
		moveUpMenuItem.setEnabled( moveUpButton.isEnabled( ) );
		moveDownMenuItem.setEnabled( moveDownButton.isEnabled( ) );
		moveUpMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				movePageUp( );
			}
		} );
		moveDownMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				movePageDown( );
			}
		} );
		completePopupMenu.add( moveUpMenuItem );
		completePopupMenu.add( moveDownMenuItem );

		return completePopupMenu;
	}

	
	private void addPage(){
		
		if (dataControl.getBookPagesList( ).addPage( )!=null){
			
			int selectedRow = pagesTable.getSelectedRow( ); 
			//Set the new page selected (below the last selected page)
			pagesTable.clearSelection( );
			if (selectedRow != -1){
			pagesTable.getSelectionModel( ).setSelectionInterval( selectedRow+1, selectedRow+1 );
			
			}else {
				int selection = dataControl.getBookPagesList( ).getBookPages().size();
				pagesTable.getSelectionModel( ).setSelectionInterval(selection , selection);
			}
			pagesTable.updateUI( );
		}
		
	}
	
	private void deletePage( ) {
		BookPagesListDataControl pagesDataControl = dataControl.getBookPagesList( );
		BookPage bookPage = pagesDataControl.getSelectedPage( );
		if (bookPage!=null && pagesDataControl.deletePage( bookPage )){
			pagesTable.clearSelection( );
			pagesTable.updateUI( );
		}
	}

	private void movePageUp(){
		int selectedRow = pagesTable.getSelectedRow( );
		BookPagesListDataControl pagesDataControl = dataControl.getBookPagesList( );
		BookPage bookPage = pagesDataControl.getSelectedPage( );
		if (bookPage!=null && pagesDataControl.movePageUp( bookPage )){
			pagesTable.getSelectionModel( ).setSelectionInterval( selectedRow-1, selectedRow-1 );
			pagesTable.updateUI( );
		}
	}
	
	private void movePageDown(){
		int selectedRow = pagesTable.getSelectedRow( );
		BookPagesListDataControl pagesDataControl = dataControl.getBookPagesList( );
		BookPage bookPage = pagesDataControl.getSelectedPage( );
		if (bookPage!=null && pagesDataControl.movePageDown( bookPage )){
			pagesTable.getSelectionModel( ).setSelectionInterval( selectedRow+1, selectedRow+1 );
			pagesTable.updateUI( );
		}
	}

	public BookDataControl getDataControl() {
		return dataControl;
	}
}
