# UserAnalysisApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getUserActivityProgress**](UserAnalysisApi.md#getUserActivityProgress) | **GET** /me/activity-progress | 


<a name="getUserActivityProgress"></a>
# **getUserActivityProgress**
> GetUserActivityProgressResponse getUserActivityProgress(date)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserAnalysisApi()
val date : kotlin.String = 1975-12-25T14:15:16-05:00 // kotlin.String | 
try {
    val result : GetUserActivityProgressResponse = apiInstance.getUserActivityProgress(date)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserAnalysisApi#getUserActivityProgress")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserAnalysisApi#getUserActivityProgress")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **date** | **kotlin.String**|  |

### Return type

[**GetUserActivityProgressResponse**](GetUserActivityProgressResponse.md)

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

