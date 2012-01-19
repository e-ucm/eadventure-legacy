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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMCreatePrimitiveContainerPanel;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESEducationalDataControl;

public class LOMESEducationalPanel extends JPanel {

    private LOMESEducationalDataControl dataControl;

    private JTextField hours;

    private JTextField minutes;

    public LOMESEducationalPanel( LOMESEducationalDataControl dataControl ) {

        this.dataControl = dataControl;

        //Layout
        setLayout( new GridBagLayout( ) );

        //Create Options panel
        JPanel optionsPanel = new JPanel( );
        GridLayout c = new GridLayout( 3, 1 );
        c.setVgap( 2 );
        optionsPanel.setLayout( c );

        JPanel firstRow = new JPanel( );
        GridLayout c1 = new GridLayout( 1, 2 );
        c1.setHgap( 2 );
        c1.setVgap( 2 );
        firstRow.setLayout( c1 );
        // INTENDED
        firstRow.add( new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.VOCABULARY_TYPE, dataControl.getIntendedEndUserRole( ), TC.get( "LOM.Educational.IntendedEndUserRole" ), dataControl.getIntendedEndUserRoleOptions( ) ) );
        //firstRow.add( new LOMESOptionsPanel(dataControl.getIntendedEndUserRoleController( ), TextConstants.getText("LOM.Educational.IntendedEndUserRole")) );
        //LEARNING RESOURCE
        firstRow.add( new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.VOCABULARY_TYPE, dataControl.getLearningResourceType( ), TC.get( "LOM.Educational.LearningResourceType" ), dataControl.getLearningResourceTypeOptions( ) ) );
        //firstRow.add( new LOMESOptionsPanel(dataControl.getLearningResourceTypeController( ), TextConstants.getText("LOM.Educational.LearningResourceType")) );

        JPanel secondRow = new JPanel( );
        GridLayout c2 = new GridLayout( 1, 2 );
        c2.setHgap( 2 );
        c2.setVgap( 2 );
        secondRow.setLayout( c2 );
        //CONTEXT
        secondRow.add( new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.VOCABULARY_TYPE, dataControl.getContext( ), TC.get( "LOM.Educational.Context" ), dataControl.getContextOptions( ) ) );
        //secondRow.add( new LOMESOptionsPanel(dataControl.getContextController( ), TextConstants.getText("LOM.Educational.Context")) );
        // COGNITIVE PROCESS
        secondRow.add( new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.VOCABULARY_TYPE, dataControl.getCognitiveProcess( ), TC.get( "LOMES.Educational.CognitiveProcess" ), dataControl.getCognitiveProcessOptions( ) ) );
        //secondRow.add(new LOMESOptionsPanel(dataControl.getCognitiveProcessController(), TextConstants.getText("LOMES.Educational.CognitiveProcess")) );

        JPanel thirdRow = new JPanel( );
        GridLayout c4 = new GridLayout( 1, 4 );
        c2.setHgap( 2 );
        c2.setVgap( 2 );
        thirdRow.setLayout( new GridBagLayout( ) );
        GridBagConstraints c6 = new GridBagConstraints( );
        c6.fill = GridBagConstraints.HORIZONTAL;
        c6.gridx = 0;
        c6.weightx = 1;
        //SEMANTIC
        thirdRow.add( new LOMESOptionsPanel( dataControl.getSemanticDensityController( ), TC.get( "LOM.Educational.SemanticDensity" ) ), c6 );
        c6.gridx++;
        //DIFFICULTY
        thirdRow.add( new LOMESOptionsPanel( dataControl.getDifficultyController( ), TC.get( "LOM.Educational.Difficulty" ) ), c6 );
        c6.gridx++;
        //INTERACTIVITY LEVEL
        thirdRow.add( new LOMESOptionsPanel( dataControl.getInteractivityLevelController( ), TC.get( "LOM.Educational.InteractivityLevel" ) ), c6 );
        c6.gridx++;
        //INTERACTIVITY TYPE
        thirdRow.add( new LOMESOptionsPanel( dataControl.getInteractivityTypeController( ), TC.get( "LOM.Educational.InteractivityType" ) ), c6 );

        optionsPanel.add( firstRow );
        optionsPanel.add( thirdRow );
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
        LOMCreatePrimitiveContainerPanel descriptionPanel = new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.LANGSTRING_TYPE, dataControl.getDescription( ), TC.get( "LOM.Educational.Description" ), LOMCreatePrimitiveContainerPanel.FIELD_TYPE_AREA );
        //LOMESTextPanel descriptionPanel = new LOMESTextPanel(dataControl.getDescriptionController( ), TextConstants.getText("LOM.Educational.Description"), LOMESTextPanel.TYPE_AREA);
        LOMCreatePrimitiveContainerPanel typicalAgeRangePanel = new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.LANGSTRING_TYPE, dataControl.getTypicalAgeRange( ), TC.get( "LOM.Educational.TypicalAgeRange" ), LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD );
        //LOMESTextPanel typicalAgeRangePanel = new LOMESTextPanel(dataControl.getTypicalAgeRangeController( ), TextConstants.getText("LOM.Educational.TypicalAgeRange"), LOMESTextPanel.TYPE_FIELD);

        JPanel container = new JPanel( );
        container.setLayout( new GridBagLayout( ) );
        GridBagConstraints c5 = new GridBagConstraints( );
        c5.gridy = 0;
        c5.fill = GridBagConstraints.HORIZONTAL;
        c5.weightx = 1.0;
        //Add the panels
        container.add( optionsPanel, c5 );
        c5.gridy = 1;
        container.add( descriptionPanel, c5 );
        c5.gridy = 2;
        container.add( typicalAgeRangePanel, c5 );
        c5.gridy = 3;
        container.add( typicalLearningTimePanel, c5 );

        c5.gridy = 0;
        c5.anchor = GridBagConstraints.NORTH;
        c5.weighty = 1.0;
        add( container, c5 );
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
