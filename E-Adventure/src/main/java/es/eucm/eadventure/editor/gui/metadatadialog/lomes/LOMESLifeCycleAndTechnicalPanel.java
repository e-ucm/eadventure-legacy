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
package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMContributeDialog;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESTechnicalDataControl;

public class LOMESLifeCycleAndTechnicalPanel extends JPanel {

    public LOMESLifeCycleAndTechnicalPanel( LOMESLifeCycleDataControl lifeCycleController, LOMESTechnicalDataControl technicalController ) {

        //Layout
        //setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        //Create the panels
        //Lyfecyle panels
        LOMESTextPanel versionPanel = new LOMESTextPanel( lifeCycleController.getVersionController( ), TC.get( "LOM.LifeCycle.Version" ), LOMESTextPanel.TYPE_FIELD );
        LOMESOptionsPanel status = new LOMESOptionsPanel( lifeCycleController.getStatusController( ), TC.get( "IMS.LifeCycle.Status" ) );
        LOMESCreateContainerPanel contribute = new LOMESCreateContainerPanel( lifeCycleController.getContribute( ), TC.get( "LOMES.LifeCycle.Contribute" ), LOMContributeDialog.LIFECYCLE );

        //Technical panels
        LOMESCreateContainerPanel requirements = new LOMESCreateContainerPanel( technicalController.getRequirement( ), TC.get( "LOMES.Technical.Requirement" ), LOMContributeDialog.NONE );

        JPanel container = new JPanel( );
        container.setLayout( new GridBagLayout( ) );
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        //c.weighty = 1.0;
        //Add the panels
        container.add( versionPanel, c );
        //add (Box.createVerticalStrut(1));
        c.gridy = 1;
        container.add( status, c );
        //add (Box.createVerticalStrut(1));
        c.gridy = 2;
        container.add( contribute, c );
        //add (Box.createVerticalStrut(1));
        c.gridy = 3;
        container.add( requirements, c );
        //add ( Box.createRigidArea( new Dimension (400,45) ));
        //setSize(450, 200);
        //setBorder(BorderFactory.createTitledBorder("Borde"));

        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        add( container, c );

    }

}
