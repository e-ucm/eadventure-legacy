package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.AdvancedFeaturesDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.AdvancedFeaturesListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.AtrezzoListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.BooksListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ChapterStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ConversationsListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.CutscenesListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ItemsListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.NPCsListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.PlayerStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ScenesListStructureElement;

public class StructurePanel extends JPanel {

	private static final long serialVersionUID = -1768584184321389780L;
	
	/**
	 * The container in which the edition panel will be placed.
	 */
	private Container editorContainer;
	
	private int selectedElement;
	
	private List<StructureListElement> structureElements;
	
	public StructurePanel(Container editorContainer) {
		this.editorContainer = editorContainer;
		this.selectedElement = 0;
		this.setLayout(new StructurePanelLayout());
		structureElements = new ArrayList<StructureListElement>();
		update();
	}
	
	public void recreateElements() {
		ChapterDataControl chapterDataControl = Controller.getInstance().getSelectedChapterDataControl( );
		structureElements.clear();
		if (chapterDataControl != null) {
			structureElements.add(new ChapterStructureElement(chapterDataControl));
			structureElements.add(new ScenesListStructureElement(chapterDataControl.getScenesList()));
			structureElements.add(new CutscenesListStructureElement(chapterDataControl.getCutscenesList()));
			structureElements.add(new BooksListStructureElement(chapterDataControl.getBooksList()));
			structureElements.add(new ItemsListStructureElement(chapterDataControl.getItemsList()));
			structureElements.add(new AtrezzoListStructureElement(chapterDataControl.getAtrezzoList()));
			structureElements.add(new PlayerStructureElement(chapterDataControl.getPlayer()));
			structureElements.add(new NPCsListStructureElement(chapterDataControl.getNPCsList()));
			structureElements.add(new ConversationsListStructureElement(chapterDataControl.getConversationsList()));
			AdvancedFeaturesDataControl advancedFeaturesDataControl = new AdvancedFeaturesDataControl();
			advancedFeaturesDataControl.setTimerListDataControl(chapterDataControl.getTimersList());
			advancedFeaturesDataControl.setAdaptationProfilesDataControl(Controller.getInstance().getAdaptationController());
			advancedFeaturesDataControl.setAssessmentProfilesDataControl(Controller.getInstance().getAssessmentController());
			advancedFeaturesDataControl.setGlobalStatesListDataContorl(chapterDataControl.getGlobalStatesListDataControl());
			advancedFeaturesDataControl.setMacrosListDataControl(chapterDataControl.getMacrosListDataControl());
			structureElements.add(new AdvancedFeaturesListStructureElement(advancedFeaturesDataControl));
		}
		update();
	}
	
	public void update() {
		int i = 0;
		removeAll();
		
		for (StructureListElement element : structureElements) {
			if (i == selectedElement)
				add(createSelectedElementPanel(element, i), new Boolean(element.getChildCount() != 0));
			else {
				JButton button = new JButton(element.getName(), element.getIcon());
				button.setHorizontalAlignment(SwingConstants.LEFT);
				Border b1 = BorderFactory.createRaisedBevelBorder();
		        Border b2 = BorderFactory.createEmptyBorder(3, 10, 3, 10);
		        button.setBorder(BorderFactory.createCompoundBorder(b1,b2));
		        button.setContentAreaFilled(false);
				button.addActionListener(new ElementButtonActionListener(i));
				if (i < selectedElement)
					add(button, new Boolean(false));
				else if (i > selectedElement)
					add(button, new Boolean(false));
			} 
			i++;
		}
		this.updateUI();
	}
	
	private JPanel createSelectedElementPanel(final StructureListElement element, int index) {
		JPanel temp = new JPanel();
		temp.setLayout(new GridBagLayout());
		JButton button = new JButton(element.getName(), element.getIcon());
		button.setHorizontalAlignment(SwingConstants.LEFT);
		//Border b1 = BorderFactory.createRaisedBevelBorder();
		Border b1 = BorderFactory.createLineBorder(Color.GRAY, 3);
		Border b2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        button.setBorder(BorderFactory.createCompoundBorder(b1,b2));
        button.setContentAreaFilled(false);
		button.addActionListener(new ElementButtonActionListener(index));
		button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 2.0;
		temp.add(button, c);
		
		
		 TableModel childData = new AbstractTableModel() {
			@Override
			public int getColumnCount() {
				return 1;
			}
			@Override
			public int getRowCount() {
				return element.getChildCount();
			}
			@Override
			public Object getValueAt(int arg0, int arg1) {
				return element.getChild(arg0);
			}
		 };
		 
		final JTable list = new JTable(childData);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.getColumnModel().getColumn(0).setCellRenderer(new StructureElementRenderer());
		list.setShowHorizontalLines(true);
		list.setRowHeight(20);
		list.setTableHeader(null);
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				list.setRowHeight(20);
				list.setRowHeight(list.getSelectedRow(), 70);
				editorContainer.removeAll();
				editorContainer.add(((StructureElement) list.getValueAt(list.getSelectedRow(), 0)).getEditPanel());
				editorContainer.validate( );
				editorContainer.repaint( );
			}
		});
		c.fill = GridBagConstraints.BOTH;
		c.gridy++;
		c.weighty = 2.0;
		JScrollPane scrollPane = new JScrollPane(list);
		temp.add(scrollPane, c);
		return temp;		
	}

	private class ElementButtonActionListener implements ActionListener {
		private int index;
		
		public ElementButtonActionListener(int index) {
			this.index = index;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			selectedElement = index;
			update();
			editorContainer.removeAll();
			editorContainer.add(structureElements.get(index).getEditPanel());
			editorContainer.validate( );
			editorContainer.repaint( );
		}
	}
	
}
