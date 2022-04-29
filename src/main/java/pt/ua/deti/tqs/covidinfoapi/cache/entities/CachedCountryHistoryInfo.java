package pt.ua.deti.tqs.covidinfoapi.cache.entities;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import pt.ua.deti.tqs.covidinfoapi.sourceapi.entities.vaccovid.VacCovidCountryHistoryData;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class CachedCountryHistoryInfo implements ISingleCache<List<VacCovidCountryHistoryData> > {

    @Generated
    @Getter
    private final static int CACHE_TTL = 5*60*1000;

    private List<VacCovidCountryHistoryData> covidCountryHistoryData;
    private Date timestamp;

    @Override
    public List<VacCovidCountryHistoryData> getCachedValue() {
        return covidCountryHistoryData;
    }

    @Override
    public void setCachedValue(List<VacCovidCountryHistoryData> vacCovidCountryHistoryData) {
        this.covidCountryHistoryData = vacCovidCountryHistoryData;
    }

    @Override
    public boolean isValid() {
        if (covidCountryHistoryData == null)
            return false;
        return Date.from(Instant.now()).getTime() - timestamp.getTime() <= CACHE_TTL;
    }

}
