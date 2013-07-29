/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.IllegalComponentStateException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public abstract class EditablePanel extends JPanel {
    
    private static final long serialVersionUID = 6829039739384766833L;

    public static final int NO_SELECTED = 1;

    public static final int OVER = 2;

    public static final int SELECTED = 3;

    protected int state;

    protected int index1;

    protected ConditionsPanelController controller;

    protected Border borderSelected = new CurvedBorder( 40, Color.DARK_GRAY );

    protected Border borderOver = new CurvedBorder( 40, Color.DARK_GRAY );

    protected Border borderNone = null;

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

    public EditablePanel( ConditionsPanelController controller, int index1 ) {

        this( controller, index1, true, true );
    }

    public EditablePanel( ConditionsPanelController controller, int index1, boolean useButtons, boolean useAlphaEffect ) {

        this.controller = controller;
        this.index1 = index1;
        this.useAlphaEffect = useAlphaEffect;
        this.useButtons = useButtons;

        if( useAlphaEffect )
            // Create timer
            timer = new AlphaEffectTimer( );

        // Create buttons Panel
        if( useButtons )
            buttonsPanel = createButtonsPanel( );

        borderOver = borderSelected = new CurvedBorder( 40, Color.DARK_GRAY );
        addMouseListener( new MouseAdapter( ) {

            @Override
            public void mouseEntered( MouseEvent e ) {

                if( EditablePanel.this.state == NO_SELECTED ) {
                    EditablePanel.this.setState( OVER );
                }
            }

            private boolean isMouseInPanel( MouseEvent e ) {
                try{
                int mouseX = e.getLocationOnScreen( ).x;
                int mouseY = e.getLocationOnScreen( ).y;

                
                int w = EditablePanel.this.getWidth( );
                int h = EditablePanel.this.getHeight( );
                
                int panelX = EditablePanel.this.getLocationOnScreen( ).x;
                int panelY = EditablePanel.this.getLocationOnScreen( ).y;
                
                
                boolean coordXInPanel = mouseX >= panelX && mouseX <= panelX + w;
                boolean coordYInPanel = mouseY >= panelY && mouseY <= panelY + h;
                return coordXInPanel && coordYInPanel;
                 }catch (IllegalComponentStateException exc){
                    //This exception is thrown when EditablePanel is not in the screen at the method call (.getWidth, etc)
                     // This not blocks the app. Return false because it reflects that the mouse is not in the panel.
                     return false;
                }
            }

            @Override
            public void mouseExited( MouseEvent e ) {

                if( !isMouseInPanel( e ) && EditablePanel.this.state == OVER ) {
                    EditablePanel.this.setState( NO_SELECTED );
                }
            }

            @Override
            public void mouseClicked( MouseEvent e ) {

                if( EditablePanel.this.state == OVER ) {
                    EditablePanel.this.setState( SELECTED );
                }
            }
        } );

    }

    public void deselect( ) {

        setState( NO_SELECTED, false );
    }

    protected void setState( int newState ) {

        setState( newState, true );
    }

    protected void setState( int newState, boolean notify ) {

        boolean changed = false;
        int oldState = state;

        if( state != newState ) {
            state = newState;
            if( state == NO_SELECTED ) {
                this.setBorder( borderNone );
                this.removeAll( );
                updateTimerButtonsComponents( NO_SELECTED );
                this.updateUI( );
                changed = true;
            }
            else if( state == OVER ) {
                this.setBorder( borderOver );
                this.removeAll( );
                updateTimerButtonsComponents( OVER );
                this.updateUI( );
                changed = true;
            }
            else if( state == SELECTED ) {
                this.setBorder( borderSelected );
                this.removeAll( );
                updateTimerButtonsComponents( SELECTED );
                this.updateUI( );
                changed = true;
            }
        }

        if( changed && notify )
            controller.evalEditablePanelSelectionEvent( EditablePanel.this, oldState, newState );
    }

    private void updateTimerButtonsComponents( int newState ) {

        if( useAlphaEffect ) {
            // Check timer update
            if( newState != NO_SELECTED ) {
                timer.start( );
            }
            else {
                if( timer != null && timer.isRunning( ) )
                    timer.stop( );
            }
        }
        addComponents( newState );

        if( useButtons ) {
            // Add buttonsPanel
            add( buttonsPanel );
            buttonsPanel.setVisible( state != NO_SELECTED );
            if( state != NO_SELECTED )
                buttonsPanel.repaint( );
        }
    }

    protected abstract void addComponents( int newState );

    protected abstract ButtonsPanel createButtonsPanel( );

    /**
     * Timer that controlls the alpha effect when mouse is over the panel
     * 
     * @author Javier
     * 
     */
    public class AlphaEffectTimer extends Timer {

        /**
         * Required
         */
        private static final long serialVersionUID = 1349344660294956997L;

        private static final int INC_PERIOD = 10; //MILIS

        private static final float INCREMENT = 0.015f; // Range:0-1

        @Override
        public void start( ) {

            alpha = 0;
            alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, alpha );
            ( (CurvedBorder) borderOver ).setAlphaComposite( alphaComposite );
            repaint( );
            super.start( );
        }

        public AlphaEffectTimer( ) {

            super( INC_PERIOD, new ActionListener( ) {

                public void actionPerformed( ActionEvent e ) {

                    // Calculate new alpha
                    if( alpha <= 1.0f - INCREMENT ) {
                        alpha += INCREMENT;
                    }
                    else {
                        alpha = 1.0f;
                    }

                    // Create new alphaComposite object
                    alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, alpha );

                    // Update border
                    ( (CurvedBorder) borderOver ).setAlphaComposite( alphaComposite );

                    // Repaint all panel
                    repaint( );

                    //Adjust alpha if necessary
                    if( alpha >= 1.0f ) {
                        alpha = 1.0f;
                        alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, alpha );
                        ( (CurvedBorder) borderOver ).setAlphaComposite( alphaComposite );
                        repaint( );
                        ( (AlphaEffectTimer) e.getSource( ) ).stop( );
                    }

                    if( useButtons )
                        // Repaint the last part (buttons)
                        buttonsPanel.repaint( );

                }

            } );
        }
    }

    /**
     * Panel with nice alpha effect for buttons
     * 
     * @author Javier
     * 
     */
    protected abstract class ButtonsPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        protected abstract void createAddButtons( );

        public ButtonsPanel( ) {

            setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
            setBorder( new LeftLineBorder( ) );
            setOpaque( false );
            createAddButtons( );
            setVisible( false );
        }

        @Override
        public void paint( Graphics g ) {

            if( useAlphaEffect && alphaComposite != null )
                ( (Graphics2D) g ).setComposite( alphaComposite );
            super.paint( g );
        }
    }

}
