package pt.ua.deti.tqs.covidinfoapi.sourceapi;

import lombok.*;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class ExternalAPI {

    private final String baseUrl;
    private final String apiKey;

    public enum AvailableAPI {
        COVID_19, VAC_COVID
    }

}
