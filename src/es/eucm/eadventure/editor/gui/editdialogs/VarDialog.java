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

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;

public class VarDialog extends JDialog {

    private static final long serialVersionUID = 2389765067746677296L;

    public static final String CLOSE = "close";

    private WholeNumberField value;

    private JComboBox actions;

    private boolean isVar;

    private boolean close;

    public VarDialog( int var, String[] actionsValues, String action ) {

        // Call to the JDialog constructor
        super( Controller.getInstance( ).peekWindow( ), TC.get( "VarDialog.Title" ), Dialog.ModalityType.TOOLKIT_MODAL );

        close = false;
        isVar = true;
        if( var == -1 ) {
            isVar = false;
        }

        // Push the dialog into the stack, and add the window listener to pop in when closing
        Controller.getInstance( ).pushWindow( this );
        addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosing( WindowEvent e ) {

                Controller.getInstance( ).popWindow( );
                close = true;
            }
        } );

        actions = new JComboBox( actionsValues );
        actions.setSelectedItem( action );
        JPanel cont1 = new JPanel( );
        cont1.add( actions, BorderLayout.CENTER );
        cont1.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "VarDialog.Value" ) ) );

        if( isVar ) {
            value = new WholeNumberField( var, 5 );
            //value.setPreferredSize(new Dimension (150,20));
            JPanel cont = new JPanel( );
            cont.add( value, BorderLayout.CENTER );
            cont.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "VarDialog.Value" ) ) );

            JPanel both = new JPanel( );
            both.setLayout( new GridLayout( 0, 2 ) );
            both.add( cont1 );
            both.add( cont );
            add( both );
        }
        else {
            add( cont1 );
        }
        // Create Ok button to close the dialog
        JButton ok = new JButton( "OK" );
        ok.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                dispose( );
            }

        } );
        JPanel container = new JPanel( );
        container.add( ok );
        add( ok, BorderLayout.SOUTH );
        // Set the size, position and properties of the dialog
        //setResizable( false );
        setSize( 400,200 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
        ok.requestFocus( );

    }

    public String getValue( ) {

        
        if( close )
            return CLOSE;
        else if( isVar )
            return (String) actions.getSelectedItem( ) + " " + value.getText( );
        else
            return (String) actions.getSelectedItem( );
    }

    public class WholeNumberField extends JTextField {

        /**
         * 
         */
        private static final long serialVersionUID = 5121937653771983070L;

        private Toolkit toolkit;

        private NumberFormat integerFormatter;

        public WholeNumberField( int value, int columns ) {

            super( columns );
            toolkit = Toolkit.getDefaultToolkit( );
            integerFormatter = NumberFormat.getNumberInstance( Locale.US );
            integerFormatter.setParseIntegerOnly( true );
            setValue( value );
        }

        public int getValue( ) {

            int retVal = 0;
            try {
                retVal = integerFormatter.parse( getText( ) ).intValue( );
            }
            catch( ParseException e ) {
                // This should never happen because insertString allows
                // only properly formatted data to get in the field.
                toolkit.beep( );
            }
            return retVal;
        }

        public void setValue( int value ) {

            setText( integerFormatter.format( value ) );
        }

        @Override
        protected Document createDefaultModel( ) {

            return new WholeNumberDocument( );
        }

        protected class WholeNumberDocument extends PlainDocument {

            /**
             * 
             */
            private static final long serialVersionUID = 3398461058646116687L;

            @Override
            public void insertString( int offs, String str, AttributeSet a ) throws BadLocationException {

                char[] source = str.toCharArray( );
                char[] result = new char[ source.length ];
                int j = 0;

                for( int i = 0; i < result.length; i++ ) {
                    if( Character.isDigit( source[i] ) )
                        result[j++] = source[i];
                    else {
                        toolkit.beep( );
                        Controller.getInstance( ).showErrorDialog( TC.get( "VarDialog.Err.Title" ), TC.get( "VarDialog.Err.Message" ) );
                    }
                }
                super.insertString( offs, new String( result, 0, j ), a );
            }
        }

    }

}
