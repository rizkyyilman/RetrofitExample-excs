package com.catnip.retrofitsexamples.data.repository

import com.catnip.retrofitexample.Product
import com.catnip.retrofitexample.ProductService
import com.catnip.retrofitexample.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart


interface ProductRepository {
    suspend fun getProducts(): Flow<ResultWrapper<List<Product>>>
}


class ProductRepositoryImpl(private val apiService: ProductService) : ProductRepository {
    override suspend fun getProducts(): Flow<ResultWrapper<List<Product>>> {
        return flow {
            val response = apiService.getProducts()
            emit(
                if (response.products.isEmpty()) {
                    ResultWrapper.Empty(response.products)
                } else {
                    ResultWrapper.Success(response.products)
                }
            )
        }.catch { e ->
            emit(ResultWrapper.Error(exception = e))
        }.onStart {
            emit(ResultWrapper.Loading())
        }
    }
}

