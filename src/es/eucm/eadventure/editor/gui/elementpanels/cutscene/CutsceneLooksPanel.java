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
package es.eucm.eadventure.editor.gui.elementpanels.cutscene;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class CutsceneLooksPanel extends LooksPanel implements Updateable {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    private ImagePanel imagePanel;
    
    private JCheckBox canSkip;
    
    

    /**
     * Constructor.
     * 
     * @param cutsceneDataControl
     *            Cutscene controller
     */
    public CutsceneLooksPanel( CutsceneDataControl cutsceneDataControl ) {

        super( cutsceneDataControl );
    }

    @Override
    protected void createPreview( ) {
        String imagePath = ( (CutsceneDataControl) dataControl ).getPreviewImage( );
        if( imagePath == null )
            imagePath = "";
        JPanel previewPanel = new JPanel( );
        previewPanel.setLayout( new BorderLayout( ) );
        imagePanel = new ImagePanel( imagePath );
        previewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Book.Preview" ) ) );
        previewPanel.add( imagePanel, BorderLayout.CENTER );
        lookPanel.add( previewPanel, cLook );
        lookPanel.setPreferredSize( new Dimension( 0, 90 ) );
        
        if (((CutsceneDataControl) dataControl).isVideoscene( )){
            canSkip = new JCheckBox ();
            canSkip.setSelected( ((CutsceneDataControl) dataControl).getCanSkip() );
            canSkip.addActionListener( new ActionListener( ) {
                public void actionPerformed( ActionEvent e ) {
                    Controller.getInstance( ).addTool( new ChangeBooleanValueTool( CutsceneLooksPanel.this.dataControl, ( (JCheckBox) e.getSource( ) ).isSelected( ),"getCanSkip"  , "setCanSkip") );
                }
            } );
            
            JLabel label = new JLabel(TC.get( "Videoscene.Skip.label" ));
            
            JPanel skipPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.weightx = 0;
            gbc.weighty = 1;
            skipPanel.add( canSkip, gbc );
            gbc.gridx = 1;
            skipPanel.add( label, gbc );
            skipPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Videoscene.Skip.border" ) ) );
            add( skipPanel, cPanel );
            
        }
        
        
    }

    @Override
    public void updatePreview( ) {

        if (((CutsceneDataControl) dataControl).isVideoscene( ))
            canSkip.setSelected( ((CutsceneDataControl) dataControl).getCanSkip() );
        
        String imagePath = ( (CutsceneDataControl) dataControl ).getPreviewImage( );
        if( imagePath == null )
            imagePath = "";
        imagePanel.loadImage( imagePath );
        imagePanel.repaint( );
        if( getParent( ) != null && getParent( ).getParent( ) != null )
            getParent( ).getParent( ).repaint( );
    }

    @Override
    public void updateResources( ) {

        super.updateResources( );
        if( getParent( ) != null && getParent( ).getParent( ) != null )
            getParent( ).getParent( ).repaint( );
    }

}
