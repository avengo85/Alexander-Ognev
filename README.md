# High Level Test Plan 
## Defining of general moments for testing of Monefy mobile application for Android.



### _Critical Priority tasks:_



*   Checking all the available requirements
*   Defining platforms and operating systems where the application is supposed to be used
*   Correspondence to guidelines
*   Defining the main devices and screen resolutions list for thorough testing
*   Defining emulators for testing


## General checks


### _Critical priority checks:_



*   Main and other screens composition check
*   Behaviour of the screens depending on each other (transitions from the main screens to other ones and then coming back to the main screen and what was changed) - an impact matrix can be created for this and then covered by tests
*   Screen elements, icons and buttons work - where they lead to, what can be done using them


### _Major priority checks:_



*   The application behaviour when some documented and not documented errors occur: e.q. unexpected application stop running, lack of device's memory, sudden interrupting of backup process, etc.
*   The application behaviour when it is unloaded from the device memory or when device was rebooted
*   Pixel perfect correspondence (depending on the requirements)
*   Portrait orientation correctness check (Note: can see no landscape orientation supported for the app in Android)


### _Minor priority checks:_



*   The application behaviour when in-active but staying on the background
*   Tooltips correct displaying


## Navigation


### _Critical priority checks:_



*   Side menus, their root partitions, what and when opens over root partitions
*   The tab bar work, and its partial duplication of side menus functions


### _Major priority checks:_



*   Device's "Back" button correct work in the application
*   Device's "Home" button correct work in the application


## Adding and editing data by a User


### _Critical priority checks:_



*   In-app keyboard work
*   Correct adding and saving new categories, notes and new data, creating data transferrs by a User
*   "Clear" button work when adding new data by User
*   In-app calculator work


### _Major priority checks:_



*   The application behaviour when null data or 0 data were entered by a User
*   Different modes selecting


### _Minor priority checks:_



*   Number of added symbols limitation
*   Different themes selecting


## Lists


### _Critical Priority checks:_



*   Lists and correct sorting and displaying of values inside the lists (e.q. lists of possible languages, currencies, days of week)
*   Lists of transactions correct displaying


## Localization


### _Major Priority checks:_



*   Setting correct language and currency inside the application
*   Correct and consistent selected language and currency displaying through all the application screens


## Permissions


### _Major Priority checks:_



*   Showing system notifications (giving access to photos, media and files on the device when e.q. selecting Export to file)
*   Application behaviour when a User rejects to give access


## Work with external applications/sources/devices


### Authorization and synchronization on Google Play


#### _Critical Priority checks:_



*   Payment process
*   Upgrade of the application
*   Review of the application
*   Lack of internet connection state
*   Weak internet connection state
*   State of switching from one type of internet connection to another
*   Enabling/Disabling Google Analytics


### Authorization and synchronization on Dropbox/Google Drive


#### _Major Priority checks:_



*   Data backup
*   Restoring Data
*   Clearing Data
*   Lack of internet connection state
*   Weak internet connection state
*   State of switching from one type of internet connection to another


### Authorization and synchronization on one/several devices


#### _Major Priority checks:_



*   Data synchronization for one User
*   Data synchronization for several Users
*   Passcode protection, password changing
*   Touch ID protection
*   Face ID protection
*   Lack of internet connection state
*   Weak internet connection state
*   State of switching from one type of internet connection to another


### Correct work and saving data at the moment when some other device applications are triggered


#### _Major Priority checks:_



*   somebody is calling, sending sms to a User, writing/calling them in chats
*   some other applications send notifications
*   alarm is ringing, etc.


#### _Minor Priority checks:_



*   Data export to a file
*   Work with a default browser


#### _Trivial Priority checks:_



*   "Privacy Policy" option re-addressing to [www.monefy.me](http://www.monefy.me)
*   Work with a bluetooth keyboard/other input devices  in case the requirements allow this


## Widgets correct work


### _Major Priority checks:_



*   when the screen is unlocked
*   when the screen is locked


## Installing/Deleting the application


### _Critical Priority check:_



*   Correct installing of the application


### _Major Priority check:_



*   Correct deleting of the application


## Performance testing of application


### _Major Priority checks:_



*   memory consumption of the application
*   battery consumption of the application


### _Minor Priority check:_



*   the application behaviour when the device performance slows down, in case of lack of memory


### Author
 **Alexander Ognev**  <avogneff@gmail.com>