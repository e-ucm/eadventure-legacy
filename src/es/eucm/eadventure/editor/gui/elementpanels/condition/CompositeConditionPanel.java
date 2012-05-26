/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
