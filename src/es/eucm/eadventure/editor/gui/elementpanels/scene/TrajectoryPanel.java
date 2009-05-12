package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.TrajectoryDataControl;
import es.eucm.eadventure.editor.control.tools.scene.ChangeHasTrajectoryTool;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.TrajectoryEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementPlayer;

public class TrajectoryPanel extends JPanel implements Updateable {

	private static final long serialVersionUID = 1L;

	private	ScenePreviewEditionPanel spep;

	private TrajectoryEditionPanel tep = null;
	
	private TrajectoryDataControl dataControl;
		
	private SceneDataControl sceneDataControl;
	
	private JCheckBox useTrajectoryCheckBox;
	
	private JCheckBox initialPositionCheckBox;

	private JPanel initialPositionPanel = null;
	
	private String scenePath;
	
	/**
	 * Constructor.
	 * 
	 * @param barriersListDataControl
	 *            ActiveAreas list controller
	 */
	public TrajectoryPanel( TrajectoryDataControl trajectoryDataControl , SceneDataControl sceneDataControl) {
		this.dataControl = trajectoryDataControl;
		this.sceneDataControl = sceneDataControl;
		scenePath = Controller.getInstance( ).getSceneImagePath( sceneDataControl.getId() );

		setLayout( new BorderLayout( ) );

		
		JPanel useTrajectoryPanel = new JPanel();
		useTrajectoryPanel.setLayout( new GridLayout(0,1));
		useTrajectoryCheckBox = new JCheckBox(TextConstants.getText("Scene.UseTrajectory"), sceneDataControl.getTrajectory().hasTrajectory());
		useTrajectoryCheckBox.addActionListener( new TrajectoryCheckBoxListener() );
		useTrajectoryPanel.add(useTrajectoryCheckBox);
		useTrajectoryPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.UseTrajectoryPanel" ) ) );

		add(useTrajectoryPanel, BorderLayout.NORTH);
		
		if (dataControl.hasTrajectory()) {
			tep = new TrajectoryEditionPanel(scenePath, dataControl);
			spep = tep.getScenePreviewEditionPanel();
			fillSpep();
			add( tep, BorderLayout.CENTER );
		} else {
			initialPositionPanel = createInitialPositionPanel();
			add(initialPositionPanel , BorderLayout.CENTER );
		}
	}

	private void fillSpep() {
		if( scenePath != null && spep != null) {
			for( ElementReferenceDataControl elementReference : sceneDataControl.getReferencesList().getItemReferences() ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_OBJECT, elementReference);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_OBJECT, false);
			for( ElementReferenceDataControl elementReference : sceneDataControl.getReferencesList().getNPCReferences() ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_CHARACTER, elementReference);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_CHARACTER, false);
			for( ElementReferenceDataControl elementReference : sceneDataControl.getReferencesList().getAtrezzoReferences() ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_ATREZZO, elementReference);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_ATREZZO, false);
			for( ExitDataControl exit : sceneDataControl.getExitsList().getExits() ) {
				spep.addExit(exit);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_EXIT, false);
			for( ActiveAreaDataControl activeArea : sceneDataControl.getActiveAreasList().getActiveAreas()) {
				spep.addActiveArea(activeArea);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_ACTIVEAREA, false);
			for( BarrierDataControl barrier : sceneDataControl.getBarriersList().getBarriers() ) {
				spep.addBarrier(barrier);
			}
		}
	}
	
	private JPanel createInitialPositionPanel() {
		JPanel initialPositionPanel = new JPanel( );
		initialPositionPanel.setLayout( new GridLayout( 0, 1 ) );
		initialPositionCheckBox = new JCheckBox( TextConstants.getText( "Scene.UseInitialPosition" ), sceneDataControl.hasDefaultInitialPosition( ) );
		initialPositionCheckBox.addActionListener( new InitialPositionCheckBoxListener( ) );
		

		initialPositionPanel.add( initialPositionCheckBox );
		initialPositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.DefaultInitialPosition" ) ) );
		
		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new BorderLayout());
		fullPanel.add(initialPositionPanel, BorderLayout.NORTH);

		spep = new ScenePreviewEditionPanel(false, scenePath);
		fillSpep();
		fullPanel.add(spep, BorderLayout.CENTER);
		spep.setShowTextEdition(true);

		if (sceneDataControl.hasDefaultInitialPosition( )) {
			Image image = AssetsController.getImage(Controller.getInstance().getPlayerImagePath());
			spep.addPlayer(sceneDataControl, image);
			spep.setSelectedElement(new ImageElementPlayer(image, sceneDataControl));
			spep.setFixedSelectedElement(true);
		}
		spep.repaint();
		
		return fullPanel;
	}
	
	/**
	 * Listener for the "Use initial position in this scene" check box.
	 */
	private class InitialPositionCheckBoxListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			sceneDataControl.toggleDefaultInitialPosition( );
			spep.setFixedSelectedElement(false);
			spep.setSelectedElement((ImageElement) null);
			spep.removeElements(ScenePreviewEditionPanel.CATEGORY_PLAYER);
			if (sceneDataControl.hasDefaultInitialPosition( )) {
				Image image = AssetsController.getImage(Controller.getInstance().getPlayerImagePath());
				spep.addPlayer(sceneDataControl, image);
				spep.setSelectedElement(new ImageElementPlayer(image, sceneDataControl));
				spep.setFixedSelectedElement(true);
			}
			spep.repaint();
		}
	}

	private class TrajectoryCheckBoxListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			Controller.getInstance().addTool(new ChangeHasTrajectoryTool(((JCheckBox) e.getSource()).isSelected(), sceneDataControl));
			updateContents();
		}
	}

	private void updateContents() {
		dataControl = sceneDataControl.getTrajectory();
		
		if (initialPositionPanel != null) {
			remove(initialPositionPanel);
			initialPositionPanel = null;
		}
		
		if (tep != null) {
			remove(tep);
			tep = null;
		}

		if (dataControl.hasTrajectory()) {
			tep = new TrajectoryEditionPanel(scenePath, dataControl);
			spep = tep.getScenePreviewEditionPanel();
			fillSpep();
			add( tep, BorderLayout.CENTER );
		} else {
			initialPositionPanel = createInitialPositionPanel();
			add(initialPositionPanel , BorderLayout.CENTER );
		}
		
	    updateUI();
	}
	
	public boolean updateFields() {
		if (sceneDataControl.getTrajectory().hasTrajectory() != useTrajectoryCheckBox.isSelected()) {
			useTrajectoryCheckBox.setSelected(sceneDataControl.getTrajectory().hasTrajectory());
			updateContents();
		}
		else {
			if (spep != null) {
				spep.updateUI();
				spep.repaint();
			}
			if (initialPositionCheckBox != null) {
				initialPositionCheckBox.setSelected(sceneDataControl.hasDefaultInitialPosition( ));
				spep.setFixedSelectedElement(false);
				spep.setSelectedElement((ImageElement) null);
				spep.removeElements(ScenePreviewEditionPanel.CATEGORY_PLAYER);
				if (sceneDataControl.hasDefaultInitialPosition( )) {
					Image image = AssetsController.getImage(Controller.getInstance().getPlayerImagePath());
					spep.addPlayer(sceneDataControl, image);
					spep.setSelectedElement(new ImageElementPlayer(image, sceneDataControl));
					spep.setFixedSelectedElement(true);
				}
				spep.updateUI();
				spep.repaint();
			}
		}
		return true;
	}
}
