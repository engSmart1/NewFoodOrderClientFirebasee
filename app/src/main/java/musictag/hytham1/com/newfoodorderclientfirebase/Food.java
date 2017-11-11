package musictag.hytham1.com.newfoodorderclientfirebase;

/**
 * Created by Hytham on 11/4/2017.
 */

public class Food {
    private String name , desc , image , price ;

    public Food(){

    }
    public Food (String name , String desc , String image , String price){
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
