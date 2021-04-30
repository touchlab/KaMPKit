# UserApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**bioAgeAppUserMeDevicesPost**](UserApi.md#bioAgeAppUserMeDevicesPost) | **POST** /bio-age-app/user/me/devices | Stores device token for the user
[**bioAgeAppUserMePurchasesPost**](UserApi.md#bioAgeAppUserMePurchasesPost) | **POST** /bio-age-app/user/me/purchases | Submit new mobile purchased receipt
[**bioAgeAppUserMeSubscriptionsPost**](UserApi.md#bioAgeAppUserMeSubscriptionsPost) | **POST** /bio-age-app/user/me/subscriptions | Submit new subscription trial
[**deleteUserSetting**](UserApi.md#deleteUserSetting) | **DELETE** /me/settings/{key} | 
[**getMyUser**](UserApi.md#getMyUser) | **GET** /bio-age-app/user/me | Return the logged in user
[**getSharedEvaluation**](UserApi.md#getSharedEvaluation) | **GET** /me/shared-evaluations/{hash} | 
[**getUserSettings**](UserApi.md#getUserSettings) | **GET** /me/settings | 
[**updateMyUser**](UserApi.md#updateMyUser) | **PUT** /bio-age-app/user/me | Updates user or usermeta
[**updateOrCreateUserSetting**](UserApi.md#updateOrCreateUserSetting) | **PUT** /me/settings/{key} | 
[**userExists**](UserApi.md#userExists) | **GET** /users | Check if a user exists


<a name="bioAgeAppUserMeDevicesPost"></a>
# **bioAgeAppUserMeDevicesPost**
> InlineResponse201 bioAgeAppUserMeDevicesPost(device)

Stores device token for the user

Through this endpoint you can store device token for logged user . 

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserApi()
val device : Device =  // Device | 
try {
    val result : InlineResponse201 = apiInstance.bioAgeAppUserMeDevicesPost(device)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#bioAgeAppUserMeDevicesPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#bioAgeAppUserMeDevicesPost")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **device** | [**Device**](Device.md)|  |

### Return type

[**InlineResponse201**](InlineResponse201.md)

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

<a name="bioAgeAppUserMePurchasesPost"></a>
# **bioAgeAppUserMePurchasesPost**
> bioAgeAppUserMePurchasesPost(userPurchasesInput)

Submit new mobile purchased receipt

This url validate and store subscription based on sent receipt 

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserApi()
val userPurchasesInput : UserPurchasesInput =  // UserPurchasesInput | 
try {
    apiInstance.bioAgeAppUserMePurchasesPost(userPurchasesInput)
} catch (e: ClientException) {
    println("4xx response calling UserApi#bioAgeAppUserMePurchasesPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#bioAgeAppUserMePurchasesPost")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userPurchasesInput** | [**UserPurchasesInput**](UserPurchasesInput.md)|  |

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

<a name="bioAgeAppUserMeSubscriptionsPost"></a>
# **bioAgeAppUserMeSubscriptionsPost**
> InlineResponse2011 bioAgeAppUserMeSubscriptionsPost(userSubscriptionInput)

Submit new subscription trial

This endpoint is now used only used for trial activation. Processing in-app purchases has moved to /bio-age-app/user/me/purchases 

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserApi()
val userSubscriptionInput : UserSubscriptionInput =  // UserSubscriptionInput | 
try {
    val result : InlineResponse2011 = apiInstance.bioAgeAppUserMeSubscriptionsPost(userSubscriptionInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#bioAgeAppUserMeSubscriptionsPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#bioAgeAppUserMeSubscriptionsPost")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSubscriptionInput** | [**UserSubscriptionInput**](UserSubscriptionInput.md)|  |

### Return type

[**InlineResponse2011**](InlineResponse2011.md)

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

<a name="deleteUserSetting"></a>
# **deleteUserSetting**
> deleteUserSetting(key)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserApi()
val key : kotlin.String = key_example // kotlin.String | 
try {
    apiInstance.deleteUserSetting(key)
} catch (e: ClientException) {
    println("4xx response calling UserApi#deleteUserSetting")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#deleteUserSetting")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **key** | **kotlin.String**|  |

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

<a name="getMyUser"></a>
# **getMyUser**
> UserResponse getMyUser()

Return the logged in user

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserApi()
try {
    val result : UserResponse = apiInstance.getMyUser()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#getMyUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#getMyUser")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**UserResponse**](UserResponse.md)

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

<a name="getSharedEvaluation"></a>
# **getSharedEvaluation**
> SharedEvaluationResponse getSharedEvaluation(hash)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserApi()
val hash : kotlin.String = hash_example // kotlin.String | A hash identifying a shared evaluation
try {
    val result : SharedEvaluationResponse = apiInstance.getSharedEvaluation(hash)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#getSharedEvaluation")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#getSharedEvaluation")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **hash** | **kotlin.String**| A hash identifying a shared evaluation |

### Return type

[**SharedEvaluationResponse**](SharedEvaluationResponse.md)

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

<a name="getUserSettings"></a>
# **getUserSettings**
> GetUserSettingsResponse getUserSettings()



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserApi()
try {
    val result : GetUserSettingsResponse = apiInstance.getUserSettings()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#getUserSettings")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#getUserSettings")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GetUserSettingsResponse**](GetUserSettingsResponse.md)

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

<a name="updateMyUser"></a>
# **updateMyUser**
> UserResponse updateMyUser(updateUserInput)

Updates user or usermeta

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserApi()
val updateUserInput : UpdateUserInput =  // UpdateUserInput | 
try {
    val result : UserResponse = apiInstance.updateMyUser(updateUserInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#updateMyUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#updateMyUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **updateUserInput** | [**UpdateUserInput**](UpdateUserInput.md)|  | [optional]

### Return type

[**UserResponse**](UserResponse.md)

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

<a name="updateOrCreateUserSetting"></a>
# **updateOrCreateUserSetting**
> PutUserSettingsResponse updateOrCreateUserSetting(key, putUserSettingsInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserApi()
val key : kotlin.String = key_example // kotlin.String | 
val putUserSettingsInput : PutUserSettingsInput =  // PutUserSettingsInput | 
try {
    val result : PutUserSettingsResponse = apiInstance.updateOrCreateUserSetting(key, putUserSettingsInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#updateOrCreateUserSetting")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#updateOrCreateUserSetting")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **key** | **kotlin.String**|  |
 **putUserSettingsInput** | [**PutUserSettingsInput**](PutUserSettingsInput.md)|  | [optional]

### Return type

[**PutUserSettingsResponse**](PutUserSettingsResponse.md)

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

<a name="userExists"></a>
# **userExists**
> userExists(email)

Check if a user exists

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = UserApi()
val email : kotlin.String = email_example // kotlin.String | 
try {
    apiInstance.userExists(email)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userExists")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userExists")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **email** | **kotlin.String**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

