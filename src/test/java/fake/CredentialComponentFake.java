package fake;

import com.rkd.binance.component.CredentialComponent;
import org.springframework.stereotype.Component;

@Component
public class CredentialComponentFake extends CredentialComponent {

    @Override
    public String getKey() {
        return "fakeKey";
    }

    @Override
    public String getSecret() {
        return "fakeSecret";
    }
}
