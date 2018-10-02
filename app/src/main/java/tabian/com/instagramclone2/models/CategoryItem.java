package tabian.com.instagramclone2.models;

public class CategoryItem {
    public String title;
    public int imageId;

    public CategoryItem(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }
}

