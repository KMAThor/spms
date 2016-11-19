package nc.ukma.thor.spms.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;

@Repository
public class TraitCategoryRepositoryJdbcImpl implements TraitCategoryRepository{
	
	private static final String INSERT_TRAITCATEGORY_SQL = "INSERT INTO trait_category (name) VALUES(?);";
	private static final String UPDATE_TRAITCATEGORY_SQL = "UPDATE trait_category SET name = ? WHERE id = ?;";
	private static final String DELETE_TRAITCATEGORY_SQL = "DELETE FROM trait_category WHERE id = ?;";
	private static final String GET_TRAITCATEGORY_BY_ID_SQL = "SELECT * FROM trait_category WHERE id = ?;";
	
	private static final String GET_TRAITCATEGORY_BY_TRAIT_SQL = "SELECT * FROM trait_category "
			+ "INNER JOIN trait ON trait_category.id = trait.category_id "
			+ "WHERE trait.id = ?;";
	
	private static final String GET_ALL_TRAITCATEGORIS_WITH_TRAITS_SQL = "SELECT "
			+ "trait_category.id AS trait_category_id, trait_category.name AS trait_category_name, "
			+ "trait.id AS trait_id, trait.name AS trait_name FROM trait_category "
			+ "LEFT JOIN trait ON trait_category.id = trait.category_id "
			+ "ORDER BY trait_category_id, trait_id;";
	
	private static final String GET_ALL_TRAITCATEGORIS_WITH_TRAITS_BY_PROJECT_SQL = "SELECT "
			+ "trait_category.id AS trait_category_id, trait_category.name AS trait_category_name, "
			+ "trait.id AS trait_id, trait.name AS trait_name FROM trait_category "
			+ "INNER JOIN trait ON trait_category.id = trait.category_id "
			+ "INNER JOIN trait_project ON trait.id=trait_project.trait_id "
			+ "WHERE trait_project.project_id = ? "
			+ "ORDER BY trait_category_id, trait_id;";
	
	private static final String GET_ALL_TRAITCATEGORIS_WITH_TRAITS_BY_MEETING_SQL = "SELECT "
			+ "trait_category.id AS trait_category_id, trait_category.name AS trait_category_name, "
			+ "trait.id AS trait_id, trait.name AS trait_name FROM trait_category "
			+ "INNER JOIN trait ON trait_category.id = trait.category_id "
			+ "INNER JOIN trait_project ON trait.id=trait_project.trait_id "
			+ "INNER JOIN project ON trait_project.project_id=project.id "
			+ "INNER JOIN team ON project.id=team.project_id "
			+ "INNER JOIN meeting ON team.id=meeting.team_id "
			+ "WHERE meeting.id = ? "
			+ "ORDER BY trait_category_id, trait_id;";
	
	private static final RowMapper<TraitCategory> TRAITCATEGORY_MAPPER = new TraitCategoryMapper();
	private static final TraitCategoriesWithTraitsMapper TRAITCATEGORIS_WITH_TRAITS_MAPPER = new TraitCategoriesWithTraitsMapper();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(TraitCategory tc) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSERT_TRAITCATEGORY_SQL, new String [] {"id"});
			ps.setString(1, tc.getName());
			return ps;
		}, keyHolder);
		tc.setId(keyHolder.getKey().shortValue());
	}

	@Override
	public void update(TraitCategory tc) {
		jdbcTemplate.update(UPDATE_TRAITCATEGORY_SQL, tc.getName(), tc.getId());
	}

	@Override
	public void delete(TraitCategory tc) {
		jdbcTemplate.update(DELETE_TRAITCATEGORY_SQL, tc.getId());
	}

	@Override
	public TraitCategory getById(Long id) {
		try{
			return jdbcTemplate.queryForObject(GET_TRAITCATEGORY_BY_ID_SQL, 
					new Object [] { id }, TRAITCATEGORY_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public TraitCategory getCategoryByTrait(Long traitId) {
		try{
			return jdbcTemplate.queryForObject(GET_TRAITCATEGORY_BY_TRAIT_SQL, 
					new Object [] { traitId }, TRAITCATEGORY_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public List<TraitCategory> getAllCategoriesWithTraits() {
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_ALL_TRAITCATEGORIS_WITH_TRAITS_SQL);
		return TRAITCATEGORIS_WITH_TRAITS_MAPPER.mapRows(rows);
	}
	
	@Override
	public List<TraitCategory> getAllCategoriesWithTraitsByProject(Long projectId) {
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_ALL_TRAITCATEGORIS_WITH_TRAITS_BY_PROJECT_SQL, 
				new Object[] { projectId });
		return TRAITCATEGORIS_WITH_TRAITS_MAPPER.mapRows(rows);
	}
	
	@Override
	public List<TraitCategory> getAllCategoriesWithTraitsByMeeting(Long meetingId) {
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_ALL_TRAITCATEGORIS_WITH_TRAITS_BY_MEETING_SQL, 
				new Object[] { meetingId });
		return TRAITCATEGORIS_WITH_TRAITS_MAPPER.mapRows(rows);
	}

	private static final class TraitCategoryMapper implements RowMapper<TraitCategory>{
		@Override
		public TraitCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
			TraitCategory traitCategory = new TraitCategory();
			traitCategory.setId(rs.getShort("id"));
			traitCategory.setName(rs.getString("name"));
			return traitCategory;
		}
	}
	
	private static final class TraitCategoriesWithTraitsMapper {

		public List<TraitCategory> mapRows(List<Map<String, Object>> rows) {
			List<TraitCategory> traitCategories = new ArrayList<TraitCategory>();
			TraitCategory currentTraitCategory = null;
			
			for (Map<String, Object> map : rows) {
				short traitCategoryId = ((Integer) map.get("trait_category_id")).shortValue();
				String traitCategoryName = (String) map.get("trait_category_name");
				
				if (currentTraitCategory == null || traitCategoryId != currentTraitCategory.getId()) {
					if(currentTraitCategory != null) traitCategories.add(currentTraitCategory);
					currentTraitCategory = new TraitCategory(traitCategoryId, traitCategoryName);
				}
				
				if(map.get("trait_id") != null){
					Trait trait = new Trait(((Long) map.get("trait_id")).longValue(), (String) map.get("trait_name"));
					currentTraitCategory.addTrait(trait);
				}
			}
			traitCategories.add(currentTraitCategory);
			return traitCategories;
		}
	}

}
