#! /bin/sh

ant debug

adb uninstall com.learnsindar

adb install bin/learnsindar-debug.apk
