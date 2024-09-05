
# Test Application with Navigation and Data Management


## Screens

- Input Form Screen: Show a screen with a form including text fields (name and description) and an "Add" button. Save data to a local database on clicking "Add".

- List of users screen: Display a list of all entered data with fields for name, description, and word count. Ensure responsive UI for both portrait and landscape modes. Data should persist after closing the app. Clicking a list item should allow editing or deleting the data.

## Requirements

- Project Architecture: Clean Architecture with separation into layers (Data, Domain, Presentation). MVI (Model-View-Intent) design pattern.
- Dependency Injection (DI): Koin
- UI: Compose
- Database: Room
- Multithreading: Coroutines


