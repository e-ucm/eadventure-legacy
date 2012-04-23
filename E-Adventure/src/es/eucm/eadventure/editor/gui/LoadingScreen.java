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
package es.eucm.eadventure.editor.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import es.eucm.eadventure.common.auxiliar.CreateImage;
import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

public class LoadingScreen extends JFrame {

    private String message;

    private ImageIcon icon;//=new ImageIcon(Controller.getInstance().getLoadingImage());

    private int status;

    private Timer thread;

    private void setStatus( int status ) {

        this.status = status;
    }

    private int getStatus( ) {

        return status;
    }

    public LoadingScreen( String message, String imagePath, Window parent ) {

        //super (parent);
        //this.setModal( false );
        //this.setModalExclusionType( Dialog.ModalExclusionType.TOOLKIT_EXCLUDE );
        super.setUndecorated( true );
        //this.setAlwaysOnTop( true );
        //this.setModal( true );
        //this.setVisible( true );

        //this.setLayout( new BorderLayout() );
        setImage( imagePath );
        //this.add( new JLabel(icon), BorderLayout.CENTER );
        this.message = message;
        int width = icon.getImage( ).getWidth( this );
        int height = icon.getImage( ).getHeight( this );
        this.setSize( width, height );
        //this.setResizable( false );
        double screenWidth = Toolkit.getDefaultToolkit( ).getScreenSize( ).getWidth( );
        double screenHeight = Toolkit.getDefaultToolkit( ).getScreenSize( ).getHeight( );
        int locX = Math.round( ( (int) screenWidth - width ) / 2.0f );
        int locY = Math.round( ( (int) screenHeight - height ) / 2.0f );
        this.setLocation( locX, locY );
        this.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
        //this.setModalExclusionType( Dialog.ModalExclusionType.APPLICATION_EXCLUDE );
        //this.setAlwaysOnTop( true );
        
        List<Image> icons = new ArrayList<Image>( );

        icons.add( AssetsController.getImage( "img/Icono-Editor-16x16.png" ) );
        icons.add( AssetsController.getImage( "img/Icono-Editor-32x32.png" ) );
        icons.add( AssetsController.getImage( "img/Icono-Editor-64x64.png" ) );
        icons.add( AssetsController.getImage( "img/Icono-Editor-128x128.png" ) );
        setIconImages( icons );
        
        status = 0;
        thread = new Timer( );
        thread.start( );
    }

    public void close( ) {

        thread.setStop( true );
        this.dispose( );
        //this.setVisible( false );
        /*while (thread.isAlive( )){
        	try {
        		Thread.sleep( 400 );
        	} catch( InterruptedException e ) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }*/

    }

    public void setMessage( String message ) {

        this.message = message;
    }

    public void setImage( String imagePath ) {

        if( imagePath != null )
            icon = new ImageIcon( imagePath );
        else
            icon = null;

        if( icon != null && icon.getImage( ) == null )
            icon = null;

        if( icon == null ) {
            icon = new ImageIcon( CreateImage.createImage( 400, 300, new Color( 250, 208, 108 ), 15, new Color( 158, 94, 30 ), "Loading / Cargando", new Color( 158, 94, 30 ), CreateImage.CENTER, CreateImage.CENTER, new Font( "Arial", Font.PLAIN, 14 ) ) );
        }
    }

    public class Timer extends Thread {

        boolean stop = false;

        private boolean isStop( ) {

            return stop;
        }

        @Override
        public void run( ) {

            while( !isStop( ) ) {
                try {
                    Thread.sleep( 400 );
                    setStatus( ( getStatus( ) + 1 ) % 5 );
                    if( isVisible( ) ) {
                        paintComponent( getGraphics( ) );
                    }
                    //repaint();
                    //invalidate();
                    //doLayout();
                    //status = (status+1)%5;

                }
                catch( InterruptedException e ) {
                    ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
                }
            }
        }

        public void setStop( boolean b ) {

            this.stop = b;
        }
    }

    public void paintComponent( Graphics g ) {

        //super.paint( g );

        paintLoadingScreen( g, message, icon, status, 0, 0 );
    }

    /*public void update (Graphics g){
    	super.update( g );
    	paintLoadingScreen (g, message, icon, status, 0,0);
    }*/

    public static void paintLoadingScreen( Graphics g, String message, ImageIcon icon, int status, int x, int y ) {

        String messageToDisplay = message;
        switch( status ) {
            case 1:
                messageToDisplay = messageToDisplay + " .";
                break;
            case 2:
                messageToDisplay = messageToDisplay + " ..";
                break;
            case 3:
                messageToDisplay = messageToDisplay + " ...";
                break;
            case 4:
                messageToDisplay = messageToDisplay + " ....";
                break;
        }

        g.drawImage( icon.getImage( ), x, y, null );
        if( messageToDisplay.length( ) >= 45 ) {
            int lastSpace = messageToDisplay.substring( 0, 45 ).lastIndexOf( ' ' );
            g.drawString( messageToDisplay.substring( 0, lastSpace ), x + 100, y + 263 );
            g.drawString( messageToDisplay.substring( lastSpace + 1, messageToDisplay.length( ) ), x + 100, y + 278 );
        }
        else {
            g.drawString( messageToDisplay, x + 100, y + 265 );
        }

        g.dispose( );

    }
}
