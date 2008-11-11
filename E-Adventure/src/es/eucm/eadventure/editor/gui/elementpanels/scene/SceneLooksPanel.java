package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.Dimension;

import javax.swing.BorderFactory;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.gui.TextConstants;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.MultipleElementImagePanel;

public class SceneLooksPanel extends LooksPanel {

	private SceneDataControl sceneDataControl;

	/**
	 * Image panel for the preview.
	 */
	private MultipleElementImagePanel multipleImagePanel;

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

		multipleImagePanel = new MultipleElementImagePanel( scenePath );
		multipleImagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.Preview" ) ) );

		// Add the item references first
		for( ElementReferenceDataControl elementReference : sceneDataControl.getItemReferencesList( ).getItemReferences( ) ) {
			String itemPath = Controller.getInstance( ).getElementImagePath( elementReference.getElementId( ) );
			multipleImagePanel.addElement( itemPath, elementReference.getElementX( ), elementReference.getElementY( ) );
		}

		// Add then the character references
		for( ElementReferenceDataControl elementReference : sceneDataControl.getNPCReferencesList( ).getNPCReferences( ) ) {
			String itemPath = Controller.getInstance( ).getElementImagePath( elementReference.getElementId( ) );
			multipleImagePanel.addElement( itemPath, elementReference.getElementX( ), elementReference.getElementY( ) );
		}

		lookPanel.add( multipleImagePanel, cLook );
		//resourcesPanel.setPreviewUpdater( this );

	}

	@Override
	public void updatePreview( ) {
		multipleImagePanel.loadImage( sceneDataControl.getPreviewBackground( ) );
		multipleImagePanel.repaint( );
		getParent( ).getParent( ).repaint( );
	}

	public void updateResources( ) {
		super.updateResources( );
		getParent( ).getParent( ).repaint( );
	}
}
