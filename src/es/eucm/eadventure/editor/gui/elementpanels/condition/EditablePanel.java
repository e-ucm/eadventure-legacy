package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.Border;


public abstract class EditablePanel extends JPanel {
	public static final int NO_SELECTED = 1;
	public static final int OVER = 2;
	public static final int SELECTED = 3;
	
	protected int state;
	
	protected int index1;
	
	protected ConditionsPanelController controller; 
	
	protected Border borderSelected=new CurvedBorder(40, Color.DARK_GRAY);
	protected Border borderOver=new CurvedBorder(40, Color.DARK_GRAY);
	protected Border borderNone=null;
	
	
	public EditablePanel (ConditionsPanelController controller, int index1){
		this.controller = controller;
		this.index1=index1;
		borderOver = borderSelected=new CurvedBorder(40, Color.DARK_GRAY);
		addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				if ( EditablePanel.this.state==NO_SELECTED ){
					EditablePanel.this.setState( OVER );
				}
			}
			
			private boolean isMouseInPanel(MouseEvent e){
				int mouseX = e.getLocationOnScreen().x;
				int mouseY = e.getLocationOnScreen().y;
				
				int w = EditablePanel.this.getWidth();
				int h = EditablePanel.this.getHeight();
				
				int panelX = EditablePanel.this.getLocationOnScreen().x;
				int panelY = EditablePanel.this.getLocationOnScreen().y;
				
				boolean coordXInPanel = mouseX>=panelX && mouseX<=panelX+w;
				boolean coordYInPanel = mouseY>=panelY && mouseY<=panelY+h;
				return coordXInPanel && coordYInPanel;
			}
			
			public void mouseExited(MouseEvent e){
				//boolean exit = true;
				/*for (Component component: EditablePanel.this.getComponents()){
					if (component == e.getSource()){
						exit =false;
						break;
					}
						
				}*/
				if ( !isMouseInPanel(e) && EditablePanel.this.state==OVER ){
					EditablePanel.this.setState( NO_SELECTED );
				}
			}
			
			public void mouseClicked(MouseEvent e){
				if ( EditablePanel.this.state==OVER ){
					EditablePanel.this.setState( SELECTED );
				}
			}
		});
		
	}
	
	public void deselect(){
		setState(NO_SELECTED,false);
	}
	
	protected void setState (int newState){
		setState (newState, true);
	}
	
	protected void setState (int newState, boolean notify){
		boolean changed = false;
		int oldState = state;
		
		if (state!=newState){
			state = newState;
			if (state == NO_SELECTED){
				this.setBorder(borderNone);
				this.removeAll();
				addComponents(NO_SELECTED);
				this.updateUI();
				changed = true;
			}
			else if (state == OVER){
				this.setBorder( borderOver );
				this.removeAll();
				addComponents(OVER);
				this.updateUI();
				changed = true;
			}
			else if (state == SELECTED){
				this.setBorder( borderSelected );
				this.removeAll();
				addComponents(SELECTED);
				this.updateUI();
				changed = true;
			}
		}
	
		if (changed&&notify)
			controller.evalEditablePanelSelectionEvent(EditablePanel.this, oldState, newState);
	}

	
	protected abstract void addComponents (int newState);
}
