/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.gui.startdialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.ProjectFolderChooser;


public class FrameForInitialDialogs {
    
    private int option;
    
    private boolean isStartDialog;

    private JFrame frame;
    
    private StartDialog fileChooser;
   
    private ProjectFolderChooser projectChooser;

    
    public FrameForInitialDialogs(boolean isStartDialog){
    
        this.isStartDialog =isStartDialog;
        if (this.isStartDialog)
            fileChooser = new StartDialog();
        else 
            projectChooser = new ProjectFolderChooser(false,false);
        
       
    }
    
    public FrameForInitialDialogs(int tab){
        
        fileChooser = new StartDialog(tab);  
        isStartDialog = true;
        
    }
 
    public synchronized int showStartDialog(){
       
        
        frame = new JFrame(TC.get( "StartDialog.Title" ));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Image> icons = new ArrayList<Image>( );

        icons.add( AssetsController.getImage( "img/Icono-Editor-16x16.png" ) );
        icons.add( AssetsController.getImage( "img/Icono-Editor-32x32.png" ) );
        icons.add( AssetsController.getImage( "img/Icono-Editor-64x64.png" ) );
        icons.add( AssetsController.getImage( "img/Icono-Editor-128x128.png" ) );
        frame.setIconImages( icons );
        
        
    //  Create ActionListener
        ActionListener actionListener = new ActionListener() {
         
        public void actionPerformed( ActionEvent e ) {

           
            String command = e.getActionCommand();
            if (command.equals(StartDialog.APPROVE_SELECTION)) {
                //caution: if this class is used for startdialog, after this method, the frame is hide, but
                // is it is used for projectFolder, the frame is directly destroy in remove method
                getOptionFromChooser();
                
                
            } else if (command.equals(StartDialog.CANCEL_SELECTION)){
                getOptionFromChooser(StartDialog.CANCEL_OPTION);
                
            }
            finish();
        }
        };
        
        if (isStartDialog){
            fileChooser.addActionListener(actionListener);
            frame.add(fileChooser, BorderLayout.CENTER);
        } else {
            projectChooser.addActionListener( actionListener );
            frame.add(projectChooser, BorderLayout.CENTER);
        }
        frame.pack();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension win = frame.getSize();
        frame.setLocation( (screen.width - win.width) / 2,(screen.height - win.height) / 2);

        frame.setVisible(true);
        
        try {
            wait();
        }
        catch( InterruptedException e1 ) {
           
        }
        return option;
      
    }
    
    private void getOptionFromChooser(int option){
        this.option = option;
    }
    
    private void getOptionFromChooser(){
        
        if (isStartDialog){
            option = fileChooser.getOption( );
            // the openfile optin is not managed in startDialog
            if (fileChooser.isOpenOption())
                option = StartDialog.OPEN_FILE_OPTION;
            
        }else {
            
            option = StartDialog.APROVE_SELECTION;
        }
            
            
    }
    
    
    public void remove(){
        frame.dispose();
   }
    
    public synchronized void finish(){
        notify();
        if (isStartDialog)
            frame.setVisible( false);
    }
    
    public int getFileType(){
        
       return fileChooser.getFileType( );
       
    }
    
    public File getRecentFile( ){
        return fileChooser.getRecentFile( );
    }
    
    public File getSelectedFile(){
        if (isStartDialog)
            return fileChooser.getSelectedFile();
        else 
            return projectChooser.getSelectedFile( );
    }

}
