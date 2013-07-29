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
package es.eucm.eadventure.editor.control.controllers.imageedition;

import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JDialog;

import es.eucm.eadventure.editor.control.controllers.imageedition.filter.TransparentColorFilter;

public class EditImageController {

    private BufferedImage image;

    private TransparentColorFilter filter;

    private JDialog frame;

    private Stack<Operation> undoOperations;

    private Stack<Operation> redoOperations;
    
    private int changed;

    public EditImageController( BufferedImage image, JDialog frame ) {

        this.image = image;
        this.frame = frame;
        undoOperations = new Stack<Operation>( );
        redoOperations = new Stack<Operation>( );
        changed = 0;
    }

    public void transform( int xImage, int yImage ) {

        if( xImage >= 0 && yImage >= 0 && xImage < image.getWidth( ) && yImage < image.getHeight( ) ) {
            filter.setAlphaValue( 0 );
            filter.transform( image, xImage, yImage );
            changed++;
            redoOperations.clear( );
            if( undoOperations.size( ) == 10 ) {
                undoOperations.remove( undoOperations.size( ) - 1 );
            }
            undoOperations.push( new Operation( xImage, yImage, filter.getThreshold( ), filter.isContiguous( ) ) );
            frame.repaint( );
        }
    }

    public void undo( ) {

        if( !undoOperations.isEmpty( ) ) {
            Operation op = undoOperations.pop( );
            redoOperations.push( op );
            applyFilter( op, 255 );
            changed--;
        }
    }

    public void redo( ) {

        if( !redoOperations.isEmpty( ) ) {
            Operation op = redoOperations.pop( );
            undoOperations.push( op );
            applyFilter( op, 0 );
            changed++;
        }
    }

    private void applyFilter( Operation op, int alphaValue ) {

        int oldThreshold = filter.getThreshold( );
        boolean oldContiguous = filter.isContiguous( );
        filter.setThreshold( op.threshold );
        filter.setContiguous( op.contiguous );
        filter.setAlphaValue( alphaValue );
        filter.transform( image, op.x, op.y );
        filter.setThreshold( oldThreshold );
        filter.setContiguous( oldContiguous );
        frame.repaint( );
    }

    public void setImageFilter( TransparentColorFilter filter ) {

        this.filter = filter;
    }

    private class Operation {

        public int x, y;

        public int threshold;

        public boolean contiguous;

        public Operation( int x, int y, int threshold, boolean contiguous ) {

            this.x = x;
            this.y = y;
            this.threshold = threshold;
            this.contiguous = contiguous;
        }
    }
    
    public boolean isChanged( ){
        return changed != 0;
    }
}
