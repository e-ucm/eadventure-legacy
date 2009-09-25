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
package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.tools.generic.ChangeStringValueTool;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.PlayerPositionDialog;

public class NextScenePanel extends JPanel implements Updateable {

    /**
     * 
     */
    private static final long serialVersionUID = 9164972283091760862L;

    private CutsceneDataControl dataControl;

    private JRadioButton returnToPrevious;

    private JRadioButton goToNewScene;

    private JRadioButton endChapter;

    private JComboBox nextSceneCombo;

    private JCheckBox usePosition;

    private JButton setPosition;

    private JButton editEffects;

    private JComboBox transition;

    private JSpinner timeSpinner;

    public NextScenePanel( CutsceneDataControl cutsceneDataControl ) {

        this.dataControl = cutsceneDataControl;

        setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Cutscene.CutsceneEndReached" ) ) );

        returnToPrevious = new JRadioButton( TC.get( "Cutscene.ReturnToLastScene" ) );
        goToNewScene = new JRadioButton( TC.get( "Cutscene.GoToNextScene" ) );
        endChapter = new JRadioButton( TC.get( "Cutscene.ChapterEnd" ) );
        returnToPrevious.addActionListener( new ReturnToPreviousActionListener( ) );
        goToNewScene.addActionListener( new GoToNewSceneActionListener( ) );
        endChapter.addActionListener( new EndChapterActionListener( ) );

        ButtonGroup group = new ButtonGroup( );
        group.add( returnToPrevious );
        group.add( goToNewScene );
        group.add( endChapter );

        JPanel buttonPanel = new JPanel( );
        buttonPanel.setLayout( new GridLayout( 1, 3 ) );
        buttonPanel.add( returnToPrevious );
        buttonPanel.add( goToNewScene );
        buttonPanel.add( endChapter );

        setLayout( new BorderLayout( ) );
        add( buttonPanel, BorderLayout.NORTH );

        JPanel detailsPanel = new JPanel( );

        nextSceneCombo = new JComboBox( Controller.getInstance( ).getIdentifierSummary( ).getGeneralSceneIds( ) );
        nextSceneCombo.addActionListener( new NextSceneComboBoxListener( ) );

        JPanel positionPanel = new JPanel( );
        positionPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints posC = new GridBagConstraints( );
        posC.gridx = 0;
        posC.gridy = 0;
        usePosition = new JCheckBox( TC.get( "NextScene.UseDestinyPosition" ) );
        usePosition.addActionListener( new DestinyPositionCheckBoxListener( ) );

        setPosition = new JButton( TC.get( "NextScene.EditDestinyPositionShort" ) );
        setPosition.addActionListener( new DestinyPositionButtonListener( ) );
        positionPanel.add( usePosition, posC );
        posC.gridx++;
        positionPanel.add( setPosition, posC );

        editEffects = new JButton( TC.get( "GeneralText.EditEffects" ) );
        editEffects.addActionListener( new EffectsButtonListener( ) );

        JPanel transitionPanel = new JPanel( );
        String[] options = new String[] { TC.get( "NextScene.NoTransition" ), TC.get( "NextScene.TopToBottom" ), TC.get( "NextScene.BottomToTop" ), TC.get( "NextScene.LeftToRight" ), TC.get( "NextScene.RightToLeft" ), TC.get( "NextScene.FadeIn" ) };
        transition = new JComboBox( options );
        transition.addActionListener( new TransitionComboChangeListener( ) );

        SpinnerModel sm = new SpinnerNumberModel( 0, 0, 5000, 100 );
        timeSpinner = new JSpinner( sm );
        timeSpinner.addChangeListener( new TransitionSpinnerChangeListener( ) );
        transitionPanel.add( new JLabel( TC.get( "NextScene.Transition" ) ) );
        transitionPanel.add( transition );
        transitionPanel.add( new JLabel( TC.get( "NextScene.TransitionTime" ) ) );
        transitionPanel.add( timeSpinner );
        transitionPanel.add( new JLabel( "seg" ) );

        detailsPanel.setLayout( new GridLayout( 0, 1 ) );
        JPanel temp = new JPanel( );
        temp.add( new JLabel( TC.get( "NextScene.NextSceneId" ) ) );
        temp.add( nextSceneCombo );
        temp.add( editEffects );
        detailsPanel.add( temp );
        if( !Controller.getInstance( ).isPlayTransparent( ) )
            detailsPanel.add( positionPanel );
        detailsPanel.add( transitionPanel );

        add( detailsPanel, BorderLayout.CENTER );

        updateNextSceneInfo( );

    }

    private void updateNextSceneInfo( ) {

        boolean enablePosition = false;
        boolean enablePositionButton = false;

        if( dataControl.getNext( ) == Cutscene.ENDCHAPTER ) {
            endChapter.setSelected( true );
        }
        else if( dataControl.getNext( ) == Cutscene.GOBACK ) {
            returnToPrevious.setSelected( true );
        }
        else {
            goToNewScene.setSelected( true );
            nextSceneCombo.setSelectedItem( dataControl.getNextSceneId( ) );
            usePosition.setSelected( dataControl.hasDestinyPosition( ) );
            transition.setSelectedIndex( dataControl.getTransitionType( ) );
            timeSpinner.setValue( dataControl.getTransitionTime( ) );
            enablePosition = Controller.getInstance( ).getIdentifierSummary( ).isScene( dataControl.getNextSceneId( ) );
            enablePositionButton = enablePosition && dataControl.hasDestinyPosition( );
        }
        nextSceneCombo.setEnabled( goToNewScene.isSelected( ) );
        usePosition.setEnabled( enablePosition && goToNewScene.isSelected( ) );
        setPosition.setEnabled( enablePositionButton && goToNewScene.isSelected( ) );
        editEffects.setEnabled( goToNewScene.isSelected( ) );
        transition.setEnabled( goToNewScene.isSelected( ) );
        timeSpinner.setEnabled( goToNewScene.isSelected( ) );
        updateUI( );
    }

    /**
     * Listener for next scene combo box.
     */
    private class NextSceneComboBoxListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            Controller.getInstance( ).addTool( new ChangeStringValueTool( dataControl, nextSceneCombo.getSelectedItem( ).toString( ), "getNextSceneId", "setNextSceneId" ) );
            //			dataControl.setNextSceneId( nextSceneCombo.getSelectedItem( ).toString( ) );
            updateNextSceneInfo( );
        }
    }

    /**
     * Listener for the "Use destiny position in this next scene" check box.
     */
    private class DestinyPositionCheckBoxListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            dataControl.toggleDestinyPosition( );
            updateNextSceneInfo( );
        }
    }

    /**
     * Listener for the "Set destiny player position" button
     */
    private class DestinyPositionButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent arg0 ) {

            PlayerPositionDialog destinyPositionDialog = new PlayerPositionDialog( nextSceneCombo.getSelectedItem( ).toString( ), dataControl.getDestinyPositionX( ), dataControl.getDestinyPositionY( ) );
            dataControl.setDestinyPosition( destinyPositionDialog.getPositionX( ), destinyPositionDialog.getPositionY( ) );
        }
    }

    /**
     * Listener for the edit effects button.
     */
    private class EffectsButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            new EffectsDialog( dataControl.getEffects( ) );
        }
    }

    private class TransitionComboChangeListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            dataControl.setTransitionType( transition.getSelectedIndex( ) );
        }
    }

    private class TransitionSpinnerChangeListener implements ChangeListener {

        public void stateChanged( ChangeEvent e ) {

            if( ( (Integer) timeSpinner.getValue( ) ).intValue( ) == 900 )
                timeSpinner.setValue( new Integer( 0 ) );
            else if( ( (Integer) timeSpinner.getValue( ) ).intValue( ) == 100 )
                timeSpinner.setValue( new Integer( 1000 ) );

            dataControl.setTransitionTime( (Integer) timeSpinner.getValue( ) );
        }
    }

    private class ReturnToPreviousActionListener implements ActionListener {

        public void actionPerformed( ActionEvent arg0 ) {

            if( returnToPrevious.isSelected( ) ) {
                dataControl.setNext( Cutscene.GOBACK );
                updateNextSceneInfo( );
            }
        }
    }

    private class GoToNewSceneActionListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            if( goToNewScene.isSelected( ) ) {
                dataControl.setNext( Cutscene.NEWSCENE );
                updateNextSceneInfo( );
            }
        }
    }

    private class EndChapterActionListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            if( endChapter.isSelected( ) ) {
                dataControl.setNext( Cutscene.ENDCHAPTER );
                updateNextSceneInfo( );
            }
        }

    }

    public boolean updateFields( ) {

        updateNextSceneInfo( );
        return true;
    }
}
