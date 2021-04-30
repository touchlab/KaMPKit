
# GetOrganizationResponseData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**uuid** | **kotlin.String** |  |  [optional]
**name** | **kotlin.String** | Name of organization |  [optional]
**brandName** | **kotlin.String** |  |  [optional]
**isPremium** | **kotlin.Boolean** |  |  [optional]
**language** | **kotlin.String** |  |  [optional]
**inviteMethods** | [**inline**](#kotlin.collections.List&lt;InviteMethodsEnum&gt;) | Types of invitation methods used by organization |  [optional]
**type** | [**inline**](#TypeEnum) |  |  [optional]
**publicInviteUrl** | **kotlin.String** | Link to the public invite to join the organization |  [optional]


<a name="kotlin.collections.List<InviteMethodsEnum>"></a>
## Enum: invite_methods
Name | Value
---- | -----
inviteMethods | email, sms


<a name="TypeEnum"></a>
## Enum: type
Name | Value
---- | -----
type | company, insurance, sports_club, gym, clinic



