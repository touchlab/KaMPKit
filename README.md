# KaMP Kit

This github repo is the "KaMP Kit". The goal is to facilitate your evaluation of Kotlin Multiplatform. Our current focus in on native mobile specifically, but will expand to web, backend, and desktop platforms if useful in the future.

## TL;DR

If you are ready to start evaluating, you can skip the next few sections. Pick back up with the [Very Very Important Message](#very-very-important-message), and follow the roadmap from there.

## What is KMP (Kotlin Multiplatform)?

Kotlin is generally seen as a replacement JVM language, and by many as an “Android thing”. However, JetBrains and the 
Kotlin team have much bigger goals in mind. The ultimate goal of “Kotlin” is a portable platform suitable for any project. 
You can transfer your skills and code to any task at hand.

> To see more about Kotlin's Multiplatform vision, watch the [Kotlinconf Keynote](https://youtu.be/0xKTM0A8gdI)

Kotlin can output JVM bytecode, Javascript, and an array of LLVM-based native executable code. Describing the entiretly 
of KMP would take some time, but the KaMP KIt is focused on native mobile development, so we’ll speak to that specifiically.
KMP enables optional shared architecture and logic, that can be used in both Android and iOS. Kotlin is already the default 
language for Android, which means unlike all other “cross platform” options, it is fully “native” to the platform (and, 
really, any JVM environment).
On iOS, the Kotlin Native compiler generates an Xcode Framework that you can include into Xcode and call from Swift or 
Objective-C. Using a 3rd party plugin (*cough* by Touchlab *cough*) you can debug Kotlin directly in Xcode. iOS 
developers (soon to be “mobile developers”) can stick to the tools they currently use while learning Kotlin. 
Integrating Kotlin is not an abrupt and dramatic (ie RISKY) change to your team’s development process.

## What is this Kit?

KMP is new tech, supporting many features and platforms, and has had rapid development over the past 2-3 years. As a 
result, the documentation ecosystem right now can be difficult to navigate. The official Jetbrains docs cover a wide 
range of options but can be difficult to navigate. That situation is being addressed, at the same time that the platform 
itself is stabilizing. The documentation out on the web is often more focused on mobile specifically, but is all over 
the place. Most of it is bad, frankly. Usually because it is outdated.

We have talked to many teams that have evaluated KMP. Like other new tech, the evaluation is generally one or a small 
number of developers building a prototype within a fixed time frame. Often referred to as a “hack week”, if you even get 
a week. Because of the KMP documentation situation, we commonly hear that it can take several days to get past basic 
setup. Often that will mean abandoning the project, but even if the team continues the evaluation, the impression is 
that KMP isn’t “ready”.

We have directly helped some teams avoid that initial setup phase by being a documentation guide and providing basic 
support. These teams have a much different experience with the tech.

The goal of this “Kit” is to get your team to avoid “setup hell” and have you ready to go before lunch on day 1. 
As we progress into 2020 and the documentation situation improves, this kit will be less necessary, but right now we 
feel this kind of starting point is critical for success.

Specifically this “Kit” includes introductory info, which you are reading currently, a pre-configured project, necessary 
docs for platform information, contact and community info for support, and some “soft skills” docs.

The project is configured with libraries generally useful for native mobile development, and some very basic examples 
of how to use them in a mobile context. We will likely add additional projects as we get community feedback.

There are aspects of Kotlin/Native that will be new to developers coming from either an Android or iOS background. The 
most obvious is the concurrency model, which you’ll need to understand. These docs will provide the MVU, Minimum Viable 
Understanding, to be productive, with links to deeper dives.

A little bit of discussion and feedback can go a long way. There are a few options for community support.
The “soft skills” info is focused around discussing KMP to your team and management. Just because it works doesn’t mean 
everybody else will like it. This section will also evolve over time as more of the common concerns and pushback points 
are addressed.

## Why KMP?

The case for KMP is a longer discussion. I discuss it at some length in various talks (https://vimeo.com/371428809). 
Over the past 2 years I’ve constructed a short definition of what we think separates KMP from other options:

> optional, natively-integrated, open-source, code sharing platform, based on the popular, modern language kotlin. 
facilitates non-ui logic availability on many platforms (and Jetbrains)

Optional: KMP interops easily and directly with the native platform, and is designed to be used seamlessly with existing 
code. That means you can start small with code sharing and increase as time goes on. You do not need big, risky rewrites.

Natively-integrated: On the JVM Kotlin makes JVM bytecode. In JS, Kotlin outputs JS. On iOS you get an Xcode framework. 
Kotlin’s Interop story is unique and a distinct advantage.

* Open Source: It would be difficult to not be open source in 2020, but some tools are not.
* Code sharing: Not a monolithic singular “app”. Kotlin’s focus is code sharing (see optional above).
* Popular: Big, engaged community. Very active library development and support. Training, recruiting, etc. This matters.
* Modern: Kotlin as a platform is being built to last by intentionally not getting old.
* Non-UI: Picking a big, monolithic tech stack for mobile is risky. Shared UI doesn’t have a great history, but shared
 logic is the history of computers. Build something that is incremental and “plays nice” with the host system is much harder and will take longer, but is ultimately the successful strategy. There will be “shared UI” options for KMP. The good news is they’ll be optional.
* Jetbrains: Jetbrains has built an amazing business on building the best IDE’s. They also make Kotlin the language
. This is a unique combination. They are self-funded, as in there is no VC or public shareholder pressure to have 
immediate ROI. Jetbrains is here to stay, and they are committed to Kotlin. The tooling around KMP and Native is evolving, 
but it’s safe to assume Kotlin as a platform will have the best tooling in the industry.

We, Touchlab, have a clear perspective on the future. That is, the future is very hard to predict. Kotlin as a platform 
is a low-risk choice because of the reasons mentioned above. We prefer less risky choices because being “right” about 
the future isn’t that important if you can reduce the cost of being “wrong”.

# Very, Very Important Message!

You may have been skimming the intro sections, *but please read this part carefully*. Because of the current KMP
 documentation situation, you must be very careful about which blog posts and samples you look at. Until you have something compiling and running, it is best to stick to just this kit, or links from developers familiar with the technology. *Google results for KMP will be conflicting and likely out of date. Be careful!*.
 
Again... 

**Stick to this kit until you have something running. It is easy to get confused.**

Config settings and patterns have changed over the past year, so various blog posts and versions of documentation will
 be conflicting. Browsing other samples and tutorials will likely extend your startup time.
 
# Getting Help

You should create an account in the [Kotlin Slack](http://slack.kotlinlang.org/?_ga=2.90769053.684071413.1577996668-1257544081.1543638011). Look for the `multiplatform` and `kotlin-native` channels, and ping me directly (Kevin Galligan) if needed.

# The Starter App

The central part of the "Kit" is the starter app. It includes a set of libraries that we use in our apps that provide for much of the architectural needs of a native mobile application. We've also included a simple set of features you can reference and build off of.

The app gets dog breed info from an api and allows you to store it locally in a sqlite database. You can read more about it's general architecture and library usage [here](docs/APP_BUILD.md).

# Getting Started

The most important first step is to get the starter app building. From there, you can make decisions about integrating KMP into other projects, but nothing will work if you can't get the basic app to build.

## 1 Dev Environment and Build Setup

**iOS Developers!!!** If you are not familiar with building Android apps, there is some build environment you'll need to configure first. For Android developers, if you run into trouble, you may want to check this as well.

[IOS_DEV_SETUP](IOS_DEV_SETUP.md)

## 2 Clone and Build

Clone this repo

```
git clone https://github.com/touchlab/KaMPStarter.git
```

Then see the [APP_BUILD](APP_BUILD.md) doc for more build instructions.

## Sanity Check

At this point, you should be able to build Android and iOS apps. If you cannot build, you need to get help. This sample app is configured to run out of the box, so if it's not working, you have something wrong with your build setup or config. Please reach out to us or the Slack grould mentioned above.

## 3 Walk Through App

Take a walk through the app's code and libraries included. Make changes, recompile. See how it works.

[GENERAL_ARCHITECTURE](GENERAL_ARCHITECTURE.md)

## 4 Background Education

If the app is building, it's a good time to take a break and get some background information.

**KMP Overview (to find)**

**Mobile Dev with KMP (to find)**

There's an important piece of the puzzle that you'll need to understand. Kotlin/Native's state and concurrency model is different than the JVM (which includes Android). In practice, if you're not writing custom concurrency logic, it's pretty simple, *but only if you understand it*. You must learn the basics.

[Practical Kotlin Native Concurrency](https://dev.to/touchlab/practical-kotlin-native-concurrency-ac7)

We cannot stress this enough. If you're going to build anything significant, you need at least a working understanding of the concurrency model. (I would point out, same is true for new Android developers, so...)

## 5 To Integrate or Not To Integrate



You first need to decide if you're going to clone the app directly or attempt to include shared code into your existing applications. 

For simplicity, it would be better to clone this starter app and work with it directly for your evaluation. If you simply 
clone this app and do not try to integrate it with your team’s production apps, you will be up and running in no time.

However, the goal often is to prove that KMP will work well with your existing apps. Once configured, the process should 
be smooth. However, production app build systems are often quite complex, with a lot of custom code written to support 
them. This situation is exacerbated by the fact that often the engineer(s) evaluating KMP have an Android backgroud and 
very little experience with iOS build tools.

Understand that integrating anything new into a production build system can be tricky. 
