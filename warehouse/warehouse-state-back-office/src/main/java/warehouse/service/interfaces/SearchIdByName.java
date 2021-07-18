package warehouse.service.interfaces;

import warehouse.dto.ParentDto;

public interface SearchIdByName {

	void searchExecutorOperatorIdByUsernameAndSetToDto(String username, ParentDto dto);
}
