package es.eucm.eadventure.engine.core.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowManager extends WindowAdapter {

    /*
     *  (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing( WindowEvent e ) {
        //FIXME: Testing to get rid of this fragment
        //( (Applet) Game.getInstance( ).getComm( ) ).stop( );
        //System.out.println( "Hey!" );
    }

}
