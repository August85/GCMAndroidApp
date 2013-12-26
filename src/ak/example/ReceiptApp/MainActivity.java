package ak.example.ReceiptApp;

import static ak.example.ReceiptApp.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static ak.example.ReceiptApp.CommonUtilities.EXTRA_MESSAGE;

import java.util.List;

import ak.example.ReceiptApp.Database.ReceiptData;
import ak.example.ReceiptApp.Database.ReceiptDataSource;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity{
	private ReceiptDataSource receiptDataSource;

	 //TextView mDisplay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		//mDisplay = (TextView) findViewById(R.id.display_main_activity);
		registerReceiver(mHandleMessageReceiver,new IntentFilter(DISPLAY_MESSAGE_ACTION));
		receiptDataSource = new ReceiptDataSource(this);
		receiptDataSource.open();
		List<ReceiptData> values = receiptDataSource.getAllReceipts();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<ReceiptData> adapter = new ArrayAdapter<ReceiptData>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		
		//set action listeners
		this.setActionListeners();
		//set contextual menus
		//this.setActionOnLongPress();
	}

	private final BroadcastReceiver mHandleMessageReceiver =
            new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
           // mDisplay.append(newMessage + "\n");
            // add the incoming receipt data to the database
            
            @SuppressWarnings("unchecked")
    		ArrayAdapter<ReceiptData> adapter = (ArrayAdapter<ReceiptData>) getListAdapter();
    		ReceiptData receiptData = null;

            receiptData = receiptDataSource.createReceipt(newMessage);
			adapter.add(receiptData);
        }
    };
    
    @Override
    protected void onDestroy() {
        unregisterReceiver(mHandleMessageReceiver);
        super.onDestroy();
    }
    
    @Override
	protected void onResume() {
    	receiptDataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		receiptDataSource.close();
		super.onPause();
	}

	private void setActionListeners() {
		
		// add action listener for simple select
				ListView listView = (ListView) findViewById(android.R.id.list);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
						Toast.makeText(getApplicationContext(),
							"Click Receipt Number " + position, Toast.LENGTH_SHORT)
							.show();
					}
				});
	}

	/*private void setActionOnLongPress() {
		
		getListView().setOnCreateContextMenuListener(null);
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (mActionMode != null) {
					return false;
				}
				selectedItem = position;

				// Start the CAB using the ActionMode.Callback defined above
				mActionMode = MyListActivityActionbar.this
						.startActionMode(mActionModeCallback);
				view.setSelected(true);
				return true;
			}
		});
	
	}
*/	
}
