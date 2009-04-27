package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.tools.structurepanel.AddElementTool;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.AdaptationControllerStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.AdvancedFeaturesListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.AssessmentControllerStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.AtrezzoListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.BooksListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ChapterStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ConversationsListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.CutscenesListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ItemsListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.NPCsListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.PlayerStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ScenesListStructureElement;

public class StructurePanel extends JPanel implements DataControlsPanel {

	private static final long serialVersionUID = -1768584184321389780L;
	
	/**
	 * The container in which the edition panel will be placed.
	 */
	private Container editorContainer;
	
	private int selectedElement;
	
	private int selectedListItem = -1;
	
	private List<StructureListElement> structureElements;
	
	private JTable list;
		
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
			structureElements.add(new AdvancedFeaturesListStructureElement(Controller.getInstance().getAdvancedFeaturesController()));
			structureElements.add(new AdaptationControllerStructureElement(Controller.getInstance().getAdaptationController()));
			structureElements.add(new AssessmentControllerStructureElement(Controller.getInstance().getAssessmentController()));
		}
		update();
	}
	
	public void update() {
		int i = 0;
		removeAll();
		
		for (StructureListElement element : structureElements) {
			if (i == selectedElement)
				add(createSelectedElementPanel(element, i), new Integer(element.getChildCount() != 0 || element.getDataControl().getAddableElements().length > 0 ? -1 : 40));
			else {
				JButton button = new JButton(element.getName(), element.getIcon());
				button.setHorizontalAlignment(SwingConstants.LEFT);
				Border b1 = BorderFactory.createRaisedBevelBorder();
		        Border b2 = BorderFactory.createEmptyBorder(3, 10, 3, 10);
		        button.setBorder(BorderFactory.createCompoundBorder(b1,b2));
		        button.setContentAreaFilled(false);
				button.addActionListener(new ElementButtonActionListener(i));
				button.setFocusable(false);
				if (i < selectedElement)
					add(button, new Integer(25));
				else if (i > selectedElement)
					add(button, new Integer(25));
			} 
			i++;
		}
		this.updateUI();
	}
	
	private JPanel createSelectedElementPanel(final StructureListElement element, final int index) {
		final JPanel temp = new JPanel();
		temp.setLayout(new StructureListElementLayout());
		JButton button = new JButton(element.getName(), element.getIcon());
		button.setHorizontalAlignment(SwingConstants.LEFT);
		//Border b1 = BorderFactory.createRaisedBevelBorder();
		Border b1 = BorderFactory.createLineBorder(Color.GRAY, 3);
		Border b2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        button.setBorder(BorderFactory.createCompoundBorder(b1,b2));
        button.setContentAreaFilled(false);
		button.addActionListener(new ElementButtonActionListener(index));
		button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		button.setFocusable(false);
		
		
		temp.add(button, "title");
		
		TableModel childData = new AbstractTableModel() {
			private static final long serialVersionUID = 3895333816471270996L;
			
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
			@Override
		    public boolean isCellEditable(int row, int col) {
				return list.getSelectedRow() == row;
	        }
		};
		 
		list = new JTable(childData);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		StructureElementRenderer renderer = new StructureElementRenderer(element);
		list.getColumnModel().getColumn(0).setCellRenderer(renderer);
		list.getColumnModel().getColumn(0).setCellEditor(renderer);
		list.setCellSelectionEnabled(true);
		list.setShowHorizontalLines(true);
		list.setRowHeight(20);
		list.setTableHeader(null);
		list.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedRow() >= 0) {
					list.setRowHeight(20);
					list.setRowHeight(list.getSelectedRow(), 70);
					list.editCellAt(list.getSelectedRow(), 0);
					editorContainer.removeAll();
					editorContainer.add(((StructureElement) list.getValueAt(list.getSelectedRow(), 0)).getEditPanel());
					StructureControl.getInstance().visitDataControl(((StructureElement) list.getValueAt(list.getSelectedRow(), 0)).getDataControl());
					editorContainer.validate( );
					editorContainer.repaint( );
				} else {
					editorContainer.removeAll();
					editorContainer.add(structureElements.get(index).getEditPanel());
					StructureControl.getInstance().visitDataControl(structureElements.get(index).getDataControl());
					editorContainer.validate( );
					editorContainer.repaint( );
				}
			}
		});
		
		if (element.getDataControl().getAddableElements().length > 0) {
			JButton addButton = new JButton(new ImageIcon("img/icons/addNode.png"));
			addButton.setContentAreaFilled( false );
			addButton.setMargin( new Insets(0,0,0,0) );
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Controller.getInstance().addTool(new AddElementTool(element, list));
				}
			});
			addButton.setToolTipText(TextConstants.getText("GeneralText.AddNew"));
			temp.add(addButton, "addButton");
			temp.setComponentZOrder(addButton, 0);
			addButton.setFocusable(false);
		}

		
		JScrollPane scrollPane = new JScrollPane(list);
		temp.add(scrollPane, "list");
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
			StructureControl.getInstance().visitDataControl(structureElements.get(index).getDataControl());
			editorContainer.validate( );
			editorContainer.repaint( );
			list.requestFocusInWindow();
		}
	}
	
	public void updateElementPanel() {
		boolean temp = false;
		if (editorContainer.getComponentCount() == 1) {
			if (editorContainer.getComponent(0) instanceof Updateable) {
				temp = ((Updateable) editorContainer.getComponent(0)).updateFields();
			}
		}
		if (!temp) {
			reloadElementPanel();
		}
	}

	public void reloadElementPanel() {
		editorContainer.removeAll();
		if (list == null || list.getSelectedRow() == -1) {
			editorContainer.add(structureElements.get(selectedElement).getEditPanel());
			StructureControl.getInstance().visitDataControl(structureElements.get(selectedElement).getDataControl());
		} else {
			editorContainer.add(structureElements.get(selectedElement).getChild(list.getSelectedRow()).getEditPanel());
		}
		editorContainer.validate( );
		editorContainer.repaint( );
	}

	
	public void setSelectedItem(List<DataControl> path) {
		boolean element = true;
		while (path.size() > 0 && element) {
			element = false;
			for (int i = 0; i < structureElements.size() && !element; i++) {
				if (structureElements.get(i).getDataControl() == path.get(path.size() - 1)) {
					selectedElement = i;
					selectedListItem = -1;
					element = true;
				}
			}
			if (element)
				path.remove(path.size() - 1);
		}
		
		update();
		if (structureElements.get(selectedElement).getChildCount() > 0 && path.size() > 0) {
			element = false;
			for (int i = 0; i < structureElements.get(selectedElement).getChildCount() && !element; i++) {
				if (structureElements.get(selectedElement).getChild(i).getDataControl() == path.get(path.size() - 1)) {
					selectedListItem = i;
					path.remove(path.size() - 1);
					element = true;
				}
			}
		}
		
		editorContainer.removeAll();
		if (selectedListItem == -1) {
			editorContainer.add(structureElements.get(selectedElement).getEditPanel());
			StructureControl.getInstance().visitDataControl(structureElements.get(selectedElement).getDataControl());
		} else {
			list.changeSelection(selectedListItem, 0, false, false);
		}
		
		if (editorContainer.getComponent(0) instanceof DataControlsPanel) {
			((DataControlsPanel) editorContainer.getComponent(0)).setSelectedItem(path);
		}
		
		editorContainer.validate( );
		editorContainer.repaint( );
		list.requestFocusInWindow();
	}
	
}
