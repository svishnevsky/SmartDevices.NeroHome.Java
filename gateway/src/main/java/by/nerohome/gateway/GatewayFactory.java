package by.nerohome.gateway;

import java.security.cert.X509Certificate;

public class GatewayFactory {
    
    public OledoHostGateway createGateway(
        String host,
        Integer port,
        X509Certificate certificate) {
        return new OledoHostGateway(host, port, certificate);
    }
}
