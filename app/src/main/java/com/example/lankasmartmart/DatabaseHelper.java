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
    public static final String TABLE_ADDRESSES = "addresses";
    public static final String TABLE_CART = "cart";

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

    // ADDRESSES Table Columns
    public static final String ADDRESS_ID = "address_id";
    public static final String ADDRESS_USER_ID = "user_id";
    public static final String ADDRESS_TYPE = "type";
    public static final String ADDRESS_LINE = "address_line";
    public static final String ADDRESS_CITY = "city";

    // CART Table Columns
    public static final String CART_ID = "cart_id";
    public static final String CART_USER_ID = "user_id";
    public static final String CART_PRODUCT_ID = "product_id";
    public static final String CART_QUANTITY = "quantity";

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

        // Create ADDRESSES table
        db.execSQL("CREATE TABLE " + TABLE_ADDRESSES + " ("
                + ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ADDRESS_USER_ID + " INTEGER NOT NULL, "
                + ADDRESS_TYPE + " TEXT, "
                + ADDRESS_LINE + " TEXT, "
                + ADDRESS_CITY + " TEXT, "
                + "FOREIGN KEY(" + ADDRESS_USER_ID + ") REFERENCES "
                + TABLE_USERS + "(" + USER_ID + ")"
                + ")");

        // Create CART table
        db.execSQL("CREATE TABLE " + TABLE_CART + " ("
                + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CART_USER_ID + " INTEGER NOT NULL, "
                + CART_PRODUCT_ID + " INTEGER NOT NULL, "
                + CART_QUANTITY + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + CART_USER_ID + ") REFERENCES "
                + TABLE_USERS + "(" + USER_ID + "), "
                + "FOREIGN KEY(" + CART_PRODUCT_ID + ") REFERENCES "
                + TABLE_PRODUCTS + "(" + PRODUCT_ID + ")"
                + ")");

        // Insert default data
        insertDefaultCategories(db);
        insertSampleProducts(db);
        insertSampleAddresses(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Insert default categories
    private void insertDefaultCategories(SQLiteDatabase db) {
        String[] categories = {"Groceries", "Household", "Personal Care", "Stationery"};
        String[] icons = {"🛒", "🏠", "💄", "📝"};

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
                // Groceries
                {"Basmati Rice - 1kg", "350.00", "Groceries", "Premium quality rice", "product_rice", "100"},
                {"Fresh Milk (1L)", "250.00", "Groceries", "Farm fresh daily milk", "", "50"},
                {"Free Range Eggs (12pcs)", "450.00", "Groceries", "Organic eggs", "", "30"},
                {"Coconut Oil 1L", "350.00", "Groceries", "Pure coconut oil", "", "40"},

                // Household
                {"Dish Soap (500ml)", "180.00", "Household", "Lemon scented", "", "60"},
                {"Washing Powder 1kg", "280.00", "Household", "Strong cleaning powder", "", "45"},
                {"Floor Cleaner (1L)", "320.00", "Household", "Pine fresh", "", "35"},

                // Personal Care
                {"Shampoo 200ml", "320.00", "Personal Care", "Herbal shampoo", "", "55"},
                {"Toothpaste", "220.00", "Personal Care", "Whitening formula", "", "70"},
                {"Body Soap (3-pack)", "350.00", "Personal Care", "Moisturizing", "", "40"},

                // Stationery
                {"Note Book A4", "150.00", "Stationery", "80 pages ruled notebook", "", "80"},
                {"Ball Pens (Pack of 10)", "350.00", "Stationery", "Blue ink", "", "100"},
                {"A4 Paper (500 sheets)", "950.00", "Stationery", "Premium quality", "", "25"}
        };

        for (String[] product : products) {
            ContentValues values = new ContentValues();
            values.put(PRODUCT_NAME, product[0]);
            values.put(PRODUCT_PRICE, Double.parseDouble(product[1]));
            values.put(PRODUCT_CATEGORY, product[2]);
            values.put(PRODUCT_DESCRIPTION, product[3]);
            values.put(PRODUCT_IMAGE, product[4]);
            values.put(PRODUCT_STOCK, Integer.parseInt(product[5]));
            db.insert(TABLE_PRODUCTS, null, values);
        }
    }

    // Insert sample addresses
    private void insertSampleAddresses(SQLiteDatabase db) {
        // Sample addresses for testing (user_id 1)
        ContentValues homeAddress = new ContentValues();
        homeAddress.put(ADDRESS_USER_ID, 1);
        homeAddress.put(ADDRESS_TYPE, "Home");
        homeAddress.put(ADDRESS_LINE, "123, Galle Road");
        homeAddress.put(ADDRESS_CITY, "Colombo 03");
        db.insert(TABLE_ADDRESSES, null, homeAddress);

        ContentValues officeAddress = new ContentValues();
        officeAddress.put(ADDRESS_USER_ID, 1);
        officeAddress.put(ADDRESS_TYPE, "Office");
        officeAddress.put(ADDRESS_LINE, "45, Duplication Road");
        officeAddress.put(ADDRESS_CITY, "Colombo 04");
        db.insert(TABLE_ADDRESSES, null, officeAddress);
    }

    // =============================================
    // USER OPERATIONS
    // =============================================

    // Register new user
    public boolean addUser(String name, String email, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME, name);
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);
        values.put(USER_PHONE, phone);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Check login
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS
                        + " WHERE " + USER_EMAIL + " = ? AND "
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

    // Get user ID by email
    public int getUserId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1;

        Cursor cursor = db.rawQuery(
                "SELECT " + USER_ID + " FROM " + TABLE_USERS
                        + " WHERE " + USER_EMAIL + " = ?",
                new String[]{email}
        );

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return userId;
    }

    // Get ALL users
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
    }

    // Update user phone
    public boolean updateUserPhone(int userId, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_PHONE, phone);

        int rows = db.update(TABLE_USERS, values, USER_ID + "=?",
                new String[]{String.valueOf(userId)});
        db.close();
        return rows > 0;
    }

    // =============================================
    // PRODUCT OPERATIONS
    // =============================================

    // Add product
    public boolean addProduct(String name, double price, String category,
                              String description, String image, int stock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PRODUCT_NAME, name);
        values.put(PRODUCT_PRICE, price);
        values.put(PRODUCT_CATEGORY, category);
        values.put(PRODUCT_DESCRIPTION, description);
        values.put(PRODUCT_IMAGE, image);
        values.put(PRODUCT_STOCK, stock);

        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return result != -1;
    }

    // Get all products
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
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

    // Get product by ID
    public Cursor getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_PRODUCTS
                        + " WHERE " + PRODUCT_ID + " = ?",
                new String[]{String.valueOf(productId)}
        );
    }

    // =============================================
    // CART OPERATIONS
    // =============================================

    // Add item to cart
    public long addToCart(int userId, int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if item already in cart
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_CART
                        + " WHERE " + CART_USER_ID + " = ? AND "
                        + CART_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(userId), String.valueOf(productId)}
        );

        if (cursor.getCount() > 0) {
            // Update quantity
            cursor.moveToFirst();
            int currentQty = cursor.getInt(cursor.getColumnIndex(CART_QUANTITY));
            int newQty = currentQty + quantity;

            ContentValues values = new ContentValues();
            values.put(CART_QUANTITY, newQty);

            long result = db.update(TABLE_CART, values,
                    CART_USER_ID + "=? AND " + CART_PRODUCT_ID + "=?",
                    new String[]{String.valueOf(userId), String.valueOf(productId)});
            cursor.close();
            db.close();
            return result;
        } else {
            // Insert new item
            ContentValues values = new ContentValues();
            values.put(CART_USER_ID, userId);
            values.put(CART_PRODUCT_ID, productId);
            values.put(CART_QUANTITY, quantity);

            long result = db.insert(TABLE_CART, null, values);
            cursor.close();
            db.close();
            return result;
        }
    }

    // Get cart items for user
    public Cursor getCartItems(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT c.*, p." + PRODUCT_NAME + " as product_name, "
                + "p." + PRODUCT_PRICE + " as product_price, "
                + "p." + PRODUCT_IMAGE + " as product_image "
                + "FROM " + TABLE_CART + " c "
                + "LEFT JOIN " + TABLE_PRODUCTS + " p "
                + "ON c." + CART_PRODUCT_ID + " = p." + PRODUCT_ID + " "
                + "WHERE c." + CART_USER_ID + "=?";
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }

    // Get cart item count
    public int getCartItemCount(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + CART_QUANTITY + ") FROM " + TABLE_CART
                        + " WHERE " + CART_USER_ID + "=?",
                new String[]{String.valueOf(userId)}
        );

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    // Update cart quantity
    public boolean updateCartQuantity(int userId, int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (quantity <= 0) {
            // Remove from cart
            int rows = db.delete(TABLE_CART,
                    CART_USER_ID + "=? AND " + CART_PRODUCT_ID + "=?",
                    new String[]{String.valueOf(userId), String.valueOf(productId)});
            db.close();
            return rows > 0;
        } else {
            ContentValues values = new ContentValues();
            values.put(CART_QUANTITY, quantity);

            int rows = db.update(TABLE_CART, values,
                    CART_USER_ID + "=? AND " + CART_PRODUCT_ID + "=?",
                    new String[]{String.valueOf(userId), String.valueOf(productId)});
            db.close();
            return rows > 0;
        }
    }

    // Clear cart
    public boolean clearCart(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_CART, CART_USER_ID + "=?",
                new String[]{String.valueOf(userId)});
        db.close();
        return rows > 0;
    }

    // =============================================
    // ORDER OPERATIONS
    // =============================================

    // Create new order
    public long addOrder(int userId, double totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ORDER_USER_ID, userId);
        values.put(ORDER_TOTAL, totalPrice);
        values.put(ORDER_STATUS, "pending");

        long orderId = db.insert(TABLE_ORDERS, null, values);
        db.close();
        return orderId;
    }

    // Add item to order
    public boolean addOrderItem(int orderId, int productId, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEM_ORDER_ID, orderId);
        values.put(ITEM_PRODUCT_ID, productId);
        values.put(ITEM_QUANTITY, quantity);
        values.put(ITEM_PRICE, price);

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

    // Get order items
    public Cursor getOrderItems(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT oi.*, p." + PRODUCT_NAME + " as product_name "
                + "FROM " + TABLE_ORDER_ITEMS + " oi "
                + "LEFT JOIN " + TABLE_PRODUCTS + " p "
                + "ON oi." + ITEM_PRODUCT_ID + " = p." + PRODUCT_ID + " "
                + "WHERE oi." + ITEM_ORDER_ID + "=?";
        return db.rawQuery(query, new String[]{String.valueOf(orderId)});
    }

    // =============================================
    // ADDRESS OPERATIONS
    // =============================================

    // Add address
    public long addAddress(int userId, String type, String addressLine, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ADDRESS_USER_ID, userId);
        values.put(ADDRESS_TYPE, type);
        values.put(ADDRESS_LINE, addressLine);
        values.put(ADDRESS_CITY, city);

        long id = db.insert(TABLE_ADDRESSES, null, values);
        db.close();
        return id;
    }

    // Get user addresses
    public Cursor getUserAddresses(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_ADDRESSES
                        + " WHERE " + ADDRESS_USER_ID + " = ?",
                new String[]{String.valueOf(userId)}
        );
    }

    // Update address
    public boolean updateAddress(int addressId, String type, String addressLine, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ADDRESS_TYPE, type);
        values.put(ADDRESS_LINE, addressLine);
        values.put(ADDRESS_CITY, city);

        int rows = db.update(TABLE_ADDRESSES, values, ADDRESS_ID + "=?",
                new String[]{String.valueOf(addressId)});
        db.close();
        return rows > 0;
    }

    // Delete address
    public boolean deleteAddress(int addressId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_ADDRESSES, ADDRESS_ID + "=?",
                new String[]{String.valueOf(addressId)});
        db.close();
        return rows > 0;
    }

    // =============================================
    // CATEGORIES OPERATIONS
    // =============================================

    // Get all categories
    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES, null);
    }

    // Get category by ID
    public Cursor getCategoryById(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_CATEGORIES
                        + " WHERE " + CATEGORY_ID + " = ?",
                new String[]{String.valueOf(categoryId)}
        );
    }
}