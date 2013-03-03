#! /bin/sh

ant debug

adb uninstall com.example.sindarstudyguide

adb install bin/SindarStudyGuide-debug.apk
