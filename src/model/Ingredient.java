package model;

public class Ingredient {
    private String ingId;
    private String ingredient;
    private int qty;
    private double cost;
    private String supplierId;


    public Ingredient() {
    }

    public Ingredient(String ingId, String ingredient, int qty, double cost, String supplierId) {
        this.ingId = ingId;
        this.ingredient = ingredient;
        this.qty = qty;
        this.cost = cost;
        this.supplierId = supplierId;
    }

    public String getIngId() {
        return ingId;
    }

    public void setIngId(String ingId) {
        this.ingId = ingId;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingId='" + ingId + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", qty=" + qty +
                ", cost=" + cost +
                ", supplierId='" + supplierId + '\'' +
                '}';
    }
}
