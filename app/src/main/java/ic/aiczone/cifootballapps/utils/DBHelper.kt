package ic.aiczone.cifootballapps.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ic.aiczone.cifootballapps.entities.Event
import ic.aiczone.cifootballapps.entities.Team
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance as DBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Event.TABLE_EVENT, true,
                Event.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Event.EVENT_ID to TEXT + UNIQUE,
                Event.EVENT_NAME to TEXT,
                Event.EVENT_DATE to TEXT,
                Event.HOME_TEAM_ID to TEXT + UNIQUE,
                Event.HOME_TEAM_NAME to TEXT,
                Event.HOME_TEAM_SCORE to TEXT,
                Event.AWAY_TEAM_ID to TEXT + UNIQUE,
                Event.AWAY_TEAM_NAME to TEXT,
                Event.AWAY_TEAM_SCORE to TEXT)

        db?.createTable(Team.TABLE_TEAM, true,
                Team.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Team.TEAM_ID to TEXT + UNIQUE,
                Team.TEAM_NAME to TEXT,
                Team.TEAM_BADGE to TEXT,
                Team.TEAM_YEAR to TEXT,
                Team.TEAM_STADIUM to TEXT,
                Team.TEAM_DESCRIPTION to TEXT
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Event.TABLE_EVENT, true)
        db?.dropTable(Team.TABLE_TEAM, true)
    }
}

val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)