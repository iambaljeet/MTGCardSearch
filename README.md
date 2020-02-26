# Example application

Example application to demonstrate a working example of several concepts. Adding Magic: The Gathering card search to [Magick - The MtG Life Counter](https://play.google.com/store/apps/details?id=com.babblingbrook.lifecounter) that can be found on Google Play.

## Concepts applied

This example is built using:
* androidx libraries, including Constraint Layout, Core KTX libraries, Material Design
* Android Architecture libraries, including Lifecycle, ViewModel, LiveData, Navigation UI, Paging
* Retrofit
* Dagger2
* Coil image loading library
* Kotlin coroutines

* API used for this example is from [Scryfall](http://scryfall.com)

* Uses MVVM architectural pattern with Single Activity / Multiple Fragments.

### To do

Basic structure is built, but still many items to complete before it can be added to the existing application. These include:

* Testing
* Additional error handling on network calls
* Adding additional fields for card objects to display in UI
* Building additional UI elements for additional fields
* Add Room to cache card objects from API
* Transitions from list to detail view

I will update this to do list as more items are discovered, as well as when items are completed.
