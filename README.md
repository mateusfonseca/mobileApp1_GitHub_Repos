# Android App: GitHub Repos

**Dorset College Dublin**  
**BSc in Science in Computing & Multimedia**  
**Mobile Applications 1 - BSC20921**  
**Stage 2, Semester 2**  
**Continuous Assessment 3**

**Lecturer name:** Saravanabalagi Ramachandran  
**Lecturer email:** saravanabalagi.ramachandran@faculty.dorset-college.ie

**Student Name:** Mateus Fonseca Campos  
**Student Number:** 24088  
**Student Email:** 24088@student.dorset-college.ie

**Submission date:** 4 April 2022

This repository contains a "GitHub Repos" Android app developed for my CA3 at Dorset College BSc in Computing, Year 2, Semester 2.

## Part 1: Requirements Checklist

- [x] 1. Get username and display corresponding user details and repositories as shown in the screenshot above
    - [x] 1.1. Show the error message with a big warning icon e.g. if not username is not found
    - [x] 1.2. If user is found, show information for user and for each repo as shown in the screenshot
    - [x] 1.3. Test and record screencast for usernames torvalds, Rich-Harris, JakeWharton, and newtoneinstein. (You can show yours too!)
- [x] 2. Extend the same functionality for organizations:
    - [x] 2.1. Add a radio button to select username or orgname
    - [x] 2.2. Show information about the organization and repositories
    - [x] 2.3. Test and record screencast for orgnames microsoft, facebook, uber, and ultrabot.
- [x] 3. Bonus Points:
    - [x] 3.1. Design Similarity E.g. colors, fonts, dark mode, etc.
    - [x] 3.2. Use short number format e.g. 42200 as 42.2k

## Part 2: Extra features implemented

- **1. View biding**
- **2. Packages**
  - **2.1. adapter.RepoAdapter**  
    This class implements an adapter for a recycler view in the layout. It loads repositories into a list. It is a recycler view of card views defined in a separate layout file.
  - **2.2. lib.NumberAbbreviator**  
    This interface implements a function *abbreviate()* that takes in a whole number and abbreviates it to a specific format, for example, 1500 to 1.5k.
  - **2.3. model**  
    - **2.3.1. Account**  
      This class implements the blueprint for the basic Account objects that hold data for either GitHub user accounts or organization accounts.
    - **2.3.2. Repo**  
      This class implements the blueprint for the basic Repo objects used to feed the recycler views.
  - **2.4. network.RepoApi**  
    This class implements an API that establishes communication with external datesources, fetches relevant content and assigns them to the datamodels defined in the Account and Repo classes.
- **3. Layouts**
  - **3.1. error_message_template**  
    Template layout file for error messages displayed when there is either a connection error or the account being searched is non-existent.
  - **3.2. repos_recycler_template**  
    Template layout file for the RecyclerView that loads repositories into CardViews in a vertical list.

## Part 3: Report

Expanding on the topic of my [previous project](https://github.com/mateusfonseca/mobileApp1_Movie_Booking), this assessment proved to be considerably challenging. Handling multiple API calls to different end-points and using their responses to render UI elements while switching back and forth between threads in the background and the main thread in the foreground is a difficult task. Kotlin offers Coroutines as a lighter way of handling these background tasks, but, as it turns out, this approach is not altogether compatible with OkHttp which was designed with Java in mind.

The main obstacle I encountered while working on this application was how to have the main thread wait for the asynchronous API calls and, only after receiving them, building the views. Admittedly, I ended up using a solution that is really more of a work-around than what I really wanted. I set up a timer with *Timer().schedule()* to halt the main thread for an arbitrary amount. The problem with this solution is that even if the API calls return earlier, the main thread will keep waiting for the set time and if, by any chance, any of the calls takes longer than expected to return, the UI will not be rendered appropriately.

The ideal solution seems to be around the concepts of [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), but unfortunately, I did not have enough time to study the subject before this assignment's submission deadline.

## Part 4: References

Conceptually, every line of code in this project was written based on official documentation:

- **Android Developers Docs:** https://developer.android.com/docs
- **Kotlin Docs:** https://kotlinlang.org/docs/home.html
- **Material Design:** https://material.io/

Visits to our most beloved **StackOverflow** (https://stackoverflow.com/) certainly happened, for insight and understanding.

This app fetches its data from two different API's:

- **GitHub Official API:** (https://api.github.com/), for all data, except for
- **Heroku GitHub Language Deploy API:** (https://github-lang-deploy.herokuapp.com/), for the color hexcodes of the programming languages' circular tags used on GitHub. 

## Part 5: Copyright Disclaimer

This project may feature content that is copyright protected. Please, keep in mind this is an student's project and has no commercial purpose whatsoever. Having said that, if you are the owner of any content featured here and would like for it to be removed, please, contact me and I will do so promptly.

Thank you very much,  
Mateus Campos.