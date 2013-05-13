/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 * research group.
 * 
 * Copyright 2005-2012 <e-UCM> research group.
 * 
 * <e-UCM> is a research group of the Department of Software Engineering and
 * Artificial Intelligence at the Complutense University of Madrid (School of
 * Computer Science).
 * 
 * C Profesor Jose Garcia Santesmases sn, 28040 Madrid (Madrid), Spain.
 * 
 * For more info please visit: <http://e-adventure.e-ucm.es> or
 * <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 * You can access a list of all the contributors to <e-Adventure> at:
 * http://e-adventure.e-ucm.es/contributors
 * 
 * ****************************************************************************
 * <e-Adventure> is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with <e-Adventure>. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.editor.converter;

import javax.swing.JFrame;

import ead.common.model.elements.operations.SystemFields;
import ead.converter.AdventureConverter;
import ead.engine.core.game.GUIImpl;
import ead.engine.core.gdx.desktop.DesktopGame;
import ead.utils.Log4jConfig;
import ead.utils.Log4jConfig.Slf4jLevel;
import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.editor.control.Controller;

public class Converter {

    private Controller controller;

    private AdventureConverter adventureConverter;

    private DesktopGame game;

    private JFrame frame;

    public Converter( Controller controller ) {
        Log4jConfig.configForConsole(Slf4jLevel.Debug, null);
        this.controller = controller;
        adventureConverter = new AdventureConverter( );
        SystemFields.EXIT_WHEN_CLOSE.getVarDef( ).setInitialValue(false);
    }

    public void run( ) {

        GUIImpl.DEBUG = false;
        String folder = controller.getProjectFolder( );
        File f = new File( folder, "data.xml" );
        if( f.exists( ) ) {
            f.delete( );
        }
        adventureConverter.convert( folder, folder );
        if( game == null ) {
            initGame( );
        }
        else {
            game.setModel( folder );
            // Frame needs to be visible
            frame.setVisible( true );
            game.getGame( ).restart( true );
        }
    }

    public void debug( ) {

        run( );
    }

    public void initGame( ) {

        game = new DesktopGame( false );
        String folder = controller.getProjectFolder( );
        game.setModel( folder );
        frame = game.start( );
    }

}
