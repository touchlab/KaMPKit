
# ClientWorkout

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **kotlin.String** |  |  [optional]
**uuid** | **kotlin.String** |  |  [optional]
**type** | [**inline**](#TypeEnum) |  |  [optional]
**activityType** | [**inline**](#ActivityTypeEnum) |  |  [optional]
**completedAt** | **kotlin.String** | iso8601 formatted time of when the exercise was completed |  [optional]
**title** | **kotlin.String** |  |  [optional]
**duration** | **kotlin.Int** | seconds |  [optional]
**calculatedEstimates** | [**ClientWorkoutCalculatedEstimates**](ClientWorkoutCalculatedEstimates.md) |  |  [optional]


<a name="TypeEnum"></a>
## Enum: type
Name | Value
---- | -----
type | outdoor, treadmill, watt_machine, generic, strength


<a name="ActivityTypeEnum"></a>
## Enum: activity_type
Name | Value
---- | -----
activityType | walking, running, cycling, cross_country_skiing, swimming, rowing, other



