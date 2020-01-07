# KMP: What and Why?

## What is KMP (Kotlin Multiplatform)?

Kotlin is generally seen as a replacement JVM language, and by many as an “Android thing”. However, JetBrains and the 
Kotlin team have much bigger goals in mind. The ultimate goal of “Kotlin” is a portable platform suitable for any project. 
You can transfer your skills and code to any task at hand.

> To see more about Kotlin's Multiplatform vision, watch the [Kotlinconf Keynote](https://youtu.be/0xKTM0A8gdI)

Kotlin can output JVM bytecode, Javascript, and an array of LLVM-based native executable code. Describing the entirety 
of KMP would take some time, but the KaMP Kit is focused on native mobile development, so we’ll speak to that specifiically.
KMP enables optional shared architecture and logic, that can be used in both Android and iOS. Kotlin is already the default 
language for Android, which means unlike all other “cross platform” options, it is fully “native” to the platform (and, 
really, any JVM environment).
On iOS, the Kotlin Native compiler generates an Xcode Framework that you can include into Xcode and call from Swift or 
Objective-C. Using [a 3rd party plugin](https://github.com/touchlab/xcode-kotlin) (*cough* by Touchlab *cough*) you can debug Kotlin directly in Xcode. iOS developers (soon to be “mobile developers”) can stick to the tools they currently use while learning Kotlin. 
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
Over the past 2 years we’ve constructed a short definition of what we think separates KMP from other options:

> optional, natively-integrated, open-source, code sharing platform, based on the popular, modern language Kotlin. 
> facilitates non-ui logic availability on many platforms (and Jetbrains)

Optional: KMP interops easily and directly with the native platform, and is designed to be used seamlessly with existing 
code. That means you can start small with code sharing and increase as time goes on. You do not need big, risky rewrites.

Natively-integrated: On the JVM Kotlin makes JVM bytecode. In JS, Kotlin outputs JS. On iOS you get an Xcode framework. 
Kotlin’s Interop story is unique and a distinct advantage.

* Open Source: It would be difficult to not be open source in 2020, but some tools are not.
* Code sharing: Not a monolithic singular “app”. Kotlin’s focus is code sharing (see optional above).
* Popular: Big, engaged community. Very active library development and support. Training, recruiting, etc. This matters.
* Modern: Kotlin as a platform is being built to last by intentionally not getting old.
* Non-UI: Picking a big, monolithic tech stack for mobile is risky. Shared UI doesn’t have a great history, but shared logic is the history of computers. Build something that is incremental and “plays nice” with the host system is much harder and will take longer, but is ultimately the successful strategy. There will be “shared UI” options for KMP. The good news is they’ll be optional.
* Jetbrains: Jetbrains has built an amazing business on building the best IDE’s. They also make Kotlin the language. This is a unique combination. They are self-funded, as in there is no VC or public shareholder pressure to have 
  immediate ROI. Jetbrains is here to stay, and they are committed to Kotlin. The tooling around KMP and Native is evolving, 
  but it’s safe to assume Kotlin as a platform will have the best tooling in the industry.

We, Touchlab, have a clear perspective on the future. That is, the future is very hard to predict. Kotlin as a platform 
is a low-risk choice because of the reasons mentioned above. We prefer less risky choices because being “right” about 
the future isn’t that important if you can reduce the cost of being “wrong”.
