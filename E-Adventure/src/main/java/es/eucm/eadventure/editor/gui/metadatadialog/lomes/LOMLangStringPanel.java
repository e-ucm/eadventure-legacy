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
package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMLangStringPanel extends JPanel {

    private JTextField value;

    private JTextField language;

    private LangString langstring;

    public LOMLangStringPanel( LangString langstring, String border ) {

        super( );
        String valueData;
        String languageData;
        this.setLayout( new GridBagLayout( ) );

        if( langstring != null ) {
            valueData = langstring.getValue( 0 );
            languageData = langstring.getLanguage( 0 );
            this.langstring = langstring;
        }
        else {
            valueData = new String( "" );
            languageData = new String( "" );
            langstring = new LangString( "" );
        }

        GridBagConstraints c = new GridBagConstraints( );
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        //c.gridy=0;
        JPanel valuePanel = new JPanel( new GridBagLayout( ) );
        value = new JTextField( valueData );
        value.getDocument( ).addDocumentListener( new TextFieldListener( value ) );
        value.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.LangString.Value" ) ) );
        valuePanel.add( value, c );

        //c.gridy=1;
        JPanel languagePanel = new JPanel( new GridBagLayout( ) );
        language = new JTextField( languageData );
        language.getDocument( ).addDocumentListener( new TextFieldListener( language ) );
        language.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.LangString.Language" ) ) );
        languagePanel.add( language, c );

        c.gridy = 0;
        this.add( valuePanel, c );
        c.gridy = 1;
        this.add( languagePanel, c );

        this.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), border ) );

    }

    private class TextFieldListener implements DocumentListener {

        private JTextField text;

        public TextFieldListener( JTextField text ) {

            this.text = text;
        }

        public void changedUpdate( DocumentEvent e ) {

            //Do nothing
        }

        public void insertUpdate( DocumentEvent e ) {

            if( text == value ) {
                langstring.setValue( value.getText( ), 0 );
            }
            else if( text == language ) {
                langstring.setLanguage( language.getText( ), 0 );
            }

        }

        public void removeUpdate( DocumentEvent e ) {

            insertUpdate( e );
        }

    }

}
