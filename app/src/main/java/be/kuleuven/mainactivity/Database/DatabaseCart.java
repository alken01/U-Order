package be.kuleuven.mainactivity.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import be.kuleuven.mainactivity.ModelClasses.Item;

public class DatabaseCart extends SQLiteOpenHelper {

    public static String CART_TABLE = "cart_table";
    public static String ITEM_NAME = "item_name";
    public static String ITEM_TOKEN = "item_token";
    public static String ITEM_QUANTITY = "item_quantity";
    public static String ITEM_ORDER = "item_order";
    public static String ITEM_DESCRIPTION = "item_description";
    public static String ITEM_IMAGE = "item_image";

    private static final String dbName = "cart_db";
    private static final int dbVersion = 1;
    private Context context;

    public DatabaseCart(Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE "
                + CART_TABLE
                + "("

                + ITEM_NAME + " TEXT PRIMARY KEY,"
                + ITEM_IMAGE + " TEXT, "
                + ITEM_TOKEN + " TEXT,"
                + ITEM_QUANTITY + " TEXT,"
                + ITEM_DESCRIPTION + " TEXT,"
                + ITEM_ORDER + " INTEGER"

                + ")";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_IMAGE,convert(item.getImage()));
        cv.put(ITEM_NAME, item.getName());
        cv.put(ITEM_TOKEN, item.getToken());
        cv.put(ITEM_QUANTITY, item.getQuantity());
        cv.put(ITEM_DESCRIPTION, item.getDescription());
        cv.put(ITEM_ORDER, item.getOrder());
        long insert = db.insert(CART_TABLE,null,cv);
        db.close();
        return insert != -1;
    }

    public void updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_ORDER,item.getOrder());
        db.update(CART_TABLE, values, ITEM_NAME + " = ?",
                new String[]{String.valueOf(item.getName())});
        db.close();
        // updating row
    }

    public void removeItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "DELETE FROM " + CART_TABLE + " WHERE " + ITEM_NAME + " = " + "'" + name + "'";
        db.execSQL(Query);
        db.close();

    }

    public boolean checkIfItemExists(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + CART_TABLE + " WHERE " + ITEM_NAME + " = " + "'" + name + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }


    public ArrayList<Item> getAllItems() {

        ArrayList<Item> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + CART_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Bitmap image = convert(cursor.getString(cursor.getColumnIndex(ITEM_IMAGE)));
                String name = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
                String token = cursor.getString(cursor.getColumnIndex(ITEM_TOKEN));
                String quantity = cursor.getString(cursor.getColumnIndex(ITEM_QUANTITY));
                String description = cursor.getString(cursor.getColumnIndex(ITEM_DESCRIPTION));
                int order = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ITEM_ORDER)));

                Item item = new Item(image, name, token, quantity, description, order);
                items.add(item);

            } while (cursor.moveToNext());
        }

        db.close();
        return items;
    }



    public Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public  String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

}
