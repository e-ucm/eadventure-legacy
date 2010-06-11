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
package es.eucm.eadventure.editor.gui.metadatadialog.lomdialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMTextDataControl;

public class LOMTextPanel extends JPanel {

    public static final int TYPE_AREA = 0;

    public static final int TYPE_FIELD = 1;

    private LOMTextDataControl dataControl;

    /**
     * Text area for the documentation.
     */
    private JTextArea textArea;

    private JTextField textField;

    public LOMTextPanel( LOMTextDataControl dataControl, String title, int type ) {

        this( dataControl, title, type, true );
    }

    public LOMTextPanel( LOMTextDataControl dataControl, String title, int type, boolean editable ) {

        this.dataControl = dataControl;

        setLayout( new GridLayout( ) );

        if( type == TYPE_AREA ) {
            textArea = new JTextArea( dataControl.getText( ), 4, 0 );
            textArea.setLineWrap( true );
            textArea.setWrapStyleWord( true );
            textArea.getDocument( ).addDocumentListener( new TextAreaChangesListener( ) );
            textArea.setEditable( editable );
            add( new JScrollPane( textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) );
            textField = null;
        }
        else if( type == TYPE_FIELD ) {
            textField = new JTextField( dataControl.getText( ) );
            textField.addActionListener( new TextFieldChangesListener( ) );
            textField.addFocusListener( new TextFieldChangesListener( ) );
            textField.setEditable( editable );
            add( textField );
            textArea = null;
        }
        setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), title ) );

    }

    /**
     * Called when a text field has changed, so that we can set the new values.
     * 
     * @param source
     *            Source of the event
     */
    private void valueChanged( Object source ) {

        // Check the name field
        if( textField != null ) {
            dataControl.setText( textField.getText( ) );
        }
        else if( textArea != null ) {
            dataControl.setText( textArea.getText( ) );
        }
    }

    /**
     * Changes the value
     * 
     * @param text
     *            new value
     */
    public void setValue( String text ) {

        dataControl.setText( text );
    }

    /**
     * Listener for the text area. It checks the value of the area and updates
     * the documentation.
     */
    private class TextAreaChangesListener implements DocumentListener {

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
         */
        public void changedUpdate( DocumentEvent arg0 ) {

            // Do nothing
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
         */
        public void insertUpdate( DocumentEvent arg0 ) {

            // Set the new content
            dataControl.setText( textArea.getText( ) );
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
         */
        public void removeUpdate( DocumentEvent arg0 ) {

            // Set the new content
            dataControl.setText( textArea.getText( ) );
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

    public void updateText( ) {

        // Check the name field
        if( textField != null ) {
            textField.setText( dataControl.getText( ) );
        }
        else if( textArea != null ) {
            textArea.setText( dataControl.getText( ) );
        }
        updateUI( );

    }
}
