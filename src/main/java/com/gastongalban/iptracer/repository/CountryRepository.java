package com.gastongalban.iptracer.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gastongalban.iptracer.model.CountryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class CountryRepository{

    private static String TABLE_NAME = "country_data";
    private static String INSERT_QUERY = "INSERT INTO country_data VALUES(?,?,?,?,?,?)";
    private static String SELECT_QUERY = "SELECT * FROM country_data WHERE country_code=?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Optional<CountryData>> mapper;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CountryRepository(RowMapper<Optional<CountryData>> mapper) {
        this.mapper = mapper;
    }


    public Integer insert(CountryData entity) throws JsonProcessingException {
        return this.jdbcTemplate.update(INSERT_QUERY,
                entity.getCountryCode(),
                entity.getName(),
                objectMapper.writeValueAsString(entity.getLanguages()),
                objectMapper.writeValueAsString(entity.getCurrencyCodes()),
                objectMapper.writeValueAsString(entity.getTimezones()),
                objectMapper.writeValueAsString(entity.getLocation()));
    }

    public Optional<CountryData> search(String countryCode){
        Optional<CountryData> countryData;
        try {
            countryData = this.jdbcTemplate.queryForObject(SELECT_QUERY, mapper,countryCode);
            return countryData;
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
