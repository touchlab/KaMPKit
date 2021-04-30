
# CreateWorkoutInput

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | [**inline**](#TypeEnum) |  | 
**source** | [**inline**](#SourceEnum) | Identifies how the workout has been registered. Manual is based purely on user input, while recorded means it has been captured by the client. | 
**startedAt** | **kotlin.String** |  | 
**duration** | **kotlin.Int** | seconds | 
**id** | **kotlin.String** | UUID for exercise, API will automatically generate one if empty. |  [optional]
**activityType** | [**inline**](#ActivityTypeEnum) |  |  [optional]
**recordingDevice** | **kotlin.String** | An identifier to be able to identify which device/model was used to record a given workout. |  [optional]
**transmission** | [**inline**](#TransmissionEnum) |  |  [optional]
**displayHealthEvaluation** | **kotlin.Boolean** | Does the user want to display health evaluation for this workout |  [optional]
**title** | **kotlin.String** |  |  [optional]
**bodyWeight** | **kotlin.Double** | Weight in kg, precision 5.2 |  [optional]
**stages** | [**kotlin.collections.List&lt;WorkoutStageInput&gt;**](WorkoutStageInput.md) |  |  [optional]
**exercises** | [**kotlin.collections.List&lt;WorkoutExerciseInput&gt;**](WorkoutExerciseInput.md) |  |  [optional]


<a name="TypeEnum"></a>
## Enum: type
Name | Value
---- | -----
type | outdoor, treadmill, watt_machine, generic, strength


<a name="SourceEnum"></a>
## Enum: source
Name | Value
---- | -----
source | recorded, manual


<a name="ActivityTypeEnum"></a>
## Enum: activity_type
Name | Value
---- | -----
activityType | walking, running, cycling, cross_country_skiing, swimming, rowing, other


<a name="TransmissionEnum"></a>
## Enum: transmission
Name | Value
---- | -----
transmission | in_progress, done



