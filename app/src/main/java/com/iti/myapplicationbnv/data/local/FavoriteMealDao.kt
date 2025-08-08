package com.iti.myapplicationbnv.data.data.local
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: FavoriteMeal)

    @Delete
    suspend fun delete(meal: FavoriteMeal)

    @Query("SELECT * FROM favorite_meals")
    fun getAll(): LiveData<List<FavoriteMeal>>


    @Query("SELECT * FROM favorite_meals WHERE id = :mealId")
    suspend fun getMealByIdSuspend(mealId: String): FavoriteMeal?


    @Query("SELECT * FROM favorite_meals WHERE id = :mealId")
    fun getMealById(mealId: String): LiveData<FavoriteMeal?>

}