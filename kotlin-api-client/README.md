# com.myworkout.kotlin.api - Kotlin client library for Myworkout API

## Requires

* Kotlin 1.4.32

## Build

```
./gradlew check assemble
```

This runs all tests and packages the library.

## Features/Implementation Notes

* Supports JSON inputs/outputs, File inputs, and Form inputs.
* Supports collection formats for query parameters: csv, tsv, ssv, pipes.
* Some Kotlin and Java types are fully qualified to avoid conflicts with types defined in OpenAPI definitions.


<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *https://api.myworkout.com*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AuthApi* | [**createSsoToken**](docs/AuthApi.md#createssotoken) | **POST** /sso-tokens | 
*AuthApi* | [**login**](docs/AuthApi.md#login) | **POST** /bio-age-app/login | Retiving JWT token for the user
*AuthApi* | [**resetPassword**](docs/AuthApi.md#resetpassword) | **POST** /bio-age-app/reset-pass | Reset user's password
*AuthApi* | [**revokeOAuthTokens**](docs/AuthApi.md#revokeoauthtokens) | **POST** /oauth/x-revoke | Revoke access/refresh tokens.
*ContestApi* | [**getContestPublicLeaderboard**](docs/ContestApi.md#getcontestpublicleaderboard) | **GET** /contests/{contestId}/public-leaderboard | 
*ContestApi* | [**getIndividualLeaderboard**](docs/ContestApi.md#getindividualleaderboard) | **GET** /me/contests/{contest}/rankings/individual-leaderboard | 
*ContestApi* | [**getUserContest**](docs/ContestApi.md#getusercontest) | **GET** /me/contests/{contestUuid} | 
*ContestApi* | [**getUserContestLeaderboardRanking**](docs/ContestApi.md#getusercontestleaderboardranking) | **GET** /me/contests/{contestId}/rankings/leaderboard | 
*DepartmentApi* | [**createOrganizationDepartment**](docs/DepartmentApi.md#createorganizationdepartment) | **POST** /organizations/{organizationUuid}/departments | Add a department to the organization
*DepartmentApi* | [**createOrganizationDepartmentMember**](docs/DepartmentApi.md#createorganizationdepartmentmember) | **POST** /organizations/{organizationUuid}/departments/{departmentUuid}/members | Assigns a department to the member of the organization
*DepartmentApi* | [**getOrganizationDepartmentMembers**](docs/DepartmentApi.md#getorganizationdepartmentmembers) | **GET** /organizations/{organizationUuid}/departments/{departmentUuid}/members | Get members of the department of the organization
*DepartmentApi* | [**getOrganizationDepartments**](docs/DepartmentApi.md#getorganizationdepartments) | **GET** /organizations/{organizationUuid}/departments | Get departments of the organization
*DepartmentApi* | [**updateOrganizationDepartment**](docs/DepartmentApi.md#updateorganizationdepartment) | **PATCH** /organizations/{organizationUuid}/departments/{departmentUuid} | Update department of the organization
*DepartmentApi* | [**updateOrganizationDepartmentMember**](docs/DepartmentApi.md#updateorganizationdepartmentmember) | **PATCH** /organizations/{organizationUuid}/departments/{departmentUuid}/members/{memberId} | Update the department member of the organization
*FeedApi* | [**createFeedActivity**](docs/FeedApi.md#createfeedactivity) | **POST** /feeds/{feedId}/activities | 
*FeedApi* | [**getFeedActivities**](docs/FeedApi.md#getfeedactivities) | **GET** /feeds/{feedId}/activities | 
*FeedbackApi* | [**addFeedback**](docs/FeedbackApi.md#addfeedback) | **PUT** /me/feedbacks/{uuid} | 
*IdentityProviderApi* | [**connectUsingIdentityProvider**](docs/IdentityProviderApi.md#connectusingidentityprovider) | **POST** /idp/{identityProvider}/connect | Connect a user with an identity provider using a token from the IdP
*IdentityProviderApi* | [**loginUsingIdentityProvider**](docs/IdentityProviderApi.md#loginusingidentityprovider) | **POST** /idp/{identityProvider}/login | Exchange a request token from a IdP to a OAuth 2.0 access tokens
*InstructorsApi* | [**createClientGroup**](docs/InstructorsApi.md#createclientgroup) | **POST** /instructors/{clinic}/client-groups | 
*InstructorsApi* | [**createSharedEvaluation**](docs/InstructorsApi.md#createsharedevaluation) | **POST** /instructors/{clinic}/clients/{client}/shared-evaluations | 
*InstructorsApi* | [**deleteClientGroup**](docs/InstructorsApi.md#deleteclientgroup) | **DELETE** /instructors/{clinic}/client-groups/{clientGroup} | 
*InstructorsApi* | [**getClient**](docs/InstructorsApi.md#getclient) | **GET** /instructors/{clinic}/clients/{client} | Return the client with the provided id
*InstructorsApi* | [**getClientGroups**](docs/InstructorsApi.md#getclientgroups) | **GET** /instructors/{clinic}/client-groups | 
*InstructorsApi* | [**getClientWorkouts**](docs/InstructorsApi.md#getclientworkouts) | **GET** /instructors/{clinic}/clients/{client}/workouts | Paginated list of workouts for the provided user (100 per page)
*InstructorsApi* | [**updateClientGroup**](docs/InstructorsApi.md#updateclientgroup) | **PATCH** /instructors/{clinic}/client-groups/{clientGroup} | 
*InstructorsApi* | [**updateClientGroupMembers**](docs/InstructorsApi.md#updateclientgroupmembers) | **PATCH** /instructors/{clinic}/client-groups/{clientGroup}/members | 
*NotificationApi* | [**getNotifications**](docs/NotificationApi.md#getnotifications) | **GET** /me/notifications | 
*NotificationApi* | [**updateNotification**](docs/NotificationApi.md#updatenotification) | **PATCH** /me/notifications/{notificationId} | 
*NotificationApi* | [**updateNotificationAllRead**](docs/NotificationApi.md#updatenotificationallread) | **PATCH** /me/notifications/actions/mark-all-as-read | 
*NotificationApi* | [**updateNotifications**](docs/NotificationApi.md#updatenotifications) | **PATCH** /me/notifications | 
*OrganizationApi* | [**activateOrganizationActivationToken**](docs/OrganizationApi.md#activateorganizationactivationtoken) | **PUT** /organizations/activation/{token} | Link the user to the Organization User identified by the token
*OrganizationApi* | [**createOrganization**](docs/OrganizationApi.md#createorganization) | **POST** /organizations | Create an organization
*OrganizationApi* | [**createOrganizationContest**](docs/OrganizationApi.md#createorganizationcontest) | **POST** /organizations/{organizationId}/contests | Add a contest to the organization
*OrganizationApi* | [**createOrganizationGroup**](docs/OrganizationApi.md#createorganizationgroup) | **POST** /organizations/{organizationId}/groups | Add a group and connect it to the organization
*OrganizationApi* | [**createOrganizationInvite**](docs/OrganizationApi.md#createorganizationinvite) | **POST** /organizations/{organizationId}/invites | Invite someone to become a member of the organization
*OrganizationApi* | [**deleteOrganizationContest**](docs/OrganizationApi.md#deleteorganizationcontest) | **DELETE** /organizations/{organizationId}/contests/{contestId} | Delete organization contest
*OrganizationApi* | [**deleteOrganizationGroup**](docs/OrganizationApi.md#deleteorganizationgroup) | **DELETE** /organizations/{organizationId}/groups/{groupId} | Delete group connected to the organization
*OrganizationApi* | [**deleteOrganizationInvite**](docs/OrganizationApi.md#deleteorganizationinvite) | **DELETE** /organizations/{organizationId}/invites/{inviteId} | Delete a pending invite
*OrganizationApi* | [**deleteOrganizationMember**](docs/OrganizationApi.md#deleteorganizationmember) | **DELETE** /organizations/{organizationId}/members/{memberId} | Delete an member of the organization
*OrganizationApi* | [**editOrganizationInvite**](docs/OrganizationApi.md#editorganizationinvite) | **PUT** /organizations/{organizationId}/invites/{inviteId} | 
*OrganizationApi* | [**editOrganizationMember**](docs/OrganizationApi.md#editorganizationmember) | **PUT** /organizations/{organizationId}/members/{memberId} | 
*OrganizationApi* | [**getInvitesForOrganization**](docs/OrganizationApi.md#getinvitesfororganization) | **GET** /organizations/{organizationId}/invites | Retrieve invites which is pending on acceptance from user before becoming a member
*OrganizationApi* | [**getOrganization**](docs/OrganizationApi.md#getorganization) | **GET** /organizations/{organizationUuid} | Get an organization
*OrganizationApi* | [**getOrganizationByActivationToken**](docs/OrganizationApi.md#getorganizationbyactivationtoken) | **GET** /organizations/activation/{token} | Get token information
*OrganizationApi* | [**getOrganizationContests**](docs/OrganizationApi.md#getorganizationcontests) | **GET** /organizations/{organizationId}/contests | Get contest created by organization
*OrganizationApi* | [**getOrganizationGroups**](docs/OrganizationApi.md#getorganizationgroups) | **GET** /organizations/{organizationId}/groups | Get groups connected to the organization
*OrganizationApi* | [**getOrganizationMembers**](docs/OrganizationApi.md#getorganizationmembers) | **GET** /organizations/{organizationId}/members | Get members of an organization
*OrganizationApi* | [**getOrganizationWhitelistedEmailDomains**](docs/OrganizationApi.md#getorganizationwhitelistedemaildomains) | **GET** /organizations/{organizationUuid}/whitelisted-email-domains | Get whitelisted email domains of an organization
*OrganizationApi* | [**grantPermissions**](docs/OrganizationApi.md#grantpermissions) | **POST** /organizations/{organizationId}/grants | 
*OrganizationApi* | [**rejectPermissions**](docs/OrganizationApi.md#rejectpermissions) | **DELETE** /organizations/{organizationId}/grants | 
*OrganizationApi* | [**sendInviteAsSms**](docs/OrganizationApi.md#sendinviteassms) | **POST** /organizations/activation/{token}/invite-sms | Sends the invite corresponding to the provided invite token to the provided phone number
*OrganizationApi* | [**sendOrganizationInvites**](docs/OrganizationApi.md#sendorganizationinvites) | **POST** /organizations/{organizationId}/actions/send-invites | Batch operation to send invites for invited persons to the organization
*OrganizationApi* | [**updateOrganizationContest**](docs/OrganizationApi.md#updateorganizationcontest) | **PUT** /organizations/{organizationId}/contests/{contestId} | Update contest of the organization
*OrganizationApi* | [**updateOrganizationGroup**](docs/OrganizationApi.md#updateorganizationgroup) | **PUT** /organizations/{organizationId}/groups/{groupId} | Update group connected to the organization
*OrganizationApi* | [**updateOrganizationGroupInvites**](docs/OrganizationApi.md#updateorganizationgroupinvites) | **PUT** /organizations/{organizationId}/groups/{groupId}/invites | Update members invites collection for group connected to the organization
*OrganizationApi* | [**updateOrganizationGroupMembers**](docs/OrganizationApi.md#updateorganizationgroupmembers) | **PUT** /organizations/{organizationId}/groups/{groupId}/members | Update members collection for group connected to the organization
*OrganizationApi* | [**updateOrganizationWhitelistedEmailDomains**](docs/OrganizationApi.md#updateorganizationwhitelistedemaildomains) | **PUT** /organizations/{organizationUuid}/whitelisted-email-domains | Update whitelisted email domains of an organization
*OrganizationApi* | [**verifyOrganizationMember**](docs/OrganizationApi.md#verifyorganizationmember) | **POST** /organizations/{organizationUuid}/members/{memberId}/verify | Verify that the member is a valid member of the organization
*PrivacySettingsApi* | [**getPrivacyConsents**](docs/PrivacySettingsApi.md#getprivacyconsents) | **GET** /me/privacy/consents | 
*PrivacySettingsApi* | [**grantPrivacyConsent**](docs/PrivacySettingsApi.md#grantprivacyconsent) | **POST** /me/privacy/consents/{feature}/grant | 
*PrivacySettingsApi* | [**revokePrivacyConsent**](docs/PrivacySettingsApi.md#revokeprivacyconsent) | **POST** /me/privacy/consents/{feature}/revoke | 
*ServiceProviderApi* | [**deleteUserService**](docs/ServiceProviderApi.md#deleteuserservice) | **DELETE** /me/services/{serviceProvider} | 
*ServiceProviderApi* | [**getUserServices**](docs/ServiceProviderApi.md#getuserservices) | **GET** /me/services | 
*ServiceProviderApi* | [**serviceProviderConnect**](docs/ServiceProviderApi.md#serviceproviderconnect) | **POST** /me/services/{serviceProvider}/connect | 
*TeamApi* | [**acceptTeamInvite**](docs/TeamApi.md#acceptteaminvite) | **PUT** /teams/invites/{token} | 
*TeamApi* | [**getTeamInviteTokenData**](docs/TeamApi.md#getteaminvitetokendata) | **GET** /teams/invites/{token} | 
*TeamApi* | [**getTeamMembers**](docs/TeamApi.md#getteammembers) | **GET** /teams/{teamUuid}/members | 
*TeamApi* | [**getUserTeamContests**](docs/TeamApi.md#getuserteamcontests) | **GET** /me/teams/{teamId}/contests | 
*TeamApi* | [**getUserTeamMembers**](docs/TeamApi.md#getuserteammembers) | **GET** /me/teams/{teamId}/members | 
*TeamApi* | [**getUserTeams**](docs/TeamApi.md#getuserteams) | **GET** /me/teams | 
*TeamApi* | [**sendTeamInviteAsSms**](docs/TeamApi.md#sendteaminviteassms) | **POST** /teams/invites/{token}/invite-sms | Sends the invite corresponding to the invite token to the provided phone number
*TeamApi* | [**updateTeamMembers**](docs/TeamApi.md#updateteammembers) | **PATCH** /teams/{teamUuid}/members | 
*TrainersApi* | [**getTrainers**](docs/TrainersApi.md#gettrainers) | **GET** /trainers | Get trainers
*UserApi* | [**bioAgeAppUserMeDevicesPost**](docs/UserApi.md#bioageappusermedevicespost) | **POST** /bio-age-app/user/me/devices | Stores device token for the user
*UserApi* | [**bioAgeAppUserMePurchasesPost**](docs/UserApi.md#bioageappusermepurchasespost) | **POST** /bio-age-app/user/me/purchases | Submit new mobile purchased receipt
*UserApi* | [**bioAgeAppUserMeSubscriptionsPost**](docs/UserApi.md#bioageappusermesubscriptionspost) | **POST** /bio-age-app/user/me/subscriptions | Submit new subscription trial
*UserApi* | [**deleteUserSetting**](docs/UserApi.md#deleteusersetting) | **DELETE** /me/settings/{key} | 
*UserApi* | [**getMyUser**](docs/UserApi.md#getmyuser) | **GET** /bio-age-app/user/me | Return the logged in user
*UserApi* | [**getSharedEvaluation**](docs/UserApi.md#getsharedevaluation) | **GET** /me/shared-evaluations/{hash} | 
*UserApi* | [**getUserSettings**](docs/UserApi.md#getusersettings) | **GET** /me/settings | 
*UserApi* | [**updateMyUser**](docs/UserApi.md#updatemyuser) | **PUT** /bio-age-app/user/me | Updates user or usermeta
*UserApi* | [**updateOrCreateUserSetting**](docs/UserApi.md#updateorcreateusersetting) | **PUT** /me/settings/{key} | 
*UserApi* | [**userExists**](docs/UserApi.md#userexists) | **GET** /users | Check if a user exists
*UserAnalysisApi* | [**getUserActivityProgress**](docs/UserAnalysisApi.md#getuseractivityprogress) | **GET** /me/activity-progress | 
*VideosApi* | [**getVideoCategories**](docs/VideosApi.md#getvideocategories) | **GET** /video-categories | Get video categories
*VideosApi* | [**getVideos**](docs/VideosApi.md#getvideos) | **GET** /videos | Get exercise videos
*WorkoutApi* | [**addWorkout**](docs/WorkoutApi.md#addworkout) | **POST** /me/workouts | 
*WorkoutApi* | [**deleteWorkout**](docs/WorkoutApi.md#deleteworkout) | **DELETE** /me/workouts/{workoutId} | 
*WorkoutApi* | [**getWorkout**](docs/WorkoutApi.md#getworkout) | **GET** /me/workouts/{workoutId} | 
*WorkoutApi* | [**getWorkoutHeartRates**](docs/WorkoutApi.md#getworkoutheartrates) | **GET** /me/workouts/{workoutId}/heart-rates | 
*WorkoutApi* | [**getWorkoutTimeseries**](docs/WorkoutApi.md#getworkouttimeseries) | **GET** /me/workouts/{workoutId}/timeseries | 
*WorkoutApi* | [**getWorkouts**](docs/WorkoutApi.md#getworkouts) | **GET** /me/workouts | 
*WorkoutApi* | [**importTcxWorkout**](docs/WorkoutApi.md#importtcxworkout) | **POST** /me/workouts/import/tcx | 
*WorkoutApi* | [**patchWorkout**](docs/WorkoutApi.md#patchworkout) | **PATCH** /me/workouts/{workoutId} | 
*WorkoutApi* | [**putWorkoutAltitudes**](docs/WorkoutApi.md#putworkoutaltitudes) | **PUT** /me/workouts/{workoutId}/altitudes | This endpoint supports \"Content-encoding\" with \"Gzip\" and should be used when possible.
*WorkoutApi* | [**putWorkoutHeartRates**](docs/WorkoutApi.md#putworkoutheartrates) | **PUT** /me/workouts/{workoutId}/heart-rates | This endpoint supports \"Content-encoding\" with \"Gzip\" and should be used when possible.
*WorkoutApi* | [**putWorkoutLocations**](docs/WorkoutApi.md#putworkoutlocations) | **PUT** /me/workouts/{workoutId}/locations | This endpoint supports \"Content-encoding\" with \"Gzip\" and should be used when possible.
*WorkoutApi* | [**updateWorkout**](docs/WorkoutApi.md#updateworkout) | **PUT** /me/workouts/{workoutId} | 
*WorkoutApi* | [**workoutActionEstimateHealth**](docs/WorkoutApi.md#workoutactionestimatehealth) | **POST** /me/workouts/{workoutId}/actions/estimate-health | 


<a name="documentation-for-models"></a>
## Documentation for Models

 - [com.myworkout.kotlin.api.models.AcceptOrganizationPermissionsInput](docs/AcceptOrganizationPermissionsInput.md)
 - [com.myworkout.kotlin.api.models.AddFeedbackInput](docs/AddFeedbackInput.md)
 - [com.myworkout.kotlin.api.models.AddWorkoutResponse](docs/AddWorkoutResponse.md)
 - [com.myworkout.kotlin.api.models.Altitude](docs/Altitude.md)
 - [com.myworkout.kotlin.api.models.Client](docs/Client.md)
 - [com.myworkout.kotlin.api.models.ClientGroup](docs/ClientGroup.md)
 - [com.myworkout.kotlin.api.models.ClientGroupMember](docs/ClientGroupMember.md)
 - [com.myworkout.kotlin.api.models.ClientGroupMembersResponse](docs/ClientGroupMembersResponse.md)
 - [com.myworkout.kotlin.api.models.ClientGroupResponse](docs/ClientGroupResponse.md)
 - [com.myworkout.kotlin.api.models.ClientGroupsResponse](docs/ClientGroupsResponse.md)
 - [com.myworkout.kotlin.api.models.ClientResponse](docs/ClientResponse.md)
 - [com.myworkout.kotlin.api.models.ClientWorkout](docs/ClientWorkout.md)
 - [com.myworkout.kotlin.api.models.ClientWorkoutCalculatedEstimates](docs/ClientWorkoutCalculatedEstimates.md)
 - [com.myworkout.kotlin.api.models.ClientWorkoutCalculatedEstimatesHealthCondition](docs/ClientWorkoutCalculatedEstimatesHealthCondition.md)
 - [com.myworkout.kotlin.api.models.ClientWorkoutCalculatedEstimatesHealthConditionParameters](docs/ClientWorkoutCalculatedEstimatesHealthConditionParameters.md)
 - [com.myworkout.kotlin.api.models.ConnectUsingIdentityProviderInput](docs/ConnectUsingIdentityProviderInput.md)
 - [com.myworkout.kotlin.api.models.ContestResponse](docs/ContestResponse.md)
 - [com.myworkout.kotlin.api.models.ContestResponseLinks](docs/ContestResponseLinks.md)
 - [com.myworkout.kotlin.api.models.ContestResponseTeams](docs/ContestResponseTeams.md)
 - [com.myworkout.kotlin.api.models.ContestsLeaderboardRanking](docs/ContestsLeaderboardRanking.md)
 - [com.myworkout.kotlin.api.models.CreateClientGroupInput](docs/CreateClientGroupInput.md)
 - [com.myworkout.kotlin.api.models.CreateContestInput](docs/CreateContestInput.md)
 - [com.myworkout.kotlin.api.models.CreateContestResponse](docs/CreateContestResponse.md)
 - [com.myworkout.kotlin.api.models.CreateFeedActivityInput](docs/CreateFeedActivityInput.md)
 - [com.myworkout.kotlin.api.models.CreateFeedActivityResponse](docs/CreateFeedActivityResponse.md)
 - [com.myworkout.kotlin.api.models.CreateGroupInput](docs/CreateGroupInput.md)
 - [com.myworkout.kotlin.api.models.CreateGroupResponse](docs/CreateGroupResponse.md)
 - [com.myworkout.kotlin.api.models.CreateOrganizationDepartmentInput](docs/CreateOrganizationDepartmentInput.md)
 - [com.myworkout.kotlin.api.models.CreateOrganizationDepartmentMemberInput](docs/CreateOrganizationDepartmentMemberInput.md)
 - [com.myworkout.kotlin.api.models.CreateOrganizationDepartmentMemberResponse](docs/CreateOrganizationDepartmentMemberResponse.md)
 - [com.myworkout.kotlin.api.models.CreateOrganizationDepartmentResponse](docs/CreateOrganizationDepartmentResponse.md)
 - [com.myworkout.kotlin.api.models.CreateOrganizationInput](docs/CreateOrganizationInput.md)
 - [com.myworkout.kotlin.api.models.CreateOrganizationInviteInput](docs/CreateOrganizationInviteInput.md)
 - [com.myworkout.kotlin.api.models.CreateOrganizationResponse](docs/CreateOrganizationResponse.md)
 - [com.myworkout.kotlin.api.models.CreateOrganizationResponseLink](docs/CreateOrganizationResponseLink.md)
 - [com.myworkout.kotlin.api.models.CreateServiceProviderConnectResponse](docs/CreateServiceProviderConnectResponse.md)
 - [com.myworkout.kotlin.api.models.CreateServiceProviderConnectResponseLinks](docs/CreateServiceProviderConnectResponseLinks.md)
 - [com.myworkout.kotlin.api.models.CreateSharedEvaluationInput](docs/CreateSharedEvaluationInput.md)
 - [com.myworkout.kotlin.api.models.CreateWorkoutInput](docs/CreateWorkoutInput.md)
 - [com.myworkout.kotlin.api.models.DailyActivitySummary](docs/DailyActivitySummary.md)
 - [com.myworkout.kotlin.api.models.DataPermission](docs/DataPermission.md)
 - [com.myworkout.kotlin.api.models.Device](docs/Device.md)
 - [com.myworkout.kotlin.api.models.EditOrganizationInviteInput](docs/EditOrganizationInviteInput.md)
 - [com.myworkout.kotlin.api.models.EditOrganizationMemberInput](docs/EditOrganizationMemberInput.md)
 - [com.myworkout.kotlin.api.models.Error](docs/Error.md)
 - [com.myworkout.kotlin.api.models.FeedActivity](docs/FeedActivity.md)
 - [com.myworkout.kotlin.api.models.FeedActivityActivityData](docs/FeedActivityActivityData.md)
 - [com.myworkout.kotlin.api.models.FeedActivityUser](docs/FeedActivityUser.md)
 - [com.myworkout.kotlin.api.models.GetClientWorkoutsResponse](docs/GetClientWorkoutsResponse.md)
 - [com.myworkout.kotlin.api.models.GetClientWorkoutsResponseLinks](docs/GetClientWorkoutsResponseLinks.md)
 - [com.myworkout.kotlin.api.models.GetClientWorkoutsResponseMeta](docs/GetClientWorkoutsResponseMeta.md)
 - [com.myworkout.kotlin.api.models.GetContestPublicLeaderboardResponse](docs/GetContestPublicLeaderboardResponse.md)
 - [com.myworkout.kotlin.api.models.GetContestPublicLeaderboardResponseData](docs/GetContestPublicLeaderboardResponseData.md)
 - [com.myworkout.kotlin.api.models.GetContestPublicLeaderboardResponseDataContest](docs/GetContestPublicLeaderboardResponseDataContest.md)
 - [com.myworkout.kotlin.api.models.GetFeedActivitiesResponse](docs/GetFeedActivitiesResponse.md)
 - [com.myworkout.kotlin.api.models.GetIndividualLeaderboardResponse](docs/GetIndividualLeaderboardResponse.md)
 - [com.myworkout.kotlin.api.models.GetNotificationsResponse](docs/GetNotificationsResponse.md)
 - [com.myworkout.kotlin.api.models.GetNotificationsResponseLinks](docs/GetNotificationsResponseLinks.md)
 - [com.myworkout.kotlin.api.models.GetNotificationsResponseMeta](docs/GetNotificationsResponseMeta.md)
 - [com.myworkout.kotlin.api.models.GetOrganizationContestCollectionResponse](docs/GetOrganizationContestCollectionResponse.md)
 - [com.myworkout.kotlin.api.models.GetOrganizationDepartmentMembersResponse](docs/GetOrganizationDepartmentMembersResponse.md)
 - [com.myworkout.kotlin.api.models.GetOrganizationDepartmentsResponse](docs/GetOrganizationDepartmentsResponse.md)
 - [com.myworkout.kotlin.api.models.GetOrganizationGroupsResponse](docs/GetOrganizationGroupsResponse.md)
 - [com.myworkout.kotlin.api.models.GetOrganizationResponse](docs/GetOrganizationResponse.md)
 - [com.myworkout.kotlin.api.models.GetOrganizationResponseData](docs/GetOrganizationResponseData.md)
 - [com.myworkout.kotlin.api.models.GetOrganizationWhitelistedEmailDomainsResponse](docs/GetOrganizationWhitelistedEmailDomainsResponse.md)
 - [com.myworkout.kotlin.api.models.GetTeamContestsResponse](docs/GetTeamContestsResponse.md)
 - [com.myworkout.kotlin.api.models.GetTeamInviteTokenDataResponse](docs/GetTeamInviteTokenDataResponse.md)
 - [com.myworkout.kotlin.api.models.GetTeamInviteTokenDataResponseData](docs/GetTeamInviteTokenDataResponseData.md)
 - [com.myworkout.kotlin.api.models.GetTeamInviteTokenDataResponseDataTeam](docs/GetTeamInviteTokenDataResponseDataTeam.md)
 - [com.myworkout.kotlin.api.models.GetTeamInviteTokenDataResponseMeta](docs/GetTeamInviteTokenDataResponseMeta.md)
 - [com.myworkout.kotlin.api.models.GetTeamInviteTokenDataResponseMetaLinks](docs/GetTeamInviteTokenDataResponseMetaLinks.md)
 - [com.myworkout.kotlin.api.models.GetTeamMembersResponse](docs/GetTeamMembersResponse.md)
 - [com.myworkout.kotlin.api.models.GetTeamsResponse](docs/GetTeamsResponse.md)
 - [com.myworkout.kotlin.api.models.GetTeamsResponseMeta](docs/GetTeamsResponseMeta.md)
 - [com.myworkout.kotlin.api.models.GetTeamsResponseMetaCaptainOf](docs/GetTeamsResponseMetaCaptainOf.md)
 - [com.myworkout.kotlin.api.models.GetUserActivityProgressResponse](docs/GetUserActivityProgressResponse.md)
 - [com.myworkout.kotlin.api.models.GetUserContestResponse](docs/GetUserContestResponse.md)
 - [com.myworkout.kotlin.api.models.GetUserContestsLeaderboardRankingResponse](docs/GetUserContestsLeaderboardRankingResponse.md)
 - [com.myworkout.kotlin.api.models.GetUserServicesResponse](docs/GetUserServicesResponse.md)
 - [com.myworkout.kotlin.api.models.GetUserSettingsResponse](docs/GetUserSettingsResponse.md)
 - [com.myworkout.kotlin.api.models.GetUserTeamMembersResponse](docs/GetUserTeamMembersResponse.md)
 - [com.myworkout.kotlin.api.models.GetWorkoutResponse](docs/GetWorkoutResponse.md)
 - [com.myworkout.kotlin.api.models.GetWorkoutTimeseriesResponse](docs/GetWorkoutTimeseriesResponse.md)
 - [com.myworkout.kotlin.api.models.GetWorkoutTimeseriesResponseData](docs/GetWorkoutTimeseriesResponseData.md)
 - [com.myworkout.kotlin.api.models.GetWorkoutsResponse](docs/GetWorkoutsResponse.md)
 - [com.myworkout.kotlin.api.models.GroupInviteResponse](docs/GroupInviteResponse.md)
 - [com.myworkout.kotlin.api.models.GroupMemberResponse](docs/GroupMemberResponse.md)
 - [com.myworkout.kotlin.api.models.GroupResponse](docs/GroupResponse.md)
 - [com.myworkout.kotlin.api.models.HeartRate](docs/HeartRate.md)
 - [com.myworkout.kotlin.api.models.HitPeriod](docs/HitPeriod.md)
 - [com.myworkout.kotlin.api.models.IndividualLeaderboardTeam](docs/IndividualLeaderboardTeam.md)
 - [com.myworkout.kotlin.api.models.IndividualLeaderboardUser](docs/IndividualLeaderboardUser.md)
 - [com.myworkout.kotlin.api.models.IndividualLeaderboardUserUser](docs/IndividualLeaderboardUserUser.md)
 - [com.myworkout.kotlin.api.models.InlineResponse200](docs/InlineResponse200.md)
 - [com.myworkout.kotlin.api.models.InlineResponse200Meta](docs/InlineResponse200Meta.md)
 - [com.myworkout.kotlin.api.models.InlineResponse200MetaLinks](docs/InlineResponse200MetaLinks.md)
 - [com.myworkout.kotlin.api.models.InlineResponse201](docs/InlineResponse201.md)
 - [com.myworkout.kotlin.api.models.InlineResponse2011](docs/InlineResponse2011.md)
 - [com.myworkout.kotlin.api.models.InlineResponse2011Meta](docs/InlineResponse2011Meta.md)
 - [com.myworkout.kotlin.api.models.InlineResponse2012](docs/InlineResponse2012.md)
 - [com.myworkout.kotlin.api.models.InlineResponse2012Meta](docs/InlineResponse2012Meta.md)
 - [com.myworkout.kotlin.api.models.InlineResponse2013](docs/InlineResponse2013.md)
 - [com.myworkout.kotlin.api.models.InlineResponse201Meta](docs/InlineResponse201Meta.md)
 - [com.myworkout.kotlin.api.models.Location](docs/Location.md)
 - [com.myworkout.kotlin.api.models.LoginUsingIdentityProviderInput](docs/LoginUsingIdentityProviderInput.md)
 - [com.myworkout.kotlin.api.models.LoginUsingIdentityProviderResponse](docs/LoginUsingIdentityProviderResponse.md)
 - [com.myworkout.kotlin.api.models.ManagedOrganization](docs/ManagedOrganization.md)
 - [com.myworkout.kotlin.api.models.Notification](docs/Notification.md)
 - [com.myworkout.kotlin.api.models.NotificationInput](docs/NotificationInput.md)
 - [com.myworkout.kotlin.api.models.Organization](docs/Organization.md)
 - [com.myworkout.kotlin.api.models.OrganizationActivationToken](docs/OrganizationActivationToken.md)
 - [com.myworkout.kotlin.api.models.OrganizationActivationTokenDepartment](docs/OrganizationActivationTokenDepartment.md)
 - [com.myworkout.kotlin.api.models.OrganizationActivationTokenMedia](docs/OrganizationActivationTokenMedia.md)
 - [com.myworkout.kotlin.api.models.OrganizationActivationTokenOrganization](docs/OrganizationActivationTokenOrganization.md)
 - [com.myworkout.kotlin.api.models.OrganizationActivationTokenOrganizationDepartments](docs/OrganizationActivationTokenOrganizationDepartments.md)
 - [com.myworkout.kotlin.api.models.OrganizationActivationTokenOrganizationLinks](docs/OrganizationActivationTokenOrganizationLinks.md)
 - [com.myworkout.kotlin.api.models.OrganizationDepartment](docs/OrganizationDepartment.md)
 - [com.myworkout.kotlin.api.models.OrganizationDepartmentAdmins](docs/OrganizationDepartmentAdmins.md)
 - [com.myworkout.kotlin.api.models.OrganizationDepartmentMemberRole](docs/OrganizationDepartmentMemberRole.md)
 - [com.myworkout.kotlin.api.models.OrganizationInviteCollectionResponse](docs/OrganizationInviteCollectionResponse.md)
 - [com.myworkout.kotlin.api.models.OrganizationInviteResponse](docs/OrganizationInviteResponse.md)
 - [com.myworkout.kotlin.api.models.OrganizationMember](docs/OrganizationMember.md)
 - [com.myworkout.kotlin.api.models.OrganizationMemberCollectionResponse](docs/OrganizationMemberCollectionResponse.md)
 - [com.myworkout.kotlin.api.models.OrganizationMemberDepartment](docs/OrganizationMemberDepartment.md)
 - [com.myworkout.kotlin.api.models.OrganizationMemberRole](docs/OrganizationMemberRole.md)
 - [com.myworkout.kotlin.api.models.PatchNotificationInput](docs/PatchNotificationInput.md)
 - [com.myworkout.kotlin.api.models.PatchNotificationsInput](docs/PatchNotificationsInput.md)
 - [com.myworkout.kotlin.api.models.PatchWorkoutInput](docs/PatchWorkoutInput.md)
 - [com.myworkout.kotlin.api.models.PrivacyConsentState](docs/PrivacyConsentState.md)
 - [com.myworkout.kotlin.api.models.PrivacyConsentsResponse](docs/PrivacyConsentsResponse.md)
 - [com.myworkout.kotlin.api.models.PutActivateOrganizationActivationTokenInput](docs/PutActivateOrganizationActivationTokenInput.md)
 - [com.myworkout.kotlin.api.models.PutGroupInviteResponse](docs/PutGroupInviteResponse.md)
 - [com.myworkout.kotlin.api.models.PutGroupMembersResponse](docs/PutGroupMembersResponse.md)
 - [com.myworkout.kotlin.api.models.PutUserSettingsInput](docs/PutUserSettingsInput.md)
 - [com.myworkout.kotlin.api.models.PutUserSettingsResponse](docs/PutUserSettingsResponse.md)
 - [com.myworkout.kotlin.api.models.PutWorkoutAltitudesInput](docs/PutWorkoutAltitudesInput.md)
 - [com.myworkout.kotlin.api.models.PutWorkoutHeartRatesInput](docs/PutWorkoutHeartRatesInput.md)
 - [com.myworkout.kotlin.api.models.PutWorkoutLocationsInput](docs/PutWorkoutLocationsInput.md)
 - [com.myworkout.kotlin.api.models.SendInviteAsSmsInput](docs/SendInviteAsSmsInput.md)
 - [com.myworkout.kotlin.api.models.SendOrganizationInviteInput](docs/SendOrganizationInviteInput.md)
 - [com.myworkout.kotlin.api.models.SendOrganizationInvitesInput](docs/SendOrganizationInvitesInput.md)
 - [com.myworkout.kotlin.api.models.SendTeamInviteAsSmsInput](docs/SendTeamInviteAsSmsInput.md)
 - [com.myworkout.kotlin.api.models.SharedEvaluation](docs/SharedEvaluation.md)
 - [com.myworkout.kotlin.api.models.SharedEvaluationResponse](docs/SharedEvaluationResponse.md)
 - [com.myworkout.kotlin.api.models.SsoToken](docs/SsoToken.md)
 - [com.myworkout.kotlin.api.models.SsoTokenResponse](docs/SsoTokenResponse.md)
 - [com.myworkout.kotlin.api.models.Team](docs/Team.md)
 - [com.myworkout.kotlin.api.models.TeamCaptain](docs/TeamCaptain.md)
 - [com.myworkout.kotlin.api.models.TeamContest](docs/TeamContest.md)
 - [com.myworkout.kotlin.api.models.TeamFeed](docs/TeamFeed.md)
 - [com.myworkout.kotlin.api.models.TeamMembers](docs/TeamMembers.md)
 - [com.myworkout.kotlin.api.models.TeamsByUuidInput](docs/TeamsByUuidInput.md)
 - [com.myworkout.kotlin.api.models.TrainerCollectionResponse](docs/TrainerCollectionResponse.md)
 - [com.myworkout.kotlin.api.models.TrainerResponse](docs/TrainerResponse.md)
 - [com.myworkout.kotlin.api.models.UpdateClientGroupInput](docs/UpdateClientGroupInput.md)
 - [com.myworkout.kotlin.api.models.UpdateClientGroupMembersInput](docs/UpdateClientGroupMembersInput.md)
 - [com.myworkout.kotlin.api.models.UpdateContestInput](docs/UpdateContestInput.md)
 - [com.myworkout.kotlin.api.models.UpdateContestResponse](docs/UpdateContestResponse.md)
 - [com.myworkout.kotlin.api.models.UpdateGroupResponse](docs/UpdateGroupResponse.md)
 - [com.myworkout.kotlin.api.models.UpdateOrganizationDepartmentInput](docs/UpdateOrganizationDepartmentInput.md)
 - [com.myworkout.kotlin.api.models.UpdateOrganizationDepartmentMemberInput](docs/UpdateOrganizationDepartmentMemberInput.md)
 - [com.myworkout.kotlin.api.models.UpdateOrganizationDepartmentMemberResponse](docs/UpdateOrganizationDepartmentMemberResponse.md)
 - [com.myworkout.kotlin.api.models.UpdateOrganizationDepartmentResponse](docs/UpdateOrganizationDepartmentResponse.md)
 - [com.myworkout.kotlin.api.models.UpdateOrganizationGroupInviteInput](docs/UpdateOrganizationGroupInviteInput.md)
 - [com.myworkout.kotlin.api.models.UpdateOrganizationGroupMembersInput](docs/UpdateOrganizationGroupMembersInput.md)
 - [com.myworkout.kotlin.api.models.UpdateOrganizationWhitelistedEmailDomainsResponse](docs/UpdateOrganizationWhitelistedEmailDomainsResponse.md)
 - [com.myworkout.kotlin.api.models.UpdateTeamMembersInput](docs/UpdateTeamMembersInput.md)
 - [com.myworkout.kotlin.api.models.UpdateTeamMembersInputRemove](docs/UpdateTeamMembersInputRemove.md)
 - [com.myworkout.kotlin.api.models.UpdateUserInput](docs/UpdateUserInput.md)
 - [com.myworkout.kotlin.api.models.UpdateUserInputMeta](docs/UpdateUserInputMeta.md)
 - [com.myworkout.kotlin.api.models.UpdateWorkoutInput](docs/UpdateWorkoutInput.md)
 - [com.myworkout.kotlin.api.models.UpdateWorkoutResponse](docs/UpdateWorkoutResponse.md)
 - [com.myworkout.kotlin.api.models.User](docs/User.md)
 - [com.myworkout.kotlin.api.models.UserActivityProgress](docs/UserActivityProgress.md)
 - [com.myworkout.kotlin.api.models.UserActivityProgressActivities](docs/UserActivityProgressActivities.md)
 - [com.myworkout.kotlin.api.models.UserBusinessClient](docs/UserBusinessClient.md)
 - [com.myworkout.kotlin.api.models.UserContest](docs/UserContest.md)
 - [com.myworkout.kotlin.api.models.UserManagedOrganizations](docs/UserManagedOrganizations.md)
 - [com.myworkout.kotlin.api.models.UserPurchasesInput](docs/UserPurchasesInput.md)
 - [com.myworkout.kotlin.api.models.UserResponse](docs/UserResponse.md)
 - [com.myworkout.kotlin.api.models.UserResponseMeta](docs/UserResponseMeta.md)
 - [com.myworkout.kotlin.api.models.UserServices](docs/UserServices.md)
 - [com.myworkout.kotlin.api.models.UserSetting](docs/UserSetting.md)
 - [com.myworkout.kotlin.api.models.UserSubscription](docs/UserSubscription.md)
 - [com.myworkout.kotlin.api.models.UserSubscriptionInput](docs/UserSubscriptionInput.md)
 - [com.myworkout.kotlin.api.models.UserWithJWT](docs/UserWithJWT.md)
 - [com.myworkout.kotlin.api.models.UserWithJWTResponseDeprecated](docs/UserWithJWTResponseDeprecated.md)
 - [com.myworkout.kotlin.api.models.UserWithJWTResponseDeprecatedMeta](docs/UserWithJWTResponseDeprecatedMeta.md)
 - [com.myworkout.kotlin.api.models.VideoCategoryResponse](docs/VideoCategoryResponse.md)
 - [com.myworkout.kotlin.api.models.VideoCollectionResponse](docs/VideoCollectionResponse.md)
 - [com.myworkout.kotlin.api.models.VideoCollectionResponsePaging](docs/VideoCollectionResponsePaging.md)
 - [com.myworkout.kotlin.api.models.VideoResponse](docs/VideoResponse.md)
 - [com.myworkout.kotlin.api.models.Workout](docs/Workout.md)
 - [com.myworkout.kotlin.api.models.WorkoutCalculatedEstimates](docs/WorkoutCalculatedEstimates.md)
 - [com.myworkout.kotlin.api.models.WorkoutCalculatedEstimatesHealthCondition](docs/WorkoutCalculatedEstimatesHealthCondition.md)
 - [com.myworkout.kotlin.api.models.WorkoutExercise](docs/WorkoutExercise.md)
 - [com.myworkout.kotlin.api.models.WorkoutExerciseInput](docs/WorkoutExerciseInput.md)
 - [com.myworkout.kotlin.api.models.WorkoutHeartRatesResponse](docs/WorkoutHeartRatesResponse.md)
 - [com.myworkout.kotlin.api.models.WorkoutSet](docs/WorkoutSet.md)
 - [com.myworkout.kotlin.api.models.WorkoutSetInput](docs/WorkoutSetInput.md)
 - [com.myworkout.kotlin.api.models.WorkoutStage](docs/WorkoutStage.md)
 - [com.myworkout.kotlin.api.models.WorkoutStageInput](docs/WorkoutStageInput.md)
 - [com.myworkout.kotlin.api.models.WorkoutTimeseries](docs/WorkoutTimeseries.md)


<a name="documentation-for-authorization"></a>
## Documentation for Authorization

<a name="ClientCredentials"></a>
### ClientCredentials

- **Type**: OAuth
- **Flow**: application
- **Authorization URL**: 
- **Scopes**: 
  - *: All scopes

<a name="JWTAuth"></a>
### JWTAuth

- **Type**: API key
- **API key parameter name**: Authorization
- **Location**: HTTP header

<a name="OAuth2"></a>
### OAuth2

- **Type**: OAuth
- **Flow**: password
- **Authorization URL**: 
- **Scopes**: 
  - *: All scopes

<a name="OAuth2"></a>
### OAuth2

- **Type**: OAuth
- **Flow**: accessCode
- **Authorization URL**: https://api.myworkout.com/oauth/authorize
- **Scopes**: 
  - *: All scopes

