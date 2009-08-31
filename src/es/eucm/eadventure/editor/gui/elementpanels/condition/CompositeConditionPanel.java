/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CompositeConditionPanel extends EditablePanel {

    /**
     * Required
     */
    private static final long serialVersionUID = -4472061217945559110L;

    public CompositeConditionPanel( ConditionsPanelController controller, int i ) {

        super( controller, i, false, false );

        borderSelected = borderOver = null;
        state = SELECTED;
        setState( NO_SELECTED );
    }

    @Override
    protected void addComponents( int newState ) {

        //Left bracket
        if( controller.getConditionCount( super.index1 ) > 1 ) {
            JPanel leftBracket = new BracketPanel( true );
            add( leftBracket );
        }

        for( int i = 0; i < controller.getConditionCount( super.index1 ); i++ ) {
            AtomicConditionPanel currentConditionPanel = new AtomicConditionPanel( controller, super.index1, i );
            add( currentConditionPanel );
            if( i < controller.getConditionCount( super.index1 ) - 1 ) {
                add( new EvalFunctionPanel( controller, super.index1, i, EvalFunctionPanel.OR ) );
            }
        }

        //Right bracket
        if( controller.getConditionCount( super.index1 ) > 1 ) {
            JPanel rightBracket = new BracketPanel( false );
            add( rightBracket );
        }

    }

    @Override
    protected ButtonsPanel createButtonsPanel( ) {

        return null;
    }

    private class BracketPanel extends JPanel {

        /**
         * Required
         */
        private static final long serialVersionUID = -5089559898788716264L;

        private boolean isLeft;

        public BracketPanel( boolean isLeft ) {

            this.isLeft = isLeft;
            this.setMinimumSize( new Dimension( 32, 32 ) );
            this.setPreferredSize( new Dimension( 32, 32 ) );
        }

        @Override
        public void paint( Graphics g ) {

            g.setColor( Color.black );
            int width = getWidth( );
            int height = getHeight( );
            if( isLeft )
                g.drawArc( 0, 0, 2 * width / 3, height, 90, 180 );
            else
                g.drawArc( 0, 0, 2 * width / 3, height, 90, -180 );

        }
    }

}
