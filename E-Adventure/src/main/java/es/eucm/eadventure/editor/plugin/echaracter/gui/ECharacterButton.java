package es.eucm.eadventure.editor.plugin.echaracter.gui;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.plugin.echaracter.control.ECharacterLauncher;
import es.eucm.eadventure.engine.core.gui.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class ECharacterButton extends JButton {
    
    /**
     * Generated
     */
    private static final long serialVersionUID = 9141821435802269755L;
    private BufferedImage normalImage;
    private BufferedImage overImage;
    private ResourcesDataControl resources;
    
    public ECharacterButton (ResourcesDataControl resources){
        this.resources = resources;
        normalImage = makeImage ("img/echaracter-normal.png");
        overImage = makeImage ("img/echaracter-over.png");
        this.setOpaque( false );
        this.setContentAreaFilled( false );
        this.setIcon( new ImageIcon(normalImage) );
        this.setEnabled( ECharacterLauncher.isAvailable( ) );
        this.addActionListener( new CreateWithECharacterButtonListener( ) );
        this.addMouseListener( new MouseAdapter(){

            @Override
            public void mouseEntered( MouseEvent e ) {
                ECharacterButton.this.setIcon( new ImageIcon(overImage) );
            }

            @Override
            public void mouseExited( MouseEvent e ) {
                ECharacterButton.this.setIcon( new ImageIcon(normalImage) );
            }
        });
        this.addFocusListener( new FocusListener(){

            @Override
            public void focusGained( FocusEvent e ) {
                ECharacterButton.this.setIcon( new ImageIcon(overImage) );
            }

            @Override
            public void focusLost( FocusEvent e ) {
                ECharacterButton.this.setIcon( new ImageIcon(normalImage) );
            }
            
        });
    }
    
    private BufferedImage makeImage ( String path ){
        BufferedImage img= null;
        try {
            img= ImageIO.read( new File(path) );
            String text = TC.get( "ECharacter.CreateWith" );
            FileInputStream is = new FileInputStream( "gui/options/quicksand_bold.ttf" );
            Font originalFont = Font.createFont( Font.TRUETYPE_FONT, is );
            HashMap<TextAttribute, Number> attributes = new HashMap<TextAttribute, Number>();
            attributes.put( TextAttribute.WIDTH, TextAttribute.WIDTH_SEMI_CONDENSED );
            originalFont = originalFont.deriveFont( attributes );

            // Create the neccessary fonts
            originalFont = originalFont.deriveFont( Font.BOLD, 18.0F );
            Graphics2D g =img.createGraphics( );
            g.setRenderingHints( GUI.getOptimumRenderingHints( ) );
            g.setFont( originalFont );
            g.setColor( Color.WHITE );
            g.drawString( text, 39, 22 );
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
    
    
    /**
     * This class is the listener for the "Create icon" buttons on the panels.
     */
    private class CreateWithECharacterButtonListener implements ActionListener {

        /**
         * Constructor.
         * 
         * @param assetIndex
         *            Index of the asset
         */
        public CreateWithECharacterButtonListener(  ) {
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( final ActionEvent e ) {
            new Thread(){
                @Override
                public void run(){
                    new ECharacterLauncher(resources).launch( (JButton)(e.getSource( )) );      
                }
            }.start( );
            
        }
    }
}
