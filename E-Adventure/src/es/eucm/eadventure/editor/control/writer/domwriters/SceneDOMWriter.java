package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;

public class SceneDOMWriter {

	/**
	 * Private constructor.
	 */
	private SceneDOMWriter( ) {}

	public static Node buildDOM( Scene scene, boolean initialScene ) {
		Element sceneElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			sceneElement = doc.createElement( "scene" );
			sceneElement.setAttribute( "id", scene.getId( ) );
			if( initialScene )
				sceneElement.setAttribute( "start", "yes" );
			else
				sceneElement.setAttribute( "start", "no" );
			
			sceneElement.setAttribute("playerLayer", Integer.toString(scene.getPlayerLayer()));
			sceneElement.setAttribute("playerScale", Float.toString(scene.getPlayerScale()));

			// Append the documentation (if avalaible)
			if( scene.getDocumentation( ) != null ) {
				Node sceneDocumentationNode = doc.createElement( "documentation" );
				sceneDocumentationNode.appendChild( doc.createTextNode( scene.getDocumentation( ) ) );
				sceneElement.appendChild( sceneDocumentationNode );
			}

			// Append the resources
			for( Resources resources : scene.getResources( ) ) {
				Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_SCENE );
				doc.adoptNode( resourcesNode );
				sceneElement.appendChild( resourcesNode );
			}

			// Append the name
			Node nameNode = doc.createElement( "name" );
			nameNode.appendChild( doc.createTextNode( scene.getName( ) ) );
			sceneElement.appendChild( nameNode );

			// Append the default inital position (if avalaible)
			if( scene.hasDefaultPosition( ) ) {
				Element initialPositionElement = doc.createElement( "default-initial-position" );
				initialPositionElement.setAttribute( "x", String.valueOf( scene.getDefaultX( ) ) );
				initialPositionElement.setAttribute( "y", String.valueOf( scene.getDefaultY( ) ) );
				sceneElement.appendChild( initialPositionElement );
			}

			// Append the exits (if there is at least one)
			if( !scene.getExits( ).isEmpty( ) ) {
				Node exitsElement = doc.createElement( "exits" );

				// Append every single exit
				for( Exit exit : scene.getExits( ) ) {
					// Create the exit element
					Element exitElement = doc.createElement( "exit" );
					exitElement.setAttribute( "x", String.valueOf( exit.getX( ) ) );
					exitElement.setAttribute( "y", String.valueOf( exit.getY( ) ) );
					exitElement.setAttribute( "width", String.valueOf( exit.getWidth( ) ) );
					exitElement.setAttribute( "height", String.valueOf( exit.getHeight( ) ) );

					// Append the documentation (if avalaible)
					if( exit.getDocumentation( ) != null ) {
						Node exitDocumentationNode = doc.createElement( "documentation" );
						exitDocumentationNode.appendChild( doc.createTextNode( exit.getDocumentation( ) ) );
						exitElement.appendChild( exitDocumentationNode );
					}
					
					//Append the default exit look (if available)
					ExitLook defaultLook =exit.getDefaultExitLook( );
					if ( defaultLook!=null){
						Element exitLook = doc.createElement( "exit-look" );
						if (defaultLook.getExitText( )!=null)
							exitLook.setAttribute( "text", defaultLook.getExitText( ) );
						if (defaultLook.getCursorPath( )!=null)
							exitLook.setAttribute( "cursor-path", defaultLook.getCursorPath( ) );
						
						if (defaultLook.getExitText( )!=null || defaultLook.getCursorPath( )!=null)
							exitElement.appendChild( exitLook );
					}

					// Append the next-scene structures
					for( NextScene nextScene : exit.getNextScenes( ) ) {
						// Create the next-scene element
						Element nextSceneElement = doc.createElement( "next-scene" );
						nextSceneElement.setAttribute( "idTarget", nextScene.getTargetId( ) );

						// Append the destination position (if avalaible)
						if( nextScene.hasPlayerPosition( ) ) {
							nextSceneElement.setAttribute( "x", String.valueOf( nextScene.getPositionX( ) ) );
							nextSceneElement.setAttribute( "y", String.valueOf( nextScene.getPositionY( ) ) );
						}

						// Append the conditions (if avalaible)
						if( !nextScene.getConditions( ).isEmpty( ) ) {
							Node conditionsNode = ConditionsDOMWriter.buildDOM( nextScene.getConditions( ) );
							doc.adoptNode( conditionsNode );
							nextSceneElement.appendChild( conditionsNode );
						}
						
						//Append the default exit look (if available)
						ExitLook look =nextScene.getExitLook( );
						if ( look!=null){
							Element exitLook = doc.createElement( "exit-look" );
							if (look.getExitText( )!=null)
								exitLook.setAttribute( "text", look.getExitText( ) );
							if (look.getCursorPath( )!=null)
								exitLook.setAttribute( "cursor-path", look.getCursorPath( ) );
							
							if (look.getExitText( )!=null || look.getCursorPath( )!=null)
								nextSceneElement.appendChild( exitLook );
						}


						// Append the effects (if avalaible)
						if( !nextScene.getEffects( ).isEmpty( ) ) {
							Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, nextScene.getEffects( ) );
							doc.adoptNode( effectsNode );
							nextSceneElement.appendChild( effectsNode );
						}

						// Append the post-effects (if avalaible)
						if( !nextScene.getPostEffects( ).isEmpty( ) ) {
							Node postEffectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.POST_EFFECTS, nextScene.getPostEffects( ) );
							doc.adoptNode( postEffectsNode );
							nextSceneElement.appendChild( postEffectsNode );
						}

						// Append the next scene
						exitElement.appendChild( nextSceneElement );
					}
					// Append the exit
					exitsElement.appendChild( exitElement );
				}
				// Append the list of exits
				sceneElement.appendChild( exitsElement );
			}

			// Add the item references (if there is at least one)
			if( !scene.getItemReferences( ).isEmpty( ) ) {
				Node itemsNode = doc.createElement( "objects" );

				// Append every single item reference
				for( ElementReference itemReference : scene.getItemReferences( ) ) {
					// Create the item reference element
					Element itemReferenceElement = doc.createElement( "object-ref" );
					itemReferenceElement.setAttribute( "idTarget", itemReference.getIdTarget( ) );
					itemReferenceElement.setAttribute( "x", String.valueOf( itemReference.getX( ) ) );
					itemReferenceElement.setAttribute( "y", String.valueOf( itemReference.getY( ) ) );
					itemReferenceElement.setAttribute( "scale", String.valueOf( itemReference.getScale()));
					if (itemReference.getLayer()!=-1)
						itemReferenceElement.setAttribute( "layer", String.valueOf( itemReference.getLayer()));
					if (itemReference.getInfluenceArea().isExists()) {
						itemReferenceElement.setAttribute( "hasInfluenceArea", "yes");
						InfluenceArea ia = itemReference.getInfluenceArea();
						itemReferenceElement.setAttribute( "influenceX", String.valueOf(ia.getX() ));
						itemReferenceElement.setAttribute( "influenceY", String.valueOf(ia.getY() ));
						itemReferenceElement.setAttribute( "influenceWidth", String.valueOf(ia.getWidth() ));
						itemReferenceElement.setAttribute( "influenceHeight", String.valueOf(ia.getHeight() ));
					} else {
						itemReferenceElement.setAttribute( "hasInfluenceArea", "no");
					}

					// Append the documentation (if avalaible)
					if( itemReference.getDocumentation( ) != null ) {
						Node itemDocumentationNode = doc.createElement( "documentation" );
						itemDocumentationNode.appendChild( doc.createTextNode( itemReference.getDocumentation( ) ) );
						itemReferenceElement.appendChild( itemDocumentationNode );
					}

					// Append the conditions (if avalaible)
					if( !itemReference.getConditions( ).isEmpty( ) ) {
						Node conditionsNode = ConditionsDOMWriter.buildDOM( itemReference.getConditions( ) );
						doc.adoptNode( conditionsNode );
						itemReferenceElement.appendChild( conditionsNode );
					}

					// Append the exit
					itemsNode.appendChild( itemReferenceElement );
				}
				// Append the list of exits
				sceneElement.appendChild( itemsNode );
			}

			// Add the character references (if there is at least one)
			if( !scene.getCharacterReferences( ).isEmpty( ) ) {
				Node charactersNode = doc.createElement( "characters" );

				// Append every single character reference
				for( ElementReference characterReference : scene.getCharacterReferences( ) ) {
					// Create the character reference element
					Element npcReferenceElement = doc.createElement( "character-ref" );
					npcReferenceElement.setAttribute( "idTarget", characterReference.getIdTarget( ) );
					npcReferenceElement.setAttribute( "x", String.valueOf( characterReference.getX( ) ) );
					npcReferenceElement.setAttribute( "y", String.valueOf( characterReference.getY( ) ) );
					npcReferenceElement.setAttribute( "scale", String.valueOf( characterReference.getScale()));
					if (characterReference.getLayer()!=-1)
						npcReferenceElement.setAttribute( "layer", String.valueOf( characterReference.getLayer()));
					if (characterReference.getInfluenceArea().isExists()) {
						npcReferenceElement.setAttribute( "hasInfluenceArea", "yes");
						InfluenceArea ia = characterReference.getInfluenceArea();
						npcReferenceElement.setAttribute( "influenceX", String.valueOf(ia.getX() ));
						npcReferenceElement.setAttribute( "influenceY", String.valueOf(ia.getY() ));
						npcReferenceElement.setAttribute( "influenceWidth", String.valueOf(ia.getWidth() ));
						npcReferenceElement.setAttribute( "influenceHeight", String.valueOf(ia.getHeight() ));
					} else {
						npcReferenceElement.setAttribute( "hasInfluenceArea", "no");
					}


					// Append the documentation (if avalaible)
					if( characterReference.getDocumentation( ) != null ) {
						Node itemDocumentationNode = doc.createElement( "documentation" );
						itemDocumentationNode.appendChild( doc.createTextNode( characterReference.getDocumentation( ) ) );
						npcReferenceElement.appendChild( itemDocumentationNode );
					}

					// Append the conditions (if avalaible)
					if( !characterReference.getConditions( ).isEmpty( ) ) {
						Node conditionsNode = ConditionsDOMWriter.buildDOM( characterReference.getConditions( ) );
						doc.adoptNode( conditionsNode );
						npcReferenceElement.appendChild( conditionsNode );
					}

					// Append the exit
					charactersNode.appendChild( npcReferenceElement );
				}
				// Append the list of exits
				sceneElement.appendChild( charactersNode );
			}
			
			// Append the exits (if there is at least one)
			if( !scene.getActiveAreas( ).isEmpty( ) ) {
				Node aasElement = doc.createElement( "active-areas" );

				// Append every single exit
				for( ActiveArea activeArea : scene.getActiveAreas( ) ) {
					// Create the active area element
					Element aaElement = doc.createElement( "active-area" );
					if (activeArea.getId() != null)
						aaElement.setAttribute( "id", activeArea.getId());
					aaElement.setAttribute( "x", String.valueOf( activeArea.getX( ) ) );
					aaElement.setAttribute( "y", String.valueOf( activeArea.getY( ) ) );
					aaElement.setAttribute( "width", String.valueOf( activeArea.getWidth( ) ) );
					aaElement.setAttribute( "height", String.valueOf( activeArea.getHeight( ) ) );

					// Append the documentation (if avalaible)
					if( activeArea.getDocumentation( ) != null ) {
						Node exitDocumentationNode = doc.createElement( "documentation" );
						exitDocumentationNode.appendChild( doc.createTextNode( activeArea.getDocumentation( ) ) );
						aaElement.appendChild( exitDocumentationNode );
					}
					
					// Append the conditions (if avalaible)
					if( !activeArea.getConditions( ).isEmpty( ) ) {
						Node conditionsNode = ConditionsDOMWriter.buildDOM( activeArea.getConditions( ) );
						doc.adoptNode( conditionsNode );
						aaElement.appendChild( conditionsNode );
					}

					
					// Create the description
					Node descriptionNode = doc.createElement( "description" );

					// Create and append the name, brief description and detailed description
					Node aaNameNode = doc.createElement( "name" );
					aaNameNode.appendChild( doc.createTextNode( activeArea.getName( ) ) );
					descriptionNode.appendChild( aaNameNode );

					Node briefNode = doc.createElement( "brief" );
					briefNode.appendChild( doc.createTextNode( activeArea.getDescription( ) ) );
					descriptionNode.appendChild( briefNode );

					Node detailedNode = doc.createElement( "detailed" );
					detailedNode.appendChild( doc.createTextNode( activeArea.getDetailedDescription( ) ) );
					descriptionNode.appendChild( detailedNode );

					// Append the description
					aaElement.appendChild( descriptionNode );
					
					// Append the actions (if there is at least one)
					if( !activeArea.getActions( ).isEmpty( ) ) {
						// Create the actions node
						Node actionsNode = doc.createElement( "actions" );

						// For every action
						for( Action action : activeArea.getActions( ) ) {
							Element actionElement = null;

							// Create the element
							switch( action.getType( ) ) {
								case Action.EXAMINE:
									actionElement = doc.createElement( "examine" );
									break;
								case Action.GRAB:
									actionElement = doc.createElement( "grab" );
									break;
								case Action.USE:
									actionElement = doc.createElement( "use" );
									break;
								case Action.USE_WITH:
									actionElement = doc.createElement( "use-with" );
									actionElement.setAttribute( "idTarget", action.getIdTarget( ) );
									break;
								case Action.GIVE_TO:
									actionElement = doc.createElement( "give-to" );
									actionElement.setAttribute( "idTarget", action.getIdTarget( ) );
									break;
								case Action.CUSTOM:
									actionElement = doc.createElement( "custom" );
									actionElement.setAttribute("name", ((CustomAction) action).getName());
									if (((CustomAction) action).isNeedsGoTo())
										actionElement.setAttribute("needsGoTo", "yes");
									else
										actionElement.setAttribute("needsGoTo", "no");
									actionElement.setAttribute("keepDistance", "" + ((CustomAction) action).getKeepDistance());
									for (Resources resources : ((CustomAction) action).getResources())
										actionElement.appendChild(ResourcesDOMWriter.buildDOM(resources, ResourcesDOMWriter.RESOURCES_CUSTOM_ACTION));
									break;
								case Action.CUSTOM_INTERACT:
									actionElement = doc.createElement( "custom-interact" );
									actionElement.setAttribute("idTarget", action.getIdTarget());
									actionElement.setAttribute("name", ((CustomAction) action).getName());
									if (((CustomAction) action).isNeedsGoTo())
										actionElement.setAttribute("needsGoTo", "yes");
									else
										actionElement.setAttribute("needsGoTo", "no");
									actionElement.setAttribute("keepDistance", "" + ((CustomAction) action).getKeepDistance());
									for (Resources resources : ((CustomAction) action).getResources())
										actionElement.appendChild(ResourcesDOMWriter.buildDOM(resources, ResourcesDOMWriter.RESOURCES_CUSTOM_ACTION));
									break;							

							}

							// Append the documentation (if avalaible)
							if( action.getDocumentation( ) != null ) {
								Node actionDocumentationNode = doc.createElement( "documentation" );
								actionDocumentationNode.appendChild( doc.createTextNode( action.getDocumentation( ) ) );
								actionElement.appendChild( actionDocumentationNode );
							}

							// Append the conditions (if avalaible)
							if( !action.getConditions( ).isEmpty( ) ) {
								Node conditionsNode = ConditionsDOMWriter.buildDOM( action.getConditions( ) );
								doc.adoptNode( conditionsNode );
								actionElement.appendChild( conditionsNode );
							}

							// Append the effects (if avalaible)
							if( !action.getEffects( ).isEmpty( ) ) {
								Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, action.getEffects( ) );
								doc.adoptNode( effectsNode );
								actionElement.appendChild( effectsNode );
							}

							// Append the action element
							actionsNode.appendChild( actionElement );
						}

						// Append the actions node
						aaElement.appendChild( actionsNode );
					}
					
					// Append the exit
					aasElement.appendChild( aaElement );
				}
				// Append the list of exits
				sceneElement.appendChild( aasElement );
			}
			
			// Append the barriers (if there is at least one)
			if( !scene.getBarriers( ).isEmpty( ) ) {
				Node barriersElement = doc.createElement( "barriers" );

				// Append every single barrier
				for( Barrier barrier : scene.getBarriers( ) ) {
					// Create the active area element
					Element barrierElement = doc.createElement( "barrier" );
					barrierElement.setAttribute( "x", String.valueOf( barrier.getX( ) ) );
					barrierElement.setAttribute( "y", String.valueOf( barrier.getY( ) ) );
					barrierElement.setAttribute( "width", String.valueOf( barrier.getWidth( ) ) );
					barrierElement.setAttribute( "height", String.valueOf( barrier.getHeight( ) ) );

					// Append the documentation (if avalaible)
					if( barrier.getDocumentation( ) != null ) {
						Node exitDocumentationNode = doc.createElement( "documentation" );
						exitDocumentationNode.appendChild( doc.createTextNode( barrier.getDocumentation( ) ) );
						barrierElement.appendChild( exitDocumentationNode );
					}
					
					// Append the conditions (if avalaible)
					if( !barrier.getConditions( ).isEmpty( ) ) {
						Node conditionsNode = ConditionsDOMWriter.buildDOM( barrier.getConditions( ) );
						doc.adoptNode( conditionsNode );
						barrierElement.appendChild( conditionsNode );
					}

					
					// Create the description
					Node descriptionNode = doc.createElement( "description" );

					// Create and append the name, brief description and detailed description
					Node aaNameNode = doc.createElement( "name" );
					aaNameNode.appendChild( doc.createTextNode( barrier.getName( ) ) );
					descriptionNode.appendChild( aaNameNode );

					Node briefNode = doc.createElement( "brief" );
					briefNode.appendChild( doc.createTextNode( barrier.getDescription( ) ) );
					descriptionNode.appendChild( briefNode );

					Node detailedNode = doc.createElement( "detailed" );
					detailedNode.appendChild( doc.createTextNode( barrier.getDetailedDescription( ) ) );
					descriptionNode.appendChild( detailedNode );

					// Append the description
					barrierElement.appendChild( descriptionNode );
					
					// Append the barrier
					barriersElement.appendChild( barrierElement );
				}
				// Append the list of exits
				sceneElement.appendChild( barriersElement );
			}
			
			// Add the atrezzo item references (if there is at least one)
			if( !scene.getAtrezzoReferences( ).isEmpty( ) ) {
				Node atrezzoNode = doc.createElement( "atrezzo" );

				// Append every single atrezzo reference
				for( ElementReference atrezzoReference : scene.getAtrezzoReferences( ) ) {
					// Create the atrezzo reference element
					Element atrezzoReferenceElement = doc.createElement( "atrezzo-ref" );
					atrezzoReferenceElement.setAttribute( "idTarget", atrezzoReference.getIdTarget( ) );
					atrezzoReferenceElement.setAttribute( "x", String.valueOf( atrezzoReference.getX( ) ) );
					atrezzoReferenceElement.setAttribute( "y", String.valueOf( atrezzoReference.getY( ) ) );
					atrezzoReferenceElement.setAttribute( "scale", String.valueOf( atrezzoReference.getScale()));
					if (atrezzoReference.getLayer()!=-1)
						atrezzoReferenceElement.setAttribute( "layer", String.valueOf( atrezzoReference.getLayer()));

					// Append the documentation (if avalaible)
					if( atrezzoReference.getDocumentation( ) != null ) {
						Node itemDocumentationNode = doc.createElement( "documentation" );
						itemDocumentationNode.appendChild( doc.createTextNode( atrezzoReference.getDocumentation( ) ) );
						atrezzoReferenceElement.appendChild( itemDocumentationNode );
					}

					// Append the conditions (if avalaible)
					if( !atrezzoReference.getConditions( ).isEmpty( ) ) {
						Node conditionsNode = ConditionsDOMWriter.buildDOM( atrezzoReference.getConditions( ) );
						doc.adoptNode( conditionsNode );
						atrezzoReferenceElement.appendChild( conditionsNode );
					}

					// Append the atrezzo reference
					atrezzoNode.appendChild( atrezzoReferenceElement );
				}
				// Append the list of atrezzo references
				sceneElement.appendChild( atrezzoNode );
			}

			if( scene.getTrajectory() != null ) {
				Node trajectoryNode = TrajectoryDOMWriter.buildDOM(scene.getTrajectory());
				doc.adoptNode( trajectoryNode );
				sceneElement.appendChild( trajectoryNode );
			}


		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return sceneElement;
	}
}
