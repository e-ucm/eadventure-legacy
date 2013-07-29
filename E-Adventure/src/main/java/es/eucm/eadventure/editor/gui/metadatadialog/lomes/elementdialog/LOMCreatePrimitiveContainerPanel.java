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
package es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

/**
 * Panel for String or LangString elements with SPM more than 1
 * 
 */

public class LOMCreatePrimitiveContainerPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -8966721707717829637L;

    public static final int STRING_TYPE = 0;

    public static final int LANGSTRING_TYPE = 1;

    public static final int VOCABULARY_TYPE = 2;

    public static final int FIELD_TYPE_NONE = 0;

    public static final int FIELD_TYPE_AREA = 1;

    public static final int FIELD_TYPE_FIELD = 2;

    private JButton add;

    private JButton delete;

    private JComboBox elements;

    private ArrayList<String> stringContainer;

    private ArrayList<LangString> langstringContainer;

    private ArrayList<Vocabulary> vocabularyContainer;

    private String[] vocabularyType;

    private int type;

    /**
     * Store if the field of LOMStringDialog is a field or area
     */
    private int fieldType;

    public LOMCreatePrimitiveContainerPanel( int type, ArrayList container, String title, int fieldType ) {

        this.setLayout( new GridBagLayout( ) );
        this.fieldType = fieldType;
        this.type = type;
        if( type == STRING_TYPE ) {
            this.stringContainer = container;
        }
        else if( type == LANGSTRING_TYPE ) {
            this.langstringContainer = container;
        }
        if( type == VOCABULARY_TYPE ) {
            this.vocabularyContainer = container;
        }

        GridBagConstraints c = new GridBagConstraints( );
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;

        String[] containerElements = getElements( );
        String[] ele = new String[ containerElements.length + 1 ];
        ele[0] = TC.get( "LOMES.AddElement" );
        for( int i = 0; i < containerElements.length; i++ ) {
            ele[i + 1] = containerElements[i];
        }

        elements = new JComboBox( ele );
        elements.setPreferredSize( new Dimension( 130, 20 ) );
        this.add( elements, c );

        add = new JButton( TC.get( "LOMES.Add" ) );
        add.addActionListener( new AddButtonListener( ) );

        c.gridx++;
        this.add( add, c );

        delete = new JButton( TC.get( "LOMES.Delete" ) );
        delete.addActionListener( new DeleteButtonListener( ) );
        if( type == VOCABULARY_TYPE )
            c.gridy++;
        else
            c.gridx++;
        this.add( delete, c );

        this.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), title ) );

    }

    public LOMCreatePrimitiveContainerPanel( int type, ArrayList container, String title, String[] vocabularyType ) {

        this( type, container, title, 0 );
        this.vocabularyType = vocabularyType;
    }

    public String[] getElements( ) {

        ArrayList aux = new ArrayList( );
        if( type == STRING_TYPE )
            aux = stringContainer;
        else if( type == LANGSTRING_TYPE )
            aux = langstringContainer;
        else if( type == VOCABULARY_TYPE )
            aux = vocabularyContainer;

        String[] elem = new String[ aux.size( ) ];
        for( int i = 0; i < aux.size( ); i++ ) {
            if( type == STRING_TYPE ) {
                elem[i] = stringContainer.get( i );
            }
            else if( type == LANGSTRING_TYPE ) {
                elem[i] = langstringContainer.get( i ).getValue( 0 );
            }
            if( type == VOCABULARY_TYPE ) {
                elem[i] = vocabularyContainer.get( i ).getValue( );
            }
        }
        return elem;
    }

    /**
     * Add a new langstring outside the button "Add"
     * 
     * @param value
     */
    public void addLangstring( String value ) {

        elements.addItem( value );
        langstringContainer.add( new LangString( value ) );
    }

    /**
     * Listener for the "Delete" button
     */
    private class DeleteButtonListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent arg0 ) {

            int index = elements.getSelectedIndex( );
            if( type == STRING_TYPE ) {
                if( index != 0 ) {
                    stringContainer.remove( index - 1 );
                    elements.removeItemAt( index );
                }
            }
            else if( type == LANGSTRING_TYPE ) {
                if( index != 0 ) {
                    langstringContainer.get( index - 1 ).deleteValue( 0 );
                    elements.removeItemAt( index );
                }
            }
            if( type == VOCABULARY_TYPE ) {
                if( index != 0 ) {
                    vocabularyContainer.remove( index - 1 );
                    elements.removeItemAt( index );
                }
            }
        }
    }

    /**
     * Listener for the "Add" button
     */
    private class AddButtonListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent arg0 ) {

            if( type == STRING_TYPE ) {
                int selectedIndex = elements.getSelectedIndex( );
                String previousValue = "";
                if( selectedIndex != 0 )
                    previousValue = stringContainer.get( selectedIndex - 1 );
                LOMStringDialog idDialog = new LOMStringDialog( previousValue, fieldType );

                if( !idDialog.getTextValue( ).equals( "" ) ) {
                    if( selectedIndex == 0 ) {
                        elements.addItem( idDialog.getTextValue( ) );
                        stringContainer.add( idDialog.getTextValue( ) );
                    }
                    else {
                        stringContainer.remove( selectedIndex - 1 );
                        stringContainer.add( selectedIndex - 1, idDialog.getTextValue( ) );
                        elements.removeItemAt( selectedIndex );
                        elements.insertItemAt( idDialog.getTextValue( ), selectedIndex );
                    }
                }
            }
            else if( type == LANGSTRING_TYPE ) {
                int selectedIndex = elements.getSelectedIndex( );
                LangString previousValue = new LangString( "" );
                if( selectedIndex != 0 )
                    previousValue = langstringContainer.get( selectedIndex - 1 );
                LOMStringDialog idDialog = new LOMStringDialog( previousValue.getValue( 0 ), fieldType );

                if( !idDialog.getTextValue( ).equals( "" ) ) {
                    if( selectedIndex == 0 ) {
                        elements.addItem( idDialog.getTextValue( ) );
                        langstringContainer.add( new LangString( idDialog.getTextValue( ) ) );
                    }
                    else {
                        langstringContainer.remove( selectedIndex - 1 );
                        langstringContainer.add( selectedIndex - 1, new LangString( idDialog.getTextValue( ) ) );
                        elements.removeItemAt( selectedIndex );
                        elements.insertItemAt( idDialog.getTextValue( ), selectedIndex );
                    }
                }
            }
            if( type == VOCABULARY_TYPE ) {
                int selectedIndex = elements.getSelectedIndex( );
                int previousValue = 0;
                if( selectedIndex != 0 )
                    previousValue = vocabularyContainer.get( selectedIndex - 1 ).getValueIndex( );
                LOMVocabularyDialog idDialog = new LOMVocabularyDialog( vocabularyType, selectedIndex == 0 ? 0 : previousValue );

                if( selectedIndex == 0 ) {
                    elements.addItem( vocabularyType[idDialog.getSelection( )] );
                    vocabularyContainer.add( new Vocabulary( vocabularyType, idDialog.getSelection( ) ) );
                }
                else {
                    vocabularyContainer.remove( selectedIndex - 1 );
                    vocabularyContainer.add( selectedIndex - 1, new Vocabulary( vocabularyType, idDialog.getSelection( ) ) );
                    elements.removeItemAt( selectedIndex );
                    elements.insertItemAt( vocabularyType[idDialog.getSelection( )], selectedIndex );
                }

            }
        }

    }

}
