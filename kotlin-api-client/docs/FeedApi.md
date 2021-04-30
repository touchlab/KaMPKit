# FeedApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createFeedActivity**](FeedApi.md#createFeedActivity) | **POST** /feeds/{feedId}/activities | 
[**getFeedActivities**](FeedApi.md#getFeedActivities) | **GET** /feeds/{feedId}/activities | 


<a name="createFeedActivity"></a>
# **createFeedActivity**
> CreateFeedActivityResponse createFeedActivity(feedId, createFeedActivityInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = FeedApi()
val feedId : kotlin.String = 123e4567-e89b-12d3-a456-426655440000 // kotlin.String | uuid
val createFeedActivityInput : CreateFeedActivityInput =  // CreateFeedActivityInput | 
try {
    val result : CreateFeedActivityResponse = apiInstance.createFeedActivity(feedId, createFeedActivityInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FeedApi#createFeedActivity")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FeedApi#createFeedActivity")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **feedId** | **kotlin.String**| uuid |
 **createFeedActivityInput** | [**CreateFeedActivityInput**](CreateFeedActivityInput.md)|  |

### Return type

[**CreateFeedActivityResponse**](CreateFeedActivityResponse.md)

### Authorization


Configure JWTAuth:
    ApiClient.apiKey["Authorization"] = ""
    ApiClient.apiKeyPrefix["Authorization"] = ""
Configure OAuth2:
    ApiClient.accessToken = ""
Configure OAuth2:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFeedActivities"></a>
# **getFeedActivities**
> GetFeedActivitiesResponse getFeedActivities(feedId)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = FeedApi()
val feedId : kotlin.String = 123e4567-e89b-12d3-a456-426655440000 // kotlin.String | uuid
try {
    val result : GetFeedActivitiesResponse = apiInstance.getFeedActivities(feedId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling FeedApi#getFeedActivities")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FeedApi#getFeedActivities")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **feedId** | **kotlin.String**| uuid |

### Return type

[**GetFeedActivitiesResponse**](GetFeedActivitiesResponse.md)

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

