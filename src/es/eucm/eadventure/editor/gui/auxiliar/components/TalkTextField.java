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
package es.eucm.eadventure.editor.gui.auxiliar.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.gui.TC;

public class TalkTextField extends JPanel {

    private JPopupMenu popup;

    /**
     * 
     */
    private static final long serialVersionUID = 2382254435133716055L;

    private JTextComponent textField;

    public TalkTextField( JTextComponent textField ) {

        initPopup( );
        this.textField = textField;

        textField.setMinimumSize( new Dimension( 600, 20 ) );

        this.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.fill = GridBagConstraints.BOTH;
        c.gridx = c.gridy = 0;
        c.gridheight = 4;
        c.weightx = 0.99f;
        this.add( new JScrollPane( textField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), c );
        c.gridx = 1;
        c.weightx = 0.01f;
        c.gridheight = 1;
        JButton button = new JButton( new ImageIcon("img/icons/ballon.png") );
        button.setToolTipText( TC.get( "ConversationLine.Type" ) );
        button.addMouseListener( new MouseAdapter( ) {

            @Override
            public void mousePressed( MouseEvent e ) {

                popup.show( e.getComponent( ), e.getX( ), e.getY( ) );
            }
        } );
        this.add( button, c );
        c.gridy++;
        this.add( new JPanel(), c );
        c.gridy++;
        this.add( new JPanel(), c );
        c.gridy++;
        this.add( new JPanel(), c );
        
        setMinimumSize( new Dimension( 600, 20 ) );

    }

    private void initPopup( ) {

        popup = new JPopupMenu( );
        for( final ConversationLine.Type type : ConversationLine.Type.values( ) ) {
            JMenuItem item = new JMenuItem( type.getName( ) );
            item.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent e ) {

                    addTag( type.toString( ) );

                }

            } );
            popup.add( item );
        }
    }

    private void addTag( String tag ) {

        String text = textField.getText( );
        if( text != null && !text.equals( "" ) )
            if( text.charAt( 0 ) == '#' ) {
                int i = text.indexOf( ' ' );
                text = i == -1 ? text : text.substring( i + 1 );
            }
        if( !tag.equals( "" ) )
            text = tag + " " + text;

        textField.setText( text );
    }

}
