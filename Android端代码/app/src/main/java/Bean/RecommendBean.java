package Bean;

public class RecommendBean {
    private int image;
    private String desc;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "RecommendBean{" +
                "image=" + image +
                ", desc='" + desc + '\'' +
                '}';
    }
}
