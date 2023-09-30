package model;

public class Product {
    private int ID;
    private String name;
    private float price;
    private String color;

   public Product(){}

    public Product(String name, float price, String color) {
        this.name = name;
        this.price = price;
        this.color= color;
    }

    public Product(int ID, String name, float price, String color) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.color= color;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID=" + ID +
                ", nameProduct='" + name + '\'' +
                ", price=" + price +
                ", color=" + color +
                '}';
    }
}
