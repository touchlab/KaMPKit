
# ContestResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **kotlin.Int** |  |  [optional]
**publicId** | **kotlin.String** |  |  [optional]
**name** | **kotlin.String** | name of the contest |  [optional]
**startAt** | **kotlin.String** | ISO8601 time of when the contest is starting |  [optional]
**finishAt** | **kotlin.String** | ISO8601 time of when the contest is finishing |  [optional]
**dailyScoreLimit** | **kotlin.Int** | Score limit per user per day. If not set, no score limit is in effect. |  [optional]
**rules** | **kotlin.collections.List&lt;kotlin.String&gt;** | Localized list of rules that is applicable to the contest that the user needs to be aware of. |  [optional]
**visibility** | **kotlin.String** |  |  [optional]
**teams** | [**kotlin.collections.List&lt;ContestResponseTeams&gt;**](ContestResponseTeams.md) |  |  [optional]
**links** | [**ContestResponseLinks**](ContestResponseLinks.md) |  |  [optional]



