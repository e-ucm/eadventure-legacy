package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.character.PlayerDataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;

public class SynthesizerDialog extends JDialog implements ItemListener{

	private JTextField voices;
	
	private int selectedConversationLine;
	
	private ConversationNode node;

	/**
	 * Check box to set that the conversation line omust be read by synthesizer
	 */
	private JCheckBox readSynthesizer;
	
	private JPanel pane;
	
	public SynthesizerDialog(int selectedRow, ConversationNode node, DataControlWithResources playerNpcControl, boolean player){
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
		JButton play = new JButton(TextConstants.getText( "Synthesizer.ButtonPlay" ));
		play.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				if (!voices.getText().equals(TextConstants.getText( "Synthesizer.Empty" ))){
				VoiceManager voiceManager = VoiceManager.getInstance();
				Voice voice = voiceManager.getVoice((String)voices.getText());
				voice.allocate();
				voice.speak(getText());
				}
		
			}
		} );
		
		pane = new JPanel(new GridLayout(2,1));
		voices = new JTextField();
		//voices.setBorder(border);
		readSynthesizer = new JCheckBox(TextConstants.getText( "Synthesizer.CheckLine" ));
		readSynthesizer.addItemListener(this);
		voices.setEditable(false);
		//TODO añadir player/npc voice 
		if (playerNpcControl!=null){
		if (player){
			if ( ((PlayerDataControl)playerNpcControl).getPlayerVoice()==null || ((PlayerDataControl)playerNpcControl).getPlayerVoice().equals("")){
				voices.setText(TextConstants.getText( "Synthesizer.Empty" ));
				play.setEnabled(false);
				readSynthesizer.setEnabled(false);
			}
			else
				voices.setText(((PlayerDataControl)playerNpcControl).getPlayerVoice());
		}
		else 
			if ( ((NPCDataControl)playerNpcControl).getNPCVoice()==null || ((NPCDataControl)playerNpcControl).getNPCVoice().equals("")){
				voices.setText(TextConstants.getText( "Synthesizer.Empty" ));
				readSynthesizer.setEnabled(false);
				play.setEnabled(false);
			}
			else
				voices.setText(((NPCDataControl)playerNpcControl).getNPCVoice());
		}
		pane.add(voices);
		pane.add(play);
		pane.add(readSynthesizer);
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

	public void itemStateChanged(ItemEvent e) {
		this.node.getLine(selectedConversationLine).setSynthesizerVoice(readSynthesizer.isSelected());
		
	}
}
