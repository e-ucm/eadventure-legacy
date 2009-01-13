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
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarriersListDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.TrajectoryEditionPanel;

public class BarriersListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param barriersListDataControl
	 *            ActiveAreas list controller
	 */
	public BarriersListPanel( BarriersListDataControl barriersListDataControl ) {

		// Take the path of the background
		String scenePath = Controller.getInstance( ).getSceneImagePath( barriersListDataControl.getParentSceneId( ) );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BarriersList.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "BarriersList.Information" ) );
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
		
		ScenePreviewEditionPanel spep;
		if (barriersListDataControl.getTrajectoryDataControl().hasTrajectory()) {
			TrajectoryEditionPanel tep = new TrajectoryEditionPanel(scenePath, barriersListDataControl.getTrajectoryDataControl());
			spep = tep.getScenePreviewEditionPanel();
			add( tep, c );
		} else {
			spep = new ScenePreviewEditionPanel(scenePath);
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_BARRIER, true);
			add( spep, c );
		}

		// Add the item references if an image was loaded
		if( scenePath != null ) {
			// Add the activeAreas
			for( BarrierDataControl barrier : barriersListDataControl.getBarriers( ) ) {
				spep.addBarrier(barrier);
			}
		}
	}
}
