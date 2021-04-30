# WorkoutApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addWorkout**](WorkoutApi.md#addWorkout) | **POST** /me/workouts | 
[**deleteWorkout**](WorkoutApi.md#deleteWorkout) | **DELETE** /me/workouts/{workoutId} | 
[**getWorkout**](WorkoutApi.md#getWorkout) | **GET** /me/workouts/{workoutId} | 
[**getWorkoutHeartRates**](WorkoutApi.md#getWorkoutHeartRates) | **GET** /me/workouts/{workoutId}/heart-rates | 
[**getWorkoutTimeseries**](WorkoutApi.md#getWorkoutTimeseries) | **GET** /me/workouts/{workoutId}/timeseries | 
[**getWorkouts**](WorkoutApi.md#getWorkouts) | **GET** /me/workouts | 
[**importTcxWorkout**](WorkoutApi.md#importTcxWorkout) | **POST** /me/workouts/import/tcx | 
[**patchWorkout**](WorkoutApi.md#patchWorkout) | **PATCH** /me/workouts/{workoutId} | 
[**putWorkoutAltitudes**](WorkoutApi.md#putWorkoutAltitudes) | **PUT** /me/workouts/{workoutId}/altitudes | This endpoint supports \&quot;Content-encoding\&quot; with \&quot;Gzip\&quot; and should be used when possible.
[**putWorkoutHeartRates**](WorkoutApi.md#putWorkoutHeartRates) | **PUT** /me/workouts/{workoutId}/heart-rates | This endpoint supports \&quot;Content-encoding\&quot; with \&quot;Gzip\&quot; and should be used when possible.
[**putWorkoutLocations**](WorkoutApi.md#putWorkoutLocations) | **PUT** /me/workouts/{workoutId}/locations | This endpoint supports \&quot;Content-encoding\&quot; with \&quot;Gzip\&quot; and should be used when possible.
[**updateWorkout**](WorkoutApi.md#updateWorkout) | **PUT** /me/workouts/{workoutId} | 
[**workoutActionEstimateHealth**](WorkoutApi.md#workoutActionEstimateHealth) | **POST** /me/workouts/{workoutId}/actions/estimate-health | 


<a name="addWorkout"></a>
# **addWorkout**
> AddWorkoutResponse addWorkout(createWorkoutInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val createWorkoutInput : CreateWorkoutInput =  // CreateWorkoutInput | 
try {
    val result : AddWorkoutResponse = apiInstance.addWorkout(createWorkoutInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#addWorkout")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#addWorkout")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createWorkoutInput** | [**CreateWorkoutInput**](CreateWorkoutInput.md)|  | [optional]

### Return type

[**AddWorkoutResponse**](AddWorkoutResponse.md)

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

<a name="deleteWorkout"></a>
# **deleteWorkout**
> deleteWorkout(workoutId)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val workoutId : kotlin.String = workoutId_example // kotlin.String | 
try {
    apiInstance.deleteWorkout(workoutId)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#deleteWorkout")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#deleteWorkout")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workoutId** | **kotlin.String**|  |

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

<a name="getWorkout"></a>
# **getWorkout**
> GetWorkoutResponse getWorkout(workoutId)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val workoutId : kotlin.String = workoutId_example // kotlin.String | 
try {
    val result : GetWorkoutResponse = apiInstance.getWorkout(workoutId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#getWorkout")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#getWorkout")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workoutId** | **kotlin.String**|  |

### Return type

[**GetWorkoutResponse**](GetWorkoutResponse.md)

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

<a name="getWorkoutHeartRates"></a>
# **getWorkoutHeartRates**
> WorkoutHeartRatesResponse getWorkoutHeartRates(workoutId, sampleRate)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val workoutId : kotlin.String = workoutId_example // kotlin.String | 
val sampleRate : kotlin.String = 3s // kotlin.String | 
try {
    val result : WorkoutHeartRatesResponse = apiInstance.getWorkoutHeartRates(workoutId, sampleRate)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#getWorkoutHeartRates")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#getWorkoutHeartRates")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workoutId** | **kotlin.String**|  |
 **sampleRate** | **kotlin.String**|  | [optional]

### Return type

[**WorkoutHeartRatesResponse**](WorkoutHeartRatesResponse.md)

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

<a name="getWorkoutTimeseries"></a>
# **getWorkoutTimeseries**
> GetWorkoutTimeseriesResponse getWorkoutTimeseries(workoutId)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val workoutId : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    val result : GetWorkoutTimeseriesResponse = apiInstance.getWorkoutTimeseries(workoutId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#getWorkoutTimeseries")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#getWorkoutTimeseries")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workoutId** | [**kotlin.String**](.md)|  |

### Return type

[**GetWorkoutTimeseriesResponse**](GetWorkoutTimeseriesResponse.md)

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

<a name="getWorkouts"></a>
# **getWorkouts**
> GetWorkoutsResponse getWorkouts(updatedAfter, trashed, perPage, page, sort)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val updatedAfter : kotlin.String = 1975-12-25T14:15:16-05:00 // kotlin.String | 
val trashed : kotlin.Boolean = true // kotlin.Boolean | Filter to retrieve trashed or not trashed workouts
val perPage : kotlin.Int = 56 // kotlin.Int | 
val page : kotlin.Int = 56 // kotlin.Int | 
val sort : kotlin.String = sort_example // kotlin.String | 
try {
    val result : GetWorkoutsResponse = apiInstance.getWorkouts(updatedAfter, trashed, perPage, page, sort)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#getWorkouts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#getWorkouts")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **updatedAfter** | **kotlin.String**|  | [optional]
 **trashed** | **kotlin.Boolean**| Filter to retrieve trashed or not trashed workouts | [optional]
 **perPage** | **kotlin.Int**|  | [optional] [default to 100]
 **page** | **kotlin.Int**|  | [optional]
 **sort** | **kotlin.String**|  | [optional] [default to started_at:desc] [enum: started_at:asc, started_at:desc, created_at:asc, created_at:desc, updated_at:asc, updated_at:desc]

### Return type

[**GetWorkoutsResponse**](GetWorkoutsResponse.md)

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

<a name="importTcxWorkout"></a>
# **importTcxWorkout**
> importTcxWorkout(file)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val file : io.ktor.client.request.forms.InputProvider = BINARY_DATA_HERE // io.ktor.client.request.forms.InputProvider | 
try {
    apiInstance.importTcxWorkout(file)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#importTcxWorkout")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#importTcxWorkout")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **file** | **io.ktor.client.request.forms.InputProvider**|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="patchWorkout"></a>
# **patchWorkout**
> UpdateWorkoutResponse patchWorkout(workoutId, patchWorkoutInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val workoutId : kotlin.String = workoutId_example // kotlin.String | 
val patchWorkoutInput : PatchWorkoutInput =  // PatchWorkoutInput | 
try {
    val result : UpdateWorkoutResponse = apiInstance.patchWorkout(workoutId, patchWorkoutInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#patchWorkout")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#patchWorkout")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workoutId** | **kotlin.String**|  |
 **patchWorkoutInput** | [**PatchWorkoutInput**](PatchWorkoutInput.md)|  | [optional]

### Return type

[**UpdateWorkoutResponse**](UpdateWorkoutResponse.md)

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

<a name="putWorkoutAltitudes"></a>
# **putWorkoutAltitudes**
> putWorkoutAltitudes(workoutId, putWorkoutAltitudesInput)

This endpoint supports \&quot;Content-encoding\&quot; with \&quot;Gzip\&quot; and should be used when possible.

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val workoutId : kotlin.String = workoutId_example // kotlin.String | 
val putWorkoutAltitudesInput : PutWorkoutAltitudesInput =  // PutWorkoutAltitudesInput | 
try {
    apiInstance.putWorkoutAltitudes(workoutId, putWorkoutAltitudesInput)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#putWorkoutAltitudes")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#putWorkoutAltitudes")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workoutId** | **kotlin.String**|  |
 **putWorkoutAltitudesInput** | [**PutWorkoutAltitudesInput**](PutWorkoutAltitudesInput.md)|  | [optional]

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

<a name="putWorkoutHeartRates"></a>
# **putWorkoutHeartRates**
> putWorkoutHeartRates(workoutId, putWorkoutHeartRatesInput)

This endpoint supports \&quot;Content-encoding\&quot; with \&quot;Gzip\&quot; and should be used when possible.

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val workoutId : kotlin.String = workoutId_example // kotlin.String | 
val putWorkoutHeartRatesInput : PutWorkoutHeartRatesInput =  // PutWorkoutHeartRatesInput | 
try {
    apiInstance.putWorkoutHeartRates(workoutId, putWorkoutHeartRatesInput)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#putWorkoutHeartRates")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#putWorkoutHeartRates")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workoutId** | **kotlin.String**|  |
 **putWorkoutHeartRatesInput** | [**PutWorkoutHeartRatesInput**](PutWorkoutHeartRatesInput.md)|  | [optional]

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

<a name="putWorkoutLocations"></a>
# **putWorkoutLocations**
> putWorkoutLocations(workoutId, putWorkoutLocationsInput)

This endpoint supports \&quot;Content-encoding\&quot; with \&quot;Gzip\&quot; and should be used when possible.

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val workoutId : kotlin.String = workoutId_example // kotlin.String | 
val putWorkoutLocationsInput : PutWorkoutLocationsInput =  // PutWorkoutLocationsInput | 
try {
    apiInstance.putWorkoutLocations(workoutId, putWorkoutLocationsInput)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#putWorkoutLocations")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#putWorkoutLocations")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workoutId** | **kotlin.String**|  |
 **putWorkoutLocationsInput** | [**PutWorkoutLocationsInput**](PutWorkoutLocationsInput.md)|  | [optional]

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

<a name="updateWorkout"></a>
# **updateWorkout**
> UpdateWorkoutResponse updateWorkout(workoutId, updateWorkoutInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val workoutId : kotlin.String = workoutId_example // kotlin.String | 
val updateWorkoutInput : UpdateWorkoutInput =  // UpdateWorkoutInput | 
try {
    val result : UpdateWorkoutResponse = apiInstance.updateWorkout(workoutId, updateWorkoutInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#updateWorkout")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#updateWorkout")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workoutId** | **kotlin.String**|  |
 **updateWorkoutInput** | [**UpdateWorkoutInput**](UpdateWorkoutInput.md)|  | [optional]

### Return type

[**UpdateWorkoutResponse**](UpdateWorkoutResponse.md)

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

<a name="workoutActionEstimateHealth"></a>
# **workoutActionEstimateHealth**
> GetWorkoutResponse workoutActionEstimateHealth(workoutId)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = WorkoutApi()
val workoutId : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    val result : GetWorkoutResponse = apiInstance.workoutActionEstimateHealth(workoutId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling WorkoutApi#workoutActionEstimateHealth")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling WorkoutApi#workoutActionEstimateHealth")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workoutId** | [**kotlin.String**](.md)|  |

### Return type

[**GetWorkoutResponse**](GetWorkoutResponse.md)

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

