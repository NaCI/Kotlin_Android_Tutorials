# NOTES ABOUT WORK MANAGER

## What is WorkManager

WorkManager is part of Android Jetpack and an Architecture Component for background work that 
needs a combination of opportunistic and guaranteed execution. Opportunistic execution means 
that WorkManager will do your background work as soon as it can. Guaranteed execution means 
that WorkManager will take care of the logic to start your work under a variety of situations, 
even if you navigate away from your app.

WorkManager is an incredibly flexible library that has many additional benefits. These include:

- Support for both asynchronous one-off and periodic tasks
- Support for constraints such as network conditions, storage space, and charging status
- Chaining of complex work requests, including running work in parallel
- Output from one work request used as input for the next
- Handling API level compatibility back to API level 14 (see note)
- Working with or without Google Play services
- Following system health best practices
- LiveData support to easily display work request state in UI

## When to use WorkManager

The WorkManager library is a good choice for tasks that are useful to complete, even if the user navigates away from the particular screen or your app.

Some examples of tasks that are a good use of WorkManager:

- Uploading logs
- Applying filters to images and saving the image
- Periodically syncing local data with the network

## WorkManager Basics

- **Worker**: This is where you put the code for the actual work you want to perform in the background. 
  You'll extend this class and override the doWork() method.
  
- **WorkRequest**: This represents a request to do some work. You'll pass in your Worker as part 
  of creating your WorkRequest. When making the WorkRequest you can also specify things like 
  Constraints on when the Worker should run.
  Input and output is passed in and out via **Data** objects.
  Data objects are lightweight containers for key/value pairs.
  They are meant to store a small amount of data that might pass into and out from WorkRequests.

- **WorkManager**: This class actually schedules your WorkRequest and makes it run. 
  It schedules WorkRequests in a way that spreads out the load on system resources, 
  while honoring the constraints you specify.
  
## Getting WorkInfo

You can get the status of any **WorkRequest** by getting a **LiveData** that holds a **WorkInfo** object.
**WorkInfo** is an object that contains details about the current state of a **WorkRequest**, including:

- Whether the work is **BLOCKED**, **CANCELLED**, **ENQUEUED**, **FAILED**, **RUNNING** or **SUCCEEDED**.
- If the **WorkRequest** is finished, any output data from the work.

> **WorkRequest**s are cancellable

## Work Constraints

You can define constraints for any work to begin. 
Work is going to start whenever conditions are met even though the app is killed

List of constraints: **NetworkType**, **BatteryNotLow**, **RequiresCharging**, **DeviceIdle**, **StorageNotLow**
  
## REFERENCES

https://developer.android.com/codelabs/android-workmanager
