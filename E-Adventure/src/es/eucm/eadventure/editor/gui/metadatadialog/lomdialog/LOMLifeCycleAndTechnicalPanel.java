/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.gui.metadatadialog.lomdialog;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.config.LOMConfigData;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMGeneralDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMTechnicalDataControl;

public class LOMLifeCycleAndTechnicalPanel extends JPanel{

	private LOMLifeCycleDataControl lifeCycleController;
	
	private LOMTechnicalDataControl technicalController;
	
	public LOMLifeCycleAndTechnicalPanel (LOMLifeCycleDataControl lifeCycleController, LOMTechnicalDataControl technicalController){
		this.lifeCycleController = lifeCycleController;
		this.technicalController = technicalController;
		
		//Layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Create the panels
		LOMTextPanel versionPanel = new LOMTextPanel(lifeCycleController.getVersionController( ), TextConstants.getText("LOM.LifeCycle.Version"), LOMTextPanel.TYPE_FIELD);
		LOMTextPanel minVersionPanel = new LOMTextPanel(technicalController.getMinimumVersionController( ), TextConstants.getText("LOM.Technical.MinimumVersion"), LOMTextPanel.TYPE_FIELD, false);
		LOMTextPanel maxVersionPanel = new LOMTextPanel(technicalController.getMaximumVersionController( ), TextConstants.getText("LOM.Technical.MaximumVersion"), LOMTextPanel.TYPE_FIELD, false);
	
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
		add (versionPanel);
		add (Box.createVerticalStrut(5));
		add (minVersionPanel);
		add (Box.createVerticalStrut(5));
		add (maxVersionPanel);
		add ( Box.createRigidArea( new Dimension (400,250) ));
	}
	
	
}
