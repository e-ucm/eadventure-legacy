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
