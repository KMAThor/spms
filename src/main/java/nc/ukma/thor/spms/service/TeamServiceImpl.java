package nc.ukma.thor.spms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.repository.TeamRepository;

@Service
public class TeamServiceImpl extends AbstractService<Team> /*implements TeamService*/{

	@Autowired
	public TeamServiceImpl(TeamRepository repository) {
		super(repository);
	}

}
