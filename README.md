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

You have likely been skimming the last section, *but please read this carefully*. Because of the current KMP
 documentation situation, you must be very careful about which blog posts and samples you look at. Until you have something running, it is best to stick to just this kit, or links from developers familiar with the technology. *Google results for KMP will be conflicting and likely out of date. Be careful!*.

**Stick to this kit until you have something running. If you start browsing other tutorials or docs while setting
 things 
up, you can easily get confused.**

Config settings and patterns have changed over the past year, so various blog posts and versions of documentation will
 be conflicting. Browsing other samples and tutorials will likely extend your startup time.
 
**TL;DR ignore the rest of the world until your project builds.**

# The Starter App

The starter app is configured with some common libraries that many native mobile apps might use. We’ve also implemented 
a very simple feature to demonstrate the libraries. Currently we are including:

* SQLDelight - Sqlite interaction library
* Ktor - Networking
* Coroutines - This includes the newer native mutlithreaded preview version
* Stately - Concurrency support library
* Multiplatform-Settings - Simple data storage
* Koin - Dependency Injection

## Getting Started

You first need to decide if you're going to clone the app directly or attempt to include shared code into your existing applications. 

For simplicity, it would be better to clone this starter app and work with it directly for your evaluation. If you simply 
clone this app and do not try to integrate it with your team’s production apps, you will be up and running in no time.

However, the goal often is to prove that KMP will work well with your existing apps. Once configured, the process should 
be smooth. However, production app build systems are often quite complex, with a lot of custom code written to support 
them. This situation is exacerbated by the fact that often the engineer(s) evaluating KMP have an Android backgroud and 
very little experience with iOS build tools.

Understand that integrating anything new into a production build system can be tricky. 
