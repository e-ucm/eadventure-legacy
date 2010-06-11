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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.text.BadLocationException;

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
            file = new File( tempfile.getAbsolutePath( ) );
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
