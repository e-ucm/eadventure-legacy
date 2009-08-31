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
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;

public class ImageElementExit extends ImageElement {

	private ExitDataControl exitDataControl;

	private int minX;
	
	private int minY;
	
	private int maxX;
	
	private int maxY;

	private Polygon polygon;
	
	public ImageElementExit(ExitDataControl exitDataControl) {
		this.exitDataControl = exitDataControl;
		createImage();
	}
	
	private void createImage() {
		if (exitDataControl.isRectangular()) {
			image = new BufferedImage(exitDataControl.getWidth(), exitDataControl
					.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			fillImage();
		} else {
			minX = Integer.MAX_VALUE;
			minY = Integer.MAX_VALUE;
			maxX = 0;
			maxY = 0;
			for (Point point : exitDataControl.getPoints()) {
				if (point.getX() > maxX) maxX = (int) point.getX();
				if (point.getX() < minX) minX = (int) point.getX();
				if (point.getY() > maxY) maxY = (int) point.getY();
				if (point.getY() < minY) minY = (int) point.getY();
			}
			
			if (exitDataControl.getPoints().size() >= 3)
				image = new BufferedImage(maxX - minX, maxY - minY, BufferedImage.TYPE_4BYTE_ABGR);
			else
				image = new BufferedImage(20, 20, BufferedImage.TYPE_4BYTE_ABGR);
			
			fillImageIrregular(minX, minY);
		}
	}

	private void fillImage() {
		Graphics2D g = (Graphics2D) image.getGraphics();
		AlphaComposite alphaComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.3f);
		g.setComposite(alphaComposite);
		g.setColor(Color.RED);
		g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, image.getWidth(null) - 1, image.getHeight(null) - 1);
	}

	
	private void fillImageIrregular(int minX, int minY) {
		Graphics2D g = (Graphics2D) image.getGraphics();
		AlphaComposite alphaComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.3f);
		g.setComposite(alphaComposite);
		
		polygon = new Polygon();
		
		int x[] = new int[exitDataControl.getPoints().size()];
		int y[] = new int[exitDataControl.getPoints().size()];
		for (int i = 0; i < exitDataControl.getPoints().size(); i++) {
			x[i] = (int) exitDataControl.getPoints().get(i).getX() - minX;
			y[i] = (int) exitDataControl.getPoints().get(i).getY() - minY;
			polygon.addPoint(x[i], y[i]);
		}
		
		if (exitDataControl.getPoints().size() >= 3) {
			g.setColor(Color.RED);
			g.fillPolygon(x, y, exitDataControl.getPoints().size());
			g.setColor(Color.BLACK);
			g.drawPolygon(x, y, exitDataControl.getPoints().size());
		}
	}

	@Override
	public void changePosition(int x, int y) {
		if (exitDataControl.isRectangular()) {
			int width = exitDataControl.getWidth();
			int height = exitDataControl.getHeight();
			exitDataControl.setExit(x - width / 2, y - height, width, height);
		}
	}

	@Override
	public DataControl getDataControl() {
		return exitDataControl;
	}

	@Override
	public int getLayer() {
		return -1;
	}

	@Override
	public float getScale() {
		return 1.0f;
	}

	@Override
	public int getX() {
		if (exitDataControl.isRectangular())
			return exitDataControl.getX() + exitDataControl.getWidth() / 2;
		else
			return minX + (maxX - minX) / 2;
	}

	@Override
	public int getY() {
		if (exitDataControl.isRectangular())
			return exitDataControl.getY() + exitDataControl.getHeight();
		else
			return maxY;
	}

	@Override
	public void recreateImage() {
		createImage();
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
		return true;
	}

	@Override
	public void changeSize(int width, int height) {
		int x = exitDataControl.getX();
		int y = exitDataControl.getY();
		if (width < 1)
			width = 1;
		if (height < 1)
			height = 1;
		exitDataControl.setExit(x, y, width, height);
	}

	@Override
	public int getHeight() {
		if (exitDataControl.isRectangular())
			return exitDataControl.getHeight();
		else
			return maxY - minY;
	}

	@Override
	public int getWidth() {
		if (exitDataControl.isRectangular())
			return exitDataControl.getWidth();
		else
			return maxX - minX;
	}

	@Override
	public boolean transparentPoint(int x, int y) {
		if (exitDataControl.isRectangular())
			return false;
		else
			return !polygon.contains(x, y);
	}

	@Override
	public DataControl getReferencedDataControl() {
		return null;
	}

}
