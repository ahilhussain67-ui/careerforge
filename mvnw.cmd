@echo off
set "PROJECT_ROOT=%~dp0carrerforge"
pushd "%PROJECT_ROOT%"
call "%PROJECT_ROOT%\mvnw.cmd" %*
set "EXIT_CODE=%ERRORLEVEL%"
popd
exit /b %EXIT_CODE%
