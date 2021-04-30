# TeamApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**acceptTeamInvite**](TeamApi.md#acceptTeamInvite) | **PUT** /teams/invites/{token} | 
[**getTeamInviteTokenData**](TeamApi.md#getTeamInviteTokenData) | **GET** /teams/invites/{token} | 
[**getTeamMembers**](TeamApi.md#getTeamMembers) | **GET** /teams/{teamUuid}/members | 
[**getUserTeamContests**](TeamApi.md#getUserTeamContests) | **GET** /me/teams/{teamId}/contests | 
[**getUserTeamMembers**](TeamApi.md#getUserTeamMembers) | **GET** /me/teams/{teamId}/members | 
[**getUserTeams**](TeamApi.md#getUserTeams) | **GET** /me/teams | 
[**sendTeamInviteAsSms**](TeamApi.md#sendTeamInviteAsSms) | **POST** /teams/invites/{token}/invite-sms | Sends the invite corresponding to the invite token to the provided phone number
[**updateTeamMembers**](TeamApi.md#updateTeamMembers) | **PATCH** /teams/{teamUuid}/members | 


<a name="acceptTeamInvite"></a>
# **acceptTeamInvite**
> acceptTeamInvite(token)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = TeamApi()
val token : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    apiInstance.acceptTeamInvite(token)
} catch (e: ClientException) {
    println("4xx response calling TeamApi#acceptTeamInvite")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TeamApi#acceptTeamInvite")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **token** | [**kotlin.String**](.md)|  |

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

<a name="getTeamInviteTokenData"></a>
# **getTeamInviteTokenData**
> GetTeamInviteTokenDataResponse getTeamInviteTokenData(token)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = TeamApi()
val token : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    val result : GetTeamInviteTokenDataResponse = apiInstance.getTeamInviteTokenData(token)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TeamApi#getTeamInviteTokenData")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TeamApi#getTeamInviteTokenData")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **token** | [**kotlin.String**](.md)|  |

### Return type

[**GetTeamInviteTokenDataResponse**](GetTeamInviteTokenDataResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTeamMembers"></a>
# **getTeamMembers**
> GetTeamMembersResponse getTeamMembers(teamUuid)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = TeamApi()
val teamUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    val result : GetTeamMembersResponse = apiInstance.getTeamMembers(teamUuid)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TeamApi#getTeamMembers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TeamApi#getTeamMembers")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **teamUuid** | [**kotlin.String**](.md)|  |

### Return type

[**GetTeamMembersResponse**](GetTeamMembersResponse.md)

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

<a name="getUserTeamContests"></a>
# **getUserTeamContests**
> GetTeamContestsResponse getUserTeamContests(teamId)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = TeamApi()
val teamId : kotlin.String = teamId_example // kotlin.String | 
try {
    val result : GetTeamContestsResponse = apiInstance.getUserTeamContests(teamId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TeamApi#getUserTeamContests")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TeamApi#getUserTeamContests")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **teamId** | **kotlin.String**|  |

### Return type

[**GetTeamContestsResponse**](GetTeamContestsResponse.md)

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

<a name="getUserTeamMembers"></a>
# **getUserTeamMembers**
> GetUserTeamMembersResponse getUserTeamMembers(teamId)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = TeamApi()
val teamId : kotlin.String = teamId_example // kotlin.String | 
try {
    val result : GetUserTeamMembersResponse = apiInstance.getUserTeamMembers(teamId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TeamApi#getUserTeamMembers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TeamApi#getUserTeamMembers")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **teamId** | **kotlin.String**|  |

### Return type

[**GetUserTeamMembersResponse**](GetUserTeamMembersResponse.md)

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

<a name="getUserTeams"></a>
# **getUserTeams**
> GetTeamsResponse getUserTeams()



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = TeamApi()
try {
    val result : GetTeamsResponse = apiInstance.getUserTeams()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TeamApi#getUserTeams")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TeamApi#getUserTeams")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GetTeamsResponse**](GetTeamsResponse.md)

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

<a name="sendTeamInviteAsSms"></a>
# **sendTeamInviteAsSms**
> sendTeamInviteAsSms(token, sendTeamInviteAsSmsInput)

Sends the invite corresponding to the invite token to the provided phone number

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = TeamApi()
val token : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val sendTeamInviteAsSmsInput : SendTeamInviteAsSmsInput =  // SendTeamInviteAsSmsInput | 
try {
    apiInstance.sendTeamInviteAsSms(token, sendTeamInviteAsSmsInput)
} catch (e: ClientException) {
    println("4xx response calling TeamApi#sendTeamInviteAsSms")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TeamApi#sendTeamInviteAsSms")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **token** | [**kotlin.String**](.md)|  |
 **sendTeamInviteAsSmsInput** | [**SendTeamInviteAsSmsInput**](SendTeamInviteAsSmsInput.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateTeamMembers"></a>
# **updateTeamMembers**
> GetTeamMembersResponse updateTeamMembers(teamUuid, updateTeamMembersInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = TeamApi()
val teamUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val updateTeamMembersInput : UpdateTeamMembersInput =  // UpdateTeamMembersInput | 
try {
    val result : GetTeamMembersResponse = apiInstance.updateTeamMembers(teamUuid, updateTeamMembersInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TeamApi#updateTeamMembers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TeamApi#updateTeamMembers")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **teamUuid** | [**kotlin.String**](.md)|  |
 **updateTeamMembersInput** | [**UpdateTeamMembersInput**](UpdateTeamMembersInput.md)|  | [optional]

### Return type

[**GetTeamMembersResponse**](GetTeamMembersResponse.md)

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

