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
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSClassificationDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSRightsDataControl;

public class IMSRightsAndClassificationPanel extends JPanel {

    public IMSRightsAndClassificationPanel( IMSRightsDataControl rightsController, IMSClassificationDataControl classificationController ) {

        //Layout
        setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );

        //Create the panels
        IMSOptionsPanel cost = new IMSOptionsPanel( rightsController.getCost( ), TC.get( "IMS.Rights.Cost" ) );
        IMSOptionsPanel copyAndOth = new IMSOptionsPanel( rightsController.getCost( ), TC.get( "IMS.Rights.CopyAndOth" ) );
        IMSOptionsPanel purpose = new IMSOptionsPanel( classificationController.getPurpose( ), TC.get( "IMS.Classification.Purpose" ) );
        IMSTextPanel description = new IMSTextPanel( classificationController.getDescription( ), TC.get( "IMS.Classification.Description" ), IMSTextPanel.TYPE_AREA );
        IMSTextPanel keywordPanel = new IMSTextPanel( classificationController.getKeywordController( ), TC.get( "IMS.Classification.Keyword" ), IMSTextPanel.TYPE_FIELD );

        //Add the panels
        add( cost );
        add( Box.createVerticalStrut( 1 ) );
        add( copyAndOth );
        add( Box.createVerticalStrut( 1 ) );
        add( purpose );
        add( Box.createVerticalStrut( 1 ) );
        add( description );
        add( Box.createVerticalStrut( 1 ) );
        add( keywordPanel );
        add( Box.createRigidArea( new Dimension( 400, 45 ) ) );
        //setSize(400, 200);
    }

}
