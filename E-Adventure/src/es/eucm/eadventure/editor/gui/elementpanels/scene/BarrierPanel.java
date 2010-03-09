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
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.gui.EAdButton;
import es.eucm.eadventure.gui.EAdPanel;
import es.eucm.eadventure.gui.EAdScrollPane;
import es.eucm.eadventure.gui.EAdTabbedPane;
import es.eucm.eadventure.gui.EAdTextField;

public class BarrierPanel extends EAdPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Controller of the barrier.
     */
    private BarrierDataControl barrierDataControl;

    /**
     * Text area for the documentation.
     */
    private JTextArea documentationTextArea;

    /**
     * Panel in which the activeArea area is painted.
     */
    private ScenePreviewEditionPanel spep;

    private EAdTabbedPane tabPanel;

    private EAdPanel docPanel;

    private EAdTextField nameTextField;

    /**
     * Constructor.
     * 
     * @param barrierDataControl
     *            ActiveArea controller
     */
    public BarrierPanel( BarrierDataControl barrierDataControl ) {

        // Set the controller
        this.barrierDataControl = barrierDataControl;

        // Create the panels and layouts
        tabPanel = new EAdTabbedPane( );
        docPanel = new EAdPanel( );
        docPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints cDoc = new GridBagConstraints( );

        // Set the layout
        setLayout( new BorderLayout( ) );
        //setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Barrier.Title" ) ) );
        //GridBagConstraints c = new GridBagConstraints( );
        cDoc.insets = new Insets( 5, 5, 5, 5 );

        // Create the text area for the documentation
        cDoc.fill = GridBagConstraints.HORIZONTAL;
        cDoc.weightx = 1;
        cDoc.weighty = 0.3;
        EAdPanel documentationPanel = new EAdPanel( );
        documentationPanel.setLayout( new GridLayout( ) );
        documentationTextArea = new JTextArea( barrierDataControl.getDocumentation( ), 4, 0 );
        documentationTextArea.setLineWrap( true );
        documentationTextArea.setWrapStyleWord( true );
        documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) barrierDataControl.getContent( ) ) );
        documentationPanel.add( new EAdScrollPane( documentationTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) );
        documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Barrier.Documentation" ) ) );
        docPanel.add( documentationPanel, cDoc );

        // Create the field for the name
        cDoc.gridy = 1;
        cDoc.weighty = 0;
        EAdPanel namePanel = new EAdPanel( );
        namePanel.setLayout( new GridLayout( ) );
        nameTextField = new EAdTextField( this.barrierDataControl.getName( ) );
        nameTextField.getDocument( ).addDocumentListener( new NameChangeListener( nameTextField, (Named) barrierDataControl.getContent( ) ) );
        namePanel.add( nameTextField );
        namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Barrier.Name" ) ) );
        docPanel.add( namePanel, cDoc );

        //cDoc.gridy = 4;
        cDoc.gridy = 2;
        cDoc.fill = GridBagConstraints.BOTH;
        cDoc.weightx = 1;
        cDoc.weighty = 0.5;
        docPanel.add( new JFiller( ), cDoc );

        // Add the tabs
        //Finally, add lookPanel to its scrollPane container, and insert it as a tab along with docPanel

        tabPanel.insertTab( TC.get( "Barrier.MainPanelTitle" ), null, createMainPanel( ), TC.get( "Barrier.MainPanelTip" ), 0 );
        tabPanel.insertTab( TC.get( "Barrier.DocPanelTitle" ), null, docPanel, TC.get( "Barrier.DocPanelTip" ), 1 );
        setLayout( new BorderLayout( ) );
        add( tabPanel, BorderLayout.CENTER );
    }

    private EAdPanel createMainPanel( ) {

        EAdPanel mainPanel = new EAdPanel( );
        // Take the path of the background
        String scenePath = Controller.getInstance( ).getSceneImagePath( barrierDataControl.getParentSceneId( ) );
        spep = new ScenePreviewEditionPanel( false, scenePath );
        spep.setShowTextEdition( true );
        spep.setSelectedElement( barrierDataControl );
        spep.setFixedSelectedElement( true );

        // Set the layout of the principal panel
        mainPanel.setLayout( new GridBagLayout( ) );
        //setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Title" ) ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );

        // Create the button for the conditions
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        EAdPanel conditionsPanel = new EAdPanel( );
        conditionsPanel.setLayout( new GridLayout( ) );
        EAdButton conditionsButton = new EAdButton( TC.get( "GeneralText.EditConditions" ) );
        conditionsButton.addActionListener( new ConditionsButtonListener( ) );
        conditionsPanel.add( conditionsButton );
        conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Barrier.Conditions" ) ) );
        mainPanel.add( conditionsPanel, c );

        // Add the created rectangle position panel
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        mainPanel.add( spep, c );

        return mainPanel;
    }

    /**
     * Listener for the edit conditions button.
     */
    private class ConditionsButtonListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            new ConditionsDialog( barrierDataControl.getConditions( ) );
        }
    }
}
