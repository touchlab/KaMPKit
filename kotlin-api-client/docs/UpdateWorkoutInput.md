
# UpdateWorkoutInput

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**inline**](#TypeEnum) |  | 
**startedAt** | **kotlin.String** |  | 
**duration** | **kotlin.Int** | seconds | 
**activityType** | [**inline**](#ActivityTypeEnum) |  |  [optional]
**displayHealthEvaluation** | **kotlin.Boolean** | Does the user want to display health evaluation for this workout |  [optional]
**title** | **kotlin.String** |  |  [optional]
**bodyWeight** | **kotlin.Double** | Weight in kg, precision 5.2 |  [optional]
**stages** | [**kotlin.collections.List&lt;WorkoutStageInput&gt;**](WorkoutStageInput.md) | Replace |  [optional]
**exercises** | [**kotlin.collections.List&lt;WorkoutExerciseInput&gt;**](WorkoutExerciseInput.md) |  |  [optional]


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



