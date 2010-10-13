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
