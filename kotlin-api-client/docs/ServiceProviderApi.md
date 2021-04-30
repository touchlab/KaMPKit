# ServiceProviderApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUserService**](ServiceProviderApi.md#deleteUserService) | **DELETE** /me/services/{serviceProvider} | 
[**getUserServices**](ServiceProviderApi.md#getUserServices) | **GET** /me/services | 
[**serviceProviderConnect**](ServiceProviderApi.md#serviceProviderConnect) | **POST** /me/services/{serviceProvider}/connect | 


<a name="deleteUserService"></a>
# **deleteUserService**
> deleteUserService(serviceProvider)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = ServiceProviderApi()
val serviceProvider : kotlin.String = serviceProvider_example // kotlin.String | 
try {
    apiInstance.deleteUserService(serviceProvider)
} catch (e: ClientException) {
    println("4xx response calling ServiceProviderApi#deleteUserService")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ServiceProviderApi#deleteUserService")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceProvider** | **kotlin.String**|  | [enum: garmin, polar, fitbit]

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

<a name="getUserServices"></a>
# **getUserServices**
> GetUserServicesResponse getUserServices()



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = ServiceProviderApi()
try {
    val result : GetUserServicesResponse = apiInstance.getUserServices()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ServiceProviderApi#getUserServices")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ServiceProviderApi#getUserServices")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GetUserServicesResponse**](GetUserServicesResponse.md)

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

<a name="serviceProviderConnect"></a>
# **serviceProviderConnect**
> CreateServiceProviderConnectResponse serviceProviderConnect(serviceProvider)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = ServiceProviderApi()
val serviceProvider : kotlin.String = serviceProvider_example // kotlin.String | 
try {
    val result : CreateServiceProviderConnectResponse = apiInstance.serviceProviderConnect(serviceProvider)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ServiceProviderApi#serviceProviderConnect")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ServiceProviderApi#serviceProviderConnect")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceProvider** | **kotlin.String**|  | [enum: garmin, polar, fitbit]

### Return type

[**CreateServiceProviderConnectResponse**](CreateServiceProviderConnectResponse.md)

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

