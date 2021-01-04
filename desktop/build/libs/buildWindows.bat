@ECHO OFF

REM This is a script to build executable for Windows.
REM I use this because I cant build for Windows on Linux somehow.

REM This will first build the game .jar file using Gradle.
REM After that it will build executable for Windows.
REM At last it will zip the executable.

SET "MSG=SCRIPT STATUS:"
SET /A SCRIPT_SUCCESS=1

REM Message
ECHO %MSG% Running Windows build script...
REM Move to project root folder.
CD ..\..\.. || ECHO %MSG% Could not find project root folder. && EXIT /b

REM Message
ECHO %MSG% Building jar file using gradlew...
REM Build game .jar file.
CALL gradlew desktop:dist && ECHO %MSG% Building .jar file succeeded. || ECHO %MSG% Building .jar file failed. && EXIT /b

REM Cd to libs:
CD desktop\build\libs || %MSG% Could not find desktop/build/libs folder. && EXIT /b

REM Message WINDOWS ONLY
ECHO %MSG% Removing old game title .jar.
REM Remove old game title jar.
IF EXIST UltraNightmare.jar (
    DEL /S /F /Q UltraNightmare.jar && ECHO %MSG% Removing old -game title- jar folder. || ECHO %MSG% Failed to remove old -game title- jar folder! && EXIT /b
) ELSE (
    ECHO %MSG% There is no old -game title- jar file: Skipping this step.
)

REM Message
ECHO %MSG% Renaming jar file to game title...
REM Rename standard jar file name with title of game.
REN desktop-1.0.jar UltraNightmare.jar && ECHO %MSG% Renaming .jar file succeeded. || ECHO %MSG% Renaming .jar file failed. && EXIT /b

REM Message
ECHO %MSG% Removing old platform folder if it exists... This MUST be done.
REM Remove old platform folder.
IF EXIST UltraNightmare_Windows (
    RMDIR /S /Q UltraNightmare_Windows && ECHO %MSG% Removing old Windows executable folder. || ECHO %MSG% Failed to remove platform directory! && EXIT /b
) ELSE (
    ECHO %MSG% There is no Windows executable folder: Skipping this step.
)

REM Message
ECHO %MSG% Building Windows executable using Packr...
REM Build Windows executable using Packr and its own .json file.
IF EXIST packr-all-3.0.1.jar (
    java -jar packr-all-3.0.1.jar buildExecutableWindows64.json || ECHO %MSG% Failed to use Packr with Json file! && EXIT /b
) ELSE (
    ECHO %MSG% Could not find Packr. This is needed in \libs folder. && EXIT /b
)

REM Message
ECHO %MSG% Zipping Windows executable folder...
REM Zip the folder containg platform executable
IF EXIST UltraNightmare_Windows (
    tar.exe -a -c -f UltraNightmare_Windows.zip UltraNightmare_Windows || ECHO %MSG% Failed to zip platform executable folder. && EXIT /b
) ELSE (
    ECHO %MSG% There is no Windows executable folder to be used for zip. && EXIT /b
)

SET /A SCRIPT_SUCCESS=0

IF /I "%SCRIPT_SUCCESS%" EQU "0" (
    ECHO %MSG% Done!
) ELSE (
    ECHO %MSG% Failed!
)

EXIT /b
