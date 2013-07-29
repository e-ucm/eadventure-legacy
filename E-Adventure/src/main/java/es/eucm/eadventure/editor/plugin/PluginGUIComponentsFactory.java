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

package es.eucm.eadventure.editor.plugin;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.plugin.ead2.EAD2Control;
import es.eucm.eadventure.editor.plugin.echaracter.gui.ECharacterButton;
import es.eucm.eadventure.editor.plugin.vignette.VignetteGUIComponent;
import es.eucm.eadventure.editor.plugin.vignette.VignetteGUIComponentImplementation;

public class PluginGUIComponentsFactory {

    ////////////////////////////////////////////////////
    //  Methods for adding Vignette, ECharacter and EAdventure2.0
    ////////////////////////////////////////////////////
    
    /*
     * Uncomment this method (and comment the next one) to get ECharacterButton visible
     */
    public static void addECharacterButtonContainer( Container parent, GridBagConstraints c, ResourcesDataControl resources ){
        JButton createWithECharacterButton = new ECharacterButton(resources);
        JPanel createWithECharacterPanel = new JPanel();
        createWithECharacterPanel.setLayout( new BorderLayout() );
        createWithECharacterPanel.add( createWithECharacterButton, BorderLayout.WEST );
        c.anchor=GridBagConstraints.WEST;
        c.weightx=0;
        parent.add( createWithECharacterPanel, c );
    }
    
    public static VignetteGUIComponent buildAddVignetteGUIComponent( ){
        return new VignetteGUIComponentImplementation();
    }
    
    public static void addEad2JMenuItems(JMenu runMenu, JMenu itExport){
        itExport.addSeparator();
        JMenuItem itExportJarNew = new JMenuItem(TC.get("MenuFile.ExportStandaloneNew"));
        itExportJarNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                EAD2Control.getInstance( ).exportJAR( );
            }
        });

        JMenuItem itExportWar = new JMenuItem(TC.get("MenuFile.ExportWar"));
        itExportWar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                EAD2Control.getInstance( ).exportWAR();
            }
        });

        itExport.add(itExportJarNew);
        itExport.add(itExportWar);   
        
        runMenu.add( new JSeparator() );
        
        JMenuItem experimentalNormalRun = new JMenuItem( TC.get( "MenuRun.Experimental.Normal" ) );
        experimentalNormalRun.setAccelerator( KeyStroke.getKeyStroke( 'R', InputEvent.ALT_GRAPH_MASK ) );
        experimentalNormalRun.addActionListener( new ActionListener( ){
           
            public void actionPerformed(ActionEvent e){

                new Thread( new Runnable() {
                    public void run() {
                        EAD2Control.getInstance( ).run( );
                    }
                }).start();
            };
        });
        runMenu.add( experimentalNormalRun );
        
        JMenuItem experimentalNormalDebug = new JMenuItem( TC.get( "MenuRun.Experimental.Debug" ) );
        experimentalNormalDebug.setAccelerator( KeyStroke.getKeyStroke( 'D', InputEvent.ALT_GRAPH_MASK ) );
        experimentalNormalDebug.addActionListener( new ActionListener( ){
           
            public void actionPerformed(ActionEvent e){

                new Thread( new Runnable() {
                    public void run() {
                        EAD2Control.getInstance( ).debug( );
                    }
                }).start();
            }
        });
        runMenu.add( experimentalNormalDebug );        
    }
    
    ////////////////////////////////////////////////////
    //  Methods for NOT adding Vignette, ECharacter and EAdventure2.0 (blank)
    ////////////////////////////////////////////////////    
    
    /*public static void addECharacterButtonContainer( Container parent, GridBagConstraints c, ResourcesDataControl resources ){
        // By default, do nothing
    }
    
    public static VignetteGUIComponent buildAddVignetteGUIComponent(  ){
        return new VignetteGUIComponentPlaceHolder();
    }

    public static void addEad2JMenuItems(JMenu runMenu, JMenu itExport){
        // By default, do nothing    
    }*/
   
}
