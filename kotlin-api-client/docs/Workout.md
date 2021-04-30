
# Workout

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **kotlin.String** |  |  [optional]
**trashed** | **kotlin.Boolean** | Is the workout trashed (deleted) |  [optional]
**displayHealthEvaluation** | **kotlin.Boolean** | Does the user want to display health evaluation for this workout |  [optional]
**healthEvaluationAvailability** | [**inline**](#HealthEvaluationAvailabilityEnum) | Can a health estimate be calculated for the workout. |  [optional]
**type** | [**inline**](#TypeEnum) |  |  [optional]
**activityType** | [**inline**](#ActivityTypeEnum) |  |  [optional]
**source** | [**inline**](#SourceEnum) | Identifies how the workout has been registered. Manual is based purely on user input, while recorded means it has been captured by the client. |  [optional]
**transmission** | [**inline**](#TransmissionEnum) |  |  [optional]
**startedAt** | **kotlin.String** | iso8601 formatted time of when the exercise was started |  [optional]
**completedAt** | **kotlin.String** | iso8601 formatted time of when the exercise was completed |  [optional]
**createdAt** | **kotlin.String** | iso8601 formatted time of when the exercise was stored |  [optional]
**updatedAt** | **kotlin.String** | iso8601 time of when the exercise or related information was updated |  [optional]
**overlappingId** | **kotlin.Int** | if any workouts share same start and completed dates, then they are grouped with same overlapping id |  [optional] [readonly]
**title** | **kotlin.String** |  |  [optional]
**duration** | **kotlin.Int** | seconds |  [optional]
**bodyWeight** | **kotlin.Double** | Weight in kg, precision 5.2 |  [optional]
**maxHeartRate** | **kotlin.Int** | The Max heart rate the user had registered at the time of the exercise |  [optional]
**heartRatePeak** | **kotlin.Int** | highest recorded heart rate |  [optional]
**heartRateAvg** | **kotlin.Int** | average heart rate among recorded values |  [optional]
**heartRateMin** | **kotlin.Int** | lowest recorded heart rate |  [optional]
**points** | **kotlin.Double** |  |  [optional]
**pointsWeekGoal** | **kotlin.Double** | The user&#39;s goal for the week the exercise was performed in. |  [optional]
**stars** | **kotlin.collections.List&lt;kotlin.String&gt;** |  |  [optional]
**stages** | [**kotlin.collections.List&lt;WorkoutStage&gt;**](WorkoutStage.md) |  |  [optional]
**exercises** | [**kotlin.collections.List&lt;WorkoutExercise&gt;**](WorkoutExercise.md) |  |  [optional]
**calculatedEstimates** | [**WorkoutCalculatedEstimates**](WorkoutCalculatedEstimates.md) |  |  [optional]


<a name="HealthEvaluationAvailabilityEnum"></a>
## Enum: health_evaluation_availability
Name | Value
---- | -----
healthEvaluationAvailability | available, insufficient_data, not_applicable


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


<a name="SourceEnum"></a>
## Enum: source
Name | Value
---- | -----
source | recorded, manual, imported


<a name="TransmissionEnum"></a>
## Enum: transmission
Name | Value
---- | -----
transmission | in_progress, done, unknown



