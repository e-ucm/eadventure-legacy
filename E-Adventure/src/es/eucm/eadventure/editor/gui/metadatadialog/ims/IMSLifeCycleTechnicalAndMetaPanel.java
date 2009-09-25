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
package es.eucm.eadventure.editor.gui.metadatadialog.ims;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSMetaMetaDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSTechnicalDataControl;

public class IMSLifeCycleTechnicalAndMetaPanel extends JPanel {

    private IMSLifeCycleDataControl lifeCycleController;

    private IMSTechnicalDataControl technicalController;

    public IMSLifeCycleTechnicalAndMetaPanel( IMSLifeCycleDataControl lifeCycleController, IMSTechnicalDataControl technicalController, IMSMetaMetaDataControl metaController ) {

        this.lifeCycleController = lifeCycleController;
        this.technicalController = technicalController;

        //Layout
        setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );

        //Create the panels
        IMSTextPanel versionPanel = new IMSTextPanel( lifeCycleController.getVersionController( ), TC.get( "LOM.LifeCycle.Version" ), IMSTextPanel.TYPE_FIELD );
        IMSTextPanel minVersionPanel = new IMSTextPanel( technicalController.getMinimumVersionController( ), TC.get( "LOM.Technical.MinimumVersion" ), IMSTextPanel.TYPE_FIELD, false );
        IMSTextPanel maxVersionPanel = new IMSTextPanel( technicalController.getMaximumVersionController( ), TC.get( "LOM.Technical.MaximumVersion" ), IMSTextPanel.TYPE_FIELD, false );
        IMSOptionsPanel status = new IMSOptionsPanel( lifeCycleController.getStatusController( ), TC.get( "IMS.LifeCycle.Status" ) );
        IMSTextPanel format = new IMSTextPanel( technicalController.getFormatController( ), TC.get( "IMS.Technical.Format" ), IMSTextPanel.TYPE_FIELD );
        IMSTextWithOptionsPanel location = new IMSTextWithOptionsPanel( technicalController.getLocation( ), TC.get( "IMS.Technical.Location" ), new String[] { "URI", "TEXT" } );
        IMSTextPanel meta = new IMSTextPanel( metaController.getMetadataschemeController( ), TC.get( "IMS.MetaMetaData.Metadatascheme" ), IMSTextPanel.TYPE_FIELD );

        //Add the panels
        add( versionPanel );
        add( Box.createVerticalStrut( 1 ) );
        add( status );
        add( Box.createVerticalStrut( 1 ) );
        add( meta );
        add( Box.createVerticalStrut( 1 ) );
        add( format );
        add( Box.createVerticalStrut( 1 ) );
        add( location );
        add( Box.createVerticalStrut( 1 ) );
        add( minVersionPanel );
        add( Box.createVerticalStrut( 1 ) );
        add( maxVersionPanel );
        add( Box.createRigidArea( new Dimension( 400, 45 ) ) );
        //setSize(400, 200);
    }

}
