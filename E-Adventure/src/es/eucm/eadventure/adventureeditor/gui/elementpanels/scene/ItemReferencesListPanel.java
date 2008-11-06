package es.eucm.eadventure.adventureeditor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.scene.ItemReferencesListDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.otherpanels.imagepanels.MultipleElementImagePanel;

public class ItemReferencesListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param itemReferencesListDataControl
	 *            Item references list controller
	 */
	public ItemReferencesListPanel( ItemReferencesListDataControl itemReferencesListDataControl ) {

		// Take the path of the background
		String scenePath = Controller.getInstance( ).getSceneImagePath( itemReferencesListDataControl.getParentSceneId( ) );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReferencesList.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "ItemReferencesList.Information" ) );
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
			for( ElementReferenceDataControl elementReference : itemReferencesListDataControl.getItemReferences( ) ) {
				String itemPath = Controller.getInstance( ).getElementImagePath( elementReference.getElementId( ) );
				multipleImagePanel.addElement( itemPath, elementReference.getElementX( ), elementReference.getElementY( ) );
			}
		}
	}
}
