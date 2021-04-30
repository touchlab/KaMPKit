# VideosApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getVideoCategories**](VideosApi.md#getVideoCategories) | **GET** /video-categories | Get video categories
[**getVideos**](VideosApi.md#getVideos) | **GET** /videos | Get exercise videos


<a name="getVideoCategories"></a>
# **getVideoCategories**
> VideoCategoryResponse getVideoCategories()

Get video categories

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = VideosApi()
try {
    val result : VideoCategoryResponse = apiInstance.getVideoCategories()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VideosApi#getVideoCategories")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VideosApi#getVideoCategories")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**VideoCategoryResponse**](VideoCategoryResponse.md)

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

<a name="getVideos"></a>
# **getVideos**
> VideoCollectionResponse getVideos(type, lang, categoryId, trainerId, limit, offset)

Get exercise videos

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = VideosApi()
val type : kotlin.String = type_example // kotlin.String | Filter the kind of videos to receive. Workout gives you videos you can exercise to, while educational gives you videos that are educational.
val lang : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | Filter videos by language
val categoryId : kotlin.collections.List<kotlin.Int> =  // kotlin.collections.List<kotlin.Int> | Optional filter by one or more categories. See /video-categories resource for valid options.
val trainerId : kotlin.collections.List<kotlin.Int> =  // kotlin.collections.List<kotlin.Int> | Optional filter by one or more trainers. See /trainers resource for valid options.
val limit : kotlin.Int = 56 // kotlin.Int | Limit the number of videos to fetch to the given number and use pagination
val offset : kotlin.Int = 56 // kotlin.Int | What position should the result start from in a paginated result
try {
    val result : VideoCollectionResponse = apiInstance.getVideos(type, lang, categoryId, trainerId, limit, offset)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling VideosApi#getVideos")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling VideosApi#getVideos")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **type** | **kotlin.String**| Filter the kind of videos to receive. Workout gives you videos you can exercise to, while educational gives you videos that are educational. | [enum: educational, workout]
 **lang** | [**kotlin.collections.List&lt;kotlin.String&gt;**](kotlin.String.md)| Filter videos by language | [optional] [enum: nb_NO, en_US]
 **categoryId** | [**kotlin.collections.List&lt;kotlin.Int&gt;**](kotlin.Int.md)| Optional filter by one or more categories. See /video-categories resource for valid options. | [optional]
 **trainerId** | [**kotlin.collections.List&lt;kotlin.Int&gt;**](kotlin.Int.md)| Optional filter by one or more trainers. See /trainers resource for valid options. | [optional]
 **limit** | **kotlin.Int**| Limit the number of videos to fetch to the given number and use pagination | [optional]
 **offset** | **kotlin.Int**| What position should the result start from in a paginated result | [optional]

### Return type

[**VideoCollectionResponse**](VideoCollectionResponse.md)

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

