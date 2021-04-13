package com.gastongalban.iptracer.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gastongalban.iptracer.model.CountryData;
import com.gastongalban.iptracer.model.CurrencyData;
import com.gastongalban.iptracer.model.TraceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class TraceDataRepository {

    private static String TABLE_NAME = "trace_data";
    private static String SELECT_MAX = "SELECT MAX(distanceToBsAs) FROM trace_data";
    private static String SELECT_MIN = "SELECT MIN(distanceToBsAs) FROM trace_data";
    private static String SELECT_ALL = "SELECT * FROM trace_data";


    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private ObjectMapper objectMapper = new ObjectMapper();

    private RowMapper<Optional<CountryData>> mapper;

    private List<TraceData> traceData = new LinkedList<>();

    @PostConstruct
    public void setUp() {
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName(TABLE_NAME).usingGeneratedKeyColumns("id");
    }

    public int insert(TraceData traceData){
        return this.simpleJdbcInsert.executeAndReturnKey(this.createSqlParametersSource(traceData)).intValue();
    }

    private MapSqlParameterSource createSqlParametersSource(TraceData traceData){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("ip", traceData.getIp())
                .addValue("country", traceData.getCountry())
                .addValue("date", traceData.getDate())
                .addValue("iso_code", traceData.getISOCode())
                .addValue("distanceToBsAs", traceData.getDistanceToBsAs());
        try {
            return parameterSource
                    .addValue("languages",objectMapper.writeValueAsString(traceData.getLanguages()))
                    .addValue("timezones",objectMapper.writeValueAsString(traceData.getDatesData()))
                    .addValue("currency_data",objectMapper.writeValueAsString(traceData.getCurrencyData()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return parameterSource;
        }
    }

    public Optional<Double> getMaxDistance() {
        Double aDouble = this.jdbcTemplate.queryForObject(SELECT_MAX, Double.class);
        return Optional.of(aDouble);
    }

    public Optional<Double> getMinDistance() {
        Double aDouble = this.jdbcTemplate.queryForObject(SELECT_MIN, Double.class);
        return Optional.of(aDouble);
    }

    public List<TraceData> getAll() {
        List<Map<String, Object>> maps = this.jdbcTemplate.queryForList(SELECT_ALL);
        List<TraceData> traceData = new ArrayList<>();

        for (Map<String, Object> map : maps)
        {
            TraceData.Builder builder = new TraceData.Builder();
            try {
                List<CurrencyData> currencyData = objectMapper.readValue((byte[]) map.get("currency_data"), new TypeReference<List<CurrencyData>>() {});
                List<String> languages = objectMapper.readValue((byte[]) map.get("languages"), new TypeReference<List<String>>() {});
                List<String> dateData = objectMapper.readValue((byte[]) map.get("timezones"), new TypeReference<List<String>>() {});
                LocalDateTime localDate = (LocalDateTime) map.get("date");
                Date date = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
                builder
                        .withCountry((String) map.get("country"))
                        .withIp((String) map.get("ip"))
                        .withDate(date)
                        .withDistanceToBsAs((Double) map.get("distanceToBsAs"))
                        .withISOCode((String) map.get("iso_code"));
                dateData.forEach(builder::withDateData);
                languages.forEach(builder::withLanguage);
                currencyData.forEach(builder::withCurrencyData);
                traceData.add(builder.build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return traceData;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
