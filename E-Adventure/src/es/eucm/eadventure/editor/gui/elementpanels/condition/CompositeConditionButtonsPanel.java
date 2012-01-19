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
package es.eucm.eadventure.editor.gui.elementpanels.condition;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.controllers.ConditionsController;

public class CompositeConditionButtonsPanel extends JPanel {

    public static final int HORIZONTAL = 0;

    public static final int VERTICAL = 1;

    private ConditionsController controller;

    private int layout;

    private JButton editButton;

    private JButton addButton;

    private JButton deleteButton;

    private JButton moveLeftUpButton;

    private JButton moveRightDownButton;

    public CompositeConditionButtonsPanel( ConditionsController controller, int layout ) {

        this.controller = controller;
        this.layout = layout;
        if( layout == HORIZONTAL ) {
            setLayout( new BoxLayout( this, BoxLayout.LINE_AXIS ) );
            moveLeftUpButton = new JButton( "img/icons/moveNodeLeft.png" );
            moveRightDownButton = new JButton( "img/icons/moveNodeRight.png" );
        }
        else if( layout == VERTICAL ) {
            setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
            moveLeftUpButton = new JButton( "img/icons/moveNodeUp.png" );
            moveRightDownButton = new JButton( "img/icons/moveNodeDown.png" );
        }
        editButton = new JButton( "img/icons/edit.png" );
        addButton = new JButton( "img/icons/addNode.png" );
        deleteButton = new JButton( "img/icons/deleteNode.png" );
        add( editButton );
        add( addButton );
        add( deleteButton );
        add( moveLeftUpButton );
        add( moveRightDownButton );
    }

}
