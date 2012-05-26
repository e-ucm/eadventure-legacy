/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.metadatadialog.ims;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSGeneralDataControl;

public class IMSGeneralPanel extends JPanel {

    private IMSGeneralDataControl dataControl;

    private IMSTextPanel titlePanel;

    private IMSTextPanel descriptionPanel;

    public IMSGeneralPanel( IMSGeneralDataControl dControl ) {

        this.dataControl = dControl;

        //Layout
        setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );

        // Create the panels

        titlePanel = new IMSTextPanel( dataControl.getTitleController( ), TC.get( "LOM.General.Title" ), IMSTextPanel.TYPE_FIELD );
        IMSOptionsPanel languagePanel = new IMSOptionsPanel( dataControl.getLanguageController( ), TC.get( "LOM.General.Language" ) );
        descriptionPanel = new IMSTextPanel( dataControl.getDescriptionController( ), TC.get( "LOM.General.Description" ), IMSTextPanel.TYPE_AREA );
        IMSTextPanel keywordPanel = new IMSTextPanel( dataControl.getKeywordController( ), TC.get( "LOM.General.Keyword" ), IMSTextPanel.TYPE_FIELD );
        IMSTextPanel catalog = new IMSTextPanel( dataControl.getCatalogController( ), TC.get( "IMS.General.Catalog" ), IMSTextPanel.TYPE_FIELD );
        IMSTextPanel entry = new IMSTextPanel( dataControl.getEntryController( ), TC.get( "IMS.General.Entry" ), IMSTextPanel.TYPE_FIELD );

        // Add the panels
        add( Box.createVerticalStrut( 1 ) );
        add( titlePanel );
        add( Box.createVerticalStrut( 1 ) );
        add( catalog );
        add( Box.createVerticalStrut( 1 ) );
        add( entry );
        add( Box.createVerticalStrut( 1 ) );
        add( languagePanel );
        add( Box.createVerticalStrut( 1 ) );
        add( descriptionPanel );
        add( Box.createVerticalStrut( 1 ) );
        add( keywordPanel );
        // Add "set defaults" button: If you press here, the title and description fields will be filled with the title and description of the adventure 
        JButton setDefaults = new JButton( TC.get( "LOM.General.SetDefaults" ) );
        setDefaults.setToolTipText( TC.get( "LOM.General.SetDefaultsTip" ) );
        setDefaults.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                String adventureDesc = Controller.getInstance( ).getAdventureDescription( );
                String adventureTitle = Controller.getInstance( ).getAdventureTitle( );
                dataControl.getTitleController( ).setText( adventureTitle );
                dataControl.getDescriptionController( ).setText( adventureDesc );
                titlePanel.updateText( );
                descriptionPanel.updateText( );
            }
        } );

        JPanel buttonPanel = new JPanel( );
        buttonPanel.add( setDefaults );
        add( Box.createVerticalStrut( 1 ) );
        add( buttonPanel );
        add( Box.createRigidArea( new Dimension( 400, 45 ) ) );
        //setSize(400, 100);
    }
}
