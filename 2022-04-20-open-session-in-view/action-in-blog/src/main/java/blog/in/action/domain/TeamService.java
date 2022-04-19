package blog.in.action.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Team registerTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team findById(long id) {
        return teamRepository.findById(id).orElseThrow();
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }
}