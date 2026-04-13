import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.GlobalOpenTelemetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class OtlTracerConfig {

    @Bean
    public Tracer otelTracer() {
        System.out.println("Creating OpenTelemetry Tracer bean");
        return GlobalOpenTelemetry.getTracer("com.coolteck.productcatalogue");
    }
}