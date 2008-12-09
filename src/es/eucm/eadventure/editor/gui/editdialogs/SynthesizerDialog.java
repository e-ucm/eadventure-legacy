package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

public class SynthesizerDialog extends JDialog implements ItemListener{

	private JComboBox voices;
	
	private int selectedConversationLine;
	
	private ConversationNode node;
	
	private Voice[] availableVoices;
	
	private JPanel pane;
	
	public SynthesizerDialog(int selectedRow, ConversationNode node){
		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "Synthesizer.Title" ), Dialog.ModalityType.APPLICATION_MODAL );

		this.selectedConversationLine = selectedRow;

		this.node = node;
		
		// Push the dialog into the stack, and add the window listener to pop in when closing
		Controller.getInstance( ).pushWindow( this );
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				Controller.getInstance( ).popWindow( );
			}
		} );
		
		TitledBorder border = BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "Synthesizer.BorderVoices" ), TitledBorder.LEFT, TitledBorder.TOP );
		// take all available voices
		VoiceManager voiceManager = VoiceManager.getInstance();
		availableVoices = voiceManager.getVoices();
		String[] voiceName = new String[availableVoices.length+1];
		voiceName[0] = TextConstants.getText( "Synthesizer.Empty" );
		int initIndex=0;
		String name = node.getLine(selectedConversationLine).getSynthesizerVoice();
		for (int i=0; i<availableVoices.length;i++){
			voiceName[i+1] = availableVoices[i].getName();
			if (availableVoices[i].getName().equals(name))
				initIndex = i+1;
		}
		JButton play = new JButton(TextConstants.getText( "Synthesizer.ButtonPlay" ));
		play.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				if (voices.getSelectedIndex()!=0){
				VoiceManager voiceManager = VoiceManager.getInstance();
				Voice voice = voiceManager.getVoice((String)voices.getSelectedItem());
				voice.allocate();
				voice.speak(getText());
				}
		
			}
		} );
		
		pane = new JPanel();
		voices = new JComboBox(voiceName);
		//voices.setBorder(border);
		voices.addItemListener(this);
		voices.setSelectedIndex(initIndex);
		pane.add(voices);
		pane.add(play);
		pane.setBorder(border);
		
		this.add(pane);
		setResizable( false );
		setSize( 300, 100 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}
	
	private String getText(){
		return this.node.getLineText(selectedConversationLine);
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		int selection = voices.getSelectedIndex();
		if (selection == 0){
			node.setSynthesizerVoice("", selectedConversationLine);
		}else {
			node.setSynthesizerVoice(availableVoices[selection-1].getName(), selectedConversationLine);
		}
		
	}
}
