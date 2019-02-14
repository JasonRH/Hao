package com.example.rh.daily.wanandroid;

/**
 * @author RH
 * @date 2019/1/17
 */
public class WanBean {
    private String title;
    private String author;
    private String time;
    private String type;
    private String category;
    private String url;
    private String tagName;
    private String tagUrl;
    private Boolean fresh;

    public WanBean(String title, String author, String time, String type, String category, String url, String tagName, String tagUrl, Boolean fresh) {
        this.title = title;
        this.time = time;
        this.type = type;
        this.category = category;
        this.url = url;
        this.author = author;
        this.tagName = tagName;
        this.tagUrl = tagUrl;
        this.fresh = fresh;
    }

    public Builder builder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagUrl() {
        return tagUrl;
    }

    public Boolean getFresh() {
        return fresh;
    }

    public static class Builder {
        private String title;
        private String author;
        private String time;
        private String type;
        private String category;
        private String url;
        private String tagName;
        private String tagUrl;
        private Boolean fresh;

        public Builder() {
        }


        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }


        public Builder setTime(String time) {
            this.time = time;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setTagName(String tagName) {
            this.tagName = tagName;
            return this;
        }

        public Builder setTagUrl(String tagUrl) {
            this.tagUrl = tagUrl;
            return this;
        }
        public Builder setFresh(Boolean fresh) {
            this.fresh = fresh;
            return this;
        }
        public WanBean build() {
            return new WanBean(title, author, time, type, category, url, tagName, tagUrl,fresh);
        }
    }
}
