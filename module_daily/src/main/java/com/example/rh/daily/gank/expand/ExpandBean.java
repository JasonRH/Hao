package com.example.rh.daily.gank.expand;

/**
 * @author RH
 * @date 2019/1/17
 */
public class ExpandBean {
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private String who;

    public ExpandBean(String desc, String publishedAt, String type, String url, String who) {
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.type = type;
        this.url = url;
        this.who = who;
    }

    public Builder builder() {
        return new Builder();
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return url;
    }

    public String getWho() {
        return who;
    }

    public String getPublishedAt() {
        return publishedAt;
    }


    public String getType() {
        return type;
    }


    public static class Builder {
        private String desc;
        private String publishedAt;
        private String type;
        private String url;
        private String who;

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }


        public Builder setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder setWho(String who) {
            this.who = who;
            return this;
        }

        public ExpandBean build() {
            return new ExpandBean(desc, publishedAt, type, url, who);
        }
    }
}
