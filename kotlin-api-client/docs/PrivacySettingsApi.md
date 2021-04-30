# PrivacySettingsApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getPrivacyConsents**](PrivacySettingsApi.md#getPrivacyConsents) | **GET** /me/privacy/consents | 
[**grantPrivacyConsent**](PrivacySettingsApi.md#grantPrivacyConsent) | **POST** /me/privacy/consents/{feature}/grant | 
[**revokePrivacyConsent**](PrivacySettingsApi.md#revokePrivacyConsent) | **POST** /me/privacy/consents/{feature}/revoke | 


<a name="getPrivacyConsents"></a>
# **getPrivacyConsents**
> PrivacyConsentsResponse getPrivacyConsents()



Get a list of decisions the user has made regarding consent per feature.

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = PrivacySettingsApi()
try {
    val result : PrivacyConsentsResponse = apiInstance.getPrivacyConsents()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PrivacySettingsApi#getPrivacyConsents")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PrivacySettingsApi#getPrivacyConsents")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**PrivacyConsentsResponse**](PrivacyConsentsResponse.md)

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

<a name="grantPrivacyConsent"></a>
# **grantPrivacyConsent**
> grantPrivacyConsent(feature)



Record that the user has granted consent to the feature. This endpoint is locked to trusted clients only. 

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = PrivacySettingsApi()
val feature : kotlin.String = feature_example // kotlin.String | 
try {
    apiInstance.grantPrivacyConsent(feature)
} catch (e: ClientException) {
    println("4xx response calling PrivacySettingsApi#grantPrivacyConsent")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PrivacySettingsApi#grantPrivacyConsent")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **feature** | **kotlin.String**|  | [enum: health_evaluation, share_workouts_in_team_feed]

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
 - **Accept**: application/json

<a name="revokePrivacyConsent"></a>
# **revokePrivacyConsent**
> revokePrivacyConsent(feature)



Record that the user has revoked consent to the feature. This endpoint is locked to trusted clients only. 

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = PrivacySettingsApi()
val feature : kotlin.String = feature_example // kotlin.String | 
try {
    apiInstance.revokePrivacyConsent(feature)
} catch (e: ClientException) {
    println("4xx response calling PrivacySettingsApi#revokePrivacyConsent")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PrivacySettingsApi#revokePrivacyConsent")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **feature** | **kotlin.String**|  | [enum: health_evaluation, share_workouts_in_team_feed]

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
 - **Accept**: application/json

