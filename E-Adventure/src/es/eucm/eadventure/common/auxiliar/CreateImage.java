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
package es.eucm.eadventure.common.auxiliar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Utility for creating virtual images. That is, images that are not stored physically on the hard disk. 
 * @author Javier
 *
 */
public class CreateImage {

	public static final int CENTER = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int TOP = 1;
	public static final int BOTTOM = 2;
	
	
	public static Image createImage ( int width, int height, String text ){
		return createImage ( width, height, text, new Font("Arial", Font.PLAIN, 12));
	}
	
	public static Image createImage ( int width, int height, String text, Font font){
		return createImage( width, height, Color.BLACK, 5, Color.LIGHT_GRAY, text, Color.WHITE, CENTER, CENTER, font);
	}
	
    public static Image createImage ( int width, int height, Color backgroundColor, int borderThickness, Color borderColor, String text, Color textColor, int alignX, int alignY, Font font){
    	// Create basic image & get graphics object
    	BufferedImage im = new BufferedImage ( width, height, BufferedImage.TYPE_INT_RGB);
    	Graphics2D gr = (Graphics2D)im.getGraphics();
    	
    	// Fill background
    	gr.setColor(backgroundColor);
    	gr.fillRect(0, 0, width, height);
    	
    	// Fill border
    	gr.setColor(borderColor);
    	for (int i=0; i<borderThickness; i++)
    		gr.drawRect(i, i, width-2*i, height-2*i);
    	
    	// Write text
    	gr.setColor(textColor);
    	gr.setFont( font );
    	
    	FontMetrics metrics = gr.getFontMetrics();
    	Rectangle2D rect = metrics.getStringBounds(text, gr);
    	double textWidth = rect.getWidth();
    	double textHeight = rect.getHeight();
    
    	// Calculate x & y according to alignment
    	int x =0; int y =0;
    	if (alignX == CENTER){
    		x = (int)(((double)width - textWidth)/2.0);
    	} else if (alignX == LEFT){
    		if (width>5)
    			x = 5;
    		else
    			x=0;
    	} else if (alignX ==RIGHT){
    		if (width>textWidth+5)
    			x = (int)(width-textWidth-5.0);
    		else
    			x=0;
    	}
    	
    	if (alignY == CENTER){
    		y = (int)(((double)height - textHeight)/2.0);
    	} else if (alignY == TOP){
    		if (height>5)
    			y = 5;
    		else
    			y=0;
    	} else if (alignY ==BOTTOM){
    		if (height>textHeight+5)
    			y = (int)(height-textHeight-5.0);
    		else
    			y=0;
    	}

    	
    	gr.drawString(text, x, y);
    	gr.dispose();
    	
    	return im;
    }
	
    
    public static void main (String []args){
    	JFrame frame = new JFrame();
    	Image image = CreateImage.createImage(400,300,"Marihuanhell");
    	ImageIcon ic = new ImageIcon(image);
    	JLabel label = new JLabel(ic);
    	frame.setLayout(new BorderLayout());
    	frame.add(label, BorderLayout.CENTER);
    	frame.pack();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true);
    }
	
}
