package br.com.curso.aws_project01.model;


import br.com.curso.aws_project01.enums.EventType;
import lombok.Data;

//@Data
public class Envelop {
    public EventType getEnventType() {
        return enventType;
    }

    public void setEnventType(EventType enventType) {
        this.enventType = enventType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private EventType enventType;
    private String data;
}
