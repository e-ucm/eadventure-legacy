package es.eucm.eadventure.editor.control.controllers.scene;

import java.awt.Point;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Rectangle;

public interface RectangleArea {

	public boolean isRectangular();

	public List<Point> getPoints();

	public void addPoint(int x, int y);

	public Point getLastPoint();

	public void deletePoint(Point point);

	public void setRectangular(boolean selected) ;

	public Rectangle getRectangle();
	
}
