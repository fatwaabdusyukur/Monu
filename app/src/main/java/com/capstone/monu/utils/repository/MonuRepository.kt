package com.capstone.monu.utils.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.capstone.monu.data.local.LocalDataSource
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.data.remote.RemoteDataSource
import com.capstone.monu.data.remote.model.FoodList
import com.capstone.monu.utils.vo.Resource
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
                val nutrient = StringBuilder().append("")

                for (res in data) {
                    if (res.food.nutrients != null) {
                        nutrient.append("${res.food.nutrients.calories}-${res.food.nutrients.carbohydrate}-${res.food.nutrients.fat}-${res.food.nutrients.fiber}-${res.food.nutrients.protein}").append(",")
                    }
                    localDataSource.addFoods(
                        FoodEntity(
                            id = res.food.id,
                            label = res.food.label,
                            image = res.food.image,
                            nutrient = nutrient.removeSuffix(",").toString()
                        )
                    )
                }
            }

        }.asLiveData()
    }

}