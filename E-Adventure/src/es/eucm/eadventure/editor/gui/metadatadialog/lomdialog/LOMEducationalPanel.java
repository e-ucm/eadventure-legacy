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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMEducationalDataControl;

public class LOMEducationalPanel extends JPanel {

    private LOMEducationalDataControl dataControl;

    private JTextField hours;

    private JTextField minutes;

    public LOMEducationalPanel( LOMEducationalDataControl dataControl ) {

        this.dataControl = dataControl;

        //Layout
        setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );

        //Create Options panel
        JPanel optionsPanel = new JPanel( );
        GridLayout c = new GridLayout( 2, 1 );
        c.setVgap( 2 );
        optionsPanel.setLayout( c );

        JPanel firstRow = new JPanel( );
        GridLayout c1 = new GridLayout( 1, 3 );
        c1.setHgap( 2 );
        c1.setVgap( 2 );
        firstRow.setLayout( c1 );
        LOMOptionsPanel intendedEnd = new LOMOptionsPanel( dataControl.getIntendedEndUserRoleController( ), TC.get( "LOM.Educational.IntendedEndUserRole" ) );
        firstRow.add( intendedEnd );
        LOMOptionsPanel semantic = new LOMOptionsPanel( dataControl.getSemanticDensityController( ), TC.get( "LOM.Educational.SemanticDensity" ) );
        firstRow.add( semantic );
        LOMOptionsPanel learningResources = new LOMOptionsPanel( dataControl.getLearningResourceTypeController( ), TC.get( "LOM.Educational.LearningResourceType" ) );
        firstRow.add( learningResources );

        JPanel secondRow = new JPanel( );
        GridLayout c2 = new GridLayout( 1, 4 );
        c2.setHgap( 2 );
        c2.setVgap( 2 );
        secondRow.setLayout( c2 );
        LOMOptionsPanel context = new LOMOptionsPanel( dataControl.getContextController( ), TC.get( "LOM.Educational.Context" ) );
        secondRow.add( context );
        LOMOptionsPanel difficulty = new LOMOptionsPanel( dataControl.getDifficultyController( ), TC.get( "LOM.Educational.Difficulty" ) );
        secondRow.add( difficulty );
        LOMOptionsPanel interactivityLevel = new LOMOptionsPanel( dataControl.getInteractivityLevelController( ), TC.get( "LOM.Educational.InteractivityLevel" ) );
        secondRow.add( interactivityLevel );
        LOMOptionsPanel interactivityType = new LOMOptionsPanel( dataControl.getInteractivityTypeController( ), TC.get( "LOM.Educational.InteractivityType" ) );
        secondRow.add( interactivityType );

        optionsPanel.add( firstRow );
        optionsPanel.add( secondRow );

        //Create the duration panel
        JPanel typicalLearningTimePanel = new JPanel( );
        GridLayout c3 = new GridLayout( 1, 4 );
        c3.setHgap( 2 );
        c3.setVgap( 2 );
        hours = new JTextField( 5 );
        hours.setText( dataControl.getTypicalLearningTime( ).getHours( ) );
        hours.addFocusListener( new TextFieldChangesListener( ) );
        minutes = new JTextField( 5 );
        minutes.setText( dataControl.getTypicalLearningTime( ).getMinutes( ) );
        minutes.addFocusListener( new TextFieldChangesListener( ) );
        JLabel hoursLabel = new JLabel( TC.get( "LOM.Duration.Hours" ) );
        JLabel minutesLabel = new JLabel( TC.get( "LOM.Duration.Minutes" ) );

        typicalLearningTimePanel.add( hoursLabel );
        typicalLearningTimePanel.add( hours );
        typicalLearningTimePanel.add( minutesLabel );
        typicalLearningTimePanel.add( minutes );
        typicalLearningTimePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOM.Educational.TypicalLearningTime" ) ) );

        //Create the other panels
        LOMTextPanel descriptionPanel = new LOMTextPanel( dataControl.getDescriptionController( ), TC.get( "LOM.Educational.Description" ), LOMTextPanel.TYPE_AREA );
        LOMTextPanel typicalAgeRangePanel = new LOMTextPanel( dataControl.getTypicalAgeRangeController( ), TC.get( "LOM.Educational.TypicalAgeRange" ), LOMTextPanel.TYPE_FIELD );

        // check if there are related stored data in config file
        /*if (LOMConfigData.isStored(LOMEducationalDataControl.GROUP, "intendedEndUserRole")){
        	intendedEnd.setSelectedOption(LOMConfigData.getProperty(LOMGeneralDataControl.GROUP, "intendedEndUserRole"));
        }
        if (LOMConfigData.isStored(LOMEducationalDataControl.GROUP, "semanticDensity")){
        	semantic.setSelectedOption(LOMConfigData.getProperty(LOMGeneralDataControl.GROUP, "semanticDensity"));
        }
        if (LOMConfigData.isStored(LOMEducationalDataControl.GROUP, "learningResourcesType")){
        	learningResources.setSelectedOption(LOMConfigData.getProperty(LOMGeneralDataControl.GROUP, "learningResourcesType"));
        }
        if (LOMConfigData.isStored(LOMEducationalDataControl.GROUP, "context")){
        	context.setSelectedOption(LOMConfigData.getProperty(LOMGeneralDataControl.GROUP, "context"));
        }
        if (LOMConfigData.isStored(LOMEducationalDataControl.GROUP, "difficulty")){
        	difficulty.setSelectedOption(LOMConfigData.getProperty(LOMGeneralDataControl.GROUP, "difficulty"));
        }
        if (LOMConfigData.isStored(LOMEducationalDataControl.GROUP, "interactivityLevel")){
        	interactivityLevel.setSelectedOption(LOMConfigData.getProperty(LOMGeneralDataControl.GROUP, "interactivityLevel"));
        }
        if (LOMConfigData.isStored(LOMEducationalDataControl.GROUP, "interactivityType")){
        	interactivityType.setSelectedOption(LOMConfigData.getProperty(LOMGeneralDataControl.GROUP, "interactivityType"));
        }
        
        if (LOMConfigData.isStored(LOMEducationalDataControl.GROUP, "description")){
        	descriptionPanel.setValue(LOMConfigData.getProperty(LOMGeneralDataControl.GROUP, "description"));
        }
        if (LOMConfigData.isStored(LOMEducationalDataControl.GROUP, "typicalAgeRange")){
        	typicalAgeRangePanel.setValue(LOMConfigData.getProperty(LOMGeneralDataControl.GROUP, "typicalAgeRange"));
        }
        if (LOMConfigData.isStored(LOMEducationalDataControl.GROUP, "typicalLearningTime")){
        	String data =LOMConfigData.getProperty(LOMGeneralDataControl.GROUP, "typicalAgeRange");
        	
        }*/

        //Add all panels
        add( optionsPanel );
        add( Box.createVerticalStrut( 2 ) );
        add( descriptionPanel );
        add( Box.createVerticalStrut( 2 ) );
        add( typicalAgeRangePanel );
        add( Box.createVerticalStrut( 2 ) );
        add( typicalLearningTimePanel );
        add( Box.createRigidArea( new Dimension( 400, 50 ) ) );
    }

    /**
     * Called when a text field has changed, so that we can set the new values.
     * 
     * @param source
     *            Source of the event
     */
    private void valueChanged( Object source ) {

        // Check the name field
        if( source == hours ) {
            if( !dataControl.getTypicalLearningTime( ).setHours( hours.getText( ) ) ) {
                hours.setText( "" );
            }
        }
        else if( source == minutes ) {
            if( !dataControl.getTypicalLearningTime( ).setMinutes( minutes.getText( ) ) ) {
                minutes.setText( "" );
            }
        }
    }

    /**
     * Listener for the text fields. It checks the values from the fields and
     * updates the data.
     */
    private class TextFieldChangesListener extends FocusAdapter implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
         */
        @Override
        public void focusLost( FocusEvent e ) {

            valueChanged( e.getSource( ) );
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            valueChanged( e.getSource( ) );
        }
    }

}
