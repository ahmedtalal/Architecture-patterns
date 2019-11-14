package ahmed.javcoder.egynews.Models;

import java.net.URL;

public class ItemModel
{
    private String textmdodel ;
    private String imagemodel ;
    private String Urlsource ;
    private String name ;
    private String descritption ;
    private String publishedAt ;

    public ItemModel()
    {

    }
    public ItemModel(String text, String image , String source , String name , String description , String publishedAt)
    {
        this.textmdodel = text;
        this.imagemodel = image;
        this.Urlsource = source ;
        this.name = name ;
        this.descritption = description ;
        this.publishedAt = publishedAt ;
    }

    public String getTextmdodel()
    {
        return textmdodel;
    }

    public void setTextmdodel(String textmdodel)
    {
        this.textmdodel = textmdodel;
    }

    public String getImagemodel()
    {
        return imagemodel;
    }

    public void setImagemodel(String imagemodel)
    {
        this.imagemodel = imagemodel;
    }

    public String getUrlsource() {
        return Urlsource;
    }

    public void setUrlsource(String urlsource) {
        Urlsource = urlsource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescritption() {
        return descritption;
    }

    public void setDescritption(String descritption) {
        this.descritption = descritption;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
