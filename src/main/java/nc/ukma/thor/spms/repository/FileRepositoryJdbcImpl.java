package nc.ukma.thor.spms.repository;

import nc.ukma.thor.spms.entity.File;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileRepositoryJdbcImpl implements FileRepository {
    @Override
    public void add(File entity) {

    }

    @Override
    public void update(File entity) {

    }

    @Override
    public void delete(File entity) {

    }

    @Override
    public File getById(Long id) {
        return null;
    }

    @Override
    public List<File> getFilesByProject(Project project) {
        return null;
    }

    @Override
    public List<File> getFilesByTeam(Team team) {
        return null;
    }
}
