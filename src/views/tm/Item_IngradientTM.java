package views.tm;

public class Item_IngradientTM {
    private String ing_ID;
    private String ing_Name;
    private int qty;

    public Item_IngradientTM() {
    }

    public Item_IngradientTM(String ing_ID, String ing_Name, int qty) {
        this.ing_ID = ing_ID;
        this.ing_Name = ing_Name;
        this.qty = qty;
    }



    public String getIng_ID() {
        return ing_ID;
    }

    public void setIng_ID(String ing_ID) {
        this.ing_ID = ing_ID;
    }

    public String getIng_Name() {
        return ing_Name;
    }

    public void setIng_Name(String ing_Name) {
        this.ing_Name = ing_Name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
