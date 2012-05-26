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

package es.eucm.eadventure.tracking.prv;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;


/**
 * This class deals with making snapshots of the game while the user is playing. It makes a snapshot each sampleFreq milliseconds, saves it to disk and push it to a queue for being sent to a back-end.
 * If the game frame is not available or isn't visible, for example, because the user has minimized the screen, it will just draw a black image with string "N/A". This is important to ensure privacy.
 * 
 * Additional information is painted over each image:
 *      A timestamp, calculated as the number of milliseconds elapsed since the game was launched. It is painted in white on the top and on the bottom in black, to ensure it's easy to read.
 *      The cursor position. A special image "gui/cursors/debug.png" is painted. If it is not found, a yellow cross is drawn instead.
 * 
 * This class is intended to be run on a separate Thread, as to not interfere with normal game updates & renders.
 * @author Javier Torrente
 *
 */
public class SnapshotProducer extends Thread{

    /**
     * X Constant for drawing timestamp on each image. This is the left margin used, in pixels, both for the message that is drawn on the top and the on in the bottom.
     */
    private static final int X_MARGIN = 5;
    
    /**
     * Y Constant for drawing timestamp on each image. This is the top margin used, in pixels.
     */
    private static final int Y_TOP_MARGIN=10;
    
    /**
     * Y Constant for drawing timestamp on each image. This is the bottom margin used, in pixels. The second fps message is drawn on location (X_MARGIN, GUI.WINDOW-HEIGHT-Y_BOTTOM_MARGIN).
     */
    private static final int Y_BOTTOM_MARGIN = 50;
    
    /**
     * Max number of errors that will be admitted in storing a snapshot to disk before SnapshotProducer is shut down
     */
    private static final int MAX_RETRIES=10;
    
    // Init values (passed through constructor)
    
    /**
     * Queue of files that have to be sent to the back-end. Each time a new snapshot is created, it is pushed to the q. When a snapshot is retrieved and successfully sent to the back-end, it is popped. 
     */
    private List<File> q;
    
    /**
     * Boolean value used to end the execution of this thread.
     */
    private boolean terminate;
    
    /**
     * Value returned by System.currentTimeMillis() when the game is launched. It's used to calculate the timestamp for each image.
     */
    private long startTime;
    
    /**
     * Random id that is used to identify each instance of the game engine. All the snapshots and game logs produced within the same instance of the game engine will contain this value.
     */
    private int gameRandomId;
    
    /**
     * Frequency to make snapshots. It's a value in milliseconds  (one snapshot is captured each freq ms).
     */
    private long freq;
    
    
    // Private values
    
    /**
     * Private value. It is autogenerated each time a snapshot is produced. It's used to enumerate snapshots that belong to the same game run.
     */
    private int seq;
    
    /**
     * Private value. Left-top X position of the applet/game frame. It's used to determine which area of the screen must be captured by the Robot.
     */
    private int appletX;
    
    /**
     * Private value. Left-top Y position of the applet/game frame. It's used to determine which area of the screen must be captured by the Robot.
     */    
    private int appletY;
    
    /**
     * BufferedImage used to represent mouse cursor position. If this image does not exist, cursor will be null.
     */
    private BufferedImage cursor;

    /**
     * Number of consecutive times a snapshot failed to be stored to disk. if MAX_RETRIES is reached, the thread will be shutdown automatically.
     */
    private int retries;

    // Public methods
    
    /**
     * Makes this thread to terminate (loop in run method will break)
     */
    public synchronized void setTerminate (){
        this.terminate = true;
    }

    /**
     * Returns true if this thread has been set to terminate by previously invoking method setTerminate (). If setTerminate() has not been invoked yet, it will return true.
     */
    public synchronized boolean terminate(){
        return terminate;
    }
    
    /**
     * This is the only constructor for SnapshotProducer. It builds the object but does not set it to run.
     * @param q The shared queue of files to store snapshots. The snapshot consumer will use the same q to retrieve the snapshots. It's the GameLog Controller who deals with synchronization.
     * @param startTime Value returned by System.currentTimeMillis() when the game was launched. It's used to calculate timestamps for each snapshot.
     * @param gameRandomId  Unique random identifier for each game run. Both snapshots and gamelogs will use this value to uniquely identify them.
     * @param sampleFreq    The frequence, in milliseconds, to make snapshots. One snapshot will be captured each sampleFreq milliseconds. 
     */
    public SnapshotProducer(List<File> q, long startTime, int gameRandomId, long sampleFreq){
        this.q = q;
        this.terminate = false;
        this.startTime = startTime;
        seq=1;
        this.gameRandomId=gameRandomId;
        appletX=0;appletY=0;
        this.freq = sampleFreq;
        retries=0;
    }
    

    @Override
    // While setTerminate() is not invoked, or any problem occurs while dealing with synchronization, a snapshot is captured each freq milliseconds.
    public void run(){
        while (!terminate()){
            
            try {
                makeSnapshot();
            } catch (Exception e){
                
            }
            try {
                Thread.sleep( freq );
            }
            catch( InterruptedException e ) {
                setTerminate();
            }
        }
    }
    
    // Private methods
    
    private void makeSnapshot(){
        BufferedImage bufferedImage = null;
        Graphics2D g = null;
        boolean isAppletVisible =GUI.getInstance( )!=null && GUI.getInstance( ).getFrame( )!=null && GUI.getInstance( ).getFrame( ).isVisible( ); 
            // If applet or game frame is visible, update it's location on screen and capture it.
            if (isAppletVisible){
                Robot robot=null;
                appletX=GUI.getInstance( ).getFrame( ).getLocationOnScreen( ).x;
                appletY=GUI.getInstance( ).getFrame( ).getLocationOnScreen( ).y;
                // Create robot. If system does not support robots (e.g. some X-Window systems), an AWTException will be thrown. Then, disable snapshots
                try {
                    robot = new Robot();
                } catch (AWTException e){
                    setTerminate();
                    robot = null;
                }
                
                if (robot!=null){
                    Rectangle captureSize = new Rectangle(appletX,appletY, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT);
                    // Try capturing the screen. If a security exception is thrown, then disable screen capturing
                    try{
                        bufferedImage = robot.createScreenCapture(captureSize);
                    } catch (SecurityException e){
                        setTerminate();
                    } catch (IllegalArgumentException e1){
                        setTerminate();
                    }
                    
                    if (bufferedImage!=null){
                        g = bufferedImage.createGraphics( );
                    }
                }
            } 
            // If applet is N/A or not visible, just paint a black image with "N/A" message.
            else {
                bufferedImage = new BufferedImage(GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, BufferedImage.OPAQUE);
                g= bufferedImage.createGraphics( );
                g.setColor( Color.BLACK );
                g.fillRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
                g.setColor( Color.WHITE );
                g.drawString( "N/A", 200, 200 );
            }
            
            // Draw Timestamps
            g.setColor( Color.white );
            g.drawString( Long.toString( System.currentTimeMillis( )-startTime ), X_MARGIN, Y_TOP_MARGIN );
            g.setColor( Color.black );
            g.drawString( Long.toString( System.currentTimeMillis( )-startTime ), X_MARGIN, GUI.WINDOW_HEIGHT - Y_BOTTOM_MARGIN );
            
            // Paint mouse location
            if ( cursor==null )
                cursor = (BufferedImage)MultimediaManager.getInstance( ).loadImage( "gui/cursors/debug.png", MultimediaManager.IMAGE_SCENE );
            Point p = MouseInfo.getPointerInfo().getLocation();
            int cursorX = p.x-appletX; int cursorY = p.y-appletY;
            g.setColor( Color.yellow );
            
            if (cursor!=null)
                g.drawImage( cursor, cursorX, cursorY, null );
            else{
                g.drawLine( cursorX-5, cursorY-5, cursorX+5, cursorY+5 );
                g.drawLine( cursorX-6, cursorY-5, cursorX+4, cursorY+5 );
                g.drawLine( cursorX-5, cursorY+5, cursorX+5, cursorY-5 );
                g.drawLine( cursorX-6, cursorY+5, cursorX+4, cursorY-5 );
            }

            // Finish draw job
            g.dispose( );
            File tempFile = getFile();
            
            try {
                boolean succeeded = ImageIO.write( bufferedImage, "jpeg", tempFile );
                if (succeeded){
                    retries=0;
                    synchronized(q){
                        System.out.println( "[SN_PRODUCER:"+((System.currentTimeMillis()-startTime)/1000)+"] "+ tempFile.getAbsolutePath( ) );
                        q.add( tempFile );
                        seq++;
                    }
                }
            }
            catch( IOException e ) {
                System.out.println("[SN_PRODUCER:"+((System.currentTimeMillis()-startTime)/1000)+"] "+"Could not save file "+ tempFile.getAbsolutePath( ));
                retries++;
                if (retries>=MAX_RETRIES){
                    setTerminate();
                }
            }
    }

    /**
     * Creates the tempFile for storing the snapshot.
     * @return
     */
    private File getFile(){
        String seqStr=""+seq;
        while (seqStr.length( )<4)seqStr="0"+seqStr;
        String prefix= "eadsnapshot-"+Game.getInstance( ).getAdventureName( )+"-"+gameRandomId+"-"+seqStr+"-";
        String suffix= ".jpeg";
        File file;
        try {
            file = File.createTempFile( prefix, suffix );
        }
        catch( IOException e ) {
            file = new File (prefix+suffix);
            e.printStackTrace();
        }
        return file;
    }

}
