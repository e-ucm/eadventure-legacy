package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class FrameSubParser extends DefaultHandler {	
	
	private Animation animation;
	
	private Frame frame;
	
	private Resources currentResources;
	
	public FrameSubParser(Animation animation) {
		this.animation = animation;
		frame = new Frame();
	}
	
	@Override
	public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) {
		if (qName.equals("frame")) {
			for (int i = 0; i < attrs.getLength(); i++) {
				if (attrs.getQName(i).equals("uri")) {
					frame.setUri(attrs.getValue(i));
				}
				if (attrs.getQName(i).equals("type")) {
					if (attrs.getValue(i).equals("image"))
						frame.setType(Frame.TYPE_IMAGE);
					if (attrs.getValue(i).equals("video"))
						frame.setType(Frame.TYPE_VIDEO);
				}
				if (attrs.getQName(i).equals("time")) {
					frame.setTime(Long.parseLong(attrs.getValue(i)));
				}
				if (attrs.getQName(i).equals("waitforclick")) {
					if (attrs.getValue(i).equals("yes"))
						frame.setWaitforclick(true);
					else
						frame.setWaitforclick(false);
				}
			}
		}
		
		if (qName.equals("resources")) {
			currentResources = new Resources();
		}
		
		if( qName.equals( "asset" ) ) {
			String type = "";
			String path = "";

			for( int i = 0; i < attrs.getLength( ); i++ ) {
				if( attrs.getQName( i ).equals( "type" ) )
					type = attrs.getValue( i );
				if( attrs.getQName( i ).equals( "uri" ) )
					path = attrs.getValue( i );
			}

			// If the asset is not an special one
			//if( !AssetsController.isAssetSpecial( path ) )
				currentResources.addAsset( type, path );
		}
	}

	@Override
	public void endElement(String namespaceURI, String sName, String qName) {
		if (qName.equals("frame")) {
			animation.getFrames().add(frame);
		}
		
		if (qName.equals("resources")) {
			frame.addResources(currentResources);
		}
	}


}
