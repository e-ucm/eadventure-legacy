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
package es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESGeneralId;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMESCreateContainerPanel;

public class LOMlIdentifierDialog extends JDialog {

    private JTextField catalog;

    private JTextField entry;

    private String catalogValue;

    private String entryValue;

    //private LOMESContainer container;

    public LOMlIdentifierDialog( LOMESContainer container, int selectedItem, int from ) {

        super( Controller.getInstance( ).peekWindow( ), container.getTitle( ), Dialog.ModalityType.APPLICATION_MODAL );
        Controller.getInstance( ).pushWindow( this );

        //this.container = container;
        if( selectedItem == 0 ) {
            catalogValue = LOMIdentifier.CATALOG_DEFAULT;
            entryValue = LOMIdentifier.ENTRY_DEFAULT;
            if( from == LOMESCreateContainerPanel.METAMETADATA ) {
                entryValue += "-meta";
            }
        }
        else {
            catalogValue = ( (LOMESGeneralId) container.get( selectedItem - 1 ) ).getCatalog( );
            entryValue = ( (LOMESGeneralId) container.get( selectedItem - 1 ) ).getEntry( );
        }

        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 2, 2, 2, 2 );
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        JPanel catalogPanel = new JPanel( new GridBagLayout( ) );
        catalog = new JTextField( catalogValue );
        catalog.getDocument( ).addDocumentListener( new TextFieldListener( catalog ) );
        catalogPanel.add( catalog, c );
        catalogPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.GeneralId.CatalogName" ) ) );

        JPanel entryPanel = new JPanel( new GridBagLayout( ) );
        entry = new JTextField( entryValue );
        entry.getDocument( ).addDocumentListener( new TextFieldListener( entry ) );
        entryPanel.add( entry, c );
        entryPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.GeneralId.EntryName" ) ) );

        JPanel buttonPanel = new JPanel( new GridBagLayout( ) );
        c = new GridBagConstraints( );
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        JButton ok = new JButton( "OK" );
        ok.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                dispose( );

            }

        } );
        buttonPanel.add( ok, c );

        JButton info = new JButton( "Info" );
        info.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                showInfo( );
            }

        } );
        c.gridx = 1;
        buttonPanel.add( info, c );

        JPanel mainPanel = new JPanel( new GridBagLayout( ) );
        c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        mainPanel.add( catalogPanel, c );
        c.gridy = 1;
        mainPanel.add( entryPanel, c );
        c.gridy = 2;
        mainPanel.add( buttonPanel, c );

        this.getContentPane( ).setLayout( new GridBagLayout( ) );
        c.gridy = 0;
        this.getContentPane( ).add( mainPanel, c );

        this.setSize( new Dimension( 350, 200 ) );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setResizable( false );
        setVisible( true );

        addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosed( WindowEvent e ) {

                Controller.getInstance( ).popWindow( );

            }

        } );

    }

    public String getCatalog( ) {

        return this.catalogValue;
    }

    public String getEntry( ) {

        return this.entryValue;
    }

    private void showInfo( ) {

        JOptionPane.showMessageDialog( this, TC.get( "LOMES.Identifier.EntryInfo" ), "Info", JOptionPane.INFORMATION_MESSAGE );
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

            if( textField == catalog ) {
                catalogValue = textField.getText( );
            }
            else if( textField == entry ) {
                entryValue = textField.getText( );
            }
        }

        public void removeUpdate( DocumentEvent e ) {

            insertUpdate( e );
        }

    }

}
