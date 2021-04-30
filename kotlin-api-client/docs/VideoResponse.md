
# VideoResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **kotlin.Int** |  |  [optional]
**type** | [**inline**](#TypeEnum) |  |  [optional]
**title** | **kotlin.String** |  |  [optional]
**createdAt** | **kotlin.String** | ISO8601 |  [optional]
**updatedAt** | **kotlin.String** | ISO8601 |  [optional]
**description** | **kotlin.String** | Description of the workout in the workouts language |  [optional]
**lang** | **kotlin.String** | Language the video content/info is available in |  [optional]
**vimeoCode** | **kotlin.String** |  |  [optional]
**duration** | **kotlin.Double** | Duration in What format? |  [optional]
**thumbnail** | **kotlin.String** | What is this? URL? Should probably not be in DB but handled differently |  [optional]
**categories** | [**kotlin.collections.List&lt;VideoCategoryResponse&gt;**](VideoCategoryResponse.md) |  |  [optional]
**trainers** | [**kotlin.collections.List&lt;TrainerResponse&gt;**](TrainerResponse.md) |  |  [optional]


<a name="TypeEnum"></a>
## Enum: type
Name | Value
---- | -----
type | education, workout



