# PowerFX

- [PowerFX](#powerfx)
  - [Collections](#collections)
  - [Text](#text)
  - [Other functions](#other-functions)
    - [User function](#user-function)

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

## Other functions

### User function

This function allows to retrieve the current user information:

```C#
User().Email
User().FullName
user().Image
```