package views.tm;

import javafx.scene.control.Button;

public class IngredientTM {
    private String supId;
    private String ingredientId;
    private String ingredient;
    private int qty;
    private double cost;
    private Button btn1;
    private Button btn2;


    public IngredientTM() {
    }


    public IngredientTM(String supId, String ingredientId, String ingredient, int qty, double cost, Button btn1, Button btn2) {
        this.supId = supId;
        this.ingredientId = ingredientId;
        this.ingredient = ingredient;
        this.qty = qty;
        this.cost = cost;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }


    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
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

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }

    public Button getBtn2() {
        return btn2;
    }

    public void setBtn2(Button btn2) {
        this.btn2 = btn2;
    }


    @Override
    public String toString() {
        return "IngredientTM{" +
                "supId='" + getSupId() + '\'' +
                ", ingredientId='" + getIngredientId() + '\'' +
                ", ingredient='" + getIngredient() + '\'' +
                ", qty=" + getQty() +
                ", cost=" + getCost() +
                ", btn1=" + getBtn1() +
                ", btn2=" + getBtn2() +
                '}';
    }
}
