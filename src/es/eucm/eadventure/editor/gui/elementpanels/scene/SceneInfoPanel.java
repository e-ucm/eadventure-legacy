/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.gui.EAdPanel;
import es.eucm.eadventure.gui.EAdScrollPane;
import es.eucm.eadventure.gui.EAdTextField;

public class SceneInfoPanel extends EAdPanel implements Updateable {

    private static final long serialVersionUID = -6436346206278699690L;

    private SceneDataControl sceneDataControl;

    /**
     * Text area for the documentation.
     */
    private JTextArea documentationTextArea;

    /**
     * Text field for the name.
     */
    private EAdTextField nameTextField;

    public SceneInfoPanel( SceneDataControl sDataControl ) {

        super( );

        this.sceneDataControl = sDataControl;

        setLayout( new GridBagLayout( ) );
        GridBagConstraints cDoc = new GridBagConstraints( );

        cDoc.insets = new Insets( 5, 5, 5, 5 );

        // Create the text area for the documentation
        cDoc.fill = GridBagConstraints.BOTH;
        cDoc.weighty = 0.5;
        cDoc.weightx = 1;
        EAdPanel documentationPanel = new EAdPanel( );
        documentationPanel.setLayout( new GridLayout( ) );
        documentationTextArea = new JTextArea( sceneDataControl.getDocumentation( ), 4, 0 );
        documentationTextArea.setLineWrap( true );
        documentationTextArea.setWrapStyleWord( true );
        documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) sceneDataControl.getContent( ) ) );
        documentationPanel.add( new EAdScrollPane( documentationTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) );
        documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Scene.Documentation" ) ) );
        add( documentationPanel, cDoc );

        // Create the text field for the name
        cDoc.gridy = 1;
        cDoc.weighty = 0;
        cDoc.fill = GridBagConstraints.HORIZONTAL;
        EAdPanel namePanel = new EAdPanel( );
        namePanel.setLayout( new GridLayout( ) );
        nameTextField = new EAdTextField( sceneDataControl.getName( ) );
        nameTextField.getDocument( ).addDocumentListener( new NameChangeListener( nameTextField, (Named) sceneDataControl.getContent( ) ) );
        namePanel.add( nameTextField );
        namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Scene.Name" ) ) );
        add( namePanel, cDoc );

        cDoc.gridy++;
        cDoc.weightx = 1;
        cDoc.weighty = 0.5;
        add( new JFiller( ), cDoc );
    }

    public boolean updateFields( ) {

        /*if (sceneDataControl.getDocumentation()!=null && documentationTextArea.getText()!=null && 
        		!sceneDataControl.getDocumentation().equals(documentationTextArea.getText()))
        	documentationTextArea.setText( sceneDataControl.getDocumentation( ) );
        if (sceneDataControl.getName()!=null && nameTextField.getText()!=null && 
        		!sceneDataControl.getName().equals(nameTextField.getText()))
        	nameTextField.setText( sceneDataControl.getName( ) );*/
        return false;
    }

}
