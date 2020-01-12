package core.configuration;

public class HttpServerSettings {

    private Domain domain;

    public HttpServerSettings() {

    }


    public final HttpServerSettings setAddressAndPort(String address, int port){
        this.domain = new Domain(address,port);
        return this;
    }

    public final HttpServerSettings setDomain(String domain){
        this.domain = new Domain(domain);
        return this;
    }


}
