package org.demo.post.domain.content;

import org.demo.post.domain.common.DatetimeInfo;

public abstract class Content {
    String contentText;
    final DatetimeInfo datetimeInfo;

    public Content(String contentText) {
        checkText(contentText);
        this.contentText = contentText;
        this.datetimeInfo = new DatetimeInfo();
    }

    public void updateContent(String updateContent){
        checkText(updateContent);
        this.contentText = updateContent;
        this.datetimeInfo.updateEditDatetime();
    }

    protected abstract void checkText(String contentText);

    public String getContentText() {
        return contentText;
    }
}
