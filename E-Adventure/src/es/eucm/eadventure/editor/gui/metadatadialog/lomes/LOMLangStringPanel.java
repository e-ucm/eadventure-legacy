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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
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
        value.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.LangString.Value" ) ) );
        valuePanel.add( value, c );

        //c.gridy=1;
        JPanel languagePanel = new JPanel( new GridBagLayout( ) );
        language = new JTextField( languageData );
        language.getDocument( ).addDocumentListener( new TextFieldListener( language ) );
        language.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.LangString.Language" ) ) );
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
