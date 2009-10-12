package fr.imag.adele.fede.workspace.si.persistence;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.imag.adele.cadse.core.CadseDomain;
import fr.imag.adele.cadse.core.CadseError;
import fr.imag.adele.cadse.core.CadseException;
import fr.imag.adele.cadse.core.CompactUUID;
import fr.imag.adele.cadse.core.ContentChangeInfo;
import fr.imag.adele.cadse.core.ContentItem;
import fr.imag.adele.cadse.core.DerivedLink;
import fr.imag.adele.cadse.core.DerivedLinkDescription;
import fr.imag.adele.cadse.core.EventFilter;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemDescription;
import fr.imag.adele.cadse.core.ItemDescriptionRef;
import fr.imag.adele.cadse.core.ItemFilter;
import fr.imag.adele.cadse.core.ItemState;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.Link;
import fr.imag.adele.cadse.core.LinkType;
import fr.imag.adele.cadse.core.LogicalWorkspace;
import fr.imag.adele.cadse.core.WorkspaceListener;
import fr.imag.adele.cadse.core.attribute.BooleanAttributeType;
import fr.imag.adele.cadse.core.attribute.IAttributeType;
import fr.imag.adele.cadse.core.attribute.IntegerAttributeType;
import fr.imag.adele.cadse.core.attribute.StringAttributeType;
import fr.imag.adele.cadse.core.attribute.URLAttributeType;
import fr.imag.adele.cadse.core.delta.CreateOperation;
import fr.imag.adele.cadse.core.delta.DeleteOperation;
import fr.imag.adele.cadse.core.delta.ImmutableWorkspaceDelta;
import fr.imag.adele.cadse.core.delta.ItemDelta;
import fr.imag.adele.cadse.core.delta.LinkDelta;
import fr.imag.adele.cadse.core.delta.MappingOperation;
import fr.imag.adele.cadse.core.delta.OperationType;
import fr.imag.adele.cadse.core.delta.OrderOperation;
import fr.imag.adele.cadse.core.delta.SetAttributeOperation;
import fr.imag.adele.cadse.core.delta.WLWCOperation;
import fr.imag.adele.cadse.core.internal.IWorkingLoadingItems;
import fr.imag.adele.cadse.core.internal.IWorkspaceNotifier;
import fr.imag.adele.cadse.core.key.ISpaceKey;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;
import fr.imag.adele.cadse.core.util.IErrorCollector;
import fr.imag.adele.cadse.core.util.OrderWay;

public class PrintItemDelta implements ItemDelta {
	
	private String _name;
	private String _qname;
	HashMap<String , Object> _atts = new HashMap<String, Object>();
	private CompactUUID _id;
	private CompactUUID _type;
	private ArrayList<LinkDelta> links=  new ArrayList<LinkDelta>();
	
	public PrintItemDelta(CompactUUID id, CompactUUID type) {
		_id = id;
		_type = type;
	}

	@Override
	public void addMappingOperaion(MappingOperation mappingOperation)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public LinkDelta addOutgoingItem(LinkType lt, Item destination)
			throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkDelta createLink(LinkType lt, Item destination)
			throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(DeleteOperation operation, int flag)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doubleClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getAdapter(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<LinkDelta> getAddedLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getAttribute(IAttributeType<T> att, boolean returnDefault) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getBaseItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBooleanAttribut(BooleanAttributeType key,
			boolean defaultValue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<LinkDelta> getDeletedLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getIncomingItems(boolean acceptDelete,
			boolean acceptAdd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getIncomingItems(LinkType lt, boolean acceptDelete,
			boolean acceptAdd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LinkDelta> getIncomingLinkOperations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LinkDelta> getIncomingLinks(boolean acceptDelete,
			boolean acceptAdd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntAttribut(IntegerAttributeType key, int defaultValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CompactUUID getItemTypeId() {
		return _type;
	}

	@Override
	public <T extends MappingOperation> T getMappingOperation(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MappingOperation[] getMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogicalWorkspace getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getOpenCompositeParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderOperation> getOrdersOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderOperation> getOrdersOperation(LinkType lt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getOutgoingItem(LinkType lt, boolean resovledOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getOutgoingItem(String linkNameID, boolean resovledOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getOutgoingItems(boolean acceptDeletedLink,
			boolean resovledOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getOutgoingItems(boolean acceptDeletedLink,
			LinkType lt, boolean resovledOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getOutgoingItems(boolean acceptDeletedLink,
			String linkType, boolean resovledOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkDelta getOutgoingLink(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkDelta getOutgoingLink(LinkType linkType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkDelta getOutgoingLink(LinkType lt, CompactUUID destId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkDelta getOutgoingLink(String lt, CompactUUID destId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkDelta getOutgoingLinkOperation(Link l) throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkDelta getOutgoingLinkOperation(String type,
			ItemDescriptionRef destination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<LinkDelta> getOutgoingLinkOperations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LinkDelta> getOutgoingLinkOperations(CompactUUID destId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LinkDelta> getOutgoingLinkOperations(LinkType linkType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Link> getOutgoingLinks(boolean acceptDeleted) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LinkDelta> getOutgoingLinks(CompactUUID destId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getParentInStorage() throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getParentInStorage(LinkType lt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkType getParentLinkTypeInStorage(Item parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link getPartLinkParent(boolean acceptDelete, boolean acceptAdd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getPartParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getPartParent(boolean attemptToRecreate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getPartParent(boolean attemptToRecreate,
			boolean acceptDeleted) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getPartParent(LinkType lt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getPartParent(LinkType lt, boolean attemptToRecreate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SetAttributeOperation getSetAttributeOperation(String key,
			boolean create) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStringAttribut(StringAttributeType key, String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemType getType(boolean createUnresolvedType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURLAttribut(URLAttributeType key)
			throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getWhyReadOnly() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends MappingOperation> T getorCreateMappingOperation(
			Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDoubleClick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFinishLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadAttribute(IAttributeType<?> key, Object value)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadAttribute(String key, Object value) throws CadseException {
		_atts.put(key, value);

	}

	@Override
	public void loadContent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadDerivedLink(String linkType, ItemDelta dest,
			boolean isAggregation, boolean isRequire, String linkInfo,
			String originLinkTypeID, CompactUUID uuidOriginLinkSourceTypeID,
			CompactUUID uuidOriginLinkDestinationTypeID, int version) {
		// TODO Auto-generated method stub

	}

	@Override
	public LinkDelta loadLink(String linkType, ItemDelta destItem)
			throws CadseException {
PrintLinkDelta  l = new PrintLinkDelta( linkType, destItem);
links.add(l);// TODO Auto-generated method stub
		return l;
	}

	@Override
	public void migratePartLink(Item newPartParent, LinkType lt)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean moveAfter(LinkDelta linkOne, Link linkTwo)
			throws CadseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveBefore(LinkDelta linkOne, Link linkTwo)
			throws CadseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void notifieChangedContent(ContentChangeInfo[] change) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<Item> propagateValue(IAttributeType<?> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Item> propagateValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetContentIsChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttribute(IAttributeType<?> key, String attributeName,
			Object newCurrentValue, boolean loaded) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setKey(ISpaceKey newkey) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModified(boolean value) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModified(boolean value, boolean loaded)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String shortname, boolean loaded) throws CadseException {
		_name = shortname;

	}

	@Override
	public void setParent(ItemDelta parent, LinkType lt,
			boolean createLinkIfNeed, boolean notify) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setQualifiedName(String uniqueName, boolean loaded)
			throws CadseException {
		_qname = uniqueName;
	}

	@Override
	public void setReadOnly(boolean readOnly, boolean loaded)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShortName(String shortname, boolean loaded)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUniqueName(String uniqueName, boolean loaded)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUpdate(boolean update) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValid(boolean valid, boolean loaded) {
		// TODO Auto-generated method stub

	}

	@Override
	public void syncOutgoingLinks() {
		// TODO Auto-generated method stub

	}

	@Override
	public void toString(StringBuilder sb, String tab) {
		try {
			sb.append(tab).append("<item ");
			XMLPersistance.writeXML(sb, "name", _name);
			XMLPersistance.writeXML(sb, "qname", _qname);
			XMLPersistance.writeXML(sb, "id", _id.toString());
			XMLPersistance.writeXML(sb, "type", _type.toString());
			
			sb.append(">\n");
			for (String k : _atts.keySet()) {
				sb.append(tab).append("  <attribute ");
				XMLPersistance.writeXML(sb, "name", k);
				XMLPersistance.writeXML(sb, "value", _atts.get(k).toString());
				sb.append("\\>\n");
			}
			
			for (LinkDelta l : links) {
				l.toString(tab+"  ", sb, tab);
			}
			
			sb.append(tab).append("</item>\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void toStringShort(StringBuilder sb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(WorkspaceListener listener, int eventFilter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(WorkspaceListener listener, EventFilter eventFilter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildComposite() throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canCreateLink(LinkType linkType, CompactUUID destItemId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canEditContent(String slashedPath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSetAttribute(String attrName, Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsComponent(CompactUUID itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsPartChild(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contentIsLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(boolean deleteContent) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<WorkspaceListener> filter(int eventFilter,
			ImmutableWorkspaceDelta delta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void finishLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Link> getAggregations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentItem getAndCreateContentManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CadseDomain getCadseDomain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<CompactUUID> getComponentIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getComponentInfo(CompactUUID itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Item> getComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getCompositeParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentItem getContentItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<DerivedLinkDescription> getDerivedLinkDescriptions(
			ItemDescription source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<DerivedLink> getDerivedLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName() {
		return _name;
	}

	@Override
	public CompactUUID getId() {
		return _id;
	}

	@Override
	public Item getIncomingItem(LinkType linkType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getIncomingItems(LinkType linkType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getIncomingItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link getIncomingLink(LinkType linkType, CompactUUID srcId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Link> getIncomingLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Link> getIncomingLinks(LinkType linkType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISpaceKey getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLastVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public File getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogicalWorkspace getLogicalWorkspace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getMainMappingContent(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getMappingContents(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getMappingContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Item getOutgoingItem(String linkTypeName, CompactUUID itemId,
			boolean resolvedOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getOutgoingItems(LinkType lt, boolean resolvedOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getOutgoingItems(boolean resolvedOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getOutgoingItems(String typesLink,
			boolean resolvedOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Link> getOutgoingLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Link> getOutgoingLinks(LinkType linkType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getPartChild(CompactUUID destItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getPartChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Item> getPartChildren(LinkType linkType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getPartParent(ItemType itemType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getPartParentByName(String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkType getPartParentLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkType getPartParentLinkType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQualifiedDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQualifiedName() {
		return _qname;
	}

	@Override
	public String getQualifiedName(boolean recompute) throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShortName() {
		return getName();
	}

	@Override
	public ItemState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUniqueName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUniqueName(boolean recompute) throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int indexOf(Link link) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAccessible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAncestorOf(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isComposite() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInIncomingLinks(Link l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInOutgoingLinks(Link l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInstanceOf(ItemType it) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOrphan() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPartItem() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequierNewRev() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isResolved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRevModified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStatic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean itemHasContent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeContentItem() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(WorkspaceListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Link removeOutgoingItem(LinkType linkType, Item destination)
			throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setComponents(Set<ItemDescriptionRef> comp)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDerivedLinks(Set<DerivedLinkDescription> derivedLinks) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String name) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public Link setOutgoingItem(LinkType linkType, Item dest)
			throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Link> setOutgoingItems(LinkType linkType,
			Collection<Item> value) throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQualifiedName(String qualifiedName) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReadOnly(boolean readOnly) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShortName(String name) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setState(ItemState newState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUniqueName(String qualifiedName) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValid(boolean isValid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shadow(boolean deleteContent) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unload() throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getAttribute(IAttributeType<T> att) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getAttribute(String att) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getAttributeH(String att, boolean fromSuperIfNull) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAttributeKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getAttributeOwner(IAttributeType<T> att) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getAttributeWithDefaultValue(IAttributeType<T> att,
			T defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getAttributeWithDefaultValue(String att, T defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTWAttributeModified(IAttributeType<?> att) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAttribute(IAttributeType<?> att, Object value)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttribute(String att, Object value) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLocalAllAttributeTypes(
			List<IAttributeType<?>> allLocalAttrDefs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLocalAllAttributeTypes(
			Map<String, IAttributeType<?>> allLocalAttrDefs,
			boolean keepLastAttribute) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLocalAllAttributeTypes(
			List<IAttributeType<?>> allLocalAttrDefs, ItemFilter filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public IAttributeType<?>[] getLocalAllAttributeTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getLocalAllAttributeTypes(
			Map<String, IAttributeType<?>> allLocalAttrDefs,
			boolean keepLastAttribute, ItemFilter filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getLocalAllAttributeTypesKeys(Set<String> allLocalAttrDefs,
			ItemFilter filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public IAttributeType<?> getLocalAttributeType(String attrName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentItem _getContentItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addIncomingLink(Link link, boolean notify) {
		// TODO Auto-generated method stub

	}

	@Override
	public Link commitLoadCreateLink(LinkType lt, Item destination)
			throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean commitMove(OrderWay kind, Link l1, Link l2)
			throws CadseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean commitSetAttribute(IAttributeType<?> type, String key,
			Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void computeAttribute(String attributeName, Object theirsValue,
			Object baseValue, Object mineValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void computeAttributes() {
		// TODO Auto-generated method stub

	}

	@Override
	public void forceState(ItemState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T internalGetGenericOwnerAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T internalGetGenericOwnerAttribute(IAttributeType<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T internalGetOwnerAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T internalGetOwnerAttribute(IAttributeType<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadItem(IWorkingLoadingItems wl, ItemDelta itemOperation,
			IErrorCollector errorCollector) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeIncomingLink(Link link, boolean notify) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeOutgoingLink(Link link, boolean notify) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setFlag(int f, boolean flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIsStatic(boolean isStatic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParent(Item parent, LinkType lt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setType(ItemType itemType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVersion(int version) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(IWorkingLoadingItems items, ItemDelta desc,
			IWorkspaceNotifier notifie) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getAttribute(String key, boolean returnDefault) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAttributeType<?> getAttributeType(
			SetAttributeOperation setAttributeOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateOperation getCreateOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeleteOperation getDeleteOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SetAttributeOperation> getSetAttributeOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SetAttributeOperation getSetAttributeOperation(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAdded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDeleted() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public CompactUUID getOperationId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationType getOperationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WLWCOperation getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationType getParentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogicalWorkspaceTransaction getWorkingCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addInParent() throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeInParent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoaded(boolean loaded) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(SetAttributeOperation setAttributeOperation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(SetAttributeOperation setAttributeOperation, boolean check) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCreateOperation(CreateOperation createItemOperation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDeleteOperation(DeleteOperation deleteItemOperation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createPartParentLink() throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemDelta getParentItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetTWAttributeModified() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRequierNewRev(boolean flag) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRevModified(boolean flag) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTWAttributeModified(IAttributeType<?> att, boolean v)
			throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public LogicalWorkspaceTransaction getCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ISpaceKey getNextKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNextKey(ISpaceKey newK) throws CadseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addError(String msg, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addError(CadseError e) {
		// TODO Auto-generated method stub
		
	}

}
