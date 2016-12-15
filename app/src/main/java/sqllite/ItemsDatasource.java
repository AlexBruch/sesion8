package sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by alexbruch on 15/12/16.
 */

public class ItemsDatasource {

    public static final String PRODUCTS_TABLE_NAME = "Productos";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";

    public static class ColumnProductos {
        public static String PRODUCT_ID = BaseColumns._ID;
        public static String MANUFACTURER = "manufacturer";
        public static String PRODUCT_NAME = "product_name";
        public static String PRICE = "price";
        public static  String STOCK = "stock";
    }

    /** PARA CREAR LA TABLA EN LA BASE DE DATOS **/

    public static final String CREATE_ITEMS_SCRIPT =
            "create table " + PRODUCTS_TABLE_NAME + "(" +
                    ColumnProductos.PRODUCT_ID + " " + INT_TYPE + " primary key autoincrement,"+
                    ColumnProductos.MANUFACTURER + " " + STRING_TYPE + " not null," +
                    ColumnProductos.PRODUCT_NAME + " " + STRING_TYPE + " not null," +
                    ColumnProductos.PRICE + " " + STRING_TYPE + " not null," +
                    ColumnProductos.STOCK + " " + STRING_TYPE + " not null)";

    private ItemsOpenHelper itemsOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public ItemsDatasource(Context context) {
        itemsOpenHelper = new ItemsOpenHelper(context);
        sqLiteDatabase = itemsOpenHelper.getWritableDatabase();
    }

    /** PARA CONSULTAR LA TABLA **/

    public Cursor consultProducts() {
        return sqLiteDatabase.rawQuery("select * from " + PRODUCTS_TABLE_NAME, null);
    }

    /** PARA ALMACENAR LOS CAMPOS EN LA TABLA **/

    public void saveProduct(String manufacturer, String product_name, String price, String stock) {
        ContentValues values = new ContentValues();

        values.put(ColumnProductos.MANUFACTURER, manufacturer);
        values.put(ColumnProductos.PRODUCT_NAME, product_name);
        values.put(ColumnProductos.PRICE, price);
        values.put(ColumnProductos.STOCK, stock);

        sqLiteDatabase.insert(PRODUCTS_TABLE_NAME, null, values);
    }

    public void cleanTable() {
        sqLiteDatabase.delete(PRODUCTS_TABLE_NAME, null, null);
    }
}
