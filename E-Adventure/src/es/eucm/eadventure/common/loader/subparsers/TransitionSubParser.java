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
