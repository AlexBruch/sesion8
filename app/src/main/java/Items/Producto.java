package Items;

/**
 * Created by alexbruch on 15/12/16.
 */

public class Producto {

    public int PRODUCT_ID = 0;
    public String MANUFACTURER = "";
    public String PRODUCT_NAME = "";
    public String PRICE = "";
    public String STOCK = "";

    public Producto(int PRODUCT_ID, String MANUFACTURER, String PRODUCT_NAME, String PRICE, String STOCK) {
        this.PRODUCT_ID = PRODUCT_ID;
        this.MANUFACTURER = MANUFACTURER;
        this.PRODUCT_NAME = PRODUCT_NAME;
        this.PRICE = PRICE;
        this.STOCK = STOCK;
    }

    public int getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(int PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public String getMANUFACTURER() {
        return MANUFACTURER;
    }

    public void setMANUFACTURER(String MANUFACTURER) {
        this.MANUFACTURER = MANUFACTURER;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public void setPRODUCT_NAME(String PRODUCT_NAME) {
        this.PRODUCT_NAME = PRODUCT_NAME;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getSTOCK() {
        return STOCK;
    }

    public void setSTOCK(String STOCK) {
        this.STOCK = STOCK;
    }
}
