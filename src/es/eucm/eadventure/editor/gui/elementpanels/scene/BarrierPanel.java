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
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;



/**
 * 
 *  THIS CLASS IS NOT INSTANCIATED !!!
 *
 */
public class BarrierPanel extends JPanel {

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

    private JTabbedPane tabPanel;

    private JPanel docPanel;

    private JTextField nameTextField;

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
        tabPanel = new JTabbedPane( );
        docPanel = new JPanel( );
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
        JPanel documentationPanel = new JPanel( );
        documentationPanel.setLayout( new GridLayout( ) );
        documentationTextArea = new JTextArea( barrierDataControl.getDocumentation( ), 4, 0 );
        documentationTextArea.setLineWrap( true );
        documentationTextArea.setWrapStyleWord( true );
        documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) barrierDataControl.getContent( ) ) );
        documentationPanel.add( new JScrollPane( documentationTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) );
        documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Barrier.Documentation" ) ) );
        docPanel.add( documentationPanel, cDoc );

        // Create the field for the name
        cDoc.gridy = 1;
        cDoc.weighty = 0;
        JPanel namePanel = new JPanel( );
        namePanel.setLayout( new GridLayout( ) );
        //barriers have no name
        // nameTextField = new JTextField( this.barrierDataControl.getName( ) );
       // nameTextField.getDocument( ).addDocumentListener( new NameChangeListener( nameTextField, (Named) barrierDataControl.getContent( ) ) );
      //  namePanel.add( nameTextField );
       // namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Barrier.Name" ) ) );
        //docPanel.add( namePanel, cDoc );

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

    private JPanel createMainPanel( ) {

        JPanel mainPanel = new JPanel( );
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
        JPanel conditionsPanel = new JPanel( );
        conditionsPanel.setLayout( new GridLayout( ) );
        JButton conditionsButton = new JButton( TC.get( "GeneralText.EditConditions" ) );
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
