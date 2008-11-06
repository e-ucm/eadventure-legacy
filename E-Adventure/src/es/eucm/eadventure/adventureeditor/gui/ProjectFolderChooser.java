package es.eucm.eadventure.adventureeditor.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.io.File;

import javax.accessibility.AccessibleContext;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.auxiliar.filefilters.FolderFileFilter;

public class ProjectFolderChooser extends JFileChooser{
	
	private static final String PROJECTS_FOLDER = "Projects";

	private static File getProjectsFolder(){
		File parentDir = new File( PROJECTS_FOLDER );
		if (!parentDir.exists( ))
			parentDir.mkdirs( );
		return parentDir;
	}
	
	public ProjectFolderChooser (boolean checkName, boolean checkDescriptor) {
		super(getProjectsFolder());
		super.setDialogTitle( TextConstants.getText( "Operation.NewProjectTitle" ) );
		super.setMultiSelectionEnabled( false );
		super.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		super.setFileFilter( new FolderFileFilter(checkName, checkDescriptor) );
		super.setCurrentDirectory( Controller.getInstance().projectsFolder() );
	}
	
    protected JDialog createDialog(Component parent) throws HeadlessException {
    	String title = TextConstants.getText( "Operation.NewProjectTitle" );
            putClientProperty(AccessibleContext.ACCESSIBLE_DESCRIPTION_PROPERTY, 
                              title);

            JDialog dialog;
            Window window = JOptionPane.getFrameForComponent( parent );
            //Window window = JOptionPane.getWindowForComponent(parent);
            if (window instanceof Frame) {
                dialog = new JDialog((Frame)window, title, true);	
            } else {
                dialog = new JDialog((Dialog)window, title, true);
            }
            dialog.setComponentOrientation(this.getComponentOrientation());

            JPanel infoPanel = new JPanel();
            JTextArea info = new JTextArea();
            info.setColumns( 10 );
            info.setWrapStyleWord( true );
            info.setFont( new Font( Font.SERIF, Font.PLAIN, 12 ) );
            info.setEditable( false );
            info.setBackground( infoPanel.getBackground( ) );
            info.setText( TextConstants.getText( "Operation.NewProjectMessage", FolderFileFilter.getAllowedChars() ) );
            infoPanel.add( info );
            
            Container contentPane = dialog.getContentPane();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(this, BorderLayout.CENTER);
            contentPane.add( infoPanel, BorderLayout.NORTH );
     
            if (JDialog.isDefaultLookAndFeelDecorated()) {
                boolean supportsWindowDecorations = 
                UIManager.getLookAndFeel().getSupportsWindowDecorations();
                if (supportsWindowDecorations) {
                    dialog.getRootPane().setWindowDecorationStyle(JRootPane.FILE_CHOOSER_DIALOG);
                }
            }
            dialog.pack();
            dialog.setLocationRelativeTo(parent);

    	return dialog;
        }

}
