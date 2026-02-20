package com.example.lankasmartmart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "LankaSmartMart.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_PRODUCTS = "products";
    public static final String TABLE_ORDERS = "orders";
    public static final String TABLE_ORDER_ITEMS = "order_items";
    public static final String TABLE_CATEGORIES = "categories";

    // USERS Table Columns
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "full_name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PHONE = "phone";
    public static final String USER_CREATED_AT = "created_at";

    // PRODUCTS Table Columns
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_CATEGORY = "category";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_IMAGE = "image_url";
    public static final String PRODUCT_STOCK = "stock";

    // ORDERS Table Columns
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_USER_ID = "user_id";
    public static final String ORDER_TOTAL = "total_price";
    public static final String ORDER_DATE = "order_date";
    public static final String ORDER_STATUS = "status";

    // ORDER ITEMS Table Columns
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_ORDER_ID = "order_id";
    public static final String ITEM_PRODUCT_ID = "product_id";
    public static final String ITEM_QUANTITY = "quantity";
    public static final String ITEM_PRICE = "price";

    // CATEGORIES Table Columns
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_ICON = "category_icon";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create USERS table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT NOT NULL, "
                + USER_EMAIL + " TEXT UNIQUE NOT NULL, "
                + USER_PASSWORD + " TEXT NOT NULL, "
                + USER_PHONE + " TEXT, "
                + USER_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")");

        // Create CATEGORIES table
        db.execSQL("CREATE TABLE " + TABLE_CATEGORIES + " ("
                + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY_NAME + " TEXT NOT NULL, "
                + CATEGORY_ICON + " TEXT"
                + ")");

        // Create PRODUCTS table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " ("
                + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCT_NAME + " TEXT NOT NULL, "
                + PRODUCT_PRICE + " REAL NOT NULL, "
                + PRODUCT_CATEGORY + " TEXT, "
                + PRODUCT_DESCRIPTION + " TEXT, "
                + PRODUCT_IMAGE + " TEXT, "
                + PRODUCT_STOCK + " INTEGER DEFAULT 0"
                + ")");

        // Create ORDERS table
        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " ("
                + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_USER_ID + " INTEGER NOT NULL, "
                + ORDER_TOTAL + " REAL NOT NULL, "
                + ORDER_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + ORDER_STATUS + " TEXT DEFAULT 'pending', "
                + "FOREIGN KEY(" + ORDER_USER_ID + ") REFERENCES "
                + TABLE_USERS + "(" + USER_ID + ")"
                + ")");

        // Create ORDER ITEMS table
        db.execSQL("CREATE TABLE " + TABLE_ORDER_ITEMS + " ("
                + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEM_ORDER_ID + " INTEGER NOT NULL, "
                + ITEM_PRODUCT_ID + " INTEGER NOT NULL, "
                + ITEM_QUANTITY + " INTEGER NOT NULL, "
                + ITEM_PRICE + " REAL NOT NULL, "
                + "FOREIGN KEY(" + ITEM_ORDER_ID + ") REFERENCES "
                + TABLE_ORDERS + "(" + ORDER_ID + "), "
                + "FOREIGN KEY(" + ITEM_PRODUCT_ID + ") REFERENCES "
                + TABLE_PRODUCTS + "(" + PRODUCT_ID + ")"
                + ")");

        // Insert default data
        insertDefaultCategories(db);
        insertSampleProducts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Insert default categories
    private void insertDefaultCategories(SQLiteDatabase db) {
        String[] categories = {"Groceries", "Household", "Personal Care", "Stationary"};
        String[] icons = {"ic_shopping_cart", "ic_home", "ic_personal_care", "ic_stationary"};

        for (int i = 0; i < categories.length; i++) {
            ContentValues values = new ContentValues();
            values.put(CATEGORY_NAME, categories[i]);
            values.put(CATEGORY_ICON, icons[i]);
            db.insert(TABLE_CATEGORIES, null, values);
        }
    }

    // Insert sample products
    private void insertSampleProducts(SQLiteDatabase db) {
        String[][] products = {
                {"Rice 5kg",          "450.00", "Groceries",     "Premium quality rice"},
                {"Coconut Oil 1L",    "350.00", "Groceries",     "Pure coconut oil"},
                {"Washing Powder 1kg","280.00", "Household",     "Strong cleaning powder"},
                {"Shampoo 200ml",     "320.00", "Personal Care", "Herbal shampoo"},
                {"Note Book A4",      "150.00", "Stationary",    "80 pages ruled notebook"}
        };

        for (String[] product : products) {
            ContentValues values = new ContentValues();
            values.put(PRODUCT_NAME,        product[0]);
            values.put(PRODUCT_PRICE,       Double.parseDouble(product[1]));
            values.put(PRODUCT_CATEGORY,    product[2]);
            values.put(PRODUCT_DESCRIPTION, product[3]);
            values.put(PRODUCT_STOCK,       100);
            db.insert(TABLE_PRODUCTS, null, values);
        }
    }

    // =============================================
    // USER OPERATIONS
    // =============================================

    // Register new user - NO hashing!
    public boolean addUser(String name, String email,
                           String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME,     name);
        values.put(USER_EMAIL,    email);
        values.put(USER_PASSWORD, password); // Plain text password
        values.put(USER_PHONE,    phone);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Check login - NO hashing!
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS
                        + " WHERE " + USER_EMAIL    + " = ? AND "
                        + USER_PASSWORD + " = ?",
                new String[]{email, password}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Check email already exists
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS
                        + " WHERE " + USER_EMAIL + " = ?",
                new String[]{email}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Get user by email
    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_USERS
                        + " WHERE " + USER_EMAIL + " = ?",
                new String[]{email}
        );
    }

    // Get ALL users (for viewing database)
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_USERS, null
        );
    }

    // =============================================
    // PRODUCT OPERATIONS
    // =============================================

    // Add product
    public boolean addProduct(String name, double price, String category,
                              String description, String image, int stock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PRODUCT_NAME,        name);
        values.put(PRODUCT_PRICE,       price);
        values.put(PRODUCT_CATEGORY,    category);
        values.put(PRODUCT_DESCRIPTION, description);
        values.put(PRODUCT_IMAGE,       image);
        values.put(PRODUCT_STOCK,       stock);

        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return result != -1;
    }

    // Get all products
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_PRODUCTS, null
        );
    }

    // Get products by category
    public Cursor getProductsByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_PRODUCTS
                        + " WHERE " + PRODUCT_CATEGORY + " = ?",
                new String[]{category}
        );
    }

    // =============================================
    // ORDER OPERATIONS
    // =============================================

    // Create new order
    public long addOrder(int userId, double totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ORDER_USER_ID, userId);
        values.put(ORDER_TOTAL,   totalPrice);
        values.put(ORDER_STATUS,  "pending");

        long orderId = db.insert(TABLE_ORDERS, null, values);
        db.close();
        return orderId;
    }

    // Add item to order
    public boolean addOrderItem(int orderId, int productId,
                                int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEM_ORDER_ID,   orderId);
        values.put(ITEM_PRODUCT_ID, productId);
        values.put(ITEM_QUANTITY,   quantity);
        values.put(ITEM_PRICE,      price);

        long result = db.insert(TABLE_ORDER_ITEMS, null, values);
        db.close();
        return result != -1;
    }

    // Get all orders for a user
    public Cursor getUserOrders(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_ORDERS
                        + " WHERE " + ORDER_USER_ID + " = ?"
                        + " ORDER BY " + ORDER_DATE + " DESC",
                new String[]{String.valueOf(userId)}
        );
    }

    // =============================================
    // CATEGORIES OPERATIONS
    // =============================================

    // Get all categories
    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_CATEGORIES, null
        );
    }
}