/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * 
 * This layout allow to define a two columns panel choosing the % of proportion between the size of the left component and the right one.
 * 
 * This is useful when there are text components only in one side 
 * 
 *
 */
public class TwoColumnsLayout implements LayoutManager {

    
    public static final String LEFT_COMPONENT = "left";
    
    
    public static final String RIGHT_COMPONENT = "right";
 
    
    private Component leftComponent;
    
    private Component rightComponent;
    
    private float proportion;
    
    public TwoColumnsLayout(float proportion){
        this.proportion = proportion;
    }
    
    public void addLayoutComponent( String name, Component comp ) {

        if (name.equals( LEFT_COMPONENT ))
            leftComponent = comp;
        if (name.equals( RIGHT_COMPONENT ))
            rightComponent = comp; 

    }
    public void layoutContainer(Container parent) {
    Insets insets = parent.getInsets();
    int x = insets.left;
    int y = insets.top;

    Dimension leftDim = leftComponent.getPreferredSize( );
    if (leftDim == null)
        leftDim  = leftComponent.getMinimumSize( );
    
    Dimension rightDim = rightComponent.getPreferredSize( );
    if (rightDim == null)
        rightDim  = rightComponent.getMinimumSize( );
    
    int h = Math.max( leftDim.height, rightDim.height);
    h = h<parent.getHeight( )?h:parent.getHeight( );
    
    int leftCompWidth = Math.round(parent.getWidth( )*proportion)- insets.right - 5;
    
    leftComponent.setBounds(x, y, leftCompWidth, h);
    rightComponent.setBounds(x +leftCompWidth, y, Math.round(parent.getWidth( )*(1-proportion)), h);
    
    }


public Dimension minimumLayoutSize(Container parent) {
   return parent.getSize( );
}

public Dimension preferredLayoutSize(Container parent) {
    return parent.getSize( );
}




public void removeLayoutComponent( Component comp ) {

    // TODO Auto-generated method stub
    
}


public Component getLeftComponent( ) {

    return leftComponent;
}


public void setLeftComponent( Component leftComponent ) {

    this.leftComponent = leftComponent;
}


public Component getRightComponent( ) {

    return rightComponent;
}


public void setRightComponent( Component rightComponent ) {

    this.rightComponent = rightComponent;
}


public float getProportion( ) {

    return proportion;
}


public void setProportion( float proportion ) {

    this.proportion = proportion;
}
}
