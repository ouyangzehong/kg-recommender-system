package Bean;

public class DMFoodBeaan {
    private String name;
    private String heat;
    private String image;
    private String effect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    @Override
    public String toString() {
        return "DMFoodBeaan{" +
                "name='" + name + '\'' +
                ", heat='" + heat + '\'' +
                ", image='" + image + '\'' +
                ", effect='" + effect + '\'' +
                '}';
    }
}
