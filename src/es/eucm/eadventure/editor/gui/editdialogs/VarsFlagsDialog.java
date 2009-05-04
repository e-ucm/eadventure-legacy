package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.VarFlagsController;
import es.eucm.eadventure.editor.gui.elementpanels.condition.ConditionsPanelOld;

/**
 * This class is the editing dialog of the flags. Here the user can add new flags to use them in the script. Also, the
 * flags can be deleted.
 * 
 * @author Bruno Torijano Bueno
 */
public class VarsFlagsDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Path of .html file with help for flags
	 */
	private static final String flagsHelpPath="";

	/**
	 * Path of .html file with help for vars
	 */
	private static final String varsHelpPath="";

	
	/**
	 * Controller for the flags.
	 */
	private VarFlagsController varFlagsController;

	/**
	 * Table holding the flags.
	 */
	private JTable flagsTable;
	
	/**
	 * Table holding the vars.
	 */
	private JTable varsTable;
	
	/**
	 * Constructor.
	 * 
	 * @param flagController
	 *            Controller for the flags
	 */
	public VarsFlagsDialog( VarFlagsController flagController ) {

		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "Ids.Title" ), Dialog.ModalityType.TOOLKIT_MODAL);

		// Push the dialog into the stack, and add the window listener to pop in when closing
		Controller.getInstance( ).pushWindow( this );
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				Controller.getInstance( ).popWindow( );
			}
		} );

		// Set the flags controller
		this.varFlagsController = flagController;

		/////////////////////////////////////////
		// FLAGS PANEL (TAB)
		/////////////////////////////////////////
		// Create a container panel, and set the properties
		JPanel flagsMainPanel = new JPanel( );
		flagsMainPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Flags.Title" ) ) );
		flagsMainPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 10, 5, 10 );

		// Create the table and add it
		flagsTable = new JTable( new FlagsTableModel( ) );
		flagsTable.getColumnModel( ).getColumn( 1 ).setMaxWidth( 60 );
		flagsTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		flagsMainPanel.add( new JScrollPane( flagsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), c );

		// Add an "Add flag" button
		JButton addFlag = new JButton( TextConstants.getText( "Flags.AddFlag" ) );
		addFlag.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				if( varFlagsController.addVarFlag( true ) )
					flagsTable.updateUI( );
			}
		} );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		c.gridy = 1;
		flagsMainPanel.add( addFlag, c );

		// Add an "Delete flag" button
		JButton deleteFlag = new JButton( TextConstants.getText( "Flags.DeleteFlag" ) );
		deleteFlag.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				if( flagsTable.getSelectedRow( ) >= 0 )
					if( varFlagsController.deleteFlag( flagsTable.getSelectedRow( ) ) )
						flagsTable.updateUI( );
			}
		} );
		c.gridy = 2;
		flagsMainPanel.add( deleteFlag, c );

		/////////////////////////////////////////
		// VARS PANEL (TAB)
		/////////////////////////////////////////
		// Create a container panel, and set the properties
		JPanel varsMainPanel = new JPanel( );
		varsMainPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Vars.Title" ) ) );
		varsMainPanel.setLayout( new GridBagLayout( ) );
		c = new GridBagConstraints( );
		c.insets = new Insets( 5, 10, 5, 10 );

		// Create the table and add it
		varsTable = new JTable( new VarsTableModel( ) );
		varsTable.getColumnModel( ).getColumn( 1 ).setMaxWidth( 60 );
		varsTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		varsMainPanel.add( new JScrollPane( varsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), c );

		// Add an "Add var" button
		JButton addVar = new JButton( TextConstants.getText( "Vars.AddVar" ) );
		addVar.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				if( varFlagsController.addVarFlag( false ) )
					varsTable.updateUI( );
			}
		} );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		c.gridy = 1;
		varsMainPanel.add( addVar, c );

		// Add an "Delete var" button
		JButton deleteVar = new JButton( TextConstants.getText( "Vars.DeleteVar" ) );
		deleteVar.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				if( varsTable.getSelectedRow( ) >= 0 )
					if( varFlagsController.deleteVar( varsTable.getSelectedRow( ) ) )
						varsTable.updateUI( );
			}
		} );
		c.gridy = 2;
		varsMainPanel.add( deleteVar, c );

		/////////////////////////////////////////
		// CREATE TABBED PANE
		/////////////////////////////////////////
		JTabbedPane mainPanel = new JTabbedPane();
		mainPanel.addTab(TextConstants.getText( "Flags.Flag" ), 
				null, flagsMainPanel, TextConstants.getText( "Flags.FlagTip" ));
		mainPanel.addTab(TextConstants.getText( "Vars.Var" ), 
				null, varsMainPanel, TextConstants.getText( "Vars.VarTip" ));
		
		JPanel flagsTabComponent = new JPanel();
		flagsTabComponent.setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.BOTH;
		c1.weightx = 1;
		c1.weighty = 1;
		JLabel flagsTitle = new JLabel( TextConstants.getText( "Flags.Flag" ) );
		flagsTitle.setHorizontalTextPosition(SwingConstants.CENTER);
		flagsTitle.setAlignmentX(0.5f);
		flagsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon flagsIcon = new ImageIcon ("img/icons/flag16.png");
		flagsTabComponent.add(new JLabel( flagsIcon ), c1 );
		c1.gridx++;
		flagsTabComponent.add(flagsTitle, c1 );
		flagsTabComponent.setBackground( ConditionsPanelOld.FLAG_COLOR );
		mainPanel.setTabComponentAt(0, flagsTabComponent);
		
		// button for html help for flags
		JButton flagsInfoButton = new JButton(new ImageIcon("img/icons/information.png"));
		flagsInfoButton.setContentAreaFilled( false );
		flagsInfoButton.setMargin( new Insets(0,0,0,0) );
		flagsInfoButton.setFocusable(false);
		flagsInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HelpDialog(flagsHelpPath);
			}
		});
		c1.gridx++;
		flagsTabComponent.add(flagsInfoButton, c1 );
		

		JPanel varsTabComponent = new JPanel();
		varsTabComponent.setLayout(new GridBagLayout());
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.BOTH;
		c2.weightx = 1;
		c2.weighty = 1;
		JLabel varsTitle = new JLabel( TextConstants.getText( "Vars.Var" ) );
		varsTitle.setHorizontalTextPosition(SwingConstants.CENTER);
		varsTitle.setAlignmentX(0.5f);
		varsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon varsIcon = new ImageIcon ("img/icons/var16.png");
		varsTabComponent.add(new JLabel( varsIcon ), c2 );
		c2.gridx ++;
		varsTabComponent.add(varsTitle, c2 );
		varsTabComponent.setBackground( ConditionsPanelOld.VAR_COLOR );
		mainPanel.setTabComponentAt(1, varsTabComponent);
		
		// button for html help for vars
		JButton varsInfoButton = new JButton(new ImageIcon("img/icons/information.png"));
		varsInfoButton.setContentAreaFilled( false );
		varsInfoButton.setMargin( new Insets(0,0,0,0) );
		varsInfoButton.setFocusable(false);
		varsInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HelpDialog(varsHelpPath);
			}
		});
		c2.gridx++;
		varsTabComponent.add(varsInfoButton, c2 );
		
		
		// Add the panel
		setLayout( new GridBagLayout( ) );
		c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add( mainPanel, c );

		
		// Set the size, position and properties of the dialog
		setResizable( false );
		setSize( 500, 400 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}

	/**
	 * Table model to display the flags.
	 */
	private class FlagsTableModel extends AbstractTableModel {

		/**
		 * Required.
		 */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount( ) {
			// Two columns, always
			return 2;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount( ) {
			return varFlagsController.getFlagCount( );
		}

		@Override
		public String getColumnName( int columnIndex ) {
			String columnName = "";

			// The first column is the name
			if( columnIndex == 0 )
				columnName = TextConstants.getText( "Flags.FlagName" );

			// The second one the references number
			else if( columnIndex == 1 )
				columnName = TextConstants.getText( "Flags.FlagReferences" );

			return columnName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt( int rowIndex, int columnIndex ) {
			Object value = null;

			// The first column has the name
			if( columnIndex == 0 )
				value = varFlagsController.getFlag( rowIndex );

			// The second one the references number
			else if( columnIndex == 1 )
				value = varFlagsController.getFlagReferences( rowIndex );

			return value;
		}
	}
	
	/**
	 * Table model to display the vars.
	 */
	private class VarsTableModel extends AbstractTableModel {

		/**
		 * Required.
		 */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount( ) {
			// Two columns, always
			return 2;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount( ) {
			return varFlagsController.getVarCount( );
		}

		@Override
		public String getColumnName( int columnIndex ) {
			String columnName = "";

			// The first column is the name
			if( columnIndex == 0 )
				columnName = TextConstants.getText( "Vars.VarName" );

			// The second one the references number
			else if( columnIndex == 1 )
				columnName = TextConstants.getText( "Vars.VarReferences" );

			return columnName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt( int rowIndex, int columnIndex ) {
			Object value = null;

			// The first column has the name
			if( columnIndex == 0 )
				value = varFlagsController.getVar( rowIndex );

			// The second one the references number
			else if( columnIndex == 1 )
				value = varFlagsController.getVarReferences( rowIndex );

			return value;
		}
	}
}
