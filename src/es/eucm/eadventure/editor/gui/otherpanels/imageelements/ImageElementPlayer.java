/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.Image;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;

public class ImageElementPlayer extends ImageElement {

	private SceneDataControl sceneDataControl;

	public ImageElementPlayer(Image image, SceneDataControl sceneDataControl) {
		this.image = image;
		if (image == null)
			image = new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR);
		this.sceneDataControl = sceneDataControl;
	}

	@Override
	public DataControl getDataControl() {
		return null;
	}

	@Override
	public int getLayer() {
		return sceneDataControl.getPlayerLayer();
	}

	@Override
	public float getScale() {
		if (sceneDataControl.getTrajectory() != null && sceneDataControl.getTrajectory().hasTrajectory())
			return 1.0f;
		else
			return sceneDataControl.getPlayerScale();
	}

	@Override
	public int getX() {
		if (sceneDataControl.getDefaultInitialPositionX() < 0)
			return 400;
		return sceneDataControl.getDefaultInitialPositionX();
	}

	@Override
	public int getY() {
		if (sceneDataControl.getDefaultInitialPositionY() < 0 && image != null)
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
		if (scale > 0.2f && scale < 5)
			sceneDataControl.setPlayerScale(scale);
	}

	@Override
	public boolean canRescale() {
		if (sceneDataControl.getTrajectory() != null
				&& sceneDataControl.getTrajectory().hasTrajectory())
			return false;
		else
			return true;
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
		if (image != null)
			return image.getHeight(null);
		else
			return 0;
	}

	@Override
	public int getWidth() {
		if (image != null)
			return image.getWidth(null);
		else
			return 0;
	}

	@Override
	public boolean transparentPoint(int x, int y) {
		if (image == null)
			return false;
		else {
			int alpha = ((BufferedImage) this.image).getRGB((int) (x / this
					.getScale()), (int) (y / this.getScale())) >>> 24;
			return !(alpha > 128);
		}
	}

	@Override
	public DataControl getReferencedDataControl() {
		return null;
	}

}
