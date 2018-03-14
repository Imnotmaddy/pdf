package com.mistakes.model;

public class InputInformation {
    private long numberRows;
    private long percentOfMistakes;
    private String region;
    private String [] mistakes;

    public long getNumberRows() {
        return numberRows;
    }

    public void setNumberRows(long numberRows) {
        this.numberRows = numberRows;
    }

    public long getPercentOfMistakes() {
        return percentOfMistakes;
    }

    public void setPercentOfMistakes(long percentOfMistakes) {
        this.percentOfMistakes = percentOfMistakes;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String[] getMistakes() {
        return mistakes;
    }

    public void setMistakes(String[] mistakes) {
        this.mistakes = mistakes;
    }
}
