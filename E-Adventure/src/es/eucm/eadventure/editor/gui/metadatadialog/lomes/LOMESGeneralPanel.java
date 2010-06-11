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
package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
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

        titlePanel = new LOMESTextPanel( dataControl.getTitleController( ), TC.get( "LOM.General.Title" ), LOMESTextPanel.TYPE_FIELD );
        LOMCreatePrimitiveContainerPanel languagePanel = new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.STRING_TYPE, dataControl.getLanguages( ), TC.get( "LOM.General.Language" ), LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD );
        descriptionPanel = new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.LANGSTRING_TYPE, dataControl.getDescriptions( ), TC.get( "LOM.General.Description" ), LOMCreatePrimitiveContainerPanel.FIELD_TYPE_AREA );
        LOMESOptionsPanel aggregationLevel = new LOMESOptionsPanel( dataControl.getAggregationLevel( ), TC.get( "LOMES.General.AggregationLevel" ) );
        // there is necessary to 
        LOMESCreateContainerPanel identifierPanel = new LOMESCreateContainerPanel( dataControl.getIdentifier( ), TC.get( "LOMES.General.Identifier" ), LOMContributeDialog.NONE );
        LOMCreatePrimitiveContainerPanel keywordPanel = new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.LANGSTRING_TYPE, dataControl.getKeywords( ), TC.get( "LOM.General.Keyword" ), LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD );

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
        JButton setDefaults = new JButton( TC.get( "LOM.General.SetDefaults" ) );
        setDefaults.setToolTipText( TC.get( "LOM.General.SetDefaultsTip" ) );
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
