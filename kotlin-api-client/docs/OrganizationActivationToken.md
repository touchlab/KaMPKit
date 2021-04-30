
# OrganizationActivationToken

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **kotlin.Int** |  |  [optional]
**confirmLink** | **kotlin.String** | Deprecated, use activation_url in stead |  [optional]
**activationUrl** | **kotlin.String** | Link to accept the invitation |  [optional]
**uuid** | **kotlin.String** |  |  [optional]
**name** | **kotlin.String** |  |  [optional]
**media** | [**OrganizationActivationTokenMedia**](OrganizationActivationTokenMedia.md) |  |  [optional]
**token** | **kotlin.String** |  |  [optional]
**invitationName** | **kotlin.String** |  |  [optional]
**invitationEmail** | **kotlin.String** |  |  [optional]
**isPremium** | **kotlin.Boolean** |  |  [optional]
**isUsed** | **kotlin.Boolean** |  |  [optional]
**type** | [**inline**](#TypeEnum) |  |  [optional]
**requiredPermissions** | [**kotlin.collections.List&lt;DataPermission&gt;**](DataPermission.md) |  |  [optional]
**onboardingText** | **kotlin.String** |  |  [optional]
**role** | [**OrganizationMemberRole**](OrganizationMemberRole.md) |  |  [optional]
**department** | [**OrganizationActivationTokenDepartment**](OrganizationActivationTokenDepartment.md) |  |  [optional]
**organization** | [**OrganizationActivationTokenOrganization**](OrganizationActivationTokenOrganization.md) |  |  [optional]


<a name="TypeEnum"></a>
## Enum: type
Name | Value
---- | -----
type | company, insurance, sports_club, gym, clinic



