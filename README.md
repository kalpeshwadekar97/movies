# Movies Android Application
An android app that shows you the list of all the popular movies.

**Features:**
- Popular Movie list.
- Sort movie list by Popularity, Release Date, Ratings, Vote Count.
- Movie Details

**Android Fundamentals & APIs used:**
- Retrofit for network calls and network cache.
- MVVM architecture to create scalable, maintainable and testable modules.
- Paging library from Android Jetpack for infinite scroll on movie list.
- Espresso, JUnit and AndroidX testing libraries for Integration and Unit testing.
- Collapsible toolbar to make smooth animation of the toolbar for better UI-UX.
- Retry option in case of failure of API call.
- MovieDB APIs to fetch the list of popular movies and the movie details.
- Sort By bottom sheet dialog fragment.
- Progressbar for API call status.
- Constraint layout to make sure the consistent layout across various screen size.
- Glide library for image loading and caching.

**Area of improvements & Assumptions:** <br />
(Due to time constraints I am not able to integrate below features which can improve the performance and maintanace of the app further)<br/>
1) I have kept the "image path" at the client side.
- We can fetch the image path from the API on app start 
and can keep it in the Shared Preference(Simple, asynchronous, persistent key value pair local storage system).<br/>
- We will then fetch the "image path" from shared preference on every app start and store it to a global constant to use it across the app for better performance.
- If in case "image path" becomes unavailable in shared preference then we will call the API again for the "image path".
This will ensure the availability and better performance of usage of "Image Path".

2) Movie Details caching:
- We can show the available movie data from the list in the Movie Details activity until successful fetching of the actual data from the API.
- To achieve this we have to send the movie object to the details activity and show the available data, and along with 
this we can call movie details API in the background and can populate the actual data on API response.
