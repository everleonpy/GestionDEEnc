package com.roshka.sifen.addon;

public class Envelope {
    public Object Header;
    public Body Body;
    public String env;
    public String text;

    public Object getHeader() {
        return Header;
    }

    public void setHeader(Object header) {
        Header = header;
    }

    public com.roshka.sifen.addon.Body getBody() {
        return Body;
    }

    public void setBody(com.roshka.sifen.addon.Body body) {
        Body = body;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}