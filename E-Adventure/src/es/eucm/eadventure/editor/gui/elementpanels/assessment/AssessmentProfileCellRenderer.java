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
package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTable;

import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class AssessmentProfileCellRenderer extends ResizeableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    public Component getTableCellRendererComponent( JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column ) {

        if( value2 == null )
            return new JPanel( );

        value = (AssessmentProfileDataControl) value2;
        name = ( (AssessmentProfileDataControl) value2 ).getName( );
        image = new BufferedImage( 200, 200, BufferedImage.TYPE_4BYTE_ABGR );

        return createPanel( );
    }

    public Component getTableCellEditorComponent( JTable table, Object value2, boolean isSelected, int row, int column ) {

        if( value2 == null )
            return new JPanel( );

        value = (AssessmentProfileDataControl) value2;
        name = ( (AssessmentProfileDataControl) value2 ).getName( );
        image = new BufferedImage( 200, 200, BufferedImage.TYPE_4BYTE_ABGR );

        return createPanel( );
    }
}
