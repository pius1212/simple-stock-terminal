package stock;

public class newsObject {
    private String category;
    private long datetime;
    private String headline;
    private int id;
    private String image;
    private String related;
    private String source;
    private String summary;
    private String url;
    public newsObject(String category, long datetime, String headline, int id, String image, String related, String source, String summary, String url){
        super();
        this.category = category;
        this.datetime = datetime;
        this.headline = headline;
        this.id = id;
        this.image = image;
        this.related = related;
        this.source = source;
        this.summary = summary;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getHeadline() {
        return headline;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getRelated() {
        return related;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
