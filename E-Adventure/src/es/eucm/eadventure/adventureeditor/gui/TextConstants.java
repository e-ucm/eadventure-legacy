package es.eucm.eadventure.adventureeditor.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * This class holds all the methods to load and handle the text strings to the rest of the application.
 * 
 * @author Bruno Torijano Bueno
 */
public class TextConstants {

	/**
	 * Properties set containing the strings.
	 */
	private static Properties guiStrings;

	/**
	 * Loads the strings of the application from the given XML properties file.
	 * 
	 * @param languageFile
	 *            Name of the file containing the text
	 */
	public static void loadStrings( String languageFile ) {
		try {
			guiStrings = new Properties( );
			guiStrings.loadFromXML( new FileInputStream( languageFile ) );
		}

		// If the file is bad formed
		catch( InvalidPropertiesFormatException e ) {
			JOptionPane.showMessageDialog( null, "The language file is bad-formed, please try to use another language file.\nThe default language (English) will be loaded.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
			loadDefaultStrings( );
		}

		// If the file was not found
		catch( FileNotFoundException e ) {
			JOptionPane.showMessageDialog( null, "The language file was not found, please verify that the \"config.xml\" file contains a reference to a valid language file.\nThe default language (English) will be loaded.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
			loadDefaultStrings( );
		}

		// If there was a I/O exception
		catch( IOException e ) {
			JOptionPane.showMessageDialog( null, "There has been an error loading the language file, please check for problem accessing the files.\nThe default language (English) will be loaded.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
			loadDefaultStrings( );
		}
	}

	/**
	 * If there is some error when loading the strings file, load the default text in english for the application.
	 */
	private static void loadDefaultStrings( ) {
		// Flush the strings hash map
		guiStrings.clear( );

		// Insert the strings in english
		guiStrings.put( "MainWindow.Title", "Adventure editor - {#0}" );

		guiStrings.put( "MenuFile.Title", "File" );
		guiStrings.put( "MenuFile.New", "New" );
		guiStrings.put( "MenuFile.Load", "Load..." );
		guiStrings.put( "MenuFile.Save", "Save" );
		guiStrings.put( "MenuFile.SaveAs", "Save as..." );
		guiStrings.put( "MenuFile.Exit", "Exit" );

		guiStrings.put( "MenuChapters.Title", "Chapters" );
		guiStrings.put( "MenuChapters.AddChapter", "Add chapter" );
		guiStrings.put( "MenuChapters.DeleteChapter", "Delete chapter" );
		guiStrings.put( "MenuChapters.Flags", "Edit chapter flags..." );
		guiStrings.put( "MenuChapters.MoveChapterUp", "Move chapter up" );
		guiStrings.put( "MenuChapters.MoveChapterDown", "Move chapter down" );

		guiStrings.put( "MenuAdventure.Title", "Adventure" );
		guiStrings.put( "MenuAdventure.CheckConsistency", "Check adventure consistency" );
		guiStrings.put( "MenuAdventure.AdventureData", "Edit adventure data..." );
		guiStrings.put( "MenuAdventure.GUIStyles", "Select GUI style..." );
		guiStrings.put( "MenuAdventure.AssessmentFiles", "Manage assessment files..." );
		guiStrings.put( "MenuAdventure.AdaptationFiles", "Manage adaptation files..." );
		guiStrings.put( "MenuAdventure.BackgroundAssets", "Manage background assets..." );
		guiStrings.put( "MenuAdventure.AnimationAssets", "Manage animation assets..." );
		guiStrings.put( "MenuAdventure.ImageAssets", "Manage image assets..." );
		guiStrings.put( "MenuAdventure.IconAssets", "Manage icon assets..." );
		guiStrings.put( "MenuAdventure.AudioAssets", "Manage audio assets..." );

		guiStrings.put( "MenuConfiguration.Title", "Configuration" );
		guiStrings.put( "MenuConfiguration.ShowItemReferences", "Show item references by default" );
		guiStrings.put( "MenuConfiguration.ShowNPCReferences", "Show character references by default" );

		guiStrings.put( "StartDialog.Title", "Adventure editor" );
		guiStrings.put( "StartDialog.Message", "To start using the <e-Adventure> editor, you must choose\nbetween creating a new adventure, or opening an\nexisting one to edit it." );
		guiStrings.put( "StartDialog.Option0", "New adventure" );
		guiStrings.put( "StartDialog.Option1", "Load adventure" );

		guiStrings.put( "Error.Title", "Error" );
		guiStrings.put( "Error.LoadData", "There has been an error loading the data. There could be an error with the format of the\nadventure, or the files \"descriptor.dtd\" or \"eadventure.dtd\" could be missing" );
		guiStrings.put( "Error.WriteData", "There has been an error writing the data, please check that the information is correct, and report the error to the development team" );
		guiStrings.put( "Error.SpecialAssetNotFound", "The special asset \"{#0}\" can't be found and won't be added in the adventure.\nPlease make sure that this file is included in the editor, as the lack of this file could lead to malfunction of the adventures" );

		guiStrings.put( "Operation.NewFileTitle", "New file" );
		guiStrings.put( "Operation.NewFileMessage", "The file has unsaved changes. Do you want to save those changes before creating the new data?" );

		guiStrings.put( "Operation.LoadFileTitle", "Load file" );
		guiStrings.put( "Operation.LoadFileMessage", "The file has unsaved changes. Do you want to save those changes before loading the new data?" );

		guiStrings.put( "Operation.SaveFileTitle", "Save file" );
		guiStrings.put( "Operation.OverwriteExistingFile", "The selected file \"{#0}\" it's not empty.\nIf you choose to save the adventure with this name, the previous data will be overwritten.\nAre you sure that you want to store the adventure in this file?" );

		guiStrings.put( "Operation.ExitTitle", "Exit" );
		guiStrings.put( "Operation.ExitMessage", "The file has unsaved changes. Do you want to save those changes before exit?" );

		guiStrings.put( "Operation.AdventureConsistencyTitle", "Adventure consistency" );
		guiStrings.put( "Operation.AdventurInconsistentWarning", "The adventure is inconsistent and will be unplayable with the <e-Adventure> engine\nPlease perform a \"Adventure consistency check\" and solve the problems" );
		guiStrings.put( "Operation.AdventureConsistentReport", "The adventure is completely consistent and can be played in the <e-Adventure> engine" );
		guiStrings.put( "Operation.AdventureInconsistentReport", "The adventure is inconsistent and will be unplayable until the following errors are solved:" );
		guiStrings.put( "Operation.AdventureConsistencyErrorResources", "The indispensable asset \"{#0}\" is not linked to an asset" );
		guiStrings.put( "Operation.AdventureConsistencyErrorBookParagraph", "The paragraph has no image attached" );
		guiStrings.put( "Operation.AdventureConsistencyErrorPlayAnimation", "The \"Play animation\" effect has no animation attached" );
		guiStrings.put( "Operation.AdventureConsistencyErrorPlaySound", "The \"Play sound\" effect has no sound clip attached" );

		guiStrings.put( "Operation.AddChapterTitle", "Add chapter" );
		guiStrings.put( "Operation.AddChapterMessage", "Input the title for the new chapter" );
		guiStrings.put( "Operation.AddChapterDefaultValue", "Chapter title" );

		guiStrings.put( "Operation.DeleteChapterTitle", "Delete chapter" );
		guiStrings.put( "Operation.DeleteChapterMessage", "Are you sure that you want to delete this chapter?\nIt will be impossible to recover it after deleting" );
		guiStrings.put( "Operation.DeleteChapterErrorLastChapter", "You can't delete the last chapter\nEvery adventure must have at least one chapter" );

		guiStrings.put( "Operation.AddSceneTitle", "Add scene" );
		guiStrings.put( "Operation.AddSceneMessage", "Type an identifier for the new scene" );
		guiStrings.put( "Operation.AddSceneDefaultValue", "SceneId" );

		guiStrings.put( "Operation.AddCutsceneTitle", "Add cutscene" );
		guiStrings.put( "Operation.AddCutsceneMessage", "Type an identifier for the new cutscene" );
		guiStrings.put( "Operation.AddCutsceneDefaultValue", "CutsceneId" );

		guiStrings.put( "Operation.AddBookTitle", "Add book" );
		guiStrings.put( "Operation.AddBookMessage", "Type an identifier for the new book" );
		guiStrings.put( "Operation.AddBookDefaultValue", "BookId" );

		guiStrings.put( "Operation.AddItemTitle", "Add item" );
		guiStrings.put( "Operation.AddItemMessage", "Type an identifier for the new item" );
		guiStrings.put( "Operation.AddItemDefaultValue", "ItemId" );

		guiStrings.put( "Operation.AddNPCTitle", "Add character" );
		guiStrings.put( "Operation.AddNPCMessage", "Type an identifier for the new character" );
		guiStrings.put( "Operation.AddNPCDefaultValue", "CharacterId" );

		guiStrings.put( "Operation.AddConversationTitle", "Add conversation" );
		guiStrings.put( "Operation.AddConversationMessage", "Type an identifier for the new conversation" );
		guiStrings.put( "Operation.AddConversationDefaultValue", "ConversationId" );

		guiStrings.put( "Operation.AddNextSceneTitle", "Add next scene" );
		guiStrings.put( "Operation.AddNextSceneMessage", "Select a scene to link with the \"Next scene\" structure" );
		guiStrings.put( "Operation.AddNextSceneErrorNoScenes", "There must be at least one scene or cutscene to create a \"Next scene\" element" );

		guiStrings.put( "Operation.AddExitTitle", "Add exit" );
		guiStrings.put( "Operation.AddExitErrorNoNextScenes", "You can't add a new exit to the scene without attaching a \"Next scene\" element to it" );

		guiStrings.put( "Operation.AddItemReferenceTitle", "Add item reference" );
		guiStrings.put( "Operation.AddItemReferenceMessage", "Select the item you want to add to the scene" );
		guiStrings.put( "Operation.AddItemReferenceErrorNoItems", "There must be at least one item in the chapter to place a reference" );

		guiStrings.put( "Operation.AddNPCReferenceTitle", "Add character reference" );
		guiStrings.put( "Operation.AddNPCReferenceMessage", "Select the character you want to add to the scene" );
		guiStrings.put( "Operation.AddNPCReferenceErrorNoNPCs", "There must be at least one character in the chapter to place a reference" );

		guiStrings.put( "Operation.AddConversationReferenceTitle", "Add conversation reference" );
		guiStrings.put( "Operation.AddConversationReferenceMessage", "Select the conversation you want to link with the character" );
		guiStrings.put( "Operation.AddConversationReferenceErrorNoConversations", "There must be at least one conversation in the chapter to place a reference" );

		guiStrings.put( "Operation.DeleteResourcesTitle", "Delete resources" );
		guiStrings.put( "Operation.DeleteResourcesErrorLastResources", "The last resources block can not be deleted" );

		guiStrings.put( "Operation.DeleteNextSceneTitle", "Delete next scene" );
		guiStrings.put( "Operation.DeleteNextSceneErrorLastNextScene", "The last \"Next scene\" of the exit can not be deleted\nYou can however delete the parent \"Exit\" element" );

		guiStrings.put( "Operation.RenameSceneTitle", "Rename scene" );
		guiStrings.put( "Operation.RenameSceneMessage", "Type a new identifier to rename the scene" );

		guiStrings.put( "Operation.RenameCutsceneTitle", "Rename cutscene" );
		guiStrings.put( "Operation.RenameCutsceneMessage", "Type a new identifier to rename the cutscene" );

		guiStrings.put( "Operation.RenameBookTitle", "Rename book" );
		guiStrings.put( "Operation.RenameBookMessage", "Type a new identifier to rename the book" );

		guiStrings.put( "Operation.RenameItemTitle", "Rename item" );
		guiStrings.put( "Operation.RenameItemMessage", "Type a new identifier to rename the item" );

		guiStrings.put( "Operation.RenameNPCTitle", "Rename character" );
		guiStrings.put( "Operation.RenameNPCMessage", "Type a new identifier to rename the character" );

		guiStrings.put( "Operation.RenameConversationTitle", "Rename conversation" );
		guiStrings.put( "Operation.RenameConversationMessage", "Type a new identifier to rename the conversation" );

		guiStrings.put( "Operation.DeleteElementTitle", "Delete element" );
		guiStrings.put( "Operation.DeleteElementWarning", "The element \"{#0}\" have {#1} references in the chapter, all these references will be deleted along with the element.\nAre you sure that you want to delete it?" );
		guiStrings.put( "Operation.RenameElementWarning", "The element \"{#0}\" have {#1} references in the chapter.\nAre you sure that you want to rename it?" );
		guiStrings.put( "Operation.ErrorLastScene", "Cannot delete the last scene of the chapter.\nAll chapters must have at least one scene or cutscene" );

		guiStrings.put( "Operation.IdErrorTitle", "Identifier error" );
		guiStrings.put( "Operation.IdErrorBlankSpaces", "The identifier must contain no blank spaces" );
		guiStrings.put( "Operation.IdErrorAlreadyUsed", "The input identifier is already in use" );
		guiStrings.put( "Operation.IdErrorReservedIdentifier", "The identifier \"{#0}\" is reserved for internal purposes" );
		guiStrings.put( "Operation.IdErrorFirstCharacter", "The first character of an identifier must be a letter" );

		guiStrings.put( "GUIStyles.Title", "GUI style for the adventure" );
		guiStrings.put( "GUIStyles.Traditional", "Traditional GUI" );
		guiStrings.put( "GUIStyles.TraditionalDescription", "This type of GUI is much like the earlier adventures of LucasArts. The user has a set of buttons to perform actions, and an inventory in the lower part of the screen" );
		guiStrings.put( "GUIStyles.Contextual", "Contextual GUI" );
		guiStrings.put( "GUIStyles.ContextualDescription", "This type of GUI is much like the latest adventures of LucasArts. The actions are displayed in front of the object, when the user selects them. Also, the inventory is not visible everytime, so the user can bring it up whenever he wants" );

		guiStrings.put( "AssessmentFiles.Title", "Assessment files" );
		guiStrings.put( "AssessmentFiles.AddFile", "Add assessment file" );
		guiStrings.put( "AssessmentFiles.DeleteFile", "Delete assessment file" );

		guiStrings.put( "AdaptationFiles.Title", "Adaptation files" );
		guiStrings.put( "AdaptationFiles.AddFile", "Add adaptation file" );
		guiStrings.put( "AdaptationFiles.DeleteFile", "Delete adaptation file" );

		guiStrings.put( "Assets.AddAsset", "Add asset" );
		guiStrings.put( "Assets.DeleteAsset", "Delete asset" );
		guiStrings.put( "Assets.DeleteAssetWarning", "The asset \"{#0}\" have {#1} references in the adventure, all these references will be deleted along with the asset.\nAre you sure that you want to delete it?" );
		guiStrings.put( "Assets.WarningAssetFound", "The asset \"{#0}\" is already in this category.\nDo you want to overwrite it?" );

		guiStrings.put( "BackgroundAssets.Title", "Background assets" );
		guiStrings.put( "BackgroundAssets.Preview", "Preview of the background" );
		guiStrings.put( "BackgroundAssets.ErrorBackgroundSize", "The file \"{#0}\" can't be added to the background assets.\nThe minimun dimensions for a background are 800x400 pixels, whilst the file's are {#1}x{#2}" );

		guiStrings.put( "AnimationAssets.Title", "Animation assets" );
		guiStrings.put( "AnimationAssets.Preview", "Preview of the animation" );

		guiStrings.put( "ImageAssets.Title", "Image assets" );
		guiStrings.put( "ImageAssets.Preview", "Preview of the image" );

		guiStrings.put( "IconAssets.Title", "Icon assets" );
		guiStrings.put( "IconAssets.Preview", "Preview of the icon" );
		guiStrings.put( "IconAssets.ErrorIconSize", "The file \"{#0}\" can't be added to the icon assets.\nThe dimensions for an icon are 80x48 pixels, whilst the file's are {#1}x{#2}" );

		guiStrings.put( "AudioAssets.Title", "Audio assets" );
		guiStrings.put( "AudioAssets.Preview", "Audio player" );

		guiStrings.put( "Flags.Title", "Flags" );
		guiStrings.put( "Flags.AddFlag", "Add flag" );
		guiStrings.put( "Flags.DeleteFlag", "Delete flag" );
		guiStrings.put( "Flags.DefaultFlagId", "FlagId" );
		guiStrings.put( "Flags.FlagName", "Flag name" );
		guiStrings.put( "Flags.FlagReferences", "References" );
		guiStrings.put( "Flags.AddFlagMessage", "Please specify the name of the new flag" );
		guiStrings.put( "Flags.ErrorFlagWithReferences", "To delete a flag, it must have no references" );
		guiStrings.put( "Flags.ErrorFlagAlreadyExists", "The input flag specified is already in use" );
		guiStrings.put( "Flags.ErrorFlagWhitespaces", "The input flag identifier must contain no white spaces" );

		guiStrings.put( "Conditions.Title", "Conditions" );
		guiStrings.put( "Conditions.MainBlockTab", "Main block" );
		guiStrings.put( "Conditions.MainBlockTitle", "Main block of conditions" );
		guiStrings.put( "Conditions.EitherBlockTab", "Either block #{#0}" );
		guiStrings.put( "Conditions.EitherBlockTitle", "Either block of conditions #{#0}" );
		guiStrings.put( "Conditions.AddCondition", "Add condition" );
		guiStrings.put( "Conditions.EditCondition", "Edit condition" );
		guiStrings.put( "Conditions.DeleteCondition", "Delete condition" );
		guiStrings.put( "Conditions.AddEitherBlock", "Add either condition block" );
		guiStrings.put( "Conditions.EditConditionMessage", "Select the values for the state and the flag of the condition" );
		guiStrings.put( "Conditions.State", "State" );
		guiStrings.put( "Conditions.Flag", "Flag" );
		guiStrings.put( "Conditions.ErrorNoFlags", "To add a condition, there must be at least one flag in the chapter" );

		guiStrings.put( "Effects.Title", "Effects" );
		guiStrings.put( "Effects.Description", "Use the buttons to add, delete and move effects. You can edit them by double clicking" );
		guiStrings.put( "Effects.EffectNumberColumnTitle", "#" );
		guiStrings.put( "Effects.EffectDescriptionColumnTitle", "Effect description" );
		guiStrings.put( "Effects.AddEffectButton", "Add effect" );
		guiStrings.put( "Effects.DeleteEffectButton", "Delete effect" );
		guiStrings.put( "Effects.OperationAddEffect", "Add effect" );
		guiStrings.put( "Effects.SelectEffectType", "Select the type of effect you want to add" );

		guiStrings.put( "Effect.Activate", "Activate" );
		guiStrings.put( "Effect.Deactivate", "Deactivate" );
		guiStrings.put( "Effect.ConsumeObject", "Consume object" );
		guiStrings.put( "Effect.GenerateObject", "Generate object" );
		guiStrings.put( "Effect.CancelAction", "Cancel action" );
		guiStrings.put( "Effect.SpeakPlayer", "Speak player" );
		guiStrings.put( "Effect.SpeakCharacter", "Speak character" );
		guiStrings.put( "Effect.TriggerBook", "Trigger book" );
		guiStrings.put( "Effect.PlaySound", "Play sound" );
		guiStrings.put( "Effect.PlayAnimation", "Play animation" );
		guiStrings.put( "Effect.MovePlayer", "Move player" );
		guiStrings.put( "Effect.MoveCharacter", "Move character" );
		guiStrings.put( "Effect.TriggerConversation", "Trigger conversation" );
		guiStrings.put( "Effect.TriggerCutscene", "Trigger cutscene" );
		guiStrings.put( "Effect.TriggerScene", "Trigger scene" );

		guiStrings.put( "Effect.ActivateInfo", "Activate flag: {#0}" );
		guiStrings.put( "Effect.DeactivateInfo", "Deactivate flag: {#0}" );
		guiStrings.put( "Effect.ConsumeObjectInfo", "Consume object {#0}" );
		guiStrings.put( "Effect.GenerateObjectInfo", "Generate object {#0}" );
		guiStrings.put( "Effect.CancelActionInfo", "Cancel default action" );
		guiStrings.put( "Effect.SpeakPlayerInfo", "Player speaks \"{#0}\"" );
		guiStrings.put( "Effect.SpeakCharacterInfo", "Character {#0} speaks \"{#1}\"" );
		guiStrings.put( "Effect.TriggerBookInfo", "Trigger book {#0}" );
		guiStrings.put( "Effect.PlaySoundInfo", "Play sound effect \"{#0}\"" );
		guiStrings.put( "Effect.PlayAnimationInfo", "Play animation effect \"{#0}\"" );
		guiStrings.put( "Effect.MovePlayerInfo", "Move player to x:{#0} y:{#1}" );
		guiStrings.put( "Effect.MoveCharacterInfo", "Move character {#0} to x:{#0} y:{#1}" );
		guiStrings.put( "Effect.TriggerConversationInfo", "Trigger conversation {#0}" );
		guiStrings.put( "Effect.TriggerCutsceneInfo", "Trigger cutscene {#0}" );
		guiStrings.put( "Effect.TriggerSceneInfo", "Trigger scene {#0}" );

		guiStrings.put( "ImageDialog.Title", "Image preview - {#0}" );
		guiStrings.put( "SlidesDialog.Title", "Slides preview - {#0}" );
		guiStrings.put( "AnimationDialog.Title", "Animation preview - {#0}" );
		guiStrings.put( "AudioDialog.Title", "Audio player - {#0}" );
		guiStrings.put( "ConversationDialog.Title", "Conversation preview - {#0}" );

		guiStrings.put( "ImagePanel.ImageNotAvalaible", "Image not avalaible" );

		guiStrings.put( "AnimationPanel.AnimationNotAvalaible", "Animation not avalaible" );

		guiStrings.put( "ActivateEffect.Title", "Activate effect" );
		guiStrings.put( "ActivateEffect.Description", "Select the flag to be activated" );

		guiStrings.put( "DeactivateEffect.Title", "Deactivate effect" );
		guiStrings.put( "DeactivateEffect.Description", "Select the flag to be deactivated" );

		guiStrings.put( "ActivateDeactivateEffect.ErrorNoFlags", "To add this type of effect, there must be at least one flag in the chapter" );

		guiStrings.put( "MoveNPCEffect.Title", "Move character effect" );
		guiStrings.put( "MoveNPCEffect.Description", "Select a character to move, and the destination point" );
		guiStrings.put( "MoveNPCEffect.ErrorNoCharacters", "To add this type of effect, there must be at least one character in the chapter" );

		guiStrings.put( "MovePlayerEffect.Title", "Move player effect" );
		guiStrings.put( "MovePlayerEffect.Description", "Select a destination point to move the player" );

		guiStrings.put( "ConsumeObject.Title", "Consume object effect" );
		guiStrings.put( "ConsumeObject.Description", "Select the item to be consumed" );

		guiStrings.put( "GenerateObject.Title", "Generate object effect" );
		guiStrings.put( "GenerateObject.Description", "Select the item to be generated" );

		guiStrings.put( "ConsumeGenerateObjectEffect.ErrorNoItems", "To add this type of effect, there must be at least one item in the chapter" );

		guiStrings.put( "PlayAnimationEffect.Title", "Play animation effect" );
		guiStrings.put( "PlayAnimationEffect.Description", "Select an animation and a destination to play it" );

		guiStrings.put( "PlaySoundEffect.Title", "Play sound effect" );
		guiStrings.put( "PlaySoundEffect.Description", "Select a sound file to be played" );
		guiStrings.put( "PlaySoundEffect.BackgroundCheckBox", "Play the sound in background" );

		guiStrings.put( "SpeakCharacterEffect.Title", "Speak character effect" );
		guiStrings.put( "SpeakCharacterEffect.Description", "Select the character you want to speak, and the line he must say" );
		guiStrings.put( "SpeakCharacterEffect.ErrorNoCharacters", "To add this type of effect, there must be at least one character in the chapter" );

		guiStrings.put( "SpeakPlayerEffect.Title", "Speak player effect" );
		guiStrings.put( "SpeakPlayerEffect.Description", "Type the line you want the player to say" );

		guiStrings.put( "TriggerBookEffect.Title", "Trigger book effect" );
		guiStrings.put( "TriggerBookEffect.Description", "Select the book you want to display" );
		guiStrings.put( "TriggerBookEffect.ErrorNoBooks", "To add this type of effect, there must be at least one book in the chapter" );

		guiStrings.put( "TriggerConversationEffect.Title", "Trigger conversation effect" );
		guiStrings.put( "TriggerConversationEffect.Description", "Select the conversation you want to trigger" );
		guiStrings.put( "TriggerConversationEffect.ErrorNoConversations", "To add this type of effect, there must be at least one conversation in the chapter" );

		guiStrings.put( "TriggerCutsceneEffect.Title", "Trigger cutscene effect" );
		guiStrings.put( "TriggerCutsceneEffect.Description", "Select the cutscene you want to trigger" );
		guiStrings.put( "TriggerCutsceneEffect.ErrorNoCutscenes", "To add this type of effect, there must be at least one cutscene in the chapter" );

		guiStrings.put( "TriggerSceneEffect.Title", "Trigger scene effect" );
		guiStrings.put( "TriggerSceneEffect.Description", "Select the scene you want to trigger and the insertion point of the player" );
		guiStrings.put( "TriggerSceneEffect.ErrorNoScenes", "To add this type of effect, there must be at least one scene in the chapter" );

		guiStrings.put( "SceneLocation.SceneListDescription", "Select a scene to pick a destination point" );
		guiStrings.put( "SceneLocation.NoSceneSelected", "No scene selected" );
		guiStrings.put( "SceneLocation.XCoordinate", "X coordinate" );
		guiStrings.put( "SceneLocation.YCoordinate", "Y coordinate" );
		guiStrings.put( "SceneLocation.Width", "Area width" );
		guiStrings.put( "SceneLocation.Height", "Area height" );

		guiStrings.put( "Resources.Title", "Resources" );
		guiStrings.put( "Resources.Information", "In this panel you can edit the resources of the element. Use the \"Examine\" buttons to select assets for the element, and the \"Preview\"/\"Play\" buttons to take a look at the selected assets." );
		guiStrings.put( "Resources.Conditions", "Conditions of the resources block" );

		guiStrings.put( "Resources.EditAsset", "Edit asset" );
		guiStrings.put( "Resources.EditAssetMessage", "Select an asset from the list" );
		guiStrings.put( "Resources.DeleteAsset", "Clear asset" );
		guiStrings.put( "Resources.Select", "Select..." );
		guiStrings.put( "Resources.ViewAsset", "View" );
		guiStrings.put( "Resources.PlayAsset", "Play" );
		guiStrings.put( "Resources.ErrorNoAssets", "There are no assets for this category.\nPlease add some assets with the managers in the \"Adventure\" menu of the main window" );

		guiStrings.put( "Resources.DescriptionSceneBackground", "Background image of the scene" );
		guiStrings.put( "Resources.DescriptionSceneForeground", "Foreground mask for the scene" );
		guiStrings.put( "Resources.DescriptionSceneHardMap", "Hard map image of the scene" );
		guiStrings.put( "Resources.DescriptionSceneMusic", "Music of the scene" );
		guiStrings.put( "Resources.DescriptionSlidesceneSlides", "Set of slides of the slidescene" );
		guiStrings.put( "Resources.DescriptionBookBackground", "Background image of the book" );
		guiStrings.put( "Resources.DescriptionItemImage", "Image of the item" );
		guiStrings.put( "Resources.DescriptionItemIcon", "Inventory icon of the item" );
		guiStrings.put( "Resources.DescriptionCharacterAnimationStandUp", "Character's animation looking up" );
		guiStrings.put( "Resources.DescriptionCharacterAnimationStandDown", "Character's animation looking down" );
		guiStrings.put( "Resources.DescriptionCharacterAnimationStandRight", "Character's animation looking right" );
		guiStrings.put( "Resources.DescriptionCharacterAnimationSpeakUp", "Character's animation speaking up" );
		guiStrings.put( "Resources.DescriptionCharacterAnimationSpeakDown", "Character's animation speaking down" );
		guiStrings.put( "Resources.DescriptionCharacterAnimationSpeakRight", "Character's animation speaking right" );
		guiStrings.put( "Resources.DescriptionCharacterAnimationUseRight", "Character's animation using an object" );
		guiStrings.put( "Resources.DescriptionCharacterAnimationWalkUp", "Character's animation walking up" );
		guiStrings.put( "Resources.DescriptionCharacterAnimationWalkDown", "Character's animation walking down" );
		guiStrings.put( "Resources.DescriptionCharacterAnimationWalkRight", "Character's animation walking right" );

		guiStrings.put( "Adventure.Title", "Adventure data" );
		guiStrings.put( "Adventure.AdventureTitle", "Title of the adventure" );
		guiStrings.put( "Adventure.AdventureDescription", "Description of the adventure" );

		guiStrings.put( "Chapter.Title", "Chapter" );
		guiStrings.put( "Chapter.ChapterTitle", "Title of the chapter" );
		guiStrings.put( "Chapter.Description", "Description of the chapter" );
		guiStrings.put( "Chapter.AssessmentPath", "Assessment file of the chapter" );
		guiStrings.put( "Chapter.AdaptationPath", "Adaptation file of the chapter" );
		guiStrings.put( "Chapter.InitialScene", "Initial scene of the chapter" );

		guiStrings.put( "ScenesList.Title", "Scenes" );
		guiStrings.put( "ScenesList.ListTitle", "List of scenes" );
		guiStrings.put( "ScenesList.Information", "The scenes are the main element of an adventure. The player can move through the scene, and interact with the exits, items and characters that are placed in it." );
		guiStrings.put( "ScenesList.ColumnHeader0", "Scene identifier" );
		guiStrings.put( "ScenesList.ColumnHeader1", "Number of exits" );
		guiStrings.put( "ScenesList.ColumnHeader2", "Number of items" );
		guiStrings.put( "ScenesList.ColumnHeader3", "Number of NPCs" );
		guiStrings.put( "ScenesList.ExitsNumber", "{#0} exits" );
		guiStrings.put( "ScenesList.ItemsNumber", "{#0} items" );
		guiStrings.put( "ScenesList.NPCsNumber", "{#0} NPCs" );

		guiStrings.put( "Scene.Title", "Scene" );
		guiStrings.put( "Scene.Documentation", "Full description of the scene" );
		guiStrings.put( "Scene.Name", "Name of the scene" );
		guiStrings.put( "Scene.DefaultInitialPosition", "Default initial position for the player" );
		guiStrings.put( "Scene.UseInitialPosition", "Use a default initial position in this scene" );
		guiStrings.put( "Scene.EditInitialPosition", "Edit the default initial position of the scene" );
		guiStrings.put( "Scene.Preview", "Preview of the complete scene" );

		guiStrings.put( "ExitsList.Title", "Exits" );
		guiStrings.put( "ExitsList.PreviewTitle", "Preview of the exits location" );
		guiStrings.put( "ExitsList.Information", "The exits are areas defined in the scenes, from which the player can access to other areas. When the player moves to an exit, it can travel to another scene or cutscene, depending on the next scene that is defined and active in the exit." );

		guiStrings.put( "Exit.Title", "Exit" );
		guiStrings.put( "Exit.Documentation", "Full description of the exit" );
		guiStrings.put( "Exit.Preview", "Position and size of the exit" );

		guiStrings.put( "NextScene.Title", "Next scene" );
		guiStrings.put( "NextScene.NextSceneId", "Id of the next scene" );
		guiStrings.put( "NextScene.DestinyPosition", "Destiny position for the player" );
		guiStrings.put( "NextScene.UseDestinyPosition", "Use a destiny position for the player" );
		guiStrings.put( "NextScene.EditDestinyPosition", "Edit the destiny position for the player in the new scene" );
		guiStrings.put( "NextScene.Conditions", "Conditions of the next scene" );
		guiStrings.put( "NextScene.Effects", "Effects of the next scene" );
		guiStrings.put( "NextScene.PostEffects", "Post-effects of the next scene" );

		guiStrings.put( "ElementReference.ItemReferences", "Show item references" );
		guiStrings.put( "ElementReference.NPCReferences", "Show character references" );

		guiStrings.put( "ItemReferencesList.Title", "Item references" );
		guiStrings.put( "ItemReferencesList.PreviewTitle", "Preview of the items location" );
		guiStrings.put( "ItemReferencesList.Information", "The item references are the items that can be found in the scene. Each item is defined out of the scene, but can be added to a lot of scenes. For each reference, you can select where do you want to place the items, and in which circumstances you want it to appear in the scene." );

		guiStrings.put( "ItemReference.Title", "Item reference" );
		guiStrings.put( "ItemReference.Documentation", "Full description of the referenced item" );
		guiStrings.put( "ItemReference.ItemId", "Id of the referenced item" );
		guiStrings.put( "ItemReference.Conditions", "Conditions of the item reference" );
		guiStrings.put( "ItemReference.Position", "Position of the item reference" );

		guiStrings.put( "NPCReferencesList.Title", "Character references" );
		guiStrings.put( "NPCReferencesList.PreviewTitle", "Preview of the characters location" );
		guiStrings.put( "NPCReferencesList.Information", "The character references are the characters that can be found in the scene. Each character is defined out of the scene, but can be added to a lot of scenes. For each reference, you can select where do you want to place the character, and in which circumstances you want it to appear in the scene." );

		guiStrings.put( "NPCReference.Title", "Character reference" );
		guiStrings.put( "NPCReference.Documentation", "Full description of the referenced character" );
		guiStrings.put( "NPCReference.NPCId", "Id of the referenced character" );
		guiStrings.put( "NPCReference.Conditions", "Conditions of the character reference" );
		guiStrings.put( "NPCReference.Position", "Position of the character reference" );

		guiStrings.put( "CutscenesList.Title", "Cutscenes" );
		guiStrings.put( "CutscenesList.ListTitle", "List of cutscenes" );
		guiStrings.put( "CutscenesList.Information", "The cutscenes are often used to display data to the player in a non-interactive way. They can be used to present the prologue or epilogue of the adventure with text and images, as well as for another wide variety of uses." );
		guiStrings.put( "CutscenesList.ColumnHeader0", "Cutscene identifier" );
		guiStrings.put( "CutscenesList.ColumnHeader1", "Type of the cutscene" );
		guiStrings.put( "CutscenesList.Slidescene", "Slidescene" );

		guiStrings.put( "Cutscene.Title", "Cutscene" );
		guiStrings.put( "Cutscene.Documentation", "Full description of the cutscene" );
		guiStrings.put( "Cutscene.Name", "Name of the cutscene" );

		guiStrings.put( "BooksList.Title", "Books" );
		guiStrings.put( "BooksList.ListTitle", "List of books" );
		guiStrings.put( "BooksList.Information", "The books are elements that can be shown during a game to display text information to the player. Here you can see a list of the current avalaible books, along with the number of paragraphs of each one." );
		guiStrings.put( "BooksList.ColumnHeader0", "Book identifier" );
		guiStrings.put( "BooksList.ColumnHeader1", "Number of paragraphs" );
		guiStrings.put( "BooksList.ParagraphsNumber", "{#0} paragraphs" );

		guiStrings.put( "Book.Title", "Book" );
		guiStrings.put( "Book.Documentation", "Full description of the book" );
		guiStrings.put( "Book.Preview", "Graphical preview of the empty book" );

		guiStrings.put( "BookParagraphsList.Title", "Book paragraphs" );
		guiStrings.put( "BookParagraphsList.ListTitle", "List of book paragraphs" );
		guiStrings.put( "BookParagraphsList.Information", "Every book consists of a group of paragraphs, that are shown in order. These paragraphs represent plain text, titles, images, bullet paragraphs... and can be combined in any way." );
		guiStrings.put( "BookParagraphsList.ColumnHeader0", "Paragraph type" );
		guiStrings.put( "BookParagraphsList.ColumnHeader1", "Word count" );
		guiStrings.put( "BookParagraphsList.TextParagraph", "{#0} - Text paragraph" );
		guiStrings.put( "BookParagraphsList.TitleParagraph", "{#0} - Title paragraph" );
		guiStrings.put( "BookParagraphsList.BulletParagraph", "{#0} - Bullet paragraph" );
		guiStrings.put( "BookParagraphsList.ImageParagraph", "{#0} - Image paragraph" );
		guiStrings.put( "BookParagraphsList.WordCount", "{#0} words" );
		guiStrings.put( "BookParagraphsList.NotApplicable", "- Not applicable -" );

		guiStrings.put( "BookParagraph.Title", "Book paragraph" );
		guiStrings.put( "BookParagraph.Text", "Text of the paragraph" );
		guiStrings.put( "BookParagraph.Title", "Text of the title paragraph" );
		guiStrings.put( "BookParagraph.Bullet", "Text of the bullet paragraph" );
		guiStrings.put( "BookParagraph.ImagePath", "Path to the image of the paragraph" );
		guiStrings.put( "BookParagraph.Preview", "Preview of the selected image" );

		guiStrings.put( "ItemsList.Title", "Items" );
		guiStrings.put( "ItemsList.ListTitle", "List of items" );
		guiStrings.put( "ItemsList.Information", "The items are one of the principal elements that the player can interact with. They can be placed in the scenes, generated, consumed or placed in the inventory. Also, a wide variety of actions can be performed with the items." );
		guiStrings.put( "ItemsList.ColumnHeader0", "Item identifier" );
		guiStrings.put( "ItemsList.ColumnHeader1", "Number of actions" );
		guiStrings.put( "ItemsList.ActionsNumber", "{#0} actions" );

		guiStrings.put( "Item.Title", "Item" );
		guiStrings.put( "Item.Documentation", "Full description of the item" );
		guiStrings.put( "Item.Name", "Name of the item" );
		guiStrings.put( "Item.Description", "Brief description of the item" );
		guiStrings.put( "Item.DetailedDescription", "Detailed description of the item" );
		guiStrings.put( "Item.Preview", "Graphical preview of the item" );

		guiStrings.put( "ActionsList.Title", "Actions" );
		guiStrings.put( "ActionsList.ListTitle", "List of actions" );
		guiStrings.put( "ActionsList.Information", "Each item can have attached a set of actions, each one of them having a set of conditions and effects. These actions will be executed when the player interacts with the items." );
		guiStrings.put( "ActionsList.ColumnHeader0", "Action type" );
		guiStrings.put( "ActionsList.ColumnHeader1", "Has conditions" );
		guiStrings.put( "ActionsList.ColumnHeader2", "Has effects" );
		guiStrings.put( "ActionsList.ExamineAction", "Examine" );
		guiStrings.put( "ActionsList.GrabAction", "Grab" );
		guiStrings.put( "ActionsList.GiveToAction", "Give to {#0}" );
		guiStrings.put( "ActionsList.UseWithAction", "Use with {#0}" );
		guiStrings.put( "ActionsList.UseAction", "Use" );

		guiStrings.put( "Action.Title", "Action" );
		guiStrings.put( "Action.OperationAddAction", "Add action" );
		guiStrings.put( "Action.MessageSelectItem", "Select an item to be the target of the action" );
		guiStrings.put( "Action.ErrorNoItems", "There are no items defined in the chapter\nIt's impossible to add an action of this type" );
		guiStrings.put( "Action.MessageSelectNPC", "Select a character to be the target of the action" );
		guiStrings.put( "Action.ErrorNoNPCs", "There are no characters defined in the chapter\nIt's impossible to add an action of this type" );
		guiStrings.put( "Action.Documentation", "Full description of the action" );
		guiStrings.put( "Action.IdTarget", "Target element of this action" );
		guiStrings.put( "Action.Conditions", "Conditions of the action" );

		guiStrings.put( "Player.Title", "Player" );
		guiStrings.put( "Player.Documentation", "Full description of the player" );
		guiStrings.put( "Player.TextColor", "Color of the player's conversation lines" );
		guiStrings.put( "Player.FrontColor", "Edit font front color" );
		guiStrings.put( "Player.BorderColor", "Edit font border color" );
		guiStrings.put( "Player.Name", "Name of the player" );
		guiStrings.put( "Player.Description", "Brief description of the player" );
		guiStrings.put( "Player.DetailedDescription", "Detailed description of the player" );
		guiStrings.put( "Player.Preview", "Graphical preview of the player" );

		guiStrings.put( "NPCsList.Title", "Characters" );
		guiStrings.put( "NPCsList.ListTitle", "List of characters" );
		guiStrings.put( "NPCsList.Information", "The characters take a very important role in every adventure. They can strike up conversations with the player, offering information or advice. They can interact with items also, receiving or giving items to the player." );
		guiStrings.put( "NPCsList.ColumnHeader0", "Character identifier" );
		guiStrings.put( "NPCsList.ColumnHeader1", "Number of conversations" );
		guiStrings.put( "NPCsList.ConversationsNumber", "{#0} conversations" );

		guiStrings.put( "NPC.Title", "Character" );
		guiStrings.put( "NPC.Documentation", "Full description of the character" );
		guiStrings.put( "NPC.TextColor", "Color of the character's conversation lines" );
		guiStrings.put( "NPC.FrontColor", "Edit font front color" );
		guiStrings.put( "NPC.BorderColor", "Edit font border color" );
		guiStrings.put( "NPC.Name", "Name of the character" );
		guiStrings.put( "NPC.Description", "Brief description of the character" );
		guiStrings.put( "NPC.DetailedDescription", "Detailed description of the character" );
		guiStrings.put( "NPC.Preview", "Graphical preview of the character" );

		guiStrings.put( "ConversationReferencesList.Title", "Conversation references" );
		guiStrings.put( "ConversationReferencesList.ListTitle", "List of conversation references" );
		guiStrings.put( "ConversationReferencesList.Information", "Each character can have attached a set of conversation references. This way, when the player talks to the character, a certain conversation will be executed. Each conversation reference can have conditions, so it will be executed only in a specific state of the game." );
		guiStrings.put( "ConversationReferencesList.ColumnHeader0", "Conversation referenced" );
		guiStrings.put( "ConversationReferencesList.ColumnHeader1", "Has conditions" );

		guiStrings.put( "ConversationReference.Title", "Conversation reference" );
		guiStrings.put( "ConversationReference.Documentation", "Full description of the conversation reference" );
		guiStrings.put( "ConversationReference.ConversationId", "Id of the referenced conversation" );
		guiStrings.put( "ConversationReference.Conditions", "Conditions of the conversation reference" );

		guiStrings.put( "ConversationsList.Title", "Conversations" );
		guiStrings.put( "ConversationsList.ListTitle", "List of conversations" );
		guiStrings.put( "ConversationsList.Information", "These conversations can be displayed when the player chooses to talk to a certain character, or during special events. These conversations have blocks of straight text, as well as parts where the player can choose an option, that will take some effect in the development of the conversation." );
		guiStrings.put( "ConversationsList.ColumnHeader0", "Conversation identifier" );
		guiStrings.put( "ConversationsList.ColumnHeader1", "Number of lines" );
		guiStrings.put( "ConversationsList.LinesNumber", "{#0} lines" );

		guiStrings.put( "Conversation.OptionPreviewConversation", "Preview conversation" );
		guiStrings.put( "Conversation.OptionPreviewPartialConversation", "Preview conversation from this node" );
		guiStrings.put( "Conversation.OptionAddDialogueNode", "Add dialogue node" );
		guiStrings.put( "Conversation.OptionAddOptionNode", "Add option node" );
		guiStrings.put( "Conversation.OptionDeleteNode", "Delete node" );
		guiStrings.put( "Conversation.OptionLinkNode", "Link node to..." );
		guiStrings.put( "Conversation.OptionMoveNode", "Move node to..." );
		guiStrings.put( "Conversation.OptionAddGoBackTag", "Add go-back tag" );
		guiStrings.put( "Conversation.OptionRemoveGoBackTag", "Remove go-back tag" );
		guiStrings.put( "Conversation.OperationAddChild", "Add child" );
		guiStrings.put( "Conversation.ConfirmationAddChildToNodeWithEffects", "Are you sure that you want to add a child to the selected terminal node?\nIf a child is added, the effects of this node will be deleted" );
		guiStrings.put( "Conversation.OperationModeNode", "Move node" );
		guiStrings.put( "Conversation.ErrorMoveNode", "Cannot move the selected node to the given position" );
		guiStrings.put( "Conversation.OperationLinkNode", "Link node" );
		guiStrings.put( "Conversation.ErrorLinkNode", "Cannot link the selected node with the given node" );
		guiStrings.put( "Conversation.ConfirmationLinkNode", "Are you sure that you want to link this node to another one?\nIf a link is added, the effect of this node will be deleted" );
		guiStrings.put( "Conversation.OperationDeleteNode", "Delete node" );
		guiStrings.put( "Conversation.ConfirmDeleteNode", "Are you sure that you want to delete this node?\nThe node will be deleted along with its children, if they have no links with the conversation.\nDeleted data will be permanently lost" );
		guiStrings.put( "Conversation.OperationDeleteLink", "Delete link" );
		guiStrings.put( "Conversation.ConfirmationDeleteLink", "Are you sure that you want to delete the link with the child?\nAll nodes without a father node will be lost.\nDeleted data will be irrecoverable" );
		guiStrings.put( "Conversation.OperationDeleteOption", "Delete option" );
		guiStrings.put( "Conversation.ConfirmationDeleteOption", "Are you sure that you want to delete this option?\nAll nodes without a father node will be lost.\nDeleted data will be irrecoverable" );
		guiStrings.put( "Conversation.OperationAddGoBackTag", "Add go-back tag" );
		guiStrings.put( "Conversation.ConfirmationAddGoBackTag", "Are you sure that you want to add a go-back tag to the selected terminal node?\nIf the tag is added, the effects of this node will be deleted" );
		guiStrings.put( "Conversation.StatusBarNormal", "Status: Normal" );
		guiStrings.put( "Conversation.StatusWaitingToMove", "Status: Waiting destination node to move selected node" );
		guiStrings.put( "Conversation.StatusWaitingToLink", "Status: Waiting destination node to be linked as a child with the selected node" );

		guiStrings.put( "LinesPanel.NoNodeSelected", "No node selected" );
		guiStrings.put( "LinesPanel.DialogueNode", "Dialogue node" );
		guiStrings.put( "LinesPanel.OptionNode", "Option node" );

		guiStrings.put( "ConversationLine.PlayerName", "Player" );
		guiStrings.put( "ConversationLine.NewOption", "New option" );
		guiStrings.put( "ConversationLine.DefaultText", "New line" );

		guiStrings.put( "ResourcesList.SelectResourcesBlock", "Resources block to be used on the element preview" );
		guiStrings.put( "ResourcesList.ResourcesBlockNumber", "Resources block #" );

		guiStrings.put( "PlayerPosition.Title", "Position of the player" );
		guiStrings.put( "PlayerPosition.PositionPanel", "Select the position in which the player will appear" );

		guiStrings.put( "TreeNode.AddElement", "Add element" );
		guiStrings.put( "TreeNode.DeleteElement", "Delete element" );
		guiStrings.put( "TreeNode.MoveElementUp", "Move element up" );
		guiStrings.put( "TreeNode.MoveElementDown", "Move element down" );
		guiStrings.put( "TreeNode.RenameElement", "Rename element..." );

		guiStrings.put( "TreeNode.AddElement0", "Add chapter" );
		guiStrings.put( "TreeNode.AddElement1", "Add scenes list" );
		guiStrings.put( "TreeNode.AddElement2", "Add scene" );
		guiStrings.put( "TreeNode.AddElement3", "Add exits list" );
		guiStrings.put( "TreeNode.AddElement4", "Add exit" );
		guiStrings.put( "TreeNode.AddElement5", "Add item references list" );
		guiStrings.put( "TreeNode.AddElement6", "Add item reference" );
		guiStrings.put( "TreeNode.AddElement7", "Add NPC references list" );
		guiStrings.put( "TreeNode.AddElement8", "Add NPC reference" );
		guiStrings.put( "TreeNode.AddElement9", "Add cutscenes list" );
		guiStrings.put( "TreeNode.AddElement10", "Add slidescene" );
		guiStrings.put( "TreeNode.AddElement11", "Add books list" );
		guiStrings.put( "TreeNode.AddElement12", "Add book" );
		guiStrings.put( "TreeNode.AddElement13", "Add paragraphs list" );
		guiStrings.put( "TreeNode.AddElement14", "Add title paragraph" );
		guiStrings.put( "TreeNode.AddElement15", "Add text paragraph" );
		guiStrings.put( "TreeNode.AddElement16", "Add bullet paragraph" );
		guiStrings.put( "TreeNode.AddElement17", "Add image paragraph" );
		guiStrings.put( "TreeNode.AddElement18", "Add items list" );
		guiStrings.put( "TreeNode.AddElement19", "Add item" );
		guiStrings.put( "TreeNode.AddElement20", "Add actions list" );
		guiStrings.put( "TreeNode.AddElement21", "Add \"Examine\" action" );
		guiStrings.put( "TreeNode.AddElement22", "Add \"Grab\" action" );
		guiStrings.put( "TreeNode.AddElement23", "Add \"Use\" action" );
		guiStrings.put( "TreeNode.AddElement24", "Add \"Use with\" action" );
		guiStrings.put( "TreeNode.AddElement25", "Add \"Give to\" action" );
		guiStrings.put( "TreeNode.AddElement26", "Add player" );
		guiStrings.put( "TreeNode.AddElement27", "Add NPCs list" );
		guiStrings.put( "TreeNode.AddElement28", "Add NPC" );
		guiStrings.put( "TreeNode.AddElement29", "Add conversation references list" );
		guiStrings.put( "TreeNode.AddElement30", "Add conversation reference" );
		guiStrings.put( "TreeNode.AddElement31", "Add conversations list" );
		guiStrings.put( "TreeNode.AddElement32", "Add tree conversation" );
		guiStrings.put( "TreeNode.AddElement33", "Add graph conversation" );
		guiStrings.put( "TreeNode.AddElement34", "Add resources" );
		guiStrings.put( "TreeNode.AddElement35", "Add next scene" );
		guiStrings.put( "TreeNode.AddElement36", "Add end scene" );

		guiStrings.put( "GeneralText.Yes", "Yes" );
		guiStrings.put( "GeneralText.No", "No" );
		guiStrings.put( "GeneralText.OK", "OK" );
		guiStrings.put( "GeneralText.Cancel", "Cancel" );
		guiStrings.put( "GeneralText.Close", "Close" );
		guiStrings.put( "GeneralText.Information", "Information" );
		guiStrings.put( "GeneralText.PreviewText", "This is a preview text line" );
		guiStrings.put( "GeneralText.EditConditions", "Edit conditions" );
		guiStrings.put( "GeneralText.EditEffects", "Edit effects" );
		guiStrings.put( "GeneralText.EditPostEffects", "Edit post effects" );

		guiStrings.put( "DefaultValue.AdventureTitle", "Adventure title" );
		guiStrings.put( "DefaultValue.ChapterTitle", "Chapter title" );
		guiStrings.put( "DefaultValue.SceneId", "SceneId" );

		guiStrings.put( "Element.Name0", "Chapter" );
		guiStrings.put( "Element.Name1", "Scenes" );
		guiStrings.put( "Element.Name2", "Scene" );
		guiStrings.put( "Element.Name3", "Exits" );
		guiStrings.put( "Element.Name4", "Exit" );
		guiStrings.put( "Element.Name5", "Item references" );
		guiStrings.put( "Element.Name6", "Item reference" );
		guiStrings.put( "Element.Name7", "Character references" );
		guiStrings.put( "Element.Name8", "Character reference" );
		guiStrings.put( "Element.Name9", "Cutscenes" );
		guiStrings.put( "Element.Name10", "Slidescene" );
		guiStrings.put( "Element.Name11", "Books" );
		guiStrings.put( "Element.Name12", "Book" );
		guiStrings.put( "Element.Name13", "Paragraphs" );
		guiStrings.put( "Element.Name14", "Title paragraph" );
		guiStrings.put( "Element.Name15", "Text paragraph" );
		guiStrings.put( "Element.Name16", "Bullet paragraph" );
		guiStrings.put( "Element.Name17", "Image paragraph" );
		guiStrings.put( "Element.Name18", "Items" );
		guiStrings.put( "Element.Name19", "Item" );
		guiStrings.put( "Element.Name20", "Actions" );
		guiStrings.put( "Element.Name21", "Examine" );
		guiStrings.put( "Element.Name22", "Grab" );
		guiStrings.put( "Element.Name23", "Use" );
		guiStrings.put( "Element.Name24", "Use with" );
		guiStrings.put( "Element.Name25", "Give to" );
		guiStrings.put( "Element.Name26", "Player" );
		guiStrings.put( "Element.Name27", "Characters" );
		guiStrings.put( "Element.Name28", "Character" );
		guiStrings.put( "Element.Name29", "Conversation references" );
		guiStrings.put( "Element.Name30", "Conversation reference" );
		guiStrings.put( "Element.Name31", "Conversations" );
		guiStrings.put( "Element.Name32", "Tree conversation" );
		guiStrings.put( "Element.Name33", "Graph conversation" );
		guiStrings.put( "Element.Name34", "Resources block" );
		guiStrings.put( "Element.Name35", "Next scene" );
		guiStrings.put( "Element.Name36", "End scene" );
		guiStrings.put( "Element.Ref", "Ref: {#0}" );
		guiStrings.put( "Element.BookParagraph", "Book paragraph" );
		guiStrings.put( "Element.Action", "Action" );
		guiStrings.put( "Element.Effects", "Effects" );
		guiStrings.put( "Element.PostEffects", "Post effects" );
	}

	/**
	 * Returns the element name for the given element identifier.
	 * 
	 * @param element
	 *            Element identifier
	 * @return Element identifier, "Error" if the element was not found
	 */
	public static String getElementName( int element ) {
		return getText( "Element.Name" + element );
	}

	/**
	 * Returns the string associated with the given identifier.
	 * 
	 * @param identifier
	 *            Identifier to search for the string
	 * @return String retrieved from the text base, "Error" if the text was not found
	 */
	public static String getText( String identifier ) {
		String text = null;

		if( guiStrings.containsKey( identifier ) )
			text = guiStrings.getProperty( identifier );
		else {
			text = "Error";
			System.err.println( "Identifier \"" + identifier + "\" not found" );
		}

		return text;
	}

	/**
	 * Returns the string associated with the given identifier. This method also replaces occurrences in the original
	 * string with words passed through the call. Every placeholder for the strings have all the form "#n", where <i>n</i>
	 * is the index of the string to replace the placeholder in the given array. This method takes only one string to
	 * replace.
	 * 
	 * @param identifier
	 *            Identifier to search for the string
	 * @param parameter
	 *            String to replace the placeholder in the returned string
	 * @return String retrieved from the text base, "Error" if the text was not found
	 */
	public static String getText( String identifier, String parameter ) {
		String text = null;

		if( guiStrings.containsKey( identifier ) ) {
			text = guiStrings.getProperty( identifier );
			text = text.replace( "{#0}", parameter );
		} else {
			text = "Error";
			System.err.println( "Identifier \"" + identifier + "\" not found" );
		}

		return text;
	}

	/**
	 * Returns the string associated with the given identifier. This method also replaces occurrences in the original
	 * string with words passed through the call. Every placeholder for the strings have all the form "#n", where <i>n</i>
	 * is the index of the string to replace the placeholder in the given array. This method takes an array of strings
	 * to replace.
	 * 
	 * @param identifier
	 *            Identifier to search for the string
	 * @param parameters
	 *            Array of strings to replace the placeholders in the returned string
	 * @return String retrieved from the text base, "Error" if the text was not found
	 */
	public static String getText( String identifier, String[] parameters ) {
		String text = null;

		if( guiStrings.containsKey( identifier ) ) {
			text = guiStrings.getProperty( identifier );
			for( int i = 0; i < parameters.length; i++ )
				text = text.replace( "{#" + i + "}", parameters[i] );
		} else {
			text = "Error";
			System.err.println( "Identifier \"" + identifier + "\" not found" );
		}

		return text;
	}
}
