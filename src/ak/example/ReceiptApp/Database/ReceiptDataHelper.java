package ak.example.ReceiptApp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReceiptDataHelper extends SQLiteOpenHelper {

	public static final String TABLE_RECEIPTS = "receipts";
	public static final String RECEIPT_ID = "_id";
	public static final String RECEIPT_DATA = "data";

	private static final String DATABASE_NAME = "receipts.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_RECEIPTS + "(" + RECEIPT_ID
			+ " integer primary key autoincrement, " + RECEIPT_DATA
			+ " text not null);";

	public ReceiptDataHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ReceiptDataHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEIPTS);
		onCreate(db);
	}

} 


