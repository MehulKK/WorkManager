# README #
## Schedule tasks with Android Jetpack WorkManager
### Pre requisites
- minimum SDK    : 14
- Android Studio : 3.4 stable
- Dev Language   : Kotlin

##  What is WorkManager?
   One of the Android Architecture component and part of Android Jetpack
   Using WorkManager we can schedule a deferrable and asynchronous task that is expected to run even if the app is exits or device restarts and when the work's constraints are satisfied.


## Key Feature
   - Backwards compatible up to API 14
   - Add work constraints like network availability or charging status
   - Schedule asynchronous one-off or periodic tasks
   - Monitor and manage scheduled tasks
   - Chain tasks together
   - Ensures task execution, even if the app or device restarts
   - Adheres to power-saving features like Doze mode

## WorkRequest can be of two type
   - ### OneTimeWorkRequest
        That means you requesting for non-repetitive work.

   - ### PeriodicWorkRequest
        This class is used for creating a request for repetitive work. This work executes multiple times until it is cancelled, with the first execution happening immediately or as soon as the given Constraints are met.