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
package es.eucm.eadventure.gamelog.pub;


public interface _HighLevelEvents {

    public static final String USE="use";
    public static final String GRAB="grb";
    public static final String TALK="tlk";
    public static final String EXAMINE="exa";
    public static final String LOOK="loo";
    public static final String EXIT_CLICK="exc";
    public static final String CUSTOM="cus";
    public static final String GO_TO="g2";
    
    public static final String START_CUSTOM_INTERACTION="sci";
    public static final String END_CUSTOM_INTERACTION="eci";
    public static final String START_USE_WITH="suw";
    public static final String END_USE_WITH="euw";
    public static final String START_GIVE_TO="sgt";
    public static final String END_GIVE_TO="egt";
    public static final String START_DRAG_TO="sdt";
    public static final String END_DRAG_TO="edt";
    
    public static final String BOOK_ENTER="bin";
    public static final String BOOK_EXIT="bout";
    public static final String BOOK_BROWSE_PREVPAGE="bpr";
    public static final String BOOK_BROWSE_NEXTPAGE="bnxt";
    
    public static final String SHOW_ACTIONS="act";
    public static final String NEW_SCENE="scn";
    
    public static final String OFFSET_ARROW_RIGHT="oar";
    public static final String OFFSET_ARROW_LEFT="oal";
    
    public static final String SLIDESCENE_ENTER="sin";
    public static final String SLIDESCENE_EXIT="sout";
    public static final String SLIDESCENE_NEXT="snxt";
    
    public static final String VIDEOSCENE_ENTER="vin";
    public static final String VIDEOSCENE_EXIT="vout";
    public static final String VIDEOSCENE_SKIP="vnxt";

    public static final String CONV_ENTER="cin";
    public static final String CONV_EXIT="cout";
    public static final String CONV_SKIP_LINE="cnxt";
    public static final String CONV_SKIP_NODE="cnde";
    public static final String CONV_SELECT_OPTION="copt";
    
    public static final String MENU_OPEN="min";
    public static final String MENU_CLOSE="mout";
    public static final String MENU_BROWSE="mbws";
    public static final String EXIT_GAME="exg";
}
