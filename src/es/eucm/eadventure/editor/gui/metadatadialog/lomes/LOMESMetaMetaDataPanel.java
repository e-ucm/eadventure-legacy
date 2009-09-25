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
package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMContributeDialog;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESMetaMetaDataControl;

public class LOMESMetaMetaDataPanel extends JPanel {

    public LOMESMetaMetaDataPanel( LOMESMetaMetaDataControl metaController ) {

        //Layout
        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        //Meta panels
        LOMESTextPanel meta = new LOMESTextPanel( metaController.getMetadataschemeController( ), TC.get( "IMS.MetaMetaData.Metadatascheme" ), LOMESTextPanel.TYPE_FIELD );
        LOMESCreateContainerPanel identifierPanel = new LOMESCreateContainerPanel( metaController.getIdentifier( ), TC.get( "LOMES.General.Identifier" ), LOMContributeDialog.NONE );
        LOMESCreateContainerPanel contribute = new LOMESCreateContainerPanel( metaController.getContribute( ), TC.get( "LOMES.LifeCycle.Contribute" ), LOMContributeDialog.METAMETADATA );
        LOMESOptionsPanel language = new LOMESOptionsPanel( metaController.getLanguageController( ), TC.get( "LOMES.MetaMetaData.Language" ) );
        //LOMESOptionsPanel catalog = new LOMESOptionsPanel(metaController.getCatalog(), TextConstants.getText("LOMES.LifeCycle.Role"));

        // add Panels

        JPanel container = new JPanel( );
        container.setLayout( new GridBagLayout( ) );
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        //c.weighty=1.0;
        //Add the panels
        container.add( meta, c );
        c.gridy = 1;
        container.add( identifierPanel, c );
        c.gridy = 2;
        container.add( contribute, c );
        c.gridy = 3;
        container.add( language, c );

        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        add( container, c );
    }
}
