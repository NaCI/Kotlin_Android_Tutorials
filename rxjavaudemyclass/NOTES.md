# SUMMARY

Data strems can be used for;
- Click events
- Network calls
- Data storage
- Variable changes
- Errors

Modules run on their own threads, executing multiple code blocks at the same time.

## What is Reactive Programming

In computing, reactive programming is a declarative programming paradigm concerned with data streams and the propagation of change.

With this paradigm it is possible to express static (e.g., arrays) or dynamic (e.g., event emitters) data streams with ease, and also communicate that an inferred dependency within the associated execution model exists, which facilitates the automatic propagation of the changed data flow.

In reactive programming, the consumer code blocks react to the data as it comes in.

## What is ReactiveX

Provides implementation on reactive programmig for different programming languages.

Word mean : Reactive Extensions

ReactiveX is a combination of the best ideas from the Observer pattern, the Iterator pattern, and functional programming

RxJava is composable, Rx operators can be combined to produce more complicated operations

Error handling becomes much easier with RxJava. You don’t need to worry about adding try catch blocks everywhere.

In mobile applications, we cannot control the app lifecycle. Sometimes we need to terminate current processes rapidly to response to a app lifecycle change. RxJava provide simple yet profound mechanism to terminate background processes rapidly.



## RxJava

RxJava makes multi-threading super easy. Using imperative approach, moving a piece of code to background thread is hard work. However in RxJava, we could easily define what thread each part of the chain would be in

RxJava is composable, Rx operators can be combined to produce more complicated operations.

Rx operators can transform one type of data to another, reducing, mapping or expanding streams as needed.

## General Structure

### Observables

Observables observe data streams and emit them to subscribed Observers

### Observers

Observers consume data emitted by the Observables.

One Observable can have many observers. An observable emit data, if there is at least one observer subscribed for the data. If there is no subscription observable will not emit data.

Main observer methods(there are other methods too)

- onNext() :- Each time an Observable emits data it  calls to Observer's  onNext() method  passing that data.

- onError() :- If any error occur Observable calls to Observer's onError() method.

- onComplete() :- Observable invokes Observer's onComplete() method, when the data emission is over.

### Schedulers

Use to perform operations of Observable on different threads(multi-threading)

#### Schedulers.io()

This can have a limitless thread pool. Used for non CPU intensive tasks. Such as database interactions, performing network  communications and interactions with the file system.

#### AndroidSchedulers.mainThread()

This is the main thread or the UI thread. This is where user interactions happen

#### Schedulers.newThread()

This scheduler creates a new thread for each unit of work scheduled.

#### Schedulers.single()

This scheduler has a single thread executing tasks one after another following the given order.

#### Schedulers.trampoline()

This scheduler executes tasks following first in, first out basics.We use this when implementing recurring tasks.

#### Schedulers.from(Executor executor)

This creates and returns a custom scheduler backed by the specified executor.

### Operators

Use to Modify data which emits from observable

### Disposables

In mobile applications we cannot control the app life-cycle. Let’s say in an app you created you have written code to run a network call to a REST API and update the view accordingly. If a user initiate a view but decide to go back before the completion of the network call, What will happen? The activity or fragment will be destroyed. But the observer subscription will be there. When observer trying to update the User Interface, in this scenario as the view already destroyed,  it can cause a memory leak. And your app will freeze or crash as a result.

In order to prevent that problem we use disposables and dispose all thread operations on screen lifecycle ends

![General Structure](art/rxjava_structure.png)

### Disposable Observers

DisposableObserver class implements both Observer and Disposable interfaces. DisposableObserver is much efficient than Observer if you have more than one observers in the activity or fragment.

### Composite Disposables

In one class you can have more than one observers . So you will have so many observers to dispose. When we have more than one observers we use CompositeDisposable. By that way we can dispose all observers in one place with "clear" method on "CompositeDisposable".

## REFERENCES

- https://www.udemy.com/course/rxjavarxandroid-bootcamp-reactivex-for-android-developers