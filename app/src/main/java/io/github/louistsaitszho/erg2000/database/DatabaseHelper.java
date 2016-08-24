package io.github.louistsaitszho.erg2000.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import io.github.louistsaitszho.erg2000.pojo.Record;
import io.github.louistsaitszho.erg2000.pojo.Section;
import io.github.louistsaitszho.erg2000.pojo.Tag;

/**
 * Created by Louis on 23/8/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "erg2000";

  private static final String ACTION_CREATE_TABLE = "CREATE TABLE";
  private static final String ACTION_FOREIGN_KEY = "FOREIGN KEY";
  private static final String ACTION_PRIMARY_KEY = "PRIMARY KEY";
  private static final String ACTION_NOT_NULL = "NOT NULL";
  private static final String ACTION_AUTOINCREMENT = "AUTOINCREMENT";
  private static final String ACTION_REFERENCES = "REFERENCES";
  private static final String ACTION_SELECT = "SELECT";
  private static final String ACTION_FROM = "FROM";
  private static final String ACTION_ALL = "*";
  private static final String ACTION_WHERE = "WHERE";
  private static final String ACTION_EQUALS = "=";
  private static final String ACTION_INNER_JOIN = "INNER JOIN";
  private static final String ACTION_ON = "ON";

  private static final String DATATYPE_INTEGER = "INTEGER";
  private static final String DATATYPE_REAL = "REAL";
  private static final String DATATYPE_TEXT = "TEXT";

  private static final String TABLE_RECORDS = "records";
  private static final String TABLE_TAG = "tags";
  private static final String TABLE_SECTIONS = "sections";
  private static final String TABLE_TAGS_VS_RECORDS = "tags_vs_records";

  private static final String RECORDS_ID = "id";
  private static final String RECORDS_START_TIMESTAMP = "start_timestamp";
  private static final String RECORDS_DURATION = "duration";
  private static final String RECORDS_DISTANCE = "distance";
  private static final String RECORDS_RATING = "rating";
  private static final String RECORDS_REMARK = "remark";

  private static final String TAG_ID = "id";
  private static final String TAG_TAG = "tag";

  private static final String SECTIONS_ID = "id";
  private static final String SECTIONS_RECORD_ID = "record_id";
  private static final String SECTIONS_ORDER = "order";
  private static final String SECTIONS_REST_OR_EASY = "rest_or_easy";
  private static final String SECTIONS_DURATION = "duration";
  private static final String SECTIONS_DISTANCE = "distance";
  private static final String SECTIONS_RATING = "rating";

  private static final String TAGS_VS_RECORDS_ID = "id";
  private static final String TAGS_VS_RECORDS_RECORD_ID = "record_id";
  private static final String TAGS_VS_RECORDS_TAG_ID = "tag_id";

  private static final String CREATE_TABLE_RECORDS = ACTION_CREATE_TABLE + " " + TABLE_RECORDS + "(" +
      RECORDS_ID +              " " + DATATYPE_INTEGER +  " " + ACTION_PRIMARY_KEY + " " + ACTION_AUTOINCREMENT + ", " +
      RECORDS_START_TIMESTAMP + " " + DATATYPE_INTEGER +  " " + ACTION_NOT_NULL + ", " +
      RECORDS_DURATION +        " " + DATATYPE_INTEGER +  " " + ACTION_NOT_NULL + ", " +
      RECORDS_DISTANCE +        " " + DATATYPE_REAL +     " " + ACTION_NOT_NULL + ", " +
      RECORDS_RATING +          " " + DATATYPE_INTEGER +  " " + ACTION_NOT_NULL + ", " +
      RECORDS_REMARK +          " " + DATATYPE_TEXT +     ", " +
      ")";

  private static final String CREATE_TABLE_TAGS = ACTION_CREATE_TABLE + " " + TABLE_TAG + "(" +
      TAG_ID +  " " + DATATYPE_INTEGER + " " + ACTION_PRIMARY_KEY + " " + ACTION_AUTOINCREMENT + ", " +
      TAG_TAG + " " + DATATYPE_TEXT + " " + ACTION_NOT_NULL +
      ")";

  private static final String CREATE_TABLE_SECTIONS = ACTION_CREATE_TABLE + " " + TABLE_SECTIONS + "(" +
      SECTIONS_ID +           " " + DATATYPE_INTEGER +  " " + ACTION_PRIMARY_KEY + " " + ACTION_AUTOINCREMENT + ", " +
      SECTIONS_RECORD_ID +    " " + DATATYPE_INTEGER +  " " + ACTION_NOT_NULL + ", " +
      SECTIONS_ORDER +        " " + DATATYPE_INTEGER +  " " + ACTION_NOT_NULL + ", " +
      SECTIONS_REST_OR_EASY + " " + DATATYPE_INTEGER +  " " + ACTION_NOT_NULL + ", " +
      SECTIONS_DURATION +     " " + DATATYPE_INTEGER +  ", " +
      SECTIONS_DISTANCE +     " " + DATATYPE_REAL +     ", " +
      SECTIONS_RATING +       " " + DATATYPE_INTEGER +  ", " +
      ACTION_FOREIGN_KEY + "(" + SECTIONS_RECORD_ID + ") " + ACTION_REFERENCES + " " + TABLE_RECORDS + "(" + RECORDS_ID + ")" +
      ")";

  /**
   * CREATE TABLE tags_vs_records(
   * id INTEGER PRIMARY KEY AUTOINCREMENT,
   * record_id INTEGER NOT NULL,
   * tag_id INTEGER NOT NULL,
   * FOREIGN KEY(record_id) REFERENCES records(id),
   * FOREIGN KEY(tag_id) REFERENCE tags(id))
   */
  private static final String CREATE_TABLE_TAGS_VS_RECORDS = ACTION_CREATE_TABLE + " " + TABLE_TAGS_VS_RECORDS + "(" +
      TAGS_VS_RECORDS_ID +        " " + DATATYPE_INTEGER + " " + ACTION_PRIMARY_KEY + " " + ACTION_AUTOINCREMENT + ", " +
      TAGS_VS_RECORDS_RECORD_ID + " " + DATATYPE_INTEGER + " " + ACTION_NOT_NULL + ", " +
      TAGS_VS_RECORDS_TAG_ID +    " " + DATATYPE_INTEGER + " " + ACTION_NOT_NULL + ", " +
      ACTION_FOREIGN_KEY + "(" + TAGS_VS_RECORDS_RECORD_ID + ") " + ACTION_REFERENCES + " " + TABLE_RECORDS + "(" + RECORDS_ID + "), " +
      ACTION_FOREIGN_KEY + "(" + TAGS_VS_RECORDS_TAG_ID + ") " + ACTION_REFERENCES + " " + TABLE_TAG + "(" + TAG_ID + ")" +
      ")";

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_TABLE_RECORDS);
    sqLiteDatabase.execSQL(CREATE_TABLE_TAGS);
    sqLiteDatabase.execSQL(CREATE_TABLE_SECTIONS);
    sqLiteDatabase.execSQL(CREATE_TABLE_TAGS_VS_RECORDS);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    //TODO later
  }

  /**
   *
   * @return
   */
  public List<Record> getAllRecords() {
    SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
    String query = ACTION_SELECT + " " + ACTION_ALL + " " + ACTION_FROM + TABLE_RECORDS;
    List<Record> records = new ArrayList<>();
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      do {
        Record r = new Record();
        r.setID(cursor.getInt(cursor.getColumnIndex(RECORDS_ID)));
        r.setStartTimestamp(cursor.getLong(cursor.getColumnIndex(RECORDS_START_TIMESTAMP)));
        r.setDuration(cursor.getInt(cursor.getColumnIndex(RECORDS_DURATION)));
        r.setDistance(cursor.getDouble(cursor.getColumnIndex(RECORDS_DISTANCE)));
        r.setRating(cursor.getInt(cursor.getColumnIndex(RECORDS_RATING)));
        r.setRemark(cursor.getString(cursor.getColumnIndex(RECORDS_REMARK)));
        records.add(r);
      } while (cursor.moveToNext());
      cursor.close();
    }
    return records;
  }

  /**
   * Add a record to the db
   * @param record i.e. new record - newTags
   * @param newTags i.e. new tags that does not exist in the db before
   * @return the row id of that record in long
   */
  public long addRecord(Record record, List<Tag> newTags) {
    //insert new tags
    List<Long> newTagsIDs = addTags(newTags);

    //insert record
    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(RECORDS_DISTANCE, record.getDistance());
    contentValues.put(RECORDS_DURATION, record.getDuration());
    contentValues.put(RECORDS_RATING, record.getRating());
    contentValues.put(RECORDS_REMARK, record.getRemark());
    contentValues.put(RECORDS_START_TIMESTAMP, record.getStartTimestamp());
    long id = sqLiteDatabase.insert(TABLE_RECORDS, null, contentValues);

    //insert tags vs records
    for (Tag tag: record.getTags()) {
      newTagsIDs.add(Long.valueOf(tag.getID()));
    }
    addTagsVsRecordss(id, newTagsIDs);

    return id;
  }

  /**
   * Add list of tags
   * @param newTags i.e. list of tags
   * @return list of row ids in long
   */
  private List<Long> addTags(List<Tag> newTags) {
    List<Long> ids = new ArrayList<>(newTags.size());
    for (Tag tag: newTags) {
      ids.add(addTag(tag));
    }
    return ids;
  }

  /**
   * Add a tag
   * @param tag dah
   * @return the row id of this tag in long
   */
  private long addTag(Tag tag) {
    SQLiteDatabase sqLiteDatabase = getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(TAG_TAG, tag.getTag());
    return sqLiteDatabase.insert(TABLE_TAG, null, contentValues);
  }

  public List<Section> getSections(int recordID) {
    return null;
  }

  /**
   * Get every tags
   * @return
   */
  public List<Tag> getAllTags() {
    SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
    String query = ACTION_SELECT + " " + ACTION_ALL + " " + ACTION_FROM + TABLE_TAG;
    List<Tag> tags = new ArrayList<>();
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      do{
        tags.add(new Tag(cursor.getInt(cursor.getColumnIndex(TAG_ID)), cursor.getString(cursor.getColumnIndex(TAG_TAG))));
      } while (cursor.moveToNext());
      cursor.close();
    }
    return tags;
  }

  /**
   * Get evert tags related to a specific record
   * @param recordID
   * @return
   */
  public List<Tag> getRelatedTags(int recordID) {
    SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
    //SELECT * FROM tags INNER JOIN records on records.id = tags_vs_records.record_id WHERE tags_vs_records.record_id = $recordID
    String query = ACTION_SELECT + " " + ACTION_ALL + " " +
        ACTION_FROM + " " + TABLE_TAG + " " +
        ACTION_INNER_JOIN + " " + TABLE_RECORDS + " " + ACTION_ON + " " + TABLE_RECORDS + "." + RECORDS_ID + ACTION_EQUALS + TABLE_TAGS_VS_RECORDS + "." + TAGS_VS_RECORDS_RECORD_ID + " " +
        ACTION_WHERE + " " + TABLE_TAGS_VS_RECORDS + "." + TAGS_VS_RECORDS_RECORD_ID + ACTION_EQUALS + String.valueOf(recordID);
    List<Tag> tags = new ArrayList<>();
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      do{
        tags.add(new Tag(cursor.getInt(cursor.getColumnIndex(TAG_ID)), cursor.getString(cursor.getColumnIndex(TAG_TAG))));
      } while (cursor.moveToNext());
      cursor.close();
    }
    return tags;
  }

  /**
   *
   * @param recordID
   * @param tagIDs
   * @return
   */
  private List<Long> addTagsVsRecordss(long recordID, List<Long> tagIDs) {
    List<Long> ids = new ArrayList<>(tagIDs.size());
    for (long l:tagIDs) {
      ids.add(addTagsVsRecords(recordID, l));
    }
    return ids;
  }

  private long addTagsVsRecords(long recordID, long tagID) {
    SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(TAGS_VS_RECORDS_TAG_ID, tagID);
    contentValues.put(TAGS_VS_RECORDS_RECORD_ID, recordID);
    return sqLiteDatabase.insert(TABLE_TAGS_VS_RECORDS, null, contentValues);
  }
}
