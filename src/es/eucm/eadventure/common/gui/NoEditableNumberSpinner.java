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
package es.eucm.eadventure.common.gui;

import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Very useful to avoid that a spinner could be edited by typing text. This
 * class has been designed to avoid JSpinners to get The Key Focus in the window
 * 
 * @author Javier
 * 
 */
public class NoEditableNumberSpinner extends JSpinner {

    /**
     * Required
     */
    private static final long serialVersionUID = 4827835884428487802L;

    /**
     * Component used as editor
     */
    private JLabel editor;

    /**
     * Constructor. Must be argumented with the selected value, min valid value,
     * max valid value and the step
     * 
     * @param value
     * @param min
     * @param max
     * @param step
     */
    public NoEditableNumberSpinner( Number value, int min, int max, Number step ) {

        super( new SpinnerNumberModel( value, min, max, step ) );
        editor = new JLabel( value.toString( ) );
        this.setEditor( editor );
        this.addChangeListener( new ChangeListener( ) {
            public void stateChanged( ChangeEvent e ) {
                editor.setText( getValue( ).toString( ) );
            }
        } );
    }
    
    /**
     * Constructor. Must be argumented with the selected value, min valid value,
     * max valid value and the step
     * 
     * @param value
     * @param min
     * @param max
     * @param step
     */
    public NoEditableNumberSpinner( Number value, float min, float max, Number step ) {

        super( new FloatSpinnerNumberModel( value, min, max, step ) );
        NumberFormat nf = NumberFormat.getInstance( );
        nf.setMaximumFractionDigits( 1 );
        editor = new JLabel(nf.format(((Float)getValue( )).floatValue( ) ) );
        this.setEditor( editor );
        this.addChangeListener( new ChangeListener( ) {
            public void stateChanged( ChangeEvent e ) {
                NumberFormat nf = NumberFormat.getInstance( );
                nf.setMaximumFractionDigits( 1 );
                editor.setText( nf.format(((Float)getValue( )).floatValue( ) ));
            }
        } );
    }

    private static class FloatSpinnerNumberModel extends SpinnerNumberModel {
        /**
         * 
         */
        private static final long serialVersionUID = 5181715509494977849L;

        private Comparable<Float> max;
        
        private Comparable<Float> min;
        
        public FloatSpinnerNumberModel(Number value, float min, float max, Number step) {
            super(value, min, max, step);
            this.max = max;
            this.min = min;
        }
        
        private Number incrValue(int dir) 
        {
            Number value = getNumber();
            Number stepSize = getStepSize();
            Float newValue ;
        
            float v = value.floatValue( ) + (stepSize.floatValue() * dir);
            newValue = new Float(v);

            if ((max != null) && (max.compareTo(newValue) < 0)) {
                return null;
            }
            if ((min != null) && (min.compareTo(newValue) > 0)) {
                return null;
            }
            else {
                return newValue;
            }
        } 
    
    @Override
    public Object getNextValue() {
        return incrValue(+1);
    }

    @Override
    public Object getPreviousValue() {
        return incrValue(-1);
    }
    }

    
}
