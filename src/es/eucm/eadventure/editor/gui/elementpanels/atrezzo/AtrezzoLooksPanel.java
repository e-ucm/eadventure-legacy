/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.gui.elementpanels.atrezzo;

import java.awt.Dimension;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.gui.TextConstants;
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
        imagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Atrezzo.Preview" ) ) );
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
