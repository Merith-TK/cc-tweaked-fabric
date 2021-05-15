
# ![CC: ReTweaked](logo.png)
[![Current build status](https://github.com/SquidDev-CC/CC-Tweaked/workflows/Build/badge.svg)](https://github.com/o-iM-nI/cc-restitched/actions "Current build status") [![Download CC: Restitched  on CurseForge](http://cf.way2muchnoise.eu/title/cc-restitched.svg)](https://www.curseforge.com/minecraft/mc-mods/cc-restitched-updated "Download CC: Restitched on CurseForge")

This is an WIP [fork](https://github.com/Zundrel/cc-tweaked-fabric) of a [fork](https://github.com/ArchivedProjects/cc-tweaked-fabric)  of a [fork/port](https://github.com/mystiacraft/cc-tweaked-fabric) of an [update/port/fork](https://github.com/SquidDev-CC/CC-Tweaked) of [ComputerCraft](https://github.com/dan200/ComputerCraft), adding programmable computers,
turtles and more to Minecraft.

## What?
This is Computercraft for Fabric updated to 1.16x.
This repo is an attempt at parity to the original [CC:Tweaked](https://github.com/SquidDev-CC/CC-Tweaked)


## Contributing
Any contribution is welcome, be that using the mod, reporting bugs or contributing code. In order to start helping
develop CC:R, you'll need to follow these steps:
-**Fork the repository:** Make a github account press the fork button.

-**Edit the code** Edit the code and make your changes.

-**Turn on github actions** Press the 'Enable Workflows' button or set up a new gradle workflow and copy-paste [this](https://github.com/3prm3/cc-retweaked/blob/v1.94.2/.github/workflows/gradle.yml) into your worklow's yml, dowload the artifacts and put in your mods folder and check for bugs.

-**Make a pull request** press the 'New Pull Request' button and set the repository on the left to 3prm3/cc-retweaked and set the repository on the right to your repository.

## Releases
Releases can be found [here on GitHub](https://github.com/Merith-TK/cc-restitched/releases) [or on CurseForge](https://www.curseforge.com/minecraft/mc-mods/cc-restitched-updated)

## Bleeding Edge Builds
Bleeding edge builds can be found [here](https://github.com/Merith-TK/cc-restitched/actions) at github actions to simplify things 

## Community
If you need help getting started with CC: Restitched, want to show off your latest project, or just want to chat about
ComputerCraft, here is the [forum](https://forums.computercraft.cc/) 

## Known Issues
Main Known issue
* Mods that add blocks that can be used as peripherals for CC:T On forge, don't work with CC:R.
	* This is because of the differences between forge and fabric, and that mod devs, to my knowledge have not agreed upon a standard method in which to implement cross compatibility between modloaders,
* Storage Peripherals throw a java "StackOverflowError" when using `pushItems()`, 
    * Work around, you are probably using `pushItems(chest, 1)` or simular. please use `pushItems(chest, 1, nil, 1)`. 

## Known Working mods that add Peripherals
* Please let me know of other mods that work with this one
	* Better End = Chests are added
	* Better Nether = Chests are added too

## [Building from sources](https://github.com/CaffeineMC/sodium-fabric#building-from-sources)
Hover over 'Building from sources'

Description based on https://github.com/SquidDev-CC/CC-Tweaked and https://github.com/Merith-TK/cc-restitched
