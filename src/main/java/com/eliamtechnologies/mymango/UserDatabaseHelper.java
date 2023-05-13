package com.eliamtechnologies.mymango;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 3; // this is the current database verison

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_TEST_SCORES = "test_scores";
    public static final String COLUMN_TEST_TITLE = "test_title";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_DATE = "date";

    private static final String TABLE_USER_PROFILE_IMAGE = "user_profile_image";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_LAST_UPDATE_DATE = "last_update_date";


    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FIRST_NAME + " TEXT NOT NULL, "
                + COLUMN_LAST_NAME + " TEXT NOT NULL, "
                + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
                + COLUMN_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_TEST_SCORES_TABLE = "CREATE TABLE " + TABLE_TEST_SCORES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMAIL + " TEXT NOT NULL, "
                + COLUMN_TEST_TITLE + " TEXT NOT NULL, "
                + COLUMN_SCORE + " INTEGER NOT NULL, "
                + COLUMN_DATE + " TEXT NOT NULL)";
        db.execSQL(CREATE_TEST_SCORES_TABLE);

        String CREATE_USER_PROFILE_IMAGE_TABLE = "CREATE TABLE " + TABLE_USER_PROFILE_IMAGE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
                + COLUMN_IMAGE + " BLOB, "
                + COLUMN_LAST_UPDATE_DATE + " TEXT NOT NULL)";
        db.execSQL(CREATE_USER_PROFILE_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST_SCORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROFILE_IMAGE);
        onCreate(db);
    }

    public boolean addUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getUserDetails(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " =?", new String[]{email});
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " =? AND " + COLUMN_PASSWORD + " =?",
                new String[]{email, password});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean addTestScore(String email, String testTitle, int score, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_TEST_TITLE, testTitle);
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_DATE, date);

        long result = db.insert(TABLE_TEST_SCORES, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getTestScoresByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TEST_SCORES + " WHERE " + COLUMN_EMAIL + " =?", new String[]{email});
    }

    public boolean hasUserTakenTest(String email, String testTitle) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEST_SCORES + " WHERE " + COLUMN_EMAIL + " =? AND " + COLUMN_TEST_TITLE + " =?",
                new String[]{email, testTitle});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public byte[] getUserProfileImage(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_IMAGE + " FROM " + TABLE_USER_PROFILE_IMAGE + " WHERE " + COLUMN_EMAIL + " =?", new String[]{email});
        if (cursor.moveToFirst()) {
            byte[] imgByte = cursor.getBlob(0);
            cursor.close();
            return imgByte;
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return null;
    }

    public boolean saveUserProfileImage(String email, byte[] image, String lastUpdateDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_LAST_UPDATE_DATE, lastUpdateDate);

        long result = db.insert(TABLE_USER_PROFILE_IMAGE, null, values);
        db.close();
        return result != -1;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
