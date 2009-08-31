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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.PointDataControl;

public class ImageElementPoint extends ImageElement {
	
	private PointDataControl point;
	
	public ImageElementPoint(PointDataControl point) {
		this.point = point;
		image = new BufferedImage(20, 20, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) image.getGraphics();
		AlphaComposite alphaComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.6f);
		g.setComposite(alphaComposite);
		g.setColor(Color.GREEN);
		g.fillOval(0, 0, 20, 20);
		g.setColor(Color.BLACK);
		g.drawOval(0, 0, 19, 19);
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
	public void changePosition(int x, int y) {
		point.setPoint(x - 5 , y - 10);
	}

	@Override
	public void changeSize(int width, int height) {
	}

	@Override
	public DataControl getDataControl() {
		return point;
	}

	@Override
	public int getHeight() {
		return 20;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public DataControl getReferencedDataControl() {
		return null;
	}

	@Override
	public float getScale() {
		return 1.0f;
	}

	@Override
	public int getWidth() {
		return 20;
	}

	@Override
	public int getX() {
		return point.getX() + 5;
	}

	@Override
	public int getY() {
		return point.getY() + 10;
	}

	@Override
	public void recreateImage() {
	}

	@Override
	public void setScale(float scale) {
	}

	@Override
	public boolean transparentPoint(int x, int y) {
		return false;
	}

}
