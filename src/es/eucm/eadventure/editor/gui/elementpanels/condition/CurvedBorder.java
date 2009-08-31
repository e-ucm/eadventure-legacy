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
package es.eucm.eadventure.editor.gui.elementpanels.condition;

//CurvedBorder.java
//A custom border that draws round rectangle borders.
//
import java.awt.*;

import javax.swing.border.*;

public class CurvedBorder extends AbstractBorder
{
 private Color wallColor = Color.gray;
 private Color bgColor = new Color(255,255,20,100);
 private int sinkLevel = 10;
 
 private AlphaComposite alphaComposite;

 /**
 * @param alphaComposite the alphaComposite to set
 */
public void setAlphaComposite(AlphaComposite alphaComposite) {
	this.alphaComposite = alphaComposite;
}
public CurvedBorder() { }
 public CurvedBorder(int sinkLevel) { this.sinkLevel = sinkLevel; }
 public CurvedBorder(Color wall) { this.wallColor = wall; }
 public CurvedBorder(int sinkLevel, Color wall)    {
     this.sinkLevel = sinkLevel;
     this.wallColor = wall;
 }
 
 public CurvedBorder(int sinkLevel, Color wall, Color bg)    {
     this.sinkLevel = sinkLevel;
     this.wallColor = wall;
     this.bgColor = bg;
 }


 public void paintBorder(Component c, Graphics g, int x, int y,
                         int w, int h)
 {
	 if (alphaComposite!=null){
		 ((Graphics2D) g).setComposite(alphaComposite);
	 }

     //  Paint a tall wall around the component
     //for (int i = 0; i < sinkLevel; i++) {
        /*g.drawRoundRect(x+i, y+i, w-i-1, h-i-1, sinkLevel-i, sinkLevel);
        g.drawRoundRect(x+i, y+i, w-i-1, h-i-1, sinkLevel, sinkLevel-i);
        g.drawRoundRect(x+i, y, w-i-1, h-1, sinkLevel-i, sinkLevel);
        g.drawRoundRect(x, y+i, w-1, h-i-1, sinkLevel, sinkLevel-i);*/
	 if (bgColor!=null){
		 g.setColor(bgColor);
	     g.fillRoundRect(x, y, w-1, h-1, sinkLevel, sinkLevel);
	 }
     g.setColor(getWallColor());
     g.drawRoundRect(x, y, w-1, h-1, sinkLevel, sinkLevel);
     ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
     //}
 }

 public Insets getBorderInsets(Component c) {
     return new Insets(0, 0, 0, 0);
 }
 public Insets getBorderInsets(Component c, Insets i) {
     i.left = i.right = i.bottom = i.top = 10;
     return i;
 }
 public boolean isBorderOpaque() { return true; }
 public int getSinkLevel() { return sinkLevel; }
 public Color getWallColor() { return wallColor; }
}
