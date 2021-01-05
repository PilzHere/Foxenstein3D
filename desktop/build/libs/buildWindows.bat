@echo off

rem This is a script to build executable for Windows.
rem I use this because I cant build for Windows on Linux somehow.

rem This will first build the game .jar file using Gradle.
rem After that it will build executable for Windows.
rem At last it will zip the executable.

set "old_jar_file=desktop-1.0.jar"
set "new_jar_file=UltraNightmare.jar"
set "executable_folder=exe\UltraNightmare_Windows"
set "zip_file=zip\Ultra_Nightmare_Windows.zip"
set "packr=packr\packr-all-3.0.1.jar"
set "json_file=json\buildExecutableWindows64.json"

set "msg=SCRIPT STATUS:"
set /A script_status=1

rem Message
echo %msg% Running Windows build script...
rem Move to project root folder.
cd ..\..\.. || echo %msg% Could not find project root folder. && exit /b

rem Message
echo %msg% Building jar file using gradlew...
rem Build game .jar file.
call gradlew desktop:dist && echo %msg% Building .jar file succeeded. || echo %msg% Building .jar file failed. && exit /b

rem Cd to libs:
cd desktop\build\libs || %msg% Could not find desktop/build/libs folder. && exit /b

rem Message WINDOWS ONLY
echo %msg% Removing old game title .jar.
rem Remove old game title jar.
if exist %new_jar_file% (
    del /S /F /Q %new_jar_file% && echo %msg% Removing old -game title- jar folder. || echo %msg% Failed to remove old -game title- jar folder! && exit /b
) else (
    echo %msg% There is no old -game title- jar file: Skipping this step.
)

rem Message
echo %msg% Renaming jar file to game title...
rem Rename standard jar file name with title of game.
ren %old_jar_file% %new_jar_file% && echo %msg% Renaming .jar file succeeded. || echo %msg% Renaming .jar file failed. && exit /b

rem Message
echo %msg% Removing old platform folder if it exists... This MUST be done.
rem Remove old platform folder.
if exist %executable_folder% (
    rmdir /S /Q %executable_folder% && echo %msg% Removing old Windows executable folder. || echo %msg% Failed to remove platform directory! && exit /b
) else (
    echo %msg% There is no Windows executable folder: Skipping this step.
)

rem Message
echo %msg% Building Windows executable using Packr...
rem Build Windows executable using Packr and its own .json_file file.
if exist %packr% (
    java -jar %packr% %json_file% || echo %msg% Failed to use Packr with Json file! && exit /b
) else (
    echo %msg% Could not find Packr. This is needed in \libs folder. && exit /b
)

rem Message
echo %msg% Zipping Windows executable folder...
rem Zip the folder containg platform executable
if exist %executable_folder% (
    tar.exe -a -c -f %zip_file% %executable_folder% || echo %msg% Failed to zip platform executable folder. && exit /b
) else (
    echo %msg% There is no Windows executable folder to be used for zip. && exit /b
)

set /A script_status=0

if /I "%script_status%" EQU "0" (
    echo %msg% Done!
) else (
    echo %msg% Failed!
)

exit /b
