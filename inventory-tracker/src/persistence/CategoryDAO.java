package persistence;

import core.model.exception.HITException;

public class CategoryDAO extends ProductContainerDAO {

	@Override
	public Iterable<DataTransferObject> getAll() throws HITException {
		DataTransferObject dto = new DataTransferObject();
		dto.setValue(COL_IS_STORAGE_UNIT, false);
		
		return super.get(dto);
	}

}
