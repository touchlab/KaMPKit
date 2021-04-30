
# WorkoutStageInput

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**stage** | [**inline**](#StageEnum) |  | 
**startedAt** | **kotlin.String** | iso8601 | 
**duration** | **kotlin.Int** | seconds | 
**distance** | **kotlin.Double** | meters |  [optional]
**avgSpeed** | **kotlin.Double** | km/h |  [optional]
**avgPower** | **kotlin.Int** | Power in Watt |  [optional]
**incline** | **kotlin.Double** | Incline in % |  [optional]


<a name="StageEnum"></a>
## Enum: stage
Name | Value
---- | -----
stage | warm_up, intensive, active_break, cool_down, generic



