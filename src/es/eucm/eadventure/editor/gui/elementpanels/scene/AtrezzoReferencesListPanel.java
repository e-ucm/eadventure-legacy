package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.AtrezzoReferencesListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.MultipleElementImagePanel;

public class AtrezzoReferencesListPanel extends JPanel{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param atrezzoReferencesListDataControl
	 *            Atrezzo item references list controller
	 */
	public AtrezzoReferencesListPanel( AtrezzoReferencesListDataControl atrezzoReferencesListDataControl ) {

		// Take the path of the background
		String scenePath = Controller.getInstance( ).getSceneImagePath( atrezzoReferencesListDataControl.getParentSceneId( ) );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoReferencesList.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "AtrezzoReferencesList.Information" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( informationTextPane, BorderLayout.CENTER );
		add( informationPanel, c );

		// Create and set the preview panel
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		MultipleElementImagePanel multipleImagePanel = new MultipleElementImagePanel( scenePath );
		multipleImagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReferencesList.PreviewTitle" ) ) );
		add( multipleImagePanel, c );

		// Add the item references if an image was loaded
		if( scenePath != null ) {
			// Add the item references
			for( ElementReferenceDataControl elementReference : atrezzoReferencesListDataControl.getAtrezzoReferences( ) ) {
				String atrezzoPath = Controller.getInstance( ).getElementImagePath( elementReference.getElementId( ) );
				multipleImagePanel.addElement( atrezzoPath, elementReference.getElementX( ), elementReference.getElementY( ) );
			}
		}
	}
	
}
