package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.text.BadLocationException;

import de.schlichtherle.io.File;
import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.component.MutableFilter;
import de.xeinfach.kafenio.interfaces.KafenioPanelConfigurationInterface;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.HTMLEditController;

public class HTMLEditPanel extends KafenioPanel {

	private HTMLEditController htmlEditController;

	public void setHtmlEditController(HTMLEditController htmlEditController) {
		this.htmlEditController = htmlEditController;
	}


	public static HTMLEditPanel getInstance(java.io.File tempfile) {
		KafenioPanelConfigurationInterface newConfig = null;
		try {
			newConfig = (KafenioPanelConfigurationInterface)
			Class.forName("de.xeinfach.kafenio.KafenioPanelConfiguration").newInstance();
		} catch (Exception ex) {}

		newConfig.setImageDir("file://");
		
		newConfig.setCustomMenuItems("edit view font format table search tools help");
		newConfig.setCustomToolBar1("SAVE,SEPARATOR,CUT,COPY,PASTE,SEPARATOR,BOLD"
									+ ",ITALIC,UNDERLINE,SEPARATOR,LEFT,CENTER,RIGHT,JUSTIFY");

		File file = null;
		if (tempfile != null)
			file = new File(tempfile.getAbsoluteFile());
		HTMLEditPanel temp = new HTMLEditPanel(newConfig, file);
		return temp;
	}
	
	
	public HTMLEditPanel(KafenioPanelConfigurationInterface arg0, File file) {
		super(arg0);
		if (file != null)
			try {
				this.loadDocument(file, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// TODO Auto-generated constructor stub
	}
	
	protected void insertLocalImage(java.io.File whatImage) throws IOException, BadLocationException, RuntimeException {
		whatImage = getImageFromChooser(".", 
											MutableFilter.EXT_IMG, 
											super.translatrix.getTranslationString("FiletypeIMG"));
		super.insertLocalImage(whatImage);
		htmlEditController.addImage(whatImage);
	}

	
	public void saveAll() {
		try {
			super.writeOut(new File(htmlEditController.getFilename()));
			
			java.io.File temp = new File(htmlEditController.getFilename());
			FileInputStream fis = new FileInputStream(temp);	
			BufferedInputStream bis = new BufferedInputStream(fis);
			InputStreamReader isr = new InputStreamReader(bis);
			
			int size = (int) temp.length();  // get the file size (in bytes)
			char[] data = new char[size]; // allocate char array of right size
			isr.read( data, 0, size );   // read into char array
			isr.close();
			bis.close();
			fis.close();
			
			String contents = new String(data);
			contents = contents.replace("\\\\", "/");
						
			for (java.io.File image : htmlEditController.getImages()) {
				String viejo = image.getAbsolutePath().replace("\\\\", "/");
				String nuevo = image.getName();
				contents = contents.replace(viejo, nuevo);
				AssetsController.addSingleAsset( AssetsController.CATEGORY_STYLED_TEXT, image.getAbsolutePath());
			}
			
			
			FileOutputStream   ostr = new FileOutputStream(temp); 
			OutputStreamWriter owtr = new OutputStreamWriter( ostr ); // promote

			owtr.write( contents, 0, contents.length() );
			owtr.close();
			ostr.close();
			
			if (htmlEditController.isNewFile())
				AssetsController.addSingleAsset(AssetsController.CATEGORY_STYLED_TEXT, temp.getAbsolutePath());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/** (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent e) {
		setVisible(false);
		dispose();
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -248722263397313316L;

}
