# KaMP Kit

![KaMP Kit Image](kampkit.png)

***Welcome to the KaMP Kit!*** 

This Github repo contains the docs and sample app that comprise the Kotlin Multiplatform evaluation kit.

## Goal

The goal of the KaMP Kit is to facilitate your evaluation of Kotlin Multiplatform (aka KMP). The KMP ecosystem has generated a lot of excitement, and has evolved very rapidly. As a result, there's a lot of old or conflicting documentation, blog posts, tutorials, etc. We, Touchlab, have worked with several teams looking at KMP, and have found that the **primary** stumbling block is simply getting started.

This project, the "Kit", is designed to get you past that primary stumbling block. You should be able to set up your development environment, clone the repo, and have a running sample app very quickly. From there, you can focus on what you want to build.

## FYI

We (Touchlab) are focused primarily on using KMP for native mobile development; i.e. Android and iOS. The sample app in this Kit currently is for native mobile. Backend and web samples will be added over time based on interest (let us know!)

## Very Important Message!!!

This kit exists because the info you may find from Google about KMP is likely to be more confusing than helpful. To make sure you minimize the time spent trying to get started, it's important to ***stick to this kit exclusively until you get something running***. The sample project is designed to be run with minimal setup. If you run into issues building the sample project, please reach out directly so we can sort out your issue and improve our docs.

Also, if you're an Android developer, it *might* be useful to find an interested iOS developer once you get to Xcode, depending on your experience and goals, but YMMV.

## About KMP and the "Kit"

For a longer intro about what KMP is, why we like it, and more about the Kit's genesis, see [KMP: What and Why?](WHAT_AND_WHY.md). For the remainder of this doc, we're going to focus on getting started.

## Getting Help

Please see the [CONTACT_US](CONTACT_US.md) page for the best ways to reach out directly if you're having issues
 building the sapmle app. 

You should also create an account in the [Kotlin Slack](http://slack.kotlinlang.org/). Look for the `multiplatform` and `kotlin-native` channels. You can also find various members
 of the team hanging out there.

# The Starter App

The central part of the "Kit" is the starter app. It includes a set of libraries that we use in our apps that provide for much of the architectural needs of a native mobile application. We've also included a simple set of features you can reference and build off of.

The app gets dog breed info from an api and allows you to store it locally in a sqlite database. You can read more about it's general architecture and library usage [here](docs/GENERAL_ARCHITECTURE.md).

## Getting Started

The most important first step is to get the starter app building. From there, you can make decisions about integrating KMP into other projects, but nothing will work if you can't get the basic app to build.

### 1) Dev Environment and Build Setup

**If you are primarily an iOS Developer** and you are not familiar with building Android apps, there is some build environment stuff you'll need to configure first. For Android developers, if you run into trouble, you may want to check this as well.

[IOS_DEV_SETUP](docs/IOS_DEV_SETUP.md)

### 2) Clone and Build

Clone this repo

```
git clone https://github.com/touchlab/KaMPStarter.git
```

Then see the [APP_BUILD](docs/APP_BUILD.md) doc for more build instructions.

## Sanity Check

At this point, you should be able to build Android and iOS apps. **If you cannot build, you need to get help.** This sample app is configured to run out of the box, so if it's not working, you have something wrong with your build setup or config. Please [reach out to us](CONTACT_US.md) or the Slack group mentioned above.

### 3) Walk Through App

Take a walk through the app's code and libraries included. Make changes, recompile. See how it works.

[GENERAL_ARCHITECTURE](docs/GENERAL_ARCHITECTURE.md)

### 4) Background Education

If the app is building, it's a good time to take a break and get some background information.

**KMP Overview (to find)**

**Mobile Dev with KMP (to find)**

There's an important piece of the puzzle that you'll need to understand. Kotlin/Native's state and concurrency model is different than the JVM (which includes Android). In practice, if you're not writing custom concurrency logic, it's pretty simple, *but only if you understand it*. You must learn the basics.

[Practical Kotlin Native Concurrency](https://dev.to/touchlab/practical-kotlin-native-concurrency-ac7)

[Kotlinconf 2019: Kotlin Native Concurrency Explained](https://www.youtube.com/watch?v=oxQ6e1VeH4M)

We cannot stress this enough. If you're going to build anything significant, you need at least a working understanding of the concurrency model.

### 5) To Integrate or Not To Integrate

(Still TODO-ing this part...)

You first need to decide if you're going to clone the app directly or attempt to include shared code into your existing applications. 

For simplicity, it would be better to clone this starter app and work with it directly for your evaluation. If you simply 
clone this app and do not try to integrate it with your team’s production apps, you will be up and running in no time.

However, the goal often is to prove that KMP will work well with your existing apps. Once configured, the process should 
be smooth. However, production app build systems are often quite complex, with a lot of custom code written to support 
them. This situation is exacerbated by the fact that often the engineer(s) evaluating KMP have an Android backgroud and 
very little experience with iOS build tools.

Understand that integrating anything new into a production build system can be tricky. 
