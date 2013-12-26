package ak.example.ReceiptApp.Database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ReceiptDataSource {

	// Database fields
	private SQLiteDatabase database;
	private ReceiptDataHelper dbHelper;
	private String[] allColumns = { ReceiptDataHelper.RECEIPT_ID,
			ReceiptDataHelper.RECEIPT_DATA };

	public ReceiptDataSource(Context context) {
		dbHelper = new ReceiptDataHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public ReceiptData createReceipt(String comment) {
		ContentValues values = new ContentValues();
		values.put(ReceiptDataHelper.RECEIPT_DATA, comment);
		long insertId = database.insert(ReceiptDataHelper.TABLE_RECEIPTS, null,
				values);
		Cursor cursor = database.query(ReceiptDataHelper.TABLE_RECEIPTS,
				allColumns, ReceiptDataHelper.RECEIPT_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		ReceiptData newComment = cursorToData(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteReceipt(ReceiptData comment) {
		long id = comment.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(ReceiptDataHelper.TABLE_RECEIPTS, ReceiptDataHelper.RECEIPT_ID
				+ " = " + id, null);
	}

	public List<ReceiptData> getAllReceipts() {
		List<ReceiptData> comments = new ArrayList<ReceiptData>();

		Cursor cursor = database.query(ReceiptDataHelper.TABLE_RECEIPTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ReceiptData comment = cursorToData(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}

	private ReceiptData cursorToData(Cursor cursor) {
		ReceiptData comment = new ReceiptData();
		comment.setId(cursor.getLong(0));
		comment.setComment(cursor.getString(1));
		return comment;
	}
}