'use strict';

const electron = require('electron');
const app = electron.app; //Module to control App Life
const BrowserWindow = electron.BrowserWindow; //Module to create native browser window

var mainWindow = null;

app.on('window-all-closed', function() {
  // Don't quit if Mac OSX
  if (process.platform != 'darwin') {
    app.quit();
  }
});

//When electron has finished initialization and is ready to start creating windows
app.on('ready', function() {
  //Create browser window
  mainWindow = new BrowserWindow({width: 800, height: 600});

  //Load index.html of app
  mainWindow.loadURL('file://' + __dirname + '/index.html');

  mainWindow.webContents.openDevTools();

  mainWindow.on('closed',function() {
    mainWindow = null;
  });
});

