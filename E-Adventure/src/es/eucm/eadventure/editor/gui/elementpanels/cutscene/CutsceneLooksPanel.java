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
package es.eucm.eadventure.editor.gui.elementpanels.cutscene;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;
import es.eucm.eadventure.gui.EAdPanel;

public class CutsceneLooksPanel extends LooksPanel implements Updateable {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    private ImagePanel imagePanel;

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
        EAdPanel previewPanel = new EAdPanel( );
        previewPanel.setLayout( new BorderLayout( ) );
        imagePanel = new ImagePanel( imagePath );
        previewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Book.Preview" ) ) );
        previewPanel.add( imagePanel, BorderLayout.CENTER );
        lookPanel.add( previewPanel, cLook );
        lookPanel.setPreferredSize( new Dimension( 0, 90 ) );
    }

    @Override
    public void updatePreview( ) {

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
