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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESGeneralDataControl;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMCreatePrimitiveContainerPanel;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMContributeDialog;

public class LOMESGeneralPanel extends JPanel {

    private LOMESGeneralDataControl dataControl;

    private LOMESTextPanel titlePanel;

    private LOMCreatePrimitiveContainerPanel descriptionPanel;

    public LOMESGeneralPanel( LOMESGeneralDataControl dControl ) {

        this.dataControl = dControl;

        //Layout
        setLayout( new GridBagLayout( ) );

        // Create the panels

        titlePanel = new LOMESTextPanel( dataControl.getTitleController( ), TextConstants.getText( "LOM.General.Title" ), LOMESTextPanel.TYPE_FIELD );
        LOMCreatePrimitiveContainerPanel languagePanel = new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.STRING_TYPE, dataControl.getLanguages( ), TextConstants.getText( "LOM.General.Language" ), LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD );
        descriptionPanel = new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.LANGSTRING_TYPE, dataControl.getDescriptions( ), TextConstants.getText( "LOM.General.Description" ), LOMCreatePrimitiveContainerPanel.FIELD_TYPE_AREA );
        LOMESOptionsPanel aggregationLevel = new LOMESOptionsPanel( dataControl.getAggregationLevel( ), TextConstants.getText( "LOMES.General.AggregationLevel" ) );
        // there is necessary to 
        LOMESCreateContainerPanel identifierPanel = new LOMESCreateContainerPanel( dataControl.getIdentifier( ), TextConstants.getText( "LOMES.General.Identifier" ), LOMContributeDialog.NONE );
        LOMCreatePrimitiveContainerPanel keywordPanel = new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.LANGSTRING_TYPE, dataControl.getKeywords( ), TextConstants.getText( "LOM.General.Keyword" ), LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD );

        GridBagConstraints c = new GridBagConstraints( );
        JPanel container = new JPanel( );
        container.setLayout( new GridBagLayout( ) );
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        // Add the panels
        container.add( titlePanel, c );
        c.gridy++;
        container.add( identifierPanel, c );
        c.gridy++;
        container.add( languagePanel, c );
        c.gridy++;
        container.add( descriptionPanel, c );
        c.gridy++;
        container.add( keywordPanel, c );
        c.gridy++;
        container.add( aggregationLevel, c );
        // Add "set defaults" button: If you press here, the title and description fields will be filled with the title and description of the adventure 
        JButton setDefaults = new JButton( TextConstants.getText( "LOM.General.SetDefaults" ) );
        setDefaults.setToolTipText( TextConstants.getText( "LOM.General.SetDefaultsTip" ) );
        setDefaults.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                String adventureDesc = Controller.getInstance( ).getAdventureDescription( );
                String adventureTitle = Controller.getInstance( ).getAdventureTitle( );
                dataControl.getTitleController( ).setText( adventureTitle );
                //dataControl.getDescriptionController( ).setText( adventureDesc );
                titlePanel.updateText( );
                //descriptionPanel.updateText( );
                descriptionPanel.addLangstring( adventureDesc );

            }
        } );

        JPanel buttonPanel = new JPanel( );
        buttonPanel.add( setDefaults );
        c.gridy++;
        container.add( buttonPanel, c );

        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        add( container, c );
    }

}
