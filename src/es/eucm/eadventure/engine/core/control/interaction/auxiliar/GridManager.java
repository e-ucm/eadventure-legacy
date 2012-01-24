/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.engine.core.control.interaction.auxiliar;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.Inventory;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * This class has been produced with support from GRADIANT (www.gradiant.org)
 * @contributors Iria Rodríguez Cruz, Pedro and Daniel Rodríguez (GRADIANT) 
 * @author Javier Torrente
 *
 */
public class GridManager {
    
    //private Dimension d;
    private int index;
    private int index_downs;
    private int button_index;
    private int index_invent;
    private int type;
    private int k;
    private boolean press_I; 
    private boolean i=false;
    
    private List<GridPosition> downs = new ArrayList<GridPosition>( ); 
    private boolean bDowns=false;
    private GridPosition position;
    private int buttonCount;
    
    private List<GridPosition> points = new ArrayList<GridPosition>( );
    
    private Inventory inventario;


    /**
     * Public event for handling key events. This method will basically use a a grid (finite set of (x,y) points) generated from
     * the layout of elements (NPCs, Items, Exits, ActiveAreas + arrows for scroll) to navigate the interactive elements with the
     * keyboard
     * @param e The key event to handle. Codes that will be listened to: VK_UP, VK_RIGHT, VK_LEFT, VK_DOWN, ENTER, ESC, I, numbers 0-9
     */
    public void handleKeyEvent( KeyEvent e ) {
      //Obtenemos los puntos correspondientes a cada escena
        points = Game.getInstance( ).getFunctionalScene( ).getGrid( ); 
        
        //Obtenemos los puntos de los objetos del inventario
        inventario = new Inventory();
        inventario = Game.getInstance( ).getInventory( );
        int aux=70;
        for (int i=0; i < inventario.getItemCount( );i++){
            if (i>0)
                aux=aux+inventario.getItem( i-1  ).getIconImage( ).getWidth( null )/2; 
            
            inventario.getItem( i ).setX(aux + inventario.getItem( i ).getIconImage( ).getWidth( null )/2);
            inventario.getItem( i ).setY(GUI.getInstance( ).getGameAreaHeight( )-(inventario.getItem( i ).getIconImage( ).getHeight( null )/2));
            
            aux=(int)inventario.getItem(i).getX( );
        }
     
        switch( e.getKeyCode( ) ) {
            case KeyEvent.VK_ESCAPE:
                handleEscapeEvent(e);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
                handleEventForBrowsingLeftOrRight(false);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_DOWN:
                handleEventForBrowsingLeftOrRight(true);
                break;
            case KeyEvent.VK_ENTER:
                handleEventForActionButton();
                break;
            case KeyEvent.VK_I:  
                handleEventForShowingInventory();
                break;
            case KeyEvent.VK_1:  
            case KeyEvent.VK_2:  
            case KeyEvent.VK_3:  
            case KeyEvent.VK_4: 
            case KeyEvent.VK_5:  
            case KeyEvent.VK_6:  
            case KeyEvent.VK_7:  
            case KeyEvent.VK_8:  
            case KeyEvent.VK_9:
                handleEventsForNumbers ( e.getKeyCode( ) );
                break;
        }
    }

    
    /////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////
    
    
    private void handleEscapeEvent(KeyEvent e){
        if (bDowns) {
            index_downs=GUI.getInstance( ).getButtonCount( );
            GridPosition down1 = new GridPosition("",0,0,"","");
            down1.setX( GUI.getInstance( ).getButtonX( 0 )-GUI.getInstance( ).getButtonWidth( 0 ),0);
            down1.setY(GUI.getInstance( ).getButtonY( 0 ),0);
            down1.setType( "Out" );
            downs.add (index_downs,down1);
            loadCursorLeft(downs.get( index_downs ),true);  
            bDowns=false;
            if (i)
                press_I=true;
            type=1;
        } else {
            if( !GUI.getInstance( ).keyInHud( e ) )
                Game.getInstance( ).setState( Game.STATE_OPTIONS );
        }
        
    }
    
    /**
     * 
     * @param left True if browsing left, false for browsing right
     */
    private void handleEventForBrowsingLeftOrRight(boolean left){
        if (press_I) { 
            if(inventario.getItemCount( )>0){
                if (left){
                    index_invent--;
                    if (index_invent<0)
                        index_invent = inventario.getItemCount()-1;
                } else {
                    index_invent++;
                    if (index_invent>=inventario.getItemCount())
                        index_invent = 0;
                }
            
                position = new GridPosition("",0,0,"","");
                position.setX( (int) inventario.getItem( index_invent ).getX( ),0);
                position.setY( (int) inventario.getItem( index_invent ).getY( ), 0);
    
                loadCursorLeft(position, false);
            }
        } else {   
            if (bDowns){
                findButtons (button_index);
                index_downs = button_index;
                
                if(left){
                    if (button_index==0) button_index = buttonCount -1;
                    else button_index--;  
                } else {
                    if (button_index==buttonCount-1) button_index = 0;
                    else button_index++;
                }
            } else {
                if (left){
                    index--;
                    if (index<0)              
                        index = points.size( ) - 1;    
                } else {
                    index++;
                    if (index >= points.size())
                        index = 0;
                }
                
                if (points.get( index).getType( ).equals("Exit")){          
                    loadCursorLeft(points.get( index ), false);
                } else {                   
                    loadCursorRight(points.get( index ), false);
                }
                type = 1;
            }
        }
    }

    
    private void handleEventForActionButton(){
        if (press_I){
            loadCursorRight(position, true); 
            bDowns=true;
            type=2;
            press_I=false;
        } else {
            boolean ok=false;
            if (type == 0){
                for( k = 0; k < points.size( ); k++ ) {
                    if ((points.get( k ).getX( ) == Game.getInstance( ).getLastMouseEvent( ).getX( )) && 
                            (points.get( k ).getY( ) == Game.getInstance( ).getLastMouseEvent( ).getY( ))) {
                        ok=true;
                        break;
                    }
                    if (ok) index=k;
                }
                
                loadCursorLeft(points.get( index ), false);
                type=1;
                
            }
        
            if (type == 1) {
                if (points.get( index ).getType( ).equals("Exit")){
                    loadCursorLeft(points.get( index ), true);
                    type=0;
                } else {
                    loadCursorRight(points.get( index ), true); 
                    bDowns=true;
                    type=2;
                }  
            } else { 
                if (type==2) {
                    if (index_downs<=1){ //Si no estamos en el centro
                        loadCursorLeft(downs.get( index_downs ), true);
                        index_downs=2;         
                        loadCursorLeft(downs.get( index_downs ),false); //Para que vuelva al centro
                    } else {  // Si estamos en el centro
                        loadCursorLeft(downs.get( index_downs ),true);  
                    }
                
                    boolean run=false;
                    for( k = 0; k < points.size( ); k++ ) {
                        if ((points.get( k ).getX( ) == downs.get( 2 ).getX( )) && (points.get( k ).getY( ) == downs.get( 2 ).getY( ))) {
                            run=true;
                            break;   
                        }   
                    }
               
                    if ((run)&&(!press_I)) {
                        index=k;
                        loadCursorLeft(points.get( index ),false);
                    }
                    type=1;
                    bDowns=false;

                }
            }
        }
    }
    
    private void handleEventForShowingInventory(){
        i=!i;
        
        if (!press_I) {
            position = new GridPosition("",0,0,"","");
            if (inventario.getItemCount( )>0) {
                position.setX( (int) inventario.getItem( 0 ).getX( ),0);
                position.setY( (int) inventario.getItem( 0 ).getY( ), 0);
            } else {
                position.setX( GUI.getInstance( ).getGameAreaWidth( )/2,0);
                position.setY( GUI.getInstance( ).getGameAreaHeight( ), position.getInvert( 48 ) );
            }
            loadCursorLeft(position,false); 
            press_I=true;
        } else {
            loadCursorLeft(points.get( index ),false);
            press_I=false;
        }
        
    }
    
    private void handleEventsForNumbers( int keyCode ){
        int numberPressed = keyCode-KeyEvent.VK_0;
        if ((inventario.getItemCount( )>=numberPressed) && press_I){
            index_invent=numberPressed-1;
            position = new GridPosition("",0,0,"","");
            position.setX( (int) inventario.getItem( index_invent ).getX( ), 0);
            position.setY( (int) inventario.getItem( index_invent ).getY( ), 0);

            loadCursorRight(position, true);
            bDowns=true;
            type=2;
            press_I=false;
        }
        
    }
    
    private void findButtons (int button_index){
        if (button_index>=0){
            buttonCount = GUI.getInstance( ).getButtonCount( );
            GridPosition down1 = new GridPosition("",0,0,"","");
            down1.setX( GUI.getInstance( ).getButtonX( button_index ),0);
            down1.setY(GUI.getInstance( ).getButtonY( button_index ),0);
            down1.setType( "Right" );
            downs.add (button_index,down1);
            loadCursorLeft(downs.get( button_index ), false);
        }
    }
    private void loadCursorLeft ( GridPosition point, boolean click){
        MouseUtils.move( point, MouseEvent.BUTTON1_MASK, click );
    }
    private void loadCursorRight ( GridPosition point, boolean click) {
        MouseUtils.move( point, MouseEvent.BUTTON3_MASK, click );
        if (click) {
            calculaDowns(point);
            button_index = 0;//Para que quede en el centro
        }
        
    }
    private void calculaDowns(GridPosition point){
        int i=0;
        
        //Si tiene algo y el centro no coincide con el que le pasamos, vaciamos la lista
        if (!downs.isEmpty( )){
            if ((downs.get( 2 ).getX( )!=point.getX( ))||(downs.get( 2 ).getY( )!=point.getY( ))){
                downs.clear( );
            }
        }
        
        if (point.getX( )>=70){
            
 
           GridPosition down1 = new GridPosition("",0,0,"","");
            down1.setX( point.getX()-45,0);
            down1.setY(point.getY( ),0);
            down1.setType( "Left" );
            i=0;
            downs.add (i,down1);
            
            GridPosition down2 = new GridPosition("",0,0,"","");
            down2.setX( point.getX()+30,0);
            down2.setY(point.getY( ),0);
            down2.setType("Right");
            i=1;
            downs.add (i,down2);   
            
            GridPosition down = new GridPosition("",0,0,"","");
            down.setX(point.getX( ),0);;
            down.setY(point.getY( ),0);
            down.setType( "Center" );
            i=2;
            downs.add( i,down );
            
        } else  {
            
            
            GridPosition down1 = new GridPosition("",0,0,"","");
            down1.setY(point.getY()+40,0);
            down1.setX(point.getX()-10,0);
            down1.setType( "Down" );
            i=0;
            downs.add (i,down1);
            
            GridPosition down2 = new GridPosition("",0,0,"","");
            down2.setY(point.getY()-40,0);
            down2.setX(point.getX()-10,0);
            down2.setType("Up");
            i=1;
            downs.add (i,down2);
            
            GridPosition down = new GridPosition("",0,0,"","");
            down.setX(point.getX( ),0);;
            down.setY(point.getY( ),0);
            down.setType( "Center" );
            i=2;
            downs.add( i, down );
        }
        
    }

    /*********************************************************************
     * PEDRO. Fin 
     *********************************************************************/    

}
