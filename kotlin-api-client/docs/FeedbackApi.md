# FeedbackApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addFeedback**](FeedbackApi.md#addFeedback) | **PUT** /me/feedbacks/{uuid} | 


<a name="addFeedback"></a>
# **addFeedback**
> addFeedback(uuid, addFeedbackInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = FeedbackApi()
val uuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val addFeedbackInput : AddFeedbackInput =  // AddFeedbackInput | 
try {
    apiInstance.addFeedback(uuid, addFeedbackInput)
} catch (e: ClientException) {
    println("4xx response calling FeedbackApi#addFeedback")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling FeedbackApi#addFeedback")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **uuid** | [**kotlin.String**](.md)|  |
 **addFeedbackInput** | [**AddFeedbackInput**](AddFeedbackInput.md)|  | [optional]

### Return type

null (empty response body)

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

