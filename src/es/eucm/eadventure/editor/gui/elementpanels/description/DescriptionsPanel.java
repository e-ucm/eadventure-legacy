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

package es.eucm.eadventure.editor.gui.elementpanels.description;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.elements.Description;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DescriptionsController;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;


public class DescriptionsPanel extends JPanel implements Updateable {
    
   
    private static final long serialVersionUID = 1L;

    private static final int HORIZONTAL_SPLIT_POSITION = 70;
    
    protected DescriptionsController descriptionsController;
    
    protected GridBagConstraints cLook;
    
    protected DescriptionTable descriptionTable;
    
    protected JPanel descriptionsPanel;
    
    private DescriptionPanel descriptionPanel;
    
    protected JButton newDescriptionGroup; 
    

    protected JButton deleteDescriptionGroup;

    protected JButton duplicateDescriptionGroup;
    
    protected int selectedDescriptionGroup = 0;
    
    public DescriptionsPanel(DescriptionsController desController){
        super( );
        
        this.descriptionsController = desController;
        
        descriptionsPanel = new JPanel( );
        descriptionsPanel.setLayout( new GridBagLayout( ) );
        cLook = new GridBagConstraints( );

        // Create the combo resources blocks
        cLook.insets = new Insets( 5, 5, 5, 5 );
        cLook.fill = GridBagConstraints.HORIZONTAL;
        cLook.weightx = 1;
        cLook.gridy = 0;
        cLook.weighty = 0;
        cLook.anchor = GridBagConstraints.LINE_START;
        JPanel resourcesComboPanel = new JPanel( );
        resourcesComboPanel.setLayout( new GridLayout( ) );

        descriptionTable = new DescriptionTable( descriptionsController  );
        descriptionTable.setSelectedIndex( -1 );
        descriptionTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                if( descriptionTable.getSelectedRow( ) >= 0 ) {
                    updateDescriptions( );
                    duplicateDescriptionGroup.setEnabled( true );
                    if( descriptionsController.getNumberOfDescriptions( ) > 1 ) {
                        deleteDescriptionGroup.setEnabled( true );
                    }
                    else {
                        deleteDescriptionGroup.setEnabled( false );
                    }
                }
                else {
                    duplicateDescriptionGroup.setEnabled( false );
                }
            }
        } );

        JScrollPane scroll = new JScrollPane( descriptionTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        scroll.setMinimumSize( new Dimension( 0, HORIZONTAL_SPLIT_POSITION ) );

        //resourcesComboBox.addActionListener( new ResourcesComboBoxListener( ) );

        //Create the add button
        newDescriptionGroup = new JButton( new ImageIcon( "img/icons/addNode.png" ) );
        newDescriptionGroup.setContentAreaFilled( false );
        newDescriptionGroup.setMargin( new Insets( 0, 0, 0, 0 ) );
        newDescriptionGroup.setBorder( BorderFactory.createEmptyBorder( ) );
        newDescriptionGroup.setToolTipText( TC.get( "ResourcesList.AddResourcesBlock" ) );
        newDescriptionGroup.addActionListener( new NewButtonListener( ) );

        this.deleteDescriptionGroup = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
        deleteDescriptionGroup.setContentAreaFilled( false );
        deleteDescriptionGroup.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteDescriptionGroup.setBorder( BorderFactory.createEmptyBorder( ) );
        deleteDescriptionGroup.setToolTipText( TC.get( "ResourcesList.DeleteResourcesBlock" ) );
        deleteDescriptionGroup.setEnabled( false );
        deleteDescriptionGroup.addActionListener( new DeleteButtonListener( ) );

        this.duplicateDescriptionGroup = new JButton( new ImageIcon( "img/icons/duplicateNode.png" ) );
        duplicateDescriptionGroup.setContentAreaFilled( false );
        duplicateDescriptionGroup.setMargin( new Insets( 0, 0, 0, 0 ) );
        duplicateDescriptionGroup.setBorder( BorderFactory.createEmptyBorder( ) );
        duplicateDescriptionGroup.setToolTipText( TC.get( "ResourcesList.DuplicateResourcesBlock" ) );
        duplicateDescriptionGroup.setEnabled( false );
        duplicateDescriptionGroup.addActionListener( new DuplicateButtonListener( ) );

        JPanel blockControls = new JPanel( );

        JPanel buttonsPanel = new JPanel( );
        buttonsPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        buttonsPanel.add( newDescriptionGroup, c );
        c.gridy = 1;
        buttonsPanel.add( duplicateDescriptionGroup, c );
        c.gridy = 3;
        buttonsPanel.add( deleteDescriptionGroup, c );
        c.gridy = 2;
        c.weighty = 2.0;
        c.fill = GridBagConstraints.VERTICAL;
        buttonsPanel.add( new JFiller( ), c );

        blockControls.setLayout( new BorderLayout( ) );
        blockControls.add( scroll, BorderLayout.CENTER );
        blockControls.add( buttonsPanel, BorderLayout.EAST );

        //Create the descriptions Panel
        descriptionPanel = new DescriptionPanel( descriptionsController.getSelectedDescriptionController( ) );
        ///resourcesPanel.setPreviewUpdater( this );
        cLook.gridy = 0;
        cLook.gridx = 0;
        cLook.gridwidth = 2;
        cLook.fill = GridBagConstraints.BOTH;
        cLook.weightx = 1;
        cLook.weighty = 0.0;
        descriptionsPanel.add( descriptionPanel, cLook );

        // Create and set the preview panel
       // cLook.gridy = 1;
       // cLook.fill = GridBagConstraints.BOTH;
       // cLook.weighty = 1;
       // cLook.weightx = 1;
       // cLook.gridwidth = 2;
        //createPreview( );

        JSplitPane tableWithSplit = new JSplitPane( JSplitPane.VERTICAL_SPLIT, blockControls, descriptionsPanel );
        tableWithSplit.setOneTouchExpandable( true );
        tableWithSplit.setDividerLocation( HORIZONTAL_SPLIT_POSITION );
        tableWithSplit.setContinuousLayout( true );
        tableWithSplit.setResizeWeight( 0 );
        tableWithSplit.setDividerSize( 10 );

        if( descriptionTable.getRowCount( ) == 1 )
            tableWithSplit.setDividerLocation( 0 );

        setLayout( new BorderLayout( ) );
        add( tableWithSplit, BorderLayout.CENTER );

        descriptionTable.setSelectedIndex( descriptionsController.getSelectedDescriptionNumber( ));
    }
    
    public void updateDescriptions( ){
        descriptionsController.setSelectedDescription( descriptionTable.getSelectedIndex( ) );
        
        descriptionsPanel.remove( descriptionPanel );
        descriptionPanel = new DescriptionPanel( descriptionsController.getSelectedDescriptionController( ));
        //descriptionPanel.setPreviewUpdater( this );
        GridBagConstraints cLook = new GridBagConstraints( );
        cLook.gridy = 0;
        cLook.gridx = 0;
        cLook.gridwidth = 2;
        cLook.fill = GridBagConstraints.BOTH;
        cLook.weightx = 1;
        cLook.weighty = 0;
        descriptionsPanel.add( descriptionPanel, cLook );

        //repaint();
        //tabPanel.repaint( );
        //lookPanel.repaint( );
        descriptionPanel.repaint( );
        descriptionPanel.updateUI( );
    }
    
    
    
    public boolean updateFields( ) {

        descriptionPanel.updateFields();
        
        
        
        // TODO Auto-generated method stub
        return true;
    }
    
    
    private class DeleteButtonListener implements ActionListener {

        // XXX 8/5/2009: Revisado tool añadir recursos (en todos los elementos)
        public void actionPerformed( ActionEvent e ) {

            boolean canDelete=true;
            boolean clearConditions=false;
            //Check if after delete a resource block, there are only one block. In this case, delete its conditions due to
            //when there is only one block its conditions can be edited
            if (descriptionsController.getNumberOfDescriptions( )==2){
                // inform user about the conditions will be deleted
                canDelete =  Controller.getInstance( ).askDeleteConditionsDescriptionSet();
                clearConditions = true;
            }
            if (canDelete){
                if( descriptionTable.getSelectedIndex( ) >= 0 ) {
                    descriptionsController.setSelectedDescription( descriptionTable.getSelectedIndex( ) );
                    Description descriptionToDelete = descriptionsController.getSelectedDescription( );
                    if( descriptionsController.deleteElement( ) ) {
                        descriptionsController.setSelectedDescription( 0 );
                        descriptionTable.setSelectedIndex( 0 );
                        updateDescriptions( );
                        descriptionTable.updateUI( );
                        ( (AbstractTableModel) descriptionTable.getModel( ) ).fireTableDataChanged( );
                        descriptionTable.changeSelection( descriptionsController.getDescriptionCount() -1, 0, false, false );
                    }
                
                }
                if (clearConditions && !descriptionsController.getDescriptionController( 0 ).getConditionsController( ).isEmpty( )){
                    descriptionsController.getDescriptionController( 0 ).getConditionsController( ).clearConditions( );
                }
            }
        }
    }

    private class DuplicateButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            // XXX 8/5/2009: Revisado tool añadir recursos (en todos los elementos)
            if( descriptionTable.getSelectedIndex( ) >= 0 ) {
                descriptionsController.setSelectedDescription( descriptionTable.getSelectedIndex( ) );
                
                if( descriptionsController.duplicateElement(  ) ) {
                    descriptionsController.setSelectedDescription( descriptionsController.getDescriptionCount( ) - 1 );
                    descriptionTable.setSelectedIndex( descriptionsController.getDescriptionCount( ) - 1 );
                    updateDescriptions( );
                    ( (AbstractTableModel) descriptionTable.getModel( ) ).fireTableDataChanged( );
                    descriptionTable.changeSelection( descriptionsController.getDescriptionCount( ) - 1, 0, false, false );
                }
            }
        }
    }

    private class NewButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            // XXX 8/5/2009: Revisado tool añadir recursos (en todos los elementos)
            if( descriptionsController.addElement(  ) ) {
                descriptionsController.setSelectedDescription( descriptionsController.getDescriptionCount( ) - 1 );
                updateDescriptions( );
                ( (AbstractTableModel) descriptionTable.getModel( ) ).fireTableDataChanged( );
                descriptionTable.changeSelection( descriptionsController.getDescriptionCount( ) - 1, 0, false, false );
                
                
               
            }
        }
    }

}
