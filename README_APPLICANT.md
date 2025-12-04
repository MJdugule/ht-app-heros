# Farms List Feature — Notes for Reviewer

This document summarizes the changes made for the Farms List screen.

## How to run

- Build the project:

```powershell
.\gradlew.bat clean assembleDebug
```

- Install & run on a connected device/emulator:

```powershell
.\gradlew.bat installDebug
```

The app launches `MainActivity` which hosts a `NavHostFragment` and starts at the Farms List screen.

## Where the new screen code lives

- Package: `app/src/main/java/com/hellotractor/android/notes/farms`
  - `FarmsListFragment.kt` — Fragment (XML-based) that renders UI and forwards events
  - `FarmsListViewModel.kt` — ViewModel (StateFlow/SharedFlow), consumes UI events and calls domain use case
  - `FarmsMvi.kt` — State / Event / Action classes (MVI types)
  - `FarmsAdapter.kt` — RecyclerView ListAdapter for `Note` items

- Layouts:
  - `app/src/main/res/layout/fragment_farms_list.xml` — fragment layout (Spinner, ProgressBar, RecyclerView)
  - `app/src/main/res/layout/item_farm.xml` — list item view

- Navigation:
  - `app/src/main/res/navigation/nav_graph.xml` — start destination is `FarmsListFragment`

## Summary of changes

- I Implemented a Farms List screen that uses the existing `GetNotesListUseCase` from the `domain` module to load notes and render them as farm items.
- Added MVI layers (State, Event, Action), a Hilt-enabled `FarmsListViewModel`, RecyclerView `ListAdapter`, fragment layout and nav-graph entry.
- Wired the `NavHostFragment` into `MainActivity` so the Farms List screen is shown at app launch.


Files added/changed (high level):
- app/src/main/java/com/hellotractor/android/notes/farms/*
- app/src/main/res/layout/fragment_farms_list.xml
- app/src/main/res/layout/item_farm.xml
- app/src/main/res/navigation/nav_graph.xml
- app/src/main/res/values/strings.xml (added small strings)

## MVI structure

- ViewModel (`FarmsListViewModel`): receives `FarmsEvent` via `sendEvent`, calls `GetNotesListUseCase`, performs ordering logic, and emits `FarmsState` via `StateFlow` and one-off `FarmsAction` via `SharedFlow`.
- State (`FarmsState`): immutable data class with `isLoading`, `notes: List<Note>`, `selectedOrder: OrderType`, and `errorMessage`.
- Event (`FarmsEvent`): UI intents — `Load`, `Refresh`, `ChangeOrder`.
- Action (`FarmsAction`): one-off side-effects — `ShowError(message)`.
- Fragment (`FarmsListFragment`): subscribes to `state` and `actions`, renders UI, forwards Spinner selection and pull-to-refresh as `FarmsEvent`s.

## Assumptions & notes

- `Note.dateCreated` in the domain model is a `String`. I sort by this field directly; if it's not ISO-8601 or can vary, consider converting to a typed date (Epoch millis or Instant) in the domain layer for reliable sorting.
- To avoid local build configuration issues, I temporarily removed Crashlytics / Google Services plugin usage from the `presentation` module and commented their application in the `app` module in local Gradle files. This was necessary to get a clean local build (the changes are small and documented in the branch). Re-enable Crashlytics in CI or after verifying plugin compatibility with AGP.
- The initial-load delay (600ms) is intentional for UX testing and can be reduced or removed.

## Known limitations / TODOs

- Unit tests: I did not add unit tests yet for `FarmsListViewModel`.
- Error presentation is currently a TextView shown in the fragment


## How to navigate to the Farms List screen

- App launches `MainActivity`. The `NavHostFragment` in `activity_main.xml` has `nav_graph.xml` set with `FarmsListFragment` as the start destination, so the Farms List is shown on app start.

