package ak.example.ReceiptApp.Database;

public class ReceiptData {

		private long id;
		private String receipt_data;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getComment() {
			return receipt_data;
		}

		public void setComment(String comment) {
			this.receipt_data = comment;
		}

		// Will be used by the ArrayAdapter in the ListView
		@Override
		public String toString() {
			return receipt_data;
		}
}
