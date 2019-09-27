package main

import (
	"github.com/getlantern/systray"
	"time"
)

func setupTray() {
	systray.Run(onReady, onExit)
}

func updateTray() {
	for {
		log.Debug("bian!")
		systray.SetIcon(Icon)
		time.Sleep(1 * time.Second)
		systray.SetIcon(IconMac)
		time.Sleep(1 * time.Second)
	}
}

func onReady() {
	go updateTray()
	systray.SetTooltip("最近上传：" + lastUpload.Format(timeLayout))
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
