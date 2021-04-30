
# Organization

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **kotlin.Int** |  |  [optional]
**uuid** | **kotlin.String** |  |  [optional]
**name** | **kotlin.String** | Name of organization |  [optional]
**isPremium** | **kotlin.Boolean** |  |  [optional]
**language** | **kotlin.String** |  |  [optional]
**inviteMethods** | [**inline**](#kotlin.collections.List&lt;InviteMethodsEnum&gt;) | Types of invitation methods used by organization |  [optional]
**type** | **kotlin.String** |  |  [optional]
**requiredPermissions** | [**kotlin.collections.List&lt;DataPermission&gt;**](DataPermission.md) |  |  [optional]


<a name="kotlin.collections.List<InviteMethodsEnum>"></a>
## Enum: invite_methods
Name | Value
---- | -----
inviteMethods | email, sms



