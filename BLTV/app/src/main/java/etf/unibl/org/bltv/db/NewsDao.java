package etf.unibl.org.bltv.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;


import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news WHERE content not null")
    List<News> getAll();
    @Insert
    long insert(News news);
    @Insert
    void insertAll(News... news);

    @Query("SELECT * FROM news WHERE id= :id")
    News findByID(int id);
    @Query("SELECT count(*) from news")
    int count();
    @Delete
    void deleteAll(News...news);
    @Query("DELETE FROM news")
    int clear();
    @Query("DELETE FROM news where id=:id")
    int delete(int id);

    @Query("UPDATE news set content=:content where id=:id")
    int update(int id,String content);

    @Update
    int update(News news);

}
