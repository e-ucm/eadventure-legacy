package es.eucm.eadventure.common.data.chapter;

import java.awt.Point;
import java.util.List;

public interface Rectangle {

	public void setValues(int x, int y, int width, int height);
	
	public int getX();
	
	public int getY();
	
	public int getWidth();
	
	public int getHeight();
	
	public boolean isRectangular();
	
	public void setRectangular(boolean rectangular);
	
	public List<Point> getPoints();
}
