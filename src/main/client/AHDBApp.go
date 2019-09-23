package main

import (
	"github.com/andlabs/ui"
	_ "github.com/andlabs/ui/winmanifest"
	"github.com/getlantern/systray"
	log "github.com/sirupsen/logrus"
	"io/ioutil"
	"os"
	"path/filepath"
	"runtime"
)

var mainwin *ui.Window
var wtfDirs []os.FileInfo

func mainui() {
	err := ui.Main(func() {
		mainwin = ui.NewWindow("Auction House Database App", 500, 500, true)
		mainwin.OnClosing(func(*ui.Window) bool {
			mainwin.Destroy()
			ui.Quit()
			systray.Quit()
			return false
		})
		ui.OnShouldQuit(func() bool {
			mainwin.Destroy()
			systray.Quit()
			return true
		})

		box := ui.NewVerticalBox()
		mainwin.SetChild(box)
		mainwin.SetMargined(true)

		chooseBtn := ui.NewButton("选择Wow.exe")
		box.Append(chooseBtn, true)

		chooseBtn.OnClicked(func(*ui.Button) {
			findTSMFiles(ui.OpenFile(mainwin))
		})

		hideBtn := ui.NewButton("最小化")
		box.Append(hideBtn, true)
		hideBtn.OnClicked(func(*ui.Button) {
			mainwin.Hide()
		})

		mainwin.Show()
	})
	if err != nil {
		log.Fatalln(err)
	}
}

func findTSMFiles(wowPathStr string) {
	dir := filepath.Dir(wowPathStr)
	log.Debug(dir)
	accountDirs, e := ioutil.ReadDir(filepath.Join(dir, "WTF"))
	if e != nil {
		log.Error(e)
	}
	log.Debug(accountDirs)
	wtfDirs = accountDirs
}

func main() {
	log.SetLevel(log.DebugLevel)
	log.SetFormatter(&log.TextFormatter{
		DisableColors: false,
	})

	platform := runtime.GOOS
	switch platform {
	case "windows":
		go mainui()
		setupTray()
	case "darwin":
		mainui()
	}

}
