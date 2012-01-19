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
package es.eucm.eadventure.editor.gui.elementpanels.atrezzo;

import java.awt.Dimension;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class AtrezzoLooksPanel extends LooksPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Preview image panel.
     */
    private ImagePanel imagePanel;

    private AtrezzoDataControl atrezzoDataControl;

    public AtrezzoLooksPanel( AtrezzoDataControl control ) {

        super( control );
        this.atrezzoDataControl = control;
    }

    @Override
    protected void createPreview( ) {

        atrezzoDataControl = (AtrezzoDataControl) this.dataControl;
        // Take the path to the current image of the atrezzo item
        String atrezzoImagePath = atrezzoDataControl.getPreviewImage( );

        imagePanel = new ImagePanel( atrezzoImagePath );
        imagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Atrezzo.Preview" ) ) );
        lookPanel.add( imagePanel, cLook );
        // TODO Parche, arreglar
        lookPanel.setPreferredSize( new Dimension( 0, 120 ) );

    }

    @Override
    public void updatePreview( ) {

        imagePanel.loadImage( atrezzoDataControl.getPreviewImage( ) );
        imagePanel.repaint( );
    }

    @Override
    public void updateResources( ) {

        super.updateResources( );
        if( getParent( ) != null )
            getParent( ).repaint( );
    }

}
