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

package es.eucm.eadventure.editor.plugin.ead2;

import es.eucm.ead.engine.desktop.DesktopGame;
import es.eucm.ead.exporter.AndroidExporter;
import es.eucm.ead.exporter.JarExporter;
import es.eucm.ead.importer.AdventureConverter;
import es.eucm.ead.tools.java.utils.FileUtils;
import es.eucm.eadventure.editor.control.Controller;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Converter {

    private Controller controller;

    private AdventureConverter adventureConverter;

    private DesktopGame game;

    private JarExporter jarExporter;

    private AndroidExporter androidExporter;

    public Converter( Controller controller ) {
        this.controller = controller;
        jarExporter = new JarExporter();
        androidExporter = new AndroidExporter();
        adventureConverter = new AdventureConverter( );
        adventureConverter.setEnableSimplifications(false);
    }

    private String getNewProjectFolder( ){
        return controller.getProjectFolder() + "/ead2";
    }

	public void initGame( ) {
		game = new DesktopGame( false );
		game.setWindowWidth(800);
		game.setWindowHeight(600);
		String folder = getNewProjectFolder();
		game.setPath( folder );
		game.start( );
	}

    public void launch( ) {
		initGame();
        game.setPath( getNewProjectFolder() );
		convert();
		game.start();
    }

    public String convert(){
        String folder = getNewProjectFolder();
        File f = new File( folder);
        if( f.exists( ) ) {
            try {
                FileUtils.deleteRecursive(f);
            } catch (IOException e) {
				e.printStackTrace();
            }
        }
        adventureConverter.convert( controller.getProjectFolder(), folder );
        return folder;
    }

    public void run(){
		launch( );
    }

    public void debug( ) {
		launch( );
    }


    public boolean exportJar( String destiny ){
        jarExporter.export(convert(), destiny, System.out);
        return true;
    }

    public boolean exportWar( String destiny ){
        String folder = getNewProjectFolder();
        String destinyFolder = adventureConverter.convert(folder, null);
        return true;
    }

    public DesktopGame getGame(){
        return game;
    }

    public void setSimplifications(boolean simplifications) {
        adventureConverter.setEnableSimplifications(simplifications);
    }

    public boolean exportApk(String destiny, Properties properties) {
        androidExporter.export(convert(), destiny, properties, true);
        return true;
    }
}
