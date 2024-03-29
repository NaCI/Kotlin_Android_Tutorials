# NOTES ABOUT LECTURE

## Structured Concurrency

It is an ability to "pause" code execution and "wait" for all concurrent flows which can be traced
back to a specific "ancestor" to complete

Kotlin coroutines provide advanced support for Structured Concurrency in production code and unit
tests

> **BEST PRACTISE :** Suspending function should not assume anything about dispatchers on which they are called. Always specify context inside suspend function

## Design with Coroutines

Presentation Layer Logic:

- UI logic

- Android controllers (Activities, Fragments)

- Standalone controllers (controllers, presenters, viewmodels, etc.)

**There should be no multithreading in these entities** Presentation layer should use UI thread
exclusively! There should be only UI thread related jobs in these entities. Instead use UseCase's(
interactors) for operations (Background thread operations) which needs to run on other threads

![design course](./art/design_course.png)

## Dispatchers

Dispatchers are responsible for “dispatching” coroutines to the underlying threads

List of Dispatcher in Android:

- Dispatchers.Main

- Dispatchers.Main.immediate

- Dispatchers.Default

- Dispatchers.IO

- Dispatchers.unconfined

What’s the added benefit of “immediate” Main Dispatcher?

### Dispatchers.Main

Behaves like Handler( Looper.getMainLooper ()).
(It will be added to main thread queue and will be called when in turn)

### Dispatchers.Main.immediate

Behaves like Activity.runOnUiThread (it will be run immediately)

> **Best Practise : ** Use Dispatchers.Main.immediate to execute code on UI thread, unless you have a reason not to

### Dispatchers.Default

Thread pool with maxThreads = max(2, NUM_OF_CPU_CORES)

Used for computation intensive tasks

### Dispatchers.IO:

Thread pool with maxThreads = max(64, NUM_OF_CPU_CORES)

Maximum number of threads can be further increased by adjusting system proprties

Used for IO tasks (tasks which are mostly “waiting”)

> Use Dispatchers.IO for time consuming tasks like network request, db operations, writing or reading from file etc.

## Coroutines Cancellation

Two methods used to cancel coroutine

- coroutineScope.coroutineContext.cancelChildren

- coroutineScope.cancel

> cancelled: CoroutineScope becomes “dead” and can’t be used to launch new coroutines (silently discards launch attempts)

Whenever a coroutine cancelled, a CancellationException thrown - as long as withContext (or delay)
function is called inside the suspending function.

**E.g**:

```kotlin
suspend fun executeBenchmark(benchmarkDurationSeconds: Int) = withContext(Dispatchers.Default) {
    // code goes here
}
```

We use isActive or coroutineContext.isActive to check if coroutine is not cancelled yet inside
suspending function.

*ensureActive()* : Check if coroutine still active otherwise throws CancellationException

### Non-cancellable Jobs

We use non-cancellable jobs to make sure all tasks completed -even if it is cancelled in the middle
of execution- in suspending function.

Example: updating db after network response

Usage:

```kotlin
withContext(NonCancellable)
// --------- or -----------
withContext(Dispatchers.Default + NonCancellable)
```

### Coroutine Mechanics

Main building blocks of coroutines:

- CoroutineScope

- CoroutineContext

- CoroutineDispatcher

- Job

CoroutineScope needs context in order to initialize. And that context arguments could be : Job -
CoroutineName - Dispatcher

```kotlin
CoroutineScope(Job() + CoroutineName("outer scope") + Dispatchers.IO)
```

Same rules available for coroutineScope.launch method

> Launching a new coroutine is concurrent process, unlike withContext block which is stuctured concurrency works. withContext simply uses to change context (thread)

Whenever coroutine launch method calls new CoroutineScope and Job creates.

> *job.join()* method uses on unit test inorder to wait for corotine job end

There is a hierarchy between the coroutine jobs which is called inside other. Check out :
CoroutinesMechanicsExplorationsTest.coroutineScope_jobsHierarchy()

Until there is no specific declaration made, coroutines uses outer scopes data.

> *withContext* block checks for cancellation signal on the beginning and the end of the code lines

> **IMPORTANT NOTE: **NonCancellable is designed for withContext exclusively!

Nested WithContext Examination

![nestedWithContext](./art/nestedWithContext.png)

Nested Coroutine Examination

![nestedCoroutine](./art/nestedCoroutine.png)

Nested Coroutine On Standalone Scope Examination

![nestedCoroutineOnStandaloneScope](./art/nestedCoroutineOnStandaloneScope.png)

### Parallel Decomposition

Taking the sequential steps and execute them in parallel - Exercise 8

It speeds up the algorithm

### Shared Mutable State

Changing state of shared object from many thread at the same time which is not thread safe.
That causes to set incorrect values to that object. (Atomic classes could be answer to that problem
but it's not correct always) - IncorrectParallelDecompositionDemoTest | AsyncCoroutineBuilderDemoTest

**E.g**: `totalIterations` variable in "atomicityProblemDemo()" function is Shared Mutable State. That's because it
defined outside thread but assign value to it in many threads. It is unsafe because some assign operations
may be misbehave or skip whenever two threads needs to access it at the same time 

In order to solve atomicity problem we need to get rid of shared mutable states. We can return value
from coroutine job with `async()` and `awaitAll()` methods - Exercise9

> IMPORTANT WARNING : Do not access shared mutable state from concurrent coroutines

### Coroutines Exception Handling

Exception handling is a bit different than general exception handling in java. 
Whenever an exception happens inside coroutineScope we can't handle it outside of the scope.

**E.g**: lets assume that loginUseCase is suspend function and throws exception. In that scenario
below code will not be able to catch the exception

```kotlin
try {
    coroutineScope.launch {
        try {
            val result = loginUseCase.logIn(getUsername(), getPassword())
            when (result) {
                is Result.Success -> onUserLoggedIn(result.user)
                is Result.InvalidCredentials -> onInvalidCredentials()
                is Result.GeneralError -> onGeneralError()
            }
        } finally {
            refreshUiState()
        }
    }
} catch (e: Exception) {
    // handle exception
}
```

In order to handle coroutine exceptions use **CoroutineExceptionHandler** class. 
Either add it to scope or coroutine level - Check **CancellationOnExceptionDemoTest**

**E.g**:

```kotlin
private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable -> 
    onException(throwable)
}
private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate + coroutineExceptionHandler)
// OR 
coroutineScope.launch(coroutineExceptionHandler) {
    // do stuff
}
```

> All potentially thrown exceptions should be documented!!

> BEST WAY TO DEAL WITH EXCEPTIONS on coroutines is to wrap the method which throws exception on UseCase or Repository layer.

 
## REFERENCES

https://www.udemy.com/course/kotlin-coroutines-in-android