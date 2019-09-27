package main

import (
	"github.com/andlabs/ui"
	_ "github.com/andlabs/ui/winmanifest"
	"github.com/getlantern/systray"
	"github.com/sirupsen/logrus"
	easy "github.com/t-tomalak/logrus-easy-formatter"
	"os"
	"time"
)

var log = logrus.New()

func init() {
	log.SetLevel(logrus.DebugLevel)
	log.SetFormatter(&easy.Formatter{
		TimestampFormat: "2006-01-02 15:04:05",
		LogFormat:       "[%lvl%]: %time% - %msg%\r\n",
	})
	if isOnWin() {
		f, err := os.OpenFile("ahdb.log", os.O_WRONLY|os.O_CREATE, 0755)
		check(err)
		log.SetOutput(f)
	}
}

var mainwin *ui.Window
var lastUpload time.Time

func panelInfoUpdater(lastUploadLb *ui.Label) {
	for {
		ui.QueueMain(func() {
			lastUploadLb.SetText("最近上传：" + lastUpload.Format(timeLayout))
		})
		time.Sleep(1 * time.Second)
	}
}

func mainui() {
	err := ui.Main(func() {
		mainwin = ui.NewWindow("Auction House Database App", 400, 300, true)
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

		hbox := ui.NewHorizontalBox()
		box.Append(hbox, true)
		box.SetPadded(true)

		lastUploadLb := ui.NewLabel("最近上传：")
		hbox.Append(lastUploadLb, true)

		chooseBtn := ui.NewButton("选择Wow.exe")
		box.Append(chooseBtn, true)

		hideBtn := ui.NewButton("隐藏到托盘")
		box.Append(hideBtn, true)

		chooseBtn.OnClicked(func(*ui.Button) {
			saveWowPath(ui.OpenFile(mainwin))
		})
		hideBtn.OnClicked(func(*ui.Button) {
			mainwin.Hide()
		})

		go panelInfoUpdater(lastUploadLb)

		mainwin.Show()
	})
	check(err)
}

func jobLoop() {
	lastUpload = readLastUploadTime()
	for {
		log.Debug("开始扫描...")
		changedTsmfilesByAccount := getChangedTsmfilesByAccount()
		if len(changedTsmfilesByAccount) == 0 {
			log.Debug("无文件变化")
			time.Sleep(time.Second * 10)
			continue
		}

		valuableDataByAccount := extractValuableDataByAccount(changedTsmfilesByAccount)
		if len(valuableDataByAccount) == 0 {
			log.Debug("无新数据上传")
			time.Sleep(time.Second * 10)
			continue
		}

		uploaded := upload(valuableDataByAccount)
		if uploaded {
			log.Info("数据上传成功")
			lastUpload = time.Now()
			saveLastUploadTime(lastUpload)
		} else {
			log.Error("数据上传失败")
		}

		time.Sleep(time.Minute * 1)
	}
}

func main() {
	if isOnMac() {
		go jobLoop()
		mainui()
		//setupTray()
	}

	if isOnWin() {
		go jobLoop()
		go mainui()
		setupTray()
	}
}
