package fr.imag.adele.fede.workspace.si.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.UUID;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.transaction.delta.ItemDelta;

public interface ReadItemType {

	ItemDelta readItemType(UUID id);
	
	ItemType getItemTypeFromDelta(ItemDelta delta);

	void writeHeaderIfNead(Item item) throws FileNotFoundException, IOException;
}
