# IdentityProviderApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**connectUsingIdentityProvider**](IdentityProviderApi.md#connectUsingIdentityProvider) | **POST** /idp/{identityProvider}/connect | Connect a user with an identity provider using a token from the IdP
[**loginUsingIdentityProvider**](IdentityProviderApi.md#loginUsingIdentityProvider) | **POST** /idp/{identityProvider}/login | Exchange a request token from a IdP to a OAuth 2.0 access tokens


<a name="connectUsingIdentityProvider"></a>
# **connectUsingIdentityProvider**
> connectUsingIdentityProvider(identityProvider, connectUsingIdentityProviderInput)

Connect a user with an identity provider using a token from the IdP

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = IdentityProviderApi()
val identityProvider : kotlin.String = identityProvider_example // kotlin.String | Name of the Identity Provider
val connectUsingIdentityProviderInput : ConnectUsingIdentityProviderInput =  // ConnectUsingIdentityProviderInput | 
try {
    apiInstance.connectUsingIdentityProvider(identityProvider, connectUsingIdentityProviderInput)
} catch (e: ClientException) {
    println("4xx response calling IdentityProviderApi#connectUsingIdentityProvider")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling IdentityProviderApi#connectUsingIdentityProvider")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **identityProvider** | **kotlin.String**| Name of the Identity Provider |
 **connectUsingIdentityProviderInput** | [**ConnectUsingIdentityProviderInput**](ConnectUsingIdentityProviderInput.md)|  | [optional]

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

<a name="loginUsingIdentityProvider"></a>
# **loginUsingIdentityProvider**
> LoginUsingIdentityProviderResponse loginUsingIdentityProvider(identityProvider, loginUsingIdentityProviderInput)

Exchange a request token from a IdP to a OAuth 2.0 access tokens

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = IdentityProviderApi()
val identityProvider : kotlin.String = identityProvider_example // kotlin.String | Name of the Identity Provider
val loginUsingIdentityProviderInput : LoginUsingIdentityProviderInput =  // LoginUsingIdentityProviderInput | 
try {
    val result : LoginUsingIdentityProviderResponse = apiInstance.loginUsingIdentityProvider(identityProvider, loginUsingIdentityProviderInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling IdentityProviderApi#loginUsingIdentityProvider")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling IdentityProviderApi#loginUsingIdentityProvider")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **identityProvider** | **kotlin.String**| Name of the Identity Provider |
 **loginUsingIdentityProviderInput** | [**LoginUsingIdentityProviderInput**](LoginUsingIdentityProviderInput.md)|  | [optional]

### Return type

[**LoginUsingIdentityProviderResponse**](LoginUsingIdentityProviderResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

