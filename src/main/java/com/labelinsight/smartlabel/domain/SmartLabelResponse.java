package com.labelinsight.smartlabel.domain;

import java.util.List;

public class SmartLabelResponse {
    List<SmartLabelProduct> content;

    public SmartLabelResponse(List<SmartLabelProduct> content) {
        this.content = content;
    }

    public List<SmartLabelProduct> getContent() {
        return content;
    }

    public void setContent(List<SmartLabelProduct> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SmartLabelResponse{" +
                "content=" + content +
                '}';
    }
}
