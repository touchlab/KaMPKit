
# User

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **kotlin.Int** |  |  [optional]
**uuid** | **kotlin.String** |  |  [optional]
**businessClient** | [**UserBusinessClient**](UserBusinessClient.md) |  |  [optional]
**managedOrganizations** | [**kotlin.collections.List&lt;UserManagedOrganizations&gt;**](UserManagedOrganizations.md) | list of organizations the user can manage |  [optional]
**organizations** | [**kotlin.collections.List&lt;Organization&gt;**](Organization.md) |  |  [optional]
**isPremium** | **kotlin.Boolean** |  |  [optional] [readonly]
**displayName** | **kotlin.String** |  |  [optional]
**userLogin** | **kotlin.String** |  |  [optional]
**userEmail** | **kotlin.String** |  |  [optional]
**firstName** | **kotlin.String** |  |  [optional]
**lastName** | **kotlin.String** |  |  [optional]
**sex** | [**inline**](#SexEnum) |  |  [optional]
**birthDate** | **kotlin.String** |  |  [optional]
**grants** | [**inline**](#kotlin.collections.List&lt;GrantsEnum&gt;) |  |  [optional]
**language** | **kotlin.String** |  |  [optional]
**maxHeartrate** | **kotlin.Int** | Max heart rate (BPM) |  [optional]
**hadPremiumTrial** | **kotlin.Boolean** | indicate that user has used trial in the past |  [optional]
**latestSubscription** | [**UserSubscription**](UserSubscription.md) |  |  [optional]
**organizationsPendingGrants** | **kotlin.collections.List&lt;kotlin.Int&gt;** | List of organizations for which the user has not accepted or rejected the required permissions |  [optional]


<a name="SexEnum"></a>
## Enum: sex
Name | Value
---- | -----
sex | male, female


<a name="kotlin.collections.List<GrantsEnum>"></a>
## Enum: grants
Name | Value
---- | -----
grants | health_evaluation, share_workouts_in_team_feed



