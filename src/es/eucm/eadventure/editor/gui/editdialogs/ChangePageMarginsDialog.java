package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.BookPagePreviewPanel;

/**
 * Class that shows a dialog to change the margins of a page in a book
 * 
 * 
 * @author Eugenio Marchiori
 *
 */
public class ChangePageMarginsDialog extends ToolManagableDialog {

	/**
	 * Default generated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The bookPage which is being edited
	 */
	private BookPagesListDataControl bookPagesList;
	
	/**
	 * The preview panel for the page (where the page is shown)
	 */
	private BookPagePreviewPanel bookPagePreview;
	
	/**
	 * Slider for the left (start) margin
	 */
	private JSlider marginSlider;
	
	/**
	 * Slider for the right (end) margin
	 */
	private JSlider marginEndSlider;

	/**
	 * Slider for the top margin
	 */
	private JSlider marginTopSlider;

	/**
	 * Slider for the bottom margin
	 */
	private JSlider marginBottomSlider;

	/**
	 * Background image for the book
	 */
	private Image background;
	
	/**
	 * This value is only used to avoid that invoking updateFileds will modify 
	 * the values stored in dataControl, as this would add new Tools
	 */
	private boolean setChanges;
	
	/**
	 * Constructor with a bookPage and an image for the background,
	 * displays the dialog
	 * 
	 * @param bookPagesList The BookPage to be edited
	 * @param background The image to display in the background
	 */
	public ChangePageMarginsDialog(BookPagesListDataControl bookPagesList, Image background) {
		super( Controller.getInstance().peekWindow(), TextConstants.getText("BookPage.MarginDialog"), true );
		this.bookPagesList = bookPagesList;
		this.background = background;
		setChanges = true;
		this.setLayout(new BorderLayout());
		this.setTitle(TextConstants.getText("BookPage.MarginDialog"));

		bookPagePreview = new BookPagePreviewPanel(null, bookPagesList.getSelectedPage(), background);
		this.add(bookPagePreview, BorderLayout.CENTER);
		
		createMarginSlider();
		createMarginEndSlider();
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(marginSlider, BorderLayout.WEST);
		topPanel.add(marginEndSlider, BorderLayout.EAST);

		// This code is used to create a square block in the top left
		// corner of the window, for esthetic ends
		JPanel block = new JPanel();
		block.setLayout(new BorderLayout());
		JPanel block2 = new JPanel();
		block2.setPreferredSize(new Dimension(30,30));
		block.add(block2, BorderLayout.LINE_START);
		block.add(topPanel, BorderLayout.CENTER);
		
		this.add(block, BorderLayout.NORTH);
		
		createMarginTopSlider();
		createMarginBottomSlider();
		
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BorderLayout());
		sidePanel.add(marginTopSlider, BorderLayout.NORTH);
		sidePanel.add(marginBottomSlider, BorderLayout.SOUTH);
		
		this.add(sidePanel, BorderLayout.LINE_START);

		this.setSize(830,630);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );		
		this.setVisible(true);
	}

	/**
	 * Creates the marginBottomSlider
	 */
	private void createMarginBottomSlider() {
		marginBottomSlider = new JSlider(JSlider.VERTICAL, 0, 150, (bookPagesList!=null)?bookPagesList.getSelectedPage().getMarginBottom( ):0);
		marginBottomSlider.setMajorTickSpacing( 15 );
		marginBottomSlider.setMinorTickSpacing( 5 );
		marginBottomSlider.setPaintTicks( true );
		marginBottomSlider.setPaintLabels( false );
		marginBottomSlider.setEnabled( bookPagesList!=null );
		marginBottomSlider.addChangeListener( new ChangeListener(){
			public void stateChanged( ChangeEvent e ) {
				if(!marginBottomSlider.getValueIsAdjusting( )){
					marginChanged();
				}
			}
		});
		marginBottomSlider.setToolTipText( TextConstants.getText( "BookPage.MarginToolTip" ) );
	}

	/**
	 * Creates the marginTopSlider
	 */
	private void createMarginTopSlider() {
		marginTopSlider = new JSlider(JSlider.VERTICAL, -150, 0, (bookPagesList!=null)?-bookPagesList.getSelectedPage().getMarginTop( ):0);
		marginTopSlider.setMajorTickSpacing( 15 );
		marginTopSlider.setMinorTickSpacing( 5 );
		marginTopSlider.setPaintTicks( true );
		marginTopSlider.setPaintLabels( false );
		marginTopSlider.setEnabled( bookPagesList!=null );
		marginTopSlider.addChangeListener( new ChangeListener(){
			public void stateChanged( ChangeEvent e ) {
				if(!marginTopSlider.getValueIsAdjusting( )){
					marginChanged();
				}
			}
		});
		marginTopSlider.setToolTipText( TextConstants.getText( "BookPage.MarginToolTip" ) );
	}

	/**
	 * Creates the marginEndSlider
	 */
	private void createMarginEndSlider() {
		marginEndSlider = new JSlider(JSlider.HORIZONTAL, -150, 0, (bookPagesList!=null)?-bookPagesList.getSelectedPage().getMarginEnd( ):0);
		marginEndSlider.setMajorTickSpacing( 15 );
		marginEndSlider.setMinorTickSpacing( 5 );
		marginEndSlider.setPaintTicks( true );
		marginEndSlider.setPaintLabels( false );
		marginEndSlider.setEnabled( bookPagesList!=null );
		marginEndSlider.addChangeListener( new ChangeListener(){
			public void stateChanged( ChangeEvent e ) {
				if(!marginEndSlider.getValueIsAdjusting( )){
					marginChanged();
				}
			}
		});
		marginEndSlider.setToolTipText( TextConstants.getText( "BookPage.MarginToolTip" ) );
	}

	/**
	 * Creates the marginSlider
	 */
	private void createMarginSlider() {
		marginSlider = new JSlider(JSlider.HORIZONTAL, 0, 150, (bookPagesList!=null)?bookPagesList.getSelectedPage().getMargin( ):0);
		marginSlider.setMajorTickSpacing( 15 );
		marginSlider.setMinorTickSpacing( 5 );
		marginSlider.setPaintTicks( true );
		marginSlider.setPaintLabels( false );
		marginSlider.setEnabled( bookPagesList!=null );
		marginSlider.addChangeListener( new ChangeListener(){
			public void stateChanged( ChangeEvent e ) {
				if(!marginSlider.getValueIsAdjusting( )){
					marginChanged();
				}
			}
		});
		marginSlider.setToolTipText( TextConstants.getText( "BookPage.MarginToolTip" ) );
	}

	/**
	 * Method called when one of the margins is modified
	 */
	protected void marginChanged() {
		if (setChanges){
			this.bookPagesList.setMargins( marginSlider.getValue( ), - marginTopSlider.getValue( ) , marginBottomSlider.getValue( ) ,- marginEndSlider.getValue( ) );
			
			this.remove(bookPagePreview);
			bookPagePreview = new BookPagePreviewPanel(null, bookPagesList.getSelectedPage(), background);
			this.add(bookPagePreview, BorderLayout.CENTER);
			
			bookPagePreview.updateUI();
		}
	}

	
	public boolean updateFields(){
		// Temporarily deactivate user changes
		setChanges = false;
		marginSlider.setValue((bookPagesList!=null)?bookPagesList.getSelectedPage().getMargin( ):0);
		marginEndSlider.setValue((bookPagesList!=null)?-bookPagesList.getSelectedPage().getMarginEnd( ):0);
		marginTopSlider.setValue((bookPagesList!=null)?bookPagesList.getSelectedPage().getMarginTop( ):0);
		marginBottomSlider.setValue((bookPagesList!=null)?bookPagesList.getSelectedPage().getMarginBottom( ):0);
		this.remove(bookPagePreview);
		bookPagePreview = new BookPagePreviewPanel(null, bookPagesList.getSelectedPage(), background);
		this.add(bookPagePreview, BorderLayout.CENTER);
		
		bookPagePreview.updateUI();
		// Reactivate user changes
		setChanges = false;
		return true;
		
	}
}
