package es.eucm.eadventure.editor.gui.elementpanels.atrezzo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class AtrezzoPanel extends JPanel implements Updateable {

	/**
	 * Requiered
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the item.
	 */
	private AtrezzoDataControl atrezzoDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Text field for the name.
	 */
	private JTextField nameTextField;

	/**
	 * Text field for the description.
	 */
	//private JTextField descriptionTextField;

	/**
	 * Text field for the detailed description.
	 */
	//private JTextField detailedDescriptionTextField;

	private JTabbedPane tabPanel;

	private JPanel docPanel;

	private LooksPanel looksPanel;

	/**
	 * Constructor.
	 * 
	 * @param atrezzoDataControl
	 *            Atrezzo controller
	 */
	public AtrezzoPanel( AtrezzoDataControl atrezzoDataControl ) {

		// Create the panels and layouts
		tabPanel = new JTabbedPane( );
		docPanel = new JPanel( );
		docPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints cDoc = new GridBagConstraints( );
		looksPanel = new AtrezzoLooksPanel( atrezzoDataControl );

		// Set the controller
		this.atrezzoDataControl = atrezzoDataControl;

		// Set the layout
		setLayout( new BorderLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Item.Title" ) ) );
		//GridBagConstraints c = new GridBagConstraints( );
		cDoc.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		cDoc.fill = GridBagConstraints.HORIZONTAL;
		cDoc.weightx = 1;
		cDoc.weighty = 0.3;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( atrezzoDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) atrezzoDataControl.getContent() ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Atrezzo.Documentation" ) ) );
		docPanel.add( documentationPanel, cDoc );

		// Create the field for the name
		cDoc.gridy = 1;
		cDoc.weighty = 0;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( atrezzoDataControl.getName( ) );
		nameTextField.getDocument().addDocumentListener(new NameChangeListener(nameTextField, (Named) atrezzoDataControl.getContent()));
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Atrezzo.Name" ) ) );
		docPanel.add( namePanel, cDoc );
		
		cDoc.gridy = 2;
		cDoc.fill = GridBagConstraints.BOTH;
		cDoc.weightx = 1;
		cDoc.weighty = 0.5;
		docPanel.add( new JFiller(),cDoc );

		// Add the tabs
		//Finally, add lookPanel to its scrollPane container, and insert it as a tab along with docPanel

		tabPanel.insertTab( TextConstants.getText( "Atrezzo.LookPanelTitle" ), null, looksPanel, TextConstants.getText( "Atrezzo.LookPanelTip" ), 0 );
		tabPanel.insertTab( TextConstants.getText( "Atrezzo.DocPanelTitle" ), null, docPanel, TextConstants.getText( "Atrezzo.DocPanelTip" ), 1 );
		setLayout( new BorderLayout( ) );
		add( tabPanel, BorderLayout.CENTER );

	}
	
	private class AtrezzoLooksPanel extends LooksPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Preview image panel.
		 */
		private ImagePanel imagePanel;

		public AtrezzoLooksPanel( DataControlWithResources control ) {
			super( control );
		}

		@Override
		protected void createPreview( ) {
			atrezzoDataControl = (AtrezzoDataControl) this.dataControl;
			// Take the path to the current image of the atrezzo item
			String atrezzoImagePath = atrezzoDataControl.getPreviewImage( );

			imagePanel = new ImagePanel( atrezzoImagePath );
			imagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Atrezzo.Preview" ) ) );
			lookPanel.add( imagePanel, cLook );
			// TODO Parche, arreglar
			lookPanel.setPreferredSize( new Dimension( 0, 90 ) );

		}

		@Override
		public void updatePreview( ) {
			imagePanel.loadImage( atrezzoDataControl.getPreviewImage( ) );
			imagePanel.repaint( );

		}

		public void updateResources( ) {
			super.updateResources( );
			getParent( ).repaint( );
		}

	}

	public boolean updateFields() {
		this.documentationTextArea.setText(this.atrezzoDataControl.getDocumentation());
		this.nameTextField.setText(this.atrezzoDataControl.getName());
		this.looksPanel.updateResources();
		this.looksPanel.updatePreview();
		return true;
	}
	
}
