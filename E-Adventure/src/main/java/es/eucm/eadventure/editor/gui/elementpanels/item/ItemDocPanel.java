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
package es.eucm.eadventure.editor.gui.elementpanels.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.chapter.elements.Item.BehaviourType;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.elementpanels.description.DescriptionsPanel;

public class ItemDocPanel extends JPanel implements Updateable {

    private static final long serialVersionUID = 4556146180537102976L;
    
    private static final int BEHAVIOUR_INDEX_NORMAL=0;
    private static final int BEHAVIOUR_INDEX_FIRSTACTION=1;
    /**
     * Text area for the documentation.
     */
    private JTextArea documentationTextArea;

    private JCheckBox returnsWhenDraggedCheckBox;
    private DescriptionsPanel descriptionsPanel;

    private ItemDataControl itemDataControl;
    
    // v1.4
    private JComboBox behaviourCombo;
    private JSpinner rTTimeSpinner;
    private JTextArea selectionDescription;
    ////////
    
    public ItemDocPanel( ItemDataControl itemDataControl ) {

        this.itemDataControl = itemDataControl;
       
        
        setLayout( new GridBagLayout( ) );
        GridBagConstraints cDoc = new GridBagConstraints( );

        cDoc.insets = new Insets( 5, 5, 5, 5 );

        cDoc.fill = GridBagConstraints.BOTH;
        cDoc.weightx = 1;
        cDoc.weighty = 0.3;
        JPanel documentationPanel = new JPanel( );
        documentationPanel.setLayout( new GridLayout( ) );
        documentationTextArea = new JTextArea( itemDataControl.getDocumentation( ), 4, 0 );
        documentationTextArea.setLineWrap( true );
        documentationTextArea.setWrapStyleWord( true );
        documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) itemDataControl.getContent( ) ) );
        documentationPanel.add( new JScrollPane( documentationTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) );
        documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Item.Documentation" ) ) );
        add( documentationPanel, cDoc );

        cDoc.gridy = 1;
        descriptionsPanel = new DescriptionsPanel(itemDataControl.getDescriptionController( ));
        add( descriptionsPanel, cDoc );

        cDoc.gridy = 2;
        JPanel returnsWhenDraggedPanel = new JPanel();
        JTextArea rwdDescription = new JTextArea(TC.get( "Item.ReturnsWhenDragged.Description" ), 4, 100);
        rwdDescription.setLineWrap( true );
        rwdDescription.setWrapStyleWord( true );
        rwdDescription.setEditable( false );
        rwdDescription.setOpaque( false );
        rwdDescription.setBorder( BorderFactory.createEtchedBorder( ) );
        returnsWhenDraggedPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( new Color(130,130,130), 1 ), TC.get( "Item.ReturnsWhenDragged.Title" ) ) );
        returnsWhenDraggedPanel.setLayout( new BorderLayout() );
        returnsWhenDraggedPanel.add( rwdDescription, BorderLayout.CENTER);
        returnsWhenDraggedCheckBox = new JCheckBox(TC.get( "Item.ReturnsWhenDragged" ));
        returnsWhenDraggedCheckBox.setSelected( itemDataControl.isReturnsWhenDragged( ) );
        returnsWhenDraggedCheckBox.addActionListener( new ActionListener( ) {
            public void actionPerformed( ActionEvent e ) {
                Controller.getInstance( ).addTool( new ChangeBooleanValueTool( ItemDocPanel.this.itemDataControl, ( (JCheckBox) e.getSource( ) ).isSelected( ), "isReturnsWhenDragged", "setReturnsWhenDragged" ) );
            }
        } );
        returnsWhenDraggedPanel.add( returnsWhenDraggedCheckBox, BorderLayout.SOUTH);
        add(returnsWhenDraggedPanel, cDoc);
        
     // v1.4
        cDoc.gridy = 3;
        JPanel behaviourPanel = new JPanel();
        behaviourPanel.setLayout( new GridBagLayout() );
        GridBagConstraints gbcB = new GridBagConstraints();
        gbcB.fill = GridBagConstraints.BOTH;
        gbcB.gridwidth = 2;
        gbcB.gridheight = 1;
        gbcB.weightx=2;
        gbcB.weighty=2;
        gbcB.gridx = 0;
        gbcB.gridy = 0;
        JTextArea behaviourDescription = new JTextArea(TC.get( "Behaviour.Description" ), 100, 5);
        behaviourDescription.setLineWrap( true );
        behaviourDescription.setWrapStyleWord( true );
        behaviourDescription.setEditable( false );
        behaviourDescription.setOpaque( false );
        behaviourDescription.setBorder( BorderFactory.createEtchedBorder( ) );
        behaviourPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( new Color(130,130,130), 1 ),TC.get( "Behaviour" ) ) );
        behaviourPanel.add( behaviourDescription, gbcB);
        if (BEHAVIOUR_INDEX_NORMAL<BEHAVIOUR_INDEX_FIRSTACTION){
            behaviourCombo = new JComboBox(new String[]{
                    TC.get( "Behaviour.Normal" ), TC.get( "Behaviour.FirstAction" )
            });
        } else {
            behaviourCombo = new JComboBox(new String[]{
                    TC.get( "Behaviour.FirstAction" ), TC.get( "Behaviour.Normal" )
            });
        }
        if (itemDataControl.getBehaviour( )==BehaviourType.NORMAL)
            behaviourCombo.setSelectedIndex( BEHAVIOUR_INDEX_NORMAL );
        //else if (itemDataControl.getBehaviour( )==BehaviourType.ATREZZO)
        //    behaviourCombo.setSelectedItem( BEHAVIOUR_ATREZZO_STRING );
        else if (itemDataControl.getBehaviour( )==BehaviourType.FIRST_ACTION)
            behaviourCombo.setSelectedIndex( BEHAVIOUR_INDEX_FIRSTACTION );
        behaviourCombo.addActionListener( new ActionListener(){

            public void actionPerformed( ActionEvent e ) {
                updateBehaviourValue();
            }
            
        });
        gbcB.fill = GridBagConstraints.BOTH;
        gbcB.gridwidth = 1;
        gbcB.gridheight = 1;
        gbcB.weightx=0.5;
        gbcB.weighty=1;
        gbcB.gridx = 0;
        gbcB.gridy = 1;
        behaviourPanel.add( behaviourCombo, gbcB );
        
        selectionDescription = new JTextArea( itemDataControl.getBehaviour( )==BehaviourType.FIRST_ACTION?TC.get( "Behaviour.Selection.FirstAction" ):TC.get( "Behaviour.Selection.Normal" ), 70, 3);
        selectionDescription.setLineWrap( true );
        selectionDescription.setWrapStyleWord( true );
        selectionDescription.setEditable( false );
        selectionDescription.setOpaque( false );
        selectionDescription.setBorder( BorderFactory.createEtchedBorder( ) );
        gbcB.fill = GridBagConstraints.BOTH;
        gbcB.gridwidth = 1;
        gbcB.gridheight = 1;
        gbcB.weightx=1.5;
        gbcB.gridx = 1;
        gbcB.gridy = 1;
        behaviourPanel.add( selectionDescription, gbcB);
        
        add (behaviourPanel, cDoc);
        
        
        rTTimeSpinner = new JSpinner(new SpinnerNumberModel(itemDataControl.getResourcesTransitionTime( ),0,100000,5));
        rTTimeSpinner.addChangeListener( new ChangeListener(){

            public void stateChanged( ChangeEvent e ) {
                ItemDocPanel.this.itemDataControl.setResourcesTransitionTime( ((Double)rTTimeSpinner.getValue()).longValue( ) );
            }
            
        });
        JPanel rTTimeSpinnerContaniner = new JPanel();
        JPanel rTTimePanel = new JPanel();
        rTTimePanel.setLayout( new BorderLayout() );
        rTTimePanel.add( rTTimeSpinnerContaniner, BorderLayout.SOUTH );
        
        JTextArea tTimerDescription = new JTextArea(TC.get( "Resources.TransitionTime.Description" ), 100, 5);
        tTimerDescription.setLineWrap( true );
        tTimerDescription.setWrapStyleWord( true );
        tTimerDescription.setEditable( false );
        tTimerDescription.setOpaque( false );
        tTimerDescription.setBorder( BorderFactory.createEtchedBorder( ) );
        rTTimePanel.add( tTimerDescription, BorderLayout.CENTER );
        
        rTTimePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( new Color(130,130,130), 1 ), TC.get( "Resources.TransitionTime" ) ) );
        rTTimeSpinnerContaniner.add( rTTimeSpinner );
        cDoc.gridy = 4;
        add(rTTimePanel, cDoc);
        // END V1.4
        
        cDoc.gridy = 5;
        cDoc.fill = GridBagConstraints.BOTH;
        cDoc.weightx = 1;
        cDoc.weighty = 0.5;
        add( new JFiller( ), cDoc );
    }
    
    // v1.4
    private void updateBehaviourValue(){
        int selectedItem = behaviourCombo.getSelectedIndex( );
        if (selectedItem==BEHAVIOUR_INDEX_NORMAL)
            itemDataControl.setBehaviour( BehaviourType.NORMAL );
        //else if (selectedItem.equals( BEHAVIOUR_ATREZZO_STRING ))
        //    itemDataControl.setBehaviour( BehaviourType.ATREZZO );
        else if (selectedItem==BEHAVIOUR_INDEX_FIRSTACTION)
            itemDataControl.setBehaviour( BehaviourType.FIRST_ACTION );
        
        selectionDescription.setText( itemDataControl.getBehaviour( )==BehaviourType.FIRST_ACTION?TC.get( "Behaviour.Selection.FirstAction" ):TC.get( "Behaviour.Selection.Normal" ));
    }
    // v1.4

    public boolean updateFields( ) {
        if (itemDataControl==null)return false;

        if (documentationTextArea!=null){
            documentationTextArea.setText( itemDataControl.getDocumentation( ) );
        }
        if (returnsWhenDraggedCheckBox!=null){
            returnsWhenDraggedCheckBox.setSelected( itemDataControl.isReturnsWhenDragged( ) );
        }
        if (behaviourCombo!=null){
            if (itemDataControl.getBehaviour( )==BehaviourType.NORMAL)
                behaviourCombo.setSelectedIndex( BEHAVIOUR_INDEX_NORMAL );
            //else if (itemDataControl.getBehaviour( )==BehaviourType.ATREZZO)
            //    behaviourCombo.setSelectedItem( BEHAVIOUR_ATREZZO_STRING );
            else if (itemDataControl.getBehaviour( )==BehaviourType.FIRST_ACTION)
                behaviourCombo.setSelectedIndex( BEHAVIOUR_INDEX_FIRSTACTION );
        }
        if (selectionDescription!=null){
            selectionDescription.setText( itemDataControl.getBehaviour( )==BehaviourType.FIRST_ACTION?TC.get( "Behaviour.Selection.FirstAction" ):TC.get( "Behaviour.Selection.Normal" ));
        }
        if (rTTimeSpinner!=null){
            rTTimeSpinner.setModel( new SpinnerNumberModel(itemDataControl.getResourcesTransitionTime( ),0,100000,5) );
            //rTTimeSpinner.setValue( (itemDataControl.getResourcesTransitionTime( )) );
        }
        if (descriptionsPanel!=null){
            descriptionsPanel.updateFields( );
        }
        return true;
    }

}
