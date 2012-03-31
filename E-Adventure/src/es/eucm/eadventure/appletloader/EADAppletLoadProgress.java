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

package es.eucm.eadventure.appletloader;

import java.applet.AppletStub;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.jnlp.DownloadServiceListener;
import javax.swing.JPanel;

public class EADAppletLoadProgress implements DownloadServiceListener{
    Container surfaceContainer = null;
    AppletStub appletStub = null;
    private BufferedImage backgroundImage=null;
    private Color backgroundColor=null;
    private Color foregroundColor=null;
    private Color textColor=null;
    private String loadingMessage;
    
    private int progressX;
    private int progressY;
    private int progressWidth;
    private int progressHeight;
    private int textX;
    private int textY;
    private float fontSize;
    boolean uiCreated = false;

    private ProgressPanel top;

    public EADAppletLoadProgress( ) {
        init(null, null);
     }
    
    public EADAppletLoadProgress(Object surface) {
       init(surface, null);
    }

    public EADAppletLoadProgress(Object surface, Object stub) {
        init(surface, stub);
    }

    public void init(Object surface, Object stub) {
        try {
            if (surface!=null){
                surfaceContainer = (Container) surface;
            }
            if (stub!=null){
                appletStub = (AppletStub) stub;
            }
        } catch (ClassCastException cce) {
            cce.printStackTrace( );
            // ...
        }
    }
    
    private void create() {
        initParams();
        top = new ProgressPanel();
        if (surfaceContainer != null) {
            // lay out loading progress UI in the given Container
            surfaceContainer.setLayout( new BorderLayout() );
            surfaceContainer.add(top, BorderLayout.CENTER);
            surfaceContainer.invalidate();
            surfaceContainer.validate();
        }     
    }
    
    private void initParams() {
        // ...
        // get applet parameter using an instance of the
        // AppletStub class "tagLine" parameter specified
        // in applet's JNLP file
        String messageParam = "";
        String backgroundParam=null;
        String bgColorParam=null;
        String fgColorParam=null;
        String textColorParam=null;
        String fontSizeParam=null;
        String textXParam=null;
        String textYParam=null;
        String xParam=null;
        String yParam=null;
        String wParam=null;
        String hParam=null;
        if (appletStub != null) {
            messageParam = appletStub.getParameter("progress-ui-message");
            backgroundParam = appletStub.getParameter( "progress-ui-background" );
            bgColorParam = appletStub.getParameter( "progress-ui-bgcolor" );
            fgColorParam = appletStub.getParameter( "progress-ui-fgcolor" );
            
            textColorParam = appletStub.getParameter( "progress-ui-textcolor" );
            fontSizeParam = appletStub.getParameter( "progress-ui-fontsize" );
            textXParam = appletStub.getParameter( "progress-ui-textx" );
            textYParam = appletStub.getParameter( "progress-ui-texty" );
            xParam = appletStub.getParameter( "progress-ui-x" );
            yParam = appletStub.getParameter( "progress-ui-y" );
            wParam = appletStub.getParameter( "progress-ui-width" );
            hParam = appletStub.getParameter( "progress-ui-height" );
        }
        
        if ( backgroundParam!=null ){
            try {
                backgroundImage = ImageIO.read( EADAppletLoadProgress.class.getResourceAsStream( backgroundParam ) );
            }
            catch( IOException e ) {
                backgroundImage = null;
            }
        }
        
        if ( backgroundImage==null ){
            backgroundImage = new BufferedImage(800, 600, BufferedImage.OPAQUE);
            Graphics2D g = backgroundImage.createGraphics( );
            g.setColor( Color.lightGray );
            g.fillRect( 0, 0, 800, 600 );
            g.setStroke( new BasicStroke(5.0f) );
            g.setColor ( Color.darkGray );
            g.drawRect( 2, 2, 800-2, 600-2 );
            g.dispose( );            
        }
        
        if (bgColorParam!=null){
            try {
                backgroundColor = new Color(Integer.parseInt( bgColorParam ));
            } catch (Exception e){
                backgroundColor=null;
            }
        }
        
        if (backgroundColor==null){
            backgroundColor=Color.orange;
        }
        
        if (fgColorParam!=null){
            try {
                foregroundColor = new Color(Integer.parseInt( fgColorParam ));
            } catch (Exception e){
                foregroundColor=null;
            }
        }
        
        if (foregroundColor==null){
            foregroundColor=Color.orange;
        }
        
        if (textColorParam!=null){
            try {
                textColor = new Color(Integer.parseInt( textColorParam ));
            } catch (Exception e){
                textColor=null;
            }
        }
        
        if (textColor==null){
            textColor=Color.DARK_GRAY;
        }

        if (messageParam!=null){
            loadingMessage=messageParam;
        } else {
            loadingMessage = "Downloading Applet. Please be patient";
        }


        if (textXParam!=null){
            try{
                textX = Integer.parseInt( textXParam );
            } catch (Exception e){
                textX = 15;
            }
        } else{
            textX=15;
        }
        
        if (textYParam!=null){
            try{
                textY = Integer.parseInt( textYParam );
            } catch (Exception e){
                textY = 550;
            }
        } else{
            textX=550;
        }

        if (xParam!=null){
            try{
                progressX = Integer.parseInt( xParam );
            } catch (Exception e){
                progressX = 15;
            }
        } else{
            progressX=15;
        }
        
        if (yParam!=null){
            try{
                progressY = Integer.parseInt( yParam );
            } catch (Exception e){
                progressY = 500;
            }
        } else{
            progressY=500;
        }

        if (wParam!=null){
            try{
                progressWidth = Integer.parseInt( wParam );
            } catch (Exception e){
                progressWidth = 800-15*2;
            }
        } else{
            progressWidth = 800-15*2;
        }
        
        if (hParam!=null){
            try{
                progressHeight = Integer.parseInt( hParam );
            } catch (Exception e){
                progressHeight = 30;
            }
        } else{
            progressHeight=30;
        }

        if (fontSizeParam!=null){
            try{
                fontSize = Float.parseFloat( fontSizeParam );
            } catch (Exception e){
                fontSize = 18.0F;
            }
        } else{
            fontSize=30;
        }
    }
    
    public void progress(URL url, String version, long readSoFar, long total, int overallPercent) {        
        // check progress of download and update display
        System.out.println( "**PROGRESS: URL="+url+" Version="+version+" ReadSoFar="+readSoFar+" Total="+total+" OverallPercent="+overallPercent );
        updateProgressUI(overallPercent);
    }

    public void upgradingArchive(java.net.URL url,   java.lang.String version, int patchPercent, int overallPercent) {
        System.out.println( "**UPGRADIN GARCHIVE: URL="+url+" Version="+version+" PatchPercent="+patchPercent+" OverallPercent="+overallPercent );
        updateProgressUI(overallPercent);
    }

    public void validating(java.net.URL url, java.lang.String version, long entry, long total, int overallPercent) {
        System.out.println( "**VALIDATING: URL="+url+" Version="+version+" Entry="+entry+" Total="+total+" OverallPercent="+overallPercent );
        updateProgressUI(overallPercent);
    }

    private void updateProgressUI(int overallPercent) {
        if (!uiCreated && overallPercent > 0 && overallPercent < 100) {
            // create custom progress indicator's
            // UI only if there is more work to do,
            // meaning overallPercent > 0 and
            // < 100 this prevents flashing when
            // RIA is loaded from cache
            create(); 
            uiCreated = true;            
        }
        if (uiCreated) {
            top.setProgress( overallPercent/100.0F );
        }
    }

    public void downloadFailed( URL arg0, String arg1 ) {
        System.out.println( "DOWNLOAD FAILED: "+arg0+" , "+arg1 );
    }
    
    private class ProgressPanel extends JPanel {
        
        /**
         * Generated
         */
        private static final long serialVersionUID = -6502654321656542251L;
        private float progress;
        
        public ProgressPanel( ) {
            super( );
            progress=0;
        }

        @Override
        public void paint (Graphics g){
            System.out.println( "PAINTING PROGRESSSSS");
            Graphics2D g2d=(Graphics2D)g;
            g2d.drawImage( backgroundImage, 0, 0, backgroundImage.getWidth( null ), backgroundImage.getHeight( null ), null);
            g2d.setColor( backgroundColor );
            g2d.fillRoundRect( progressX, progressY, Math.round( progressWidth*progress ), progressHeight, 15, 15 );
            g2d.setColor( textColor );
            g2d.setFont( g2d.getFont( ).deriveFont( Font.BOLD ).deriveFont( fontSize ) );
            g2d.drawString( loadingMessage, textX, textY );
            g2d.setColor( foregroundColor );
            g2d.setStroke( new BasicStroke(3.0f) );
            g2d.drawRoundRect( progressX, progressY, progressWidth, progressHeight, 15, 15 );
            
        }
        
        private void setProgress(float progress){
            System.out.println( "PROGRESS UPDATED = "+progress);
            this.progress = progress;
            top.invalidate( );
            top.validate( );
            top.repaint( );
        }
    }
}
