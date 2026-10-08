package org.acme.audit;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.*;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

@ApplicationScoped
public class AuditBridgeRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {

        // lets build a special custom error message for timeout
        onException(ExchangeTimedOutException.class)
                // here we tell Camel to continue routing
                .continued(true)
                // after it has built this special timeout error message body
                .setBody(simple("#${header.corId}-Time out error!!!"));

        from("netty-http:http://0.0.0.0:8090")
                .id("audit-bridge-route")
                .log(LoggingLevel.INFO, "HTTP Headers: ${headers}")
                .process(new AuditBridgeProcess());
    }
}

