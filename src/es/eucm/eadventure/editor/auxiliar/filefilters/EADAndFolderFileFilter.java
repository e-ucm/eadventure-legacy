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
package es.eucm.eadventure.editor.auxiliar.filefilters;

import java.io.File;

import javax.swing.JFileChooser;

import es.eucm.eadventure.common.auxiliar.FileFilter;

/**
 * Filter for ZIP files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
/*
 * @updated by Javier Torrente. New functionalities added - Support for .ead files. Therefore <e-Adventure> files are no
 * longer .zip but .ead
 */

public class EADAndFolderFileFilter extends FileFilter {

    private JFileChooser fileChooser;

    public EADAndFolderFileFilter( JFileChooser startDialog ) {

        this.fileChooser = startDialog;
    }

    @Override
    public boolean accept( File file ) {

        // Accept XML files and folders
        File[] files = fileChooser.getCurrentDirectory( ).listFiles( );
        for( int i = 0; i < files.length; i++ ) {
            if( file.isDirectory( ) && ( file.getAbsolutePath( ).toLowerCase( ) + ".eap" ).equals( files[i].getAbsolutePath( ).toLowerCase( ) ) )
                return false;
        }
        return file.getAbsolutePath( ).toLowerCase( ).endsWith( ".ead" ) || file.getAbsolutePath( ).toLowerCase( ).endsWith( ".eap" ) || file.isDirectory( );
    }

    @Override
    public String getDescription( ) {

        // Description of the filter
        return "<e-Adventure> Files (*.ead) and Projects (*.eap)";
    }
}
