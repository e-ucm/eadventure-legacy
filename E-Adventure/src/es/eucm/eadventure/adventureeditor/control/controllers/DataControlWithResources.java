package es.eucm.eadventure.adventureeditor.control.controllers;

import java.util.List;

import es.eucm.eadventure.adventureeditor.control.controllers.general.ResourcesDataControl;

public abstract class DataControlWithResources extends DataControl {

	public abstract List<ResourcesDataControl> getResources( );

	public abstract int getResourcesCount( );

	public abstract ResourcesDataControl getLastResources( );

	public abstract int getSelectedResources( );

	public abstract void setSelectedResources( int selectedResources );
}
