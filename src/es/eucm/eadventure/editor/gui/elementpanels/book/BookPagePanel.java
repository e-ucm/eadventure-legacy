package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ChangePageMarginsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.HTMLEditDialog;

public class BookPagePanel extends JPanel{

	private BookPagesListDataControl dataControl;
	
	private BookPage bookPage;

	/**
	 * Radio button for the "Traditional" option.
	 */
	private JRadioButton resourceRadioButton;

	/**
	 * Radio button for the "Contextual" option.
	 */
	private JRadioButton urlRadioButton;
	
	private JLabel validPage;
	
	private Icon valid;
	
	private Icon notValid;
	
	private URLChangeListener urlListener;
	
	private JTextField pathTextField;
	
	private JButton selectButton;
	
	private JButton createButton;
	
	private JButton changeMargins;
	
	private BookPagesPanel parent;
	
	public BookPagePanel (BookPagesPanel parentPanel, BookPagesListDataControl pagesDataControl, int pageIndex){
		dataControl = pagesDataControl;
		this.parent = parentPanel;
		bookPage = pagesDataControl.getSelectedPage( );

		// Set the values

		// Panel with the report options
		JPanel pageTypePanel = new JPanel( );
		pageTypePanel.setLayout( new GridBagLayout( ) );
		pageTypePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookPageType.Title" ) ) );

		// Traditional radio button
		GridBagConstraints c1 = new GridBagConstraints( );
		c1.insets = new Insets( 5, 5, 3, 5 );
		c1.gridx = 0;
		c1.gridy = 0;
		c1.anchor = GridBagConstraints.LINE_START;
		c1.fill = GridBagConstraints.NONE;
		resourceRadioButton = new JRadioButton( TextConstants.getText( "BookPageType.Resource" ) );
		pageTypePanel.add( resourceRadioButton, c1 );

		// Traditional description
		c1.insets = new Insets( 0, 5, 5, 5 );
		c1.gridy = 1;
		c1.weightx = 1;
		c1.fill = GridBagConstraints.BOTH;
		c1.anchor = GridBagConstraints.CENTER;
		JTextPane xmlReportInfo = new JTextPane( );
		xmlReportInfo.setEditable( false );
		xmlReportInfo.setBackground( getBackground( ) );
		xmlReportInfo.setText( TextConstants.getText( "BookPageType.ResourceDescription" ) );
		pageTypePanel.add( xmlReportInfo, c1 );

		// Contextual radio button
		c1.insets = new Insets( 5, 5, 3, 5 );
		c1.gridy = 2;
		c1.fill = GridBagConstraints.NONE;
		c1.anchor = GridBagConstraints.LINE_START;
		urlRadioButton = new JRadioButton( TextConstants.getText( "BookPageType.URL" ) );
		urlRadioButton.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {}
		} );
		pageTypePanel.add( urlRadioButton, c1 );

		// Contextual description
		c1.insets = new Insets( 0, 5, 5, 5 );
		c1.gridx = 0;
		c1.gridy = 3;
		c1.weightx = 1;
		c1.fill = GridBagConstraints.BOTH;
		c1.anchor = GridBagConstraints.CENTER;
		JTextPane htmlReportInfo = new JTextPane( );
		htmlReportInfo.setEditable( false );
		htmlReportInfo.setBackground( getBackground( ) );
		htmlReportInfo.setText( TextConstants.getText( "BookPageType.URLDescription" ) );
		pageTypePanel.add( htmlReportInfo, c1 );

		// Configure the radio buttons
		resourceRadioButton.addActionListener( new OptionChangedListener( ) );
		urlRadioButton.addActionListener( new OptionChangedListener( ) );
		ButtonGroup typeButtonGroup = new ButtonGroup( );
		typeButtonGroup.add( resourceRadioButton );
		typeButtonGroup.add( urlRadioButton );
		if ( bookPage == null){
			resourceRadioButton.setSelected( false );
			resourceRadioButton.setEnabled( false );
			urlRadioButton.setSelected( false );
			urlRadioButton.setEnabled( false );
		}else if( bookPage.getType( ) == BookPage.TYPE_RESOURCE )
			resourceRadioButton.setSelected( true );
		else if( bookPage.getType( ) == BookPage.TYPE_URL )
			urlRadioButton.setSelected( true );


		//---------------- Asset path panel -------------------------------------//
		JPanel assetPathPanel = new JPanel( );
		assetPathPanel.setLayout( new GridBagLayout( ) );
		if (bookPage!=null && bookPage.getType( ) == BookPage.TYPE_RESOURCE)
			assetPathPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookPage.AssetPath" ) ) );
		else
			assetPathPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookPage.URL" ) ) );
		GridBagConstraints c2 = new GridBagConstraints( );
		c2.insets = new Insets( 4, 5, 4, 5 );
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		c2.weighty = 0;

		// Create the delete content button
		notValid = new ImageIcon( "img/icons/deleteContent.png" );
		valid = new ImageIcon( "img/icons/okIcon.png" );
		validPage = new JLabel();
		validateContentSource();
		
		validPage.setPreferredSize( new Dimension( 16, 16 ) );
		assetPathPanel.add( validPage, c2 );

		// Create the text field and insert it
		c2.gridx = 1;
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.weightx = 1;
		if (bookPage==null){
			pathTextField = new JTextField( "" );
		}else
			pathTextField = new JTextField( bookPage.getUri( ) );
		pathTextField.setEditable( bookPage!=null && bookPage.getType( ) == BookPage.TYPE_URL );
		urlListener = new URLChangeListener() ;
		pathTextField.getDocument( ).addDocumentListener( urlListener );
		assetPathPanel.add( pathTextField, c2 );

		// Create the "Select" button and insert it
		c2.gridx = 2;
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		selectButton = new JButton( TextConstants.getText( "Resources.Select" ) );
		selectButton.addActionListener( new ExamineButtonListener( ) );
		selectButton.setEnabled(bookPage!=null && ! pathTextField.isEditable( ) );
		assetPathPanel.add( selectButton, c2 );
		
		
		c2.gridx = 3;
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		createButton = new JButton( TextConstants.getText("Resources.Create"));
		createButton.addActionListener(new CreateButtonListener());
		createButton.setEnabled(bookPage != null && !pathTextField.isEditable());
		assetPathPanel.add( createButton, c2);

		//---------------- Asset path panel -------------------------------------//
		
		//---------------- Scrollable & Margin panel ----------------------------//
		
		JPanel otherOptions = new JPanel ();
		changeMargins = new JButton(TextConstants.getText("BookPage.Margin"));
		changeMargins.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String backgroundPath =  parent.getDataControl().getPreviewImage();
		        Image background;
		        if (backgroundPath!=null && backgroundPath.length( )>0)
		        	background = AssetsController.getImage( backgroundPath );
		        else
		        	background = null;
				new ChangePageMarginsDialog(bookPage, background);
				parent.updatePreview( );				
			}
		});
		changeMargins.setEnabled(bookPage!=null && ! pathTextField.isEditable( ) );
		otherOptions.add(changeMargins);
		
		// Add the panels to the principal panel
		this.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints ();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy=0;
		c.weightx = 1;
		
		// Add the principal and the buttons panel
		add( pageTypePanel, c );
		
		c.gridy = 1;
		add( assetPathPanel, c );
		
		c.gridy = 2;
		add(otherOptions, c);
		
		//Set border
		String index = TextConstants.getText( "BookPage.None" );
		String type = TextConstants.getText( "BookPage.NotApplicable" );
		if (bookPage!=null){
			index = "#"+Integer.toString( pageIndex+1 );
			if (bookPage.getType( ) == BookPage.TYPE_RESOURCE)
				type = TextConstants.getText( "BookPagesList.TypeResource" );
			else if (bookPage.getType( ) == BookPage.TYPE_URL)
				type = TextConstants.getText( "BookPagesList.TypeURL" );
		}
		
		this.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookPage.Title", new String[]{index, type} )) );
	}

	private void validateContentSource(){
		if (bookPage==null || !dataControl.isValidPage( bookPage )){
			validPage.setIcon(notValid);
			if (bookPage==null || bookPage.getType( ) == BookPage.TYPE_URL)
				validPage.setToolTipText( TextConstants.getText( "BookPage.NotValidURL" ) );
			else
				validPage.setToolTipText( TextConstants.getText( "BookPage.NotValidResource" ) );
			
		}else{
			validPage.setIcon(valid);
			validPage.setToolTipText( TextConstants.getText( "BookPage.Valid" ) );
			parent.updatePreview( );
		}
	}
	
	public void stop(){
		urlListener.stop( );
	}
	
	private class URLChangeListener implements DocumentListener{

		private Thread updater;
		private boolean stop = false;
		
		private boolean changed =false;
	
		private long lastUpdate=-1;
		
		private synchronized void setChanged(boolean changed){
			this.changed = changed;
		}
		
		private synchronized boolean hasChanged(){
			return changed;
		}

		
		private synchronized long getLastUpdate(){
			return lastUpdate;
		}
		
		private synchronized void setLastUpdate(long lastUpdate){
			this.lastUpdate = lastUpdate;
		}
		
		private synchronized boolean isStop(){
			return stop;
		}
		
		public synchronized void stop(){
			stop = true;
		}
		
		public URLChangeListener(){
			updater = new Thread(){
				public void run(){
					while (!isStop()){
						if (getLastUpdate()!=-1 && System.currentTimeMillis( )-getLastUpdate()>1000 && hasChanged()){
							if (dataControl.getSelectedPage( ).getType( ) == BookPage.TYPE_URL)
								if (dataControl.editURL( pathTextField.getText( ) )){
									validateContentSource();
									setChanged(false);
								}
						}
						try {
							Thread.sleep( 5 );
						} catch( InterruptedException e ) {	}

					}
				}
			};
			updater.start( );
		}
		
		public void changedUpdate( DocumentEvent e ) {
			//Do nothing
		}

		public void insertUpdate( DocumentEvent e ) {
			//dataControl.editURL( pathTextField.getText( ) );
			setLastUpdate( System.currentTimeMillis( ));
			setChanged(true);
		}

		public void removeUpdate( DocumentEvent e ) {
			insertUpdate(e);
		}
		
	}
	
	/**
	 * Listener for the radio buttons.
	 */
	private class OptionChangedListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			if( e.getSource( ).equals( resourceRadioButton ) ){
				dataControl.setType(BookPage.TYPE_RESOURCE);
				pathTextField.setText( dataControl.getSelectedPage( ).getUri( ) );
				pathTextField.setEditable( false );
				selectButton.setEnabled( true );
				createButton.setEnabled( true );
			}else if( e.getSource( ).equals( urlRadioButton ) ){
				dataControl.setType(BookPage.TYPE_URL);
				pathTextField.setText( dataControl.getSelectedPage( ).getUri( ) );
				pathTextField.setEditable( true );
				selectButton.setEnabled( false );
				createButton.setEnabled( false );
			}
		}
	}

	/**
	 * Listener for the examine button.
	 */
	private class ExamineButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Ask the user for an image
			//bookParagraphDataControl.setImageParagraphContent( );
			if (dataControl.getSelectedPage( ).getType( ) == BookPage.TYPE_RESOURCE){
				if (dataControl.editAssetPath( )){
					pathTextField.setText( dataControl.getSelectedPage( ).getUri( ) );
					validateContentSource(); 
				}
			} 
		}
	}
	
	
	/**
	 * Listener for the create/edit button.
	 */
	private class CreateButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Ask the user for an image
			//bookParagraphDataControl.setImageParagraphContent( );

			/*
			if (bookPage.getUri() == null || bookPage.getUri().compareTo("") == 0) {
				String newFileName = "folder..." + parent.getDataControl().getId() + "_" + dataControl.getSelectedPage() + ".html";
			}
			*/
			
			// Create new asset if no asset is selected
		
			String filename = null;
			if (!(bookPage.getUri() == null) && !(bookPage.getUri().compareTo("") == 0)) {
				filename = Controller.getInstance( ).getProjectFolder( ) + "/" + bookPage.getUri();
			}
			HTMLEditDialog bepg = new HTMLEditDialog(filename, null);
			
			File temp = new File(bepg.getHtmlEditController().getFilename());
			String uri = "assets/styledtext/" + temp.getName();
			dataControl.getSelectedPage().setUri(uri);
			pathTextField.setText( dataControl.getSelectedPage( ).getUri( ) );
			parent.updatePreview( );
			
		}
	}
	
}