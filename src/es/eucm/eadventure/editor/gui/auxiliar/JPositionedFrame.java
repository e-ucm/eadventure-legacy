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

package es.eucm.eadventure.editor.gui.auxiliar;

import javax.swing.JFrame;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.config.ConfigData;


public class JPositionedFrame extends JFrame{
    public JPositionedFrame( String string ) {
        super(string);
    }
    
    public JPositionedFrame( ) {
        super();
    }

    @Override
    public void setLocation(int x, int y) {
        x=adjustXWithConfig(x, this.getWidth( ));
        y=adjustYWithConfig(y, this.getHeight( ));
        super.setLocation( x, y );
    }
    
    public static int adjustXWithConfig ( int x, int w ){
        if (Controller.getInstance( )!=null && Controller.getInstance( ).isMainWindowBuilt()){
            int wx=Controller.getInstance( ).getMainWindowX( );
            int ww=Controller.getInstance( ).getMainWindowWidth( );
        
            x=wx+(Math.max( ww, w )-w)/2;
        } else {
            try {
                if (ConfigData.getEditorWindowX( )!=Integer.MAX_VALUE && ConfigData.getEditorWindowY( )!=Integer.MAX_VALUE
                        && ConfigData.getEditorWindowWidth( )!=Integer.MAX_VALUE && ConfigData.getEditorWindowHeight( )!=Integer.MAX_VALUE){
                    // Center the dialog on main window
                    int wx=ConfigData.getEditorWindowX( );
                    int ww=ConfigData.getEditorWindowWidth( );
                    
                    x=wx+(Math.max( ww, w )-w)/2;
                }        
            } catch (NoClassDefFoundError e){
                
            } catch (Exception e1){
                
            }
        }
        
        return x;
    }
    
    public static int adjustYWithConfig ( int y, int h ){
        if (Controller.getInstance( )!=null && Controller.getInstance( ).isMainWindowBuilt()){
            int wy=Controller.getInstance( ).getMainWindowY( );
            int wh=Controller.getInstance( ).getMainWindowHeight( );
            y=wy+(Math.max( wh, h )-h)/2;
        } else {
            try {
                if (ConfigData.getEditorWindowX( )!=Integer.MAX_VALUE && ConfigData.getEditorWindowY( )!=Integer.MAX_VALUE
                        && ConfigData.getEditorWindowWidth( )!=Integer.MAX_VALUE && ConfigData.getEditorWindowHeight( )!=Integer.MAX_VALUE){
                    // Center the dialog on main window
                    int wy=ConfigData.getEditorWindowY( );
                    int wh=ConfigData.getEditorWindowHeight( );
                    
                    y=wy+(Math.max( wh, h )-h)/2;
                }        
            } catch (NoClassDefFoundError e){
                
            } catch (Exception e1){
                
            }           
        }
        
        
        return y;
    }
}
