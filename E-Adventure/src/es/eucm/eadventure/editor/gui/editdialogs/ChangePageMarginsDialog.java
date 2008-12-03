package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.gui.otherpanels.BookPagePreviewPanel;

/**
 * Class that shows a dialog to change the margins of a page in a book
 * 
 * 
 * @author Eugenio Marchiori
 *
 */
public class ChangePageMarginsDialog extends JDialog {

	/**
	 * Default generated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The bookPage which is being edited
	 */
	private BookPage bookPage;
	
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
	 * Constructor with a bookPage and an image for the background,
	 * displays the dialog
	 * 
	 * @param bookPage The BookPage to be edited
	 * @param background The image to display in the background
	 */
	public ChangePageMarginsDialog(BookPage bookPage, Image background) {
		super();
		this.bookPage = bookPage;
		this.background = background;
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setModal(true);
		this.setLayout(new BorderLayout());
		this.setTitle(TextConstants.getText("BookPage.MarginDialog"));

		bookPagePreview = new BookPagePreviewPanel(null, bookPage, background);
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
		marginBottomSlider = new JSlider(JSlider.VERTICAL, 0, 150, (bookPage!=null)?bookPage.getMarginBottom( ):0);
		marginBottomSlider.setMajorTickSpacing( 15 );
		marginBottomSlider.setMinorTickSpacing( 5 );
		marginBottomSlider.setPaintTicks( true );
		marginBottomSlider.setPaintLabels( false );
		marginBottomSlider.setEnabled( bookPage!=null );
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
		marginTopSlider = new JSlider(JSlider.VERTICAL, -150, 0, (bookPage!=null)?-bookPage.getMarginTop( ):0);
		marginTopSlider.setMajorTickSpacing( 15 );
		marginTopSlider.setMinorTickSpacing( 5 );
		marginTopSlider.setPaintTicks( true );
		marginTopSlider.setPaintLabels( false );
		marginTopSlider.setEnabled( bookPage!=null );
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
		marginEndSlider = new JSlider(JSlider.HORIZONTAL, -150, 0, (bookPage!=null)?-bookPage.getMarginEnd( ):0);
		marginEndSlider.setMajorTickSpacing( 15 );
		marginEndSlider.setMinorTickSpacing( 5 );
		marginEndSlider.setPaintTicks( true );
		marginEndSlider.setPaintLabels( false );
		marginEndSlider.setEnabled( bookPage!=null );
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
		marginSlider = new JSlider(JSlider.HORIZONTAL, 0, 150, (bookPage!=null)?bookPage.getMargin( ):0);
		marginSlider.setMajorTickSpacing( 15 );
		marginSlider.setMinorTickSpacing( 5 );
		marginSlider.setPaintTicks( true );
		marginSlider.setPaintLabels( false );
		marginSlider.setEnabled( bookPage!=null );
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
		this.bookPage.setMargin( marginSlider.getValue( ) );
		this.bookPage.setMarginTop(- marginTopSlider.getValue( ) );
		this.bookPage.setMarginBottom( marginBottomSlider.getValue( ) );
		this.bookPage.setMarginEnd(- marginEndSlider.getValue( ) );
		this.remove(bookPagePreview);
		bookPagePreview = new BookPagePreviewPanel(null, bookPage, background);
		this.add(bookPagePreview, BorderLayout.CENTER);
		bookPagePreview.updateUI();
	}

}
