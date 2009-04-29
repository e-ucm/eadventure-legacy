package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class CutscenePanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Text field for the name
	 */
	private JTextField nameTextField;

	private LooksPanel looksPanel;

	/**
	 * Constructor.
	 * 
	 * @param cutsceneDataControl
	 *            Cutscene controller
	 */
	public CutscenePanel( CutsceneDataControl cutsceneDataControl ) {
		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Cutscene.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.gridwidth=1;c.gridx=0;
		c.weighty = 0.3;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( cutsceneDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) cutsceneDataControl.getContent() ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Cutscene.Documentation" ) ) );
		add( documentationPanel, c );

		// Create the label for the name
		c.gridy = 1;
		c.weighty = 0;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( cutsceneDataControl.getName( ) );
		nameTextField.getDocument().addDocumentListener( new NameChangeListener(nameTextField, (Named) cutsceneDataControl.getContent()));
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Cutscene.Name" ) ) );
		add( namePanel, c );

		
		c.gridy = 2;
		
		add( new NextScenePanel(cutsceneDataControl) , c);
		
		// Add resourcesPanel at the end
		c.gridy = 3;
		c.gridwidth = 2;
		c.gridx=0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.5;
		/*add( new JFiller( ), c );*/
		looksPanel = new CutsceneLooksPanel( cutsceneDataControl );
		looksPanel.setBorder( null );
		add( looksPanel, c );
	}

	
	
	
	private class CutsceneLooksPanel extends LooksPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private ImagePanel imagePanel;

		public CutsceneLooksPanel( DataControlWithResources control ) {
			super( control );
		}

		@Override
		protected void createPreview( ) {
			String imagePath = ((CutsceneDataControl)dataControl).getPreviewImage();
			if (imagePath == null)
				imagePath = "";
			JPanel previewPanel = new JPanel();
			previewPanel.setLayout( new BorderLayout() );
			imagePanel = new ImagePanel( imagePath );
			previewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Book.Preview" ) ) );
			previewPanel.add( imagePanel, BorderLayout.CENTER );
			lookPanel.add( previewPanel, cLook );
			lookPanel.setPreferredSize( new Dimension( 0, 90 ) );
		}

		@Override
		public void updatePreview( ) {
			String imagePath = ((CutsceneDataControl)dataControl).getPreviewImage();
			if (imagePath == null)
				imagePath = "";
			imagePanel.loadImage(imagePath);
			imagePanel.repaint( );
			getParent( ).getParent( ).repaint( );
		}

		public void updateResources( ) {
			super.updateResources( );
			getParent( ).getParent( ).repaint( );
		}

	}
}
