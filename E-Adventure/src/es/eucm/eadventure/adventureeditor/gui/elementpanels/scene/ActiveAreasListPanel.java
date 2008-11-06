package es.eucm.eadventure.adventureeditor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.otherpanels.imagepanels.MultipleRectangleImagePanel;

public class ActiveAreasListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param activeAreasListDataControl
	 *            ActiveAreas list controller
	 */
	public ActiveAreasListPanel( ActiveAreasListDataControl activeAreasListDataControl ) {

		// Take the path of the background
		String scenePath = Controller.getInstance( ).getSceneImagePath( activeAreasListDataControl.getParentSceneId( ) );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveAreasList.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "ActiveAreasList.Information" ) );
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
		MultipleRectangleImagePanel multipleRectanglePanel = new MultipleRectangleImagePanel( scenePath, Color.GREEN );
		multipleRectanglePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveAreasList.PreviewTitle" ) ) );
		add( multipleRectanglePanel, c );

		// Add the item references if an image was loaded
		if( scenePath != null ) {
			// Add the activeAreas
			for( ActiveAreaDataControl activeArea : activeAreasListDataControl.getActiveAreas( ) ) {
				multipleRectanglePanel.addRectangle( activeArea.getX( ), activeArea.getY( ), activeArea.getWidth( ), activeArea.getHeight( ) );
			}
		}
	}
}
