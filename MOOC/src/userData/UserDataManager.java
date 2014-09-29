package userData;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDataManager extends Activity {
	private static final String TAG = "UserDataManager";
	private static final String DB_NAME = "user2.db";
	private static final String TABLE_NAME = "userInfor";
	public static final String ID = "_id";

	public static final String USER_NAME = "user_name";
	public static final String USER_PWD = "user_pwd";
	public static final String USER_Email = "user_email";
	public static final String SILENT = "silent";
	public static final String VIBRATE = "vibrate";

	private static final int DB_VERSION = 2;
	private Context mContext = null;

//	private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
//			+ ID + " integer primary key," + USER_NAME + " text,"
//			+ USER_PWD + " varchar" + ");";
	
	private static final String DB_CREATE="create table if not exists userInfor(_id integer primary key autoincrement,"
			+ "user_name text not null,user_pwd integer not null,user_email varchar(20) not null)";

	private SQLiteDatabase mSQLiteDatabase = null;
	private DataBaseManagementHelper mDatabaseHelper = null;
	

	private static class DataBaseManagementHelper extends SQLiteOpenHelper {

		DataBaseManagementHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(TAG,"db.getVersion()="+db.getVersion());
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
			db.execSQL(DB_CREATE);
			ContentValues values=new ContentValues();
			values.put("user_name", "Ҷǿ");
			values.put("user_pwd", "090152");
			db.insert("users", null, values);
			Log.i(TAG, "db.execSQL(DB_CREATE)");
			Log.e(TAG, DB_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i(TAG, "DataBaseManagementHelper onUpgrade");
			onCreate(db);
		}
	}

	public UserDataManager(Context context) {
		mContext = context;
		Log.i(TAG, "UserDataManager construction!");
	}

	public void openDataBase() throws SQLException {
		
		mDatabaseHelper = new DataBaseManagementHelper(mContext);
		mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
	}

	public void closeDataBase() throws SQLException {

		mDatabaseHelper.close();
	}

	public long insertUserData(UserData userData) {
		
		String userName=userData.getUserName();
		String userPwd=userData.getUserPwd();
		String userEmail=userData.getUserEmail();

		ContentValues values = new ContentValues();
		values.put(USER_NAME, userName);
		values.put(USER_PWD, userPwd);
		values.put(USER_Email, userEmail);
		return mSQLiteDatabase.insert(TABLE_NAME, ID, values);
	}

	public boolean updateUserData(UserData userData) {

		int id = userData.getUserId();
		String userName = userData.getUserName();
		String userPwd = userData.getUserPwd();
		String userEmail=userData.getUserEmail();

		ContentValues values = new ContentValues();
		values.put(USER_NAME, userName);
		values.put(USER_PWD, userPwd);
		values.put(USER_Email, userEmail);
		return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
	}

	public Cursor fetchUserData(int id) throws SQLException {

		Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME, null, ID
				+ "=" + id, null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchAllUserDatas() {

		return mSQLiteDatabase.query(TABLE_NAME, null, null, null, null, null,
				null);
	}

	public boolean deleteUserData(int id) {

		return mSQLiteDatabase.delete(TABLE_NAME, ID + "=" + id, null) > 0;
	}

	public boolean deleteAllUserDatas() {

		return mSQLiteDatabase.delete(TABLE_NAME, null, null) > 0;
	}


	public String getStringByColumnName(String columnName, int id) {
		Cursor mCursor = fetchUserData(id);
		int columnIndex = mCursor.getColumnIndex(columnName);
		String columnValue = mCursor.getString(columnIndex);
		mCursor.close();
		return columnValue;
	}


	public boolean updateUserDataById(String columnName, int id,
			String columnValue) {
		ContentValues values = new ContentValues();
		values.put(columnName, columnValue);
		return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
	}
	
	public int findUserByName(String userName){
		Log.i(TAG,"findUserByName , userName="+userName);
		int result=0;
		Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName, null, null, null, null);
		
		if(mCursor!=null){
			result=mCursor.getCount();
			mCursor.close();
			Log.i(TAG,"findUserByName , result="+result);
		}
		else{
			result=0;
		}
		return result;
	}
	
	public boolean findUserByNameAndPwd(String userName,String userPwd){
		Log.i(TAG,"findUserByNameAndPwd");
		
		SQLiteDatabase sdb=mDatabaseHelper.getReadableDatabase();
		String sql="select * from userInfor where user_name=? and user_pwd=?";
		Cursor mCursor=sdb.rawQuery(sql, new String[]{userName,userPwd});
		if(mCursor.moveToFirst()==true){
			
			mCursor.close();
			return true;
			
		}
		
		return false;
	}

}
