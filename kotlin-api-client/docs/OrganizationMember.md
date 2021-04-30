
# OrganizationMember

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **kotlin.Int** |  |  [optional]
**userId** | **kotlin.Int** |  |  [optional]
**userUuid** | **kotlin.String** |  |  [optional]
**name** | **kotlin.String** | Name of the invited user |  [optional]
**email** | **kotlin.String** | E-mail the user was invited to organization with |  [optional]
**userEmail** | **kotlin.String** | Masked e-mail the user is using to login with |  [optional]
**phone** | **kotlin.String** | Phone number the user was invited with |  [optional]
**isAdmin** | **kotlin.Boolean** | Flag to determine if the user can administrate the organization |  [optional]
**permissionsStatus** | [**inline**](#PermissionsStatusEnum) |  |  [optional]
**verified** | **kotlin.Boolean** | True if it is verified that the user belongs to the organization. Verification is either done manually by an administrator or by joining the organization with a personal invite. |  [optional]
**createdAt** | **kotlin.String** | ISO8601 time of when the member was invited/created |  [optional]
**department** | [**OrganizationMemberDepartment**](OrganizationMemberDepartment.md) |  |  [optional]


<a name="PermissionsStatusEnum"></a>
## Enum: permissions_status
Name | Value
---- | -----
permissionsStatus | accepted, rejected, pending



