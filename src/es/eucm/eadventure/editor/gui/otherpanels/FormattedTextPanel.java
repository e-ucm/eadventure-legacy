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
package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

public class FormattedTextPanel extends JPanel {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    private boolean isValid;

    private String uri;

    private JEditorPane editorPane;

    private JPanel errorPanel;

    public FormattedTextPanel( ) {

        super( );
        editorPane = new JEditorPane( );

        errorPanel = new JPanel( );
        errorPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        errorPanel.add( new JLabel( TC.get( "FormattedTextAssets.NotAvailable" ) ), c );

        editorPane.setOpaque( false );
        editorPane.setEditable( false );
        this.setOpaque( false );
        this.setLayout( new BorderLayout( ) );
        this.add( errorPanel, BorderLayout.CENTER );
    }

    public void loadFile( String uri ) {

        isValid = true;
        this.uri = uri;
        String ext = uri.substring( uri.lastIndexOf( '.' ) + 1, uri.length( ) ).toLowerCase( );
        if( ext.equals( "html" ) || ext.equals( "htm" ) || ext.equals( "rtf" ) ) {

            //Read the text
            StringBuffer textBuffer = new StringBuffer( );
            InputStream is = null;
            try {
                is = AssetsController.getInputStream( uri );
                int c;
                while( ( c = is.read( ) ) != -1 ) {
                    textBuffer.append( (char) c );
                }
            }
            catch( IOException e ) {
                isValid = false;
            }
            finally {
                if( is != null ) {
                    try {
                        is.close( );
                    }
                    catch( IOException e ) {
                        isValid = false;
                    }
                }
            }

            //Set the proper content type
            if( ext.equals( "html" ) || ext.equals( "htm" ) ) {
                editorPane.setContentType( "text/html" );
                ProcessHTML processor = new ProcessHTML( textBuffer.toString( ) );
                String htmlProcessed = processor.start( );
                editorPane.setText( htmlProcessed );
            }
            else {
                editorPane.setContentType( "text/rtf" );
                editorPane.setText( textBuffer.toString( ) );
            }
            isValid = true;

        }

        this.removeAll( );
        if( isValid )
            this.add( editorPane, BorderLayout.CENTER );
        else
            this.add( errorPanel, BorderLayout.CENTER );

        this.updateUI( );
    }

    public void removeFile( ) {

        this.uri = null;
        this.removeAll( );
        this.add( errorPanel, BorderLayout.CENTER );
    }

    /**
     * @return the isValid
     */
    @Override
    public boolean isValid( ) {

        return isValid;
    }

    /**
     * @param isValid
     *            the isValid to set
     */
    public void setValid( boolean isValid ) {

        this.isValid = isValid;
    }

    public class ProcessHTML {

        private String html;

        private int currentPos;

        private int state;

        private final int STATE_NONE = 0;

        private final int STATE_LT = 1;

        private final int STATE_SRC = 2;

        private final int STATE_EQ = 3;

        private final int STATE_RT = 4;

        private final int STATE_RTQ = 5;

        private String reference;

        public ProcessHTML( String html ) {

            this.html = html;
            currentPos = 0;
            state = STATE_NONE;
        }

        public String start( ) {

            state = STATE_NONE;
            String lastThree = "";
            reference = "";
            for( currentPos = 0; currentPos < html.length( ); currentPos++ ) {
                char current = html.charAt( currentPos );
                if( lastThree.length( ) < 3 )
                    lastThree += current;
                else
                    lastThree = lastThree.substring( 1, 3 ) + current;

                if( state == STATE_NONE ) {
                    if( current == '<' ) {
                        state = STATE_LT;
                    }
                }
                else if( state == STATE_LT ) {
                    if( lastThree.toLowerCase( ).equals( "src" ) ) {
                        state = STATE_SRC;
                    }
                    else if( current == '>' ) {
                        state = STATE_NONE;
                    }
                }

                else if( state == STATE_SRC ) {
                    if( current == '=' ) {
                        state = STATE_EQ;
                    }
                    else if( current != ' ' ) {
                        state = STATE_NONE;
                    }
                }
                else if( state == STATE_EQ ) {
                    if( current == '"' ) {
                        state = STATE_RTQ;
                    }
                    else if( current != ' ' ) {
                        reference += current;
                        state = STATE_RT;
                    }
                }
                else if( state == STATE_RTQ ) {
                    if( current != '>' && current != '"' ) {
                        reference += current;
                    }
                    else {
                        state = STATE_NONE;
                        replaceReference( currentPos - reference.length( ), reference.length( ) );
                    }
                }
                else if( state == STATE_RT ) {
                    if( current != '>' && current != ' ' ) {
                        reference += current;
                    }
                    else {
                        state = STATE_NONE;
                        replaceReference( currentPos - reference.length( ), reference.length( ) );
                    }
                }

            }

            return html;
        }

        private void replaceReference( int index, int length ) {

            try {
                boolean isInZip = false;
                String[] assets = AssetsController.getAssetsList( AssetsConstants.CATEGORY_STYLED_TEXT );
                for( String asset : assets )
                    if( asset.equals( uri ) )
                        isInZip = true;

                if( isInZip ) {
                    int lastSlash = Math.max( uri.lastIndexOf( "/" ), uri.lastIndexOf( "\\" ) );
                    String assetPath = uri.substring( 0, lastSlash ) + "/" + reference;
                    String destinyPath = AssetsController.extractResource( assetPath );
                    if( destinyPath != null ) {
                        String leftSide = html.substring( 0, index );
                        String rightSide = html.substring( index + length, html.length( ) );
                        File file = new File( destinyPath );
                        html = leftSide + file.toURI( ).toURL( ).toString( ) + rightSide;
                    }
                }
                reference = "";
            }
            catch( Exception e ) {
                ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
            }
        }
    }

}
