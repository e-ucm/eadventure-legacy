/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESClassificationDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESRightsDataControl;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMCreatePrimitiveContainerPanel;

public class LOMESRightsAndClassificationPanel extends JPanel {

    //private IMSRightsDataControl rightsController;

    //private IMSClassificationDataControl classificationController;

    public LOMESRightsAndClassificationPanel( LOMESRightsDataControl rightsController, LOMESClassificationDataControl classificationController ) {

        //Layout
        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        //Create the panels
        //Rights Panels
        LOMESOptionsPanel cost = new LOMESOptionsPanel( rightsController.getCost( ), TC.get( "IMS.Rights.Cost" ) );
        LOMESOptionsPanel copyAndOth = new LOMESOptionsPanel( rightsController.getCopyrightandotherrestrictions( ), TC.get( "IMS.Rights.CopyAndOth" ) );
        LOMESTextPanel descriptionR = new LOMESTextPanel( rightsController.getDescriptionController( ), TC.get( "LOMES.Rights.Description" ), LOMESTextPanel.TYPE_AREA );
        LOMESOptionsPanel accessType = new LOMESOptionsPanel( rightsController.getAccesType( ), TC.get( "LOMES.Rights.AccessType" ) );
        LOMESTextPanel accessDescription = new LOMESTextPanel( rightsController.getAccessDescriptionController( ), TC.get( "LOMES.Rights.AccessDescription" ), LOMESTextPanel.TYPE_FIELD );

        //Classification Panels
        LOMESOptionsPanel purpose = new LOMESOptionsPanel( classificationController.getPurpose( ), TC.get( "IMS.Classification.Purpose" ) );
        //LOMESCreateContainerPanel taxonPath = new LOMESCreateContainerPanel(classificationController.getTaxonPath(),TextConstants.getText("LOMES.TaxonPath.Name"),LOMContributeDialog.NONE);
        LOMESTextPanel description = new LOMESTextPanel( classificationController.getDescription( ), TC.get( "IMS.Classification.Description" ), LOMESTextPanel.TYPE_AREA );
        LOMCreatePrimitiveContainerPanel keywordPanel = new LOMCreatePrimitiveContainerPanel( LOMCreatePrimitiveContainerPanel.LANGSTRING_TYPE, classificationController.getKeywords( ), TC.get( "LOM.General.Keyword" ), LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD );

        //Add the panels

        JPanel costAndCopy = new JPanel( );
        costAndCopy.setLayout( new GridLayout( 0, 2 ) );
        costAndCopy.add( cost );
        costAndCopy.add( copyAndOth );

        JPanel access = new JPanel( );
        access.setLayout( new GridLayout( 0, 2 ) );
        access.add( accessType );
        access.add( accessDescription );
        access.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "LOMES.Rights.Access" ) ) );

        JPanel container = new JPanel( );
        container.setLayout( new GridBagLayout( ) );
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        //Add the panels
        container.add( costAndCopy, c );
        c.gridy++;
        c.ipady = 50;
        container.add( descriptionR, c );
        c.gridy++;
        c.ipady = 0;
        container.add( access, c );
        c.gridy++;
        container.add( purpose, c );
        //c.gridy++;
        //container.add(taxonPath,c);
        c.gridy++;
        c.ipady = 30;
        container.add( description, c );
        c.gridy++;
        c.ipady = 0;
        container.add( keywordPanel, c );

        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        add( container, c );
    }

}
