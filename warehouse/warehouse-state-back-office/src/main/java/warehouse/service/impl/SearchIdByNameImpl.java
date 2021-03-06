package warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import warehouse.dto.ParentDto;
//import warehouse.exceptions.NotFoundException;
import warehouse.repo.OperatorStateBackOficeRepo;
import warehouse.service.interfaces.SearchIdByName;

@Slf4j
@Service
public class SearchIdByNameImpl implements SearchIdByName {
	@Autowired
	OperatorStateBackOficeRepo operatorRepo;

	@Override
	public void searchExecutorOperatorIdByUsernameAndSetToDto(String username, ParentDto dto) {
		log.debug("searchExecutorOperatorIdByUsernameAndSetToDto: dto {} by {}, username {} received", dto.toString(),
				dto.getClass(), username);
		Long executorOperatorId = (long) 0;
		if (!username.equalsIgnoreCase("admin")) {
			executorOperatorId = operatorRepo.findOperatorIdByUsername(username);
		}
		log.debug("searchExecutorOperatorIdByUsernameAndSetToDto:  executorOperatorId {}", executorOperatorId);
		dto.setExecutorOperatorId(executorOperatorId);
		log.debug("searchExecutorOperatorIdByUsernameAndSetToDto: dto {} by {} returned ", dto.toString(),
				dto.getClass());

	}

}
