# PowerFX

- [Operators](#operators)
- [Collections](#collections)
- [Text](#text)
- [Navigation](#navigation)
  - [Navigate function](#navigate-function)
  - [Transitions](#transitions)
  - [Back function](#back-function)
- [Controls](#controls)
  - [Timer control](#timer-control)
- [Other functions](#other-functions)
  - [User function](#user-function)

## Operators

[Microsoft Learn | PowerFX operators](https://learn.microsoft.com/en-us/power-platform/power-fx/operators)

| Symbol | Description                                         |
|-------:|-----------------------------------------------------|
|      ; | Separate the execution of several PowerFX formulas. |
|      & | Concatenates text strings.                          |

## Collections

PowerFX provides a way to manage a set of items. These are the collections. Functions of collections:

```C#
// Collect adds the listed items to a collection
Collect(MyCollection, {Name: "Alber", Age: 21})
Collect(MyCollection, 
    {Name: "Alberto", Age: 22}, 
    {Name: "Diego", Age: 14}
)

// Clear just removes all the items of the collection
Clear(MyCollection)

// Clear collect, removes all the items of a collection and
// add a new set of items
ClearCollect(MyCollection, {Name: "Alber", Age: 21})
ClearCollect(MyCollection, 
    {Name: "Alberto", Age: 22}, 
    {Name: "Alber", Age: 21}
)
```

Also, the arguments can be introduced in a table structure.

## Text

Is possible to transform text and some imputs with this function:

```C#
// Adds a $ before the price
Text(ThisItem.price, "$ ##.00")
```

Uses:

- Format numbers
- Represent dates
- Show the information in the correct format

## Navigation

### Navigate function

```C#
Navigate(Screen, Transition [, UpdateContextRecord])

/*
 * Screen: name of the screen to show
 *
 * ScreenTransition: Visual transition used to
 * switch between the screens
 *
 * UpdateContextRecord: optional. Contains the name 
 * of at least a column and a value for every 
 * column. Updates the context variables.
*/
```

### Transitions

- ScreenTransition.Cover: the new screen slides in the view, covering the current screen.
- ScreenTransition.Fade: the old screen fades to show the new screen.
- ScreenTransition.None: the old screen disapears rapidly showing the new one.
- ScreenTransition.UnCover: the old screen slides out of the view showing the new one.

### Back function

The back function allows go back to the previous screen.

```C#
Back()
```

## Controls

### Timer control

The timer control allows to execute a formula when elapsed the specified time.

## Other functions

### User function

This function allows to retrieve the current user information:

```C#
User().Email
User().FullName
user().Image
```
