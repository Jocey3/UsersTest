Application with Navigation and Data Management
Task: Create an Android application with a navigation menu containing two items:

Input Form Screen:

Show a screen with a form including text fields (name and description) and an "Add" button.
Save data to a local database on clicking "Add".
List Screen:

Display a list of all entered data with fields for name, description, and word count.
Ensure responsive UI for both portrait and landscape modes.
Data should persist after closing the app.
Clicking a list item should allow editing or deleting the data.
Requirements:

Architecture: Clean Architecture (Data, Domain, Presentation), MVI pattern.
UI: Jetpack Compose, responsive design.
Data Management: Room or SQLDelight, Kotlin Coroutines and Flows.
DI: Koin.
Testing: JUnit.
