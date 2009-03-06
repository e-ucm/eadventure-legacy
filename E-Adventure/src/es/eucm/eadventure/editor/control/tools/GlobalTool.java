package es.eucm.eadventure.editor.control.tools;

/**
 * Global Tool. All the tools modifying data that is not attached directly to a chapter (i.e. adventure data) but to the
 * descriptor, or which involves adding or removing chapters must extend this class.
 * GlobalTools are not treated as normal Tools, as the ChapterListDataControl will add them not to a certain ToolManager
 * but to all of them in order to ensure that the tool is reachable from any chapter. This is also essential to gurantee 
 * the linearity of actions
 * @author Javier
 *
 */
public abstract class GlobalTool extends Tool{

}
