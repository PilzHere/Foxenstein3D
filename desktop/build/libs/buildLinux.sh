#!/bin/bash

# This will first build the game .jar file using Gradle.
# After that it will build executable for Linux.
# At last it will gzip the executable.

old_jar_file="desktop-1.0.jar"
new_jar_file="UltraNightmare.jar"
executable_folder="UltraNightmare_Linux"
zip_file="Ultra_Nightmare_Linux.tar.gz"
packr="packr-all-3.0.1.jar"
json_file="buildExecutableLinux64.json"

set -e
msg="SCRIPT STATUS:"
script_status=false

# Message
echo "$msg Running Linux build script..."
# Move to project root folder.
cd ../../../ || exit_on_error "$msg Could not find project root folder."

# Message
echo "$msg Building jar file using gradlew..."
# Build game .jar file.
if ./gradlew desktop:dist ; then
    echo "$msg Building .jar file succeeded."
else
    echo "$msg Building .jar file failed." EXIT
fi

# Cd to libs:
cd desktop/build/libs || exit_on_error "$msg Could not find desktop/build/libs folder."

# Message
echo "$msg Renaming jar file to game title..."
# Rename standard jar file name with title of game.
if mv $old_jar_file $new_jar_file ; then
    echo "$msg Renaming .jar file succeeded."
else
    echo "$msg Renaming .jar file failed." EXIT
fi

# Message
echo "$msg Removing old platform folder if it exists... This MUST be done."
# Remove old platform folder.
if [ -d "$executable_folder" ] ; then
    rm -rf $executable_folder "$msg Removing old linux executable folder." || exit_on_error "$msg Failed to remove platform directory!"
else
    echo "$msg There is no Linux executable folder: Skipping this step."
fi

# Message
echo "$msg Building Linux executable using Packr..."
# Build linux executable using Packr and its own .json file.
if [ -f "$packr" ] ; then
    java -jar $packr $json_file || exit_on_error "$msg Failed to use Packr with Json file!"
else
    echo "$msg Could not find Packr. This is needed in /libs folder." EXIT
fi

# Message
echo "$msg Zipping Linux executable folder..."
# tar/gzip the folder containg platform executable
if [ -d "$executable_folder" ] ; then
    tar -czvf $zip_file $executable_folder || exit_on_error "$msg Failed to zip platform executable folder."
else
    echo "$msg There is no Linux executable folder to be used for zip." EXIT
fi

script_status=true;

if [ "$script_status" = true ] ; then
    echo "$msg Done!"
else
    echo "$msg Failed!"
fi
