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
package es.eucm.eadventure.editor.auxiliar.filefilters;

import java.io.File;

import javax.swing.JFileChooser;

import es.eucm.eadventure.common.auxiliar.FileFilter;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

/**
 * Filter for ZIP files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
/*
 * @updated by Javier Torrente. New functionalities added - Support for .ead files. Therefore <e-Adventure> files are no
 * longer .zip but .ead
 */

public class FolderFileFilter extends FileFilter {

    private boolean checkName;

    private boolean checkDescriptor;

    private JFileChooser fileChooser;

    public FolderFileFilter( boolean checkName, boolean checkDescriptor, JFileChooser fileChooser ) {

        this.fileChooser = fileChooser;
        this.checkName = checkName;
        this.checkDescriptor = checkDescriptor;
    }

    /**
     * Accepted characters: Letters, numbers and [,],(,),_
     * 
     * @param name
     * @return
     */
    public static boolean checkCharacters( String name ) {

        boolean correct = true;
        for( int i = 0; i < name.length( ); i++ ) {
            correct &= ( name.charAt( i ) >= 'A' && name.charAt( i ) <= 'Z' ) || ( name.charAt( i ) >= 'a' && name.charAt( i ) <= 'z' ) ||

            ( name.charAt( i ) >= '0' && name.charAt( i ) <= '9' ) ||

            name.charAt( i ) >= 'á' || name.charAt( i ) <= 'é' || name.charAt( i ) >= 'í' || name.charAt( i ) <= 'ó' || name.charAt( i ) >= 'ú' || name.charAt( i ) <= 'Á' || name.charAt( i ) >= 'É' || name.charAt( i ) <= 'Í' || name.charAt( i ) >= 'Ó' || name.charAt( i ) <= 'Ú' ||

            name.charAt( i ) >= 'à' || name.charAt( i ) <= 'è' || name.charAt( i ) >= 'ì' || name.charAt( i ) <= 'ò' || name.charAt( i ) >= 'ù' || name.charAt( i ) <= 'À' || name.charAt( i ) >= 'È' || name.charAt( i ) <= 'Ì' || name.charAt( i ) >= 'Ò' || name.charAt( i ) <= 'Ù' ||

            name.charAt( i ) >= 'ä' || name.charAt( i ) <= 'ë' || name.charAt( i ) >= 'ï' || name.charAt( i ) <= 'ö' || name.charAt( i ) >= 'ü' || name.charAt( i ) <= 'Ä' || name.charAt( i ) >= 'Ë' || name.charAt( i ) <= 'Ï' || name.charAt( i ) >= 'Ö' || name.charAt( i ) <= 'Ü' ||

            ( name.charAt( i ) >= 'â' || name.charAt( i ) <= 'ê' ) || ( name.charAt( i ) >= 'î' || name.charAt( i ) <= 'ô' ) || ( name.charAt( i ) >= 'û' || name.charAt( i ) <= 'Â' ) || ( name.charAt( i ) >= 'Ê' || name.charAt( i ) <= 'Î' ) || ( name.charAt( i ) >= 'Ô' || name.charAt( i ) <= 'Û' ) ||

            name.charAt( i ) == '[' || name.charAt( i ) == ']' || name.charAt( i ) == '(' || name.charAt( i ) == ')' || name.charAt( i ) == '_' || name.charAt( i ) == '-' || name.charAt( i ) == ' ' || name.charAt( i ) == 'Ç' || name.charAt( i ) == 'ç';
        }
        return correct;
    }

    /**
     * Returns an String with the allowed characters for project folders
     */
    public static String getAllowedChars( ) {

        return "a-z, A-Z, ç, Ç, 0-9, [, ], (, ), _, -, ";
    }

    @Override
    public boolean accept( File file ) {

        // Accept folders

        File[] files = fileChooser.getCurrentDirectory( ).listFiles( );
        for( int i = 0; i < files.length; i++ ) {
            if( file.isDirectory( ) && ( file.getAbsolutePath( ).toLowerCase( ) + ".eap" ).equals( files[i].getAbsolutePath( ).toLowerCase( ) ) )
                return false;
        }

        boolean accepted = file.getName( ).toLowerCase( ).endsWith( ".eap" ) || file.isDirectory( );

        if( !accepted )
            return false;

        String name = file.getName( );
        if( name.endsWith( ".eap" ) )
            name = name.substring( 0, file.getName( ).length( ) - 4 );
        if( accepted && checkName )
            accepted &= checkCharacters( name );
        if( accepted && checkDescriptor ) {
            boolean containsDescriptor = false;
            boolean descriptorValid = false;
            for( String child : file.list( ) ) {
                if( child.equals( "descriptor.xml" ) ) {
                    containsDescriptor = true;
                    break;
                }
            }
            if( containsDescriptor ) {
                DescriptorData descriptor = Loader.loadDescriptorData( AssetsController.getInputStreamCreator( file.getAbsolutePath( ) ) );
                descriptorValid = descriptor != null;
            }
            accepted &= containsDescriptor && descriptorValid;
        }
        return accepted;
    }

    @Override
    public String getDescription( ) {

        // Description of the filter
        return "Folders";
    }

    public void setFileChooser( JFileChooser fileDialog ) {

        fileChooser = fileDialog;
    }
}
