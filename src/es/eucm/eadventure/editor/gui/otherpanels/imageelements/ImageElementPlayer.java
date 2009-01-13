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
		if (sceneDataControl.getDefaultInitialPositionX() < 0)
			return 400;
		return sceneDataControl.getDefaultInitialPositionX();
	}

	@Override
	public int getY() {
		if (sceneDataControl.getDefaultInitialPositionY() < 0)
			return 300 + image.getHeight(null);
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

	@Override
	public void setScale(float scale) {
	}

	@Override
	public boolean canRescale() {
		return false;
	}

	@Override
	public boolean canResize() {
		return false;
	}

	@Override
	public void changeSize(int width, int height) {
	}

	@Override
	public int getHeight() {
		return image.getHeight(null);
	}

	@Override
	public int getWidth() {
		return image.getWidth(null);
	}

}
