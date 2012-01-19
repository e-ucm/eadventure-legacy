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
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TC;
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

            super( TC.get( "Scene.LookPanelTitle" ), sDataControl );
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

            super( TC.get( "Scene.DocPanelTitle" ), sDataControl );
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

            super( TC.get( "ItemReferencesList.Title" ), sDataControl.getReferencesList( ) );
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

            super( TC.get( "BarriersList.Title" ), sDataControl.getBarriersList( ) );
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

            super( TC.get( "ActiveAreasList.Title" ), sDataControl.getActiveAreasList( ) );
            setToolTipText( TC.get( "ActiveAreasList.Information" ) );
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

            super( TC.get( "ExitsList.Title" ), sDataControl.getExitsList( ) );
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

            super( TC.get( "Trajectory.Title" ), sDataControl.getTrajectory( ) );
            setToolTipText( TC.get( "Trajectory.Title.ToolTip" ) );
            this.setHelpPath( "scenes/Scene_Trajectory.html" );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new TrajectoryPanel( sDataControl.getTrajectory( ), sDataControl );
        }
    }
}
