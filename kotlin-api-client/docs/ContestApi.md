# ContestApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getContestPublicLeaderboard**](ContestApi.md#getContestPublicLeaderboard) | **GET** /contests/{contestId}/public-leaderboard | 
[**getIndividualLeaderboard**](ContestApi.md#getIndividualLeaderboard) | **GET** /me/contests/{contest}/rankings/individual-leaderboard | 
[**getUserContest**](ContestApi.md#getUserContest) | **GET** /me/contests/{contestUuid} | 
[**getUserContestLeaderboardRanking**](ContestApi.md#getUserContestLeaderboardRanking) | **GET** /me/contests/{contestId}/rankings/leaderboard | 


<a name="getContestPublicLeaderboard"></a>
# **getContestPublicLeaderboard**
> GetContestPublicLeaderboardResponse getContestPublicLeaderboard(contestId)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = ContestApi()
val contestId : kotlin.String = e3bfbb26-1936-49eb-b84a-5c029f00eb9f // kotlin.String | The public ID of the contest
try {
    val result : GetContestPublicLeaderboardResponse = apiInstance.getContestPublicLeaderboard(contestId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ContestApi#getContestPublicLeaderboard")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ContestApi#getContestPublicLeaderboard")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contestId** | [**kotlin.String**](.md)| The public ID of the contest |

### Return type

[**GetContestPublicLeaderboardResponse**](GetContestPublicLeaderboardResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getIndividualLeaderboard"></a>
# **getIndividualLeaderboard**
> GetIndividualLeaderboardResponse getIndividualLeaderboard(contest)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = ContestApi()
val contest : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    val result : GetIndividualLeaderboardResponse = apiInstance.getIndividualLeaderboard(contest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ContestApi#getIndividualLeaderboard")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ContestApi#getIndividualLeaderboard")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contest** | [**kotlin.String**](.md)|  |

### Return type

[**GetIndividualLeaderboardResponse**](GetIndividualLeaderboardResponse.md)

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

<a name="getUserContest"></a>
# **getUserContest**
> GetUserContestResponse getUserContest(contestUuid)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = ContestApi()
val contestUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    val result : GetUserContestResponse = apiInstance.getUserContest(contestUuid)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ContestApi#getUserContest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ContestApi#getUserContest")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contestUuid** | [**kotlin.String**](.md)|  |

### Return type

[**GetUserContestResponse**](GetUserContestResponse.md)

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

<a name="getUserContestLeaderboardRanking"></a>
# **getUserContestLeaderboardRanking**
> GetUserContestsLeaderboardRankingResponse getUserContestLeaderboardRanking(contestId)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = ContestApi()
val contestId : kotlin.String = contestId_example // kotlin.String | 
try {
    val result : GetUserContestsLeaderboardRankingResponse = apiInstance.getUserContestLeaderboardRanking(contestId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ContestApi#getUserContestLeaderboardRanking")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ContestApi#getUserContestLeaderboardRanking")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contestId** | **kotlin.String**|  |

### Return type

[**GetUserContestsLeaderboardRankingResponse**](GetUserContestsLeaderboardRankingResponse.md)

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

