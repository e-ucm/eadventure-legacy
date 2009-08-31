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

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class ScenePanel extends ElementPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param sceneDataControl
     *            Scene controller
     */
    public ScenePanel( SceneDataControl sDataControl ) {

        this.addTab( new SceneLookPanelTab( sDataControl ) );
        this.addTab( new SceneDocPanelTab( sDataControl ) );
        this.addTab( new ItemsPanelTab( sDataControl ) );
        if( !Controller.getInstance( ).isPlayTransparent( ) )
            this.addTab( new BarriersPanelTab( sDataControl ) );
        this.addTab( new ActiveAreasPanelTab( sDataControl ) );
        this.addTab( new ExitsPanelTab( sDataControl ) );
        if( !Controller.getInstance( ).isPlayTransparent( ) )
            this.addTab( new TrajectoryPanelTab( sDataControl ) );
    }

    private class SceneLookPanelTab extends PanelTab {

        private SceneDataControl sDataControl;

        public SceneLookPanelTab( SceneDataControl sDataControl ) {

            super( TextConstants.getText( "Scene.LookPanelTitle" ), sDataControl );
            this.setHelpPath( "scenes/Scene_Appearence.html" );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new SceneLooksPanel( sDataControl );
        }
    }

    private class SceneDocPanelTab extends PanelTab {

        private SceneDataControl sDataControl;

        public SceneDocPanelTab( SceneDataControl sDataControl ) {

            super( TextConstants.getText( "Scene.DocPanelTitle" ), sDataControl );
            this.setHelpPath( "scenes/Scene_Documentation.html" );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new SceneInfoPanel( sDataControl );
        }
    }

    private class ItemsPanelTab extends PanelTab {

        private SceneDataControl sDataControl;

        public ItemsPanelTab( SceneDataControl sDataControl ) {

            super( TextConstants.getText( "ItemReferencesList.Title" ), sDataControl.getReferencesList( ) );
            this.setHelpPath( "scenes/Scene_References.html" );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new ReferencesListPanel( sDataControl.getReferencesList( ) );
        }
    }

    private class BarriersPanelTab extends PanelTab {

        private SceneDataControl sDataControl;

        public BarriersPanelTab( SceneDataControl sDataControl ) {

            super( TextConstants.getText( "BarriersList.Title" ), sDataControl.getBarriersList( ) );
            this.setHelpPath( "scenes/Scene_Barriers.html" );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new BarriersListPanel( sDataControl.getBarriersList( ) );
        }
    }

    private class ActiveAreasPanelTab extends PanelTab {

        private SceneDataControl sDataControl;

        public ActiveAreasPanelTab( SceneDataControl sDataControl ) {

            super( TextConstants.getText( "ActiveAreasList.Title" ), sDataControl.getActiveAreasList( ) );
            setToolTipText( TextConstants.getText( "ActiveAreasList.Information" ) );
            this.setHelpPath( "scenes/Scene_ActiveAreas.html" );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new ActiveAreasListPanel( sDataControl.getActiveAreasList( ) );
        }
    }

    private class ExitsPanelTab extends PanelTab {

        private SceneDataControl sDataControl;

        public ExitsPanelTab( SceneDataControl sDataControl ) {

            super( TextConstants.getText( "ExitsList.Title" ), sDataControl.getExitsList( ) );
            this.setHelpPath( "scenes/Scene_Exits.html" );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new ExitsListPanel( sDataControl.getExitsList( ) );
        }
    }

    private class TrajectoryPanelTab extends PanelTab {

        private SceneDataControl sDataControl;

        public TrajectoryPanelTab( SceneDataControl sDataControl ) {

            super( TextConstants.getText( "Trajectory.Title" ), sDataControl.getTrajectory( ) );
            setToolTipText( TextConstants.getText( "Trajectory.Title.ToolTip" ) );
            this.setHelpPath( "scenes/Scene_Trajectory.html" );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new TrajectoryPanel( sDataControl.getTrajectory( ), sDataControl );
        }
    }
}
