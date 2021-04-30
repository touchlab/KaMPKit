
# UserSubscription

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **kotlin.Int** |  |  [optional]
**uuid** | **kotlin.String** |  |  [optional]
**userId** | **kotlin.Int** |  |  [optional]
**userUuid** | **kotlin.String** |  |  [optional]
**platform** | [**inline**](#PlatformEnum) |  |  [optional]
**type** | [**inline**](#TypeEnum) |  |  [optional]
**state** | [**inline**](#StateEnum) | The current state of the subscription. |  [optional]
**identifier** | **kotlin.String** | product key |  [optional]
**expireAt** | **kotlin.String** | iso8601 |  [optional]


<a name="PlatformEnum"></a>
## Enum: platform
Name | Value
---- | -----
platform | ios, android


<a name="TypeEnum"></a>
## Enum: type
Name | Value
---- | -----
type | trial, periodical


<a name="StateEnum"></a>
## Enum: state
Name | Value
---- | -----
state | active, on_hold, canceled, expired



