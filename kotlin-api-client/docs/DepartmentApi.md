# DepartmentApi

All URIs are relative to *https://api.myworkout.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createOrganizationDepartment**](DepartmentApi.md#createOrganizationDepartment) | **POST** /organizations/{organizationUuid}/departments | Add a department to the organization
[**createOrganizationDepartmentMember**](DepartmentApi.md#createOrganizationDepartmentMember) | **POST** /organizations/{organizationUuid}/departments/{departmentUuid}/members | Assigns a department to the member of the organization
[**getOrganizationDepartmentMembers**](DepartmentApi.md#getOrganizationDepartmentMembers) | **GET** /organizations/{organizationUuid}/departments/{departmentUuid}/members | Get members of the department of the organization
[**getOrganizationDepartments**](DepartmentApi.md#getOrganizationDepartments) | **GET** /organizations/{organizationUuid}/departments | Get departments of the organization
[**updateOrganizationDepartment**](DepartmentApi.md#updateOrganizationDepartment) | **PATCH** /organizations/{organizationUuid}/departments/{departmentUuid} | Update department of the organization
[**updateOrganizationDepartmentMember**](DepartmentApi.md#updateOrganizationDepartmentMember) | **PATCH** /organizations/{organizationUuid}/departments/{departmentUuid}/members/{memberId} | Update the department member of the organization


<a name="createOrganizationDepartment"></a>
# **createOrganizationDepartment**
> CreateOrganizationDepartmentResponse createOrganizationDepartment(organizationUuid, createOrganizationDepartmentInput)

Add a department to the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = DepartmentApi()
val organizationUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val createOrganizationDepartmentInput : CreateOrganizationDepartmentInput =  // CreateOrganizationDepartmentInput | 
try {
    val result : CreateOrganizationDepartmentResponse = apiInstance.createOrganizationDepartment(organizationUuid, createOrganizationDepartmentInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DepartmentApi#createOrganizationDepartment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DepartmentApi#createOrganizationDepartment")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationUuid** | [**kotlin.String**](.md)|  |
 **createOrganizationDepartmentInput** | [**CreateOrganizationDepartmentInput**](CreateOrganizationDepartmentInput.md)|  |

### Return type

[**CreateOrganizationDepartmentResponse**](CreateOrganizationDepartmentResponse.md)

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

<a name="createOrganizationDepartmentMember"></a>
# **createOrganizationDepartmentMember**
> CreateOrganizationDepartmentMemberResponse createOrganizationDepartmentMember(organizationUuid, departmentUuid, createOrganizationDepartmentMemberInput)

Assigns a department to the member of the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = DepartmentApi()
val organizationUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val departmentUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val createOrganizationDepartmentMemberInput : CreateOrganizationDepartmentMemberInput =  // CreateOrganizationDepartmentMemberInput | 
try {
    val result : CreateOrganizationDepartmentMemberResponse = apiInstance.createOrganizationDepartmentMember(organizationUuid, departmentUuid, createOrganizationDepartmentMemberInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DepartmentApi#createOrganizationDepartmentMember")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DepartmentApi#createOrganizationDepartmentMember")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationUuid** | [**kotlin.String**](.md)|  |
 **departmentUuid** | [**kotlin.String**](.md)|  |
 **createOrganizationDepartmentMemberInput** | [**CreateOrganizationDepartmentMemberInput**](CreateOrganizationDepartmentMemberInput.md)|  |

### Return type

[**CreateOrganizationDepartmentMemberResponse**](CreateOrganizationDepartmentMemberResponse.md)

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

<a name="getOrganizationDepartmentMembers"></a>
# **getOrganizationDepartmentMembers**
> GetOrganizationDepartmentMembersResponse getOrganizationDepartmentMembers(organizationUuid, departmentUuid)

Get members of the department of the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = DepartmentApi()
val organizationUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val departmentUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    val result : GetOrganizationDepartmentMembersResponse = apiInstance.getOrganizationDepartmentMembers(organizationUuid, departmentUuid)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DepartmentApi#getOrganizationDepartmentMembers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DepartmentApi#getOrganizationDepartmentMembers")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationUuid** | [**kotlin.String**](.md)|  |
 **departmentUuid** | [**kotlin.String**](.md)|  |

### Return type

[**GetOrganizationDepartmentMembersResponse**](GetOrganizationDepartmentMembersResponse.md)

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

<a name="getOrganizationDepartments"></a>
# **getOrganizationDepartments**
> GetOrganizationDepartmentsResponse getOrganizationDepartments(organizationUuid)

Get departments of the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = DepartmentApi()
val organizationUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
try {
    val result : GetOrganizationDepartmentsResponse = apiInstance.getOrganizationDepartments(organizationUuid)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DepartmentApi#getOrganizationDepartments")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DepartmentApi#getOrganizationDepartments")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationUuid** | [**kotlin.String**](.md)|  |

### Return type

[**GetOrganizationDepartmentsResponse**](GetOrganizationDepartmentsResponse.md)

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

<a name="updateOrganizationDepartment"></a>
# **updateOrganizationDepartment**
> UpdateOrganizationDepartmentResponse updateOrganizationDepartment(organizationUuid, departmentUuid, updateOrganizationDepartmentInput)

Update department of the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = DepartmentApi()
val organizationUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val departmentUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val updateOrganizationDepartmentInput : UpdateOrganizationDepartmentInput =  // UpdateOrganizationDepartmentInput | 
try {
    val result : UpdateOrganizationDepartmentResponse = apiInstance.updateOrganizationDepartment(organizationUuid, departmentUuid, updateOrganizationDepartmentInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DepartmentApi#updateOrganizationDepartment")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DepartmentApi#updateOrganizationDepartment")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationUuid** | [**kotlin.String**](.md)|  |
 **departmentUuid** | [**kotlin.String**](.md)|  |
 **updateOrganizationDepartmentInput** | [**UpdateOrganizationDepartmentInput**](UpdateOrganizationDepartmentInput.md)|  |

### Return type

[**UpdateOrganizationDepartmentResponse**](UpdateOrganizationDepartmentResponse.md)

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

<a name="updateOrganizationDepartmentMember"></a>
# **updateOrganizationDepartmentMember**
> UpdateOrganizationDepartmentMemberResponse updateOrganizationDepartmentMember(organizationUuid, departmentUuid, memberId, updateOrganizationDepartmentMemberInput)

Update the department member of the organization

### Example
```kotlin
// Import classes:
//import com.myworkout.kotlin.api.infrastructure.*
//import com.myworkout.kotlin.api.models.*

val apiInstance = DepartmentApi()
val organizationUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val departmentUuid : kotlin.String = 38400000-8cf0-11bd-b23e-10b96e4ef00d // kotlin.String | 
val memberId : kotlin.Int = 56 // kotlin.Int | 
val updateOrganizationDepartmentMemberInput : UpdateOrganizationDepartmentMemberInput =  // UpdateOrganizationDepartmentMemberInput | 
try {
    val result : UpdateOrganizationDepartmentMemberResponse = apiInstance.updateOrganizationDepartmentMember(organizationUuid, departmentUuid, memberId, updateOrganizationDepartmentMemberInput)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DepartmentApi#updateOrganizationDepartmentMember")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DepartmentApi#updateOrganizationDepartmentMember")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizationUuid** | [**kotlin.String**](.md)|  |
 **departmentUuid** | [**kotlin.String**](.md)|  |
 **memberId** | **kotlin.Int**|  |
 **updateOrganizationDepartmentMemberInput** | [**UpdateOrganizationDepartmentMemberInput**](UpdateOrganizationDepartmentMemberInput.md)|  |

### Return type

[**UpdateOrganizationDepartmentMemberResponse**](UpdateOrganizationDepartmentMemberResponse.md)

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

