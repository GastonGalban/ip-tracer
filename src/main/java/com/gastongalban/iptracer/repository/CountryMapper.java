package com.gastongalban.iptracer.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gastongalban.iptracer.model.CountryData;

import com.gastongalban.iptracer.model.Location;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class CountryMapper implements RowMapper<Optional<CountryData>> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Optional<CountryData> mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            CountryData countryData = new CountryData(rs.getString("country_code"),
                    rs.getString("name"),
                    this.mapper.readValue(rs.getString("languages"), new TypeReference<List<String>>() {}),
                    this.mapper.readValue(rs.getString("location"), new TypeReference<Location>() {}),
                    this.mapper.readValue(rs.getString("timezones"), new TypeReference<List<String>>() {}),
                    this.mapper.readValue(rs.getString("currency_codes"), new TypeReference<List<String>>() {})
                    );
            return Optional.of(countryData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}