package es.eucm.eadventure.editor.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.treepanel.TreeNodeControl;
import es.eucm.eadventure.editor.gui.treepanel.TreePanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.EmptyTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.ChapterTreeNode;

/**
 * This class represents the main frame of the application. It has all the elements of the view part of the application,
 * as well as the responsible functions for showing and requesting data.
 * 
 * @author Bruno Torijano Bueno
 */
public class MainWindow extends JFrame {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default width of the window.
	 */
	private static final int WINDOW_WIDTH = 900;

	/**
	 * Default height of the window.
	 */
	private static final int WINDOW_HEIGHT = 725;

	/**
	 * Instance of the controller.
	 */
	private Controller controller;

	/**
	 * Chapters menu (to reload the chapters when needed).
	 */
	private JMenu chaptersMenu;

	private JMenuItem itPlayerMode;

	private JCheckBoxMenuItem itShowStartDialog;

	/**
	 * Tree panel containing the data tree.
	 */
	private TreePanel treePanel;

	/**
	 * Stack of windows opened.
	 */
	private Stack<Window> windowsStack;
	
	private JMenuItem normalRun;
	
	private JMenuItem debugRun;
	
	private JMenuItem undo;
	
	private JMenuItem redo;
	
	
	/**
	 * Constructor. Creates the general layout.
	 */
	public MainWindow( ) {

		// Store the controller
		controller = Controller.getInstance( );

		// Set the look and feel
		try {
			//UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName( ) );
		} catch( Exception e ) {
        	//ErrorReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		// Load the icons and graphic resources
		TreeNode.loadIcons( );
		TreePanel.loadIcons( );
		
		// Create the list of icons of the window
		List<Image> icons = new ArrayList<Image>();
		
		icons.add( AssetsController.getImage("img/Icono-Editor-16x16.png") );
		icons.add( AssetsController.getImage("img/Icono-Editor-32x32.png") );
		icons.add( AssetsController.getImage("img/Icono-Editor-64x64.png") );
		icons.add( AssetsController.getImage("img/Icono-Editor-128x128.png") );
		this.setIconImages(icons);

		// First of all, create the bar
		setJMenuBar( createMenuBar() );
		
		// Create the tree structure for the panel
		TreeNode root = null;
		if (Controller.getInstance( ).isFloderLoaded( ))
			root = new ChapterTreeNode( null, controller.getSelectedChapterDataControl( ),
					controller.getAssessmentController( ), controller.getAdaptationController( ));
		else
			root = new EmptyTreeNode(null);
		
		TreeNodeControl.getInstance().setRoot(root);

		// Create the two panels
		JPanel editorContainer = new JPanel( );
		editorContainer.setMinimumSize( new Dimension( 400, 0 ) );
		editorContainer.setLayout( new BorderLayout( ) );
		treePanel = new TreePanel( root, editorContainer );
		treePanel.setMinimumSize( new Dimension( 210, 0 ) );
		treePanel.setPreferredSize( new Dimension( 210, 0 ) );

		// Create the split panel
		JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, treePanel, editorContainer );
		splitPane.setBorder( null );

		// Add the panels to the frame
		setLayout( new BorderLayout( ) );
		add( splitPane, BorderLayout.CENTER );

		// Set the "on close" operation and the closing listener
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent arg0 ) {
				controller.exit( );
			}
		} );

		// Create the windows stack
		windowsStack = new Stack<Window>( );

		// Set size and position
		setMinimumSize( new Dimension( 640, 400 ) );
		setSize( WINDOW_WIDTH, WINDOW_HEIGHT );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - WINDOW_WIDTH ) / 2, ( screenSize.height - WINDOW_HEIGHT ) / 2 );

		this.setModalExclusionType( Dialog.ModalExclusionType.APPLICATION_EXCLUDE );
		// Set title and properties
		updateTitle( );
	}

	
	private JMenuBar createMenuBar (){
		return createMenuBarAdventureMode();
	}
	
	private JMenuBar createMenuBarAdventureMode (){
		JMenuBar windowMenu = new JMenuBar( );
		//windowMenu.setLayout( new BoxLayout(windowMenu, BoxLayout.LINE_AXIS));
		windowMenu.setLayout( new FlowLayout(FlowLayout.LEFT));

		// Create the menus
		JMenu fileMenu = new JMenu( TextConstants.getText( "MenuFile.Title" ) );
		fileMenu.setMnemonic( KeyEvent.VK_F );
		windowMenu.add( fileMenu );
		JMenu editMenu = new JMenu(TextConstants.getText("MenuEdit.Title"));
		windowMenu.add(editMenu);
		JMenu adventureMenu = new JMenu( TextConstants.getText( "MenuAdventure.Title" ) );
		adventureMenu.setEnabled( Controller.getInstance( ).isFloderLoaded( ) );
		adventureMenu.setMnemonic( KeyEvent.VK_A );
		windowMenu.add( adventureMenu );
		chaptersMenu = new JMenu( TextConstants.getText( "MenuChapters.Title" ) );
		chaptersMenu.setEnabled( Controller.getInstance( ).isFloderLoaded( ) );
		chaptersMenu.setMnemonic( KeyEvent.VK_H );
		windowMenu.add( chaptersMenu );
		JMenu runMenu = new JMenu( TextConstants.getText( "MenuRun.Title" ) );
		windowMenu.add( runMenu );
		JMenu configurationMenu = new JMenu( TextConstants.getText( "MenuConfiguration.Title" ) );
		configurationMenu.setMnemonic( KeyEvent.VK_T );
		windowMenu.add( configurationMenu );
		JMenu about = new JMenu( TextConstants.getText( "Menu.About" ) );
		JMenuItem aboutEadventure = new JMenuItem ( TextConstants.getText( "Menu.AboutEAD" ) );
		about.add(aboutEadventure);
		aboutEadventure.setArmed( false );
		aboutEadventure.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent arg0 ) {
				controller.showAboutDialog();
			}
			
		});
		JMenuItem sendComments = new JMenuItem ( TextConstants.getText( "Menu.SendComments" ) );
		about.add(sendComments);
		sendComments.setArmed( false );
		sendComments.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent arg0 ) {
				ReportDialog.GenerateCommentsReport();
			}
			
		});
		windowMenu.add( about );

		// Create the "File" elements
		JMenu itFileNew = new JMenu( TextConstants.getText( "MenuFile.New" ) );
		JMenuItem itNewAdventurePlayerVisible = new JMenuItem( TextConstants.getText( "MenuFile.NewAdventurePlayerVisible" ) );
		JMenuItem itNewAdventurePlayerTransparent = new JMenuItem( TextConstants.getText( "MenuFile.NewAdventurePlayerTransparent" ) );
		itNewAdventurePlayerVisible.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.newFile( Controller.FILE_ADVENTURE_3RDPERSON_PLAYER );
			}
		} );
		itNewAdventurePlayerTransparent.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.newFile( Controller.FILE_ADVENTURE_1STPERSON_PLAYER );
			}
		} );
		itFileNew.add( itNewAdventurePlayerTransparent );
		itFileNew.add( itNewAdventurePlayerVisible );
		itNewAdventurePlayerVisible.setAccelerator( KeyStroke.getKeyStroke( 'N', InputEvent.CTRL_MASK ) );
		fileMenu.add( itFileNew );
		JMenuItem itFileLoad = new JMenuItem( TextConstants.getText( "MenuFile.Load" ) );
		itFileLoad.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.loadFile( );
			}
		} );
		itFileLoad.setAccelerator( KeyStroke.getKeyStroke( 'L', InputEvent.CTRL_MASK ) );
		fileMenu.add( itFileLoad );
		fileMenu.addSeparator( );
		JMenuItem itFileSave = new JMenuItem( TextConstants.getText( "MenuFile.Save" ) );
		itFileSave.setEnabled( controller.isFloderLoaded( ) );
		itFileSave.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				//controller.saveFile( controller.isTempFile( ) );
				controller.saveFile( false );
			}
		} );
		itFileSave.setAccelerator( KeyStroke.getKeyStroke( 'S', InputEvent.CTRL_MASK ) );
		fileMenu.add( itFileSave );
		JMenuItem itFileSaveAs = new JMenuItem( TextConstants.getText( "MenuFile.SaveAs" ) );
		itFileSaveAs.setEnabled( controller.isFloderLoaded( ) );
		itFileSaveAs.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.saveFile( true );
			}
		} );
		fileMenu.add( itFileSaveAs );
		fileMenu.addSeparator( );
		JMenuItem itLOMProp = new JMenuItem( TextConstants.getText( "MenuFile.LOMProperties" ) );
		itLOMProp.setEnabled( controller.isFloderLoaded( ) );
		itLOMProp.addActionListener(  new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showLOMDataDialog( );
			}
		} );
		fileMenu.add( itLOMProp );
		fileMenu.addSeparator( );
		
		JMenu itImport = new JMenu( TextConstants.getText( "MenuFile.Import" ) );
		JMenuItem itImportGame = new JMenuItem( TextConstants.getText( "MenuFile.ImportGame" ) );
		itImportGame.addActionListener(  new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.importGame();
			}
		} );

		JMenuItem importChapter = new JMenuItem( TextConstants.getText( "MenuFile.ImportChapter" ) );
		importChapter.addActionListener(  new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.importChapter();
			}
		} );
		itImport.add( itImportGame );
		//itImport.add( importChapter );
		fileMenu.add( itImport );
		fileMenu.addSeparator( );
		
		JMenu itExport = new JMenu( TextConstants.getText( "MenuFile.Export" ) );
		itExport.setEnabled( controller.isFloderLoaded( ) );
		JMenuItem itExportGame = new JMenuItem( TextConstants.getText( "MenuFile.ExportGame" ) );
		itExportGame.addActionListener(  new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.exportGame();
			}
		} );

		JMenuItem itExportStandalone = new JMenuItem( TextConstants.getText( "MenuFile.ExportStandalone" ) );
		itExportStandalone.addActionListener(  new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.exportStandaloneGame( );
			}
		} );
		
		JMenuItem itExportLOM = new JMenuItem( TextConstants.getText( "MenuFile.ExportLOM" ) );
		itExportLOM.addActionListener(  new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.exportToLOM();
			}
		} );
		itExport.add( itExportGame );
		itExport.add( itExportStandalone );
		itExport.add( itExportLOM );
		fileMenu.add( itExport );
		fileMenu.addSeparator( );

		JMenuItem itFileExit = new JMenuItem( TextConstants.getText( "MenuFile.Exit" ) );
		itFileExit.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.exit( );
			}
		} );
		fileMenu.add( itFileExit );

		
		undo = new JMenuItem(TextConstants.getText("MenuEdit.Undo"));
		undo.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undoTool();
			}
		});
		undo.setAccelerator( KeyStroke.getKeyStroke( 'Z', InputEvent.CTRL_MASK ) );

		redo = new JMenuItem(TextConstants.getText("MenuEdit.Redo"));
		redo.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redoTool();
			}
		});
		redo.setAccelerator( KeyStroke.getKeyStroke( 'Y', InputEvent.CTRL_MASK ) );

		editMenu.add(undo);
		editMenu.add(redo);
		
		
		// Create the "Adventure" elements
		JMenuItem itCheckConsistency = new JMenuItem( TextConstants.getText( "MenuAdventure.CheckConsistency" ) );
		itCheckConsistency.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.checkAdventureConsistency( );
			}
		} );
		adventureMenu.add( itCheckConsistency );
		adventureMenu.addSeparator( );
		JMenuItem itAdventureData = new JMenuItem( TextConstants.getText( "MenuAdventure.AdventureData" ) );
		itAdventureData.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showAdventureDataDialog( );
			}
		} );
		adventureMenu.add( itAdventureData );
		
		JMenu visualization = new JMenu(TextConstants.getText("MenuAdventure.Visualization"));
		JMenuItem itGUIStyles = new JMenuItem( TextConstants.getText( "MenuAdventure.GUIStyles" ) );
		itGUIStyles.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showGUIStylesDialog( );
			}
		} );
		visualization.add( itGUIStyles );
		JMenuItem itCustomizeGUI = new JMenuItem( TextConstants.getText( "MenuAdventure.CustomizeGUI" ) );
		itCustomizeGUI.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showCustomizeGUIDialog( );
			}
		} );
		visualization.add( itCustomizeGUI );
		JMenuItem itGraphicConfig = new JMenuItem( TextConstants.getText( "MenuAdventure.GraphicConfig"));
		itGraphicConfig.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				controller.showGraphicConfigDialog();
			}
		});
		visualization.add( itGraphicConfig);
		adventureMenu.add( visualization);
		
		if( controller.isPlayTransparent( ) ) {
			itPlayerMode = new JMenuItem( TextConstants.getText( "MenuAdventure.ChangeToModePlayerVisible" ) );
			itPlayerMode.setToolTipText( TextConstants.getText( "MenuAdventure.ModePlayerVisible" ) );
		} else {
			itPlayerMode = new JMenuItem( TextConstants.getText( "MenuAdventure.ChangeToModePlayerTransparent" ) );
			itPlayerMode.setToolTipText( TextConstants.getText( "MenuAdventure.ModePlayerTransparent" ) );
		}
		itPlayerMode.addActionListener( new ActionListener( ) {

			public void actionPerformed( ActionEvent e ) {
				controller.swapPlayerMode( true );
				reloadData(  );
			}

		} );
		adventureMenu.add( itPlayerMode );
		adventureMenu.addSeparator( );
		/*JMenuItem itAssessmentFiles = new JMenuItem( TextConstants.getText( "MenuAdventure.AssessmentFiles" ) );
		itAssessmentFiles.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showAssessmentFilesDialog( );
			}
		} );
		adventureMenu.add( itAssessmentFiles );
		JMenuItem itAdaptationFiles = new JMenuItem( TextConstants.getText( "MenuAdventure.AdaptationFiles" ) );
		itAdaptationFiles.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showAdaptationFilesDialog( );
			}
		} );
		adventureMenu.add( itAdaptationFiles );*/
		//adventureMenu.addSeparator( );
		JMenuItem itBackgroundAssets = new JMenuItem( TextConstants.getText( "MenuAdventure.BackgroundAssets" ) );
		itBackgroundAssets.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showBackgroundAssetsDialog( );
			}
		} );
		adventureMenu.add( itBackgroundAssets );
		JMenuItem itAnimationAssets = new JMenuItem( TextConstants.getText( "MenuAdventure.AnimationAssets" ) );
		itAnimationAssets.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showAnimationAssetsDialog( );
			}
		} );
		adventureMenu.add( itAnimationAssets );
		JMenuItem itImageAssets = new JMenuItem( TextConstants.getText( "MenuAdventure.ImageAssets" ) );
		itImageAssets.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showImageAssetsDialog( );
			}
		} );
		adventureMenu.add( itImageAssets );
		JMenuItem itIconAssets = new JMenuItem( TextConstants.getText( "MenuAdventure.IconAssets" ) );
		itIconAssets.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showIconAssetsDialog( );
			}
		} );
		adventureMenu.add( itIconAssets );
		JMenuItem itAudioAssets = new JMenuItem( TextConstants.getText( "MenuAdventure.AudioAssets" ) );
		itAudioAssets.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showAudioAssetsDialog( );
			}
		} );
		adventureMenu.add( itAudioAssets );
		JMenuItem itVideoAssets = new JMenuItem( TextConstants.getText( "MenuAdventure.VideoAssets" ) );
		itVideoAssets.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showVideoAssetsDialog( );
			}
		} );
		adventureMenu.add( itVideoAssets );

		// Create the "Chapter" elements
		JMenuItem itAddChapter = new JMenuItem( TextConstants.getText( "MenuChapters.AddChapter" ) );
		itAddChapter.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.addChapter( );
			}
		} );
		chaptersMenu.add( itAddChapter );
		JMenuItem itDeleteChapter = new JMenuItem( TextConstants.getText( "MenuChapters.DeleteChapter" ) );
		itDeleteChapter.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.deleteChapter( );
			}
		} );
		chaptersMenu.add( itDeleteChapter );
		chaptersMenu.addSeparator( );
		JMenuItem itMoveChapterUp = new JMenuItem( TextConstants.getText( "MenuChapters.MoveChapterUp" ) );
		itMoveChapterUp.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.moveChapterUp( );
			}
		} );
		chaptersMenu.add( itMoveChapterUp );
		JMenuItem itMoveChapterDown = new JMenuItem( TextConstants.getText( "MenuChapters.MoveChapterDown" ) );
		itMoveChapterDown.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.moveChapterDown( );
			}
		} );
		chaptersMenu.add( itMoveChapterDown );
		chaptersMenu.addSeparator( );
		JMenuItem itEditFlags = new JMenuItem( TextConstants.getText( "MenuChapters.Flags" ) );
		itEditFlags.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.showEditFlagDialog( );
			}
		} );
		itEditFlags.setAccelerator( KeyStroke.getKeyStroke( 'F', InputEvent.CTRL_MASK ) );
		chaptersMenu.add( itEditFlags );
		chaptersMenu.addSeparator( );
		updateChapterMenu( );

		// Create "run" elements
		normalRun = new JMenuItem( TextConstants.getText( "MenuRun.Normal" ) );
		normalRun.setAccelerator( KeyStroke.getKeyStroke( 'R', InputEvent.CTRL_MASK ) );
		normalRun.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				controller.run( );
			}
			
		});
		runMenu.add( normalRun );
		
		debugRun = new JMenuItem( TextConstants.getText( "MenuRun.Debug" ) );
		debugRun.setAccelerator( KeyStroke.getKeyStroke( 'D', InputEvent.CTRL_MASK ) );
		debugRun.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				controller.debugRun( );
			}
			
		});
		runMenu.add( debugRun );
		
		// Create the "Configuration" elements
		JCheckBoxMenuItem itShowItemReferences = new JCheckBoxMenuItem( TextConstants.getText( "MenuConfiguration.ShowItemReferences" ), controller.getShowItemReferences( ) );
		itShowItemReferences.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.setShowItemReferences( ( (JCheckBoxMenuItem) e.getSource( ) ).isSelected( ) );
			}
		} );
		configurationMenu.add( itShowItemReferences );
		JCheckBoxMenuItem itShowNPCReferences = new JCheckBoxMenuItem( TextConstants.getText( "MenuConfiguration.ShowNPCReferences" ), controller.getShowNPCReferences( ) );
		itShowNPCReferences.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.setShowNPCReferences( ( (JCheckBoxMenuItem) e.getSource( ) ).isSelected( ) );
			}
		} );
		configurationMenu.add( itShowNPCReferences );
		JCheckBoxMenuItem itShowAtrezzoReferences = new JCheckBoxMenuItem( TextConstants.getText( "MenuConfiguration.ShowAtrezzoReferences" ), controller.getShowAtrezzoReferences( ) );
		itShowNPCReferences.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.setShowAtrezzoReferences( ( (JCheckBoxMenuItem) e.getSource( ) ).isSelected( ) );
			}
		} );
		configurationMenu.add( itShowAtrezzoReferences );
		itShowStartDialog = new JCheckBoxMenuItem( TextConstants.getText( "MenuConfiguration.ShowStartDialog" ), controller.getShowStartDialog( ) );
		itShowStartDialog.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.setShowStartDialog( ( (JCheckBoxMenuItem) e.getSource( ) ).isSelected( ) );
			}
		} );
		configurationMenu.add( itShowStartDialog );
		JCheckBoxMenuItem itEnglish = new JCheckBoxMenuItem(TextConstants.getText( "MenuConfiguration.Language.English" ), controller.getLanguage( ) == ReleaseFolders.LANGUAGE_ENGLISH);
		itEnglish.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				controller.setLanguage( ReleaseFolders.LANGUAGE_ENGLISH );
			}
		});
		JCheckBoxMenuItem itSpanish = new JCheckBoxMenuItem(TextConstants.getText( "MenuConfiguration.Language.Spanish" ), controller.getLanguage( ) == ReleaseFolders.LANGUAGE_SPANISH);
		itSpanish.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				controller.setLanguage( ReleaseFolders.LANGUAGE_SPANISH );
			}
		});
		JMenu languageMenu = new JMenu(TextConstants.getText( "MenuConfiguration.Language") );
		languageMenu.add( itEnglish );
		languageMenu.add( itSpanish );
		configurationMenu.add( languageMenu );
		
		Icon back = new ImageIcon( "img/icons/moveNodeLeft.png" );
		JMenuItem itBack = new JMenuItem(back);
		itBack.addActionListener( new ActionListener() {
			public void actionPerformed ( ActionEvent e ) {
				TreeNodeControl.getInstance().goBack();
			}
		});
		windowMenu.add(itBack);
		
		Icon forward = new ImageIcon( "img/icons/moveNodeRight.png" );
		JMenuItem itForward = new JMenuItem(forward);
		itForward.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreeNodeControl.getInstance().goForward();
			}
		});
		windowMenu.add(itForward);
		
		return windowMenu;
	}
	

	/*private JMenuBar createMenuBarAssessmentMode (){
		JMenuBar windowMenu = new JMenuBar( );
		// Create the menus
		JMenu fileMenu = new JMenu( TextConstants.getText( "MenuFile.Title" ) );
		fileMenu.setMnemonic( KeyEvent.VK_F );
		windowMenu.add( fileMenu );

		// Create the "File" elements
		JMenuItem itNewFile = new JMenuItem( TextConstants.getText( "MenuFile.NewAssessmentFile" ) );
		itNewFile.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.newFile( Controller.FILE_ASSESSMENT );
			}
		} );
		fileMenu.add( itNewFile );
		JMenuItem itFileLoad = new JMenuItem( TextConstants.getText( "MenuFile.Load" ) );
		itFileLoad.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.loadFile( );
			}
		} );
		itFileLoad.setAccelerator( KeyStroke.getKeyStroke( 'L', InputEvent.CTRL_MASK ) );
		fileMenu.add( itFileLoad );
		fileMenu.addSeparator( );
		JMenuItem itFileSave = new JMenuItem( TextConstants.getText( "MenuFile.Save" ) );
		itFileSave.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.saveFile( false );
			}
		} );
		itFileSave.setAccelerator( KeyStroke.getKeyStroke( 'S', InputEvent.CTRL_MASK ) );
		fileMenu.add( itFileSave );
		JMenuItem itFileSaveAs = new JMenuItem( TextConstants.getText( "MenuFile.SaveAs" ) );
		itFileSaveAs.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.saveFile( true );
			}
		} );
		fileMenu.add( itFileSaveAs );
		fileMenu.addSeparator( );
		JMenuItem itFileExit = new JMenuItem( TextConstants.getText( "MenuFile.Exit" ) );
		itFileExit.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				controller.exit( );
			}
		} );
		fileMenu.add( itFileExit );

		return windowMenu;
	}*/

	public void setNormalRunAvailable ( boolean available ){
		this.normalRun.setEnabled ( available );
		this.debugRun.setEnabled(available);
	}
	
	/**
	 * This method reloads the data of the window. It picks the new tree, along with the file name.
	 */
	public void reloadData( ) {
		
		// Create and place the new root
		TreeNode newRoot=null;
		if (Controller.getInstance( ).isFloderLoaded( ))
			newRoot = new ChapterTreeNode( null, controller.getSelectedChapterDataControl( ),
					controller.getAssessmentController( ), controller.getAdaptationController( ) );
		else
			newRoot = new EmptyTreeNode(null);
		
		updateChapterMenu( );
		
			
			//Update the change player mode item menu
			if( controller.isPlayTransparent( ) ) {
				itPlayerMode.setText( TextConstants.getText( "MenuAdventure.ChangeToModePlayerVisible" ) );
				itPlayerMode.setToolTipText( TextConstants.getText( "MenuAdventure.ModePlayerVisible" ) );
			}

			else {
				itPlayerMode.setText( TextConstants.getText( "MenuAdventure.ChangeToModePlayerTransparent" ) );
				itPlayerMode.setToolTipText( TextConstants.getText( "MenuAdventure.ModePlayerTransparent" ) );
			}
			//Update the Show Start Dialog item
			itShowStartDialog.setSelected( controller.getShowStartDialog( ) );


		
		this.reloadPanel( );
		treePanel.reloadTree( newRoot );
		
		// Update the menu bar
		this.setJMenuBar( createMenuBar() );
		this.getJMenuBar( ).updateUI( );

		// Update the title
		updateTitle( );
		
		this.repaint( );
		

	}

	/**
	 * Updates the title of the window.
	 */
	public void updateTitle( ) {
		String modified = controller.isDataModified( ) ? " *" : "";
		//if( controller.isTempFile( ) ) {
		//	setTitle( TextConstants.getText( "MainWindow.Title.NewFile" ) + modified );
		//} else {

			setTitle( TextConstants.getText( "MainWindow.Title", controller.getFileName( ) ) + modified );
//		}
	}

	/**
	 * Updates the chapter menu, deleting all the items and adding new ones.
	 */
	public void updateChapterMenu( ) {
		// First, delete all chapter elements (there are eight elements above the chapters in the menu)
		while( chaptersMenu.getItemCount( ) > 8 )
			chaptersMenu.remove( 8 );

		if (Controller.getInstance( ).isFloderLoaded( )){
			// Then, add the new chapters to the menu
			int chapterIndex = 0;
			ButtonGroup chapterButtonGroup = new ButtonGroup( );
			for( String chapterTitle : controller.getChapterTitles( ) ) {
				// Create the button, add the action listener and set an accelerator for the first nine chapters
				JRadioButtonMenuItem itChapter = new JRadioButtonMenuItem( ( chapterIndex + 1 ) + ": " + chapterTitle, controller.getSelectedChapter( ) == chapterIndex );
				itChapter.setEnabled( true );
				
				itChapter.addActionListener( new ChapterMenuItemListener( chapterIndex ) );
				if( chapterIndex < 9 )
					itChapter.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1 + chapterIndex++, InputEvent.CTRL_MASK ) );
	
				// Add to the chapter menu and the chapter button group
				chapterButtonGroup.add( itChapter );
				chaptersMenu.add( itChapter );
			}
		}
	}

	/**
	 * Updates the representation of the tree. It doesn't reload the nodes, just updates the view of the panel.
	 */
	public void updateTree( ) {
		treePanel.updateTreePanel( );
	}

	/**
	 * Reloads the panel selected.
	 */
	public void reloadPanel( ) {
		treePanel.loadPanel( );
	}

	/**
	 * Returns the last window opened by the application.
	 * 
	 * @return Last window opened
	 */
	public Window peekWindow( ) {
		Window window;

		// If the stack is empty, take the main window
		if( windowsStack.empty( ) )
			window = this;
		// If not, take a peek at the last window pushed
		else
			window = windowsStack.peek( );

		return window;
	}

	/**
	 * Pushes a new window in the windows stack.
	 * 
	 * @param window
	 *            Window to push
	 */
	public void pushWindow( Window window ) {
		windowsStack.push( window );
	}

	/**
	 * Pops the last window pushed into the stack.
	 */
	public void popWindow( ) {
		windowsStack.pop( );
	}

	/**
	 * Shows a load dialog to select a single existing file.
	 * 
	 * @param path
	 *            Base path for the dialog
	 * @param filter
	 *            File filter for the dialog
	 * @return Full path of the selected file, null if no file was selected
	 */
	public String showSingleSelectionLoadDialog( String path, FileFilter filter ) {
		String fileFullPath = null;

		// If no path gas given, set the path of the application
		if( path == null )
			path = ".";

		// Create the file chooser dialog
		JFileChooser fileDialog = new JFileChooser( path );
		fileDialog.setAcceptAllFileFilterUsed( false );
		fileDialog.setFileFilter( filter );

		// If a file has really been choosen and opened, load it
		if( fileDialog.showOpenDialog( peekWindow( ) ) == JFileChooser.APPROVE_OPTION ) {
			fileFullPath = fileDialog.getSelectedFile( ).getAbsolutePath( );
		}

		return fileFullPath;
	}

	/**
	 * Shows a load dialog to select multiple existing files.
	 * 
	 * @param path
	 *            Base path for the dialog
	 * @param filter
	 *            File filter for the dialog
	 * @return Full path of the selected files, null if no files were selected
	 */
	public String[] showMultipleSelectionLoadDialog( String path, FileFilter filter ) {
		String[] filesFullPath = null;

		// If no path gas given, set the path of the application
		if( path == null )
			path = ".";

		// Create the file chooser dialog
		JFileChooser fileDialog = new JFileChooser( path );
		fileDialog.setAcceptAllFileFilterUsed( false );
		fileDialog.setMultiSelectionEnabled( true );
		fileDialog.setFileFilter( filter );

		// If a file has really been choosen and opened, load it
		if( fileDialog.showOpenDialog( peekWindow( ) ) == JFileChooser.APPROVE_OPTION ) {
			// Take the selected files and initialize the array
			File[] selectedFiles = fileDialog.getSelectedFiles( );
			filesFullPath = new String[selectedFiles.length];

			// Copy the data
			for( int i = 0; i < selectedFiles.length; i++ )
				filesFullPath[i] = selectedFiles[i].getAbsolutePath( );
		}

		return filesFullPath;
	}

	/**
	 * Shows a save dialog to select a file.
	 * 
	 * @param path
	 *            Base path for the dialog
	 * @param filter
	 *            File filter for the dialog
	 * @return Full path of the selected file, null if no file was selected
	 */
	public String showSaveDialog( String path, FileFilter filter ) {
		String fileFullPath = null;

		// Create the file chooser dialog
		JFileChooser fileDialog = new JFileChooser( path );
		fileDialog.setFileFilter( filter );

		// If a file has really been choosen and opened, load it
		if( fileDialog.showSaveDialog( peekWindow( ) ) == JFileChooser.APPROVE_OPTION ) {
			fileFullPath = fileDialog.getSelectedFile( ).getAbsolutePath( );
		}

		return fileFullPath;
	}

	/**
	 * Shows a dialog with the given set of options.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 * @param options
	 *            Array of strings containing the options of the dialog
	 * @return The index of the option selected, JOptionPane.CLOSED_OPTION if the dialog was closed.
	 */
	public int showOptionDialog( String title, String message, String[] options ) {
		// Create the panel
		JPanel messagePanel = new JPanel( );
		messagePanel.setLayout( new BorderLayout( ) );

		// Create the text pane
		JTextPane messageTextPane = new JTextPane( );
		messageTextPane.setPreferredSize( new Dimension( 220, 80 ) );
		messageTextPane.setEditable( false );
		messageTextPane.setBackground( messagePanel.getBackground( ) );
		messageTextPane.setText( message );
		messagePanel.add( messageTextPane, BorderLayout.CENTER );

		// Show the dialog
		return JOptionPane.showOptionDialog( peekWindow( ), messagePanel, title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null );
	}

	/**
	 * Shows a dialog with the options "Yes", "No" and "Cancel", with the given title and text.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 * @return Value for the option selected. It can be JOptionPane.YES_OPTION, JOptionPane.NO_OPTION or
	 *         JOptionPane.CANCEL_OPTION
	 */
	public int showConfirmDialog( String title, String message ) {
		return JOptionPane.showConfirmDialog( peekWindow( ), message, title, JOptionPane.YES_NO_CANCEL_OPTION );
	}

	/**
	 * Shows a dialog with the options "Yes" and "No", with the given title and text.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 * @return True if the "Yes" button was pressed, false otherwise
	 */
	public boolean showStrictConfirmDialog( String title, String message ) {
		return JOptionPane.showConfirmDialog( peekWindow( ), message, title, JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION;
	}

	/**
	 * Shows an input dialog with the given title, message and default value.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 * @param defaultValue
	 *            Default value of the dialog
	 * @return String typed in the dialog, null if the cancel button was pressed
	 */
	public String showInputDialog( String title, String message, String defaultValue ) {
		return (String) JOptionPane.showInputDialog( peekWindow( ), message, title, JOptionPane.PLAIN_MESSAGE, null, null, defaultValue );
	}

	/**
	 * Shows an input dialog with the given title, message and set of possible values.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 * @param selectionValues
	 *            Possible selection values of the dialog
	 * @return Option selected in the dialog, null if the cancel button was pressed
	 */
	public String showInputDialog( String title, String message, Object[] selectionValues ) {
		return (String) JOptionPane.showInputDialog( peekWindow( ), message, title, JOptionPane.PLAIN_MESSAGE, null, selectionValues, null );
	}

	/**
	 * Shows an information dialog with the given title and message.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 */
	public void showInformationDialog( String title, String message ) {
		JOptionPane.showMessageDialog( peekWindow( ), message, title, JOptionPane.INFORMATION_MESSAGE );
	}

	/**
	 * Shows a warning dialog with the given title and message.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 */
	public void showWarningDialog( String title, String message ) {
		JOptionPane.showMessageDialog( peekWindow( ), message, title, JOptionPane.WARNING_MESSAGE );
	}

	/**
	 * Shows an error dialog with the given title and message.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 */
	public void showErrorDialog( String title, String message ) {
		JOptionPane.showMessageDialog( peekWindow( ), message, title, JOptionPane.ERROR_MESSAGE );
	}

	/**
	 * Listener for the chapter elements in the "Chapter" menu. Used to switch between chapters when the user desires
	 * so.
	 */
	private class ChapterMenuItemListener implements ActionListener {

		/**
		 * Index of the chapter that holds this listener.
		 */
		private int chapterIndex;

		/**
		 * Constructor.
		 * 
		 * @param chapterIndex
		 *            Chapter index of the button holding this listener
		 */
		public ChapterMenuItemListener( int chapterIndex ) {
			this.chapterIndex = chapterIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			controller.setSelectedChapter( chapterIndex );
		}
	}
}
