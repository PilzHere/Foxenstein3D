#!/bin/bash

# This will first build the game .jar file using Gradle.
# After that it will build executable for Linux.
# At last it will gzip the executable.

set -e

MSG="SCRIPT STATUS:"

SCRIPT_SUCCESS=false

# Message
echo "$MSG Running Linux build script..."
# Move to project root folder.
cd ../../../ || exit_on_error "Could not find project root folder."

# Message
echo "$MSG Building jar file using gradlew..."
# Build game .jar file.
if ./gradlew desktop:dist ; then
    echo "$MSG Building .jar file succeeded."
else
    echo "$MSG Building .jar file failed." EXIT
fi

# Cd to libs:
cd desktop/build/libs || exit_on_error "$MSG Could not find desktop/build/libs folder."

# Message
echo "$MSG Renaming jar file to game title..."
# Rename standard jar file name with title of game.
if mv desktop-1.0.jar UltraNightmare.jar ; then
    echo "$MSG Renaming .jar file succeeded."
else
    echo "$MSG Renaming .jar file failed." EXIT
fi

# Message
echo "$MSG Removing old platform folder if it exists... This MUST be done."
# Remove old platform folder.
if [ -d "UltraNightmare_Linux" ] ; then
    rm -rf UltraNightmare_Linux "$MSG Removing old linux executable folder." || exit_on_error "Failed to remove platform directory!"
else
    echo "$MSG There is no Linux executable folder: Skipping this step."
fi

# Message
echo "$MSG Building Linux executable using Packr..."
# Build linux executable using Packr and its own .json file.
if [ -f "packr-all-3.0.1.jar" ] ; then
    java -jar packr-all-3.0.1.jar buildExecutableLinux64.json || exit_on_error "$MSG Failed to use Packr with Json file!"
else
    echo "$MSG Could not find Packr. This is needed in /libs folder." EXIT
fi

# Message
echo "$MSG Zipping Linux executable folder..."
# tar/gzip the folder containg platform executable
if [ -d "UltraNightmare_Linux" ] ; then
    tar -czvf Ultra_Nightmare_Linux.tar.gz UltraNightmare_Linux || exit_on_error "BUILD STATUS: Failed to zip platform executable folder."
else
    echo "$MSG There is no Linux executable folder to be used for zip." EXIT
fi

SCRIPT_SUCCESS=true;

if [ "$SCRIPT_SUCCESS" = true ] ; then
    echo "$MSG Done!"
else
    echo "$MSG Failed!"
fi
