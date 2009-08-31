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
package es.eucm.eadventure.editor.gui.metadatadialog.lomdialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMGeneralDataControl;

public class LOMGeneralPanel extends JPanel {

    private LOMGeneralDataControl dataControl;

    private LOMTextPanel titlePanel;

    private LOMTextPanel descriptionPanel;

    public LOMGeneralPanel( LOMGeneralDataControl dControl ) {

        this.dataControl = dControl;

        //Layout
        setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );

        // Create the panels

        titlePanel = new LOMTextPanel( dataControl.getTitleController( ), TextConstants.getText( "LOM.General.Title" ), LOMTextPanel.TYPE_FIELD );
        LOMOptionsPanel languagePanel = new LOMOptionsPanel( dataControl.getLanguageController( ), TextConstants.getText( "LOM.General.Language" ) );
        descriptionPanel = new LOMTextPanel( dataControl.getDescriptionController( ), TextConstants.getText( "LOM.General.Description" ), LOMTextPanel.TYPE_AREA );
        LOMTextPanel keywordPanel = new LOMTextPanel( dataControl.getKeywordController( ), TextConstants.getText( "LOM.General.Keyword" ), LOMTextPanel.TYPE_FIELD );

        // Add the panels
        add( Box.createVerticalStrut( 1 ) );
        add( titlePanel );
        add( Box.createVerticalStrut( 1 ) );
        add( languagePanel );
        add( Box.createVerticalStrut( 1 ) );
        add( descriptionPanel );
        add( Box.createVerticalStrut( 1 ) );
        add( keywordPanel );
        // Add "set defaults" button: If you press here, the title and description fields will be filled with the title and description of the adventure 
        JButton setDefaults = new JButton( TextConstants.getText( "LOM.General.SetDefaults" ) );
        setDefaults.setToolTipText( TextConstants.getText( "LOM.General.SetDefaultsTip" ) );
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
        add( Box.createRigidArea( new Dimension( 400, 100 ) ) );
    }
}
