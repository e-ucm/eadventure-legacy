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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;

public class ExportToLOMDialog extends JDialog {

    private boolean validated;

    private String lomName;

    private String authorName;

    private String organizationName;

    private JTextField lomNameTextField;

    private JTextField authorNameTextField;

    private JTextField organizationTextField;

    private JCheckBox windowedCheckBox;

    private JComboBox typeComboBox;

    //4 field attributes FOR GAMETEL
    private String testURI;
    private JTextField testURITextField;
    
    private String testUserID;
    private JTextField testUserIDTextField;
    private JPanel gametelContainerPanel;
    private JPanel gametelPanel;
    
    private boolean windowed = false;

    public ExportToLOMDialog( String defaultLomName ) {

        // Set the values
        super( Controller.getInstance( ).peekWindow( ), TC.get( "Operation.ExportToLOM.Title" ), Dialog.ModalityType.APPLICATION_MODAL );

        this.lomName = defaultLomName;

        this.getContentPane( ).setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;

        JPanel typePanel = new JPanel( );
        String[] options = { "IMS CP", "WebCT 4 CP", "SCORM", "SCORM2004", "AGREGA", "LAMS"/*, "GAME·TEL-PC"*/};
        typeComboBox = new JComboBox( options );
        typeComboBox.addItemListener( new ItemListener(){

            public void itemStateChanged( ItemEvent e ) {
                System.out.println( "TYPE SELECTED"+typeComboBox.getSelectedIndex( ) );
                if (typeComboBox.getSelectedIndex( )==6){
                    if (!gametelContainerPanel.isAncestorOf( gametelPanel )){
                        gametelContainerPanel.add( gametelPanel );
                        ExportToLOMDialog.this.setSize( new Dimension( 400, 670 ) );
                    }
                } else {
                    if (gametelContainerPanel.isAncestorOf( gametelPanel )){
                        gametelContainerPanel.remove( gametelPanel );
                        ExportToLOMDialog.this.setSize( new Dimension( 400, 600 ) );
                    }
                }
            }
            
        });
        typePanel.add( typeComboBox );
        typePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Operation.ExportToLOM.LOMType" ) ) );

        //LOM NAME PANEL
        JPanel lomNamePanel = new JPanel( );
        lomNamePanel.setLayout( new GridBagLayout( ) );
        lomNamePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Operation.ExportToLOM.LOMName" ) ) );
        lomNameTextField = new JTextField( defaultLomName );
        lomNameTextField.getDocument( ).addDocumentListener( new TextFieldListener( lomNameTextField ) );
        JTextPane lomNameDescription = new JTextPane( );
        lomNameDescription.setEditable( false );
        lomNameDescription.setBackground( getContentPane( ).getBackground( ) );
        lomNameDescription.setText( TC.get( "Operation.ExportToLOM.LOMName.Description" ) );
        GridBagConstraints c2 = new GridBagConstraints( );
        c2.insets = new Insets( 5, 5, 5, 5 );
        c.gridy = 0;
        c2.gridy = 0;
        c2.fill = GridBagConstraints.BOTH;
        c2.weightx = 1;
        lomNamePanel.add( lomNameDescription, c2 );
        c2.gridy = 1;
        lomNamePanel.add( lomNameTextField, c2 );

        //Credentials panel
        JPanel credentialsPanel = new JPanel( );
        credentialsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Operation.ExportToLOM.Credentials" ) ) );
        credentialsPanel.setLayout( new GridBagLayout( ) );
        c2 = new GridBagConstraints( );
        c2.insets = new Insets( 5, 5, 5, 5 );
        c2.fill = GridBagConstraints.BOTH;
        c2.weightx = 1;
        c2.gridy = 0;
        JTextPane credentialsDescription = new JTextPane( );
        credentialsDescription.setEditable( false );
        credentialsDescription.setBackground( getContentPane( ).getBackground( ) );
        credentialsDescription.setText( TC.get( "Operation.ExportToLOM.Credentials.Description" ) );
        credentialsPanel.add( credentialsDescription, c2 );

        //Author name
        JPanel authorNamePanel = new JPanel( );
        authorNamePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Operation.ExportToLOM.AuthorName" ) ) );
        authorNamePanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c3 = new GridBagConstraints( );
        c3.insets = new Insets( 2, 2, 2, 2 );
        c3.weightx = 1;
        c3.fill = GridBagConstraints.BOTH;
        authorNameTextField = new JTextField( "" );
        authorNameTextField.getDocument( ).addDocumentListener( new TextFieldListener( authorNameTextField ) );
        authorNamePanel.add( authorNameTextField, c3 );
        c2.gridy = 1;
        credentialsPanel.add( authorNamePanel, c2 );

        //Organization name
        JPanel organizationNamePanel = new JPanel( );
        organizationNamePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Operation.ExportToLOM.OrganizationName" ) ) );
        organizationNamePanel.setLayout( new GridBagLayout( ) );
        c3 = new GridBagConstraints( );
        c3.insets = new Insets( 2, 2, 2, 2 );
        c3.weightx = 1;
        c3.fill = GridBagConstraints.BOTH;
        organizationTextField = new JTextField( "" );
        organizationTextField.getDocument( ).addDocumentListener( new TextFieldListener( organizationTextField ) );
        organizationNamePanel.add( organizationTextField, c3 );
        c2.gridy = 2;
        credentialsPanel.add( organizationNamePanel, c2 );

        //Applet properties panel
        JPanel lomAppletPanel = new JPanel( );
        lomAppletPanel.setLayout( new GridBagLayout( ) );
        lomAppletPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Operation.ExportToLOM.LOMAppletProperties" ) ) );
        //lomNameTextField = new JTextField(defaultLomName);
        windowedCheckBox = new JCheckBox( TC.get( "Operation.ExportToLOM.LOMAppletRunInsideBrowser" ) );
        windowedCheckBox.setSelected( true );
        windowedCheckBox.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent arg0 ) {

                changeWindowedMode( );
            }
        } );
        c2 = new GridBagConstraints( );
        c2.insets = new Insets( 5, 5, 5, 5 );
        c.gridy = 0;
        c2.gridy = 0;
        c2.fill = GridBagConstraints.BOTH;
        c2.weightx = 1;
        lomAppletPanel.add( windowedCheckBox, c2 );

        // FOR GAMETEL: Test fields
        gametelContainerPanel = new JPanel();
        gametelPanel = new JPanel();
        gametelPanel.setLayout( new GridLayout(2,2) );
        gametelPanel.add( new JLabel( TC.get( "Operation.ExportToLOM.GAMETEL.TestURI" ) ) );
        testURITextField = new JTextField();
        testURITextField.getDocument( ).addDocumentListener( new DocumentListener(){

            public void changedUpdate( DocumentEvent e ) {
                testURI = testURITextField.getText( );
            }

            public void insertUpdate( DocumentEvent e ) {
                changedUpdate(e);
            }

            public void removeUpdate( DocumentEvent e ) {
                changedUpdate(e);
            }
            
        });
        gametelPanel.add( testURITextField );
        
        gametelPanel.add( new JLabel( TC.get( "Operation.ExportToLOM.GAMETEL.TestUserID" ) ) );
        testUserIDTextField = new JTextField();
        testUserIDTextField.getDocument( ).addDocumentListener( new DocumentListener(){

            public void changedUpdate( DocumentEvent e ) {
                testUserID = testUserIDTextField.getText( );
            }

            public void insertUpdate( DocumentEvent e ) {
                changedUpdate(e);
            }

            public void removeUpdate( DocumentEvent e ) {
                changedUpdate(e);
            }
            
        });
        gametelPanel.add( testUserIDTextField );

        //Button panel
        JPanel buttonPanel = new JPanel( );
        JButton okButton = new JButton( TC.get( "Operation.ExportToLOM.OK" ) );
        okButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                validated = true;
                setVisible( false );
                dispose( );
            }
        } );
        buttonPanel.add( okButton );

        //Add all panels
        this.getContentPane( ).add( typePanel, c );
        c.gridy++;
        this.getContentPane( ).add( lomNamePanel, c );
        c.gridy++;
        this.getContentPane( ).add( credentialsPanel, c );
        c.gridy++;
        this.getContentPane( ).add( lomAppletPanel, c );
        c.gridy++;
        this.getContentPane( ).add( gametelContainerPanel, c  );
        c.anchor = GridBagConstraints.CENTER;
        c.gridy++;
        this.getContentPane( ).add( buttonPanel, c );

        // Add window listener
        addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosing( WindowEvent e ) {

                validated = false;
                setVisible( false );
                dispose( );
            }
        } );

        this.setSize( new Dimension( 400, 600 ) );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        //setResizable( false );
        setVisible( true );
    }

    protected void changeWindowedMode( ) {

        windowed = !windowedCheckBox.isSelected( );
    }

    private class TextFieldListener implements DocumentListener {

        private JTextField textField;

        public TextFieldListener( JTextField textField ) {

            this.textField = textField;
        }

        public void changedUpdate( DocumentEvent e ) {

            //Do nothing
        }

        public void insertUpdate( DocumentEvent e ) {

            if( textField == lomNameTextField ) {
                lomName = textField.getText( );
            }
            else if( textField == authorNameTextField ) {
                authorName = textField.getText( );
            }
            else if( textField == organizationTextField ) {
                organizationName = textField.getText( );
            }
        }

        public void removeUpdate( DocumentEvent e ) {

            insertUpdate( e );
        }

    }

    /**
     * @return the lomName
     */
    public String getLomName( ) {

        return lomName;
    }

    /**
     * @return the authorName
     */
    public String getAuthorName( ) {

        return authorName;
    }

    /**
     * @return the organizationName
     */
    public String getOrganizationName( ) {

        return organizationName;
    }

    public int getType1( ) {

        return typeComboBox.getSelectedIndex( );
    }

    /**
     * @return the validated
     */
    public boolean isValidated( ) {

        return validated;
    }

    /**
     * @param validated
     *            the validated to set
     */
    public void setValidated( boolean validated ) {

        this.validated = validated;
    }

    public boolean getWindowed( ) {

        return windowed;
    }
    
    public String getTestUserID( ) {
        
        return testUserID;
    }

    
    public String getTestReturnURI( ) {
    
        return this.testURI;
    }

}
