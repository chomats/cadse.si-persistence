package fr.imag.adele.fede.workspace.si.persistence;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import fr.imag.adele.cadse.core.CadseDomain;
import fr.imag.adele.cadse.core.CadseException;
import fr.imag.adele.cadse.core.CadseRuntime;
import fr.imag.adele.cadse.core.CompactUUID;
import fr.imag.adele.cadse.core.EventFilter;
import fr.imag.adele.cadse.core.IItemManager;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemDescriptionRef;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.Link;
import fr.imag.adele.cadse.core.LinkDescription;
import fr.imag.adele.cadse.core.LinkType;
import fr.imag.adele.cadse.core.ProjectAssociation;
import fr.imag.adele.cadse.core.WSModelState;
import fr.imag.adele.cadse.core.WorkspaceListener;
import fr.imag.adele.cadse.core.attribute.IAttributeType;
import fr.imag.adele.cadse.core.attribute.SetAttrVal;
import fr.imag.adele.cadse.core.delta.ImmutableWorkspaceDelta;
import fr.imag.adele.cadse.core.delta.ItemDelta;
import fr.imag.adele.cadse.core.delta.LinkDelta;
import fr.imag.adele.cadse.core.delta.MappingOperation;
import fr.imag.adele.cadse.core.delta.OrderOperation;
import fr.imag.adele.cadse.core.delta.SetAttributeOperation;
import fr.imag.adele.cadse.core.delta.WLWCOperationImpl;
import fr.imag.adele.cadse.core.internal.ILoggableAction;
import fr.imag.adele.cadse.core.internal.IWorkspaceNotifier;
import fr.imag.adele.cadse.core.key.ISpaceKey;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransactionListener;
import fr.imag.adele.cadse.core.ui.view.FilterContext;
import fr.imag.adele.cadse.core.ui.view.NewContext;
import fr.imag.adele.cadse.core.util.ElementsOrder;
import fr.imag.adele.cadse.core.var.ContextVariable;

public class Main {

	
	
	private static final class PrintLogicalW implements
			LogicalWorkspaceTransaction {
		@Override
		public void setLog(ILoggableAction log) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void remove(ItemDelta itemOperation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyDoubleClick(ItemDelta item) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyDeletedLink(LinkDelta link) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyDeletedItem(ItemDelta item) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyCreatedLink(LinkDelta link) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyCreatedItem(ItemDelta item) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyChangeLinkOrder(LinkDelta link,
				OrderOperation orderOperation) throws CadseException,
				CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyChangeAttribute(LinkDelta link,
				SetAttributeOperation attOperation) throws CadseException,
				CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyChangeAttribute(ItemDelta item,
				SetAttributeOperation attOperation) throws CadseException,
				CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyCancelCreatedLink(LinkDelta link) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyCancelCreatedItem(ItemDelta item) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void notifyAddMappingOperation(ItemDelta item,
				MappingOperation mappingOperation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isForcetoLoad() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isForceToSave() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Collection<ProjectAssociation> getProjectAssociationSet() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta getOrCreateItemOperation(Item itembase)
				throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta getOrCreateItemOperation(CompactUUID id, CompactUUID type,
				boolean add) throws CadseException, CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta getOrCreateItemOperation(CompactUUID id, CompactUUID type)
				throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta getOrCreateItemOperation(CompactUUID id)
				throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<WLWCOperationImpl> getOperations() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IWorkspaceNotifier getNotifier() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ElementsOrder<MappingOperation> getMappingOrder() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ILoggableAction getLog() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemType getItemType(CompactUUID id, boolean createUnresolvedType) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Item getBaseItem(CompactUUID id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean containsUniqueName(String un) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean containsSpaceKey(ISpaceKey key) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void addLoadedItem(Item item) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionRemoveOperation(WLWCOperationImpl operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionRemoveLink(LinkDescription linkDescription)
				throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionRemoveItem(ItemDescriptionRef itemDescriptionRef)
				throws CadseException, CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionRemoveAttribute(LinkDescription linkDescription,
				String key) throws CadseException, CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionRemoveAttribute(CompactUUID itemId, String key)
				throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionDelete(ItemDelta item) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionChangeAttribute(LinkDescription linkDescription,
				String key, Object value) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionChangeAttribute(CompactUUID itemId, String key,
				Object value) throws CadseException, CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionAddOperation(WLWCOperationImpl operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionAddLink(LinkDescription linkDescription)
				throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public ItemDelta actionAddItem(ItemDescriptionRef itemDescriptionRef,
				CompactUUID parent, LinkType lt) throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void actionAddAttribute(LinkDescription linkDescription, String key,
				Object value) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionAddAttribute(CompactUUID itemId, String key, Object value)
				throws CadseException, CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionAdd(ItemDelta item) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeLogicalWorkspaceTransactionListener(
				LogicalWorkspaceTransactionListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public LogicalWorkspaceTransactionListener[] getLogicalWorkspaceTransactionListener() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addLogicalWorkspaceTransactionListener(
				LogicalWorkspaceTransactionListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setState(WSModelState state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<Link> getUnresolvedLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Link> getUnresolvedLink(CompactUUID id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Item> getUnresolvedItem() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public WSModelState getState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<Item> getItems() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Item> getItems(ItemType it) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Item> getItems(String it) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<ItemType> getItemTypes() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemType getItemTypeByName(String shortName) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemType getItemType(CompactUUID itemTypeId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Item getItemByShortName(ItemType type, String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Item getItem(ISpaceKey key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Item getItem(String uniqueName) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int[] getCadseVersion() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CadseRuntime[] getCadseRuntime() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] getCadseName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CadseDomain getCadseDomain() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean existsItem(Item item, String shortName)
				throws CadseException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean existsItem(Item item) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public LinkType createUnresolvedLinkType(String linkTypeName,
				ItemType sourceType, ItemType destType) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean canDeleteLink(Link link) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canDeleteInverseLink(Link link) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setAttribute(Item item, IAttributeType<?> key, Object value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setAttribute(Item item, String key, Object value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void rollback() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeListener(WorkspaceListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadItems(Collection<URL> itemdescription)
				throws CadseException, IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public ItemDelta loadItem(Item item) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta loadItem(CompactUUID id, CompactUUID type)
				throws CadseException {
			return new PrintItemDelta(id, type);
		}

		@Override
		public ItemDelta loadItem(ItemDescriptionRef itemRef) throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void loadCadseModel(String qualifiedCadseModelName) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void load() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isUpdate() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isModified() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isCommitted() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ContextVariable getOldContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ContextVariable getNewContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LinkDelta getLink(Link link) throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<ItemDelta> getItemOperations() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta getItemOperation(CompactUUID id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta getItem(Item item) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta getItem(CompactUUID itemId, boolean showDeleteItem) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta getItem(CompactUUID id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T getAttribute(Item source, String key, boolean ownerOnly) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T getAttribute(Item source, IAttributeType<T> type,
				boolean ownerOnly) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<WorkspaceListener> filter(int filters,
				ImmutableWorkspaceDelta workspaceDelta) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LogicalWorkspaceTransaction createTransaction() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LinkDelta createLinkIfNeed(ItemDelta source, Item dest,
				LinkType linkType) throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemType createItemType(ItemType itemTypeType,
				CadseRuntime cadseName, ItemType superType, int intID,
				CompactUUID id, String shortName, String displayName,
				boolean hasContent, boolean isAbstract, IItemManager manager) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta createItemIfNeed(ItemType itemType, Item parent,
				LinkType partLinkType, String uniqueName, String shortName,
				SetAttrVal<?>... attributes) throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta createItemIfNeed(String uniqueName, String shortname,
				ItemType itemType, Item parent, LinkType partLinkType,
				Object... attributes) throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta createItem(ItemType itemType, Item parent, LinkType lt,
				CompactUUID id, String uniqueName, String shortName)
				throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemDelta createItem(ItemType itemType, Item parent,
				LinkType partLinkType) throws CadseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CadseRuntime createCadseRuntime(String name, CompactUUID runtimeId,
				CompactUUID definitionId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<Item> commit(boolean update, boolean forceToSave,
				boolean forceLoad,
				Collection<ProjectAssociation> projectAssociationSet)
				throws CadseException, IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void commit() throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void clear() throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void check_write() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addListener(WorkspaceListener l, EventFilter eventFilter) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addListener(WorkspaceListener l, int eventFilter) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public ContextVariable getContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void validateDeleteLink(LinkDelta link) throws CadseException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public NewContext[] getNewContextFrom(FilterContext context) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static void main(String[] args) {
		java.io.File dir = new java.io.File(args[0]);
		StringBuilder sb = new StringBuilder();
		PersistenceNew2009 p  = new PersistenceNew2009();
		
		if (args.length == 1) {
			File[] ser = dir.listFiles();
			for (int i = 0; i < ser.length; i++) {
				if (ser[i].getName().endsWith(".ser")) {
					try {
						p.loadFromPersistence(new PrintLogicalW(), ser[i].toURL()).toString(sb, "");
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CadseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		for (int i = 1; i < args.length; i++) {
			try {
				p.loadFromPersistence(new PrintLogicalW(), new File(dir, args[i]+".ser").toURL()).toString(sb, "");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CadseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(sb.toString());
	}
}
