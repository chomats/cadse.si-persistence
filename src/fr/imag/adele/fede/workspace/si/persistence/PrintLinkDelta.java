package fr.imag.adele.fede.workspace.si.persistence;

import java.util.Collection;

import fr.imag.adele.cadse.core.CadseError;
import fr.imag.adele.cadse.core.CadseException;
import fr.imag.adele.cadse.core.CompactUUID;
import fr.imag.adele.cadse.core.Item;
import fr.imag.adele.cadse.core.ItemType;
import fr.imag.adele.cadse.core.Link;
import fr.imag.adele.cadse.core.LinkType;
import fr.imag.adele.cadse.core.attribute.IAttributeType;
import fr.imag.adele.cadse.core.delta.CreateOperation;
import fr.imag.adele.cadse.core.delta.DeleteOperation;
import fr.imag.adele.cadse.core.delta.ItemDelta;
import fr.imag.adele.cadse.core.delta.LinkDelta;
import fr.imag.adele.cadse.core.delta.OperationType;
import fr.imag.adele.cadse.core.delta.SetAttributeOperation;
import fr.imag.adele.cadse.core.delta.WLWCOperation;
import fr.imag.adele.cadse.core.transaction.LogicalWorkspaceTransaction;

public class PrintLinkDelta implements LinkDelta {

	private String _linkType;
	private ItemDelta _destItem;
	private String _info;
	private int _version;

	public PrintLinkDelta(String linkType, ItemDelta destItem) {
		_linkType = linkType;
		_destItem = destItem;
	}

	@Override
	public void addCompatibleVersions(int... versions) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeDestination(Item att) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearCompatibleVersions() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean commitSetAttribute(IAttributeType<?> type, Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(DeleteOperation options) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public IAttributeType<?> getAttributeType(
			SetAttributeOperation setAttributeOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link getBaseLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getCompatibleVersions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getDestination() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getDestination(boolean resolved) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompactUUID getDestinationId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDestinationName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getDestinationOperation() throws CadseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDestinationQualifiedName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDestinationShortName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemType getDestinationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHandleIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkDelta getInverseLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getItemOperationParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLinkTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getResolvedDestination() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompactUUID getSourceId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemDelta getSourceOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAggregation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAnnotation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isComposition() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCreatedLink() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDerived() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLinkResolved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMappingLink() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOther() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPart() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequire() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStatic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveAfter(Link link) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveBefore(Link link) throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean mustDeleteDestination() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mustDeleteSource() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resolve() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void restore() throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttribute(String key, Object v, Object oldValue,
			boolean loaded) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIndex(int index, boolean loaded) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInfo(String info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInfo(String info, boolean loaded) {
		_info = info;

	}

	@Override
	public void setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVersion(int version) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVersion(int version, boolean loaded) {
		_version = version;
	}

	@Override
	public void toString(String begin, StringBuilder sb, String tab) {
		sb.append(begin);
		sb.append("<link type =\"").append(_linkType).append("\"");
		if (_info != null)
			sb.append(" info=\"").append(_info).append("\"");
		sb.append(" version=\"").append(_version).append("\"");
		sb.append(" dest-name=\"").append(_destItem.getName()).append("\"");
		sb.append(" dest-qname=\"").append(_destItem.getQualifiedName()).append("\"");
		sb.append(" dest-type=\"").append(_destItem.getItemTypeId()).append("\"");
		sb.append(" dest-id=\"").append(_destItem.getId()).append("\"");
		
		sb.append("\\>\n");
	}

	@Override
	public LinkType getLinkType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void commitDelete() throws CadseException {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getAttribute(String key, boolean returnDefault) {
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
	public void addError(String msg, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addError(CadseError e) {
		// TODO Auto-generated method stub
		
	}

}
