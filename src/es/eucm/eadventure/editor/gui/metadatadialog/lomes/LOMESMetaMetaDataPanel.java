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
