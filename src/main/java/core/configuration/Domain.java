package core.configuration;

class Domain {

    private String domain;
    private String address;
    private short port;

    Domain(String domain) {
        this.domain = domain;
        setAddressAndPort(domain);
    }

    Domain(String address, int port) {
        this.domain = String.format("%s:%d", checkAddress(address), port);
        setAddressAndPort(address, port);
    }

    private String checkAddress(String address) {
        if (address.contains(":")) {
            address.replace(":", "");
        }
        return address;
    }

    private void setAddressAndPort(String address, int port) {
        this.address = address;
        this.port = (short) port;
    }

    private void setAddressAndPort(String domain) {
        this.address = domain.substring(0, domain.indexOf(":"));
        this.port = Short.parseShort(domain.substring(domain.lastIndexOf(":") + 1));
    }
}
