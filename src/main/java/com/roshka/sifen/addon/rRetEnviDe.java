package com.roshka.sifen.addon;

import java.io.Serializable;

@SuppressWarnings("serial")
public class rRetEnviDe implements Serializable 
{
    public rProtDe rProtDe;
    public String ns2;
    public String text;

    public com.roshka.sifen.addon.rProtDe getrProtDe() {
        return rProtDe;
    }

    public void setrProtDe(com.roshka.sifen.addon.rProtDe rProtDe) {
        this.rProtDe = rProtDe;
    }

    public String getNs2() {
        return ns2;
    }

    public void setNs2(String ns2) {
        this.ns2 = ns2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}