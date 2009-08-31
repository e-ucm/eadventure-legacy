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
package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;
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
	
	/*
	 * Buttons panel
	 */
	private ButtonsPanel buttonsPanel;
	
	/*
	 * Elements for alpha effect
	 */
	protected AlphaEffectTimer timer;
	protected AlphaComposite alphaComposite;//=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
	protected float alpha = 0;
	
	protected boolean useAlphaEffect;
	protected boolean useButtons;
	
	public EditablePanel (ConditionsPanelController controller, int index1 ){
		this (controller, index1, true, true);
	}
	
	public EditablePanel (ConditionsPanelController controller, int index1, boolean useButtons, boolean useAlphaEffect){
		this.controller = controller;
		this.index1=index1;
		this.useAlphaEffect = useAlphaEffect;
		this.useButtons = useButtons;
		
		if(useAlphaEffect)
			// Create timer
			timer = new AlphaEffectTimer();
		
		// Create buttons Panel
		if (useButtons)
			buttonsPanel = createButtonsPanel();
		
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
				updateTimerButtonsComponents(NO_SELECTED);
				this.updateUI();
				changed = true;
			}
			else if (state == OVER){
				this.setBorder( borderOver );
				this.removeAll();
				updateTimerButtonsComponents(OVER);
				this.updateUI();
				changed = true;
			}
			else if (state == SELECTED){
				this.setBorder( borderSelected );
				this.removeAll();
				updateTimerButtonsComponents(SELECTED);
				this.updateUI();
				changed = true;
			}
		}
	
		if (changed&&notify)
			controller.evalEditablePanelSelectionEvent(EditablePanel.this, oldState, newState);
	}

	private void updateTimerButtonsComponents(int newState){
		
		if(useAlphaEffect){
			// Check timer update
			if (newState!=NO_SELECTED){
				timer.start();
			} else {
				if (timer!=null&&timer.isRunning())
					timer.stop();
			}
		}
		addComponents(newState);
		
		if (useButtons){
			// Add buttonsPanel
			add (buttonsPanel);
			buttonsPanel.setVisible(state!=NO_SELECTED);
			if (state!=NO_SELECTED)
				buttonsPanel.repaint();
		}
	}
	
	protected abstract void addComponents (int newState);
	
	protected abstract ButtonsPanel createButtonsPanel();
	
	/**
	 * Timer that controlls the alpha effect when mouse is over the panel
	 * @author Javier
	 *
	 */
	public class AlphaEffectTimer extends Timer {
		
		/**
		 * Required
		 */
		private static final long serialVersionUID = 1349344660294956997L;
		private static final int INC_PERIOD=10; //MILIS
		private static final float INCREMENT=0.015f; // Range:0-1

		public void start(){
			alpha = 0;
			alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			((CurvedBorder)borderOver).setAlphaComposite(alphaComposite);
			repaint();
			super.start();
		}
		
		public AlphaEffectTimer(){
			super(INC_PERIOD, new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					// Calculate new alpha
					if (alpha<=1.0f-INCREMENT){
						alpha+=INCREMENT;
					} else{
						alpha = 1.0f;
					}

					// Create new alphaComposite object
					alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
					
					// Update border
					((CurvedBorder)borderOver).setAlphaComposite(alphaComposite);
					
					// Repaint all panel
					repaint();

					//Adjust alpha if necessary
					if (alpha>=1.0f){
						alpha = 1.0f;
						alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
						((CurvedBorder)borderOver).setAlphaComposite(alphaComposite);
						repaint();
						((AlphaEffectTimer)e.getSource()).stop();
					}
					
					if (useButtons)
						// Repaint the last part (buttons)
						buttonsPanel.repaint();	
					
				}
				
			});
		}
	}		
	
	/**
	 * Panel with nice alpha effect for buttons
	 * @author Javier
	 *
	 */
	protected abstract class ButtonsPanel extends JPanel {
		
		protected abstract void createAddButtons();
		
		public ButtonsPanel(){
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			setBorder(new LeftLineBorder());
			setOpaque(false);
			createAddButtons();
			setVisible(false);
		}
		
		public void paint(Graphics g){
			if (useAlphaEffect && alphaComposite!=null)
				((Graphics2D) g).setComposite(alphaComposite);
			super.paint(g);
		}
	}

}
