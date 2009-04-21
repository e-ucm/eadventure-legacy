package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class SearchDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	private JTextField textField;
	
	private JCheckBox caseSensitive;
	
	private JCheckBox fullMatch;
	
	private JTable table;
	
	private DefaultTableModel dtm;
	
	private List<DataControl> dataControls;
	
	public SearchDialog() {
		controller = Controller.getInstance();
		this.setLayout(new BorderLayout());
		this.setTitle(TextConstants.getText("Search.DialogTitle"));
		
		JPanel inputPanel = new JPanel();
		textField = new JTextField(15);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search();
			}
		});
		inputPanel.add(textField);
		caseSensitive = new JCheckBox(TextConstants.getText("Search.CaseSensitive"));
		inputPanel.add(caseSensitive);
		fullMatch = new JCheckBox(TextConstants.getText("Search.FullMatch"));
		inputPanel.add(fullMatch);
		JButton search = new JButton(TextConstants.getText("Search.Search"));
		search.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				search();
			}
		});
		inputPanel.add(search);
		
		this.add(inputPanel, BorderLayout.NORTH);

		
		dataControls = new ArrayList<DataControl>();
		dtm = new DefaultTableModel();
		dtm.setColumnCount(2);
		String[] ids = {TextConstants.getText("Search.Where"), TextConstants.getText("Search.Places")};
		dtm.setColumnIdentifiers(ids);
		//table = new JTable(dtm);
	    table = new JTable(dtm){
	        public boolean isCellEditable(int rowIndex, int colIndex) {
	          return false; 
	        }
	      };
		JScrollPane scrollPane = new JScrollPane(table);
		ListSelectionModel listMod =  table.getSelectionModel();
		listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		listMod.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting())
				StructureControl.getInstance().changeDataControl(dataControls.get(table.getSelectedRow()));
			}
		});
		table.setFillsViewportHeight(true);


		this.add(scrollPane, BorderLayout.CENTER);
				
		
		this.setSize(460, 600);
		
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation(screenSize.width - 460 , ( screenSize.height - 600 ) / 2 );

		this.setVisible(true);
		Controller.getInstance( ).pushWindow( this );
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				Controller.getInstance( ).popWindow( );
			}
		} );
	}

	protected void search() {
		String text = textField.getText();
		if (text != null && !text.equals("")) {
			while(dtm.getRowCount() > 0) {
				dtm.removeRow(0);
			}
			dataControls.clear();
			
			boolean caseSensitive = this.caseSensitive.isSelected();
			boolean fullMatch = this.fullMatch.isSelected();
			
			HashMap<DataControl, List<String>> result = controller.getSelectedChapterDataControl().search(text, caseSensitive, fullMatch);
			
			for (DataControl dc : result.keySet()) {
				String where = "";
				for (String s : result.get(dc))
					where += (s + "|");
				if (where.length() > 1)
					where = where.substring(0, where.length() - 1);
				Object[] row = new Object[]{dc.getClass().getSimpleName().replace("DataControl", ""), where};
				dtm.addRow(row);
				dataControls.add(dc);
			}

			table.updateUI();
		}
	}	
}
