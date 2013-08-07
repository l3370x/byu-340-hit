package persistence;

import core.model.exception.HITException;

public class StorageUnitDAO extends ProductContainerDAO {

	@Override
	public Iterable<DataTransferObject> getAll() throws HITException {
		DataTransferObject dto = new DataTransferObject();
		dto.setValue(COL_IS_STORAGE_UNIT, true);
		
		return super.get(dto);
	}

}
