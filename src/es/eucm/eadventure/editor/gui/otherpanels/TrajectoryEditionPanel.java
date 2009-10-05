/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author L�pez Ma�as, E., P�rez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fern�ndez-Manj�n, B. (directors)
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
package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.TrajectoryScenePreviewEditionController;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.TrajectoryDataControl;

/**
 * A Panel for the edition of the trajectory and barriers, including the
 * necessary buttons.
 * 
 * @author Eugenio Marchiori
 */
public class TrajectoryEditionPanel extends JPanel {

    /**
     * Default generated serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The preview and edition panel
     */
    private ScenePreviewEditionPanel spep;

    /**
     * The mouse controller for the panel
     */
    protected TrajectoryScenePreviewEditionController tspec;

    private TrajectoryDataControl trajectoryDataControl;

    /**
     * Default constructor, with the path to the background and the
     * trajectoryDataControl
     * 
     * @param scenePath
     *            path to the background image
     * @param trajectoryDataControl
     *            the trajectoryDataControl
     */
    public TrajectoryEditionPanel( String scenePath, TrajectoryDataControl trajectoryDataControl ) {

        setLayout( new BorderLayout( ) );
        this.trajectoryDataControl = trajectoryDataControl;
        spep = new ScenePreviewEditionPanel( false, scenePath );
        tspec = new TrajectoryScenePreviewEditionController( spep, trajectoryDataControl );
        spep.changeController( tspec );
        for( NodeDataControl nodeDataControl : trajectoryDataControl.getNodes( ) )
            spep.addNode( nodeDataControl );
        spep.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_NODE, true );
        spep.setTrajectory( (Trajectory) trajectoryDataControl.getContent( ) );

        add( createButtonPanel( ), BorderLayout.NORTH );

        add( spep, BorderLayout.CENTER );
    }

    /**
     * Create the button panel
     * 
     * @return the new button panel
     */
    private JPanel createButtonPanel( ) {

        JPanel buttonPanel = new JPanel( );
        ButtonGroup group = new ButtonGroup( );
        buttonPanel.add( createToolButton( "BarriersList.EditNodes", TrajectoryScenePreviewEditionController.NODE_EDIT, "img/icons/nodeEdit.png", group ) );
        buttonPanel.add( createToolButton( "BarriersList.EditSides", TrajectoryScenePreviewEditionController.SIDE_EDIT, "img/icons/sideEdit.png", group ) );
        buttonPanel.add( createToolButton( "BarriersList.SelectInitialNode", TrajectoryScenePreviewEditionController.SELECT_INITIAL, "img/icons/selectStartNode.png", group ) );
        buttonPanel.add( createToolButton( "BarriersList.DeleteTool", TrajectoryScenePreviewEditionController.DELETE_TOOL, "img/icons/deleteTool.png", group ) );
        return buttonPanel;
    }

    /**
     * Create a tool button
     * 
     * @param text
     *            The text of the button
     * @param tool
     *            The tool asosiated with the button
     * @param group
     * @return The new button
     */
    private AbstractButton createToolButton( String text, final int tool, String iconPath, ButtonGroup group ) {

        AbstractButton button;
        ImageIcon icon = new ImageIcon( iconPath );
        button = new JToggleButton( icon );
        group.add( button );
        button.setToolTipText( TC.get( text ) );
        button.setFocusable( false );

        if( tool == TrajectoryScenePreviewEditionController.NODE_EDIT ) {
            button.setSelected( true );
        }

        button.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                tspec.setSelectedTool( tool );
            }
        } );
        return button;

    }

    /**
     * Returns the preview panel
     * 
     * @return the preview panel
     */
    public ScenePreviewEditionPanel getScenePreviewEditionPanel( ) {
        return spep;
    }

    public void update( ) {

        spep.removeElements( ScenePreviewEditionPanel.CATEGORY_NODE );
        for( NodeDataControl nodeDataControl : trajectoryDataControl.getNodes( ) )
            spep.addNode( nodeDataControl );
        spep.setTrajectory( (Trajectory) trajectoryDataControl.getContent( ) );
    }
}
