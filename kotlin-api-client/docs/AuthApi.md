# AuthApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createSsoToken**](AuthApi.md#createSsoToken) | **POST** /sso-tokens | 
[**login**](AuthApi.md#login) | **POST** /bio-age-app/login | Retiving JWT token for the user
[**resetPassword**](AuthApi.md#resetPassword) | **POST** /bio-age-app/reset-pass | Reset user&#39;s password
[**revokeOAuthTokens**](AuthApi.md#revokeOAuthTokens) | **POST** /oauth/x-revoke | Revoke access/refresh tokens.


<a name="createSsoToken"></a>
# **createSsoToken**
> SsoTokenResponse createSsoToken()



Creates a short lived token that can be used for logging in during authorization process

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = AuthApi()
try {
    val result : SsoTokenResponse = apiInstance.createSsoToken()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#createSsoToken")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#createSsoToken")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**SsoTokenResponse**](SsoTokenResponse.md)

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

<a name="login"></a>
# **login**
> UserWithJWTResponseDeprecated login(email, password)

Retiving JWT token for the user

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = AuthApi()
val email : kotlin.String = email_example // kotlin.String | 
val password : kotlin.String = password_example // kotlin.String | 
try {
    val result : UserWithJWTResponseDeprecated = apiInstance.login(email, password)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#login")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#login")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **email** | **kotlin.String**|  |
 **password** | **kotlin.String**|  |

### Return type

[**UserWithJWTResponseDeprecated**](UserWithJWTResponseDeprecated.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/x-www-form-urlencoded
 - **Accept**: application/json

<a name="resetPassword"></a>
# **resetPassword**
> resetPassword(email)

Reset user&#39;s password

Start reset password process. 

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = AuthApi()
val email : kotlin.String = email_example // kotlin.String | 
try {
    apiInstance.resetPassword(email)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#resetPassword")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#resetPassword")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **email** | **kotlin.String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/x-www-form-urlencoded
 - **Accept**: application/json

<a name="revokeOAuthTokens"></a>
# **revokeOAuthTokens**
> revokeOAuthTokens(clientId)

Revoke access/refresh tokens.

A custom extension on oauth to revoke tokens for the user

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = AuthApi()
val clientId : kotlin.String = clientId_example // kotlin.String | 
try {
    apiInstance.revokeOAuthTokens(clientId)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#revokeOAuthTokens")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#revokeOAuthTokens")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clientId** | **kotlin.String**|  |

### Return type

null (empty response body)

### Authorization


Configure OAuth2:
    ApiClient.accessToken = ""
Configure OAuth2:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/x-www-form-urlencoded
 - **Accept**: application/json

