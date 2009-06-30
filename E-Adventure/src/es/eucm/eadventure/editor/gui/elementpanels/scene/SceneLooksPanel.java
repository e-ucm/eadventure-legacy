package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class SceneLooksPanel extends LooksPanel{

	private SceneDataControl sceneDataControl;

	/**
	 * Panel for the preview.
	 */
	private ScenePreviewEditionPanel scenePreviewEditionPanel;

	public SceneLooksPanel( SceneDataControl control ) {
		super( control );
		// TODO Parche, arreglar
		lookPanel.setPreferredSize( new Dimension( 0, 600 ) );

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void createPreview( ) {
		this.sceneDataControl = (SceneDataControl) this.dataControl;

		// Take the path of the background
		String scenePath = sceneDataControl.getPreviewBackground( );

		scenePreviewEditionPanel = new ScenePreviewEditionPanel(false, scenePath );
		scenePreviewEditionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.Preview" ) ) );

		// Add the item references first
		for( ElementReferenceDataControl elementReference : sceneDataControl.getReferencesList( ).getItemReferences( ) ) {
			scenePreviewEditionPanel.addElement(ScenePreviewEditionPanel.CATEGORY_OBJECT, elementReference);
		}

		// Add then the character references
		for( ElementReferenceDataControl elementReference : sceneDataControl.getReferencesList( ).getNPCReferences( ) ) {
			scenePreviewEditionPanel.addElement(ScenePreviewEditionPanel.CATEGORY_CHARACTER, elementReference);
		}
		
		
		// Add the atrezzo item references first
		for( ElementReferenceDataControl elementReference : sceneDataControl.getReferencesList( ).getAtrezzoReferences( ) ) {
			scenePreviewEditionPanel.addElement(ScenePreviewEditionPanel.CATEGORY_ATREZZO, elementReference);
		}
		// Deleted the checking if player has layer
		if (!Controller.getInstance().isPlayTransparent() /*&& sceneDataControl.isAllowPlayer()*/ )
			//scenePreviewEditionPanel.addPlayer(sceneDataControl, sceneDataControl.getReferencesList().getPlayerImage());
		    scenePreviewEditionPanel.addPlayer(sceneDataControl, AssetsController.getImage(Controller.getInstance().getPlayerImagePath()));
		    	
		scenePreviewEditionPanel.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_OBJECT, false);
		scenePreviewEditionPanel.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_CHARACTER, false);
		scenePreviewEditionPanel.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_ATREZZO, false);
		scenePreviewEditionPanel.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_PLAYER, false);
		lookPanel.add( scenePreviewEditionPanel, cLook );
		//resourcesPanel.setPreviewUpdater( this );

	}
	
	public void addPlayer(){
	 // Deleted the checking if player has layer
		if (!Controller.getInstance().isPlayTransparent()/* && sceneDataControl.isAllowPlayer()*/)
			scenePreviewEditionPanel.addPlayer(sceneDataControl, sceneDataControl.getReferencesList().getPlayerImage());
		scenePreviewEditionPanel.repaint();
	}

	@Override
	public void updatePreview( ) {
		if (scenePreviewEditionPanel != null){
			System.out.println(sceneDataControl.getPreviewBackground( ));
			scenePreviewEditionPanel.loadBackground(sceneDataControl.getPreviewBackground( ) );
			System.out.println("BG UPDATED!!");
		}
		if (getParent() != null && getParent().getParent() != null)
			getParent( ).getParent( ).repaint( );
	}

	public void updateResources( ) {
		super.updateResources( );
		if (getParent() != null && getParent().getParent() != null)
			getParent( ).getParent( ).repaint( );
	}

	public SceneDataControl getSceneDataControl() {
		return sceneDataControl;
	}
	
}
