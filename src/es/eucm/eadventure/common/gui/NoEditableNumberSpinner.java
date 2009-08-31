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
package es.eucm.eadventure.common.gui;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Very useful to avoid that a spinner could be edited by typing text. 
 * This class has been designed to avoid JSpinners to get The Key Focus in the window 
 * @author Javier
 *
 */
public class NoEditableNumberSpinner extends JSpinner{

	/**
	 * Required
	 */
	private static final long serialVersionUID = 4827835884428487802L;
	/**
	 * Component used as editor
	 */
	private JLabel editor;
	
	/**
	 * Constructor. Must be argumented with the selected value, min valid value, max valid value and the step
	 * @param value
	 * @param min
	 * @param max
	 * @param step
	 */
	public NoEditableNumberSpinner(Number value, int min, int max, Number step){
		super (new SpinnerNumberModel (value, min,max,step));
		editor = new JLabel(value.toString());
		this.setEditor(editor);
		this.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				editor.setText(getValue().toString());
			}
			
		});
	}
}
