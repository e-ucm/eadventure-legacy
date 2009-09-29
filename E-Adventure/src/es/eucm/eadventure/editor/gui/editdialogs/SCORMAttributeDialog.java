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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.config.SCORMConfigData;

/**
 * Class to manage the arrays attributes in SCORM data model
 */
public class SCORMAttributeDialog extends JDialog{

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constants to manage the index of the spinner.
     */
    private static final int INIT_INDEX = 0;
    
    private static final int FINAL_INDEX = 50;
    
    /**
     * Constant for especial panel configuration.
     */
    private static final String mIndex = "cmi.interactions";
   
    /**
     * The correct cmi value 
     */
    private String attribute;

    /**
     * The SCORM type (v1.2 or 2004)
     */
    private int type;
    
    /**
     * Combo box with the fields of the attribute.
     */
    private JComboBox fieldsCombo;

    /**
     * The index n in the data model array
     */
    private JSpinner indexNSpinner;
    
    /**
     * 
     */
    private JSpinner indexMSpinner;
    
    /**
     * 
     */
    private int readWrite;

    /**
     *
     * Constructor
     * 
     * @param type
     * 		SCORM type (v1.2 or 2004).
     * @param attribute
     * 		The name of the attribute.
     * @param lastIndex
     * 		Last index in the array.
     * @param readWrite
     * 		Shows the read/write field of the attribute.
     * 
     */
    public SCORMAttributeDialog( int type, String attribute, int readWrite ) {

	// Call the super method
        super( Controller.getInstance( ).peekWindow( ), "", Dialog.ModalityType.TOOLKIT_MODAL );

        

        this.readWrite = readWrite;
        this.type = type;
        this.attribute = attribute;
        
        // Change the Dialog title
        this.setTitle( generateTitle() );
        
        // create the Ok and Cancel buttons
        prepareButtons( );
        
        // Create the main panel
        JPanel mainPanel = new JPanel( );
        mainPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

       
        
        c.insets = new Insets( 2, 4, 4, 4 );
        c.weightx = 0.5;
        c.gridx=0;
        c.gridy=0;
        
        String indexN = null;
        String indexM = null;
        String comboSelect = null;
        // Check if attribute has an array value
        if (SCORMConfigData.isArrayAttributeExtension(attribute)){
            String aux = attribute.substring(attribute.indexOf("cmi."));
            aux = aux.substring(attribute.indexOf(".")+1);
            aux = aux.substring(aux.indexOf(".")+1);
            String aux2= attribute.substring(0,attribute.indexOf(aux)-1);
            indexN = aux.substring(0,1);
            
            comboSelect = attribute.replaceFirst("[.]"+indexN+"[.]", ".n.");
            aux = aux.substring(aux.indexOf(".")+1);
            if (aux.matches( ".*[.]\\d[.].*" ) ){
        	aux = aux.substring(aux.indexOf(".")+1);
        	indexM = aux.substring(0,1);
        	comboSelect = comboSelect.replaceFirst("[.]"+indexM+"[.]", ".m.");
            }
            	attribute = aux2;
        }
        
        // Get the attribute`s fields list
        ArrayList<String> fields = SCORMConfigData.getAttribute(attribute, type, readWrite );
        JPanel comboContainer = new JPanel();
        fieldsCombo = new JComboBox( fields.toArray() );
        fieldsCombo.setEditable( false );
        if (comboSelect!=null)
            fieldsCombo.setSelectedItem(comboSelect);
        
        // Set the border of the fieldCombo with the description
        comboContainer.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "SCORMDialog.Field" ) ) ) );
        comboContainer.add(fieldsCombo);

        // Create the spinner for the "n" index increment/decrement
        JPanel spinnerContainer = new JPanel();
        indexNSpinner = new JSpinner( new SpinnerNumberModel(INIT_INDEX , INIT_INDEX, FINAL_INDEX, 1 ) );
        spinnerContainer .setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "SCORMDialog.IndexN" ) ) ) );
        spinnerContainer.add(indexNSpinner);
        if (indexN!=null)
            indexNSpinner.setValue(Integer.parseInt(indexN));
        
        mainPanel.add( comboContainer, c );
        c.gridx=1;
        //c.gridy=1;
        mainPanel.add( spinnerContainer, c );
        this.setSize(400, 200);
        
        if (attribute.startsWith(mIndex)){
            // Create the spinner for the "m" index increment/decrement
            JPanel spinnerContainer2 = new JPanel();
            indexMSpinner = new JSpinner( new SpinnerNumberModel(INIT_INDEX , INIT_INDEX, FINAL_INDEX, 1 ) );
            spinnerContainer2.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "SCORMDialog.IndexM" ) ) ) );
            spinnerContainer2.add(indexMSpinner);
            indexMSpinner.setEnabled(false);
            fieldsCombo.addActionListener(new ActionListener(){

    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    		
    		if (SCORMConfigData.isArrayInsideArray((String)fieldsCombo.getSelectedItem()))
    		    indexMSpinner.setEnabled( true );
    		else 
    		    indexMSpinner.setEnabled( false );
    	    }
                
            });
            
            if (indexM!=null){
        	indexMSpinner.setValue(Integer.parseInt(indexM));
        	indexMSpinner.setEnabled(true);
            }
            c.gridx=2;
            this.setSize(500, 200);
            mainPanel.add( spinnerContainer2, c );
            
            }
            
        
        // Add the panel to the center
        add( mainPanel, BorderLayout.CENTER );

        // Set the dialog
        //pack( );
        setResizable( false );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
        
    }
    
    /**
     * Generate the title for this dialog.
     * 
     * @return
     * 		The Dialog`s title.
     */
    private String generateTitle(){
	
	String[] parameters = new String[2];
	if ( type == SCORMConfigData.SCORM_V12 )
	    parameters[0] = "v1.2";
	else if ( type == SCORMConfigData.SCORM_2004 )
	    parameters[0] = "2004";
	parameters[1] = attribute;
	return TC.get("SCORMDialog.Title", parameters);
	
    }
    
    
    private void prepareButtons(){
	// Set the controller and the properties  """""""puede que sobre!!!!!!!!!
       // controller = Controller.getInstance( );

        // Create the panel for the buttons
        JPanel buttonsPanel = new JPanel( );
        buttonsPanel.setLayout( new FlowLayout( FlowLayout.RIGHT, 4, 4 ) );

        // Create and add the OK button
        JButton okButton = new JButton( TC.get( "GeneralText.OK" ) );
        okButton.addActionListener( new OKButtonActionListener( ) );
        buttonsPanel.add( okButton );

        // Create and add the Cancel button
        JButton cancelButton = new JButton( TC.get( "GeneralText.Cancel" ) );
        cancelButton.addActionListener( new CancelButtonActionListener( ) );
        buttonsPanel.add( cancelButton );

        // Add and empty border
        buttonsPanel.setBorder( BorderFactory.createEmptyBorder( 8, 4, 4, 4 ) );

        // Add the panel to the south
        add( buttonsPanel, BorderLayout.SOUTH );

    }

    protected void pressedOKButton( ) {

	// get the selected field
	String result = (String)fieldsCombo.getSelectedItem();
	// get the selected index. Add "." at the beginning and at the end, because it is going
	// to be changed for ".n."
	String index = "."+String.valueOf(((Integer)indexNSpinner.getValue()))+".";
	attribute = result.replaceFirst("[.]n[.]", index);
	if (indexMSpinner!=null && indexMSpinner.isEnabled()){
	 // get the selected index. Add "." at the beginning and at the end, because it is going
	// to be changed for ".m."
	index = "."+String.valueOf(((Integer)indexMSpinner.getValue()))+".";
	attribute = attribute.replaceFirst("[.]m[.]", index);
	}
	    

    }
    
    /**
     * Listener for the OK button.
     */
    private class OKButtonActionListener implements ActionListener {

        public void actionPerformed( ActionEvent arg0 ) {

            pressedOKButton( );
            setVisible( false );
            SCORMAttributeDialog.this.dispose();

        }
    }
    
    /**
     * Listener for the Cancel button.
     */
    private class CancelButtonActionListener implements ActionListener {

        public void actionPerformed( ActionEvent arg0 ) {

            setVisible( false );
            SCORMAttributeDialog.this.dispose();

        }
    }
    
    public static String showAttributeDialogForRead( int type, String attribute ){
	
	SCORMAttributeDialog att = new SCORMAttributeDialog( type, attribute, SCORMConfigData.READ );
	return att.attribute;
    }
    
    public static String showAttributeDialogForWrite( int type, String attribute ){
	
	SCORMAttributeDialog att = new SCORMAttributeDialog( type, attribute, SCORMConfigData.WRITE );
	return att.attribute;
    }


}

