# NOTES ABOUT LECTURE

## Structured Concurrency

It is an ability to "pause" code execution and "wait" for all concurrent flows which can be traced
back to a specific "ancestor" to complete

Kotlin coroutines provide advanced support for Structured Concurrency in production code and
unit tests

> **BEST PRACTISE :** Suspending function should not assume anything about dispatchers on which
they are called. Always specify context inside suspend function