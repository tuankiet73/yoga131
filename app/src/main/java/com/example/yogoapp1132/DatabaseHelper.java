package com.example.yogoapp1132;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "YogaClasses.db";
    private static final String TABLE_NAME = "yoga_classes";
    private static final String TABLE_INSTANCES = "class_instances";

    // Yoga classes columns
    public static final String COL_1 = "_id";
    public static final String COL_2 = "DAY";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "CAPACITY";
    public static final String COL_5 = "DURATION";
    public static final String COL_6 = "PRICE";
    public static final String COL_7 = "TYPE";
    public static final String COL_8 = "DESCRIPTION";

    // Class instances columns
    public static final String COL_INSTANCE_ID = "ID";
    public static final String COL_INSTANCE_COURSE_ID = "COURSE_ID"; // Foreign Key
    public static final String COL_INSTANCE_DATE = "DATE";
    public static final String COL_INSTANCE_TEACHER = "TEACHER";
    public static final String COL_INSTANCE_COMMENTS = "COMMENTS";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, DAY TEXT, TIME TEXT, CAPACITY INTEGER, DURATION TEXT, PRICE TEXT, TYPE TEXT, DESCRIPTION TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_INSTANCES + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, COURSE_ID INTEGER, DATE TEXT, TEACHER TEXT, COMMENTS TEXT, FOREIGN KEY(COURSE_ID) REFERENCES " + TABLE_NAME + "(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTANCES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String day, String time, int capacity, String duration, String price, String type, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, day);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, capacity);
        contentValues.put(COL_5, duration);
        contentValues.put(COL_6, price);
        contentValues.put(COL_7, type);
        contentValues.put(COL_8, description);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Returns true if data is inserted successfully
    }

    public Cursor getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT _id, _id AS ID, DAY, TIME, CAPACITY, DURATION, PRICE, TYPE, DESCRIPTION FROM " + TABLE_NAME, null);
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
    }

    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertInstance(int courseId, String date, String teacher, String comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_INSTANCE_COURSE_ID, courseId);
        contentValues.put(COL_INSTANCE_DATE, date);
        contentValues.put(COL_INSTANCE_TEACHER, teacher);
        contentValues.put(COL_INSTANCE_COMMENTS, comments);

        long result = db.insert(TABLE_INSTANCES, null, contentValues);
        return result != -1; // Returns true if data is inserted successfully
    }

    public Cursor getInstancesByCourseId(int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_INSTANCES + " WHERE COURSE_ID = ?", new String[]{String.valueOf(courseId)});
    }

    public void updateInstance(int id, String courseId, String date, String teacher, String comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("COURSE_ID", courseId);
        values.put("DATE", date);
        values.put("TEACHER", teacher);
        values.put("COMMENTS", comments);

        db.update("your_table_name", values, "ID = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getAllInstances() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}