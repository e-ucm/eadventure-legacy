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

package es.eucm.eadventure.editor.plugin.vignette;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.engine.core.gui.GUI;


public class VignetteButton extends JButton {
    
    /**
     * Generated
     */
    private static final long serialVersionUID = 9141821435802269755L;
    private ImageIcon normalImage;
    private ImageIcon overImage;
    private ImageIcon disabledImage;
    
    private String tooltipEnabled;
    private String tooltipDisabled;
    
    private ActionListener listener;
    
    
    private boolean export = false;
    private VignetteConversationWrapper wrapper;
    
    public void updateButton( ){
        this.setEnabled( wrapper.getPermission( export?VignetteUICallback.OPERATION_EXPORT:VignetteUICallback.OPERATION_IMPORT ) );
    }
    
    public VignetteButton ( VignetteConversationWrapper w, boolean export ){
        this.wrapper = w;
        this.export = export;
        if (!export){
            normalImage = new ImageIcon(makeImage (export, "img/vignette-import-normal.png"));
            overImage = new ImageIcon(makeImage (export,"img/vignette-import-over.png"));
            disabledImage = new ImageIcon(makeImage (export,"img/vignette-import-disabled.png"));
            tooltipEnabled = TC.get( "Vignette.Import.Tooltip.Enabled" );
            tooltipDisabled = TC.get( "Vignette.Import.Tooltip.Disabled" );
        } else {
            normalImage = new ImageIcon(makeImage (export,"img/vignette-edit-normal.png"));
            overImage = new ImageIcon(makeImage (export,"img/vignette-edit-over.png"));
            disabledImage = new ImageIcon(makeImage (export,"img/vignette-edit-disabled.png"));
            tooltipEnabled = TC.get( "Vignette.CreateWith.Tooltip.Enabled" );
            tooltipDisabled = TC.get( "Vignette.CreateWith.Tooltip.Disabled" );
        }
        this.setOpaque( false );
        this.setContentAreaFilled( false );
        this.swapIconAndTooltip( false );
        updateButton();
        listener = new ActionListener(){

            @Override
            public void actionPerformed( ActionEvent e ) {
                new Thread(){
                    @Override
                    public void run(){
                        if (VignetteButton.this.export){
                           wrapper.exportToVignette( );
                        } else {
                           wrapper.importFromVignette( );
                        }
                    }
                }.start( );                
            }
            
        };
        this.addActionListener( listener );
        
        this.addMouseListener( new MouseListener(){

            @Override
            public void mouseClicked( MouseEvent e ) {
                
            }

            @Override
            public void mousePressed( MouseEvent e ) {
                
            }

            @Override
            public void mouseReleased( MouseEvent e ) {
                
            }

            @Override
            public void mouseEntered( MouseEvent e ) {
                swapIconAndTooltip(true);
            }

            @Override
            public void mouseExited( MouseEvent e ) {
                swapIconAndTooltip(false);
            }
            
        });
        this.addFocusListener( new FocusListener(){

            @Override
            public void focusGained( FocusEvent e ) {
                swapIconAndTooltip(true);
            }

            @Override
            public void focusLost( FocusEvent e ) {
                swapIconAndTooltip(false);
            }
            
        });
    }
    
    private void swapIconAndTooltip(boolean over){
        if (isEnabled( )){
            if (over){
                setIcon( overImage) ;
            } else {
                setIcon( normalImage) ;
            }
            this.setToolTipText( tooltipEnabled );
        } else {
            setIcon( disabledImage );
            this.setToolTipText( tooltipDisabled );
        }
    }
    
    @Override
    public void setEnabled (boolean enabled){
        super.setEnabled( enabled );
        swapIconAndTooltip(false);
    }
    
    private BufferedImage makeImage ( boolean export, String path ){
        BufferedImage img= null;
        try {
            img= ImageIO.read( new File(path) );
            String text = TC.get( "Vignette.CreateWith" );
            if (!export)
                text = TC.get( "Vignette.Import" );
            FileInputStream is = new FileInputStream( "gui/options/quicksand_bold.ttf" );
            Font originalFont = Font.createFont( Font.TRUETYPE_FONT, is );
            HashMap<TextAttribute, Number> attributes = new HashMap<TextAttribute, Number>();
            //attributes.put( TextAttribute.WIDTH, TextAttribute.WIDTH_SEMI_CONDENSED );
            attributes.put( TextAttribute.WIDTH, TextAttribute.WIDTH_CONDENSED );
            originalFont = originalFont.deriveFont( attributes );

            // Create the neccessary fonts
            //originalFont = originalFont.deriveFont( Font.BOLD, 13.0F ).deriveFont( AffineTransform.getScaleInstance( 0.7, 0.85 ) );
            originalFont = originalFont.deriveFont( Font.BOLD, 13.0F );
            Graphics2D g =img.createGraphics( );
            g.setRenderingHints( GUI.getOptimumRenderingHints( ) );
            g.setFont( originalFont );
            g.setColor( Color.WHITE );
            g.drawString( text, 19, 13 );
            g.dispose( );
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
        catch( FontFormatException e ) {
            e.printStackTrace();
        }
        return img;
    }
  
    
}
