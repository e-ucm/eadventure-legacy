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
package es.eucm.eadventure.editor.control.tools.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;

public class DocumentationChangeListener implements DocumentListener {

    private Documented documented;

    private JTextComponent textComponent;

    public DocumentationChangeListener( JTextComponent textComponent, Documented documented ) {

        this.textComponent = textComponent;
        this.documented = documented;
    }

    public void changedUpdate( DocumentEvent e ) {

        // DO NOTHING
    }

    public void insertUpdate( DocumentEvent e ) {

        Tool tool = new ChangeDocumentationTool( documented, textComponent.getText( ) );
        Controller.getInstance( ).addTool( tool );
    }

    public void removeUpdate( DocumentEvent e ) {

        Tool tool = new ChangeDocumentationTool( documented, textComponent.getText( ) );
        Controller.getInstance( ).addTool( tool );
    }

}
