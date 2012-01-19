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
package es.eucm.eadventure.editor.gui.metadatadialog.lomdialog;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMTechnicalDataControl;

public class LOMLifeCycleAndTechnicalPanel extends JPanel {

    private LOMLifeCycleDataControl lifeCycleController;

    private LOMTechnicalDataControl technicalController;

    public LOMLifeCycleAndTechnicalPanel( LOMLifeCycleDataControl lifeCycleController, LOMTechnicalDataControl technicalController ) {

        this.lifeCycleController = lifeCycleController;
        this.technicalController = technicalController;

        //Layout
        setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );

        //Create the panels
        LOMTextPanel versionPanel = new LOMTextPanel( lifeCycleController.getVersionController( ), TC.get( "LOM.LifeCycle.Version" ), LOMTextPanel.TYPE_FIELD );
        LOMTextPanel minVersionPanel = new LOMTextPanel( technicalController.getMinimumVersionController( ), TC.get( "LOM.Technical.MinimumVersion" ), LOMTextPanel.TYPE_FIELD, false );
        LOMTextPanel maxVersionPanel = new LOMTextPanel( technicalController.getMaximumVersionController( ), TC.get( "LOM.Technical.MaximumVersion" ), LOMTextPanel.TYPE_FIELD, false );

        // check if there are related stored data in config file
        /*if (LOMConfigData.isStored(LOMLifeCycleDataControl.GROUP, "version")){
        	versionPanel.setValue(LOMConfigData.getProperty(LOMLifeCycleDataControl.GROUP, "version"));
        }
        if (LOMConfigData.isStored(LOMLifeCycleDataControl.GROUP, "minimumVersion")){
        	minVersionPanel.setValue(LOMConfigData.getProperty(LOMTechnicalDataControl.GROUP, "minimumVersion"));
        }
        if (LOMConfigData.isStored(LOMLifeCycleDataControl.GROUP, "maximumVersion")){
        	maxVersionPanel.setValue(LOMConfigData.getProperty(LOMTechnicalDataControl.GROUP, "maximumVersion"));
        }*/

        //Add the panels
        add( versionPanel );
        add( Box.createVerticalStrut( 5 ) );
        add( minVersionPanel );
        add( Box.createVerticalStrut( 5 ) );
        add( maxVersionPanel );
        add( Box.createRigidArea( new Dimension( 400, 250 ) ) );
    }

}
