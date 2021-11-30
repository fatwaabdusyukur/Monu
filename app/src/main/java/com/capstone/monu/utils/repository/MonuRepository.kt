package com.capstone.monu.utils.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.capstone.monu.data.local.LocalDataSource
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.data.remote.RemoteDataSource
import com.capstone.monu.data.remote.model.FoodList
import com.capstone.monu.utils.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class MonuRepository private constructor(private val localDataSource: LocalDataSource,
                                        private val remoteDataSource: RemoteDataSource,
                                         private val contextProviders: ContextProviders
                                         ) : MonuDataSource {

    companion object {
        @Volatile
        private var instance: MonuRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, ctx : ContextProviders) : MonuRepository =
            instance ?: synchronized(this) {
                instance ?: MonuRepository(localData, remoteData, ctx)
            }
    }

    override fun getFoods(): LiveData<Resource<PagedList<FoodEntity>>> {
        return object : NetworkBoundResource<PagedList<FoodEntity>, List<FoodList>>(contextProviders) {
            override fun loadFromDb(): LiveData<PagedList<FoodEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.obtainFoods(), config).build()
            }

            override fun shouldFetch(data: PagedList<FoodEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<FoodList>>> =
                remoteDataSource.getFoods()

            override fun saveCallResult(data: List<FoodList>) {
                val ingredientLines = StringBuilder().append("")
                val ingredientImage = StringBuilder().append("")
                for (item in data) {
                    val id = item.food.id.split("_").toTypedArray()

                    item.food.ingredient.forEach {
                        ingredientImage.append(it.image).append("-")
                    }

                    item.food.ingredientLines.forEach {
                        ingredientLines.append(it).append("-")
                    }

                   localDataSource.addFoods(
                       FoodEntity(
                           id = id[1],
                           label = item.food.label,
                           image = item.food.image,
                           calories = item.food.calories,
                           ingredientLines = ingredientLines.toString(),
                           ingredientImage = ingredientImage.toString(),
                           fat = item.food.nutrients.fat.quantity,
                           carbohydrate = item.food.nutrients.carbohydrate.quantity,
                           protein = item.food.nutrients.protein.quantity
                       )
                   )
                }
            }

        }.asLiveData()
    }

    override fun addDailySchedule(dailyEntity: DailyEntity, scope : CoroutineScope) {
        scope.launch(Dispatchers.IO) {
            localDataSource.addDailyFood(dailyEntity)
        }
    }

    override fun getAllDailySchedule(): LiveData<PagedList<DailyEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.obtainDaily(), config).build()
    }

    override fun getDailySchedule(id: Int): LiveData<DailyEntity> {
        return localDataSource.obtainDailyById(id)
    }


}