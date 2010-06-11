/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.elementpanels.cutscene;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

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
        JPanel previewPanel = new JPanel( );
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
