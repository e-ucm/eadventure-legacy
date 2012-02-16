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

package es.eucm.eadventure.engine.gamelog;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;


public class SnapshotProducer extends Thread{

    public static final long FREQ = 10000;
    
    private List<File> q;
    
    private boolean terminate;
    
    private long startTime;
    
    private int randomId;
    private int seq;
    
    private int x;
    private int y;
    
    public synchronized void setTerminate (boolean interrupt){
        this.terminate = interrupt;
    }
    
    public synchronized boolean terminate(){
        return terminate;
    }
    
    public SnapshotProducer(List<File> q, long startTime, int randomId){
        this.q = q;
        setTerminate(false);
        this.startTime = startTime;
        seq=1;
        this.randomId=randomId;
        x=0;y=0;
    }
    
    private void makeSnapshot(){
        Robot robot;
        try {
            if (GUI.getInstance( )!=null && GUI.getInstance( ).getFrame( )!=null && GUI.getInstance( ).getFrame( ).isVisible( )){
                x=GUI.getInstance( ).getFrame( ).getLocationOnScreen( ).x;
                y=GUI.getInstance( ).getFrame( ).getLocationOnScreen( ).y;
            }
            robot = new Robot();
            Rectangle captureSize = new Rectangle(x,y, 800, 600);
            BufferedImage bufferedImage = robot.createScreenCapture(captureSize);
            Graphics2D g = bufferedImage.createGraphics( );
            g.setColor( Color.white );
            g.drawString( Long.toString( System.currentTimeMillis( )-startTime ), 5, 10 );
            g.setColor( Color.black );
            g.drawString( Long.toString( System.currentTimeMillis( )-startTime ), 5, 550 );
            g.dispose( );
            File tempFile = getFile();
            boolean succeeded = ImageIO.write( bufferedImage, "jpeg", tempFile );
            if (succeeded){
                synchronized(q){
                    System.out.println( "[SN_PRODUCER:"+((System.currentTimeMillis()-startTime)/1000)+"] "+ tempFile.getAbsolutePath( ) );
                    q.add( tempFile );
                    seq++;
                }
            }
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while (!terminate()){
            try {
                makeSnapshot();
                Thread.sleep( FREQ );
            }
            catch( InterruptedException e ) {
            }
        }
    }

    private File getFile(){
        String seqStr=""+seq;
        while (seqStr.length( )<4)seqStr="0"+seqStr;
        String prefix= "eadsnapshot-"+Game.getInstance( ).getAdventureName( )+"-"+randomId+"-"+seqStr+"-";
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
