package com.example.semdam_1;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MasiniDAO {
    @Insert
    void insert (Masina masina);

    @Insert
    void insert(List<Masina> masinaList);

    @Query("select * from masini")
    List<Masina> getAll();

    @Delete
    void delete(Masina masina);

    @Update
    void update(Masina masina);

    @Query("delete from masini")
    void deleteAll();
}
