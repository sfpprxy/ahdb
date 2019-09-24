package main

import (
	"github.com/andlabs/ui"
	_ "github.com/andlabs/ui/winmanifest"
	"github.com/getlantern/systray"
	"github.com/sirupsen/logrus"
	easy "github.com/t-tomalak/logrus-easy-formatter"
	"os"
	"runtime"
	"time"
)

var log = logrus.New()
func init () {
	log.SetLevel(logrus.DebugLevel)
	log.SetFormatter(&easy.Formatter{
		TimestampFormat: "2006-01-02 15:04:05",
		LogFormat:       "[%lvl%]: %time% - %msg%\n",
	})
}

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
			saveWowPath(ui.OpenFile(mainwin))
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

func jobLoop() {
	for {
		changedTsmfilesByAccount := getChangedTsmfilesByAccount()
		if len(changedTsmfilesByAccount) == 0 {
			break
		}
		valuableDataByAccount := selectValuableDataByAccount(changedTsmfilesByAccount)
		log.Debug(valuableDataByAccount)
		//TODO impl uploader
		time.Sleep(time.Minute * 1)
	}
}

func main() {
	platform := runtime.GOOS
	switch platform {
	case "windows":
		go mainui()
		setupTray()
		go jobLoop()
	case "darwin":
		mainui()
		go jobLoop()
	}

}
