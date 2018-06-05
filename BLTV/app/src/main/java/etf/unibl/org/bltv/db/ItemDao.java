package etf.unibl.org.bltv.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item")
    List<Item> getAll();
    @Insert
    long insert(Item item);
    @Insert
    void insertAll(Item... items);
    @Query("SELECT count(*) from item")
    int count();
    @Query("SELECT title,latitude,longitude from item")
    List<MapInfo> getMapInfo();
    @Query("SELECT * from item where title LIKE :title")
    List<Item> getByTitle(String title);
    @Query("SELECT * from item where is_liked=1")
    List<Item> getAllLiked();
    @Query("SELECT * from item where category=:category")
    List<Item> getByCategory(int category);
    @Query("SELECT * FROM item WHERE id= :id")
    List<Item> findByID(int id);
    @Delete
    void deleteAll(Item...items);
    @Query("DELETE FROM item")
    int clear();

    @Query("DELETE FROM item where id=:id")
    int delete(int id);

    @Update
    int update(Item item);
}
