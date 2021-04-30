
# WorkoutStage

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**stage** | [**inline**](#StageEnum) |  | 
**startedAt** | **kotlin.String** | iso8601 | 
**duration** | **kotlin.Double** | seconds | 
**gpsPath** | **kotlin.String** | Google Encoded Polyline format (google.maps.geometry.encoding.decodePath()) |  [optional]
**distance** | **kotlin.Double** | meters |  [optional]
**avgSpeed** | **kotlin.Double** | km/h |  [optional]
**avgPower** | **kotlin.Int** | Power in the unit of Watt |  [optional]
**incline** | **kotlin.Double** | Incline in % during this stage |  [optional]
**ascent** | **kotlin.Double** | Ascent in meters during this stage |  [optional]
**heartRatePeak** | **kotlin.Int** | highest recorded heart rate |  [optional]
**heartRateAvg** | **kotlin.Int** | average heart rate among recorded values |  [optional]
**heartRateMin** | **kotlin.Int** | lowest recorded heart rate |  [optional]


<a name="StageEnum"></a>
## Enum: stage
Name | Value
---- | -----
stage | warm_up, intensive, active_break, cool_down, generic



