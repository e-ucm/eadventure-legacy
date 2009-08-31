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
package es.eucm.eadventure.editor.gui.elementpanels.character;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DetailedDescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;

public class NPCDocPanel extends JPanel {

    private static final long serialVersionUID = -4320895192596025506L;

    private NPCDataControl dataControl;

    /**
     * Text area for the documentation.
     */
    private JTextArea documentationTextArea;

    /**
     * Text field for the name.
     */
    private JTextField nameTextField;

    /**
     * Text field for the description.
     */
    private JTextField descriptionTextField;

    /**
     * Text field for the detailed description.
     */
    private JTextField detailedDescriptionTextField;

    public NPCDocPanel( NPCDataControl dataControl ) {

        this.dataControl = dataControl;
        setLayout( new GridBagLayout( ) );
        GridBagConstraints cDoc = new GridBagConstraints( );

        cDoc.insets = new Insets( 5, 5, 2, 2 );

        // Create the text area for the documentation
        cDoc.fill = GridBagConstraints.BOTH;
        cDoc.weightx = 1;
        cDoc.weighty = 1;
        JPanel documentationPanel = new JPanel( );
        documentationPanel.setLayout( new BorderLayout( ) );
        documentationTextArea = new JTextArea( dataControl.getDocumentation( ), 4, 0 );
        documentationTextArea.setLineWrap( true );
        documentationTextArea.setWrapStyleWord( true );
        documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) dataControl.getContent( ) ) );
        documentationPanel.add( new JScrollPane( documentationTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
        documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Documentation" ) ) );
        add( documentationPanel, cDoc );

        cDoc.fill = GridBagConstraints.HORIZONTAL;
        cDoc.weighty = 0;

        // Create the field for the name
        cDoc.gridy = 1;
        JPanel namePanel = new JPanel( );
        namePanel.setLayout( new GridLayout( ) );
        nameTextField = new JTextField( dataControl.getName( ) );
        nameTextField.getDocument( ).addDocumentListener( new NameChangeListener( nameTextField, (Named) dataControl.getContent( ) ) );
        namePanel.add( nameTextField );
        namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Name" ) ) );
        add( namePanel, cDoc );

        // Create the field for the brief description
        cDoc.gridy = 2;
        JPanel descriptionPanel = new JPanel( );
        descriptionPanel.setLayout( new GridLayout( ) );
        descriptionTextField = new JTextField( dataControl.getBriefDescription( ) );
        descriptionTextField.getDocument( ).addDocumentListener( new DescriptionChangeListener( descriptionTextField, (Described) dataControl.getContent( ) ) );
        descriptionPanel.add( descriptionTextField );
        descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Description" ) ) );
        add( descriptionPanel, cDoc );

        // Create the field for the detailed description
        cDoc.gridy = 3;
        JPanel detailedDescriptionPanel = new JPanel( );
        detailedDescriptionPanel.setLayout( new GridLayout( ) );
        detailedDescriptionTextField = new JTextField( dataControl.getDetailedDescription( ) );
        detailedDescriptionTextField.getDocument( ).addDocumentListener( new DetailedDescriptionChangeListener( detailedDescriptionTextField, (Detailed) dataControl.getContent( ) ) );
        detailedDescriptionPanel.add( detailedDescriptionTextField );
        detailedDescriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.DetailedDescription" ) ) );
        add( detailedDescriptionPanel, cDoc );
    }

    public boolean updateFields( ) {

        return false;
    }

}
