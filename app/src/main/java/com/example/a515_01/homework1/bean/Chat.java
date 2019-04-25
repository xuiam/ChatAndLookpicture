package com.example.a515_01.homework1.bean;

/**
 * 聊天
 */
public class Chat {
    private String content;
    private boolean type;//true 小灵 false me

    public Chat() {
    }

    public Chat(String content, boolean type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
