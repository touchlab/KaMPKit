[![KaMP Kit Android](https://img.shields.io/github/workflow/status/touchlab/KaMPKit/KaMPKit-Android/main?logo=Android&style=plastic)](https://github.com/touchlab/KaMPKit/actions/workflows/KaMPKit-Android.yml)
[![KaMP Kit iOS](https://img.shields.io/github/workflow/status/touchlab/KaMPKit/KaMPKit-iOS?logo=iOS&style=plastic)](https://github.com/touchlab/KaMPKit/actions/workflows/KaMPKit-iOS.yml)

# KaMP Kit

![KaMP Kit Image](kampkit.png)

***Welcome to KaMP Kit!*** 

## 2022 Update

KaMP Kit started a little over 2 years ago with the goal of helping developers interested in KMP and KMM get started
quickly with a great set of libraries and patterns. At the time, there were not many sample apps and getting started
was not trivial. The KMM situation has improved considerably since then, and various barriers to entry have been
removed.

Whereas KaMP Kit started with the goal of being a minimal sample, we now intend it to be less "getting started" and
more "best practice model". Watch this repo and follow [@TouchlabHQ](https://twitter.com/TouchlabHQ) for updates!

> ## Subscribe!
>
> We build solutions that get teams started smoothly with Kotlin Multiplatform Mobile and ensure their success in production. Join our community to learn how your peers are adopting KMM.
 [Sign up here](https://go.touchlab.co/newsletter-gh)!

## Getting Help

KaMP Kit support can be found in the Kotlin Community Slack, [request access here](http://slack.kotlinlang.org/). Post in the "[#touchlab-tools](https://kotlinlang.slack.com/archives/CTJB58X7X)" channel.

For direct assistance, please [contact Touchlab](https://go.touchlab.co/contactkamp) to discuss support options.

## About

### Goal

The goal of KaMP Kit is to facilitate your evaluation of Kotlin Multiplatform (aka KMP). It is a collection of code and
tools designed to get you started quickly. It's also a showcase of Touchlab's typical choices for architecture,
libraries, and other best practices.

The KMP ecosystem has generated a lot of excitement, and has evolved very rapidly. As a result, there's a lot of old or
conflicting documentation, blog posts, tutorials, etc. We, Touchlab, have worked with several teams looking at KMM (
Kotlin Multiplatform Mobile) and KMP, and have found that the **primary** stumbling block is simply getting started.

KaMP Kit is designed to get you past that primary stumbling block. You should be able to set up your development environment, clone the repo, and have a running sample app very quickly. From there, you can focus on what you want to build.

#### *Very Important Message!!!*

This kit exists because the info you may find from Google about KMM and KMP is likely to be outdated or conflicting with the config here. It is highly recommended that you reach out directly if you run into issues.

### Audience

We (Touchlab) are focused primarily on using KMP for native mobile development (now called KMM). As a result, this kit is primarily targeted at native mobile developers (Android or iOS), as well as engineering managers for native mobile teams. You should have little-to-no experience with KMP, although some of the information after setup may be useful if you do have KMP experience.

## What's Included?

1. The Starter App - A native mobile KMP app with a small functional feature set.
2. Educational Resources - Introductory information on KMP and Kotlin/Native.
3. Integration Information - If you're integrating shared code into an existing application, guides to assist with that effort.

## What's *Not* Included?

Comprehensive guides, advanced tutorials, or generally support for fixing anything not included in the starter app. The goal is to have a solid starting point from which you can create something meaningful for evaluating KMP. We're intentionally limiting the scope to keep focus.

# The Starter App

The central part of the "Kit" is the starter app. It includes a set of libraries that we use in our apps that provide for much of the architectural needs of a native mobile application. We've also included a simple set of features you can use as a reference when adding your features.

## 1) Dev Environment and Build Setup

You will need the following:

* JVM 8
* Android SDK and the latest stable Android Studio(2021.2.1+) or IntelliJ(2021.2+)
* Intellij Kotlin plugin with 1.7.10 support (should be included in the latest Android Studio or IDEA)
* Mac with Xcode 14+ for the iOS build

For a more detailed guide targeted at iOS developers, see [DETAILED_DEV_SETUP](docs/DETAILED_DEV_SETUP.md).

## 2) Clone and Build

See [APP_BUILD](docs/APP_BUILD.md) for detailed build instructions. By the end of that doc you should be able to build and run both Android and iOS apps.

---

## Sanity Check

At this point, you should be able to build Android and iOS apps. **If you cannot build, you need to get help.** This sample app is configured to run out of the box, so if it's not working, you have something wrong with your build setup or config. Please [reach out to us](CONTACT_US.md) so we can improve either the config or troubleshooting docs, and/or the Kotlin Slack group mentioned above.

---

## 3) Walk Through App

Take a walk through the app's code and libraries. Make changes, recompile. See how it works.

[GENERAL_ARCHITECTURE](docs/GENERAL_ARCHITECTURE.md)

## 4) Background Education

If the app is building, it's a good time to take a break and get some background information.

### KMP Intro

It's important to understand not just how to set up the platform, but to get a better perspective on what the tech can do and why we think it'll be very successful. KMP is distinct from other code sharing and "cross platform" systems, and understanding those distinctions is useful.

[Longer intro to KaMP Kit](docs/WHAT_AND_WHY.md) - Original version of this doc's intro. Cut because it was pretty long.

[Intro to Kotlin Multiplatform](https://vimeo.com/371428809) - General intro to KMP from Oredev in Nov 2019. Good overall summary of the platform.

### "Selling" KMP

KaMPKit can help you demonstrate to management and other stakeholders the value of sharing code with KMP. Check out these resources for more advice on pitching KMP to your team:
[Building a Business Case for KMP](https://touchlab.co/building-business-case-kotlin-multiplatform/)
[7 ways to convince your engineering manager to pilot Kotlin Multiplatform](https://touchlab.co/7-ways-convince-engineering-manager-pilot-kotlin-multiplatform/)

### Xcode Debugging

For information on how to debug Kotlin in Xcode, check out the [Debugging Kotlin In Xcode](docs/DEBUGGING_KOTLIN_IN_XCODE.md) doc. 

## 5) Integrating 'shared' With Existing Apps

As part of your evaluation, you'll need to decide if you're going to integrate KMP into existing apps. Some teams feel integrating with their production apps is a better demonstration of KMP's viability. While KMP's interop is great, relative to other technologies, **integrating *anything* into a production app build process can be a difficult task**. Once integrated, development is generally smooth, but modifying production build systems can be a time consuming task.

### Android

The Android side is somewhat more straightforward. Kotlin is the preferred language for Android, and the library can be integrated as just another module library. We'll be updating soon with a general Android integration doc. In the meantime, the simplest method would be to copy the shared module into your standard Android build, and use the `app` module as a reference for dependency resolution.

### iOS

The iOS integration process is relatively new and has been iterating fast. Be prepared to spend more time with config related issues when integrating with a production build.

You can integrate with Cocoapods, or by directly including the Xcode framework. If you are an Android developer without extensive iOS build experience, be aware that this is a risky option. Production build systems, for any ecosystem, tend to be complex. You'll almost certainly need to recruit somebody with experience maintaining your iOS build.

See [IOS_PROJ_INTEGRATION.md](docs/IOS_PROJ_INTEGRATION.md) for iOS integration information.

If you are attempting to integrate your KMP project with a production iOS application, please let us know what issues you run into and reach out with questions if stuck. This is an ongoing area of improvement for the KMP platform and we'd like to help make this as smooth as possible.

---

## Troubleshooting

[TROUBLESHOOTING](docs/TROUBLESHOOTING.md) - We'll be growing this file over time, with your help. Please make sure
to document any issues you run into and [let us know](CONTACT_US.md). 

## More To Come!

KaMP Kit is just the beginning. Our hope is that after KaMP Kit you’ll have a better sense of what a possible KMP implementation might look like.

---

### About Touchlab

Touchlab is a mobile-focused development agency based in NYC. We have been working on Android since the beginning, and have worked on a wide range of mobile and hardware projects for the past decade. Over the past few years, we have invested significantly on R&D for code sharing technologies. We believe strongly in KMP's future and have made the Kotlin multiplatform the focus of our business.

### About The Kit

We talked to a few teams early on who got to do a "hack week" with KMP. A common story was, if they didn't abandon the project altogether, they didn't have anything running till the week was half over. Even then, picking libraries and architecture ate the rest of the time. Inevitably the result was, "KMP isn't ready". We know that once you're past the setup phase, KMP is really amazing tech. This Kit exists so you're evaluating KMP on Monday afternoon, not Thursday.

### What We Can Do For You

We have made KMP the focus of Touchlab. We had possibly the first KMP* app published in the iOS App Store, and have extensive experience in building libraries and the Kotlin platform, including contributions to Kotlin/Native itself. We can establish and accelerate your adoption of shared Kotlin code. See [touchlab.co](https://touchlab.co) for more info.

> ## Subscribe!
>
> We build solutions that get teams started smoothly with Kotlin Multiplatform Mobile and ensure their success in production. Join our community to learn how your peers are adopting KMM.
 [Sign up here](https://go.touchlab.co/newsletter)!
