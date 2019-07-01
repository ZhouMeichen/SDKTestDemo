# SDK Test Demo
## File Structure

Assets: Provision and Native resources

Libs: libaiengine.so (voice evaluation SDK version)

Src: Source code

Res/values: configure files

## How to operate

![Image of homescreen](https://github.com/ZhouMeichen/SDKTestDemo/blob/master/homescreen.png)

As shown in the figure, there are several configurable parameters. "CloudServer" will only be displayed when source is selected as "cloud". "Realtime" will only be displayed when "en.pred.exam" is selected in "coreType".

Steps:

1) Initially, only the "Init" button can be clicked. After clicking "Init" button and the engine is initiated sucessfully, the "Record", "Replay" and "Delete" buttons are clickable.

2) When "Record" button is clicked, the engine starts and it starts recording. The audio will be sent to the kernel/core while recording. The "Record" button can be stopped automatically or manually.

3) After stopping recording, the result will be displayed on the "Result" page.

4) After viewing the results, "Replay" button can be clicked to play back the audio on the "Workbench" page.

## How to replace the SDK

Replace the libaiengine.so file in the libs directory, where the file name can be changed to "libaiengine".


## How to replace native resources

Replace the resources.zip package in the assets directory, where the package name should be "resources". It is recommended to include only the resources to be tested because the limited memory of the mobile device may cause the program to crash.
