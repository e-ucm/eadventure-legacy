package es.eucm.eadventure.editor.gui.editdialogs;

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
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;

public class SynthesizerDialog extends ToolManagableDialog implements ItemListener{

	/**
	 * Required
	 */
	private static final long serialVersionUID = -8944148760482181057L;

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
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "Synthesizer.Title" ), false);//, Dialog.ModalityType.APPLICATION_MODAL );

		this.selectedConversationLine = selectedRow;

		this.node = node;
		
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
		readSynthesizer.setSelected(node.getLine(selectedConversationLine).getSynthesizerVoice());
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
		Controller.getInstance().addTool(new ChangeBooleanValueTool(node.getLine(selectedConversationLine),
				readSynthesizer.isSelected(), "getSynthesizerVoice", "setSynthesizerVoice"));
		//this.node.getLine(selectedConversationLine).setSynthesizerVoice(readSynthesizer.isSelected());
	}

	@Override
	public boolean updateFields() {
		readSynthesizer.setSelected(node.getLine(selectedConversationLine).getSynthesizerVoice());
		return true;
	}
}
