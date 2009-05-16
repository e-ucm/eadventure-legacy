package es.eucm.eadventure.editor.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.accessibility.AccessibleContext;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import es.eucm.eadventure.common.auxiliar.FileFilter;
import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.auxiliar.filefilters.FolderFileFilter;

public class ProjectFolderChooser extends JFileChooser{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField projectName = null;
	
	private static File getDefaultSelectedFile () {
		String defaultName = TextConstants.getText("Operation.NewFileTitle");
		File parentDir = getProjectsFolder();
		int i=0;
		while (new File(parentDir, defaultName).exists()){
			i++;
			defaultName = TextConstants.getText("Operation.NewFileTitle")+ " ("+i+")";
		}
		
		return new File (defaultName);
		
	}

/*	@Override
	public File getSelectedFile() {
		File temp = super.getSelectedFile();
		
		if (projectName != null) {
			temp = new File(this.getCurrentDirectory().getAbsolutePath() + File.separatorChar + projectName.getText());
		}
		
		return temp;
	}
*/	
	private static File getProjectsFolder(){
		File parentDir = ReleaseFolders.projectsFolder() ;
		if (!parentDir.exists( ))
			parentDir.mkdirs( );
		return parentDir;
	}
	
	public ProjectFolderChooser (boolean checkName, boolean checkDescriptor) {
		super(getProjectsFolder());
		super.setDialogTitle( TextConstants.getText( "Operation.NewProjectTitle" ) );
		super.setMultiSelectionEnabled( false );
		super.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
		for (javax.swing.filechooser.FileFilter filter : super.getChoosableFileFilters()) {
			super.removeChoosableFileFilter(filter);
		}
		FolderFileFilter filter = new FolderFileFilter(checkName, checkDescriptor, this);
		super.addChoosableFileFilter(filter);
		super.setFileFilter(filter);
		super.setFileHidingEnabled(true);
		//super.setSelectedFile( new File ( Controller.projectsFolder(),  TextConstants.getText("GeneralText.NewProjectFolder") ) );
		super.setSelectedFile(getDefaultSelectedFile());
		super.setAcceptAllFileFilterUsed(false);
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
            infoPanel.setLayout(new BorderLayout());
            JTextArea info = new JTextArea();
            info.setColumns( 10 );
            info.setWrapStyleWord( true );
            info.setFont( new Font( Font.SERIF, Font.PLAIN, 12 ) );
            info.setEditable( false );
            info.setBackground( infoPanel.getBackground( ) );
            info.setText( TextConstants.getText( "Operation.NewProjectMessage", FolderFileFilter.getAllowedChars() ) );
            infoPanel.add( info , BorderLayout.NORTH);



            String os = System.getProperty("os.name");
            if (os.contains("MAC") || os.contains("mac") || os.contains("Mac")){
            	projectName = new JTextField(50);
            	projectName.setText(ProjectFolderChooser.getDefaultSelectedFile().getName());
            	JPanel tempName = new JPanel();
            	tempName.add(new JLabel(TextConstants.getText("Operation.NewProjectName")));
            	tempName.add(projectName);
            	JButton create = new JButton(TextConstants.getText("Operation.CreateNewProject"));
            	create.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (projectName.getText() != null) {
							String name = projectName.getText();
							if (!name.endsWith(".eap"))
								name = name + ".eap";
							File file = new File(ProjectFolderChooser.this.getCurrentDirectory().getAbsolutePath() + File.separatorChar + name);
							if (!file.exists()) {
								try {
									file.createNewFile();
									ProjectFolderChooser.this.updateUI();
									ProjectFolderChooser.this.setSelectedFile(file);
									ProjectFolderChooser.this.approveSelection();
								} catch( Exception e) {}
							}
						}
					}
            	});
            	tempName.add(create);
            	infoPanel.add(tempName, BorderLayout.SOUTH);
            }
            
            
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
