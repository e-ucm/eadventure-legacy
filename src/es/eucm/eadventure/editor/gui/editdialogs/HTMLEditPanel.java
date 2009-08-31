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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.text.BadLocationException;

import de.schlichtherle.io.File;
import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.component.MutableFilter;
import de.xeinfach.kafenio.interfaces.KafenioPanelConfigurationInterface;
import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.HTMLEditController;

public class HTMLEditPanel extends KafenioPanel {

    private HTMLEditController htmlEditController;

    public void setHtmlEditController( HTMLEditController htmlEditController ) {

        this.htmlEditController = htmlEditController;
    }

    public static HTMLEditPanel getInstance( java.io.File tempfile ) {

        KafenioPanelConfigurationInterface newConfig = null;
        try {
            newConfig = (KafenioPanelConfigurationInterface) Class.forName( "de.xeinfach.kafenio.KafenioPanelConfiguration" ).newInstance( );
        }
        catch( Exception ex ) {
        }

        newConfig.setImageDir( "file://" );

        // NOTE: remove support for tables because of bad support in the editor library
        newConfig.setCustomMenuItems( "edit view font format search tools help" );
        newConfig.setCustomToolBar1( "SAVE,SEPARATOR,CUT,COPY,PASTE,SEPARATOR,BOLD" + ",ITALIC,UNDERLINE,SEPARATOR,LEFT,CENTER,RIGHT,JUSTIFY,SEPARATOR,TABLE" );
        newConfig.setCustomToolBar2( "ulist,olist,spearator,deindent,indent,separator,image,separator,viewsource," + "separator,strike,superscript,subscript,insertcharacter,separator,find,color" );

        File file = null;
        if( tempfile != null )
            file = new File( tempfile.getAbsoluteFile( ) );
        HTMLEditPanel temp = new HTMLEditPanel( newConfig, file );
        return temp;
    }

    public HTMLEditPanel( KafenioPanelConfigurationInterface arg0, File file ) {

        super( arg0 );
        if( file != null )
            try {
                this.loadDocument( file, null );
            }
            catch( IOException e ) {
                ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
            }
            catch( BadLocationException e ) {
                ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
            }
    }

    @Override
    protected void insertLocalImage( java.io.File whatImage ) throws IOException, BadLocationException, RuntimeException {

        whatImage = getImageFromChooser( ".", MutableFilter.EXT_IMG, super.translatrix.getTranslationString( "FiletypeIMG" ) );
        super.insertLocalImage( whatImage );
        htmlEditController.addImage( whatImage );
    }

    public void saveAll( ) {

        try {
            super.writeOut( new File( htmlEditController.getFilename( ) ) );

            java.io.File temp = new File( htmlEditController.getFilename( ) );
            FileInputStream fis = new FileInputStream( temp );
            BufferedInputStream bis = new BufferedInputStream( fis );
            InputStreamReader isr = new InputStreamReader( bis );

            int size = (int) temp.length( ); // get the file size (in bytes)
            char[] data = new char[ size ]; // allocate char array of right size
            isr.read( data, 0, size ); // read into char array
            isr.close( );
            bis.close( );
            fis.close( );

            String contents = new String( data );
            contents = contents.replace( "\\\\", "/" );

            for( java.io.File image : htmlEditController.getImages( ) ) {
                String viejo = image.getAbsolutePath( ).replace( "\\\\", "/" );
                String nuevo = image.getName( );
                contents = contents.replace( viejo, nuevo );
                AssetsController.addSingleAsset( AssetsConstants.CATEGORY_STYLED_TEXT, image.getAbsolutePath( ) );
            }

            FileOutputStream ostr = new FileOutputStream( temp );
            OutputStreamWriter owtr = new OutputStreamWriter( ostr ); // promote

            owtr.write( contents, 0, contents.length( ) );
            owtr.close( );
            ostr.close( );

            if( htmlEditController.isNewFile( ) )
                AssetsController.addSingleAsset( AssetsConstants.CATEGORY_STYLED_TEXT, temp.getAbsolutePath( ) );

        }
        catch( IOException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }
        catch( BadLocationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing( WindowEvent e ) {

        setVisible( false );
        dispose( );
    }

    /**
     * 
     */
    private static final long serialVersionUID = -248722263397313316L;

}
