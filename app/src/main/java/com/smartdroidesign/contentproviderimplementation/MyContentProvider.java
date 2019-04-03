package com.smartdroidesign.contentproviderimplementation;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    // Declaring a field to store the context
    private Context context;
    // Declaring a field to store the database
    private SQLiteDatabase database;

    /** onCreate() implementation:
     * a - get the context in the onCreate
     * b - create a DatabaseHelper object and pass the context to it
     * c - populate the database field
     * d - we should be returning weather or not the provider was started successfully
     * @return true = the database exists false = it doesn't
     */
    @Override
    public boolean onCreate() {
        context = getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return false;
    }

    /** query() implementation
     *
     * Each ContentProvider has its own URI that we can use to access it **
     * a - create a SQLiteQueryBuilder object
     * b - set the table
     * c - return the results of calling the query method to the builder
     *
     * @param uri > Uniform Resource Identifier > address of a resource in Android
     * @param columns > SELECT columns from the URI
     * @param selection > WHERE clause (this combines with selectionArgs)
     * @param selectionArgs > same as above
     * @param sortOrder > ORDER BY clause
     * @return a cursor containing the results of the query
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseHelper.TABLE_VICS);
        return queryBuilder.query(database, columns, selection, selectionArgs, null, null, sortOrder);
    }

    /** getType() implementation
     *
     * Other apps can call our getType() to see what kind of content we are providing
     * @param uri
     * @return the media-type of the content in the specified URI > no need in this case
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
