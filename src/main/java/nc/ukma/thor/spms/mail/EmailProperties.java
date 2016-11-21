package nc.ukma.thor.spms.mail;

public enum EmailProperties {

    GMAIL("smtp.gmail.com", 587);


    private String host;
    private Integer port;

    EmailProperties(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}