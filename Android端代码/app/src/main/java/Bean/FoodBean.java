package Bean;

public class FoodBean {
    private String name;
    private String image;
    private String heat;
    private String cho;
    private String fat;
    private String e460;
    private String pro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getCho() {
        return cho;
    }

    public void setCho(String cho) {
        this.cho = cho;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getE460() {
        return e460;
    }

    public void setE460(String e460) {
        this.e460 = e460;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    @Override
    public String toString() {
        return "FoodBean{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", heat='" + heat + '\'' +
                ", cho='" + cho + '\'' +
                ", fat='" + fat + '\'' +
                ", e460='" + e460 + '\'' +
                ", pro='" + pro + '\'' +
                '}';
    }
}
