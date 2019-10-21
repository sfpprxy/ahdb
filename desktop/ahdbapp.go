package main

import (
	"github.com/andlabs/ui"
	_ "github.com/andlabs/ui/winmanifest"
	"github.com/getlantern/systray"
	"github.com/sirupsen/logrus"
	easy "github.com/t-tomalak/logrus-easy-formatter"
	"os"
	"os/exec"
	"time"
)

var log = logrus.New()

func init() {
	handleCliInput(os.Args[1:])

	if onDebug() {
		log.SetLevel(logrus.DebugLevel)
	}
	log.SetFormatter(&easy.Formatter{
		TimestampFormat: "2006-01-02 15:04:05",
		LogFormat:       "[%lvl%]: %time% - %msg%\r\n",
	})
	if isOnWin() {
		f, err := os.OpenFile("ahdb.log", os.O_RDWR|os.O_APPEND|os.O_CREATE, 0755)
		check(err)
		log.SetOutput(f)
	}
	log.Info("log.Level ", log.Level)
}

var mainwin *ui.Window
var lastUpload time.Time
var lastUploadLbText = "最近充能点："
var accountLbText = "账号和角色："
var powerLbText = "传送器能量："
var accountStats AccountStats

func panelInfoUpdater(lastUploadLb *ui.Label, accountLb *ui.Label, powerLb *ui.Label) {
	for {
		accountStats = getAccountStats()

		ui.QueueMain(func() {
			lastUploadLb.SetText(lastUploadLbText + lastUpload.Format(timeLayout))
			accountLb.SetText(accountLbText + accountStats.AccountId + " - " + accountStats.Chars)
			powerLb.SetText(powerLbText + string(accountStats.Power))
		})
		time.Sleep(2 * time.Second)
	}
}

func mainui() {
	err := ui.Main(func() {
		mainwin = ui.NewWindow("地精传送器", 400, 300, true)
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

		box.SetPadded(true)

		accountLb := ui.NewLabel("账号和角色：")
		box.Append(accountLb, true)

		powerLb := ui.NewLabel("传送器能量：")
		box.Append(powerLb, true)

		lastUploadLb := ui.NewLabel(lastUploadLbText)
		box.Append(lastUploadLb, true)

		chooseBtn := ui.NewButton("选择Wow.exe")
		box.Append(chooseBtn, true)

		hideBtn := ui.NewButton("隐藏到托盘")
		box.Append(hideBtn, true)

		webBtn := ui.NewButton("传送到 藏宝海湾")
		box.Append(webBtn, true)

		chooseBtn.OnClicked(func(*ui.Button) {
			saveWowPath(ui.OpenFile(mainwin))
		})

		hideBtn.OnClicked(func(*ui.Button) {
			mainwin.Hide()
		})

		webBtn.OnClicked(func(*ui.Button) {
			url := getBootyBayUrl() + "/" + accountStats.AccountId
			var err error
			if isOnWin() {
				err = exec.Command("rundll32", "url.dll,FileProtocolHandler", url).Start()
			}
			if isOnMac() {
				err = exec.Command("open", url).Start()
			}
			check(err, "传送藏宝海湾失败")
		})

		go panelInfoUpdater(lastUploadLb, accountLb, powerLb)

		mainwin.Show()
	})
	check(err)
}

var lc = 1

func jobLoop() {
	lastUpload = readLastUploadTime()
	for {
		lc += 1
		log.Debug("loop: ", lc, "开始扫描...")
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
