package nc.ukma.thor.spms.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
public class TraitRepositoryJdbcImpl implements MyRepository<Trait>, TraitRepository{
	
	private static final String INSERT_TRAIT_SQL = "INSERT INTO trait (name, category_id) VALUES(?,?);";
	private static final String UPDATE_TRAIT_SQL = "UPDATE trait SET name=?, category_id=? WHERE id=?;";
	private static final String DELETE_TRAIT_SQL = "DELETE FROM trait WHERE id=?;";
	private static final String GET_TRAIT_BY_ID_SQL = "SELECT * FROM trait WHERE id=?;";
	private static final String GET_TRAITS_BY_TRAITCATEGORY_TRAIT_SQL = "SELECT * FROM trait WHERE category_id=?;";
	
	private static final RowMapper<Trait> TRAIT_MAPPER = new TraitMapper();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(Trait trait) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			 PreparedStatement ps = connection.prepareStatement(INSERT_TRAIT_SQL, new String[] {"id"});
			 ps.setString(1, trait.getName());
			 ps.setShort(2, trait.getTraitCategory().getId());
	         return ps;
		},keyHolder);
		trait.setId((long) keyHolder.getKey());
	}

	@Override
	public void update(Trait trait) {
		jdbcTemplate.update(UPDATE_TRAIT_SQL,
				new Object [] { trait.getName(), trait.getTraitCategory().getId(), trait.getId()});
	}

	@Override
	public void delete(Trait trait) {
		jdbcTemplate.update(DELETE_TRAIT_SQL, trait.getId());
	}

	@Override
	public Trait getById(long id) {
		try{
			return jdbcTemplate.queryForObject(GET_TRAIT_BY_ID_SQL,
					new Object[] { id }, TRAIT_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public List<Trait> getTraitsByTraitCategory(TraitCategory traitCategory) {
		return jdbcTemplate.query(GET_TRAITS_BY_TRAITCATEGORY_TRAIT_SQL,
				new Object[] { traitCategory.getId() },
				TRAIT_MAPPER);
	}
	
	private static final class TraitMapper implements RowMapper<Trait>{
		@Override
		public Trait mapRow(ResultSet rs, int rowNum) throws SQLException {
			Trait trait = new Trait();
			trait.setId(rs.getLong("id"));
			trait.setName(rs.getString("name"));
			trait.setTraitCategory(new TraitCategory(rs.getShort("category_id")));
			return trait;
		}
	}

}
