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
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMClassificationTaxon;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMLangStringPanel;

public class LOMTaxonDialog extends JDialog {

    private JTextField id;

    private LOMLangStringPanel entry;

    private String idValue;

    private LangString entryValue;

    //private LOMESContainer container;

    public LOMTaxonDialog( LOMESContainer container, int selectedItem ) {

        super( Controller.getInstance( ).peekWindow( ), container.getTitle( ), Dialog.ModalityType.APPLICATION_MODAL );

        Controller.getInstance( ).pushWindow( this );

        //this.container = container;
        if( selectedItem == 0 ) {
            idValue = "";
            entryValue = new LangString( "" );
        }
        else {
            idValue = ( (LOMClassificationTaxon) container.get( selectedItem - 1 ) ).getIdentifier( );
            entryValue = ( (LOMClassificationTaxon) container.get( selectedItem - 1 ) ).getEntry( );
        }

        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 2, 2, 2, 2 );
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;

        JPanel idPanel = new JPanel( new GridBagLayout( ) );
        id = new JTextField( idValue );
        id.getDocument( ).addDocumentListener( new TextFieldListener( ) );
        idPanel.add( id, c );
        idPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.Classification.TaxonId" ) ) );

        entry = new LOMLangStringPanel( entryValue, TC.get( "LOMES.Classification.TaxonEntry" ) );

        JPanel mainPanel = new JPanel( new GridBagLayout( ) );
        c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        mainPanel.add( idPanel, c );
        c.gridy = 1;
        mainPanel.add( entry, c );

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

        this.getContentPane( ).setLayout( new GridLayout( 0, 2 ) );
        this.getContentPane( ).add( mainPanel );
        this.getContentPane( ).add( buttonPanel );

        this.setSize( new Dimension( 250, 200 ) );
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

    private class TextFieldListener implements DocumentListener {

        public void changedUpdate( DocumentEvent e ) {

            //Do nothing
        }

        public void insertUpdate( DocumentEvent e ) {

            idValue = id.getText( );
        }

        public void removeUpdate( DocumentEvent e ) {

            insertUpdate( e );
        }

    }

    /**
     * @return the idValue
     */
    public String getIdValue( ) {

        return idValue;
    }

    /**
     * @return the entryValue
     */
    public LangString getEntryValue( ) {

        return entryValue;
    }

}
