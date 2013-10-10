/*
 * Copyright © 2013-2014 Xiong Zhijun, All Rights Reserved.
 */
package org.herod.alarmclock;

import java.util.List;

import org.herod.framework.db.DatabaseAccessSupport;
import org.herod.framework.db.DatabaseUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 */
public class AlarmClockDas extends DatabaseAccessSupport {
	private static final String ALARM_CLOCK = "ALARM_CLOCK";

	public AlarmClockDas(SQLiteOpenHelper openHelper) {
		super(openHelper);
	}

	public void add(AlarmClock alarmClock) {
		executeWrite(new NewWriter(alarmClock));
	}

	public void delete(long id) {
		executeWrite(new DeleteWriter(id));
	}

	public void update(AlarmClock alarmClock) {
		executeWrite(new UpdateWriter(alarmClock));
	}

	public List<AlarmClock> getAllAlarmClocks() {
		return executeRead(new Reader<List<AlarmClock>>() {
			public List<AlarmClock> read(SQLiteDatabase db) {
				Cursor cursor = db.query(ALARM_CLOCK, null, null, null, null,
						null, null);
				return DatabaseUtils.toList(cursor, -1, -1, AlarmClock.class);
			}
		});
	}

	class UpdateWriter implements Writer {
		private AlarmClock alarmClock;

		public UpdateWriter(AlarmClock alarmClock) {
			this.alarmClock = alarmClock;
		}

		@Override
		public void write(SQLiteDatabase db) {
			deleteAlarmClock(db, alarmClock.getId());
			insertAlarmClock(db, alarmClock);
		}

	}

	class DeleteWriter implements Writer {
		private long id;

		public DeleteWriter(long id) {
			this.id = id;
		}

		public void write(SQLiteDatabase db) {
			deleteAlarmClock(db, id);
		}

	}

	class NewWriter implements Writer {
		private AlarmClock alarmClock;

		public NewWriter(AlarmClock alarmClock) {
			this.alarmClock = alarmClock;
		}

		public void write(SQLiteDatabase db) {
			insertAlarmClock(db, alarmClock);
		}
	}

	private void deleteAlarmClock(SQLiteDatabase db, long id) {
		db.delete(ALARM_CLOCK, "ID=?", new String[] { id + "" });
	}

	protected void insertAlarmClock(SQLiteDatabase db, AlarmClock alarmClock) {
		List<String> columns = getAllColumnsByDatabase(db, ALARM_CLOCK);
		ContentValues values = DatabaseUtils.toContentValues(alarmClock,
				columns);
		db.insert(ALARM_CLOCK, null, values);
	}
}
