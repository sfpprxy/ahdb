package main

import (
	"github.com/getlantern/systray"
	"time"
)

func setupTray() {
	systray.Run(onReady, onExit)
}

func trayUpdater() {
	for {
		diff := time.Now().Sub(lastUpload)
		if diff.Minutes() > 60 {
			systray.SetIcon(IconCopperWin)
		} else if diff.Minutes() > 30 {
			systray.SetIcon(IconSilverWin)
		} else {
			systray.SetIcon(IconGoldWin)
		}

		systray.SetTooltip("最近上传：" + lastUpload.Format(timeLayout))
		time.Sleep(2 * time.Second)
	}
}

func onReady() {
	go trayUpdater()
	mMain := systray.AddMenuItem("主界面", "1")
	mQuit := systray.AddMenuItem("退出", "2")

	go func() {
		for {
			select {
			case <-mMain.ClickedCh:
				log.Debug("mMain.ClickedCh")
				mainwin.Show()
			case <-mQuit.ClickedCh:
				log.Debug("mQuit.ClickedCh")
				systray.Quit()
				return
			}
		}
	}()
}

func onExit() {
	// clean up here
}
