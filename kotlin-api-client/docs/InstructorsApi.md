# InstructorsApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createClientGroup**](InstructorsApi.md#createClientGroup) | **POST** /instructors/{clinic}/client-groups | 
[**createSharedEvaluation**](InstructorsApi.md#createSharedEvaluation) | **POST** /instructors/{clinic}/clients/{client}/shared-evaluations | 
[**deleteClientGroup**](InstructorsApi.md#deleteClientGroup) | **DELETE** /instructors/{clinic}/client-groups/{clientGroup} | 
[**getClient**](InstructorsApi.md#getClient) | **GET** /instructors/{clinic}/clients/{client} | Return the client with the provided id
[**getClientGroups**](InstructorsApi.md#getClientGroups) | **GET** /instructors/{clinic}/client-groups | 
[**getClientWorkouts**](InstructorsApi.md#getClientWorkouts) | **GET** /instructors/{clinic}/clients/{client}/workouts | Paginated list of workouts for the provided user (100 per page)
[**updateClientGroup**](InstructorsApi.md#updateClientGroup) | **PATCH** /instructors/{clinic}/client-groups/{clientGroup} | 
[**updateClientGroupMembers**](InstructorsApi.md#updateClientGroupMembers) | **PATCH** /instructors/{clinic}/client-groups/{clientGroup}/members | 


<a name="createClientGroup"></a>
# **createClientGroup**
> ClientGroupResponse createClientGroup(clinic, createClientGroupInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = InstructorsApi()
val clinic : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | uuid of the clinic
val createClientGroupInput : CreateClientGroupInput =  // CreateClientGroupInput | 
try {
    val result : ClientGroupResponse = apiInstance.createClientGroup(clinic, createClientGroupInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstructorsApi#createClientGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstructorsApi#createClientGroup")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clinic** | [**kotlin.String**](.md)| uuid of the clinic |
 **createClientGroupInput** | [**CreateClientGroupInput**](CreateClientGroupInput.md)|  | [optional]

### Return type

[**ClientGroupResponse**](ClientGroupResponse.md)

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

<a name="createSharedEvaluation"></a>
# **createSharedEvaluation**
> SharedEvaluationResponse createSharedEvaluation(clinic, client, createSharedEvaluationInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = InstructorsApi()
val clinic : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | uuid of the belonging clinic
val client : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | uuid of the client
val createSharedEvaluationInput : CreateSharedEvaluationInput =  // CreateSharedEvaluationInput | 
try {
    val result : SharedEvaluationResponse = apiInstance.createSharedEvaluation(clinic, client, createSharedEvaluationInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstructorsApi#createSharedEvaluation")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstructorsApi#createSharedEvaluation")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clinic** | [**kotlin.String**](.md)| uuid of the belonging clinic |
 **client** | [**kotlin.String**](.md)| uuid of the client |
 **createSharedEvaluationInput** | [**CreateSharedEvaluationInput**](CreateSharedEvaluationInput.md)|  | [optional]

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

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteClientGroup"></a>
# **deleteClientGroup**
> deleteClientGroup(clinic, clientGroup)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = InstructorsApi()
val clinic : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | uuid of the clinic
val clientGroup : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | uuid of the client group
try {
    apiInstance.deleteClientGroup(clinic, clientGroup)
} catch (e: ClientException) {
    println("4xx response calling InstructorsApi#deleteClientGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstructorsApi#deleteClientGroup")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clinic** | [**kotlin.String**](.md)| uuid of the clinic |
 **clientGroup** | [**kotlin.String**](.md)| uuid of the client group |

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

<a name="getClient"></a>
# **getClient**
> ClientResponse getClient(clinic, client)

Return the client with the provided id

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = InstructorsApi()
val clinic : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | Organization uuid of the clinic
val client : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | User uuid of the client
try {
    val result : ClientResponse = apiInstance.getClient(clinic, client)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstructorsApi#getClient")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstructorsApi#getClient")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clinic** | [**kotlin.String**](.md)| Organization uuid of the clinic |
 **client** | [**kotlin.String**](.md)| User uuid of the client |

### Return type

[**ClientResponse**](ClientResponse.md)

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

<a name="getClientGroups"></a>
# **getClientGroups**
> ClientGroupsResponse getClientGroups(clinic)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = InstructorsApi()
val clinic : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | uuid of the clinic
try {
    val result : ClientGroupsResponse = apiInstance.getClientGroups(clinic)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstructorsApi#getClientGroups")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstructorsApi#getClientGroups")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clinic** | [**kotlin.String**](.md)| uuid of the clinic |

### Return type

[**ClientGroupsResponse**](ClientGroupsResponse.md)

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

<a name="getClientWorkouts"></a>
# **getClientWorkouts**
> GetClientWorkoutsResponse getClientWorkouts(clinic, client, page)

Paginated list of workouts for the provided user (100 per page)

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = InstructorsApi()
val clinic : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | Organization id of the clinic
val client : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | User id of the client
val page : kotlin.Int = 56 // kotlin.Int | 
try {
    val result : GetClientWorkoutsResponse = apiInstance.getClientWorkouts(clinic, client, page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstructorsApi#getClientWorkouts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstructorsApi#getClientWorkouts")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clinic** | [**kotlin.String**](.md)| Organization id of the clinic |
 **client** | [**kotlin.String**](.md)| User id of the client |
 **page** | **kotlin.Int**|  | [optional]

### Return type

[**GetClientWorkoutsResponse**](GetClientWorkoutsResponse.md)

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

<a name="updateClientGroup"></a>
# **updateClientGroup**
> ClientGroupResponse updateClientGroup(clinic, clientGroup, updateClientGroupInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = InstructorsApi()
val clinic : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | uuid of the clinic
val clientGroup : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | uuid of the client group
val updateClientGroupInput : UpdateClientGroupInput =  // UpdateClientGroupInput | 
try {
    val result : ClientGroupResponse = apiInstance.updateClientGroup(clinic, clientGroup, updateClientGroupInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstructorsApi#updateClientGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstructorsApi#updateClientGroup")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clinic** | [**kotlin.String**](.md)| uuid of the clinic |
 **clientGroup** | [**kotlin.String**](.md)| uuid of the client group |
 **updateClientGroupInput** | [**UpdateClientGroupInput**](UpdateClientGroupInput.md)|  | [optional]

### Return type

[**ClientGroupResponse**](ClientGroupResponse.md)

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

<a name="updateClientGroupMembers"></a>
# **updateClientGroupMembers**
> ClientGroupMembersResponse updateClientGroupMembers(clinic, clientGroup, updateClientGroupMembersInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = InstructorsApi()
val clinic : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | uuid of the clinic
val clientGroup : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | uuid of the client group
val updateClientGroupMembersInput : UpdateClientGroupMembersInput =  // UpdateClientGroupMembersInput | 
try {
    val result : ClientGroupMembersResponse = apiInstance.updateClientGroupMembers(clinic, clientGroup, updateClientGroupMembersInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InstructorsApi#updateClientGroupMembers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InstructorsApi#updateClientGroupMembers")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **clinic** | [**kotlin.String**](.md)| uuid of the clinic |
 **clientGroup** | [**kotlin.String**](.md)| uuid of the client group |
 **updateClientGroupMembersInput** | [**UpdateClientGroupMembersInput**](UpdateClientGroupMembersInput.md)|  | [optional]

### Return type

[**ClientGroupMembersResponse**](ClientGroupMembersResponse.md)

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

