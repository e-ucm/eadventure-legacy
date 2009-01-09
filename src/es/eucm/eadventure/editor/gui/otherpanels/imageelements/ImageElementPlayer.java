package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.Image;

import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;

public class ImageElementPlayer extends ImageElement {

	private SceneDataControl sceneDataControl;
	
	public ImageElementPlayer(Image image, 
			SceneDataControl sceneDataControl) {
		this.image = image;
		this.sceneDataControl = sceneDataControl;
	}

	@Override
	public ElementReferenceDataControl getElementReferenceDataControl() {
		return null;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getScale() {
		return 1.0f;
	}

	@Override
	public int getX() {
		return sceneDataControl.getDefaultInitialPositionX();
	}

	@Override
	public int getY() {
		return sceneDataControl.getDefaultInitialPositionY();
	}

	@Override
	public void recreateImage() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void changePosition(int x, int y) {
		sceneDataControl.setDefaultInitialPosition(x, y);
	}

}
