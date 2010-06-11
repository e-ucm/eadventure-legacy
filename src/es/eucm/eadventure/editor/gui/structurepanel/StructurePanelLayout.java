/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.List;

public class StructurePanelLayout implements LayoutManager2 {

    List<StructureComponent> components = new ArrayList<StructureComponent>( );

    public void addLayoutComponent( Component arg0, Object arg1 ) {

        components.add( new StructureComponent( arg0, (Integer) arg1 ) );
    }

    public float getLayoutAlignmentX( Container arg0 ) {

        return 0;
    }

    public float getLayoutAlignmentY( Container arg0 ) {

        return 0;
    }

    public void invalidateLayout( Container arg0 ) {

    }

    public Dimension maximumLayoutSize( Container arg0 ) {

        return null;
    }

    public void addLayoutComponent( String arg0, Component arg1 ) {

    }

    public void layoutContainer( Container parent ) {

        int width = parent.getWidth( );
        int y = 0;
        for( StructureComponent c : components ) {
            int height2 = c.getSize( );
            if( c.getSize( ) == -1 ) {
                height2 = parent.getHeight( );
                for( StructureComponent d : components ) {
                    if( d != c )
                        height2 -= d.getSize( );
                }
            }
            c.getComponent( ).setBounds( 0, y, width, height2 );
            y += height2;
        }
    }

    public Dimension minimumLayoutSize( Container arg0 ) {

        return new Dimension( 0, 0 );
    }

    public Dimension preferredLayoutSize( Container arg0 ) {

        return new Dimension( arg0.getSize( ).width, arg0.getSize( ).height );
    }

    public void removeLayoutComponent( Component arg0 ) {

        int k = -1;
        for( int i = 0; i < components.size( ); i++ )
            if( components.get( i ).getComponent( ) == arg0 )
                k = i;
        if( k != -1 )
            components.remove( k );
    }

    private class StructureComponent {

        private Component component;

        private Integer size;

        public StructureComponent( Component component, Integer size ) {

            this.component = component;
            this.size = size;
        }

        public Component getComponent( ) {

            return component;
        }

        public int getSize( ) {

            return size.intValue( );
        }
    }

}
