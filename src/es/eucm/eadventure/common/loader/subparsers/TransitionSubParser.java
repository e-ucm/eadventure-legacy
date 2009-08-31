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
package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Transition;

public class TransitionSubParser extends DefaultHandler {	
	
	private Animation animation;
	
	private Transition transition;
	
	public TransitionSubParser(Animation animation) {
		this.animation = animation;
		transition = new Transition();
	}
	

	@Override
	public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) {
		if (qName.equals("transition")) {
			for (int i = 0; i < attrs.getLength(); i++){
				if (attrs.getQName(i).equals("type")) {
					if (attrs.getValue(i).equals("none"))
						transition.setType(Transition.TYPE_NONE);
					else if (attrs.getValue(i).equals("fadein"))
						transition.setType(Transition.TYPE_FADEIN);
					else if (attrs.getValue(i).equals("vertical"))
						transition.setType(Transition.TYPE_VERTICAL);
					else if (attrs.getValue(i).equals("horizontal"))
						transition.setType(Transition.TYPE_HORIZONTAL);
				}
				else if (attrs.getQName(i).equals("time")) {
					transition.setTime(Long.parseLong(attrs.getValue(i)));
				}
			}
		}
	}

	@Override
	public void endElement(String namespaceURI, String sName, String qName) {
		if (qName.equals("transition")) {
			animation.getTransitions().add(transition);
		}
	}
}
