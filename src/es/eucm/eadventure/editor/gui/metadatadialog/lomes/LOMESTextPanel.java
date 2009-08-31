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
package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

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

import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESTextDataControl;

public class LOMESTextPanel extends JPanel {

    public static final int TYPE_AREA = 0;

    public static final int TYPE_FIELD = 1;

    protected LOMESTextDataControl dataControl;

    /**
     * Text area for the documentation.
     */
    private JTextArea textArea;

    protected JTextField textField;

    public LOMESTextPanel( LOMESTextDataControl dataControl, String title, int type ) {

        this( dataControl, title, type, true );
    }

    public LOMESTextPanel( LOMESTextDataControl dataControl, String title, int type, boolean editable ) {

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

    public String getText( ) {

        //textArea or textField must be != null
        if( this.textArea != null ) {
            return textArea.getText( );
        }
        else {
            return textField.getText( );
        }
    }
}
