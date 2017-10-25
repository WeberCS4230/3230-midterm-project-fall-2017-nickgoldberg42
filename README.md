# README #

### CS 3230 Midterm Base Project ###

* Includes the required library

### How do I get set up? ###

* Clone the repository then open Eclipse
* File -> New Project -> Java Project
* Project Name = whatever directory you've downloaded the repository into, `cs_3230_midterm` by default
    * Note that if you have the default name (cs-4230-midterm), it will cause problems with the build
    * If you have already tried to create the project once and are getting the error 'A project already exists on the file system at this location:', you must delete the `.project` file and the `.settings` folder in your project directory in order to continue
    * It's probably easiest to just rename it "midterm"
* Configuration is simple
    * In Eclipse, simply drag and drop the `game_contract.jar` file into your main directory
    * Right-click and select "Add to Build Path"
* No automated tests exist for this midterm

### Contribution guidelines ###

* Make sure you are cleaning up any errors or warnings as you go - when I inspect the code, warnings left in the code will be docked points, so keep your code clean
    * The only exception is that _one_ method can have an `unchecked` warning, as I will assume that method is the one you are currently working on