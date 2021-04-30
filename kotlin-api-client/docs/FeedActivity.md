
# FeedActivity

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**uuid** | **kotlin.String** |  |  [optional]
**activityData** | [**FeedActivityActivityData**](FeedActivityActivityData.md) |  |  [optional]
**type** | [**inline**](#TypeEnum) |  |  [optional]
**createdAt** | **kotlin.String** | iso8601 formatted time of when the notification was created |  [optional]
**updatedAt** | **kotlin.String** | iso8601 formatted time of when the notification was updated |  [optional]
**user** | [**FeedActivityUser**](FeedActivityUser.md) |  |  [optional]


<a name="TypeEnum"></a>
## Enum: type
Name | Value
---- | -----
type | message, event_workout_points, event_gained_handicap_points, event_contest_has_started, event_contest_has_ended, event_contest_weekly_bout_ended, event_team_member_added, event_team_member_removed



