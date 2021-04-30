# OrganizationApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**activateOrganizationActivationToken**](OrganizationApi.md#activateOrganizationActivationToken) | **PUT** /organizations/activation/{token} | Link the user to the Organization User identified by the token
[**createOrganization**](OrganizationApi.md#createOrganization) | **POST** /organizations | Create an organization
[**createOrganizationContest**](OrganizationApi.md#createOrganizationContest) | **POST** /organizations/{organizationId}/contests | Add a contest to the organization
[**createOrganizationGroup**](OrganizationApi.md#createOrganizationGroup) | **POST** /organizations/{organizationId}/groups | Add a group and connect it to the organization
[**createOrganizationInvite**](OrganizationApi.md#createOrganizationInvite) | **POST** /organizations/{organizationId}/invites | Invite someone to become a member of the organization
[**deleteOrganizationContest**](OrganizationApi.md#deleteOrganizationContest) | **DELETE** /organizations/{organizationId}/contests/{contestId} | Delete organization contest
[**deleteOrganizationGroup**](OrganizationApi.md#deleteOrganizationGroup) | **DELETE** /organizations/{organizationId}/groups/{groupId} | Delete group connected to the organization
[**deleteOrganizationInvite**](OrganizationApi.md#deleteOrganizationInvite) | **DELETE** /organizations/{organizationId}/invites/{inviteId} | Delete a pending invite
[**deleteOrganizationMember**](OrganizationApi.md#deleteOrganizationMember) | **DELETE** /organizations/{organizationId}/members/{memberId} | Delete an member of the organization
[**editOrganizationInvite**](OrganizationApi.md#editOrganizationInvite) | **PUT** /organizations/{organizationId}/invites/{inviteId} | 
[**editOrganizationMember**](OrganizationApi.md#editOrganizationMember) | **PUT** /organizations/{organizationId}/members/{memberId} | 
[**getInvitesForOrganization**](OrganizationApi.md#getInvitesForOrganization) | **GET** /organizations/{organizationId}/invites | Retrieve invites which is pending on acceptance from user before becoming a member
[**getOrganization**](OrganizationApi.md#getOrganization) | **GET** /organizations/{organizationUuid} | Get an organization
[**getOrganizationByActivationToken**](OrganizationApi.md#getOrganizationByActivationToken) | **GET** /organizations/activation/{token} | Get token information
[**getOrganizationContests**](OrganizationApi.md#getOrganizationContests) | **GET** /organizations/{organizationId}/contests | Get contest created by organization
[**getOrganizationGroups**](OrganizationApi.md#getOrganizationGroups) | **GET** /organizations/{organizationId}/groups | Get groups connected to the organization
[**getOrganizationMembers**](OrganizationApi.md#getOrganizationMembers) | **GET** /organizations/{organizationId}/members | Get members of an organization
[**getOrganizationWhitelistedEmailDomains**](OrganizationApi.md#getOrganizationWhitelistedEmailDomains) | **GET** /organizations/{organizationUuid}/whitelisted-email-domains | Get whitelisted email domains of an organization
[**grantPermissions**](OrganizationApi.md#grantPermissions) | **POST** /organizations/{organizationId}/grants | 
[**rejectPermissions**](OrganizationApi.md#rejectPermissions) | **DELETE** /organizations/{organizationId}/grants | 
[**sendInviteAsSms**](OrganizationApi.md#sendInviteAsSms) | **POST** /organizations/activation/{token}/invite-sms | Sends the invite corresponding to the provided invite token to the provided phone number
[**sendOrganizationInvites**](OrganizationApi.md#sendOrganizationInvites) | **POST** /organizations/{organizationId}/actions/send-invites | Batch operation to send invites for invited persons to the organization
[**updateOrganizationContest**](OrganizationApi.md#updateOrganizationContest) | **PUT** /organizations/{organizationId}/contests/{contestId} | Update contest of the organization
[**updateOrganizationGroup**](OrganizationApi.md#updateOrganizationGroup) | **PUT** /organizations/{organizationId}/groups/{groupId} | Update group connected to the organization
[**updateOrganizationGroupInvites**](OrganizationApi.md#updateOrganizationGroupInvites) | **PUT** /organizations/{organizationId}/groups/{groupId}/invites | Update members invites collection for group connected to the organization
[**updateOrganizationGroupMembers**](OrganizationApi.md#updateOrganizationGroupMembers) | **PUT** /organizations/{organizationId}/groups/{groupId}/members | Update members collection for group connected to the organization
[**updateOrganizationWhitelistedEmailDomains**](OrganizationApi.md#updateOrganizationWhitelistedEmailDomains) | **PUT** /organizations/{organizationUuid}/whitelisted-email-domains | Update whitelisted email domains of an organization
[**verifyOrganizationMember**](OrganizationApi.md#verifyOrganizationMember) | **POST** /organizations/{organizationUuid}/members/{memberId}/verify | Verify that the member is a valid member of the organization


<a name="activateOrganizationActivationToken"></a>
# **activateOrganizationActivationToken**
> activateOrganizationActivationToken(token, putActivateOrganizationActivationTokenInput)

Link the user to the Organization User identified by the token

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val token : kotlin.String = token_example // kotlin.String | 
val putActivateOrganizationActivationTokenInput : PutActivateOrganizationActivationTokenInput =  // PutActivateOrganizationActivationTokenInput | 
try {
    apiInstance.activateOrganizationActivationToken(token, putActivateOrganizationActivationTokenInput)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#activateOrganizationActivationToken")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#activateOrganizationActivationToken")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **token** | **kotlin.String**|  |
 **putActivateOrganizationActivationTokenInput** | [**PutActivateOrganizationActivationTokenInput**](PutActivateOrganizationActivationTokenInput.md)|  | [optional]

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

<a name="createOrganization"></a>
# **createOrganization**
> InlineResponse2012 createOrganization(createOrganizationInput)

Create an organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val createOrganizationInput : CreateOrganizationInput =  // CreateOrganizationInput | 
try {
    val result : InlineResponse2012 = apiInstance.createOrganization(createOrganizationInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#createOrganization")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#createOrganization")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createOrganizationInput** | [**CreateOrganizationInput**](CreateOrganizationInput.md)|  |

### Return type

[**InlineResponse2012**](InlineResponse2012.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createOrganizationContest"></a>
# **createOrganizationContest**
> CreateContestResponse createOrganizationContest(organizationId, createContestInput)

Add a contest to the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val createContestInput : CreateContestInput =  // CreateContestInput | 
try {
    val result : CreateContestResponse = apiInstance.createOrganizationContest(organizationId, createContestInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#createOrganizationContest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#createOrganizationContest")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **createContestInput** | [**CreateContestInput**](CreateContestInput.md)|  |

### Return type

[**CreateContestResponse**](CreateContestResponse.md)

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

<a name="createOrganizationGroup"></a>
# **createOrganizationGroup**
> CreateGroupResponse createOrganizationGroup(organizationId, createGroupInput)

Add a group and connect it to the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val createGroupInput : CreateGroupInput =  // CreateGroupInput | 
try {
    val result : CreateGroupResponse = apiInstance.createOrganizationGroup(organizationId, createGroupInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#createOrganizationGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#createOrganizationGroup")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **createGroupInput** | [**CreateGroupInput**](CreateGroupInput.md)|  |

### Return type

[**CreateGroupResponse**](CreateGroupResponse.md)

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

<a name="createOrganizationInvite"></a>
# **createOrganizationInvite**
> InlineResponse2013 createOrganizationInvite(organizationId, createOrganizationInviteInput)

Invite someone to become a member of the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val createOrganizationInviteInput : CreateOrganizationInviteInput =  // CreateOrganizationInviteInput | 
try {
    val result : InlineResponse2013 = apiInstance.createOrganizationInvite(organizationId, createOrganizationInviteInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#createOrganizationInvite")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#createOrganizationInvite")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **createOrganizationInviteInput** | [**CreateOrganizationInviteInput**](CreateOrganizationInviteInput.md)|  |

### Return type

[**InlineResponse2013**](InlineResponse2013.md)

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

<a name="deleteOrganizationContest"></a>
# **deleteOrganizationContest**
> deleteOrganizationContest(organizationId, contestId)

Delete organization contest

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val contestId : kotlin.Int = 56 // kotlin.Int | 
try {
    apiInstance.deleteOrganizationContest(organizationId, contestId)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#deleteOrganizationContest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#deleteOrganizationContest")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **contestId** | **kotlin.Int**|  |

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
 - **Accept**: */*

<a name="deleteOrganizationGroup"></a>
# **deleteOrganizationGroup**
> deleteOrganizationGroup(organizationId, groupId)

Delete group connected to the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val groupId : kotlin.Int = 56 // kotlin.Int | 
try {
    apiInstance.deleteOrganizationGroup(organizationId, groupId)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#deleteOrganizationGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#deleteOrganizationGroup")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **groupId** | **kotlin.Int**|  |

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
 - **Accept**: */*

<a name="deleteOrganizationInvite"></a>
# **deleteOrganizationInvite**
> deleteOrganizationInvite(organizationId, inviteId)

Delete a pending invite

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val inviteId : kotlin.Int = 56 // kotlin.Int | 
try {
    apiInstance.deleteOrganizationInvite(organizationId, inviteId)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#deleteOrganizationInvite")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#deleteOrganizationInvite")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **inviteId** | **kotlin.Int**|  |

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
 - **Accept**: */*

<a name="deleteOrganizationMember"></a>
# **deleteOrganizationMember**
> deleteOrganizationMember(organizationId, memberId)

Delete an member of the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val memberId : kotlin.Int = 56 // kotlin.Int | 
try {
    apiInstance.deleteOrganizationMember(organizationId, memberId)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#deleteOrganizationMember")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#deleteOrganizationMember")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **memberId** | **kotlin.Int**|  |

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

<a name="editOrganizationInvite"></a>
# **editOrganizationInvite**
> InlineResponse2013 editOrganizationInvite(organizationId, inviteId, editOrganizationInviteInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val inviteId : kotlin.Int = 56 // kotlin.Int | 
val editOrganizationInviteInput : EditOrganizationInviteInput =  // EditOrganizationInviteInput | 
try {
    val result : InlineResponse2013 = apiInstance.editOrganizationInvite(organizationId, inviteId, editOrganizationInviteInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#editOrganizationInvite")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#editOrganizationInvite")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **inviteId** | **kotlin.Int**|  |
 **editOrganizationInviteInput** | [**EditOrganizationInviteInput**](EditOrganizationInviteInput.md)|  |

### Return type

[**InlineResponse2013**](InlineResponse2013.md)

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

<a name="editOrganizationMember"></a>
# **editOrganizationMember**
> InlineResponse2013 editOrganizationMember(organizationId, memberId, editOrganizationMemberInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val memberId : kotlin.Int = 56 // kotlin.Int | 
val editOrganizationMemberInput : EditOrganizationMemberInput =  // EditOrganizationMemberInput | 
try {
    val result : InlineResponse2013 = apiInstance.editOrganizationMember(organizationId, memberId, editOrganizationMemberInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#editOrganizationMember")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#editOrganizationMember")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **memberId** | **kotlin.Int**|  |
 **editOrganizationMemberInput** | [**EditOrganizationMemberInput**](EditOrganizationMemberInput.md)|  |

### Return type

[**InlineResponse2013**](InlineResponse2013.md)

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

<a name="getInvitesForOrganization"></a>
# **getInvitesForOrganization**
> OrganizationInviteCollectionResponse getInvitesForOrganization(organizationId)

Retrieve invites which is pending on acceptance from user before becoming a member

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
try {
    val result : OrganizationInviteCollectionResponse = apiInstance.getInvitesForOrganization(organizationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#getInvitesForOrganization")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#getInvitesForOrganization")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |

### Return type

[**OrganizationInviteCollectionResponse**](OrganizationInviteCollectionResponse.md)

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

<a name="getOrganization"></a>
# **getOrganization**
> GetOrganizationResponse getOrganization(organizationUuid)

Get an organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    val result : GetOrganizationResponse = apiInstance.getOrganization(organizationUuid)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#getOrganization")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#getOrganization")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationUuid** | [**kotlin.String**](.md)|  |

### Return type

[**GetOrganizationResponse**](GetOrganizationResponse.md)

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

<a name="getOrganizationByActivationToken"></a>
# **getOrganizationByActivationToken**
> InlineResponse200 getOrganizationByActivationToken(token)

Get token information

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val token : kotlin.String = token_example // kotlin.String | 
try {
    val result : InlineResponse200 = apiInstance.getOrganizationByActivationToken(token)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#getOrganizationByActivationToken")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#getOrganizationByActivationToken")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **token** | **kotlin.String**|  |

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOrganizationContests"></a>
# **getOrganizationContests**
> GetOrganizationContestCollectionResponse getOrganizationContests(organizationId)

Get contest created by organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
try {
    val result : GetOrganizationContestCollectionResponse = apiInstance.getOrganizationContests(organizationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#getOrganizationContests")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#getOrganizationContests")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |

### Return type

[**GetOrganizationContestCollectionResponse**](GetOrganizationContestCollectionResponse.md)

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

<a name="getOrganizationGroups"></a>
# **getOrganizationGroups**
> GetOrganizationGroupsResponse getOrganizationGroups(organizationId)

Get groups connected to the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
try {
    val result : GetOrganizationGroupsResponse = apiInstance.getOrganizationGroups(organizationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#getOrganizationGroups")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#getOrganizationGroups")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |

### Return type

[**GetOrganizationGroupsResponse**](GetOrganizationGroupsResponse.md)

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

<a name="getOrganizationMembers"></a>
# **getOrganizationMembers**
> OrganizationMemberCollectionResponse getOrganizationMembers(organizationId)

Get members of an organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
try {
    val result : OrganizationMemberCollectionResponse = apiInstance.getOrganizationMembers(organizationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#getOrganizationMembers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#getOrganizationMembers")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |

### Return type

[**OrganizationMemberCollectionResponse**](OrganizationMemberCollectionResponse.md)

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

<a name="getOrganizationWhitelistedEmailDomains"></a>
# **getOrganizationWhitelistedEmailDomains**
> GetOrganizationWhitelistedEmailDomainsResponse getOrganizationWhitelistedEmailDomains(organizationUuid)

Get whitelisted email domains of an organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    val result : GetOrganizationWhitelistedEmailDomainsResponse = apiInstance.getOrganizationWhitelistedEmailDomains(organizationUuid)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#getOrganizationWhitelistedEmailDomains")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#getOrganizationWhitelistedEmailDomains")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationUuid** | [**kotlin.String**](.md)|  |

### Return type

[**GetOrganizationWhitelistedEmailDomainsResponse**](GetOrganizationWhitelistedEmailDomainsResponse.md)

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

<a name="grantPermissions"></a>
# **grantPermissions**
> grantPermissions(organizationId, acceptOrganizationPermissionsInput)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val acceptOrganizationPermissionsInput : AcceptOrganizationPermissionsInput =  // AcceptOrganizationPermissionsInput | 
try {
    apiInstance.grantPermissions(organizationId, acceptOrganizationPermissionsInput)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#grantPermissions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#grantPermissions")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **acceptOrganizationPermissionsInput** | [**AcceptOrganizationPermissionsInput**](AcceptOrganizationPermissionsInput.md)|  |

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

<a name="rejectPermissions"></a>
# **rejectPermissions**
> rejectPermissions(organizationId)



### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
try {
    apiInstance.rejectPermissions(organizationId)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#rejectPermissions")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#rejectPermissions")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |

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

<a name="sendInviteAsSms"></a>
# **sendInviteAsSms**
> sendInviteAsSms(token, sendInviteAsSmsInput)

Sends the invite corresponding to the provided invite token to the provided phone number

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val token : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val sendInviteAsSmsInput : SendInviteAsSmsInput =  // SendInviteAsSmsInput | 
try {
    apiInstance.sendInviteAsSms(token, sendInviteAsSmsInput)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#sendInviteAsSms")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#sendInviteAsSms")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **token** | [**kotlin.String**](.md)|  |
 **sendInviteAsSmsInput** | [**SendInviteAsSmsInput**](SendInviteAsSmsInput.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="sendOrganizationInvites"></a>
# **sendOrganizationInvites**
> sendOrganizationInvites(organizationId, sendOrganizationInvitesInput)

Batch operation to send invites for invited persons to the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val sendOrganizationInvitesInput : SendOrganizationInvitesInput =  // SendOrganizationInvitesInput | 
try {
    apiInstance.sendOrganizationInvites(organizationId, sendOrganizationInvitesInput)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#sendOrganizationInvites")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#sendOrganizationInvites")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **sendOrganizationInvitesInput** | [**SendOrganizationInvitesInput**](SendOrganizationInvitesInput.md)|  |

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
 - **Accept**: Not defined

<a name="updateOrganizationContest"></a>
# **updateOrganizationContest**
> UpdateContestResponse updateOrganizationContest(organizationId, contestId, updateContestInput)

Update contest of the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val contestId : kotlin.Int = 56 // kotlin.Int | 
val updateContestInput : UpdateContestInput =  // UpdateContestInput | 
try {
    val result : UpdateContestResponse = apiInstance.updateOrganizationContest(organizationId, contestId, updateContestInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#updateOrganizationContest")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#updateOrganizationContest")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **contestId** | **kotlin.Int**|  |
 **updateContestInput** | [**UpdateContestInput**](UpdateContestInput.md)|  |

### Return type

[**UpdateContestResponse**](UpdateContestResponse.md)

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

<a name="updateOrganizationGroup"></a>
# **updateOrganizationGroup**
> UpdateGroupResponse updateOrganizationGroup(organizationId, groupId, createGroupInput)

Update group connected to the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val groupId : kotlin.Int = 56 // kotlin.Int | 
val createGroupInput : CreateGroupInput =  // CreateGroupInput | 
try {
    val result : UpdateGroupResponse = apiInstance.updateOrganizationGroup(organizationId, groupId, createGroupInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#updateOrganizationGroup")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#updateOrganizationGroup")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **groupId** | **kotlin.Int**|  |
 **createGroupInput** | [**CreateGroupInput**](CreateGroupInput.md)|  |

### Return type

[**UpdateGroupResponse**](UpdateGroupResponse.md)

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

<a name="updateOrganizationGroupInvites"></a>
# **updateOrganizationGroupInvites**
> PutGroupInviteResponse updateOrganizationGroupInvites(organizationId, groupId, updateOrganizationGroupInviteInput)

Update members invites collection for group connected to the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val groupId : kotlin.Int = 56 // kotlin.Int | 
val updateOrganizationGroupInviteInput : UpdateOrganizationGroupInviteInput =  // UpdateOrganizationGroupInviteInput | 
try {
    val result : PutGroupInviteResponse = apiInstance.updateOrganizationGroupInvites(organizationId, groupId, updateOrganizationGroupInviteInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#updateOrganizationGroupInvites")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#updateOrganizationGroupInvites")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **groupId** | **kotlin.Int**|  |
 **updateOrganizationGroupInviteInput** | [**UpdateOrganizationGroupInviteInput**](UpdateOrganizationGroupInviteInput.md)|  |

### Return type

[**PutGroupInviteResponse**](PutGroupInviteResponse.md)

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

<a name="updateOrganizationGroupMembers"></a>
# **updateOrganizationGroupMembers**
> PutGroupMembersResponse updateOrganizationGroupMembers(organizationId, groupId, updateOrganizationGroupMembersInput)

Update members collection for group connected to the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationId : kotlin.Int = 56 // kotlin.Int | 
val groupId : kotlin.Int = 56 // kotlin.Int | 
val updateOrganizationGroupMembersInput : UpdateOrganizationGroupMembersInput =  // UpdateOrganizationGroupMembersInput | 
try {
    val result : PutGroupMembersResponse = apiInstance.updateOrganizationGroupMembers(organizationId, groupId, updateOrganizationGroupMembersInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#updateOrganizationGroupMembers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#updateOrganizationGroupMembers")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationId** | **kotlin.Int**|  |
 **groupId** | **kotlin.Int**|  |
 **updateOrganizationGroupMembersInput** | [**UpdateOrganizationGroupMembersInput**](UpdateOrganizationGroupMembersInput.md)|  |

### Return type

[**PutGroupMembersResponse**](PutGroupMembersResponse.md)

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

<a name="updateOrganizationWhitelistedEmailDomains"></a>
# **updateOrganizationWhitelistedEmailDomains**
> UpdateOrganizationWhitelistedEmailDomainsResponse updateOrganizationWhitelistedEmailDomains(organizationUuid, body)

Update whitelisted email domains of an organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val body : kotlin.collections.List<kotlin.String> =  // kotlin.collections.List<kotlin.String> | 
try {
    val result : UpdateOrganizationWhitelistedEmailDomainsResponse = apiInstance.updateOrganizationWhitelistedEmailDomains(organizationUuid, body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#updateOrganizationWhitelistedEmailDomains")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#updateOrganizationWhitelistedEmailDomains")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationUuid** | [**kotlin.String**](.md)|  |
 **body** | **kotlin.collections.List&lt;kotlin.String&gt;**|  |

### Return type

[**UpdateOrganizationWhitelistedEmailDomainsResponse**](UpdateOrganizationWhitelistedEmailDomainsResponse.md)

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

<a name="verifyOrganizationMember"></a>
# **verifyOrganizationMember**
> verifyOrganizationMember(organizationUuid, memberId)

Verify that the member is a valid member of the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = OrganizationApi()
val organizationUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val memberId : kotlin.Int = 56 // kotlin.Int | 
try {
    apiInstance.verifyOrganizationMember(organizationUuid, memberId)
} catch (e: ClientException) {
    println("4xx response calling OrganizationApi#verifyOrganizationMember")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OrganizationApi#verifyOrganizationMember")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationUuid** | [**kotlin.String**](.md)|  |
 **memberId** | **kotlin.Int**|  |

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

