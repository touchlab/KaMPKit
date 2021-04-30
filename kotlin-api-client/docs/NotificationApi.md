# NotificationApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getNotifications**](NotificationApi.md#getNotifications) | **GET** /me/notifications | 
[**updateNotification**](NotificationApi.md#updateNotification) | **PATCH** /me/notifications/{notificationId} | 
[**updateNotificationAllRead**](NotificationApi.md#updateNotificationAllRead) | **PATCH** /me/notifications/actions/mark-all-as-read | 
[**updateNotifications**](NotificationApi.md#updateNotifications) | **PATCH** /me/notifications | 


<a name="getNotifications"></a>
# **getNotifications**
> GetNotificationsResponse getNotifications(since, perPage)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = NotificationApi()
val since : kotlin.String = 1975-12-25T14:15:16-05:00 // kotlin.String | 
val perPage : kotlin.Int = 56 // kotlin.Int | 
try {
    val result : GetNotificationsResponse = apiInstance.getNotifications(since, perPage)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NotificationApi#getNotifications")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationApi#getNotifications")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **since** | **kotlin.String**|  | [optional]
 **perPage** | **kotlin.Int**|  | [optional] [default to 100]

### Return type

[**GetNotificationsResponse**](GetNotificationsResponse.md)

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

<a name="updateNotification"></a>
# **updateNotification**
> updateNotification(notificationId, patchNotificationInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = NotificationApi()
val notificationId : kotlin.String = notificationId_example // kotlin.String | 
val patchNotificationInput : PatchNotificationInput =  // PatchNotificationInput | 
try {
    apiInstance.updateNotification(notificationId, patchNotificationInput)
} catch (e: ClientException) {
    println("4xx response calling NotificationApi#updateNotification")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationApi#updateNotification")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **notificationId** | **kotlin.String**|  |
 **patchNotificationInput** | [**PatchNotificationInput**](PatchNotificationInput.md)|  | [optional]

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

<a name="updateNotificationAllRead"></a>
# **updateNotificationAllRead**
> updateNotificationAllRead()



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = NotificationApi()
try {
    apiInstance.updateNotificationAllRead()
} catch (e: ClientException) {
    println("4xx response calling NotificationApi#updateNotificationAllRead")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationApi#updateNotificationAllRead")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

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

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="updateNotifications"></a>
# **updateNotifications**
> updateNotifications(patchNotificationsInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = NotificationApi()
val patchNotificationsInput : PatchNotificationsInput =  // PatchNotificationsInput | 
try {
    apiInstance.updateNotifications(patchNotificationsInput)
} catch (e: ClientException) {
    println("4xx response calling NotificationApi#updateNotifications")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NotificationApi#updateNotifications")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **patchNotificationsInput** | [**PatchNotificationsInput**](PatchNotificationsInput.md)|  | [optional]

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

