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

package es.eucm.eadventure.editor.plugin.ead2;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;


public class EAD2Control {

    private static EAD2Control instance;
    
    public static EAD2Control getInstance(){
        if (instance==null)
            instance=new EAD2Control();
        return instance;
    }
    
    private Converter converter;
    
    private EAD2Control(){
        converter = new Converter();
    }
    
    public void exportJAR(){
        java.io.File destinyFile = Controller.getInstance( ).export();

        if ( destinyFile != null ){
            // Finally, export it
            Controller.getInstance( ).showLoadingScreen( TC.get( "Operation.ExportProject.AsJAR" ) );
            if( converter.exportJar(destinyFile.getAbsolutePath())) {
                Controller.getInstance( ).showInformationDialog( TC.get( "Operation.ExportT.Success.Title" ), TC.get( "Operation.ExportT.Success.Message" ) );
            }
            else {
                Controller.getInstance( ).showInformationDialog( TC.get( "Operation.ExportT.NotSuccess.Title" ), TC.get( "Operation.ExportT.NotSuccess.Message" ) );
            }
        }
        Controller.getInstance( ).hideLoadingScreen( );
    }
    public void exportWAR(){
        
    }
    public void run(){
        converter.run();
    }
    public void debug(){
        converter.debug();
    }
    
}
