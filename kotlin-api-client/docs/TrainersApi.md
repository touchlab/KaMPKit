# TrainersApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getTrainers**](TrainersApi.md#getTrainers) | **GET** /trainers | Get trainers


<a name="getTrainers"></a>
# **getTrainers**
> TrainerCollectionResponse getTrainers()

Get trainers

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = TrainersApi()
try {
    val result : TrainerCollectionResponse = apiInstance.getTrainers()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TrainersApi#getTrainers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TrainersApi#getTrainers")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**TrainerCollectionResponse**](TrainerCollectionResponse.md)

### Authorization


Configure JWTAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""
Configure OAuth2:
    ApiClient.accessToken = ""
Configure OAuth2:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

