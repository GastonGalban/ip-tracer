package com.gastongalban.iptracer.service;

import com.gastongalban.iptracer.client.currconv.CurrConvClient;
import com.gastongalban.iptracer.client.ip2country.IP2CountryClient;
import com.gastongalban.iptracer.model.CountryData;
import com.gastongalban.iptracer.model.CurrencyData;
import com.gastongalban.iptracer.model.Location;
import com.gastongalban.iptracer.model.TraceData;
import com.gastongalban.iptracer.repository.TraceDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TracerServiceTest {

    private static final String SOME_IP = "111.111.111.111";
    @InjectMocks
    private TracerService service;
    @Mock
    private CountryService countryService;
    @Mock
    private IP2CountryClient ip2CountryClient;
    @Mock
    private CurrConvClient currConvClient;
    @Mock
    private DistanceCalculator distanceCalculator;
    @Mock
    private TraceDataRepository traceDataRepository;
    @Mock
    private CountryData countryData;
    private List<String> currencyCodes = Collections.singletonList("ARG");
    private List<String> languages = Arrays.asList("Spanish (es)", "Guaraní (gn)");
    private List<String> timezones = Collections.singletonList("UTC-03:00");
    private Location location = new Location(-34d,-64d);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(countryData.getCurrencyCodes()).thenReturn(currencyCodes);
        when(countryData.getLanguages()).thenReturn(languages);
        when(countryData.getLocation()).thenReturn(location);
        when(countryData.getName()).thenReturn("ARGENTINA");
        when(countryData.getTimezones()).thenReturn(timezones);

    }

    @Test
    void traceTest() {
        when(ip2CountryClient.getCountryCode(eq(SOME_IP))).thenReturn(Optional.of("ARG"));
        when(countryService.getCountry(eq("ARG"))).thenReturn(Optional.of(countryData));
        when(currConvClient.getCurrency(eq("ARG"))).thenReturn(Optional.of(0.010835d));
        when(distanceCalculator.calculate(eq(location))).thenReturn(0d);

        Optional<TraceData> trace = service.trace(SOME_IP);

        assertTrue(trace.isPresent());
        TraceData traceData = trace.get();
        //assertEquals(Collections.singletonList("2021-04-10 13:14:50-0300"),traceData.getDatesData());
        assertEquals(Collections.singletonList(new CurrencyData("ARG",0.010835d)),traceData.getCurrencyData());
        assertEquals("ARGENTINA",traceData.getCountry());
        assertEquals(Arrays.asList("Spanish (es)", "Guaraní (gn)"),traceData.getLanguages());
        assertEquals(0d,traceData.getDistanceToBsAs());
        assertEquals(SOME_IP,traceData.getIp());
        assertEquals("ARG",traceData.getISOCode());

        verify(traceDataRepository).save(traceData);
    }
}